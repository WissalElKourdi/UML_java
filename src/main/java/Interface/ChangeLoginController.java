package Interface;

import Database.createDB;
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
                        mainFXML.mainStage.setScene(scene);
                        mainFXML.mainStage.show();
                        //ession.getInstance().getSock(" ").close();


                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void SaveNewLogin(ActionEvent event) throws IOException, SQLException {
                //get new username
                String name = NewLogin.getText();
                //if it's not already used, change to menu scene
                // new UDP_Client(port).start();
               // if (LoginController.isValid(name)) {
                        if (serv_udp.broadcast_ChangePseudo(name, port)) {
                                try {
                                        createDB DB = new createDB(Name_DB);

                                        System.out.println("ICIII" + DB.getMonPseudo(Name_DB));

                                        //UDP_Server.broadcast_connection(name, port);
                                        serv_udp.broadcast_end(port);
                                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                                        Parent parent = loader.load();
                                        Scene scene = new Scene(parent, 1200, 800);
                                        scene.getStylesheets().add("/styles.css");
                                        mainFXML.mainStage.setTitle("Chat App");
                                        mainFXML.mainStage.setScene(scene);
                                        mainFXML.mainStage.show();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        } else {
                                serv_udp.broadcast_end(port);
                                Text text = new Text("This username is already taken, choose another one");
                                result.getChildren().clear();
                                result.getChildren().add(text);
                        }
               /* } else{
                        Text text = new Text("Username should be between 5 and 15 characters and only contain letters and digits");
                        result.getChildren().clear();
                        result.getChildren().add(text);
                }*/

        }
}