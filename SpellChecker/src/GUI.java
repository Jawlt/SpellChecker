import java.util.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener {
    private String correctWord; 
    private String originalWord;
    private String userDocument; // Massive peice of string to display on interface
    private List<String> correctionList = new ArrayList<String>();
    private List<String> errorSummaryBlock = new ArrayList<String>();
    private Dictionary errorCorrectionMetrics = new Hashtable();
    
    private int count = 0;
    private JLabel label;
    private JFrame frame;

    private JPanel backgroundPanel;
    private JPanel topButtonPanel;
    private JPanel textPanel;
    private JPanel errorPanel;
    

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
        topButtonPanel.setBackground(Color.green);
        topButtonPanel.setBounds(20, 20, 1325,50);
        
        textPanel.setBackground(Color.blue);
        textPanel.setBounds(20, 90, 820, 950);
        
        errorPanel.setBackground(Color.red);
        errorPanel.setBounds(860, 90, 485, 950);
        
        
        frame.add(topButtonPanel);
        frame.add(textPanel);
        frame.add(errorPanel);
 
        //panel.setLayout(new GridLayout(0, 1));
        //panel.add(button);
        //panel.add(label);

        frame.setTitle("GUI");    
        frame.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);
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
