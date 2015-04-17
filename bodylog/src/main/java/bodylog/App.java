package bodylog;

import javax.swing.SwingUtilities;

/**
 * Main class.
 */
public class App {

    /**
     * Main method. Runs the program within the AWT event dispatching thread.
     *
     * @param args arguments
     * @see javax.swing.SwingUtilities#invokeLater
     * @see bodylog.BodyLog
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new BodyLog());
    }
}
