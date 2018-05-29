/*MainFrame for use with KnollGewinnt
*(c)2018
*/
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	public static void main(String[] args) {
		new MainFrame();
	}
	
	public MainFrame()  {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(new BaseP(6, 6), BorderLayout.CENTER);

		this.getContentPane().add(new JButton("Test"), BorderLayout.EAST);
		pack();
		this.setVisible(true);
	}
}
