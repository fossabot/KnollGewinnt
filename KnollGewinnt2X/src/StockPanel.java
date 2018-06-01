/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: stockPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert
 * @version 0.1
 * (c) 2018
 */
public class StockPanel {

	private PlayPanel first, second, third, fourth;
	private String direction;
	private boolean filledCompletely;

	/**
	 * Constructor of Class stock Panel. A stockPanel has four playPanels. 
	 * @param first		#1 playPanel in row
	 * @param second	#2 playPanel in row
	 * @param third		#3 playPanel in row
	 * @param fourth	#4 playPanel in row
	 */
	public StockPanel(PlayPanel first, PlayPanel second, PlayPanel third, PlayPanel fourth) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
	}

	/**
	 * Evaluates if all playPanels of this stockPanel have the same owner, which is not -1 or -2 (-1 = emptyPanels, -2 = borderPanels).
	 */
	public void evaluate() {

		if (first.owner == second.owner && second.owner == third.owner && third.owner == fourth.owner
				&& first.owner != -1 && first.owner != -2) {
			this.filledCompletely = true;
		}
	}
	/**
	 * @return filledCompletely Parameter.
	 */
	public boolean isFilledCompletely() {
		return filledCompletely;
	}

}
