package pienkiinteistohuoltosovellus.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    private Scene mainScene;
    
        @Override
    public void init() throws Exception {
      // ...
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox loginPane = new VBox(10);
        mainScene = new Scene(loginPane, 300,250);
        
        stage.setScene(mainScene);
        
        stage.show();
    }
}
