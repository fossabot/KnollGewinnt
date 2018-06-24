package gamePackage;

import java.awt.event.ActionListener;

import javax.swing.*;

public class ConfigPanel extends JPanel {

	JButton newGame;
	JButton save;
	JButton load;
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
	 */

	private void init(ActionListener e) {

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		Icon b = new ImageIcon(getClass().getResource("dhbw_grey_logo.png"));
		JLabel a = new JLabel(b);
		this.add(a);
		
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
		
		players = new JLabel("NULL" +" VS "+ "NULL");
		this.add(players);
		//Icon c = new ImageIcon(getClass().getResource("knollEins.jpg"));
		//JLabel d = new JLabel(c);
		//this.add(d);

	}

	/**
	 * Sets the gameInfo Text to whats actually going on.
	 * 
	 * @param player
	 *            - Player Number
	 */

	public void setWin(int player) {
		if (player == -1) {
			this.gameInfo.setText("Game currently running");
		} else {
			this.gameInfo.setText("WINNER: " + player);
		}

	}

	
	/**
	 * Sets the players Text to the current Players
	 * @param player1
	 * @param player2
	 */
	
	public void setPlayers(KnollPlayer player1, KnollPlayer player2) {
		this.players.setText(player1.getName() + " VS " + player2.getName());;
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

}
