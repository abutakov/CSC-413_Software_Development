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
public class FalseBranchCode extends ByteCode implements AddressResolution {

    private String Label;
    int labelAddress;

    public void init(java.util.ArrayList args) {
        Label = (String) args.get(0);

    }

    public void execute(VirtualMachine vm) {

        int tmp = vm.pop();
        if (tmp == 0) {
            vm.setProgramCounter(labelAddress); //if false(0) then branch to label/address
        }                           //if true, then do nothing and vm will continue iterating through they bytecodes in program...
        
        String dumpString= "FALSEBRANCH "; dumpString= dumpString.concat(Label); 
        vm.dump(dumpString);

    }

    public String getArgs() {
        return Label;

    }

    public void setAddress(int index) {

        labelAddress = index;

    }

    public int getAddress() {
        return labelAddress;
    }
}
