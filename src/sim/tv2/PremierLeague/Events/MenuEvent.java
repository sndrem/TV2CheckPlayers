package sim.tv2.PremierLeague.Events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import sim.tv2.PremierLeague.Gui.Gui;

public class MenuEvent implements ActionListener {
	private Gui gui;
	
	public MenuEvent(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == gui.getAboutItem()){
			JOptionPane.showMessageDialog(gui, "Utviklet av Sindre Moldeklev");
		}
	}

}
