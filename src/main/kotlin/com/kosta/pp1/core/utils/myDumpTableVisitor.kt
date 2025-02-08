package com.kosta.pp1.core.utils

import com.kosta.pp1.core.SET_TYPE_ID
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Scope
import rs.etf.pp1.symboltable.concepts.Struct
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor

class myDumpSymbolTableVisitor : SymbolTableVisitor() {
    protected var output: StringBuilder = StringBuilder()
    protected val indent: String = "   "
    protected var currentIndent: StringBuilder = StringBuilder()

    protected fun nextIndentationLevel() {
        currentIndent.append(indent)
    }

    protected fun previousIndentationLevel() {
        if (currentIndent.length > 0) currentIndent.setLength(currentIndent.length - indent.length)
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * rs.etf.pp1.symboltable.test.SymbolTableVisitor#visitObjNode(symboltable.Obj)
	 */
    override fun visitObjNode(objToVisit: Obj) {
        // output.append("[");
        when (objToVisit.kind) {
            Obj.Con -> output.append("Con ")
            Obj.Var -> output.append("Var ")
            Obj.Type -> output.append("Type ")
            Obj.Meth -> output.append("Meth ")
            Obj.Fld -> output.append("Fld ")
            Obj.Prog -> output.append("Prog ")
        }
        output.append(objToVisit.name)
        output.append(": ")

        if ((Obj.Var == objToVisit.kind) && "this".equals(objToVisit.name, ignoreCase = true)) output.append("")
        else objToVisit.type.accept(this)

        output.append(", ")
        output.append(objToVisit.adr)
        output.append(", ")
        output.append(objToVisit.level.toString() + " ")

        if (objToVisit.kind == Obj.Prog || objToVisit.kind == Obj.Meth) {
            output.append("\n")
            nextIndentationLevel()
        }

        for (o in objToVisit.localSymbols) {
            output.append(currentIndent.toString())
            o.accept(this)
            output.append("\n")
        }

        if (objToVisit.kind == Obj.Prog || objToVisit.kind == Obj.Meth) previousIndentationLevel()

        // output.append("]");
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * rs.etf.pp1.symboltable.test.SymbolTableVisitor#visitScopeNode(symboltable.
	 * Scope)
	 */
    override fun visitScopeNode(scope: Scope) {
        for (o in scope.values()) {
            o.accept(this)
            output.append("\n")
        }
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * rs.etf.pp1.symboltable.test.SymbolTableVisitor#visitStructNode(symboltable.
	 * Struct)
	 */
    override fun visitStructNode(structToVisit: Struct) {
        when (structToVisit.kind) {
            Struct.None -> output.append("notype")
            Struct.Int -> output.append("int")
            Struct.Char -> output.append("char")
            Struct.Bool -> output.append("bool")
            Struct.Array -> {
                output.append("Arr of ")
                when (structToVisit.elemType.kind) {
                    Struct.None -> output.append("notype")
                    Struct.Int -> output.append("int")
                    Struct.Char -> output.append("char")
                    Struct.Bool -> output.append("bool")
                    Struct.Class -> output.append("Class")
                }
            }

            Struct.Class -> {
                output.append("Class [")
                for (obj in structToVisit.members) {
                    obj.accept(this)
                }
                output.append("]")
            }

            Struct.Interface -> {
                output.append("Interface [")
                for (obj in structToVisit.members) {
                    obj.accept(this)
                }
                output.append("]")
            }

            SET_TYPE_ID -> output.append("set")
        }
    }

    override fun getOutput(): String {
        return output.toString()
    }
}