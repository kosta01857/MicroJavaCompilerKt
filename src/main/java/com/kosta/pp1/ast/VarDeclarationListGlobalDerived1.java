// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class VarDeclarationListGlobalDerived1 extends VarDeclarationListGlobal {

    public VarDeclarationListGlobalDerived1 () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationListGlobalDerived1(\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationListGlobalDerived1]");
        return buffer.toString();
    }
}
