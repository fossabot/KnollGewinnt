/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: StockPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert
 * @version 0.1
 * (c) 2018
 */
public class StockPanel {

	private PlayPanel[] playPanels;
	private String direction;
	private boolean filledCompletely;

	/**
	 * Constructor of Class stock Panel. A stockPanel has four playPanels. 
	 * @param first			#1 playPanel in row
	 * @param second		#2 playPanel in row
	 * @param third			#3 playPanel in row
	 * @param fourth		#4 playPanel in row
	 * @param playPanel[]	Array of the four PlayPanels in row
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
	 * Evaluates if all playPanels of this stockPanel have the same owner, which is not -1 or -2 (-1 = emptyPanels, -2 = borderPanels).
	 */
	public void evaluate() {

		if (playPanels[0].getOwner() == playPanels[1].getOwner() && playPanels[1].getOwner() == playPanels[2].getOwner() && playPanels[2].getOwner() == playPanels[3].getOwner()
				&& playPanels[0].getOwner() != -1 && playPanels[0].getOwner() != -2) {
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
		if(getFilledFields(player)>0) {
			for (int i = playPanels.length-1; i >= 0; i--) {
				if(playPanels[i].getOwner()==-1 && playPanels[i].isPlayable()==true) {
					return 4-i;
				}
			}
		}
		
		return -1;
	}
	/**
	 *  
	 * @param player
	 * @return
	 */
	public int getFilledFields(int player) {
		int counter=0;
		for (int i = 0; i < playPanels.length; i++) {
			if (playPanels[i].getOwner()==player) counter++;
		}
		return counter;
	}
}
