// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class ConditionFactExpressions extends ConditionFact {

    private Expression Expression;
    private Relop Relop;
    private Expression Expression1;

    public ConditionFactExpressions (Expression Expression, Relop Relop, Expression Expression1) {
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.Expression1=Expression1;
        if(Expression1!=null) Expression1.setParent(this);
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public Expression getExpression1() {
        return Expression1;
    }

    public void setExpression1(Expression Expression1) {
        this.Expression1=Expression1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expression!=null) Expression.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(Expression1!=null) Expression1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(Expression1!=null) Expression1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(Expression1!=null) Expression1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionFactExpressions(\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression1!=null)
            buffer.append(Expression1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionFactExpressions]");
        return buffer.toString();
    }
}
