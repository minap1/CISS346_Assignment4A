package org.example.ConsumerFXForm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Main;

import java.io.IOException;

public class consumerApplication extends Application {
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/ConsumerForm.fxml"));
        Scene s = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Consumer");
        primaryStage.setScene(s);
        primaryStage.show();
    }
    public static void main(String[] args) {launch(args);}
}
