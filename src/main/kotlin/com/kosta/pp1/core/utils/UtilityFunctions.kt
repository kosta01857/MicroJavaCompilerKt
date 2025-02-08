package com.kosta.pp1.core.utils

import com.kosta.pp1.core.Cache
import com.kosta.pp1.core.SET_TYPE_ID
import com.kosta.pp1.ast.ArrayAccess
import com.kosta.pp1.ast.Designator
import com.kosta.pp1.ast.MemberAccess
import com.kosta.pp1.core.utils.extensions.isClassOrInterface
import com.kosta.pp1.core.utils.extensions.isArray
import com.kosta.pp1.core.utils.extensions.isOfType
import com.kosta.pp1.semanticAnalysis.register.Register
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

fun objExists(objName: String) = true.takeUnless { Tab.find(objName) == Tab.noObj } ?: false
fun objExistsInScope(objName: String): Boolean {
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

inline fun inScopeOf(scopeOwner: Obj, block: () -> Unit) {
    Tab.openScope()
    try {
        block()
        Tab.chainLocalSymbols(scopeOwner)
    } finally {
        Tab.closeScope()
    }
}

fun handleMemberAccess(targetStruct: Struct, accessor: MemberAccess): Pair<Obj, Struct>? {
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
    return Pair(currentObj, currentType)
}

fun handleArrayElemet(currentObj: Obj, currentType: Struct, lastAccessor: ArrayAccess, designator: Designator): Obj {
    if (!currentType.isArray() && currentObj.type.isArray()) {
        val elemObj = Obj(Obj.Elem, currentObj.name, currentType)
        Cache.elemToArrMap[elemObj] = currentObj
        Cache.designatorToExprMap[designator] = lastAccessor.expression
        return elemObj
    }
    return currentObj
}

fun handleArrayAccess(arrayStruct: Struct, accessor: ArrayAccess): Struct? {
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

