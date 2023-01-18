
package Interface;

import java.util.ArrayList;

public class List_Connected {
    public List_Connected() {
        this.listCo.add("ststst");
    }

    ArrayList<String> listCo= new ArrayList<>();
    public ArrayList<String> get_List(){
        return this.listCo;
    }
    public void add_co(String pseudo){
        this.listCo.add(pseudo);
    }
    public void print_co() {


        for (int i = 0; i < this.listCo.size();i++)
        {
            System.out.println(this.listCo.get(i));
        }
    }

}
