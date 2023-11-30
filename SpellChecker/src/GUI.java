import java.util.*;
import java.awt.Color;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLWriter;

import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

// GUI(child class) inherits methods from Dictionary(super class)
public class GUI extends Dictionary implements ActionListener {
	private String correctSubstitutionWord, correctOmissionWord, correctInsertionWord, correctInsertionSpaceWord, correctReversalWord, correctManualWord, correctCapitalizedWord; 
    private String originalWord;
    private String textDocument; // Massive peice of string to display on interface
    private List<String> correctionList = new ArrayList<String>();
    private List<String> errorSummaryBlock = new ArrayList<String>();
    //private Dictionary errorCorrectionMetrics = new Hashtable();
    
    private JFrame frame;
    private JLayeredPane layeredPane;

    // Initialize menu buttons
    private JPanel topButtonPanel;
    private JPanel textPanel;
    private JPanel errorPanel;
    private JButton openFileButton;
    private JButton saveFileButton;
    private JButton resetButton;
    private JButton spellCheck;
    private JButton viewUserDictionary;
    private JButton removeUserDictionaryWord;
    private JButton helpButton;
    private JButton exitButton;

    // Initialize error buttons
    private JButton incorrectWord; //hold the current incorrect word
    private JButton substitution;
    private JButton omission;
    private JButton insertion;
    private JButton insertionSpace;
    private JButton reversal;
    private JButton manual;

    // Initialize other buttons
    private JButton capitalize;
    private JButton addToDictionary;
    private JButton ignoreError;

    // text area + scroll bar
    private JTextPane textPane;
    private JTextPane userDictionaryTextPane;
    private JScrollPane scrollPane;

    // updated text pane
    private JFrame userFrame;

    public GUI(){
        loadDictionary();

        // menu buttons
        this.textDocument = new String();
        this.layeredPane = new JLayeredPane();
        this.frame = new JFrame();
        this.topButtonPanel = new JPanel();
        this.textPanel = new JPanel();
        this.errorPanel = new JPanel();
        this.openFileButton = new JButton("Open File");
        this.resetButton = new JButton("Reset");
        this.saveFileButton = new JButton("Save File");
        this.viewUserDictionary = new JButton("View User Dictionary");
        this.removeUserDictionaryWord = new JButton("Remove Word");
        this.spellCheck = new JButton("SpellCheck");
        this.helpButton = new JButton("Help");
        this.exitButton = new JButton("Exit");
        this.textPane = new JTextPane();
        this.userDictionaryTextPane = new JTextPane();
        this.scrollPane = new JScrollPane(textPane);
        this.userFrame = new JFrame();

        textPane.setDragEnabled(true);
        textPane.setDropTarget(new DropTarget(textPane, new TextFileDropTargetListener(textPane)));

        layeredPane.setBounds(0, 0, 1380, 1080);
        frame.add(layeredPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1380, 1080);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Setting borders for each panel
        topButtonPanel.setBackground(Color.gray);
        topButtonPanel.setBounds(20, 20, 1325,50);

        textPanel.setBackground(Color.gray);
        textPanel.setBounds(20, 90, 820, 930);

        errorPanel.setBackground(Color.gray);
        errorPanel.setBounds(860, 90, 485, 930);

        // display menu Buttons
        openFileButton.addActionListener(this);
        openFileButton.setBounds(30, 30, 100, 30);

        saveFileButton.addActionListener(this);
        saveFileButton.setBounds(140, 30, 100, 30);

        viewUserDictionary.addActionListener(this);
        viewUserDictionary.setBounds(250, 30, 170, 30);

        spellCheck.addActionListener(this);
        spellCheck.setBounds(730, 30, 100, 30);

        helpButton.addActionListener(this);
        helpButton.setBounds(1125, 30, 100, 30);
;
        exitButton.addActionListener(this);
        exitButton.setBounds(1235, 30, 100, 30);

        // Display Text Document on textPanel
        textPane.setEditable(false);
        //textPane.setText(this.textDocument);
        scrollPane.setBounds(30, 100, 800, 910);
        
        // Adding panels and buttons to frame
        // Integer.valueOf(x) indicates the layer where the layered pane is, higher number means higher priority
        layeredPane.add(scrollPane, Integer.valueOf(1)); 
        layeredPane.add(openFileButton, Integer.valueOf(1));
        layeredPane.add(saveFileButton, Integer.valueOf(1));
        layeredPane.add(viewUserDictionary, Integer.valueOf(1));
        layeredPane.add(spellCheck, Integer.valueOf(1));
        layeredPane.add(helpButton, Integer.valueOf(1));
        layeredPane.add(exitButton, Integer.valueOf(1));
        
        layeredPane.add(topButtonPanel, Integer.valueOf(0));
        layeredPane.add(textPanel, Integer.valueOf(0));
        layeredPane.add(errorPanel, Integer.valueOf(0));

        frame.setTitle("GUI");    
        frame.setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent e) {  
        if (e.getSource() == openFileButton){
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame); 
            File file = fileChooser.getSelectedFile();
            StringBuilder fileContent = new StringBuilder();

            try {
                // Read the file
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent = fileContent.append(line).append("\n").append(" ");   // You can change this to display in a GUI component
                }
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
           //this.textDocument = textPane.getText();
            this.textDocument = fileContent.toString();
           //this.textDocument = Jsoup.parse(this.textDocument).text();
            textPane.setText(this.textDocument);
            scrollPane.setBounds(30, 100, 800, 910);
            setTextDoc(this.textDocument);

            // incorrect word and related buttons
            this.originalWord = getIncorrectWord();
            this.incorrectWord = new JButton(originalWord); 

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Define a style
            Style style = textPane.addStyle("Red Style", null);
            StyleConstants.setForeground(style, Color.RED);

            // color in original error word
            String wordToColor = this.originalWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }

            // correct word buttons
            this.correctSubstitutionWord = getSubstitution(this.originalWord);
            this.correctOmissionWord = getOmission(this.originalWord);
            this.correctInsertionWord = getInsertion(this.originalWord);
            this.correctInsertionSpaceWord = getInsertionSpace(this.originalWord);
            this.correctReversalWord = getReversal(this.originalWord);
            this.correctCapitalizedWord = capitalization(this.originalWord);

            //add word to error buttons
            this.substitution = new JButton("Substitution: " + this.correctSubstitutionWord);
            this.omission = new JButton("Omission: " + this.correctOmissionWord);
            this.insertion = new JButton("Insertion: " + this.correctInsertionWord);
            this.insertionSpace = new JButton("InsertionSpace: " + this.correctInsertionSpaceWord);
            this.reversal = new JButton("Reversal: "+ this.correctReversalWord);
            this.manual = new JButton("Manual Correction");


            // add word to user dictionary button
            this.capitalize = new JButton("Capitalize: " + this.correctCapitalizedWord);
            this.addToDictionary = new JButton("Add to UserDictionary");
            this.ignoreError = new JButton("Ignore Error");

            // error buttons action listeners
            incorrectWord.addActionListener(this);
            incorrectWord.setBounds(870, 100, 465, 30);
            
            substitution.addActionListener(this);
            substitution.setBounds(870, 140, 232, 30);

            omission.addActionListener(this);
            omission.setBounds(1102, 140, 232, 30);
            
            insertion.addActionListener(this);
            insertion.setBounds(870, 180, 232, 30);
            
            insertionSpace.addActionListener(this);
            insertionSpace.setBounds(1102, 180, 232, 30);
            
            reversal.addActionListener(this);
            reversal.setBounds(870, 220, 232, 30);

            manual.addActionListener(this);
            manual.setBounds(1102, 220, 232, 30);

            // other buttons
            capitalize.addActionListener(this);
            capitalize.setBounds(870, 260, 465, 30);

            addToDictionary.addActionListener(this);
            addToDictionary.setBounds(870, 300, 465, 30);

            ignoreError.addActionListener(this);
            ignoreError.setBounds(870, 340, 465, 30);
            
            layeredPane.add(incorrectWord, Integer.valueOf(1));
            
            // error buttons added to layered pane
            layeredPane.add(substitution, Integer.valueOf(1));
            layeredPane.add(omission, Integer.valueOf(1));
            layeredPane.add(insertion, Integer.valueOf(1));
            layeredPane.add(insertionSpace, Integer.valueOf(1));
            layeredPane.add(reversal, Integer.valueOf(1));
            layeredPane.add(manual, Integer.valueOf(1));
            
            layeredPane.add(capitalize, Integer.valueOf(1));
            layeredPane.add(addToDictionary, Integer.valueOf(1));
            layeredPane.add(ignoreError, Integer.valueOf(1));

            openFileButton.setVisible(false);
            resetButton.addActionListener(this);
            resetButton.setBounds(30, 30, 100, 30);
            layeredPane.add(resetButton, Integer.valueOf(1));
        }

        if(e.getSource() == resetButton){
            frame.dispose();
            new GUI();
        }

        if(e.getSource() == saveFileButton){
            JFileChooser saveAs = new JFileChooser();
            int option = saveAs.showSaveDialog(frame);
                        
            File fileName = new File(saveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
            outFile = new BufferedWriter(new FileWriter(fileName));
            this.textPane.write(outFile);   // *** here: ***
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
            finally {
                if (outFile != null) {
                   try {
                      outFile.close();
                   } 
                   catch (IOException ez) {
                    //left blank intentionally
                   }
                }
            }
        }

        if(e.getSource() == viewUserDictionary){
            userDictionaryTextPane.setEditable(false);
            userDictionaryTextPane.setText(userDictionarytoString());

            userFrame.setTitle("User Dictionary");
            userFrame.setBounds(0, 0, 500, 500);

            removeUserDictionaryWord.addActionListener(this);
            removeUserDictionaryWord.setBounds(330, 10, 150, 30);

            userFrame.add(removeUserDictionaryWord);
            userFrame.add(userDictionaryTextPane);
            
            userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userFrame.setVisible(true);
        } 

        if(e.getSource() == removeUserDictionaryWord){
            String wordToRemove = JOptionPane.showInputDialog("Enter Word to Remove");
            removeWordUser(wordToRemove);
            userDictionaryTextPane.setText(userDictionarytoString());
            removeWordDictionary(wordToRemove); //remove the word from the combined dictionary
            combineDictionary();
        }

        if(e.getSource() == spellCheck){
            this.textDocument = textPane.getText();
            scrollPane.setBounds(30, 100, 800, 910);
            setTextDoc(this.textDocument);

            // incorrect word and related buttons
            this.originalWord = getIncorrectWord();
            this.incorrectWord = new JButton(this.originalWord); 

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Define a style
            Style style = textPane.addStyle("Red Style", null);
            StyleConstants.setForeground(style, Color.RED);

            // color in original error word
            String wordToColor = this.originalWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
        
            // correct word buttons
            this.correctSubstitutionWord = getSubstitution(this.originalWord);
            this.correctOmissionWord = getOmission(this.originalWord);
            this.correctInsertionWord = getInsertion(this.originalWord);
            this.correctInsertionSpaceWord = getInsertionSpace(this.originalWord);
            this.correctReversalWord = getReversal(this.originalWord);
            this.correctCapitalizedWord = capitalization(this.originalWord);

            //add word to error buttons
            this.substitution = new JButton("Substitution: " + this.correctSubstitutionWord);
            this.omission = new JButton("Omission: " + this.correctOmissionWord);
            this.insertion = new JButton("Insertion: " + this.correctInsertionWord);
            this.insertionSpace = new JButton("InsertionSpace: " + this.correctInsertionSpaceWord);
            this.reversal = new JButton("Reversal: "+ this.correctReversalWord);
            this.manual = new JButton("Manual Correction");

            // add word to user dictionary button
            this.capitalize = new JButton("Capitalize: " + this.correctCapitalizedWord);
            this.addToDictionary = new JButton("Add to UserDictionary");
            this.ignoreError = new JButton("Ignore Error");

            // display Error Buttons
            incorrectWord.addActionListener(this);
            incorrectWord.setBounds(870, 100, 465, 30);
            
            // error buttons action listener
            substitution.addActionListener(this);
            substitution.setBounds(870, 140, 232, 30);

            omission.addActionListener(this);
            omission.setBounds(1102, 140, 232, 30);
            
            insertion.addActionListener(this);
            insertion.setBounds(870, 180, 232, 30);
            
            insertionSpace.addActionListener(this);
            insertionSpace.setBounds(1102, 180, 232, 30);
            
            reversal.addActionListener(this);
            reversal.setBounds(870, 220, 232, 30);

            manual.addActionListener(this);
            manual.setBounds(1102, 220, 232, 30);

            // other buttons
            capitalize.addActionListener(this);
            capitalize.setBounds(870, 260, 465, 30);

            addToDictionary.addActionListener(this);
            addToDictionary.setBounds(870, 300, 465, 30);

            ignoreError.addActionListener(this);
            ignoreError.setBounds(870, 340, 465, 30);
            
            layeredPane.add(incorrectWord, Integer.valueOf(1));

            // error buttons added to layered pane
            layeredPane.add(substitution, Integer.valueOf(1));
            layeredPane.add(omission, Integer.valueOf(1));
            layeredPane.add(insertion, Integer.valueOf(1));
            layeredPane.add(insertionSpace, Integer.valueOf(1));
            layeredPane.add(reversal, Integer.valueOf(1));
            layeredPane.add(manual, Integer.valueOf(1));

            layeredPane.add(capitalize, Integer.valueOf(1));
            layeredPane.add(addToDictionary, Integer.valueOf(1));
            layeredPane.add(ignoreError, Integer.valueOf(1));

            openFileButton.setVisible(false);
            resetButton.addActionListener(this);
            resetButton.setBounds(30, 30, 100, 30);
            layeredPane.add(resetButton, Integer.valueOf(1));
        }

        if(e.getSource() == exitButton){
            System.exit(0);
        } 

        if(e.getSource() == substitution){
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctSubstitutionWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctSubstitutionWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            findNextError();
        } 

        if(e.getSource() == omission){
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctOmissionWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctOmissionWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            findNextError();
        } 

        if(e.getSource() == insertion){
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctInsertionWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctInsertionWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            findNextError();
        } 

        if(e.getSource() == insertionSpace){
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctInsertionSpaceWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctInsertionSpaceWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            findNextError();
        } 

        if(e.getSource() == reversal){
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctReversalWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctReversalWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            findNextError();
        } 

        if(e.getSource() == capitalize){
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctCapitalizedWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctCapitalizedWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            findNextError();
        }
        
        if(e.getSource() == manual){
            this.correctManualWord = JOptionPane.showInputDialog("Enter word to replace with: ");
            
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctManualWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctManualWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            findNextError();
        }
        
        // if user presses addToDictionary button
        // for ignoreError button, add to user dictonary as well. Since if the user wants to ignore for one word, ignore for the rest as well
        if(e.getSource() == addToDictionary || e.getSource() == ignoreError){
            String str = this.originalWord;
            if(isPunctuationPresent(this.originalWord)){
                str = removePunctuations(this.originalWord);
            }
            addWordUser(str);
            combineDictionary();
            userDictionaryTextPane.setText(userDictionarytoString());
            findNextError();
        } 
        
    }

    private void findNextError() { 
        // updates the textDocument
        setTextDoc(this.textDocument);
        
        // gets the next incorrect word
        
        this.originalWord = getIncorrectWord();
        // if no more errors
        if (this.originalWord == null) {
            incorrectWord.setVisible(false);
            substitution.setVisible(false);
            omission.setVisible(false);
            insertion.setVisible(false);
            insertionSpace.setVisible(false);
            reversal.setVisible(false);
            manual.setVisible(false);
            capitalize.setVisible(false);
            addToDictionary.setVisible(false);
            ignoreError.setVisible(false);
        } 
        else {

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
                
            // Define a style
            Style style = textPane.addStyle("Red Style", null);
            StyleConstants.setForeground(style, Color.RED);

            // color in original error word
            String wordToColor = this.originalWord;

            // find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            //find corrected word
            // this.correctWord = findCorrections(this.originalWord);
            
            // update the buttons for the next error
            incorrectWord.setText(this.originalWord);
            this.correctSubstitutionWord = getSubstitution(this.originalWord);
            this.correctOmissionWord = getOmission(this.originalWord);
            this.correctInsertionWord = getInsertion(this.originalWord);
            this.correctInsertionSpaceWord = getInsertionSpace(this.originalWord);
            this.correctReversalWord = getReversal(this.originalWord);
            this.correctCapitalizedWord = capitalization(this.originalWord);

            substitution.setText("Subsitution: " + this.correctSubstitutionWord);
            omission.setText("Omission: "+ this.correctOmissionWord);
            insertion.setText("Insertion: " + this.correctInsertionWord);
            insertionSpace.setText("InsertionSpace: " + this.correctInsertionSpaceWord);
            reversal.setText("Reversal: " + this.correctReversalWord);
            manual.setText("Manual Correction");
            capitalize.setText("Capitalization: " + this.correctCapitalizedWord);
        }
    }
    
    
    /** 
    public String viewErrorSummary(){

    }

    public String viewCorrectionMetrics(){

    }
    */

    public static void main(String[] args) {
        new GUI();
 
    } 
}
