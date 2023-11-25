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
            File githubFile = new File("./SpellChecker/testDictionary.txt");
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
    }

    /** Testing the dictionary/user dictionary: [UPDATE: it works now ~lance, jawlt*/
    public static void main(String[] args) {
        Dictionary test = new Dictionary();
        test.loadDictionary();

        test.addWordUser("jawlt");
        test.addWordUser("aaa");
        
        test.combineDictionary();
        test.combinedDictionary.remove("aaa");

        for (Entry<String, String> entry : test.combinedDictionary.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        } 
    }
    
}
