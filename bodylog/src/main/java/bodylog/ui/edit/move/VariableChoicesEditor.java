
package bodylog.ui.edit.move;

import bodylog.logic.datahandling.Names;
import bodylog.logic.exceptions.NameNotAllowedException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class VariableChoicesEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {

    private static final String EDIT = "edit";

    private final JButton button;
    private String[] currentChoices;
    private ChoiceEditor editor;

    public VariableChoicesEditor() {
        this.button = new JButton(Arrays.toString(currentChoices));
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        editor = new ChoiceEditor(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            editor.setTable(currentChoices);
            editor.setVisible(true);
        } else {
            currentChoices = editor.updateChoices();
            button.setText(Arrays.toString(currentChoices));
            editor.setVisible(false);
        }
    }

    @Override
    public Object getCellEditorValue() {
        return currentChoices;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        currentChoices = (String[]) value;
        return button;
    }

    private class ChoiceEditor extends JDialog {

        private final JScrollPane tablePane;
        private JTable table;
        private DefaultTableModel model;

        public ChoiceEditor(ActionListener doneListener) {
            super(JOptionPane.getRootFrame(), "edit choices", true);
            tablePane = new JScrollPane();

            setMinimumSize(new Dimension(120, 200));
            setLocationRelativeTo(button);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            final JPanel outerPanel = new JPanel();
            outerPanel.setLayout(new BorderLayout());
            final JPanel innerPanel = new JPanel();
            outerPanel.add(innerPanel, BorderLayout.NORTH);
            innerPanel.setLayout(new GridBagLayout());

            final JButton addChoice = new JButton("add choice");
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
            innerPanel.add(addChoice, c);

            final JButton removeChoice = new JButton("remove last");
            final Action removeAction = new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowCount = model.getRowCount();
                    if (rowCount > 0) {
                        if (rowCount == 1) {
                            setEnabled(false);
                        }
                        model.removeRow(rowCount - 1);
                        resizeAndUpdate();
                    }
                }
            };
            removeChoice.addActionListener(removeAction);
            addChoice.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (model.getRowCount() == 0) {
                        removeAction.setEnabled(true);
                    }

                    model.addRow(new Object[1]);
                    resizeAndUpdate();
                }
            });

            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            innerPanel.add(removeChoice, c);

            JButton done = new JButton("Done");
            done.addActionListener(doneListener);
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 2;
            c.fill = GridBagConstraints.HORIZONTAL;
            innerPanel.add(done, c);

            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 3;
            innerPanel.add(tablePane, c);

            setContentPane(new JScrollPane(outerPanel));
            pack();
        }

        private void setTable(String[] currentChoices) {
            final Object[][] tableChoices;
            if (currentChoices == null) {
                tableChoices = new Object[0][1];
            } else {
                int size = currentChoices.length;
                tableChoices = new Object[size][1];
                for (int i = 0; i < size; i++) {
                    tableChoices[i][0] = currentChoices[i];
                }
            }
            model = newModel(tableChoices);

            table = new JTable(model);
            table.setToolTipText("Characters not allowed in choices: "
                    + Names.IllegalCharsWithSpaces(Names.Illegal.VARIABLE));
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            table.getTableHeader().setReorderingAllowed(false);
            tablePane.setViewportView(table);
            resizeAndUpdate();
        }

        private void resizeAndUpdate() {
            Dimension d = table.getPreferredSize();
            tablePane.setPreferredSize(new Dimension(d.width + 18, d.height + 23));
            revalidate();
            repaint();
        }

        private String[] updateChoices() {
            int rowCount = table.getRowCount();
            String[] newChoices = new String[rowCount];
            for (int i = 0; i < rowCount; i++) {
                newChoices[i] = (String) table.getValueAt(i, 0);
            }
            return newChoices;
        }

        private DefaultTableModel newModel(Object[][] tableChoices) {
            return new DefaultTableModel(tableChoices, new Object[]{"choice"}) {

                @Override
                public Class getColumnClass(int column) {
                    return String.class;
                }

                @Override
                public void setValueAt(Object value, int row, int column) {
                    Object parsedValue = null;
                    try {
                        parsedValue = Names.isAllowed(
                                (String) value, Names.Illegal.VARIABLE);
                    } catch (NameNotAllowedException n) {
                    }
                    Vector rowVector = (Vector) dataVector.elementAt(row);
                    rowVector.setElementAt(parsedValue, column);
                    fireTableCellUpdated(row, column);
                }
            };
        }
    }
}
