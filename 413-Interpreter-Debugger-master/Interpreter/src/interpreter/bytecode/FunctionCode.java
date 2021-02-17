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
public class FunctionCode extends ByteCode{
    String name;
    Integer start, end;

    @Override
    public void init(ArrayList args) {
        name= (String)args.get(0);
        start= Integer.parseInt((String)args.get(1));
        end= Integer.parseInt((String)args.get(2));
        
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebuggerVM dVM = (DebuggerVM) vm;
        if (!(dVM.isWriteRead())) {
            dVM.setFuncNameStartEnd(name, start, end);
        }
        else{
            dVM.setFuncNameStartEnd(name, start, end);
        }
        

//        ////puts an instance of Record class in vector for keeping track of function executions
//        if ((!(name.contains("Read"))) && (!(name.contains("Write")))) {
//            dVM.exePushRecordOnExecutionStack();
//        }
    }
    
}
