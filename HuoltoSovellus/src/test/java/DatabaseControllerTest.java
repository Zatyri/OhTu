
import domain.MaintenanceFileService;
import domain.MaintenanceTask;
import domain.OneTimeTask;
import domain.database.DatabaseController;
import java.sql.ResultSet;
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
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        DatabaseController.initializeDatabase();
        MaintenanceFileService.createMaintenanceFile("test file", true);
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
    public void testAddTask() {
       OneTimeTask task = new OneTimeTask("test", LocalDate.now(), LocalDate.now());
       
       assertEquals(DatabaseController.addTask(uuid, task), true);
    }
    
    @Test
    public void canGetDefaultMaintenanceFile(){
        assertEquals(DatabaseController.getDefaultMaintenanceFile()[1], "test file");
    }

}
