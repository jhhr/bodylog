package bodauslogi.logiikka;

import java.util.ArrayList;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TaulukkoTest {

    Liike penkki;
    Sarja sarja;
    Date pvm;
    Taulukko taulukko;

    @Before
    public void setUp() {
        penkki = new Liike("penkki");
        sarja = new Sarja();
        for (Double arvo : new Double[]{60.0, 15.0, 3.0}) {
            sarja.lisaaArvo(arvo);
        }
        penkki.lisaaMuuttuja("paino");
        penkki.lisaaMuuttuja("toistot");
        penkki.lisaaMuuttuja("lepoaika");
        penkki.lisaaSarja(sarja);
        pvm = new Date();
        taulukko = new Taulukko(penkki, pvm);
    }

    @Test
    public void konstruktoriToimii() {
        assertEquals(penkki, taulukko.getLiike());
        assertEquals(pvm, taulukko.getPaivamaara());
    }
    
    @Test
    public void RiviMaaraOikein(){
        assertEquals(1,taulukko.getRowCount());
    }
    
    @Test
    public void SarakeMaaraOikein(){
        assertEquals(3, taulukko.getColumnCount());
    }
    
    public void TaulukkoArvoOikein(){
        assertEquals(15.0, taulukko.getValueAt(1, 2));
    }

}
