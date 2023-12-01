/**
 * This class runs the code and displays the GUI
 * @author Ojas Hunjan, Jawalant Patel, Lance Cheong Youne
 */

// Imports needed
import java.util.*;
import java.util.Map.Entry;

// userDictionary(child class) inherits methods from checkSpelling(super class)
public class userDictionary extends checkSpelling{
    //initialize private variable
    private Hashtable<String, String> userDictionary;

    /**
     * class constructor method
     * sets initial value for necessary variables
     */
    public userDictionary(){
        super();
        this.userDictionary = new Hashtable<String, String>();
    }

    /**
    * setDictionary getter method used to initialize the dictionary
    * @param none
    * @return Hashtable<String, String> contains the user dictionary
    */
    public Hashtable<String, String> getUserDictionary(){
        return this.userDictionary;
    }

    /**
    * addWordUser method adds the word to the user dictionary
    * @param String is the word to be added to user dictionary
    * @return void
    */
    public void addWordUser(String word) {
        if(!this.userDictionary.containsKey(word)){
            this.userDictionary.put(word, word);
        }

    }
    
    /**
    * removeWordUser method removes the word from the user dictionary
    * @param String is the word to be removed foro
    * @return void
    */
    public void removeWordUser(String word) {
        if(this.userDictionary.containsKey(word)){
            this.userDictionary.remove(word, word);
        }
    }

    /**
    * userDictionarytoString toString method used to convert the user dictionary to a string
    * @param none
    * @return String contains the user dictionary
    */
    public String userDictionarytoString(){
        // Uses string builder to construct a string
        StringBuilder sb = new StringBuilder();
        //enchance for loop to iterate the set of user dictionary
        for (Entry<String, String> entry: this.userDictionary.entrySet()) {  
            String key = entry.getKey();
            sb.append(key).append("\n"); // adds word to end of sb
        }
        String result = sb.toString(); // StringBuilder toString method
        return result;
    }
}