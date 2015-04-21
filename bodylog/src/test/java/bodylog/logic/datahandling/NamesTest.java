package bodylog.logic.datahandling;

import bodylog.logic.datahandling.Names;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class NamesTest {
    
    @Before
    public void setUp(){
        
    }
    
    @Test
    public void NoTrailingSpaceInString_IllegalCharsWithSpaces(){
        assertTrue(Names.IllegalCharsWithSpaces(
                Names.Illegal.VARIABLE).matches("(\\S )+\\S"));
    }

}
