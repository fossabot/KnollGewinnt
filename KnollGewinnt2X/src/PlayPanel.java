/*PlayPanel for use with KnollGewinnt
*(c)2018
*/
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlayPanel extends JPanel {

	boolean isFilled;
	public PlayPanel n, o, s, w, no, so, sw, nw;
	public int owner;
	
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
		System.out.println("Neighbours added: " + n + o + s + w+ no + so + sw + nw);
		
	}
	
	public PlayPanel getNeighbour(String direction) {
		switch (direction) {
		case "n":
			return n;
		case "o":
			return o;
		case "s":
			return s;
		case "w":
			return w;
		case "no":
			return no;
		case "so":
			return so;
		case "sw":
			return sw;
		case "nw":
			return nw;
		default:
			return null;
		}
	}
	public void fill(int player) {
		this.owner=player;
		switch (owner) {
		case -1:
			this.setBackground(Color.WHITE);
			break;
		case 1:
			this.setBackground(Color.BLUE);
			isFilled=true;
			break;
		case 2:
			this.setBackground(Color.GREEN);
			isFilled=true;
			break;
		}
	}
	
	public void activateBorder(Color bg) {
		this.setBorder(BorderFactory.createLineBorder(bg));
	}

}
