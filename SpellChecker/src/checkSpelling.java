import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class checkSpelling {

    private String correctedWord;
    private String originalWord;
    private Hashtable<String, String>dictionary;
    private String textDocument;
    private Hashtable<String, String>incorrectWordList;
    private Hashtable<Character, Integer>punctuationIndex; //
    private char punctuationRemoved;
    private boolean capFirst;
    private String noPunctuationWord;
    

    public checkSpelling(){
        this.textDocument = new String();
        this.dictionary = new Hashtable<String, String>();
        this.correctedWord = new String();
        this.originalWord = new String();
        this.incorrectWordList = new Hashtable<String, String>();
        this.punctuationIndex = new Hashtable<Character, Integer>();
        this.punctuationRemoved = ' ';
        this.noPunctuationWord = new String();
        this.capFirst = true;
    }

    //returns true if it finds any punctuations
    public boolean check(char ch) {
        return "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".indexOf(ch) != -1;
    }
    
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

    public String removePunctuations(String word) {
        char[] temp = word.toCharArray();
        char[] edited_temp = new char[temp.length]; 
        for (int i = 0; i < word.length(); i++){
            if (check(temp[i])){
                punctuationIndex.put(temp[i], i);
                punctuationRemoved = temp[i]; //punctuation is stored
                break;
            }
            else {
                edited_temp[i] = temp[i];
            }
        }
        for (int i = 0; i < word.length(); i++){
            if (temp[i] == '.'){
                this.capFirst = true;
                break;
            }
            else {
                this.capFirst = false;
            }
        }
        noPunctuationWord =  String.valueOf(edited_temp).toLowerCase().trim();
        return noPunctuationWord;
    }

    public String addPunctuation(String correctWord) {
        correctWord = correctWord.substring(0, punctuationIndex.get(punctuationRemoved)) + punctuationRemoved + correctWord.substring(punctuationIndex.get(punctuationRemoved));
        return correctWord;
    }

    public void setTextDoc(String textDoc){
        this.textDocument = textDoc;
    }

    public void setDictionary(Hashtable<String, String> dictionary){
        this.dictionary = dictionary;
    }

    public String getIncorrectWord() {
        String[] textWords = textDocument.split(" ");
        
        for (int i = 0; i < textWords.length; i++) {
            if(this.capFirst == true){
                char temp = textWords[i].charAt(0);
                System.out.println("original temp is: " + temp);
                this.capFirst = false;
                if(temp != textWords[i].toUpperCase().charAt(0)){ //this != This
                    System.out.println("word is: " + temp + "!=" + textWords[i].toUpperCase().charAt(0));
                    this.capFirst = false;
                    this.originalWord = textWords[i];
                    return this.originalWord; //return this as a capitalization error
                }
            }
            
            if (isPunctuationPresent(textWords[i])){
                textWords[i]= removePunctuations(textWords[i]);
            }
            
            
            if (!this.dictionary.containsKey(textWords[i].toLowerCase())){ 
                this.originalWord = textWords[i];
                return this.originalWord;
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
    

    // public String findCorrections(String focusedWord) {
    //     boolean checkCap = false;
    //     String returnWord = "none";
    //     String lowerCaseWord = focusedWord.toLowerCase();
    //     if(!(this.dictionary.containsKey(lowerCaseWord))){
    //         String ins_space = insertionSpace(lowerCaseWord);
    //         String sub = substitution(lowerCaseWord);
    //         String omi = omission(lowerCaseWord);
    //         String ins = insertion(lowerCaseWord);
    //         String rev = reversal(lowerCaseWord);
    //         if(ins_space!="none"){
    //             returnWord = ins_space;
    //         }
    //         else if(rev!="none"){
    //             returnWord = rev;
    //         }
    //         else if(sub!="none"){
    //             returnWord = sub;
    //         }
    //         else if(ins!="none"){
    //             returnWord = ins;
    //         }
    //         else if(omi!="none"){
    //             returnWord = omi;
    //         }
    //     }
        
    //     char alphabet[]= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    //     char[] arr_focusedWord = focusedWord.toCharArray();
    //     for(int i = 0; i<alphabet.length; i++){
    //         if(arr_focusedWord[0] == alphabet[i]){
    //             char[] arr_returnWord = returnWord.toCharArray();
    //             arr_returnWord[0] = Character.toUpperCase(arr_returnWord[0]);
    //         }
    //     }
            
        
    //     return returnWord;
    // }
    
    
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
                str_word = str_word.toLowerCase();
                if(this.dictionary.containsKey(str_word)){ 
                    return str_word; 
                }
            }
        }
        return "none";
    }
    
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
            String str_word = String.valueOf(edited_arr_word).toLowerCase().trim(); 
            if(this.dictionary.containsKey(str_word)) { 
                return str_word;
            }
        }
        return "none"; 
    }

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
                String str_word = String.valueOf(insert_arr_word).trim().toLowerCase();
                if (this.dictionary.containsKey(str_word)) {
                    return str_word;
                }
            }
        }
        return "none";
    }

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
    
    private String insertionSpace(String word) {
        char[] arr_word = word.toCharArray();
        for (int i = 1; i < arr_word.length; i++) {
            char[] insert_arr_word = new char[arr_word.length + 1]; 
            for (int k = 0; k < i; k++) {
                insert_arr_word[k] = arr_word[k]; 
            }
            insert_arr_word[i] = ' ';
            for (int k = i; k < arr_word.length; k++) {
                insert_arr_word[k + 1] = arr_word[k];
            }
            String str_word = String.valueOf(insert_arr_word).trim().toLowerCase();
            if (this.dictionary.containsKey(str_word)) {
                return str_word;
            }
            
        }
        return "none";
    }

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
        for(int i=0;i<((arr_word.length)-1);i++){ 
            char[] edited_arr_word = Arrays.copyOf(arr_word, arr_word.length); 
            char temp; 
            temp = edited_arr_word[i];
            edited_arr_word[i] = edited_arr_word[i+1];
            edited_arr_word[i+1] = temp;
            String str_word = String.valueOf(edited_arr_word);
            str_word.trim(); 
            str_word.toLowerCase();
            if (this.dictionary.containsKey(str_word)) {
                return str_word; 
            }

        }
        return "none";
    }

}