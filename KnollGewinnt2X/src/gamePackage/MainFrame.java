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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 7046940995312716846L;
	private static final int left = 1;
	private static final int right = 2;
	private int player1Count = 0;
	private int player2Count = 0;
	private int kCounter;
	private BasePanel tog;
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
				if (e.getSource() == configPanel.newGame) {
					resetFrame(configPanel.selectedMode(), player1, player2);
					openPlayersChoice();
				} else if (e.getSource() == configPanel.save) {
					try {
						saveGame();
					} catch (IOException e1) {
						dataErrorMessage();
					}
				} else if (e.getSource() == configPanel.load) {
					try {
						loadGame();
					} catch (IOException e1) {
						dataErrorMessage();
					}
				}

			}
		};

		configPanel = new ConfigPanel(newGameAction);
		gameModeListener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getSource() == configPanel.singlePlayer || e.getSource() == configPanel.multiPlayer) {
					selectedMode = configPanel.selectedMode();
					configPanel.setPlayers(null, null);
					resetFrame(selectedMode, null, null);
					openPlayersChoice();
				}
				if (e.getSource() == configPanel.networkPlayer) {
					JOptionPane.showMessageDialog(null, new NetworkPanel(), "Setup knoll.net Game",
							JOptionPane.PLAIN_MESSAGE);
				}

			}
		};
		configPanel.addChangeListener(gameModeListener);
		this.getContentPane().add(tog, BorderLayout.CENTER);
		this.getContentPane().add(configPanel, BorderLayout.EAST);
		this.getContentPane().add(manualGame, BorderLayout.SOUTH);
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(800, 400));
		this.setResizable(false);
		this.setVisible(true);
		pack();
		eventListener();
		try {
			playAudio();
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
							updateStats();
						} catch (IOException e1) {
							dataErrorMessage();
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
								updateStats();
						} catch (IOException e1) {
							dataErrorMessage();
						}
					}
				}
				// ---Show Stats Dialog---
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
						dataErrorMessage();
					}

				}
				// ---Show BashControl Dialog---
				if (e.getSource() == menueItemFileBash) {
					BashPanel bash = new BashPanel();
					ActionListener f = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								bash.setAnswer(sendMessageToServer(bash.getText()));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					};
					bash.addActionListener(f);
					JOptionPane.showOptionDialog(null, bash,
				            "knollgewinnt@knollserver.westus.cloudapp.azure.com", JOptionPane.DEFAULT_OPTION,
				            JOptionPane.PLAIN_MESSAGE, null, new Object[] {},
				            null);

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
				dataErrorMessage();

			}
		if (selectedMode == 2)// multiPlayer
			try {
				multiPlayerProfileChoice();
			} catch (IOException e1) {
				dataErrorMessage();

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
		refreshProfiles();// IOException if Profiles.kg corrupted or not existent
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
		refreshProfiles();// IOException if Profiles.kg corrupted or not existent
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

	private void refreshProfiles() throws IOException {
		URL temp = MainFrame.class.getResource("profiles.kg");
		//String profiles = sendMessageToServer("GETSTATS");
		FileReader fr = new FileReader(URLDecoder.decode(temp.getPath()));
		BufferedReader br = new BufferedReader(fr);
		int amountOfRegisteredPlayers = 0;
		ArrayList<KnollPlayer> players = new ArrayList<>();

		try {
			if (br.readLine().equals("<HEAD>KNOLLGEWINNT PROFILES<HEAD>")) {
				amountOfRegisteredPlayers = Integer.parseInt((br.readLine().split("'")[1]));
				for (int i = 0; i < amountOfRegisteredPlayers; i++) {
					String[] actualLine = br.readLine().split("\\.");
					players.add(new KnollPlayer(actualLine[0], Integer.parseInt(actualLine[1]),
							Integer.parseInt(actualLine[2]), Integer.parseInt(actualLine[3])));
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
		} catch (Exception e) {
			dataErrorMessage();
		}
	}

	/**
	 * Loads the last saved Game from the save.kg file and calls the resumeGame()
	 * with the values read
	 * 
	 * @throws IOException
	 */
	protected void loadGame() throws IOException {
		URL temp = MainFrame.class.getResource("save.kg");

		FileReader fr = new FileReader(URLDecoder.decode(temp.getPath()));
		BufferedReader br = new BufferedReader(fr);

		String[] rows = new String[tog.getPlayBoard().length];
		int readMode = 1;
		if (br.readLine().equals("<HEAD>KNOLLGEWINNT SAVINGS<HEAD>")) {

			try {
				readMode = Integer.parseInt(br.readLine().split("'")[3]);
			} catch (NumberFormatException e) {
				br.close();
				dataErrorMessage();
			}
			for (int i = 0; i < rows.length; i++) {
				rows[i] = br.readLine();
			}
		} else {
			br.close();
			dataErrorMessage();
		}
		br.close();
		try {
			resumeGame(readMode, rows);
		} catch (Exception e) {
			br.close();
			dataErrorMessage();
		}
		JOptionPane.showMessageDialog(this, "Game succesfully loaded.");
	}

	/**
	 * Shows a user friendly dataError Message dialog. Should be used in a catch
	 * section instead of printing a stack trace.
	 * 
	 * @param reply
	 *            - Integer value showing if the user wants to create new DATA.KG
	 *            files.
	 */
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

	/**
	 * Saves the current Game with its PlayBoard, Time and the selectedMode into a
	 * new save.kg file. Overwrites the old.
	 * 
	 * @throws IOException
	 *             - FileWriter Failure
	 */
	protected void saveGame() throws IOException {

		URL temp = MainFrame.class.getResource("save.kg");
		FileWriter fw = new FileWriter(URLDecoder.decode(temp.getPath()));
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
						tog.changePointer(left);

					} else if (KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("d")
							|| KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("D")) {
						tog.changePointer(right);
					} else if ((KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("s") && won == false)
							|| (KeyEvent.getKeyText(((KeyEvent) event).getKeyCode()).equals("S") && won == false)) {
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
	protected void updateStats() throws IOException {
		URL temp = MainFrame.class.getResource("profiles.kg");

		FileWriter fw = new FileWriter(URLDecoder.decode(temp.getPath()));
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

	/**
	 * Stops the game with just removing the Key Event Listener.
	 */
	protected void stopGame() {
		this.getToolkit().removeAWTEventListener(awt);
		tog.removePointer();

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

	/**
	 * Is called if player 3 is the current Player.
	 * 
	 * @throws Exception
	 *             if the throwCoin() fails.
	 */
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
		configPanel.setPlayers(player1, player2);
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
		String[] temp = new String[tog.getPlayBoard()[0].length];
		for (int i = 0; i < tog.getPlayBoard().length; i++) {
			temp = rows[i].split("-");
			for (int j = 0; j < tog.getPlayBoard()[i].length; j++) {
				String[] temp2 = temp[j].split("\\.");
				switch (temp2[0]) {
				case "0":
					tog.getPlayBoard()[i][j].fill(-1, false);// false means dont check if already filled, just fill.
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
		configPanel.setMode(readMode);
	}

	/**
	 * Plays the background music.
	 * 
	 * @throws IOException
	 *             if Files corrupted / not found.
	 * @throws Exception
	 */
	public void playAudio() throws Exception {
		File audioFile = new File(URLDecoder.decode(MainFrame.class.getResource("title.wav").getPath()));
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.loop(clip.LOOP_CONTINUOUSLY);
		System.out.println(System.currentTimeMillis() + ": AUDIO IS PLAYING");
		// URL temp = MainFrame.class.getResource("title.wav");
		// java.applet.AudioClip clip = Applet.newAudioClip(temp);
		// clip.loop();

	}

	protected String sendMessageToServer(String text) throws IOException {
		String address = "knollserver.westus.cloudapp.azure.com";
		int port = 8000;
		Socket s1 = null;
		BufferedReader is = null;
		PrintWriter os = null;

		try {
			s1 = new Socket(address, port);
			is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			os = new PrintWriter(s1.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}

		System.out.println(System.currentTimeMillis()+ ": Client Address : " + address);
		String answer = null;
		try {

			os.println(text);
			os.flush();
			answer = is.readLine();
			System.out.println(System.currentTimeMillis()+ ": Server Response : " + answer);
			os.println("QUIT");
			os.flush();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(System.currentTimeMillis()+ ": Socket read Error");
		} finally {
			is.close();
			os.close();
			s1.close();
			System.out.println(System.currentTimeMillis()+ ": Connection Closed");

		}

		return answer;
	}
}
