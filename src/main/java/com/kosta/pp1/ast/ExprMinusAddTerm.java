// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class ExprMinusAddTerm extends Expression {

    private AddTerm AddTerm;

    public ExprMinusAddTerm (AddTerm AddTerm) {
        this.AddTerm=AddTerm;
        if(AddTerm!=null) AddTerm.setParent(this);
    }

    public AddTerm getAddTerm() {
        return AddTerm;
    }

    public void setAddTerm(AddTerm AddTerm) {
        this.AddTerm=AddTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddTerm!=null) AddTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddTerm!=null) AddTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddTerm!=null) AddTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprMinusAddTerm(\n");

        if(AddTerm!=null)
            buffer.append(AddTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprMinusAddTerm]");
        return buffer.toString();
    }
}
