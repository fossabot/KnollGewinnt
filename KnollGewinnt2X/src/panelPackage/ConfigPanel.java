/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: GroundPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
package panelPackage;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.*;

import gamePackage.KnollPlayer;

public class ConfigPanel extends JPanel {

	private static final long serialVersionUID = -4392630694778787679L;
	public JButton newGame;
	public JButton save;
	public JButton load;
	JRadioButton singlePlayer;
	JRadioButton multiPlayer;
	ButtonGroup playerChoice = new ButtonGroup();;
	JLabel status;
	JLabel gameInfo;
	JLabel players;
	String[] arg = { "1 Player", "2 Player" };

	/**
	 * Constructor of Class ConfigPanel.
	 * 
	 * @param e
	 *            - Action Listener which is provided by the MainFrame. The
	 *            ActionListener is used for the functionality of the included
	 *            Buttons.
	 * @param g
	 */

	public ConfigPanel(ActionListener e) {
		super();
		init(e);

	}

	/**
	 * Initializes the ConfigPanel.
	 * 
	 * @param e
	 *            - ActionListener which is provided by the Constructor. The
	 *            ActionListener is used for the functionality of the included
	 *            Buttons.
	 * @param g
	 */

	private void init(ActionListener e) {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		Icon dhbw_icon = new ImageIcon(getClass().getResource("dhbw_grey_logo.png"));
		JLabel dhbw_label = new JLabel(dhbw_icon);
		this.add(dhbw_label);

		newGame = new JButton("New Game");
		this.add(newGame);
		newGame.addActionListener(e);

		singlePlayer = new JRadioButton("Single Player");
		multiPlayer = new JRadioButton("Multi Player");

		playerChoice.add(singlePlayer);
		playerChoice.add(multiPlayer);
		singlePlayer.setSelected(true);
		this.add(singlePlayer);
		this.add(multiPlayer);

		save = new JButton("Save current Game");
		this.add(save);
		save.addActionListener(e);
		load = new JButton("Load saved Game");
		this.add(load);
		load.addActionListener(e);
		gameInfo = new JLabel("<html> Game currently running<html>");
		this.add(gameInfo);

		players = new JLabel("Please select Players");
		players.setForeground(Color.red);
		this.add(players);
		// Icon c = new ImageIcon(getClass().getResource("knollEins.jpg"));
		// JLabel d = new JLabel(c);
		// this.add(d);

	}

	/**
	 * Sets the gameInfo Text to whats actually going on.
	 * 
	 * @param string
	 *            - Player Number
	 */

	public void setWin(String string) {
		if (string != null)
			this.gameInfo.setText("WINNER: " + string);
		if (string == null)
			this.gameInfo.setText("Game currently running.");

	}

	/**
	 * Sets the players Text to the current Players
	 * 
	 * @param player1
	 * @param player2
	 */

	public void setPlayers(KnollPlayer player1, KnollPlayer player2) {
		if (player1 != null && player2 != null) {
			this.players.setText(player1.getName() + " VS " + player2.getName());
			players.setForeground(Color.black);
		}
		if (player1 == null && player2 == null) {
			this.players.setText("Please select Players");
			players.setForeground(Color.red);
		}
	}

	/**
	 * @return - Returns the number of the selected Mode depending on which
	 *         radioButton is selected.
	 */

	public int selectedMode() {
		if (singlePlayer.isSelected() == true)
			return 1;
		else if (multiPlayer.isSelected() == true)
			return 2;
		return 0;
	}

	/**
	 * Sets the RadioButton depending on the Mode.
	 * 
	 * @param readMode
	 */
	public void setMode(int readMode) {
		switch (readMode) {
		case 1:
			singlePlayer.setSelected(true);
			multiPlayer.setSelected(false);
			break;

		case 2:
			singlePlayer.setSelected(false);
			multiPlayer.setSelected(true);
			break;
		}

	}

	/**
	 * adds the ItemListener for the radioButtons
	 * 
	 * @param g - ItemListener
	 */
	public void addChangeListener(ItemListener g) {
		singlePlayer.addItemListener(g);
		multiPlayer.addItemListener(g);
	}

}
