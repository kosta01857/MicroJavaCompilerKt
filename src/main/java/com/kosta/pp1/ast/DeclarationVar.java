// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class DeclarationVar extends Declaration {

    private VarDeclarationListGlobal VarDeclarationListGlobal;

    public DeclarationVar (VarDeclarationListGlobal VarDeclarationListGlobal) {
        this.VarDeclarationListGlobal=VarDeclarationListGlobal;
        if(VarDeclarationListGlobal!=null) VarDeclarationListGlobal.setParent(this);
    }

    public VarDeclarationListGlobal getVarDeclarationListGlobal() {
        return VarDeclarationListGlobal;
    }

    public void setVarDeclarationListGlobal(VarDeclarationListGlobal VarDeclarationListGlobal) {
        this.VarDeclarationListGlobal=VarDeclarationListGlobal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclarationListGlobal!=null) VarDeclarationListGlobal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclarationListGlobal!=null) VarDeclarationListGlobal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclarationListGlobal!=null) VarDeclarationListGlobal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationVar(\n");

        if(VarDeclarationListGlobal!=null)
            buffer.append(VarDeclarationListGlobal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationVar]");
        return buffer.toString();
    }
}
