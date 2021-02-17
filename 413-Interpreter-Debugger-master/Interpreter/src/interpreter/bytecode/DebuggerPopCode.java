/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import debugger.DebuggerVM;
import interpreter.VirtualMachine;

/**
 *
 * @author senatori
 */
public class DebuggerPopCode extends PopCode{
    
    
    @Override
    public void execute(VirtualMachine vm){
       super.execute(vm);
       
       DebuggerVM dVM= (DebuggerVM)vm;
       dVM.PopInFuncEnvRec(levels2pop);
        
    }
    
}
