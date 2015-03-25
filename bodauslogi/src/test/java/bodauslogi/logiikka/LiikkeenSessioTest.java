
package bodauslogi.logiikka;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class LiikkeenSessioTest {
    
    Liike penkki;
    Date pvm;
    LiikkeenSessio liikeSessio;
    
    @Before
    public void setUp(){
        penkki = new Liike("penkki");
        pvm = new Date();
    }
    
    @Test
    public void konstruktoriToimii(){
        liikeSessio = new LiikkeenSessio(penkki, pvm);
        assertEquals(penkki, liikeSessio.getLiike());
        assertEquals(pvm, liikeSessio.getPaivamaara());
    }

}
