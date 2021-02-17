/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

import interpreter.VirtualMachine;
import javax.swing.JOptionPane;

/**
 *
 * @author senatori
 */
public class ReadCode extends ByteCode{
    
     public void init(java.util.ArrayList args){
         
    }
    public void execute(VirtualMachine vm){
        String readStr = JOptionPane.showInputDialog("Enter an integer:");
        Integer i= new Integer(0);
        i=i.parseInt(readStr);
        
        vm.push(i);
        
        String tmp= "READ "; 
        vm.dump(tmp);
    }
    
    
}
