import java.util.*;

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

        this.userErrors = new ArrayList<>();

    }

    public HashMap<String, String> loadDictionary() {

        this.dictionary.clear();

        try {

            File githubFile = new File("words_alpha.txt");

            Scanner fileScan = new Scanner(githubFile);

            while (fileScan.hasNextLine()) {

                String dictWord = fileScan.nextLine();

                this.dictionary.put(dictWord, dictWord);

            }

            fileScan.close();
            

        } 
        
        catch (Exception e) {
            
            System.out.println("Error, could not open the file");
        }

        return this.dictionary;
    }

    public List<String> loadUserDictionary() {

        this.userDictionary.clear();

        try {

            File userDict = new File("userdictionary.txt");

            Scanner dictScan = new Scanner(userDict);

            while (dictScan.hasNextLine()) {

                String dictWord = dictScan.nextLine();

                this.userDictionary.add(dictWord);

                if(!this.dictionary.containsKey(dictWord)) {

                    this.userErrors.add(dictWord);
                }


            }

            dictScan.close();
            
        } 
        
        catch (Exception e) {
            
            System.out.println("Error, user dictionary could not be loaded");
        }

        return this.userErrors;
    }

    public List<String> combineDictionary(List<String> wordList, List<String> userDictionary) {

        this.combinedDictionary.clear();

        this.combinedDictionary.addAll(wordList);

        this.combinedDictionary.addAll(userDictionary);

        return this.combinedDictionary;

    }

    public List<String> addWord (String word, List<String> wordList) {

        wordList.add(word);

        return wordList;

    }

    public List<String> removeWord(String word, List<String> wordList) {

        wordList.remove(word);

        return wordList;
    }
    
}
