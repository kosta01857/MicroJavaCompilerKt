package com.kosta.pp1.extensions

import com.kosta.pp1.Cache
import com.kosta.pp1.Finder
import com.kosta.pp1.ast.*
import com.kosta.pp1.types.TypeInferenceEngine
import com.kosta.pp1.utils.*
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

fun Struct.kindEquals(otherStruct: Struct) = this.kind == otherStruct.kind
fun Struct.kindEquals(otherKind: Int) = this.kind == otherKind
fun Struct.kindEquals(otherKinds: Set<Int>) = otherKinds.any { this.kind == it }
fun Struct.isPrimitiveType() =
    true.takeIf { kindEquals(Struct.Bool) || kindEquals(Struct.Int) || kindEquals(Struct.Char) } ?: false

fun Struct.isClassOrInterface(): Boolean =
    true.takeIf { kindEquals(Struct.Class) || kindEquals(Struct.Interface) } ?: false

fun Struct.isArray() = true.takeIf { kind == Struct.Array } ?: false

/**
 * Converts Type to String
 *
 * @return String name of the type
 */
fun Struct.typeName(): String =
    when (this.kind) {
        1 -> "Integer"
        2 -> "Character"
        5 -> "Boolean"
        3 -> "Array of ${this.elemType.typeName()}s"
        4 -> "Class"
        7 -> "Interface"
        10 -> "set"
        else -> "None"
    }


/**
 * Returns int value of Literal. For bool -> 1 for true, 0 for false. For char,
 * it will return ASCII code
 */
fun Literal.getValue(): Int =
    when (this) {
        is CHAR -> `val`.code
        is NUMBER -> `val`
        is BOOL -> if (`val`) 1 else 0
        else -> -1

    }

fun Literal.isOfType(targetStruct: Struct): Boolean {
    val literalType = TypeInferenceEngine.inferType(this)
    return literalType.kindEquals(targetStruct)
}

fun Literal.isOfType(targetStructKind: Int): Boolean {
    val literalType = TypeInferenceEngine.inferType(this)
    return literalType.kindEquals(targetStructKind)
}

fun Expression.isOfTypes(structs: Set<Struct>? = null, kinds: Set<Int>? = null): Boolean {
    require(kinds != null || structs != null) { "At least one of 'structs' or 'kinds' must be provided" }
    return structs?.any(this::isOfType) ?: false || kinds?.any(this::isOfType) ?: false
}

fun Expression.isOfType(targetStruct: Struct): Boolean {
    val expressionType = TypeInferenceEngine.inferType(this)
    return expressionType.kindEquals(targetStruct)
}

fun Expression.isOfType(targetStructKind: Int): Boolean {
    val expressionType = TypeInferenceEngine.inferType(this)
    return expressionType.kindEquals(targetStructKind)
}

fun Expression.isValid(): Boolean = !this.isOfType(Tab.noType)


fun Condition.isValid(): Boolean {
    val conditionType = TypeInferenceEngine.inferType(this)
    return conditionType.kindEquals(Struct.Bool)
}

/**
 * Runs through the tail of the designator and returns the Obj of the final tail element
 * It also performs the semantic checks, and caches the result
 */
fun Designator.getObj(): Obj? {
    Cache.objMap[this]?.let { return it }
    val accessors = Finder.findAccessors(this.designatorTail)
    var currentObj = Tab.find(this.name)
    if (currentObj == Tab.noObj) {
        Log4JUtils.reportError("use of undeclared identifier ${this.name}", this)
        return null
    }
    if (accessors.isEmpty()) {
        Cache.objMap[this] = currentObj
        return currentObj
    }
    var currentType = currentObj.type
    if (!isRefType(currentType)) {
        Log4JUtils.reportError("the designator ${currentObj.name} is a non ref type", this)
        return null
    }
    accessors.forEach {
        when (it) {
            is MemberAccess -> {
                val (obj, struct) = handleMemberAccess(currentType, it) ?: return null
                currentObj = obj
                currentType = struct
            }

            is ArrayAccess -> currentType = handleArrayAccess(currentType, it) ?: return null
        }
    }
    if (!currentType.isArray() && currentObj.type.isArray()) {
        val elemObj = Obj(Obj.Elem, currentObj.name, currentType)
        Cache.elemToArrMap[elemObj] = currentObj
        Cache.designatorToExprMap[this] = (accessors.last() as ArrayAccess).expression
        currentObj = elemObj
    }
    Cache.objMap[this] = currentObj
    return currentObj
}

fun Obj.isOfKinds(kinds: Set<Int>): Boolean {
    return kinds.any { this.kind == it }
}

/**
 * checks if two class references are compatible in the polymorphic context
 * @param rightSide reference type on the right side of the = sign
 * @return returns true of the references(types) are compatible, false otherwise
 */
fun Struct.isCompatibleWithClass(rightSide: Struct): Boolean {
    require(this.isClassOrInterface() && rightSide.isClassOrInterface()) {
        "both arguments must be a class structs"
    }
    var rightSide: Struct? = rightSide
    while (rightSide != null) {
        if (this.compatibleWith(rightSide)) return true
        rightSide = rightSide.elemType
    }
    return false
}

fun Struct.isAssignableTo(leftSideStruct: Struct): Boolean {
    val rightSideStruct = this
    return when {
        leftSideStruct.isClassOrInterface() && this.isClassOrInterface() -> leftSideStruct.isCompatibleWithClass(
            rightSideStruct
        )

        else -> leftSideStruct.compatibleWith(rightSideStruct)
    }
}

fun Obj.isFunctionCallValid(parameters: ActPars): Boolean {
    require(this.isOfKinds(setOf(Obj.Meth))) {
        "object must represent a function"
    }

    val parameterTypeList = Cache.functionArgsTypeMap[this] ?: return false


    if (parameters !is ActParsConcrete) {
        return parameterTypeList.isEmpty()
    }

    val expressions = Finder.findExpressions(parameters.expressions)

    val isValid = expressions.zip(parameterTypeList).all { (expression, struct) ->
        expression.isOfType(struct)
    }
    if (!isValid) {
        Log4JUtils.reportError(
            "Call of function $name does not match its signature", parameters
        )
    }
    return isValid
}

fun Obj.addMember(member: Obj) {
    this.type.membersTable.insertKey(member)
}

fun Obj.addMembers(members: Collection<Obj>) = members.forEach { this.addMember(it) }
