package gamePackage;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MultiPlayerProfileChooser implements ProfileChooser {

	private JOptionPane selectionPane;
	static JComboBox<String> playersList;
	static JComboBox<String> playersList2;
	static JButton selectMultiPlayer;
	private JDialog multiPlayerDialog;

	public MultiPlayerProfileChooser() {

	}

	@Override
	public void open(HashMap<String, KnollPlayer> playersMap, ActionListener profileSelected) {
		String[] playersStringArray = playersMap.values().stream().filter(player -> !(player.getName().equals("KI")))
				.map(player -> player.getName()).toArray(String[]::new);

		selectionPane = new JOptionPane("Select Player Profiles", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		selectionPane.add(new JLabel("Player 1: "));
		playersList = new JComboBox<String>(playersStringArray);
		selectionPane.add(playersList);
		selectionPane.add(new JLabel("Player 2: "));
		playersList2 = new JComboBox<String>(playersStringArray);
		selectionPane.add(playersList2);
		selectMultiPlayer = new JButton("Select");
		selectionPane.add(selectMultiPlayer);
		selectMultiPlayer.addActionListener(profileSelected);

		multiPlayerDialog = selectionPane.createDialog(null, "MultiPlayer Profile Choice");
		multiPlayerDialog.setVisible(true);
	}

	@Override
	public void dispose() {
		if (multiPlayerDialog!=null)multiPlayerDialog.dispose();
		
	}

}
