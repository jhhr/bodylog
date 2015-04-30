package bodylog.ui.help;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 * Window containing instructions on how to use this program. To be implemented.
 */
public class HelpWindow extends JScrollPane {

    public HelpWindow() {
        JEditorPane textPane = new JEditorPane();
        System.out.println(getClass().getClassLoader().getResource("").getPath());
        URL helpHTML = getClass().getResource("help.html");
        try {
            textPane.setPage(helpHTML);
        } catch (IOException ex) {
            Logger.getLogger(HelpWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        textPane.setEditable(false);
        setViewportView(textPane);
    }

}
