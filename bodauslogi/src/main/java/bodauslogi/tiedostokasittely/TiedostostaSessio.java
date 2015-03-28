package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TiedostostaSessio {

    private TiedostostaSessio() {
    }

    private static Sarja luoSarja(Scanner lukija) {
        Sarja sarja = new Sarja();
        String rivi = lukija.nextLine();
        rivi = rivi.substring(rivi.indexOf("{") + 1, rivi.indexOf("}"));
        String[] arvotArray = rivi.split(",");
        for (String arvo : arvotArray) {
            if (arvo.equals("null")) {
                sarja.lisaaArvo();
            } else {
                sarja.lisaaArvo(Double.parseDouble(arvo));
            }
        }
        return sarja;
    }

    private static Date luoPaivamaara(File sessioTiedosto) throws Exception {
        String pvm = sessioTiedosto.getName();
        pvm = pvm.substring(0, pvm.length() - 4);
        return new SimpleDateFormat(Vakiot.PAIVAFORMAATTI).parse(pvm);
    }

    public static Sessio luo(File sessioTiedosto) throws Exception {
        Scanner lukija = new Scanner(sessioTiedosto);
        Sessio sessio = new Sessio(luoPaivamaara(sessioTiedosto));
        while (lukija.hasNextLine()) {
            sessio.lisaaSarja(luoSarja(lukija));
        }
        lukija.close();
        return sessio;
    }
}
