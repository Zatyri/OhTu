package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MaintenanceFile {

    private UUID id;
    private String name;
    private HashMap<UUID, MaintenanceTask> tasks;
    private ArrayList<UUID> deletedTaskIds;
    private ArrayList<UUID> modifiedTaskIds;
    private ArrayList<UUID> addedTasksIds;

    public MaintenanceFile(UUID uuid, String name) {
        id = uuid;
        this.name = name;
        tasks = new HashMap<>();
        deletedTaskIds = new ArrayList<>();
        modifiedTaskIds = new ArrayList<>();
        addedTasksIds = new ArrayList<>();
    }

    /**
     * @return the tasks
     */
    public HashMap<UUID, MaintenanceTask> getTasks() {
        return tasks;
    }

    /**
     * @param task the tasks to add to list
     */
    public void addTask(MaintenanceTask task) {
        tasks.put(task.getID(), task);
        addedTasksIds.add(task.getID());
    }

    public void deleteTask(MaintenanceTask task) {
        deletedTaskIds.add(task.getID());
        tasks.remove(task.getID());
    }

    public void updateTask(MaintenanceTask task) {
        modifiedTaskIds.add(task.getID());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the deletedTaskIds
     */
    public ArrayList<UUID> getDeletedTaskIds() {
        return deletedTaskIds;
    }

    /**
     * @return the modifiedTaskIds
     */
    public ArrayList<UUID> getModifiedTaskIds() {
        return modifiedTaskIds;
    }

    /**
     * @return the addedTasksIds
     */
    public ArrayList<UUID> getAddedTasksIds() {
        return addedTasksIds;
    }
}
