package com.app.office.fc.hssf.formula;

import com.app.office.fc.hssf.formula.ptg.AreaPtg;
import com.app.office.fc.hssf.formula.ptg.AreaPtgBase;
import com.app.office.fc.hssf.formula.ptg.OperandPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.RefPtg;
import com.app.office.fc.hssf.formula.ptg.RefPtgBase;
import com.app.office.fc.ss.SpreadsheetVersion;

public class SharedFormula {
    private final int _columnWrappingMask;
    private final int _rowWrappingMask;

    public SharedFormula(SpreadsheetVersion spreadsheetVersion) {
        this._columnWrappingMask = spreadsheetVersion.getLastColumnIndex();
        this._rowWrappingMask = spreadsheetVersion.getLastRowIndex();
    }

    public Ptg[] convertSharedFormulas(Ptg[] ptgArr, int i, int i2) {
        SharedFormula sharedFormula = this;
        Ptg[] ptgArr2 = ptgArr;
        int i3 = i;
        int i4 = i2;
        Ptg[] ptgArr3 = new Ptg[ptgArr2.length];
        int i5 = 0;
        while (i5 < ptgArr2.length) {
            Ptg ptg = ptgArr2[i5];
            byte b = -1;
            if (!ptg.isBaseToken()) {
                b = ptg.getPtgClass();
            }
            if (ptg instanceof RefPtgBase) {
                RefPtgBase refPtgBase = (RefPtgBase) ptg;
                RefPtg refPtg = new RefPtg(sharedFormula.fixupRelativeRow(i3, refPtgBase.getRow(), refPtgBase.isRowRelative()), sharedFormula.fixupRelativeColumn(i4, refPtgBase.getColumn(), refPtgBase.isColRelative()), refPtgBase.isRowRelative(), refPtgBase.isColRelative());
                refPtg.setClass(b);
                ptg = refPtg;
            } else if (ptg instanceof AreaPtgBase) {
                AreaPtgBase areaPtgBase = (AreaPtgBase) ptg;
                AreaPtg areaPtg = r8;
                AreaPtg areaPtg2 = new AreaPtg(sharedFormula.fixupRelativeRow(i3, areaPtgBase.getFirstRow(), areaPtgBase.isFirstRowRelative()), sharedFormula.fixupRelativeRow(i3, areaPtgBase.getLastRow(), areaPtgBase.isLastRowRelative()), sharedFormula.fixupRelativeColumn(i4, areaPtgBase.getFirstColumn(), areaPtgBase.isFirstColRelative()), sharedFormula.fixupRelativeColumn(i4, areaPtgBase.getLastColumn(), areaPtgBase.isLastColRelative()), areaPtgBase.isFirstRowRelative(), areaPtgBase.isLastRowRelative(), areaPtgBase.isFirstColRelative(), areaPtgBase.isLastColRelative());
                areaPtg.setClass(b);
                ptg = areaPtg;
            } else if (ptg instanceof OperandPtg) {
                ptg = ((OperandPtg) ptg).copy();
            }
            ptgArr3[i5] = ptg;
            i5++;
            sharedFormula = this;
        }
        return ptgArr3;
    }

    private int fixupRelativeColumn(int i, int i2, boolean z) {
        if (!z) {
            return i2;
        }
        return this._columnWrappingMask & (i2 + i);
    }

    private int fixupRelativeRow(int i, int i2, boolean z) {
        if (!z) {
            return i2;
        }
        return this._rowWrappingMask & (i2 + i);
    }
}
