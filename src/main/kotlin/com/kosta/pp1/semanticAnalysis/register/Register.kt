package com.kosta.pp1.semanticAnalysis.register

import com.kosta.pp1.Cache
import com.kosta.pp1.ast.*
import com.kosta.pp1.utils.extensions.addMembers
import com.kosta.pp1.utils.extensions.getValue
import com.kosta.pp1.semanticAnalysis.SemanticAnalyzer
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import com.kosta.pp1.utils.Log4JUtils
import com.kosta.pp1.utils.objExists
import com.kosta.pp1.utils.objExistsInScope
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct


object Register {
    private var state: RegisterState = RegisterStateDefault()

    var inClass = false
    fun setState(newState: RegisterState) {
        inClass = when(newState){
            is RegisterStateDefault -> false
            else -> true
        }
        state = newState
    }

    fun registerProgram(program: Program): Obj {
        return Tab.insert(Obj.Prog, program.progName.name, Tab.noType)
    }

    fun registerVar(declaration: IdDeclaration, struct: Struct): Obj {
        var struct: Struct = struct
        val name: String
        when (declaration) {
            is IdDecl -> {
                name = declaration.identDecl.name
                if(objExistsInScope(name)){
                    Log4JUtils.reportError("Variable with name $name was already declared!", declaration)
                    return Tab.noObj
                }
            }
            is ArrayDecl -> {
                name = declaration.arrayDeclaration.name
                if(objExistsInScope(name)){
                    Log4JUtils.reportError("Variable with name $name was already declared!", declaration)
                    return Tab.noObj
                }
                struct = Struct(Struct.Array,struct)
            }
            else -> return Tab.noObj
        }
        val obj = state.registerVar(name, struct) ?: Tab.noObj
        Log4JUtils.reportDeclaration(obj,declaration)
        Cache.objMap[declaration] = obj
        return obj
    }

    fun registerMethod(methodSignature: MethodSignature): Obj {
        val obj = state.registerMethod(methodSignature)
        Log4JUtils.reportDeclaration(obj,methodSignature)
        Cache.objMap[methodSignature] = obj
        return obj
    }

    fun registerConst(def: IdDefinition, type: Type): Obj? {
        val name = def.name
        if(objExists(name)){
            Log4JUtils.reportError("Variable with name $name was already defined!", def)
            return null
        }
        val structType = TypeInferenceEngine.inferType(type)
        val node = Tab.insert(Obj.Con, name, structType)
        node.adr = def.literal.getValue()
        Log4JUtils.reportDeclaration(node,def)
        Cache.objMap[def] = node
        return node
    }

    fun registerFunctionParameters(argumentList: FunctionArgumentList,funcObj: Obj){
        state.registerFunctionParameters(argumentList,funcObj)
    }

    fun registerClass(classDecl: ClassDeclaration): Obj? {
        val classObj: Obj
        val classStruct = Struct(Struct.Class)
        if (classDecl is ClassDeclarationNoExtend) {
            val className = classDecl.name
            if(objExists(className)){
                Log4JUtils.reportError("identifier $className already exists!", classDecl)
                return null
            }
            classObj = Tab.insert(Obj.Type, className, classStruct)
        } else {
            val classD = classDecl as ClassDeclarationExtend
            val className = classD.name
            if(objExists(className)){
                Log4JUtils.reportError("identifier $className already exists!", classDecl)
                return null
            }
            val extendStruct = TypeInferenceEngine.inferType(classD.type)
            if (extendStruct == Tab.noType) {
                Log4JUtils.reportError("parent class of class $className doesnt exist!", classDecl)
                return null
            }
            classStruct.setElementType(extendStruct)
            classObj = Tab.insert(Obj.Type,className,classStruct)
        }
        Log4JUtils.reportDeclaration(classObj,classDecl)
        Cache.objMap[classDecl] = classObj
        return classObj
    }

    fun registerInterface(interfaceDecl: InterfaceDeclaration): Obj? {
        val interfaceObj: Obj
        val interfaceName = interfaceDecl.name
        if(objExists(interfaceName)){
            Log4JUtils.reportError("identifier $interfaceName already exists!", interfaceDecl)
            return null
        }
        val interfaceStruct = Struct(Struct.Interface)
        interfaceObj = Tab.insert(Obj.Type, interfaceName, interfaceStruct)
        Log4JUtils.reportDeclaration(interfaceObj,interfaceDecl)
        Cache.objMap[interfaceDecl] = interfaceObj
        return interfaceObj
    }
}
