package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.ptg.AttrPtg;
import com.app.office.fc.hssf.formula.ptg.MemAreaPtg;
import com.app.office.fc.hssf.formula.ptg.MemErrPtg;
import com.app.office.fc.hssf.formula.ptg.MemFuncPtg;
import com.app.office.fc.hssf.formula.ptg.OperationPtg;
import com.app.office.fc.hssf.formula.ptg.ParenthesisPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import java.util.Stack;

public class FormulaRenderer {
    public static String toFormulaString(FormulaRenderingWorkbook formulaRenderingWorkbook, Ptg[] ptgArr) {
        if (ptgArr == null || ptgArr.length == 0) {
            throw new IllegalArgumentException("ptgs must not be null");
        }
        Stack stack = new Stack();
        for (AttrPtg attrPtg : ptgArr) {
            if (!(attrPtg instanceof MemAreaPtg) && !(attrPtg instanceof MemFuncPtg) && !(attrPtg instanceof MemErrPtg)) {
                if (attrPtg instanceof ParenthesisPtg) {
                    stack.push("(" + ((String) stack.pop()) + ")");
                } else if (attrPtg instanceof AttrPtg) {
                    AttrPtg attrPtg2 = attrPtg;
                    if (!attrPtg2.isOptimizedIf() && !attrPtg2.isOptimizedChoose() && !attrPtg2.isSkip() && !attrPtg2.isSpace() && !attrPtg2.isSemiVolatile()) {
                        if (attrPtg2.isSum()) {
                            stack.push(attrPtg2.toFormulaString(getOperands(stack, attrPtg2.getNumberOfOperands())));
                        } else {
                            throw new RuntimeException("Unexpected tAttr: " + attrPtg2.toString());
                        }
                    }
                } else if (attrPtg instanceof WorkbookDependentFormula) {
                    stack.push(((WorkbookDependentFormula) attrPtg).toFormulaString(formulaRenderingWorkbook));
                } else if (!(attrPtg instanceof OperationPtg)) {
                    stack.push(attrPtg.toFormulaString());
                } else {
                    OperationPtg operationPtg = (OperationPtg) attrPtg;
                    stack.push(operationPtg.toFormulaString(getOperands(stack, operationPtg.getNumberOfOperands())));
                }
            }
        }
        if (!stack.isEmpty()) {
            String str = (String) stack.pop();
            if (stack.isEmpty()) {
                return str;
            }
            throw new IllegalStateException("too much stuff left on the stack");
        }
        throw new IllegalStateException("Stack underflow");
    }

    private static String[] getOperands(Stack<String> stack, int i) {
        String[] strArr = new String[i];
        int i2 = i - 1;
        while (i2 >= 0) {
            if (!stack.isEmpty()) {
                strArr[i2] = stack.pop();
                i2--;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Too few arguments supplied to operation. Expected (");
                sb.append(i);
                sb.append(") operands but got (");
                sb.append((i - i2) - 1);
                sb.append(")");
                throw new IllegalStateException(sb.toString());
            }
        }
        return strArr;
    }
}
