package Interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class List_Connected {
    private final ObservableList<String> listCo = FXCollections.observableArrayList();

    public ObservableList<String> get_List(){
        return this.listCo;
    }
    public void add_co(String pseudo){
        this.listCo.add(pseudo);
    }
}
