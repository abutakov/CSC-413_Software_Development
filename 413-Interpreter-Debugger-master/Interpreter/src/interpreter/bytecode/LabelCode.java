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
public class LabelCode extends ByteCode{
    
    private String Label;
    
    public void init(java.util.ArrayList args){
        Label= (String)args.get(0);
    }
    public void execute(VirtualMachine vm){
        String dumpString= "LABEL "; dumpString= dumpString.concat(Label); 
        vm.dump(dumpString);
    }
    
    public String getArgs(){
    
        return Label;
    
    }
    
}
