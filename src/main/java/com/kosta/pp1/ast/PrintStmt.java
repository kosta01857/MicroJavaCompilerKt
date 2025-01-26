// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class PrintStmt extends Statement {

    private PrintStatement PrintStatement;

    public PrintStmt (PrintStatement PrintStatement) {
        this.PrintStatement=PrintStatement;
        if(PrintStatement!=null) PrintStatement.setParent(this);
    }

    public PrintStatement getPrintStatement() {
        return PrintStatement;
    }

    public void setPrintStatement(PrintStatement PrintStatement) {
        this.PrintStatement=PrintStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PrintStatement!=null) PrintStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrintStatement!=null) PrintStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrintStatement!=null) PrintStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintStmt(\n");

        if(PrintStatement!=null)
            buffer.append(PrintStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintStmt]");
        return buffer.toString();
    }
}
