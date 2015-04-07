package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.Sarja;
import bodauslogi.logiikka.Sessio;
import bodauslogi.util.Vakiot;
import bodauslogi.util.liikeFiltteri;
import bodauslogi.util.sessioFiltteri;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Scanner;

public class Tiedostosta {
    
    public static Liike liikeSessioton(File liikeTiedosto) throws Exception {
        Scanner lukija = new Scanner(liikeTiedosto);
        String nimi = liikeTiedosto.getName();
        nimi = nimi.substring(0, nimi.length() - 4);
        Liike liike = new Liike(nimi);
        while (lukija.hasNextLine()) {
            liike.lisaaMuuttuja(lukija.nextLine());
        }
        lukija.close();
        return liike;
    }

    private static Sarja sarjaSessiolle(Scanner lukija) {
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

    private static TemporalAccessor paivamaaraSessiolle(File sessioTiedosto) throws Exception {
        String pvm = sessioTiedosto.getName();
        pvm = pvm.substring(0, pvm.length() - 4);
        return Vakiot.TIEDOSTOPVM.parse(pvm);
    }

    public static Sessio sessio(File sessioTiedosto) throws Exception {
        Scanner lukija = new Scanner(sessioTiedosto);
        Sessio sessio = new Sessio(paivamaaraSessiolle(sessioTiedosto));
        while (lukija.hasNextLine()) {
            sessio.lisaaSarja(sarjaSessiolle(lukija));
        }
        lukija.close();
        return sessio;
    }

    public static Liike liikeSessioineen(File liikeTiedosto) throws Exception {
        Liike liike = liikeSessioton(liikeTiedosto);
        File liikeSessioKansio = new File(Vakiot.SESSIOT + "/" + liike);
        liikeSessioKansio.mkdir();
        for (File sessioFilu : liikeSessioKansio.listFiles(new sessioFiltteri())) {
            liike.lisaaSessio(sessio(sessioFilu));
        }
        return liike;
    }

    public static Liike[] kaikkiLiikkeetSessioineen() throws Exception {
        File[] liikeKansioLista = liikeTiedostoLista();
        Liike[] liikeLista = new Liike[liikeKansioLista.length];
        for (int i = 0; i < liikeKansioLista.length; i++) {
            liikeLista[i] = liikeSessioineen(liikeKansioLista[i]);
        }
        return liikeLista;
    }
    
    public static File[] liikeTiedostoLista() throws Exception {
        File liikkeetKansio = new File(Vakiot.LIIKKEET);
        liikkeetKansio.mkdir();
        return liikkeetKansio.listFiles(new liikeFiltteri());
    }
    
    public static Liike[] liikeLista() throws Exception{
        File[] liikeTiedostoLista = liikeTiedostoLista();
        Liike[] liikeLista = new Liike[liikeTiedostoLista.length];
        for (int i = 0; i < liikeTiedostoLista.length; i++) {
            liikeLista[i] = liikeSessioton(liikeTiedostoLista[i]);
        }
        return liikeLista;
    }
    
    public static String[] liikeNimiLista() throws Exception{
        File[] liikeTiedostoLista = liikeTiedostoLista();
        String[] liikeNimiLista = new String[liikeTiedostoLista.length];
        for (int i = 0; i < liikeTiedostoLista.length; i++) {
            String tiedostoNimi = liikeTiedostoLista[i].getName();
            liikeNimiLista[i] = tiedostoNimi.substring(0, tiedostoNimi.length()-4);
        }
        return liikeNimiLista;
    }
}
