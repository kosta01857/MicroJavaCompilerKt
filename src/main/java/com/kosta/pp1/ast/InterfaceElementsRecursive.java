// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class InterfaceElementsRecursive extends InterfaceElements {

    private InterfaceElements InterfaceElements;
    private InterfaceElement InterfaceElement;

    public InterfaceElementsRecursive (InterfaceElements InterfaceElements, InterfaceElement InterfaceElement) {
        this.InterfaceElements=InterfaceElements;
        if(InterfaceElements!=null) InterfaceElements.setParent(this);
        this.InterfaceElement=InterfaceElement;
        if(InterfaceElement!=null) InterfaceElement.setParent(this);
    }

    public InterfaceElements getInterfaceElements() {
        return InterfaceElements;
    }

    public void setInterfaceElements(InterfaceElements InterfaceElements) {
        this.InterfaceElements=InterfaceElements;
    }

    public InterfaceElement getInterfaceElement() {
        return InterfaceElement;
    }

    public void setInterfaceElement(InterfaceElement InterfaceElement) {
        this.InterfaceElement=InterfaceElement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceElements!=null) InterfaceElements.accept(visitor);
        if(InterfaceElement!=null) InterfaceElement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceElements!=null) InterfaceElements.traverseTopDown(visitor);
        if(InterfaceElement!=null) InterfaceElement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceElements!=null) InterfaceElements.traverseBottomUp(visitor);
        if(InterfaceElement!=null) InterfaceElement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceElementsRecursive(\n");

        if(InterfaceElements!=null)
            buffer.append(InterfaceElements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceElement!=null)
            buffer.append(InterfaceElement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceElementsRecursive]");
        return buffer.toString();
    }
}
