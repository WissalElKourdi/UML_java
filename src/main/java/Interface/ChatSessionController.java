import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginChatSession {

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
        //broadcast_deconnection(); //quels arguments
        //close window ?
    }

    @FXML
    void send(ActionEvent event) {
        //envoi du message tapé dans la zone de texte
    }

    @FXML
    void backToMenu(ActionEvent event) {
        //retour vers la page principale
        root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
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
