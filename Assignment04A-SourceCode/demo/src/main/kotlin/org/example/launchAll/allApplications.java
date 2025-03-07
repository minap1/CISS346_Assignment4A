package org.example.launchAll;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Main;

import java.io.IOException;

public class allApplications extends Application {
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/ServerForm.fxml"));
        Scene s = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Server");
        primaryStage.setScene(s);
        primaryStage.show();
        FXMLLoader fxmlLoader2 = new FXMLLoader(Main.class.getResource("/FXML/ProducerForm.fxml"));
        Scene s2 = new Scene(fxmlLoader2.load());
        Stage stage = new Stage();
        stage.setTitle("Producer");
        stage.setScene(s2);
        stage.show();
        FXMLLoader fxmlLoader3 = new FXMLLoader(Main.class.getResource("/FXML/ConsumerForm.fxml"));
        Scene s3 = new Scene(fxmlLoader3.load());
        Stage stage2 = new Stage();
        stage2.setTitle("Consumer");
        stage2.setScene(s3);
        stage2.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
