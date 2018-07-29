package handlerPackage;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import errorMessages.StatsErrorMessage;
import errorMessages.UnknownErrorMessage;
import gamePackage.KnollPlayer;
import gamePackage.MainFrame;

public class ProfilesHandler {

	public ProfilesHandler() {

	}
/**
 * kommentieren
 * @param playersMap
 * @throws IOException
 */
	public void writeFile(HashMap<String, KnollPlayer> playersMap) throws IOException {
		System.out.println(System.currentTimeMillis() + ": WRITING STATS TO FILE...");

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
			bw.write(getPlayerEntry(playersMap.get(next)));
			bw.newLine();
		}
		bw.close();
		fw.close();
	//	JOptionPane.showMessageDialog(null, "Update succesful!");

		System.out.println(System.currentTimeMillis() + ": WRITING STATS TO FILE FINISHED.");
	}

	private String getPlayerEntry(KnollPlayer player) {
		return player.getName() + "." + player.getPlayedGames() + "."
				+ player.getWins() + "." + player.getStepsToWin();
	}
	
	public HashMap<String, KnollPlayer> readFile() throws IOException {
		System.out.println(System.currentTimeMillis() + ": READING STATS FROM FILE...");
		URL temp = MainFrame.class.getResource("profiles.kg");

		int amountOfRegisteredPlayers = 0;
		ArrayList<KnollPlayer> players = new ArrayList<>();

		try(FileReader fr = new FileReader(URLDecoder.decode(temp.getPath()));BufferedReader br = new BufferedReader(fr)) {
			if (br.readLine().equals("<HEAD>KNOLLGEWINNT PROFILES<HEAD>")) {
				amountOfRegisteredPlayers = Integer.parseInt((br.readLine().split("'")[1]));
				for (int i = 0; i < amountOfRegisteredPlayers; i++) {
					String[] actualLine = br.readLine().split("\\.");
					players.add(new KnollPlayer(actualLine[0], Integer.parseInt(actualLine[1]),
							Integer.parseInt(actualLine[2]), Integer.parseInt(actualLine[3])));
				}
				HashMap<String, KnollPlayer> playerObjects = new HashMap<>();
				Iterator<KnollPlayer> i = players.iterator();
				while (i.hasNext()) {
					KnollPlayer p = i.next();
					playerObjects.put(p.getName(), p);
				}
				System.out.println(System.currentTimeMillis() + ": READING STATS FROM FILE FINISHED.");
				return playerObjects;

			} else {
				dataErrorMessage();
			}
		} catch (Exception e) {
			createNewStats(false);

		}

		return null;
	}

	/**
	 *
	 */
	public void createNewStats(boolean showMessage) {
		if (showMessage) {
			if (dataErrorMessage() == JOptionPane.YES_OPTION) {
				try(FileWriter fw = new FileWriter("profiles.kg");
				BufferedWriter bw = new BufferedWriter(fw)) {
					bw.write("<HEAD>KNOLLGEWINNT PROFILES<HEAD>");
					bw.newLine();
					bw.write("<INFO> PLAYERS: '" + 1 + "' <INFO>");
					bw.newLine();
					bw.write("KI.0.0.0");
				} catch (Exception f) {
					f.printStackTrace();
					UnknownErrorMessage.ShowError();;
				}
			} 
		}else {
			try (FileWriter fw = new FileWriter("profiles.kg");
				BufferedWriter bw = new BufferedWriter(fw)){
				bw.write("<HEAD>KNOLLGEWINNT PROFILES<HEAD>");
				bw.newLine();
				bw.write("<INFO> PLAYERS: '" + 1 + "' <INFO>");
				bw.newLine();
				bw.write("KI.0.0.0");
			} catch (Exception f) {
				f.printStackTrace();
			}
		}
	}

	private int dataErrorMessage() {
		
		return StatsErrorMessage.showError();

	}
}
