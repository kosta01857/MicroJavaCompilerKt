package com.kosta.pp1.semanticAnalysis.register

import com.kosta.pp1.core.Cache
import com.kosta.pp1.ast.*
import com.kosta.pp1.core.findFunctionParameters
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import com.kosta.pp1.core.utils.objExistsInScope
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
            parameters.findFunctionParameters().forEach {
                val struct = Cache.typeMap[it] ?: Tab.noType
                val paramObj = Register.registerVar(it, struct)
                paramObj.fpPos = cnt++
                structList.add(struct)
            }
        }
        funcObj.level = structList.size + 1
    }

    override fun insertMethod(signature: MethodSignature): Obj?{
        val returnStruct: Struct
        val name: String
        when (signature) {
            is MethodSignatureTyped -> {
                name = signature.methodName
                returnStruct = TypeInferenceEngine.inferType(signature.type)
            }

            is MethodSignatureVoid -> {
                name = signature.methodName
                returnStruct = Tab.noType
            }

            else -> {
                name = ""
                returnStruct = Tab.noType
            }
        }
        if(objExistsInScope(name)){
            Tab.currentScope.locals.deleteKey(name)
        }
        val funcObj = Tab.insert(Obj.Meth, name, returnStruct)
        return funcObj
    }
}