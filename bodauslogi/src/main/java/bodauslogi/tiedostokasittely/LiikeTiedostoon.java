package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import java.io.File;
import java.io.FileWriter;

public class LiikeTiedostoon {

    private LiikeTiedostoon() {
    }

    public static void luoLiikkeetKansio() throws Exception {
        new File(Vakiot.LIIKKEET).mkdir();
    }

    public static void kirjoita(Liike liike) throws Exception {
        File liikeTiedosto = new File(Vakiot.LIIKKEET + "/" + liike.getNimi() + ".txt");
        FileWriter kirjoittaja = new FileWriter(liikeTiedosto);
        for (String muuttuja : liike.muuttujatToArray()) {
            kirjoittaja.write(muuttuja + "\n");
        }
        kirjoittaja.close();
    }

}
