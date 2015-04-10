package bodylog.files;

import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class SessionFileFilterTest {

    private SessionFileFilter sessionFilter;

    @Before
    public void setUp() {
        sessionFilter = new SessionFileFilter();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void FileWithSessionEndingInDataFolderAccepted() {
        assertTrue(sessionFilter.accept(Constant.DATA_DIR, "test" + Constant.SESSION_END));
    }

    @Test
    public void FileWithoutSessionEndingInDataFolderNotAccepted() {
        assertFalse(sessionFilter.accept(Constant.DATA_DIR, "test.txt"));
    }

}
