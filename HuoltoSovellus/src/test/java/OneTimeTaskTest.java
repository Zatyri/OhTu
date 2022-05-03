
import domain.OneTimeTask;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class OneTimeTaskTest {

    OneTimeTask task;

    public OneTimeTaskTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        String name = "test task";
        task = new OneTimeTask(name, LocalDate.now(), LocalDate.now());
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void taskCreationTest() {
        String name = "test task";
        OneTimeTask task1 = new OneTimeTask(name, LocalDate.now(), LocalDate.now());
        assertEquals(task1.getClass(), OneTimeTask.class);
        assertEquals(task1.getName(), name);
        assertEquals(task1.getCreationDate(), LocalDate.now());
        assertEquals(task1.getIsCompleted(), false);
    }

    @Test
    public void taskCreationWithAllDataTest() {
        String name = "test task";
        UUID uuid = UUID.randomUUID();
        OneTimeTask task1 = new OneTimeTask(uuid, name, LocalDate.now(), LocalDate.now(), LocalDate.now(), false);
        assertEquals(task1.getClass(), OneTimeTask.class);
        assertEquals(task1.getName(), name);
        assertEquals(task1.getCreationDate(), LocalDate.now());
        assertEquals(task1.getIsCompleted(), false);
        assertEquals(task1.getID(), uuid);
        assertEquals(task1.getCompletedOnDate(), LocalDate.now());
        assertEquals(task1.getDueDate(), LocalDate.now());
    }
}
