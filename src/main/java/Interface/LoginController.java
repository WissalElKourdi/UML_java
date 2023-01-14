package Interface;

import UDP.UDP_Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button LoginButton;
    @FXML
    private TextField choose_username;
    @FXML
    private TextFlow returnText;

    @FXML
    void CheckLogin(ActionEvent event) throws IOException {
        //fonction pour rediriger vers le menu quand on lcique sur ok, à supprimer à la fin des tests
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 600, 400);
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void saveUsername(ActionEvent event) throws IOException, SQLException {
        //get new username and check that it's not already used : if it's not, change to menu scene
        String name = choose_username.getText();
        if (UDP_Server.broadcast_Pseudo(name)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent, 600, 400);
                mainFXML.mainStage.setTitle("Chat App");
                mainFXML.mainStage.setScene(scene);
                mainFXML.mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Text text = new Text ("This username is already taken, choose another one");
            returnText.getChildren().add(text);
        }
    }
}