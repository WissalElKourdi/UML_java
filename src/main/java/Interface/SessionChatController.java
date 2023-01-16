
package Interface;
import Interface.ServerTcp;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class SessionChatController implements Initializable {

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
    private ClientTcp client;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("SessionChatController");
        ArrayList<ServerTcp> sessionsList = new ArrayList<>();
        try{System.out.println("Connected to Client!");

            socket = new Socket("10.1.5.52",5678);
            client = new ClientTcp(socket);
            //  server = new ServerTcp(socket,sessionsList);
            //  sessionsList.add(server);
            System.out.println("Connected to Client!");

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error creating Server ... ");
        }
        vBoxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double) newValue);
            }
        });


        client.rcv(socket,vBoxMessages);
        // server.receiveMessageFromClient(vBoxMessages, socket.accept());


        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String messageToSend = tf_message.getText();
                if (!messageToSend.isBlank()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle(
                            "-fx-color: rgb(239, 242, 255);" +
                                    "-fx-background-color: rgb(15, 125, 242);" +
                                    "-fx-background-radius: 20px;");

                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.925, 0.996));

                    hBox.getChildren().add(textFlow);
                    vBoxMessages.getChildren().add(hBox);



                    client.send(socket,messageToSend);
                    //    server.sendMessageToClient(messageToSend, socket.accept());

                    tf_message.clear();
                }
            }
        });
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
    }}