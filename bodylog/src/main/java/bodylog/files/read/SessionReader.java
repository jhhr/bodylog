package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.files.filters.SessionFileFilter;
import bodylog.logic.Move;
import bodylog.logic.Session;
import bodylog.logic.datahandling.Sessions;
import bodylog.logic.exceptions.ParsingException;
import bodylog.logic.exceptions.VariableStateException;
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
     *
     * @throws FileNotFoundException when a session file cannot be found
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     *
     * @see bodylog.files.SessionFileFilter
     * @see bodylog.logic.Move
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     */
    public void fetchSessionsForMove(Move move) throws FileNotFoundException,
            ParsingException, VariableStateException {
        File moveDataFolder = new File(Constant.DATA_DIR, move.getName());
        moveDataFolder.mkdirs();
        SessionFileFilter filter = new SessionFileFilter();
        File[] fileList = moveDataFolder.listFiles(filter);
        for (File sessionFile : fileList) {
            move.addSession(fetchSession(sessionFile));
        }
    }

    /**
     * Creates a new Session from a file. Session date is acquired from the file
     * name and a DateTimeFormatter is used to parse the string into a
     * TemporalAccessor. Sets are created from the contents, one line parsed to
     * one Set. into appropriate values.
     *
     * @param sessionFile file to be read
     *
     * @return A new Session populated with Sets, unless the file was empty
     *
     * @throws FileNotFoundException when the file cannot be found
     * @throws ParsingException when failing to parse the type of a Variable
     * @throws VariableStateException when a parsed Variable is found not proper
     *
     * @see bodylog.logic.DataHandling#stringToSetValue(String)
     * @see bodylog.logic.Session
     * @see bodylog.logic.Set
     * @see bodylog.files.Constant#FILE_DATE_FORMAT
     */
    public Session fetchSession(File sessionFile) throws FileNotFoundException,
            ParsingException, VariableStateException {
        Scanner scanner = new Scanner(sessionFile);
        Session session = Sessions.parse(scanner, fetchDate(sessionFile));
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
    private TemporalAccessor fetchDate(File sessionFile) {
        String dateStr = sessionFile.getName();
        dateStr = dateStr.substring(0,
                dateStr.length() - Constant.SESSION_END.length());
        return Constant.FILE_DATE_FORMAT.parse(dateStr);
    }
}
