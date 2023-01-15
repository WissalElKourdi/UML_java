package Interface;

import Database.createDB;
import UDP.UDP_Server;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Text;
import javafx.scene.Node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChatSessionController implements Initializable {

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
    @FXML
            private VBox vbox_messages;
    @FXML
            private ServerSocket server;

    String OtherUser;


    @FXML
    void disconnect(ActionEvent event) throws SQLException, IOException {
        //deconnexion
        createDB DB = new createDB(DB_name);
        UDP_Server.broadcast_deconnection(DB.getPseudo(InetAddress.getLocalHost().toString(),DB_name),port);

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
    void Display_previous_messages(ActionEvent event) {
        //display the previous messages with the other person (stored in db)
        //utiliser get message from pour les 2 username ??
    }


    @FXML
    private void receiveData(MouseEvent event) {
        Node node = (Node) event.getSource();
        //Stage stage = (Stage) node.getScene().getWindow();
        User v = (User) mainFXML.mainStage.getUserData();
        OtherUser = User.getName(v);

        //modify textfield to display the username of the other person
        Text text = new Text (OtherUser);
        pseudo_autre.getChildren().add(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}