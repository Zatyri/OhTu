
import domain.MaintenanceFile;
import domain.MaintenanceFileService;
import domain.OneTimeTask;
import domain.RecurringTask;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaintenanceFileServiceTest {

    public MaintenanceFileServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        MaintenanceFileService.createMaintenanceFile("test file", false);

    }

    @After
    public void tearDown() {
        MaintenanceFileService.getTasks().clear();
    }

    @Test
    public void maintenanceFileServiceInitialized() {
        assertEquals(MaintenanceFileService.getDefaultMaintenanceFile().getClass(), MaintenanceFile.class);
    }

    @Test
    public void canAddNewOneTimeTaskToMaintenanceFile() {
        MaintenanceFileService.createTask("test task", LocalDate.now(), LocalDate.now(), 0);
        assertEquals(MaintenanceFileService.getTasks().get(0).getClass(), OneTimeTask.class);
        assertEquals(MaintenanceFileService.getTasks().get(0).getName(), "test task");

    }

    @Test
    public void canAddNewRecurringTaskToMaintenanceFile() {
        MaintenanceFileService.createTask("test task", LocalDate.now(), LocalDate.now(), 12);
        assertEquals(MaintenanceFileService.getTasks().get(0).getClass(), RecurringTask.class);
        assertEquals(MaintenanceFileService.getTasks().get(0).getName(), "test task");
        assertEquals(((RecurringTask) MaintenanceFileService.getTasks().get(0)).getRecurringIntervalMonths(), 12);
    }

    @Test
    public void canDeleteTask() {
        MaintenanceFileService.createTask("test task", LocalDate.now(), LocalDate.now(), 0);
        assertEquals(MaintenanceFileService.getTasks().size(), 1);
        MaintenanceFileService.deleteTask(MaintenanceFileService.getTasks().get(0));
        assertEquals(MaintenanceFileService.getTasks().size(), 0);
    }
}
