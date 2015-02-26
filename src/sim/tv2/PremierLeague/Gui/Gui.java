package sim.tv2.PremierLeague.Gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import sim.tv2.PremierLeague.Events.Event;
import sim.tv2.PremierLeague.Parser.PLParser;
import sim.tv2.PremierLeague.Player.Player;

/**
 * Class representing the graphical user interface
 * @author Sindre Moldeklev
 *TODO Fiks bedre kommunikasjon med brukeren ved hjelp av labels
 */
public class Gui extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3459732324107741611L;
	private PLParser parser;
	private JComboBox<String> teamComboBox;
	private JPanel topPanel;
	private DefaultListModel<Player> playerModel;
	private JPanel centerPanel;
	private JList<Player> squadList;
	private Map<String, Integer> teamsMap;
	private JButton directoryButton;
	private JScrollPane playerListScrollPane;
	private DefaultListModel<Player> missingPlayerModel;
	private JList<Player> missingPlayers;
	private JScrollPane missingPlayerScrollPane;
	private JButton missingPlayersButton;
	private File directory;
	private int id;
	private JLabel numberOfPlayersLabel;
	private JLabel directoryLabel;
	private JLabel missingPlayersLabel;
	private JPanel altOmFotballInfoPanel;
	private DefaultListModel<Player> presentPlayerModel;
	private JList<Player> presentPlayerList;
	private JScrollPane presentPlayerScrollPane;
	private JButton seePresentPlayersButton;
	private JLabel sameLastNameLabel;

	/**
	 * Constructor for the Gui
	 * @param parser
	 */
	public Gui(PLParser parser){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		this.setParser(parser);
		setupFrame();
		setupTopPanel();
		setupCenterPanel();
		setupMenuBar();
		this.setVisible(true);
	}
	
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Meny");
		JMenu about = new JMenu("Om");
		menuBar.add(menu);
		menuBar.add(about);
		
		this.setJMenuBar(menuBar);
		
		
	}

	/**
	 * Method to set up the JFrame of the application
	 */
	private void setupFrame(){
		this.setBounds(new Rectangle(1000,800));
		this.setLayout(new BorderLayout());
		this.setTitle("TV 2s spillersjekker");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerFrame();
	}
	
	/**
	 * Method to setup the top panel
	 */
	private void setupTopPanel(){
		topPanel = new JPanel();
		setTeamsMap(new HashMap<>());
		getTeamsMap().put("Chelsea", 723);
		getTeamsMap().put("Man. City", 722);
		getTeamsMap().put("Arsenal", 738);
		getTeamsMap().put("Man. United", 735);
		getTeamsMap().put("Southampton", 728);
		getTeamsMap().put("Liverpool", 733);
		getTeamsMap().put("Tottenham", 740);
		getTeamsMap().put("West Ham", 724);
		getTeamsMap().put("Swansea", 791);
		getTeamsMap().put("Stoke", 794);
		getTeamsMap().put("Newcastle", 736);
		getTeamsMap().put("Everton", 730);
		getTeamsMap().put("Crystal P.", 767);
		getTeamsMap().put("West Brom.", 783);
		getTeamsMap().put("Hull City", 834);
		getTeamsMap().put("Sunderland", 737);
		getTeamsMap().put("QPR", 768);
		getTeamsMap().put("Burnley", 762);
		getTeamsMap().put("Aston Villa", 732);
		getTeamsMap().put("Leicester", 731);
		
		List<String> teamList = new ArrayList<>();
		for(String team : getTeamsMap().keySet()){
			teamList.add(team);
		}
		String[] teamsArray = new String[getTeamsMap().size()];
		teamsArray = teamList.toArray(teamsArray);
		setDirectoryButton(new JButton("Velg mappe"));
		teamComboBox = new JComboBox<>(teamsArray);
		teamComboBox.addActionListener(new Event(this));
		getDirectoryButton().addActionListener(new Event(this));
		topPanel.add(getDirectoryButton());
		topPanel.add(new JLabel("Velg lag"));
		topPanel.add(teamComboBox);
		missingPlayersButton = new JButton("Finn manglende spillere");
		setSeePresentPlayersButton(new JButton("Se tilgjengelige spillere"));
		getSeePresentPlayersButton().addActionListener(new Event(this));
		getSeePresentPlayersButton().setEnabled(false);
		missingPlayersButton.addActionListener(new Event(this));
		missingPlayersButton.setEnabled(false);
		topPanel.add(missingPlayersButton);
		topPanel.add(getSeePresentPlayersButton());
		this.add(topPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Method to add the center panel of the frame
	 */
	private void setupCenterPanel(){
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2,5));
		
		altOmFotballInfoPanel = new JPanel();
		altOmFotballInfoPanel.setLayout(new GridLayout(3,0));
		directoryLabel = new JLabel("Valgt mappe: ");
		altOmFotballInfoPanel.add(directoryLabel);
		numberOfPlayersLabel = new JLabel("Antall spillere: ");
		altOmFotballInfoPanel.add(numberOfPlayersLabel);
		centerPanel.add(altOmFotballInfoPanel);
		
		playerModel = new DefaultListModel<>();
		squadList = new JList<>(playerModel);
		playerListScrollPane = new JScrollPane(squadList);
		playerListScrollPane.setBorder(new TitledBorder("Hele laget"));
		centerPanel.add(playerListScrollPane);
		
		missingPlayerModel = new DefaultListModel<Player>();
		missingPlayers = new JList<>(missingPlayerModel);
		missingPlayerScrollPane = new JScrollPane(missingPlayers);
		missingPlayerScrollPane.setBorder(new TitledBorder("Spillere som mangler"));
		
		presentPlayerModel = new DefaultListModel<>();
		presentPlayerList = new JList<>(presentPlayerModel);
		presentPlayerScrollPane = new JScrollPane(presentPlayerList);
		presentPlayerScrollPane.setBorder(new TitledBorder("Tilgjengelige spillere"));
		centerPanel.add(presentPlayerScrollPane);
		
		setMissingPlayersLabel(new JLabel("Spillere som mangler: "));
		centerPanel.add(getMissingPlayersLabel());
		centerPanel.add(missingPlayerScrollPane);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	
	
	/**
	 * Method to center the frame
	 */
	private void centerFrame(){
		Toolkit tk = Toolkit.getDefaultToolkit();

		Dimension dim = tk.getScreenSize();

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);

		this.setLocation(xPos, yPos);
	}

	/**
	 * @return the parser
	 */
	public PLParser getParser() {
		return parser;
	}

	/**
	 * @param parser the parser to set
	 */
	public void setParser(PLParser parser) {
		this.parser = parser;
	}

	public Map<String, Integer> getTeamsMap() {
		return teamsMap;
	}

	public void setTeamsMap(Map<String, Integer> teamsMap) {
		this.teamsMap = teamsMap;
	}

	/**
	 * @return the teamComboBox
	 */
	public JComboBox<String> getTeamComboBox() {
		return teamComboBox;
	}

	/**
	 * @param teamComboBox the teamComboBox to set
	 */
	public void setTeamComboBox(JComboBox<String> teamComboBox) {
		this.teamComboBox = teamComboBox;
	}

	/**
	 * @return the topPanel
	 */
	public JPanel getTopPanel() {
		return topPanel;
	}

	/**
	 * @param topPanel the topPanel to set
	 */
	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}

	/**
	 * @return the playerModel
	 */
	public DefaultListModel<Player> getPlayerModel() {
		return playerModel;
	}

	/**
	 * @param playerModel the playerModel to set
	 */
	public void setPlayerModel(DefaultListModel<Player> playerModel) {
		this.playerModel = playerModel;
	}

	/**
	 * @return the centerPanel
	 */
	public JPanel getCenterPanel() {
		return centerPanel;
	}

	/**
	 * @param centerPanel the centerPanel to set
	 */
	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	/**
	 * @return the squadList
	 */
	public JList<Player> getSquadList() {
		return squadList;
	}

	/**
	 * @param squadList the squadList to set
	 */
	public void setSquadList(JList<Player> squadList) {
		this.squadList = squadList;
	}

	public JButton getDirectoryButton() {
		return directoryButton;
	}

	public void setDirectoryButton(JButton directoryButton) {
		this.directoryButton = directoryButton;
	}

	/**
	 * @return the playerListScrollPane
	 */
	public JScrollPane getPlayerListScrollPane() {
		return playerListScrollPane;
	}

	/**
	 * @param playerListScrollPane the playerListScrollPane to set
	 */
	public void setPlayerListScrollPane(JScrollPane playerListScrollPane) {
		this.playerListScrollPane = playerListScrollPane;
	}

	/**
	 * @return the missingPlayerModel
	 */
	public DefaultListModel<Player> getMissingPlayerModel() {
		return missingPlayerModel;
	}

	/**
	 * @param missingPlayerModel the missingPlayerModel to set
	 */
	public void setMissingPlayerModel(DefaultListModel<Player> missingPlayerModel) {
		this.missingPlayerModel = missingPlayerModel;
	}

	/**
	 * @return the missingPlayers
	 */
	public JList<Player> getMissingPlayers() {
		return missingPlayers;
	}

	/**
	 * @param missingPlayers the missingPlayers to set
	 */
	public void setMissingPlayers(JList<Player> missingPlayers) {
		this.missingPlayers = missingPlayers;
	}

	/**
	 * @return the missingPlayerScrollPane
	 */
	public JScrollPane getMissingPlayerScrollPane() {
		return missingPlayerScrollPane;
	}

	/**
	 * @param missingPlayerScrollPane the missingPlayerScrollPane to set
	 */
	public void setMissingPlayerScrollPane(JScrollPane missingPlayerScrollPane) {
		this.missingPlayerScrollPane = missingPlayerScrollPane;
	}

	/**
	 * @return the missingPlayersButton
	 */
	public JButton getMissingPlayersButton() {
		return missingPlayersButton;
	}

	/**
	 * @param missingPlayersButton the missingPlayersButton to set
	 */
	public void setMissingPlayersButton(JButton missingPlayersButton) {
		this.missingPlayersButton = missingPlayersButton;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the numberOfPlayersLabel
	 */
	public JLabel getNumberOfPlayersLabel() {
		return numberOfPlayersLabel;
	}

	/**
	 * @param numberOfPlayersLabel the numberOfPlayersLabel to set
	 */
	public void setNumberOfPlayersLabel(JLabel numberOfPlayersLabel) {
		this.numberOfPlayersLabel = numberOfPlayersLabel;
	}

	/**
	 * @return the directoryLabel
	 */
	public JLabel getDirectoryLabel() {
		return directoryLabel;
	}

	/**
	 * @param directoryLabel the directoryLabel to set
	 */
	public void setDirectoryLabel(JLabel directoryLabel) {
		this.directoryLabel = directoryLabel;
	}

	public JLabel getMissingPlayersLabel() {
		return missingPlayersLabel;
	}

	public void setMissingPlayersLabel(JLabel missingPlayersLabel) {
		this.missingPlayersLabel = missingPlayersLabel;
	}

	/**
	 * @return the altOmFotballInfoPanel
	 */
	public JPanel getAltOmFotballInfoPanel() {
		return altOmFotballInfoPanel;
	}

	/**
	 * @param altOmFotballInfoPanel the altOmFotballInfoPanel to set
	 */
	public void setAltOmFotballInfoPanel(JPanel altOmFotballInfoPanel) {
		this.altOmFotballInfoPanel = altOmFotballInfoPanel;
	}

	/**
	 * @return the presentPlayerModel
	 */
	public DefaultListModel<Player> getPresentPlayerModel() {
		return presentPlayerModel;
	}

	/**
	 * @param presentPlayerModel the presentPlayerModel to set
	 */
	public void setPresentPlayerModel(DefaultListModel<Player> presentPlayerModel) {
		this.presentPlayerModel = presentPlayerModel;
	}

	/**
	 * @return the presentPlayerList
	 */
	public JList<Player> getPresentPlayerList() {
		return presentPlayerList;
	}

	/**
	 * @param presentPlayerList the presentPlayerList to set
	 */
	public void setPresentPlayerList(JList<Player> presentPlayerList) {
		this.presentPlayerList = presentPlayerList;
	}

	/**
	 * @return the presentPlayerScrollPane
	 */
	public JScrollPane getPresentPlayerScrollPane() {
		return presentPlayerScrollPane;
	}

	/**
	 * @param presentPlayerScrollPane the presentPlayerScrollPane to set
	 */
	public void setPresentPlayerScrollPane(JScrollPane presentPlayerScrollPane) {
		this.presentPlayerScrollPane = presentPlayerScrollPane;
	}

	public JButton getSeePresentPlayersButton() {
		return seePresentPlayersButton;
	}

	public void setSeePresentPlayersButton(JButton seePresentPlayersButton) {
		this.seePresentPlayersButton = seePresentPlayersButton;
	}

	public JLabel getSameLastNameLabel() {
		return sameLastNameLabel;
	}

	public void setSameLastNameLabel(JLabel sameLastNameLabel) {
		this.sameLastNameLabel = sameLastNameLabel;
	}
	
	
	
	
	
	

}
