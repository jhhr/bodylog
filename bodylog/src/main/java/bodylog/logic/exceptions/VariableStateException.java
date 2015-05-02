/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodylog.logic.exceptions;

/**
 * Custom exception used in the Variable class.
 *
 * @see bodylog.logic.Variable#checkState
 */
public class VariableStateException extends Exception {

    public VariableStateException(String message) {
        super(message);
    }
}
