package Interface;

import Database.createDB;
import UDP.UDP_Client;
import UDP.UDP_Server;
import communication.Sender;
import communication.Session;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;
import static javafx.collections.FXCollections.observableArrayList;

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
    private Socket sockett;
    private BufferedReader bufferedReaderr;
    private BufferedWriter bufferedWriterr;
    //private ServerTcp server;

    public static Session session;
    public static ServerSocket Srvsocket;



    //List users :
    static List_Connected conn = new List_Connected();
    //private static ArrayList<String> users_co = conn.get_List();

  //  private static final String[] users_co = {"Wissal","LEo","Sis"} ;

  public static ArrayList<String> coo = new ArrayList<>(conn.get_List());
  @FXML
  static
  ObservableList<String> observable_co = FXCollections.observableArrayList(coo);
  //public ObservableList<String> observable_co = observableArrayList(coo);
    @FXML
    static final ListView<String> myListView = new ListView<>();
    @FXML
    private Label myLabel;
    //List<String> connected;
    String currentConnected;
     // ListView<String> listView = new ListView(co);


    public MenuController() throws SQLException {

    //    createDB BD = new createDB(name_db);
      //  connected = BD.selectAllConnected(name_db);
     //   System.out.println(connected);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //myListView.setEditable(true);





        try {
            session = new Session();
            session.start();// on launce l'écout
            createDB BD = new createDB(name_db);
            Srvsocket = new ServerSocket(5679);
            ClientTcp.sock_acc(Srvsocket);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
           }

      //  observable_co.addAll("A", "B", "C", "D", "E");
       // data.add("double click to sleect user");
      //  update_list();
       // System.out.println("Connected to Client!");
     //  observable_co.addListener((ListChangeListener<? super String>) observable_co);
     //  observable_co.addAll(coo);
       myListView.setItems(observable_co);
        myListView.getItems().addAll();
                 // myListView.
      //  myListView.ysetCellFactory(ComboBoxListCell.forListView(observable_co));


        //update_list();
        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
                 //   (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentConnected =     myListView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentConnected);
              //  scrollPane.setVvalue((Double) newValue);
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
        onglets.getTabs().add(tab);
    }

    @FXML
    public static void update_list(){

        observable_co.clear();
        observable_co.addAll(coo);
        myListView.setItems(observable_co);

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



    public void start(Stage primaryStage) {
        ListView<String> list = new ListView<>();
        FXCollections FXCollections = null;
        ObservableList<String> arr = observableArrayList("Java", "HTML", "CSS", "C++", "PHP");
        list.setItems(arr);
        FlowPane root = new FlowPane();
        root.getChildren().add(list);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("ListView");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @FXML
    void esteban(ActionEvent event){
                 conn.add_co("Sirine");
            conn.add_co("wissal");
                conn.add_co("leoonie");
           conn.print_co();      }



           @FXML
    void change_pseudo(ActionEvent event) {
        //redirect to change pseudo page
        try {
            Srvsocket.close();
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
    void disconnect(ActionEvent event) throws SQLException, IOException {
        Srvsocket.close();
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

