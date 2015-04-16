package bodylog.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DataHandlingTest {
    
    @Before
    public void setUp(){
        
    }
    
    @Test
    public void NoTrailingSpaceInString_IllegalCharsWithSpaces(){
        assertTrue(DataHandling.IllegalCharsWithSpaces(
                DataHandling.Illegal.VARIABLE).matches("(\\S )+\\S"));
    }

}
