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

    // Initialize menu buttons
    private JPanel topButtonPanel;
    private JPanel textPanel;
    private JPanel errorPanel;
    private JButton openFileButton;
    private JButton saveFileButton;
    private JButton helpButton;
    private JButton exitButton;

    // Initialize error buttons
    private JButton incorrectWord; //hold the current incorrect word
    private JButton autoCorrectButton;
    private JButton addToDictionary;
    private JButton ignoreError;


    private JTextPane textPane;
    private JScrollPane scrollPane;

    public GUI(){
        loadDictionary();

        // menu buttons
        this.textDocument = new String();
        this.frame = new JFrame();
        this.topButtonPanel = new JPanel();
        this.textPanel = new JPanel();
        this.errorPanel = new JPanel();
        this.openFileButton = new JButton("Open File");
        this.saveFileButton = new JButton("Save File");
        this.helpButton = new JButton("Help");
        this.exitButton = new JButton("Exit");
        
        // error buttons
        originalWord = getIncorrectWord();
        this.incorrectWord = new JButton(originalWord);
        correctWord = getCorrectWord();
        this.autoCorrectButton = new JButton("AutoCorrect: " + correctWord);
        this.addToDictionary = new JButton("Add to Dictionary");
        this.ignoreError = new JButton("Ignore Error");
        this.textPane = new JTextPane();
        this.scrollPane = new JScrollPane(textPane);
        System.out.println(originalWord);
        // Get the StyledDocument of the JTextPane
        StyledDocument doc = textPane.getStyledDocument();
        
        // Define a style
        Style style = textPane.addStyle("Red Style", null);
        StyleConstants.setForeground(style, Color.RED);

        // color in original error word
        String wordToColor = originalWord;
        String text = textPane.getText();

        // find index of word to apply style
        int offset = text.indexOf(wordToColor);
        int length = wordToColor.length();
        if (offset != -1) {
            doc.setCharacterAttributes(offset, length, style, false);
        }
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

            // display Error Buttons
            incorrectWord.addActionListener(this);
            incorrectWord.setBounds(870, 100, 465, 30);
            
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

        if(e.getSource() == incorrectWord){
            System.exit(0);
        }   

        if(e.getSource() == autoCorrectButton){
            System.exit(0);
        } 

        if(e.getSource() == addToDictionary){
            System.exit(0);
        }   

        if(e.getSource() == ignoreError){
            System.exit(0);
        }  

    }

    /** 
    public void showPossibleCorrections(List<String> correctionList){
        return;
    }

    public String applyCorrection(String orignalWord, String correctWord){
        return correctWord;
    }

    public void ignoreError(String originalWord){
        return;
    }

    public void addToDictionary(String originalWord,List<String> userDictionary ){
        
    }

    public String viewErrorSummary(){

    }

    public String viewCorrectionMetrics(){

    }
    */
    public static void main(String[] args) {

        GUI testing = new GUI();
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

        testing.helpButton.addActionListener(testing);
        testing.helpButton.setBounds(1125, 30, 100, 30);
;
        testing.exitButton.addActionListener(testing);
        testing.exitButton.setBounds(1235, 30, 100, 30);

        
        
        // Display Text Document on textPanel
        testing.textPane.setEditable(true);

        testing.textPane.setText(testing.textDocument);
        testing.scrollPane.setBounds(30, 100, 800, 910);
        
        // Adding panels and buttons to frame
        testing.frame.getContentPane().add(testing.scrollPane); 
        testing.frame.getContentPane().add(testing.openFileButton);
        testing.frame.getContentPane().add(testing.saveFileButton);
        testing.frame.getContentPane().add(testing.helpButton);
        testing.frame.getContentPane().add(testing.exitButton);
        testing.frame.add(testing.topButtonPanel);
        testing.frame.add(testing.textPanel);
        testing.frame.add(testing.errorPanel);

        testing.frame.setTitle("GUI");    
        testing.frame.setVisible(true);

        
    }


    
}
