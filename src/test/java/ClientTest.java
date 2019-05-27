import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    @Test
    public void Client_instantiatesCorrectly_true() {
        Client myClient = new Client("My hair style", 1);
        assertEquals(true, myClient instanceof Client);
    }

    @Test
    public void Client_instantiatesWithDescription_String() {
        Client myClient = new Client("My hair style", 1);
        assertEquals("My hair style", myClient.getDescription());
    }

    @Test
    public void isCompleted_isFalseAfterInstantiation_false() {
        Client myClient = new Client("My hair style",1);
        assertEquals(false, myClient.isCompleted());
    }
}
