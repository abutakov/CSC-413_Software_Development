/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import debugger.DebuggerVM;
import interpreter.VirtualMachine;

/**
 *
 * @author senatori
 */
public class DebuggerLitCode extends LitCode {

    public void execute(VirtualMachine vm) {
        super.execute(vm);

        DebuggerVM tmp = (DebuggerVM) vm;
        if (!(id.isEmpty())) {
            int offsetval= tmp.getOffsetOfLastItem();
            tmp.setVar(id, offsetval);
        }


    }
}
