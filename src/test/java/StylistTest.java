import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class StylistTest {

    @Before
    public void setUp(){
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "kos","210");
    }

    @After
    public void tearDown(){
        try(Connection con = DB.sql2o.open()){
            String deleteClientQuery = "DELETE FROM clients*;";
            String deleteStylistQuery = "DELETE FROM stylists *;";
            con.createQuery(deleteClientQuery).executeUpdate();
            con.createQuery(deleteStylistQuery).executeUpdate();
        }
    }


    @Test
    public void getClients_retrievesALlClientsFromDatabase_clientsList() {
        Stylist myStylist = new Stylist("Hair day");
        myStylist.save();
        Client firstClient = new Client("My hair style", myStylist.getId());
        firstClient.save();
        Client secondClient = new Client("Get more braids", myStylist.getId());
        secondClient.save();
        Client[] clients = new Client[] { firstClient, secondClient };
        assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
    }

    @Test
    public void equals_returnsTrueIfNamesAreTheSame() {
        Stylist firstStylist = new Stylist("New braids");
        Stylist secondStylist = new Stylist("New braids");
        assertTrue(firstStylist.equals(secondStylist));
    }

    @Test
    public void save_savesIntoDatabase_tru(){
        Stylist myStylist = new Stylist("New braids");
        myStylist.save();
        assertTrue(Stylist.all().get(0).equals(myStylist));
    }
}
