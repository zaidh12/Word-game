//package skeletonCodeAssgnmt2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

/**
 * This WordGame app is a game that determines how fast a player can type in words recognized before they disappear. 
 * A player's final score is determined by the combined of length of all the words they managed to catch (type in correctly before they disappeared).
 * The WordApp class contains the main method which takes in command line arguements of the total number of words, the number of words
 * that will be displayed on the screen at a given moment in time and the file name to extract the words from.
 */

public class WordApp {
//shared variables
	static int noWords=4;
	static volatile int totalWords, numWords;
	static boolean isCaught = false;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();
	static volatile boolean hasStarted = false;
	static JLabel missed;
	
	static JTextField textEntry = new JTextField("",40);

	static WordPanel w;

	/**
	 * Used to display a message when the game ends due to all words being dropped.
	 */
	public static void getOutputMessage(){ 
		textEntry.setFont(new Font("Helvetica", Font.BOLD,12));
		textEntry.setText("GAME OVER. To play again, click END and then START");}
	
	
	/**
	 * Handles the design and functionaliy of the GUI. Initializes the frames and panels and all components required such as 
	 * labels to handle counters,reactive START, END and EXIT buttons and a textbox for player input.
	 * @params frameX (Length of frame), frameY (Width of frame), yLimit (Represents the start of the cyan border)
	 */
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions

	  JOptionPane.showMessageDialog(null, "         WELCOME TO WORDGAME \n Click START (alt+S ) to play. \n Click END (alt+E) if you want to stop playing.", "WordGame Instructions", JOptionPane.INFORMATION_MESSAGE);

	  JFrame frame = new JFrame("WordGame"); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(frameX, frameY);
	  frame.setBackground(Color.white);
      JPanel g = new JPanel();
      g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      g.setSize(frameX,frameY);
    	
	  w = new WordPanel(words,yLimit);
	  w.setSize(frameX,yLimit+100);
	  g.add(w); 
	    
      JPanel txt = new JPanel();
      txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
      JLabel caught =new JLabel("Caught: " + score.getCaught() + "    ");
      missed =new JLabel("Missed:" + score.getMissed()+ "    ");
      JLabel scr =new JLabel("Score:" + score.getScore()+ "    ");    
      txt.add(caught);
	  txt.add(missed);
	  txt.add(scr);
  
	   textEntry.addActionListener(new ActionListener()
	   {
	      public void actionPerformed(ActionEvent evt) {
	         String text = textEntry.getText();
			 for (int i =0; i < noWords; i++)
			 {
				 if (words[i].matchWord(text) == true)
				 {
					 isCaught = true;
					 score.caughtWord(words[i].getWord().length());
					 caught.setText("Caught:" + score.getCaught() + "    ");;
					 scr.setText("Score:" + score.getScore() + "    ");;
				 }
			 }
	         textEntry.setText("");
	         textEntry.requestFocus();
	      }
	   });
	   
	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);
	    
	   JPanel b = new JPanel();
       b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
	   JButton startB = new JButton("Start");;
	   startB.setMnemonic('S');
		
			// add the listener to the jbutton to handle the "pressed" event
		startB.addActionListener(new ActionListener()
		{
		   public synchronized void actionPerformed(ActionEvent e)
		   {
			   textEntry.setText("");
			   done = false;
			   hasStarted = true;
			   for(int i = 0; i < totalWords; i++) {
					Thread c = new Thread(new WordPanel(words, yLimit-5));
					c.start();
			   }
		      textEntry.requestFocus();  //return focus to the text entry field
		   }
		   
		});
		JButton endB = new JButton("End");;
		endB.setMnemonic('E');

		endB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
			  numWords = totalWords;
			  startB.setEnabled(true);
			  hasStarted = false;
			  done = true;
			  score.resetScore();
			  caught.setText("Caught: " + score.getCaught() + "    ");
			  missed.setText("Missed:" + score.getMissed() + "    ");
			  scr.setText("Score:" + score.getScore() + "    ");
			  for (int i =0; i < noWords; i++){
				words[i].resetWord();  
				words[i].resetPos();} 
				textEntry.setFont(new Font("Helvetica", Font.BOLD,12)); 
				textEntry.setText("GAME OVER, click END and then START to play again");
			  
			  g.revalidate();
			  g.repaint();
		   }
		});

		JButton exitB = new JButton("Exit");;
		exitB.setMnemonic('x');

		exitB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		b.add(startB);
		b.add(endB);
		b.add(exitB);
		
		g.add(b);
		
	  frame.setLocationRelativeTo(null);  // Center window on screen.
      frame.add(g); //add contents to window
      frame.setContentPane(g);     
      frame.setVisible(true);
	}

   /**
	* Takes in a filename that includes a list of words and inputs them into an array, kept seperate from the GUI ensuring the model is seperate from the view
	* @param filename
	* @return dictStr (A string array of English dictionary words)
    */
   public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;
	}

	public static void main(String[] args) {
    	
		System.setProperty("sun.java2d.opengl", "true"); //To enable better graphics on a Linux system.
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		setupGUI(frameX, frameY, yLimit);  
		//Start WordPanel thread - for redrawing animation

		int x_inc=(int)frameX/noWords;
		//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}	
	}
}