/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debugger;

import java.util.Iterator;
import java.util.Set;

/** <pre>
 *  Binder objects group 3 fields
 *  1. a value
 *  2. the next link in the chain of symbols in the current scope
 *  3. the next link of a previous Binder for the same identifier
 *     in a previous scope
 *  </pre>
 */
class Binder {

    private Object value;
    private String prevtop;   // prior symbol in same scope
    private Binder tail;      // prior binder for same symbol
    // restore this when closing scope

    Binder(Object v, String p, Binder t) {
        value = v;
        prevtop = p;
        tail = t;
    }

    Object getValue() {
        return value;
    }

    String getPrevtop() {
        return prevtop;
    }

    Binder getTail() {
        return tail;
    }
}


class Table {

    private java.util.HashMap<String, Binder> symbols = new java.util.HashMap<String, Binder>();
    private static String top;    // reference to last symbol added to
    // current scope; this essentially is the
    // start of a linked list of symbols in scope
    private static Binder marks;  // scope mark; essentially we have a stack of
    // marks - push for new scope; pop when closing
    // scope

//    public static void main(String args[]) {
//        String s = "a",
//                s1 = "b",
//                s2 = "c";
//
//        Table t = new Table();
//        t.beginScope();
//        t.put(s, 1);
//        t.put(s1, 2);
//        t.beginScope();
//        t.put(s2, 3);
//        t.put(s, 4);
//        t.endScope();
//        t.put(s2, 69);
//        t.endScope();
//    }
    public Table() {
    }

    /**
     * Gets the object associated with the specified symbol in the Table.
     */
    public Object get(String key) {
        Binder e = symbols.get(key);
        return e.getValue();

    }

    /**
     * Puts the specified value into the Table, bound to the specified Symbol.<br>
     * Maintain the list of symbols in the current scope (top);<br>
     * Add to list of symbols in prior scope with the same string identifier
     */
    public void put(String key, Object value) {
        Binder tmpBinder= new Binder(value, top, symbols.get(key));
        symbols.put(key, tmpBinder);
        top = key;
        //System.out.println("put called " + top);
    }

    /**
     * Remembers the current state of the Table; push new mark on mark stack
     */
    public void beginScope() {
        marks = new Binder(null, top, marks);
        top = null;
    }

    /**
     * Restores the table to what it was at the most recent beginScope
     *	that has not already been ended.
     */
    public void endScope() {
        while (top != null) {
            Binder e = symbols.get(top);
            if (e.getTail() != null) {
                symbols.put(top, e.getTail());
            } else {
                symbols.remove(top);
            }
            top = e.getPrevtop();
        }
        top = marks.getPrevtop();//since weve deleted stuff on the scope, set top to the top of the previous scope
        marks = marks.getTail();
    }

    /**
     * Poping implementation(more of a delete, really)<P>
     * @param i number of vars on "stack" that you're going to pop (indiscriminate of scope)
     */
    public void Pop(int i) {
        int k = 0;// in case we are deleting more vars then in the current scope
        boolean overflowFlag = false;
  //      System.out.println(top);
        for (int n = 1; n <= i; n++) {
            Binder e = symbols.get(top);
            if (e.getTail() != null) {
                symbols.put(top, e.getTail()); //if another instance of same var(via linked list) replace top of hash
            } else {
                symbols.remove(top);          //else remove entire key from table
            }
            top = e.getPrevtop();  //set top to the last id/symbol that was entered into hash, regardless if same type or not
            if (top == null) {  //if we got to the end of the scope but still have more iterations to go
                k = n;          //we will stop iterationg through the loop and resume where we left off via recursion
                overflowFlag = true;
                break;
            }

        }

        if (overflowFlag) {
            top = marks.getPrevtop();  //since weve deleted stuff on the scope, set top to the last id of the previous scope
            marks = marks.getTail();   //set marks to the previous scope's previoius scope...BLEW MY MIND!!
            //Pop(k);   //continue where we left off in the for loop in the next scope  //RECURSION

        }
    }

    /**
     * @return a set of the Table's symbols.
     */
    public java.util.Set<String> keys() {
        return symbols.keySet();
    }
}

public class FunctionEnvironmentRecord {

    Table table;
    Integer start, end, currentLine = 0;

    
    String name;
    private String prevtop;   // prior symbol in same scope
    private Binder tail;      // prior binder for same symbol
    // restore this when closing scope

    public static void main(String args[]) {
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();
        fctEnvRecord.beginScope();
        fctEnvRecord.Dump();

        fctEnvRecord.setFuncNameStartEnd("g", 1, 20);
        fctEnvRecord.Dump();
        fctEnvRecord.setLine(5);
        fctEnvRecord.Dump();
        fctEnvRecord.setVarVal("a", 4);
        fctEnvRecord.Dump();
        fctEnvRecord.setVarVal("b", 2);
        fctEnvRecord.Dump();
        fctEnvRecord.setVarVal("c", 7);
        fctEnvRecord.Dump();
        fctEnvRecord.setVarVal("a", 1);
        fctEnvRecord.Dump();
        fctEnvRecord.Pop(2);
        fctEnvRecord.Dump();
        fctEnvRecord.Pop(1);
        fctEnvRecord.Dump();


    }

    FunctionEnvironmentRecord() {
        table = new Table();
        name = new String();  //so it dowsn't throw an exeption when calling dump right after beginScope();

    }

    void setFuncNameStartEnd(String nombre, Integer start, Integer end) {
        name = nombre;
        this.start = start;
        this.end = end;
    }

    void setLine(Integer n) {
        currentLine = n;
    }

    void setVarVal(String id, Integer literal) {
        table.put(id, literal);

    }

    void beginScope() {
        table.beginScope();
    }

    void endScope() {
        table.endScope();
    }

    void Pop(Integer n) {
        table.Pop(n);
    }

    void Dump() {
        String output = "(";
        String temp;
        String tableString = "<";
        Integer tempVal;

        java.util.Set<String> tableSet = table.keys();
        int size = tableSet.size();
        Iterator iterator = tableSet.iterator();
        while (iterator.hasNext()) {
            temp = (String) (iterator.next());
            tempVal = (Integer) table.get(temp);

            if (iterator.hasNext()) {
                tableString = tableString.concat(temp + "/" + tempVal.toString() + ",");
            } else {
                tableString = tableString.concat(temp + "/" + tempVal.toString());
            }


        }

        output = output.concat(tableString + ">,");
        if (name.isEmpty()) {
            temp = "-,-,-,-";
            output = output.concat(temp);
        } else if (currentLine == 0) {
            output = output.concat(name + "," + start.toString() + "," + end.toString() + "," + "-");
        } else {
            output = output.concat(name + "," + start.toString() + "," + end.toString() + "," + currentLine);
        }

        output = output.concat(")");


        System.out.println(output);
    }
    Set<String> getKeySet(){
        return table.keys();
    }
    Integer getOffsetValue(String key){
        Integer x= (Integer)(table.get(key));
        return x;
    }
    public Integer getCurrentLine() {
        return currentLine;
    }

    public Integer getEndLine() {
        return end;
    }

    public String getFuncitonName() {
        return name;
    }

    public Integer getStartLine() {
        return start;
    }
    
    //Object getValue() { return value; }
}
