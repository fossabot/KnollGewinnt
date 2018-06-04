
/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: MainFrame
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert
 * @version 0.1
 * (c) 2018
 */
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.text.*;

public class MainFrame extends JFrame {

	private static final int left = 1;
	private static final int right = 2;
	private BasePanel tog;
	private int currentPlayer = 1;
	private ConfigPanel panel;
	private Boolean won = false;
	private ActionListener newGameAction;
	private AWTEventListener awt;
	private JLabel manualGame;
	private int selectedMode; // '1'  singlePlayer '2'  multiPlayer 

	public MainFrame() {
		init();

	}

	/**
	 * Initializes the MainFrame.
	 * 
	 * @param tog
	 *            - BasePanel which includes the GroundPanels and Playpanels to
	 *            display the field.
	 * @param newGameAction
	 *            - ActionListener for the Buttons of the ConfigPanel
	 * @param panel
	 *            - ConfigPanel which includes the Buttons
	 * @param manualGame
	 *            - JLabel with instructions how to play
	 */
	private void init() {
		consoleIntro();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Knoll Gewinnt Ver.0.1");
		this.setLayout(new BorderLayout());

		tog = new BasePanel(7, 6);
		
		manualGame = new JLabel(
				"<html><br><br><br>WELCOME TO KNOLL GEWINNT VER.0.1 <br>Press 'A' to move pointer left. Press 'D' to move pointer right. Press 'S' to throw coin. <html>");
		manualGame.setFont(new Font("Calibri", Font.PLAIN, 20));
		newGameAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == panel.newGame) {

					resetFrame(panel.selectedMode());
				} else if (e.getSource() == panel.save) {

				}

			}
		};
		panel = new ConfigPanel(newGameAction);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		this.getContentPane().add(panel, BorderLayout.EAST);
		this.getContentPane().add(manualGame, BorderLayout.SOUTH);
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(800, 400));
		this.setVisible(true);
		pack();
		eventListener();
	}


	/**
	 * consoleIntro
	 */
	static void consoleIntro() {

		System.out.println("***********************************************");
		System.out.println("*          KNOLL GEWINNT VER. 0.1             *");
		System.out.println("*                   by...                     *");
		System.out.println("*TEMPCorp 2018 TimoEliasMoritzPaulC(aspar)orp *");
		System.out.println("*                initialCommitDate 29.05.2018 *");
		System.out.println("***********************************************");
	}

	
	/**
	 * Generates an eventListener to recognize pressed Keys.
	 * @param awt - AWTEventListener to process the KeyEvents.
	 */
	private void eventListener() {
		awt = new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				switch (event.getID()) {
				case KeyEvent.KEY_RELEASED:
					System.out.println(System.currentTimeMillis() + ": "
							+ ((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()));

					if (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("a")
							|| ((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("A")) {
						tog.changePointer(left);

					} else if (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("d")
							|| ((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("D")) {
						tog.changePointer(right);
					} else if ((((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("s")
							&& won == false)
							|| (((KeyEvent) event).getKeyText(((KeyEvent) event).getKeyCode()).equals("S")
									&& won == false)) {
						tog.throwCoin(currentPlayer);
						if (tog.evaluateRows() == true) {
							won = true;
							displayWinner(currentPlayer);

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

	/**
	 * Switches the Player depending on the current value of Player.
	 */
	
	private void switchPlayer() {
		switch (currentPlayer) {
		case 1:
			currentPlayer = 2;
			tog.player = 2;
			tog.changePlayer();
			System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
			break;
		case 2:
			currentPlayer = 1;
			tog.player = 1;
			tog.changePlayer();
			System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
			break;
		}

	}

	/**
	 * Resets the Frame to start a new Game and recognize changement of the selected Mode
	 * @param selectedModeInt - '1'  singlePlayer '2'  multiPlayer 
	 */
	
	private void resetFrame(int selectedModeInt) {
		this.selectedMode = selectedModeInt;
		won = false;
		this.remove(tog);
		tog = new BasePanel(7, 6);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		pack();
		this.setVisible(true);
		this.getToolkit().removeAWTEventListener(awt);
		panel.setWin(-1);
		eventListener();

	}

}
