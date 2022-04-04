
import domain.OneTimeTask;
import java.time.LocalDate;
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
        task = new OneTimeTask(name);
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void taskCreationTest() {
        String name = "test task";
        OneTimeTask task1 = new OneTimeTask(name);
        assertEquals(task1.getClass(), OneTimeTask.class);
        assertEquals(task1.getName(), name);
        assertEquals(task1.getCreationDate(), LocalDate.now());
        assertEquals(task1.getIsCompleted(), false);
    }
}
