package com.kosta.pp1.semanticAnalysis.types.visitors

import com.kosta.pp1.ast.*
import com.kosta.pp1.core.utils.extensions.getObj
import com.kosta.pp1.core.utils.extensions.isOfKind
import com.kosta.pp1.core.utils.extensions.isOfKinds
import com.kosta.pp1.core.utils.extensions.kindEquals
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

class ExpressionVisitor: VisitorAdaptor() {
    val typeMap = mutableMapOf<SyntaxNode,Struct>()
    var isValid: Boolean = true
    var type : Struct = Tab.noType

    override fun visit(exprAddTerm: ExprAddTerm){
        type = typeMap[exprAddTerm] ?: throw NullPointerException("FATAL ERROR")
    }
    override fun visit(exprMinusAddTerm: ExprMinusAddTerm){
        val exprType = typeMap[exprMinusAddTerm]
        type = exprType?.takeIf { exprType.kindEquals(Tab.intType) } ?: throw NullPointerException("FATAL ERROR")
    }
    override fun visit(mapExpr: MapExpr){
        val rightDesignator = mapExpr.designator1
        val leftDesignator = mapExpr.designator
        val rightDesignatorType = TypeInferenceEngine.inferType(rightDesignator)
        type = Tab.intType.takeIf {
            val funcObj = leftDesignator.getObj() ?: return
            funcObj.isOfKind(Obj.Meth) &&
                    funcObj.level == 1 &&
                    funcObj.type.kindEquals(Tab.intType) &&
                    rightDesignatorType.kindEquals(Struct(Struct.Array,Tab.intType))
        } ?: return
    }

    private fun AddTerm.assignTypeToParent(){
        if(!isValid) return
        val addTermType = typeMap[this] ?: throw RuntimeException("FATAL ERROR")
        val parent = this.parent
        val parentType = typeMap[parent]
        typeMap[parent] = parentType?.let {
            when{
                !parentType.kindEquals(Tab.intType) -> {
                    isValid = false
                    Tab.noType
                }
                else -> parentType
            }
        } ?: addTermType
    }
    override fun visit(addTermConcrete: AddTermConcrete){
        addTermConcrete.assignTypeToParent()
    }
    override fun visit(addTermRecursive: AddTermRecursive){
        addTermRecursive.assignTypeToParent()
    }

    private fun Term.assignTypeToParent(){
        if(!isValid) return
        val termType = typeMap[this] ?: throw NullPointerException("FATAL ERROR")
        val parent = this.parent
        val parentType = typeMap[parent]
        typeMap[parent] = parentType?.let {
            when{
                !parentType.kindEquals(Tab.intType) -> {
                    isValid = false
                    Tab.noType
                }
                else -> parentType
            }
        } ?: termType
    }
    override fun visit(termConcrete: TermConcrete){
        termConcrete.assignTypeToParent()
    }
    override fun visit(termRecursive: TermRecursive){
        termRecursive.assignTypeToParent()
    }

    private fun Factor.assignTypeToParent(){
        if(!isValid) return
        val factorType = TypeInferenceEngine.inferType(this)
        val parent = this.parent
        val parentType = typeMap[parent]
        typeMap[parent] = parentType?.let {
            when{
                !parentType.kindEquals(Tab.intType) -> {
                    isValid = false
                    Tab.noType
                }
                else -> parentType
            }
        } ?: factorType
    }
    override fun visit(factorLiteral: FactorLiteral){
        factorLiteral.assignTypeToParent()
    }
    override fun visit(nestedExpression: NestedExpression){
        nestedExpression.assignTypeToParent()
    }
    override fun visit(factorNewType: FactorNewType){
        factorNewType.assignTypeToParent()
    }
    override fun visit(factorFunctionCall: FactorFunctionCall){
        factorFunctionCall.assignTypeToParent()
    }
    override fun visit(factorIdent: FactorIdent){
        factorIdent.assignTypeToParent()
    }
}