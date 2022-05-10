//package skeletonCodeAssgnmt2;

/**
 * The Score class handles the score calculation. The score is calculated by the number of letters of the words that were caught.
 * This class was edited from its skeleton by synchronizing all the getters and settters to enable concurrency.
 * @version 07/09/2021
 * @author Zaidh Imran
 */

public class Score {
	private int missedWords;
	private int caughtWords;
	private int gameScore;
	
	Score() {
		missedWords=0;
		caughtWords=0;
		gameScore=0;
	}
		
	// all getters and setters must be synchronized
	
	/**
	 * Returns the number of missed words.
	 */
	public synchronized int getMissed() {
		return missedWords;
	}

	/**
	 * Returns the number of caught words.
	 */
	public synchronized int getCaught() {
		return caughtWords;
	}
	
	/**
	 * Returns the total number of words dropped, both caught and missed.
	 */
	public synchronized int getTotal() {
		return (missedWords+caughtWords);
	}

	/**
	 * Returns the score of the game.
	 */
	public synchronized int getScore() {
		return gameScore;
	}
	
	/**
	 * Increments the counter of missed words.
	 */
	public synchronized void missedWord() {
		missedWords++;
	}

	/**
	 * Increments the counter of caught words as well as updates the score.
	 * @param length (The length of a caught word)
	 */
	public synchronized void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}

	/**
	 * Resets the score and counters for missed and caught words to zero so a new game can be started.
	 */
	public synchronized void resetScore() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
	}
}
