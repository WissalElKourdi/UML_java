
package Interface;

import Database.createDB;
import USERS.List_USers;

import communication.Handler;
import communication.Launch_receive;
import communication.Sender;
import communication.Session;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class SessionChatController implements Initializable {
    @FXML
    public MenuController parentcontroller;
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    VBox vBoxMessages;
    @FXML
    AnchorPane anchor;
    @FXML
    private ScrollPane sp_main;
    @FXML
    public ObservableList<String> observableHistory;
    @FXML
    private Label pseudo_autre;

    private     List<String>  myListMsg = new ArrayList<>();
    private static String currentMsg;

    private Socket socket;
    private Sender sender;
    private String  ip;
    private Launch_receive receiver;
    private Label Id;
    public SessionChatController sessionchat;
    private String name_DB = "DB_MSG.db";




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setHistory();
        update_chat();
        pseudo_autre.setText(MenuController.get_pseudo_user());

        vBoxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });
    }
    @FXML
    void send(ActionEvent event) throws IOException, SQLException {
        String pseudo = MenuController.get_pseudo_user();
        LocalTime time = LocalTime.now();
        String messageToSend = tf_message.getText() + "  ---  " + time;
        Socket sock = null;
        if (!messageToSend.isBlank()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle(
                    "-fx-background-color: #357EC7;" +
                            "-fx-background-radius: 20px;" +
                            "-fx-font-size: 15pt;");

            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.925, 0.996));
            hBox.getChildren().add(textFlow);
            sp_main.setContent(vBoxMessages);

            //cas 1 : la session avec l'utilisateur est déja établie
            if (Handler.getInstance().isEtablished(pseudo)) {
                System.out.println("old connection");
                try {
                    sock = Session.getInstance().getSock(pseudo);
                    sender = new Sender(sock, pseudo, messageToSend);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("new Connection");
                try {
                    sock = Handler.getInstance().startConnection(pseudo);
                } catch (IOException e) {
                    System.out.println("erreur création du socket ");
                    throw new RuntimeException(e);
                }
                // sender = new Sender(sock, pseudo, messageToSend);

                if (sock.isConnected()) {
                    try {
                        System.out.println("Connected to sock");
                        sender = new Sender(sock, pseudo, messageToSend); // le thread qui envoie les messages au client
                        System.out.println("the sender is created");
                        receiver = new Launch_receive(sock, pseudo); // le thread qui reçoit les messages
                        System.out.println("the receiver is created ");
                        Launch_receive.sessions.add(receiver);
                        receiver.start();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (messageToSend.isEmpty() && sock.isConnected()) {
                sender.start();
            }
            tf_message.clear();
            vBoxMessages.getChildren().add(hBox);
        }
    }





    public void setHistory(){

        try {
            createDB DB = new createDB(name_DB);
            String pseudo = MenuController.get_pseudo_user();
            if(pseudo!=null){
            String addr = List_USers.get_IP_user(pseudo);
            myListMsg=DB.selectMsg(addr,name_DB); }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update_chat(){

        List<String> msg;
        String msg_display;

        vBoxMessages.getChildren().clear();
        for(int i=0; i< myListMsg.size(); i++){
            msg= new ArrayList<String>(Arrays.asList(myListMsg.get(i).split(":")));
           if(msg.get(0).equals("sender")){
                msg_display = msg.get(2) + msg.get(3);
                addMsg(msg_display,true);
            }else{
                msg_display = msg.get(2) + msg.get(3) ;
                addMsg(msg_display,false);
            }
        }
    }

    public void addMsg(String msg, Boolean Sender) {
        if (vBoxMessages == null) {
            System.out.println("ici");
        }
        System.out.println(msg);
        if (Sender) {
            if (!msg.isBlank()) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                Text text = new Text(msg);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle(
                        "-fx-background-color: #357EC7;" +
                                "-fx-background-radius: 20px;" +
                                "-fx-font-size: 15pt;");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(0.934, 0.925, 0.996));
                hBox.getChildren().add(textFlow);
                sp_main.setContent(vBoxMessages);
                vBoxMessages.getChildren().add(hBox);
            }
        } else {
            if (!msg.isBlank()) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                Text text = new Text(msg);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle(
                        "-fx-background-color: #42AB18;" +
                                "-fx-background-radius: 20px;" +
                                "-fx-font-size: 15pt;");

                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(0.934, 0.925, 0.996));
                hBox.getChildren().add(textFlow);
                sp_main.setContent(vBoxMessages);

                vBoxMessages.getChildren().add(hBox);
            }
        }
    }


    public static void addLabel(String messageFromClient, VBox vBox){
        VBox primaryVbox = new VBox();
        primaryVbox.setAlignment(Pos.CENTER_LEFT);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        System.out.println("msg from client "+ messageFromClient);
        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: #42AB18;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-font-size: 15pt;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);
        primaryVbox.getChildren().add(hBox);

        System.out.println("jai add txt");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("JE run je add ds vbox");
                vBox.getChildren().add(primaryVbox);
            }
        });
    }


    public static void close_tab_sess(String pseudo){
        Tab tab = MenuController.ListTabs.get(pseudo);
        EventHandler<Event> handler = tab.getOnClosed();
        if (null != handler) {
            handler.handle(null);
        } else {
            tab.getTabPane().getTabs().remove(tab);
        }
    }
}