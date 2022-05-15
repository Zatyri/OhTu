package domain.database;

import domain.MaintenanceTask;
import domain.RecurringTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import org.sqlite.SQLiteConfig;

/**
 * Controls data flow to the database
 *
 */
public class DatabaseController {

    private static Connection connection = null;
    private static String dbName = "MaintenanceFileDB";

    private static Connection connect() {

        String url = "jdbc:sqlite:" + dbName;

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

    /**
     * Initializes the database by creating required tables if they do not exist
     *
     * @param dbName name of the database. Used only for testing purposes
     */
    public static void initializeDatabase(String dbName) {
        if (dbName != null) {
            DatabaseController.dbName = dbName;
        }

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

    /**
     * Gets all maintenance files from database
     *
     * @return SQL Result set of maintenance files
     */
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

    /**
     * Gets specific maintenance file from database
     *
     * @param id of the wanted maintenance file
     * @return array of strings. [0] = uuid, [1] = name, [2] = database index
     * id, [3] = isDefault
     */
    public static String[] getMaintenanceFile(UUID id) {
        String sqlGetMaintenanceFile = "SELECT id, uuid, name, isDefault FROM maintenanceFiles WHERE uuid = ?";
        String[] result = new String[4];

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlGetMaintenanceFile);

            pstmt.setString(1, id.toString());

            ResultSet resultSet = pstmt.executeQuery();

            result[0] = resultSet.getString("uuid");
            result[1] = resultSet.getString("name");
            result[2] = resultSet.getString("id");
            result[3] = String.valueOf(resultSet.getInt("isDefault"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Gets default maintenance file from database
     *
     * @return array of strings. [0] = uuid, [1] = name, [2] = database index
     * id, [3] = isDefault
     */
    public static String[] getDefaultMaintenanceFile() {
        String sqlGetMaintenanceFile = "SELECT id, uuid, name, isDefault "
                + "FROM maintenanceFiles "
                + "WHERE isDefault = 1";
        String[] result = new String[4];

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlGetMaintenanceFile);

            ResultSet resultSet = pstmt.executeQuery();

            result[0] = resultSet.getString("uuid");
            result[1] = resultSet.getString("name");
            result[2] = resultSet.getString("id");
            result[3] = String.valueOf(resultSet.getInt("isDefault"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Adds maintenance file to database
     *
     * @param uuid of the maintenance file
     * @param name of the maintenance file
     * @param isDefault if maintenance file is set as default
     * @return true if procedure is success, otherwise false
     */
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
        String sqlResetDefaultValues = "UPDATE maintenanceFiles SET isDefault = 0";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlResetDefaultValues);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add task to database
     *
     * @param maintenanceFileId uuid of the maintenance file the task belongs to
     * @param task the task to add to the database
     * @return true if success, false if fail
     */
    public static boolean addTask(UUID maintenanceFileId, MaintenanceTask task) {
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
            return false;
        }
        return true;
    }

    /**
     * Update task in database
     *
     * @param task task to update (task should contain data to update)
     * @return true if successful, false if not
     */
    public static Boolean updateTask(MaintenanceTask task) {
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
                pstmt.setInt(
                        6, ((RecurringTask) task).getRecurringIntervalMonths());
            }
            pstmt.setString(7, task.getID().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Delete task from database
     *
     * @param taskId uuid of the task
     * @return true if successful, false if not
     */
    public static Boolean deleteTask(UUID taskId) {
        String sqlDeleteTask = "DELETE FROM maintenanceTasks WHERE uuid = ?";

        try {
            Connection conn = connect();

            PreparedStatement pstmt = conn.prepareStatement(sqlDeleteTask);

            pstmt.setString(1, taskId.toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Get specific maintenance file from database
     *
     * @param id uuid of the maintenance file
     * @return result set of the maintenance file
     */
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

    public static void deleteMaintenanceFile(UUID id) {
        String sqlDeleteMaintenanceFile = "DELETE FROM maintenanceFiles WHERE uuid = ?";

        try {
            Connection conn = connect();

            PreparedStatement pstmt = conn.prepareStatement(sqlDeleteMaintenanceFile);

            pstmt.setString(1, id.toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sets maintenance file as default
     *
     * @param id the maintenance file uuid to set as default
     */
    public static void setMaintenanceFileAsDefault(UUID id) {
        String sqlsetDefaultMaintenanceFile = "UPDATE maintenanceFiles SET isDefault = 1 "
                + "WHERE uuid = ?";

        resetIsDefaultValues();

        try {
            Connection conn = connect();

            PreparedStatement pstmt = conn.prepareStatement(sqlsetDefaultMaintenanceFile);

            pstmt.setString(1, id.toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the maintenance file
     *
     * @param id the maintenance file uuid to set as default
     * @param newName the new name of the maintenance file
     */
    public static void updateMaintenanceFile(UUID id, String newName) {
        String sqlUpdateMaintenanceFile = "UPDATE maintenanceFiles SET name = ? "
                + "WHERE uuid = ?";

        try {
            Connection conn = connect();

            PreparedStatement pstmt = conn.prepareStatement(sqlUpdateMaintenanceFile);

            pstmt.setString(1, newName);
            pstmt.setString(2, id.toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
