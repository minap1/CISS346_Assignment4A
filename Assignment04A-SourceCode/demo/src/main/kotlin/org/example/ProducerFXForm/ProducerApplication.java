package org.example.ProducerFXForm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Main;

import java.io.IOException;

public class ProducerApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/ProducerForm.fxml"));
        Scene s = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Producer");
        primaryStage.setScene(s);
        primaryStage.show();
    }
    public static void main(String[] args) {
    }
}
