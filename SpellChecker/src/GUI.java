/**
 * This class runs the code and displays the GUI
 * @author Jawalant Patel, Lance Cheong Youne
 */

// Imports needed
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
import javax.swing.text.Style;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

// GUI(child class) inherits methods from Dictionary(super class) and implements ActionListener needed for buttons
public class GUI extends Dictionary implements ActionListener {
    // Initialize private variables
	private String correctSubstitutionWord, correctOmissionWord, correctInsertionWord, correctInsertionSpaceWord, correctReversalWord, correctManualWord, correctCapitalizedWord; 
    private String originalWord;
    private String textDocument;
    
    // Initialize private frame/panel variables
    private JFrame frame;
    private JLayeredPane layeredPane;
    private JPanel topButtonPanel, textPanel, errorPanel;

    // Initialize private button variables
    private JButton openFileButton, saveFileButton, resetButton, spellCheck, viewUserDictionary, removeUserDictionaryWord, exitButton;
    private JButton darkModeButton, lightModeButton;

    // Initialize error buttons
    private JButton incorrectWord, substitution, omission, insertion, insertionSpace, reversal, manual;
    private JButton capitalize, addToDictionary, ignoreError;

    // Initialize text areas and scroll bar
    private JTextPane textPane, metricsPane, helpPane;
    private JScrollPane scrollPane;

    // Initialize a separate userDictionary text frame
    private JFrame userFrame;
    private JTextPane userDictionaryTextPane;

    // Intialize counter variables for metrics
    private int substitutionCounter, omissionCounter, insertionCounter, insertionSpaceCounter, reversalCounter, manualCounter, capitalizeCounter, wordCounter, characterCounter;

    /**
     * class constructor method:
     * sets initial value for necessary variables and adds initial buttons 
     */
    public GUI(){
        // Loads words into the Dictionary hashtable
        loadDictionary();

        // Intialize variables and menu buttons
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
        this.darkModeButton = new JButton("🌙 Dark Mode");
        this.lightModeButton = new JButton("🌝 Light Mode");
        this.exitButton = new JButton("Exit");
        this.textPane = new JTextPane();
        this.metricsPane = new JTextPane();
        this.helpPane = new JTextPane();
        this.userDictionaryTextPane = new JTextPane();
        this.scrollPane = new JScrollPane(textPane);
        this.userFrame = new JFrame();
        this.substitutionCounter = 0;
        this.omissionCounter = 0;
        this.insertionCounter = 0;
        this.insertionSpaceCounter = 0;
        this.reversalCounter = 0;
        this.manualCounter = 0;
        this.capitalizeCounter = 0;
        this.wordCounter = 0;
        this.characterCounter = 0;

        textPane.setDragEnabled(true);
        
        // Allow user to drop files onto textPane
        textPane.setDropTarget(new DropTarget(textPane, new TextFileDropTargetListener(textPane)));

        // Set location for layered pane
        layeredPane.setBounds(0, 0, 1380, 1080);

        // Setup frame
        frame.add(layeredPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1380, 1080);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Setting colour and borders for each panel
        topButtonPanel.setBackground(Color.gray);
        topButtonPanel.setBounds(20, 20, 1325,50);

        textPanel.setBackground(Color.gray);
        textPanel.setBounds(20, 90, 820, 930);

        errorPanel.setBackground(Color.gray);
        errorPanel.setBounds(860, 90, 485, 930);

        // Add listening feature to button
        openFileButton.addActionListener(this);
        openFileButton.setBounds(30, 30, 100, 30);

        saveFileButton.addActionListener(this);
        saveFileButton.setBounds(140, 30, 100, 30);

        viewUserDictionary.addActionListener(this);
        viewUserDictionary.setBounds(250, 30, 170, 30);

        spellCheck.addActionListener(this);
        spellCheck.setBounds(730, 30, 100, 30);

        darkModeButton.addActionListener(this);
        darkModeButton.setBounds(1105, 30, 120, 30);
        
        lightModeButton.addActionListener(this);
        lightModeButton.setBounds(1105, 30, 120, 30);
        lightModeButton.setVisible(false); //hide the light mode button

        exitButton.addActionListener(this);
        exitButton.setBounds(1235, 30, 100, 30);
        
        // Display scroll pane (Text Document) on the textPanel
        textPane.setEditable(false);
        scrollPane.setBounds(30, 100, 800, 910);
        scrollPane.setBackground(Color.WHITE);
        
        // Set and display the help pane
        helpPane.setEditable(false);
        helpPane.setBounds(870, 440, 465, 300);
        helpPane.setBackground(Color.WHITE);
        displayHelpPane();
        
        // Set and display the metrics pane
        metricsPane.setEditable(false);
        metricsPane.setBounds(870, 815, 465, 195);
        metricsPane.setBackground(Color.WHITE);
        metricsPane.setVisible(false); // Set hidden at the start
        
        // Adding panels and buttons to layered frame
        // Integer.valueOf(x) indicates the layer where the layered pane is, higher number means higher priority
        layeredPane.add(scrollPane, Integer.valueOf(1)); 
        layeredPane.add(openFileButton, Integer.valueOf(1));
        layeredPane.add(saveFileButton, Integer.valueOf(1));
        layeredPane.add(viewUserDictionary, Integer.valueOf(1));
        layeredPane.add(spellCheck, Integer.valueOf(1));
        layeredPane.add(darkModeButton, Integer.valueOf(1));
        layeredPane.add(lightModeButton, Integer.valueOf(1));
        layeredPane.add(exitButton, Integer.valueOf(1));
        layeredPane.add(topButtonPanel, Integer.valueOf(0));
        layeredPane.add(textPanel, Integer.valueOf(0));
        layeredPane.add(errorPanel, Integer.valueOf(0));
        layeredPane.add(helpPane, Integer.valueOf(1));
        layeredPane.add(metricsPane, Integer.valueOf(1));

        // Set window name
        frame.setTitle("GUI");    
        frame.setVisible(true);
        
    }
    
    /**
     * actionPerformed method check if any action event is detected
     * @param e is the action event type
     * @return void
     */
    public void actionPerformed(ActionEvent e) {  
        // If user presses the Open File button
        if (e.getSource() == openFileButton){
            // Display metricsPane
            metricsPane.setVisible(true); // displays metrics pane
            openFileButton.setVisible(false); // hide Open File button
            spellCheck.setVisible(false); // hide Spell Check button

            // Initialize File Reader
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame); 
            File file = fileChooser.getSelectedFile();
            StringBuilder fileContent = new StringBuilder();
            try {
                // Read and write the file to fileContent
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent = fileContent.append(line).append("\n").append(" ");   // You can change this to display in a GUI component
                }
                reader.close(); //close file reader
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            // Sets and displays text from file into text pane
            this.textDocument = fileContent.toString();
            textPane.setText(this.textDocument);
            
            // Updates text pane
            setTextDoc(this.textDocument);
            // Updates metrics panel
            updateMetrics();
            
            // Incorrect word and related buttons
            this.originalWord = getIncorrectWord();
            this.incorrectWord = new JButton(originalWord); 

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Define a style and color in original error word
            Style style = textPane.addStyle("Red Style", null);
            StyleConstants.setForeground(style, Color.RED); 
            String wordToColor = this.originalWord;

            // Find index of word to apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }

            // Find the corrected word
            this.correctSubstitutionWord = getSubstitution(this.originalWord);
            this.correctOmissionWord = getOmission(this.originalWord);
            this.correctInsertionWord = getInsertion(this.originalWord);
            this.correctInsertionSpaceWord = getInsertionSpace(this.originalWord);
            this.correctReversalWord = getReversal(this.originalWord);
            this.correctCapitalizedWord = capitalization(this.originalWord);

            // Display button with corrected word
            this.substitution = new JButton("Substitution: " + this.correctSubstitutionWord);
            this.omission = new JButton("Omission: " + this.correctOmissionWord);
            this.insertion = new JButton("Insertion: " + this.correctInsertionWord);
            this.insertionSpace = new JButton("InsertionSpace: " + this.correctInsertionSpaceWord);
            this.reversal = new JButton("Reversal: "+ this.correctReversalWord);
            this.capitalize = new JButton("Capitalize: " + this.correctCapitalizedWord);

            // Display other buttons
            this.manual = new JButton("Manual Correction");
            this.addToDictionary = new JButton("Add to UserDictionary");
            this.ignoreError = new JButton("Ignore Error");

            // Add listening feature to button
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

            // Add listening feature to other buttons 
            capitalize.addActionListener(this);
            capitalize.setBounds(870, 260, 465, 30);

            addToDictionary.addActionListener(this);
            addToDictionary.setBounds(870, 300, 465, 30);

            ignoreError.addActionListener(this);
            ignoreError.setBounds(870, 340, 465, 30);
            
            resetButton.addActionListener(this);
            resetButton.setBounds(30, 30, 100, 30);

            // Adding error and correction buttons to layered frame
            // Integer.valueOf(x) indicates the layer where the layered pane is, higher number means higher   
            layeredPane.add(incorrectWord, Integer.valueOf(1));
            layeredPane.add(substitution, Integer.valueOf(1));
            layeredPane.add(omission, Integer.valueOf(1));
            layeredPane.add(insertion, Integer.valueOf(1));
            layeredPane.add(insertionSpace, Integer.valueOf(1));
            layeredPane.add(reversal, Integer.valueOf(1));
            layeredPane.add(manual, Integer.valueOf(1));
            layeredPane.add(capitalize, Integer.valueOf(1));
            layeredPane.add(addToDictionary, Integer.valueOf(1));
            layeredPane.add(ignoreError, Integer.valueOf(1));            
            layeredPane.add(resetButton, Integer.valueOf(1));
        }

        // If user presses the Reset button
        if(e.getSource() == resetButton){
            // Resets everything
            frame.dispose();
            new GUI();
        }

        // If user presses the Save File button
        if(e.getSource() == saveFileButton){
            // Calls helper method 
            save();
        }
        
        // If user presses the View User Dictionary button
        if(e.getSource() == viewUserDictionary){
            // Displays User Dictionary
            userDictionaryTextPane.setEditable(false);
            userDictionaryTextPane.setText(userDictionarytoString());

            userFrame.setTitle("User Dictionary");
            userFrame.setBounds(0, 0, 500, 500);

            // Adds listening feature to remove button
            removeUserDictionaryWord.addActionListener(this);
            removeUserDictionaryWord.setBounds(330, 10, 150, 30);
            
            // Adds button and text pane to frame
            userFrame.add(removeUserDictionaryWord);
            userFrame.add(userDictionaryTextPane);
            userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userFrame.setVisible(true);
        } 

        // If user presses the Remove button in the User Dictionary
        if(e.getSource() == removeUserDictionaryWord){
            //Prompts user to enter a word of their choice and removed the word from user dictionary
            String wordToRemove = JOptionPane.showInputDialog("Enter Word to Remove");
            removeWordUser(wordToRemove);

            // Updates the text pane and the dictionary
            userDictionaryTextPane.setText(userDictionarytoString());
            removeWordDictionary(wordToRemove); //remove the word from the combined dictionary
            combineDictionary(); //recombines user dictionary and dictionary
        }
        
        // If user presses the Spell Check button
        if(e.getSource() == spellCheck){
            // Adjusted the visibility of certain panes and buttons
            openFileButton.setVisible(false);
            metricsPane.setVisible(true);
            spellCheck.setVisible(false);

            // Updates the Text Pane
            this.textDocument = textPane.getText();
            scrollPane.setBounds(30, 100, 800, 910);
            setTextDoc(this.textDocument);
            updateMetrics(); // displays metrics pane
            
            // Get the incorrect word and displays related buttons
            this.originalWord = getIncorrectWord();
            this.incorrectWord = new JButton(this.originalWord); 

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Define a style and color in original error word
            Style style = textPane.addStyle("Red Style", null);
            StyleConstants.setForeground(style, Color.RED);
            String wordToColor = this.originalWord;

            // Find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
        
            // Find the corrected word
            this.correctSubstitutionWord = getSubstitution(this.originalWord);
            this.correctOmissionWord = getOmission(this.originalWord);
            this.correctInsertionWord = getInsertion(this.originalWord);
            this.correctInsertionSpaceWord = getInsertionSpace(this.originalWord);
            this.correctReversalWord = getReversal(this.originalWord);
            this.correctCapitalizedWord = capitalization(this.originalWord);

            // Displayed error buttons with corrected words
            this.substitution = new JButton("Substitution: " + this.correctSubstitutionWord);
            this.omission = new JButton("Omission: " + this.correctOmissionWord);
            this.insertion = new JButton("Insertion: " + this.correctInsertionWord);
            this.insertionSpace = new JButton("InsertionSpace: " + this.correctInsertionSpaceWord);
            this.reversal = new JButton("Reversal: "+ this.correctReversalWord);
            this.capitalize = new JButton("Capitalize: " + this.correctCapitalizedWord);

            // Displayed other option buttons
            this.manual = new JButton("Manual Correction");
            this.addToDictionary = new JButton("Add to UserDictionary");
            this.ignoreError = new JButton("Ignore Error");

            // Set listening features and the bounds
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

            capitalize.addActionListener(this);
            capitalize.setBounds(870, 260, 465, 30);

            addToDictionary.addActionListener(this);
            addToDictionary.setBounds(870, 300, 465, 30);

            ignoreError.addActionListener(this);
            ignoreError.setBounds(870, 340, 465, 30);
            
            resetButton.addActionListener(this);
            resetButton.setBounds(30, 30, 100, 30);
            
            // Display incorrect word and error buttons on the layered pane
            layeredPane.add(incorrectWord, Integer.valueOf(1));
            layeredPane.add(substitution, Integer.valueOf(1));
            layeredPane.add(omission, Integer.valueOf(1));
            layeredPane.add(insertion, Integer.valueOf(1));
            layeredPane.add(insertionSpace, Integer.valueOf(1));
            layeredPane.add(reversal, Integer.valueOf(1));
            layeredPane.add(manual, Integer.valueOf(1));
            layeredPane.add(capitalize, Integer.valueOf(1));
            layeredPane.add(addToDictionary, Integer.valueOf(1));
            layeredPane.add(ignoreError, Integer.valueOf(1));
            layeredPane.add(resetButton, Integer.valueOf(1));
        }

        // If user presses the Dark Mode button
        if(e.getSource() == darkModeButton){
            darkModeButton.setVisible(false);
            lightModeButton.setVisible(true);

            // Update colouring scheme
            frame.getContentPane().setBackground(Color.BLACK);
            topButtonPanel.setBackground(Color.DARK_GRAY);
            textPanel.setBackground(Color.DARK_GRAY);
            errorPanel.setBackground(Color.DARK_GRAY);
            topButtonPanel.setBackground(Color.DARK_GRAY);
            textPane.setBackground(Color.BLACK);
            textPane.setForeground(Color.WHITE);
            metricsPane.setBackground(Color.BLACK);
            metricsPane.setForeground(Color.WHITE);
            helpPane.setBackground(Color.BLACK);
            helpPane.setForeground(Color.WHITE);
        } 
        
        // If user presses the Light Mode button
        if(e.getSource() == lightModeButton){
            darkModeButton.setVisible(true);
            lightModeButton.setVisible(false);

            // Update colouring scheme
            frame.getContentPane().setBackground(Color.WHITE);
            topButtonPanel.setBackground(Color.GRAY);
            textPanel.setBackground(Color.GRAY);
            errorPanel.setBackground(Color.GRAY);
            topButtonPanel.setBackground(Color.GRAY);
            textPane.setBackground(Color.WHITE);
            textPane.setForeground(Color.BLACK);
            metricsPane.setBackground(Color.WHITE);
            metricsPane.setForeground(Color.BLACK);
            helpPane.setBackground(Color.WHITE);
            helpPane.setForeground(Color.BLACK);
        }

        // If user presses the Exit button
        if(e.getSource() == exitButton){
            save(); // prompt user to save
            System.exit(0);
        } 
        
        // If user presses the Substitution button
        if(e.getSource() == substitution){
            // Update text document with the corrected word
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctSubstitutionWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Define a style and color in original error word
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);
            String wordToColor = this.correctSubstitutionWord;

            // find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            this.substitutionCounter++; // increment counter
            findNextError(); // find the next error
        } 
        
        // If user presses the Omission button
        if(e.getSource() == omission){
            // Update text document with the corrected word
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctOmissionWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);

            // color in original error word
            String wordToColor = this.correctOmissionWord;

            // find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            this.omissionCounter++; // increment counter
            findNextError(); // find the next error
        } 
        
        // If user presses the Open File button
        if(e.getSource() == insertion){
            // Update text document with the corrected word
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctInsertionWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();

            // Define a style and color in original error word
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);
            String wordToColor = this.correctInsertionWord;

            // find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            this.insertionCounter++;  // increment counter
            findNextError(); // find the next error
        } 

        // If user presses the Insert Space button
        if(e.getSource() == insertionSpace){
            // Update text document with the corrected word
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctInsertionSpaceWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();

            // Define a style and color in original error word
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);
            String wordToColor = this.correctInsertionSpaceWord;

            // find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }

            this.insertionSpaceCounter++; // increment counter
            findNextError(); // find the next error
        } 

        // If user presses the Reversal button
        if(e.getSource() == reversal){
            // Update text document with the corrected word
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctReversalWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Define a style and color in original error word
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);
            String wordToColor = this.correctReversalWord;

            // find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            this.reversalCounter++; // increment counter
            findNextError(); // find the next error
        } 

        // If user presses the Capitalize button
        if(e.getSource() == capitalize){
            // Update text document with the corrected word
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctCapitalizedWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            // Define a style and color in original error word
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);
            String wordToColor = this.correctCapitalizedWord;

            // find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            this.capitalizeCounter++; // increment counter
            findNextError(); // find the next error
        }
        
        // If user presses the Manual button
        if(e.getSource() == manual){
            this.correctManualWord = JOptionPane.showInputDialog("Enter word to replace with: ");
            
            // Update text document with the corrected word
            this.textDocument = this.textDocument.replaceFirst(this.originalWord, this.correctManualWord);
            textPane.setText(this.textDocument);

            // Get the StyledDocument of the JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Define a style and color in original error word
            Style style = textPane.addStyle("Green Style", null);
            StyleConstants.setForeground(style, Color.GREEN);
            String wordToColor = this.correctManualWord;

            // find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }
            this.manualCounter++; // increment counter
            findNextError(); // find the next error
        }
        
        // If user presses addToDictionary button
        // For Ignore Error button, add to User Dictionary as well. Since if the user wants to ignore for one word, ignore for the rest as well
        if(e.getSource() == addToDictionary || e.getSource() == ignoreError){
            String str = this.originalWord;
            if(isPunctuationPresent(this.originalWord)){
                //If punctuation is present, remove punctuation
                str = removePunctuations(this.originalWord);
            }
            addWordUser(str); // add the str to User Dictionary
            combineDictionary();  // update the dictionary
            userDictionaryTextPane.setText(userDictionarytoString()); // updates text pane with string from user dictionry
            findNextError(); // find the next error
        } 
    }

    /**
     * findNextError helper method used to load the next error in the text document
     * @param none
     * @return void 
     */
    private void findNextError() { 
        // updates the textDocument
        setTextDoc(this.textDocument);
        
        //updates metrics panel
        updateMetrics();
        
        // Gets the next incorrect word
        this.originalWord = getIncorrectWord();

        // If no more errors can be found, hide all buttons
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
                
            // Define a style and color in original error word
            Style style = textPane.addStyle("Red Style", null);
            StyleConstants.setForeground(style, Color.RED);
            String wordToColor = this.originalWord;

            // Find index of word and apply style
            int offset = this.textDocument.indexOf(wordToColor);
            int length = wordToColor.length();
            if (offset != -1) {
                doc.setCharacterAttributes(offset, length, style, false);
            }

            // Find corrected word
            // Update the buttons for the next error
            this.correctSubstitutionWord = getSubstitution(this.originalWord);
            this.correctOmissionWord = getOmission(this.originalWord);
            this.correctInsertionWord = getInsertion(this.originalWord);
            this.correctInsertionSpaceWord = getInsertionSpace(this.originalWord);
            this.correctReversalWord = getReversal(this.originalWord);
            this.correctCapitalizedWord = capitalization(this.originalWord);

            // Update the corrected word and error correction buttons
            incorrectWord.setText(this.originalWord);
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
     * updateMetrics helper method used to display and update correction metrics
     * @param none
     * @return void
     */
    public void updateMetrics(){
        // Loops through entire text document and counts words
        // Update Word Count and Char Count
        String[] textLines = textDocument.split("\\s+");
        String[] textWords;
        wordCounter = 0;
        characterCounter = 0;
        for(int l = 0; l < textLines.length; l++){
            textWords = new String[textLines[l].length()-1];
            textWords = textLines[l].split(" ");
            wordCounter += textWords.length;
            for (int i = 0; i < textWords.length; i++) {
                characterCounter += textWords[i].length();
          }
        }

        String line = "-------------------------------------------------------------------------------------------------------------------";
        String metrics = "\t\t            Correction Metrics\n" + line + "\nSwapped Letters (Substitution): " + substitutionCounter + "\nExtra Letters Removed (Omission): " + omissionCounter 
        + "\nAdded Letters (Insertion): " + insertionCounter + "\nSpaced Letters (InsertionSpace): " + insertionSpaceCounter + "\nReversed Letters (Reversal): " + reversalCounter
        + "\nCapitalized Words (Capitalize): " + capitalizeCounter + "\nManually Corrected Words (Capitalize): " + manualCounter
        + "\n\nWord Count: " + wordCounter + "\t\tCharacter Count: " + characterCounter;
        metricsPane.setText(metrics);
    }
    
    /**
     * displayHelpPane helper method used to display the Help/Steps pane
     * @param none
     * @return void
     */
    public void displayHelpPane(){
        String dotLine = "-------------------------------------------------------------------------------------------------------------------\n";
        String help = "\t\t                   Help/Steps\n" + dotLine
        + "1. Open file or drag File onto white space on the left.\n\n"
        + "2. Run spellCheck button if file is draged in.\n\n"
        + "3. Choose corrections from the 8 buttons on the right.\n\n"
        + "4. If a word was added to userDictionary or Ignored, you can remove it by clicking view userDictionary button.\n\n"
        + "5. If no correction is appealing choose manual correcion.\n\n"
        + "6. Repeat Steps until last correction (buttons wont apear).\n\n"
        + "7. Save file using \"filename\" (do not write \".txt\").\n\n"
        + "8. Exit applicatioons or reset to test another file.\n\n";
        helpPane.setText(help);
    }

    /**
     * save helper method used to save current text document to .txt
     * @param none
     * @return void
     */
    public void save(){
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
                    ez.printStackTrace();
                }
            }
        }
    }

    /**
     * main method used to call the GUI constructor
     * @param none
     * @return void
     */
    public static void main(String[] args) {
        new GUI();
    } 
}