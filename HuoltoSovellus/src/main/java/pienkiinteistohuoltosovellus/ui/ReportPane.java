package pienkiinteistohuoltosovellus.ui;

import domain.MaintenanceFileService;
import domain.MaintenanceTask;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ReportPane {

    private static Pane pane;
    private static DirectoryChooser dirChooser = new DirectoryChooser();

    private ReportPane() {
    }

    private static void initializeReportPane() {
        pane = new Pane();
        Button generateReportButton = new Button("Generate report");
        generateReportButton.setOnAction((final ActionEvent e) -> {
            generateReport();
        });
        pane.getChildren().add(generateReportButton);

    }

    public static Pane getInstance() {
        if (pane == null) {
            initializeReportPane();
        }
        return pane;
    }

    private static void generateReport() {

        File selectedDirectory = dirChooser.showDialog((Stage) pane.getScene().getWindow());

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            try (PDPageContentStream cont = new PDPageContentStream(document, page)) {
                cont.beginText();
                cont.setFont(font, 12);
                cont.setLeading(14.5f);
                cont.newLineAtOffset(25, 700);
                cont.showText("Report for maintenance file: " + MaintenanceFileService.getDefaultMaintenanceFile().getName());
                cont.newLine();
                cont.newLine();
                cont.showText("Completed tasks");
                cont.newLine();
                font = PDType1Font.HELVETICA;
                cont.setFont(font, 10);

                addTasksToPDF(cont);

                cont.endText();
            }

            String path = selectedDirectory.getAbsolutePath() + "/" + MaintenanceFileService.getDefaultMaintenanceFile().getName() + ".pdf";
            int number = 1;
            while (new File(path).isFile()) {
                path = selectedDirectory.getAbsolutePath() + "/" + MaintenanceFileService.getDefaultMaintenanceFile().getName() + " (" + number + ")" + ".pdf";
            }

            document.save(path);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private static void addTasksToPDF(PDPageContentStream cont) {
        List<MaintenanceTask> tasks = MaintenanceFileService.getCompletedTasks();

        tasks.forEach(task -> {
            try {
                cont.showText("Task: " + task.getName());
                cont.newLine();
                cont.showText("Completed on: " + task.getCompletedOnDate().toString());
                cont.newLine();
                cont.newLine();
            } catch (IOException ex) {
                //empty on purpose
            }
        });
    }
}
