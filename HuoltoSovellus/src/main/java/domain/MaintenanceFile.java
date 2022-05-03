package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * MaintenanceFile is a file containing all the MaintenanceTask-tasks
 */
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
     * Get all tasks
     *
     * @return HashMap of all tasks
     */
    public HashMap<UUID, MaintenanceTask> getTasks() {
        return tasks;
    }

    /**
     * Add Task to MaintenanceFile
     * Please note that this change is permanent only after separately saving the MaintenanceFile
     *
     * @param task MaintenanceTask to add to MaintenanceFile
     */
    public void addTask(MaintenanceTask task) {
        tasks.put(task.getID(), task);
        addedTasksIds.add(task.getID());
    }

    /**
     * Delete Task from MaintenanceFile
     * Please note that this change is permanent only after separately saving the MaintenanceFile
     *
     * @param task MaintenanceTask to delete from MaintenanceFile
     */
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
     * This method returns all deleted task ID's in this session
     * Used when saving the MaintenanceFile to database and removing deleted tasks from the database
     * 
     * @return the deletedTaskIds
     */
    public ArrayList<UUID> getDeletedTaskIds() {
        return deletedTaskIds;
    }

    /**
     * This method returns all modified task ID's in this session
     * Used when saving the MaintenanceFile to database and updating modified tasks in the database
     * 
     * @return the modifiedTaskIds
     */
    public ArrayList<UUID> getModifiedTaskIds() {
        return modifiedTaskIds;
    }

    /**
     * This method returns all added task ID's in this session
     * Used when saving the MaintenanceFile to database and adding new tasks to the database
     * @return the addedTasksIds
     */
    public ArrayList<UUID> getAddedTasksIds() {
        return addedTasksIds;
    }
}
