/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import debugger.DebuggerVM;
import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 *
 * @author senatori
 */
public class FormalCode extends ByteCode{

    String id; Integer offset;
    
    @Override
    public void init(ArrayList args) {
        id= (String)args.get(0);
        offset= Integer.parseInt(((String)args.get(1)));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVM dVM= (DebuggerVM) vm;
        dVM.setVar(id, offset);
        
        dVM.exePushPod(offset);
        
    }
    
}
