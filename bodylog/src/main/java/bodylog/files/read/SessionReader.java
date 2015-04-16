

package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.files.filters.SessionFileFilter;
import bodylog.logic.DataHandling;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;

public class SessionReader {

    /**
     * Creates a new Move with Sessions added. Reads a move file and all session
     * files in the movement's folder. First creates a Move without Sessions,
     * then adds all the Sessions. Identifies session files in the movement's
     * folder through the file ending using a SessionFileFilter.
     *
     * @param move Move to which sessions will be added
     * @return A Move with Sessions added, populated with Sets when the session
     * file contained set data.
     * @throws FileNotFoundException when a session file cannot be found
     * @see bodylog.files.SessionFileFilter
     * @see bodylog.logic.Move
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     */
    public Move addSessionsToMove(Move move) throws FileNotFoundException {
        File moveDataFolder = new File(Constant.DATA_DIR, move.getName());
        moveDataFolder.mkdir();
        for (File sessionFile : moveDataFolder.listFiles(new SessionFileFilter())) {
            move.addSession(session(sessionFile));
        }
        return move;
    }

    /**
     * Creates a new Session from a file. Session date is acquired from the file
     * name and a DateTimeFormatter is used to parse the string into a
     * TemporalAccessor. Sets are created from the contents, one line parsed to
     * one Set. into appropriate values.
     *
     * @param sessionFile file to be read
     * @return A new Session populated with Sets, unless the file was empty
     * @throws FileNotFoundException when the file cannot be found
     *
     * @see bodylog.logic.DataHandling#stringToSetValue(String)
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     */
    public Session session(File sessionFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(sessionFile);
        Session session = new Session(dateForSession(sessionFile));
        while (scanner.hasNextLine()) {
            session.addSet(setForSession(scanner));
        }
        scanner.close();
        return session;
    }

    /**
     * Creates a time object for a Session. Acquired from the filename of a
     * session file. Uses a DateTimeFormatter to parse the string into a
     * TemporalAccessor.
     *
     * @param sessionFile file to be read
     * @return a TemporalAccessor used in the constructor of a Session
     * @see bodylog.logic.Session
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     */
    private TemporalAccessor dateForSession(File sessionFile) {
        String dateStr = sessionFile.getName();
        dateStr = dateStr.substring(0, dateStr.length() - 4);
        return Constant.FILE_DATE_FORMAT.parse(dateStr);
    }

    /**
     * Creates a Set from a line in a session file given in a Scanner. Private
     * method used by <code>session</code>. Uses
     * <code>DataHandling.stringToSetValue</code> to parse the strings into
     * appropriate values for the Set.
     *
     * @param scanner Scanner that holds the file, given by the calling method
     * @return a Set with the values read from the line in the file
     * @see bodylog.logic.DataHandling#stringToSetValue(String)
     * @see bodylog.logic.Set
     */
    private Set setForSession(Scanner scanner) {
        Set set = new Set();
        String[] stringValues = DataHandling.lineToStringArray(scanner.nextLine());
        for (String valueStr : stringValues) {
            set.addValue(DataHandling.stringToSetValue(valueStr));
        }
        return set;
    }

}
