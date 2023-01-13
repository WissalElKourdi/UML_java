package Interface;

import UDP.UDP_Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public class ChangeLoginController {
        @FXML
        private Button cancelButton;
        @FXML
        private Button SaveButton;
        @FXML
        private TextArea NewLoginArea;
        @FXML
        private TextFlow result;

        @FXML
        void CancelClicked(ActionEvent event) throws IOException{
                try {
                        //retour vers la page principale
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                        Parent parent = loader.load();
                        Scene scene = new Scene(parent, 600, 300);
                        mainFXML.mainStage.setTitle("Chat App");
                        mainFXML.mainStage.setScene(scene);
                        mainFXML.mainStage.show();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void SaveNewLogin(ActionEvent event) throws IOException, SQLException {
                //get new username
                String name = NewLoginArea.getText();
                //if it's not already used, change to menu scene
                if (UDP_Server.broadcast_ChangePseudo(name)) {
                        try {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                                Parent parent = loader.load();
                                Scene scene = new Scene(parent, 600, 300);
                                mainFXML.mainStage.setTitle("Chat App");
                                mainFXML.mainStage.setScene(scene);
                                mainFXML.mainStage.show();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }else{
                        Text text = new Text ("This username is already taken, choose another one");
                        result.getChildren().add(text);
                }
        }
}