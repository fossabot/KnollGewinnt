package panelPackage;


import java.net.URL;

import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EasterEgg extends JPanel{


	public EasterEgg() throws Exception {
		super();
		this.add(new JLabel(new ImageIcon(EasterEgg.class.getResource("KnollTurn.gif"))));
		this.setVisible(true);	
	}
	

	
}
