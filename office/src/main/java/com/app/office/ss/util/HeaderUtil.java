package com.app.office.ss.util;

import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Sheet;

public class HeaderUtil {
    private static HeaderUtil util = new HeaderUtil();

    public static HeaderUtil instance() {
        return util;
    }

    public String getColumnHeaderTextByIndex(int i) {
        String str = "";
        while (i >= 0) {
            str = ((char) (((char) (i % 26)) + 'A')) + str;
            i = (i / 26) - 1;
        }
        return str;
    }

    public int getColumnHeaderIndexByText(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            i = (i * 26) + (str.charAt(i2) - 'A') + 1;
        }
        return i - 1;
    }

    public boolean isActiveRow(Sheet sheet, int i) {
        if (sheet.getActiveCellType() == 2) {
            return true;
        }
        if (sheet.getActiveCellType() != 1) {
            Cell activeCell = sheet.getActiveCell();
            if (activeCell != null && activeCell.getRangeAddressIndex() >= 0) {
                CellRangeAddress mergeRange = sheet.getMergeRange(activeCell.getRangeAddressIndex());
                if (mergeRange.getFirstRow() > i || mergeRange.getLastRow() < i) {
                    return false;
                }
                return true;
            } else if (sheet.getActiveCellRow() == i) {
                return true;
            }
            return false;
        } else if (sheet.getActiveCellRow() == i) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isActiveColumn(Sheet sheet, int i) {
        if (sheet.getActiveCellType() == 1) {
            return true;
        }
        if (sheet.getActiveCellType() != 2) {
            Cell activeCell = sheet.getActiveCell();
            if (activeCell != null && activeCell.getRangeAddressIndex() >= 0) {
                CellRangeAddress mergeRange = sheet.getMergeRange(activeCell.getRangeAddressIndex());
                if (mergeRange.getFirstColumn() > i || mergeRange.getLastColumn() < i) {
                    return false;
                }
                return true;
            } else if (sheet.getActiveCellColumn() == i) {
                return true;
            }
            return false;
        } else if (sheet.getActiveCellColumn() == i) {
            return true;
        } else {
            return false;
        }
    }
}
