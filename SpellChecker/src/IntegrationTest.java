import org.junit.jupiter.api.Test;

//import java.util.Dictionary;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    @Test
    public void OutputofCombiningUserandTestDictionary() {

        checkSpelling corrections = new checkSpelling();
        Dictionary dict = new Dictionary();
        Hashtable<String, String> testDict = dict.getDictionary();
        Hashtable<String, String> userDict = dict.getUserDictionary();
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();
        userDict.put("test", "test");
        testDict.put("program", "program");
        dict.combineDictionary();
        combineddict.put("test", "test");
        combineddict.put("program", "program");
        assertEquals(combineddict, dict.getCombinedDictionary());

    }

    @Test
    public void fixErrorinuserDictbeforeCombining() {

        checkSpelling corrections = new checkSpelling();
        Dictionary dict = new Dictionary();
        Hashtable<String, String> testDict = dict.getDictionary();
        Hashtable<String, String> userDict = dict.getUserDictionary();
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();
        userDict.put("tes", "test");
        testDict.put("program", "program");
        corrections.setDictionary(userDict);
        corrections.getInsertion("tes");
        dict.combineDictionary();
        combineddict.put("test", "test");
        combineddict.put("program", "program");
        assertEquals(combineddict, dict.getCombinedDictionary());

    }

    @Test
    public void fixErrorintestDictbeforeCombining() {

        checkSpelling corrections = new checkSpelling();
        Dictionary dict = new Dictionary();
        Hashtable<String, String> testDict = dict.getDictionary();
        Hashtable<String, String> userDict = dict.getUserDictionary();
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();
        userDict.put("test", "test");
        testDict.put("pprogram", "program");
        corrections.setDictionary(userDict);
        corrections.getOmission("pprogram");
        dict.combineDictionary(); 
        combineddict.put("test", "test");
        combineddict.put("program", "program");
        assertEquals(combineddict, dict.getCombinedDictionary());

    }

    @Test
    public void addwordafterDictionaryisCombined() {

        Dictionary dict = new Dictionary();
        Hashtable<String, String> testDict = dict.getDictionary();
        Hashtable<String, String> userDict = dict.getUserDictionary();
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();
        testDict.put("test", "test");
        userDict.put("the", "the");
        combineddict.put("test", "test");
        combineddict.put("the", "the");
        combineddict.put("program", "program");
        dict.combineDictionary();
        dict.getDictionary().put("program", "program");
        assertEquals(combineddict, dict.getCombinedDictionary());

    }

    @Test
    public void removewordafterDictionaryisCombined() {

        Dictionary dict = new Dictionary();
        Hashtable<String, String> testDict = dict.getDictionary();
        Hashtable<String, String> userDict = dict.getUserDictionary();
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();
        testDict.put("test", "test");
        testDict.put("the", "the");
        userDict.put("program", "program");
        combineddict.put("test", "test");
        combineddict.put("the", "the");
        dict.combineDictionary();
        dict.getDictionary().remove("program", "program");
        assertEquals(combineddict, dict.getCombinedDictionary());

    }

}