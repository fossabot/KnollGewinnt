/**
 * KNOLL GEWINNT powered by javax.swing CLASS: StockPanel
 * 
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert
 * @version 0.1 (c) 2018
 */
public class StockPanel {

	private PlayPanel[] playPanels;
	private String direction;
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
		}
	}

	/**
	 * @return filledCompletely Parameter.
	 */
	public boolean isFilledCompletely() {
		return filledCompletely;
	}

	/**
	 * 
	 * @param player
	 * @return
	 */

	public int calculateWinningChance(int player) {
		if (getFilledFields(player) > 0) {
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

	public boolean isOnePlayable() {
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].isPlayable() == true)
				return true;
		}
		return false;
	}
	
	public PlayPanel getPlayable() {
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].isPlayable() == true)
				return playPanels[i];
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
