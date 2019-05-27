import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    @Test
    public void Task_instantiatesCorrectly_true() {
        Client myClient = new Client("My hair style", 1);
        assertEquals(true, myClient instanceof Client);
    }
}
