package bodauslogi;

import bodauslogi.kayttoliittyma.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                try {
                    
                    Paaikkuna.luoJaNaytaIkkuna();
                } catch (Exception ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
}
