package History;

import java.util.ArrayList;
import java.util.Date;

public class History {
    ArrayList Message_History = new ArrayList();

    public void Add_Message_History(String Message){
        Message_History.add(Message);
    }

}
