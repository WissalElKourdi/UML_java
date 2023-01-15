package Interface;

import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import communication.TCP_Server;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.*;
import javafx.scene.*;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import Database.createDB;
import static javafx.application.Application.launch;

public class MenuController extends Thread implements  Initializable {

    //private static final int port =2000;

    @FXML
    private Button disconnect;
    @FXML
    private Button change_pseudo;


    @FXML
    private ListView<String> myListView;
    @FXML
    private Label myLabel;

    List<String> connected = new ArrayList<>();

   String currentConnected;
    String name_db = "DB_MSG.db";

    public MenuController() throws SQLException {

        createDB BD = new createDB(name_db);

       /* connected.add("Wissal");
        connected.add("LEo");
        connected.add("SIS");
        */

        connected = BD.selectAllConnected(name_db);

    }
 /*   @FXML

    public void initialize(ListView.EditEvent<?> event) throws SQLException {
        //afficher la liste des users connectés
        createDB DB = new createDB(DB_name);


        ListView<String> connected_users_list = new ListView<String>(DB.selectAllConnected(DB_name));

      connected_users_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("clicked on"+ connected_users_list.getSelectionModel().getSelectedItem());
            }
        });

    }
    public void start(Stage primaryStage) {

        ListView<String> list = new ListView<>();
        FXCollections FXCollections = null;
        ObservableList<String> arr = javafx.collections.FXCollections.observableArrayList("Java", "HTML", "CSS", "C++", "PHP");
        list.setItems(arr);
        FlowPane root = new FlowPane();
        root.getChildren().add(list);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("ListView");
        primaryStage.setScene(scene);
        primaryStage.show();
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

*/
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
        String DB_name = "DB_MSG.db";
        createDB DB = new createDB(DB_name);
        String addr ;
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.connect(InetAddress.getByName("255.255.255.255"), 12345);
            addr = datagramSocket.getLocalAddress().getHostAddress();
        }
        int port=900;
        new UDP_Client(port).start();
        UDP_Server.broadcast_deconnection(DB.getPseudo(addr, DB_name), port);
        UDP_Server.broadcast_end(port);
        createDB BD = new createDB(name_db);
        connected = BD.selectAllConnected(name_db);
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


 /*   @FXML
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
*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createDB DB = null;


        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            DB = new createDB("DB_MSG.db");
            String addr ;
            datagramSocket.connect(InetAddress.getByName("255.255.255.255"), 12345);
            addr = datagramSocket.getLocalAddress().getHostAddress();
           int port= DB.selectPort( DB.getPseudo(addr,"DB_MSG.db"),"DB_MSG.db");
            port = 5000;
            TCP_Server TCP_srv = new TCP_Server();
            TCP_Server.goThread(port);
        } catch (SQLException | UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }


        myListView.getItems().addAll(connected);
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
       currentConnected = myListView.getSelectionModel().getSelectedItem();
       myLabel.setText(currentConnected);

        try { FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChatSession.fxml"));
            Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        mainFXML.mainStage.setTitle("Chatting with  "+ currentConnected);
        mainFXML.mainStage.setScene(scene);
        mainFXML.mainStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
});
    }
}

