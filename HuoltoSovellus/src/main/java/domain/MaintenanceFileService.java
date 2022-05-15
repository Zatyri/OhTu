package domain;

import domain.database.DatabaseController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Class to allow ui to access data
 */
public class MaintenanceFileService {

    private static MaintenanceFile maintenanceFile;

    private MaintenanceFileService() {
    }

    private static void initializeMaintenanceFile() {
        String[] result = DatabaseController.getDefaultMaintenanceFile();
        if (result[0] != null) {
            maintenanceFile = new MaintenanceFile(UUID.fromString(result[0]), result[1], result[3].equals("1"));
            getTasksFromDatabase();
        } else {
            maintenanceFile = new MaintenanceFile(UUID.randomUUID(), "Unnamed", false, true);
        }

        // get tasks with result[2]
    }

    /**
     * Gets the default maintenance file from the database If default database
     * is not found is a new unnamed maintenance file generated
     *
     * @return the initialized maintenance file
     */
    public static MaintenanceFile getDefaultMaintenanceFile() {
        if (maintenanceFile == null) {
            initializeMaintenanceFile();
        }
        return maintenanceFile;
    }

    /**
     * Gets a specified maintenance file from the database
     *
     * @param uuid the id of the maintenance file to retrieve
     * @return the retrieved maintenance file. Returns the current one if
     * maintenance file is not found in database
     */
    public static MaintenanceFile getmaintenanceFile(UUID uuid) {
        String[] result = DatabaseController.getMaintenanceFile(uuid);

        if (result[0] != null) {
            maintenanceFile = new MaintenanceFile(UUID.fromString(result[0]), result[1], result[3].equals("1"));
            getTasksFromDatabase();
        }

        return maintenanceFile;
    }

    /**
     * Gets the all maintenance files from the database
     *
     * @return ArrayList containing maintenance files
     */
    public static ArrayList<MaintenanceFile> getAllMaintenanceFiles() {
        ArrayList<MaintenanceFile> maintenanceFiles = new ArrayList<>();

        try {
            ResultSet result = DatabaseController.getMaintenanceFiles();
            while (result.next()) {
                maintenanceFiles.add(new MaintenanceFile(UUID.fromString(result.getString("uuid")), result.getString("name"), result.getInt("isDefault") == 1));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return maintenanceFiles;
    }

    /**
     * Creates a new maintenance file
     *
     *
     * @param name name of the maintenance file
     * @param isDefault default maintenance file will be shown when opening
     * application
     */
    public static void createMaintenanceFile(String name, Boolean isDefault) {
        UUID id = UUID.randomUUID();
        int isDefaultInt = isDefault ? 1 : 0;
        maintenanceFile = new MaintenanceFile(id, name, isDefault);

        DatabaseController.addMaintenanceFile(id, name, isDefaultInt);
    }

    /**
     * Creates a new maintenance task and adds it to the active maintenance file
     *
     * @param name name of the maintenance file
     * @param creationDate date when task was created
     * @param dueDate date when the task due date is
     * @param recurringTime number of months between recurrences
     * @return UUID of the created task
     *
     */
    public static UUID createTask(String name, LocalDate creationDate, LocalDate dueDate, int recurringTime) {

        MaintenanceTask task;
        if (recurringTime > 0) {
            task = new RecurringTask(name, creationDate, recurringTime, dueDate);
            maintenanceFile.addTask(task);

        } else {
            task = new OneTimeTask(name, creationDate, dueDate);
            maintenanceFile.addTask(task);
        }

        return task.getID();
    }

    public static ArrayList<UUID> getModifiedTasks() {
        return new ArrayList<>(maintenanceFile.getModifiedTaskIds());
    }

    public static ArrayList<MaintenanceTask> getTasks() {
        if (maintenanceFile == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(maintenanceFile.getTasks().values());
    }

    /**
     * Gets tasks with due date from specific month
     *
     * @param year the year of the due date
     * @param month the month of the due date
     * @return list of maintenance tasks
     */
    public static List<MaintenanceTask> getTasks(int year, Month month) {
        return maintenanceFile.getTasks().values().stream()
                .filter(task -> task.getDueDate().getYear() == year)
                .filter(task -> task.getDueDate().getMonth() == month)
                .collect(Collectors.toList());
    }

    public static void deleteTask(MaintenanceTask taskToRemove) {
        maintenanceFile.deleteTask(taskToRemove);
    }

    public static void updateTask(MaintenanceTask task, String updatedName, LocalDate updatedCreationDate, int updatedRecurringInterval, boolean isCompleted, LocalDate dueDate) {
        task.setName(updatedName);
        task.setCreationDate(updatedCreationDate);
        task.setDueDate(dueDate);
        if (task.getClass() == RecurringTask.class) {
            ((RecurringTask) task).setRecurringIntervalMonths(updatedRecurringInterval);
        }
        if (isCompleted) {
            task.setCompletedNow();
            if (task.getClass() == RecurringTask.class && !((RecurringTask) task).getMarkedCompletedInInstance()) {
                createTask(updatedName, updatedCreationDate, dueDate.plusMonths(updatedRecurringInterval), updatedRecurringInterval);
                ((RecurringTask) task).setMarkedCompletedInInstance(true);
            }
        } else {
            task.setAsNotCompleted();
        }
        maintenanceFile.updateTask(task);
    }

    /**
     * Saves the active maintenance file to the database
     */
    public static void saveMaintenanceFile() {
        ArrayList<UUID> addedTasks = maintenanceFile.getAddedTasksIds();
        ArrayList<UUID> modiefiedTasks = maintenanceFile.getModifiedTaskIds();
        ArrayList<UUID> deletedTasks = maintenanceFile.getDeletedTaskIds();

        addedTasks.forEach(uuid -> {
            if (DatabaseController.addTask(maintenanceFile.getId(), maintenanceFile.getTasks().get(uuid))) {
                addedTasks.remove(uuid);
            }
        });

        modiefiedTasks.forEach(uuid -> {
            if (DatabaseController.updateTask(maintenanceFile.getTasks().get(uuid))) {
                modiefiedTasks.remove(uuid);
            }
        });

        deletedTasks.forEach(uuid -> {
            if (DatabaseController.deleteTask(uuid)) {
                deletedTasks.remove(uuid);
            }
        });
    }

    /**
     * Gets all tasks from database
     *
     * @return true if successful, false if not
     */
    public static Boolean getTasksFromDatabase() {
        ResultSet result = DatabaseController.getMaintenanceFileTasks(maintenanceFile.getId());

        MaintenanceTask task;
        UUID id;
        String name;
        LocalDate creationDate;
        LocalDate completedOnDate;
        LocalDate dueDate;
        Boolean isCompleted;
        int recurringIntervalMonths;

        try {
            while (result.next()) {
                id = UUID.fromString(result.getString("uuid"));
                name = result.getString("name");
                creationDate = LocalDate.parse(result.getString("creationDate"));
                if (result.getString("completedOnDate") != null) {
                    completedOnDate = LocalDate.parse(result.getString("completedOnDate"));
                } else {
                    completedOnDate = null;
                }
                if (result.getString("dueDate") != null) {
                    dueDate = LocalDate.parse(result.getString("dueDate"));
                } else {
                    dueDate = null;
                }
                isCompleted = result.getInt("isCompleted") == 1;

                if (result.getInt("recurringIntervalMonths") != 0) {
                    recurringIntervalMonths = result.getInt("recurringIntervalMonths");
                    task = new RecurringTask(id, name, creationDate, completedOnDate, dueDate, isCompleted, recurringIntervalMonths);
                } else {
                    task = new OneTimeTask(id, name, creationDate, completedOnDate, dueDate, isCompleted);
                }

                maintenanceFile.getTasks().put(id, task);
            }
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    /**
     * Delete maintenance file from database
     *
     * @param id the uuid of the file to delete
     */
    public static void deleteMaintenanceFile(UUID id) {
        DatabaseController.deleteMaintenanceFile(id);
        if (id.toString().equals(maintenanceFile.getId().toString())) {
            maintenanceFile = null;
            getDefaultMaintenanceFile();
        }
    }

    /**
     * Sets maintenance file as default
     *
     * @param id the maintenance file uuid to set as default
     */
    public static void setMaintenanceFileAsDefault(UUID id) {
        DatabaseController.setMaintenanceFileAsDefault(id);
    }

    /**
     * Updated the maintenance file
     *
     * @param id of the maintenance file
     * @param newName new name of the maintenance file
     */
    public static void updateMaintenanceFile(UUID id, String newName) {
        DatabaseController.updateMaintenanceFile(id, newName);
    }

    /**
     * Get task that are not completed and are over due date
     *
     * @return tasks that are not completed and are over due date
     */
    public static List<MaintenanceTask> getOverDueTasks() {
        LocalDate dateNow = LocalDate.now();
        return maintenanceFile.getTasks().values().stream()
                .filter(f -> f.getDueDate().isBefore(dateNow) && !f.getIsCompleted())
                .collect(Collectors.toList());
    }

    public static List<MaintenanceTask> getCompletedTasks() {
        return maintenanceFile.getTasks().values().stream()
                .filter(f -> f.getIsCompleted())
                .collect(Collectors.toList());
    }
}
