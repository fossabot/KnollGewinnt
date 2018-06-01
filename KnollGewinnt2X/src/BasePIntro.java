import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class BasePIntro extends JPanel {

	private PlayPanel[][] struktur;
	private GroundPanel[] control;
	public BasePIntro(int x, int y) {
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
		control[control.length - 1].setPointer(true, 1);
		
		for (int i = 0; i < struktur.length; i++) {
			struktur[i][0].setBackground(Color.RED);
		}
		struktur[0][2].setBackground(Color.RED);
		struktur[1][1].setBackground(Color.RED);
		struktur[3][1].setBackground(Color.RED);
		struktur[4][2].setBackground(Color.RED);
		struktur[4][6].setBackground(Color.RED);

	}

	

}
