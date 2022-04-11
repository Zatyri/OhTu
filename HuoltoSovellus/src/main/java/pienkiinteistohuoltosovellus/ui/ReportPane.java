package pienkiinteistohuoltosovellus.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ReportPane {

    private static Pane pane;

    private ReportPane() {
    }

    private static void initializeReportPane() {
        pane = new Pane();
    }

    public static Pane getInstance() {
        if (pane == null) {
            initializeReportPane();
        }
        return pane;
    }
}
