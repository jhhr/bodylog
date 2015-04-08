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

public abstract class WindowWithMoveChooser extends JScrollPane {

    protected final JPanel chooserPanel;
    protected final JPanel editorPanel;
    protected final JLabel noEditorsOpen;

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
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_START;
        panel.add(editorPanel, c);

        setViewportView(panel);
    }

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

    public void removeEditorComponent(Component editor) {
        editorPanel.remove(editor);
        if (editorPanel.getComponentCount() == 0) {
            editorPanel.add(noEditorsOpen);
        }
        validate();
        repaint();
    }

    public void updateWindow(Move move) {
        editorPanel.remove(noEditorsOpen);

        if (updateAllowed(move)) {
            return;
        }
        addEditor(move);
    }

    protected abstract boolean updateAllowed(Move liike);

    protected abstract void addEditor(Move liike);
}
