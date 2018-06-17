package testPackage;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;

import javax.swing.border.LineBorder;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import gamePackage.PlayPanel;

public class PlayPanelTest4 {

	private PlayPanel p;

	@Test
	@DisplayName("Standard PlayPanelTest")
	public void test() {
		System.out.println("PlayPanelTest");
		p = new PlayPanel();
		if(p==null) fail("PlayPanel is NULL");
		assertEquals(false, p.isFilled());
		assertEquals(-1, p.getOwner());
		assertEquals(Color.BLACK,((LineBorder)p.getBorder()).getLineColor());
		try {
			p.fill(1);
		} catch (Exception e) {
			fail("Expected no Exception because p wasnt filled.");
		}

		try {
			p.fill(2);
			fail("Expected an Exception because p was already filled.");
		} catch (Exception e) {
			assertEquals(Color.BLUE, p.getBackground());
			assertEquals(true, p.isFilled());
		}
	}

}
