package Interface;

import Database.createDB;
import UDP.IP_addr;
import UDP.UDP_Client;
import UDP.UDP_Server;
import communication.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;

public class ChangeLoginController {
        private static final int port = 2000;
        private String Name_DB = "DB_MSG.db";
        @FXML
        private Button cancelButton;

        @FXML
        private Button SaveButton;
        @FXML
        private TextField NewLogin;
        @FXML
        private TextFlow result;
        UDP_Server serv_udp = new UDP_Server();

        public ChangeLoginController() throws SocketException, SQLException {
        }



        @FXML
        void CancelClicked(ActionEvent event) throws IOException{
                try {
                        //retour vers la page principale
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                        Parent parent = loader.load();
                        Scene scene = new Scene(parent, 1200,800);
                        scene.getStylesheets().add("/styles.css");
                        mainFXML.mainStage.setTitle("Totally spicy");
                        mainFXML.mainStage.setScene(scene);
                        mainFXML.mainStage.show();


                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void SaveNewLogin(ActionEvent event) throws IOException, SQLException {
                //get new username
                String name = NewLogin.getText();
                //if it's not already used, change to menu scene
               if (LoginController.isValid(name)) {
                       serv_udp.broadcast_AskState(name, port);
                        if (serv_udp.broadcast_ChangePseudo(name, port)) {
                                try {
                                        String addr = IP_addr.get_my_IP().toString();
                                        serv_udp.broadcast_change_info(name,addr,port);
                                        serv_udp.broadcast_connection(name, port);
                                        serv_udp.broadcast_ChangePseudo(name,port);
                                       // serv_udp.broadcast_end(port);
                                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                                        Parent parent = loader.load();
                                        Scene scene = new Scene(parent, 1200, 800);
                                        scene.getStylesheets().add("/styles.css");
                                        mainFXML.mainStage.setTitle("Totally spicy");
                                        mainFXML.mainStage.setScene(scene);
                                        mainFXML.mainStage.show();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        } else {

                                Text text = new Text("This username is already taken, choose another one");
                                result.getChildren().clear();
                                result.getChildren().add(text);
                        }
                } else{
                        Text text = new Text("Username should be between 5 and 15 characters and only contain letters and digits");
                        result.getChildren().clear();
                        result.getChildren().add(text);
                }
        }
}