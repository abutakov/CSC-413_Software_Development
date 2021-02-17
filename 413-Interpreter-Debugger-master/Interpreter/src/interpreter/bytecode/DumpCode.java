/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.ArrayList;

/**
 *
 * @author senatori
 */
public class DumpCode extends ByteCode{

    String status;
    @Override
    public void init(ArrayList args) {
        status= (String)(args.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        if(status.equals("ON")){
            vm.setDumping(true);
        }
        else if(status.equals("OFF")){
            vm.setDumping(false);
        }
    }
    
}
