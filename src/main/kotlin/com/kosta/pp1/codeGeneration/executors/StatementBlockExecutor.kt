package com.kosta.pp1.codeGeneration.executors

import com.kosta.pp1.ast.StatementBlock
import com.kosta.pp1.ast.SyntaxNode
import com.kosta.pp1.findStatements

class StatementBlockExecutor : StatementExecutor {
    override fun executeStatement(node: SyntaxNode?) {
        val sBlock = node as StatementBlock
        StatementExecutor.executeStatements(sBlock.statements.findStatements())
    }

}
