import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Dictionary {

    private List<String> wordList;

    private List<String> dictionary;

    private List<String> userDictionary;

    private List<String> combinedDictionary;

    public Dictionary() {

        this.wordList = new ArrayList<>();
        this.dictionary = new ArrayList<>();
        this.userDictionary = new ArrayList<>();
        this.combinedDictionary = new ArrayList<>();
    }

    public List<String> loadDictionary(String nameofDictionary) {

        List<String> wordsToadd = new ArrayList<>();

        try {
            BufferedReader dictionaryReader = new BufferedReader(new FileReader(nameofDictionary));

            String dictionaryLine;

            try {

                while ((dictionaryLine = dictionaryReader.readLine()) != null) {

                    wordsToadd.add(dictionaryLine);


                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }

        } 
        
        catch (FileNotFoundException e) {

            System.out.println("Error, dictionary is not found.");
        }

        return wordsToadd;

    }

    public List<String> loadUserDictionary(String nameofUserDictionary) {

        List<String> wordsToadd = new ArrayList<>();

        try {
            BufferedReader dictionaryReader = new BufferedReader(new FileReader(nameofUserDictionary));

            String dictionaryLine;

            try {

                while ((dictionaryLine = dictionaryReader.readLine()) != null) {

                    wordsToadd.add(dictionaryLine);


                }
            } 
            catch (IOException e) {
                e.printStackTrace();
            }

        } 
        
        catch (FileNotFoundException e) {

            System.out.println("Error, dictionary is not found.");
        }

        return wordsToadd;
    }

    public List<String> combineDictionary(List<String> wordList, List<String> userDictionary) {

        this.combinedDictionary.clear();

        this.combinedDictionary.addAll(wordList);

        this.combinedDictionary.addAll(userDictionary);

        return this.combinedDictionary;
    }

    public List<String> addWord(String word, List<String> wordList) {

        wordList.add(word);

        return wordList;

    }

    public List<String> removeWord(String word, List<String> wordList) {

        wordList.remove(word);

        return wordList;

    }

    public List<String> getwordList() {

        return this.wordList;
    }

    public List<String> getDictionary() {

        return this.dictionary;
    }

    public List<String> getUserDictionary() {

        return this.userDictionary;
    }

    public List<String> getCombinedDictionary() {

        return this.combinedDictionary;
    }


    
}
