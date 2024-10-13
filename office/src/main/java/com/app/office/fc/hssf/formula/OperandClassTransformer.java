package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.ptg.AbstractFunctionPtg;
import com.app.office.fc.hssf.formula.ptg.AttrPtg;
import com.app.office.fc.hssf.formula.ptg.ControlPtg;
import com.app.office.fc.hssf.formula.ptg.FuncVarPtg;
import com.app.office.fc.hssf.formula.ptg.MemAreaPtg;
import com.app.office.fc.hssf.formula.ptg.MemFuncPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.RangePtg;
import com.app.office.fc.hssf.formula.ptg.UnionPtg;
import com.app.office.fc.hssf.formula.ptg.ValueOperatorPtg;

final class OperandClassTransformer {
    private final int _formulaType;

    public OperandClassTransformer(int i) {
        this._formulaType = i;
    }

    public void transformFormula(ParseNode parseNode) {
        byte b;
        int i = this._formulaType;
        if (i == 0) {
            b = 32;
        } else if (i == 2) {
            b = 64;
        } else if (i == 4 || i == 5) {
            b = 0;
        } else {
            throw new RuntimeException("Incomplete code - formula type (" + this._formulaType + ") not supported yet");
        }
        transformNode(parseNode, b, false);
    }

    private void transformNode(ParseNode parseNode, byte b, boolean z) {
        Ptg token = parseNode.getToken();
        ParseNode[] children = parseNode.getChildren();
        int i = 0;
        if (isSimpleValueFunction(token)) {
            boolean z2 = b == 64;
            while (i < children.length) {
                transformNode(children[i], b, z2);
                i++;
            }
            setSimpleValueFuncClass((AbstractFunctionPtg) token, b, z);
            return;
        }
        if (isSingleArgSum(token)) {
            token = FuncVarPtg.SUM;
        }
        if ((token instanceof ValueOperatorPtg) || (token instanceof ControlPtg) || (token instanceof MemFuncPtg) || (token instanceof MemAreaPtg) || (token instanceof UnionPtg)) {
            if (b == 0) {
                b = 32;
            }
            while (i < children.length) {
                transformNode(children[i], b, z);
                i++;
            }
        } else if (token instanceof AbstractFunctionPtg) {
            transformFunctionNode((AbstractFunctionPtg) token, children, b, z);
        } else if (children.length > 0) {
            if (token != RangePtg.instance) {
                throw new IllegalStateException("Node should not have any children");
            }
        } else if (!token.isBaseToken()) {
            token.setClass(transformClass(token.getPtgClass(), b, z));
        }
    }

    private static boolean isSingleArgSum(Ptg ptg) {
        if (ptg instanceof AttrPtg) {
            return ((AttrPtg) ptg).isSum();
        }
        return false;
    }

    private static boolean isSimpleValueFunction(Ptg ptg) {
        if (!(ptg instanceof AbstractFunctionPtg)) {
            return false;
        }
        AbstractFunctionPtg abstractFunctionPtg = (AbstractFunctionPtg) ptg;
        if (abstractFunctionPtg.getDefaultOperandClass() != 32) {
            return false;
        }
        for (int numberOfOperands = abstractFunctionPtg.getNumberOfOperands() - 1; numberOfOperands >= 0; numberOfOperands--) {
            if (abstractFunctionPtg.getParameterClass(numberOfOperands) != 32) {
                return false;
            }
        }
        return true;
    }

    private byte transformClass(byte b, byte b2, boolean z) {
        if (b2 != 0) {
            if (b2 != 32) {
                if (b2 != 64) {
                    throw new IllegalStateException("Unexpected operand class (" + b2 + ")");
                }
            } else if (!z) {
                return 32;
            }
            return 64;
        } else if (!z) {
            return b;
        } else {
            return 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0070, code lost:
        if (r0 == 32) goto L_0x00b5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b8 A[LOOP:0: B:34:0x00b5->B:36:0x00b8, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void transformFunctionNode(com.app.office.fc.hssf.formula.ptg.AbstractFunctionPtg r8, com.app.office.fc.hssf.formula.ParseNode[] r9, byte r10, boolean r11) {
        /*
            r7 = this;
            byte r0 = r8.getDefaultOperandClass()
            r1 = 1
            java.lang.String r2 = ")"
            java.lang.String r3 = "Unexpected operand class ("
            r4 = 32
            r5 = 64
            r6 = 0
            if (r11 == 0) goto L_0x0041
            if (r0 == 0) goto L_0x0037
            if (r0 == r4) goto L_0x0032
            if (r0 != r5) goto L_0x001a
            r8.setClass(r5)
            goto L_0x0073
        L_0x001a:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            r9.append(r0)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x0032:
            r8.setClass(r5)
            goto L_0x00b5
        L_0x0037:
            if (r10 != 0) goto L_0x003d
            r8.setClass(r6)
            goto L_0x0073
        L_0x003d:
            r8.setClass(r5)
            goto L_0x0073
        L_0x0041:
            if (r0 != r10) goto L_0x0047
            r8.setClass(r0)
            goto L_0x0073
        L_0x0047:
            if (r10 == 0) goto L_0x0091
            if (r10 == r4) goto L_0x008d
            if (r10 != r5) goto L_0x0075
            if (r0 == 0) goto L_0x006d
            if (r0 != r4) goto L_0x0055
            r8.setClass(r5)
            goto L_0x0070
        L_0x0055:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            r9.append(r0)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x006d:
            r8.setClass(r6)
        L_0x0070:
            if (r0 != r4) goto L_0x0073
            goto L_0x00b5
        L_0x0073:
            r1 = 0
            goto L_0x00b5
        L_0x0075:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            r9.append(r10)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x008d:
            r8.setClass(r4)
            goto L_0x0073
        L_0x0091:
            if (r0 == r4) goto L_0x00b1
            if (r0 != r5) goto L_0x0099
            r8.setClass(r5)
            goto L_0x0073
        L_0x0099:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r3)
            r9.append(r0)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            throw r8
        L_0x00b1:
            r8.setClass(r4)
            goto L_0x0073
        L_0x00b5:
            int r10 = r9.length
            if (r6 >= r10) goto L_0x00c4
            r10 = r9[r6]
            byte r11 = r8.getParameterClass(r6)
            r7.transformNode(r10, r11, r1)
            int r6 = r6 + 1
            goto L_0x00b5
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.OperandClassTransformer.transformFunctionNode(com.app.office.fc.hssf.formula.ptg.AbstractFunctionPtg, com.app.office.fc.hssf.formula.ParseNode[], byte, boolean):void");
    }

    private void setSimpleValueFuncClass(AbstractFunctionPtg abstractFunctionPtg, byte b, boolean z) {
        if (z || b == 64) {
            abstractFunctionPtg.setClass((byte) 64);
        } else {
            abstractFunctionPtg.setClass((byte) 32);
        }
    }
}
