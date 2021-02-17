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
public class BopCode extends ByteCode{
    
    private String operator;
    
    public void init(java.util.ArrayList args){
        
        operator = (String)args.get(0);
                
        
    }
    /**
     * 
     * @param vm
     */
    public void execute(VirtualMachine vm){
        int topval= vm.pop(); //uper leve is the second operand
        int lowerval= vm.pop();
        Integer result= new Integer(0);
        
        if(operator.equals("+")){
            result= lowerval + topval;
            
        }
        else if(operator.equals("-")){
            result= lowerval - topval;
        }
        else if(operator.equals("/")){
            result= lowerval / topval;
        }
        else if(operator.equals("*")){
            result = lowerval * topval;
        }
        else if(operator.equals("==")){
            if(lowerval == topval)
                result= 1;
            else
                result= 0;
        }
        else if(operator.equals("!=")){
            if(lowerval == topval)
                result= 0;
            else
                result= 1;
         }
        else if(operator.equals("<=")){
            if(lowerval <= topval)
                result= 1;
            else
                result= 0;
        }
        else if(operator.equals("<")){
            if(lowerval < topval)
                result= 1;
            else
                result= 0;
        }
        else if(operator.equals(">=")){
            if(lowerval >= topval)
                result= 1;
            else
                result= 0;
        }
        else if(operator.equals(">")){
            if(lowerval > topval)
                result= 1;
            else
                result= 0;     
        }
        else if(operator.equals("|")){
            if ((lowerval !=0) || (topval !=0))
                result =1;
            else
                result= 0;
        }
        else if(operator.equals("&")){
            if((lowerval != 0)&&(topval != 0))
                result =1;
            else
                result = 0;
        }
        
        vm.push(result);
        
        String tmp= "BOP "; tmp= tmp.concat(operator); 
        vm.dump(tmp);
    }
    
}

