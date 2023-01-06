package Interface;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;

public class mainFXML extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Hello World");
        stage.setWidth(300);
        stage.setHeight(200);

        //Load fxml file
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new Controller());

        URL xmlUrl = getClass().getResource("/welcome_page.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Label helloWorldLabel = new Label("Hello world!");
        Scene scene = new Scene(helloWorldLabel);

        stage.setScene(scene);
        stage.show();
    }
}