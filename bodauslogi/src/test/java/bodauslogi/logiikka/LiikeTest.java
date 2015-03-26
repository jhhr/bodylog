package bodauslogi.logiikka;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LiikeTest {

    Liike penkki;
    Sarja sarja;

    @Before
    public void SetUp() {
        penkki = new Liike("penkki");
        sarja = new Sarja();
    }

    @Test
    public void LiikkeenMuuttujaJoukkoAlussaTyhja() {
        assertTrue(penkki.getMuuttujaJoukko().isEmpty());
    }

    @Test
    public void LiikkeenSarjaListaAlussaTyhja() {
        assertTrue(penkki.getSarjaLista().isEmpty());
    }

    @Test
    public void MuuttujaJoukonKokoYksiKunLisattyYksiMuuttuja() {
        penkki.lisaaMuuttuja("paino");
        assertEquals(1, penkki.getMuuttujaJoukko().size());
    }
    
    @Test
    public void LaittomiaMerkkejaEiVoiKayttaaLiikkeenNimessä() {
        boolean liikeLuotu = false;
        for (char ch : new char[]{'{','}',':',','}) {
            try {
                new Liike("asd"+ch+"fjkl");
                liikeLuotu = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(liikeLuotu);
    }

    @Test
    public void LaittomiaMerkkejaEiVoiKayttaaMuuttujassa() {
        boolean muuttujaLisatty = false;
        for (char ch : new char[]{'{','}',':',','}) {
            try {
                penkki.lisaaMuuttuja("asd"+ch+"fjkl");
                muuttujaLisatty = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(muuttujaLisatty);
    }

    @Test
    public void LisattyTyhjaSarjaLoytyyOikeastaPaikasta() {
        penkki.lisaaSarja(sarja);
        assertEquals(sarja, penkki.getSarjaLista().get(0));
    }

    @Test
    public void SarjaListanKokoYksiKunSarjojaYksi() {
        penkki.lisaaSarja(sarja);
        assertEquals(1, penkki.getSarjaLista().size());
    }
}
