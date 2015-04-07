package bodauslogi.logiikka;

import bodauslogi.util.Merkit;
import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LiikeTest {

    Liike penkki;
    Sessio sessio;

    @Before
    public void SetUp() {
        penkki = new Liike("penkki");
        sessio = new Sessio(LocalDate.now());
    }

    @Test
    public void LiikkeenMuuttujaJoukkoAlussaTyhja() {
        assertEquals(0, penkki.muuttujatToArray().length);
    }

    @Test
    public void LiikkeenSessioListaAlussaTyhja() {
        assertTrue(penkki.getSessiot().isEmpty());
    }

    @Test
    public void MuuttujaJoukonKokoYksiKunLisattyYksiMuuttuja() {
        penkki.lisaaMuuttuja("paino");
        assertEquals(1, penkki.muuttujatToArray().length);
    }

    @Test
    public void LaittomiaMerkkejaEiVoiKayttaaluodessaLiiketta() {
        boolean liikeLuotu = false;
        for (char ch : Merkit.kielletyt) {
            try {
                String nimi = "asd" + ch + "fjkl";
                new Liike(nimi);
                liikeLuotu = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(liikeLuotu);
    }

    @Test
    public void LaittomiaMerkkejaEiVoiKayttaaVaihtaessaNimea() {
        boolean nimiLaitettu = false;
        for (char ch : Merkit.kielletyt) {
            try {
                String nimi = "asd" + ch + "fjkl";
                penkki.setNimi(nimi);
                nimiLaitettu = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(nimiLaitettu);
    }

    @Test
    public void LaittomiaMerkkejaEiVoiKayttaaMuuttujassa() {
        boolean muuttujaLisatty = false;
        for (char ch : Merkit.kielletyt) {
            try {
                penkki.lisaaMuuttuja("asd" + ch + "fjkl");
                muuttujaLisatty = true;
            } catch (IllegalArgumentException e) {
            }
        }
        assertFalse(muuttujaLisatty);
    }

    @Test
    public void LisattyTyhjaSessioLoytyyOikeastaPaikasta() {
        penkki.lisaaSessio(sessio);
        assertEquals(sessio, penkki.getSessio(0));
    }

    @Test(expected = NullPointerException.class)
    public void EiVoiLisataNulliaSessiolistaan() {
        penkki.lisaaSessio(null);
    }

    @Test
    public void SarjaListanKokoYksiKunSarjojaYksi() {
        penkki.lisaaSessio(sessio);
        assertEquals(1, penkki.getSessiot().size());
    }
}
