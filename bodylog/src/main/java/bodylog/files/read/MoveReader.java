package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.files.filters.MoveFileFilter;
import bodylog.logic.Move;
import bodylog.logic.datahandling.Moves;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class encapsulating the reading of fetchMove files and creating Moves from
 * them.
 */
public class MoveReader {

    /**
     * Creates a new <code>Move</code> based on a specified file. Name is
     * acquired from the filename and variables from the contents. The Move will
     * contain no Sessions.
     *
     * @param moveFile the move file to be read
     * @return a <code>Move</code>
     * @throws FileNotFoundException if the file is not found
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     */
    public Move fetchMove(File moveFile) throws FileNotFoundException,
            ParsingException, VariableStateException {
        Scanner scanner = new Scanner(moveFile);
        String name = moveFile.getName();
        name = name.substring(0, name.length() - Constant.MOVE_END.length());
        Move move = Moves.parse(scanner, name);
        scanner.close();
        return move;
    }

    /**
     * Creates a list of fetchMove files which are used to create new Moves.
     *
     * @return an array of Files, empty if no move files are in the
     * movements folder. Identifies the right kinds of files through the file
     * ending using a MoveFileFilter
     * 
     * @throws SecurityException if the files cannot be accessed
     * 
     * @see bodylog.files.MoveFileFilter
     */
    public File[] fetchMoveFileList() throws SecurityException {
        Constant.createMovesFolder();
        return Constant.MOVES_DIR.listFiles(new MoveFileFilter());
    }

    /**
     * Creates a list of MoveFiles by reading all fetchMove files and creating a
     * new MoveSaver from each one. Used in the MoveChooser UI class and the
     * method allMovesWithSessions.
     *
     * @return an array containing Moves with Sessions added. The array is empty
     * if no fetchMove files are found. The Moves will contain no Sessions if no
     * session files are found. The Sessions contain Sets when the session file
     * contains set data
     * 
     * @throws FileNotFoundException when a move file is not found
     * @throws SecurityException when a move file cannot be accessed
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     * 
     * @see bodylog.files.FromFile#moveFileList
     * @see bodylog.files.edit.MoveSaver
     * @see bodylog.files.FromFile#allMovesWithSessions
     * @see bodylog.ui.dataediting.MoveChooser
     */
    public Move[] fetchAllMoves() throws FileNotFoundException,
            SecurityException, ParsingException, VariableStateException {
        File[] moveFileList = fetchMoveFileList();
        Move[] moveList = new Move[moveFileList.length];
        for (int i = 0; i < moveFileList.length; i++) {
            moveList[i] = fetchMove(moveFileList[i]);
        }
        return moveList;
    }
}
