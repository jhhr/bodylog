package bodylog;

import bodylog.ui.MainWindow;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Encapsulates the starting of the UI and uncaught exception handling.
 *
 * @see bodylog.ui.MainWindow
 */
public class BodyLog implements Runnable {

    // set to false when not wanting to print stacktrace to console
    private static final boolean PRINT_STACKTRACE_TO_CONSOLE = true;

    private JFrame frame;

    /**
     * Creates an instance of MainWindow and sets it visible. Also sets an
     * UncaughtExceptionHandler to catch any uncaught exceptions and write the
     * stack trace to a file. In such case a pop-up window will be displayed
     * saying there's been an unexpected error. The program will close after
     * closing the pop-up.
     *
     * @see bodylog.ui.MainWindow
     */
    @Override
    public void run() {

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtHandler());

        frame = new MainWindow();
        frame.setVisible(true);
    }

    private class UncaughtHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Logger logger = Logger.getLogger("Uncaught Exception logger");
            setFileHandlerToLogger(logger);

            JOptionPane.showMessageDialog(null,
                    "An unexpected error occurred.\nProgram will close.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, null, e);

            frame.dispose();
        }

    }

    private void setFileHandlerToLogger(Logger logger) {
        FileHandler fileHandler;
        try {
            // This block configures the logger with handler and formatter  
            fileHandler = new FileHandler("ERROR.log", true);//appends to file
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            logger.setUseParentHandlers(PRINT_STACKTRACE_TO_CONSOLE);

            logger.info("Logger created.");

        } catch (Exception ex) {
            logger.info("Exception encountered while adding file handler to logger.");
        }
    }
}
