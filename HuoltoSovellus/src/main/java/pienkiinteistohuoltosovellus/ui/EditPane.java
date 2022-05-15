package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFileService;
import domain.MaintenanceTask;
import domain.RecurringTask;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditPane {

    private static BorderPane borderPane;

    private EditPane() {
    }

    private static void initializeEditPane() {
        borderPane = new BorderPane();

        Button createNewTaskButton = new Button("Create new task");
        Button editTaskButton = new Button("Edit existing task");

        createNewTaskButton.setOnAction((final ActionEvent e) -> {
            borderPane.setCenter(showTaskCreationVBox());
        });

        editTaskButton.setOnAction((final ActionEvent e) -> {
            borderPane.setCenter(showTaskEditVBox());
        });

        HBox menuVBox = new HBox(createNewTaskButton, editTaskButton);
        menuVBox.setAlignment(Pos.CENTER);

        borderPane.setTop(menuVBox);
    }

    private static VBox showTaskCreationVBox() {

        if (MaintenanceFileService.getDefaultMaintenanceFile().getIsNotSaved()) {
            Label label = new Label("Create a maintenance file in File section first");
            return new VBox(label);
        }
        Label label = new Label("Create new task");

        Label nameLabel = new Label("Task name:");
        TextField nameTextField = new TextField("");
        HBox nameHBox = new HBox(nameLabel, nameTextField);

        Label creationDateLabel = new Label("Date when task was completed:");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });

        HBox creationDateHBox = new HBox(creationDateLabel, datePicker);

        Label dueDateLabel = new Label("Choose due date:");
        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setValue(LocalDate.now());
        dueDatePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });
        HBox dueDateHBox = new HBox(dueDateLabel, dueDatePicker);

        Label recurringLabel = new Label("Set recurring interval(months) 0 = not recurring");
        TextField recurringTextField = new TextField("0");
        recurringTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                recurringTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        HBox recurringHBox = new HBox(recurringLabel, recurringTextField);

        Button addNewTaskButton = new Button("Create task");
        addNewTaskButton.disableProperty().bind(Bindings.isEmpty(nameTextField.textProperty()));

        addNewTaskButton.setOnAction((final ActionEvent e) -> {
            if (MaintenanceFileService.getDefaultMaintenanceFile().getIsNotSaved()) {
                return;
            }
            String name = nameTextField.getText();
            LocalDate creationDate = datePicker.getValue();
            LocalDate dueDate = dueDatePicker.getValue();

            if (!name.isBlank()) {

                MaintenanceFileService.createTask(name, creationDate, dueDate, Integer.parseInt(recurringTextField.getText()));
            }

            datePicker.setValue(LocalDate.now());
            nameTextField.setText("");
            recurringTextField.setText("0");
        });

        return new VBox(label, nameHBox, creationDateHBox, dueDateHBox, recurringHBox, addNewTaskButton);
    }

    private static VBox showTaskEditVBox() {
        if (MaintenanceFileService.getDefaultMaintenanceFile().getIsNotSaved()) {
            Label label = new Label("Create a maintenance file in File section first");
            return new VBox(label);
        }
        Label label = new Label("Edit tasks");

        VBox tasksVBox = new VBox(label);

        ArrayList<MaintenanceTask> tasks = MaintenanceFileService.getTasks();

        tasks.forEach((task) -> {
            Label nameLabel = new Label("Task name:");
            TextField nameTextField = new TextField(task.getName());
            HBox taskHBox = new HBox(nameLabel, nameTextField);

            Label creationDateLabel = new Label("Date when task was completed:");
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(task.getCreationDate());
            datePicker.setDayCellFactory(param -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.compareTo(LocalDate.now()) > 0);
                }
            });

            HBox creationDateHBox = new HBox(creationDateLabel, datePicker);

            Label dueDateLabel = new Label("Change due date:");
            DatePicker dueDatePicker = new DatePicker();
            dueDatePicker.setValue(task.getDueDate());
            dueDatePicker.setDayCellFactory(param -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.compareTo(LocalDate.now()) < 0);
                }
            });
            HBox dueDateHBox = new HBox(dueDateLabel, dueDatePicker);

            tasksVBox.getChildren().add(new VBox(taskHBox, creationDateHBox, dueDateHBox));

            TextField recurringTextField = new TextField("");
            if (task.getClass() == RecurringTask.class) {
                Label recurringLabel = new Label("Set recurring interval(months) 0 = not recurring");
                recurringTextField.setText(Integer.toString(((RecurringTask) task).getRecurringIntervalMonths()));
                recurringTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        recurringTextField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
                HBox recurringHBox = new HBox(recurringLabel, recurringTextField);
                tasksVBox.getChildren().add(recurringHBox);
            }

            Button confirmChangesButton = new Button("Confirm changes");
            confirmChangesButton.setOnAction((final ActionEvent e) -> {
                int recurringInterval = 0;
                if (task.getClass() == RecurringTask.class) {
                    recurringInterval = Integer.parseInt(recurringTextField.getText());
                }

                MaintenanceFileService.updateTask(task, nameTextField.getText(), datePicker.getValue(), recurringInterval, task.getIsCompleted(), dueDatePicker.getValue());
            });

            Button resetChangesButton = new Button("Reset changes");
            resetChangesButton.setOnAction((final ActionEvent e) -> {
                nameTextField.setText(task.getName());
                datePicker.setValue(task.getCreationDate());
                dueDatePicker.setValue(task.getDueDate());
                if (task.getClass() == RecurringTask.class) {
                    recurringTextField.setText((Integer.toString(((RecurringTask) task).getRecurringIntervalMonths())));
                }
            });

            Button deleteTaskButton = new Button("Delete task");
            deleteTaskButton.setOnAction((final ActionEvent e) -> {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirm removing task");
                alert.setContentText("This will delete the task and can not be undone. Are you sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    MaintenanceFileService.deleteTask(task);
                    updateTaskList();
                }
            });

            HBox buttonsHBox = new HBox(confirmChangesButton, resetChangesButton, deleteTaskButton);
            tasksVBox.getChildren().add(buttonsHBox);
        });

        return new VBox(label, tasksVBox);
    }

    private static void updateTaskList() {
        borderPane.setCenter(showTaskEditVBox());
    }

    public static BorderPane getInstance() {
        if (borderPane == null) {
            initializeEditPane();
        }
        return borderPane;
    }
}
