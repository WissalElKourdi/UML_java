package Interface;

import UDP.UDP_Server;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChatSessionController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button disconnect;
    @FXML
    private Button change;
    @FXML
    private Button Back;
    @FXML
    private ScrollPane Conversation;
    @FXML
    private Text pseudo_autre;
    @FXML
    private TextField message;
    @FXML
    private Button send;

    @FXML
    void disconnect(ActionEvent event) {
        //deconnexion
    }

    @FXML
    void send(ActionEvent event) {
        //envoi du message tapé dans la zone de texte
        //send_udp();
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
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
    void changepseudo(ActionEvent event) {
        //broadcast_ChangePseudo(); //quels arguments

    }


    @FXML
    void Display_previous_messages(ActionEvent event) {
        //display the previous messages with the other person
    }

    @FXML
    void find_other_pseudo(ActionEvent event) {
        //afficher le pseudo de l'interlocuteur

    }

}