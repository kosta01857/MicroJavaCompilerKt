package com.kosta.pp1.semanticAnalysis

import com.kosta.pp1.ast.*
import com.kosta.pp1.semanticAnalysis.register.Register
import com.kosta.pp1.semanticAnalysis.register.RegisterStateClass
import com.kosta.pp1.semanticAnalysis.register.RegisterStateDefault
import com.kosta.pp1.semanticAnalysis.types.TypeInferenceEngine
import rs.etf.pp1.symboltable.Tab
import rs.etf.pp1.symboltable.concepts.Obj
import rs.etf.pp1.symboltable.concepts.Struct
import com.kosta.pp1.ast.Designator
import com.kosta.pp1.ast.SetDesignation
import com.kosta.pp1.core.*
import com.kosta.pp1.core.utils.Log4JUtils
import com.kosta.pp1.core.utils.extensions.*
import com.kosta.pp1.core.utils.inScopeOf
import com.kosta.pp1.core.utils.objExists
import com.kosta.pp1.core.utils.setType
import com.kosta.pp1.utils.*
import com.kosta.pp1.utils.extensions.*

const val IS_UNDECLARED = "is declared"
const val USE_OF_VARIABLE = "use of variable"

object SemanticAnalyzer {
    var type: Type = Type("")
    var error = false
    var globalVars = 0
    private var returnFound = false
    private var currentFunction: Obj = Tab.noObj
    private var inDoWhile = false


    fun programPass(program: Program) {
        val programNode = Register.registerProgram(program)
        inScopeOf(programNode) {
            val decls = program.declarations
            declarationsPass(decls)
            methodDeclarationsPass(program.methodDeclarations)
            globalVars = Tab.currentScope().getnVars()
            if (!findMainFunction()) Log4JUtils.reportError(
                "main function that matches requirements not found!", null
            )
        }
    }

    fun declarationsPass(declarationsObj: Declarations) =
        declarationsObj.findDeclarations().forEach(this::declarationPass)

    fun methodDeclarationsPass(methodDeclarations: MethodDeclarations) =
        methodDeclarations.findMethodDeclarations().forEach(this::methodDeclarationPass)

    fun statementsPass(statements: Statements) =
        statements.findStatements().forEach(this::statementPass)

    fun varDeclarationListPass(list: VarDeclarationList) =
        list.varDeclaration.findIdDeclarations()
            .forEach { Register.registerVar(it, TypeInferenceEngine.inferType(list.type)) }

    fun definitionListPass(constDeclarationList: ConstDeclarationList) =
        constDeclarationList.idDefinitionList.findIdDefinitions().forEach {
            Register.registerConst(it, constDeclarationList.type)
        }


    fun declarationPass(declaration: Declaration) {
        when (declaration) {
            is DeclarationVar -> varDeclarationListPass((declaration.varDeclarationListGlobal as GlobalVarDeclarationList).varDeclarationList)
            is DeclarationConst -> definitionListPass(declaration.constDeclarationList)
            is DeclarationClass -> classDeclarationPass(declaration.classDeclaration)
            is DeclarationInterface -> interfaceDeclarationPass(declaration.interfaceDeclaration)
        }
    }

    fun designatorStatementPass(dStatement: DesignatorStatement?) {
        when (dStatement) {
            is PostDec -> postDecPass(dStatement)
            is PostInc -> postIncPass(dStatement)
            is VarDesignation -> varDesignationPass(dStatement)
            is SetDesignation -> setDesignationPass(dStatement)
            is FunctionCall -> functionCallPass(dStatement)
        }
    }

    fun statementPass(statement: Statement) {
        when (statement) {
            is StatementBlock -> statementsPass(statement.statements)
            is DesignatorStmt -> designatorStatementPass(statement.designatorStatement)
            is WhileStmt -> whileStatementPass(statement.doWhile)
            is IfStmt -> ifStatementPass(statement.ifStatement)
            is Break -> if (!inDoWhile) Log4JUtils.reportError("cannot use break outside do while loop", statement)

            is Continue -> if (!inDoWhile) Log4JUtils.reportError(
                "cannot use continue outside do while loop",
                statement
            )

            is ReturnStmt -> returnStatementPass(statement.returnStatement)
            is PrintStmt -> printStatementPass(statement.printStatement)
            is ReadStatement -> readStatementPass(statement)
        }
    }


    fun classDeclarationPass(classDeclaration: ClassDeclaration) {
        val classObj: Obj = Register.registerClass(classDeclaration) ?: return
        Log4JUtils.reportInfo("Class declaration of name ${classObj.name}.", null)
        Register.setState(RegisterStateClass(classObj.type))
        inScopeOf(classObj) {
            val extendStruct = classObj.type.elemType
            val body = classDeclaration.findClassBody()
            classObj.type.inheritFromParent(extendStruct)
            this.classBodyPass(body)
            classObj.insertAllMembers()
        }
        Register.setState(RegisterStateDefault())

    }

    fun interfaceDeclarationPass(interfaceDeclaration: InterfaceDeclaration) {
        Log4JUtils.reportInfo("Interface declaration of name ${interfaceDeclaration.name}", null)
        val interfaceObj = Register.registerInterface(interfaceDeclaration) ?: return
        Register.setState(RegisterStateClass(interfaceObj.type))
        inScopeOf(interfaceObj) {
            val body: InterfaceBody = interfaceDeclaration.interfaceBody
            this.interfaceBodyPass(body)
            interfaceObj.insertAllMembers()
        }
        Register.setState(RegisterStateDefault())
    }

    private fun interfaceBodyPass(interfaceBody: InterfaceBody) {
        val interfaceElementsObj = interfaceBody.interfaceElements
        val interfaceElements = interfaceElementsObj.findInterfaceElements()
        interfaceElements.forEach(this::interfaceElementPass)
    }

    fun readStatementPass(statement: ReadStatement) {
        val designator = statement.designator
        val name = designator.name
        if (!objExists(name)) {
            Log4JUtils.reportError("Use of undeclared identifier: $name", statement)
            return // Exit early if the object doesn't exist
        }
        val designatorObj = Tab.find(name)
        var designatorType = designatorObj.type
        if (designatorType.isArray()) {
            designatorType = designatorType.elemType
        }
        if (!designatorType.isPrimitiveType() || !designatorObj.isOfKinds(setOf(Obj.Fld, Obj.Con, Obj.Var))) {

            Log4JUtils.reportError("Incorrect read call", statement)
        }
    }

    fun printStatementPass(printStatement: PrintStatement) {
        val expr = when (printStatement) {
            is PrintOne -> printStatement.expression
            is PrintTwo -> printStatement.expression
            else -> throw RuntimeException()
        }

        if (!expr.isOfTypes(kinds = setOf(Struct.Int, Struct.Bool, Struct.Char), structs = setOf(setType()))) {
            Log4JUtils.reportError("incorrect print call", printStatement)
        }
    }

    fun returnStatementPass(returnStatement: ReturnStatement) {
        when (returnStatement) {
            is ReturnVoid -> {
                if (!currentFunction.type.kindEquals(Struct.None)) {
                    Log4JUtils.reportError(
                        "This function must return a value of type ${currentFunction.type.typeName()}",
                        returnStatement
                    )
                }
            }

            is ReturnTyped -> {
                returnFound = true
                val expr = returnStatement.expression
                if (!expr.isOfType(currentFunction.type)) {
                    Log4JUtils.reportError(
                        "Declared return type of the function does not match the type of the value you are trying to return!",
                        returnStatement
                    )
                }
            }
        }
    }


    fun ifStatementPass(ifStatement: IfStatement) {
        when (ifStatement) {
            is IfOnly -> {
                conditionPass(ifStatement.condition)
                statementPass(ifStatement.statement)
            }

            is IfElse -> {
                conditionPass(ifStatement.condition)
                statementPass(statement = ifStatement.statement)
                statementPass(ifStatement.statement1)
            }
        }
    }

    fun whileStatementPass(doWhile: DoWhile) {
        inDoWhile = true
        when (doWhile) {
            is WhileCond -> {
                conditionPass(doWhile.condition)
                statementsPass(doWhile.statements)
            }

            is WhileSimple -> statementsPass(doWhile.statements)
            is WhileDesignator -> {
                statementsPass(doWhile.statements)
                conditionPass(doWhile.condition)
                designatorStatementPass(doWhile.designatorStatement)
            }
        }
        inDoWhile = false
    }

    fun conditionPass(condition: Condition) {
        if (!condition.isValid()) {
            Log4JUtils.reportError("bad conditional expression", condition)
        }
    }


    private fun incDecPass(designator: Designator) {
        val node = designator.getObj() ?: return
        val name = node.name
        Log4JUtils.reportInfo("use of variable $name", designator)
        if (node == Tab.noObj) {
            Log4JUtils.reportError("use of undeclared identifier $name", designator)
        }
        if (!node.isOfKinds(setOf(Obj.Var, Obj.Elem, Obj.Fld))) {
            Log4JUtils.reportError("use of undeclared identifier $name", designator)
        }
        if (!node.type.kindEquals(Struct.Int)) {
            Log4JUtils.reportError("cannot use this operator on the variable $name", designator)
        }
    }

    fun postDecPass(postDec: PostDec) {
        val designator = postDec.designator
        incDecPass(designator)
    }

    fun postIncPass(postInc: PostInc) {
        val designator = postInc.designator
        incDecPass(designator)
    }

    private fun varDesignationPass(varDesignation: VarDesignation) {
        val designator = varDesignation.designator
        val node = designator.getObj() ?: return
        if (!node.isOfKinds(setOf(Obj.Var, Obj.Fld, Obj.Elem))) {
            Log4JUtils.reportError(
                "Designator cannot be of type ${ObjectNames[node.kind]}",
                varDesignation
            )
            return
        }
        val nodeType = node.type
        val expr = varDesignation.expression
        val exprType = TypeInferenceEngine.inferType(expr)
        if (!exprType.isAssignableTo(nodeType)) {
            Log4JUtils.reportError("bad designation", varDesignation)
        }
        Log4JUtils.reportUse(node, varDesignation)
    }

    fun setDesignationPass(setDesignation: SetDesignation) {
        val leftD: Designator = setDesignation.designator
        val op1: Designator = setDesignation.designator1
        val op2: Designator = setDesignation.designator2
        if (!objExists(leftD.name)) {
            Log4JUtils.reportError("set  ${leftD.name}  $IS_UNDECLARED", leftD)
            return
        }
        if (!objExists(op1.name)) {
            Log4JUtils.reportError("set  ${op1.name}  $IS_UNDECLARED", leftD)
            return
        }
        if (!objExists(op2.name)) {
            Log4JUtils.reportError("set  ${op2.name}  $IS_UNDECLARED", leftD)
            return
        }
        val d1 = leftD.getObj() ?: return
        val d2 = op1.getObj() ?: return
        val d3 = op2.getObj() ?: return
        Log4JUtils.reportInfo("$USE_OF_VARIABLE ${d1.name}", leftD)
        Log4JUtils.reportInfo("$USE_OF_VARIABLE ${d2.name}", op1)
        Log4JUtils.reportInfo("$USE_OF_VARIABLE ${d3.name}", op2)
        if (!d1.type.kindEquals(SET_TYPE_ID)) {
            Log4JUtils.reportError("incorrect type for  ${d1.name} $IS_UNDECLARED", leftD)
        }
        if (!d2.type.kindEquals(SET_TYPE_ID)) {
            Log4JUtils.reportError("incorrect type for ${d2.name}  $IS_UNDECLARED", op1)
        }
        if (!d2.type.kindEquals(SET_TYPE_ID)) {
            Log4JUtils.reportError("incorrect type for ${d3.name}  $IS_UNDECLARED", op2)
        }
    }

    private fun functionCallPass(functionCall: FunctionCall) {
        val designator = functionCall.designator
        val name = designator.name
        if (!objExists(name)) {
            Log4JUtils.reportError("use of undeclared identifier $name", functionCall)
            return
        }
        val designatorObj = designator.getObj() ?: return
        if (!designatorObj.isOfKind(Obj.Meth)) {
            Log4JUtils.reportError("Designator " + designatorObj.name + " is not a function!", functionCall)
            return
        }
        currentFunction = designatorObj
        Log4JUtils.reportUse(designatorObj, functionCall)
        if (!currentFunction.isFunctionCallValid(functionCall.actPars)) {
            Log4JUtils.reportError(
                "Call of function " + currentFunction.name + " does not match its signature",
                functionCall.actPars
            )
        }

    }


    private fun interfaceElementPass(interfaceElement: InterfaceElement) {
        if (interfaceElement is AbstractMethodDecl) {
            val methodSignature = interfaceElement.methodSignature
            val funcObj = Register.registerMethod(methodSignature)
            inScopeOf(funcObj) {
                when (methodSignature) {
                    is MethodSignatureTyped -> Register.registerFunctionParameters(
                        methodSignature.functionArgumentList,
                        funcObj
                    )

                    is MethodSignatureVoid -> Register.registerFunctionParameters(
                        methodSignature.functionArgumentList,
                        funcObj
                    )
                }
            }
        } else {
            val methodDecl = (interfaceElement as InterfaceMethodDecl).methodDeclaration
            methodDeclarationPass(methodDecl)
        }
    }


    private fun classBodyPass(classBody: ClassBody) {
        val fieldListObj = classBody.classLocalVarDeclarationLists
        val methodListObj = classBody.classMethodDeclarations

        val fieldLists = fieldListObj.findVarDeclarationLists()
        fieldLists.forEach(this::varDeclarationListPass)
        if (methodListObj is ClassMethodDecls) {
            val methodDeclarations = methodListObj.methodDeclarations.findMethodDeclarations()
            methodDeclarations.forEach(this::methodDeclarationPass)
        }

    }


    fun methodDeclarationPass(methodDeclaration: MethodDeclaration) {
        val funcObj: Obj
        when (methodDeclaration) {
            is MethodDefinitionNoLocals -> {
                funcObj = Register.registerMethod(methodDeclaration.methodSignature)
                currentFunction = funcObj
                returnFound = false
                val args = methodDeclaration.methodSignature.findFunctionArgs()
                inScopeOf(funcObj) {
                    Register.registerFunctionParameters(args, currentFunction)
                    statementsPass(methodDeclaration.statements)
                }
            }

            is MethodDefinition -> {
                funcObj = Register.registerMethod(methodDeclaration.methodSignature)
                currentFunction = funcObj
                returnFound = false
                val args = methodDeclaration.methodSignature.findFunctionArgs()
                inScopeOf(funcObj) {
                    Register.registerFunctionParameters(args, currentFunction)
                    methodDeclaration.localVarDeclarationLists.findVarDeclarationLists()
                        .forEach(this::varDeclarationListPass)
                    statementsPass(methodDeclaration.statements)
                }
            }

            else -> funcObj = Tab.noObj
        }
        if (!funcObj.type.kindEquals(Tab.noType) && !returnFound) {
            Log4JUtils.reportError(
                "function " + funcObj.name + " must have a return statement", methodDeclaration
            )
        }
    }
}