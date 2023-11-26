import java.util.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.text.Style;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

// GUI(child class) inherits methods from Dictionary(super class)
public class GUI extends Dictionary implements ActionListener {
    private String correctWord; 
    private String originalWord;
    private String textDocument; // Massive peice of string to display on interface
    private List<String> correctionList = new ArrayList<String>();
    private List<String> errorSummaryBlock = new ArrayList<String>();
    //private Dictionary errorCorrectionMetrics = new Hashtable();
    
    private JLabel label;
    private JFrame frame;
    private JLayeredPane layeredPane;

    // Initialize menu buttons
    private JPanel topButtonPanel;
    private JPanel textPanel;
    private JPanel errorPanel;
    private JButton openFileButton;
    private JButton saveFileButton;
    private JButton viewUserDictionary;
    private JButton helpButton;
    private JButton exitButton;

    // Initialize error buttons
    private JButton incorrectWord; //hold the current incorrect word
    private JButton autoCorrectButton;
    private JButton addToDictionary;
    private JButton ignoreError;

    // text area + scroll bar
    private JTextPane textPane;
    private JScrollPane scrollPane;

    //
    private JFrame userFrame;
    private JTextPane userTextPane;

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
        this.saveFileButton = new JButton("Save File");
        this.viewUserDictionary = new JButton("View User Dictionary");
        this.helpButton = new JButton("Help");
        this.exitButton = new JButton("Exit");
        this.textPane = new JTextPane();
        this.scrollPane = new JScrollPane(textPane);
        this.userFrame = new JFrame();
        this.userTextPane = new JTextPane();
    
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
                    fileContent.append(line).append("\n");   // You can change this to display in a GUI component
                }
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            this.textDocument = fileContent.toString();
            textPane.setText(this.textDocument);
            scrollPane.setBounds(30, 100, 800, 910);
            setTextDoc(textDocument);

            // incorrect word and related buttons
            originalWord = getIncorrectWord();
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
            correctWord = getCorrectWord(originalWord);
            this.autoCorrectButton = new JButton("AutoCorrect: " + correctWord);

            // add word to user dictionary button
            this.addToDictionary = new JButton("Add to UserDictionary");
            this.ignoreError = new JButton("Ignore Error");

            // display Error Buttons
            incorrectWord.addActionListener(this);
            incorrectWord.setBounds(870, 100, 155, 30);
            
            autoCorrectButton.addActionListener(this);
            autoCorrectButton.setBounds(870, 140, 155, 30);

            addToDictionary.addActionListener(this);
            addToDictionary.setBounds(870, 170, 155, 30);

            ignoreError.addActionListener(this);
            ignoreError.setBounds(870, 200, 155, 30);
            
            layeredPane.add(incorrectWord, Integer.valueOf(1));
            layeredPane.add(autoCorrectButton, Integer.valueOf(1));
            layeredPane.add(addToDictionary, Integer.valueOf(1));
            layeredPane.add(ignoreError, Integer.valueOf(1));
            
        }

        if(e.getSource() == saveFileButton){
            JFileChooser saveAs = new JFileChooser();
            int option = saveAs.showSaveDialog(frame);
                        
            File fileName = new File(saveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
            outFile = new BufferedWriter(new FileWriter(fileName));
            textPane.write(outFile);   // *** here: ***
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
            textPane.setEditable(false);
            textPane.setText(userDictionarytoString());

            userFrame.setTitle("User Dictionary");
            userFrame.setBounds(0, 0, 500, 500);
            userFrame.add(textPane);
            userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userFrame.setVisible(true);

        } 

        if(e.getSource() == exitButton){
            System.exit(0);
        } 

        if(e.getSource() == autoCorrectButton){
           // textPane.setText();
        } 

        // if user presses addToDictionary button
        // for ignoreError button, add to user dictonary as well. Since if the user wants to ignore for one word, ignore for the rest as well
        if(e.getSource() == addToDictionary || e.getSource() == ignoreError){
            addWordUser(originalWord);
        }    

    }

    /** 
    public String applyCorrection(String orignalWord, String correctWord){
        return correctWord;
    }


    public String viewErrorSummary(){

    }

    public String viewCorrectionMetrics(){

    }
    */
    public static void main(String[] args) {

        GUI testing = new GUI();
        testing.layeredPane.setBounds(0, 0, 1380, 1080);
        testing.frame.add(testing.layeredPane);

        testing.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testing.frame.setSize(1380, 1080);
        testing.frame.setLocationRelativeTo(null);
        testing.frame.setLayout(null);

        
        // Setting borders for each panel
        testing.topButtonPanel.setBackground(Color.gray);
        testing.topButtonPanel.setBounds(20, 20, 1325,50);

        testing.textPanel.setBackground(Color.gray);
        testing.textPanel.setBounds(20, 90, 820, 930);

        testing.errorPanel.setBackground(Color.gray);
        testing.errorPanel.setBounds(860, 90, 485, 930);

        // display menu Buttons
        testing.openFileButton.addActionListener(testing);
        testing.openFileButton.setBounds(30, 30, 100, 30);

        testing.saveFileButton.addActionListener(testing);
        testing.saveFileButton.setBounds(140, 30, 100, 30);

        testing.viewUserDictionary.addActionListener(testing);
        testing.viewUserDictionary.setBounds(250, 30, 170, 30);

        testing.helpButton.addActionListener(testing);
        testing.helpButton.setBounds(1125, 30, 100, 30);
;
        testing.exitButton.addActionListener(testing);
        testing.exitButton.setBounds(1235, 30, 100, 30);
        
        // Display Text Document on textPanel
        testing.textPane.setEditable(false);

        testing.textPane.setText(testing.textDocument);
        testing.scrollPane.setBounds(30, 100, 800, 910);
        
        // Adding panels and buttons to frame
        // Integer.valueOf(x) indicates the layer where the layered pane is, higher number means higher priority
        testing.layeredPane.add(testing.scrollPane, Integer.valueOf(1)); 
        testing.layeredPane.add(testing.openFileButton, Integer.valueOf(1));
        testing.layeredPane.add(testing.saveFileButton, Integer.valueOf(1));
        testing.layeredPane.add(testing.viewUserDictionary, Integer.valueOf(1));
        testing.layeredPane.add(testing.helpButton, Integer.valueOf(1));
        testing.layeredPane.add(testing.exitButton, Integer.valueOf(1));
        
        testing.layeredPane.add(testing.topButtonPanel, Integer.valueOf(0));
        testing.layeredPane.add(testing.textPanel, Integer.valueOf(0));
        testing.layeredPane.add(testing.errorPanel, Integer.valueOf(0));

        testing.frame.setTitle("GUI");    
        testing.frame.setVisible(true);

    } 
}
