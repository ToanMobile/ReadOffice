package com.app.office.fc.hssf.formula.udf;

import com.app.office.fc.hssf.formula.atp.AnalysisToolPak;
import com.app.office.fc.hssf.formula.function.FreeRefFunction;

public interface UDFFinder {
    public static final UDFFinder DEFAULT = new AggregatingUDFFinder(AnalysisToolPak.instance);

    FreeRefFunction findFunction(String str);
}
