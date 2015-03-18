package bodauslogi.logiikka;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SessioTest {

    Date tanaan;
    Sessio ses1;
    Liike penkki;

    @Before
    public void SetUp() {
        tanaan = new Date();
        ses1 = new Sessio(tanaan);
        penkki = new Liike("penkki");
    }

    @Test
    public void SessionTreemapAvaimetAlussaTyhja() {
        assertTrue(ses1.getLiikkeidenNimienJoukko().isEmpty());
    }

    @Test
    public void SessionTreemapArvotAlussaTyhja() {
        assertTrue(ses1.getLiikkeidenJoukko().isEmpty());
    }

    @Test
    public void SessioonLisattyYksiLiikeNiinTreeMapAvaimiaYksi() {
        ses1.lisaaLiike(penkki);
        assertEquals(1, ses1.getLiikkeidenNimienJoukko().size());
    }

    @Test
    public void SessioonLisattyYksiLiikeNiinTreeMapArvojaYksi() {
        ses1.lisaaLiike(penkki);
        assertEquals(1, ses1.getLiikkeidenJoukko().size());
    }

    @Test
    public void YhdenLiikkeenLisaysOnnistuuKunAiempiaNolla() {
        assertTrue(ses1.lisaaLiike(penkki));
    }

    @Test
    public void SessioPalauttaaOikeanLiikkeenKunLiikettaOnMuokattu() {
        ses1.lisaaLiike(penkki);
        penkki.lisaaMuuttuja("paino");
        assertEquals(penkki, ses1.getLiike("penkki"));
    }

    @Test
    public void SamannimisenLiikkeenLisays_EiOnnistu() {
        ses1.lisaaLiike(penkki);
        Liike penkki2 = new Liike("penkki");
        assertFalse(ses1.lisaaLiike(penkki2));
    }

    @Test
    public void SamannimisenLiikkeenLisays_EiKorvaaAiempaa() {
        ses1.lisaaLiike(penkki);
        Liike penkki2 = new Liike("penkki");
        ses1.lisaaLiike(penkki2);
        assertEquals(penkki, ses1.getLiike("penkki"));
    }

    @Test
    public void SamannimisenLiikkeenLisays_UusiLiikeEiSisallySessioon() {
        ses1.lisaaLiike(penkki);
        Liike penkki2 = new Liike("penkki");
        ses1.lisaaLiike(penkki2);
        assertFalse(ses1.getLiikkeidenJoukko().contains(penkki2));
    }

    @Test
    public void EriNimisenLiikkeenLisaysKunAiempiaYksi_Onnistuu() {
        ses1.lisaaLiike(penkki);
        Liike penkki2 = new Liike("vinopenkki");
        assertTrue(ses1.lisaaLiike(penkki2));
    }
    
}
