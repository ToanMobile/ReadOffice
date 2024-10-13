package com.app.office.fc.hssf.util;

import com.app.office.fc.hssf.usermodel.HSSFCell;
import com.app.office.fc.hssf.usermodel.HSSFCellStyle;
import com.app.office.fc.hssf.usermodel.HSSFFont;
import com.app.office.fc.hssf.usermodel.HSSFRow;
import com.app.office.fc.hssf.usermodel.HSSFSheet;
import com.app.office.fc.hssf.usermodel.HSSFWorkbook;
import com.app.office.fc.ss.util.CellUtil;

public final class HSSFCellUtil {
    private HSSFCellUtil() {
    }

    public static HSSFRow getRow(int i, HSSFSheet hSSFSheet) {
        return (HSSFRow) CellUtil.getRow(i, hSSFSheet);
    }

    public static HSSFCell getCell(HSSFRow hSSFRow, int i) {
        return (HSSFCell) CellUtil.getCell(hSSFRow, i);
    }

    public static HSSFCell createCell(HSSFRow hSSFRow, int i, String str, HSSFCellStyle hSSFCellStyle) {
        return (HSSFCell) CellUtil.createCell(hSSFRow, i, str, hSSFCellStyle);
    }

    public static HSSFCell createCell(HSSFRow hSSFRow, int i, String str) {
        return createCell(hSSFRow, i, str, (HSSFCellStyle) null);
    }

    public static void setAlignment(HSSFCell hSSFCell, HSSFWorkbook hSSFWorkbook, short s) {
        CellUtil.setAlignment(hSSFCell, hSSFWorkbook, s);
    }

    public static void setFont(HSSFCell hSSFCell, HSSFWorkbook hSSFWorkbook, HSSFFont hSSFFont) {
        CellUtil.setFont(hSSFCell, hSSFWorkbook, hSSFFont);
    }

    public static void setCellStyleProperty(HSSFCell hSSFCell, HSSFWorkbook hSSFWorkbook, String str, Object obj) {
        CellUtil.setCellStyleProperty(hSSFCell, hSSFWorkbook, str, obj);
    }

    public static HSSFCell translateUnicodeValues(HSSFCell hSSFCell) {
        CellUtil.translateUnicodeValues(hSSFCell);
        return hSSFCell;
    }
}
