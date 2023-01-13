package Interface;

import Database.createDB;
import UDP.UDP_Server;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

public class MenuController {

    private String DB_name = "My-DB.db";
    @FXML
    private Button disconnect;
    @FXML
    private Button change_pseudo;
    @FXML
    private ListView<?> connected_users_list;

    @FXML
    void display_list(ActionEvent event) throws SQLException {
        //display list
        ListView<String> connected_users_list = new ListView<String>(createDB.selectAllConnected(DB_name));
    }

    @FXML
    void open_chat_session(ActionEvent event)throws IOException {
        //choose a person to chat with and switch to chatsession window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChatSession.fxml"));
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
    void change_pseudo(ActionEvent event) {
        //redirect to change pseudo page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChangeLogin.fxml"));
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
    void disconnect(ActionEvent event) throws IOException, SQLException {
        //deconnexion
        createDB DB = new createDB(DB_name);
        UDP_Server.broadcast_deconnection(DB.getPseudo(InetAddress.getLocalHost().toString(),DB_name));
        //retour à la page d'accueil (login)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login_page.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 600, 300);
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

