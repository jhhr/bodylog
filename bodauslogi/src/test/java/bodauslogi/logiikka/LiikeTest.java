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
    public void LiikkeenMuuttujaJoukonKokoYksiKunLisattyYksiMuuttuja() {
        penkki.lisaaMuuttuja("paino");
        assertEquals(1, penkki.getMuuttujaJoukko().size());
    }

    @Test
    public void LiikkeeseenLisattyTyhjaSarjaLoytyyOikeastaPaikasta(){
        penkki.lisaaSarja(sarja);
        assertEquals(sarja, penkki.getSarjaLista().get(0));
    }
    
    @Test
    public void LiikkeenSarjaListanKokoYksiKunSarjojaYksi(){
        penkki.lisaaSarja(sarja);
        assertEquals(1, penkki.getSarjaLista().size());
    }
}
