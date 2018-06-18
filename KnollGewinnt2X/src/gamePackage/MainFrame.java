package gamePackage;

/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: MainFrame
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	private int selectedMode; // '1' singlePlayer '2' multiPlayer

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
		this.selectedMode = 1;
		tog = new BasePanel(7, 7);

		manualGame = new JLabel(
				"<html><br><br><br>WELCOME TO KNOLL GEWINNT VER.0.1 <br>Press 'A' to move pointer left. Press 'D' to move pointer right. Press 'S' to throw coin. <html>");
		manualGame.setFont(new Font("Calibri", Font.PLAIN, 20));
		newGameAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == panel.newGame) {
					resetFrame(panel.selectedMode());
				} else if (e.getSource() == panel.save) {
					try {
						saveGame();
					} catch (IOException e1) {
					}
				} else if (e.getSource() == panel.load) {
					try {
						loadGame();
					} catch (IOException e1) {
					}
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

	protected void loadGame() throws IOException {
	    FileReader fr = new FileReader("save.kg");
	    BufferedReader br = new BufferedReader(fr);

	    String [] rows = new String [tog.getPlayBoard().length];
	    int readMode=1;
	    if(br.readLine().equals("<HEAD>KNOLLGEWINNT SAVINGS<HEAD>")) {
	    	readMode = Integer.parseInt(br.readLine().split("'")[3]);
	    	 for (int i = 0; i < rows.length; i++) {
	 			rows[i]=br.readLine();
	 		}
	    }else {
	    	throw new IOException("No valid savings File!");
	    }
	    br.close();
	    try {
			resumeGame(readMode, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    JOptionPane.showMessageDialog(this, "Game succesfully loaded.");
	}

	protected void saveGame() throws IOException {

		FileWriter fw = new FileWriter("save.kg");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("<HEAD>KNOLLGEWINNT SAVINGS<HEAD>");
		bw.newLine();
		bw.write("<GAMEINFO> TIME: '" + System.currentTimeMillis() + "' MODE: '" + this.selectedMode + "'<GAMEINFO>");
		bw.newLine();

		for (int i = 0; i < tog.getPlayBoard().length; i++) {
			for (int j = 0; j < tog.getPlayBoard()[i].length; j++) {
				if (tog.getPlayBoard()[i][j].isFilled() == true)
					bw.write("1.");
				if (tog.getPlayBoard()[i][j].isFilled() == false)
					bw.write("0.");
				if (tog.getPlayBoard()[i][j].getOwner() == -1)
					bw.write("NO");
				if (tog.getPlayBoard()[i][j].getOwner() == 1)
					bw.write("P1");
				if (tog.getPlayBoard()[i][j].getOwner() == 2)
					bw.write("P2");
				if (tog.getPlayBoard()[i][j].getOwner() == 3)
					bw.write("KI");
				bw.write("-");
			}
			bw.newLine();
		}

		bw.close();
		JOptionPane.showMessageDialog(this, "Game succesfully saved.");

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
	 * 
	 * @param awt
	 *            - AWTEventListener to process the KeyEvents.
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
						try {

							tog.throwCoin(currentPlayer);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (tog.evaluateRows() == true) {
							won = true;
							displayWinner(currentPlayer);
							stopGame();

						}

						try {
							switchPlayer();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					break;
				}

			}

		};
		this.getToolkit().addAWTEventListener(awt, AWTEvent.KEY_EVENT_MASK);

	}

	protected void stopGame() {
		this.getToolkit().removeAWTEventListener(awt);
	}

	private void displayWinner(int player) {
		panel.setWin(player);

	}

	/**
	 * Switches the Player depending on the current value of Player.
	 * 
	 * @throws Exception
	 */

	private void switchPlayer() throws Exception {
		switch (currentPlayer) {
		case 1:
			switch (this.selectedMode) {
			case 1:
				currentPlayer = 3;
				tog.player = 3;
				// tog.evaluatePlayablePanels();
				tog.changePlayer();
				System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
				letKIPlay();
				break;

			case 2:
				currentPlayer = 2;
				tog.player = 2;

				tog.changePlayer();
				System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
				break;
			}
			break;

		case 2:
			currentPlayer = 1;
			tog.player = 1;

			tog.changePlayer();
			System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
			break;

		case 3:
			currentPlayer = 1;
			tog.player = 1;

			tog.changePlayer();
			System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
			break;
		}

	}

	private void letKIPlay() throws Exception {
		tog.evaluatePlayablePanels();
		if (won == false) {
			tog.throwCoin(currentPlayer);
			if (tog.evaluateRows() == true) {
				won = true;
				displayWinner(currentPlayer);

			}

			switchPlayer();
		}

	}

	/**
	 * Resets the Frame to start a new Game and recognize changement of the selected
	 * Mode
	 * 
	 * @param selectedModeInt
	 *            - '1' singlePlayer '2' multiPlayer
	 */

	private void resetFrame(int selectedModeInt) {
		this.selectedMode = selectedModeInt;
		currentPlayer = 1;
		tog.player = 1;
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
	
	public void resumeGame(int readMode, String[] rows) throws Exception {
		String[] temp = new String[tog.getPlayBoard()[0].length];
		for (int i = 0; i < tog.getPlayBoard().length; i++) {
			temp = rows[i].split("-");
			for (int j = 0; j < tog.getPlayBoard()[i].length; j++) {
				String[] temp2=temp[j].split("\\.");
				switch (temp2[0]) {
				case "0":
					tog.getPlayBoard()[i][j].fill(-1, false);
					break;
				case "1":
					switch (temp[j].split("\\.")[1]) {
					case "NO":
						tog.getPlayBoard()[i][j].fill(-1, false);
						break;
					case "P1":
						tog.getPlayBoard()[i][j].fill(1, false);
						break;
					case "P2":
						tog.getPlayBoard()[i][j].fill(2, false);
						break;
					case "KI":
						tog.getPlayBoard()[i][j].fill(3,false);
						break;

					}
				}

			}
		}
		this.selectedMode=readMode;
		panel.setMode(readMode);
	}

}