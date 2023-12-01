/**
 * This class converts dropable .txt file into text to display onto textpane in GUI
 * @author Lance Cheong Youne, Jwalant Patel
 */

import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextPane;

class TextFileDropTargetListener extends DropTargetAdapter {
    //initialize private variable
    private JTextPane textPane;

    /**
     * class constructor method
     * sets initial value for necessary variables
     */
    public TextFileDropTargetListener(JTextPane textPane) {
        this.textPane = textPane;
    }

    /**
    * drop method used to accept dropable files copies text onto a pane
    * @param event is the event called by another class
    * @return void
    */
    @Override
    public void drop(DropTargetDropEvent event) {
        try {
            // Accept the drop first
            event.acceptDrop(DnDConstants.ACTION_COPY);

            // Get the transfer which can provide the dropped item data
            Transferable transferable = event.getTransferable();

            // We accept only file list data flavor
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<?> droppedFiles = (List<?>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

                // Process the first file in the dropped files
                if (!droppedFiles.isEmpty() && droppedFiles.get(0) instanceof File) {
                    File file = (File) droppedFiles.get(0);
                    if (file.isFile() && (file.getName().endsWith(".txt") || file.getName().endsWith(".html"))) {
                        readFile(file);
                    }
                }
            }

            event.dropComplete(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            event.dropComplete(false);
        }
    }

    /**
    * readFile helper method used to read and write from the file dropped
    * @param file is the file dropped into the pane
    * @return void
    */
    private void readFile(File file) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try {
            // Read the file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            // While new line is not null add string to fileContent
            while ((line = reader.readLine()) != null) {
                fileContent = fileContent.append(line).append("\n").append(" ");   // You can change this to display in a GUI component
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Set text pane using string from fileContent
        String textDocument = fileContent.toString();
        textPane.setText(textDocument);
    }
}
