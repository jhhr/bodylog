

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
     * Creates a new Move with Sessions added. Reads a move file and all fetchSession
 files in the movement's folder. First creates a Move without Sessions,
 then adds all the Sessions. Identifies fetchSession files in the movement's
 folder through the file ending using a SessionFileFilter.
     *
     * @param move Move to which sessions will be added
     * @return A Move with Sessions added, populated with Sets when the fetchSession
 file contained set data.
     * @throws FileNotFoundException when a fetchSession file cannot be found
     * @see bodylog.files.SessionFileFilter
     * @see bodylog.logic.Move
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     */
    public Move fetchSessionsForMove(Move move) throws FileNotFoundException {
        File moveDataFolder = new File(Constant.DATA_DIR, move.getName());
        moveDataFolder.mkdir();
        for (File sessionFile : moveDataFolder.listFiles(new SessionFileFilter())) {
            move.addSession(fetchSession(sessionFile));
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
    public Session fetchSession(File sessionFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(sessionFile);
        Session session = new Session(fetchDateForSession(sessionFile));
        while (scanner.hasNextLine()) {
            session.addSet(fetchSetForSession(scanner));
        }
        scanner.close();
        return session;
    }

    /**
     * Creates a time object for a Session. Acquired from the filename of a
 fetchSession file. Uses a DateTimeFormatter to parse the string into a
 TemporalAccessor.
     *
     * @param sessionFile file to be read
     * @return a TemporalAccessor used in the constructor of a Session
     * @see bodylog.logic.Session
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     */
    private TemporalAccessor fetchDateForSession(File sessionFile) {
        String dateStr = sessionFile.getName();
        dateStr = dateStr.substring(0, 
                dateStr.length() - Constant.SESSION_END.length());
        return Constant.FILE_DATE_FORMAT.parse(dateStr);
    }

    /**
     * Creates a Set from a line in a fetchSession file given in a Scanner. Private
     * method used by <code>fetchSession</code>. Uses
     * <code>DataHandling.stringToSetValue</code> to parse the strings into
     * appropriate values for the Set.
     *
     * @param scanner Scanner that holds the file, given by the calling method
     * @return a Set with the values read from the line in the file
     * @see bodylog.logic.DataHandling#stringToSetValue(String)
     * @see bodylog.logic.Set
     */
    private Set fetchSetForSession(Scanner scanner) {
        Set set = new Set();
        String[] stringValues = DataHandling.lineToStringArray(scanner.nextLine());
        for (String valueStr : stringValues) {
            set.addValue(DataHandling.stringToSetValue(valueStr));
        }
        return set;
    }

}
