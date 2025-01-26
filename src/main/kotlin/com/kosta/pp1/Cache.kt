package com.kosta.pp1

import com.kosta.pp1.ast.Designator
import com.kosta.pp1.ast.Expression
import com.kosta.pp1.ast.SyntaxNode
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct

/**
 * holds key -> value mappings , that describe the AST in more detail
 */

object Cache {

    /**
     * Fetches the mapping between an Object Node from the symbol table representing
     * a method
     * and the List containing the types of the method arguments
     * Types are represented as Struct Nodes from the symbol table
     */
    val functionArgsTypeMap = mutableMapOf<Obj, List<Struct>>()

    /**
     * Fetches the mapping between a SyntaxNode and its symbol table object node
     *
     */
    val objMap = mutableMapOf<SyntaxNode, Obj>()


    /**
     * Fetches the mapping between a SyntaxNode and its type
     * Type (represented as a Struct Node from the symbol table)
     */
    val designatorToExprMap = mutableMapOf<Designator, Expression>()

    /**
     * Fetches the mapping between an Elem Obj node and Obj node representing the
     * array itself
     */
    val elemToArrMap = mutableMapOf<Obj, Obj>()

    /**
     * Fetches the mapping between a SyntaxNode and its type
     * Type (represented as a Struct Node from the symbol table)
     */
    val typeMap = mutableMapOf<SyntaxNode, Struct>()





    private fun inBuiltFunctionsGetArgsTypes(name: String): List<Struct> {
        return when (name) {
            "chr" -> listOf(Tab.intType)
            "ord" -> listOf(Tab.charType)
            "len" -> listOf(Struct(Struct.Array))
            "add" -> listOf(Tab.find("set").type, Tab.intType)
            "addAll" -> listOf(Tab.find("set").type, Struct(Struct.Array, Tab.intType))
            else -> emptyList()
        }
    }

    /**
     * Fills the functionTypeMap with types for in-built methods
     */
    fun init() {
        val inBuiltFunctionNames = arrayOf("chr", "ord", "len", "add", "addAll")
        inBuiltFunctionNames.forEach {
            Cache.functionArgsTypeMap[Tab.find(it)] = inBuiltFunctionsGetArgsTypes(it)
        }
    }

}