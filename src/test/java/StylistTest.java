import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
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

    @Test
    public void save_assignsIdToObject() {
        Stylist myStylist = new Stylist("New hair style");
        myStylist.save();
        Stylist savedStylist = Stylist.all().get(0);
        assertEquals(myStylist.getId(), savedStylist.getId());

    }

    @Test
    public void all_returnsAllInstancesOfStylist_true() {
        Stylist firstStylist = new Stylist("hair");
        firstStylist.save();
        Stylist secondStylist = new Stylist("salon");
        secondStylist.save();
        assertEquals(true, Stylist.all().get(0).equals(firstStylist));
        assertEquals(true, Stylist.all().get(1).equals(secondStylist));
    }

    @Test
    public void getId_stylistsInstantiateWithAnId_1() {
        Stylist  testStylist  = new Stylist ("hair");
        testStylist .save();
        assertTrue( testStylist .getId()>0);
    }

    @Test
    public void find_returnsStylistWithSameId_secondStylist() {
        Stylist firstStylist = new Stylist("Hair");
        firstStylist.save();
        Stylist secondStylist = new Stylist("Work");
        secondStylist.save();
        assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
    }

}
