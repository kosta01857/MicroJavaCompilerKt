package com.kosta.pp1.types.visitors

import com.kosta.pp1.ast.FactorFunctionCall
import com.kosta.pp1.ast.FactorIdent
import com.kosta.pp1.ast.FactorLiteral
import com.kosta.pp1.ast.FactorNewType
import com.kosta.pp1.ast.NestedExpression
import com.kosta.pp1.ast.NewArray
import com.kosta.pp1.ast.NewClass
import com.kosta.pp1.ast.VisitorAdaptor
import com.kosta.pp1.extensions.getObj
import com.kosta.pp1.extensions.isFunctionCallValid
import com.kosta.pp1.extensions.isOfKinds
import com.kosta.pp1.extensions.isOfType
import com.kosta.pp1.types.TypeInferenceEngine
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

class FactorVisitor: VisitorAdaptor() {
    var type = Tab.noType

    override fun visit(factorLiteral: FactorLiteral){
        type = TypeInferenceEngine.inferType(factorLiteral.literal)
    }
    override fun visit(factorIdent: FactorIdent){
        type = TypeInferenceEngine.inferType(factorIdent.designator)
    }

    override fun visit(nestedExpression: NestedExpression){
        type = TypeInferenceEngine.inferType(nestedExpression.expression)
    }
    override fun visit(factorNewType: FactorNewType){
        val newType = factorNewType.newType
        when(newType){
            is NewClass -> {
                type = TypeInferenceEngine.inferType(newType.type)
            }
            is NewArray -> {
                val expression = newType.expression
                val elemType = TypeInferenceEngine.inferType(newType.type)
                type = Struct(Struct.Array,elemType).takeIf {
                    expression.isOfType(Tab.intType)
                } ?: return
            }
        }
    }

    override fun visit(functionCall: FactorFunctionCall){
        val designator = functionCall.designator
        val functionObj = designator.getObj() ?: return

        if (!functionObj.isOfKinds(setOf(Obj.Meth))){
            return
        }
        if(functionObj.isFunctionCallValid(functionCall.actPars)){
            type = functionObj.type
        }
    }
}