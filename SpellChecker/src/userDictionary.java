import java.util.*;

// userDictionary(child class) inherits methods from checkSpelling(super class)
public class userDictionary extends checkSpelling{
    //private variables of userDictionary class
    private Hashtable<String, String> userDictionary;

    // userDictionary constructor
    public userDictionary(){
        super();
        this.userDictionary = new Hashtable<String, String>();
    }

    public Hashtable<String, String> getUserDictionary(){
        return this.userDictionary;
    }

    //adds word into userDictionary only if dictionary doesn't contain word
    public void addWordUser(String word) {
        if(!this.userDictionary.containsKey(word)){
            this.userDictionary.put(word, word);
        }

    }

    //removes word from userDictionary if dictionary contains word
    public void removeWordUser(String word) {
        if(this.userDictionary.containsKey(word)){
            this.userDictionary.remove(word, word);
        }
    }
}