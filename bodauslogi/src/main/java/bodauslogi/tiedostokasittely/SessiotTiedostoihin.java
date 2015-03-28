package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class SessiotTiedostoihin {

    public static void luoKansiot(Liike liike) throws Exception {
        new File(Vakiot.DATA).mkdir();
        new File(Vakiot.DATA + "/" + liike.getNimi() + "/").mkdir();
    }

    public static void kirjoita(Liike liike) throws Exception {

        for (Sessio sessio : liike.getSessiot()) {
            String pvmteksti = new SimpleDateFormat(Vakiot.PAIVAFORMAATTI).format(sessio.getPaivamaara());
            File sessioTiedosto = new File(Vakiot.DATA + "/" + liike.getNimi() + "/" + pvmteksti + ".txt");
            FileWriter kirjoittaja = new FileWriter(sessioTiedosto);
            for (Sarja sarja : sessio.getSarjat()) {
                kirjoittaja.write(sarja.toString() + "\n");
            }
            kirjoittaja.close();
        }
    }
}
