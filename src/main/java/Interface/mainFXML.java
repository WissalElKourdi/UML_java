package Interface;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

public class mainFXML extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login_page.fxml"));    //Tell the FXMLLoader where the FXML file is
        Parent parent = loader.load();                     //create the view and link it with the Controller
        ModuleLayer.Controller controller = loader.getController();
        //controller.setLabelText("Test Label");

        //Preparing the Scene
        Scene scene = new Scene(parent, 600, 300);

        //Preparing the stage
        stage.setTitle("Test stage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}