
package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import java.io.File;
import java.io.FileWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TiedostostaLiikeTest {
    File testiLiikeTiedosto;
    File liikkeetKansio;
    TiedostostaLiike liikkeenLuoja;
    
    @Before
    public void setUp() throws Exception{
        liikkeetKansio = new File(Kansiot.LIIKKEET);
        liikkeetKansio.mkdir();
        testiLiikeTiedosto = new File(Kansiot.LIIKKEET + "/testi.txt");
        FileWriter kirjoittaja = new FileWriter(testiLiikeTiedosto);
        kirjoittaja.write("kenkä\nkukka");
        kirjoittaja.close();
        liikkeenLuoja = new TiedostostaLiike(testiLiikeTiedosto);
    }
    
    @After
    public void tearDown(){
        if (liikkeetKansio.exists()) {
            for (String liikeFilu : liikkeetKansio.list()) {
                new File(Kansiot.LIIKKEET + "/" + liikeFilu).delete();
            }
            liikkeetKansio.delete();
        }
    }
    
    @Test
    public void luodunLiikkeeNimiOikein(){
        Liike liike = liikkeenLuoja.luoLiike();
        assertEquals("testi", liike.getNimi());
    }
    
    @Test
    public void luotuLiikeSisaltaaMuuttujatKenkaJaKukka(){
        Liike liike = liikkeenLuoja.luoLiike();
        assertArrayEquals(new String[]{"kenkä","kukka"},liike.getMuuttujaJoukko().toArray(new String[0]));
    }

}
