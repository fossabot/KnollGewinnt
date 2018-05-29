
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

		provideNeighbours();

	}

	private void provideNeighbours() {

		for (int i = 0; i < struktur.length; i++) {
			for (int j = 0; j < struktur[i].length; j++) {
				if (i == 0 && j == 0) {// links oben
					struktur[i][j].setNeighbours(null, struktur[i][j + 1], struktur[i + 1][j], null, null,
							struktur[i + 1][j + 1], null, null);
				} else if (i == 0 && j == struktur[i].length - 1) {// rechts oben
					struktur[i][j].setNeighbours(null, null, struktur[i + 1][j], struktur[i][j - 1], null, null,
							struktur[i + 1][j - 1], null);
				} else if (i == struktur.length - 1 && j == 0) {// links unten
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], null, null,
							struktur[i - 1][j + 1], null, null, null);
				} else if (i == struktur.length - 1 && j == struktur[i].length - 1) {// rechts unten
					struktur[i][j].setNeighbours(struktur[i - 1][j], null, null, struktur[i][j - 1], null, null, null,
							struktur[i - 1][j - 1]);
				} else if (i == 0 && j != 0 && j != struktur[i].length - 1) {// oberer Rand
					struktur[i][j].setNeighbours(null, struktur[i][j + 1], struktur[i + 1][j], struktur[i][j - 1], null,
							struktur[i + 1][j + 1], struktur[i + 1][j - 1], null);
				} else if (i == struktur.length - 1 && j != 0 && j != struktur[i].length - 1) {// unterer Rand
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], null, struktur[i][j - 1],
							struktur[i - 1][j + 1], null, null, struktur[i - 1][j - 1]);
				} else if (i != 0 && i != struktur.length - 1 && j == struktur[i].length - 1) {// rechter Rand
					struktur[i][j].setNeighbours(struktur[i - 1][j], null, struktur[i + 1][j], struktur[i][j - 1], null,
							null, struktur[i + 1][j - 1], struktur[i - 1][j - 1]);
				} else if (i != 0 && i != struktur.length - 1 && j == 0) {// linker Rand
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], struktur[i + 1][j], null,
							struktur[i - 1][j + 1], struktur[i + 1][j + 1], null, null);
				} else {
					struktur[i][j].setNeighbours(struktur[i - 1][j], struktur[i][j + 1], struktur[i + 1][j],
							struktur[i][j - 1], struktur[i - 1][j + 1], struktur[i + 1][j + 1], struktur[i + 1][j - 1],
							struktur[i - 1][j - 1]);
				}

			}
		}

	}

}
