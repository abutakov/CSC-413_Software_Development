/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

/**
 *
 * @author senatori
 */
public abstract class ByteCode {
    
    public abstract void init(java.util.ArrayList args);
    
    public abstract void execute(VirtualMachine vm);
    
    
}
