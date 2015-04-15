package bodylog.files.filters;

import bodylog.files.Constant;
import bodylog.files.filters.MoveFileFilter;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MoveFileFilterTest {

    private MoveFileFilter moveFilter;

    @Before
    public void setUp() {
        moveFilter = new MoveFileFilter();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void WithSessionEnding_InMovesFolderAccepted() {
        assertTrue(moveFilter.accept(Constant.MOVES_DIR, "test" + Constant.MOVE_END));
    }

    @Test
    public void WithoutSessionEnding_InMovesFolderNotAccepted() {
        assertFalse(moveFilter.accept(Constant.MOVES_DIR, "test.txt"));
    }

    @Test
    public void WithSessionEnding_NotInMovesFolderNotAccepted() {
        assertFalse(moveFilter.accept(Constant.DATA_DIR, "test" + Constant.MOVE_END));
    }
}
