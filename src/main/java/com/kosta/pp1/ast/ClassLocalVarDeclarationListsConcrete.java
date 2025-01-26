// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class ClassLocalVarDeclarationListsConcrete extends ClassLocalVarDeclarationLists {

    private LocalVarDeclarationLists LocalVarDeclarationLists;

    public ClassLocalVarDeclarationListsConcrete (LocalVarDeclarationLists LocalVarDeclarationLists) {
        this.LocalVarDeclarationLists=LocalVarDeclarationLists;
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.setParent(this);
    }

    public LocalVarDeclarationLists getLocalVarDeclarationLists() {
        return LocalVarDeclarationLists;
    }

    public void setLocalVarDeclarationLists(LocalVarDeclarationLists LocalVarDeclarationLists) {
        this.LocalVarDeclarationLists=LocalVarDeclarationLists;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassLocalVarDeclarationListsConcrete(\n");

        if(LocalVarDeclarationLists!=null)
            buffer.append(LocalVarDeclarationLists.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassLocalVarDeclarationListsConcrete]");
        return buffer.toString();
    }
}
