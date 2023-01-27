

package USERS;

import org.junit.Assert;
import org.junit.Test;

public class ListUsersTest {

    @Test
    public void testAddUser() {
        Remote_Users user1 = new Remote_Users("John", "192.168.1.1");
        List_USers.add_User(user1);
        Assert.assertEquals(user1, List_USers.get_user_from_pseudo("John"));
    }

    @Test
    public void testGetIPUser() {
        Remote_Users user1 = new Remote_Users("John", "192.168.1.1");
        List_USers.add_User(user1);
        Assert.assertEquals("192.168.1.1", List_USers.get_IP_user("John"));
    }

    @Test
    public void testGetPseudoUser() {
        Remote_Users user1 = new Remote_Users("John", "192.168.1.1");
        List_USers.add_User(user1);
        Assert.assertEquals("John", List_USers.get_pseudo_user("192.168.1.1"));
    }


}



