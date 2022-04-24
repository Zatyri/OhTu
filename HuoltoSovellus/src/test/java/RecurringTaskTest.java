
import domain.RecurringTask;
import java.time.LocalDate;
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
}
