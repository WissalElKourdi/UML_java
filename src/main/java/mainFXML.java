import javafx.scene.paint.Color;

@Override
public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("welcome_page.fxml"));

        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
        }