package org.example.ServerFXForm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import org.example.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class ServerController {

    private boolean running = false;
    private boolean isLinked = false;
    private final PipedOutputStream serverInput = new PipedOutputStream();
    private final PipedInputStream serverOutput = new PipedInputStream();
    private Server server;
    @FXML public TextArea sOutput;
    @FXML public TextField portnum;

    public ServerController() throws IOException {
    }

    @FXML
    public void onStartClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        if(!isLinked){
            isLinked = true;
            serverOutput.connect(serverInput);
            //serverInput.connect(serverOutput);
        }
        server = new Server(0, serverInput);
        int port = 0;
        if(!portnum.getText().isEmpty()){
            port = Integer.parseInt(portnum.getText());
            server.setPort(port);
        }else{
            server.setPort(50444);
        }
        server.start();

        running = true;
    }

    /* by far the hardest thing to do, get a stream from the server, pipe to textarea, have text are output live */
    @FXML
    public void streamServerOutput() throws IOException {
        byte[] array;
        String writeable;
        while(running){
          if(serverOutput.available() > 0){
                array = serverOutput.readAllBytes();
                writeable = new String(array);
                sOutput.appendText(writeable);
            }
        }
    }

    @FXML
    public void onStopClicked(javafx.event.ActionEvent actionEvent) throws InterruptedException, IOException {
        if(running){
            server.stopThread();
            server.join();
        }else{
            streamServerOutput();
        }
    }
}
