package com.app.office.fc.hssf.formula.atp;

import com.app.office.fc.hssf.formula.OperationEvaluationContext;
import com.app.office.fc.hssf.formula.eval.ValueEval;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;
import com.app.office.fc.hssf.formula.udf.UDFFinder;
import java.util.HashMap;
import java.util.Map;

public final class AnalysisToolPak implements UDFFinder {
    public static final UDFFinder instance = new AnalysisToolPak();
    private final Map<String, FreeRefFunction> _functionsByName = createFunctionsMap();

    private static final class NotImplemented implements FreeRefFunction {
        private final String _functionName;

        public ValueEval evaluate(ValueEval[] valueEvalArr, OperationEvaluationContext operationEvaluationContext) {
            return null;
        }

        public NotImplemented(String str) {
            this._functionName = str;
        }
    }

    private AnalysisToolPak() {
    }

    public FreeRefFunction findFunction(String str) {
        return this._functionsByName.get(str);
    }

    private Map<String, FreeRefFunction> createFunctionsMap() {
        HashMap hashMap = new HashMap(108);
        r(hashMap, "ACCRINT", (FreeRefFunction) null);
        r(hashMap, "ACCRINTM", (FreeRefFunction) null);
        r(hashMap, "AMORDEGRC", (FreeRefFunction) null);
        r(hashMap, "AMORLINC", (FreeRefFunction) null);
        r(hashMap, "AVERAGEIF", (FreeRefFunction) null);
        r(hashMap, "AVERAGEIFS", (FreeRefFunction) null);
        r(hashMap, "BAHTTEXT", (FreeRefFunction) null);
        r(hashMap, "BESSELI", (FreeRefFunction) null);
        r(hashMap, "BESSELJ", (FreeRefFunction) null);
        r(hashMap, "BESSELK", (FreeRefFunction) null);
        r(hashMap, "BESSELY", (FreeRefFunction) null);
        r(hashMap, "BIN2DEC", (FreeRefFunction) null);
        r(hashMap, "BIN2HEX", (FreeRefFunction) null);
        r(hashMap, "BIN2OCT", (FreeRefFunction) null);
        r(hashMap, "COMPLEX", (FreeRefFunction) null);
        r(hashMap, "CONVERT", (FreeRefFunction) null);
        r(hashMap, "COUNTIFS", (FreeRefFunction) null);
        r(hashMap, "COUPDAYBS", (FreeRefFunction) null);
        r(hashMap, "COUPDAYS", (FreeRefFunction) null);
        r(hashMap, "COUPDAYSNC", (FreeRefFunction) null);
        r(hashMap, "COUPNCD", (FreeRefFunction) null);
        r(hashMap, "COUPNUM", (FreeRefFunction) null);
        r(hashMap, "COUPPCD", (FreeRefFunction) null);
        r(hashMap, "CUBEKPIMEMBER", (FreeRefFunction) null);
        r(hashMap, "CUBEMEMBER", (FreeRefFunction) null);
        r(hashMap, "CUBEMEMBERPROPERTY", (FreeRefFunction) null);
        r(hashMap, "CUBERANKEDMEMBER", (FreeRefFunction) null);
        r(hashMap, "CUBESET", (FreeRefFunction) null);
        r(hashMap, "CUBESETCOUNT", (FreeRefFunction) null);
        r(hashMap, "CUBEVALUE", (FreeRefFunction) null);
        r(hashMap, "CUMIPMT", (FreeRefFunction) null);
        r(hashMap, "CUMPRINC", (FreeRefFunction) null);
        r(hashMap, "DEC2BIN", (FreeRefFunction) null);
        r(hashMap, "DEC2HEX", (FreeRefFunction) null);
        r(hashMap, "DEC2OCT", (FreeRefFunction) null);
        r(hashMap, "DELTA", (FreeRefFunction) null);
        r(hashMap, "DISC", (FreeRefFunction) null);
        r(hashMap, "DOLLARDE", (FreeRefFunction) null);
        r(hashMap, "DOLLARFR", (FreeRefFunction) null);
        r(hashMap, "DURATION", (FreeRefFunction) null);
        r(hashMap, "EDATE", (FreeRefFunction) null);
        r(hashMap, "EFFECT", (FreeRefFunction) null);
        r(hashMap, "EOMONTH", (FreeRefFunction) null);
        r(hashMap, "ERF", (FreeRefFunction) null);
        r(hashMap, "ERFC", (FreeRefFunction) null);
        r(hashMap, "FACTDOUBLE", (FreeRefFunction) null);
        r(hashMap, "FVSCHEDULE", (FreeRefFunction) null);
        r(hashMap, "GCD", (FreeRefFunction) null);
        r(hashMap, "GESTEP", (FreeRefFunction) null);
        r(hashMap, "HEX2BIN", (FreeRefFunction) null);
        r(hashMap, "HEX2DEC", (FreeRefFunction) null);
        r(hashMap, "HEX2OCT", (FreeRefFunction) null);
        r(hashMap, "IFERROR", (FreeRefFunction) null);
        r(hashMap, "IMABS", (FreeRefFunction) null);
        r(hashMap, "IMAGINARY", (FreeRefFunction) null);
        r(hashMap, "IMARGUMENT", (FreeRefFunction) null);
        r(hashMap, "IMCONJUGATE", (FreeRefFunction) null);
        r(hashMap, "IMCOS", (FreeRefFunction) null);
        r(hashMap, "IMDIV", (FreeRefFunction) null);
        r(hashMap, "IMEXP", (FreeRefFunction) null);
        r(hashMap, "IMLN", (FreeRefFunction) null);
        r(hashMap, "IMLOG10", (FreeRefFunction) null);
        r(hashMap, "IMLOG2", (FreeRefFunction) null);
        r(hashMap, "IMPOWER", (FreeRefFunction) null);
        r(hashMap, "IMPRODUCT", (FreeRefFunction) null);
        r(hashMap, "IMREAL", (FreeRefFunction) null);
        r(hashMap, "IMSIN", (FreeRefFunction) null);
        r(hashMap, "IMSQRT", (FreeRefFunction) null);
        r(hashMap, "IMSUB", (FreeRefFunction) null);
        r(hashMap, "IMSUM", (FreeRefFunction) null);
        r(hashMap, "INTRATE", (FreeRefFunction) null);
        r(hashMap, "ISEVEN", ParityFunction.IS_EVEN);
        r(hashMap, "ISODD", ParityFunction.IS_ODD);
        r(hashMap, "JIS", (FreeRefFunction) null);
        r(hashMap, "LCM", (FreeRefFunction) null);
        r(hashMap, "MDURATION", (FreeRefFunction) null);
        r(hashMap, "MROUND", MRound.instance);
        r(hashMap, "MULTINOMIAL", (FreeRefFunction) null);
        r(hashMap, "NETWORKDAYS", (FreeRefFunction) null);
        r(hashMap, "NOMINAL", (FreeRefFunction) null);
        r(hashMap, "OCT2BIN", (FreeRefFunction) null);
        r(hashMap, "OCT2DEC", (FreeRefFunction) null);
        r(hashMap, "OCT2HEX", (FreeRefFunction) null);
        r(hashMap, "ODDFPRICE", (FreeRefFunction) null);
        r(hashMap, "ODDFYIELD", (FreeRefFunction) null);
        r(hashMap, "ODDLPRICE", (FreeRefFunction) null);
        r(hashMap, "ODDLYIELD", (FreeRefFunction) null);
        r(hashMap, "PRICE", (FreeRefFunction) null);
        r(hashMap, "PRICEDISC", (FreeRefFunction) null);
        r(hashMap, "PRICEMAT", (FreeRefFunction) null);
        r(hashMap, "QUOTIENT", (FreeRefFunction) null);
        r(hashMap, "RANDBETWEEN", RandBetween.instance);
        r(hashMap, "RECEIVED", (FreeRefFunction) null);
        r(hashMap, "RTD", (FreeRefFunction) null);
        r(hashMap, "SERIESSUM", (FreeRefFunction) null);
        r(hashMap, "SQRTPI", (FreeRefFunction) null);
        r(hashMap, "SUMIFS", (FreeRefFunction) null);
        r(hashMap, "TBILLEQ", (FreeRefFunction) null);
        r(hashMap, "TBILLPRICE", (FreeRefFunction) null);
        r(hashMap, "TBILLYIELD", (FreeRefFunction) null);
        r(hashMap, "WEEKNUM", (FreeRefFunction) null);
        r(hashMap, "WORKDAY", (FreeRefFunction) null);
        r(hashMap, "XIRR", (FreeRefFunction) null);
        r(hashMap, "XNPV", (FreeRefFunction) null);
        r(hashMap, "YEARFRAC", YearFrac.instance);
        r(hashMap, "YIELD", (FreeRefFunction) null);
        r(hashMap, "YIELDDISC", (FreeRefFunction) null);
        r(hashMap, "YIELDMAT", (FreeRefFunction) null);
        return hashMap;
    }

    private static void r(Map<String, FreeRefFunction> map, String str, FreeRefFunction freeRefFunction) {
        if (freeRefFunction == null) {
            freeRefFunction = new NotImplemented(str);
        }
        map.put(str, freeRefFunction);
    }
}
