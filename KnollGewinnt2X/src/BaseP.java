
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

public class BaseP extends JPanel {

	private PlayPanel[][] playBoard;
	private GroundPanel[] control;
	ArrayList<StockPanel> stocks;
	public int player = 1;

	/**
	 * Constructor of Class BaseP. A BaseP has the size of the dimension (x, y).
	 * 
	 * @param x
	 *            - Dimension size of allPanels
	 * @param y
	 *            - Dimension size of allPanels
	 */
	public BaseP(int x, int y) {
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

	public void changePointer(int direction) {
		int i = getActiveGroundPanel();
		switch (direction) {
		// 1 steht für links und 2 steht für rechts.
		case 1:

			if (i > 0 && i < (control.length)) {
				control[i].setPointer(false, player);
				control[i - 1].setPointer(true, player);
			}
			return;

		case 2:

			if (i >= 0 && i < (control.length) - 1) {
				control[i].setPointer(false, player);
				control[i + 1].setPointer(true, player);
			}
			return;

		default:
			break;
		}
	}

	public void throwCoin(int player) {
		for (int i = 0; i < control.length; i++) {
			if (control[i].pointer == true) {
				for (int j = playBoard.length - 1; j >= 0; j--) {
					if (playBoard[j][i].isFilled == false) {
						playBoard[j][i].fill(player);
						return;
					}

				}
				return;
			}
		}

	}

	private int getActiveGroundPanel() {
		for (int i = 0; i < control.length; i++) {
			if (control[i].pointer == true) {
				return i;
			}
		}
		return -1;
	}

	public void changePlayer() {
		control[getActiveGroundPanel()].setPointer(true, player);
	}

	public void newGame() {

	}

	public void generateStocks() {
		stocks = new ArrayList<StockPanel>();
		for (int i = 0; i < playBoard.length; i++) {
			for (int j = 0; j < playBoard[i].length; j++) {
				// ---vertikale Stocks---
				if (i + 3 < playBoard.length) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i + 1][j], playBoard[i + 2][j],
							playBoard[i + 3][j]));
				}
				// --horizontale Stocks---
				if (j + 3 < playBoard[i].length) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i][j + 1], playBoard[i][j + 2],
							playBoard[i][j + 3]));
				}
				// --diagonale Stacks #1---
				if (j + 3 < playBoard[i].length && i + 3 < playBoard.length) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i + 1][j + 1], playBoard[i + 2][j + 2],
							playBoard[i + 3][j + 3]));
				}
				// --diagonale Stacks #2---
				if (j - 3 < playBoard[i].length && i - 3 < playBoard.length && j - 3 >= 0 && i - 3 >= 0) {
					stocks.add(new StockPanel(playBoard[i][j], playBoard[i - 1][j - 1], playBoard[i - 2][j - 2],
							playBoard[i - 3][j - 3]));
				}

			}
		}
	}
}
