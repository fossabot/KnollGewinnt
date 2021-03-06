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
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;

public class StockPanel {

	private PlayPanel[] playPanels;
	private boolean filledCompletely;

	/**
	 * Constructor of Class stock Panel. A stockPanel has four playPanels.
	 * 
	 * @param first
	 *            #1 playPanel in row
	 * @param second
	 *            #2 playPanel in row
	 * @param third
	 *            #3 playPanel in row
	 * @param fourth
	 *            #4 playPanel in row
	 * @param playPanel[]
	 *            Array of the four PlayPanels in row
	 */
	public StockPanel(PlayPanel first, PlayPanel second, PlayPanel third, PlayPanel fourth) {
		super();
		playPanels = new PlayPanel[4];
		this.playPanels[0] = first;
		this.playPanels[1] = second;
		this.playPanels[2] = third;
		this.playPanels[3] = fourth;

	}

	/**
	 * @param index
	 *            - PlayPanel number to return
	 * @return - a PlayPanel
	 */
	public PlayPanel getPlayPanel(int index) {
		return playPanels[index];
	}

	/**
	 * Evaluates if all playPanels of this stockPanel have the same owner, which is
	 * not -1 or -2 (-1 = emptyPanels, -2 = borderPanels).
	 */
	public void evaluate() {

		if (playPanels[0].getOwner() == playPanels[1].getOwner() && playPanels[1].getOwner() == playPanels[2].getOwner()
				&& playPanels[2].getOwner() == playPanels[3].getOwner() && playPanels[0].getOwner() != -1
				&& playPanels[0].getOwner() != -2) {
			this.filledCompletely = true;
			// ---promote Panels which are responsible for the win---
			for (int i = 0; i < playPanels.length; i++) {
				playPanels[i].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));
			}
		}
	}

	/**
	 * @return filledCompletely Parameter.
	 */
	public boolean isFilledCompletely() {
		return filledCompletely;
	}

	/**
	 * Calculates the WinningChance in this specific StockPanel (0-4)
	 * 
	 * @param player
	 *            - WinningChance for what player?
	 * @return - int between 0 and 4
	 */

	public int calculateWinningChance(int player) {
		if (getFilledFields(player) > 0 && noFilledFieldsByOtherPlayer(player) == true) {
			// ---check if all the Panels are free or not filled by another player

			for (int i = 0; i < playPanels.length; i++) {
				if (playPanels[i].getOwner() != player && playPanels[i].getOwner() != -1)
					return 0;
			}

			if (getFilledFields(player) == 1 && isOnePlayable() == true)
				return 2;
			if (getFilledFields(player) == 2 && isOnePlayable() == true)
				return 3;
			if (getFilledFields(player) == 3 && isOnePlayable() == true)
				return 4;

		}

		return 0;
	}

	private boolean noFilledFieldsByOtherPlayer(int player) {
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].getOwner() != player && playPanels[i].getOwner() != -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return - true if one of the PlayPanels is playable (Itself is not filled but
	 *         all underneath in the same coloumn)
	 */
	public boolean isOnePlayable() {
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].isPlayable() == true && playPanels[i].isFilled() == false) {
				return true;
			}

		}
		return false;
	}

	/**
	 * @return - the PlayPanel which is playable (Itself is not filled but all
	 *         underneath in the same coloumn)
	 */
	public PlayPanel getPlayable() {
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].isPlayable() == true && playPanels[i].isFilled() == false) {
				if (i < playPanels.length - 1 && playPanels[i + 1].isFilled() == true) {
					return playPanels[i];
				} else if (i > 0 && playPanels[i - 1].isFilled() == true) {
					return playPanels[i];
				}

			}
		}
		ArrayList<PlayPanel> playablePanels = new ArrayList<>();
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].isPlayable() == true && playPanels[i].isFilled() == false) {
				playablePanels.add(playPanels[i]);
			}
		}
		if (playablePanels.size() > 0) {
			return playablePanels.get((int) (Math.random() * playablePanels.size()));
		}
		return null;
	}

	/**
	 * 
	 * @param player
	 * @return
	 */
	public int getFilledFields(int player) {
		int counter = 0;
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].getOwner() == player)
				counter++;
		}
		return counter;
	}
}
