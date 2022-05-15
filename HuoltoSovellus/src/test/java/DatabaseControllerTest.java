
import domain.MaintenanceFileService;
import domain.OneTimeTask;
import domain.RecurringTask;
import domain.database.DatabaseController;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseControllerTest {

    private UUID uuid;

    public DatabaseControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DatabaseController.initializeDatabase(":memory:");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        MaintenanceFileService.createMaintenanceFile("database test file", true);
        uuid = MaintenanceFileService.getDefaultMaintenanceFile().getId();
        MaintenanceFileService.saveMaintenanceFile();
        MaintenanceFileService.createTask("task recurring", LocalDate.now(), LocalDate.now(), 12);
        MaintenanceFileService.createTask("task oneTime", LocalDate.now(), LocalDate.now(), 0);
    }

    @After
    public void tearDown() {
        MaintenanceFileService.getTasks().clear();
        MaintenanceFileService.deleteMaintenanceFile(uuid);
    }

    @Test
    public void testAddOneTimeTask() {
        OneTimeTask task = new OneTimeTask(UUID.randomUUID(), "test", LocalDate.now(), LocalDate.now(), LocalDate.now(), false);
        assertEquals(DatabaseController.addTask(uuid, task), true);
    }

    @Test
    public void testAddRecurringTask() {
        RecurringTask task = new RecurringTask(UUID.randomUUID(), "test", LocalDate.now(), LocalDate.now(), LocalDate.now(), false, 12);
        assertEquals(DatabaseController.addTask(uuid, task), true);
    }

    @Test
    public void testUpdateOneTimeTask() {
        UUID id = UUID.randomUUID();
        OneTimeTask task = new OneTimeTask(id, "test", LocalDate.now(), LocalDate.now(), LocalDate.now(), false);
        DatabaseController.addTask(uuid, task);
        task.setName("updated name");

        assertEquals(DatabaseController.updateTask(task), true);
    }

    @Test
    public void testCanGetDefaultMaintenanceFile() {
        assertEquals(DatabaseController.getDefaultMaintenanceFile()[1], "database test file");
    }

    @Test
    public void testCanDeleteTask() {
        UUID id = UUID.randomUUID();
        OneTimeTask task = new OneTimeTask(id, "test remove", LocalDate.now(), LocalDate.now(), LocalDate.now(), false);
        assertEquals(DatabaseController.addTask(uuid, task), true);
        assertEquals(DatabaseController.deleteTask(id), true);
    }

}
