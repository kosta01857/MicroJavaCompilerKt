// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class VarDeclRecursive extends VarDeclaration {

    private VarDeclaration VarDeclaration;
    private IdDeclaration IdDeclaration;

    public VarDeclRecursive (VarDeclaration VarDeclaration, IdDeclaration IdDeclaration) {
        this.VarDeclaration=VarDeclaration;
        if(VarDeclaration!=null) VarDeclaration.setParent(this);
        this.IdDeclaration=IdDeclaration;
        if(IdDeclaration!=null) IdDeclaration.setParent(this);
    }

    public VarDeclaration getVarDeclaration() {
        return VarDeclaration;
    }

    public void setVarDeclaration(VarDeclaration VarDeclaration) {
        this.VarDeclaration=VarDeclaration;
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
        if(VarDeclaration!=null) VarDeclaration.accept(visitor);
        if(IdDeclaration!=null) IdDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclaration!=null) VarDeclaration.traverseTopDown(visitor);
        if(IdDeclaration!=null) IdDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclaration!=null) VarDeclaration.traverseBottomUp(visitor);
        if(IdDeclaration!=null) IdDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclRecursive(\n");

        if(VarDeclaration!=null)
            buffer.append(VarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdDeclaration!=null)
            buffer.append(IdDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclRecursive]");
        return buffer.toString();
    }
}
