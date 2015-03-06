package sim.tv2.PremierLeague.Gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;

import sim.tv2.PremierLeague.Application.PremierLeagueApp;
import sim.tv2.PremierLeague.Events.Event;
import sim.tv2.PremierLeague.Events.MenuEvent;
import sim.tv2.PremierLeague.Parser.PLParser;
import sim.tv2.PremierLeague.Player.Player;

/**
 * Class representing the graphical user interface
 * @author Sindre Moldeklev
 */
public class Gui extends JFrame {

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
	private JTabbedPane tabbedPane;
	private JMenu about;
	private JMenuItem aboutItem;
	private JCheckBox checkBox;
	private JLabel teamSettingsLabel;
	private JTextField teamSettingsTextField;
	private JLabel teamSettingsIDLabel;
	private JTextField teamSettingsIDField;
	private JButton updateButton;
	private List<String> teamList;
	private String[] teamsArrays;
	private DefaultComboBoxModel<String> comboBoxModel;
	private JLabel updateLabel;
	private JButton removeTeamButton;
	private JTextField removeTeamTextField;
	private JLabel removeTeamLabelStatus;
	private JMenuItem exitItem;
	private JComboBox<String> removeTeamComboBox;

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

	/**
	 * Method to setup the menuBar 
	 */
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Meny");
		setExitItem(new JMenuItem("Avslutt"));
		getExitItem().addActionListener(new Event(this));
		menu.add(getExitItem());
		setAbout(new JMenu("Om"));
		setAboutItem(new JMenuItem("Om"));
		getAbout().add(getAboutItem());
		getAboutItem().addActionListener(new MenuEvent(this));
		menuBar.add(menu);
		menuBar.add(getAbout());


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
		setTabbedPane(new JTabbedPane());
		centerFrame();
		this.add(getTabbedPane());
		this.setResizable(false);
	}

	/**
	 * Method to setup the top panel
	 */
	private void setupTopPanel(){
		topPanel = new JPanel();
		String fileName = "data.ser";
		File teamsFile = new File(fileName);
		setTeamsMap(new HashMap<String, Integer>());
		if(teamsFile.exists()){
			setTeamsMap(PremierLeagueApp.loadTeams(fileName));
		} else {
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
		}

		setComboBoxModel(new DefaultComboBoxModel<String>());
		for(String team : getTeamsMap().keySet()){
			getComboBoxModel().addElement(team);
		}
		
		setDirectoryButton(new JButton("Velg mappe"));
		teamComboBox = new JComboBox<>(getComboBoxModel());
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
		//		getTabbedPane().add("Hovedvindu", topPanel);
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
		// Legg til valg for oppdatering av lag her
		centerPanel.add(createSettingsPanel());
		this.add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Method to create a jpanel with settings
	 */
	private JTabbedPane createSettingsPanel(){
		JTabbedPane tabbedSettingsPane = new JTabbedPane();
		JPanel settingsPanel = new JPanel();
		JPanel topSettingsPanel = new JPanel();
		settingsPanel.setLayout(new BorderLayout());
		setCheckBox(new JCheckBox());
		getCheckBox().addActionListener(new Event(this));
		getCheckBox().setText("Vis innstillinger");
		topSettingsPanel.add(new JLabel("Legg til lag i dropdown"));
		topSettingsPanel.add(getCheckBox());

		JPanel centerSettingsPanel = new JPanel();
		teamSettingsLabel = new JLabel("Navn på lag");
		teamSettingsTextField = new JTextField(23);
		centerSettingsPanel.add(teamSettingsLabel);
		centerSettingsPanel.add(teamSettingsTextField);

		teamSettingsIDLabel = new JLabel("Lag-ID fra Alt om Fotball");
		teamSettingsIDField = new JTextField(23);
		centerSettingsPanel.add(teamSettingsIDLabel);
		centerSettingsPanel.add(teamSettingsIDField);
		setUpdateButton(new JButton("Legg til lag"));
		getUpdateButton().addActionListener(new Event(this));
		centerSettingsPanel.add(getUpdateButton());
		setUpdateLabel(new JLabel(""));
		centerSettingsPanel.add(getUpdateLabel());

		settingsPanel.add(centerSettingsPanel, BorderLayout.CENTER);
		settingsPanel.add(topSettingsPanel, BorderLayout.NORTH);
		// Slutt på panelet for å legge til lag

		// Start på panelet for å fjerne et lag.
		JPanel removeTeamPanel = new JPanel();
		JPanel topRemovePanel = new JPanel();
		JPanel centerRemovePanel = new JPanel();
		removeTeamPanel.setLayout(new BorderLayout());
		removeTeamPanel.add(topRemovePanel, BorderLayout.NORTH);
		removeTeamPanel.add(centerRemovePanel, BorderLayout.CENTER);
		JLabel removeTeamLabel = new JLabel("Skriv inn laget du vil fjerne fra dropdown-menyen");
		topRemovePanel.add(removeTeamLabel, BorderLayout.NORTH);
		removeTeamTextField = new JTextField(25);
		centerRemovePanel.add(removeTeamTextField, BorderLayout.CENTER);
		removeTeamButton = new JButton("Fjern lag");
		centerRemovePanel.add(removeTeamButton, BorderLayout.SOUTH);
		getRemoveTeamButton().addActionListener(new Event(this));
		setRemoveTeamLabelStatus(new JLabel());
		centerRemovePanel.add(removeTeamLabelStatus);
		setRemoveTeamComboBox(new JComboBox<>(getComboBoxModel()));
		centerRemovePanel.add(getRemoveTeamComboBox());
		getRemoveTeamComboBox().addActionListener(new Event(this));
		
		tabbedSettingsPane.add(settingsPanel, "Legg til lag");
		tabbedSettingsPane.add(removeTeamPanel, "Fjerne lag");
		disablePropertiesPanel();
		return tabbedSettingsPane;
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
	 * Method to disable innstillings panel
	 */
	public void disablePropertiesPanel(){
		getTeamSettingsIDField().setEnabled(false);
		getTeamSettingsTextField().setEnabled(false);
		getUpdateButton().setEnabled(false);
	}

	/**
	 * Method to disable innstillings panel
	 */
	public void enablePropertiesPanel(){
		getTeamSettingsIDField().setEnabled(true);
		getTeamSettingsTextField().setEnabled(true);
		getUpdateButton().setEnabled(true);
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

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JMenu getAbout() {
		return about;
	}

	public void setAbout(JMenu about) {
		this.about = about;
	}

	public JMenuItem getAboutItem() {
		return aboutItem;
	}

	public void setAboutItem(JMenuItem aboutItem) {
		this.aboutItem = aboutItem;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}

	/**
	 * @return the teamSettingsLabel
	 */
	public JLabel getTeamSettingsLabel() {
		return teamSettingsLabel;
	}

	/**
	 * @param teamSettingsLabel the teamSettingsLabel to set
	 */
	public void setTeamSettingsLabel(JLabel teamSettingsLabel) {
		this.teamSettingsLabel = teamSettingsLabel;
	}

	/**
	 * @return the teamSettingsTextField
	 */
	public JTextField getTeamSettingsTextField() {
		return teamSettingsTextField;
	}

	/**
	 * @param teamSettingsTextField the teamSettingsTextField to set
	 */
	public void setTeamSettingsTextField(JTextField teamSettingsTextField) {
		this.teamSettingsTextField = teamSettingsTextField;
	}

	/**
	 * @return the teamSettingsIDLabel
	 */
	public JLabel getTeamSettingsIDLabel() {
		return teamSettingsIDLabel;
	}

	/**
	 * @param teamSettingsIDLabel the teamSettingsIDLabel to set
	 */
	public void setTeamSettingsIDLabel(JLabel teamSettingsIDLabel) {
		this.teamSettingsIDLabel = teamSettingsIDLabel;
	}

	/**
	 * @return the teamSettingsIDField
	 */
	public JTextField getTeamSettingsIDField() {
		return teamSettingsIDField;
	}

	/**
	 * @param teamSettingsIDField the teamSettingsIDField to set
	 */
	public void setTeamSettingsIDField(JTextField teamSettingsIDField) {
		this.teamSettingsIDField = teamSettingsIDField;
	}

	public JButton getUpdateButton() {
		return updateButton;
	}

	public void setUpdateButton(JButton updateButton) {
		this.updateButton = updateButton;
	}

	public List<String> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<String> teamList) {
		this.teamList = teamList;
	}

	public String[] getTeamsArrays() {
		return teamsArrays;
	}

	public void setTeamsArrays(String[] teamsArrays) {
		this.teamsArrays = teamsArrays;
	}

	public DefaultComboBoxModel<String> getComboBoxModel() {
		return comboBoxModel;
	}

	public void setComboBoxModel(DefaultComboBoxModel<String> comboBoxModel) {
		this.comboBoxModel = comboBoxModel;
	}

	public JLabel getUpdateLabel() {
		return updateLabel;
	}

	public void setUpdateLabel(JLabel updateLabel) {
		this.updateLabel = updateLabel;
	}

	/**
	 * @return the removeTeamButton
	 */
	public JButton getRemoveTeamButton() {
		return removeTeamButton;
	}

	/**
	 * @param removeTeamButton the removeTeamButton to set
	 */
	public void setRemoveTeamButton(JButton removeTeamButton) {
		this.removeTeamButton = removeTeamButton;
	}

	/**
	 * @return the removeTeamTextField
	 */
	public JTextField getRemoveTeamTextField() {
		return removeTeamTextField;
	}

	/**
	 * @param removeTeamTextField the removeTeamTextField to set
	 */
	public void setRemoveTeamTextField(JTextField removeTeamTextField) {
		this.removeTeamTextField = removeTeamTextField;
	}

	public JLabel getRemoveTeamLabelStatus() {
		return removeTeamLabelStatus;
	}

	public void setRemoveTeamLabelStatus(JLabel removeTeamLabelStatus) {
		this.removeTeamLabelStatus = removeTeamLabelStatus;
	}

	public JMenuItem getExitItem() {
		return exitItem;
	}

	public void setExitItem(JMenuItem exitItem) {
		this.exitItem = exitItem;
	}

	public JComboBox<String> getRemoveTeamComboBox() {
		return removeTeamComboBox;
	}

	public void setRemoveTeamComboBox(JComboBox<String> removeTeamComboBox) {
		this.removeTeamComboBox = removeTeamComboBox;
	}

}
