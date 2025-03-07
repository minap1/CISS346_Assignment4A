package org.example.ConsumerFXForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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

    private ToggleGroup priorityGroup = new ToggleGroup();
    private ToggleGroup retrieveGroup = new ToggleGroup();
    @FXML
    public void initialize() {
        low.setToggleGroup(priorityGroup);
        medium.setToggleGroup(priorityGroup);
        high.setToggleGroup(priorityGroup);
        consume.setToggleGroup(retrieveGroup);
        priority.setToggleGroup(retrieveGroup);
        latest.setToggleGroup(retrieveGroup);
    }
    public void retrieve(ActionEvent actionEvent) {
        int pritority;
        int retrieveType;

        if (low.isSelected()) {

        }else if (medium.isSelected()) {

        }else if (high.isSelected()) {

        }else{

        }
    }
}
