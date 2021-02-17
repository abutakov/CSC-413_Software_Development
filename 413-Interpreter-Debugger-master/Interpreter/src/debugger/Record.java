/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debugger;


import java.util.Vector;




/**
 *
 * @author senatori
 */
public class Record {
    private Integer pcCopy;
    private Integer runstackSize;
    private Vector<VarPod> podStack;
    
    public Record(int pc, int runSize){
        this.pcCopy= pc;
        this.runstackSize= runSize;
        podStack= new Vector<VarPod>();
    }

    /**
     * Pops a VarPod off of a stack of pods in Record class & returns instance of VarPod
     * @return 
     */
//    public VarPod PopPod() {
//        return podStack.pop();
//    }
    public void PushPod(VarPod pod){
        podStack.add(pod);
        //pcCopy++;
        
    }

//    public void setFormalStack(Vector<VarPod> formalStack) {
//        this.formalStack = formalStack;
//    }

    public Integer getPc() {
        return pcCopy;
    }

    public void setPc(Integer pc) {
        this.pcCopy = pc;
    }

    public Integer getRunstackSize() {
        return runstackSize;
    }

    public void setRunstackSize(Integer runstackSize) {
        this.runstackSize = runstackSize;
    }
    /**
     * Returns pod stack, which is a Vector, actually
     * @return podStack
     */
    public Vector<VarPod> getPodStack(){
        return podStack;
    }
    
    
    
    
}
