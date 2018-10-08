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
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import se.lth.base.server.rest.providers.JsonProvider;

public class SearchHandler {

	private static final double ENDRADIUS = 10;
	
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
	
	
	/** Returns true if the User's rating qualifies for the trip.
	 * @param A trip t
	 * @return true or false
	 */
	private boolean compareRating(Trip t) {
		return User.getRating >= t.getRatingRequired;
	}
	
	/** Returns true if there is capacity available for the trip.
	 * @param A trip t
	 * @return true or false
	 */
	private boolean checkCapacity(Trip t) {
		return t.emptyCapacity > 0; 
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
	
	private boolean compareStart(double lat1, double lon1, double lat2, double lon2, double r) {
		return (r > haversine(lat1, lon1, lat2, lon2));
	}
	
	private boolean compareEnd(double lat1, double lon1, double lat2, double lon2, double r)
	
	private double haversine(double lat1, double lon1, double lat2, double lon2) {
		int R = 6371;
		double φ1 = Math.toRadians(lat1);
		double φ2 = Math.toRadians(lat2);
		double Δφ = Math.toRadians(lat2 - lat1);
		double Δλ = Math.toRadians(lon2 - lon1);

		double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
		        Math.cos(φ1) * Math.cos(φ2) *
		        Math.sin(Δλ/2) * Math.sin(Δλ/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		return R * c;
	}
	
}

