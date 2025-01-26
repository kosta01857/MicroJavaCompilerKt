package com.kosta.pp1.types

import com.kosta.pp1.ast.*
import com.kosta.pp1.extensions.getObj
import com.kosta.pp1.semanticAnalysis.visitors.ConditionVisitor
import com.kosta.pp1.semanticAnalysis.visitors.ExpressionVisitor
import com.kosta.pp1.types.visitors.FactorVisitor
import com.kosta.pp1.utils.Log4JUtils
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Struct

object TypeInferenceEngine {
    fun inferType(node: SyntaxNode): Struct {
        return when (node) {
            is Type -> {
                val struct = Tab.find(node.typeName).type
                return struct.takeIf { struct != Tab.noType } ?: run {
                    Log4JUtils.reportError("Type of name " + node.typeName + " doesnt exist", node)
                    Tab.noType
                }
            }

            is Literal -> {
                when (node) {
                    is CHAR -> Tab.charType
                    is NUMBER -> Tab.intType
                    is BOOL -> Struct(Struct.Bool)
                    else -> throw RuntimeException("unexpected type")
                }
            }

            is Expression -> {
                val visitor = ExpressionVisitor()
                node.traverseBottomUp(visitor)
                visitor.type
            }

            is Condition -> {
                val visitor = ConditionVisitor()
                node.traverseBottomUp(visitor)
                visitor.type
            }

            is Designator ->
                node.getObj()?.type ?: Tab.noType

            is Factor -> {
                val visitor = FactorVisitor()
                node.traverseBottomUp(visitor)
                visitor.type
            }

            else -> throw RuntimeException("unexpected type")
        }
    }
}