package se.lth.base.server.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import se.lth.base.server.rest.providers.JsonProvider;

public class SearchHandler {

	public static void main(String[] args) throws Exception{
		System.out.println(search("Spolegatan+15", 15, new TripDataAccess()));
	}
	
	/** Returns a list of Trips that are within the specified radius.
	 * @param radius The radius specified on frontend.
	 * @param startAdress Adress specified on frontend.
	 * @return List of Trips.
	 * */
	public static List<Trip> search(String startAdress, 
			double radius, TripDataAccess tripDao) {
		JsonObject json = (JsonObject) getJson(startAdress);
	    double userLat = ((JsonObject) json).get("lat").getAsDouble();
	    double userLon = ((JsonObject) json).get("lon").getAsDouble();
	    
	    
		List<Trip> allTrips = tripDao.getAllTrips();
		for(int i = 0; i < allTrips.size(); i++) {
			double lat = allTrips.get(i).getCoords().get(1);
			double lon = allTrips.get(i).getCoords().get(2);
			
			
		}
		return null;
	}
	
	private static JsonElement getJson(String adress) throws IOException {
		String urlString = "https://nominatim.openstreetmap.org/search?q="
				+ adress + "&format=json&addressdetails=1";
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.connect();

	    // Convert to a JSON object to print data
	    JsonParser jp = new JsonParser(); //from gson
	    JsonArray jsonArray = (JsonArray) jp.parse(new InputStreamReader((InputStream) conn.getContent())); 
	    return jsonArray.get(0);
	}

    /** Returns whether the driver's departure time lies between the passenger's time interval.
     * @param departureTime The driver's departure time.
     * @param intervalStart The interval's start time.
     * @param intervalEnd The interval's end time
     * @return true/false.
     * */
	private boolean timeInInterval(String departureTime, String intervalStart, String intervalEnd){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YY:MM:dd:HH:mm");
		LocalDateTime depTime = LocalDateTime.parse(departureTime, formatter);
		LocalDateTime intStart = LocalDateTime.parse(intervalStart, formatter);
		LocalDateTime intEnd = LocalDateTime.parse(intervalEnd, formatter);

		return (depTime.isAfter(intStart) && depTime.isBefore(intEnd));



    }
	
}

