package co.itpex.imago;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga la vista principal desde el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main_view.fxml"));
        
        // Crea la escena y aplica el estilo CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        
        // Configuración de la ventana principal
        primaryStage.setTitle("Image Processor App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}