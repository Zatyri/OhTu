package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFile;
import domain.MaintenanceFileService;
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
        launch(args);
    }

    final FileChooser fileChooser = new FileChooser();

    private Scene mainScene;
    private BorderPane root;
    private MaintenanceFile maintenanceFile;

    @Override
    public void init() throws Exception {
        root = new BorderPane();
        maintenanceFile = MaintenanceFileService.getMaintenanceFile();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setTop(createMenu());
        root.setCenter(ViewPane.getInstance());
        mainScene = new Scene(root, 960, 600);

        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);

        primaryStage.show();
    }

    private HBox createMenu() {

        Button fileButton = new Button("File");
        Button editButton = new Button("Edit");
        Button viewButton = new Button("view");
        Button reportButton = new Button("Report");
        
        editButton.setStyle("-fx-skin: com.sun.javafx.scene.control.skin.ButtonSkin;" +
    "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"+
    "-fx-background-insets: 0 0 -1 0, 0, 1, 2;"+
    "-fx-background-radius: 5, 5, 4, 3;"+
    "-fx-padding: 0.166667em 0.833333em 0.25em 0.833333em; /* 2 10 3 10 */"+
    "-fx-text-fill: -fx-text-base-color;"+
    "-fx-alignment: CENTER;"+
    "-fx-content-display: LEFT;)");
        
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

    private void openFile(File file) {

    }
}
