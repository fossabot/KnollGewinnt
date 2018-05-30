
/*MainFrame for use with KnollGewinnt
*(c)2018
*/
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class MainFrame extends JFrame {

	BaseP tog;
	int player=1;
	JButton newGame;
	JTextComponent statusGame;
	Boolean won=false;
	public static void main(String[] args) {
		consoleIntro();
		new MainFrame();
	}

	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		tog = new BaseP(7, 6);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		newGame=new JButton("New Game");
		this.getContentPane().add(newGame, BorderLayout.EAST);

		statusGame=new JTextArea("");
		this.getContentPane().add(statusGame, BorderLayout.SOUTH);
		

		pack();
		this.setVisible(true);
		eventListener();
	}

	static void consoleIntro() {
		System.out.println("***********************************************");
		System.out.println("*          KNOLL GEWINNT VER. 0.1             *");
		System.out.println("*                   by...                     *");
		System.out.println("*                                             *");
		System.out.println("*                initialCommitDate 29.05.2018 *");
		System.out.println("***********************************************");
	}

	private void eventListener() {
		this.getToolkit().addAWTEventListener(new AWTEventListener() {


			@Override
			public void eventDispatched(AWTEvent event) {
				switch (event.getID()) {
				case KeyEvent.KEY_RELEASED:
					System.out.println(((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()));

					if (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("Links") || ((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("←")) {
						tog.changePointer(1);

					} else if (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("Rechts") ||((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("→")) {
						tog.changePointer(2);
					} else if ((((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("Leertaste")&&won==false )||(((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("␣")&&won==false )) {
						tog.throwCoin(player);
						if(tog.evaluateRows()==true) {
							won=true;
							displayWinner(player);
					
						};
						switchPlayer();
						
					}
				break;	
				}

			}

			private void displayWinner(int player) {
				statusGame.setText("WINNER_WINNNER_WINNER_WINNER_" + player);
				
			}


		}, AWTEvent.KEY_EVENT_MASK);

	}
	private void switchPlayer() {
		switch (player) {
		case 1:
			player=2;
			tog.player=2;
			tog.changePlayer();
			System.out.println("Turn of Player: " + player);
			break;
		case 2:
			player=1;
			tog.player=1 ;
			tog.changePlayer();
			System.out.println("Turn of Player: " + player);
			break;
		}
			
	}
	
	private void resetFrame() {
		tog = new BaseP(7, 6);
		statusGame=new JTextArea("************************************");
		
		
	}

}
