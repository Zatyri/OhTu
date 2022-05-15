package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFile;
import domain.MaintenanceFileService;
import domain.database.DatabaseController;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserInterface extends Application {

    public static void main(String[] args) {
        DatabaseController.initializeDatabase(null);
        launch(args);
    }

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

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            if (!maintenanceFile.getAddedTasksIds().isEmpty()
                    || !maintenanceFile.getModifiedTaskIds().isEmpty()
                    || !maintenanceFile.getDeletedTaskIds().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("unsaved tasks");
                alert.setHeaderText("You hav unsaved tasks");
                alert.setContentText("You have unsaved tasks. Do you want to exit?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.CANCEL) {
                    event.consume();
                }
            }
        });

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
