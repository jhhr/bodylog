package bodylog.ui.edit;

import bodylog.ui.edit.MoveChooser;
import bodylog.logic.Move;
import bodylog.ui.MoveListContainer;
import bodylog.ui.MoveListContainerUpdater;
import bodylog.ui.WindowWithMoveListContainer;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;
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
 * @see bodylog.ui.edit.Editor
 * @see bodylog.ui.dataediting.BodyFileChooser
 * @see bodylog.ui.edit.session.SessionEditorWindow
 * @see bodylog.ui.edit.move.MoveEditorWindow
 */
public abstract class WindowWithMoveChooser extends WindowWithMoveListContainer {

    protected final JPanel editorPanel;
    protected final JLabel noEditorsOpen;

    /**
     * Creates a new updater with the MoveChooser at the top and a JLabel titled
     * "No movements selected" as the component contained in the bottom part.
     *
     * @param updater MoveListContainerUpdater to which is given the MoveListContainer
     * of this window, the MoveChooser
     * @throws FileNotFoundException when creating the MoveChooser as it reads
     * move files a file may not be founds
     */
    public WindowWithMoveChooser(MoveListContainerUpdater updater)
            throws FileNotFoundException {
        super(updater);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        this.moveListContainer = new MoveChooser(this, updater.newMoveList());
        updater.addContainer(this.moveListContainer);
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
        panel.add(this.moveListContainer, c);

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
     * so, displays a pop-up updater informing the user. Move is acquired from
     * the MoveChooser. Meant to be used in the <code>addEditorAllowed</code>
     * method that must be implemented by classes extending
     * WindowWithMoveChooser.
     *
     * @param move The move used for checking
     * @param editorType added to the message in the pop-up updater signifying
     * what type of Editor is already open
     * @return true when an Editor for which
     * <code>editor.getMove().equals(move)</code> is true is found, false
     * otherwise
     * @see bodylog.ui.dataediting.BodyFileChooser
     * @see bodylog.ui.dataediting.SessionEditorWindow#addEditorAllowed
     * @see bodylog.ui.dataediting.MoveEditorWindow#addEditorAllowed
     */
    protected boolean moveHasOpenEditor(Move move, String editorType) {
        Component[] editors = editorPanel.getComponents();
        for (Component comp : editors) {
            Editor editor = (Editor) comp;
            if (editor.getMove().equals(move)) {
                JOptionPane.showMessageDialog(this,
                        "There's an open " + editorType + " for that already",
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
     * @see bodylog.ui.dataediting.Editor#closeButton
     * @see bodylog.ui.dataediting.Editor#actionPerformed
     */
    public void removeEditor(Editor editor) {
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
     * @see bodylog.ui.dataediting.MoveChooser#actionPerformed
     * @see bodylog.ui.dataediting.SessionEditorWindow#addEditor
     * @see bodylog.ui.dataediting.MoveEditorWindow#addEditor
     */
    @Override
    public void moveSelectedAction(Move move) {
        editorPanel.remove(noEditorsOpen);

        if (addEditorAllowed(move)) {
            return;
        }
        addEditor(move);
    }

    /**
     * Checks whether adding an editor for the Move should happen or not. Used
     * in <code>moveSelectedAction</code>.
     *
     * @param move Move used in checking if adding and Editor for the Move is
     * allowed
     * @return true if allowed, false otherwise
     * @see bodylog.ui.dataediting.SessionEditorWindow#addEditorAllowed
     * @see bodylog.ui.dataediting.MoveEditorWindow#addEditorAllowed
     */
    protected abstract boolean addEditorAllowed(Move move);

    /**
     * Actually adds an editor to this updater. Used in
     * <code>moveSelectedAction</code>.
     *
     * @param move move used in adding the Editor
     * @see bodylog.ui.dataediting.SessionEditorWindow#addEditor
     * @see bodylog.ui.dataediting.MoveEditorWindow#addEditor
     */
    protected abstract void addEditor(Move move);
}
