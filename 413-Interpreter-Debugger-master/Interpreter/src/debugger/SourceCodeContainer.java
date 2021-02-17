/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debugger;

/**
 *
 * @author senatori
 */
public class SourceCodeContainer {
    String sourceLine;
    Boolean isBreakptSet;
    //int lineNum
    
    public SourceCodeContainer(String line){
        isBreakptSet= false;
        sourceLine= line;
        
    }
    
//    public void setSourceLine(String line){
//        sourceLine= line;
//    }
    public void setBreakPointTrue(){
        isBreakptSet= true;
    }
    public void setBreakPointFalse(){
        isBreakptSet= false;
    }
    public String getSourceLine() {
        return sourceLine;
    }
    public boolean getBreakptBoolVal(){
        return isBreakptSet;
    }
    
}
