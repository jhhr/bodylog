package bodylog.files;

import java.time.format.DateTimeParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class ConstantTest {

    private final String uiDate = "12.11.2015";
    private final String fileDate = "2015-11-12";
    private final String unparseableDate = "1a.50.1999";

    @BeforeClass
    public static void oneTimeSetUp() {
        // Delete files first in case some files are leftover from manual testing.
        Util.deleteFiles();
    }

    @After
    public void tearDown() {
        Util.deleteFiles();
    }

    @Test
    public void uiDateConvertedToExpectedFileDate() {
        assertEquals(fileDate, Constant.uiDateToFileDate(uiDate));
    }

    @Test(expected = DateTimeParseException.class)
    public void DoesntParseAnIncorrectUiDate() {
        Constant.uiDateToFileDate(unparseableDate);
    }

    @Test
    public void Move_MoveFolderCreationWhenNotAlreadyExisting() throws Exception {
        if (!Constant.MOVES_DIR.exists()) {
            Constant.createMovesFolder();
        }
        assertTrue(Constant.MOVES_DIR.exists());
    }

}
