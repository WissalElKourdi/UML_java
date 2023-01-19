/*package Interface;
import javafx.application.Application;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class ObservableListCo extends Application{
    private final ObservableList<String> connected_users_remote;
    private final ListView<String> users;
    private List_Connected co = new List_Connected();


    public ObservableListCo() {
        connected_users_remote = FXCollections.observableArrayList(co.get_List());
        countriesListView = new ListView<>(connected_users_remote);
    }

    public static void main(String[] args) {
        launch(args);
    }
public void add (String pseudo){
        connected_users_remote.add(pseudo);
}


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arrange Countries and Capitals");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 450);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(50);
        ColumnConstraints column3 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        Label countriesLabel = new Label("Countries");
        GridPane.setHalignment(countriesLabel, HPos.CENTER);
        gridPane.add(countriesLabel, 0, 0);

        Label capitalsLabel = new Label("Capitals");
        GridPane.setHalignment(capitalsLabel, HPos.CENTER);
        gridPane.add(capitalsLabel, 2, 0);

        gridPane.add(countriesListView, 0, 1);
     //   gridPane.add(capitalsListView, 2, 1);

        VBox vbox = new VBox();
    //    vbox.getChildren().addAll(rightButton, leftButton);

        gridPane.add(vbox, 1, 1);

        root.setCenter(gridPane);
        GridPane.setVgrow(root, Priority.ALWAYS);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

         //   if (event.getSource().equals(leftButton)) {
                String str = countriesListView.getSelectionModel()
                        .getSelectedItem();
                if (str != null) {
                 //   capitals.remove(str);
                    connected_users_remote.add(str);
                }
          //  } else if (event.getSource().equals(rightButton)) {
                String str = countriesListView.getSelectionModel()
                        .getSelectedItem();
                if (str != null) {
                    countriesListView.getSelectionModel().clearSelection();
                    connected_users_remote.remove(str);
                  //  capitals.add(str);
                }
            }

        }
    }
}
*/