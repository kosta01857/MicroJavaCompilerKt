package com.kosta.pp1.codeGeneration

import com.kosta.pp1.Cache
import com.kosta.pp1.ast.MethodDefinition
import com.kosta.pp1.ast.MethodDefinitionNoLocals
import com.kosta.pp1.ast.SyntaxNode
import com.kosta.pp1.ast.VisitorAdaptor
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.mj.runtime.Code

class CodeGenVisitor(
    private val generator: Generator
) : VisitorAdaptor() {
    var mainPc = 0

    private fun getObj(node: SyntaxNode): Obj? {
        return Cache.objMap[node]
    }

    override fun visit(definition: MethodDefinition) {
        val signature = definition.getMethodSignature()
        val methodNode = getObj(signature) ?: throw RuntimeException("code generator searched for non existing obj")
        val b1 = methodNode.level
        val b2 = methodNode.localSymbols.size
        generator.enterFunction(b1, b2)
        generator.executeStatements(definition.getStatements())
        generator.exitFunction()
    }

    override fun visit(definition: MethodDefinitionNoLocals) {
        val signature = definition.getMethodSignature()
        val methodNode = getObj(signature) ?: throw RuntimeException("code generator searched for non existing obj")
        val b1 = methodNode.level
        val b2 = methodNode.localSymbols.size
        if (methodNode.name == "main") {
            mainPc = Code.pc
        }
        generator.enterFunction(b1, b2)
        generator.executeStatements(definition.getStatements())
        generator.exitFunction()
    }
}

