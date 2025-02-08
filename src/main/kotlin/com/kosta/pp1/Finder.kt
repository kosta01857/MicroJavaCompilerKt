package com.kosta.pp1

import com.kosta.pp1.ast.*
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct


fun Statements.findStatements(): List<Statement> {
    val list = mutableListOf<Statement>()
    var current = this

    while (current is StatementsRecursive) {
        list.add(current.statement)
        current = current.statements
    }

    return list.asReversed().toList()
}

fun Declarations.findDeclarations(): List<Declaration> {
    val list = mutableListOf<Declaration>()
    var current = this
    while (current is DeclarationsRecursive) {
        list.add(current.declaration)
        current = current.declarations
    }
    return list.asReversed().toList()
}

fun ClassDeclaration.findClassBody(): ClassBody {
    return when (this) {
        is ClassDeclarationNoExtend -> this.classBody
        is ClassDeclarationExtend -> this.classBody
        else -> throw RuntimeException()
    }
}

fun VarDeclaration.findIdDeclarations(): List<IdDeclaration> {
    val list = mutableListOf<IdDeclaration>()
    var current = this
    while (current is VarDeclRecursive) {
        list.add(current.idDeclaration)
        current = current.varDeclaration
    }
    current = current as VarDecl
    list.add(current.idDeclaration)
    return list.asReversed().toList()
}

fun InterfaceElements.findInterfaceElements(): List<InterfaceElement> {
    val list = mutableListOf<InterfaceElement>()
    var current = this
    while (current is InterfaceElementsRecursive) {
        list.add(current.interfaceElement)
        current = current.interfaceElements
    }
    return list.asReversed().toList()
}

fun LocalVarDeclarationLists.findVarDeclarationLists(): List<VarDeclarationList> {
    val list = mutableListOf<VarDeclarationList>()
    var current = this
    while (current is LocalVarDeclarationListsRecursive) {
        list.add(current.varDeclarationList)
        current = current.localVarDeclarationLists
    }
    current = current as LocalVarDeclarationListsConcrete
    list.add(current.varDeclarationList)
    return list.asReversed().toList()

}

fun ClassLocalVarDeclarationLists.findVarDeclarationLists(): List<VarDeclarationList> {
    return if (this is ClassLocalVarDeclarationListsConcrete) {
        this.findVarDeclarationLists()
    } else {
        listOf()
    }
}

fun FunctionParameters.findFunctionParameters(): List<IdDeclaration> {
    var current = this
    val list = mutableListOf<IdDeclaration>()
    while (current is FunctionParameterDeclRecursive) {
        list.add(current.idDeclaration)
        Cache.typeMap[current.idDeclaration] = TypeInferenceEngine.inferType(current.type)
        current = current.functionParameters
    }
    current = current as FunctionParameterDeclConcrete
    Cache.typeMap[current.idDeclaration] = TypeInferenceEngine.inferType(current.type)
    list.add(current.idDeclaration)
    return list.asReversed().toList()
}

fun IdDefinitionList.findIdDefinitions(): List<IdDefinition> {
    val list = mutableListOf<IdDefinition>()
    var current = this
    while (current is IdDefinitionListRecursive) {
        list.add(current.idDefinition)
        current = current.idDefinitionList
    }
    current = current as IdDefinitionListConcrete
    list.add(current.idDefinition)
    return list.asReversed().toList()
}

fun findMainFunction(): Boolean {
    val mainObj = Tab.find("main")
    return mainObj != Tab.noObj &&
            mainObj.kind == Obj.Meth &&
            mainObj.type.kind == Struct.None &&
            mainObj.level == 0
}

fun MethodDeclarations.findMethodDeclarations(): List<MethodDeclaration> {
    val list = mutableListOf<MethodDeclaration>()
    var current = this
    while (current is MethodDeclarationsRecursive) {
        list.add(current.methodDeclaration)
        current = current.methodDeclarations
    }
    return list.asReversed().toList()
}

fun Expressions.findExpressions(): List<Expression> {
    var current = this
    val list = mutableListOf<Expression>()
    while (current is ExpressionsRecursive) {
        list.add(current.expression)
        current = current.expressions
    }
    list.add((current as ExpressionsConcrete).expression)
    return list.asReversed().toList()
}

fun DesignatorTail.findAccessors(): List<DesignatorTail> {
    var current = this
    val list = mutableListOf<DesignatorTail>()
    while (current !is NoTail) {
        list.add(current)
        when (current) {
            is MemberAccess -> current = current.designatorTail
            is ArrayAccess -> current = current.designatorTail
        }
    }
    return list.asReversed().toList()
}

fun MethodSignature.findFunctionArgs(): FunctionArgumentList {
    return when (this) {
        is MethodSignatureTyped -> this.functionArgumentList
        is MethodSignatureVoid -> this.functionArgumentList
        else -> throw RuntimeException("FATAL ERROR")
    }
}

