package pienkiinteistohuoltosovellus.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ViewPane {

    private static Pane pane;

    private ViewPane() {
    }

    private static void initializeViewPane() {
        pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static Pane getInstance() {
        if (pane == null) {
            initializeViewPane();
        }
        return pane;
    }
}
