package domain;

import domain.database.DatabaseController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;

public class MaintenanceFileService {

    private static MaintenanceFile maintenanceFile;

    private MaintenanceFileService() {
    }

    private static void initializeMaintenanceFile() {
        String[] result = DatabaseController.getDefaultMaintenanceFile();
        if (result[0] != null) {
            maintenanceFile = new MaintenanceFile(UUID.fromString(result[0]), result[1]);
            getTasksFromDatabase();
        } else {
            maintenanceFile = new MaintenanceFile(UUID.randomUUID(), "Unnamed");
        }

        // get tasks with result[2]
    }

    public static MaintenanceFile getDefaultMaintenanceFile() {
        if (maintenanceFile == null) {
            initializeMaintenanceFile();
        }
        return maintenanceFile;
    }

    public static MaintenanceFile getmaintenanceFile(UUID uuid) {
        String[] result = DatabaseController.getMaintenanceFile(uuid);

        if (result[0] != null) {
            maintenanceFile = new MaintenanceFile(UUID.fromString(result[0]), result[1]);
            getTasksFromDatabase();
        }

        return maintenanceFile;
    }

    public static ArrayList<MaintenanceFile> getAllMaintenanceFiles() {
        ArrayList<MaintenanceFile> maintenanceFiles = new ArrayList<>();

        try {
            ResultSet result = DatabaseController.getMaintenanceFiles();
            while (result.next()) {
                maintenanceFiles.add(new MaintenanceFile(UUID.fromString(result.getString("uuid")), result.getString("name")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return maintenanceFiles;
    }

    public static void createMaintenanceFile(String name, Boolean isDefault) {
        UUID id = UUID.randomUUID();
        int isDefaultInt = isDefault ? 1 : 0;
        maintenanceFile = new MaintenanceFile(id, name);

        DatabaseController.addMaintenanceFile(id, name, isDefaultInt);
    }

    public static void createTask(String name, LocalDate creationDate, LocalDate dueDate, int recurringTime) {

        MaintenanceTask task;
        if (recurringTime > 0) {
            task = new RecurringTask(name, creationDate, recurringTime, dueDate);
        } else {
            task = new OneTimeTask(name, creationDate, dueDate);
        }
        maintenanceFile.addTask(task);

    }

    public static ArrayList<MaintenanceTask> getTasks() {

        return new ArrayList<>(maintenanceFile.getTasks().values());
    }

    public static List<MaintenanceTask> getTasks(int year, Month month) {
        return maintenanceFile.getTasks().values().stream()
                .filter(task -> task.getDueDate().getYear() == year)
                .filter(task -> task.getDueDate().getMonth() == month)
                .collect(Collectors.toList());
    }

    public static void deleteTask(MaintenanceTask taskToRemove) {
        maintenanceFile.deleteTask(taskToRemove);
    }

    public static void updateTask(MaintenanceTask task, String updatedName, LocalDate updatedCreationDate, int updatedRecurringInterval) {
        task.setName(updatedName);
        task.setCreationDate(updatedCreationDate);
        if (task.getClass() == RecurringTask.class) {
            ((RecurringTask) task).setRecurringIntervalMonths(updatedRecurringInterval);
        }
        maintenanceFile.updateTask(task);
    }

    public static void saveMaintenanceFile() {
        ArrayList<UUID> addedTasks = maintenanceFile.getAddedTasksIds();
        ArrayList<UUID> modiefiedTasks = maintenanceFile.getModifiedTaskIds();
        ArrayList<UUID> deletedTasks = maintenanceFile.getDeletedTaskIds();

        addedTasks.forEach(uuid -> {
            DatabaseController.addTask(maintenanceFile.getId(), maintenanceFile.getTasks().get(uuid));
        });

        modiefiedTasks.forEach(uuid -> {
            DatabaseController.updateTask(maintenanceFile.getTasks().get(uuid));
        });

        deletedTasks.forEach(uuid -> {
            DatabaseController.deleteTask(uuid);
        });
    }

    private static void getTasksFromDatabase() {
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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
