/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.Stack;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author senatori
 */
public class RunTimeStack {

    private Stack<Integer> framePointers;
    private Vector<Integer> runStack;

    public RunTimeStack() {

        runStack = new Vector<Integer>();

        framePointers = new Stack<Integer>();
        framePointers.add(0);

    }

    public Vector<Integer> giveMeTheArgs() {
        Vector<Integer> numOfArgs = new Vector<Integer>();
        Integer bench = framePointers.peek();
        Integer ceiling = (runStack.size()) - 1;
        if ((ceiling - bench) >= 0) {  //if 0 or more then there are 1 or more args passed to stack, if less, then its void
            for (; bench <= ceiling; bench++) {
                Integer tmp = runStack.get(bench);
                numOfArgs.add(tmp);
            }
        }

        return numOfArgs;
    }

    /**
     * Dumps the RunTimeStack information for debugging
     * 
     */
    public void dump() {
        if ((framePointers.size()) == 1) {
            System.out.println(runStack.toString());
        } else {
            Iterator<Integer> stackIterator = runStack.iterator();
            Iterator<Integer> frameIterator = framePointers.iterator();
            
            Integer stackSize= (runStack.size()-1);//-1
            Integer frameSize= (framePointers.size())-1;
            System.out.print("["); int framecount=0; int stackcount=1;

            Integer fStop = frameIterator.next();
            fStop = frameIterator.next();
            while (stackIterator.hasNext()) {

                Integer stacky = stackIterator.next();
                if((stackIterator.hasNext())&& (stackcount!=fStop))
                    System.out.print(stacky.toString() + ",");
                else if((stackIterator.hasNext())&& (stackcount==fStop))
                    System.out.print(stacky.toString());
                
                if (stackcount== fStop) {
                    System.out.print("] [");
                    if(frameIterator.hasNext())
                        fStop= frameIterator.next();
                    
                    
                }


                
                stackcount++;
            }
            System.out.print("]"); System.out.println();
        }
    }

    /**
     * 
     * @return the top item on the runtime stack 
     */
    public int peek() {

        Integer i = runStack.lastElement();

        return i.intValue();

    }
    
     public int getOffsetOfLastItem(){
    
        int size= runStack.size();
        size--;
        int frameval= framePointers.peek();
        
        int offset= size - frameval;
                
        return offset;
    }
    
    public int getValue(int offset){
        int rOffset= (framePointers.peek()) + offset;
        int value= runStack.get(rOffset);
        
        return value;
    }
    public void setValue(int offset, int newVal){
        int rOffset = (framePointers.peek()) + offset;
        runStack.remove(rOffset);
        runStack.add(rOffset, newVal);
     
    }

    /**
     * Pops the top item from the runtime stack
     * 
     * @return the top item on the runtime stack
     */
    public int pop() {

        Integer i = runStack.lastElement();
        int n = runStack.size();
        --n;
        runStack.remove(n);

        return i.intValue();

    }

    /**
     * Used by LIT
     * 
     * @param n push this item on the runtime stack
     * @return item just pushed (um, why?)
     */
    public int push(int n) {

        Integer i = new Integer(n);
        //runStack.add(n);
        runStack.add(i);

        return (runStack.lastElement()).intValue();

    }

    /**
     * Starts a new frame
     * <P>
     * offset will be provided by ArgsCode...I think 
     * @param offset the number of slots down from the top of RunTimeStack for starting the new frame 
     */
    public void newFrameAt(int offset) {

        int size = runStack.size();   //CAUTION SIZE != to INDEX!!!!!!!
        framePointers.add(size - offset);  //I think offset will be provided by ArgsCode

    }

    /**
     * Pops the top frame but retains the top value of the frame(the RETURN VALUE) and pushes it
     * on to the top available frame
     */
    public void popFrame() {

        int topindex = (runStack.size()) - 1;
        Integer returnVal = runStack.lastElement();
        Integer bottom = framePointers.pop();
        for (; topindex >= bottom; topindex--) {

            runStack.removeElementAt(topindex);

        }

        runStack.add(returnVal);

    }

    /**
     * Pops the top of the stack; stores value into variables at index FrameStart + offset
     * Overwrites elements at that index;
     * 
     * @param offset  int from the start of the frame
     * @return 
     */
    public int store(int offset) {

        Integer topVal = pop();
        offset += framePointers.peek(); //real offset, taking current frame pointer into account
        //topVal= runStack.set(offset, topVal); 
        runStack.setElementAt(topVal, offset);

        return runStack.get(offset);
    }

    /**
     * Takes value at index FrameStart+offset and places a copy at the top of the runtime stack
     * @param offset int offset from start of the frame
     * @return 
     */
    public int load(int offset) {

        offset += framePointers.peek(); //real offset, taking current frame into account
        Integer litVal = runStack.get(offset);
        runStack.add(litVal);

        return runStack.lastElement();
    }

    /**
     * Used by LIT
     * 
     * @param i push this item on the runtime stack
     * @return item just pushed (um, why?)
     */
    public Integer push(Integer i) {

        runStack.add(i);
        return runStack.lastElement();
    }
    
    public int runstackSize(){
        return runStack.size();
    }
}
