package com.kosta.pp1.register

import com.kosta.pp1.Cache
import com.kosta.pp1.Finder
import com.kosta.pp1.ast.*
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

class RegisterStateClass(val classStruct: Struct) : RegisterState {
    var passedFields = false
    override fun registerVar(name: String, struct: Struct): Obj? {
        val obj = Tab.insert(Obj.Var.takeIf { passedFields } ?: Obj.Fld, name, struct)
        if (!passedFields) obj.level = 2
        return obj
    }

    override fun registerMethod(signature: MethodSignature): Obj {
        passedFields = true
        val funcObj = insertMethod(signature) ?: return Tab.noObj
        return funcObj
    }

    override fun registerFunctionParameters(argumentList: FunctionArgumentList, funcObj: Obj) {
        val thisObj = Tab.insert(Obj.Var, "this", classStruct)
        var cnt = 0
        thisObj.level = cnt++
        val structList = mutableListOf<Struct>()
        Cache.functionArgsTypeMap[funcObj] = structList
        if (argumentList is FuncPars) {
            val parameters = argumentList.functionParameters
            Finder.findFunctionParameters(parameters).forEach {
                val struct = Cache.typeMap[it] ?: Tab.noType
                val paramObj = Register.registerVar(it,struct)
                paramObj.fpPos = cnt++
                structList.add(struct)
            }
        }
        funcObj.level = structList.size
    }
}