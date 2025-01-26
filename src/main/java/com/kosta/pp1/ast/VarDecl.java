// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class VarDecl extends VarDeclaration {

    private IdDeclaration IdDeclaration;

    public VarDecl (IdDeclaration IdDeclaration) {
        this.IdDeclaration=IdDeclaration;
        if(IdDeclaration!=null) IdDeclaration.setParent(this);
    }

    public IdDeclaration getIdDeclaration() {
        return IdDeclaration;
    }

    public void setIdDeclaration(IdDeclaration IdDeclaration) {
        this.IdDeclaration=IdDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdDeclaration!=null) IdDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdDeclaration!=null) IdDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdDeclaration!=null) IdDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl(\n");

        if(IdDeclaration!=null)
            buffer.append(IdDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl]");
        return buffer.toString();
    }
}
