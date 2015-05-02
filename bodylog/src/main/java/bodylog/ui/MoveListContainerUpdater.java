package bodylog.ui;

import bodylog.files.read.MoveReader;
import bodylog.logic.Move;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
import bodylog.ui.view.StatisticsDisplayer;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Class for updating the contents of the list of Moves in MoveChoosers when new
 * move files have been added or old ones edited.
 *
 * Contains a list of MoveListContainers. When a WindowWithMoveChooser is
 * created, it adds its MoveChooser to that list.
 *
 * @see bodylog.ui.edit.WindowWithMoveChooser
 * @see bodylog.ui.edit.MoveChooser
 */
public class MoveListContainerUpdater {

    private final ArrayList<MoveListContainer> moveListContainers;
    private final MoveReader reader;
    private StatisticsDisplayer displayer;

    /**
     * Creates a new MoveListUpdater with an empty list of MoveListContainers.
     */
    public MoveListContainerUpdater() {
        this.moveListContainers = new ArrayList();
        this.reader = new MoveReader();
    }

    /**
     * Sets the displayer for this.
     *
     * @param displayer the displayer to be remembered
     */
    public void setDisplayer(StatisticsDisplayer displayer) {
        this.displayer = displayer;
    }

    /**
     * Tells the displayer to reset the display for the given Move. The
     * displayer is null, if it has not been set which is the situation when a
     * StatisticsViewerWindow has not been opened yet. Once one has been opened,
     * the displayer stays non-null even when the window is closed. This method
     * may still be called which results in a useless resetDisplay call.
     *
     * @param move the Move whose display should be reset
     *
     * @see bodylog.ui.view.StatisticsDisplayer#resetDisplay
     */
    public void updateDisplayer(Move move) {
        if (displayer != null) {
            displayer.resetDisplay(move);
        }
    }

    /**
     * Adds a MoveListContainer to the list of containers that are be updated by
     * this.
     *
     * @param container MoveListContainer to be added
     */
    public void addContainer(MoveListContainer container) {
        moveListContainers.add(container);
    }

    /**
     * Removes the specified MoveListContainer from the list. Used when a
     * WindowWithMoveListContainer is closed.
     *
     * @param container MoveListContainer to be removed
     */
    public void removeContainer(MoveListContainer container) {
        moveListContainers.remove(container);
    }

    /**
     * Creates a new data model from move files to be used by MoveChoosers.
     *
     * @return a new array of all Moves
     * @throws FileNotFoundException if a file cannot be found during reading
     * move files
     * @throws SecurityException when a move file cannot be accessed
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     */
    public Move[] newMoveList() throws FileNotFoundException, SecurityException,
            ParsingException, VariableStateException {
        return reader.fetchAllMoves();
    }

    /**
     * Updates the MoveListContainers with a new list of moves.
     *
     * @throws FileNotFoundException when a move file is not found while
     * creating the move list
     * @throws SecurityException when a move file cannot be accessed
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     */
    public void updateContainers() throws FileNotFoundException,
            SecurityException, ParsingException, VariableStateException {
        Move[] moves = reader.fetchAllMoves();
        for (MoveListContainer container : moveListContainers) {
            container.reloadMoveList(moves);
        }
    }

    public void updateContainersWithNewMove(Move move) {
        for (MoveListContainer container : moveListContainers) {
            container.addMove(move);
        }
    }

    public void updateContainersWithChangedMove(Move oldMove, Move newMove) {
        for (MoveListContainer container : moveListContainers) {
            container.removeMove(oldMove);
            container.addMove(newMove);
        }
    }
}
