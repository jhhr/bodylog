package bodylog.ui.dataediting;

import bodylog.logic.Move;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

/**
 * Abstract class implemented by SessionEditorWindow and MoveEditorWindow.
 * Functions with the abstract class Editor. Has a top and bottom component. Top
 * component contains a MoveChooser that adds components to the bottom part
 * based on the Move chosen by the user.
 *
 * @see Editor
 * @see MoveChooser
 * @see SessionEditorWindow
 * @see MoveEditorWindow
 */
public abstract class WindowWithMoveChooser extends JScrollPane {

    protected final JPanel chooserPanel;
    protected final JPanel editorPanel;
    protected final JLabel noEditorsOpen;

    /**
     * Creates a new window with the MoveChooser at the top and a JLabel titled
     * "No movements selected" as the component contained in the bottom part.
     *
     * @throws Exception May throw an exception when creating the MoveChooser as
     * it reads move files.
     */
    public WindowWithMoveChooser() throws Exception {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        chooserPanel = new MoveChooser(this);
        editorPanel = new JPanel();
        noEditorsOpen = new JLabel("No movements selected");

        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));
        editorPanel.add(noEditorsOpen);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(chooserPanel, c);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JSeparator(), c);

        c = new GridBagConstraints();
//        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_START;
        panel.add(editorPanel, c);

        setViewportView(panel);
    }

    /**
     * Checks if there's an existing Editor created from the specified Move. If
     * so, displays a pop-up window informing the user. Move is acquired from
     * the MoveChooser. Meant to be used in the <code>addEditorAllowed</code>
     * method that must be implemented by classes extending
     * WindowWithMoveChooser.
     *
     * @param move The move used for checking
     * @param editorType added to the message in the pop-up window signifying
     * what type of Editor is already open
     * @return true when an Editor for which
     * <code>editor.getMove().equals(move)</code> is true is found, false
     * otherwise
     * @see MoveChooser
     * @see SessionEditorWindow#addEditorAllowed
     * @see MoveEditorWindow#addEditorAllowed
     */
    protected boolean moveHasOpenEditor(Move move, String editorType) {
        Component[] editors = editorPanel.getComponents();
        for (Component comp : editors) {
            Editor editor = (Editor) comp;
            if (editor.getMove().equals(move)) {
                JOptionPane.showMessageDialog(this,
                        "There's an open " + editorType + "for this move already",
                        "Not allowed", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the specified Editor from the editor panel. Checks after removing
     * whether no Editors are open. If so, adds the JLabel titled "No movements
     * selected" to the panel. Called by the Editor itself.
     *
     * @param editor Editor to be removed
     * @see Editor#closeButton
     * @see Editor#actionPerformed
     */
    public void removeEditorComponent(Editor editor) {
        editorPanel.remove(editor);
        if (editorPanel.getComponentCount() == 0) {
            editorPanel.add(noEditorsOpen);
        }
        validate();
        repaint();
    }

    /**
     * Adds an Editor for the specified Move if allowed. Called by the
     * MoveChooser when the user selects a Move.
     *
     * @param move move to be used in adding the new Editor
     *
     * @see MoveChooser#actionPerformed
     * @see SessionEditorWindow#addEditor
     * @see MoveEditorWindow#addEditor
     */
    public void addNewEditor(Move move) {
        editorPanel.remove(noEditorsOpen);

        if (addEditorAllowed(move)) {
            return;
        }
        addEditor(move);
    }

    /**
     * Checks whether adding an editor for the Move should happen or not. Used
     * in <code>addNewEditor</code>.
     *
     * @param move Move used in checking if adding and Editor for the Move is
     * allowed
     * @return true if allowed, false otherwise
     * @see SessionEditorWindow#addEditorAllowed
     * @see MoveEditorWindow#addEditorAllowed
     */
    protected abstract boolean addEditorAllowed(Move move);

    /**
     * Actually adds an editor to this window. Used in
     * <code>addNewEditor</code>.
     *
     * @param move move used in adding the Editor
     * @see SessionEditorWindow#addEditor
     * @see MoveEditorWindow#addEditor
     */
    protected abstract void addEditor(Move move);
}
