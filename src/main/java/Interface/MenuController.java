package Interface;

import Database.createDB;
import UDP.UDP_Server;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    void display_list(ActionEvent event) {
        //display list
    }

    @FXML
    void open_chat_session(ActionEvent event)throws IOException {
        //choose a person to chat with and switch to chatsession window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChatSession.fxml"));    //Tell the FXMLLoader where the FXML file is
            Parent parent = loader.load();                     //create the view and link it with the Controller
            Scene scene = new Scene(parent, 600, 300);
            //Stage stage = new Stage();

            //Preparing the stage
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void change_pseudo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChangeLogin.fxml"));    //Tell the FXMLLoader where the FXML file is
            Parent parent = loader.load();                     //create the view and link it with the Controller
            Scene scene = new Scene(parent, 600, 300);
            //Stage stage = new Stage();

            //Preparing the stage
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void disconnect(ActionEvent event) throws IOException, SQLException {
        createDB DB = new createDB(DB_name);
        UDP_Server.broadcast_deconnection(DB.getPseudo(InetAddress.getLocalHost().toString(),DB_name));

    }

}

