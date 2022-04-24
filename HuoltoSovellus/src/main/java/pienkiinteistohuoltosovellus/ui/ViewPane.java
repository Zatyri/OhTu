package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFileService;
import domain.MaintenanceTask;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ViewPane {

    private static BorderPane borderPane;
    private static LocalDate displayDate;

    private ViewPane() {
    }

    private static void initializeViewPane() {
        displayDate = LocalDate.now();
        borderPane = new BorderPane();
    }

    public static Pane getInstance() {
        if (borderPane == null) {
            initializeViewPane();
        }
        createMonthPane();
        return borderPane;
    }

    private static void createMonthPane() {
        BorderPane monthBorderPane = new BorderPane();

        Button prevMonthButton = new Button("<");
        Button nextMonthButton = new Button(">");

        Label currentMonthLabel = new Label(displayDate.getMonth().toString().toLowerCase());
        HBox menuHBox = new HBox(prevMonthButton, currentMonthLabel, nextMonthButton);

        monthBorderPane.setTop(menuHBox);

        FlowPane taskFlowPane = new FlowPane();
        List<MaintenanceTask> tasks = getDisplayDateTasks();

        tasks.forEach(task -> {            
            taskFlowPane.getChildren().add(createTaskDisplayVBox(task));
        });

        monthBorderPane.setCenter(taskFlowPane);

        borderPane.setCenter(monthBorderPane);
    }

    private static VBox createTaskDisplayVBox(MaintenanceTask task) {
        VBox vBox = new VBox();
        Label nameLabel = new Label(String.format("%s (created on %s)", task.getName(), task.getCreationDate()));
        vBox.getChildren().add(nameLabel);
        Label isCompletedLabel;
        if (task.getIsCompleted()) {
            isCompletedLabel = new Label("Task completed on: " + task.getCompletedOnDate().toString());
        } else {
            isCompletedLabel = new Label("Task not yet completed. Due date on: " + task.getDueDate().toString());
        }
        vBox.getChildren().add(isCompletedLabel);       

        return vBox;
    }

    private static List<MaintenanceTask> getDisplayDateTasks() {
        return MaintenanceFileService.getTasks(displayDate.getYear(), displayDate.getMonth());
    }

}
