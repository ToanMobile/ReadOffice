package com.app.office.fc.ss.util;

import com.app.office.fc.ss.usermodel.ICell;
import com.app.office.fc.ss.usermodel.ICellStyle;
import com.app.office.fc.ss.usermodel.IFont;
import com.app.office.fc.ss.usermodel.IRow;
import com.app.office.fc.ss.usermodel.Sheet;
import com.app.office.fc.ss.usermodel.Workbook;
import java.util.HashMap;
import java.util.Map;

public final class CellUtil {
    public static final String ALIGNMENT = "alignment";
    public static final String BORDER_BOTTOM = "borderBottom";
    public static final String BORDER_LEFT = "borderLeft";
    public static final String BORDER_RIGHT = "borderRight";
    public static final String BORDER_TOP = "borderTop";
    public static final String BOTTOM_BORDER_COLOR = "bottomBorderColor";
    public static final String DATA_FORMAT = "dataFormat";
    public static final String FILL_BACKGROUND_COLOR = "fillBackgroundColor";
    public static final String FILL_FOREGROUND_COLOR = "fillForegroundColor";
    public static final String FILL_PATTERN = "fillPattern";
    public static final String FONT = "font";
    public static final String HIDDEN = "hidden";
    public static final String INDENTION = "indention";
    public static final String LEFT_BORDER_COLOR = "leftBorderColor";
    public static final String LOCKED = "locked";
    public static final String RIGHT_BORDER_COLOR = "rightBorderColor";
    public static final String ROTATION = "rotation";
    public static final String TOP_BORDER_COLOR = "topBorderColor";
    public static final String VERTICAL_ALIGNMENT = "verticalAlignment";
    public static final String WRAP_TEXT = "wrapText";
    private static UnicodeMapping[] unicodeMappings = {um("alpha", "α"), um("beta", "β"), um("gamma", "γ"), um("delta", "δ"), um("epsilon", "ε"), um("zeta", "ζ"), um("eta", "η"), um("theta", "θ"), um("iota", "ι"), um("kappa", "κ"), um("lambda", "λ"), um("mu", "μ"), um("nu", "ν"), um("xi", "ξ"), um("omicron", "ο")};

    public static ICell createCell(IRow iRow, int i, String str, ICellStyle iCellStyle) {
        return null;
    }

    public static IRow getRow(int i, Sheet sheet) {
        return null;
    }

    public static void setCellStyleProperty(ICell iCell, Workbook workbook, String str, Object obj) {
    }

    private static void setFormatProperties(ICellStyle iCellStyle, Workbook workbook, Map<String, Object> map) {
    }

    private static final class UnicodeMapping {
        public final String entityName;
        public final String resolvedValue;

        public UnicodeMapping(String str, String str2) {
            this.entityName = "&" + str + ";";
            this.resolvedValue = str2;
        }
    }

    private CellUtil() {
    }

    public static ICell getCell(IRow iRow, int i) {
        ICell cell = iRow.getCell(i);
        return cell == null ? iRow.createCell(i) : cell;
    }

    public static ICell createCell(IRow iRow, int i, String str) {
        return createCell(iRow, i, str, (ICellStyle) null);
    }

    public static void setAlignment(ICell iCell, Workbook workbook, short s) {
        setCellStyleProperty(iCell, workbook, ALIGNMENT, Short.valueOf(s));
    }

    public static void setFont(ICell iCell, Workbook workbook, IFont iFont) {
        setCellStyleProperty(iCell, workbook, "font", Short.valueOf(iFont.getIndex()));
    }

    private static Map<String, Object> getFormatProperties(ICellStyle iCellStyle) {
        HashMap hashMap = new HashMap();
        putShort(hashMap, ALIGNMENT, iCellStyle.getAlignment());
        putShort(hashMap, BORDER_BOTTOM, iCellStyle.getBorderBottom());
        putShort(hashMap, BORDER_LEFT, iCellStyle.getBorderLeft());
        putShort(hashMap, BORDER_RIGHT, iCellStyle.getBorderRight());
        putShort(hashMap, BORDER_TOP, iCellStyle.getBorderTop());
        putShort(hashMap, BOTTOM_BORDER_COLOR, iCellStyle.getBottomBorderColor());
        putShort(hashMap, DATA_FORMAT, iCellStyle.getDataFormat());
        putShort(hashMap, FILL_BACKGROUND_COLOR, iCellStyle.getFillBackgroundColor());
        putShort(hashMap, FILL_FOREGROUND_COLOR, iCellStyle.getFillForegroundColor());
        putShort(hashMap, FILL_PATTERN, iCellStyle.getFillPattern());
        putShort(hashMap, "font", iCellStyle.getFontIndex());
        putBoolean(hashMap, HIDDEN, iCellStyle.getHidden());
        putShort(hashMap, INDENTION, iCellStyle.getIndention());
        putShort(hashMap, LEFT_BORDER_COLOR, iCellStyle.getLeftBorderColor());
        putBoolean(hashMap, LOCKED, iCellStyle.getLocked());
        putShort(hashMap, RIGHT_BORDER_COLOR, iCellStyle.getRightBorderColor());
        putShort(hashMap, "rotation", iCellStyle.getRotation());
        putShort(hashMap, TOP_BORDER_COLOR, iCellStyle.getTopBorderColor());
        putShort(hashMap, VERTICAL_ALIGNMENT, iCellStyle.getVerticalAlignment());
        putBoolean(hashMap, WRAP_TEXT, iCellStyle.getWrapText());
        return hashMap;
    }

    private static short getShort(Map<String, Object> map, String str) {
        Object obj = map.get(str);
        if (obj instanceof Short) {
            return ((Short) obj).shortValue();
        }
        return 0;
    }

    private static boolean getBoolean(Map<String, Object> map, String str) {
        Object obj = map.get(str);
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        return false;
    }

    private static void putShort(Map<String, Object> map, String str, short s) {
        map.put(str, Short.valueOf(s));
    }

    private static void putBoolean(Map<String, Object> map, String str, boolean z) {
        map.put(str, Boolean.valueOf(z));
    }

    public static ICell translateUnicodeValues(ICell iCell) {
        String string = iCell.getRichStringCellValue().getString();
        String lowerCase = string.toLowerCase();
        int i = 0;
        while (true) {
            UnicodeMapping[] unicodeMappingArr = unicodeMappings;
            if (i >= unicodeMappingArr.length) {
                return iCell;
            }
            UnicodeMapping unicodeMapping = unicodeMappingArr[i];
            String str = unicodeMapping.entityName;
            if (lowerCase.indexOf(str) != -1) {
                string = string.replaceAll(str, unicodeMapping.resolvedValue);
            }
            i++;
        }
    }

    private static UnicodeMapping um(String str, String str2) {
        return new UnicodeMapping(str, str2);
    }
}
