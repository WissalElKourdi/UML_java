package Interface;

import UDP.UDP_Client;
import USERS.List_Connected;
import USERS.List_USers;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.io.IOException;
import java.sql.SQLException;

public class mainFXML extends Application {

    public static Stage mainStage ;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        List_USers.clear_list_user();
        List_Connected.clear_list_co();
        mainStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_page.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 1200,800);
            scene.getStylesheets().add("/styles.css");
            mainStage.setResizable(false);
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
