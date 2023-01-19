package communication;


import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.io.IOException;


public class ServerApp extends Application {
    public static Stage mainStage ;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server-view.fxml"));    //Tell the FXMLLoader where the FXML file is
            Parent parent = loader.load();                     //create the view and link it with the Controller

            Scene scene = new Scene(parent, 600, 400);

            stage.setTitle("Chat App");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("server-view.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Server!");
        primaryStage.setScene(new Scene(root, 480, 400));
        primaryStage.show();
    }*/
    }

    public static void main(String[] args) {
        launch();
    }
}
