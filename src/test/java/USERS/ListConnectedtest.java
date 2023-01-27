

package USERS;

import org.junit.Assert;
import org.junit.Test;

public class ListConnectedtest {
    @Test
    public void testAdd_co() {
        List_Connected.add_co("John");
        Assert.assertTrue(List_Connected.exists("John"));
    }

    @Test
    public void testDelete_co() {
        List_Connected.add_co("John");
        List_Connected.delete_co("John");
        Assert.assertFalse(List_Connected.exists("John"));
    }

    @Test
    public void testExists() {
        List_Connected.add_co("John");
        Assert.assertTrue(List_Connected.exists("John"));
        Assert.assertFalse(List_Connected.exists("Jane"));
    }

    @Test
    public void testClear_list_co() {
        List_Connected.add_co("John");
        List_Connected.clear_list_co();
        Assert.assertFalse(List_Connected.exists("John"));
    }
}



