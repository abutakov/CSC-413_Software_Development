/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author senatori
 */
public class CallCode extends ByteCode implements AddressResolution {

    protected String Label;
    protected int labelAddress;

    public void init(java.util.ArrayList args) {

        Label = (String) args.get(0);

    }

    public void execute(VirtualMachine vm) {
        vm.saveReturnAddrs();
        vm.setProgramCounter(labelAddress); //change iteration to function/label.

        //-----for dumping-------------------------------------------------
        String tmp = "CALL ";
        tmp= tmp.concat(Label);
        Vector<Integer> funcArgs = vm.giveMeTheArgs();
        if (funcArgs.isEmpty()) {

            tmp=tmp.concat("   f( )");
        } else {

            for (int n = 0; n < (funcArgs.size()); n++) {
                
                if (n == 0) {
                    Integer i = funcArgs.get(0);
                    tmp=tmp.concat("    f(" + i.toString());
                }
                else{
                
                    Integer i= funcArgs.get(n);
                    tmp=tmp.concat(", "+ n); 
                }
                
                
            }
            tmp= tmp.concat(")");
        }


        vm.dump(tmp);
        //--------------------------------------------------------------------
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
