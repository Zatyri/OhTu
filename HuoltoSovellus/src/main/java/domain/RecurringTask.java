package domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Class for recurring tasks
 */
public class RecurringTask extends MaintenanceTask {

    private int recurringIntervalMonths;

    public RecurringTask(String name, LocalDate creationDate, int recurringIntervalInMonths, LocalDate dueDate) {
        super(name, creationDate, dueDate);
        recurringIntervalMonths = recurringIntervalInMonths;
    }
    
    public RecurringTask(UUID uuid, String name, LocalDate creationDate, LocalDate completedOnDate, LocalDate dueDate, Boolean isCompleted, int recurringIntervalInMonths) {
        super(uuid, name, creationDate, completedOnDate, dueDate, isCompleted);
        recurringIntervalMonths = recurringIntervalInMonths;
    }

    /**
     * @return the recurringIntervalMonths
     */
    public int getRecurringIntervalMonths() {
        return recurringIntervalMonths;
    }

    /**
     * @param recurringIntervalMonths the recurringIntervalMonths to set
     */
    public void setRecurringIntervalMonths(int recurringIntervalMonths) {
        this.recurringIntervalMonths = recurringIntervalMonths;
    }
    
    
}
