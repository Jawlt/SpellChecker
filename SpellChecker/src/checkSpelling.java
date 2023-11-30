/**
 * This class is is used to generate possible corrections for misspelled words
 * @author Ross Cameron
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class checkSpelling {
    //initialize private variable to be used in later methods
    private String correctedWord;
    private String originalWord;
    private Hashtable<String, String>dictionary;
    private Hashtable<String, String>userDictionary;
    private String textDocument;
    private Hashtable<String, String>incorrectWordList;
    private Hashtable<Character, Integer>punctuationIndex; //
    private char punctuationRemoved;
    private boolean capFirst;
    private String noPunctuationWord;
    
    /**
     * class constructor method:
     * sets initial value for necessary variables
     */
    public checkSpelling(){
        this.textDocument = new String();
        this.dictionary = new Hashtable<String, String>();
        this.userDictionary = new Hashtable<String, String>();
        this.correctedWord = new String();
        this.originalWord = new String();
        this.incorrectWordList = new Hashtable<String, String>();
        this.punctuationIndex = new Hashtable<Character, Integer>();
        this.punctuationRemoved = ' ';
        this.noPunctuationWord = new String();
        this.capFirst = true;
    }
    
    /**
     * check method returns true if input ch (type char) is a punctuation mark
     * @param ch a character from a string which another method is checking
     * @return boolean
     */
    public boolean check(char ch) {
        return "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".indexOf(ch) != -1;
    }
    
    /**
     * isPunctuationPresent method check if string contains punctuation
     * <p>
     * iterates over characters in String word and utilizes check() method 
     * to determine if any character in the String is a punctation mark
     * <p>
     * @param word is the the word to be checked against dictionary from user text
     * @return boolean
     */
    public boolean isPunctuationPresent(String word){
        char[] temp = word.toCharArray();
        boolean isPresent = false;
        for (int i = 0; i < word.length(); i++){
            if (check(temp[i])) {
                isPresent = true;
                break;
            }
            else {
                isPresent = false;
            }
        }
        return isPresent;
    }

    /**
     * removePunctuations method stores and removes punctuation mark from string
     * <p>
     * method removes punctuation by finding the punctuation the moving each of 
     * the following characters backwards
     * <p>
     * @param word 
     * @return String new version of word with no punctuation
     */
    public String removePunctuations(String word) {
        char[] temp = word.toCharArray();
        char[] edited_temp = new char[temp.length]; 
        for (int i = 0; i < word.length(); i++){
            if (check(temp[i])){
                punctuationIndex.put(temp[i], i);
                punctuationRemoved = temp[i]; //punctuation is stored

                for(int j = i; j < word.length()-1; j++){
                    edited_temp[j] = temp[j+1];
                }
                break;
            }
            else {
                //app_l  
                edited_temp[i] = temp[i];
            }
        }
        
        for (int i = 0; i < word.length(); i++){
            if (temp[i] == '.' || temp[i] == '!' || temp[i] == '?'){
                this.capFirst = true;
                break;
            }
            else {
                this.capFirst = false;
            }
        }
        noPunctuationWord =  String.valueOf(edited_temp).trim();
        return noPunctuationWord;
    }

    /**
     * addPunctuation method reinserts punctuation into same spot in correct word
     * <p>
     * iterates over characters in String word and utilizes check() method 
     * to determine if any character in the String is a punctation mark
     * <p>
     * @param correctWord is the correctly spelled word 
     * @return String is the correctly spelled word with punctuation added back
     */
    public String addPunctuation(String correctWord) {
        correctWord = correctWord.substring(0, punctuationIndex.get(punctuationRemoved)) + punctuationRemoved + correctWord.substring(punctuationIndex.get(punctuationRemoved));
        return correctWord;
    }

    /**
     * setTextDoc simple setter method used to initialize the user's text document
     * @param textDoc String containing text document 
     * @return void
     */
    public void setTextDoc(String textDoc){
        this.textDocument = textDoc;
    }

    /**
     * setDictionary simple setter method used to initialize the dictionary
     * @param dictionary Hashtable<String, String> containing dictionary
     * @return void
     */
    public void setDictionary(Hashtable<String, String> dictionary){
        this.dictionary = dictionary;
    }

    /**
     * setUserDictionary simple setter method used to initialize the user dictionary
     * @param userDictionary Hashtable<String, String> containing user dictionary
     * @return void
     */
    public void setUserDictionary(Hashtable<String, String> userDictionary){
        this.userDictionary = userDictionary;
    }

    public String getIncorrectWord() {
        String[] textLines = textDocument.split("\\s+ ");
        String[] textWords;

        for(int l = 0; l < textLines.length; l++){
            textWords = new String[textLines[l].length()-1];
            textWords = textLines[l].split(" ");

            for (int i = 0; i < textWords.length; i++) { 
                if(this.capFirst == true){
                    char temp = textWords[i].charAt(0);
                    this.capFirst = false;
                    if(temp != textWords[i].toUpperCase().charAt(0)){ //this != This
                        this.capFirst = false;
                        this.originalWord = textWords[i];
                        return this.originalWord; //return this as a capitalization error
                    }
                }

                if (isPunctuationPresent(textWords[i])){
                    textWords[i] = removePunctuations(textWords[i]);

                    //DoG -> DoG
                    if(this.userDictionary.containsKey(textWords[i])){
                        this.capFirst = false;
                        continue;
                    }

                    if(!this.dictionary.containsKey(textWords[i].toLowerCase())){
                        if(punctuationRemoved == '-'){
                            this.originalWord = addPunctuation(textWords[i]);
                            this.capFirst = false;
                            return this.originalWord;
                        }
                        this.originalWord = removePunctuations(textWords[i]);
                        return this.originalWord;
                    }
                }

                //DoG -> Dog
                if(this.userDictionary.containsKey(textWords[i])){
                    this.capFirst = false;
                    continue;
                }

                //assignment DoG -> Dog
                String word = textWords[i];
                String word2 = word.substring(0, 1).toLowerCase() + word.substring(1); 
                
                if (!this.dictionary.containsKey(word2)){
                    this.originalWord = textWords[i];
                    this.capFirst = false;
                    return this.originalWord;
                }

                if(i < textWords.length-1){
                    if(textWords[i].toLowerCase().equals(textWords[i+1].toLowerCase())){
                        if(this.userDictionary.containsKey(textWords[i] + " " + textWords[i+1])){
                            this.capFirst = false;
                            continue;
                        } 
                        this.originalWord = textWords[i] + " " + textWords[i+1];
                        this.capFirst = false;
                        return this.originalWord;
                    }
                }
                
            }
        }
        return null;    
    }


    public String capitalization(String word) {                              // test
        String s1 = word.substring(0, 1).toUpperCase();  // first letter t
        String s2 = word.substring(1);                            // After 1st letter est
        word = s1.toUpperCase() + s2;                                        // T + est
        return word;
    }
    
    /**
     * getSubstitution method applies remove and insert punctuation methods 
     * before and after finding correct word respectively
    * <p>
     * this is done to avoid indexing the dictionary with a string 
     * that is spelled corretly but contains characters which are 
     * not present in dictionary
     * <p>
     * @param word is the incorrectly spelled word
     * @return String is the correctly spelled word with punctuation added back (if punctuation was present initially)
     */
    public String getSubstitution(String word) {
        if (isPunctuationPresent(word)){
            word = removePunctuations(word);
            word = substitution(word);
            word = addPunctuation(word);
            return word;
        }
        else {
            word = substitution(word);
            return word;
        }
    }
    private String substitution(String word){
        char alphabet[]= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; // create alphabet array
        char[] arr_word = word.toCharArray();
        
        for(int k=0;k<arr_word.length;k++){
            char[] edited_arr_word = Arrays.copyOf(arr_word, arr_word.length); 
            for(int i=0;i<alphabet.length;i++){ 
                edited_arr_word[k] = alphabet[i]; 
                String str_word = String.valueOf(edited_arr_word); 
                if(this.dictionary.containsKey(str_word.toLowerCase())){ 
                    return str_word; 
                }
            }
        }
        return "none";
    }

    /**
     * getOmission method applies remove and insert punctuation methods 
     * before and after finding correct word respectively
     * <p>
     * this is done to avoid indexing the dictionary with a string 
     * that is spelled corretly but contains characters which are 
     * not present in dictionary
     * <p>
     * @param word is the incorrectly spelled word
     * @return String is the correctly spelled word with punctuation added back (if punctuation was present initially)
     */
    public String getOmission(String word) {
        if (isPunctuationPresent(word)){
            word = removePunctuations(word);
            word = omission(word);
            word = addPunctuation(word);
            return word;
        }
        else {
            word = omission(word);
            return word;
        }
    }
    
    private String omission(String word) {
        char[] arr_word = word.toCharArray();
        for(int k=0;k<arr_word.length;k++){ 
            char[] edited_arr_word = new char[arr_word.length-1]; 
            int j = 0;
            for(int i=0;i<arr_word.length;i++){
                if(i != k){
                    edited_arr_word[j++] = arr_word[i];
                }
            }
            String str_word = String.valueOf(edited_arr_word).trim(); 
            if(this.dictionary.containsKey(str_word.toLowerCase())) { 
                return str_word;
            }
        }
        return "none"; 
    }
    /**
     * getInsertion method applies remove and insert punctuation methods 
     * before and after finding correct word respectively
     * <p>
     * this is done to avoid indexing the dictionary with a string 
     * that is spelled corretly but contains characters which are 
     * not present in dictionary
     * <p>
     * @param word is the incorrectly spelled word
     * @return String is the correctly spelled word with punctuation added back (if punctuation was present initially)
     */
    public String getInsertion(String word) {
        if (isPunctuationPresent(word)){
            word = removePunctuations(word);
            word = insertion(word);
            word = addPunctuation(word);
            return word;
        }
        else {
            word = insertion(word);
            return word;
        }
    }

    private String insertion(String word) {
        char alphabet[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] arr_word = word.toCharArray();
        for (int i = 0; i < arr_word.length + 1; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                char[] insert_arr_word = new char[arr_word.length + 1]; 
                for (int k = 0; k < i; k++) {
                    insert_arr_word[k] = arr_word[k]; 
                }
                insert_arr_word[i] = alphabet[j];
                for (int k = i; k < arr_word.length; k++) {
                    insert_arr_word[k + 1] = arr_word[k];
                }
                String str_word = String.valueOf(insert_arr_word).trim();
                if (this.dictionary.containsKey(str_word.toLowerCase())) {
                    return str_word;
                }
            }
        }
        return "none";
    }
    /**
     * getInsertionSpace method applies remove and insert punctuation methods 
     * before and after finding correct word respectively
     * <p>
     * this is done to avoid indexing the dictionary with a string 
     * that is spelled corretly but contains characters which are 
     * not present in dictionary
     * <p>
     * @param word is the incorrectly spelled word
     * @return String is the correctly spelled word with punctuation added back (if punctuation was present initially)
     */
    public String getInsertionSpace(String word) {
        if (isPunctuationPresent(word)){ // if punctuation found in word
            word = removePunctuations(word); //remove punctuation
            word = insertionSpace(word); // check for corrections
            word = addPunctuation(word); // reinsert punctuation
            return word;
        }
        else {
            word = insertionSpace(word);
            return word;
        }
    }
    
    public String insertionSpace(String word) {
        char[] arr_word = word.toCharArray(); // convert string word to char[] to allow
        for (int i = 1; i < arr_word.length; i++) {
            char[] str1 = new char[i];
            char[] str2 = new char[arr_word.length-i];
            for (int k = 0; k < i; k++) {
                str1[k] = arr_word[k]; 
            }
            for (int k = 0; k < str2.length; k++) {
                str2[k] = arr_word[k+i];
            }
            String str1_word = String.valueOf(str1).trim();
            String str2_word = String.valueOf(str2).trim();
            if (this.dictionary.containsKey(str1_word.toLowerCase()) && this.dictionary.containsKey(str2_word.toLowerCase())) {
                char[] final_words = new char[arr_word.length+1];
                for(int k = 0; k<str1.length;k++){
                    final_words[k] = str1[k];
                }
                final_words[str1.length] = ' ';
                for(int k = 0; k<str2.length;k++){
                    final_words[k+str1.length+1] = str2[k];
                }
                String stringToReturn = String.valueOf(final_words).trim();
                return stringToReturn;
            }
            
        }
        return "none";
    }
    
    /**
     * getReversal method applies remove and insert punctuation methods 
     * before and after finding correct word respectively
     * <p>
     * this is done to avoid indexing the dictionary with a string 
     * that is spelled corretly but contains characters which are 
     * not present in dictionary
     * <p>
     * @param word is the incorrectly spelled word
     * @return String is the correctly spelled word with punctuation added back (if punctuation was present initially)
     */
    public String getReversal(String word) {
        if (isPunctuationPresent(word)){
            word = removePunctuations(word);
            word = reversal(word);
            word = addPunctuation(word);
            return word;
        }
        else {
            word = reversal(word);
            return word;
        }
    }

    private String reversal(String word) {
        char[] arr_word = word.toCharArray();
        for(int i = 0; i < ((arr_word.length)-1); i++){ 
            char[] edited_arr_word = Arrays.copyOf(arr_word, arr_word.length); 
            char temp; 
            temp = edited_arr_word[i];
            edited_arr_word[i] = edited_arr_word[i+1];
            edited_arr_word[i+1] = temp;
            String str_word = String.valueOf(edited_arr_word);
            str_word.trim(); 
            if (this.dictionary.containsKey(str_word.toLowerCase())) {
                return str_word; 
            }
        }
        return "none";
    }

}