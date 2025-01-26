// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class InterfaceBody implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private InterfaceElements InterfaceElements;

    public InterfaceBody (InterfaceElements InterfaceElements) {
        this.InterfaceElements=InterfaceElements;
        if(InterfaceElements!=null) InterfaceElements.setParent(this);
    }

    public InterfaceElements getInterfaceElements() {
        return InterfaceElements;
    }

    public void setInterfaceElements(InterfaceElements InterfaceElements) {
        this.InterfaceElements=InterfaceElements;
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
        if(InterfaceElements!=null) InterfaceElements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceElements!=null) InterfaceElements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceElements!=null) InterfaceElements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceBody(\n");

        if(InterfaceElements!=null)
            buffer.append(InterfaceElements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceBody]");
        return buffer.toString();
    }
}
