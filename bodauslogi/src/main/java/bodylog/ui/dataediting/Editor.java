package bodylog.ui.dataediting;

import bodylog.logic.Move;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class Editor extends JPanel implements ActionListener {

    protected Move move;
    protected WindowWithMoveChooser window;

    public Editor(Move move, WindowWithMoveChooser window) {
        this.move = move;
        this.window = window;
    }

    public Move getMove() {
        return move;
    }

    /**
     * Creates a button for removing this component from its parent container.
     *
     * @param buttonTitle title for the button describing what is being closed.
     * @return JButton object with ActionListener added
     */
    protected JButton closeButton(String buttonTitle) {
        JButton b = new JButton(buttonTitle);
        b.addActionListener(this);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        window.removeEditorComponent(this);
    }

    /**
     * Sets a compound border for this component with the name of the move of
     * this Editor + an optional addition as its title.
     *
     * @param addition Optional addition to the title of the border
     */
    protected void setEditorBorder(String addition) {
        setBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createTitledBorder(move + addition),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );
    }

    /**
     * Creates a button for writing the session to file. The ActionListener must
     * be implemented and given to this button as its function will depend on
     * what is being written.
     *
     * @param listener an ActionListener that will actually do the writing
     * @return JButton with the listener added
     */
    protected JButton saveButton(ActionListener listener) {
        JButton tallennusNappi = new JButton("save to file");
        tallennusNappi.addActionListener(listener);
        return tallennusNappi;
    }

    /**
     * Method for checking whether the file to be to written to already exists.
     *
     * @return should return true if file exists, false otherwise
     */
    protected abstract boolean fileExistsAlready();

    /**
     * Method for checking whether the user wants to overwrite an existing file.
     * Uses the fileExistsAlready() to check if an user check is necessary.
     *
     * @param addition An optional addition to the the message given in the
     * option window shown to the user.
     * @return true if the user does want to overwrite, false otherwise
     */
    protected boolean userConfirmsSaveToFile(String addition) {
        if (fileExistsAlready()) {
            String message = "Move: " + move + addition + "\n"
                    + "A file for the above already exists.\n"
                    + "Do you want to overwrite it?\n";
            Object[] options = {"Yes", "No"};
            int userChoice = JOptionPane.showOptionDialog(
                    getParent(), message, "Confirm save to file",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[1]);

            return (userChoice != JOptionPane.YES_OPTION);
        } else {
            return false;
        }
    }

}
