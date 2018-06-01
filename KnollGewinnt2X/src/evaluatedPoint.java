import java.awt.Dimension;

public class evaluatedPoint {

	
	Dimension point;
	int prio;
	int owner;
	Dimension nextFreeInLine;
	String direction;
	public evaluatedPoint(Dimension point, int prio, int owner, String direction) {
		if(owner==3) {
			this.owner=owner;
			this.point=point;
			this.prio=prio+1;
			this.direction=direction;
		}else if (owner==1) {
			this.owner=owner;
			this.point=point;
			this.prio=prio;
			this.direction=direction;
		}
		nextFreeInLine=calculatenextFree();
		
		
	}
	public evaluatedPoint(int x, int y, int prio, int owner, String direction) {
		this.owner=owner;
		this.point=new Dimension(y, y);
		this.prio=prio;
		this.direction=direction;
		nextFreeInLine=calculatenextFree();
	}
	private Dimension calculatenextFree() {
		switch (direction) {
		case "n":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width, point.height-3);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width, point.height-2);
			}
			
			return null;
		case "o":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width+3, point.height);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width+2, point.height);
			}
			return null;
		case "s":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width, point.height+3);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width, point.height+2);
			}
			return null;
		case "w":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width-3, point.height);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width-2, point.height);
			}
			return null;
		case "no":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width+3, point.height-3);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width+2, point.height-2);
			}
			return null;
		case "so":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width+3, point.height+3);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width+2, point.height+2);
			}
			return null;
		case "sw":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width-3, point.height+3);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width-2, point.height+2);
			}
			return null;
		case "nw":
			if(prio==1 || prio ==2) {
				return new Dimension(point.width-3, point.height-3);
			}else if(prio==3 || prio ==4) {
				return new Dimension(point.width-2, point.height-2);
			}
			return null;
		default:
			return null;}
	}
	public Dimension getNextFreeInLine() {
		return nextFreeInLine;
	}
	public Dimension getPoint() {
		return point;
	}
	
	public int getPrio() {
		return prio;
	}
	
	public int getOwner() {
		return owner;
	}
	
	
	

}
