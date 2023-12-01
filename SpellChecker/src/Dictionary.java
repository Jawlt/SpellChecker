/**
 * This class runs the code and displays the GUI
 * @author Jawalant Patel, Lance Cheong Youne
 */
import java.io.File;
import java.util.*;
import java.util.Map.Entry;

// Dictionary(child class) inherits methods from userDictionary(super class)
/**
 * This class is used to create a Dictionary Object that extends the previous userDictionary class and has specific methods to 
 * work with the dictionary.
 * @author Ojas Hunjan
 */
public class Dictionary extends userDictionary{
    private Hashtable<String, String> dictionary;//create new dictionary
    private Hashtable<String, String> userDictionary;//create new user dictionary
    private Hashtable<String, String> combinedDictionary; //create new combined dictionary

    /**
     * Constructor method for the class calls the super class of userdictionary and sets instance variables to desired values
     */
    public Dictionary() {
        super(); //call userDictionary constructor
        this.dictionary = new Hashtable<String, String>();//new dictionary is a new hashtable
        this.userDictionary = super.getUserDictionary();//userdict is the user dict from that class
        this.combinedDictionary = new Hashtable<String, String>();//combined dict is new hashtable
    }

    /**
     * This method gets the dictionary of this object
     * @return dictionary
     */
    public Hashtable<String, String> getDictionary(){
        return this.dictionary;
    }

    /**
     * This method returns the combined dictionary
     * @return combineddictionary
     */
    public Hashtable<String, String> getCombinedDictionary(){
        return this.combinedDictionary;
    }

    /**
     * This method loads the dictionary with the reference dictionary from github and returns it loaded with words
     * @return dictionary with words
     */
    public Hashtable<String, String> loadDictionary() {
        this.dictionary.clear();//clears the dictionary

        try {
            File githubFile = new File("./SpellChecker/words_alpha.txt");//opens reference dictionary from github
            Scanner fileScan = new Scanner(githubFile);//file scanner object

            while (fileScan.hasNextLine()) {//scans each line of the file
                String dictWord = fileScan.nextLine();//store word of each line
                this.dictionary.put(dictWord, dictWord);//put word into the dictionary
            }
            fileScan.close();//close the file scan
        } 
        catch (Exception e) { 
            System.out.println("Error, could not open the file");//if file cannot be opened
        }
        setDictionary(this.dictionary);//sets dictionary of this class to be that with the newly loaded words
        
        return this.dictionary;//returns the dictionary
    }

    /**
     * This method combines the user dict and test dict together to create one dictionary
     * @param none
     * @return void
     */
    public void combineDictionary() {
        this.combinedDictionary.clear();//clears the combined dictionary

        this.userDictionary = getUserDictionary();//get the user dict and store it in var
        
        // Combine userDictionary and dictionary into one hashtable
        this.combinedDictionary.putAll(dictionary);

        // Iterate through userDictionary and add unique entries to the combined hashtable
        // Store all Hashtable entries into a Set ussing entrySet() method
        for (Entry<String, String> entry: userDictionary.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Check if the key already exists in the combined hashtable
            if (!combinedDictionary.containsKey(key)) {
                // If the key is not already present, add the key-value pair
                combinedDictionary.put(key, value);
            } 
        }
        setDictionary(this.combinedDictionary);//sets combined dict as new dictionary
        setUserDictionary(this.userDictionary);//sets the user dict as the userdictionary
    }
    
    /**
     * This method takes in the param of a word and removes it from the dictionary and returns nothing
     * @param word
     * @return void
     */
    public void removeWordDictionary(String word) {
        if(this.dictionary.containsKey(word)){//if the dict has the word
            this.dictionary.remove(word, word);//removes word from the dict
        }
    }
    
    /**
     * Helper function for testing that takes in two hash tables and compares them and returns true if they are equal 
     * and false if they are false
     * @param a
     * @param b
     * @return true or false
     */
    public boolean assertEquals(Hashtable<String, String> a, Hashtable<String, String> b){
        if(a.equals(b)){
            return true;
        }
        return false;
    }
}
