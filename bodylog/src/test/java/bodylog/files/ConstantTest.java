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
        Delete.filesAndFolders();
    }

    @After
    public void tearDown() {
        Delete.filesAndFolders();
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
    public void MovesFolderCreationReturnsTrueWhenNotAlreadyExisting() throws Exception {
        assertTrue(Constant.createMovesFolder());
    }
    
    @Test
    public void MovesFolderCreationReturnsTrueWhenAlreadyExisting() throws Exception {
        Constant.MOVES_DIR.mkdir();
        assertTrue(Constant.createMovesFolder());
    }
    
    @Test
    public void DataFolderCreationReturnsTrueWhenNotAlreadyExisting() throws Exception {
        assertTrue(Constant.createMovesFolder());
    }
    
    @Test
    public void DataFolderCreationReturnsTrueWhenAlreadyExisting() throws Exception {
        Constant.DATA_DIR.mkdir();
        assertTrue(Constant.createMovesFolder());
    }

}
