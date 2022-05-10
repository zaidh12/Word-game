//package skeletonCodeAssgnmt2;

/**
 * The WordRecord class handles all the functionality regarding individual words. It contains the animation properties like 
 * resetting a word, reposition a word and determining how fast the words drop. It also checks if the player's typed in word 
 * matches any of the words currently dropping. No changes have been made to this class and it is as the skeleton provided.
 * @version 07/09/2021
 * @author Zaidh Imran
 */
public class WordRecord {
	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;
	
	private int fallingSpeed;
	private static int maxWait=1500;
	private static int minWait=100;

	public static WordDictionary dict;

	WordRecord() {
		text="";
		x=0;
		y=0;	
		maxY=300;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}
	
	WordRecord(String text) {
		this();
		this.text=text;
	}
	
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}
	
// all getters and setters must be synchronized

	/**
	 * Sets the position of a word on the y-axis
	 * @param y
	 */
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}
	
	/**
	 * Sets the position of a word on the x-axis
	 * @param x
	 */
	public synchronized  void setX(int x) {
		this.x=x;
	}
	
	/**
	 * Sets the word to be dropped.
	 * @param text
	 */
	public synchronized  void setWord(String text) {
		this.text=text;
	}

	/**
	 * Gets the word to be dropped/is being dropped.
	 * @return text
	 */
	public synchronized  String getWord() {
		return text;
	}
	
	/**
	 * Gets the position of a word on the x-axis
	 * @return x
	 */
	public synchronized  int getX() {
		return x;
	}	
	
	/**
	 * Gets the position of a word on the y-axis
	 * @return y
	 */
	public synchronized  int getY() {
		return y;
	}
	
	/**
	 * Gets the speed the word is falling with.
	 * @return fallingSpeed
	 */
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	/**
	 * Sets the position of a word on both the x and y axis
	 * @param x, y
	 */
	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

	/**
	 * Resest the position of a word, essentially taking it off the screen
	 * 
	 */
	public synchronized void resetPos() {
		setY(0);
	}

	/**
	 * Resets the word that is being dropped i.e. makes thenext word drop.
	 */
	public synchronized void resetWord() {
		resetPos();
		text=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());
	}
	
	/**
	 * Checks if the text typed in by the player matches any word that is currently on the screen.
	 * @param typedText
	 * @return true/false
	 */
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Drops a word from the top of the screen by incrementing its vertical position
	 * @param inc 
	 */
	public synchronized  void drop(int inc) {
		setY(y+inc);
	}
	
	/**
	 * Checks if a word has been dropped.
	 * @return true/false
	 */
	public synchronized  boolean dropped() {
		return dropped;
	}

}
