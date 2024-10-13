package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.WorkbookEvaluator;
import com.app.office.fc.hssf.formula.eval.AreaEval;
import com.app.office.fc.hssf.formula.eval.BlankEval;
import com.app.office.fc.hssf.formula.eval.BoolEval;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.eval.EvaluationException;
import com.app.office.fc.hssf.formula.eval.NumberEval;
import com.app.office.fc.hssf.formula.eval.NumericValueEval;
import com.app.office.fc.hssf.formula.eval.OperandResolver;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.StringEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

final class LookupUtils {

    public interface LookupValueComparer {
        CompareResult compareTo(ValueEval valueEval);
    }

    public interface ValueVector {
        ValueEval getItem(int i);

        int getSize();
    }

    LookupUtils() {
    }

    private static final class RowVector implements ValueVector {
        private final int _rowIndex;
        private final int _size;
        private final TwoDEval _tableArray;

        public RowVector(TwoDEval twoDEval, int i) {
            this._rowIndex = i;
            int height = twoDEval.getHeight() - 1;
            if (i < 0 || i > height) {
                throw new IllegalArgumentException("Specified row index (" + i + ") is outside the allowed range (0.." + height + ")");
            }
            this._tableArray = twoDEval;
            this._size = twoDEval.getWidth();
        }

        public ValueEval getItem(int i) {
            if (i <= this._size) {
                ValueEval value = this._tableArray.getValue(this._rowIndex, i);
                while (value instanceof RefEval) {
                    try {
                        value = OperandResolver.getSingleValue(value, 0, 0);
                    } catch (EvaluationException e) {
                        return e.getErrorEval();
                    }
                }
                return value;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Specified index (");
            sb.append(i);
            sb.append(") is outside the allowed range (0..");
            sb.append(this._size - 1);
            sb.append(")");
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }

        public int getSize() {
            return this._size;
        }
    }

    private static final class ColumnVector implements ValueVector {
        private final int _columnIndex;
        private final int _size;
        private final TwoDEval _tableArray;

        public ColumnVector(TwoDEval twoDEval, int i) {
            this._columnIndex = i;
            int width = twoDEval.getWidth() - 1;
            if (i < 0 || i > width) {
                throw new IllegalArgumentException("Specified column index (" + i + ") is outside the allowed range (0.." + width + ")");
            }
            this._tableArray = twoDEval;
            this._size = twoDEval.getHeight();
        }

        public ValueEval getItem(int i) {
            if (i <= this._size) {
                return this._tableArray.getValue(i, this._columnIndex);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Specified index (");
            sb.append(i);
            sb.append(") is outside the allowed range (0..");
            sb.append(this._size - 1);
            sb.append(")");
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }

        public int getSize() {
            return this._size;
        }
    }

    public static ValueVector createRowVector(TwoDEval twoDEval, int i) {
        return new RowVector(twoDEval, i);
    }

    public static ValueVector createColumnVector(TwoDEval twoDEval, int i) {
        return new ColumnVector(twoDEval, i);
    }

    public static ValueVector createVector(TwoDEval twoDEval) {
        if (twoDEval.isColumn()) {
            return createColumnVector(twoDEval, 0);
        }
        if (twoDEval.isRow()) {
            return createRowVector(twoDEval, 0);
        }
        return null;
    }

    public static final class CompareResult {
        public static final CompareResult EQUAL = new CompareResult(false, 0);
        public static final CompareResult GREATER_THAN = new CompareResult(false, 1);
        public static final CompareResult LESS_THAN = new CompareResult(false, -1);
        public static final CompareResult TYPE_MISMATCH = new CompareResult(true, 0);
        private final boolean _isEqual;
        private final boolean _isGreaterThan;
        private final boolean _isLessThan;
        private final boolean _isTypeMismatch;

        private CompareResult(boolean z, int i) {
            boolean z2 = true;
            if (z) {
                this._isTypeMismatch = true;
                this._isLessThan = false;
                this._isEqual = false;
                this._isGreaterThan = false;
                return;
            }
            this._isTypeMismatch = false;
            this._isLessThan = i < 0;
            this._isEqual = i == 0;
            this._isGreaterThan = i <= 0 ? false : z2;
        }

        public static final CompareResult valueOf(int i) {
            if (i < 0) {
                return LESS_THAN;
            }
            if (i > 0) {
                return GREATER_THAN;
            }
            return EQUAL;
        }

        public boolean isTypeMismatch() {
            return this._isTypeMismatch;
        }

        public boolean isLessThan() {
            return this._isLessThan;
        }

        public boolean isEqual() {
            return this._isEqual;
        }

        public boolean isGreaterThan() {
            return this._isGreaterThan;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(formatAsString());
            stringBuffer.append("]");
            return stringBuffer.toString();
        }

        private String formatAsString() {
            if (this._isTypeMismatch) {
                return "TYPE_MISMATCH";
            }
            if (this._isLessThan) {
                return "LESS_THAN";
            }
            if (this._isEqual) {
                return "EQUAL";
            }
            return this._isGreaterThan ? "GREATER_THAN" : "??error??";
        }
    }

    private static abstract class LookupValueComparerBase implements LookupValueComparer {
        private final Class<? extends ValueEval> _targetClass;

        /* access modifiers changed from: protected */
        public abstract CompareResult compareSameType(ValueEval valueEval);

        /* access modifiers changed from: protected */
        public abstract String getValueAsString();

        protected LookupValueComparerBase(ValueEval valueEval) {
            if (valueEval != null) {
                this._targetClass = valueEval.getClass();
                return;
            }
            throw new RuntimeException("targetValue cannot be null");
        }

        public final CompareResult compareTo(ValueEval valueEval) {
            if (valueEval == null) {
                throw new RuntimeException("compare to value cannot be null");
            } else if (this._targetClass != valueEval.getClass()) {
                return CompareResult.TYPE_MISMATCH;
            } else {
                return compareSameType(valueEval);
            }
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append(getValueAsString());
            stringBuffer.append("]");
            return stringBuffer.toString();
        }
    }

    private static final class StringLookupComparer extends LookupValueComparerBase {
        private String _value;

        protected StringLookupComparer(StringEval stringEval) {
            super(stringEval);
            this._value = stringEval.getStringValue();
        }

        /* access modifiers changed from: protected */
        public CompareResult compareSameType(ValueEval valueEval) {
            return CompareResult.valueOf(this._value.compareToIgnoreCase(((StringEval) valueEval).getStringValue()));
        }

        /* access modifiers changed from: protected */
        public String getValueAsString() {
            return this._value;
        }
    }

    private static final class NumberLookupComparer extends LookupValueComparerBase {
        private double _value;

        protected NumberLookupComparer(NumberEval numberEval) {
            super(numberEval);
            this._value = numberEval.getNumberValue();
        }

        /* access modifiers changed from: protected */
        public CompareResult compareSameType(ValueEval valueEval) {
            return CompareResult.valueOf(Double.compare(this._value, ((NumberEval) valueEval).getNumberValue()));
        }

        /* access modifiers changed from: protected */
        public String getValueAsString() {
            return String.valueOf(this._value);
        }
    }

    private static final class BooleanLookupComparer extends LookupValueComparerBase {
        private boolean _value;

        protected BooleanLookupComparer(BoolEval boolEval) {
            super(boolEval);
            this._value = boolEval.getBooleanValue();
        }

        /* access modifiers changed from: protected */
        public CompareResult compareSameType(ValueEval valueEval) {
            boolean booleanValue = ((BoolEval) valueEval).getBooleanValue();
            boolean z = this._value;
            if (z == booleanValue) {
                return CompareResult.EQUAL;
            }
            if (z) {
                return CompareResult.GREATER_THAN;
            }
            return CompareResult.LESS_THAN;
        }

        /* access modifiers changed from: protected */
        public String getValueAsString() {
            return String.valueOf(this._value);
        }
    }

    public static int resolveRowOrColIndexArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        if (valueEval != null) {
            try {
                ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, (short) i2);
                if (!(singleValue instanceof StringEval) || OperandResolver.parseDouble(((StringEval) singleValue).getStringValue()) != null) {
                    int coerceValueToInt = OperandResolver.coerceValueToInt(singleValue);
                    if (coerceValueToInt >= 1) {
                        return coerceValueToInt - 1;
                    }
                    throw EvaluationException.invalidValue();
                }
                throw EvaluationException.invalidRef();
            } catch (EvaluationException unused) {
                throw EvaluationException.invalidRef();
            }
        } else {
            throw new IllegalArgumentException("argument must not be null");
        }
    }

    public static TwoDEval resolveTableArrayArg(ValueEval valueEval) throws EvaluationException {
        if (valueEval instanceof TwoDEval) {
            return (TwoDEval) valueEval;
        }
        if (valueEval instanceof RefEval) {
            return ((RefEval) valueEval).offset(0, 0, 0, 0);
        }
        throw EvaluationException.invalidValue();
    }

    public static boolean resolveRangeLookupArg(ValueEval valueEval, int i, int i2) throws EvaluationException {
        ValueEval singleValue = OperandResolver.getSingleValue(valueEval, i, i2);
        if (singleValue instanceof BlankEval) {
            return false;
        }
        if (singleValue instanceof BoolEval) {
            return ((BoolEval) singleValue).getBooleanValue();
        }
        if (singleValue instanceof StringEval) {
            String stringValue = ((StringEval) singleValue).getStringValue();
            if (stringValue.length() >= 1) {
                Boolean parseBoolean = Countif.parseBoolean(stringValue);
                if (parseBoolean != null) {
                    return parseBoolean.booleanValue();
                }
                throw EvaluationException.invalidValue();
            }
            throw EvaluationException.invalidValue();
        } else if (!(singleValue instanceof NumericValueEval)) {
            throw new RuntimeException("Unexpected eval type (" + singleValue.getClass().getName() + ")");
        } else if (0.0d != ((NumericValueEval) singleValue).getNumberValue()) {
            return true;
        } else {
            return false;
        }
    }

    public static int lookupIndexOfValue(ValueEval valueEval, ValueVector valueVector, boolean z) throws EvaluationException {
        int i;
        LookupValueComparer createLookupComparer = createLookupComparer(valueEval);
        if (z) {
            i = performBinarySearch(valueVector, createLookupComparer);
        } else {
            i = lookupIndexOfExactValue(createLookupComparer, valueVector);
        }
        if (i >= 0) {
            return i;
        }
        throw new EvaluationException(ErrorEval.NA);
    }

    public static int lookupIndexOfValue(int i, int i2, ValueEval valueEval, ValueVector valueVector, boolean z) throws EvaluationException {
        int i3;
        LookupValueComparer createLookupComparer = createLookupComparer(i, i2, valueEval);
        if (z) {
            i3 = performBinarySearch(valueVector, createLookupComparer);
        } else {
            i3 = lookupIndexOfExactValue(createLookupComparer, valueVector);
        }
        if (i3 >= 0) {
            return i3;
        }
        throw new EvaluationException(ErrorEval.NA);
    }

    private static int lookupIndexOfExactValue(LookupValueComparer lookupValueComparer, ValueVector valueVector) {
        int size = valueVector.getSize();
        for (int i = 0; i < size; i++) {
            if (lookupValueComparer.compareTo(valueVector.getItem(i)).isEqual()) {
                return i;
            }
        }
        return -1;
    }

    private static final class BinarySearchIndexes {
        private int _highIx;
        private int _lowIx = -1;

        public BinarySearchIndexes(int i) {
            this._highIx = i;
        }

        public int getMidIx() {
            int i = this._highIx;
            int i2 = this._lowIx;
            int i3 = i - i2;
            if (i3 < 2) {
                return -1;
            }
            return i2 + (i3 / 2);
        }

        public int getLowIx() {
            return this._lowIx;
        }

        public int getHighIx() {
            return this._highIx;
        }

        public void narrowSearch(int i, boolean z) {
            if (z) {
                this._highIx = i;
            } else {
                this._lowIx = i;
            }
        }
    }

    private static int performBinarySearch(ValueVector valueVector, LookupValueComparer lookupValueComparer) {
        BinarySearchIndexes binarySearchIndexes = new BinarySearchIndexes(valueVector.getSize());
        while (true) {
            int midIx = binarySearchIndexes.getMidIx();
            if (midIx < 0) {
                return binarySearchIndexes.getLowIx();
            }
            CompareResult compareTo = lookupValueComparer.compareTo(valueVector.getItem(midIx));
            if (compareTo.isTypeMismatch()) {
                midIx = handleMidValueTypeMismatch(lookupValueComparer, valueVector, binarySearchIndexes, midIx);
                if (midIx < 0) {
                    continue;
                } else {
                    compareTo = lookupValueComparer.compareTo(valueVector.getItem(midIx));
                }
            }
            if (compareTo.isEqual()) {
                return findLastIndexInRunOfEqualValues(lookupValueComparer, valueVector, midIx, binarySearchIndexes.getHighIx());
            }
            binarySearchIndexes.narrowSearch(midIx, compareTo.isLessThan());
        }
    }

    private static int handleMidValueTypeMismatch(LookupValueComparer lookupValueComparer, ValueVector valueVector, BinarySearchIndexes binarySearchIndexes, int i) {
        CompareResult compareTo;
        int highIx = binarySearchIndexes.getHighIx();
        int i2 = i;
        do {
            i2++;
            if (i2 == highIx) {
                binarySearchIndexes.narrowSearch(i, true);
                return -1;
            }
            compareTo = lookupValueComparer.compareTo(valueVector.getItem(i2));
            if (compareTo.isLessThan() && i2 == highIx - 1) {
                binarySearchIndexes.narrowSearch(i, true);
                return -1;
            }
        } while (compareTo.isTypeMismatch());
        if (compareTo.isEqual()) {
            return i2;
        }
        binarySearchIndexes.narrowSearch(i2, compareTo.isLessThan());
        return -1;
    }

    private static int findLastIndexInRunOfEqualValues(LookupValueComparer lookupValueComparer, ValueVector valueVector, int i, int i2) {
        do {
            i++;
            if (i >= i2) {
                return i2 - 1;
            }
        } while (lookupValueComparer.compareTo(valueVector.getItem(i)).isEqual());
        return i - 1;
    }

    public static LookupValueComparer createLookupComparer(ValueEval valueEval) {
        if (valueEval == BlankEval.instance) {
            return new NumberLookupComparer(NumberEval.ZERO);
        }
        if (valueEval instanceof StringEval) {
            return new StringLookupComparer((StringEval) valueEval);
        }
        if (valueEval instanceof NumberEval) {
            return new NumberLookupComparer((NumberEval) valueEval);
        }
        if (valueEval instanceof BoolEval) {
            return new BooleanLookupComparer((BoolEval) valueEval);
        }
        throw new IllegalArgumentException("Bad lookup value type (" + valueEval.getClass().getName() + ")");
    }

    public static LookupValueComparer createLookupComparer(int i, int i2, ValueEval valueEval) {
        if (valueEval instanceof AreaEval) {
            return createLookupComparer(i, i2, WorkbookEvaluator.dereferenceResult(valueEval, i, i2));
        }
        return createLookupComparer(valueEval);
    }
}
