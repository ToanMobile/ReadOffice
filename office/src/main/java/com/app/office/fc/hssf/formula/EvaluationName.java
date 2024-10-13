package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.ptg.NamePtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;

public interface EvaluationName {
    NamePtg createPtg();

    Ptg[] getNameDefinition();

    String getNameText();

    boolean hasFormula();

    boolean isFunctionName();

    boolean isRange();
}
