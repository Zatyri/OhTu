
import domain.MaintenanceFile;
import domain.MaintenanceFileService;
import domain.MaintenanceTask;
import domain.OneTimeTask;
import domain.RecurringTask;
import domain.database.DatabaseController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sqlite.SQLiteConfig;

public class MaintenanceFileServiceTest {

    private UUID uuid;

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
        DatabaseController.initializeDatabase();
        MaintenanceFileService.createMaintenanceFile("test file", false);
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
    public void maintenanceFileServiceInitialized() {
        assertEquals(MaintenanceFileService.getDefaultMaintenanceFile().getClass(), MaintenanceFile.class);
    }

    @Test
    public void canAddNewOneTimeTaskToMaintenanceFile() {
        int startTaskCount = MaintenanceFileService.getTasks().size();
        MaintenanceFileService.createTask("test task", LocalDate.now(), LocalDate.now(), 0);
        assertEquals(MaintenanceFileService.getTasks().size(), startTaskCount + 1);
    }

    @Test
    public void canAddNewRecurringTaskToMaintenanceFile() {
        int startTaskCount = MaintenanceFileService.getTasks().size();
        MaintenanceFileService.createTask("test task", LocalDate.now(), LocalDate.now(), 12);
        assertEquals(MaintenanceFileService.getTasks().size(), startTaskCount + 1);
    }

    @Test
    public void canDeleteTask() {
        assertEquals(MaintenanceFileService.getTasks().size(), 2);
        MaintenanceFileService.deleteTask(MaintenanceFileService.getTasks().get(0));
        assertEquals(MaintenanceFileService.getTasks().size(), 1);
    }

    @Test
    public void canGetAllMaintenanceFiles() {
        ArrayList<MaintenanceFile> files = MaintenanceFileService.getAllMaintenanceFiles();
        assertEquals(files.get(0).getName(), "test file");
    }

    @Test
    public void canGetTasksFromDatabase() {
        MaintenanceFileService.getTasksFromDatabase();
        assertEquals(MaintenanceFileService.getTasks().size(), 2);
    }

    @Test
    public void canUpdateTaskToDatabase() {
        MaintenanceTask task = MaintenanceFileService.getTasks().get(0);
        MaintenanceFileService.updateTask(task, "upated name", LocalDate.now(), 0, true, LocalDate.now());
        assertEquals(MaintenanceFileService.getModifiedTasks().size(), 1);
    }
    
    @Test
    public void canInitializeNewMaintenanceFile(){
        MaintenanceFileService.deleteMaintenanceFile(uuid);
        MaintenanceFileService.createMaintenanceFile("new test file", true);
        uuid = MaintenanceFileService.getDefaultMaintenanceFile().getId();
        assertEquals(MaintenanceFileService.getmaintenanceFile(uuid).getName(), "new test file");
        MaintenanceFileService.deleteMaintenanceFile(uuid);
    }
}
