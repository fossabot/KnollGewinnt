package testPackage;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import javax.swing.border.LineBorder;

import org.junit.Test;

import gamePackage.BasePanel;
import gamePackage.GroundPanel;
import gamePackage.PlayPanel;

public class BasePanelTest {

	private BasePanel b;
	private PlayPanel[][] playBoard;
	private GroundPanel[] control;
	
	@Test
	public void test() {
		System.out.println("BasePanelTest");
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				b = new BasePanel(i, j);
				playBoard=b.getPlayBoard();
				control=b.getControl();
				
				
				if (playBoard==null) fail("PlayBoard wasn't created");
				if (control==null) fail("Control wasn't created");
				
				for (int k = 0; i < playBoard.length; i++) {
					for (int l = 0; j < playBoard[k].length; j++) {
						assertEquals(-1, playBoard[k][l].getOwner());
						assertEquals(false, playBoard[k][l].isFilled());
						assertEquals(PlayPanel.class, playBoard[k][l].getClass());
					}
				}
				
				for (int m = 0; m < control.length-1; m++) {
					
					assertEquals(Color.GRAY, control[m].getBackground());
					assertEquals(Color.BLACK,((LineBorder)control[m].getBorder()).getLineColor());
					assertEquals(false, control[m].getPointer());
					assertEquals(GroundPanel.class, control[m].getClass());
				}
				
				assertEquals(Color.blue, control[control.length-1].getBackground());
			}
		}
	}

}
