/*GroundPanel for use with KnollGewinnt
*(c)2018
*/
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GroundPanel extends JPanel {

	boolean pointer=false;
	
	public void setPointer(boolean pointer, int player) {
		
		this.pointer = pointer;
		if(pointer==true) {
			switch (player) {
			case 1:
				this.setBackground(Color.blue);
				break;

			case 2:
				this.setBackground(Color.green);
				break;
			}
		}else {
			this.setBackground(Color.GRAY);
		}
	}
	
	public void activateBorder(Color bg) {
		this.setBorder(BorderFactory.createLineBorder(bg));
	}
	
	public GroundPanel() {
		super();
		System.out.println("**GROUND PANEL CREATED**");
		this.setBackground(Color.GRAY);
		this.pointer=false;
		
	}
	

}
