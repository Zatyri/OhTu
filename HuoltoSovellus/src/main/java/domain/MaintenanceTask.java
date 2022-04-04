package domain;

import java.time.LocalDate;

public class MaintenanceTask {

    private String name;
    private LocalDate creationDate;
    private LocalDate completedOnDate;
    private LocalDate dueDate;
    private Boolean isCompleted;

    public MaintenanceTask(String name) {
        this.name = name;
        isCompleted = false;
        creationDate = LocalDate.now();
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

    /**
     * @param Sets completed task from this date
     */
    public void setCompletedNow() {
        if (!isCompleted) {
            completedOnDate = LocalDate.now();
            isCompleted = true;
        }
    }

    /**
     * @param Changes task to not completed
     */
    public void setAsNotCompleted() {
        if (isCompleted) {
            completedOnDate = null;
            isCompleted = false;
        }
    }
}
