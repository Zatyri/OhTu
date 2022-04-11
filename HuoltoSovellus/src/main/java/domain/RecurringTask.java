package domain;

import java.time.LocalDate;

public class RecurringTask extends MaintenanceTask {

    private int recurringIntervalMonths;

    public RecurringTask(String name, LocalDate creationDate, int recurringIntervalInMonths) {
        super(name, creationDate);
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
