/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: ConfigPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert
 * @version 0.1
 * (c) 2018
 */
import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConfigPanel extends JPanel {

	JButton newGame;
	JButton save;
	JRadioButton singlePlayer;
	JRadioButton multiPlayer;
	ButtonGroup playerChoice = new ButtonGroup();;
	JLabel status;
	JLabel gameInfo;
	String[] arg = { "1 Player", "2 Player" };
	

	/**
	 * Constructor of Class ConfigPanel. 
	 * @param e - Action Listener which is provided by the MainFrame. The ActionListener is used for the functionality of the included Buttons.
	 */
	
	public ConfigPanel(ActionListener e) {
		super();
		init(e);

	}

	/**
	 * Initializes the ConfigPanel.
	 * @param e - ActionListener which is provided by the Constructor. The ActionListener is used for the functionality of the included Buttons.
	 */
	
	private void init(ActionListener e) {
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
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
		gameInfo = new JLabel("<html> Game currently running<html>");
		this.add(gameInfo);

	}

	/**
	 * Sets the gameInfo Text to whats actually going on. 
	 * @param player - Player Number
	 */
	
	public void setWin(int player) {
		if (player == -1) {
			this.gameInfo.setText("Game currently running");
		} else {
			this.gameInfo.setText("WINNER: " + player);
		}

	}

	/**
	 * @return - Returns the number of the selected Mode depending on which radioButton is selected.
	 */
	
	public int selectedMode() {
		if (singlePlayer.isSelected() == true)
			return 1;
		else if (multiPlayer.isSelected() == true)
			return 2;
		return 0;
	}

}
