package sim.tv2.PremierLeague.Application;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import sim.tv2.PremierLeague.Gui.Gui;
import sim.tv2.PremierLeague.Parser.PLParser;
import sim.tv2.PremierLeague.Player.Player;
/**
 * Class used to represent the logic of the application
 * @author Sindre Moldeklev
 * @version 0.0.1
 */
public class PremierLeagueApp{

	private static Gui gui;

	/**
	 * Main method for the application
	 * @param args
	 */
	public static void main(String[] args) {
		PLParser p = new PLParser(733);

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				gui = new Gui(p);
			}
		});

	}

	/**
	 * Method to populate all the players
	 * @param players
	 */
	public static void populateModel(List<Player> players){
		Collections.sort(players, new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		for(Player player : players){
			gui.getPlayerModel().addElement(player);
		}
	}
	
	/**
	 * Method to read the teams from a file
	 * @param fileName
	 * @return teams
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Integer> loadTeams(String fileName){
		FileInputStream inputStream = null;
		ObjectInputStream objectInput = null;
		Map<String, Integer> teams = new HashMap<>();
		try {
			inputStream = new FileInputStream(fileName);
			objectInput = new ObjectInputStream(inputStream);
			teams = (Map<String, Integer>)objectInput.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				objectInput.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
		return teams;
	}
	
	/**
	 * Method to save the hashMap of teams
	 */
	public static void saveTeams(){
		FileOutputStream outputStream = null;
		ObjectOutputStream objectOutput = null;
		Map<String, Integer> teams = gui.getTeamsMap();
		
		try {
			outputStream = new FileOutputStream("data.ser");
			objectOutput = new ObjectOutputStream(outputStream);
			objectOutput.writeObject(teams);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			try{
				outputStream.close();
				objectOutput.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Method to update the dropdown menu
	 * TODO Lag exceptions dersom man ikke kan opprette nytt lag
	 */
	public static void updateTeamDropDown(String team, Integer id){
		gui.getTeamsMap().put(team, id);
		gui.getComboBoxModel().addElement(team);
	}
	
	/**
	 * Method to remove a team from the dropdown
	 * @param team
	 * @return boolean
	 */
	public static boolean removeFromDropDown(String team){
		boolean found = false;
		for(int i = 0; i < gui.getComboBoxModel().getSize(); i++){
			if(gui.getComboBoxModel().getElementAt(i).equalsIgnoreCase(team)){
				gui.getTeamsMap().remove(team);
				gui.getComboBoxModel().removeElement(team);
				found = true;
			}
		}
		
		if(found){
			return true;
		} else return false;
		
	}

	/**
	 * Method to populate the list of missing players
	 * @param missingPlayers
	 */
	public static void populateMissingPlayers(List<Player> missingPlayers){
		for(Player missingPlayer : missingPlayers){
			gui.getMissingPlayerModel().addElement(missingPlayer);
		}
	}

	/**
	 * Method to populate the list of missing players
	 * @param presentPlayers
	 */
	public static void populatePresentPlayers(List<Player> presentPlayers){
		for(Player presentPlayer : presentPlayers){
			gui.getPresentPlayerModel().addElement(presentPlayer);
		}
	}

	/**
	 * Method to present the present players
	 */
	public static void setupPresentPlayer(){
		File directory = gui.getDirectory();
		Integer id = gui.getId();
		if(directory != null && id != null){
			gui.setParser(new PLParser(directory, id));
			populatePresentPlayers(gui.getParser().getPresentPlayers());
		}
	}

	/**
	 * Method to setup the missing players
	 */
	public static void setupMissingPlayers(){
		File directory = gui.getDirectory();
		Integer id = gui.getId();
		if(directory != null && id != null){
			gui.setParser(new PLParser(directory, id));
			populateMissingPlayers(gui.getParser().getNonPresentPlayers());
		}
	}


}
