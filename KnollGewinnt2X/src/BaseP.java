
/*BasePanel for use with KnollGewinnt
*(c)2018
*/
  import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class BaseP extends JPanel {

	private PlayPanel[][] struktur;
	private GroundPanel[] control;

	public BaseP(int x, int y) {
		super();
		this.setLayout(new GridLayout(y, x));
		struktur = new PlayPanel[y - 1][x];
		for (int i = 0; i < struktur.length; i++) {
			for (int j = 0; j < struktur[i].length; j++) {
				struktur[i][j] = new PlayPanel();
				struktur[i][j].activateBorder(Color.BLACK);
				this.add(struktur[i][j]);
			}
		}
		control = new GroundPanel[x];
		for (int i = 0; i < control.length; i++) {
			control[i] = new GroundPanel();
			control[i].activateBorder(Color.BLACK);
			this.add(control[i]);
		}
		control[control.length - 1].setPointer(true);
		provideNeighbours();

	}

	private void provideNeighbours() {

		for (int i = 0; i < struktur.length; i++) {
			for (int j = 0; j < struktur[i].length; j++) {
				if (i == 0 && j == 0) {// links oben
					struktur[i][j].setNeighbours(new PlayPanel(), struktur[i][j + 1], struktur[i + 1][j],
							new PlayPanel(), new PlayPanel(), struktur[i + 1][j + 1], new PlayPanel(), new PlayPanel());
				} else if (i == 0 && j == struktur[i].length - 1) {// rechts oben
					struktur[i][j].setNeighbours(new PlayPanel(), new PlayPanel(), struktur[i + 1][j],
							struktur[i][j - 1], new PlayPanel(), new PlayPanel(), struktur[i + 1][j - 1],
							new PlayPanel());
				} else if (i == struktur.length - 1 && j == 0) {// links unten
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], new PlayPanel(),
							new PlayPanel(), struktur[i - 1][j + 1], new PlayPanel(), new PlayPanel(), new PlayPanel());
				} else if (i == struktur.length - 1 && j == struktur[i].length - 1) {// rechts unten
					struktur[i][j].setNeighbours(struktur[i - 1][j], new PlayPanel(), new PlayPanel(),
							struktur[i][j - 1], new PlayPanel(), new PlayPanel(), new PlayPanel(),
							struktur[i - 1][j - 1]);
				} else if (i == 0 && j != 0 && j != struktur[i].length - 1) {// oberer Rand
					struktur[i][j].setNeighbours(new PlayPanel(), struktur[i][j + 1], struktur[i + 1][j],
							struktur[i][j - 1], new PlayPanel(), struktur[i + 1][j + 1], struktur[i + 1][j - 1],
							new PlayPanel());
				} else if (i == struktur.length - 1 && j != 0 && j != struktur[i].length - 1) {// unterer Rand
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], new PlayPanel(),
							struktur[i][j - 1], struktur[i - 1][j + 1], new PlayPanel(), new PlayPanel(),
							struktur[i - 1][j - 1]);
				} else if (i != 0 && i != struktur.length - 1 && j == struktur[i].length - 1) {// rechter Rand
					struktur[i][j].setNeighbours(struktur[i - 1][j], new PlayPanel(), struktur[i + 1][j],
							struktur[i][j - 1], new PlayPanel(), new PlayPanel(), struktur[i + 1][j - 1],
							struktur[i - 1][j - 1]);
				} else if (i != 0 && i != struktur.length - 1 && j == 0) {// linker Rand
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], struktur[i + 1][j],
							new PlayPanel(), struktur[i - 1][j + 1], struktur[i + 1][j + 1], new PlayPanel(),
							new PlayPanel());
				} else {
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], struktur[i + 1][j],
							struktur[i][j - 1], struktur[i - 1][j + 1], struktur[i + 1][j + 1], struktur[i + 1][j - 1],
							struktur[i - 1][j - 1]);
				}

			}
		}

	}

	public boolean evaluateRows() {
		String[] posb = { "n", "o", "s", "w", "no", "so", "sw", "nw" };
		for (int i = 0; i < struktur.length; i++) {
			for (int j = 0; j < struktur[i].length; j++) {
				for (String string : posb) {
					if (struktur[i][j].owner == struktur[i][j].getNeighbour(string).owner && struktur[i][j].owner != -1
							&& struktur[i][j].getNeighbour(string).owner != -1) {
						if (struktur[i][j].getNeighbour(string).owner == struktur[i][j].getNeighbour(string)
								.getNeighbour(string).owner && struktur[i][j].getNeighbour(string).owner != -1
								&& struktur[i][j].getNeighbour(string).getNeighbour(string).owner != -1) {
							if (struktur[i][j].getNeighbour(string).getNeighbour(string).owner == struktur[i][j]
									.getNeighbour(string).getNeighbour(string).getNeighbour(string).owner
									&& struktur[i][j].getNeighbour(string).getNeighbour(string).owner != -1
									&& struktur[i][j].getNeighbour(string).getNeighbour(string)
											.getNeighbour(string).owner != -1) {
								System.out.println("WINNER");
								return true;
							}
						}

					}
				}

			}
		}
		return false;

	}

	public void changePointer(int direction) {
		switch (direction) {
		case 1:
			for (int i = 0; i < control.length; i++) {
				if (control[i].pointer == true) {
					System.out.println("ja");
					if (i > 0 && i < (control.length)) {
						control[i].setPointer(false);
						control[i - 1].setPointer(true);
					}
					return;

				}
			}
			break;
		case 2:
			for (int i = 0; i < control.length; i++) {
				if (control[i].pointer == true) {
					System.out.println("ja");
					if (i >= 0 && i < (control.length) - 1) {
						control[i].setPointer(false);
						control[i + 1].setPointer(true);
					}
					return;

				}
			}
			break;
		default:
			break;
		}
	}

	public void throwCoin(int player) {
		for (int i = 0; i < control.length; i++) {
			if (control[i].pointer == true) {
				for (int j = struktur.length - 1; j >= 0; j--) {
					if (struktur[j][i].isFilled == false) {
						struktur[j][i].fill(player);
						return;
					}

				}
				return;
			}
		}

	}
}
