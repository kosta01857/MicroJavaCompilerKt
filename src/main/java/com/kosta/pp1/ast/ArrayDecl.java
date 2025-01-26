// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class ArrayDecl extends IdDeclaration {

    private ArrayDeclaration ArrayDeclaration;

    public ArrayDecl (ArrayDeclaration ArrayDeclaration) {
        this.ArrayDeclaration=ArrayDeclaration;
        if(ArrayDeclaration!=null) ArrayDeclaration.setParent(this);
    }

    public ArrayDeclaration getArrayDeclaration() {
        return ArrayDeclaration;
    }

    public void setArrayDeclaration(ArrayDeclaration ArrayDeclaration) {
        this.ArrayDeclaration=ArrayDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayDeclaration!=null) ArrayDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayDeclaration!=null) ArrayDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayDeclaration!=null) ArrayDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayDecl(\n");

        if(ArrayDeclaration!=null)
            buffer.append(ArrayDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayDecl]");
        return buffer.toString();
    }
}
