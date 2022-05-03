package domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Class for one time tasks
 */
public class OneTimeTask extends MaintenanceTask {

    public OneTimeTask(String name, LocalDate creationDate, LocalDate dueDate) {
        super(name, creationDate, dueDate);
    }

    public OneTimeTask(UUID uuid, String name, LocalDate creationDate, LocalDate completedOnDate, LocalDate dueDate, Boolean isCompleted) {
        super(uuid, name, creationDate, completedOnDate, dueDate, isCompleted);
    }
}
