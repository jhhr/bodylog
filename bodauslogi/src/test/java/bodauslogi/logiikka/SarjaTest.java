package bodauslogi.logiikka;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SarjaTest {

    Sarja sarja;

    @Before
    public void setUp() {
        sarja = new Sarja();
    }

    @Test
    public void SarjaAlussaTyhja() {
        assertTrue(sarja.getArvoKokoelma().isEmpty() && sarja.getAvainJoukko().isEmpty());
    }

    @Test
    public void LisattyArvoLoytyy() {
        sarja.lisaaArvo("paino", 60.0);
        assertEquals(60.0, sarja.getArvo("paino"), 0.001);
    }

    @Test
    public void LisattyAvainLoytyy() {
        sarja.lisaaArvo("paino", 60.0);
        assertTrue(sarja.getAvainJoukko().contains("paino"));
    }
    
    @Test
    public void ArvojenJarjestysOikeaKahdellaMuuttujalla(){
        sarja.lisaaArvo("paino", 60.0);
        sarja.lisaaArvo("toistot", 15.0);
        assertArrayEquals(new Double[]{60.0,15.0}, sarja.getArvoKokoelma().toArray(new Double[0]));
    }
    
    @Test
    public void ArvojenJarjestysOikeaKahdellaMuuttujallaArvonMuokkauksenJalkeen(){
        sarja.lisaaArvo("paino", 60.0);
        sarja.lisaaArvo("toistot", 15.0);
        sarja.lisaaArvo("paino", 70.0);
        assertArrayEquals(new Double[]{70.0,15.0}, sarja.getArvoKokoelma().toArray(new Double[0]));
    } 
    
    @Test
    public void AvaintenJarjestysOikeaKahdellaMuuttujalla(){
        sarja.lisaaArvo("paino", 60.0);
        sarja.lisaaArvo("toistot", 15.0);
        assertArrayEquals(new String[]{"paino","toistot"}, sarja.getAvainJoukko().toArray(new String[0]));
    }
    
    @Test
    public void toStringMuotoOikeinKahdellaMuuttujalla(){
        sarja.lisaaArvo("paino", 60.0);
        sarja.lisaaArvo("toistot", 15.0);
        assertEquals("[60.0,15.0]", sarja.toString());
    }
    
}
