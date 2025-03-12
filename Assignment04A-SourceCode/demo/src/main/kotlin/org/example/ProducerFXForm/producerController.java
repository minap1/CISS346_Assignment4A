package org.example.ProducerFXForm;

import java.net.InetAddress;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.example.MessengerClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.RsaEncryptor;

public class producerController {

    @FXML public Button browse;
    @FXML public TextField filename;
    @FXML public Button clear_file;
    @FXML public TextArea reply;
    @FXML public TextArea input;
    @FXML public RadioButton low;
    @FXML public RadioButton medium;
    @FXML public RadioButton high;
    @FXML public TextField portnum;
    @FXML public TextField address;
    @FXML public CheckBox clearInput;

    private ToggleGroup group = new ToggleGroup();

    private MessengerClient producer;

    String publicKeyFilename = "public.key";
        PublicKey publicKey;

        

    @FXML
    public void initialize() {
        low.setToggleGroup(group);
        medium.setToggleGroup(group);
        high.setToggleGroup(group);
        producer = new MessengerClient();
        // Read public key from file
        try {
            publicKey = RsaEncryptor.readPublicKeyFromFile(publicKeyFilename);
        } catch (Exception e) {
            System.out.println("Error reading public key from file\n".getBytes());
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void send(ActionEvent actionEvent) throws Exception {
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
        String encryptedString = "";
        encryptedString = RsaEncryptor.encryptMessageAsString(input.getText(), publicKey);
        reply.setText(producer.sendMessage("0", priority, encryptedString, add, port));
    }
}
