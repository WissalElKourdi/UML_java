
package Interface;
import communication.Handler;
import communication.Launch_receive;
import communication.Sender;
import communication.Session;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;


public class SessionChatController implements Initializable {

    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    VBox vBoxMessages;
    @FXML
    AnchorPane anchor;
    @FXML
    Label time;
    @FXML
    private ScrollPane sp_main;

    String name_db = "DB_MSG.db";

    private Socket socket;
    private Sender sender;
    private String  ip;
    private Launch_receive receiver;
    private Label Id;
    public static SessionChatController sessionchat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionchat = this;
        vBoxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });

        vBoxMessages.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               /*
               //get the time at which then message was sent (find in db)
               String Messagetime = Database.createDB.getDateFromMessage(mouseEvent.getSource(),name_db);
               //display it on the label
               time.setText(Messagetime);
               */

           }
        });

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String pseudo = MenuController.get_pseudo_user();
                String messageToSend = tf_message.getText();
                Socket sock=null;
                if (!messageToSend.isBlank()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle(
                            "-fx-color: rgb(239, 242, 255);" +
                                    "-fx-background-color: #ae96b7;" +
                                    "-fx-background-radius: 20px;" +
                                    "-fx-font-size: 15pt;");

                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.925, 0.996));

                    hBox.getChildren().add(textFlow);
                    sp_main.setContent(vBoxMessages);
                    //anchor.setStyle("-fx-background-color: #024029;");
                    System.out.println("pseudos recupere sur sessionchatcontrolle : " + pseudo);

                    //cas 1 : la session avec l'utilisateur est déja établie
                    if(Handler.getInstance().isEtablished(pseudo)){
                        System.out.println("old connection");
                        try {
                            //Session.getInstance().start();
                            sock=Session.getInstance().getSock(pseudo);
                            sender= new Sender(sock,pseudo,messageToSend);
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);

                        }
                     }else {
                        System.out.println("new Connection");
                        try {
                            System.out.println("PSEUDOOO de sessionchatcontroller pour le sock" + pseudo);
                            sock =Handler.getInstance().startConnection(pseudo);
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
                                System.out.println("the receiver is ready to receive youpii");

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    if(messageToSend.isEmpty() && sock.isConnected()){
                        sender.start();
                        tf_message.clear();
                    }
                    vBoxMessages.getChildren().add(hBox);


                }
            }
            });
        }

    public void updatercv_msg(String msgrcv){
        addLabel(msgrcv,vBoxMessages);
            this.vBoxMessages.getChildren().clear();

    }


    public static void addLabel(String messageFromClient, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: #2685c5;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-font-size: 15pt;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }


}