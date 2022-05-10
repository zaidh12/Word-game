//package skeletonCodeAssgnmt2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * The WordPanel class after being edited from its skeleton version handles the primary animation of the game i.e. concurrently 
 * dropping words from the top of the screen until they reach the cyan border at the bottom at various random speeds. Upon reaching
 * the border or being correctly typed in by the player, the word gets reset and a new word is dropped. 
 * 
 * @version 07/09/2021
 * @author Zaidh Imran
 */

public class WordPanel extends JPanel implements Runnable {
	public  volatile boolean done;
	private WordRecord[] words;
	private volatile int noWords;
	private int i, numWords;
	private int maxY;
	
	/**
	 * The painComponent method handles the drawing and redrawing of words on the screen. At any given time there will be a 
	 * certain number of words on the screen which is pre-determined through a command line argument.
	 * 
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		g.setColor(Color.cyan);
		g.fillRect(0,maxY-10,width,height);
		g.setColor(Color.black);
		g.setFont(new Font("Cambria", Font.PLAIN, 26));
			for (int i=0;i<noWords;i++){	    	
				g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()-5);	
				repaint();
			}	
	}
	
	WordPanel(WordRecord[] words, int maxY) {
		this.words=words; //will this work?
		noWords = words.length;
		done=false;
		this.maxY=maxY;		
	}
	
	/**
	 * The run method is invoked by the threads when the Start button is clicked and it handles the terminating of the game 
	 * when all words have dropped as well as the checking and counting of missed words through the Score class methods.
	 */
	public synchronized void run() {
		while (! WordApp.done )
		{
			try
			{	Thread.sleep(1000);
				for (int i = 0; i < noWords; i++)
				{
					int speed = (int)Math.ceil(words[i].getSpeed()/100);
					words[i].drop(speed);
					repaint();
					if (words[i].dropped() )
					{
						words[i].resetWord();
						WordApp.score.missedWord();
						WordApp.missed.setText("Missed:" + WordApp.score.getMissed() + "    ");
					}
					WordApp.numWords = WordApp.score.getTotal();
					if (WordApp.numWords == WordApp.totalWords) 
					{
						words[i].resetPos();

						WordApp.done = true; //terminator
						WordApp.getOutputMessage();
					}
				}

			}
			catch (Exception e)
			{} 
		}
	}	
}


