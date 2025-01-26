// generated with ast extension for cup
// version 0.8
// 25/0/2025 14:17:59


package com.kosta.pp1.ast;

public class MethodDefinition extends MethodDeclaration {

    private MethodSignature MethodSignature;
    private LocalVarDeclarationLists LocalVarDeclarationLists;
    private Statements Statements;

    public MethodDefinition (MethodSignature MethodSignature, LocalVarDeclarationLists LocalVarDeclarationLists, Statements Statements) {
        this.MethodSignature=MethodSignature;
        if(MethodSignature!=null) MethodSignature.setParent(this);
        this.LocalVarDeclarationLists=LocalVarDeclarationLists;
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.setParent(this);
        this.Statements=Statements;
        if(Statements!=null) Statements.setParent(this);
    }

    public MethodSignature getMethodSignature() {
        return MethodSignature;
    }

    public void setMethodSignature(MethodSignature MethodSignature) {
        this.MethodSignature=MethodSignature;
    }

    public LocalVarDeclarationLists getLocalVarDeclarationLists() {
        return LocalVarDeclarationLists;
    }

    public void setLocalVarDeclarationLists(LocalVarDeclarationLists LocalVarDeclarationLists) {
        this.LocalVarDeclarationLists=LocalVarDeclarationLists;
    }

    public Statements getStatements() {
        return Statements;
    }

    public void setStatements(Statements Statements) {
        this.Statements=Statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.accept(visitor);
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.accept(visitor);
        if(Statements!=null) Statements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSignature!=null) MethodSignature.traverseTopDown(visitor);
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.traverseTopDown(visitor);
        if(Statements!=null) Statements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.traverseBottomUp(visitor);
        if(LocalVarDeclarationLists!=null) LocalVarDeclarationLists.traverseBottomUp(visitor);
        if(Statements!=null) Statements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDefinition(\n");

        if(MethodSignature!=null)
            buffer.append(MethodSignature.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarDeclarationLists!=null)
            buffer.append(LocalVarDeclarationLists.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statements!=null)
            buffer.append(Statements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDefinition]");
        return buffer.toString();
    }
}
