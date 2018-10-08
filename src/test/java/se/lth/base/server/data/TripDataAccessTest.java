package se.lth.base.server.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import se.lth.base.server.Config;
import se.lth.base.server.database.BaseDataAccessTest;

/** @author Rasmus Ros, rasmus.ros@cs.lth.se */
public class TripDataAccessTest extends BaseDataAccessTest {

  private TripDataAccess tripDao = new TripDataAccess(Config.instance().getDatabaseDriver());
  private ArrayList<Integer> passengers = new ArrayList<Integer>();
  private ArrayList<Float> coordinates = new ArrayList<Float>();

  // 1,1,(4,5,6),5,3,'2018-10-08T08:00','2018-10-08T08:45',(55.7029296,13.1929449,55.6052931,13.0001566),'Lund,
  // Sverige','Malmö, Sverige',22.5,FALSE)
  @Test
  public void addTrip() {
    passengers.add(3);
    passengers.add(4);
    passengers.add(5);
    coordinates.add(55.7029296f);
    coordinates.add(13.1929449f);
    coordinates.add(55.6052931f);
    coordinates.add(13.0001566f);

    Trip data =
        tripDao.addTrip(
            TEST.getId(),
            passengers,
            5,
            3,
            "2018-10-8T08:00",
            "2018-10-8T08:45",
            coordinates,
            "Malmö, Sverige",
            "Lund, Sverige",
            22.5,
            false);
    assertEquals(TEST.getId(), data.getDriver());
    assertTrue(tripDao.getTripFromId(3) != null);
    // tripDao.getAllTrips().forEach((trip) -> System.out.println(trip));
  }

  // two trips added by default
  @Test
  public void getAllTrips() {
    /*
     * tripDao.addTrip( TEST.getId(), "2018-10-8T08:00", "2018-10-8T08:45",
     * "Malmö, Sverige", "Lund, Sverige", 22.5, false); assertEquals(3,
     * tripDao.getAllTrip().size()); tripDao.addTrip( ADMIN.getId(),
     * "2018-10-8T08:00", "2018-10-8T08:45", "Malmö, Sverige", "Lund, Sverige",
     * 22.5, false); assertEquals(4, tripDao.getAllTrip().size()); assertEquals(2L,
     * tripDao.getAllTrip().stream().map(Trip::getDriver).distinct().count());
     */

  }

  @Test
  public void getUsersTrips() {
    assertEquals(2, tripDao.getUsersTrips(1).size());
  }

  @Test
  public void deleteTrip() {
    assertEquals(2, tripDao.getAllTrips().size());
    passengers.add(3);
    passengers.add(4);
    passengers.add(5);
    coordinates.add(55.7029296f);
    coordinates.add(13.1929449f);
    coordinates.add(55.6052931f);
    coordinates.add(13.0001566f);
    tripDao.addTrip(
        TEST.getId(),
        passengers,
        5,
        3,
        "2018-10-8T08:00",
        "2018-10-8T08:45",
        coordinates,
        "Malmö, Sverige",
        "Lund, Sverige",
        22.5,
        false);

    List<Trip> temp = tripDao.getAllTrips();
    assertEquals(3, temp.size());
    tripDao.deleteTrip(TEST.getId(), temp.get(temp.size() - 1).getTripId());
    assertEquals(2, tripDao.getAllTrips().size());
  }

  @Test
  public void getTripFromId() {
    Trip t = tripDao.getTripFromId(1);
    assertEquals(1, t.getTripId());
  }

  @Test
  public void removePassengerFromTrip() {
    Trip t = tripDao.getTripFromId(1);
    assertTrue(t.getPassengers().contains(4));
    tripDao.removePassenger(4, 1);
    Trip t2 = tripDao.getTripFromId(1);
    assertFalse(t2.getPassengers().contains(4));
  }

  @Test
  public void addPassengerToTrip() {
    Trip t = tripDao.getTripFromId(1);
    assertFalse(t.getPassengers().contains(2));
    tripDao.addPassenger(2, 1);
    Trip t1 = tripDao.getTripFromId(1);
    assertTrue(t1.getPassengers().contains(2));
    /*
     * ResultSet rs = tripDao.addPassenger(1,1); if(rs != null) { try {
     * ResultSetMetaData rsmd = rs.getMetaData(); String name =
     * rsmd.getColumnLabel(1); while(rs.next()) {
     * System.out.println(rs.getArray("PASSENGERS")); } /* Array passArr =
     * rs.getInt("passengers"); Object[] passArrJava = (Object[])
     * passArr.getArray(); for(int i=0;i<passArrJava.length;i++){
     * System.out.println(passArrJava[i]); } / } catch(Exception e) {
     * System.out.println(e); } }
     */

  }
}
