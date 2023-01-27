



package Controller;

import Interface.LoginController;
import javafx.event.ActionEvent;
import javafx.scene.text.TextFlow;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class LoginControllerTest {

    @Test
    public void testIsValid() throws Exception {
        // Test valid username
        String validUsername = "abc123";
        assertTrue(LoginController.isValid(validUsername));

        // Test invalid username (too short)
        String invalidUsernameShort = "abc";
        assertFalse(LoginController.isValid(invalidUsernameShort));

        // Test invalid username (too long)
        String invalidUsernameLong = "abcdefghijklmnopqrstuvwxyz";
        assertFalse(LoginController.isValid(invalidUsernameLong));

        // Test invalid username (contains non-alphanumeric characters)
        String invalidUsernameSpecialChars = "abc!@#";
        assertFalse(LoginController.isValid(invalidUsernameSpecialChars));
    }

}


