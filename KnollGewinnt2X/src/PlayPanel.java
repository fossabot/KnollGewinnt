/*PlayPanel for use with KnollGewinnt
*(c)2018
*/
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlayPanel extends JPanel {

	boolean isFilled;
	PlayPanel n, o, s, w, no, so, sw, nw;
	int owner;
	
	public PlayPanel() {
		super();
		this.isFilled=false;
		this.owner=-1;
		System.out.println("**PLAY PANEL CREATED**");
	}
	
	public void setNeighbours(PlayPanel n, PlayPanel o, PlayPanel s, PlayPanel w, PlayPanel no, PlayPanel so, PlayPanel sw, PlayPanel nw) {
		this.n=n;
		this.o=o;
		this.s=s;
		this.w=w;
		this.no=no;
		this.so=so;
		this.sw=sw;
		this.nw=nw;
	}
	
	public void fill() {
		switch (owner) {
		case -1:
			this.setBackground(Color.WHITE);
			break;
		case 1:
			this.setBackground(Color.BLUE);
			break;
		case 2:
			this.setBackground(Color.GREEN);
			break;
		}
	}
	
	public void activateBorder(Color bg) {
		this.setBorder(BorderFactory.createLineBorder(bg));
	}

}
