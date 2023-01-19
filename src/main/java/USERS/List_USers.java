package USERS;

import java.util.ArrayList;

public class List_USers {
    private static ArrayList<Remote_Users> list = new ArrayList<Remote_Users>();

    public static void add_User(Remote_Users user){list.add(user);}

    public static String get_IP_user(String name){
        for(int i = 0; i<list.size();i++){
            if (name.equals(list.get(i).get_pseudo())){
                return list.get(i).get_IP();
            }
        }
        return "Didn't find this USER";
    }

    public static String get_pseudo_user(String addr){
        for(int i = 0; i<list.size();i++){
            System.out.println(list.get(i).get_IP() + "ICIII JE GET PSEUDO USER");
            if (addr.equals(list.get(i).get_IP())){
                return list.get(i).get_pseudo();
            }
        }
        return "Didn't find this USER";
    }

    public static void clear_list_user(){
            list.clear();
    }
}
