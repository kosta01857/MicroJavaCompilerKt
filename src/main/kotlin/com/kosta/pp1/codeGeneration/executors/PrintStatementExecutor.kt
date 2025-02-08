package com.kosta.pp1.codeGeneration.executors

import com.kosta.pp1.core.Cache
import com.kosta.pp1.ast.PrintOne
import com.kosta.pp1.ast.PrintStmt
import com.kosta.pp1.ast.SyntaxNode
import com.kosta.pp1.codeGeneration.exprStack.ExprStack
import rs.etf.pp1.mj.runtime.Code
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Struct

internal class PrintStatementExecutor : StatementExecutor {
    override fun executeStatement(node: SyntaxNode?) {
        val pStmt = node as PrintStmt
        val pStatement = pStmt.printStatement
        if (pStatement is PrintOne) {
            val printExpression = pStatement.expression
            ExprStack.pushExpression(printExpression)
            Code.loadConst(0)
            val printExpressionType: Struct = Cache.typeMap[printExpression] ?: throw RuntimeException("type map failed")
            if (printExpressionType.equals(Tab.charType)) Code.put(Code.bprint)
            else Code.put(Code.print)
        }
    }
}
