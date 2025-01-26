package com.kosta.pp1

import com.kosta.pp1.ast.*
import com.kosta.pp1.types.TypeInferenceEngine
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

object Finder {

    fun findStatements(statements: Statements): List<Statement> {
        val list = mutableListOf<Statement>()
        var current = statements

        while (current is StatementsRecursive) {
            list.add(current.statement)
            current = current.statements
        }

        return list.asReversed().toList()
    }

    fun findDeclarations(decls: Declarations): List<Declaration> {
        val list = mutableListOf<Declaration>()
        var current = decls
        while (current is DeclarationsRecursive) {
            list.add(current.declaration)
            current = current.declarations
        }
        return list.asReversed().toList()
    }

    fun findClassBody(classDecl: ClassDeclaration): ClassBody {
        return when (classDecl) {
            is ClassDeclarationNoExtend -> classDecl.classBody
            is ClassDeclarationExtend -> classDecl.classBody
            else -> throw RuntimeException()
        }
    }

    fun findIdDeclarations(varDeclaration: VarDeclaration): List<IdDeclaration> {
        val list = mutableListOf<IdDeclaration>()
        var current = varDeclaration
        while (current is VarDeclRecursive) {
            list.add(current.idDeclaration)
            current = current.varDeclaration
        }
        current = current as VarDecl
        list.add(current.idDeclaration)
        return list.asReversed().toList()
    }

    fun findInterfaceElements(elems: InterfaceElements): List<InterfaceElement> {
        val list = mutableListOf<InterfaceElement>()
        var current = elems
        while (current is InterfaceElementsRecursive) {
            list.add(current.interfaceElement)
            current = current.interfaceElements
        }
        return list.asReversed().toList()
    }

    fun findVarDeclarationLists(lists: LocalVarDeclarationLists): List<VarDeclarationList> {
        val list = mutableListOf<VarDeclarationList>()
        var current = lists
        while (current is LocalVarDeclarationListsRecursive) {
            list.add(current.varDeclarationList)
            current = current.localVarDeclarationLists
        }
        current = current as LocalVarDeclarationListsConcrete
        list.add(current.varDeclarationList)
        return list.asReversed().toList()

    }

    fun findVarDeclarationLists(lists: ClassLocalVarDeclarationLists): List<VarDeclarationList> {
        return if (lists is ClassLocalVarDeclarationListsConcrete) {
            findVarDeclarationLists(lists.localVarDeclarationLists)
        } else {
            listOf()
        }
    }

    fun findFunctionParameters(parameters: FunctionParameters): List<IdDeclaration> {
        var current = parameters
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

    fun findIdDefinitions(defList: IdDefinitionList): List<IdDefinition> {
        val list = mutableListOf<IdDefinition>()
        var current = defList
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

    fun findMethodDeclarations(decls: MethodDeclarations): List<MethodDeclaration>{
        val list = mutableListOf<MethodDeclaration>()
        var current = decls
        while (current is MethodDeclarationsRecursive){
            list.add(current.methodDeclaration)
            current = current.methodDeclarations
        }
        return list.asReversed().toList()
    }

    fun findExpressions(expressions: Expressions): List<Expression>{
        var current = expressions
        val list = mutableListOf<Expression>()
        while(current is ExpressionsRecursive){
            list.add(current.expression)
            current = current.expressions
        }
        list.add((current as ExpressionsConcrete).expression)
        return list.asReversed().toList()
    }

    fun findAccessors(designatorTail: DesignatorTail): List<DesignatorTail>{
        var current = designatorTail
        val list = mutableListOf<DesignatorTail>()
        while(current !is NoTail){
            list.add(current)
            when(current){
                is MemberAccess -> current = current.designatorTail
                is ArrayAccess -> current = current.designatorTail
            }
        }
        return list.asReversed().toList()
    }

    fun findFunctionArgs(methodSignature: MethodSignature): FunctionArgumentList{
        return when(methodSignature){
            is MethodSignatureTyped -> methodSignature.functionArgumentList
            is MethodSignatureVoid -> methodSignature.functionArgumentList
            else -> throw RuntimeException("FATAL ERROR")
        }
    }
}

