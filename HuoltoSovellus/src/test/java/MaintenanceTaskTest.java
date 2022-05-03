
import domain.MaintenanceTask;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaintenanceTaskTest {

    MaintenanceTask task;

    public MaintenanceTaskTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        task = new MaintenanceTask("test task");
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void taskCreationTest() {
        String name = "test task";
        MaintenanceTask task1 = new MaintenanceTask(name);
        assertEquals(task1.getClass(), MaintenanceTask.class);
        assertEquals(task1.getName(), name);
        assertEquals(task1.getCreationDate(), LocalDate.now());
        assertEquals(task1.getIsCompleted(), false);
        task1.setName("new name");
        task1.setCreationDate(LocalDate.parse("2022-10-10"));
        assertEquals(task1.getName(), "new name");
        assertEquals(task1.getCreationDate(), LocalDate.parse("2022-10-10"));
        task1.setName("");
        assertEquals(task1.getName(), "new name");
    }

    @Test
    public void taskHasCorrectCompletionDateNow() {
        task.setCompletedNow();
        assertEquals(task.getIsCompleted(), true);
        assertEquals(task.getCompletedOnDate(), LocalDate.now());
    }

    @Test
    public void taskHasCorrectCompletionDate() {
        LocalDate date = LocalDate.of(2022, 5, 30);
        task.setCompletedOnDate(date);
        assertEquals(task.getIsCompleted(), true);
        assertEquals(task.getCompletedOnDate(), date);
        
        LocalDate date2 = LocalDate.of(2022, 5, 28);
        task.setCompletedOnDate(date2);
        assertEquals(task.getCompletedOnDate(), date);
    }

    @Test
    public void taskIsNotCompletedAfterMarkingItNotCompleted() {
        task.setCompletedNow();
        assertEquals(task.getIsCompleted(), true);
        assertEquals(task.getCompletedOnDate(), LocalDate.now());
        // test again to make sure condition works
        task.setCompletedNow();
        assertEquals(task.getIsCompleted(), true);
        assertEquals(task.getCompletedOnDate(), LocalDate.now());

        task.setAsNotCompleted();
        assertEquals(task.getIsCompleted(), false);
        assertEquals(task.getCompletedOnDate(), null);
        // test again to make sure condition works
        task.setAsNotCompleted();
        assertEquals(task.getIsCompleted(), false);
        assertEquals(task.getCompletedOnDate(), null);
    }

    @Test
    public void taskDoesNotHaveDueDateByDefault() {
        assertEquals(task.getDueDate(), null);
    }

    @Test
    public void taskHasCorrectDueDate() {
        LocalDate date = LocalDate.of(2022, 5, 30);
        task.setDueDate(date);
        assertEquals(task.getDueDate(), date);

    }
}
