package bodylog.ui.dataediting;

import bodylog.ui.dataediting.abstracts.Editor;
import bodylog.logic.Move;
import bodylog.files.ToFile;
import bodylog.logic.DataHandling;
import bodylog.ui.tables.EditorTable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * The UI component used in creating new movements and editing existing ones.
 * Contained in a MoveEditorWindow. Implementation of the abstract class Editor.
 * Has buttons for adding and removing variables.
 *
 * @see bodylog.logic.Move
 * @see bodylog.ui.dataediting.MoveEditorWindow
 * @see bodylog.ui.dataediting.Editor
 */
public class MoveEditor extends Editor {

    private final EditorTable tableModel;
    private final JScrollPane tablePane;
    private final JTable table;
    private final JPanel buttonsUpper;

    public MoveEditor(Move move, MoveEditorWindow window) {
        super(move, window);

        setLayout(new GridBagLayout());

        this.buttonsUpper = new JPanel();
        String[] variables;
        Object[][] tableData;
        int varCount = move.variableCount();
        if (varCount != 0) {
            tableData = new Object[varCount][2];
            for (int i = 0; i < varCount; i++) {
                tableData[i][0] = move.getVariable(i);
                tableData[i][1] = false;
            }
        } else {
            tableData = new Object[][]{{null},{false}};
        }

        tableModel = new EditorTable(tableData, new String[]{"Variable", "Boolean"}, this) {
            @Override
            public Class getColumnClass(int column) {
                return (column == 0) ? String.class : Boolean.class;
            }
        };
        table = new JTable(tableModel);
        //sets tooltip for describing what inputs are accepted, displayed when hovering over the table
        table.setToolTipText("Characters not allowed in variables: "
                + new String(DataHandling.BANNED_CHARS_VARIABLE));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.getTableHeader().setReorderingAllowed(false);

        tablePane = new JScrollPane(table);

        setEditorBorder("");

        setComponentsIntoLayout();
        setButtonLayouts();

    }

    private void setComponentsIntoLayout() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
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

    private void setButtonLayouts() {

        buttonsUpper.setLayout(new GridLayout(1, 3));
        buttonsUpper.add(setNameField());
        buttonsUpper.add(saveButton(""));
        buttonsUpper.add(closeButton("close movement"));
    }

    /**
     * Creates an input field for setting the name of the Move. When the user
     * types into the field it and pressed enter a name change is attempted for
     * the Move. If the change fails nothing happens but the input will remain
     * in the field until changed by the user. When change succeeds the border
     * displaying the name is redrawn showing the user the name has been
     * changed.
     */
    private JPanel setNameField() {
        JPanel setNameContainer = new JPanel();
        setNameContainer.add(new JLabel("name:"));
        JTextField nameField = new JTextField(move.getName());
        nameField.setToolTipText("Charactesr not allowed in name: "
                + new String(DataHandling.BANNED_CHARS_MOVE_NAME));
        nameField.addActionListener(new NameFieldListener());
        setNameContainer.add(nameField);
        return setNameContainer;
    }

    private class NameFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField textField = (JTextField) e.getSource();
            String text = textField.getText();
            try {
                move.setName(text);
            } catch (IllegalArgumentException ill) {
                return;
            }
            setEditorBorder("");
        }

    }

    /**
     * Checks whether a move file with the same name as the Move of this Editor
     * already exists using <code>ToFile.moveFileExists</code>.
     *
     * @return true if the file exists, false otherwise
     * @see bodylog.files.ToFile#moveFileExists
     */
    @Override
    protected boolean fileExists() {
        return ToFile.moveFileExists(move);
    }

    /**
     * Writes the edited Move into a move fine using the method
     * <code>ToFile.move</code>.
     *
     * @see bodylog.files.ToFile#move
     */
    @Override
    protected void saveToFile() {
        try {
            ToFile.move(move);
            window.getMoveChooser().loadMoveList();
        } catch (Exception ex) {
            Logger.getLogger(MoveEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
