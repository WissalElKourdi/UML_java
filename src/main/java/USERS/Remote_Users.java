package USERS;

import java.util.ArrayList;

public class Remote_Users {
    private String pseudo;
    private String IP;

    public Remote_Users(String pseudo, String addr){
        this.pseudo=pseudo;
        this.IP=addr;
        ArrayList<Remote_Users> list_users = new ArrayList<>();
        list_users.add(this);
    }
    public String get_pseudo(){return this.pseudo;}
    public String get_IP(){return this.IP;}

    /*
    public Remote_Users get_user(){
        return this;
    }

     */

}