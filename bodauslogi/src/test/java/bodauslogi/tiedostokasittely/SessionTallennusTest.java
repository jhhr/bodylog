package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class SessionTallennusTest {

    Sessio sessio;
    Liike penkki;
    Liike mave;
    Sarja sarja1;
    Sarja sarja2;
    Sarja sarja3;
    SessionTallennus tallennus;
    
    @Before
    public void setUp() {
        Sessio sessio = new Sessio(new Date(0));
        Liike penkki = new Liike("penkki");
        Liike mave = new Liike("mave");
        sessio.lisaaLiike(penkki);
        sessio.lisaaLiike(mave);
        for (Liike liike : sessio.getLiikkeidenJoukko()) {
            liike.lisaaMuuttuja("paino");
            liike.lisaaMuuttuja("toistot");
        }
        Sarja sarja1 = new Sarja();
        sarja1.lisaaArvo("paino", 65.0);
        sarja1.lisaaArvo("toistot", 10);
        Sarja sarja2 = new Sarja();
        sarja2.lisaaArvo("paino", 55.0);
        sarja2.lisaaArvo("toistot", 8);
        Sarja sarja3 = new Sarja();
        sarja3.lisaaArvo("paino", 125.0);
        sarja3.lisaaArvo("toistot", 5);
        penkki.lisaaSarja(sarja1);
        penkki.lisaaSarja(sarja2);
        mave.lisaaSarja(sarja3);
        SessionTallennus tallennus = new SessionTallennus(sessio);
    }
}
