/**
 * Purpose of this class is to represent the dictionaries for the SpellChecker
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;//packages to use in class

public class Dictionary {//creation of class

    private List<String> wordList;//private instance variable for wordList

    private List<String> dictionary;//private instance variable for dictionary

    private List<String> userDictionary;//private instance variable for user dictionary

    private List<String> combinedDictionary;//private instance variable for combined dictionary

    /**
     * Constructor method that sets instance variables as new array lists
     */
    public Dictionary() {//create constructor method

        this.wordList = new ArrayList<>();//create word list
        this.dictionary = new ArrayList<>();//create dictionary
        this.userDictionary = new ArrayList<>();//create user dictionary
        this.combinedDictionary = new ArrayList<>();//create combined dictionary
    }

    /**
     * This method loads the dictionary given the name of the dictionary as the parameter (filepath). It returns this dictionary.
     * @param nameofDictionary
     * @return
     */
    public List<String> loadDictionary(String nameofDictionary) {//creation of this method

        List<String> wordsToadd = new ArrayList<>();//create new array list of words to add to the return dictionary from the loaded dictionary.

        try {
            BufferedReader dictionaryReader = new BufferedReader(new FileReader(nameofDictionary));//bufferedreader to read the dictionary given the file of the dict

            String dictionaryLine;//string reprresenting the word/line of the dictionary

            try {

                while ((dictionaryLine = dictionaryReader.readLine()) != null) {//while loop for is the line of the dictionary is not null (has a word there)

                    wordsToadd.add(dictionaryLine);//add this word to the created array list


                }
            } 
            catch (IOException e) {//catches exception if the dictionary is not found
                e.printStackTrace();
            }

        } 
        
        catch (FileNotFoundException e) {//catches exception if the file for the dictionary is not found

            System.out.println("Error, dictionary is not found.");//prints error message
        }

        return wordsToadd;//return the loaded dictionary

    }

    /**
     * This method loads the user dictionary given the name of the user dictionary as the parameter (filepath). It returns this dictionary.
     * @param nameofUserDictionary
     * @return
     */
    public List<String> loadUserDictionary(String nameofUserDictionary) {//creation of this method

        List<String> wordsToadd = new ArrayList<>();//create new array list of words to add to the return user dictionary from the loaded user dictionary.

        try {
            BufferedReader dictionaryReader = new BufferedReader(new FileReader(nameofUserDictionary)); //bufferedreader to read the user dictionary given the file of the dict

            String dictionaryLine;//string reprresenting the word/line of the user dictionary

            try {

                while ((dictionaryLine = dictionaryReader.readLine()) != null) {//while loop for is the line of the user dictionary is not null (has a word there)

                    wordsToadd.add(dictionaryLine);//add this word to the created array list


                }
            } 
            catch (IOException e) {//catches exception if the user dictionary is not found
                e.printStackTrace();
            }

        } 
        
        catch (FileNotFoundException e) {//catches exception if the file for the dictionary is not found

            System.out.println("Error, dictionary is not found.");//prints error message
        }

        return wordsToadd;//return the loaded dictionary
    }

    /**
     * This method takes in the wordList and user dictionary and combines them to create one, solidified dictionary and returns it
     * @param wordList
     * @param userDictionary
     * @return
     */
    public List<String> combineDictionary(List<String> wordList, List<String> userDictionary) {//create the method

        this.combinedDictionary.clear();//clear the combined dictionary

        this.combinedDictionary.addAll(wordList);//add all the words of the word list to the combined dictionary

        this.combinedDictionary.addAll(userDictionary);//add all the words of the user dictionary to the combined dictionary

        return this.combinedDictionary;//return the combined dictionary
    }

    /**
     * This method adds a word to the wordList and returns it updated
     * @param word
     * @param wordList
     * @return
     */
    public List<String> addWord(String word, List<String> wordList) {//Creation of the method

        wordList.add(word);//add the word to the word list

        return wordList;//returns the word list

    }

    /**
     * This method removes a word from the wordList and returns it updated
     * @param word
     * @param wordList
     * @return
     */
    public List<String> removeWord(String word, List<String> wordList) {//creation of the method

        wordList.remove(word);//remove the word from the word list

        return wordList;//returns the word list

    }

    public List<String> getwordList() {//getter function for wordList

        return this.wordList;//return wordList
    }

    public List<String> getDictionary() {//getter function for dictionary

        return this.dictionary;//return dictionary
    }

    public List<String> getUserDictionary() {//getter function for user dictionary

        return this.userDictionary;//return user dictionary
    }

    public List<String> getCombinedDictionary() {//getter function for combined dictionary

        return this.combinedDictionary;//returns combined dictionary
    }


    
}
