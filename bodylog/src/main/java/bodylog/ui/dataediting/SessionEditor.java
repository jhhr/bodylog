package bodylog.ui.dataediting;

import bodylog.ui.tables.EditorTable;
import bodylog.logic.Move;
import bodylog.logic.Set;
import bodylog.logic.Session;
import bodylog.files.ToFile;
import bodylog.files.Constant;
import bodylog.ui.tables.StatTable;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * The UI component used in creating actual training data and writing it into
 * session files. Contained in a SessionEditorWindow. Implementation of the
 * abstract class Editor. Data editing is done in a JTable using EditorTable as
 * its TableModel. Has buttons for adding and removing sets and setting the date
 * for the session. adding and removing rows from the table.
 *
 * @see SessionEditorWindow
 * @see Editor
 * @see EditorTable
 */
public class SessionEditor extends Editor {

    private final DefaultTableModel tableModel;
    private final JScrollPane tablePane;
    private final JTable table;
    private final JPanel buttonsUpper;
    private final JPanel buttonsLeft;
    private String dateStr;

    /**
     * Creates a new SessionEditor for the given Move. Date is set to the
     * current time acquired by with <code>LocalDate.now()</code>. The date is
     * shown in the border surrounding this component and can be changed with
     * <code>setEditorBorder</code>.
     *
     * @param move Move to be added session data into
     * @param editorWindow Parent container of this SessionEditor
     * @see Editor#setEditorBorder
     */
    public SessionEditor(Move move, SessionEditorWindow editorWindow) {
        super(move, editorWindow);

        setLayout(new GridBagLayout());

        this.buttonsUpper = new JPanel();
        this.buttonsLeft = new JPanel();
        this.dateStr = Constant.UI_DATE_FORMAT.format(LocalDate.now());

        tableModel = new EditorTable(move.variablesToArray(), 1, this);
        table = new JTable(tableModel);
        //sets tooltip for describing what inputs are accepted, displayed when hovering over the table
        table.setToolTipText("Allowed values are numbers of the form X or X.X, 'true', 'false' and nothing.");
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.getTableHeader().setReorderingAllowed(false);

        tablePane = new JScrollPane(table);
        setEditorBorder(" " + dateStr);

        setComponentsIntoLayout();
        setButtonLayouts();
    }

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

    private void setButtonLayouts() {
        buttonsLeft.setLayout(new GridLayout(2, 1));
        buttonsLeft.add(setAdderButton());
        buttonsLeft.add(setRemoverButton());

        buttonsUpper.setLayout(new GridLayout(1, 3));
        buttonsUpper.add(setDateButton());
        buttonsUpper.add(saveButton(", " + dateStr));
        buttonsUpper.add(closeButton("close session"));
    }

    /**
     * Creates a button for adding rows into the Table. After adding the
     * container of the table is resized.
     *
     * @return JButton with ActionListener added
     * @see SessionEditor#resizeAndUpdate
     */
    private JButton setAdderButton() {
        JButton setAdderButton = new JButton("add set");
        setAdderButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[tableModel.getColumnCount()]);
                resizeAndUpdate();
            }
        });
        return setAdderButton;
    }

    /**
     * Creates button for removing rows from the table. Does nothing if only one
     * row remains. After removing container of the table is resized.
     *
     * @return JButton with ActionListener added
     * @see SessionEditor#resizeAndUpdate
     */
    private JButton setRemoverButton() {
        JButton setRemoverButton = new JButton("remove set");
        setRemoverButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() > 1) {
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                    resizeAndUpdate();
                }
            }
        });
        return setRemoverButton;
    }

    /**
     * Resizes the container holding the JTable when a row is added or
     * subtracted. There should be a better to do this than the current
     * implementation.
     *
     * @see SessionEditor#setAdderButton
     * @see SessionEditor#setRemoverButton
     */
    private void resizeAndUpdate() {
        Dimension d = table.getPreferredSize();
        tablePane.setPreferredSize(new Dimension(d.width + 18, d.height + 23));
        revalidate();
        repaint();
    }

    /**
     * Creates an input field for setting the date. When the user types into the
     * field it is parsed with a <code>DateTimeFormatter</code>. If the parsing
     * fails the date of the session data is not changed, though the input will
     * remain in the field until changed by the user. When the date is changed
     * the change
     *
     * @return JPanel containing a JLabel and JTextField with added
     * ActionListener
     * @see Constant#UI_DATE_FORMAT
     */
    private JPanel setDateButton() {
        JPanel setDateContainer = new JPanel();

        JTextField tekstiAlue = new JTextField(dateStr);
        tekstiAlue.setToolTipText("Date must be in form 'dd.mm.yyyy'.");
        tekstiAlue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();
                try {
                    Constant.UI_DATE_FORMAT.parse(text);
                } catch (DateTimeParseException ex) {
                    return;
                }
                dateStr = text;
                setEditorBorder(" " + dateStr);
            }
        });

        setDateContainer.add(new JLabel("Set date:"));
        setDateContainer.add(tekstiAlue);
        return setDateContainer;
    }

    /**
     * Writes the inputted session data into a session file using the method
     * <code>ToFile.sessions</code>. First converts the table data into Sets,
     * gives those a created Session with the date of this SessionEditor and
     * gives these to the Move of this SessionEditor.The attempt may throw an
     * exception which is handled by showing a pup-up message.
     *
     * @see ToFile#sessions
     * @see SessionEditor#addSessionsToMove
     */
    @Override
    protected void saveToFile() {
        addSessionsToMove();

        try {
            ToFile.sessions(move);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(getParent(), ex.getCause() + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(SessionEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the Set and Session objects to be given the the Move. One set for
     * each row in the table. If nothing was inputted into a cell,
     * <code>null</code> is saved as the value in the proper index of that Set.
     */
    private void addSessionsToMove() {
        Session session = new Session(Constant.UI_DATE_FORMAT.parse(dateStr));
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Set sarja = new Set();
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                sarja.addValue(tableModel.getValueAt(i, j));
            }
            session.addSet(sarja);
        }
        move.addSession(session);
    }

    /**
     * Checks whether a session file with the same date as this SessionEditor
     * already exists using <code>ToFile.sessionFileExists</code>.
     *
     * @return true if the file exists, false otherwise
     * @see ToFile#sessionFileExists
     */
    @Override
    protected boolean fileExists() {
        return ToFile.sessionFileExists(move, Constant.UIDateToFileDate(dateStr));
    }
}
