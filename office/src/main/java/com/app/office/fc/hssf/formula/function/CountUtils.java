package com.app.office.fc.hssf.formula.function;

import com.app.office.fc.hssf.formula.TwoDEval;
import com.app.office.fc.hssf.formula.eval.RefEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;

final class CountUtils {

    public interface I_MatchAreaPredicate extends I_MatchPredicate {
        boolean matches(TwoDEval twoDEval, int i, int i2);
    }

    public interface I_MatchPredicate {
        boolean matches(ValueEval valueEval);
    }

    private CountUtils() {
    }

    public static int countMatchingCellsInArea(TwoDEval twoDEval, I_MatchPredicate i_MatchPredicate) {
        int height = twoDEval.getHeight();
        int width = twoDEval.getWidth();
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                ValueEval value = twoDEval.getValue(i2, i3);
                if ((!(i_MatchPredicate instanceof I_MatchAreaPredicate) || ((I_MatchAreaPredicate) i_MatchPredicate).matches(twoDEval, i2, i3)) && i_MatchPredicate.matches(value)) {
                    i++;
                }
            }
        }
        return i;
    }

    public static int countMatchingCell(RefEval refEval, I_MatchPredicate i_MatchPredicate) {
        return i_MatchPredicate.matches(refEval.getInnerValueEval()) ? 1 : 0;
    }

    public static int countArg(ValueEval valueEval, I_MatchPredicate i_MatchPredicate) {
        if (valueEval == null) {
            throw new IllegalArgumentException("eval must not be null");
        } else if (valueEval instanceof TwoDEval) {
            return countMatchingCellsInArea((TwoDEval) valueEval, i_MatchPredicate);
        } else {
            if (valueEval instanceof RefEval) {
                return countMatchingCell((RefEval) valueEval, i_MatchPredicate);
            }
            return i_MatchPredicate.matches(valueEval) ? 1 : 0;
        }
    }
}
