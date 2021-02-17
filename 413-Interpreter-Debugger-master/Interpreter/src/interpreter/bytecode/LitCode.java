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
public class LitCode extends ByteCode{
    
    protected int literal; protected String id;
    
     public void init(java.util.ArrayList args){
         id= "";  //for functionrecord...otherwise i'm poping more stuff then there is
         literal = Integer.parseInt(((String)args.get(0)));
         if(args.size() > 1)
             id= (String)args.get(1);
         
    }
    public void execute(VirtualMachine vm){
        vm.push(literal);
        
        String tmp= "LIT "; tmp= tmp.concat(((Integer)literal).toString()); 
        if(!(id.isEmpty())){//<------ if(!(id.equals("void")))
            
            tmp=tmp.concat(" "+ id+ "   int "+ id);
        }
        vm.dump(tmp);
    }
    
}
