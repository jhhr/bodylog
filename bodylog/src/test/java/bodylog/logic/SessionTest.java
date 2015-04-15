package bodylog.logic;

import bodylog.files.Constant;
import java.time.temporal.TemporalAccessor;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SessionTest {

    TemporalAccessor date;
    String dateStr;
    Session session;
    Set set1;
    Set set2;

    @Before
    public void SetUp() throws Exception{
        dateStr = "4.5.2010";
        date = Constant.UI_DATE_FORMAT.parse(dateStr);
        session = new Session();
        set1 = new Set();
        set2 = new Set();
    }

    @Test
    public void UIDateStringReturnsExpectedDate() {        
        session.setDate(date);
        assertEquals(dateStr, session.getUIDateString());
    }

    @Test
    public void SetListEmptyInNewSet() {
        assertTrue(session.getSets().isEmpty());
    }

    @Test
    public void SetListMaxSizeCorrect() {
        set1.addValue(1.0);
        set2.addValue(null);
        set2.addValue(2.0);
        session.addSet(set1);
        session.addSet(set2);
        assertEquals(2, session.maxSetSize());
    }

    @Test
    public void CorrectSetIsReturnedFromTheExpectedIndexAfterAddingValueToSet() {
        session.addSet(set1);
        set1.addValue(1.0);
        assertEquals(set1, session.getSet(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void CantAddNullIntoSetList() {
        session.addSet(null);
    }
}
