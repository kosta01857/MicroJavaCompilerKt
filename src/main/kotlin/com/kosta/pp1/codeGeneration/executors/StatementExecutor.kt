package com.kosta.pp1.codeGeneration.executors

import com.kosta.pp1.ast.*
import java.util.function.Consumer


interface StatementExecutor {
    fun executeStatement(node: SyntaxNode?)

    companion object {
        val executorMap: MutableMap<Class<out Statement>, StatementExecutor> = HashMap()
        init {
            executorMap[DesignatorStmt::class.java] = DesignatorStatementExecutor()
            executorMap[Break::class.java] = BreakStatementExecutor()
            executorMap[Continue::class.java] = ContinueStatementExecutor()
            executorMap[WhileStmt::class.java] = DoWhileExecutor()
            executorMap[IfStmt::class.java] = IfStatementExecutor()
            executorMap[PrintStmt::class.java] = PrintStatementExecutor()
            executorMap[ReadStatement::class.java] = ReadStatementExecutor()
            executorMap[StatementBlock::class.java] = StatementBlockExecutor()
            executorMap[ReturnStmt::class.java] = ReturnStatementExecutor()
        }

        fun executeStatements(statements: List<Statement>) {
            statements.forEach { statement ->
                executorMap[statement.javaClass]?.executeStatement(statement)
            }
        }
    }
}
