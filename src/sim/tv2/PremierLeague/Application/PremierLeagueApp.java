package sim.tv2.PremierLeague.Application;



import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.SwingUtilities;

import sim.tv2.PremierLeague.Gui.Gui;
import sim.tv2.PremierLeague.Parser.PLParser;
import sim.tv2.PremierLeague.Player.Player;

public class PremierLeagueApp{

	private static Gui gui;

	public static void main(String[] args) {
//		File dir = new File("TestMappe");
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
	 * Method to populate the list of missing players
	 */
	public static void populateMissingPlayers(List<Player> missingPlayers){
		for(Player missingPlayer : missingPlayers){
			gui.getMissingPlayerModel().addElement(missingPlayer);
		}
	}
	
	/**
	 * Method to populate the list of missing players
	 */
	public static void populatePresentPlayers(List<Player> presentPlayers){
		for(Player presentPlayer : presentPlayers){
			gui.getPresentPlayerModel().addElement(presentPlayer);
		}
	}
	
	public static void setupPresentPlayer(){
		File directory = gui.getDirectory();
		Integer id = gui.getId();
		if(directory != null && id != null){
			gui.setParser(new PLParser(directory, id));
			populatePresentPlayers(gui.getParser().getPresentPlayers());
		}
	}
	
	public static void setupMissingPlayers(){
		File directory = gui.getDirectory();
		Integer id = gui.getId();
		if(directory != null && id != null){
			gui.setParser(new PLParser(directory, id));
			populateMissingPlayers(gui.getParser().getNonPresentPlayers());
		}
	}


}
