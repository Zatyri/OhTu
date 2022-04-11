package domain;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MaintenanceFile {

    private ArrayList<MaintenanceTask> tasks;

    public MaintenanceFile() {
        tasks = new ArrayList<>();
    }

    /**
     * @return the tasks
     */
    public ArrayList<MaintenanceTask> getTasks() {
        return tasks;
    }

    /**
     * @param task the tasks to add to list
     */
    public void addTask(MaintenanceTask task) {
        tasks.add(task);
    }
}
