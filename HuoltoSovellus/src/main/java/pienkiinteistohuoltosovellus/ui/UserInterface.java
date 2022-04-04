package pienkiinteistohuoltosovellus.ui;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    final FileChooser fileChooser = new FileChooser();

    private Scene mainScene;
    private BorderPane root;
    private Pane paneFile, paneView, paneEdit, paneReport;

    @Override
    public void init() throws Exception {
        root = new BorderPane();
        paneView = CreatePaneView();
        paneEdit = CreatePaneEdit();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root.setTop(CreateMenu());
        root.setCenter(paneView);
        mainScene = new Scene(root, 960, 600);

        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);

        primaryStage.show();
    }
    
    private HBox CreateMenu(){
        
        Button file = new Button("File");
        Button edit = new Button("Edit");
        Button view = new Button("view");
        Button report = new Button("Report");
        
        file.setOnAction((final ActionEvent e) -> {
            root.setCenter(paneFile);
        });        
        edit.setOnAction((final ActionEvent e) -> {
            root.setCenter(paneEdit);
        });
        view.setOnAction((final ActionEvent e) -> {
            root.setCenter(paneView);
        });
        report.setOnAction((final ActionEvent e) -> {
            root.setCenter(paneReport);
        });
        
        HBox menu = new HBox(file, edit, view, report);
        
        return menu;
    }

//    private MenuBar CreateMenuBar() {
//        MenuBar menuBar = new MenuBar();
//
//        Menu file = new Menu("File");
//        Menu edit = new Menu("Edit");
//        Menu view = new Menu("view");
//        Menu report = new Menu("Report");
//
//        MenuItem open = new MenuItem("Open");
//        MenuItem create = new MenuItem("Create New");
//
//        open.setOnAction((final ActionEvent e) -> {
//            fileChooser.setTitle("Open maintnance file");
//            File document = fileChooser.showOpenDialog(new Stage());
//            if (document != null) {
//                openFile(document);
//            }
//        });
//        file.getItems().add(open);
//        file.getItems().add(create);
//        
//        MenuItem editTask = new MenuItem("Edit tasks");
//        
//        editTask.setOnAction((final ActionEvent e) -> {
//            root.setCenter(paneEdit);            
//            mainScene.setRoot(root);
//            Stage stage = (Stage)(mainScene.getWindow());
//            stage.setScene(mainScene);
//            stage.show();
//        });
//        
//        edit.getItems().add(editTask);
//
//
//        menuBar.getMenus().add(file);
//        menuBar.getMenus().add(edit);
//        menuBar.getMenus().add(view);
//        menuBar.getMenus().add(report);
//
//        return menuBar;
//    }
    
    private Pane CreatePaneView(){
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));        
        return pane;
    }
    
    private Pane CreatePaneEdit(){
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY))); 
        return pane;
    }
    
    

    private void openFile(File file) {

    }
}
