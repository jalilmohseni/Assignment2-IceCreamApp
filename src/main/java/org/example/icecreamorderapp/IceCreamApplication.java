package org.example.icecreamorderapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * Main application class for the Ice Cream Order System.
 * This class initializes and displays the main application window.
 * Author: Jalil
 * Date: February 2025
 */
public class IceCreamApplication extends Application {
    /**
     * Starts the JavaFX application.
     * Loads the FXML layout and sets up the primary stage.
     *
     * @param stage The primary stage for this application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/icecreamorderapp/IceCreamMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Setting up the main stage (window)
        stage.setTitle("Ice Cream Order System");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(550);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
