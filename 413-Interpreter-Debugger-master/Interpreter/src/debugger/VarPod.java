/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debugger;

/**
 *
 * @author senatori
 */
public class VarPod {

    Integer offset;
    Integer value;

    public VarPod(Integer offset, Integer value) {
        this.offset= offset;
        this.value= value;
    }
    
    public Integer getOffset(){
        return offset;
    }
    public Integer getValue(){
        return value;
    }
    
}
