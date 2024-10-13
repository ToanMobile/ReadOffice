package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.eval.ConcatEval;
import com.app.office.fc.hssf.formula.eval.FunctionEval;
import com.app.office.fc.hssf.formula.eval.IntersectionEval;
import com.app.office.fc.hssf.formula.eval.PercentEval;
import com.app.office.fc.hssf.formula.eval.RangeEval;
import com.app.office.fc.hssf.formula.eval.RelationalOperationEval;
import com.app.office.fc.hssf.formula.eval.TwoOperandNumericOperation;
import com.app.office.fc.hssf.formula.eval.UnaryMinusEval;
import com.app.office.fc.hssf.formula.eval.UnaryPlusEval;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.Function;
import com.app.office.fc.hssf.formula.function.Indirect;
import com.app.office.fc.hssf.formula.ptg.AbstractFunctionPtg;
import com.app.office.fc.hssf.formula.ptg.AddPtg;
import com.app.office.fc.hssf.formula.ptg.ConcatPtg;
import com.app.office.fc.hssf.formula.ptg.DividePtg;
import com.app.office.fc.hssf.formula.ptg.EqualPtg;
import com.app.office.fc.hssf.formula.ptg.GreaterEqualPtg;
import com.app.office.fc.hssf.formula.ptg.GreaterThanPtg;
import com.app.office.fc.hssf.formula.ptg.IntersectionPtg;
import com.app.office.fc.hssf.formula.ptg.LessEqualPtg;
import com.app.office.fc.hssf.formula.ptg.LessThanPtg;
import com.app.office.fc.hssf.formula.ptg.MultiplyPtg;
import com.app.office.fc.hssf.formula.ptg.NotEqualPtg;
import com.app.office.fc.hssf.formula.ptg.OperationPtg;
import com.app.office.fc.hssf.formula.ptg.PercentPtg;
import com.app.office.fc.hssf.formula.ptg.PowerPtg;
import com.app.office.fc.hssf.formula.ptg.RangePtg;
import com.app.office.fc.hssf.formula.ptg.SubtractPtg;
import com.app.office.fc.hssf.formula.ptg.UnaryMinusPtg;
import com.app.office.fc.hssf.formula.ptg.UnaryPlusPtg;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

final class OperationEvaluatorFactory {
    private static final Map<OperationPtg, Function> _instancesByPtgClass = initialiseInstancesMap();

    private OperationEvaluatorFactory() {
    }

    private static Map<OperationPtg, Function> initialiseInstancesMap() {
        HashMap hashMap = new HashMap(32);
        put(hashMap, EqualPtg.instance, RelationalOperationEval.EqualEval);
        put(hashMap, GreaterEqualPtg.instance, RelationalOperationEval.GreaterEqualEval);
        put(hashMap, GreaterThanPtg.instance, RelationalOperationEval.GreaterThanEval);
        put(hashMap, LessEqualPtg.instance, RelationalOperationEval.LessEqualEval);
        put(hashMap, LessThanPtg.instance, RelationalOperationEval.LessThanEval);
        put(hashMap, NotEqualPtg.instance, RelationalOperationEval.NotEqualEval);
        put(hashMap, ConcatPtg.instance, ConcatEval.instance);
        put(hashMap, AddPtg.instance, TwoOperandNumericOperation.AddEval);
        put(hashMap, DividePtg.instance, TwoOperandNumericOperation.DivideEval);
        put(hashMap, MultiplyPtg.instance, TwoOperandNumericOperation.MultiplyEval);
        put(hashMap, PercentPtg.instance, PercentEval.instance);
        put(hashMap, PowerPtg.instance, TwoOperandNumericOperation.PowerEval);
        put(hashMap, SubtractPtg.instance, TwoOperandNumericOperation.SubtractEval);
        put(hashMap, UnaryMinusPtg.instance, UnaryMinusEval.instance);
        put(hashMap, UnaryPlusPtg.instance, UnaryPlusEval.instance);
        put(hashMap, RangePtg.instance, RangeEval.instance);
        put(hashMap, IntersectionPtg.instance, IntersectionEval.instance);
        return hashMap;
    }

    private static void put(Map<OperationPtg, Function> map, OperationPtg operationPtg, Function function) {
        Constructor[] declaredConstructors = operationPtg.getClass().getDeclaredConstructors();
        if (declaredConstructors.length > 1 || !Modifier.isPrivate(declaredConstructors[0].getModifiers())) {
            throw new RuntimeException("Failed to verify instance (" + operationPtg.getClass().getName() + ") is a singleton.");
        }
        map.put(operationPtg, function);
    }

    public static ValueEval evaluate(OperationPtg operationPtg, ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
        if (operationPtg != null) {
            Function function = _instancesByPtgClass.get(operationPtg);
            if (function != null) {
                return function.evaluate(valueEvalArr, operationEvaluationContext.getRowIndex(), (short) operationEvaluationContext.getColumnIndex());
            }
            if (operationPtg instanceof AbstractFunctionPtg) {
                short functionIndex = ((AbstractFunctionPtg) operationPtg).getFunctionIndex();
                if (functionIndex == 148) {
                    return Indirect.instance.evaluate(valueEvalArr, operationEvaluationContext);
                }
                if (functionIndex != 255) {
                    return FunctionEval.getBasicFunction(functionIndex).evaluate(valueEvalArr, operationEvaluationContext.getRowIndex(), (short) operationEvaluationContext.getColumnIndex());
                }
                return UserDefinedFunction.instance.evaluate(valueEvalArr, operationEvaluationContext);
            }
            throw new RuntimeException("Unexpected operation ptg class (" + operationPtg.getClass().getName() + ")");
        }
        throw new IllegalArgumentException("ptg must not be null");
    }
}
