// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class FunctionParameterDeclConcrete extends FunctionParameters {

    private Type Type;
    private IdDeclaration IdDeclaration;

    public FunctionParameterDeclConcrete (Type Type, IdDeclaration IdDeclaration) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.IdDeclaration=IdDeclaration;
        if(IdDeclaration!=null) IdDeclaration.setParent(this);
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
        if(Type!=null) Type.accept(visitor);
        if(IdDeclaration!=null) IdDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(IdDeclaration!=null) IdDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(IdDeclaration!=null) IdDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FunctionParameterDeclConcrete(\n");

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
        buffer.append(") [FunctionParameterDeclConcrete]");
        return buffer.toString();
    }
}
