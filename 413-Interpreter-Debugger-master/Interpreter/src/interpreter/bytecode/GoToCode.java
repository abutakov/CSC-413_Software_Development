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
public class GoToCode extends ByteCode implements AddressResolution{

    private String Label; int labelAddress;

    public void init(java.util.ArrayList args) {

        Label = (String) args.get(0);
    }

    public void execute(VirtualMachine vm) {
        
        vm.setProgramCounter(labelAddress);
        
        String dumpString= "GOTO "; dumpString= dumpString.concat(Label); 
        vm.dump(dumpString);
    }

    public String toString() {

        return Label;
    }

    public String getArgs() {
        
        return Label;
    }
    
    public void setAddress(int index){
        
        labelAddress= index;
    
    }
    
    public int getAddress(){
        return labelAddress;
    }
}
