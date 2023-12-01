/**
 * This class is is used to generate possible corrections for misspelled words
 * @author Ross Cameron, Jwalant Patel, Lance Cheong Youne
 */

import java.util.Arrays;
import java.util.Hashtable;

public class checkSpelling {
    //initialize private variable to be used in later methods
    private String originalWord;
    private Hashtable<String, String>dictionary;
    private Hashtable<String, String>userDictionary;
    private String textDocument;
    private Hashtable<Character, Integer>punctuationIndex;
    private char punctuationRemoved;
    private boolean capFirst;
    private String noPunctuationWord;
    
    /**
     * class constructor method
     * sets initial value for necessary variables
     */
    public checkSpelling(){
        this.textDocument = new String();
        this.dictionary = new Hashtable<String, String>();
        this.userDictionary = new Hashtable<String, String>();
        this.originalWord = new String();
        this.punctuationIndex = new Hashtable<Character, Integer>();
        this.punctuationRemoved = ' ';
        this.noPunctuationWord = new String();
        this.capFirst = true;
    }
    
    /**
     * check method returns true if input ch (type char) is a punctuation mark
     * @param ch a character from a string which another method is checking
     * @return boolean is true or false
     */
    public boolean check(char ch) {
        return "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".indexOf(ch) != -1;
    }
    
    /**
     * isPunctuationPresent method checks if string contains a punctuation
     * <p>
     * iterates over characters in String word and utilizes check() method 
     * to determine if any character in the String is a punctation mark
     * <p>
     * @param word is the the word to be checked for punctuation
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
     * method removes punctuation by finding the punctuation, removes it, saves/stores the
     * index of the punctuation and store the punctuation itself
     * <p>
     * @param word is the word to be stripped of punctuation
     * @return String is the new version of the word passed with no punctuation
     */
    public String removePunctuations(String word) {
        char[] temp = word.toCharArray();
        char[] edited_temp = new char[temp.length]; 
        for (int i = 0; i < word.length(); i++){
            if (check(temp[i])){
                punctuationIndex.put(temp[i], i); //punctuation and index is stored
                punctuationRemoved = temp[i]; //punctuation is stored
                
                //skips puncation and stores in previous index
                for(int j = i; j < word.length()-1; j++){
                    edited_temp[j] = temp[j+1];
                }
                break;
            }
            else {  
                edited_temp[i] = temp[i];
            }
        }
        
        for (int i = 0; i < word.length(); i++){
            // Checks for punctuatuiosn that end sentences and sets capFirst to true
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
     * addPunctuation method reinserts punctuation into the original index in the correct word
     * <p>
     * makes two substrings and combine them while inserting the punctuation at the right index
     * <p>
     * @param correctWord is the word with the punctuation removed
     * @return String is the word with punctuation added back
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
     * @param dictionary Hashtable<String, String> containing the dictionary
     * @return void
     */
    public void setDictionary(Hashtable<String, String> dictionary){
        this.dictionary = dictionary;
    }

    /**
     * setUserDictionary simple setter method used to initialize the user dictionary
     * @param userDictionary Hashtable<String, String> containing the user dictionary
     * @return void
     */
    public void setUserDictionary(Hashtable<String, String> userDictionary){
        this.userDictionary = userDictionary;
    }
    
    /**
     * getIncorrect method gets the next incorrect word
     * <p>
     * test for each case where a word can be spelled incorrectly
     * and returns the word if so
     * <p>
     * @param none
     * @return String is the incorrect word returned 
     */
    public String getIncorrectWord() {
        // Splits textDocument into lines 
        String[] textLines = textDocument.split("\\s+ ");
        String[] textWords;
        for(int l = 0; l < textLines.length; l++){
            // Splits textLines into words
            textWords = new String[textLines[l].length()-1];
            textWords = textLines[l].split(" ");

            // Iterate through the words
            for (int i = 0; i < textWords.length; i++) { 
                // Checks if the first letter is supposed to be capitalized or at index 0
                if(this.capFirst == true){ //check for first leter being capital
                    char temp = textWords[i].charAt(0);
                    this.capFirst = false; //set following word to be lowercase
                    if(temp != textWords[i].toUpperCase().charAt(0)){ // example: this != This
                        this.capFirst = false; //set following word to be lowercase
                        this.originalWord = textWords[i];
                        return this.originalWord; //return this as a capitalization error
                    }
                }
                
                // Checks if punctuation is present
                if (isPunctuationPresent(textWords[i])){
                    // remove punctuation
                    textWords[i] = removePunctuations(textWords[i]);

                    // Checks if words is contained in user Dictionary
                    if(this.userDictionary.containsKey(textWords[i])){
                        this.capFirst = false;
                        continue; // go to next word
                    }
                    
                    // Checks if lower case word is contained in dictionarye
                    if(!this.dictionary.containsKey(textWords[i].toLowerCase())){
                        // If there is a hyphen
                        if(punctuationRemoved == '-'){
                            // add punctuation back (example: co-worker)
                            this.originalWord = addPunctuation(textWords[i]);
                            this.capFirst = false;
                            return this.originalWord;
                        }
                        // do not add punctuation back (example: assignment. is not correct)
                        this.originalWord = removePunctuations(textWords[i]);
                        return this.originalWord;
                    }
                }

                // Checks if words is contained in user Dictionary
                if(this.userDictionary.containsKey(textWords[i])){
                    this.capFirst = false;
                    continue; // go to next word
                }

                // Checks if any letter apart from the first one of each word is wrongly capitalized
                String word = textWords[i];
                String word2 = word.substring(0, 1).toLowerCase() + word.substring(1); 
                if (!this.dictionary.containsKey(word2)){
                    this.originalWord = textWords[i];
                    this.capFirst = false;
                    return this.originalWord;
                }

                // If i still less than textWords.length
                if(i < textWords.length-1){
                    // Checks and returns errors for double words (example: pizza pizza, This this)
                    if(textWords[i].toLowerCase().equals(textWords[i+1].toLowerCase())){ // checks if word 
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

     /**
     * capitalization method take in string and capitalizes first character
     * @param word is the lowercase word
     * @return String is spelled the same as input but the first letter is capitalized
     */
    public String capitalization(String word) {                 // test
        String s1 = word.substring(0, 1).toUpperCase();        // first letter t
        String s2 = word.substring(1);                        // After 1st letter est
        word = s1.toUpperCase() + s2;                        // T + est
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
        if (isPunctuationPresent(word)){ // if punctuation found in word
            word = removePunctuations(word); //remove punctuation
            word = substitution(word); // check for corrections
            word = addPunctuation(word); // reinsert punctuation
            return word;
        }
        else {
            word = substitution(word);
            return word;
        }
    }
    /**
     * substitution method swaps the letter at index i for each letter in aplhabet (each time indexing dictionary) before moving onto the next index (i)
     * @param word is the correctly spelled word 
     * @return String is the correctly spelled word or 'none'
     */
    private String substitution(String word){
        char alphabet[]= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; // create alphabet array
        char[] arr_word = word.toCharArray(); //convert string char[]
        
        for(int k=0;k<arr_word.length;k++){ //iterate through each character in word
            char[] edited_arr_word = Arrays.copyOf(arr_word, arr_word.length); //copy arr_word into new char[] to ensure array is reset at each iteration
            for(int i=0;i<alphabet.length;i++){ //iterate through alphabet array
                edited_arr_word[k] = alphabet[i]; //set letter in word at index i to letter in alphabet at index k
                String str_word = String.valueOf(edited_arr_word); //covert to string
                if(this.dictionary.containsKey(str_word.toLowerCase())){ //index dictionary
                    return str_word; //return correction
                }
            }
        }
        return "none"; //if no correction found return 'none'
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
    /**
     * omission method removes letter at index i from the word, then indexes dictionary before iterating to the next index
     * @param word is the correctly spelled word 
     * @return String is the correctly spelled word or 'none'
     */
    private String omission(String word) {
        char[] arr_word = word.toCharArray(); //convert string word to array
        for(int k=0;k<arr_word.length;k++){  //iterate through each letter in word
            char[] edited_arr_word = new char[arr_word.length-1];  //create new array one lement smaller than word
            int j = 0;
            for(int i=0;i<arr_word.length;i++){//shift all letters after index back one
                if(i != k){
                    edited_arr_word[j++] = arr_word[i];
                }
            }
            String str_word = String.valueOf(edited_arr_word).trim();  //convert to string and trim
            if(this.dictionary.containsKey(str_word.toLowerCase())) { //index dictionary
                return str_word; //return correction
            }
        }
        return "none"; //if no correction found return 'none'
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
    /**
     * insertion method inserts a new character before and after each letter in the word then indexes the dictionary
     * <p>
     * method iterates over each index of word, then tries adding every character in the aplhabet to that position
     * <p>
     * @param word is the correctly spelled word 
     * @return String is the correctly spelled word or 'none'
     */
    private String insertion(String word) {
        char alphabet[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; //create char[] with all letter in alphabet
        char[] arr_word = word.toCharArray();//convert string word to char[]
        for (int i = 0; i < arr_word.length + 1; i++) { //for loop which iterates one more time than the length of arr_word
            for (int j = 0; j < alphabet.length; j++) { //for each letter in aplhabet
                char[] insert_arr_word = new char[arr_word.length + 1]; //create an array with one more element than arr_word
                for (int k = 0; k < i; k++) { //copy arr_word beofre index i to new array
                    insert_arr_word[k] = arr_word[k]; 
                }
                insert_arr_word[i] = alphabet[j]; //add new character
                for (int k = i; k < arr_word.length; k++) { //add letters from arr_word following index i
                    insert_arr_word[k + 1] = arr_word[k];
                }
                String str_word = String.valueOf(insert_arr_word).trim(); //convert to string and trim
                if (this.dictionary.containsKey(str_word.toLowerCase())) { //index dictionary
                    return str_word; //return correction
                }
            }
        }
        return "none"; //if no corrections found, return 'none'
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
        if (isPunctuationPresent(word)){ 
            word = removePunctuations(word); 
            word = insertionSpace(word); 
            word = addPunctuation(word); 
            return word;
        }
        else {
            word = insertionSpace(word);
            return word;
        }
    }
    /**
     * insertionSpace method splits word into two words and indexs both in dictionary
     * <p>
     * method iterates over each index of word and uses that index as the split point for the two words
     * <p>
     * @param word is the correctly spelled word 
     * @return String is the correctly spelled word or 'none'
     */
    public String insertionSpace(String word) {
        char[] arr_word = word.toCharArray(); // convert string word to char[] to allow editing of word before indexing dictionary
        for (int i = 1; i < arr_word.length; i++) { //iterate through word starting after first letter
            char[] str1 = new char[i]; //char[] is length of i (number of characters in first section of word)
            char[] str2 = new char[arr_word.length-i]; //str2 char[] is length of word - i (length of second section of word)
            for (int k = 0; k < i; k++) { // add characters to str1
                str1[k] = arr_word[k]; 
            }
            for (int k = 0; k < str2.length; k++) { // add characters to str2
                str2[k] = arr_word[k+i];
            }
            String str1_word = String.valueOf(str1).trim(); //convert char arrays to strings to index dictionary
            String str2_word = String.valueOf(str2).trim(); // trim both char arrays to ensure that no empty elements are included
            if (this.dictionary.containsKey(str1_word.toLowerCase()) && this.dictionary.containsKey(str2_word.toLowerCase())) { //index dictionary to see if both strings are words
                char[] final_words = new char[arr_word.length+1]; //create char array to contain both words and space in between
                for(int k = 0; k<str1.length;k++){ //add first word
                    final_words[k] = str1[k];
                }
                final_words[str1.length] = ' '; //add space
                for(int k = 0; k<str2.length;k++){ //add second word
                    final_words[k+str1.length+1] = str2[k];
                }
                String stringToReturn = String.valueOf(final_words).trim(); // trim char array
                return stringToReturn; //return string
            }
            
        }
        return "none"; //if no corrections found return 'none'
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
    /**
     * reversal method swaps each letter with the following letter in word and indexes the new word in dictionary
     * @param word is the correctly spelled word 
     * @return String is the correctly spelled word or 'none'
     */
    private String reversal(String word) {
        char[] arr_word = word.toCharArray(); // convert string to char[] to allow for editing before indexing
        for(int i = 0; i < ((arr_word.length)-1); i++){ //iterate through word and stop before reaching last index
            char[] edited_arr_word = Arrays.copyOf(arr_word, arr_word.length); //recopy char array arr_word into new char array to ensure the array reset at every iteration
            char temp; 
            temp = edited_arr_word[i]; // save letter at first index
            edited_arr_word[i] = edited_arr_word[i+1]; //set first index to letter at second index
            edited_arr_word[i+1] = temp; // set senond index to saved letter at temp
            String str_word = String.valueOf(edited_arr_word); //convert to string
            str_word.trim(); //trim
            if (this.dictionary.containsKey(str_word.toLowerCase())) { //index dictionary
                return str_word; //return correction
            }
        }
        return "none"; //if no correction return 'none'
    }

}