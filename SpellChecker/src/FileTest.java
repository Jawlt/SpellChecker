import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileTest {
    public static void main(String[] args) {
        int wordCounter = 0;
        int characterCounter = 0;
        JFileChooser fileChooser = new JFileChooser();
        StringBuilder fileContent = new StringBuilder();
        
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Process each line as needed
                    fileContent = fileContent.append(line).append(" \n");   // You can change this to display in a GUI component
                    
                }
               // System.out.println(fileContent);
               // System.out.println("==========================");
                
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String textDocument = fileContent.toString();

        //loops through entire text document and counts words
        String[] textLines = textDocument.split("\\s+");
        String[] textWords;
        for(int l = 0; l < textLines.length; l++){
            textWords = new String[textLines[l].length()-1];
            textWords = textLines[l].split(" ");
            wordCounter += textWords.length;
            for (int i = 0; i < textWords.length; i++) {
                characterCounter += textWords[i].length();
          }
        }
        System.out.println();
        //Word Count: 73
        //Character Count: 401
        System.out.println("Word Count: " + wordCounter);
        System.out.println("Character Count: " + characterCounter);

    }
}