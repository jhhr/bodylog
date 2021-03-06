package bodylog.logic;

import bodylog.logic.datahandling.Sets;
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
        assertEquals(60.0, set.getValue(0));        
        assertEquals(null, set.getValue(1));        
        assertEquals(1, set.getValue(2));               
        assertEquals(true, set.getValue(3));
    }    
    
    @Test
    public void GetValueReturnsNullIfOutofBounds(){
        assertEquals(null, set.getValue(0));
        set.addValue(false);
        assertEquals(null, set.getValue(1));
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
        set.setValue(null,0);    
        assertEquals(1, set.size());        
        set.setValue(false,3);  
        assertEquals(4, set.size());        
        set.setValue(1,2);      
        assertEquals(4, set.size());
    }
    
    @Test
    public void ToArrayWithSizeReturnsArrayWithExpectedContents(){
        set.addValue(2.1);
        set.addValue(false);
        assertArrayEquals(new Object[]{2.1, false}, set.toArray(2));        
        assertArrayEquals(new Object[]{2.1}, set.toArray(1));
        assertArrayEquals(new Object[]{2.1, false,null,null}, set.toArray(4));
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
        set.setValue(4,0);
        set.addValue(null);
        set.setValue(false,1);
        set.addValue(null);
        assertArrayEquals(new Object[]{4, false, null,}, set.toArray());
    }

}
