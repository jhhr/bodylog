package bodauslogi.logiikka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;

public class Sessio {

    private Date pvm;
    private TreeMap<String, Liike> liikkeet;

    public Sessio(Date pvm) {
        this.pvm = pvm;
        this.liikkeet = new TreeMap<>();
    }

    public Set<String> getLiikkeidenNimienJoukko() {
        return liikkeet.keySet();
    }

    public Collection<Liike> getLiikkeidenJoukko() {
        return liikkeet.values();
    }

    public Liike getLiike(String nimi) {
        return liikkeet.get(nimi);
    }

    public boolean lisaaLiike(Liike liike) {
        if (liikkeet.containsKey(liike.getNimi())) {
            return false;
        } else {
        liikkeet.put(liike.getNimi(), liike);
        return true;
        }
    }

    public boolean lisaaSarjaLiikkeelle(String nimi, ArrayList<Double> sarja) {
        return liikkeet.get(nimi).lisaaSarja(sarja);
    }

//    @Override
//    public String toString() {
//        String str = pvm + "\n";
//        for (Liike liike : liikkeet.values()) {
//            str += liike + "\n";
//        }
//        return str;
//    }
}
