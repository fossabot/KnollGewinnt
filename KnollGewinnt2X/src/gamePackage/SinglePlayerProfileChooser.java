package gamePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class SinglePlayerProfileChooser implements ProfileChooser {

	private JOptionPane selectionPane;
	static JComboBox<String> playersList;
	static JButton select;
	private JDialog singlePlayerDialog;
	private ActionListener profileSelected;

	public SinglePlayerProfileChooser() {

	}

	@Override
	public void open(HashMap<String, KnollPlayer> playersMap, ActionListener profileSelected) {

		String[] playersStringArray = playersMap.values().stream().filter(player -> !(player.getName().equals("KI")))
				.map(player -> player.getName()).toArray(String[]::new);

		selectionPane = new JOptionPane("Select your Player Profile", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		playersList = new JComboBox<String>(playersStringArray);
		selectionPane.add(playersList);
		select = new JButton("Select");
		selectionPane.add(select);
		select.addActionListener(profileSelected);

		singlePlayerDialog = selectionPane.createDialog(null, "SinglePlayer Profile Choice");
		singlePlayerDialog.setVisible(true);

	}

	@Override
	public void dispose() {
		if (singlePlayerDialog != null)
			singlePlayerDialog.dispose();

	}

}
