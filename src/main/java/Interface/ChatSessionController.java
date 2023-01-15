package Interface;

import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;

import communication.TCP_Client;
import communication.TCP_Server;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    private ListView<String> myListView;
    @FXML
    private Label myLabel;
    String OtherUser;

    List<String> msgs = new ArrayList<>();

    String currentmsg;

public ChatSessionController( )  throws SQLException {

    createDB BD = new createDB(DB_name);

       /* connected.add("Wissal");
        connected.add("LEo");
        connected.add("SIS");
        */

    msgs = BD.selectAllMsgHistory(DB_name);
    System.out.println(msgs);
}
    public void initialize(URL url, ResourceBundle resourceBundle){


        myListView.getItems().addAll(msgs);
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
         // ServerTcp server = new ServerTcp()



    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        currentmsg = myListView.getSelectionModel().getSelectedItem();
        myLabel.setText(currentmsg);
    }
        });}
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
            scene.getStylesheets().add("/styles.css");
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void send(ActionEvent event) throws SQLException {
        //récupération du message tapé dans la zone de texte
        String  message = writtenMessage.getText();
        System.out.println("MEssage written : " + message);
        TCP_Client.main(message);
        System.out.println("MEssage sent");
        String pseudo = mainFXML.mainStage.getTitle();
        createDB DB = new createDB(DB_name);
        int port = DB.selectPort(pseudo,DB_name);
        TCP_Server server= new TCP_Server();
        TCP_Client client = new TCP_Client();
       //TCP_Client t_c = new TCP_Client();
       //TCP_Client.goClient(message,port);
       //TCP_Server.goThreadsend(port,message);
    }
        //send_udp();
    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        try {
            //retour vers la page principale
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 600, 400);
            scene.getStylesheets().add("/styles.css");
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
            scene.getStylesheets().add("/styles.css");
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}