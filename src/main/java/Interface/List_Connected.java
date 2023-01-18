package Interface;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class List_Connected {
    private static final ArrayList<String> listCo= new ArrayList<>();


    public ArrayList<String> get_List(){
        return listCo;
    }
    public void add_co(String pseudo){
        listCo.add(pseudo);
    }
}
