package Interface;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.*;
import java.net.URL;
import java.util.List;
import javafx.event.*;

public class mainFXML extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/welcome_page.fxml"));    //Tell the FXMLLoader where the FXML file is
        Parent root = loader.load();                     //create the view and link it with the Controller
        Controller controller = loader.getController();
        controller.setLabelText("Injected Text");

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}