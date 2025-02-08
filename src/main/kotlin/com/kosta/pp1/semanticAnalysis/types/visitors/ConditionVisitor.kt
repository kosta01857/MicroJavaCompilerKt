package com.kosta.pp1.semanticAnalysis.types.visitors

import com.kosta.pp1.Cache
import com.kosta.pp1.ast.ConditionFactExpression
import com.kosta.pp1.ast.ConditionFactExpressions
import com.kosta.pp1.ast.Equal
import com.kosta.pp1.ast.NotEqual
import com.kosta.pp1.ast.VisitorAdaptor
import com.kosta.pp1.utils.extensions.isValid
import com.kosta.pp1.utils.Log4JUtils

class ConditionVisitor : VisitorAdaptor() {
    var isValid = true
    override fun visit(conditionFactorExpression: ConditionFactExpression) {
        val expression = conditionFactorExpression.expression
        if (!expression.isValid()) {
            Log4JUtils.reportError("INVALID EXPRESSION", conditionFactorExpression)
            isValid = false
        }
    }
    override fun visit(conditionFactorExpressions: ConditionFactExpressions){
        val expressionOne = conditionFactorExpressions.expression
        val expressionTwo = conditionFactorExpressions.expression1
        val expressions = listOf(expressionOne,expressionTwo)
        expressions.forEach {
            if (!it.isValid()) {
                Log4JUtils.reportError("INVALID EXPRESSION", it)
                isValid = false
                return
            }
        }
        val exprOneType = Cache.typeMap[expressionOne] ?: throw RuntimeException("FATAL ERROR")
        val exprTwoType = Cache.typeMap[expressionTwo] ?: throw RuntimeException("FATAL ERROR")
        if (!exprOneType.compatibleWith(exprTwoType)){
            Log4JUtils.reportError("incompatible types in condition",conditionFactorExpressions)
            isValid = false
            return
        }
        if(exprTwoType.isRefType){
            val relOp = conditionFactorExpressions.relop
            if (relOp !is Equal && relOp !is NotEqual){
                Log4JUtils.reportError("invalid operator for given types",conditionFactorExpressions)
                isValid = false
                return
            }
        }
    }
}