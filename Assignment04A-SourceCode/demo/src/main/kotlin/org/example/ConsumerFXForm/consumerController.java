package org.example.ConsumerFXForm;
import java.net.InetAddress;
import java.security.PrivateKey;

import org.example.MessengerClient;
import org.example.RsaEncryptor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.example.RsaEncryptor;

public class consumerController {
    @FXML public RadioButton low;
    @FXML public RadioButton medium;
    @FXML public RadioButton high;
    @FXML public TextArea reply;
    @FXML public TextField address;
    @FXML public TextField portnum;

    private ToggleGroup priorityGroup = new ToggleGroup();

    private MessengerClient consumer;

    String privateKeyFilename = "private.key";
    PrivateKey privateKey;

    @FXML
    public void initialize() {
        low.setToggleGroup(priorityGroup);
        medium.setToggleGroup(priorityGroup);
        high.setToggleGroup(priorityGroup);
        consumer = new MessengerClient();
        // Read private key from file
        try {
            privateKey = RsaEncryptor.readPrivateKeyFromFile(privateKeyFilename);
        } catch (Exception e) {
            System.out.println("Error reading private key from file\n".getBytes());
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void retrieve(ActionEvent actionEvent) throws Exception {
        String priority;
        if (low.isSelected()) {
            priority = "Low";
        }else if (medium.isSelected()) {
            priority = "Medium";
        }else if (high.isSelected()) {
            priority = "High";
        }else{
            priority = "Low";
            //throw an error---pop-up
        }
        InetAddress add = null;
        try {
            if(!address.getText().isEmpty()){
                add = InetAddress.getByName(this.address.getText());
            }else{
                add = InetAddress.getLoopbackAddress();
            }
        }
        catch (Exception e) {
            reply.setText(e.getMessage());
            return;
        }
        int port;
        if(!portnum.getText().isEmpty()){
            port = Integer.parseInt(portnum.getText());
        }else{
            port = 50444;
        }
        String decryptedString = "";
        decryptedString = RsaEncryptor.decryptMessageAsString(consumer.getMessage("1", priority, add, port), privateKey);
        reply.setText(decryptedString);
    }
}
