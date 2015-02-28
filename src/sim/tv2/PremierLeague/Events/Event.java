package sim.tv2.PremierLeague.Events;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import sim.tv2.PremierLeague.Application.PremierLeagueApp;
import sim.tv2.PremierLeague.Gui.Gui;
import sim.tv2.PremierLeague.Parser.PLParser;

public class Event implements ActionListener {

	private Gui gui;
	private String team;
	private int id;

	public Event (Gui gui){
		this.gui = gui;
	}

	// TODO fortsett med å implemtere hentingen av lag
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
			

		} else if (e.getSource() == gui.getDirectoryButton()){
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int choice = fileChooser.showOpenDialog(gui);
			if(choice == JFileChooser.APPROVE_OPTION){
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
			Integer id = Integer.parseInt(gui.getTeamSettingsIDField().getText());
			PremierLeagueApp.updateTeamDropDown(team, id);
			gui.getTeamSettingsIDField().setText("");
			gui.getTeamSettingsTextField().setText("");
		}
	}

}
