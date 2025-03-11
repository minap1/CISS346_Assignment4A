package org.example.ConsumerFXForm;
import java.net.InetAddress;

import org.example.MessengerClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class consumerController {
    @FXML public RadioButton low;
    @FXML public RadioButton medium;
    @FXML public RadioButton high;
    @FXML public TextArea reply;
    @FXML public TextField address;
    @FXML public TextField portnum;

    private ToggleGroup priorityGroup = new ToggleGroup();

    private MessengerClient consumer;

    @FXML
    public void initialize() {
        low.setToggleGroup(priorityGroup);
        medium.setToggleGroup(priorityGroup);
        high.setToggleGroup(priorityGroup);
        consumer = new MessengerClient();
    }
    @FXML
    public void retrieve(ActionEvent actionEvent) {
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
        reply.setText(consumer.getMessage("1", priority, add, port));
    }
}
