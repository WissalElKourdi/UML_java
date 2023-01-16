package Interface;

import UDP.UDP_Client;
import UDP.UDP_Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private static final int port = 5000;

    @FXML
    private Button LoginButton;
    @FXML
    private TextField choose_username;
    @FXML
    private TextFlow returnText;

    public static boolean isValid(String value) {
        String legalCharacters = "abcdefghijklmnopqrstuvwxzy0123456789";
        boolean valid = true;
        if (value.length() < 4 || value.length() > 15) {
            valid = false;
        }
        else {
            for (int x = 0; x < value.length() ; x++) {
                boolean found = false;
                for (int z = 0; z < legalCharacters.length(); z++) {
                    char c = value.charAt(x);
                    c = java.lang.Character.toLowerCase(c);
                    if (c == legalCharacters.charAt(z)) {
                        found = true;
                    }
                }
                if (!found) {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    @FXML
    void saveUsername(ActionEvent event) throws IOException, SQLException {
        //get new username and check that it's not already used : if it's not, change to menu scene
        String name = choose_username.getText();
       // System.out.println("je suis ici" + UDP_Server.broadcast_Pseudo(name));
        new UDP_Client(port).start();
        if (UDP_Server.broadcast_Pseudo(name,port)) {
            if (!isValid(name)){
                Text text = new Text ("Your username should contain between 5 and 15 characters, only letters and digits are allowed.");
                returnText.getChildren().clear();
                returnText.getChildren().add(text);
            } else {
                try {
                    UDP_Server.broadcast_connection(name, port);
                    UDP_Server.broadcast_end(port);
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Menu.fxml"));
                    Parent parent = loader.load();
                    mainFXML.mainStage.setResizable(false);


                    //Stage newstage = new Stage();

                    Scene scene = new Scene(parent, 1200, 800);
                    scene.getStylesheets().add("/styles.css");
/*
                    newstage.setTitle("My New Stage Title");
                    newstage.setScene(scene);
                    newstage.show();
                    */
                    mainFXML.mainStage.setTitle("Chat App");
                    mainFXML.mainStage.setScene(scene);
                    mainFXML.mainStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("je suis ici");
            UDP_Server.broadcast_end(port);
            System.out.println("je suis ici");
            Text text = new Text ("This username is already taken, choose another one");
            returnText.getChildren().clear();
            returnText.getChildren().add(text);
        }
    }
}