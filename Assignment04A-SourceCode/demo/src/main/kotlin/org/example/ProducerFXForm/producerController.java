package org.example.ProducerFXForm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class producerController {

    @FXML public Button browse;
    @FXML public TextField filename;
    @FXML public Button clear_file;
    @FXML public TextArea serverReply;
    @FXML public TextArea input;
    @FXML public RadioButton rb1;
    @FXML public RadioButton rb2;
    @FXML public RadioButton rb3;
    @FXML public TextField port;
    @FXML public TextField address;
    @FXML public CheckBox clearInput;

    final ToggleGroup group = new ToggleGroup();



    @FXML
    public void initialize() {
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
    }
}
