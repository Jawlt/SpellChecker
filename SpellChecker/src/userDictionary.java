import java.util.*;

public class userDictionary {
    private Hashtable<String, String> userDictionary;

    public userDictionary(){
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