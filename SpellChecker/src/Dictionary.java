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
        this.dictionary = new Hashtable<String, String>();
        this.userDictionary = new Hashtable<String, String>();
        this.combinedDictionary = new Hashtable<String, String>();
    }

    // Loads words from the Dictionary text file to the Dictionary hashtable
    public Hashtable<String, String> loadDictionary() {
        this.dictionary.clear();

        try {
            File githubFile = new File("/SpellChecker/testDictionary.txt");
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
        userDictionary.putAll(dictionary);
        this.combinedDictionary.putAll(userDictionary);
    }

    public void main(String[] args) {
        Dictionary test = new Dictionary();
        test.loadDictionary();

        userDictionary.put("jawlt", "jawlt");
        userDictionary.put("aaa", "aaa");
        userDictionary.remove("aaa");
        test.combineDictionary();

        for (Entry<String, String> entry : this.combinedDictionary.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        
        
    }
    
}
