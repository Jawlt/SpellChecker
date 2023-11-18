import java.util.*;

public class userDictionary{
    private List<String> userDictionary = new ArrayList<String>();

    public List<String> getUserDictionary(){
        return userDictionary;
    }

    public List<String> storeUserDictionary(List<String> userDictionary){
        return userDictionary;
    }

    public List<String> addWord(String word, List<String> userDictionary){
        userDictionary.add(word);
        return userDictionary;
    }

    public List<String> removeWord(String word, List<String> userDictionary){
        userDictionary.remove(word);
        return userDictionary;
    }
}