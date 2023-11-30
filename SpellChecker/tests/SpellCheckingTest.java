import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Hashtable;

public class SpellCheckingTest {

    private checkSpelling spellChecker;
    private Hashtable<String, String> dictionary;

    @Before
    public void setUp() {
        spellChecker = new checkSpelling();
        dictionary = new Hashtable<>();
        // Populate the dictionary with correct words
        dictionary.put("hello", "hello");
        dictionary.put("world", "world");
        spellChecker.setDictionary(dictionary);
    }

    @Test
    public void testInsertion() {
        //dictionary.put("hell", "helloo"); // Assuming "hell" is the omitted version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getInsertion("hell"));
    }

    @Test
    public void testOmission() {
        //dictionary.put("hhello", "hello"); // Assuming "hhello" is the inserted version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getOmission("hhello"));
    }

    @Test
    public void testSubstitution() {
        //dictionary.put("jello", "hello"); // Assuming "jello" is the substituted version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getSubstitution("jello"));
    }

    @Test
    public void testReversal() {
        //dictionary.put("ehllo", "hello"); // Assuming "ehllo" is the reversed version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello", spellChecker.getReversal("ehllo"));
    }

    @Test
    public void testInsertionSpace() {
        //dictionary.put("he llo", "hello"); // Assuming "he llo" is the spaced version of "hello"
        //spellChecker.setDictionary(dictionary);
        assertEquals("hello world", spellChecker.getInsertionSpace("helloworld"));
    }

}
