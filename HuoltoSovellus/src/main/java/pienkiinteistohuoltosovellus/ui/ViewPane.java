package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFileService;
import domain.MaintenanceTask;
import domain.RecurringTask;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.event.ActionEvent;
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
        createMonthViewPane();

        return borderPane;
    }

    private static void createMonthViewPane() {

        Button prevMonthButton = new Button("<");
        Button nextMonthButton = new Button(">");

        prevMonthButton.setOnAction((final ActionEvent e) -> {
            displayDate = displayDate.minusMonths(1);
            createMonthViewPane();
        });

        nextMonthButton.setOnAction((final ActionEvent e) -> {
            displayDate = displayDate.plusMonths(1);
            createMonthViewPane();
        });

        Label currentMonthLabel = new Label(displayDate.getMonth().toString().toLowerCase() + " - " + String.valueOf(displayDate.getYear()));
        HBox menuHBox = new HBox(prevMonthButton, currentMonthLabel, nextMonthButton);

        borderPane.setTop(menuHBox);
        createMonthFlowPane();
    }

    private static void createMonthFlowPane() {
        FlowPane taskFlowPane = new FlowPane();
        List<MaintenanceTask> tasks = getDisplayDateTasks();

        tasks.forEach(task -> {
            taskFlowPane.getChildren().add(createTaskDisplayVBox(task));
        });

        borderPane.setCenter(taskFlowPane);
    }

    private static VBox createTaskDisplayVBox(MaintenanceTask task) {
        VBox vBox = new VBox();
        Label nameLabel = new Label(String.format("%s (created on %s)", task.getName(), task.getCreationDate()));
        vBox.getChildren().add(nameLabel);
        Label isCompletedLabel;
        Button toggleCompleteButton;
        if (task.getIsCompleted()) {
            isCompletedLabel = new Label("Task completed on: " + task.getCompletedOnDate().toString());
            toggleCompleteButton = new Button("Mark uncompleted");
            toggleCompleteButton.setOnAction((final ActionEvent e) -> {
                if (task.getClass() == RecurringTask.class) {
                    MaintenanceFileService.updateTask(task, task.getName(), task.getCreationDate(), ((RecurringTask) task).getRecurringIntervalMonths(), false, task.getDueDate());
                } else {
                    MaintenanceFileService.updateTask(task, task.getName(), task.getCreationDate(), 0, false, task.getDueDate());
                }
                createMonthFlowPane();
            });
        } else {
            isCompletedLabel = new Label("Task not yet completed. Due date on: " + task.getDueDate().toString());
            toggleCompleteButton = new Button("Mark completed");
            toggleCompleteButton.setOnAction((final ActionEvent e) -> {
                if (task.getClass() == RecurringTask.class) {
                    MaintenanceFileService.updateTask(task, task.getName(), task.getCreationDate(), ((RecurringTask) task).getRecurringIntervalMonths(), true, task.getDueDate());
                } else {
                    MaintenanceFileService.updateTask(task, task.getName(), task.getCreationDate(), 0, true, task.getDueDate());
                }
                createMonthFlowPane();

            });
        }
        vBox.getChildren().add(isCompletedLabel);
        vBox.getChildren().add(toggleCompleteButton);

        return vBox;
    }

    private static List<MaintenanceTask> getDisplayDateTasks() {
        return MaintenanceFileService.getTasks(displayDate.getYear(), displayDate.getMonth());
    }

}
