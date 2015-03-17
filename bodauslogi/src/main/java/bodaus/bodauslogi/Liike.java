
package bodaus.bodauslogi;

import java.util.ArrayList;

public class Liike {

    private String nimi;
    private ArrayList<String> muuttujat;
    private ArrayList<Sarja> sarjat;
    
    public Liike(String nimi){
        this.nimi = nimi;
        this.muuttujat = new ArrayList<>();
        this.sarjat = new ArrayList<>();
    }
    
    public ArrayList<String> getMuuttujat(){
        return this.muuttujat;
    }
    
    public void lisaaMuuttuja(String nimike){
        muuttujat.add(nimike);
    }
    
    public void lisaaSarja(){
        sarjat.add(new Sarja(this));
    }
    
    @Override
    public String toString(){
        String str = nimi + "\n";
        for (Sarja sarja : sarjat) {
            str += sarja + "\n";
        }
        return str;
    }
}
