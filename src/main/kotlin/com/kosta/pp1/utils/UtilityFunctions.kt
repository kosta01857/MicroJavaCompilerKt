package com.kosta.pp1.utils

import com.kosta.pp1.SET_TYPE_ID
import com.kosta.pp1.ast.ArrayAccess
import com.kosta.pp1.ast.MemberAccess
import com.kosta.pp1.extensions.addMembers
import com.kosta.pp1.extensions.isClassOrInterface
import com.kosta.pp1.extensions.isArray
import com.kosta.pp1.extensions.isOfType
import com.kosta.pp1.register.Register
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

fun objExists(objName: String) = true.takeUnless { Tab.find(objName) == Tab.noObj } ?: false
fun objExistsInScope(objName: String): Boolean{
    return true.takeUnless {
        Tab.currentScope.findSymbol(objName) == null
    } ?: false
}

/**
 *
 * @param memberName name of the class member
 * @param struct Struct you get from the Obj node of the microjava object
 * @return Obj node of the class member, null if no such member
 */
fun getMemberObj(memberName: String, struct: Struct): Obj? = struct.membersTable.searchKey(memberName)


fun isRefType(struct: Struct) = true.takeIf { struct.isArray() || struct.isClassOrInterface() } ?: false

fun setType() = Struct(SET_TYPE_ID)

inline fun  withScope(block: () -> Obj) {
    Tab.openScope()
    try{
        val obj = block()
        obj.addMembers(Tab.currentScope.locals.symbols())
        Tab.chainLocalSymbols(obj)
    }
    finally {
        Tab.closeScope()
    }
}
fun handleMemberAccess(targetStruct: Struct, accessor: MemberAccess): Pair<Obj,Struct>?{
    var currentType = targetStruct
    val currentObj: Obj
    val memberName = accessor.ident
    if (!currentType.isClassOrInterface()) {
        Log4JUtils.reportError("trying to access a field for a non object variable", accessor)
        return null
    }
    currentObj = if (Register.inClass) {
        Tab.currentScope.outer.findSymbol(memberName) ?: Tab.noObj
    } else {
        getMemberObj(memberName, currentType) ?: Tab.noObj
    }
    if (currentObj == Tab.noObj) {
        Log4JUtils.reportError("trying to access a field $memberName that doesnt exist", accessor)
        return null
    }
    currentType = currentObj.type
    return Pair(currentObj,currentType)
}

fun handleArrayAccess(arrayStruct: Struct, accessor: ArrayAccess): Struct?{
    var currentType = arrayStruct
    if (!currentType.isArray()) {
        Log4JUtils.reportError("cannot access array elements of non array type", accessor)
        return null
    }
    val expr = accessor.expression
    if (!expr.isOfType(Tab.intType)) {
        Log4JUtils.reportError("expression inside [] must be of type int", accessor)
        return null
    }
    currentType = currentType.elemType
    return currentType
}
