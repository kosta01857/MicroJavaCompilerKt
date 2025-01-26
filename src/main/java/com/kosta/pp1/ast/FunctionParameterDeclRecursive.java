// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class FunctionParameterDeclRecursive extends FunctionParameters {

    private FunctionParameters FunctionParameters;
    private Type Type;
    private IdDeclaration IdDeclaration;

    public FunctionParameterDeclRecursive (FunctionParameters FunctionParameters, Type Type, IdDeclaration IdDeclaration) {
        this.FunctionParameters=FunctionParameters;
        if(FunctionParameters!=null) FunctionParameters.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.IdDeclaration=IdDeclaration;
        if(IdDeclaration!=null) IdDeclaration.setParent(this);
    }

    public FunctionParameters getFunctionParameters() {
        return FunctionParameters;
    }

    public void setFunctionParameters(FunctionParameters FunctionParameters) {
        this.FunctionParameters=FunctionParameters;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public IdDeclaration getIdDeclaration() {
        return IdDeclaration;
    }

    public void setIdDeclaration(IdDeclaration IdDeclaration) {
        this.IdDeclaration=IdDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FunctionParameters!=null) FunctionParameters.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(IdDeclaration!=null) IdDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FunctionParameters!=null) FunctionParameters.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(IdDeclaration!=null) IdDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FunctionParameters!=null) FunctionParameters.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(IdDeclaration!=null) IdDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FunctionParameterDeclRecursive(\n");

        if(FunctionParameters!=null)
            buffer.append(FunctionParameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdDeclaration!=null)
            buffer.append(IdDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FunctionParameterDeclRecursive]");
        return buffer.toString();
    }
}
