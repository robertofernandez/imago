package co.itpex.imago.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {

    @FXML
    private MenuItem menuOpen;
    @FXML
    private MenuItem menuSave;

    @FXML
    private void handleOpenImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.bmp")
        );
        Stage stage = (Stage) menuOpen.getParentPopup().getOwnerWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Lógica para abrir la imagen y mostrarla en el editor
            System.out.println("Imagen abierta: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleSaveImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivo PNG", "*.png"),
                new FileChooser.ExtensionFilter("Archivo JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("Archivo BMP", "*.bmp")
        );
        Stage stage = (Stage) menuSave.getParentPopup().getOwnerWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            // Lógica para guardar la imagen
            System.out.println("Imagen guardada en: " + file.getAbsolutePath());
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        
    }
    
    @FXML
    private void handleResize(ActionEvent event) {
        
    }

    @FXML
    private void handleFilter(ActionEvent event) {
        
    }
}
