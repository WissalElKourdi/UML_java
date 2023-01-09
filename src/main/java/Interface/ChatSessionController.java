package Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginChatSession {

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
        broadcast_deconnection(); //quels arguments
        //close window ?
    }

    private void broadcast_deconnection() {
    }

    @FXML
    void send(ActionEvent event) {
        //envoi du message tapé dans la zone de texte
    }

    @FXML
    void backToMenu(ActionEvent event) {
        //retour vers la page principale
    }

    @FXML
    void changepseudo(ActionEvent event) {
        broadcast_ChangePseudo(); //quels arguments

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
