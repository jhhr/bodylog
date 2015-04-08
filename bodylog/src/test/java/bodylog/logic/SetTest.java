package bodylog.logic;

import bodylog.logic.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SetTest {

    Set set;

    @Before
    public void setUp() {
        set = new Set();
    }

    @Test
    public void NewSetIsEmpty() {
        assertTrue(set.size() == 0);
    }

    @Test
    public void AddedValuesAreFoundInExpectedIndex() {
        set.addValue(60.0);
        set.addValue(null);
        set.addValue(1);
        set.addValue(true);
        assertEquals(60.0, (Double) set.getValue(0), 0.001);        
        assertEquals(null, set.getValue(1));        
        assertEquals(1, set.getValue(2));               
        assertEquals(true, set.getValue(3));
    }

    @Test
    public void AddingValuesNormallyIncreasesListSizeAsExpected() {
        set.addValue(60.0);
        assertEquals(1, set.size());        
        set.addValue(null);
        assertEquals(2, set.size());        
        set.addValue(false);
        assertEquals(3, set.size());        
        set.addValue(1);
        assertEquals(4, set.size());        
        set.addValue(true);
        assertEquals(5, set.size());
    }
    
    @Test
    public void AddingValuesToIndexIncreasesListSizeAsExpected() {
        set.addValue(60.0);
        assertEquals(1, set.size());        
        set.addValue(null,0);    
        assertEquals(1, set.size());        
        set.addValue(false,3);  
        assertEquals(4, set.size());        
        set.addValue(1,2);      
        assertEquals(4, set.size());
    }

    @Test
    public void OrderOfValuesAsExpectedWhenAddingNormally() {
        set.addValue(5);
        set.addValue(true);
        set.addValue(null);
        assertArrayEquals(new Object[]{5, true, null}, set.toArray());
    }
    
    @Test
    public void OrderOfValuesAsExpectedWhenAddingToExistingIndex(){        
        set.addValue(12.3);
        set.addValue(4,0);
        set.addValue(null);
        set.addValue(false,1);
        set.addValue(null);
        assertArrayEquals(new Object[]{4, false, null,}, set.toArray());
    }

    @Test
    public void StringFormatCorrect() {
        set.addValue(62.5);
        set.addValue(15);
        set.addValue(null);
        set.addValue(false);
        set.addValue(0.0);
        assertEquals("{62.5,15,null,false,0}", set.toString());
    }

}
