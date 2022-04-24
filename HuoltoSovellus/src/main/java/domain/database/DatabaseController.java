package domain.database;

import domain.MaintenanceTask;
import domain.RecurringTask;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import org.sqlite.SQLiteConfig;

public class DatabaseController {

    private static Connection connection = null;

    private static Connection connect() {
        String url = "jdbc:sqlite:MaintenanceFileDB";

        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, config.toProperties());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void initializeDatabase() {

        String sqlCreateMaintenanceFilesTable = "CREATE TABLE IF NOT EXISTS maintenanceFiles ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "uuid TEXT NOT NULL, "
                + "name TEXT NOT NULL, "
                + "isDefault INTEGER)";

        String sqlCreateMaintenanceTasksTable = "CREATE TABLE IF NOT EXISTS maintenanceTasks ("
                + "id integer PRIMARY KEY AUTOINCREMENT, "
                + "uuid text NOT NULL, "
                + "name text NOT NULL, "
                + "creationDate text NOT NULL, "
                + "completedOnDate text, "
                + "dueDate text, "
                + "isCompleted integer NOT NULL, "
                + "recurringIntervalMonths integer DEFAULT 0)";

        String sqlCreateFileTaskJoinTable = "CREATE TABLE IF NOT EXISTS maintenanceFileTasks ("
                + "maintenanceFileID integer NOT NULL, "
                + "taskID integer NOT NULL, "
                + "PRIMARY KEY (maintenanceFileID, taskID),"
                + "FOREIGN KEY (maintenanceFileID) REFERENCES maintenanceFiles (id) ON DELETE CASCADE,"
                + "FOREIGN KEY (taskID) REFERENCES maintenanceTasks (id) ON DELETE CASCADE)";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            stmt.execute(sqlCreateMaintenanceFilesTable);
            stmt.execute(sqlCreateMaintenanceTasksTable);
            stmt.execute(sqlCreateFileTaskJoinTable);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet getMaintenanceFiles() {
        String sqlGetMaintenanceFiles = "SELECT id, uuid, name, isDefault FROM maintenanceFiles";
        ResultSet resultSet = null;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sqlGetMaintenanceFiles);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultSet;
    }

    public static String[] getMaintenanceFile(UUID id) {
        String sqlGetMaintenanceFile = "SELECT id, uuid, name FROM maintenanceFiles WHERE uuid = ?";
        String[] result = new String[3];

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlGetMaintenanceFile);

            pstmt.setString(1, id.toString());

            ResultSet resultSet = pstmt.executeQuery();

            result[0] = resultSet.getString("uuid");
            result[1] = resultSet.getString("name");
            result[2] = resultSet.getString("id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static String[] getDefaultMaintenanceFile() {
        String sqlGetMaintenanceFile = "SELECT id, uuid, name, isDefault "
                + "FROM maintenanceFiles "
                + "WHERE isDefault = 1";
        String[] result = new String[3];

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlGetMaintenanceFile);

            ResultSet resultSet = pstmt.executeQuery();

            result[0] = resultSet.getString("uuid");
            result[1] = resultSet.getString("name");
            result[2] = resultSet.getString("id");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static Boolean addMaintenanceFile(UUID uuid, String name, int isDefault) {
        String sqlAddMaintenanceFile = "INSERT INTO maintenanceFiles(uuid, name, isDefault) VALUES(?,?,?)";
        Boolean isSuccess = false;

        if (isDefault == 1) {
            resetIsDefaultValues();
        }

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlAddMaintenanceFile);
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, name);
            pstmt.setInt(3, isDefault);
            pstmt.executeUpdate();

            isSuccess = true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return isSuccess;
    }

    private static void resetIsDefaultValues() {
        String sqlResetDefaultValues = "UPDATE maintenanceFiles SET isDefault = 0 , "
                + "WHERE id = *";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlResetDefaultValues);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addTask(UUID maintenanceFileId, MaintenanceTask task) {
        String sqlAddTask = "INSERT INTO maintenanceTasks(uuid, name, creationDate, "
                + "completedOnDate, dueDate, isCompleted, recurringIntervalMonths) "
                + "VALUES(?,?,?,?,?,?,?)";
        String sqlCreateRelation = "INSERT INTO maintenanceFileTasks (maintenanceFileID, taskID)"
                + "VALUES(?,?)";

        try {
            String[] result = getMaintenanceFile(maintenanceFileId);

            Connection conn = connect();
            conn.setAutoCommit(false);
            PreparedStatement pstmt1 = conn.prepareStatement(sqlAddTask, Statement.RETURN_GENERATED_KEYS);

            pstmt1.setString(1, task.getID().toString());
            pstmt1.setString(2, task.getName());
            pstmt1.setString(3, task.getCreationDate().toString());
            if (task.getCompletedOnDate() != null) {
                pstmt1.setString(4, task.getCompletedOnDate().toString());
            }
            if (task.getDueDate() != null) {
                pstmt1.setString(5, task.getDueDate().toString());
            }
            pstmt1.setInt(6, task.getIsCompleted() ? 1 : 0);
            if (task.getClass() == RecurringTask.class) {
                pstmt1.setInt(7, ((RecurringTask) task).getRecurringIntervalMonths());
            }

            int rowsAffected = pstmt1.executeUpdate();

            ResultSet resultSet = pstmt1.getGeneratedKeys();
            int addedTaskId = 0;
            if (resultSet.next()) {
                addedTaskId = resultSet.getInt(1);
            }

            if (rowsAffected != 1) {
                conn.rollback();
            }

            PreparedStatement pstmt2 = conn.prepareStatement(sqlCreateRelation);
            pstmt2.setInt(1, Integer.parseInt(result[2]));
            pstmt2.setInt(2, addedTaskId);

            pstmt2.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateTask(MaintenanceTask task) {
        String sqlUpdateTask = "UPDATE maintenanceTasks SET name = ? , "
                + "creationDate = ? , "
                + "completedOnDate = ? , "
                + "dueDate = ? , "
                + "isCompleted = ? , "
                + "recurringIntervalMonths = ? "
                + "WHERE uuid = ?";

        try {
            Connection conn = connect();

            PreparedStatement pstmt = conn.prepareStatement(sqlUpdateTask);
            pstmt.setString(1, task.getName());
            pstmt.setString(2, task.getCreationDate().toString());
            if (task.getCompletedOnDate() != null) {
                pstmt.setString(3, task.getCompletedOnDate().toString());
            }
            if (task.getDueDate() != null) {
                pstmt.setString(4, task.getDueDate().toString());
            }
            pstmt.setInt(5, task.getIsCompleted() ? 1 : 0);
            if (task.getClass() == RecurringTask.class) {
                pstmt.setInt(6, ((RecurringTask) task).getRecurringIntervalMonths());
            }
            pstmt.setString(7, task.getID().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTask(UUID taskId) {
        String sqlDeleteTask = "DELETE FROM maintenanceTasks WHERE uuid = ?";

        try {
            Connection conn = connect();

            PreparedStatement pstmt = conn.prepareStatement(sqlDeleteTask);

            pstmt.setString(1, taskId.toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet getMaintenanceFileTasks(UUID id) {
        String sqlSelectTasks = "SELECT uuid, name, creationDate, "
                + "completedOnDate, dueDate, isCompleted, recurringIntervalMonths "
                + "FROM maintenanceFileTasks "
                + "JOIN maintenanceTasks "
                + "ON maintenanceFileTasks.taskID = maintenanceTasks.ID "
                + "WHERE maintenanceFileTasks.maintenanceFileID = ?";

        String[] result = getMaintenanceFile(id);

        ResultSet resultSet = null;
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlSelectTasks);

            pstmt.setInt(1, Integer.parseInt(result[2]));

            resultSet = pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultSet;
    }
}
