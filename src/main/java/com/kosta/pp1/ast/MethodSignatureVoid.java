// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class MethodSignatureVoid extends MethodSignature {

    private String MethodName;
    private FunctionArgumentList FunctionArgumentList;

    public MethodSignatureVoid (String MethodName, FunctionArgumentList FunctionArgumentList) {
        this.MethodName=MethodName;
        this.FunctionArgumentList=FunctionArgumentList;
        if(FunctionArgumentList!=null) FunctionArgumentList.setParent(this);
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String MethodName) {
        this.MethodName=MethodName;
    }

    public FunctionArgumentList getFunctionArgumentList() {
        return FunctionArgumentList;
    }

    public void setFunctionArgumentList(FunctionArgumentList FunctionArgumentList) {
        this.FunctionArgumentList=FunctionArgumentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FunctionArgumentList!=null) FunctionArgumentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FunctionArgumentList!=null) FunctionArgumentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FunctionArgumentList!=null) FunctionArgumentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodSignatureVoid(\n");

        buffer.append(" "+tab+MethodName);
        buffer.append("\n");

        if(FunctionArgumentList!=null)
            buffer.append(FunctionArgumentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodSignatureVoid]");
        return buffer.toString();
    }
}
