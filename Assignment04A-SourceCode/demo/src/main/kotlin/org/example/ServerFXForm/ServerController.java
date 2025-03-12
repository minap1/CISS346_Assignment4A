package org.example.ServerFXForm;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.Server;
import org.example.Utilities;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

public class ServerController {

    @FXML public CheckBox all;
    public TextField type;
    public TextField priority;
    public RadioButton low;
    public RadioButton med;
    public RadioButton high;
    public RadioButton latest;
    public TextArea mOutput;
    @FXML private boolean running = false;
    @FXML private boolean isLinked = false;
    @FXML private  PipedOutputStream serverInput;
    @FXML private  PipedInputStream serverOutput;
    private Server server;
    @FXML public TextArea sOutput;
    @FXML public TextField portnum;
    static final String PRODUCE_MESSAGE_STRING = "PRODUCE_MESSAGE";
    static final String CONSUME_MESSAGE_STRING = "CONSUME_MESSAGE";
    private ArrayList<String> messageList;
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
                    String type = "";
                    String priority = "";
                    Boolean messageGet = false;
                    while ((bytesRead = readme.read(buffer)) != -1) {
                        type = "";
                        priority = "";
                        String writeable = new String(buffer, 0, bytesRead);
                        if(messageGet){
                            priority = writeable;
                            messageGet = false;
                        }else if(writeable.contains(PRODUCE_MESSAGE_STRING)){
                            type = "PRODUCE";
                            messageGet = true;
                        } else if (writeable.contains(CONSUME_MESSAGE_STRING)) {
                            type = "CONSUME";
                            messageGet = true;
                        }
                        System.out.println(writeable);
                        Platform.runLater(() -> sOutput.appendText(writeable));

                        if (!type.isEmpty()) Platform.runLater(() -> this.type.setText(writeable));
                        if (!priority.isEmpty()) Platform.runLater(() -> this.priority.setText(writeable));
                    }
                } catch (Exception e) {

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
    @FXML
    public void onMessageClick(ActionEvent actionEvent) throws Exception {
        String priority = "";
        String type = "";
        if(low.isSelected()){
            priority = "low";
        } else if (med.isSelected()) {
            priority = "medium";
        } else if (high.isSelected()) {
            priority = "high";
        }
        if(all.isSelected()){
            ArrayList<String> li = Utilities.getAllMessagesofPriorty(priority, "EncryptedMessages.txt");
            messageList = li;
            mOutput.setText(messageList.getFirst());
        }else{
            type = Utilities.consumeEncryptedMessageFromFile(priority, "EncryptedMessages.txt");
            mOutput.setText(type);
        }
    }
}
