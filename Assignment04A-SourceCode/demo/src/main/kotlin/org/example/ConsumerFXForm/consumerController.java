package org.example.ConsumerFXForm;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class consumerController {
    @FXML public RadioButton low;
    @FXML public RadioButton medium;
    @FXML public RadioButton latest;
    @FXML public RadioButton high;
    @FXML public RadioButton consume;
    @FXML public RadioButton priority;
    @FXML public TextArea reply;
    @FXML public TextField address;
    @FXML public TextField port;
}
