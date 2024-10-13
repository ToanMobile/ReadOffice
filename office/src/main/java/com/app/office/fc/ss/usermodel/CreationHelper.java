package com.app.office.fc.ss.usermodel;

import com.app.office.fc.hssf.usermodel.IClientAnchor;

public interface CreationHelper {
    IClientAnchor createClientAnchor();

    DataFormat createDataFormat();

    FormulaEvaluator createFormulaEvaluator();

    IHyperlink createHyperlink(int i);

    RichTextString createRichTextString(String str);
}
