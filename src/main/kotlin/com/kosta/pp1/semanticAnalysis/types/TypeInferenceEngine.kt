package com.kosta.pp1.semanticAnalysis.types

import com.kosta.pp1.Cache
import com.kosta.pp1.SET_TYPE_ID
import com.kosta.pp1.ast.*
import com.kosta.pp1.utils.extensions.getObj
import com.kosta.pp1.semanticAnalysis.types.visitors.ConditionVisitor
import com.kosta.pp1.semanticAnalysis.types.visitors.ExpressionVisitor
import com.kosta.pp1.semanticAnalysis.types.visitors.FactorVisitor
import com.kosta.pp1.utils.Log4JUtils
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Struct

object TypeInferenceEngine {
    fun inferType(node: SyntaxNode): Struct {
        return when (node) {
            is Type -> {
                val struct = Tab.find(node.typeName).type
                if (node.typeName == "set") return Struct(SET_TYPE_ID)
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
                Cache.typeMap[node] ?: run {
                    val visitor = ExpressionVisitor()
                    node.traverseBottomUp(visitor)
                    Cache.typeMap[node] = visitor.type
                    visitor.type
                }
            }

            is Condition -> {
                val visitor = ConditionVisitor()
                node.traverseBottomUp(visitor)
                if(visitor.isValid) Struct(Struct.Bool) else Tab.noType
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