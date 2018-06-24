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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.text.*;

public class MainFrame extends JFrame {

	private static final int left = 1;
	private static final int right = 2;
	private int player1Count = 0;
	private int player2Count = 0;
	private BasePanel tog;
	private int currentPlayer = 1;
	private ConfigPanel panel;
	private Boolean won = false;
	private ActionListener newGameAction;
	private ActionListener profileSelected;
	private AWTEventListener awt;
	private JLabel manualGame;
	private int selectedMode; // '1' singlePlayer '2' multiPlayer
	private JButton select;
	private JOptionPane testPane;
	private JComboBox playersList;
	private HashMap<String, KnollPlayer> playersMap;
	private KnollPlayer player1;
	private KnollPlayer player2;
	private JDialog singlePlayerDialog;
	private JButton selectMultiPlayer;
	private JComboBox playersList2;
	private ItemListener gameModeListener;

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
		initMenuBar();
		try {
			refreshProfiles();
		} catch (IOException e2) {
		}
		manualGame = new JLabel(
				"<html><br>WELCOME TO KNOLL GEWINNT VER.0.1 <br>SEE HELP FOR INSTRUCTIONS.<br> @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz<html>");
		manualGame.setFont(new Font("Calibri", Font.PLAIN, 20));
		newGameAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == panel.newGame) {
					resetFrame(panel.selectedMode(), player1, player2);
					openPlayersChoice();
				} else if (e.getSource() == panel.save) {
					try {
						saveGame();
					} catch (IOException e1) {
						dataErrorMessage();
					}
				} else if (e.getSource() == panel.load) {
					try {
						loadGame();
					} catch (IOException e1) {
						dataErrorMessage();
					}
				}

			}
		};

		panel = new ConfigPanel(newGameAction);
		gameModeListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				selectedMode = panel.selectedMode();
				panel.setPlayers(null, null);
				resetFrame(selectedMode, null, null);
				openPlayersChoice();
			}
		};
		panel.addChangeListener(gameModeListener);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		this.getContentPane().add(panel, BorderLayout.EAST);
		this.getContentPane().add(manualGame, BorderLayout.SOUTH);
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(800, 400));
		this.setResizable(false);
		this.setVisible(true);
		pack();
		eventListener();

	}

	private void initMenuBar() {
		JMenuItem menuItemFileExit = new JMenuItem("Exit");
		JMenuItem menueItemFileProfiles = new JMenuItem("Profiles");
		JMenuItem menueItemFileAddProfiles = new JMenuItem("Add Profile");
		JMenuItem menueItemFileStats = new JMenuItem("Stats");
		JMenuItem menueItemHelpAbout = new JMenuItem("About");
		JMenuItem menueItemHelpHelp = new JMenuItem("Help");
		ActionListener actionMenu = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == menuItemFileExit) {
					System.exit(0);
				}
				if (e.getSource() == menueItemHelpAbout) {
					JOptionPane.showMessageDialog(null,
							"<html>KNOLL GEWINNT VER.0.1 <br>@since 29.05.2018<br>@version 0.1<br>@repository github.com/tmbchrt/knollgewinnt<br>@author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz  <html>",
							"About", JOptionPane.INFORMATION_MESSAGE);
				}
				if (e.getSource() == menueItemHelpHelp) {
					JOptionPane.showMessageDialog(null,
							"<html>Press 'A' to move pointer left. Press 'D' to move pointer right.<br>Press 'S' to throw coin.<br>Try to get 4 in a row<br> <html>",
							"Help", JOptionPane.INFORMATION_MESSAGE);
				}

				if (e.getSource() == menueItemFileProfiles) {
					openPlayersChoice();
				}
				if (e.getSource() == menueItemFileAddProfiles) {
					String name = JOptionPane.showInputDialog("Enter new Players name: ");
					playersMap.put(name, new KnollPlayer(name, 0, 0, 0));
					try {
						updateStats();
					} catch (IOException e1) {
						dataErrorMessage();
					}
				}
				if (e.getSource() == menueItemFileStats) {
					try {
						updateStats();
						String[][] rows = new String[playersMap.keySet().size()][4];
						String[] cols = { "Name", "Played Games", "Wins", "Steps to Win" };
						Iterator<String> j = playersMap.keySet().iterator();
						while (j.hasNext()) {
							for (int i = 0; i < rows.length; i++) {
								String next = j.next();
								rows[i][0] = playersMap.get(next).getName();
								rows[i][1] = Integer.toString(playersMap.get(next).getPlayedGames());
								rows[i][2] = Integer.toString(playersMap.get(next).getWins());
								rows[i][3] = Integer.toString(playersMap.get(next).getStepsToWin());
							}
						}

						JTable table = new JTable(rows, cols);
						JOptionPane.showMessageDialog(null, new JScrollPane(table));
					} catch (IOException e1) {
						dataErrorMessage();
					}

				}

			}

			/**
			 * 
			 */

		};

		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuFile);
		menuBar.add(menuHelp);

		menuFile.add(menueItemFileProfiles);
		menueItemFileProfiles.addActionListener(actionMenu);

		menuFile.add(menueItemFileAddProfiles);
		menueItemFileAddProfiles.addActionListener(actionMenu);

		menuFile.add(menueItemFileStats);
		menueItemFileStats.addActionListener(actionMenu);

		menuFile.add(menuItemFileExit);
		menuItemFileExit.addActionListener(actionMenu);

		menuHelp.add(menueItemHelpAbout);
		menueItemHelpAbout.addActionListener(actionMenu);

		menuHelp.add(menueItemHelpHelp);
		menueItemHelpHelp.addActionListener(actionMenu);

		setJMenuBar(menuBar);

	}

	private void openPlayersChoice() {
		if (selectedMode == 1)
			try {
				singlePlayerProfileChoice();
			} catch (IOException e1) {
				dataErrorMessage();

			}
		if (selectedMode == 2)
			try {
				multiPlayerProfileChoice();
			} catch (IOException e1) {
				dataErrorMessage();

			}
	}

	protected void multiPlayerProfileChoice() throws IOException {
		refreshProfiles();
		String[] playersStringArray = new String[playersMap.keySet().size() - 1];
		Iterator<String> i = playersMap.keySet().iterator();
		int j = 0;
		while (i.hasNext()) {
			String next = i.next();
			if (!(next.equals("KI")))
				playersStringArray[j] = next;
			j++;
		}

		testPane = new JOptionPane("Select Player Profiles", JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION,
				null, new Object[] {}, null);
		testPane.add(new JLabel("Player 1: "));
		playersList = new JComboBox(playersStringArray);
		testPane.add(playersList);
		testPane.add(new JLabel("Player 2: "));
		playersList2 = new JComboBox(playersStringArray);
		testPane.add(playersList2);
		selectMultiPlayer = new JButton("Select");
		testPane.add(selectMultiPlayer);
		selectMultiPlayer.addActionListener(profileSelected);

		singlePlayerDialog = testPane.createDialog(null, "MultiPlayer Profile Choice");
		singlePlayerDialog.setVisible(true);

	}

	protected void singlePlayerProfileChoice() throws IOException {
		refreshProfiles();
		String[] playersStringArray = new String[playersMap.keySet().size() - 1];
		Iterator<String> i = playersMap.keySet().iterator();
		int j = 0;
		while (i.hasNext()) {
			String next = i.next();
			if (!(next.equals("KI")))
				playersStringArray[j] = next;
			j++;
		}

		testPane = new JOptionPane("Select your Player Profile", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		playersList = new JComboBox(playersStringArray);
		testPane.add(playersList);
		select = new JButton("Select");
		testPane.add(select);
		select.addActionListener(profileSelected);

		singlePlayerDialog = testPane.createDialog(null, "SinglePlayer Profile Choice");
		singlePlayerDialog.setVisible(true);
	}

	private void refreshProfiles() throws IOException {
		FileReader fr = new FileReader("profiles.kg");
		BufferedReader br = new BufferedReader(fr);
		int amountOfRegisteredPlayers = 0;
		ArrayList<KnollPlayer> players = new ArrayList<>();

		if (br.readLine().equals("<HEAD>KNOLLGEWINNT PROFILES<HEAD>")) {
			amountOfRegisteredPlayers = Integer.parseInt((br.readLine().split("'")[1]));
			for (int i = 0; i < amountOfRegisteredPlayers; i++) {
				String[] actualLine = br.readLine().split("\\.");
				players.add(new KnollPlayer(actualLine[0], Integer.parseInt(actualLine[1]),
						Integer.parseInt(actualLine[2]), 0));
			}
			br.close();
			HashMap<String, KnollPlayer> playerObjects = new HashMap<>();
			Iterator<KnollPlayer> i = players.iterator();
			while (i.hasNext()) {
				KnollPlayer p = i.next();
				playerObjects.put(p.getName(), p);
			}
			this.playersMap = playerObjects;
		} else {
			dataErrorMessage();
		}
	}

	protected void loadGame() throws IOException {
		FileReader fr = new FileReader("save.kg");
		BufferedReader br = new BufferedReader(fr);

		String[] rows = new String[tog.getPlayBoard().length];
		int readMode = 1;
		if (br.readLine().equals("<HEAD>KNOLLGEWINNT SAVINGS<HEAD>")) {

			try {
				readMode = Integer.parseInt(br.readLine().split("'")[3]);
			} catch (NumberFormatException e) {
				dataError(br);
			}
			for (int i = 0; i < rows.length; i++) {
				rows[i] = br.readLine();
			}
		} else {
			dataError(br);
		}
		br.close();
		try {
			resumeGame(readMode, rows, br);
		} catch (Exception e) {
			dataError(br);
		}
		JOptionPane.showMessageDialog(this, "Game succesfully loaded.");
	}

	/**
	 * @param br
	 * @throws IOException
	 */
	private void dataError(BufferedReader br) throws IOException {
		JOptionPane.showMessageDialog(this, "Corrupt File Error.", "Warning", JOptionPane.WARNING_MESSAGE);
		br.close();
		throw new IOException("Corrupt File Error!");
	}

	private void dataErrorMessage() {
		JOptionPane.showMessageDialog(this, "Corrupt File Error.", "Warning", JOptionPane.WARNING_MESSAGE);
		int reply = JOptionPane.showConfirmDialog(this, "Create new DATA.KG Files?", "Confirm Dialog",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			try {
				FileWriter fw = new FileWriter("profiles.kg");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("<HEAD>KNOLLGEWINNT PROFILES<HEAD>");
				bw.newLine();
				bw.write("<INFO> PLAYERS: '" + 1 + "' <INFO>");
				bw.newLine();
				bw.write("KI.0.0.0");
				bw.close();
				fw.close();
				refreshProfiles();
			} catch (IOException e) {
				dataErrorMessage();
			}
		}
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
							if (player1 == null || player2 == null)
								return;
							tog.throwCoin(currentPlayer);
							if (currentPlayer == 1)
								player1Count++;
							if (currentPlayer == 2 || currentPlayer == 3)
								player2Count++;
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (tog.evaluateRows() == true) {
							won = true;
							displayWinner(currentPlayer);
							try {
								switch (currentPlayer) {
								case 1:
									player1.setWin(player1Count);
									updateStats();
									break;
								case 2:
									player2.setWin(player2Count);
									updateStats();
									break;
								case 3:
									player2.setWin(player2Count);
									updateStats();
									break;
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							stopGame();

						}

						try {

							switchPlayer();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					break;
				}

			}

		};
		this.getToolkit().addAWTEventListener(awt, AWTEvent.KEY_EVENT_MASK);

		profileSelected = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == select) {
					player1 = playersMap.get(playersList.getSelectedItem());
					player2 = playersMap.get(new String("KI"));
					resetFrame(selectedMode, player1, player2);
					System.out.println(System.currentTimeMillis() + ": PLAYER1: " + player1.getName());
					System.out.println(System.currentTimeMillis() + ": PLAYER2: " + player2.getName());
					singlePlayerDialog.dispose();
				}
				if (e.getSource() == selectMultiPlayer) {
					player1 = playersMap.get(playersList.getSelectedItem());
					player2 = playersMap.get(playersList2.getSelectedItem());
					resetFrame(selectedMode, player1, player2);
					System.out.println(System.currentTimeMillis() + ": PLAYER1: " + player1.getName());
					System.out.println(System.currentTimeMillis() + ": PLAYER2: " + player2.getName());
					singlePlayerDialog.dispose();
				}

			}
		};

	}

	protected void updateStats() throws IOException {
		FileWriter fw = new FileWriter("profiles.kg");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("<HEAD>KNOLLGEWINNT PROFILES<HEAD>");
		bw.newLine();
		bw.write("<INFO> PLAYERS: '" + playersMap.keySet().size() + "' <INFO>");
		bw.newLine();
		Iterator<String> i = playersMap.keySet().iterator();
		while (i.hasNext()) {
			String next = i.next();
			bw.write(playersMap.get(next).getName() + "." + playersMap.get(next).getPlayedGames() + "."
					+ playersMap.get(next).getWins() + "." + playersMap.get(next).getStepsToWin());
			bw.newLine();
		}
		bw.close();
		fw.close();
		JOptionPane.showMessageDialog(this, "Update succesful!");

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
			player2Count++;
			if (tog.evaluateRows() == true) {
				won = true;
				displayWinner(currentPlayer);
				player2.setWin(player2Count);
				updateStats();
				stopGame();

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

	private void resetFrame(int selectedModeInt, KnollPlayer player1, KnollPlayer player2) {
		this.selectedMode = selectedModeInt;
		this.player1 = player1;
		this.player2 = player2;
		panel.setPlayers(player1, player2);
		player1Count = 0;
		player2Count = 0;
		currentPlayer = 1;
		tog.player = 1;
		won = false;
		this.remove(tog);
		tog = new BasePanel(7, 7);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		pack();
		this.setVisible(true);
		this.getToolkit().removeAWTEventListener(awt);
		panel.setWin(-1);
		eventListener();

	}

	public void resumeGame(int readMode, String[] rows, BufferedReader br) throws Exception {
		String[] temp = new String[tog.getPlayBoard()[0].length];
		for (int i = 0; i < tog.getPlayBoard().length; i++) {
			temp = rows[i].split("-");
			for (int j = 0; j < tog.getPlayBoard()[i].length; j++) {
				String[] temp2 = temp[j].split("\\.");
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
						tog.getPlayBoard()[i][j].fill(3, false);
						break;
					}

				}

			}
		}
		this.selectedMode = readMode;
		panel.setMode(readMode);
	}

}
