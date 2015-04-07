package bodauslogi.logiikka;

import bodauslogi.util.Vakiot;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class Sessio {

    private TemporalAccessor paivamaara;
    private ArrayList<Sarja> sarjat;

    public Sessio(TemporalAccessor pvm) {
        this.paivamaara = LocalDate.from(pvm);
        this.sarjat = new ArrayList<>();
    }
    
    public Sessio(){
        this.paivamaara = LocalDate.now();
        this.sarjat = new ArrayList<>();
    }
    
    public void setPaivamaara(TemporalAccessor pvm) {
        this.paivamaara = LocalDate.from(pvm);
    }

    public TemporalAccessor getPaivamaara() {
        return paivamaara;
    }
    
    public String getPaivamaaraString() {
        return Vakiot.UIPVM.format(paivamaara);
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
