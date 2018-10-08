package se.lth.base.server.data;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.List;

public class Trip {
	
	private LocalDateTime departureTime;
	private LocalDateTime expectedTime;
	private int capacity;
	private Point departureAdress;
	private final static int CO2PERKM = 120;
	private List<User> passengers;
	private double distance;
	
	/** Calculates the total CO2 saved on a trip.
	 * The used value for CO2PERKM is 120g/km, taken from http://ecoscore.be/en/info/ecoscore/co2
	 * @return Total CO2 saved
	 *  */
	public double co2Saved() {
		return (CO2PERKM * distance) * passengers.size();
	}
	
}
