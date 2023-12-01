/**
 * This class is used for Integration testing of the program. Specifically, this testing focuses on the smooth flow between the created
 * classes and their respective functions, making sure the program inherits changes correctly
 * @author Ojas Hunjan
 */

import org.junit.jupiter.api.Test;//import unit tests
import java.util.Hashtable;//import hash table functionality
import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {//creation of class

    @Test
    /**
     * This method gets adds words to the user and test dictionary and combines them and then compares it to another created dictionary
     * to see if they are the same after functions have been applied
     * @param none
     * @return void
     */
    public void OutputofCombiningUserandTestDictionary() {//test for combining user and test dictionary

        Dictionary dict = new Dictionary();//create new dictionary object
        Hashtable<String, String> testDict = dict.getDictionary();//get test dict from object
        Hashtable<String, String> userDict = dict.getUserDictionary();//get user dict from object
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();//get combined dict from the object
        userDict.put("test", "test");//add word test to user dict
        testDict.put("program", "program");//add word program to test dict
        dict.combineDictionary();//combine the user and test dict
        combineddict.put("test", "test");//add test to combined dict
        combineddict.put("program", "program");//add program to combined dict
        assertEquals(combineddict, dict.getCombinedDictionary());//compare the two dictionaries should have same contents

    }

    @Test
    /**
     * This test fixes an error in the user dict before combining with the test dict. It ensures that checkSpelling methods
     * apply correctly to the created dictionaries.
     * @param none
     * @return node
     *
     */
    public void fixErrorinuserDictbeforeCombining() {

        checkSpelling corrections = new checkSpelling();//new checkspelling object
        Dictionary dict = new Dictionary();//new dictionary object
        Hashtable<String, String> testDict = dict.getDictionary();//get test dict
        Hashtable<String, String> userDict = dict.getUserDictionary();//get user dict
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();//get combined dict
        userDict.put("tes", "test");//add word tes to user dict
        testDict.put("program", "program");//add word program to test dict
        corrections.setDictionary(userDict);//set dictionary to user dict
        corrections.getInsertion("tes");//call insertion error function in userdict to fix error
        dict.combineDictionary();//combine the dictionaries
        combineddict.put("test", "test");//add test to combined dict
        combineddict.put("program", "program");//add program to combined dict
        assertEquals(combineddict, dict.getCombinedDictionary());//compare the two dicts should have same contents

    }

    @Test
    /**
     * This test fixes an error in the test dictionary before combining with user dict. It ensures that checkSpelling methods
     * apply correctly to the created dictionaries.
     * @param none
     * @return none
     */
    public void fixErrorintestDictbeforeCombining() {

        checkSpelling corrections = new checkSpelling();//create new checkSpelling object
        Dictionary dict = new Dictionary();//create new dictionary object
        Hashtable<String, String> testDict = dict.getDictionary();//get test dict
        Hashtable<String, String> userDict = dict.getUserDictionary();//get user dict
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();//get combined dict
        userDict.put("test", "test");//put word test in user dict
        testDict.put("pprogram", "program");//put word pprogram in test dict
        corrections.setDictionary(userDict);//set userdict to be the dictionary
        corrections.getOmission("pprogram");//corrects word in test dict before combining
        dict.combineDictionary(); //combines the dictionaries
        combineddict.put("test", "test");//put test in combined dict
        combineddict.put("program", "program");//put program in combined dict
        assertEquals(combineddict, dict.getCombinedDictionary());//compare two dictionaries should have same contents

    }

    @Test
    /**
     * This test adds a word after the dictionary has combined. It ensures that even after the creation of one dictionary, it can
     * still be updated and compared.
     * @param none
     * @return void
     */
    public void addwordafterDictionaryisCombined() {

        Dictionary dict = new Dictionary();//create new dictionary
        Hashtable<String, String> testDict = dict.getDictionary();//get test dict
        Hashtable<String, String> userDict = dict.getUserDictionary();//get user dict
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();//get combined dictionary
        testDict.put("test", "test");//add word test into test dict
        userDict.put("the", "the");//add word the intouser dict
        combineddict.put("test", "test");//add test to combined dict
        combineddict.put("the", "the");//add the to combined dict
        combineddict.put("program", "program");//add program to combined dict
        dict.combineDictionary();//combine user and test dict
        dict.getDictionary().put("program", "program");//add program to the combined dict
        assertEquals(combineddict, dict.getCombinedDictionary());//compare the two dictionaries should have same contents

    }

    @Test
    /**
     * This test removes a word after the dictionary has been combined. It ensures that even after the creation of one dictionary, it can
     * still be updated and compared.
     * @param none
     * @return void
     */
    public void removewordafterDictionaryisCombined() {

        Dictionary dict = new Dictionary();//create new dict object
        Hashtable<String, String> testDict = dict.getDictionary();//get test dict
        Hashtable<String, String> userDict = dict.getUserDictionary();//get user dict
        Hashtable<String, String> combineddict = dict.getCombinedDictionary();//get combined dict
        testDict.put("test", "test");//add word test to test dict
        testDict.put("the", "the");//add word the to test dict
        userDict.put("program", "program");//add word program to user dict
        combineddict.put("test", "test");//add word test to combined dict
        combineddict.put("the", "the");//add word the to combined dict
        dict.combineDictionary();//combine dictionary
        dict.getDictionary().remove("program", "program");//remove word program from combined dict
        assertEquals(combineddict, dict.getCombinedDictionary());//compare two dictionaries should have same contents

    }

}