package bodylog.files.read;

import bodylog.files.Constant;
import bodylog.files.Delete;
import bodylog.logic.Move;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SessionReaderTest {

    private SessionReader reader;
    private Util util;

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Delete.filesAndFolders();
    }

    @Before
    public void setUp() throws Exception {
        util = new Util();
        reader = new SessionReader();
    }

    @After
    public void tearDown() {
        Delete.filesAndFolders();
    }

    @Test
    public void Session_SetDataSameAsInFile() throws Exception {
        util.useReadingFiles();
        util.compareSetDataInSession(util.readingSessionData,
                reader.session(util.readSessionFile));
    }

    @Test
    public void Session_SessionDateSameAsOnFile() throws Exception {
        util.useReadingFiles();
        util.compareDateInSession(util.dateStrONE,
                reader.session(util.readSessionFile));
    }

    @Test
    public void AddSessionsToMove_SessionsDatesAsExpected() throws Exception {
        util.useSkipLegsFiles();
        Move skipLegs2 = reader.addSessionsToMove(new Move(util.skipName));

        util.compareDateInSession(util.dateStrONE, skipLegs2.getSession(0));
        util.compareDateInSession(util.dateStrTWO, skipLegs2.getSession(1));
    }

    @Test
    public void AddSessionsToMove_SetDataAsExpected() throws Exception {
        util.useSkipLegsFiles();
        Move skipLegs2 = reader.addSessionsToMove(new Move(util.skipName));

        util.compareSetDataInSession(util.skipSessionONEData, skipLegs2.getSession(0));
        util.compareSetDataInSession(util.skipSessionTWOData, skipLegs2.getSession(1));
    }

}
