
package Interface;

import java.util.ArrayList;
import java.util.List;

public class List_Connected {
    public static final List<String> listCo = new ArrayList<String>();
    public List_Connected() {
        listCo.add("ststst");
    }

    public List<String> get_List(){
        return listCo;
    }
    public static void add_co(String pseudo){
        listCo.add(pseudo);
      //  MenuController.notifyChangelist_Co;
    }
    public static void print_co() {
        for (int i = 0; i < listCo.size();i++)
        {
            System.out.println(listCo.get(i));
        }
    }



}
