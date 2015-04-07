
package bodauslogi.tiedostokasittely;

import bodauslogi.util.Vakiot;
import bodauslogi.logiikka.Liike;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TiedostostaTest {

    private File dataKansio;
    private File liikeKansio;
    private File liikkeetKansio;
    private File sessioTiedosto;
    private File liikeTiedosto;

    @Before
    public void setUp() throws Exception {
        dataKansio = new File(Vakiot.SESSIOT);
        dataKansio.mkdir();
        liikeKansio = new File(Vakiot.SESSIOT + "/lehdenluku");
        liikeKansio.mkdir();
        sessioTiedosto = new File(Vakiot.SESSIOT + "/lehdenluku/2014-04-07" + Vakiot.SESSIOPAATE);
        FileWriter sessioKirjoittaja = new FileWriter(sessioTiedosto);
        sessioKirjoittaja.write("{60,5}\n{40,null}");
        sessioKirjoittaja.close();
        liikkeetKansio = new File(Vakiot.LIIKKEET);
        liikkeetKansio.mkdir();
        liikeTiedosto = new File(Vakiot.LIIKKEET + "/lehdenluku" + Vakiot.LIIKEPAATE);
        FileWriter muuttujaKirjoittaja = new FileWriter(liikeTiedosto);
        muuttujaKirjoittaja.write("pisteitä\nankka");
        muuttujaKirjoittaja.close();
    }

    @After
    public void tearDown() {
        if (dataKansio.exists()) {
            for (File kansio : dataKansio.listFiles()) {
                if (kansio.isDirectory()) {
                    for (File sessioFilu : kansio.listFiles()) {
                        sessioFilu.delete();
                    }
                }
                kansio.delete();
            }
            dataKansio.delete();
        }
        if (liikkeetKansio.exists()) {
            for (File liikeFilu : liikkeetKansio.listFiles()) {
                liikeFilu.delete();
            }
            liikkeetKansio.delete();
        }
    }

    @Test
    public void sessio_luodunLiikkeenSarjatOikein() throws Exception {
        assertEquals("[{60,5}, {40,null}]",
                Tiedostosta.sessio(sessioTiedosto).getSarjat().toString());
    }

    @Test
    public void sessio_PaivamaaraOikein() throws Exception {
        assertEquals(LocalDate.from(Vakiot.TIEDOSTOPVM.parse("2014-04-07")),
                Tiedostosta.sessio(sessioTiedosto).getPaivamaara());
    }
    
    

    @Test
    public void like_NimiOikein() throws Exception {
        Liike liike = Tiedostosta.liikeSessioton(liikeTiedosto);
        assertEquals("lehdenluku", liike.toString());
    }

    @Test
    public void liike_SisaltaaOikeatMuuttujat() throws Exception {
        Liike liike = Tiedostosta.liikeSessioton(liikeTiedosto);
        assertArrayEquals(new String[]{"pisteitä", "ankka"}, liike.muuttujatToArray());
    }


}
