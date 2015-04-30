package bodylog.logic.datahandling;

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
