/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debugger;

import interpreter.Program;
import interpreter.bytecode.*;
import java.io.*;
import java.util.*;
import java.io.InputStreamReader;

/**
 *
 * @author senatori
 */
public class UI {

    private DebuggerVM dVM;
    private String codeFile;
    private Vector<SourceCodeContainer> sourceCodeVector;
    private BufferedReader in;
    private boolean kill= false;

    public UI(String codeFile, Program program) throws IOException {
        String nextLine;
        sourceCodeVector = new Vector<SourceCodeContainer>();
        this.dVM = new DebuggerVM(program, sourceCodeVector);
             
        in = new BufferedReader(new FileReader(codeFile));
        nextLine = in.readLine(); //priming the while loop

        //loop to populate the Source Code Vector
        while (nextLine != null) {

            SourceCodeContainer temp = new SourceCodeContainer(nextLine);
            sourceCodeVector.add(temp);
            nextLine = in.readLine();

        }
    }

    public void PrintSourceCode() {
        int size = sourceCodeVector.size();
        String sourceView;
        for (int i = 0; i < size; i++) {
            SourceCodeContainer temp = sourceCodeVector.get(i);
            if (temp.isBreakptSet) {
                sourceView = "*";
            } else {
                sourceView = Integer.toString(i + 1);
            }

            sourceView = sourceView.concat(" " + temp.getSourceLine());

            System.out.println(sourceView);
        }

    }

    public void execute() {
        boolean continueReading = true;
        boolean isRunning= true;
        PrintSourceCode();
        
        //int n = 0;
        //int end = sourceCodeVector.size();
        //System.out.println("In Debugger Mode. Type ? for help");
        while (isRunning) {
            continueReading= true;// flag to continue recieving user input
            while (continueReading) {
                if(dVM.getHaltFlag()){
                    isRunning= false;
                    continueReading= false;
                    kill= true;
                    break;
                }
                System.out.println("Type ? for help");
                System.out.print(">:");
                continueReading = readInput();
                
            }
            if(kill){//if user wishes to quit debugger
                break;
            }
            
            dVM.executeProgram();
            //this.PrintFunctionCode(); //when continue hits breakpoint, print funciton
            
//            if(dVM.getHaltFlag()){
//                isRunning= false;// if dVM hit halt flag, set UI loop to false. End.
//            }
            if(!(dVM.getHaltFlag())){ //prints after resuming execution/continue
                PrintFunctionCode();
            }
            
            
            
        }





    }

    public boolean readInput() {
    
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line = in.readLine();
            StringTokenizer st = new StringTokenizer(line);
            String sToken = st.nextToken();
            if (sToken.equals("?")) {
                Help();
                return true;
            } else if (sToken.equals("s")) {
                if(st.hasMoreTokens()){
                    System.out.print("Breakpoints set @:");
                }
                while (st.hasMoreTokens()) {
                    int linenum = Integer.parseInt(st.nextToken());
                    if (linenum < sourceCodeVector.size()) {
                        SourceCodeContainer temp = sourceCodeVector.get(linenum - 1);
                        String sTemp = temp.getSourceLine();
                        //conditional state to check if we cans set a breakpoint here
                        if ((sTemp.contains("{")) || (sTemp.contains("boolean")) || (sTemp.contains("int"))
                                || (sTemp.contains("if")) || (sTemp.contains("while"))
                                || (sTemp.contains("return")) || (sTemp.contains("="))) {
                            
                            temp.setBreakPointTrue();
                            System.out.print(" "+ Integer.toString(linenum)); //breakpoint set at...
                        }
                    }
                }
                
                System.out.println();
                return true;
            }
            else if(sToken.equals("cb")){ //clear breakpoints
                if(st.hasMoreTokens()){
                    System.out.print("Breakpoints removed @:");
                }
                while (st.hasMoreTokens()) {
                    int linenum = Integer.parseInt(st.nextToken());
                    if (linenum < sourceCodeVector.size()) {
                        SourceCodeContainer temp = sourceCodeVector.get(linenum - 1);
                        temp.setBreakPointFalse();
                        System.out.print(" "+ Integer.toString(linenum)); //breakpoint set at...
                        
                    }
                    
                }
                System.out.println();
                return true;
            
            }
            else if(sToken.equals("cv")){
                String scopeKey; Integer newVal;
                if(st.hasMoreTokens()){
                    
                    scopeKey= st.nextToken();
                    
                    if(st.hasMoreTokens()){
                        newVal = Integer.parseInt(st.nextToken());
                        if(!(dVM.setValue(scopeKey, newVal))){
                           System.out.println("Variable does not exist in this scope");
                        }
                    }
                    else{
                        System.out.println("Invalid Input");
                    }
                }
                else{
                    System.out.println("Invalid Input");
                }
                
                return true;
            }
            else if(sToken.equals("v")){
                dVM.displayScopeValues();
                return true;
            }
            else if(sToken.equals("c")){
                dVM.setWhileLoop2True(); //resumming dVM Loop
                return false;
            }
            else if(sToken.equals("d")){
                this.PrintSourceCode();
                return true;
            }
            else if(sToken.equals("df")){
                this.PrintFunctionCode();
                return true;
            }
            else if(sToken.equals("r")){
                dVM.exeRe_Execute();
                this.PrintFunctionCode();
                return true;
            
            }
            else if(sToken.equals("u")){
                //dVM.executeStepOut(sourceCodeVector);
                dVM.enableStepOutflag();
                dVM.setWhileLoop2True();
                dVM.executeProgram();
                this.PrintFunctionCode();
                return true;
            }
            else if(sToken.equals("i")){
                dVM.enableStepInflag();
                dVM.setWhileLoop2True();
                dVM.executeProgram();
                this.PrintFunctionCode();
                return true;
            }
            else if(sToken.equals("o")){
                dVM.enableStepOverflag();
                dVM.setWhileLoop2True();
                dVM.executeProgram();
                this.PrintFunctionCode();
                return true;
            }
            else if(sToken.equals("q")){
                kill= true;
                return false;
            }

            return false;
        } catch (java.io.IOException ex) {
            System.out.println("Input Error. Lets Try that again");
            return false;
        }
    }

    public void Help() {
        System.out.println("s to set break points i.e. s 1 4 12 25");
        System.out.println("c to continue");
        System.out.println("cb to clear break points i.e. cb 1 4 12");
        System.out.println("v to display values");
        System.out.println("cv to change a value i.e. cv i 4");
        System.out.println("d to display entire program");
        System.out.println("df to display function");
        System.out.println("u to step out");
        System.out.println("i to step in");
        System.out.println("o to step over");
        System.out.println("r to re-execute");
        System.out.println("q to quit");
    }
    
    public void PrintFunctionCode() {
        int size = sourceCodeVector.size();
        String sourceView;
        Integer start, end;
        
        start= dVM.getStartLine();
        if(start== null){ //if we are in the beginning of funciton entry
            dVM.setWhileLoop2True();
            dVM.enableFunctionDisplay();
            dVM.executeProgram();
            //dVM.executeDisplayFunction(sourceCodeVector); //iteratre to the FunctionCode
            start = dVM.getStartLine();
        }
        start--; //line num is one more then its respective index value
        end= dVM.getEndLine();
        if (start >= 0) {
            for (; start < end; start++) {
                SourceCodeContainer temp = sourceCodeVector.get(start);
                if (temp.isBreakptSet) {
                    sourceView = "*";
                } else {
                    sourceView = Integer.toString(start + 1);
                }

                sourceView = sourceView.concat(" " + temp.getSourceLine());
                int currentLine = dVM.getCurrentLine();
                if ((start + 1) == currentLine) {
                    sourceView = sourceView.concat(" <===");
                }

                System.out.println(sourceView);
            }
        }

    }
}
