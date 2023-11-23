import java.util.List;
import java.util.ArrayList;

public class spellchecker {

    private String word;
    private List<String> dictionary;
    private List<String> correctionsList;

    public void spellChecker(String w, List<String> dict, List<String> userDict){
        this.word = w;
        this.dictionary = dict;
        this.correctionsList = new ArrayList<>();
    }
    
    private static boolean inDictionary(String word, List<String> dictionary){
        if(dictionary.contains(word.toLowerCase())){
            return true;
        }
        else{
            return false;
        }
    }
    

}