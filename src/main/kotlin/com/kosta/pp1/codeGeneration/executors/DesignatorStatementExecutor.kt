package com.kosta.pp1.codeGeneration.executors

import com.kosta.pp1.ast.*
import com.kosta.pp1.codeGeneration.Generator
import com.kosta.pp1.codeGeneration.exprStack.ExprStack
import com.kosta.pp1.core.Cache
import com.kosta.pp1.core.utils.extensions.isOfKind
import rs.etf.pp1.mj.runtime.Code
import rs.etf.pp1.symboltable.concepts.Obj

internal class DesignatorStatementExecutor : StatementExecutor {
    override fun executeStatement(node: SyntaxNode?) {
        val dStmt = node as DesignatorStmt?
        when(val dStatement = dStmt!!.designatorStatement){
            is PostDec ->  TODO()
            is VarDesignation -> {
                val leftSideObj: Obj = Cache.objMap[dStatement.designator] ?: throw RuntimeException("obj map failed")
                val isArrayElem = leftSideObj.isOfKind(Obj.Elem)
                if(isArrayElem){
                    Generator.storeArrayElem(dStatement,leftSideObj)
                }
                else{
                    ExprStack.pushExpression(dStatement.expression)
                    Code.store(leftSideObj)
                }
            }
            is SetDesignation -> TODO()
            is FunctionCall -> TODO()
        }
    }
}
