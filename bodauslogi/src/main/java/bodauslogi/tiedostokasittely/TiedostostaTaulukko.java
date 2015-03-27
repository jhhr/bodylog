package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Taulukko;
import bodauslogi.logiikka.Sarja;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TiedostostaTaulukko {

    private TiedostostaTaulukko() {
    }

    private static Liike luoLiike(File sessioTiedosto) throws Exception {
        String nimi = sessioTiedosto.getParentFile().getName();
        File liikeTiedosto = new File(Kansiot.LIIKKEET + "/" + nimi + ".txt");
        Liike liike = TiedostostaLiike.luo(liikeTiedosto);
        return liike;
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
        return new SimpleDateFormat("dd.MM.yyyy").parse(pvm);
    }

    public static Taulukko luo(File sessioTiedosto) throws Exception {
        Scanner lukija = new Scanner(sessioTiedosto);
        Liike liike = luoLiike(sessioTiedosto);
        while (lukija.hasNextLine()) {
            liike.lisaaSarja(luoSarja(lukija));
        }
        lukija.close();
        return new Taulukko(liike, luoPaivamaara(sessioTiedosto));
    }

}
