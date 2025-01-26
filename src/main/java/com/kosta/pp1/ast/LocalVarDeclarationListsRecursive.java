// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class LocalVarDeclarationListsRecursive extends LocalVarDeclarationLists {

    private LocalVarDeclarationLists LocalVarDeclarationLists;
    private VarDeclarationList VarDeclarationList;

    public LocalVarDeclarationListsRecursive (LocalVarDeclarationLists LocalVarDeclarationLists, VarDeclarationList VarDeclarationList) {
        this.LocalVarDeclarationLists=LocalVarDeclarationLists;
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.setParent(this);
        this.VarDeclarationList=VarDeclarationList;
        if(VarDeclarationList!=null) VarDeclarationList.setParent(this);
    }

    public LocalVarDeclarationLists getLocalVarDeclarationLists() {
        return LocalVarDeclarationLists;
    }

    public void setLocalVarDeclarationLists(LocalVarDeclarationLists LocalVarDeclarationLists) {
        this.LocalVarDeclarationLists=LocalVarDeclarationLists;
    }

    public VarDeclarationList getVarDeclarationList() {
        return VarDeclarationList;
    }

    public void setVarDeclarationList(VarDeclarationList VarDeclarationList) {
        this.VarDeclarationList=VarDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.accept(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.traverseTopDown(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.traverseBottomUp(visitor);
        if(VarDeclarationList!=null) VarDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVarDeclarationListsRecursive(\n");

        if(LocalVarDeclarationLists!=null)
            buffer.append(LocalVarDeclarationLists.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclarationList!=null)
            buffer.append(VarDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarDeclarationListsRecursive]");
        return buffer.toString();
    }
}
