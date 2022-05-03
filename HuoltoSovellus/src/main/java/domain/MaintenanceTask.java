package domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Base class maintenance tasks
 * 
 */
public class MaintenanceTask {

    private UUID id;
    private String name;
    private LocalDate creationDate;
    private LocalDate completedOnDate;
    private LocalDate dueDate;
    private Boolean isCompleted;

    /**
     * Create task with only name
     * Other values are generated
     * 
     * @param name name of the task
     */
    public MaintenanceTask(String name) {
        id = UUID.randomUUID();
        this.name = name;
        isCompleted = false;
        creationDate = LocalDate.now();
    }

    /**
     * Missing properties are generated
     * 
     * @param name name of the task
     * @param creationDate date when task was created
     * @param dueDate due date of the task
     */
    public MaintenanceTask(String name, LocalDate creationDate, LocalDate dueDate) {
        id = UUID.randomUUID();
        this.name = name;
        isCompleted = false;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    /**
     * Create task with all properties specified
     * Used when retrieving task from database
     * 
     * @param uuid id of the task
     * @param name name of the task
     * @param creationDate creation of the task
     * @param completedOnDate completion of the task
     * @param dueDate die date of the task
     * @param isCompleted is the task completed
     */
    public MaintenanceTask(UUID uuid, String name, LocalDate creationDate, LocalDate completedOnDate, LocalDate dueDate, Boolean isCompleted) {
        id = uuid;
        this.name = name;
        this.isCompleted = isCompleted;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.completedOnDate = completedOnDate;
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
        if (!name.isEmpty()) {
            this.name = name;
        }
    }

    /**
     * @return the isCompleted
     */
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * @return True if task has due date, false if not.
     */
    public Boolean hasDueDate() {
        return dueDate == null ? false : true;
    }

    /**
     * @return the dueDate
     */
    public LocalDate getDueDate() {
        if (hasDueDate()) {
            return dueDate;
        }
        return null;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the creationDate
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * @return the completedOnDate
     */
    public LocalDate getCompletedOnDate() {
        return completedOnDate;
    }

    /**
     * @param completedOnDate the completedOnDate to set
     */
    public void setCompletedOnDate(LocalDate completedOnDate) {
        if (!isCompleted) {
            this.completedOnDate = completedOnDate;
            isCompleted = true;
        }
    }


    public void setCompletedNow() {
        if (!isCompleted) {
            completedOnDate = LocalDate.now();
            isCompleted = true;
        }
    }

    public void setAsNotCompleted() {
        if (isCompleted) {
            completedOnDate = null;
            isCompleted = false;
        }
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the ID
     */
    public UUID getID() {
        return id;
    }
}
