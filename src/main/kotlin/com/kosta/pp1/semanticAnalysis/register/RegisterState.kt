package com.kosta.pp1.semanticAnalysis.register

import com.kosta.pp1.ast.*
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import com.kosta.pp1.utils.Log4JUtils
import com.kosta.pp1.utils.objExistsInScope
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

interface RegisterState {
    fun registerVar(name: String, struct: Struct): Obj?

    fun registerMethod(signature: MethodSignature): Obj

    fun registerFunctionParameters(argumentList: FunctionArgumentList, funcObj: Obj)

    fun insertMethod(signature: MethodSignature): Obj?{
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