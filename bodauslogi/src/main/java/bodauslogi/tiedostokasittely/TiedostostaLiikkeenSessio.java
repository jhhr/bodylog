package bodauslogi.tiedostokasittely;

import bodauslogi.logiikka.Liike;
import bodauslogi.logiikka.LiikkeenSessio;
import bodauslogi.logiikka.Sarja;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TiedostostaLiikkeenSessio {

    private final File sessioTiedosto;
    private final Scanner lukija;

    public TiedostostaLiikkeenSessio(File sessioTiedosto) throws Exception {
        this.sessioTiedosto = sessioTiedosto;
        lukija = new Scanner(sessioTiedosto);
    }

    private Liike luoLiikeTiedostoista() throws Exception {
        String nimi = sessioTiedosto.getParentFile().getName();
        File liikeTiedosto = new File(Kansiot.LIIKKEET + "/" + nimi + ".txt");
        Liike liike = new TiedostostaLiike(liikeTiedosto).luoLiike();
        return liike;
    }

    private Sarja luoSarjaSessioTiedostosta() {
        Sarja sarja = new Sarja();
        String[] muuttujatJaArvotArray = lukija.nextLine().split(",");
        for (String pari : muuttujatJaArvotArray) {
            String[] muuttujaJaArvo = pari.split(":");            
            String muuttuja = muuttujaJaArvo[0];
            String arvo = muuttujaJaArvo[1];
            if (muuttuja.contains("{")) {
                StringBuilder sb = new StringBuilder(muuttuja); 
                sb.deleteCharAt(sb.indexOf("{"));
                muuttuja = sb.toString();
            }
            if (arvo.contains("}")) {
                StringBuilder sb = new StringBuilder(arvo);
                sb.deleteCharAt(sb.indexOf("}"));
                arvo = sb.toString();
            }
            if (arvo.equals("null")) {
                sarja.lisaaArvo(muuttuja);
            } else {
                sarja.lisaaArvo(muuttuja, Double.parseDouble(arvo));
            }
        }
        return sarja;
    }

    private Date luoPaivamaaraSessioTiedostosta() throws Exception {
        String pvm = sessioTiedosto.getName();
        pvm = pvm.substring(0,pvm.length() - 4);
        return new SimpleDateFormat("dd.MM.yyyy").parse(pvm);
    }

    public LiikkeenSessio luoLiikkeenSessio() throws Exception {
        Liike liike = luoLiikeTiedostoista();
        while (lukija.hasNextLine()) {
            liike.lisaaSarja(luoSarjaSessioTiedostosta());
        }
        lukija.close();
        return new LiikkeenSessio(liike, luoPaivamaaraSessioTiedostosta());
    }

}
