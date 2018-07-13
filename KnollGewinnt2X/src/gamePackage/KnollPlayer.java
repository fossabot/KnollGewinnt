/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: MainFrame
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
package gamePackage;

public class KnollPlayer implements Comparable<KnollPlayer>{

	
	
	private String name;
	private int playedGames;
	private int wins;
	private int stepsToWin;
	private int steps;

	
	/**
	 * Contructor of Class KnollPlayer
	 * @param name - Players nickname
	 * @param playedGames - Amount of Played Games by the Player
	 * @param wins	- Amount of Wins by the Player
	 * @param stepsToWin - Minimum steps to win ever reached
	 */
	public KnollPlayer(String name, int playedGames, int wins, int stepsToWin) {
		this.name = name;
		this.playedGames = playedGames;
		this.wins = wins;
		this.stepsToWin = stepsToWin;
	}

	
	public void reset() {
		this.steps=0;
	}
	/**
	 * Play a new Game
	 */
	public void playGame() {
		playedGames++;
	}
	
	/**
	 * Increase steps
	 */
	
	public void increaseTurn() {
		this.steps++;
	}

	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * Increase Wins and take stepsToWin if theyre smaller than before
	 * @param stepsToWin
	 */
	public void setWin(int stepsToWin) {
		wins++;
		if (stepsToWin < this.stepsToWin || this.stepsToWin==0) {
			this.stepsToWin = stepsToWin;	
		}
			

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

	@Override
	public int compareTo(KnollPlayer o) {
		if(this.getWins()<o.getWins() )return -1;
		if(this.getWins()>o.getWins())return 1;
		return 0;
	}

}
