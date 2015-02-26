package sim.tv2.PremierLeague.Util;

public class Util {

	/**
	 * Method to get the first name of a player
	 */
	public static String getFirstName(String name){
		String[] nameArray = name.split("_");
		if(nameArray.length >= 1){
			return nameArray[0];
		}
		else return null;
	}
	
	/**
	 * Check if a string is an integer
	 * @param s
	 * @return
	 */
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}

	/**
	 * Method to get the last name of a player
	 */
	public static String getLastName(String name){
		String[] nameArray = name.split("_");
		if(nameArray.length > 2){
			return nameArray[1] + nameArray[2];
		} else if (nameArray.length == 2) {
			return nameArray[1];
		} else if (nameArray.length < 2){
			return nameArray[0];
		}
		return null;
	}


	}





