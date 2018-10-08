package se.lth.base.server.data;

import java.math.*;
import java.sql.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import se.lth.base.server.database.DataAccess;
import se.lth.base.server.database.Mapper;

public class TripDataAccess extends DataAccess<Trip> {
  private static class TripMapper implements Mapper<Trip> {
    @Override
    public Trip map(ResultSet resultSet) throws SQLException {
      Array passArr = resultSet.getArray("passengers");
      Array coordsArr = resultSet.getArray("coordinates");
      Object[] passArrJava = (Object[]) passArr.getArray();
      Object[] coordsArrJava = (Object[]) coordsArr.getArray();
      ArrayList<Integer> passengers = new ArrayList<>();
      ArrayList<Float> coordinates = new ArrayList<>();

      for (int i = 0; i < passArrJava.length; i++) {
        // System.out.println(passArrJava[i]);
        passengers.add((Integer) passArrJava[i]);
      }
      for (int i = 0; i < coordsArrJava.length; i++) {
        if (coordsArrJava[i] instanceof BigDecimal) {
          BigDecimal bg = (BigDecimal) coordsArrJava[i];
          coordinates.add((Float) bg.floatValue());
        } else {
          coordinates.add((Float) coordsArrJava[i]);
        }
      }

      return new Trip(
          resultSet.getInt("tripId"),
          resultSet.getInt("driverId"),
          passengers,
          resultSet.getInt("capacity"),
          resultSet.getInt("ratingRequired"),
          resultSet.getString("departureTime"),
          resultSet.getString("expectedArrivalTime"),
          coordinates,
          resultSet.getString("departureAddress"),
          resultSet.getString("destinationAddress"),
          resultSet.getDouble("distance"),
          resultSet.getBoolean("completed"));
    }
  }

  public TripDataAccess(String driverUrl) {
    super(driverUrl, new TripMapper());
  }

  /**
   * Add new trip payload connected to a user.
   *
   * @param userId user to add payload to.
   * @return the created trip object.
   */
  public Trip addTrip(
      int driverId,
      ArrayList<Integer> passengers,
      int capacity,
      int ratingRequired,
      String departureTime,
      String expectedArrivalTime,
      ArrayList<Float> coordinates,
      String destinationAddress,
      String departureAddress,
      double distance,
      boolean completed) {

    try {
      Connection conn = super.getConnection();
      Integer[] pass = passengers.toArray(new Integer[passengers.size()]);
      Float[] coords = coordinates.toArray(new Float[coordinates.size()]);
      Array passSql = conn.createArrayOf("INT", pass);
      Array coordsSql = conn.createArrayOf("FLOAT", coords);
      int tripId =
          insert(
              "INSERT INTO trip (driverId,passengers,capacity,ratingRequired,departureTime,expectedArrivalTime,coordinates,destinationAddress,departureAddress,distance,completed,co2Saved) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
              driverId,
              passSql,
              capacity,
              ratingRequired,
              departureTime,
              expectedArrivalTime,
              coordsSql,
              departureAddress,
              destinationAddress,
              distance,
              completed,
              0);
      // System.out.println(tripId);
      return new Trip(
          tripId,
          driverId,
          passengers,
          capacity,
          ratingRequired,
          departureTime,
          expectedArrivalTime,
          coordinates,
          departureAddress,
          destinationAddress,
          distance,
          completed);
    } catch (SQLException e) {
      System.out.println(e);
      return new Trip(
          0,
          driverId,
          passengers,
          capacity,
          ratingRequired,
          departureTime,
          expectedArrivalTime,
          coordinates,
          departureAddress,
          destinationAddress,
          distance,
          completed);
    }
  }

  public Trip getTripFromId(int tripId) {
    List<Trip> temp = query("SELECT * FROM trip WHERE tripId =?", tripId);
    return temp.get(0);
  }

  /** @return all trips */
  public List<Trip> getAllTrips() {
    return query("SELECT * FROM trip");
  }

  /**
   * Get all trips created by one user (driver)
   *
   * @param userId user to filter on.
   * @return created trips of the user
   */
  public List<Trip> getUsersTrips(int userId) {
    return query("SELECT * FROM trip WHERE driverId = ?", userId);
  }

  // @Todo
  /**
   * Deletes a trip
   *
   * @param userId user to match
   * @param tripId trip to delete
   * @return true if the trip was deleted
   */
  public boolean deleteTrip(int userId, int tripId) {
    return execute("DELETE FROM trip WHERE driverId= ? AND tripId = ?", userId, tripId) > 0;
  }

  /**
   * Adds a passenger to a trip
   *
   * @param userId user to add
   * @param tripId trip to add user to
   * @return true if the passenger was added
   */
  public boolean addPassenger(int userId, int tripId) {
    try {
      Connection conn = super.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT passengers FROM trip WHERE tripId =" + tripId);
      if (rs.next()) {
        Array passSql = rs.getArray("PASSENGERS");
        ArrayList<Integer> passArr = sqlToArrayList(passSql);
        passArr.add(userId);
        Array sqlAdd = arrayListToSqlArray(passArr);
        return execute("UPDATE trip SET passengers=? WHERE tripId=?", sqlAdd, tripId) > 0;
      }
    } catch (SQLException e) {
      System.out.println("SQLException: " + e);
      return false;
    }
    return false;
  }
  /**
   * @param userId user to remove
   * @param tripId trip to remove user from
   * @return true if passenger was removed
   */
  public boolean removePassenger(int userId, int tripId) {
    try {
      Connection conn = super.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT passengers FROM trip WHERE tripId =" + tripId);
      if (rs.next()) {
        Array passSql = rs.getArray("PASSENGERS");
        ArrayList<Integer> passArr = sqlToArrayList(passSql);
        if (passArr.contains(userId)) {
          passArr.remove(Integer.valueOf(userId));
        } else {
          return false;
        }
        Array sqlAdd = arrayListToSqlArray(passArr);
        return execute("UPDATE trip SET passengers=? WHERE tripId=?", sqlAdd, tripId) > 0;
      }
    } catch (SQLException e) {
      System.out.println("SQLException: " + e);
      return false;
    }
    return false;
  }
  /**
   * @param tripId trip which is supposed to be set as completed
   * @return true if trip was set as completed.
   */
  public boolean setTripCompleted(int tripId) {
    Trip t = getTripFromId(tripId);
    // Implement some timechecks, or add a scheduler to the database.
    return execute("UPDATE trip SET completed = TRUE WHERE tripId=?", tripId) > 0;
  }

  /**
   * @param tripId trip to calculate saved co2 emissions from
   * @return amount of saved co2 emissions per person or 0, if the trip is not yet completed.
   */
  public Float calculateCo2Saved(int tripId) {
    Trip t = getTripFromId(tripId);
    if (t.isCompleted()) {
      float co2Saved = t.co2Saved();
      execute("UPDATE trip SET co2Saved = ? WHERE tripId=?", co2Saved, tripId);
      return co2Saved;
    } else {
      return null;
    }
  }

  private Array arrayListToSqlArray(ArrayList<Integer> arrL) {
    try {
      Connection conn = super.getConnection();
      Integer[] temp = arrL.toArray(new Integer[arrL.size()]);
      Array sql = conn.createArrayOf("INT", temp);
      return sql;
    } catch (SQLException e) {
      System.out.println("SQLException: " + e);
      return null;
    }
  }

  private ArrayList<Integer> sqlToArrayList(Array sql) {
    try {
      Object[] javaArray = (Object[]) sql.getArray();
      ArrayList<Integer> temp = new ArrayList<Integer>();
      for (int i = 0; i < javaArray.length; i++) {
        temp.add((Integer) javaArray[i]);
      }
      return temp;
    } catch (SQLException e) {
      return null;
    }
  }
}
