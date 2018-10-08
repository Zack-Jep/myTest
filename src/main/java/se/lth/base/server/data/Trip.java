package se.lth.base.server.data;

import java.util.ArrayList;

/**
 * Trip class for managing trips
 *
 * @author Gustav Handmark, gustav.handmark@live.com
 */
public class Trip {
  private final int id;
  private final int driverId; // user who initiated the request -> to be selected as driver.
  private final ArrayList<Integer> passengers;
  private final int capacity;
  private final int ratingRequired;
  private final String departureTime;
  private final String expectedArrivalTime;
  private final ArrayList<Float> coordinates;
  private final String departureAddress;
  private final String destinationAddress;
  private final double distance;
  private final boolean completed;
  private final Float co2Saved;

  public Trip(
      int id,
      int driverId,
      ArrayList<Integer> passengers,
      int capacity,
      int ratingRequired,
      String departureTime,
      String expectedArrivalTime,
      ArrayList<Float> coordinates,
      String departureAddress,
      String destinationAddress,
      double distance,
      boolean completed) {
    this.id = id;
    this.driverId = driverId;
    this.passengers = passengers;
    this.capacity = capacity;
    this.ratingRequired = ratingRequired;
    this.departureTime = departureTime;
    this.expectedArrivalTime = expectedArrivalTime;
    this.coordinates = coordinates;
    this.departureAddress = departureAddress;
    this.destinationAddress = destinationAddress;
    this.distance = distance;
    this.completed = completed;
    this.co2Saved = 0f;
  }

  public int getTripId() {
    return id;
  }

  public int getDriver() {
    return driverId;
  }

  public int emptyCapacity() {
    return capacity - passengers.size();
  }

  public ArrayList<Integer> getPassengers() {
    return passengers;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getRatingRequired() {
    return ratingRequired;
  }

  public boolean isCompleted() {
    return completed;
  }

  /* methods to be implemented at a later stage.
  public List<Int> passengerRatings() {

  }

  //These methods are now implemented in the TripDataAccess as it makes 0 sense to implement them here.
  public boolean addPassenger(int userId) {
  }
  public boolean removePassenger(int userId) {
  }
  public boolean setCompleted() {
  }
  */

  /*
  Implemented by algorithm group:

  */
  public float co2Saved() {
    // add method function; or call private from algorithm group as:
    // this.co2Saved = calculateco2Saved()
    return co2Saved;
  }

  public String getDepartureTime() {
    return departureTime;
  }

  public String getExpectedArrivalTime() {
    return expectedArrivalTime;
  }

  public ArrayList<Float> getCoordinates() {
    return coordinates;
  }

  public String getDestinationAddress() {
    return destinationAddress;
  }

  public String getDepartureAddress() {
    return departureAddress;
  }

  public double getDistance() {
    return distance;
  }

  public String toString() {
    return "Trip ---- \n"
        + "id: "
        + id
        + "\nDriver: "
        + driverId
        + "\nPassengers: "
        + passengers.toString()
        + "\nCapacity: "
        + capacity
        + "\nratingRequired: "
        + ratingRequired
        + "\nDepartureTime: "
        + departureTime
        + "\nexpectedArrivalTime: "
        + expectedArrivalTime
        + "\ncoordinates; "
        + coordinates.toString();
  }
}
