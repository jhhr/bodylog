package bodauslogi.logiikka;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;

public class Sessio {

    private Date paivamaara;
    private TreeMap<String, Liike> liikkeet;

    public Sessio(Date pvm) {
        this.paivamaara = pvm;
        this.liikkeet = new TreeMap<>();
    }

    public Date getPaivamaara() {
        return paivamaara;
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
        if (liike.getNimi().equals("") || liikkeet.containsKey(liike.getNimi())) {
            return false;
        } else {
            liikkeet.put(liike.getNimi(), liike);
            return true;
        }
    }

    public void poistaLiike(String nimi) {
        liikkeet.remove(nimi);
    }

    public boolean lisaaSarjaLiikkeelle(String nimi, Sarja sarja) {
        Liike liike = liikkeet.get(nimi);
        if (liike == null || liike.getMuuttujaJoukko().isEmpty()
                || liike.getMuuttujaJoukko().size() != sarja.getArvot().size()) {
            return false;
        }
        liikkeet.get(nimi).lisaaSarja(sarja);
        return true;
    }
}
