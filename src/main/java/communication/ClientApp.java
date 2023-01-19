package communication;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.io.IOException;

public class ClientApp extends Application {
    public static Stage mainStage ;
    @Override
    public void start(Stage primaryStage) throws IOException {
        mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/client-view.fxml"));    //Tell the FXMLLoader where the FXML file is
        Parent root = loader.load();                     //create the view and link it with the Controller


        primaryStage.setTitle("Client!");
        primaryStage.setScene(new Scene(root, 480, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
