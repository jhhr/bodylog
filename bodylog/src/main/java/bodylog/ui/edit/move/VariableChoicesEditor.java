/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.ui.edit.move;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class VariableChoicesEditor extends AbstractCellEditor
        implements TableCellEditor, ActionListener {

    private static final String EDIT = "edit";
    
    private final JButton button;
    private final Frame window;
    private String currentChoices;
    private ChoiceEditor editor;

    public VariableChoicesEditor(Frame window) {
        this.window = window;
        this.button = new JButton();
        button.setActionCommand(EDIT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            
        } else {
            
        }
    }

    @Override
    public Object getCellEditorValue() {
        return currentChoices;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        currentChoices = (String) value;
        return button;
    }
    
    private class ChoiceEditor extends Window{

        public ChoiceEditor() {
            super(window);
        }
        
    }

}
