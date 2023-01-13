package Interface;

import Database.createDB;
import UDP.UDP_Server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

public class MenuController {

    private String DB_name = "DB_MSG.db";
    @FXML
    private Button disconnect;
    @FXML
    private Button change_pseudo;
    @FXML
    private ListView<?> connected_users_list;

    @FXML
    void display_list(ActionEvent event) throws SQLException {
        //afficher la liste des users connectés
        ListView<String> connected_users_list = new ListView<String>(createDB.selectAllConnected(DB_name));

        /*connected_users_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("clicked on"+ connected_users_list.getSelectionModel().getSelectedItem());
            }
        });
         */
    }

    @FXML public void handleMouseClick(MouseEvent arg0) {
        //sauvegarder l'user choisi par l'utilisateur (évènement on click)
        String User = (String) connected_users_list.getSelectionModel().getSelectedItem();

        try {
            Text text = new Text (User);

            //ouvrir la page de chat avec l'user choisi => pb sur comment transmettre l'user choisi
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChatSession.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 600, 300);
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("clicked on " + connected_users_list.getSelectionModel().getSelectedItem());
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

