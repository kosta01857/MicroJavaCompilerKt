package com.kosta.pp1.core

import com.kosta.pp1.MJParser
import com.kosta.pp1.Yylex
import com.kosta.pp1.ast.*
import com.kosta.pp1.codeGeneration.CodeGenVisitor
import com.kosta.pp1.codeGeneration.Generator
import com.kosta.pp1.semanticAnalysis.SemanticAnalyzer
import com.kosta.pp1.core.utils.Log4JUtils
import com.kosta.pp1.core.utils.myDumpSymbolTableVisitor
import com.kosta.pp1.core.utils.inScopeOf
import org.apache.log4j.Logger
import org.apache.log4j.xml.DOMConfigurator
import rs.etf.pp1.mj.runtime.Code
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct
import java.io.*


val ObjectNames = mapOf(
    Obj.Con to "Const",
    Obj.Var to "Variable",
    Obj.Type to "Type",
    Obj.Meth to "Method",
    Obj.Prog to "Program",
    Obj.Elem to "Array Element",
    Obj.Fld to "Field",
    Obj.NO_VALUE to "NO VALUE",
)

const val SET_TYPE_ID = 10


class CompilerKt {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val compiler = CompilerKt()
            compiler.compile()
        }

        init {
            Tab.init()
            DOMConfigurator.configure(Log4JUtils.findLoggerConfigFile())
            Log4JUtils.prepareLogFile(Logger.getRootLogger())
            val bool = Struct(Struct.Bool)
            val boolObj = Obj(Obj.Type, "bool", bool)
            val set = Struct(SET_TYPE_ID)
            val setObj = Obj(Obj.Type, "set", set)
            Tab.currentScope().addToLocals(boolObj)
            Tab.currentScope().addToLocals(setObj)
            val addObj: Obj
            val addAllObj: Obj
            Tab.currentScope().addToLocals(Obj(Obj.Meth, "add", Tab.noType, 0, 2).also {
                addObj = it
            })
            inScopeOf(addObj) {
                Tab.currentScope().addToLocals(Obj(Obj.Var, "a", Tab.find("set").type))
                Tab.currentScope().addToLocals(Obj(Obj.Var, "b", Tab.intType))
            }

            Tab.currentScope().addToLocals(Obj(Obj.Meth, "addAll", Tab.noType, 0, 2).also {
                addAllObj = it
            })
            inScopeOf(addAllObj) {
                Tab.currentScope().addToLocals(Obj(Obj.Var, "a", Tab.find("set").type))
                Tab.currentScope().addToLocals(Obj(Obj.Var, "b", Struct(Struct.Array, Tab.intType)))
            }
            Cache.init()
        }
    }

    val log = Logger.getLogger(CompilerKt::class.java)
    val mjFileName = "test301"
    var br: BufferedReader? = null
    fun compile() {
        val sourceCode = this::class.java.classLoader.getResource("$mjFileName.mj").toURI().let{ File(it) }
        log.info("Compiling source file: " + sourceCode.getAbsolutePath())

        br = BufferedReader(FileReader(sourceCode))
        br.use {
            val lexer = Yylex(br)

            val p = MJParser(lexer)

            val s = p.parse() // pocetak parsiranja
            val prog = s.value as Program
            log.info(prog.toString(""))

            log.info("===================================")

            // ispis prepoznatih programskih konstrukcija
            SemanticAnalyzer.programPass(prog)

             Tab.dump(myDumpSymbolTableVisitor())

            // log.info(" Print count calls = " + v.printCallCount)

            // log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount)
            if (SemanticAnalyzer.error) {
                log.info("semantic analysis failed!")
            } else {
                log.info("semantic analysis passed!")
                val objFile = File("build/generated/program.obj")
                if (objFile.exists()) {
                    objFile.delete()
                }
                val codeGenerator = CodeGenVisitor(Generator)
                prog.traverseBottomUp(codeGenerator)
                Code.dataSize = SemanticAnalyzer.globalVars
                Code.mainPc = codeGenerator.mainPc
                Code.write(FileOutputStream (objFile))
            }
        }
    }
}
