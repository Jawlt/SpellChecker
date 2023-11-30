import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReadExample {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        StringBuilder fileContent = new StringBuilder();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Process each line as needed
                    fileContent = fileContent.append(line).append("\n");   // You can change this to display in a GUI component
                    
                }
                System.out.println(fileContent);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}