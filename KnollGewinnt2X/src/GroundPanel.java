/*GroundPanel for use with KnollGewinnt
*(c)2018
*/
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GroundPanel extends JPanel {

	boolean pointer=false;
	
	public void setPointer(boolean pointer) {
		
		this.pointer = pointer;
		if(pointer==true) {
			this.setBackground(Color.RED);
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
