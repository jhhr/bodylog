/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.files.edit;

import bodylog.files.Constant;
import bodylog.logic.Move;
import bodylog.logic.Variable;
import java.io.File;

public class Util {

    public Move bench;
    public Move deadlift;
    public Move newMove;
    public final String benchName = "bench";
    public final String dlName = "deadlift";
    public final String weightName = "weight";
    public final String repsName = "reps";
    public final String pumpedName = "pumped";
    public final String jackedName = "jackedness";
    public final String progName = "current program";
    public final String[] noChoices = new String[]{};
    public final String[] jackedChoices = new String[]{"shredded", "fat"};
    public final String[] progChoices = new String[]{"SS", "TM"};
    public final Variable varWeight = new Variable(
            weightName, Variable.Type.NUMERICAL, noChoices);
    public final Variable varReps = new Variable(
            repsName, Variable.Type.NUMERICAL, noChoices);
    public final Variable varPumped = new Variable(
            pumpedName, Variable.Type.CHECKBOX, noChoices);
    public final Variable varJacked = new Variable(
            jackedName, Variable.Type.OPTIONAL_CHOICE, jackedChoices);
    public final Variable varProg = new Variable(
            progName, Variable.Type.MANDATORY_CHOICE, progChoices);
    public final Variable[] vars = new Variable[]{
        varWeight, varReps, varPumped, varJacked, varProg};

    public final File dlFolder = new File(Constant.DATA_DIR, dlName);
    public final File benchFolder = new File(Constant.DATA_DIR, benchName);
}
