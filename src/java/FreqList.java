/**
 * Frequency List of word/# of occurrence pairs
 * @author Kim Bruce
 * @version 2/08
 *  revised: added package --kpc 2/13
 *  
 * @author Sean Zhu
 * @version 7/15 
 *  - renamed insert method to add
 * 	- added toString method for grading
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import structure5.Association;

public class FreqList {
    // list of associations holding words and their frequencies
    private List<Association<String, Integer>> flist;
    
    // This is a double to save time later when dividing
    private double totalFreq;    
    
    /**
     * Constructs a new FreqList
     */
    public FreqList() {
    	// Start off with an ArrayList with 16 entries
    	flist = new ArrayList<Association<String,Integer>>();
    	totalFreq = 0.0;
    }
   

    /**
     * Add word to list or, if word already occurs, increment its frequency
     * 
     * @param word
     */
    public void add(String word) {    	

    	// Determine if any entry in flist contains the key word
    	int index = flist.indexOf(new Association<String, Integer>(word, 0));    

    	// There is already an entry in flist with key word
    	if(index != -1) {
    		Integer val = (flist.get(index)).getValue();
    		(flist.get(index)).setValue(val+1);
    	}

    	// There is no entry in flist with key word
    	else{
    		flist.add(new Association<String, Integer>(word,1));
    	}
    	totalFreq += 1.0;
    }

    /**
     * Return a word from the list 
     * 
     * @param p probability
     * @pre 0 <= p <= 1
     * @returns randomly chosen word from the list
     * @throws IllegalArgumentException if p < 0 or p > 1
     * @throws AssertionError if no word from list is selected
     */
    public String get(double p) {
    	if(p < 0 || p > 1) {    		
    		throw new IllegalArgumentException("p must be between 0 and 1 inclusive");
    	}    	
    	if(totalFreq == 0.0) {
    		return ""; 
    	}
    	else{
    		double wordProb = 0.0;
    		for(int i = 0; i < flist.size(); ++i) {
    			String key = (flist.get(i)).getKey();
    			wordProb += (flist.get(i)).getValue()/totalFreq;
    			if(p <= wordProb) {
    				return key;
    			}
    		}
    		
    		// Should never reach here
    		throw new AssertionError("Reached end of list and prob still less than 1");
    	}
    }
    
    
    public void printFreqList() {
    	for(int i = 0; i < flist.size(); ++i) {
    		System.out.println("key: " + (flist.get(i)).getKey() + " value: " + (flist.get(i)).getValue());
    	}
    }
    
    /**
     * Implementation of toString method of this class
     * 
     * @returns a string representation of a list of associations
     */
    public String toString() {
    	String str = "Frequency List: ";
    	for (Association<String,Integer> assoc: flist){
    		str += assoc.toString();
    	}
    	return str;
    }
        
    // static method to test the class
    public static void main(String args[]) {
        FreqList list = new FreqList();
        list.add("cow");
        list.add("apple");
        list.add("banana");
        list.add("dog");
        list.printFreqList();
        System.out.println(list.toString());
        
        Random rand = new Random();
        for(int i = 0; i < 10; ++i) {
        	String word = list.get(rand.nextDouble());
        	System.out.println(word);
        }        
    }
}
