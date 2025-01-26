// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class ClassBody implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassLocalVarDeclarationLists ClassLocalVarDeclarationLists;
    private ClassMethodDeclarations ClassMethodDeclarations;

    public ClassBody (ClassLocalVarDeclarationLists ClassLocalVarDeclarationLists, ClassMethodDeclarations ClassMethodDeclarations) {
        this.ClassLocalVarDeclarationLists=ClassLocalVarDeclarationLists;
        if(ClassLocalVarDeclarationLists!=null) ClassLocalVarDeclarationLists.setParent(this);
        this.ClassMethodDeclarations=ClassMethodDeclarations;
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.setParent(this);
    }

    public ClassLocalVarDeclarationLists getClassLocalVarDeclarationLists() {
        return ClassLocalVarDeclarationLists;
    }

    public void setClassLocalVarDeclarationLists(ClassLocalVarDeclarationLists ClassLocalVarDeclarationLists) {
        this.ClassLocalVarDeclarationLists=ClassLocalVarDeclarationLists;
    }

    public ClassMethodDeclarations getClassMethodDeclarations() {
        return ClassMethodDeclarations;
    }

    public void setClassMethodDeclarations(ClassMethodDeclarations ClassMethodDeclarations) {
        this.ClassMethodDeclarations=ClassMethodDeclarations;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassLocalVarDeclarationLists!=null) ClassLocalVarDeclarationLists.accept(visitor);
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassLocalVarDeclarationLists!=null) ClassLocalVarDeclarationLists.traverseTopDown(visitor);
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassLocalVarDeclarationLists!=null) ClassLocalVarDeclarationLists.traverseBottomUp(visitor);
        if(ClassMethodDeclarations!=null) ClassMethodDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBody(\n");

        if(ClassLocalVarDeclarationLists!=null)
            buffer.append(ClassLocalVarDeclarationLists.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethodDeclarations!=null)
            buffer.append(ClassMethodDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBody]");
        return buffer.toString();
    }
}
