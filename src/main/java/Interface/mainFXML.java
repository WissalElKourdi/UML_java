package Interface;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.io.IOException;

public class mainFXML extends Application {

    public static Stage mainStage ;

    @Override
    public void start(Stage stage) throws IOException{

        mainStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_page.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent, 1200,800);
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
