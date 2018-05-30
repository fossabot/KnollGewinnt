
import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConfigPanel extends JPanel {

	JButton newGame;
	JRadioButton singlePlayer;
	JRadioButton multiPlayer;
	ButtonGroup playerChoice = new ButtonGroup();;
	JLabel status;
	JButton save;
	String[] arg = { "1 Player", "2 Player" };

	public ConfigPanel(ActionListener e) {
		super();
		init(e);

	}

	private void init(ActionListener e) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		newGame = new JButton("New Game");
		this.add(newGame);
		newGame.addActionListener(e);

		singlePlayer = new JRadioButton("Single Player");
		multiPlayer = new JRadioButton("Multi Player");

		playerChoice.add(singlePlayer);
		playerChoice.add(multiPlayer);
		this.add(singlePlayer);
		this.add(multiPlayer);

		save = new JButton("Save current Game");
		this.add(save);
		
		
	}

}
