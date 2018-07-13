/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: MainFrame
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
package gamePackage;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import errorMessages.SavingsErrorMessage;
import handlerPackage.KnollProfilesHandler;
import handlerPackage.KnollSavingsHandler;
import panelPackage.BasePanel;
import panelPackage.ConfigPanel;
import panelPackage.KnollEasterPanel;

public class MainFrame extends JFrame {

	private static final int left = 1;
	private static final int right = 2;
	private int kCounter;
	private BasePanel basePanel;
	private int currentPlayer = 1;
	private ConfigPanel configPanel;
	private Boolean won = false;
	private ActionListener newGameAction;
	private ActionListener profileSelected;
	private AWTEventListener awt;
	private JLabel manualGame;
	private int selectedMode; // '1' singlePlayer '2' multiPlayer
	private JButton select;
	private JOptionPane selectionPane;
	private JComboBox<String> playersList;
	private HashMap<String, KnollPlayer> playersMap;
	private KnollPlayer player1;
	private KnollPlayer player2;
	private JDialog singlePlayerDialog;
	private JButton selectMultiPlayer;
	private JComboBox<String> playersList2;
	private ItemListener gameModeListener;
	private boolean playerset;
	private KnollAudioPlayer audioPlayer;
	private KnollSavingsHandler saver;
	private KnollProfilesHandler profSaver;

	public MainFrame() {
		init();

	}

	/**
	 * Initializes the MainFrame.
	 * 
	 * @param basePanel
	 *            - BasePanel which includes the GroundPanels and Playpanels to
	 *            display the field.
	 * @param newGameAction
	 *            - ActionListener for the Buttons of the ConfigPanel
	 * @param configPanel
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
		basePanel = new BasePanel(7, 7);
		initMenuBar();
		saver = new KnollSavingsHandler();
		profSaver = new KnollProfilesHandler();
		try {
			readProfilesFromFile();
		} catch (IOException e2) {
		}
		manualGame = new JLabel(
				"<html><br>WELCOME TO KNOLL GEWINNT VER.0.1 <br>SEE HELP FOR INSTRUCTIONS.<br> @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz<html>");
		manualGame.setFont(new Font("Calibri", Font.PLAIN, 20));
		newGameAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == configPanel.newGame) {
					resetFrame(configPanel.selectedMode(), player1, player2);
					openPlayersChoice();
				} else if (e.getSource() == configPanel.save) {
					try {
						if (won == true) {
							JOptionPane.showMessageDialog(null, "You can't save a game if somebody won already.",
									"Warning", JOptionPane.WARNING_MESSAGE);
						} else if (playerset == false) {
							JOptionPane.showMessageDialog(null, "You can't save a game without players.", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} else {
							saveGame();
						}
					} catch (IOException e1) {
						new SavingsErrorMessage();
					}
				} else if (e.getSource() == configPanel.load) {
					try {
						if (won == true) {
							JOptionPane.showMessageDialog(null,
									"You can't load a game if somebody won already. \n Please start a new game.",
									"Warning", JOptionPane.WARNING_MESSAGE);
						} else if (playerset == false) {
							JOptionPane.showMessageDialog(null, "You can't load a game without players.", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} else {
							loadGame();
						}

					} catch (Exception e1) {
						new SavingsErrorMessage();
					}
				}

			}
		};

		configPanel = new ConfigPanel(newGameAction);
		gameModeListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				selectedMode = configPanel.selectedMode();
				configPanel.setPlayers(null, null);
				resetFrame(selectedMode, null, null);
				openPlayersChoice();
			}
		};
		configPanel.addChangeListener(gameModeListener);
		this.getContentPane().add(basePanel, BorderLayout.CENTER);
		this.getContentPane().add(configPanel, BorderLayout.EAST);
		this.getContentPane().add(manualGame, BorderLayout.SOUTH);
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(800, 400));
		this.setResizable(false);
		this.setVisible(true);
		pack();
		eventListener();

		try {
			audioPlayer = new KnollAudioPlayer();
			audioPlayer.play();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * initializes MenuBar including the ActionListener which is needed for enable
	 * the items to function
	 * 
	 * @param menuItem*
	 *            - all MenuItems in the Menus
	 * @param menuHelp
	 *            - Help Menu which includes Items
	 * @param menuFile
	 *            - File Menu which includes Items
	 * @param menuBar
	 *            - the complete menuBar
	 * @param actionMenu
	 *            - ActionListener which looks up the Source and execute the
	 *            corresponding steps
	 */
	private void initMenuBar() {
		// ---MenuItems
		JMenuItem menuItemFileExit = new JMenuItem("Exit");
		JMenuItem menueItemFileProfiles = new JMenuItem("Profiles");
		JMenuItem menueItemFileAddProfiles = new JMenuItem("Add Profile");
		JMenuItem menueItemFileDelProfiles = new JMenuItem("Delete Profile");
		JMenuItem menueItemFileStats = new JMenuItem("Profiles & Stats");
		JMenuItem menueItemFileBash = new JMenuItem("Bash Control");
		JMenuItem menueItemHelpAbout = new JMenuItem("About");
		JMenuItem menueItemHelpHelp = new JMenuItem("Help");

		ActionListener actionMenu = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ---Exit Program---
				if (e.getSource() == menuItemFileExit) {
					System.exit(0);
				}
				// ---Show About Dialog---
				if (e.getSource() == menueItemHelpAbout) {
					JOptionPane.showMessageDialog(null,
							"<html>KNOLL GEWINNT VER.0.1 <br>@since 29.05.2018<br>@version 0.1<br>@repository github.com/tmbchrt/knollgewinnt<br>@author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz  <html>",
							"About", JOptionPane.INFORMATION_MESSAGE);
				}
				// ---Show Help Dialog---
				if (e.getSource() == menueItemHelpHelp) {
					JOptionPane.showMessageDialog(null,
							"<html>Press 'A' to move pointer left. Press 'D' to move pointer right.<br>Press 'S' to throw coin.<br>Try to get 4 in a row<br> <html>",
							"Help", JOptionPane.INFORMATION_MESSAGE);
				}
				// ---Show PlayersProfileChoice Dialog---
				if (e.getSource() == menueItemFileProfiles) {
					openPlayersChoice();
				}
				// ---Show AddProfiles Dialog---
				if (e.getSource() == menueItemFileAddProfiles) {
					String name = JOptionPane.showInputDialog("Enter new Players name: ");
					if (name != null && name.length() > 0) {
						playersMap.put(name, new KnollPlayer(name, 0, 0, 0));
						try {
							writeProfilesToFile();
						} catch (IOException e1) {
							profSaver.createNewStats();
						}
					}
				}
				// ---Show DelProfiles Dialog---
				if (e.getSource() == menueItemFileDelProfiles) {
					String name = JOptionPane.showInputDialog("Enter Players name to delete: ");
					if (name != null && name.length() > 0) {
						Set<String> playersKeySet = playersMap.keySet();
						Iterator<String> i = playersKeySet.iterator();
						boolean exist = false;
						while (i.hasNext()) {
							String next = i.next();
							if (next.equals(name) && !(next.equals("KI"))) {
								i.remove();
								exist = true;
							}

						}
						if (exist == true)
							JOptionPane.showMessageDialog(null, "Player found and deleted.");
						if (exist == false)
							JOptionPane.showMessageDialog(null, "Player not found.");
						try {
							if (exist == true)
								writeProfilesToFile();
						} catch (IOException e1) {
							profSaver.createNewStats();
						}
					}
				}
				// ---Show Stats Dialog---
				if (e.getSource() == menueItemFileStats) {
					try {
						writeProfilesToFile();
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
						DefaultTableModel model = new DefaultTableModel(rows, cols) {
							@Override
							public boolean isCellEditable(int row, int column) {
								return false;
							}

						};
						table.setModel(model);
						JOptionPane.showMessageDialog(null, new JScrollPane(table), "Stats",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						profSaver.createNewStats();
					}

				}
				// ---Show BashControl Dialog---
				if (e.getSource() == menueItemFileBash) {
					JOptionPane.showInputDialog(null, "", "BashControl", JOptionPane.PLAIN_MESSAGE);
				}

			}

			/**
			 * 
			 */

		};

		// ---MenuBar with Menus and the corresponding MenuItems with actionMenu
		// Listener---
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		// ---Add SelectProfiles---
		menuFile.add(menueItemFileProfiles);
		menueItemFileProfiles.addActionListener(actionMenu);
		// ---Add AddProfiles---
		menuFile.add(menueItemFileAddProfiles);
		menueItemFileAddProfiles.addActionListener(actionMenu);
		// ---Add DelProfiles---
		menuFile.add(menueItemFileDelProfiles);
		menueItemFileDelProfiles.addActionListener(actionMenu);
		// ---Add Stats---
		menuFile.add(menueItemFileStats);
		menueItemFileStats.addActionListener(actionMenu);
		// ---Add Bash Control---
		menuFile.add(menueItemFileBash);
		menueItemFileBash.addActionListener(actionMenu);
		// ---Add Exit---
		menuFile.add(menuItemFileExit);
		menuItemFileExit.addActionListener(actionMenu);
		// ---Add About---
		menuHelp.add(menueItemHelpAbout);
		menueItemHelpAbout.addActionListener(actionMenu);
		// ---Add Help---
		menuHelp.add(menueItemHelpHelp);
		menueItemHelpHelp.addActionListener(actionMenu);

		setJMenuBar(menuBar);

	}

	/**
	 * opens the playersChoice PopUp depending on which mode is currently selected
	 */
	private void openPlayersChoice() {
		if (selectedMode == 1) // singlePlayer
			try {
				singlePlayerProfileChoice();
			} catch (IOException e1) {
				profSaver.createNewStats();

			}
		if (selectedMode == 2)// multiPlayer
			try {
				multiPlayerProfileChoice();
			} catch (IOException e1) {
				profSaver.createNewStats();

			}
	}

	/**
	 * Opens a JOption Pane for MultiPlayers with 2 ComboBoxes which list all the
	 * Players read from the Profiles.kg file
	 * 
	 * @throws IOException
	 *             if refreshProfiles() fails
	 */
	protected void multiPlayerProfileChoice() throws IOException {
		readProfilesFromFile();// IOException if Profiles.kg corrupted or not existent
		String[] playersStringArray = new String[playersMap.keySet().size() - 1];
		Iterator<String> i = playersMap.keySet().iterator();

		int j = 0;
		while (i.hasNext()) {
			String next = i.next();
			if (!(next.equals("KI"))) {
				playersStringArray[j] = next;
				j++;
			}

		}

		selectionPane = new JOptionPane("Select Player Profiles", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		selectionPane.add(new JLabel("Player 1: "));
		playersList = new JComboBox<String>(playersStringArray);
		selectionPane.add(playersList);
		selectionPane.add(new JLabel("Player 2: "));
		playersList2 = new JComboBox<String>(playersStringArray);
		selectionPane.add(playersList2);
		selectMultiPlayer = new JButton("Select");
		selectionPane.add(selectMultiPlayer);
		selectMultiPlayer.addActionListener(profileSelected);

		singlePlayerDialog = selectionPane.createDialog(null, "MultiPlayer Profile Choice");
		singlePlayerDialog.setVisible(true);

	}

	/**
	 * Opens a JOption Pane for SinglePlayers with 1 ComboBoxes which list all the
	 * Players read from the Profiles.kg file
	 * 
	 * @throws IOException
	 *             if refreshProfiles() fails
	 */
	protected void singlePlayerProfileChoice() throws IOException {
		readProfilesFromFile();// IOException if Profiles.kg corrupted or not existent
		String[] playersStringArray = new String[playersMap.keySet().size() - 1];
		Iterator<String> i = playersMap.keySet().iterator();
		int j = 0;
		while (i.hasNext()) {
			String next = i.next();
			if (!(next.equals("KI"))) {
				playersStringArray[j] = next;
				j++;
			}
		}

		selectionPane = new JOptionPane("Select your Player Profile", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		playersList = new JComboBox<String>(playersStringArray);
		selectionPane.add(playersList);
		select = new JButton("Select");
		selectionPane.add(select);
		select.addActionListener(profileSelected);

		singlePlayerDialog = selectionPane.createDialog(null, "SinglePlayer Profile Choice");
		singlePlayerDialog.setVisible(true);
	}

	/**
	 * Reads all the players and stats from the profiles.kg file into the HashMap
	 * playersMap Opens dataErrorMessage() if file is not as expected or corrupt
	 * 
	 * @throws IOException
	 *             if File is not found
	 */

	private void readProfilesFromFile() throws IOException {
		this.playersMap = profSaver.readFile();
	}

	/**
	 * Loads the last saved Game from the save.kg file and calls the resumeGame()
	 * with the values read
	 * 
	 * @throws IOException
	 */
	protected void loadGame() throws Exception {
		Object [] read = saver.readFile(basePanel);
		try {
			resumeGame((int) read[0], (String[]) read[1]);
			JOptionPane.showMessageDialog(this, "Game succesfully loaded.");

		} catch (Exception e) {
			new SavingsErrorMessage();
		}

	}

	/**
	 * Saves the current Game with its PlayBoard, Time and the selectedMode into a
	 * new save.kg file. Overwrites the old.
	 * 
	 * @throws IOException
	 *             - FileWriter Failure
	 */
	protected void saveGame() throws IOException {
		try {
			saver.writeFile(this.basePanel, this.selectedMode);
		} catch (Exception e) {
			new SavingsErrorMessage();
		}
	}

	/**
	 * consoleIntro for Coolness
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
	 * Generates an eventListener to recognize pressed Keys. Generates an
	 * actionListener to recognize Players selects in Players choice OptionPane
	 * 
	 * @param awt
	 *            - AWTEventListener to process the KeyEvents.
	 * @param profileSelected
	 *            - ActionListener for the select Keys in Players choice OptionPane
	 */
	private void eventListener() {
		// ---eventListener---
		awt = new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				switch (event.getID()) {
				case KeyEvent.KEY_RELEASED:
					System.out.println(
							System.currentTimeMillis() + ": " + KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()));

					if (KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("a")
							|| KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("A")) {
						basePanel.changePointer(left);

					} else if (KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("d")
							|| KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("D")) {
						basePanel.changePointer(right);
					} else if ((KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("s") && won == false)
							|| (KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("S") && won == false)) {
						try {
							if (player1 == null || player2 == null)
								return;
							basePanel.throwCoin(currentPlayer);
							if (currentPlayer == 1)
								player1.increaseTurn();
							if (currentPlayer == 2 || currentPlayer == 3)
								player2.increaseTurn();
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (basePanel.evaluateRows() == true) {
							won = true;
							displayWinner(currentPlayer);
							try {
								switch (currentPlayer) {
								case 1:
									player1.setWin(player1.getSteps());
									writeProfilesToFile();
									break;
								case 2:
									player2.setWin(player2.getSteps());
									writeProfilesToFile();
									break;
								case 3:
									player2.setWin(player2.getSteps());
									writeProfilesToFile();
									break;
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							stopGame();

						} else {
							try {

								switchPlayer();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} else if (KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("k")
							|| KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("K")) {
						if (kCounter > 10) {
							easterEgg();
						} else
							kCounter++;

					}
					break;
				}

			}

		};
		this.getToolkit().addAWTEventListener(awt, AWTEvent.KEY_EVENT_MASK);
		// ---actionListener for Players choice OptionPanes select Button---
		profileSelected = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == select) {
					player1 = playersMap.get(playersList.getSelectedItem());
					player2 = playersMap.get(new String("KI"));
					player1.playGame();
					player2.playGame();
					resetFrame(selectedMode, player1, player2);
					playerset = true;
					System.out.println(System.currentTimeMillis() + ": PLAYER1: " + player1.getName());
					System.out.println(System.currentTimeMillis() + ": PLAYER2: " + player2.getName());
					singlePlayerDialog.dispose();
				}
				if (e.getSource() == selectMultiPlayer) {
					player1 = playersMap.get(playersList.getSelectedItem());
					player2 = playersMap.get(playersList2.getSelectedItem());
					if (player1 == player2) {
						JOptionPane.showMessageDialog(null, "You can not play against yourself!", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					player1.playGame();
					player2.playGame();
					resetFrame(selectedMode, player1, player2);
					playerset = true;
					System.out.println(System.currentTimeMillis() + ": PLAYER1: " + player1.getName());
					System.out.println(System.currentTimeMillis() + ": PLAYER2: " + player2.getName());
					singlePlayerDialog.dispose();
				}

			}
		};

	}

	protected void easterEgg() {
		kCounter = 0;

		try {
			JOptionPane.showMessageDialog(null, new KnollEasterPanel(), "EasterEgg", JOptionPane.PLAIN_MESSAGE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Synchronizes the profiles.kg File from the playersMap.
	 * 
	 * @throws IOException
	 *             if FileWriter fails.
	 */
	protected void writeProfilesToFile() throws IOException {
		profSaver.writeFile(playersMap);

	}

	/**
	 * Stops the game with just removing the Key Event Listener.
	 */
	protected void stopGame() {
		this.getToolkit().removeAWTEventListener(awt);
		basePanel.removePointer();

	}

	/**
	 * Display the player on the configPanels Label.
	 * 
	 * @param player
	 */
	private void displayWinner(int player) {
		switch (player) {
		case 1:
			configPanel.setWin(player1.getName());
			break;

		case 2:
			configPanel.setWin(player2.getName());
			break;

		case 3:
			configPanel.setWin(player2.getName());
			break;
		}

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
				basePanel.player = 3;
				// tog.evaluatePlayablePanels();
				basePanel.changePlayer();
				System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
				letKIPlay();
				break;

			case 2:
				currentPlayer = 2;
				basePanel.player = 2;

				basePanel.changePlayer();
				System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
				break;
			}
			break;

		case 2:
			currentPlayer = 1;
			basePanel.player = 1;

			basePanel.changePlayer();
			System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
			break;

		case 3:
			currentPlayer = 1;
			basePanel.player = 1;

			basePanel.changePlayer();
			System.out.println(System.currentTimeMillis() + ": Turn of Player: " + currentPlayer);
			break;
		}

	}

	/**
	 * Is called if player 3 is the current Player.
	 * 
	 * @throws Exception
	 *             if the throwCoin() fails.
	 */
	private void letKIPlay() throws Exception {
		basePanel.evaluatePlayablePanels();
		if (won == false) {
			basePanel.throwCoin(currentPlayer);
			player2.increaseTurn();
			if (basePanel.evaluateRows() == true) {
				won = true;
				displayWinner(currentPlayer);
				player2.setWin(player2.getSteps());
				writeProfilesToFile();
				stopGame();

			} else {
				switchPlayer();
			}

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
		if (player1 == null || player2 == null) {
			playerset = false;
		}else {
			player1.reset();
			player2.reset();
			currentPlayer = 1;
			basePanel.player = 1;
		}
			
		configPanel.setPlayers(player1, player2);

		won = false;
		this.remove(basePanel);
		basePanel = new BasePanel(7, 7);
		this.getContentPane().add(basePanel, BorderLayout.CENTER);
		pack();
		this.setVisible(true);
		this.getToolkit().removeAWTEventListener(awt);
		configPanel.setWin(null);
		eventListener();

	}

	/**
	 * Resumes the Game from the read savings.kg file. Fills the PlayBoard from the
	 * rows read from the file.
	 * 
	 * @param readMode
	 *            - chosen Game Mode (singlePlayer, multiPlayer)
	 * @param rows
	 *            - array of the rows from the file
	 *            (filled/notFilled.Owner-filled/notFilled.Owner)
	 * @throws Exception
	 *             if the PlayBoard (fill) method fails.
	 */
	public void resumeGame(int readMode, String[] rows) throws Exception {
		String[] temp = new String[basePanel.getPlayBoard()[0].length];
		for (int i = 0; i < basePanel.getPlayBoard().length; i++) {
			temp = rows[i].split("-");
			for (int j = 0; j < basePanel.getPlayBoard()[i].length; j++) {
				String[] temp2 = temp[j].split("\\.");
				switch (temp2[0]) {
				case "0":
					basePanel.getPlayBoard()[i][j].fill(-1, false);// false means dont check if already filled, just
																	// fill.
					break;
				case "1":
					switch (temp[j].split("\\.")[1]) {
					case "NO":
						basePanel.getPlayBoard()[i][j].fill(-1, false);
						break;
					case "P1":
						basePanel.getPlayBoard()[i][j].fill(1, false);
						break;
					case "P2":
						basePanel.getPlayBoard()[i][j].fill(2, false);
						break;
					case "KI":
						basePanel.getPlayBoard()[i][j].fill(3, false);
						break;
					}

				}

			}
		}
		this.selectedMode = readMode;
		configPanel.setMode(readMode);
	}
}
