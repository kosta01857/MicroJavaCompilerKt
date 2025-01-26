// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class FuncPars extends FunctionArgumentList {

    private FunctionParameters FunctionParameters;

    public FuncPars (FunctionParameters FunctionParameters) {
        this.FunctionParameters=FunctionParameters;
        if(FunctionParameters!=null) FunctionParameters.setParent(this);
    }

    public FunctionParameters getFunctionParameters() {
        return FunctionParameters;
    }

    public void setFunctionParameters(FunctionParameters FunctionParameters) {
        this.FunctionParameters=FunctionParameters;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FunctionParameters!=null) FunctionParameters.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FunctionParameters!=null) FunctionParameters.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FunctionParameters!=null) FunctionParameters.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncPars(\n");

        if(FunctionParameters!=null)
            buffer.append(FunctionParameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncPars]");
        return buffer.toString();
    }
}
