package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFile;
import domain.MaintenanceFileService;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
            borderPane.setCenter(null);
        });

        HBox menuVBox = new HBox(createNewMaintenanceFileButton, selectMaintenanceFileButton, editMaintenanceFileButton);

        borderPane.setTop(menuVBox);

        Button saveMaintenanceFileButton = new Button("Save");

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
        });

        return new VBox(label, nameHBox, isDefaultCheckBox, addNewMaintenanceFileButton);
    }

    private static VBox showMaintenanceFileSelectionVBox() {
        ArrayList<MaintenanceFile> maintenanceFiles = MaintenanceFileService.getAllMaintenanceFiles();
        Label label = new Label("Select active maintenance file");
        VBox maintenanceFilesVBox = new VBox(label);

        maintenanceFiles.forEach(file -> {
            Label nameLabel = new Label("Maintenance file name: " + file.getName());
            Button selectMaintenanceFileButton = new Button("Select maintenance file");
            selectMaintenanceFileButton.setOnAction((final ActionEvent e) -> {
                MaintenanceFileService.getmaintenanceFile(file.getId());
            });
            VBox maintenanceFileVBox = new VBox(nameLabel, selectMaintenanceFileButton);
            maintenanceFilesVBox.getChildren().add(maintenanceFileVBox);
        });

        return maintenanceFilesVBox;
    }

}
