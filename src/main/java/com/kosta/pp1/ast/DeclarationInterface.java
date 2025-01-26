// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class DeclarationInterface extends Declaration {

    private InterfaceDeclaration InterfaceDeclaration;

    public DeclarationInterface (InterfaceDeclaration InterfaceDeclaration) {
        this.InterfaceDeclaration=InterfaceDeclaration;
        if(InterfaceDeclaration!=null) InterfaceDeclaration.setParent(this);
    }

    public InterfaceDeclaration getInterfaceDeclaration() {
        return InterfaceDeclaration;
    }

    public void setInterfaceDeclaration(InterfaceDeclaration InterfaceDeclaration) {
        this.InterfaceDeclaration=InterfaceDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceDeclaration!=null) InterfaceDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceDeclaration!=null) InterfaceDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceDeclaration!=null) InterfaceDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationInterface(\n");

        if(InterfaceDeclaration!=null)
            buffer.append(InterfaceDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationInterface]");
        return buffer.toString();
    }
}
