package bodylog.files;

import bodylog.logic.DataHandling;
import bodylog.logic.Move;
import bodylog.logic.Set;
import bodylog.logic.Session;
import bodylog.ui.dataediting.MoveChooser;
import bodylog.ui.dataviewing.StatWindow;
import java.io.File;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;

/**
 * Static class for reading things from files and creating objects from them,
 * namely Moves, Sessions, Sets and file lists.
 */
public class FromFile {

    /**
     * Creates a new Move based on a specified file. Name is acquired from the
     * filename and variables from the contents. Each line in the file is read
     * and added as a variable to the Move. No sessions are added to the Move.
     *
     * @param moveFile file to be read
     * @return a Move without sessions.
     * @throws Exception when something goes wrong with the file
     * @see Move
     */
    public static Move moveWithoutSessions(File moveFile) throws Exception {
        Scanner scanner = new Scanner(moveFile);
        String name = moveFile.getName();
        name = name.substring(0, name.length() - 4);
        Move move = new Move(name);
        while (scanner.hasNextLine()) {
            move.addVariable(scanner.nextLine());
        }
        scanner.close();
        return move;
    }

    /**
     * Creates a Set from a line in a session file given in a Scanner. Private
     * method used by <code>session</code>. Uses
     * t<code>DataHandling.stringToSetValue</code> to parse the strings into
     * appropriate values for the Set.
     *
     * @param scanner Scanner that holds the file, given by the calling method
     * @return a Set with the values read from the line in the file
     * @see DataHandling#stringToSetValue(String)
     * @see Set
     */
    private static Set setForSession(Scanner scanner) {
        Set set = new Set();
        String row = scanner.nextLine();
        row = row.substring(row.indexOf("{") + 1, row.indexOf("}"));
        String[] valuesArray = row.split(",");
        for (String arvo : valuesArray) {
            set.addValue(DataHandling.stringToSetValue(arvo));
        }
        return set;
    }

    /**
     * Creates a time object for a Session. Acquired from the filename of a
     * session file. Uses a DateTimeFormatter to parse the string into a
     * TemporalAccessor.
     *
     * @param sessionFile file to be read
     * @return a TemporalAccessor used in the constructor of a Session
     * @see Session
     * @see Constant#FILE_DATE_FORMATTER
     */
    private static TemporalAccessor dateForSession(File sessionFile) {
        String dateStr = sessionFile.getName();
        dateStr = dateStr.substring(0, dateStr.length() - 4);
        return Constant.FILE_DATE_FORMATTER.parse(dateStr);
    }

    /**
     * Creates a new Session from a file. Session date is acquired from the file
     * name. Sets are created from the contents, one line to one Set.
     *
     * @param sessionFile file to be read
     * @return A new Session populated with Sets, unless the file was empty
     * @throws Exception when something goes wrong with the file
     * @see FromFile#dateForSession
     * @see FromFile#setForSession
     * @see Session
     * @see Set
     */
    public static Session session(File sessionFile) throws Exception {
        Scanner scanner = new Scanner(sessionFile);
        Session session = new Session(dateForSession(sessionFile));
        while (scanner.hasNextLine()) {
            session.addSet(setForSession(scanner));
        }
        scanner.close();
        return session;
    }

    /**
     * Creates a new Move with Sessions added. Reads a move file and all session
     * files in the movement's folder. First creates a Move without Sessions,
     * then adds all the Sessions. Identifies session files in the movement's
     * folder through the file ending using a SessionFileFilter.
     *
     * @param moveFile file given to create the Move and identify where to find
     * the session files
     * @return A Move with Sessions added, populated with Sets when the session
     * file contained set data.
     * @throws Exception when something goes reading any of the files
     * @see FromFile#session
     * @see FromFile#moveWithoutSessions
     * @see SessionFileFilter
     * @see Move
     * @see Session
     * @see Set
     */
    public static Move moveWithSessions(File moveFile) throws Exception {
        Move move = moveWithoutSessions(moveFile);
        File moveDataFolder = new File(Constant.DATA_DIR_NAME + "/" + move);
        moveDataFolder.mkdir();
        for (File sessionFile : moveDataFolder.listFiles(new SessionFileFilter())) {
            move.addSession(session(sessionFile));
        }
        return move;
    }

    /**
     * Creates a list of Moves by reading all move files and creating a new Move
     * with Sessions added from each one. Used in the StatWindow UI class.
     *
     * @return An array containing Moves with Sessions added. The array is empty
     * if no move files are found. The Moves will contain no Sessions if no
     * session files are found. The Sessions contain Sets when the session file
     * contains set data.
     * @throws Exception when something goes wrong reading any of the files
     * @see FromFile#moveFileList
     * @see FromFile#moveWithSessions
     * @see StatWindow
     * @see Move
     * @see Session
     * @see Set
     */
    public static Move[] allMovesWithSessions() throws Exception {
        File[] moveFileList = moveFileList();
        Move[] moveList = new Move[moveFileList.length];
        for (int i = 0; i < moveFileList.length; i++) {
            moveList[i] = moveWithSessions(moveFileList[i]);
        }
        return moveList;
    }

    /**
     * Creates a list of move files which are used to create new Moves.
     *
     * @return An array of Files, empty if no move files are in the movements
     * folder. Identifies the right kinds of files through the file ending using
     * a MoveFileFilter.
     * @throws Exception when something goes wrong reading any of the file
     * @see MoveFileFilter
     */
    public static File[] moveFileList() throws Exception {
        File movesFolder = new File(Constant.MOVES_DIR_NAME);
        movesFolder.mkdir();
        return movesFolder.listFiles(new MoveFileFilter());
    }

    /**
     * Creates a list of Moves by reading all move files and creating a new Move
     * without Sessions from each one. Used in the MoveChooser UI class and the
     * method allMovesWithSessions.
     *
     * @return An array containing Moves with Sessions added. The array is empty
     * if no move files are found. The Moves will contain no Sessions if no
     * session files are found. The Sessions contain Sets when the session file
     * contains set data.
     * @throws Exception when something goes wrong reading any of the files
     * @see FromFile#moveFileList
     * @see FromFile#moveWithoutSessions
     * @see FromFile#allMovesWithSessions
     * @see MoveChooser
     */
    public static Move[] allMovesWithoutSessions() throws Exception {
        File[] moveFileList = moveFileList();
        Move[] moveList = new Move[moveFileList.length];
        for (int i = 0; i < moveFileList.length; i++) {
            moveList[i] = moveWithoutSessions(moveFileList[i]);
        }
        return moveList;
    }
}
