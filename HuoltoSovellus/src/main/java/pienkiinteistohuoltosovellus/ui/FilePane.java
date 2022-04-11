package pienkiinteistohuoltosovellus.ui;

import javafx.scene.layout.Pane;

public class FilePane {

    private static Pane pane;

    private FilePane() {
    }

    private static void initializeFilePane() {
        pane = new Pane();
    }

    public static Pane getInstance() {
        if (pane == null) {
            initializeFilePane();
        }
        return pane;
    }

}
