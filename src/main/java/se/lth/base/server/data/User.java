package se.lth.base.server.data;

import java.security.Principal;

public class User implements Principal {

  public static User NONE = new User(0, Role.NONE, "-");

  private final int id;
  private final Role role;
  private final String username;
  private double rating;
  private int totalRatings = 0;

  public User(int id, Role role, String username) {
    this.id = id;
    this.role = role;
    this.username = username;
  }

  public Role getRole() {
    return role;
  }

  public int getId() {
    return id;
  }
  
  /**Updates the average rating of a user.
   * @param r Rating input from frontend. 
   *  */
  public void ratingChange(int r) {
	  rating = (totalRatings*rating + r)/(totalRatings + 1);
	  totalRatings++;
	  return;
  }
 

  @Override
  public String getName() {
    return username;
  }
}
