/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;

/**
 *
 * @author senatori
 * 
 */
public class ReturnCode extends ByteCode {

    protected String Label; protected int labelAddress;

    public void init(java.util.ArrayList args) {
        
        if(args.isEmpty())
            Label= "NULL";
        else
            Label = (String) args.get(0);
        
    }

    public void execute(VirtualMachine vm) {
        vm.popFrame();        //delete the frame  & put return value on stack
        Integer pc= vm.popReturnAddrs();  
        vm.setProgramCounter(pc);   //set program counter to where we were when we made the funciton call
        
        //------dumping-----
        
        String tmp= "RETURN "; 
        if((Label.equals("NULL")) ){
            vm.dump(tmp);
        }
        else {
            tmp=tmp.concat(Label);
            vm.dump(tmp);
        }
        
        
    }

    /**
     *
     * @return String or NULL if empty
     */
//    public String getArgs() {
//
//        return Label;
//    }
    
    
}
