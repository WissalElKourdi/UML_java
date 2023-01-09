package Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    void loginaccepted(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));    //Tell the FXMLLoader where the FXML file is
        Parent parent = loader.load();                     //create the view and link it with the Controller
        Scene scene = new Scene(parent, 600, 300);
        Stage stage = new Stage();

        //Preparing the stage
        stage.setTitle("Test stage");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void remembering(ActionEvent event) {

    }
}