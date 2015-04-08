package bodylog.ui.dataediting;

import bodylog.logic.Move;
import bodylog.files.FromFile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MoveChooser extends JPanel implements ActionListener {

    WindowWithMoveChooser window;

    public MoveChooser(WindowWithMoveChooser window) throws Exception {
        this.window = window;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel("Choose movement:"));

        JComboBox moveChooser = new JComboBox(FromFile.allMovesWithoutSessions());
        moveChooser.addActionListener(this);
        add(moveChooser);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        Move move = (Move) cb.getSelectedItem();
        try {
            window.updateWindow(move);
        } catch (Exception ex) {
            throw ex;
        }
    }

}
