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
public class StoreCode extends ByteCode{
    
  
    private int literal; private String id;
    
     public void init(java.util.ArrayList args){
         literal = Integer.parseInt(((String)args.get(0)));
         id= (String)args.get(1);
         
         
         
    }
    public void execute(VirtualMachine vm){
        
        Integer storeVar= vm.peek();
        vm.store(literal);
        
        //----------------dumping
        
        String tmp= "STORE "; Integer i= literal;
        tmp= tmp.concat(i.toString() + " "+ id+ "  "+id+" = "+ storeVar);
        vm.dump(tmp);
    }
    
}
