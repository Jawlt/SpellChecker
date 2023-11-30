import java.io.File;
import java.util.*;
import java.util.Map.Entry;

// Dictionary(child class) inherits methods from userDictionary(super class)
public class Dictionary extends userDictionary{
    private Hashtable<String, String> dictionary;
    private Hashtable<String, String> userDictionary;
    private Hashtable<String, String> combinedDictionary; 

    // Dictionary constructor
    public Dictionary() {
        super(); //call userDictionary constructor
        this.dictionary = new Hashtable<String, String>();
        this.userDictionary = super.getUserDictionary();
        this.combinedDictionary = new Hashtable<String, String>();
    }

    // Get dictionary method
    public Hashtable<String, String> getDictionary(){
        return this.dictionary;
    }

    // Loads words from the Dictionary text file to the Dictionary hashtable
    public Hashtable<String, String> loadDictionary() {
        this.dictionary.clear();

        try {
            File githubFile = new File("./SpellChecker/words_alpha.txt");
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
        setDictionary(this.dictionary);
        
        return this.dictionary;
    }

    // Combines userDictionary and Dictionary into combinedDictionary hashtable
    public void combineDictionary() {
        this.combinedDictionary.clear();

        this.userDictionary = getUserDictionary();
        
        // Combine userDictionary and dictionary into one hashtable
        this.combinedDictionary.putAll(dictionary);

        // Iterate through userDictionary and add unique entries to the combined hashtable
        // Store all Hashtable entries into a Set ussing entrySet() method
        for (Entry<String, String> entry: userDictionary.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Check if the key already exists in the combined hashtable
            if (!combinedDictionary.containsKey(key)) {
                // If the key is not already present, add the key-value pair
                combinedDictionary.put(key, value);
            } 
        }
        setDictionary(this.combinedDictionary);
        setUserDictionary(this.userDictionary);
    }
    
    //removes word from userDictionary if dictionary contains word
    public void removeWordDictionary(String word) {
        if(this.dictionary.containsKey(word)){
            this.dictionary.remove(word, word);
        }
    }
    
    /** Testing the dictionary/user dictionary: [UPDATE: it works now ~lance, jawlt*/
    public static void main(String[] args) {
        Dictionary test = new Dictionary();
        test.loadDictionary();

        
        //System.out.println(test.reversal("phtoo"));
        //System.out.println(test.substitution("aplpe"));
        
    
        //System.out.println(test.omission("aplpe"));
        //System.out.println(test.insertionSpace("isa"));
        //System.out.println(test.insertionSpace("notworking"));
        //System.out.println(test.addPunctuation("assignment"));
        //System.out.println(test.addPunctuation(test.removePunctuations("code")));
        //System.out.println(test.addPunctuation(test.removePunctuations("co-wrrker")));
    }
}
