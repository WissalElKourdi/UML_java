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

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import Database.createDB;

import javax.crypto.SecretKeyFactory;

import static javafx.application.Application.launch;

public class MenuController extends Thread implements  Initializable {

    //private static final int port =2000;

    private String DB_name = "DB_MSG.db";
    private static final int port =2000;


    @FXML
    private Button disconnect;
    @FXML
    private Button change_pseudo;


    @FXML
    private ListView<String> myListView;
    @FXML
    private Label myLabel;
private VBox Vbox;
    List<String> connected = new ArrayList<>();
    //  public static ServerTcp server;

    String currentConnected;
    String name_db = "DB_MSG.db";
    private Socket sockett;
    private BufferedReader bufferedReaderr;
    private BufferedWriter bufferedWriterr;
    private ServerTcp server;
    public static ServerSocket Srvsocket;

    public MenuController() throws SQLException {

        createDB BD = new createDB(name_db);
        connected = BD.selectAllConnected(name_db);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      /*  try {
            TCP_Server.servtcp();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        ArrayList<ServerTcp> sessionsList = new ArrayList<>();
        try (ServerSocket Srvsocket = new ServerSocket(5678)){
               ServerTcp.sock_acc(Srvsocket);
                //ServerTcp thread = new ServerTcp(socket, sessionsList);
               // sessionsList.add(thread);
                //thread.rcv(socket,Vbox,thread);

        //  server = new ServerTcp(Srvsocket,sessionsList);

        System.out.println("Connected to Client!");

            //ServerTcp.sock_acc(Srvsocket);
        } catch (IOException e) {
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

                    Stage newstage = new Stage();

                    mainFXML.mainStage.setResizable(false);


                    Scene scene = new Scene(parent, 1200, 800);
                    scene.getStylesheets().add("/styles.css");

                    newstage.setTitle("My New Stage Title");
                    newstage.setScene(scene);
                    newstage.show();
                    /*
                    mainFXML.mainStage.setTitle("Chatting with  "+ currentConnected);
                    mainFXML.mainStage.setScene(scene);
                    mainFXML.mainStage.show();
                    */
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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


    @FXML
    void change_pseudo(ActionEvent event) {
        //redirect to change pseudo page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChangeLogin.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 1200, 800);
            scene.getStylesheets().add("/styles.css");

            mainFXML.mainStage.setResizable(false);

            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void disconnect(ActionEvent event) throws SQLException, IOException {
        //deconnexion
        createDB DB = new createDB(DB_name);
        String addr = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().indexOf("/")+1);
        new UDP_Client(port).start();
        UDP_Server.broadcast_deconnection(DB.getPseudo(addr,DB_name), port);
        UDP_Server.broadcast_end(port);
        //retour à la page d'accueil (login)
        Srvsocket.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login_page.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 1200, 800);
            scene.getStylesheets().add("/styles.css");
            mainFXML.mainStage.setResizable(false);

            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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
/*  @FXML
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
    }*/

