/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.Stack;
import java.util.Vector;
import interpreter.bytecode.*;

/**
 *
 * @author senatori
 */
public class VirtualMachine {

    protected RunTimeStack runStack;
    protected int pc=0;
    protected Stack<Integer> returnAddrs;  //OJO, store return address FOR CALLcode!
    protected boolean isRunning;
    protected boolean dumping; //dumpstatus bool
    protected Program program;
    
    public VirtualMachine(Program program){
    
        this.program= program;
        runStack = new RunTimeStack();
        returnAddrs = new Stack<Integer>();
    }

    public void executeProgram() {
        //pc = 0;
        //runStack = new RunTimeStack();
        //returnAddrs = new Stack<Integer>();
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);  //this method passes in this instance of VirtualMachine to bytecodes...
            //runStack.dump();     
            pc++;
            
       
            //if(pc==53)
            //System.out.print("reachedPC Mark");

        }
        
    }
    /**
     * Sets program counter pc 
     * used by GOTO,  falsebranch etc <p> CAUTION, if called in CALLcode, remember to save where left off in returnAddrs stack! 
     * @param i 
     */
    public void setProgramCounter(int i){
    
        pc= i;
    }
    
    public Integer popReturnAddrs(){
        return returnAddrs.pop();
    }
    public Vector<Integer> giveMeTheArgs(){
        
        return runStack.giveMeTheArgs();
    }
    
    /**
     * Dumps the RunTimeStack information for debugging
     * 
     */
    public void dump(String labelArgs) {
        
        if(dumping){
        System.out.println(labelArgs);
        runStack.dump();
        }
        
    }
    
    public boolean isDumping() {
        return dumping;
    }

    public void setDumping(boolean dumping) {
        this.dumping = dumping;
    }
    
    /**
     * Stores your last position into the returnAddrs stack
     * 
     */
    public void saveReturnAddrs(){
        returnAddrs.push(pc);
    }
    
    /**
     * 
     * @return the top item on the runtime stack 
     */
    public int peek() {
        
        return runStack.peek();
    }

    /**
     * Pops the top item from the runtime stack
     * 
     * @return the top item on the runtime stack
     */
    public int pop() {

        return runStack.pop();

    }
    
    /**
     * Used by LIT
     * 
     * @param n push this item on the runtime stack
     * @return item just pushed (um, why?)
     */
    public int push(int n) {

        return runStack.push(n);
    }
    
    /**
     * Used by LIT
     * 
     * @param i push this item on the runtime stack
     * @return item just pushed (um, why?)
     */
    public Integer push(Integer i) {

        return runStack.push(i);
    }

    /**
     * Starts a new frame
     * <P>
     * offset will be provided by ArgsCode...I think 
     * @param offset the number of slots down from the top of RunTimeStack for starting the new frame 
     */
    public void newFrameAt(int offset) {

        runStack.newFrameAt(offset);

    }

    /**
     * Pops the top frame but retains the top value of the frame(the RETURN VALUE) and pushes it
     * on to the top available frame
     */
    public void popFrame() {

       runStack.popFrame();

    }

    /**
     * Pops the top of the stack; stores value into variables at index FrameStart + offset
     * Overwrites elements at that index;
     * 
     * @param offset  int from the start of the frame
     * @return 
     */
    public int store(int offset) {

        
        return runStack.store(offset);
    }

    /**
     * Takes value at index FrameStart+offset and places a copy at the top of the runtime stack
     * @param offset int offset from start of the frame
     * @return 
     */
    public int load(int offset) {

        return runStack.load(offset);
    }
    
    public void stopVM(){
    
        isRunning= false;
    }
    
    
}
