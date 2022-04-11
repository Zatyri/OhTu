package domain;

import java.time.LocalDate;
import java.util.UUID;

public class MaintenanceTask {

    private UUID id;
    private String name;
    private LocalDate creationDate;
    private LocalDate completedOnDate;
    private LocalDate dueDate;
    private Boolean isCompleted;

    public MaintenanceTask(String name) {
        id = UUID.randomUUID();
        this.name = name;
        isCompleted = false;
        creationDate = LocalDate.now();
    }

    public MaintenanceTask(String name, LocalDate creationDate) {
        id = UUID.randomUUID();
        this.name = name;
        isCompleted = false;
        this.creationDate = creationDate;
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
