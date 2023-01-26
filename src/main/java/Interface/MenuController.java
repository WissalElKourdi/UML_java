package Interface;

import static Interface.LoginController.client;
import Database.createDB;
import UDP.UDPManager;
import UDP.UDP_Server;
import USERS.List_Connected;
import communication.Launch_receive;
import communication.Sender;
import communication.Session;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
    @FXML
    Tab mainTab;
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
    @FXML
    private Label myLabel;
    @FXML
    private ListView<String> myListconnected;

    private static final int port =2000;
    private Socket socket;
    private Sender sender;
    private BufferedReader bufferedReaderr;
    private BufferedWriter bufferedWriterr;
    public List<Tab> listTabs;
    UDP_Server serv_udp = new UDP_Server();
    String name_db = "DB_MSG.db";


    //List users :
  //  public static List_Connected conn = new List_Connected();

    public static ArrayList<String> coo = new ArrayList<>();
    private ObservableList<String> list ;
    private static String currentConnected;
    public static HashMap<String,SessionChatController> ListControllers = new HashMap<>();
    public static HashMap<String,Tab> ListTabs = new HashMap<>();

    Session session = Session.getInstance();

    public MenuController() throws SocketException, SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //update_list();
       // UDPManager manager = new UDPManager();
        mainTab.setClosable(false);
        client.setMenu(this);
        myListconnected.getItems().addAll(List_Connected.listCo);


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

        });
    }

    @FXML
    private void addTab(String pseudo) throws IOException {
        int numTabs = onglets.getTabs().size();

        Tab tab = new Tab(pseudo);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChatSession.fxml"));
        tab.setContent(loader.load());
        //listTabs.add(loader.getController());
       // listTabs.add(pseudo);
        SessionChatController controller = (SessionChatController) loader.getController();
       /* Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        controller.addMsg("hohoeoeoeoe");
                    }
                }
        );*/

      ListControllers.put(pseudo, controller);
    ListTabs.put(pseudo,tab);
        onglets.getTabs().add(tab);
      //  onglets.getTabs().get()
    }



    public void update_list(){
        myListconnected.getItems().clear();
        myListconnected.getItems().addAll(List_Connected.listCo);
    }


    @FXML
    void change_pseudo(ActionEvent event) {
        //redirect to change pseudo page
        try { createDB DB =new createDB("DB_MSG.db");
            System.out.println(List_Connected.listCo + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            serv_udp.broadcast_je_vais_change_mon_pseudo(DB.getMonPseudo(name_db),port);
            System.out.println("MON PSEUDOOOOO+  "+ DB.getMonPseudo(name_db));
            //session.close_sess();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChangeLogin.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 1200, 800);
            scene.getStylesheets().add("/styles.css");
            mainFXML.mainStage.setTitle("Chat App");
            mainFXML.mainStage.setScene(scene);
            mainFXML.mainStage.show();
        } catch (IOException | SQLException e) {
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
                System.out.println("JE ME DCOOO" + DB.getMonPseudo(DB_name));
                serv_udp.broadcast_end(port);
                session.close_sess();
                //retour à la page d'accueil (login)
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login_page.fxml"));
                    Parent parent = loader.load();
                    Scene scene = new Scene(parent, 1200, 800);
                    scene.getStylesheets().add("/styles.css");
                    mainFXML.mainStage.setTitle("Chat App");
                    mainFXML.mainStage.setScene(scene);
                    mainFXML.mainStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    public static String get_pseudo_user(){return currentConnected;}




}

