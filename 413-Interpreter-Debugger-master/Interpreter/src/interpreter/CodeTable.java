/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author senatori
 */
public class CodeTable {
    
    private static java.util.HashMap<String,String> hash= new java.util.HashMap<String, String>();
    
    public static String get (String code){
    
       return hash.get(code);
    }
    
    public static void init(){
    
     
    hash.put("HALT", "HaltCode");
    hash.put("POP", "PopCode");
    hash.put("FALSEBRANCH", "FalseBranchCode");
    hash.put("GOTO", "GoToCode");
    hash.put("STORE", "StoreCode");
    hash.put("LOAD", "LoadCode");
    hash.put("LIT", "LitCode");
    hash.put("ARGS", "ArgsCode");
    hash.put("CALL", "CallCode");
    hash.put("RETURN", "ReturnCode");
    hash.put("BOP", "BopCode");
    hash.put("READ", "ReadCode");
    hash.put("WRITE", "WriteCode");
    hash.put("LABEL", "LabelCode");
    hash.put("DUMP", "DumpCode");
    
    hash.put("LINE", "LineCode");
    hash.put("FUNCTION", "FunctionCode");
    hash.put("FORMAL", "FormalCode");
    
        
    }
    
    public static void initDebug(){  //if in debug mode, initialize this method to so we can later instatiate
                                     //via reflection the proper debugger ByteCodes
    
        hash.put("CALL", "DebuggerCallCode");
        hash.put("POP", "DebuggerPopCode");
        hash.put("RETURN", "DebuggerReturnCode");
        hash.put("LIT", "DebuggerLitCode");
        
    }
}
