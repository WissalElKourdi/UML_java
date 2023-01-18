package Interface;

public class Pseudo {
    private static final Pseudo instance = new Pseudo();

    private String pseudo;


    private Pseudo(){}

    public static Pseudo getInstance(){return instance;}
    public String getPseudo(){
        return pseudo;
    }
    public void setPseudo(String pseudo){
        this.pseudo=pseudo;
    }
}
