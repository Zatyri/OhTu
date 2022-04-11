package domain;

import java.time.LocalDate;

public class OneTimeTask extends MaintenanceTask {

    public OneTimeTask(String name, LocalDate creationDate) {
        super(name, creationDate);
    }
}
