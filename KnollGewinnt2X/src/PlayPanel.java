
/*PlayPanel for use with KnollGewinnt
*(c)2018
/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: PlayPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert
 * @version 0.1
 * (c) 2018
 */
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlayPanel extends JPanel {

	boolean isFilled;
	public int owner;
	public int x, y;

	public PlayPanel() {
		super();
		this.isFilled = false;
		this.owner = -1;
		this.activateBorder(Color.black);
		System.out.println("**PLAY PANEL CREATED**");
		
	}

	public void fill(int player) {
		this.owner = player;
		switch (owner) {
		case -1:
			this.setBackground(Color.WHITE);
			break;
		case 1:
			this.setBackground(Color.BLUE);
			isFilled = true;
			break;
		case 2:
			this.setBackground(Color.GREEN);
			isFilled = true;
			break;
		}
	}

	public void activateBorder(Color bg) {
		this.setBorder(BorderFactory.createLineBorder(bg));
	}

}
