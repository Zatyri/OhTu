package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class MaintenanceFileService {

    private static MaintenanceFile maintenanceFile;

    private MaintenanceFileService() {
    }

    private static void initializeMaintenanceFile() {
        maintenanceFile = new MaintenanceFile();

    }

    public static MaintenanceFile getMaintenanceFile() {
        if (maintenanceFile == null) {
            initializeMaintenanceFile();
        }
        return maintenanceFile;
    }

    public static void createTask(String name, LocalDate creationDate, int recurringTime) {

        MaintenanceTask task;
        if (recurringTime > 0) {
            task = new RecurringTask(name, creationDate, recurringTime);
        } else {
            task = new OneTimeTask(name, creationDate);
        }
        maintenanceFile.addTask(task);
    }

    public static ArrayList<MaintenanceTask> getTasks() {
        return maintenanceFile.getTasks();
    }

    public static void deleteTask(MaintenanceTask taskToRemove) {
        maintenanceFile.getTasks().remove(taskToRemove);
    }

}
