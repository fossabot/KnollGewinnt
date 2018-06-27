package gamePackage;


import java.net.URL;

import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class KnollEasterPanel extends JPanel{


	public KnollEasterPanel() throws Exception {
		super();
		
		this.add(new JLabel(new ImageIcon(new URL("file:./KnollTurn.gif"))) );
		this.setVisible(true);	


	}
	

	
}
