package testPackage;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;

import javax.swing.border.LineBorder;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import gamePackage.GroundPanel;

public class GroundPanelTest4 {

	private GroundPanel g;
	

	@Test
	@DisplayName("Standard GroundPanelTest")
	public void test() {
		System.out.println("GroundPanelTest");
		g = new GroundPanel();
		if(g==null) fail("GroundPanel is NULL");
		assertEquals(Color.GRAY, g.getBackground());
		assertEquals(Color.BLACK,((LineBorder)g.getBorder()).getLineColor());
		assertEquals(false, g.getPointer());
		
		
		for (int i = -1000; i < 1000; i++) {
			g.setPointer(false, i);
			assertEquals(Color.GRAY, g.getBackground());
		}
		
		g.setPointer(true, 1);
		assertEquals(Color.blue, g.getBackground());
		
		g.setPointer(true, 2);
		assertEquals(Color.green, g.getBackground());
		
		for (int i = -1000; i < 1; i++) {
			g.setPointer(true, i);
			assertEquals(Color.GRAY, g.getBackground());
		}
		
		for (int i = 3; i < 1000; i++) {
			g.setPointer(true, i);
			assertEquals(Color.GRAY, g.getBackground());
		}
	}

}
