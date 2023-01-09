package Interface;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class LoginController {
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;

    public void initialize(){
    }

    //insert on click events and other functions for interface-user interaction
    public void CancelClicked() {
        System.out.println("Button clicked!");
    }

}
