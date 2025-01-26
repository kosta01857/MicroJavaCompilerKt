// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class ConstDeclarationList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private IdDefinitionList IdDefinitionList;

    public ConstDeclarationList (Type Type, IdDefinitionList IdDefinitionList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.IdDefinitionList=IdDefinitionList;
        if(IdDefinitionList!=null) IdDefinitionList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public IdDefinitionList getIdDefinitionList() {
        return IdDefinitionList;
    }

    public void setIdDefinitionList(IdDefinitionList IdDefinitionList) {
        this.IdDefinitionList=IdDefinitionList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(IdDefinitionList!=null) IdDefinitionList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(IdDefinitionList!=null) IdDefinitionList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(IdDefinitionList!=null) IdDefinitionList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdDefinitionList!=null)
            buffer.append(IdDefinitionList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationList]");
        return buffer.toString();
    }
}
