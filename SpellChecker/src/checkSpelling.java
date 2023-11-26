import java.util.Arrays;
import java.util.Hashtable;

public class checkSpelling {

    private String correctedWord;
    private String originalWord;
    public Hashtable<String, String>dictionary;
    private String textDocument;
    public Hashtable<String, String>incorrectWordList;

    public checkSpelling(){
        this.textDocument = new String();
        this.dictionary = new Hashtable<String, String>();
        this.correctedWord = new String();
        this.originalWord = new String();
        this.incorrectWordList = new Hashtable<String, String>();
    }

    public void setTextDoc(String textDoc){
        this.textDocument = textDoc;
    }

    public void setDictionary(Hashtable<String, String> dictionary){
        this.dictionary = dictionary;
    }

    public String getIncorrectWord() {
        String[] textWords = textDocument.split("\\s+");
        for (String textWord: textWords) {
            if (!this.dictionary.containsKey(textWord)){ 
                this.originalWord = textWord;
                return this.originalWord;
            
            }
        }
        return null;    
    }

    public String getCorrectWord(String originalWord) {
        this.correctedWord = findCorrections(originalWord);
        return this.correctedWord;   

    }
    
    public String findCorrections(String focusedWord) {
        boolean checkCap = false;
        String returnWord = "none";
        String lowerCaseWord = focusedWord.toLowerCase();
        if(!(this.dictionary.containsKey(lowerCaseWord))){
            String ins_space = insertionSpace(lowerCaseWord);
            String sub = substitution(lowerCaseWord);
            String omi = omission(lowerCaseWord);
            String ins = insertion(lowerCaseWord);
            String rev = reversal(lowerCaseWord);
            if(ins_space!="none"){
                returnWord = ins_space;
            }
            else if(rev!="none"){
                returnWord = rev;
            }
            else if(sub!="none"){
                returnWord = sub;
            }
            else if(ins!="none"){
                returnWord = ins;
            }
            else if(omi!="none"){
                returnWord = omi;
            }
        }
        char alphabet[]= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] arr_focusedWord = focusedWord.toCharArray();
        for(int i = 0; i<alphabet.length; i++){
            if(arr_focusedWord[0] == alphabet[i]){
                char[] arr_returnWord = returnWord.toCharArray();
                arr_returnWord[0] = Character.toUpperCase(arr_returnWord[0]);
            }
        }
            
        
        return returnWord;
    }
    
    
    public String substitution(String word) {
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

    public String omission(String word) {
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

    public String insertion(String word) {
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
    public String insertionSpace(String word) {
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

    public String reversal(String word) {
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