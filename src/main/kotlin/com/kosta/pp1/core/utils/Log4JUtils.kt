package com.kosta.pp1.core.utils

import com.kosta.pp1.ast.SyntaxNode
import com.kosta.pp1.core.utils.extensions.typeName
import com.kosta.pp1.semanticAnalysis.SemanticAnalyzer
import org.apache.log4j.FileAppender
import org.apache.log4j.Logger
import rs.etf.pp1.symboltable.concepts.Obj
import java.io.File
import java.net.URL


object Log4JUtils {
    private val log = Logger.getLogger(SemanticAnalyzer::class.java)

    private const val LEVEL = " level: "
    private const val OF_TYPE = " of type "
    private const val AT_LINE = " at line "

    fun findLoggerConfigFile(): URL? = Thread.currentThread().contextClassLoader.getResource("log4j.xml")

    fun prepareLogFile(root: Logger) {
        val appender = root.getAppender("file")
        if (appender !is FileAppender) return
        val logFileName = appender.file.apply {
            this.substring(0, this.lastIndexOf(".")) + "-test.log"
        }
        val logFile = File(logFileName)
        val renamedFile = File(logFile.absolutePath+ "." + System.currentTimeMillis())
        if (logFile.exists() && !logFile.renameTo(renamedFile)) System.err.println("Could not rename log file")
        appender.file = logFile.absolutePath
        appender.activateOptions()
    }

    fun reportInfo(message: String, info: SyntaxNode?) {
        val msg = java.lang.StringBuilder(message)
        val line = if ((info == null)) 0 else info.line
        if (line != 0) msg.append(AT_LINE).append(line)
        log.info(msg.toString())
    }


    fun reportError(message: String?, info: SyntaxNode?) {
        val msg = StringBuilder(message)
        val line = if ((info == null)) 0 else info.line
        if (line != 0) msg.append(AT_LINE).append(line)
        log.error(msg.toString())
        SemanticAnalyzer.error = true
    }

    fun reportUse(`object`: Obj, node: SyntaxNode?) {
        val name = `object`.name
        val global = true.takeIf { `object`.level == 0 } ?: false
        when (`object`.kind) {
            0 -> {
                reportInfo(
                    ("use of constant variable " + name + OF_TYPE + `object`.type.typeName() + LEVEL
                            + `object`.level), node
                )
            }

            1 -> {
                reportInfo(
                    ("use of " + ("global".takeIf { global } ?: "local") + " variable " + name + OF_TYPE
                            + `object`.type.typeName() + LEVEL + `object`.level), node
                )
            }

            3 -> {
                reportInfo(
                    ("function call of name $name of return type " + `object`.type
                        .typeName() + " number of args: " + `object`.level), node
                )
            }
        }
    }


    fun reportDeclaration(`object`: Obj, node: SyntaxNode?) {
        val name = `object`.name
        when (`object`.kind) {
            0 -> {
                reportInfo(
                    ("Declaration of constant variable $name of type " + `object`.type
                        .typeName() + " level: " + `object`.level), node
                )
            }

            1 -> {
                reportInfo(
                    ("Declaration of variable " + name + " of type " + `object`.type.typeName() + " level: "
                            + `object`.level), node
                )
            }

            2 -> {
                reportInfo(
                    "Declaration of ${`object`.type.typeName()} of name $name level: ${`object`.level}", node
                )

            }

            3 -> {
                reportInfo(
                    ("Declaration of method $name of return type " + `object`.type
                        .typeName() + " number of args: " + `object`.level), node
                )
            }

            4 -> {
                reportInfo(
                    ("Declaration of field" + name + " of type " + `object`.type.typeName() + " level: "
                            + `object`.level), node
                )
            }
        }
    }

}