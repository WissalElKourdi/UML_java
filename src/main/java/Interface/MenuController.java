package Interface;

import static Interface.LoginController.client;
import Database.createDB;
import UDP.UDP_Server;
import USERS.List_Connected;
import communication.Sender;
import communication.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Tab;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Interface.LoginController.get_client;
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

    @FXML Tab mainTab;

    @FXML
    private TextField tf_message;
    @FXML
    VBox vBoxMessages;
    @FXML
    private ScrollPane sp_main;
    private Socket socket;
    private Sender sender;


    String name_db = "DB_MSG.db";
    private BufferedReader bufferedReaderr;
    private BufferedWriter bufferedWriterr;
    public static List<String> listTabs;



    //List users :
  //  public static List_Connected conn = new List_Connected();

    public static ArrayList<String> coo = new ArrayList<>();
    @FXML
    private Label myLabel;

    @FXML
    private ListView<String> myListconnected;
    private ObservableList<String> list ;
    private static String currentConnected;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //update_list();
        mainTab.setClosable(false);
        client.setMenu(this);

        myListconnected.getItems().addAll(List_Connected.listCo);
        Session session = Session.getInstance();
        session.start();
        myListconnected.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        currentConnected = myListconnected.getSelectionModel().getSelectedItem();
                        myLabel.setText(currentConnected);
                        try {
                            addTab(currentConnected);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                });  }

    @FXML
    private void addTab(String pseudo) throws IOException {
        int numTabs = onglets.getTabs().size();
        //listTabs.add(pseudo);
        Tab tab = new Tab(pseudo);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChatSession.fxml"));
        tab.setContent(loader.load());
        onglets.getTabs().add(tab);
    }


    public void update_list(){
        myListconnected.getItems().clear();
        myListconnected.getItems().addAll(List_Connected.listCo);
    }

    @FXML
    void change_pseudo(ActionEvent event) {
        //redirect to change pseudo page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChangeLogin.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 1200, 800);
            scene.getStylesheets().add("/styles.css");
            mainFXML.mainStage.setTitle("Chat App");

            mainFXML.mainStage.setTitle("Totally spicy");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

            @FXML
            void disconnect(ActionEvent event) throws SQLException, IOException {
                //deconnexion
                String DB_name = "DB_MSG.db";
                createDB DB = new createDB(DB_name);
                String addr = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().indexOf("/") + 1);
                UDP_Server serv_udp = new UDP_Server();
                serv_udp.broadcast_deconnection(DB.getMonPseudo(DB_name), port);
                //  serv_udp.broadcast_end(port);
                //retour à la page d'accueil (login)
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login_page.fxml"));
                    Parent parent = loader.load();
                    Scene scene = new Scene(parent, 1200, 800);
                    scene.getStylesheets().add("/styles.css");
                    mainFXML.mainStage.setTitle("Totally spicy");
                    mainFXML.mainStage.setScene(scene);
                    mainFXML.mainStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public static String get_pseudo_user(){return currentConnected;}




}

