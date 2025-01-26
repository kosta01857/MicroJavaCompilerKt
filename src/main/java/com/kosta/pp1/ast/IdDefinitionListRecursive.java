// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class IdDefinitionListRecursive extends IdDefinitionList {

    private IdDefinitionList IdDefinitionList;
    private IdDefinition IdDefinition;

    public IdDefinitionListRecursive (IdDefinitionList IdDefinitionList, IdDefinition IdDefinition) {
        this.IdDefinitionList=IdDefinitionList;
        if(IdDefinitionList!=null) IdDefinitionList.setParent(this);
        this.IdDefinition=IdDefinition;
        if(IdDefinition!=null) IdDefinition.setParent(this);
    }

    public IdDefinitionList getIdDefinitionList() {
        return IdDefinitionList;
    }

    public void setIdDefinitionList(IdDefinitionList IdDefinitionList) {
        this.IdDefinitionList=IdDefinitionList;
    }

    public IdDefinition getIdDefinition() {
        return IdDefinition;
    }

    public void setIdDefinition(IdDefinition IdDefinition) {
        this.IdDefinition=IdDefinition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdDefinitionList!=null) IdDefinitionList.accept(visitor);
        if(IdDefinition!=null) IdDefinition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdDefinitionList!=null) IdDefinitionList.traverseTopDown(visitor);
        if(IdDefinition!=null) IdDefinition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdDefinitionList!=null) IdDefinitionList.traverseBottomUp(visitor);
        if(IdDefinition!=null) IdDefinition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdDefinitionListRecursive(\n");

        if(IdDefinitionList!=null)
            buffer.append(IdDefinitionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdDefinition!=null)
            buffer.append(IdDefinition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdDefinitionListRecursive]");
        return buffer.toString();
    }
}
