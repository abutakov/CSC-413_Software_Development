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
public class ArgsCode extends ByteCode{
    
    private int numOfArgs; 
    
     public void init(java.util.ArrayList args){
        
         numOfArgs= Integer.parseInt(((String)args.get(0)));
         
         
         //numOfArgs= args.yahoo
         
         
    }
    public void execute(VirtualMachine vm){
        vm.newFrameAt(numOfArgs);
        
        String tmp= "ARGS "; tmp=tmp.concat(((Integer)numOfArgs).toString()); 
        vm.dump(tmp);
    }
    
}
