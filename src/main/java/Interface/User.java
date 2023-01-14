package Interface;

public class User {
    static String name;

    public void setName(String name2){
        this.name = name2;
    }

    public static String getName(User user){
        return name;
    }
}