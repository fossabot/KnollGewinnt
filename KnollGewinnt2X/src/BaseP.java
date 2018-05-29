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
		struktur = new PlayPanel[y-1][x];
		for (int i = 0; i < struktur.length; i++) {
			for (int j = 0; j < struktur.length; j++) {
				struktur[i][j]=new PlayPanel();
				struktur[i][j].activateBorder(Color.BLACK);
				this.add(struktur[i][j]);
			}
		}
		control = new GroundPanel[x];
		for (int i = 0; i < control.length; i++) {
			control[i]=new GroundPanel();
			control[i].activateBorder(Color.BLACK);
			this.add(control[i]);
		}
		
	}

}
 