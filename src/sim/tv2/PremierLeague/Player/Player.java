package sim.tv2.PremierLeague.Player;

import sim.tv2.PremierLeague.Util.Util;

/**
 * Class used to represent a player for a team
 * @author Sindre Moldeklev
 * @version 0.0.1
 *
 */

public class Player {
	
	private String firstName;
	private String lastName;
	private boolean isPresent;
	
	/**
	 * Constructor for the Player class
	 * @param name
	 */
	public Player(String name){
		this.setFirstName(Util.getFirstName(name));
		this.setLastName(Util.getLastName(name));
		setPresent(false);
	}
	
	/**
	 * Method used to represent the player
	 */
	public String toString(){
		if(this.firstName.equals(this.lastName)){
			return this.lastName;
		} else {
			return this.firstName + " " + this.lastName;
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}

}

