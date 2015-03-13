package sim.tv2.PremierLeague.Events;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import sim.tv2.PremierLeague.Application.PremierLeagueApp;
import sim.tv2.PremierLeague.Gui.Gui;
import sim.tv2.PremierLeague.Parser.PLParser;
/**
 * Class used to represent the user events triggered by the Graphical User Interface
 * @author Sindre Moldeklev
 * @version 0.0.1
 *
 */
public class Event implements ActionListener {

	private Gui gui;
	private String team;
	private int id;
	private String directory;

	/**
	 * Constructor for the Event class
	 * @param gui
	 */
	public Event (Gui gui){
		this.gui = gui;
		directory = ".";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == gui.getTeamComboBox()){
			gui.getPlayerModel().clear();
			gui.getPresentPlayerModel().clear();
			gui.getMissingPlayerModel().clear();
			team = (String) gui.getTeamComboBox().getSelectedItem();
			id = gui.getTeamsMap().get(team);
			gui.setParser(new PLParser(id));
			PremierLeagueApp.populateModel(gui.getParser().getPlayers());
			gui.setId(id);
			gui.getNumberOfPlayersLabel().setText("Antall spillere på " + team + " " +  gui.getParser().getPlayers().size());
			gui.getUpdateLabel().setText("");
			gui.getRemoveTeamLabelStatus().setText("");

		} else if (e.getSource() == gui.getDirectoryButton()){
			JFileChooser fileChooser = new JFileChooser(directory);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int choice = fileChooser.showOpenDialog(gui);
			if(choice == JFileChooser.APPROVE_OPTION){
				directory = fileChooser.getSelectedFile().getAbsolutePath();
				gui.setDirectory(fileChooser.getSelectedFile());
				gui.getDirectoryLabel().setText("Valgt mappe: " + gui.getDirectory().getName());
				gui.getMissingPlayersButton().setEnabled(true);
				gui.getSeePresentPlayersButton().setEnabled(true);
			} 
		} else if (e.getSource() == gui.getMissingPlayersButton()){
			String team = (String) gui.getTeamComboBox().getSelectedItem();
			gui.getMissingPlayerModel().clear();
			PremierLeagueApp.setupMissingPlayers();
			gui.getMissingPlayersLabel().setText("Spillere som mangler på " + team + " " + gui.getParser().getNonPresentPlayers().size());
		} else if (e.getSource() == gui.getSeePresentPlayersButton()){
			gui.getPresentPlayerModel().clear();
			PremierLeagueApp.setupPresentPlayer();
		} else if (e.getSource() == gui.getUpdateButton()){
			String team = gui.getTeamSettingsTextField().getText();
			try{
				Integer id = Integer.parseInt(gui.getTeamSettingsIDField().getText());				
				PremierLeagueApp.updateTeamDropDown(team.trim(), id);
				gui.getUpdateLabel().setText("Oppdatert dropdown med " + team + " og ID: " + id);
				gui.getTeamSettingsIDField().setText("");
				gui.getTeamSettingsTextField().setText("");
				PremierLeagueApp.saveTeams();
			} catch (NumberFormatException numberException){
				numberException.printStackTrace();
				gui.getUpdateLabel().setText("Ikke bruk bokstaver for ID");
			}
		} else if (e.getSource() == gui.getRemoveTeamButton()){
			String team = gui.getRemoveTeamTextField().getText();
			if(PremierLeagueApp.removeFromDropDown(team)){
				gui.getRemoveTeamTextField().setText("");
				gui.getRemoveTeamLabelStatus().setText(team + " ble fjernet fra dropdown-menyen");
				PremierLeagueApp.saveTeams();
			} else {
				gui.getRemoveTeamLabelStatus().setText("Gitt lag finnes ikke i dropdown-menyen");
			}
		} else if (e.getSource() == gui.getCheckBox()){
			if(gui.getCheckBox().isSelected()){
				gui.enablePropertiesPanel();
			} else {
				gui.disablePropertiesPanel();
			}
		} else if (e.getSource() == gui.getExitItem()){
			PremierLeagueApp.saveTeams();
			System.exit(0);
		} else if (e.getSource() == gui.getRemoveTeamComboBox()){
			gui.getRemoveTeamTextField().setText((String) gui.getRemoveTeamComboBox().getSelectedItem());
		}
	}

}
