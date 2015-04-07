package bodauslogi.logiikka;

import bodauslogi.util.Merkit;
import java.util.ArrayList;

public class Liike {

    private String nimi;
    private ArrayList<String> muuttujat;
    private ArrayList<Sessio> sessiot;

    public Liike(String nimi) {
        Merkit.tarkistaOnkoSallittu(nimi);
        this.nimi = nimi;
        this.muuttujat = new ArrayList<>();
        this.sessiot = new ArrayList<>();
    }

    public void setNimi(String uusiNimi) {
        Merkit.tarkistaOnkoSallittu(uusiNimi);
        nimi = uusiNimi;
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

    public int muuttujaMaara() {
        return muuttujat.size();
    }

    public String getMuuttuja(int indeksi) {
        return muuttujat.get(indeksi);
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

    @Override
    public String toString() {
        return nimi;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return obj.toString().equals(this.nimi);
    }
}
