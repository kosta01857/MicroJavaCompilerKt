// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class ActParsConcrete extends ActPars {

    private Expressions Expressions;

    public ActParsConcrete (Expressions Expressions) {
        this.Expressions=Expressions;
        if(Expressions!=null) Expressions.setParent(this);
    }

    public Expressions getExpressions() {
        return Expressions;
    }

    public void setExpressions(Expressions Expressions) {
        this.Expressions=Expressions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expressions!=null) Expressions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expressions!=null) Expressions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expressions!=null) Expressions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsConcrete(\n");

        if(Expressions!=null)
            buffer.append(Expressions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParsConcrete]");
        return buffer.toString();
    }
}
