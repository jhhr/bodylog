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
        assertTrue(sarja.koko() == 0);
    }

    @Test
    public void LisattyArvoLoytyy() {
        sarja.lisaaArvo(60.0);
        assertEquals(60.0, sarja.getArvo(0), 0.001);
    }

    @Test
    public void LisattyArvoKasvattaaKokoa() {
        sarja.lisaaArvo(60.0);
        assertEquals(1, sarja.koko());
    }

    @Test
    public void ArvojenJarjestysOikeaKahdellaMuuttujalla() {
        sarja.lisaaArvo(60.0);
        sarja.lisaaArvo(15.0);
        assertArrayEquals(new Double[]{60.0, 15.0}, sarja.toArray());
    }

    @Test
    public void toStringMuotoOikeinKahdellaMuuttujalla() {
        sarja.lisaaArvo(60.0);
        sarja.lisaaArvo(15.0);
        assertEquals("{60.0,15.0}", sarja.toString());
    }

    @Test
    public void ToStringMuotoOikeinNullArvolla() {
        sarja.lisaaArvo();
        assertEquals("{null}", sarja.toString());
    }

}
