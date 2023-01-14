package Interface;

import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Objects;

public class MenuController {

    private static final int port =2000;
    private final String DB_name = "DB_MSG.db";

    @FXML
    private Button disconnect;
    @FXML
    private Button change_pseudo;
    @FXML
    private ListView<?> connected_users_list;

    @FXML
    void display_list(ListView.EditEvent<?> event) throws SQLException {
        //afficher la liste des users connectés
        createDB DB = new createDB(DB_name);
        System.out.println("here");
        ListView<String> connected_users_list = new ListView<String>(DB.selectAllConnected(DB_name));

        /*connected_users_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("clicked on"+ connected_users_list.getSelectionModel().getSelectedItem());
            }
        });
         */
    }

    @FXML public void handleMouseClick(MouseEvent arg0) throws SQLException {
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
        createDB DB = new createDB(DB_name);

        //System.out.println("clicked on " + connected_users_list.getSelectionModel().getSelectedItem());
        ListView<String> connected_users_list = new ListView<String>(DB.selectAllConnected(DB_name));
    }


    @FXML
    void change_pseudo(ActionEvent event) {
        //redirect to change pseudo page
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
    void disconnect(ActionEvent event) throws IOException, SQLException {
        createDB DB = new createDB(DB_name);
       // System.out.println(DB.selectAllConnected(DB_name));
       // System.out.println("je suis ici ---------"  );
        String addr = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().indexOf("/")+1);
       // System.out.println(addr);
      //  System.out.println(DB.selectAllMsgIPseudo(DB_name));
     //   System.out.println(DB.getPseudo(addr,DB_name));
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
    void Display_previous_messages(ActionEvent event) {
        //display the previous messages with the other person (stored in db)
        //utiliser get message from pour les 2 username ??
    }


    @FXML
    private void receiveData(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        //Stage stage = (Stage) node.getScene().getWindow();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ChatSession.fxml")));
        Scene scene = new Scene(parent, 600, 400);
        mainFXML.mainStage.setTitle("Chat App");
        mainFXML.mainStage.setScene(scene);
        mainFXML.mainStage.show();
    }


    @FXML
    private void sendData(MouseEvent event) {
        User u = new User();
        User.name = (String) connected_users_list.getSelectionModel().getSelectedItem();
        //Node node = (Node) event.getSource();
       // Stage stage = (Stage) node.getScene().getWindow();
        //stage.close();
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ChatSession.fxml")));
            mainFXML.mainStage.setUserData(u);
            Scene scene = new Scene(parent,600, 400);
            mainFXML.mainStage.setTitle("Chatting with " + User.name);
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();        }
    }

}

