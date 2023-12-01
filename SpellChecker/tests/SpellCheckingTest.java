import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Hashtable;

/**
 * This class utlizied J-Unit testing in order to test individual aspects of the program related to making sure the various spell checking 
 * methods work and provide the right correction.
 */
public class SpellCheckingTest {

    private checkSpelling spellChecker;//private checkSpelling object is created
    private Hashtable<String, String> dictionary;//private hashtable dictionary is created

    @Before
    /**
     * Before running tests the dictionary is set up and set as the dictionary for the spellchecker object
     * @param none
     * @return void
     */
    public void setUp() {
        spellChecker = new checkSpelling();//create checkSpelling object
        dictionary = new Hashtable<>();//create dictionary as Hashtable
        // Populate the dictionary with correct words
        dictionary.put("hello", "hello");//add word hello to dict
        dictionary.put("world", "world");//add word world to dict
        spellChecker.setDictionary(dictionary);//sets the created dictionary as the dict for the spellchecker object
    }

    @Test
    /**
     * This test tests the insertion correction method that was implemented. For the test to pass, the word returned
     * by the method must equal the correct word
     * @param none
     * @return void
     */
    public void testInsertion() {
        //dictionary.put("hell", "helloo"); // Assuming "hell" is the omitted version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getInsertion("hell"));//if equal tests pass
    }

    @Test
    /**
     * This test tests the omission correction method that was created. For the test to pass, the word return bu the 
     * method must equal the correct word
     * @param none
     * @return void
     */
    public void testOmission() {
        //dictionary.put("hhello", "hello"); // Assuming "hhello" is the inserted version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getOmission("hhello"));//if equal tests pass
    }

    @Test
    /**
     * This test tests the substitution correction method that was created. For the test to pass, the word returned by the method
     * must equal the correct word
     * @param none
     * @return void
     */
    public void testSubstitution() {
        //dictionary.put("jello", "hello"); // Assuming "jello" is the substituted version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getSubstitution("jello"));//if equal tests pass
    }

    @Test
    /**
     * This test tests the reversal method that was created. For the test to pass, the word returned by the method must equal 
     * the correct word.
     * @param none
     * @return void
     */
    public void testReversal() {
        //dictionary.put("ehllo", "hello"); // Assuming "ehllo" is the reversed version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getReversal("ehllo"));//if equal tests pass
    }

    @Test
    /**
     * This test tests the insertion method that was created. For the test to pass, the word returned by the method must equal the 
     * correct word.
     * @param none
     * @return void
     */
    public void testInsertionSpace() {
        //dictionary.put("he llo", "hello"); // Assuming "he llo" is the spaced version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello world", spellChecker.getInsertionSpace("helloworld"));//if equal tests pass
    }

}
