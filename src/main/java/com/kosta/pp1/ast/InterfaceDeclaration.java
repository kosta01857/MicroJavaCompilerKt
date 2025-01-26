// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class InterfaceDeclaration implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String name;
    private InterfaceBody InterfaceBody;

    public InterfaceDeclaration (String name, InterfaceBody InterfaceBody) {
        this.name=name;
        this.InterfaceBody=InterfaceBody;
        if(InterfaceBody!=null) InterfaceBody.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public InterfaceBody getInterfaceBody() {
        return InterfaceBody;
    }

    public void setInterfaceBody(InterfaceBody InterfaceBody) {
        this.InterfaceBody=InterfaceBody;
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
        if(InterfaceBody!=null) InterfaceBody.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceBody!=null) InterfaceBody.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceBody!=null) InterfaceBody.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceDeclaration(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(InterfaceBody!=null)
            buffer.append(InterfaceBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceDeclaration]");
        return buffer.toString();
    }
}
