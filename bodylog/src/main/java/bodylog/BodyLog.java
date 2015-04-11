package bodylog;

import bodylog.ui.MainWindow;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main class.
 *
 * Creates an instance of MainWindow and sets it visible. Also sets an
 * UncaughtExceptionHandler to catch any uncaught exceptions and write the stack
 * trace to a file instead of the console. In such case a pop-up window will be
 * displayed saying there's an error. The program will close after closing the
 * pop-up.
 *
 * @see bodylog.ui.MainWindow
 */
public class BodyLog {

    private static void createAndShowFrame() {

        final JFrame frame = new MainWindow();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger logger = Logger.getLogger("Uncaught Exception logger");
                FileHandler fileHandler;

                try {

                    // This block configures the logger with handler and formatter  
                    fileHandler = new FileHandler("ERROR.log");
                    logger.addHandler(fileHandler);
                    SimpleFormatter formatter = new SimpleFormatter();
                    fileHandler.setFormatter(formatter);

                    // Set to false when not wanting to print stacktrace to console
                    logger.setUseParentHandlers(true);

                    logger.info("Logger created.");

                } catch (SecurityException | IOException ex) {
                    logger.info("Exception encountered while adding file handler to logger.");
                }

                JOptionPane.showMessageDialog(null,
                        "An unexpected error occurred.\nProgram will close.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, null, e);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowFrame();
            }
        });
    }
}
