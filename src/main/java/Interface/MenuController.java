package Interface;

import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import communication.Launch_receive;
import communication.Sender;
import communication.Session;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class MenuController extends Thread implements  Initializable {
    @FXML
    public AnchorPane connected_users;
    @FXML
    public TabPane onglets;
    @FXML
    public VBox vbox_messages;
    @FXML
    public VBox vbox_messages1;
    private static final int port =2000;

    @FXML
    private Button disconnect;
    @FXML
    private Button change_pseudo;

    @FXML
    private Button button_send;

    @FXML
    private TextField tf_message;
    @FXML
    VBox vBoxMessages;
    @FXML
    private ScrollPane sp_main;
    private ServerTcp server;
    private Socket socket;
    private Sender sender;


    String name_db = "DB_MSG.db";
    private BufferedReader bufferedReaderr;
    private BufferedWriter bufferedWriterr;



    //List users :
    @FXML
    private ListView<String> myListView;
    @FXML
    private Label myLabel;
    List<String> connected;
    static String currentConnected;

    public MenuController() throws SQLException {

        createDB BD = new createDB(name_db);
        BD.insertIpseudo("wissou","192.168.1.44",name_db);
        BD.insertConnected("wissou",5000,name_db);
        connected = BD.selectAllConnected(name_db);
        System.out.println(connected);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Session.getInstance().start();
        System.out.println("Connected to Client!");


        myListView.getItems().addAll(connected);

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
                 //   (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentConnected = myListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentConnected);
                try {
                    addTab(currentConnected);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }



        });




    /*******************************/

    }
    @FXML
    private void addTab(String pseudo) throws IOException {
        int numTabs = onglets.getTabs().size();
        Tab tab = new Tab(pseudo);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChatSession.fxml"));

        tab.setContent(loader.load());
        onglets.getTabs().add(tab);
    }
    @FXML
    private void listTabs() {
        onglets.getTabs().forEach(tab -> System.out.println(tab.getText()));
        System.out.println();
    }
    public static void addLabel(String messageFromClient, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(233, 233, 235);" +
                        "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

    public static void update_list(){
        List_Connected co = new List_Connected();
      //  connected = FXCollections.observableArrayList();
        //connected.addAll( co.get_List());
      //  myListView.getItems().addAll(connected);
       // myListView.setItems(connected);

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
            //Srvsocket.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChangeLogin.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 1200, 800);
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void disconnect(ActionEvent event) throws SQLException, IOException {
        //Srvsocket.close();
        //deconnexion
        String DB_name = "DB_MSG.db";
        createDB DB = new createDB(DB_name);
        String addr = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().indexOf("/")+1);
        System.out.println("ADDRR" +addr);

        System.out.println( DB.getMonPseudo(DB_name));
        // new UDP_Client(port).start();
        UDP_Server serv_udp = new UDP_Server();
        serv_udp.broadcast_deconnection( DB.getMonPseudo(DB_name), port);
        System.out.println("PSEUDOOO" +DB.getPseudo(addr, DB_name));
      //  serv_udp.broadcast_end(port);
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

    public static String get_pseudo_user(){
        return currentConnected;
    }


    public void esteban(ActionEvent actionEvent) {
    }
}

