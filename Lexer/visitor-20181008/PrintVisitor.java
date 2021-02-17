package visitor;

import ast.*;

/**
 *  PrintVisitor is used to visit an AST and print it using
 *  appropriate indentation:<br>
 *  <pre>
 *  1. root
 *  2.   Kid1
 *  3.   Kid2
 *  4.     Kid21
 *  5.     Kid22
 *  6.     Kid23
 *  7.   Kid3
 *  </pre>
*/
public class PrintVisitor extends ASTVisitor {
    private int indent = 0;

    private void printSpaces(int num) {
        String s = "";
        for (int i = 0; i < num; i++) {
            s += ' ';
        }
        System.out.print(s);
    }

/**
 *  Print the tree
 *  @param s is the String for the root of t
 *  @param t is the tree to print - print the information
 *  in the node at the root (e.g. decoration) and its kids
 *  indented appropriately
*/
    public void print(String s,ASTree t) {
        // assume less than 1000 nodes; no problem for csc 413
        int num = t.getNodeNum();
        String spaces = "";
        if (num < 100) spaces += " ";
        if (num < 10) spaces += " ";
        System.out.print(num + ":" + spaces);
        printSpaces(indent);
        System.out.println(s);
        indent += 2;
        visitKids(t);
        indent -= 2;
    }

    
    public Object visitIntTree(ASTree t) { print("Int: "+((IntTree)t).getSymbol().toString(),t);  return null; }
    public Object visitAddOpTree(ASTree t) { print("AddOp: "+((AddOpTree)t).getSymbol().toString(),t);  return null; }
    public Object visitMultOpTree(ASTree t) { print("MultOp: "+((MultOpTree)t).getSymbol().toString(),t);  return null; }
}