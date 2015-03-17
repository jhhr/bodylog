package bodaus.bodauslogi;

import java.util.Date;
import java.util.TreeMap;

public class Sessio {

    private Date pvm;
    private TreeMap<String,Liike> liikkeet;

    public Sessio(Date pvm) {
        this.pvm = pvm;
        this.liikkeet = new TreeMap<>();
    }

    public void lisaaLiike(String nimi, Liike liike) {
        liikkeet.put(nimi,liike);
    }
    
    public void lisaaSarjaLiikkeelle(String nimi){
        liikkeet.get(nimi).lisaaSarja();
    }
    
    @Override
    public String toString(){
        String str = pvm.toString() + "\n";
        for (Liike liike : liikkeet.values()) {
            str += liike + "\n";
        }
        return str;
    }
}
