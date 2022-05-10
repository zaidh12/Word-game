//package skeletonCodeAssgnmt2;

/**
 * The WordDictionary class handles the input of words into the game through text files which are given as command line paramters.
 * If no file is found a small default dictionary will be passed through.
 */

public class WordDictionary {
	int size;
	static String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
	"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
	"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
	"boysenberry","loquat","avocado"}; //default dictionary
	
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
	}
	
	WordDictionary() {
		size=theDict.length;
		
	}
	
	/**
	 * Returns an element of the array i.e. words from the list.
	 * @return theDict[wdPos] (The next word in the array)
	 */
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return theDict[wdPos];
	}
}
