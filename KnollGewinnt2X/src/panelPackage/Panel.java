/** 
 * KNOLL GEWINNT powered by javax.swing
 * INTERFACE: KnollPanel
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
package panelPackage;

import java.awt.Color;

public interface Panel {
	boolean filled=false;
	
	public void activateBorder(Color color);
	public void fill(int player, boolean b);
}
