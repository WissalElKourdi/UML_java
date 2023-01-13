package Interface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ChangeLoginController {
        @FXML
        private Button cancelButton;
        @FXML
        private Button SaveButton;
        @FXML
        private TextArea NewLoginArea;

        @FXML
        void CancelClicked(ActionEvent event) throws IOException{
                try {
                        //retour vers la page principale
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));    //Tell the FXMLLoader where the FXML file is
                        Parent parent = loader.load();                     //create the view and link it with the Controller
                        Scene scene = new Scene(parent, 600, 300);

                        Stage stage = new Stage();

                        //Preparing the stage
                        stage.setTitle("Chat App");
                        stage.setScene(scene);
                        stage.show();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void SaveNewLogin(ActionEvent event) {
                //broadcast_ChangePseudo();
                //back to Menu
        }
}