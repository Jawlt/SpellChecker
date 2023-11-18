import java.util.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener {
    private String correctWord; 
    private String originalWord;
    private String userDocument; //Massive peice of string to display on interface
    private List<String> correctionList = new ArrayList<String>();
    private List<String> errorSummaryBlock = new ArrayList<String>();
    private Dictionary errorCorrectionMetrics = new Hashtable();
    
    private int count = 0;
    private JLabel label;
    private JFrame frame;
    JPanel panel;
    
    public GUI(){
        frame = new JFrame();
        
        JButton button = new JButton("Click me");
        button.addActionListener(this);
        label = new JLabel("Number of clicks: 0");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Error GUI");    
        frame.pack();
        frame.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        count++;

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
