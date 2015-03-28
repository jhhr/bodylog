package bodauslogi.logiikka;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SessioTest {

    Date tanaan;
    Sessio sessio;
    Sarja sarja1;
    Sarja sarja2;

    @Before
    public void SetUp() {
        tanaan = new Date();
        sessio = new Sessio(tanaan);
        sarja1 = new Sarja();
        sarja2 = new Sarja();
    }

    @Test
    public void PaivamaaraSamaKuinAnnettu() {
        assertEquals(tanaan, sessio.getPaivamaara());
    }

    @Test
    public void SarjalistatAlussaTyhja() {
        assertTrue(sessio.getSarjat().isEmpty());
    }

    @Test
    public void SarjalistanMaxKokoOikein() {
        sarja1.lisaaArvo(1.0);
        sarja2.lisaaArvo();
        sarja2.lisaaArvo(2.0);
        sessio.lisaaSarja(sarja1);
        sessio.lisaaSarja(sarja2);
        assertEquals(2, sessio.maxSarjaKoko());
    }

    @Test
    public void PalauttaaOikeanSarjanKunLiikettaOnMuokattu() {
        sessio.lisaaSarja(sarja1);
        sarja1.lisaaArvo(1.0);
        assertEquals(sarja1, sessio.getSarja(0));
    }

    @Test(expected = NullPointerException.class)
    public void EiVoiLisataNulliaSarjalistaan() {
        sessio.lisaaSarja(null);
    }
}
