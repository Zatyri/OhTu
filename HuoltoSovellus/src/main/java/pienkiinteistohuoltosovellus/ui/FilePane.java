package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFile;
import domain.MaintenanceFileService;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FilePane {

    private static BorderPane borderPane;

    private FilePane() {
    }

    private static void initializeFilePane() {
        borderPane = new BorderPane();
        Button createNewMaintenanceFileButton = new Button("Create new maintenance file");
        Button selectMaintenanceFileButton = new Button("Select maintenance file");
        Button editMaintenanceFileButton = new Button("Edit existing maintenence files");

        createNewMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
            borderPane.setCenter(showMaintenanceFileCreationVBox());
        });

        selectMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
            borderPane.setCenter(showMaintenanceFileSelectionVBox());
        });

        editMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
            borderPane.setCenter(showMaintenanceFileEditVBox());
        });

        HBox menuVBox = new HBox(createNewMaintenanceFileButton, selectMaintenanceFileButton, editMaintenanceFileButton);
        menuVBox.setAlignment(Pos.CENTER);

        borderPane.setTop(menuVBox);

        Button saveMaintenanceFileButton = new Button("Save Changes");

        saveMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
            MaintenanceFileService.saveMaintenanceFile();
        });

        borderPane.setLeft(saveMaintenanceFileButton);
        

    }

    public static Pane getInstance() {
        if (borderPane == null) {
            initializeFilePane();
        }
        return borderPane;
    }

    private static VBox showMaintenanceFileCreationVBox() {
        Label label = new Label("Create new maintenance file");

        Label nameLabel = new Label("Maintenance file name:");
        TextField nameTextField = new TextField("");
        HBox nameHBox = new HBox(nameLabel, nameTextField);

        CheckBox isDefaultCheckBox = new CheckBox("Default");

        Button addNewMaintenanceFileButton = new Button("Create maintenance file");
        addNewMaintenanceFileButton.disableProperty().bind(Bindings.isEmpty(nameTextField.textProperty()));

        addNewMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
            String name = nameTextField.getText();
            Boolean isDefault = isDefaultCheckBox.isSelected();

            if (!name.isBlank()) {
                MaintenanceFileService.createMaintenanceFile(name, isDefault);
            }

            nameTextField.setText("");
            isDefaultCheckBox.setSelected(false);
            updateStageTitle(e);
        });

        return new VBox(label, nameHBox, isDefaultCheckBox, addNewMaintenanceFileButton);
    }

    private static VBox showMaintenanceFileSelectionVBox() {
        ArrayList<MaintenanceFile> maintenanceFiles = MaintenanceFileService.getAllMaintenanceFiles();
        Label label = new Label("Select active maintenance file");
        VBox maintenanceFilesVBox = new VBox(label);

        maintenanceFiles.forEach(file -> {
            Label nameLabel = new Label("Maintenance file name: " + file.getName() + (file.getIsDefault() ? " (default)" : ""));
            Button selectMaintenanceFileButton = new Button("Select maintenance file");
            selectMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
                MaintenanceFileService.getmaintenanceFile(file.getId());
                updateStageTitle(e);
            });
            VBox maintenanceFileVBox = new VBox(nameLabel, selectMaintenanceFileButton);
            maintenanceFilesVBox.getChildren().add(maintenanceFileVBox);
        });

        return maintenanceFilesVBox;
    }

    private static VBox showMaintenanceFileEditVBox() {
        ArrayList<MaintenanceFile> maintenanceFiles = MaintenanceFileService.getAllMaintenanceFiles();
        Label label = new Label("Edit maintenance files");
        VBox maintenanceFilesVBox = new VBox(label);

        maintenanceFiles.forEach(file -> {
            Label nameLabel = new Label("Maintenance file name: ");
            TextField nameTextField = new TextField(file.getName());
            HBox nameHBox = new HBox(nameLabel, nameTextField);

            Button confirmChangesButton = new Button("Confirm changes");
            confirmChangesButton.setDisable(true);
            nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!oldValue.equals(newValue)) {
                    confirmChangesButton.setDisable(false);
                } 
            });
            confirmChangesButton.setOnAction((final ActionEvent e) -> {
                MaintenanceFileService.updateMaintenanceFile(file.getId(), nameTextField.getText());
            });

            Button setDefaultMaintenanceFileButton = new Button("Set as default");
            if (file.getIsDefault()) {
                setDefaultMaintenanceFileButton.setDisable(true);
            }
            setDefaultMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
                MaintenanceFileService.setMaintenanceFileAsDefault(file.getId());
                updateMaintenanceFile();
            });

            Button deleteMaintenanceFileButton = new Button("Delete maintenance file");
            deleteMaintenanceFileButton.setOnAction((final ActionEvent e) -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirm deleting maintenance file");
                alert.setContentText("This will delete the maintenance file '" + file.getName() + "' and can not be undone. Are you sure?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    MaintenanceFileService.deleteMaintenanceFile(file.getId());
                    updateMaintenanceFile();
                }
            });

            HBox buttonsHBox = new HBox(confirmChangesButton, setDefaultMaintenanceFileButton, deleteMaintenanceFileButton);
            VBox maintenanceFileVBox = new VBox(nameHBox, buttonsHBox);
            maintenanceFilesVBox.getChildren().add(maintenanceFileVBox);
        });

        return maintenanceFilesVBox;
    }

    private static void updateMaintenanceFile() {
        borderPane.setCenter(showMaintenanceFileEditVBox());
    }

    private static void updateStageTitle(ActionEvent e) {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle("Mainetnance file: " + MaintenanceFileService.getDefaultMaintenanceFile().getName());
    }

}
