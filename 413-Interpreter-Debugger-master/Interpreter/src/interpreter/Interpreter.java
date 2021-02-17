//
//
//
package interpreter;

import debugger.DebuggerVM;
import debugger.UI;
import java.io.*;

/**
 * <pre>
 * 
 *  
 *   
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 *     
 *   
 *  
 * </pre>
 */
public class Interpreter {

	ByteCodeLoader bcl; boolean isDebugEnabled; String codeFile;

	public Interpreter(String codeFile) {
		try {
			CodeTable.init();
			bcl = new ByteCodeLoader(codeFile);
		} catch (IOException e) {
			System.out.println("**** " + e);
		}
	}
        
        public Interpreter(String codeFile, boolean d) {
		try {
			isDebugEnabled= d;
                        CodeTable.init();
                        CodeTable.initDebug(); //adds some more bytecodes to hashtable
                        
                        this.codeFile= codeFile.concat(".x");
                        
                        String tmp= this.codeFile.concat(".cod");
                        
			bcl = new ByteCodeLoader(tmp);
		} catch (IOException e) {
			System.out.println("**** " + e);
		}
	}

	void run() {
		Program program = bcl.loadCodes();
		VirtualMachine vm = new VirtualMachine(program);
		vm.executeProgram();
	}
        
        
        void runWithDebugging(){
            try{
            Program program = bcl.loadCodes();
            UI terminalUI= new UI(codeFile, program);
            terminalUI.execute();
            
            }
            catch(Exception e){
                System.out.println("Something went wrong");
            }
        }
        
        

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
			System.exit(1);
		}
                else if(args.length== 1){
               // new Interpreter(args[0]);
		(new Interpreter(args[0])).run();
                }
                //if debugging is enabled...
                else if(args.length == 2){
                    if(args[0].equals("-d")){
                    
                        boolean isDebugEnabled= true;
                        //new Interpreter(args[1], isDebugEnabled);
                        (new Interpreter(args[1], isDebugEnabled)).runWithDebugging();
                        
                        
                    }
                }
                
	}
}

