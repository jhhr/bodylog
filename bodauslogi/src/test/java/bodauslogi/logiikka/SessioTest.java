package bodauslogi.logiikka;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SessioTest {

    Date tanaan;
    Sessio sessio;
    Liike penkki;
    Sarja sarja;

    @Before
    public void SetUp() {
        tanaan = new Date();
        sessio = new Sessio(tanaan);
        penkki = new Liike("penkki");
        sarja = new Sarja();
    }

    @Test
    public void SessionTreemapAvaimetAlussaTyhja() {
        assertTrue(sessio.getLiikkeidenNimienJoukko().isEmpty());
    }

    @Test
    public void SessionPaivamaaraSamaKuinAnnettu() {
        assertEquals(tanaan, sessio.getPaivamaara());
    }

    @Test
    public void SessionTreemapArvotAlussaTyhja() {
        assertTrue(sessio.getLiikkeidenJoukko().isEmpty());
    }

    @Test
    public void SessioonEiVoiLisataLiikettaJollaTyhjaNimi() {
        assertFalse(sessio.lisaaLiike(new Liike("")));
    }

    @Test
    public void SessioonLisattyYksiLiikeNiinTreeMapAvaimiaYksi() {
        sessio.lisaaLiike(penkki);
        assertEquals(1, sessio.getLiikkeidenNimienJoukko().size());
    }

    @Test
    public void SessioonLisattyYksiLiikeNiinTreeMapArvojaYksi() {
        sessio.lisaaLiike(penkki);
        assertEquals(1, sessio.getLiikkeidenJoukko().size());
    }

    @Test
    public void LiikkeenPoisto_TreeMapAvaimiaOikeaMaara() {
        sessio.lisaaLiike(penkki);
        sessio.poistaLiike("penkki");
        assertTrue(sessio.getLiikkeidenNimienJoukko().isEmpty());
    }

    @Test
    public void LiikkeenPoisto_TreeMapArvojaOikeaMaara() {
        sessio.lisaaLiike(penkki);
        sessio.poistaLiike("penkki");
        assertTrue(sessio.getLiikkeidenJoukko().isEmpty());
    }

    @Test
    public void YhdenLiikkeenLisaysOnnistuuKunAiempiaNolla() {
        assertTrue(sessio.lisaaLiike(penkki));
    }

    @Test
    public void SessioPalauttaaOikeanLiikkeenKunLiikettaOnMuokattu() {
        sessio.lisaaLiike(penkki);
        penkki.lisaaMuuttuja("paino");
        assertEquals(penkki, sessio.getLiike("penkki"));
    }

    @Test
    public void SamannimisenLiikkeenLisays_EiOnnistu() {
        sessio.lisaaLiike(penkki);
        Liike penkki2 = new Liike("penkki");
        assertFalse(sessio.lisaaLiike(penkki2));
    }

    @Test
    public void SamannimisenLiikkeenLisays_EiKorvaaAiempaa() {
        sessio.lisaaLiike(penkki);
        Liike penkki2 = new Liike("penkki");
        sessio.lisaaLiike(penkki2);
        assertEquals(penkki, sessio.getLiike("penkki"));
    }

    @Test
    public void SamannimisenLiikkeenLisays_UusiLiikeEiSisallySessioon() {
        sessio.lisaaLiike(penkki);
        Liike penkki2 = new Liike("penkki");
        sessio.lisaaLiike(penkki2);
        assertFalse(sessio.getLiikkeidenJoukko().contains(penkki2));
    }

    @Test
    public void EriNimisenLiikkeenLisaysKunAiempiaYksi_Onnistuu() {
        sessio.lisaaLiike(penkki);
        Liike penkki2 = new Liike("vinopenkki");
        assertTrue(sessio.lisaaLiike(penkki2));
    }

    @Test
    public void EiVoiLisataSarjaaLiikkeelleIlmanMuuttujia() {
        sessio.lisaaLiike(penkki);
        assertFalse(sessio.lisaaSarjaLiikkeelle("penkki", sarja));
    }

    @Test
    public void EiVoiLisataSarjaaOlemattomalleLiikkeelle() {
        assertFalse(sessio.lisaaSarjaLiikkeelle("penkki", sarja));
    }

    @Test
    public void LisattyOikeanKokoinenSarjaLoytyyOikeastaPaikasta_YksiMuuttuja() {
        sessio.lisaaLiike(penkki);
        penkki.lisaaMuuttuja("paino");
        sarja.lisaaArvo(50.0);
        sessio.lisaaSarjaLiikkeelle("penkki", sarja);
        assertEquals(sarja, penkki.getSarjaLista().get(0));
    }

    @Test
    public void OikeanlaisenSarjanLisaysLiikkeelle_OnnistuuKunMuuttujiaYksi() {
        penkki.lisaaMuuttuja("paino");
        sarja.lisaaArvo(60.0);
        sessio.lisaaLiike(penkki);
        assertTrue(sessio.lisaaSarjaLiikkeelle("penkki", sarja));
    }

    @Test
    public void VaaranKokoisenSarjanLisaysLiikkeelle_EiOnnistuKunMuuttujiaNollavsYksi() {
        penkki.lisaaMuuttuja("paino");
        sessio.lisaaLiike(penkki);
        assertFalse(sessio.lisaaSarjaLiikkeelle("penkki", sarja));
    }

    @Test
    public void VaaranKokoisenSarjanLisaysLiikkeelle_EiOnnistuKunMuuttujiaYksiVsNolla() {
        sarja.lisaaArvo(60.0);
        sessio.lisaaLiike(penkki);
        assertFalse(sessio.lisaaSarjaLiikkeelle("penkki", sarja));
    }
}
