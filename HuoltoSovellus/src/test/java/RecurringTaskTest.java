
import domain.OneTimeTask;
import domain.RecurringTask;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecurringTaskTest {

    RecurringTask task;

    public RecurringTaskTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        task = new RecurringTask("test task", LocalDate.now(), 1, LocalDate.now());
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void taskHasCorrectRecurringInterval() {
        assertEquals(task.getRecurringIntervalMonths(), 1);
        task.setRecurringIntervalMonths(12);
        assertEquals(task.getRecurringIntervalMonths(), 12);
    }
    
        @Test
    public void taskCreationWithAllDataTest() {
        String name = "test task";
        UUID uuid = UUID.randomUUID();
        RecurringTask task1 = new RecurringTask(uuid, name, LocalDate.now(), LocalDate.now(), LocalDate.now(), false, 1);
        assertEquals(task1.getClass(), RecurringTask.class);
        assertEquals(task1.getName(), name);
        assertEquals(task1.getCreationDate(), LocalDate.now());
        assertEquals(task1.getIsCompleted(), false);
        assertEquals(task1.getID(), uuid);
        assertEquals(task1.getCompletedOnDate(), LocalDate.now());
        assertEquals(task1.getDueDate(), LocalDate.now());
        assertEquals(task1.getRecurringIntervalMonths(), 1);
    }
}
