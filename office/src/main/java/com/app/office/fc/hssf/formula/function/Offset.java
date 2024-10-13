package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

public final class Offset implements Function {
    private static final int LAST_VALID_COLUMN_INDEX = 255;
    private static final int LAST_VALID_ROW_INDEX = 65535;

    static final class LinearOffsetRange {
        private final int _length;
        private final int _offset;

        public LinearOffsetRange(int i, int i2) {
            if (i2 != 0) {
                this._offset = i;
                this._length = i2;
                return;
            }
            throw new RuntimeException("length may not be zero");
        }

        public short getFirstIndex() {
            return (short) this._offset;
        }

        public short getLastIndex() {
            return (short) ((this._offset + this._length) - 1);
        }

        public LinearOffsetRange normaliseAndTranslate(int i) {
            int i2 = this._length;
            if (i2 <= 0) {
                return new LinearOffsetRange(i + this._offset + i2 + 1, -i2);
            }
            if (i == 0) {
                return this;
            }
            return new LinearOffsetRange(i + this._offset, i2);
        }

        public boolean isOutOfBounds(int i, int i2) {
            if (this._offset >= i && getLastIndex() <= i2) {
                return false;
            }
            return true;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(this._offset);
            stringBuffer.append("...");
            stringBuffer.append(getLastIndex());
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private static final class BaseRef {
        private final AreaEval _areaEval;
        private final int _firstColumnIndex;
        private final int _firstRowIndex;
        private final int _height;
        private final RefEval _refEval;
        private final int _width;

        public BaseRef(RefEval refEval) {
            this._refEval = refEval;
            this._areaEval = null;
            this._firstRowIndex = refEval.getRow();
            this._firstColumnIndex = refEval.getColumn();
            this._height = 1;
            this._width = 1;
        }

        public BaseRef(AreaEval areaEval) {
            this._refEval = null;
            this._areaEval = areaEval;
            this._firstRowIndex = areaEval.getFirstRow();
            this._firstColumnIndex = areaEval.getFirstColumn();
            this._height = (areaEval.getLastRow() - areaEval.getFirstRow()) + 1;
            this._width = (areaEval.getLastColumn() - areaEval.getFirstColumn()) + 1;
        }

        public int getWidth() {
            return this._width;
        }

        public int getHeight() {
            return this._height;
        }

        public int getFirstRowIndex() {
            return this._firstRowIndex;
        }

        public int getFirstColumnIndex() {
            return this._firstColumnIndex;
        }

        public AreaEval offset(int i, int i2, int i3, int i4) {
            RefEval refEval = this._refEval;
            if (refEval == null) {
                return this._areaEval.offset(i, i2, i3, i4);
            }
            return refEval.offset(i, i2, i3, i4);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003c A[Catch:{ EvaluationException -> 0x0051 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.fc.hssf.formula.eval.ValueEval evaluate(com.app.office.fc.hssf.formula.eval.ValueEval[] r10, int r11, int r12) {
        /*
            r9 = this;
            int r0 = r10.length
            r1 = 3
            if (r0 < r1) goto L_0x0057
            int r0 = r10.length
            r2 = 5
            if (r0 <= r2) goto L_0x0009
            goto L_0x0057
        L_0x0009:
            r0 = 0
            r0 = r10[r0]     // Catch:{ EvaluationException -> 0x0051 }
            com.app.office.fc.hssf.formula.function.Offset$BaseRef r0 = evaluateBaseRef(r0)     // Catch:{ EvaluationException -> 0x0051 }
            r3 = 1
            r3 = r10[r3]     // Catch:{ EvaluationException -> 0x0051 }
            int r3 = evaluateIntArg(r3, r11, r12)     // Catch:{ EvaluationException -> 0x0051 }
            r4 = 2
            r4 = r10[r4]     // Catch:{ EvaluationException -> 0x0051 }
            int r4 = evaluateIntArg(r4, r11, r12)     // Catch:{ EvaluationException -> 0x0051 }
            int r5 = r0.getHeight()     // Catch:{ EvaluationException -> 0x0051 }
            int r6 = r0.getWidth()     // Catch:{ EvaluationException -> 0x0051 }
            int r7 = r10.length     // Catch:{ EvaluationException -> 0x0051 }
            r8 = 4
            if (r7 == r8) goto L_0x0034
            if (r7 == r2) goto L_0x002d
            goto L_0x003a
        L_0x002d:
            r2 = r10[r8]     // Catch:{ EvaluationException -> 0x0051 }
            int r2 = evaluateIntArg(r2, r11, r12)     // Catch:{ EvaluationException -> 0x0051 }
            r6 = r2
        L_0x0034:
            r10 = r10[r1]     // Catch:{ EvaluationException -> 0x0051 }
            int r5 = evaluateIntArg(r10, r11, r12)     // Catch:{ EvaluationException -> 0x0051 }
        L_0x003a:
            if (r5 == 0) goto L_0x004e
            if (r6 != 0) goto L_0x003f
            goto L_0x004e
        L_0x003f:
            com.app.office.fc.hssf.formula.function.Offset$LinearOffsetRange r10 = new com.app.office.fc.hssf.formula.function.Offset$LinearOffsetRange     // Catch:{ EvaluationException -> 0x0051 }
            r10.<init>(r3, r5)     // Catch:{ EvaluationException -> 0x0051 }
            com.app.office.fc.hssf.formula.function.Offset$LinearOffsetRange r11 = new com.app.office.fc.hssf.formula.function.Offset$LinearOffsetRange     // Catch:{ EvaluationException -> 0x0051 }
            r11.<init>(r4, r6)     // Catch:{ EvaluationException -> 0x0051 }
            com.app.office.fc.hssf.formula.eval.AreaEval r10 = createOffset(r0, r10, r11)     // Catch:{ EvaluationException -> 0x0051 }
            return r10
        L_0x004e:
            com.app.office.fc.hssf.formula.eval.ErrorEval r10 = com.app.office.fc.hssf.formula.eval.ErrorEval.REF_INVALID     // Catch:{ EvaluationException -> 0x0051 }
            return r10
        L_0x0051:
            r10 = move-exception
            com.app.office.fc.hssf.formula.eval.ErrorEval r10 = r10.getErrorEval()
            return r10
        L_0x0057:
            com.app.office.fc.hssf.formula.eval.ErrorEval r10 = com.app.office.fc.hssf.formula.eval.ErrorEval.VALUE_INVALID
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.function.Offset.evaluate(com.app.office.fc.hssf.formula.eval.ValueEval[], int, int):com.app.office.fc.hssf.formula.eval.ValueEval");
    }

    private static AreaEval createOffset(BaseRef baseRef, LinearOffsetRange linearOffsetRange, LinearOffsetRange linearOffsetRange2) throws EvaluationException {
        LinearOffsetRange normaliseAndTranslate = linearOffsetRange.normaliseAndTranslate(baseRef.getFirstRowIndex());
        LinearOffsetRange normaliseAndTranslate2 = linearOffsetRange2.normaliseAndTranslate(baseRef.getFirstColumnIndex());
        if (normaliseAndTranslate.isOutOfBounds(0, 65535)) {
            throw new EvaluationException(ErrorEval.REF_INVALID);
        } else if (!normaliseAndTranslate2.isOutOfBounds(0, 255)) {
            return baseRef.offset(linearOffsetRange.getFirstIndex(), linearOffsetRange.getLastIndex(), linearOffsetRange2.getFirstIndex(), linearOffsetRange2.getLastIndex());
        } else {
            throw new EvaluationException(ErrorEval.REF_INVALID);
        }
    }

    private static BaseRef evaluateBaseRef(ValueEval valueEval) throws EvaluationException {
        if (valueEval instanceof RefEval) {
            return new BaseRef((RefEval) valueEval);
        }
        if (valueEval instanceof AreaEval) {
            return new BaseRef((AreaEval) valueEval);
        }
        if (valueEval instanceof ErrorEval) {
            throw new EvaluationException((ErrorEval) valueEval);
        }
        throw new EvaluationException(ErrorEval.VALUE_INVALID);
    }

    static int evaluateIntArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        return OperandResolver.coerceValueToInt(OperandResolver.getSingleValue(valueEval, i, i2));
    }
}
