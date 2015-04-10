
package bodylog.files;

import java.time.format.DateTimeParseException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConstantTest {
    
    private final String uiDate = "12.11.2015";
    private final String fileDate = "2015-11-12";
    private final String unparseableDate = "1a.50.1999";
    
    @Before
    public void setUp() {
    }

    @Test
    public void uiDateConvertedToExpectedFileDate() {
        assertEquals(fileDate, Constant.uiDateToFileDate(uiDate));
    }
    
    @Test (expected = DateTimeParseException.class)
    public void DoesntParseAnIncorrectUiDate(){
        Constant.uiDateToFileDate(unparseableDate);
    }
    
}
