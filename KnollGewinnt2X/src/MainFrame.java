
/*MainFrame for use with KnollGewinnt
*(c)2018
*/
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class MainFrame extends JFrame {

	BaseP tog;
	int player = 1;
	ConfigPanel panel;
	JTextComponent statusGame;
	Boolean won = false;
	private ActionListener newGameAction;
	private AWTEventListener awt;
	private JLabel manualGame;
	int selectedMode; //'1' entspricht singlePlayer '2' steht für den multiPlayer Modus

	public static void main(String[] args) {
		consoleIntro();
		new MainFrame();
	}

	public MainFrame() {
		init();
	}

	/**
	 * 
	 */
	private void init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Knoll Gewinnt Ver.0.1");
		this.setLayout(new BorderLayout());
		tog = new BaseP(7, 6);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		newGameAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==panel.newGame) {
					
					resetFrame(panel.selectedMode());
				}else if (e.getSource()==panel.save) {
					
				}
				

			}
		};
		panel = new ConfigPanel(newGameAction);
		this.getContentPane().add(panel, BorderLayout.EAST);
		manualGame = new JLabel("<html><br><br><br>WELCOME TO KNOLL GEWINNT VER.0.1 <br>Press 'A' to move pointer left. Press 'D' to move pointer right. Press 'S' to throw coin. <html>");
		manualGame.setFont(new Font("Calibri", Font.PLAIN, 20));
		this.getContentPane().add(manualGame, BorderLayout.SOUTH);

		pack();
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(800, 400));
		this.setVisible(true);
		eventListener();
	}

	static void consoleIntro() {
		System.out.println("***********************************************");
		System.out.println("*          KNOLL GEWINNT VER. 0.1             *");
		System.out.println("*                   by...                     *");
		System.out.println("*TEMPCorp 2018 TimoEliasMoritzPaulC(aspar)orp *");
		System.out.println("*                initialCommitDate 29.05.2018 *");
		System.out.println("***********************************************");
	}

	private void eventListener() {
		awt = new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				switch (event.getID()) {
				case KeyEvent.KEY_RELEASED:
					System.out.println(((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()));

					if (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("a")
							|| ((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("A")) {
						tog.changePointer(1);

					} else if (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("d")
							|| ((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("D")) {
						tog.changePointer(2);
					} else if ((((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("s")
							&& won == false)
							|| (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("S")
									&& won == false)) {
						tog.throwCoin(player);
						if (tog.evaluateRows() == true) {
							won = true;
							displayWinner(player);

						}

						switchPlayer();

					}
					break;
				}

			}

			private void displayWinner(int player) {
				panel.setWin(player);

			}

		};
		this.getToolkit().addAWTEventListener(awt, AWTEvent.KEY_EVENT_MASK);

	}

	private void switchPlayer() {
		switch (player) {
		case 1:
			player = 2;
			tog.player = 2;
			tog.changePlayer();
			System.out.println("Turn of Player: " + player);
			break;
		case 2:
			player = 1;
			tog.player = 1;
			tog.changePlayer();
			System.out.println("Turn of Player: " + player);
			break;
		}

	}

	private void resetFrame(int selectedModeInt) {
		this.selectedMode = selectedModeInt;
		won = false;
		this.remove(tog);
		tog = new BaseP(7, 6);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		pack();
		this.setVisible(true);
		this.getToolkit().removeAWTEventListener(awt);
		panel.setWin(-1);
		eventListener();

	}

}
