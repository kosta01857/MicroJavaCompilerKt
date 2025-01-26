package com.kosta.pp1.semanticAnalysis.visitors

import com.kosta.pp1.ast.VisitorAdaptor
import rs.etf.pp1.symboltable.Tab

class ConditionVisitor: VisitorAdaptor() {
    var type = Tab.noType
}