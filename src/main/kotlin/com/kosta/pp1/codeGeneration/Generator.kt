package com.kosta.pp1.codeGeneration

import com.kosta.pp1.core.Cache
import com.kosta.pp1.ast.Expression
import com.kosta.pp1.ast.NewArray
import com.kosta.pp1.ast.Statements
import com.kosta.pp1.ast.Type
import com.kosta.pp1.ast.VarDesignation
import com.kosta.pp1.codeGeneration.executors.StatementExecutor
import com.kosta.pp1.codeGeneration.exprStack.ExprStack
import com.kosta.pp1.core.findStatements
import com.kosta.pp1.core.utils.Log4JUtils
import com.kosta.pp1.core.utils.extensions.kindEquals
import com.kosta.pp1.core.utils.extensions.size
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import rs.etf.pp1.mj.runtime.Code
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

object Generator {
    fun enterFunction(b1: Int, b2: Int) {
        Code.put(Code.enter);
        Code.put(b1);
        Code.put(b2);
    }


    fun exitFunction() {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }
    fun storeArrayElem(dStatement: VarDesignation, elemObj: Obj){
        Code.put(Code.load)
        Code.put2(2 + elemObj.adr)
        val index = Cache.designatorToExprMap[dStatement.designator]
        ExprStack.pushExpression(index)
        ExprStack.pushExpression(dStatement.expression)
        Code.put(Code.astore)
        Code.put(Code.pop) // because 0 remains after the op, because we use put2
    }

    /**
     * Generates bytecode that loads the array element on the expr stack
     * @param expr Expression that evalutes to the index of the array element
     * */
    fun loadArrayElem(arrayObj: Obj, expr: Expression) {
        Code.put(Code.pop) // to wipe out the initial index that will be put by the expr visitor, because [expression] is nested
        Code.load(arrayObj)
        ExprStack.pushExpression(expr)
        Log4JUtils.reportInfo("aload",expr)
        Code.put(Code.aload)
    }

    fun allocArray(nArray: NewArray) {
        val type = TypeInferenceEngine.inferType(nArray.type)
        Code.loadConst(type.size())
        Code.put(Code.mul)
        Code.put(Code.newarray)
        Code.put(if (type.kindEquals(Struct.Char)) 0 else 1)
    }

    fun executeStatements(statements: Statements) {
        StatementExecutor.executeStatements(statements.findStatements())
    }
}
