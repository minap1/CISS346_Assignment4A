package org.example.ProducerFXForm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class producerController {

    @FXML TextArea input;
    @FXML RadioButton rb1;
    @FXML RadioButton rb2;
    @FXML RadioButton rb3;

    final ToggleGroup group = new ToggleGroup();

    @FXML
    public void initialize() {
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
    }
}
