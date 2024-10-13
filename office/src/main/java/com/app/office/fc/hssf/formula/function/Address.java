package com.app.office.fc.hssf.formula.function;

public class Address implements Function {
    public static final int REF_ABSOLUTE = 1;
    public static final int REF_RELATIVE = 4;
    public static final int REF_ROW_ABSOLUTE_COLUMN_RELATIVE = 2;
    public static final int REF_ROW_RELATIVE_RELATIVE_ABSOLUTE = 3;

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0044 A[Catch:{ EvaluationException -> 0x0093 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005a A[Catch:{ EvaluationException -> 0x0093 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007a A[Catch:{ EvaluationException -> 0x0093 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.fc.hssf.formula.eval.ValueEval evaluate(com.app.office.fc.hssf.formula.eval.ValueEval[] r11, int r12, int r13) {
        /*
            r10 = this;
            int r0 = r11.length
            r1 = 2
            if (r0 < r1) goto L_0x0099
            int r0 = r11.length
            r2 = 5
            if (r0 <= r2) goto L_0x000a
            goto L_0x0099
        L_0x000a:
            r0 = 0
            r3 = r11[r0]     // Catch:{ EvaluationException -> 0x0093 }
            double r3 = com.app.office.fc.hssf.formula.function.NumericFunction.singleOperandEvaluate(r3, r12, r13)     // Catch:{ EvaluationException -> 0x0093 }
            int r3 = (int) r3     // Catch:{ EvaluationException -> 0x0093 }
            r4 = 1
            r5 = r11[r4]     // Catch:{ EvaluationException -> 0x0093 }
            double r5 = com.app.office.fc.hssf.formula.function.NumericFunction.singleOperandEvaluate(r5, r12, r13)     // Catch:{ EvaluationException -> 0x0093 }
            int r5 = (int) r5     // Catch:{ EvaluationException -> 0x0093 }
            int r6 = r11.length     // Catch:{ EvaluationException -> 0x0093 }
            if (r6 <= r1) goto L_0x0025
            r6 = r11[r1]     // Catch:{ EvaluationException -> 0x0093 }
            double r6 = com.app.office.fc.hssf.formula.function.NumericFunction.singleOperandEvaluate(r6, r12, r13)     // Catch:{ EvaluationException -> 0x0093 }
            int r6 = (int) r6     // Catch:{ EvaluationException -> 0x0093 }
            goto L_0x0026
        L_0x0025:
            r6 = 1
        L_0x0026:
            r7 = 4
            r8 = 3
            if (r6 == r4) goto L_0x003f
            if (r6 == r1) goto L_0x003c
            if (r6 == r8) goto L_0x003a
            if (r6 != r7) goto L_0x0032
            r1 = 0
            goto L_0x003d
        L_0x0032:
            com.app.office.fc.hssf.formula.eval.EvaluationException r11 = new com.app.office.fc.hssf.formula.eval.EvaluationException     // Catch:{ EvaluationException -> 0x0093 }
            com.app.office.fc.hssf.formula.eval.ErrorEval r12 = com.app.office.fc.hssf.formula.eval.ErrorEval.VALUE_INVALID     // Catch:{ EvaluationException -> 0x0093 }
            r11.<init>(r12)     // Catch:{ EvaluationException -> 0x0093 }
            throw r11     // Catch:{ EvaluationException -> 0x0093 }
        L_0x003a:
            r1 = 0
            goto L_0x0040
        L_0x003c:
            r1 = 1
        L_0x003d:
            r6 = 0
            goto L_0x0041
        L_0x003f:
            r1 = 1
        L_0x0040:
            r6 = 1
        L_0x0041:
            int r9 = r11.length     // Catch:{ EvaluationException -> 0x0093 }
            if (r9 <= r8) goto L_0x0056
            r8 = r11[r8]     // Catch:{ EvaluationException -> 0x0093 }
            com.app.office.fc.hssf.formula.eval.ValueEval r8 = com.app.office.fc.hssf.formula.eval.OperandResolver.getSingleValue(r8, r12, r13)     // Catch:{ EvaluationException -> 0x0093 }
            com.app.office.fc.hssf.formula.eval.MissingArgEval r9 = com.app.office.fc.hssf.formula.eval.MissingArgEval.instance     // Catch:{ EvaluationException -> 0x0093 }
            if (r8 != r9) goto L_0x004f
            goto L_0x0056
        L_0x004f:
            java.lang.Boolean r0 = com.app.office.fc.hssf.formula.eval.OperandResolver.coerceValueToBoolean(r8, r0)     // Catch:{ EvaluationException -> 0x0093 }
            r0.booleanValue()     // Catch:{ EvaluationException -> 0x0093 }
        L_0x0056:
            int r0 = r11.length     // Catch:{ EvaluationException -> 0x0093 }
            r8 = 0
            if (r0 != r2) goto L_0x006a
            r11 = r11[r7]     // Catch:{ EvaluationException -> 0x0093 }
            com.app.office.fc.hssf.formula.eval.ValueEval r11 = com.app.office.fc.hssf.formula.eval.OperandResolver.getSingleValue(r11, r12, r13)     // Catch:{ EvaluationException -> 0x0093 }
            com.app.office.fc.hssf.formula.eval.MissingArgEval r12 = com.app.office.fc.hssf.formula.eval.MissingArgEval.instance     // Catch:{ EvaluationException -> 0x0093 }
            if (r11 != r12) goto L_0x0065
            goto L_0x006a
        L_0x0065:
            java.lang.String r11 = com.app.office.fc.hssf.formula.eval.OperandResolver.coerceValueToString(r11)     // Catch:{ EvaluationException -> 0x0093 }
            r8 = r11
        L_0x006a:
            com.app.office.fc.ss.util.CellReference r11 = new com.app.office.fc.ss.util.CellReference     // Catch:{ EvaluationException -> 0x0093 }
            int r3 = r3 - r4
            int r5 = r5 - r4
            r11.<init>(r3, r5, r1, r6)     // Catch:{ EvaluationException -> 0x0093 }
            java.lang.StringBuffer r12 = new java.lang.StringBuffer     // Catch:{ EvaluationException -> 0x0093 }
            r13 = 32
            r12.<init>(r13)     // Catch:{ EvaluationException -> 0x0093 }
            if (r8 == 0) goto L_0x0082
            com.app.office.fc.hssf.formula.SheetNameFormatter.appendFormat(r12, r8)     // Catch:{ EvaluationException -> 0x0093 }
            r13 = 33
            r12.append(r13)     // Catch:{ EvaluationException -> 0x0093 }
        L_0x0082:
            java.lang.String r11 = r11.formatAsString()     // Catch:{ EvaluationException -> 0x0093 }
            r12.append(r11)     // Catch:{ EvaluationException -> 0x0093 }
            com.app.office.fc.hssf.formula.eval.StringEval r11 = new com.app.office.fc.hssf.formula.eval.StringEval     // Catch:{ EvaluationException -> 0x0093 }
            java.lang.String r12 = r12.toString()     // Catch:{ EvaluationException -> 0x0093 }
            r11.<init>((java.lang.String) r12)     // Catch:{ EvaluationException -> 0x0093 }
            return r11
        L_0x0093:
            r11 = move-exception
            com.app.office.fc.hssf.formula.eval.ErrorEval r11 = r11.getErrorEval()
            return r11
        L_0x0099:
            com.app.office.fc.hssf.formula.eval.ErrorEval r11 = com.app.office.fc.hssf.formula.eval.ErrorEval.VALUE_INVALID
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.function.Address.evaluate(com.app.office.fc.hssf.formula.eval.ValueEval[], int, int):com.app.office.fc.hssf.formula.eval.ValueEval");
    }
}
