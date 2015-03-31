package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import bodauslogi.util.Vakiot;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class Tallenna {

    public static void luoLiikkeetKansio() throws Exception {
        new File(Vakiot.LIIKKEET).mkdir();
    }

    public static void liike(Liike liike) throws Exception {
        File liikeTiedosto = new File(Vakiot.LIIKKEET + "/" + liike.getNimi() + Vakiot.LIIKEPAATE);
        FileWriter kirjoittaja = new FileWriter(liikeTiedosto);
        for (String muuttuja : liike.muuttujatToArray()) {
            kirjoittaja.write(muuttuja + "\n");
        }
        kirjoittaja.close();
    }

    public static void luoDataKansiot(Liike liike) throws Exception {
        new File(Vakiot.SESSIOT).mkdir();
        new File(Vakiot.SESSIOT + "/" + liike.getNimi()).mkdir();
    }

    public static void sessiot(Liike liike) throws Exception {

        for (Sessio sessio : liike.getSessiot()) {
            String pvmteksti = new SimpleDateFormat(Vakiot.PAIVAFORMAATTI).format(sessio.getPaivamaara());
            File sessioTiedosto = new File(Vakiot.SESSIOT + "/" + liike.getNimi() + "/" + pvmteksti + Vakiot.SESSIOPAATE);
            FileWriter kirjoittaja = new FileWriter(sessioTiedosto);
            for (Sarja sarja : sessio.getSarjat()) {
                kirjoittaja.write(sarja.toString() + "\n");
            }
            kirjoittaja.close();
        }
    }

}
