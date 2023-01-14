package Interface;

import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.*;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

public class ChatSessionController {

    private String DB_name = "DB_MSG.db";
    private static final int port =2000;
    @FXML
    private Button disconnect;
    @FXML
    private Button change;
    @FXML
    private Button Back;
    @FXML
    private ScrollPane Conversation;
    @FXML
    private TextFlow pseudo_autre;
    @FXML
    private TextField writtenMessage;
    @FXML
    private Button send;

    String OtherUser;


    @FXML
    void disconnect(ActionEvent event) throws SQLException, IOException {
        //deconnexion
        createDB DB = new createDB(DB_name);
        String addr = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().indexOf("/")+1);
        new UDP_Client(port).start();
        UDP_Server.broadcast_deconnection(DB.getPseudo(addr,DB_name), port);
        UDP_Server.broadcast_end(port);
        //retour à la page d'accueil (login)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login_page.fxml"));
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
    void send(ActionEvent event) {
        //récupération du message tapé dans la zone de texte
        String  message = writtenMessage.getText();
        //send_udp();
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        try {
            //retour vers la page principale
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
    void changepseudo(ActionEvent event) throws IOException {
        //redirige vers la page de changement de login
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChangeLogin.fxml"));
       User v = (User) mainFXML.mainStage.getUserData();
        OtherUser = User.getName(v);

        //modify textfield to display the username of the other person
        Text text = new Text (OtherUser);
        pseudo_autre.getChildren().add(text);
    } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }}