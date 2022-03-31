package pienkiinteistohuoltosovellus.ui;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    final FileChooser fileChooser = new FileChooser();
    
    private Scene mainScene;
    
        @Override
    public void init() throws Exception {
      // ...
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        VBox vBoxMenuBar = new VBox(CreateMenuBar());
        mainScene = new Scene(vBoxMenuBar, 960, 600);
        
        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);
        
        primaryStage.show();
    }
    
    private MenuBar CreateMenuBar(){
        MenuBar menuBar = new MenuBar();       
        
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu view = new Menu("view");
        Menu report = new Menu("Report");
        
        MenuItem open = new MenuItem("Open");
        MenuItem create = new MenuItem("Create New");   
        
        open.setOnAction((final ActionEvent e) -> {
            fileChooser.setTitle("Open maintnance file");
            File document = fileChooser.showOpenDialog(new Stage());
            if (document != null) {
                openFile(document);
            }
        });
        
        file.getItems().add(open);
        file.getItems().add(create);
        
        menuBar.getMenus().add(file);
        menuBar.getMenus().add(edit);
        menuBar.getMenus().add(view);
        menuBar.getMenus().add(report);
        
        return menuBar;
    }
        
     private void openFile(File file) {

    }
}
