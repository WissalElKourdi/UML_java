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
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));    //Tell the FXMLLoader where the FXML file is
        Parent parent = loader.load();                     //create the view and link it with the Controller
        Scene scene = new Scene(parent, 600, 300);
        Stage stage = new Stage();

        //Preparing the stage
        stage.setTitle("Chat App");
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
