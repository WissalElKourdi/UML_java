package Interface;

import UDP.UDP_Client;
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
        private static final int port = 2000;
        @FXML
        private Button cancelButton;
        @FXML
        private Button SaveButton;
        @FXML
        private TextField NewLoginArea;
        @FXML
        private TextFlow result;

        /*      public ChangeLoginController() {
                try {
                       new UDP_Client(port).start();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }*/


        @FXML
        void CancelClicked(ActionEvent event) throws IOException{
                try {
                        //retour vers la page principale
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                        Parent parent = loader.load();
                        Scene scene = new Scene(parent, 1200,800);
                        scene.getStylesheets().add("/styles.css");
                        mainFXML.mainStage.setTitle("Chat App");
                        mainFXML.mainStage.setResizable(false);
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
                /*if (LoginController.isValid(name)) {*/
                        new UDP_Client(port).start();
                        if (UDP_Server.broadcast_ChangePseudo(name, port)) {
                                try {
                                        //UDP_Server.broadcast_connection(name, port);
                                        UDP_Server.broadcast_end(port);
                                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                                        Parent parent = loader.load();
                                        Scene scene = new Scene(parent, 1200, 800);
                                        scene.getStylesheets().add("/styles.css");
                                        mainFXML.mainStage.setTitle("Chat App");
                                        mainFXML.mainStage.setResizable(false);
                                        mainFXML.mainStage.setScene(scene);
                                        mainFXML.mainStage.show();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        } else {
                                UDP_Server.broadcast_end(port);
                                Text text = new Text("This username is already taken, choose another one");
                                result.getChildren().clear();
                                result.getChildren().add(text);
                        }
                /*} else {
                        Text text = new Text("Please choose a username between 5 and 15 characters containing only letters and digits, no special character.");
                        result.getChildren().clear();
                        result.getChildren().add(text);
                }*/
        }
}