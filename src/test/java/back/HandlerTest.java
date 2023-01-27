package back;
import USERS.List_USers;
import USERS.Remote_Users;
import communication.Handler;
import communication.Sender;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.IOException;
import java.net.Socket;

public class HandlerTest {
    private Handler handler;
    private String pseudo;

    private Socket socket;
    @Before
    public void setup() throws IOException {
        handler = Handler.getInstance();
        pseudo = "testuser";
        List_USers.add_User(new Remote_Users("testuser", ""));

    }

    @Test
    public void testStartConnection() throws IOException {
        Socket socket = handler.startConnection(pseudo);
        assertTrue(socket.isConnected());
    }

    @Test
    public void testIsEtablished_whenTrue() throws IOException {
        handler.startConnection(pseudo);
        boolean result = handler.isEtablished(pseudo);
        assertTrue(result);
    }

    @Test
    public void testIsEtablished_whenFalse() {
        boolean result = handler.isEtablished(pseudo);
        assertFalse(result);
    }

    @Test
    public void testGetInstance() {
        Handler result = Handler.getInstance();
        assertEquals(handler, result);
    }

}


