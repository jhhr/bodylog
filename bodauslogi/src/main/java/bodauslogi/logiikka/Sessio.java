package bodauslogi.logiikka;

import bodauslogi.util.Vakiot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Sessio {

    private Date paivamaara;
    private ArrayList<Sarja> sarjat;

    public Sessio(Date pvm) {
        this.paivamaara = pvm;
        this.sarjat = new ArrayList<>();
    }

    public Date getPaivamaara() {
        return paivamaara;
    }
    
    public String getPaivamaaraString() {
        return new SimpleDateFormat(Vakiot.PAIVAFORMAATTI).format(paivamaara);
    }

    public ArrayList<Sarja> getSarjat() {
        return sarjat;
    }

    public int maxSarjaKoko() {
        int koko = 0;
        for (Sarja sarja : sarjat) {
            int sarjanKoko = sarja.koko();
            if (sarja.koko() > koko) {
                koko = sarjanKoko;
            }
        }
        return koko;
    }

    public Sarja getSarja(int indeksi) {
        return sarjat.get(indeksi);
    }

    public void lisaaSarja(Sarja sarja) {
        if (sarja == null) {
            throw new IllegalArgumentException("yritetty lisata null session sarjalistaan");
        }
        sarjat.add(sarja);
    }
}
