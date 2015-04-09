package bodylog.ui.dataediting;

import bodylog.logic.Move;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Abstract class implemented by SessionEditor and MoveEditor. Contains a Move
 * given by a MoveChooser from a WindowWithMoveChooser. Is contained in a
 * WindowWithMoveChooser. Has implemented methods for creating some buttons and
 * setting the border.
 *
 * @see SessionEditor
 * @see MoveEditor
 * @see WindowWithMoveChooser
 * @see MoveChooser
 */
public abstract class Editor extends JPanel implements ActionListener {

    protected Move move;
    protected WindowWithMoveChooser window;

    /**
     * Creates a new Editor for the given move and window.
     *
     * @param move Move to be used in this Editor
     * @param window Window that contains this Editor
     */
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
     * Creates a button for writing the session to file. An ActionListener is
     * added to the button. <code>userConfirmsSaveToFile</code> is called in the
     * implementation of the <code>actionPerformed</code> which finally calls
     * <code>saveToFile</code>.
     *
     * @param addition used in <code>userConfirmsSaveToFile</code> which is used
     * the ActionListener given to this button
     * @return JButton with a listener added
     * @see Editor#userConfirmsSaveToFile
     * @see SessionEditor#saveToFile
     * @see MoveEditor#saveToFile
     */
    protected JButton saveButton(final String addition) {
        JButton tallennusNappi = new JButton("save to file");
        tallennusNappi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (userConfirmsSaveToFile(addition)) {
                    return;
                }

                saveToFile();
            }
        });
        return tallennusNappi;
    }

    /**
     * Saves the data put in by the user into this editor into a file.
     * 
     * @see SessionEditor#saveToFile
     * @see MoveEditor#saveToFile
     */
    protected abstract void saveToFile();

    /**
     * Method for checking whether the file to be to written to already exists.
     * Used in <code>userConfirmsSaveToFile</code>.
     *
     * @return should return true if file exists, false otherwise
     * @see Editor#userConfirmsSaveToFile
     */
    protected abstract boolean fileExists();

    /**
     * Method for checking whether the user wants to overwrite an existing file.
     * Uses the method <code>fileExists</code> to check if an user check is
     * necessary. Used by ActionListeners given to the saveButton. When
     * <code>fileExists</code> returns true a pop-up window is shown to the user
     * prompting for input.
     *
     * @param addition An optional addition to the the message given in the
     * option window shown to the user.
     * @return true if the user does want to overwrite, false otherwise
     * @see Editor#saveButton
     */
    protected boolean userConfirmsSaveToFile(String addition) {
        if (fileExists()) {
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
