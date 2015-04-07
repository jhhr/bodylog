package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import bodauslogi.util.Vakiot;
import java.io.File;
import java.io.FileWriter;

public class Tallenna {

    public static void luoLiikkeetKansio() throws Exception {
        new File(Vakiot.LIIKKEET).mkdir();
    }

    public static void liike(Liike liike) throws Exception {
        File liikeTiedosto = new File(Vakiot.LIIKKEET + "/" + liike + Vakiot.LIIKEPAATE);
        FileWriter kirjoittaja = new FileWriter(liikeTiedosto);
        for (String muuttuja : liike.muuttujatToArray()) {
            kirjoittaja.write(muuttuja + "\n");
        }
        kirjoittaja.close();
    }

    public static void luoDataKansiot(Liike liike) throws Exception {
        new File(Vakiot.SESSIOT).mkdir();
        new File(Vakiot.SESSIOT + "/" + liike).mkdir();
    }

    public static void sessiot(Liike liike) throws Exception {

        for (Sessio sessio : liike.getSessiot()) {
            String pvmteksti = Vakiot.TIEDOSTOPVM.format(sessio.getPaivamaara());
            File sessioTiedosto = new File(Vakiot.SESSIOT + "/" + liike + "/" + pvmteksti + Vakiot.SESSIOPAATE);
            FileWriter kirjoittaja = new FileWriter(sessioTiedosto);
            for (Sarja sarja : sessio.getSarjat()) {
                kirjoittaja.write(sarja.toString() + "\n");
            }
            kirjoittaja.close();
        }
    }

}
