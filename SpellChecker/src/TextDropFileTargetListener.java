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

import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

class TextFileDropTargetListener extends DropTargetAdapter {
    private JTextPane textPane;

    public TextFileDropTargetListener(JTextPane textPane) {
        this.textPane = textPane;
    }

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

    private void readFile(File file) throws IOException {
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

        String textDocument = fileContent.toString();
        //textDocument = Jsoup.parse(textDocument).text();
        textPane.setText(textDocument);
    }
}
