package visitor;

import ast.*;

/**
 *  ASTVisitor class is the root of the Visitor hierarchy for visiting
 *  various AST's; each visitor asks each node in the AST it is given
 *  to <i>accept</i> its visit; <br>
 *  each subclass <b>must</b> provide all of the visitors mentioned
 *  in this class; <br>
 *  after visiting a tree the visitor can return any Object of interest<br>
 *  e.g. when the constrainer visits an expression tree it will return
 *  a reference to the type tree representing the type of the expression
*/
public abstract class ASTVisitor {

    public void visitKids(ASTree t) {
        for (ASTree kid : t.getKids()) {
            kid.accept(this);
        }
        return;
    }

    
    public abstract Object visitIntTree(ASTree t);
    public abstract Object visitAddOpTree(ASTree t);
    public abstract Object visitMultOpTree(ASTree t);
}