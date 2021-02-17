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
public class LineCode extends ByteCode {

    private Integer n;
    
    @Override
    public void init(ArrayList args) {
        
        n= Integer.parseInt((String)args.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        
        DebuggerVM debugVM= (DebuggerVM)vm;
        if(n == -1)
            debugVM.setWriteRead(true);  // so we don't pop funcrecordstack on return since we didn't add on readwrite
        else    
            debugVM.setLine(n);
        
    }
    public Integer getN() {
        return n;
    }
    
}
