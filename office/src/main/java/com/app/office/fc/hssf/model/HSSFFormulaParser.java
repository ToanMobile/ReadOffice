package com.app.office.fc.hssf.model;

import com.app.office.fc.hssf.formula.FormulaParseException;
import com.app.office.fc.hssf.formula.FormulaParser;
import com.app.office.fc.hssf.formula.FormulaParsingWorkbook;
import com.app.office.fc.hssf.formula.FormulaRenderer;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.usermodel.HSSFEvaluationWorkbook;
import com.app.office.ss.model.XLSModel.AWorkbook;

public final class HSSFFormulaParser {
    private static FormulaParsingWorkbook createParsingWorkbook(AWorkbook aWorkbook) {
        return HSSFEvaluationWorkbook.create(aWorkbook);
    }

    private HSSFFormulaParser() {
    }

    public static Ptg[] parse(String str, AWorkbook aWorkbook) throws FormulaParseException {
        return parse(str, aWorkbook, 0);
    }

    public static Ptg[] parse(String str, AWorkbook aWorkbook, int i) throws FormulaParseException {
        return parse(str, aWorkbook, i, -1);
    }

    public static Ptg[] parse(String str, AWorkbook aWorkbook, int i, int i2) throws FormulaParseException {
        return FormulaParser.parse(str, createParsingWorkbook(aWorkbook), i, i2);
    }

    public static String toFormulaString(AWorkbook aWorkbook, Ptg[] ptgArr) {
        return FormulaRenderer.toFormulaString(HSSFEvaluationWorkbook.create(aWorkbook), ptgArr);
    }
}
