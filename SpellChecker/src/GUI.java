import java.util.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUI implements ActionListener {
    private String correctWord; 
    private String originalWord;
    private String textDocument; // Massive peice of string to display on interface
    private List<String> correctionList = new ArrayList<String>();
    private List<String> errorSummaryBlock = new ArrayList<String>();
    private Dictionary errorCorrectionMetrics = new Hashtable();
    
    private JLabel label;
    private JFrame frame;

    private JPanel topButtonPanel;
    private JPanel textPanel;
    private JPanel errorPanel;
    private JButton openFileButton;
    private JButton exitButton;

    public GUI(){
        frame = new JFrame();
        /** 
        JButton button = new JButton("Click me");
        button.addActionListener(this);

        label = new JLabel("Number of clicks: 0");
        */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1380, 1080);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        

        // Create Panels
        topButtonPanel = new JPanel();
        textPanel = new JPanel();
        errorPanel = new JPanel();

        
        // Set border for each panels
        topButtonPanel.setBackground(Color.gray);
        topButtonPanel.setBounds(20, 20, 1325,50);

        textPanel.setBackground(Color.gray);
        textPanel.setBounds(20, 90, 820, 930);

        errorPanel.setBackground(Color.gray);
        errorPanel.setBounds(860, 90, 485, 930);

        // Create Buttons
        openFileButton = new JButton("Open File");
        openFileButton.addActionListener(this);
        openFileButton.setBounds(30, 30, 100, 30);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setBounds(1235, 30, 100, 30);
        
        frame.getContentPane().add(openFileButton);
        frame.getContentPane().add(exitButton);
        frame.add(topButtonPanel);
        frame.add(textPanel);
        frame.add(errorPanel);
        
        frame.setTitle("GUI");    
        frame.setVisible(true);
    }

    
    public void actionPerformed(ActionEvent e) {  
        if (e.getSource() == openFileButton){
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame); 
            File file = fileChooser.getSelectedFile();
            try {
                // Read the file
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    with open( reader)
                    textDocument.append(line)   // You can change this to display in a GUI component
                }
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == exitButton){
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
        new GUI();
    }


    
}
