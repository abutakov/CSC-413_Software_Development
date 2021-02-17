/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecode;

/**
 *
 * @author senatori
 */
public interface AddressResolution {
    
    public String getArgs();
    
    public void setAddress(int index);
    
    public int getAddress(); 
    
    
}
