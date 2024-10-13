package com.app.office.fc.hssf.usermodel;

import com.app.office.fc.ss.usermodel.CreationHelper;

public class HSSFCreationHelper implements CreationHelper {
    private HSSFDataFormat dataFormat = new HSSFDataFormat(this.workbook.getWorkbook());
    private HSSFWorkbook workbook;

    public HSSFFormulaEvaluator createFormulaEvaluator() {
        return null;
    }

    HSSFCreationHelper(HSSFWorkbook hSSFWorkbook) {
        this.workbook = hSSFWorkbook;
    }

    public HSSFRichTextString createRichTextString(String str) {
        return new HSSFRichTextString(str);
    }

    public HSSFDataFormat createDataFormat() {
        return this.dataFormat;
    }

    public HSSFHyperlink createHyperlink(int i) {
        return new HSSFHyperlink(i);
    }

    public HSSFClientAnchor createClientAnchor() {
        return new HSSFClientAnchor();
    }
}
