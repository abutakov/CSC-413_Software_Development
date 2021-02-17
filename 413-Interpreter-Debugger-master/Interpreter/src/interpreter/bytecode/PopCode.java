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
public class PopCode extends ByteCode {
    protected int levels2pop;
    
    
     public void init(java.util.ArrayList args){
         
         levels2pop= Integer.parseInt(((String)args.get(0)));
         
    }
    public void execute(VirtualMachine vm){
        
        for(int i=1; i <= levels2pop; i++){
            vm.pop();
        }
        
        //-------dumping
        String tmp= "POP "; tmp= tmp.concat(((Integer)levels2pop).toString()); 
        
        vm.dump(tmp);
    }
    
}
