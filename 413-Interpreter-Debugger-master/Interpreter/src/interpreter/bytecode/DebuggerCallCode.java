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
public class DebuggerCallCode extends CallCode {

    @Override
    public void execute(VirtualMachine vm) {
        
        
        ((DebuggerVM)vm).exePushRecordOnExecutionStack();
        
        super.execute(vm);

        DebuggerVM dVM = (DebuggerVM) vm;
        if (Label.equals("Write") || Label.equals("Read")) {   //don't want to add to functionrecredstack with write call
            System.out.println("Write/Read");
            dVM.PushNewRecord();
        } 
        else {
            dVM.PushNewRecord();
            ////puts an instance of Record class in vector for keeping track of function executions
            
        }
    }
}
