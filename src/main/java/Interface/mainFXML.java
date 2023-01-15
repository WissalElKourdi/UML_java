package Interface;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.css.*;

import java.io.IOException;
import java.net.URL;

public class mainFXML extends Application {

    public static Stage mainStage ;

    @Override
    public void start(Stage stage) throws IOException{

        mainStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_page.fxml"));    //Tell the FXMLLoader where the FXML file is
            Parent parent = loader.load();                     //create the view and link it with the Controller

            Scene scene = new Scene(parent, 600, 400);
            scene.getStylesheets().add("/styles.css");

            stage.setTitle("Chat App");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
