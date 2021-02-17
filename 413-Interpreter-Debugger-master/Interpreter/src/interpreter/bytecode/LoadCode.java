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
public class LoadCode extends ByteCode{
    
    private int offset; private String id;
    
     public void init(java.util.ArrayList args){
         offset= Integer.parseInt(((String)args.get(0)));
         id= (String)args.get(1);
    }
    public void execute(VirtualMachine vm){
        
        vm.load(offset);
        
        //-----dumping
        
        String tmp= "LOAD "; tmp= tmp.concat(((Integer)offset).toString()); 
        if( (id.isEmpty())==false ){
            
            tmp=tmp.concat(" "+ id+ "   int"+ id +"   <load "+ id+ ">");
        }
        vm.dump(tmp);
    }
    
}
