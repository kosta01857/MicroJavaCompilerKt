package com.kosta.pp1.codeGeneration.exprStack
import com.kosta.pp1.Cache
import com.kosta.pp1.ast.*
import com.kosta.pp1.codeGeneration.Generator
import com.kosta.pp1.utils.Log4JUtils
import com.kosta.pp1.utils.extensions.getValue
import rs.etf.pp1.mj.runtime.Code
import rs.etf.pp1.symboltable.concepts.Obj

class ExprVisitor: VisitorAdaptor() {
    override fun visit(addTermRecursive: AddTermRecursive) {
        val addOp = addTermRecursive.getAddop()
        if (addOp is Plus){
            Code.put(Code.add)
        }
        else{
            Code.put(Code.sub)
        }
    }

    override fun visit(tRecursive: TermRecursive) {
        val mulop = tRecursive.getMulop()
        when(mulop){
            is Mul -> Code.put(Code.mul)
            is Div -> Code.put(Code.div)
            is Mod -> Code.put(Code.rem)
        }
    }

    override fun visit(factor: FactorLiteral) {
        val literal = factor.literal
        Code.loadConst(literal.getValue())
    }

    override fun visit(nArray: NewArray) {
        Generator.allocArray(nArray)
    }

    public override fun visit(ident: FactorIdent) {
        val designator = ident.getDesignator()
        val obj = Cache.objMap[designator] ?: throw RuntimeException("obj map failed")
        Log4JUtils.reportInfo("obj is " + obj.getName(), ident)
        val arrayObj = Cache.elemToArrMap[obj]
        if (arrayObj != null) {
            val expr: Expression = Cache.designatorToExprMap[designator] ?: throw RuntimeException("designator to expr map failed")
            Generator.loadArrayElem(arrayObj, expr)
        } else {
            Code.load(obj)
        }
    }
}
