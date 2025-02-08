package com.kosta.pp1.semanticAnalysis.register

import com.kosta.pp1.ast.*
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import com.kosta.pp1.core.utils.Log4JUtils
import com.kosta.pp1.core.utils.objExistsInScope
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

interface RegisterState {
    fun registerVar(name: String, struct: Struct): Obj?

    fun registerMethod(signature: MethodSignature): Obj

    fun registerFunctionParameters(argumentList: FunctionArgumentList, funcObj: Obj)

    fun insertMethod(signature: MethodSignature): Obj?

}