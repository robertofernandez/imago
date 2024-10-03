package co.itpex.imago;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Checking resource: " + getClass().getResource("/css/styles.css"));
        System.out.println("Checking resource: " + getClass().getResource("/fmx/main-view.fxml"));
        URL rootLocation = getClass().getResource("/");
        if (rootLocation != null) {
            System.out.println("Root location: " + rootLocation.toString());
        } else {
            System.out.println("Root location is not found.");
        }
        System.out.println("Classpath locations:");
        System.out.println(System.getProperty("java.class.path"));

        Path path = Paths.get("target/classes/fmx/images/toolbar-icons/open.png");
        if (Files.exists(path)) {
            Image image = new Image(path.toUri().toString());
            System.out.println("image height: " + image.getHeight());
        } else {
            System.out.println("File not found: " + path);
        }
        
        URL fxmlLocation = getClass().getResource("/fmx/main-view.fxml");
        if (fxmlLocation == null) {
            System.out.println("FXML file not found!");
            return;
        } else {
            System.out.println("FXML file found at: " + fxmlLocation.toString());
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/main_view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        
        primaryStage.setTitle("IMago");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}