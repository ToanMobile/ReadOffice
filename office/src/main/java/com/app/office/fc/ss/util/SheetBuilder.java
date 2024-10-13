package com.app.office.fc.ss.util;

import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.Sheet;
import com.app.office.fc.ss.usermodel.Workbook;
import java.util.Calendar;
import java.util.Date;

public class SheetBuilder {
    private Object[][] cells;
    private boolean shouldCreateEmptyCells = false;
    private Workbook workbook;

    public Sheet build() {
        return null;
    }

    public SheetBuilder(Workbook workbook2, Object[][] objArr) {
        this.workbook = workbook2;
        this.cells = objArr;
    }

    public boolean getCreateEmptyCells() {
        return this.shouldCreateEmptyCells;
    }

    public SheetBuilder setCreateEmptyCells(boolean z) {
        this.shouldCreateEmptyCells = z;
        return this;
    }

    public void setCellValue(ICell iCell, Object obj) {
        if (obj != null && iCell != null) {
            if (obj instanceof Number) {
                iCell.setCellValue(((Number) obj).doubleValue());
            } else if (obj instanceof Date) {
                iCell.setCellValue((Date) obj);
            } else if (obj instanceof Calendar) {
                iCell.setCellValue((Calendar) obj);
            } else if (isFormulaDefinition(obj)) {
                iCell.setCellFormula(getFormula(obj));
            } else {
                iCell.setCellValue(obj.toString());
            }
        }
    }

    private boolean isFormulaDefinition(Object obj) {
        if (!(obj instanceof String)) {
            return false;
        }
        String str = (String) obj;
        if (str.length() >= 2 && str.charAt(0) == '=') {
            return true;
        }
        return false;
    }

    private String getFormula(Object obj) {
        return ((String) obj).substring(1);
    }
}
