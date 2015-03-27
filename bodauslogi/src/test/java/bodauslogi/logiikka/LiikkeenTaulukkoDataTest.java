package bodauslogi.logiikka;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LiikkeenTaulukkoDataTest {

    Liike penkki;
    Date pvm;
    LiikkeenTaulukkoData liikeSessio;

    @Before
    public void setUp() {
        penkki = new Liike("penkki");
        pvm = new Date();
    }

    @Test
    public void konstruktoriToimii() {
        liikeSessio = new LiikkeenTaulukkoData(penkki, pvm);
        assertEquals(penkki, liikeSessio.getLiike());
        assertEquals(pvm, liikeSessio.getPaivamaara());
    }

}
