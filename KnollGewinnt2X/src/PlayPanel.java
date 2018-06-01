
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
	private static final int noOwner = -1;
	private boolean filled;
	private int owner;

	/**
	 * Constructor of Class PlayPanel. A PlayPanel is a panel which is displayed on
	 * the PlayBoard. It can be filled with a coin. The player who filled it is the
	 * owner.
	 * 
	 * @param filled
	 *            - boolean value that shows if this groundPanel is filled or not.
	 * @param owner
	 *            - the player who filled the playPanel
	 */
	public PlayPanel() {
		super();
		this.filled = false;
		this.owner = -1;
		this.activateBorder(Color.black);
		System.out.println(System.currentTimeMillis()+ ": PLAYPANEL CREATED");

	}

	/**
	 * Fills this PlayPanel with the specific color of the player
	 * 
	 * @param player
	 */
	public void fill(int player) {
		this.owner = player;
		switch (owner) {
		// ---Put white for an empty PlayPanel with no owner---
		case noOwner:
			this.setBackground(Color.WHITE);
			break;
		// ---Put blue for Player 1---
		case 1:
			this.setBackground(Color.BLUE);
			filled = true;
			break;
		// ---Put green for Player 2---
		case 2:
			this.setBackground(Color.GREEN);
			filled = true;
			break;
		}
	}

	/**
	 * Activates the border of this groundPanel to make it visible.
	 * 
	 * @param color
	 *            - defines the color which is used
	 */
	public void activateBorder(Color color) {
		this.setBorder(BorderFactory.createLineBorder(color));
	}

	/**
	 * @return the filled
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}

}
