package bodauslogi.logiikka;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Liike {

    private String nimi;
    private LinkedHashSet<String> muuttujat;
    private ArrayList<Sessio> sessiot;

    public Liike(String nimi) {
        Merkit.tarkistaOnkoSallittu(nimi);
        this.nimi = nimi;
        this.muuttujat = new LinkedHashSet<>();
        this.sessiot = new ArrayList<>();
    }

    public void setNimi(String uusiNimi) {
        Merkit.tarkistaOnkoSallittu(uusiNimi);
        nimi = uusiNimi;
    }

    public String getNimi() {
        return nimi;
    }

    public String[] muuttujatToArray() {
        return muuttujat.toArray(new String[0]);
    }

    public ArrayList<Sessio> getSessiot() {
        return sessiot;
    }

    public Sessio getSessio(int indeksi) {
        return sessiot.get(indeksi);
    }

    public void lisaaMuuttuja(String nimike) {
        Merkit.tarkistaOnkoSallittu(nimike);
        muuttujat.add(nimike);
    }

    public void lisaaSessio(Sessio sessio) {
        if (sessio == null) {
            throw new NullPointerException("yritetty lisata null liikkeen sessiolistaan");
        }
        sessiot.add(sessio);
    }
}
