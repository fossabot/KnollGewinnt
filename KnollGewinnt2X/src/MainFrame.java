
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
		consoleIntro();
		new MainFrame();
	}

	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(new BaseP(7, 6), BorderLayout.CENTER);

		pack();
		this.setVisible(true);
	}

	static void consoleIntro() {
		System.out.println("***********************************************");
		System.out.println("*          KNOLL GEWINNT VER. 0.1             *");
		System.out.println("*                   by...                     *");
		System.out.println("*                                             *");
		System.out.println("*                initialCommitDate 29.05.2018 *");
		System.out.println("***********************************************");
	}
}
