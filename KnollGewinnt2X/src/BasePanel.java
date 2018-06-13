
/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: BaseP
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert
 * @version 0.1
 * (c) 2018
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BasePanel extends JPanel {

	private static final int left = 1;
	private static final int right = 2;
	private static final int noOwner = -1;
	private PlayPanel[][] playBoard;
	private GroundPanel[] control;
	ArrayList<StockPanel> stocks;
	public int player = 1;

	/**
	 * Constructor of Class BasePanel. A BasePanel has the size of the dimension (x,
	 * y).
	 * 
	 * @param x
	 *            - Dimension size of allPanels
	 * @param y
	 *            - Dimension size of allPanels
	 */
	public BasePanel(int x, int y) {
		super();
		this.setLayout(new GridLayout(y, x));
		initializePanels(x, y);
		generateStocks();

	}

	/**
	 * 
	 * @param x
	 *            - Dimension size of allPanels
	 * @param y
	 *            - Dimension size of allPanels
	 * @param playBoard
	 *            - grid of all PlayPanels
	 */
	private void initializePanels(int x, int y) {
		playBoard = new PlayPanel[y - 1][x]; // contains all playPanels excluding controlPanels

		// ---create PlayPanels---
		for (int i = 0; i < playBoard.length; i++) {
			for (int j = 0; j < playBoard[i].length; j++) {
				playBoard[i][j] = new PlayPanel();
				this.add(playBoard[i][j]);
			}
		}

		control = new GroundPanel[x]; // contains all GroundPanels
		// ---create GroundPanels---
		for (int i = 0; i < control.length; i++) {
			control[i] = new GroundPanel();
			this.add(control[i]);
		}
		// ---set first pointer position (shows the selected coloumn)
		control[control.length - 1].setPointer(true, player);
	}

	/**
	 * 
	 * @return - returns true if one of the StockPanels is filled completely in one
	 *         color
	 */
	public boolean evaluateRows() {

		// ---let the stocks evaluate themselves---
		for (int i = 0; i < stocks.size(); i++) {
			stocks.get(i).evaluate();
		}
		// ---check if one of the stocks evaluated true
		for (int i = 0; i < stocks.size(); i++) {
			if (stocks.get(i).isFilledCompletely() == true) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 
	 * @param direction
	 *            - information about where to move the pointer
	 */
	public void changePointer(int direction) {
		int i = getActiveGroundPanel();
		switch (direction) {

		case left:

			if (i > 0 && i < (control.length)) {
				// ---deactivate current pointer---
				control[i].setPointer(false, player);
				// ---activate pointer to the left
				control[i - 1].setPointer(true, player);
			}
			return;

		case right:

			if (i >= 0 && i < (control.length) - 1) {
				// ---deactivate current pointer---
				control[i].setPointer(false, player);
				// ---activate pointer to the right
				control[i + 1].setPointer(true, player);
			}
			return;

		default:
			break;
		}
	}

	/**
	 * Throws the coin of the current Player into the coloumn which is selected by
	 * the pointer
	 * 
	 * @param player
	 *            - the player who throws the coin
	 * @throws Exception 
	 */
	public void throwCoin(int player) throws Exception {

		if (player == 3) {
			int highestChanceKI = 0;
			int highestChancePL = 0;
			// ---highestChance KI---
			for (int i = 0; i < stocks.size(); i++) {
				if (stocks.get(i).calculateWinningChance(3) > highestChanceKI) {
					highestChanceKI = stocks.get(i).calculateWinningChance(3);
				}
			}
			// ---highestChance PL--- 
			for (int i = 0; i < stocks.size(); i++) {
				if (stocks.get(i).calculateWinningChance(1) > highestChancePL) {
					highestChancePL = stocks.get(i).calculateWinningChance(1);
				}
			}
			System.out.println("HighestChanceKI: " + highestChanceKI);
			System.out.println("HighestChancePL: " + highestChancePL);

			if (highestChanceKI == 0 && highestChancePL <= 0) {
			System.out.println("Random");
				for (int j = playBoard.length - 1; j >= 0; j--) {

					for (int i = 0; i < playBoard[0].length; i++) {
						if (playBoard[j][i].isFilled() == false) {
							playBoard[j][i].fill(player);
							return;
						}
					}
				}
			} else if (highestChanceKI >= highestChancePL && highestChanceKI >0) {
				System.out.println("NotRandom");
				this.evaluatePlayablePanels();
				for (int i = 0; i < stocks.size(); i++) {
					if (stocks.get(i).calculateWinningChance(3) == highestChanceKI) {
						stocks.get(i).getPlayable().fill(3);
						 return;
					}
				}
			}else if (highestChancePL > highestChanceKI && highestChancePL >0) {
				System.out.println("NotRandomPL");
				for (int i = 0; i < stocks.size(); i++) {
					if (stocks.get(i).calculateWinningChance(1) == highestChancePL) {
						stocks.get(i).getPlayable().fill(3);
						return;
					}
				}
			}

		} else {
			for (int j = playBoard.length - 1; j >= 0; j--) {

				// ---fill next free playPanel starting from the bottom
				if (playBoard[j][getActiveGroundPanel()].isFilled() == false) {
					playBoard[j][getActiveGroundPanel()].fill(player);
					return;
				}

			}
			return;
		}
	}

	/**
	 * 
	 * @return - returns the index of the selected GroundPanel by the pointer
	 */
	private int getActiveGroundPanel() {
		for (int i = 0; i < control.length; i++) {
			if (control[i].getPointer() == true) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * changes the pointer to the current player (calls setPointer who changes the
	 * color)
	 */
	public void changePlayer() {
		control[getActiveGroundPanel()].setPointer(true, player);
	}

	/**
	 * Sets one playble PlayPanel per coloumn.
	 */

	public void evaluatePlayablePanels() {

		for (int i = 0; i < playBoard.length; i++) {
			for (int j = 0; j < playBoard.length; j++) {
				playBoard[i][j].setPlayable(false);
			}
		}

		for (int i = 0; i < playBoard[0].length; i++) {
			for (int j = playBoard.length - 1; j >= 0; j--) {
				if (playBoard[j][i].getOwner() == noOwner) {
					playBoard[j][i].setPlayable(true);
					System.out.println(System.currentTimeMillis() + " " + i + " " + j + " playable_obj_ID "+ playBoard[j][i].hashCode());
					break;
				}
			}
		}
	}

	/**
	 * analyzes the current playBoard and generates forms of 4 playpanels (1
	 * stockpanel contains 4 playpanels).
	 * 
	 * @param stocks
	 *            - contains a list of all possible combinations of 4 playpanels
	 */
	public void generateStocks() {
		stocks = new ArrayList<StockPanel>();
		for (int i = 0; i < playBoard.length; i++) {
			for (int j = 0; j < playBoard[i].length; j++) {
				// ---vertikale Stocks---aa
				if (i + 3 < playBoard.length) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i + 1][j], playBoard[i + 2][j],
							playBoard[i + 3][j]));
				}
				// --horizontale Stocks---
				if (j + 3 < playBoard[i].length) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i][j + 1], playBoard[i][j + 2],
							playBoard[i][j + 3]));
				}
				// --diagonale Stocks #1---
				if (j + 3 < playBoard[i].length && i + 3 < playBoard.length) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i + 1][j + 1], playBoard[i + 2][j + 2],
							playBoard[i + 3][j + 3]));
				}
				// --diagonale Stocks #2---
				if (j - 3 < playBoard[i].length && i - 3 < playBoard.length && j - 3 >= 0 && i - 3 >= 0) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i - 1][j - 1], playBoard[i - 2][j - 2],
							playBoard[i - 3][j - 3]));
				}

			}
		}
	}
}
