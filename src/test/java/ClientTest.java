import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientTest {

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

    @Test
    public void clear_emptiesAllClientsFromArrayList_0() {
        Client myClient = new Client("My hair style",1);
        assertEquals(Client.all().size(), 0);
    }

    @Test
    public void getId_clientsInstantiateWithAnID_1() {
        Client myClient = new Client("My hair style",1);
        myClient.save();
        assertTrue(myClient.getId()>0);
    }

    @Test
    public void equals_returnsTrueIfDescriptionsAreTheSame(){
        Client firstClient = new Client ("buy more braids",1);
        Client secondClient = new Client("buy more braids",1);
        assertTrue(firstClient.equals(secondClient));
    }

    @Test
    public void find_returnsClientWithSameId_secondClient() {
        Client firstClient = new Client("My hair style",1);
        firstClient.save();
        Client secondClient = new Client("Buy more braids",2);
        secondClient.save();
        assertEquals(Client.find(secondClient.getId()), secondClient);
    }

    public void save_assignsIdToObject(){
        Client myClient = new Client("my hair styles",1);
        myClient.save();
        Client savedClient = Client.all().get(0);
        assertEquals(myClient.getId(), savedClient.getId());
    }
    @Test
    public void update_updatesClientDescription_true() {
        Client myClient = new Client("My hair styles", 1);
        myClient.save();
        myClient.update("More braids please");
        assertEquals("More braids please", Client.find(myClient.getId()).getDescription());
    }

    @Test
    public void delete_deletesClient_true() {
        Client myClient = new Client("My hair style", 1);
        myClient.save();
        int myClientId = myClient.getId();
        myClient.delete();
        assertEquals(null, Client.find(myClientId));
    }


    @Test
    public void getTasks_retrievesAllClientsFromDatabase_clientList() {
        Stylist stylist = new Stylist("Jacky");
        stylist.save();
        Client client = new Client("faux locks", stylist.getId());
        client.save();
        Client client1 = new Client("braids", stylist.getId());
        client1.save();
        Client[] clients = new Client[] { client, client1 };
        assertTrue(stylist.getClients().containsAll(Arrays.asList(clients)));
    }
}
