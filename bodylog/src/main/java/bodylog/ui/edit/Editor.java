package bodylog.ui.edit;

import bodylog.files.abstracts.Saver;
import bodylog.logic.Move;
import bodylog.ui.tables.abstracts.EditorTable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Abstract class implemented by SessionEditor and MoveEditor. Contains a Move
 * given by a MoveChooser from a <code>WindowWithMoveChooser</code>. Is
 * contained in a WindowWithMoveChooser. Has implemented methods for creating
 * some buttons and setting the border.
 *
 * @see bodylog.ui.edit.session.SessionEditor
 * @see bodylog.ui.edit.move.MoveEditor
 * @see bodylog.ui.edit.WindowWithMoveChooser
 * @see bodylog.ui.edit.MoveChooser
 */
public abstract class Editor extends JPanel implements ActionListener {

    protected final EditorTable tableModel;
    protected final JTable table;
    protected final JScrollPane tablePane;
    protected final Saver saver;
    protected final WindowWithMoveChooser window;
    protected final JPanel buttonsUpper;
    protected final JPanel buttonsLeft;

    public static final String SAVE_TITLE = "Save to File";
    public static final String CLOSE_TITLE = "Close Editor";

    /**
     * Creates a new Editor for the given Saver and window.
     *
     * @param saver the Saver used to save to file with this Editor
     * @param window the window that contains this Editor
     */
    public Editor(Saver saver, WindowWithMoveChooser window) {
        this.window = window;
        this.saver = saver;
        this.tableModel = setTableModel();
        this.table = setTable();
        this.tablePane = new JScrollPane(table);

        setLayout(new GridBagLayout());

        this.buttonsUpper = new JPanel();
        this.buttonsLeft = new JPanel();

        setComponentsIntoLayout();
    }

    /**
     * Sets up the TableModel used in this Editor. Used in constructor.
     *
     * @return an implementation of the abstract class EditorTable
     */
    protected abstract EditorTable setTableModel();

    /**
     * Sets up the JTable used in this Editor. Used in constructor.
     *
     * @return a JTable using the TableModel set of this Editor.
     */
    protected abstract JTable setTable();

    private void setComponentsIntoLayout() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(buttonsLeft, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(tablePane, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        add(buttonsUpper, c);
    }

    /**
     * Sets the buttons in this Editor into their proper place.
     *
     * @param rowAdderLabel the label for rowAdderButton
     * @param rowRemoverLabel the label for rowRemoverButton
     * @param textFieldLabel the label for the text input field
     * @param textFieldText the text for the text input field
     * @param textFieldToolTip the toolTip for the text field
     * @param msgAddition an optional addition to the confirm message in save
     * button
     *
     * @see bodylog.ui.edit.Editor#rowAdderButton
     * @see bodylog.ui.edit.Editor#rowRemoverButton
     * @see bodylog.ui.edit.Editor#textField
     * @see bodylog.ui.edit.Editor#saveButton
     * @see bodylog.ui.edit.Editor#closeButton
     */
    protected void setButtonLayouts(
            String rowAdderLabel,
            String rowRemoverLabel,
            String textFieldLabel,
            String textFieldText,
            String textFieldToolTip,
            String msgAddition) {
        buttonsLeft.setLayout(new GridLayout(2, 1));
        buttonsLeft.add(rowAdderButton(rowAdderLabel));
        buttonsLeft.add(rowRemoverButton(rowRemoverLabel));

        buttonsUpper.setLayout(new GridLayout(1, 3));
        buttonsUpper.add(textField(textFieldLabel, textFieldText, textFieldToolTip));
        buttonsUpper.add(saveButton(msgAddition));
        buttonsUpper.add(closeButton(CLOSE_TITLE));
    }

    /**
     * Creates a button for removing this component from its parent container.
     *
     * @param buttonTitle title for the button describing what is being closed.
     *
     * @return JButton object with ActionListener added
     */
    protected JButton closeButton(String buttonTitle) {
        JButton b = new JButton(buttonTitle);
        b.addActionListener(this);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        window.removeEditor(this);
    }

    /**
     * Sets a compound border for this component with the name of the move of
     * this Editor + an optional addition as its title.
     *
     * @param addition Optional addition to the title of the border
     */
    protected void setEditorBorder(String addition) {
        String moveName = getMove().getName();
        if (moveName.isEmpty()) {
            moveName = "Name not set";
        }
        setBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createTitledBorder(
                                moveName + addition),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );
    }

    /**
     * Creates a button for adding rows into the Table. After adding the
     * container of the table is resized.
     *
     * @param title The displayed title of the button
     *
     * @return JButton with ActionListener added
     */
    protected JButton rowAdderButton(String title) {
        JButton setAdderButton = new JButton(title);
        setAdderButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow();
                resizeAndUpdate();
            }
        });
        return setAdderButton;
    }

    /**
     * Creates button for removing rows from the table. Does nothing if only one
     * row remains. Asks for confirmation if the row has data. After removal the
     * container of the table is resized.
     *
     * @param title The displayed title of the button
     *
     * @return JButton with ActionListener added
     */
    protected JButton rowRemoverButton(String title) {
        final JButton setRemoverButton = new JButton(title);
        setRemoverButton.addActionListener(
                new RemoveButtonListenerWithConfirmPopup(setRemoverButton));
        return setRemoverButton;
    }

    private class RemoveButtonListenerWithConfirmPopup implements ActionListener {

        private final JPopupMenu confirmPopup;
        private final JButton removeButton;

        public RemoveButtonListenerWithConfirmPopup(JButton removeButton) {
            this.removeButton = removeButton;
            this.confirmPopup = new JPopupMenu();
            JLabel info = new JLabel(" row is not default");
            info.setForeground(Color.GRAY);
            confirmPopup.add(info);
            confirmPopup.addSeparator();
            confirmPopup.add(new JMenuItem(new AbstractAction("confirm remove") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeRow(tableModel.getRowCount() - 1);
                }
            }));
            confirmPopup.setPreferredSize(new Dimension(110,
                    confirmPopup.getPreferredSize().height));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int rowCount = tableModel.getRowCount();
            //check if there's more than one row
            if (rowCount > 1) {
                //if so check if the last row has been edited
                if (!tableModel.rowHasBeenEdited(rowCount - 1)) {
                    confirmPopup.show(removeButton,
                            removeButton.getBounds().x,
                            removeButton.getBounds().y);
                } else {
                    removeRow(rowCount - 1);
                }
            }
        }

        private void removeRow(int row) {
            tableModel.removeRow(row);
            resizeAndUpdate();
        }

    }

    /**
     * Resizes the container holding the JTable when a row is added or
     * subtracted. There should be a better way to do this than manually
     * changing the preferred size.
     */
    protected void resizeAndUpdate() {
        Dimension d = table.getPreferredSize();
        tablePane.setPreferredSize(new Dimension(d.width + 18, d.height + 23));
        revalidate();
        repaint();
    }

    /**
     * Creates a text input field. When the user types into the field it and
     * presses enter the Editor will change the text displayed in the border. It
     * is intended that, if the attempt fails, nothing happens but the input
     * will remain in the field until changed by the user.
     *
     * @param label the label used in front of the text field to signify what
     * it's for
     * @param startText the text displayed in the text field at the start
     * @param message the message shown in the toolTip when hovering over the
     * text field
     *
     * @return a JPanel containing the label and text field
     *
     * @see bodylog.ui.edit.move.MoveEditor#textFieldAction
     * @see bodylog.ui.edit.session.SessionEditor#textFieldAction
     */
    protected JPanel textField(String label, String startText, String message) {
        JPanel setNameContainer = new JPanel();
        setNameContainer.add(new JLabel(label));
        JTextField nameField = new JTextField(startText);
        //sets width of the text field
        nameField.setColumns(10);
        //make 5 pixels of empty space left of the text field
        nameField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        nameField.setToolTipText(message);
        nameField.addActionListener(new TextFieldListener());
        setNameContainer.add(nameField);
        //add 5 pixels of empty space to the left of the whole thing
        setNameContainer.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        return setNameContainer;
    }

    private class TextFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField textField = (JTextField) e.getSource();
            String text = textField.getText();

            textFieldAction(text);
        }

    }

    /**
     * Action taken for user input in the main text field of this Editor.
     *
     * @param text string created by the ActionListener of the text field
     *
     * @see bodylog.ui.edit.move.MoveEditor#textFieldAction
     * @see bodylog.ui.edit.session.SessionEditor#textFieldAction
     */
    protected abstract void textFieldAction(String text);

    /**
     * Creates a button for writing the session to move. An ActionListener is
     * added to the button. <code>userConfirmsSaveToFile</code> is called in the
     * implementation of the <code>actionPerformed</code> which finally calls
     * <code>saveToFile</code>.
     *
     * @param addition used in <code>userConfirmsSaveToFile</code> which is used
     * the ActionListener given to this button
     * @return JButton with a listener added
     * @see bodylog.ui.edit.Editor#userConfirmsSaveToFile
     * @see bodylog.ui.edit.session.SessionEditor#saveToFile
     * @see bodylog.ui.edit.move.MoveEditor#saveToFile
     */
    protected JButton saveButton(final String addition) {
        JButton tallennusNappi = new JButton(SAVE_TITLE);
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
     * Saves the data put in by the user into this editor into a move.
     *
     * @see bodylog.ui.edit.session.SessionEditor#saveToFile
     * @see bodylog.ui.edit.move.MoveEditor#saveToFile
     */
    protected abstract void saveToFile();

    /**
     * Method for checking whether the file to be to written to already exists.
     * Used in <code>userConfirmsSaveToFile</code>.
     *
     * @return should return true if move exists, false otherwise
     * @see bodylog.ui.edit.Editor#userConfirmsSaveToFile
     */
    protected abstract boolean fileExists();

    /**
     * Method for checking whether the user wants to overwrite an existing move.
     * Uses the method <code>fileExists</code> to check if an user check is
     * necessary. Used by ActionListeners given to the saveButton. When
     * <code>fileExists</code> returns true a pop-up window is shown to the user
     * prompting for input.
     *
     * @param addition An optional addition to the the message given in the
     * option window shown to the user.
     *
     * @return true if the user does want to overwrite, false otherwise
     *
     * @see bodylog.ui.edit.Editor#saveButton
     */
    protected boolean userConfirmsSaveToFile(String addition) {
        if (fileExists()) {
            String message = "Movement: " + getMove().getName() + addition + "\n"
                    + "A file for this already exists.\n"
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

    public Move getMove() {
        return saver.getMove();
    }

}
