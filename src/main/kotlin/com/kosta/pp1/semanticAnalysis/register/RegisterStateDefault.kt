package com.kosta.pp1.semanticAnalysis.register

import com.kosta.pp1.core.Cache
import com.kosta.pp1.ast.*
import com.kosta.pp1.core.findFunctionParameters
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import com.kosta.pp1.core.utils.Log4JUtils
import com.kosta.pp1.core.utils.objExistsInScope
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

class RegisterStateDefault : RegisterState {
    override fun registerVar(name: String, struct: Struct): Obj? {
        val obj = Tab.insert(Obj.Var, name, struct)
        return obj
    }

    override fun registerMethod(signature: MethodSignature): Obj {
        val funcObj = insertMethod(signature) ?: return Tab.noObj
        return funcObj
    }

    override fun registerFunctionParameters(argumentList: FunctionArgumentList, funcObj: Obj) {
        val structList = mutableListOf<Struct>()
        Cache.functionArgsTypeMap[funcObj] = structList
        if (argumentList is FuncPars) {
            val parameters = argumentList.functionParameters
            var cnt = 0
            parameters.findFunctionParameters().forEach {

                val struct = Cache.typeMap[it] ?: Tab.noType
                val paramObj = Register.registerVar(it, struct)
                paramObj.fpPos = cnt++
                structList.add(struct)
            }
        }
        funcObj.level = structList.size
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
            Log4JUtils.reportError("method of name $name is already defined in this scope",signature)
            return null
        }
        val funcObj = Tab.insert(Obj.Meth, name, returnStruct)
        return funcObj
    }

}