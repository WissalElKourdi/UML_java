package Interface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    //insert on click events and other functions for interface-user interaction
    public void CancelClicked() {
        System.out.println("Button clicked!");
    }

    private Label label;
    public void setLabelText(String text) {
        label.setText(text);
    }
}
