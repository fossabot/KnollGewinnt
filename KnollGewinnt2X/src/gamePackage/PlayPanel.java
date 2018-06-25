package gamePackage;

/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: PlayPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayPanel extends JPanel implements KnollPanel {
	private static final int noOwner = -1;
	private boolean filled;
	private boolean playable;
	private int owner;
	private JLabel a;

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
		System.out.println(System.currentTimeMillis() + ": PLAYPANEL CREATED");
		//Icon b = new ImageIcon(getClass().getResource("knolll.png"));
		//a = new JLabel(b);
		//this.add(a);
		//this.setLayout(new FlowLayout());
		//a.setVisible(false);
	}

	/**
	 * Fills this PlayPanel with the specific color of the player
	 * 
	 * @param player
	 * @param b 
	 * @throws Exception
	 */
	public void fill(int player, boolean b) throws Exception {
		if ((this.getBackground().equals(Color.GREEN) || this.getBackground().equals(Color.BLUE)
				|| this.getBackground().equals(Color.RED))&&b==true) {
			throw new Exception("PlayPanel is already filled!");
		} else {
			this.owner = player;
			switch (owner) {
			// ---Put white for an empty PlayPanel with no owner---
			case noOwner:
				this.setBackground(Color.WHITE);
				filled = false;
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
			// ---Put red for Player 3 aka KI---
			case 3:
				this.setBackground(Color.RED);
				//a.setVisible(true);
				filled = true;
				break;
			}
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

	/**
	 * Sets the actual status of the param playable. A PlayPanel is playable if all
	 * the lower PlayPanels in the same Coloumn are filled.
	 * 
	 * @param playable
	 */

	public void setPlayable(Boolean playable) {
		this.playable = playable;
	}

	/**
	 * @return the boolean value playable
	 */
	public boolean isPlayable() {
		return this.playable;
	}

}
