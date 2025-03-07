package org.example.ServerFXForm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import org.example.Server;
import com.sun.javafx.application.PlatformImpl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class ServerController {

    @FXML public RadioButton all;
    @FXML private boolean running = false;
    @FXML private boolean isLinked = false;
    @FXML private  PipedOutputStream serverInput;
    @FXML private  PipedInputStream serverOutput;
    private Server server;
    @FXML public TextArea sOutput;
    @FXML public TextField portnum;

    public ServerController() throws IOException {
    }

    @FXML
    public void onStartClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        serverInput = new PipedOutputStream();
        serverOutput = new PipedInputStream(serverInput);
        server = new Server(0, serverInput);
        int port = 0;
        if(!portnum.getText().isEmpty()){
            port = Integer.parseInt(portnum.getText());
            server.setPort(port);
        }else{
            server.setPort(50444);
        }
        server.start();
        streamServerOutput(serverOutput);
        running = true;
    }

    /* by far the hardest thing to do, get a stream from the server, pipe to textarea, have text are output live */
    @FXML
    public void streamServerOutput(PipedInputStream readme) throws IOException {
        Thread thread = new Thread(() -> {
            System.out.println("streamServerOutput");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while (running) {
                try {
                    while ((bytesRead = readme.read(buffer)) != -1) {
                        String writeable = new String(buffer, 0, bytesRead);
                        System.out.println(writeable);
                        String finalWriteable = writeable;
                        Platform.runLater(() -> sOutput.appendText(finalWriteable));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    @FXML
    public void onStopClicked(javafx.event.ActionEvent actionEvent) throws InterruptedException, IOException {
        if(running){
            server.stopThread();
            server.join();
        }else{
            sOutput.appendText("No server running.\n");
        }
    }
}
