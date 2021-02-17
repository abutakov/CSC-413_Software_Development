/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debugger;

import interpreter.VirtualMachine;
import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.bytecode.*;
import java.util.Iterator;

import java.util.Stack;
import java.util.Vector;
import java.util.Set;

/**
 *
 * @author senatori
 */
public class DebuggerVM extends VirtualMachine {

    protected Stack<FunctionEnvironmentRecord> environmentStack;
    protected Stack<Record> executionStack;
    protected Vector<SourceCodeContainer> sourceCodeVector;
    
    boolean WriteRead = false;
    
    private boolean haltflag= false;
    private boolean functionDisplayflag= false;
    private boolean stepOutflag= false;
    private boolean stepInflag= false;
    private boolean stepOverflag= false;
    private boolean recording= false;
    private boolean logistics= false; //so I can iterate to the formal codes...if there are any


    public boolean isWriteRead() {
        return WriteRead;
    }

    public void setWriteRead(boolean WriteRead) {
        this.WriteRead = WriteRead;
    }

    public DebuggerVM(Program program, Vector<SourceCodeContainer> sourceCodeVector) {
        super(program);
        this.sourceCodeVector= sourceCodeVector;
        
        executionStack= new Stack<Record>();

        environmentStack = new Stack<FunctionEnvironmentRecord>();
        environmentStack.push((new FunctionEnvironmentRecord()));
        beginScope();
        
        this.exePushRecordOnExecutionStack();

    }

    public void executeProgram() {
        int envSize= environmentStack.size();
        
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);  //this method passes in this instance of VirtualMachine to bytecodes...
            //runStack.dump();
            //this.Dump();
            pc++;
            
            //checks if breakpoint is set, if true, sets loop to false state
            //and exits loop. Resumes when executeProgram is called again(in this case by UI class)
            if ((code instanceof LineCode)) {
               Integer lineNum= ((LineCode)code).getN(); //get linenum from bytecode
               if(lineNum >= 1){
               SourceCodeContainer sccTemp= sourceCodeVector.get(lineNum -1);
                if(sccTemp.getBreakptBoolVal()){ //if breakpoint value is set in instance of sourceCodeContainer
                    // System.out.println("Breakpoint at Line " + lineNum+ " :");
                    isRunning=false;
                    stepOutflag = false;
                    stepInflag= false;
                    stepOverflag = false;
                    
                }
               }
            }
//            if(code instanceof FunctionCode){
//            
//            }
            
            if (functionDisplayflag) {
                if ((code instanceof FunctionCode)) {

                    isRunning = false;
                    functionDisplayflag= false;
                }
            }
            if (stepOutflag) {

                if ((environmentStack.size()) == (envSize - 1)) //if environmentStack goes down in size
                {                                        //then we've left the execution of the funciton
                    //System.out.println("Step Out");
                    isRunning = false;
                    stepOutflag= false;
                }
                
            }
            if (stepInflag) {
                if (((environmentStack.size()) == (envSize + 1))) {
                    if(code instanceof FunctionCode){
                        logistics= true;
                    }
                    ByteCode nextcode= program.getCode(pc);
                    if (logistics && ((nextcode instanceof LineCode)||(nextcode instanceof ReadCode)||(nextcode instanceof WriteCode))) {
                        //System.out.println("Step In");
                        isRunning = false;
                        stepInflag = false;
                        logistics = false;

                    }
                }
                else if ((code instanceof LineCode)) {
                    Integer lineNum = ((LineCode) code).getN(); //get linenum from bytecode
                    if (lineNum >= 1) { //check to make sure were not in intrinsic function
                        //System.out.println("Step In/over");
                        isRunning = false;
                        stepInflag= false;
                    }
                }
            }
            if (stepOverflag){
                if((environmentStack.size()) == envSize){
                    if((code instanceof LineCode)&& ((((LineCode)code).getN())>= 1)){
                        isRunning = false;
                        stepOverflag = false;                        
                    }
                    
                }
                else if(environmentStack.size() < envSize){
                    isRunning = false;
                    stepOverflag = false;
                }
            }
                    
            //Sets a flag that UI checks to know to stop the UI/debugging loop
            if((code instanceof HaltCode)){
                haltflag= true;
                isRunning = false; //stop VM execution loop
            }
            //if(())


            //if(pc==53)
            //System.out.print("reachedPC Mark");

        }

    }


   //------------------------METHODS FOR UI-------------------------------------
     
    /**
     * Gets the Offset influenced by framepointer of the most recent item on runtimestack
     * @return int offset value
     */
    public int getOffsetOfLastItem() {
        return runStack.getOffsetOfLastItem();
    }
    /**
     * 
     * @param offset the offset value stored in funcitonrecord table
     * @return the actual value of the variable on the respective variable on the runtimestack
     */
    public int getValue(int offset){
    
        return runStack.getValue(offset);
    }
    public boolean setValue(String key, int newVal){
        Integer offset= this.getLocalScopeVarOffset(key);
        if(offset == null){
            return false;
        }
        runStack.setValue(offset, newVal);
        return true;
    }
    
    //public void setValue()
    /**
     * MUST BE CALLED WHEN CALLING EXECUTE AGAIN!
     */
    public void setWhileLoop2True(){
        isRunning= true;
    }
    
    public boolean getHaltFlag(){
        return haltflag;
    }
    
    /**
     * Gets the offset of the local scope of a particular variable in the hashmap of the FunctEnvironRecord
     * @param key the variable name
     * @return the offset value as an Integer
     */
    public Integer getLocalScopeVarOffset(String key) {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        return temp.getOffsetValue(key);
    }
    public Integer getCurrentLine() {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        return temp.getCurrentLine();
    }

    public Integer getEndLine() {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        return temp.getEndLine();
    }
    public Integer getStartLine() {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        return temp.getStartLine();
    }
    public String getFuncitonName() {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        return temp.getFuncitonName();
    }
    
    public boolean isFunctionDisplayflag() {
        return functionDisplayflag;
    }

    public void enableFunctionDisplay() {
        this.functionDisplayflag = true;
    }
    
    public void enableStepOutflag() {
        this.stepOutflag = true;
    }
    public void enableStepInflag(){
        this.stepInflag= true;
    }
    public void enableStepOverflag(){
        this.stepOverflag= true;
    }
    public void enableRecording(){
        this.recording= true;
    }

    //--------------------------------------------------------------------------
    
    public void exePushRecordOnExecutionStack(){
        int runsize= this.SizeOfRuntimeStack();
        
        executionStack.push((new Record(pc, runsize)));
    }
    public void exePopRecordOnExecutionStack(){
        executionStack.pop();
    }
    
    
    /**
     * Pushes a Pod(which contains formal offset & value onto podStack in Record class)
     * @param offset the scope offset. found in the funcenvrecord symtable and in FormalCode field
     */
    public void exePushPod(int offset){
        int value= this.getValue(offset);
        VarPod pod= new VarPod(offset, value);
        Record temp= executionStack.peek();
        temp.PushPod(pod); 
    }
    
    public void exeRe_Execute(){
        Record record= executionStack.peek();
        Vector<VarPod> podStack= record.getPodStack();
        VarPod pod;
        int oldSize= record.getRunstackSize();
        int newSize= this.SizeOfRuntimeStack();
        int limit= newSize - oldSize;
        limit+= podStack.size();
        for(int i=0; i < limit; i++){
            this.pop();
        }
        for (int i=0; i< podStack.size(); i++){
            pod= podStack.get(i);
            //this.runStack.setValue(i, (pod.getValue()));
            this.push((pod.getValue()));
        }
        pc= record.getPc();
        endScope();
        PopRecord();
        exePopRecordOnExecutionStack();
        if (pc == 0) { //if the user dared to inconvenience me and rollback in main
            environmentStack.push((new FunctionEnvironmentRecord()));
            beginScope();
            this.exePushRecordOnExecutionStack();
            this.stepOverflag= true;
            this.isRunning= true;
            this.executeProgram();
        }
        else{  //call step in so the formals are visible
        this.stepInflag= true;
        this.isRunning= true;
        this.executeProgram();
        }
    
    }

    
    /*
     * Creates a new FunctionEnvironmentRecord(empty) and puts it on the stack
     */
    public void PushNewRecord() {

        environmentStack.push((new FunctionEnvironmentRecord()));
        beginScope();
        //Dump();

    }

    /**
     * Deletes record off environmentStack
     */
    public void PopRecord() {
        FunctionEnvironmentRecord temp = environmentStack.pop();
        //temp.endScope();
        //Dump();

    }

    /**
     * Sets the Line number in FunctionEnvironmentRecord
     * @param n Line number
     */
    public void setLine(Integer n) {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        temp.setLine(n);
        //Dump();

    }

    public void setFuncNameStartEnd(String nombre, Integer start, Integer end) {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        temp.setFuncNameStartEnd(nombre, start, end);
        //Dump();
    }

    public void setVar(String id, Integer literal) {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        temp.setVarVal(id, literal);
        //Dump();
    }

    /**
     * Poping implementation(more of a delete, really)<P> EXECUTES Pop in FunctionEnvironmentRecord's table
     * @param i number of vars on "stack" in FunctionEnvironmentRecord that you're going to pop (indiscriminate of scope)
     */
    public void PopInFuncEnvRec(Integer n) {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        temp.Pop(n);
        //Dump();
    }

    public void beginScope() {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        temp.beginScope();
        //Dump();
    }

    /**
     * ****CAUTION****
     */
    public void endScope() {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        temp.endScope();
    }

    public void Dump() {
        FunctionEnvironmentRecord temp = environmentStack.peek();
        temp.Dump();
    }
    public void displayScopeValues(){
        String key; Integer value;;
        FunctionEnvironmentRecord record= environmentStack.peek();
        Set<String> tableSet= record.getKeySet();
        
        Iterator iterator= tableSet.iterator();
        while(iterator.hasNext()){//iterates through tableSet of keys, obtains offset,
            key= (String)(iterator.next());  
            value= record.getOffsetValue(key);
            value= this.getValue(value);
            System.out.println(key + ": "+ value);
            
            
        }
    }
        
    public int SizeOfRuntimeStack() {
        return this.runStack.runstackSize();
    }
    

}