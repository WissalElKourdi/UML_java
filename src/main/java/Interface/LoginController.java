package Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
public class LoginController {
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    public void CancelClicked(ActionEvent actionEvent) {
        System.out.println("cancel is clicked succesffully");
    }


    public class PleaseProvideControllerClassName {

        @FXML
        private CheckBox remember;

        @FXML
        private Button cancelButton;

        @FXML
        private TextField loginUsernameTextField;

        @FXML
        private Button LoginButton;

        @FXML
        void CancelClicked(ActionEvent event) {
            System.out.println("Button clicked!");
        }

    }

    @FXML
    void remembering(ActionEvent event) {

    }
}