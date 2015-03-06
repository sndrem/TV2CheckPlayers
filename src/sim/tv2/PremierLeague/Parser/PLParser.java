package sim.tv2.PremierLeague.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sim.tv2.PremierLeague.Player.Player;

/**
 * Class used to represent the the parser to get data from Alt om Fotball
 * @author Sindre Moldeklev
 * @version 0.0.1
 *
 */
public class PLParser{

	private List<Player> players;
	private List<String> directoryPlayerNames;

	/**
	 * Constructor for the PLParser class
	 */
	public PLParser(File directory, int id){
		setPlayers(new ArrayList<Player>());
		setDirectoryPlayerNames(new ArrayList<String>());
		readDirectory(directory);
		getPlayerNames(id);
		for(String fileName : directoryPlayerNames){
			setupSquad(fileName);
		}
	}
	
	/**
	 * Constructor 2 for the PLParser
	 * @param id
	 */
	public PLParser(int id){
		setPlayers(new ArrayList<Player>());
		setDirectoryPlayerNames(new ArrayList<String>());
		getPlayerNames(id);
	}

	/**
	 * Method to read a file of players and extract the names
	 * The file must be a text file with tab separated columns
	 * @param playerFile	A textfile with tab separated columns
	 * @deprecated
	 */
	public void readFromTextFile(File playerFile){
		BufferedReader brReader = null;
		FileReader fileReader = null;
		String line = "";

		List<String> list = new ArrayList<>();

		try{
			fileReader = new FileReader(playerFile);
			brReader = new BufferedReader(fileReader);

			while((line = brReader.readLine()) != null){
				String[] tempPlayers = line.split("\t");
				list.add(tempPlayers[0]);	
				//				getPlayers().add(new Player(tempPlayers[1]));
			}
			//			getPlayers().add(new Player(Util.getManager(list)));

			//			Util.removeManagerText(this.players);

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Method to get the players names from the internet
	 * @param id from Alt om Fotball
	 */
	public void getPlayerNames(int id){
		try {
			Document root = Jsoup.connect("http://www.altomfotball.no/element.do?cmd=team&teamId=" + id).get();
			Element table = root.getElementById("sd_players_table");
			Elements names = (Elements) table.getElementsByTag("a");
			
			for(Element name : names){
				players.add(new Player(name.html().replaceAll("&nbsp;", "_")));
			}

		} catch (NumberFormatException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e){
			e.printStackTrace();
		}

	} 


/**
 * Method to read in all fileNames from a directory
 * @param directory
 */
public void readDirectory(File directory){
	File[] files = directory.listFiles();
	for(File file : files){
		directoryPlayerNames.add(FilenameUtils.removeExtension(file.getName().toLowerCase().substring(0,1).toUpperCase() + file.getName().substring(1).toLowerCase()));
	}
}

/**
 * Method to check if a player is in a directory as a file
 * @param fileName
 */
public void setupSquad(String fileName){
	for(Player player : players){
		if(player.getLastName() != null && player.getLastName().equalsIgnoreCase(fileName)){
			player.setPresent(true);
		} else if (player.getFirstName() != null && player.getFirstName().equalsIgnoreCase(fileName)){
			player.setPresent(true);
		} 
	}
}

/**
 * Method to get all the present players
 * @return presentPlayers
 */
public List<Player> getPresentPlayers(){
	List<Player> presentPlayers = new ArrayList<>();
	for(Player player : players){
		if(player.isPresent()){
			presentPlayers.add(player);
		}
	}
	return presentPlayers;
}

/**
 * Method to get all the non present players
 * @return nonPresentPlayers
 */
public List<Player> getNonPresentPlayers(){
	List<Player> nonPresentPlayers = new ArrayList<>();
	for(Player player : players){
		if(!player.isPresent()){
			nonPresentPlayers.add(player);
		}
	}
	return nonPresentPlayers;
}



/**
 * Method to list out the present and not present players
 * @deprecated
 */
public void getWholeTeam(){
	for(Player player : players){
		if(player.isPresent()){
			System.out.println(player + " is present.");
		} else {
			System.out.println(player + " is not present.");
		}
	}
}

public List<Player> getPlayers() {
	return players;
}

public void setPlayers(List<Player> players) {
	this.players = players;
}

public List<String> getDirectoryPlayerNames() {
	return directoryPlayerNames;
}

public void setDirectoryPlayerNames(List<String> directoryPlayerNames) {
	this.directoryPlayerNames = directoryPlayerNames;
}
}
