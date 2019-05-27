import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void getCreatedAt_instantiatesWithCurrentTime_today() {
        Client myClient = new Client("My hair style", 1);
        assertEquals(LocalDateTime.now().getDayOfWeek(), myClient.getCreatedAt().getDayOfWeek());
    }

    @Test
    public void save_returnsTrueIfDescriptionsAretheSame() {
        Client myClient = new Client("My hair style",1);
        myClient.save();
        assertTrue(Client.all().get(0).equals(myClient));
    }

    @Test
    public void all_returnsAllInstancesOfClient_true() {
        Client firstClient = new Client("My hair style", 1);firstClient.save();
        Client secondClient = new Client("Buy new braids", 2);secondClient.save();
        assertEquals(true, Client.all().get(0).equals(firstClient));
        assertEquals(true, Client.all().get(1).equals(secondClient));
    }


}
