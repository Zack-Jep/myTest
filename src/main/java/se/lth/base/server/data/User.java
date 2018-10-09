package se.lth.base.server.data;

import java.security.Principal;
import java.util.List;

import se.lth.base.server.Config;

public class User implements Principal {

    public static User NONE = new User(0, Role.NONE, "-");
    
    private final FooDataAccess fooDao = new FooDataAccess(Config.instance().getDatabaseDriver());

    private final int id;
    private Role role;
    private final String username;
    private final String firstname;
    private final String lastname;
    private String email;
    private double rating;
    //private int nbrofrates;

    public User(int id, Role role, String username) {
        this.id = id;
        this.role = role;
        this.username = username;
        
        // Kanske i constructorn kanske på andra ställen
        firstname = null;
        lastname= null;
        
        
    }

    public Role getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return username;
    }
    
    public String getLastName() {
    	return lastname;
    }
    
    public String getFirstName() {
    	return firstname;
    }
    
    public double getRating() {
    	return rating;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public double ratingChange(double grade) {
    	//double s = rating * nbrofrates
    	//nbrofrates++;
    	//s += grade
    	//rating = s/nbrofrates
    	return rating;
    }
    
    public double adminSetRating(double grade, User user) {
    	if (role.clearanceFor(role.ADMIN)) {
    	//user.nbrofrates = 1;
    	user.rating = grade;
    	return  user.rating;
    	}
    	return -1;
    }
    
    public boolean adminSetRole(Role role, User user) {
    	if (role.clearanceFor(role.ADMIN)) {
    		user.role = role;
    		return true;
    	}
    	return false;
    }
    
    public List<Foo> getAllTrips() {
    	return fooDao.getUsersFoo(id);
    }
    
    public double getCO2s() {
    	List<Foo> trips = fooDao.getUsersFoo(id);
    	double CO2 =0;
    	 for (int i = 0; trips.size() > i; i++){
    		// CO2 += trips.get(i).CO2calc; CO2calc not implemented
    	}
    	 return CO2;
    }
    
    public boolean uppdateEmail(String s) {
    	email = s;
    	return true;
    }
    
}
