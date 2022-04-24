
import domain.MaintenanceFile;
import domain.MaintenanceTask;
import domain.OneTimeTask;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaintenanceFileTest {

    private MaintenanceFile file;

    public MaintenanceFileTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        file = new MaintenanceFile(UUID.randomUUID(), "test file");

    }

    @After
    public void tearDown() {
        file = null;

    }

    @Test
    public void canCreateMaintenancFile() {
        MaintenanceFile maintenanceFile = new MaintenanceFile(UUID.randomUUID(), "test file");
        assertEquals(maintenanceFile.getClass(), MaintenanceFile.class);
    }

    @Test
    public void canAddTasks() {
        HashMap<UUID, MaintenanceTask> tasks = file.getTasks();
        assertEquals(tasks.size(), 0);

        MaintenanceTask task2 = new OneTimeTask("test task2", LocalDate.now(), LocalDate.now());
        file.addTask(new OneTimeTask("test task1", LocalDate.now(), LocalDate.now()));
        file.addTask(task2);
        file.addTask(new OneTimeTask("test task3", LocalDate.now(), LocalDate.now()));
        tasks = file.getTasks();
        assertEquals(tasks.size(), 3);
        assertEquals(tasks.get(task2.getID()), task2);

    }
}
