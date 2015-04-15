/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bodylog.files.edit;

import bodylog.logic.Move;

public interface Saver {

    public void saveToFile() throws Exception;
    
    public boolean fileExists();
    
    public Move getMove();
}
