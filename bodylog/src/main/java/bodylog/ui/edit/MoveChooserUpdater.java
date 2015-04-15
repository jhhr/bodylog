package bodylog.ui.edit;

import bodylog.files.FromFile;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 * Class for updating the contents of the list of Moves in MoveChoosers when new
 * move files have been added or old ones edited.
 *
 * Contains a list of MoveChoosers. When a WindowWithMoveChooser is created, it
 * adds its MoveChooser to that list.
 *
 * @see bodylog.ui.edit.abstracts.WindowWithMoveChooser
 * @see bodylog.ui.edit.MoveChooser
 */
public class MoveChooserUpdater {

    private final ArrayList<MoveChooser> choosers;

    /**
     * Creates a new MoveListUpdater with an empty list of MoveChoosers.
     */
    public MoveChooserUpdater() {
        this.choosers = new ArrayList();
    }

    public void addChooser(MoveChooser chooser) {
        choosers.add(chooser);
    }

    public void removeChooser(MoveChooser chooser) {
        choosers.remove(chooser);
    }

    /**
     * Creates a new data model from move files to be used by MoveChoosers.
     * @return a new DefaultComboBoxModel containing a list of Moves
     * @throws FileNotFoundException if a file cannot be found during reading move files
     */
    public ComboBoxModel newModel() throws FileNotFoundException {
        return new DefaultComboBoxModel(FromFile.allMovesWithoutSessions());
    }

    /**
     * Updates
     * @throws FileNotFoundException
     */
    public void updateChoosers() throws FileNotFoundException {
        for (MoveChooser chooser : choosers) {
            chooser.updateMoveChooser(newModel());
        }
    }
}
