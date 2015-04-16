package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.files.filters.SessionFileFilter;
import bodylog.files.filters.MoveFileFilter;
import bodylog.files.read.SessionReader;
import bodylog.logic.Move;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class encapsulating the reading of move files and creating Moves from them.
 */
public class MoveReader {

    /**
     * Creates a new <code>Move</code> based on a specified file. Name is
     * acquired from the filename and variables from the contents. Each line in
     * the file is read and added as a variable to the Move. No sessions are
     * added to the Move.
     *
     * @param moveFile move file to be read
     * @return a <code>Move</code>
     * @throws FileNotFoundException if the file is not found
     */
    public Move move(File moveFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(moveFile);
        String name = moveFile.getName();
        name = name.substring(0, name.length() - 4);
        Move moveFromFile = new Move(name);
        while (scanner.hasNextLine()) {
            moveFromFile.addVariable(scanner.nextLine());
        }
        scanner.close();
        return moveFromFile;
    }



//    /**
//     * Creates a list of Moves by reading all move files and creating a new Move
//     * with Sessions added from each one. Used in the StatWindow UI class.
//     *
//     * @return An array containing Moves with Sessions added. The array is empty
//     * if no move files are found. The Moves will contain no Sessions if no
//     * session files are found. The Sessions contain Sets when the session file
//     * contains set data.
//     * @throws FileNotFoundException when any of the files cannot be found
//     * @throws SecurityException when any of the move files cannot be accessed
//     * @see bodylog.files.MoveReader#moveFileList
//     * @see bodylog.files.MoveReader#moveWithSessions
//     * @see bodylog.ui.dataviewing.StatWindow
//     * @see bodylog.logic.Move
//     * @see bodylog.logic.Session
//     * @see bodylog.logic.Set
//     */
//    public static Move[] allMovesWithSessions() throws FileNotFoundException,
//            SecurityException {
//        File[] moveFileList = moveFileList();
//        Move[] moveList = new Move[moveFileList.length];
//        for (int i = 0; i < moveFileList.length; i++) {
//            moveList[i] = moveWithSessions(moveFileList[i]);
//        }
//        return moveList;
//    }

    /**
     * Creates a list of move files which are used to create new Moves.
     *
     * @return An array of Files, empty if no move files are in the movements
     * folder. Identifies the right kinds of files through the file ending using
     * a MoveFileFilter.
     * @throws SecurityException if the files cannot be accessed
     * @see bodylog.files.MoveFileFilter
     */
    public File[] moveFileList() throws SecurityException {
        Constant.createMovesFolder();
        return Constant.MOVES_DIR.listFiles(new MoveFileFilter());
    }

    /**
     * Creates a list of MoveFiles by reading all move files and creating a new
     * MoveSaver from each one. Used in the MoveChooser UI class and the method
     * allMovesWithSessions.
     *
     * @return An array containing Moves with Sessions added. The array is empty
     * if no move files are found. The Moves will contain no Sessions if no
     * session files are found. The Sessions contain Sets when the session file
     * contains set data.
     * @throws FileNotFoundException when a move file is not found
     * @throws SecurityException when a move file cannot be accessed
     * @see bodylog.files.FromFile#moveFileList
     * @see bodylog.files.edit.MoveSaver
     * @see bodylog.files.FromFile#allMovesWithSessions
     * @see bodylog.ui.dataediting.MoveChooser
     */
    public Move[] allMoves() throws FileNotFoundException,
            SecurityException {
        File[] moveFileList = moveFileList();
        Move[] moveList = new Move[moveFileList.length];
        for (int i = 0; i < moveFileList.length; i++) {
            moveList[i] = move(moveFileList[i]);
        }
        return moveList;
    }
}
