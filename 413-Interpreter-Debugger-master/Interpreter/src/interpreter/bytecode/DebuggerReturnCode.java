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
public class DebuggerReturnCode extends ReturnCode{
    
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        
        DebuggerVM dVM= (DebuggerVM) vm;
        dVM.exePopRecordOnExecutionStack();
        
        //dVM.endScope(); //was getting nullpointer exceptions untill I enabled this
        if(dVM.isWriteRead()){
            dVM.setWriteRead(false);  //set WriteRead flag to false(to prevent poping envfuncrecordstack
            dVM.endScope();
            dVM.PopRecord();
        }
        else{
            
            dVM.endScope();
            dVM.PopRecord();
            
            //dVM.exePopRecordOnExecutionStack();
        }
            
        
    }
}
