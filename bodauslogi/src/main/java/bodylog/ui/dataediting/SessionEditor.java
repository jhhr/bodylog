package bodylog.ui.dataediting;

import bodylog.ui.tables.EditorTable;
import bodylog.logic.Move;
import bodylog.logic.Set;
import bodylog.logic.Session;
import bodylog.files.ToFile;
import bodylog.util.Constant;
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

public class SessionEditor extends Editor {

    private final DefaultTableModel tableModel;
    private final JScrollPane tablePane;
    private final JTable table;
    private final JPanel buttonsUpper;
    private final JPanel buttonsLeft;
    private String dateStr;

    public SessionEditor(Move move, SessionEditorWindow editorWindow) {
        super(move, editorWindow);
        
        setLayout(new GridBagLayout());
        
        this.buttonsUpper = new JPanel();
        this.buttonsLeft = new JPanel();
        this.dateStr = Constant.UI_DATE_FORMAT.format(LocalDate.now());

        tableModel = new EditorTable(move.variablesToArray(), 1, this);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.getTableHeader().setReorderingAllowed(false);

        tablePane = new JScrollPane(table);
        setEditorBorder(" " + dateStr);

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

        buttonsLeft.setLayout(new GridLayout(2, 1));
        buttonsLeft.add(setAdderButton());
        buttonsLeft.add(setRemoverButton());

        buttonsUpper.setLayout(new GridLayout(1, 3));
        buttonsUpper.add(setDateButton());
        buttonsUpper.add(saveButton(new SaveButtonListener()));
        buttonsUpper.add(closeButton("close session"));
    }

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

    private JPanel setDateButton() {
        JPanel setDateContainer = new JPanel();
        setDateContainer.add(new JLabel("Set date:"));
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
//                    JOptionPane.showMessageDialog(getParent(), "Päivämäärä oltava muodossa 'pp.kk.vvvv'.", "Error", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(SessionLisays.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                dateStr = text;
                setEditorBorder(" " + dateStr);
            }
        });
        setDateContainer.add(tekstiAlue);
        return setDateContainer;
    }

    protected class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (userConfirmsSaveToFile(", " + dateStr)) {
                return;
            }

            addSessionsToMove();

            try {
                ToFile.sessions(move);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getParent(), ex.getCause() + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(SessionEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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

    }

    @Override
    protected boolean fileExistsAlready() {
        return ToFile.sessionFileExists(move, Constant.UIDateToFileDate(dateStr));
    }

    private void resizeAndUpdate() {
        Dimension d = table.getPreferredSize();
        tablePane.setPreferredSize(new Dimension(d.width + 18, d.height + 23));
        revalidate();
        repaint();
    }
}
