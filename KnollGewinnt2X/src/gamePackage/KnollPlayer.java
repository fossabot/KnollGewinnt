package gamePackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnollPlayer {

	private String name;
	private int playedGames;
	private int wins;
	private int stepsToWin;

	public KnollPlayer(String name, int playedGames, int wins, int stepsToWin) {
		this.name = name;
		this.playedGames = playedGames;
		this.wins = wins;
		this.stepsToWin = stepsToWin;
	}

	public void playGame() {
		playedGames++;
	}

	public void setWin(int stepsToWin) {
		wins++;
		if (stepsToWin < this.stepsToWin)
			this.stepsToWin = stepsToWin;

	}
	
	public void play() {
		this.playedGames++;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the playedGames
	 */
	public int getPlayedGames() {
		return playedGames;
	}

	/**
	 * @return the stepsToWin
	 */
	public int getStepsToWin() {
		return stepsToWin;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

}
