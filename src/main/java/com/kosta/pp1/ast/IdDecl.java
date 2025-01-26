// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class IdDecl extends IdDeclaration {

    private IdentDecl IdentDecl;

    public IdDecl (IdentDecl IdentDecl) {
        this.IdentDecl=IdentDecl;
        if(IdentDecl!=null) IdentDecl.setParent(this);
    }

    public IdentDecl getIdentDecl() {
        return IdentDecl;
    }

    public void setIdentDecl(IdentDecl IdentDecl) {
        this.IdentDecl=IdentDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdentDecl!=null) IdentDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentDecl!=null) IdentDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentDecl!=null) IdentDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdDecl(\n");

        if(IdentDecl!=null)
            buffer.append(IdentDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdDecl]");
        return buffer.toString();
    }
}
