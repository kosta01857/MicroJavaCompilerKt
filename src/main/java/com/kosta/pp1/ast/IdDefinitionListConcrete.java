// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class IdDefinitionListConcrete extends IdDefinitionList {

    private IdDefinition IdDefinition;

    public IdDefinitionListConcrete (IdDefinition IdDefinition) {
        this.IdDefinition=IdDefinition;
        if(IdDefinition!=null) IdDefinition.setParent(this);
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
        if(IdDefinition!=null) IdDefinition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdDefinition!=null) IdDefinition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdDefinition!=null) IdDefinition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdDefinitionListConcrete(\n");

        if(IdDefinition!=null)
            buffer.append(IdDefinition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdDefinitionListConcrete]");
        return buffer.toString();
    }
}
