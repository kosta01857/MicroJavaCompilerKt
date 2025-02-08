package com.kosta.pp1.codeGeneration.exprStack
import com.kosta.pp1.ast.ExprAddTerm
import com.kosta.pp1.ast.Expression
import com.kosta.pp1.utils.Log4JUtils

interface ExprStack {
    companion object {
        fun pushExpression(expr: Expression?) {
            Log4JUtils.reportInfo("pushExpression", expr)
            if (expr is ExprAddTerm) {
                expr.traverseBottomUp(ExprVisitor())
            }
        }

        fun pushExpressionTopDown(expr: Expression?) {
            Log4JUtils.reportInfo("pushExpression", expr)
            if (expr is ExprAddTerm) {
                expr.traverseTopDown(ExprVisitor())
            }
        }
    }
}
