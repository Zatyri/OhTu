package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFile;
import domain.MaintenanceFileService;
import domain.database.DatabaseController;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInterface extends Application {

    public static void main(String[] args) {
        DatabaseController.initializeDatabase();
        launch(args);
    }

    final FileChooser fileChooser = new FileChooser();

    private Scene mainScene;
    private BorderPane root;
    private MaintenanceFile maintenanceFile;

    @Override
    public void init() throws Exception {
        root = new BorderPane();
        maintenanceFile = MaintenanceFileService.getDefaultMaintenanceFile();
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setTop(createMenu());
        root.setCenter(ViewPane.getInstance());
        mainScene = new Scene(root, 960, 600);        
        
        primaryStage.setTitle("Mainetnance file: " + maintenanceFile.getName());
        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);

        primaryStage.show();
    }

    private HBox createMenu() {

        Button fileButton = new Button("File");
        Button editButton = new Button("Edit");
        Button viewButton = new Button("view");
        Button reportButton = new Button("Report");
                
        fileButton.setOnAction((final ActionEvent e) -> {
            root.setCenter(FilePane.getInstance());
        });
        editButton.setOnAction((final ActionEvent e) -> {
            root.setCenter(EditPane.getInstance());
        });
        viewButton.setOnAction((final ActionEvent e) -> {
            root.setCenter(ViewPane.getInstance());
        });
        reportButton.setOnAction((final ActionEvent e) -> {
            root.setCenter(ReportPane.getInstance());
        });

        HBox menu = new HBox(fileButton, editButton, viewButton, reportButton);

        return menu;
    }

}
