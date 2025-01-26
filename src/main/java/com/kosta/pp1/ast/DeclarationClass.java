// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class DeclarationClass extends Declaration {

    private ClassDeclaration ClassDeclaration;

    public DeclarationClass (ClassDeclaration ClassDeclaration) {
        this.ClassDeclaration=ClassDeclaration;
        if(ClassDeclaration!=null) ClassDeclaration.setParent(this);
    }

    public ClassDeclaration getClassDeclaration() {
        return ClassDeclaration;
    }

    public void setClassDeclaration(ClassDeclaration ClassDeclaration) {
        this.ClassDeclaration=ClassDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassDeclaration!=null) ClassDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassDeclaration!=null) ClassDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassDeclaration!=null) ClassDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationClass(\n");

        if(ClassDeclaration!=null)
            buffer.append(ClassDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationClass]");
        return buffer.toString();
    }
}
