package gamePackage;

/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: GroundPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GroundPanel extends JPanel implements KnollPanel{

	private static final long serialVersionUID = -6043247421499329778L;
	private boolean filled = false;

	/**
	 * Constructor of Class GroundPanel. A groundPanel is a controlPanel which is
	 * displayed below the playBoard.
	 * 
	 * @param filled
	 *            - bolean value that shows if this groundPanel is selected or not.
	 */

	public GroundPanel() {
		super();
		System.out.println(System.currentTimeMillis() + ": GROUNDPANEL CREATED");
		this.setBackground(Color.GRAY);
		this.activateBorder(Color.black);
		this.filled = false;

	}

	/**
	 * Sets this pointer to selected (true) or unselected (false). The current
	 * player defines the color of the pointer.
	 * 
	 * @param b
	 *            - boolean value that shows if this groundPanel is selected or not.
	 * @param player
	 *            - number that defines the current player
	 */
	public void fill(int player, boolean b) {

		this.filled = b;
		if (b == true) {
			switch (player) {
			// ---player 1---
			case 1:
				this.setBackground(Color.blue);
				break;
			// ---player 2---
			case 2:
				this.setBackground(Color.green);
				break;

			default:
				this.setBackground(Color.GRAY);
				break;
			}
		} else {
			// ---inactive pointer---
			this.setBackground(Color.GRAY);
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
	 * @return - returns the current value of the boolean pointer
	 */
	public boolean getPointer() {
		return filled;
	}

}
