package com.app.office.ss.util;

import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.java.awt.Rectangle;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.drawing.CellAnchor;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.other.SheetScroller;
import com.app.office.ss.util.format.NumericFormatter;
import com.app.office.ss.view.SheetView;

public class ModelUtil {
    private static ModelUtil mu = new ModelUtil();
    private RectF area = new RectF();

    public static ModelUtil instance() {
        return mu;
    }

    public static Rectangle processRect(Rectangle rectangle, float f) {
        float f2 = f % 360.0f;
        if ((f2 > 45.0f && f2 <= 135.0f) || (f2 > 225.0f && f2 < 315.0f)) {
            double centerX = rectangle.getCenterX();
            double centerY = rectangle.getCenterY();
            rectangle.x = (int) Math.round(centerX - ((double) (rectangle.height / 2)));
            rectangle.y = (int) Math.round(centerY - ((double) (rectangle.width / 2)));
            int i = rectangle.width;
            rectangle.width = rectangle.height;
            rectangle.height = i;
        }
        return rectangle;
    }

    public int getCellRangeAddressIndex(Sheet sheet, int i, int i2) {
        int mergeRangeCount = sheet.getMergeRangeCount();
        for (int i3 = 0; i3 < mergeRangeCount; i3++) {
            if (containsCell(sheet.getMergeRange(i3), i, i2)) {
                return i3;
            }
        }
        return -1;
    }

    public CellRangeAddress getCellRangeAddress(Sheet sheet, int i, int i2) {
        int mergeRangeCount = sheet.getMergeRangeCount();
        for (int i3 = 0; i3 < mergeRangeCount; i3++) {
            CellRangeAddress mergeRange = sheet.getMergeRange(i3);
            if (containsCell(mergeRange, i, i2)) {
                return mergeRange;
            }
        }
        return null;
    }

    public boolean containsCell(CellRangeAddress cellRangeAddress, int i, int i2) {
        return i >= cellRangeAddress.getFirstRow() && i <= cellRangeAddress.getLastRow() && i2 >= cellRangeAddress.getFirstColumn() && i2 <= cellRangeAddress.getLastColumn();
    }

    public RectF getCellRangeAddressAnchor(SheetView sheetView, CellRangeAddress cellRangeAddress) {
        this.area.left = getValueX(sheetView, cellRangeAddress.getFirstColumn(), 0.0f);
        this.area.top = getValueY(sheetView, cellRangeAddress.getFirstRow(), 0.0f);
        this.area.right = getValueX(sheetView, cellRangeAddress.getLastColumn() + 1, 0.0f);
        this.area.bottom = getValueY(sheetView, cellRangeAddress.getLastRow() + 1, 0.0f);
        return this.area;
    }

    private Rect getCellRangeAddressAnchor(Sheet sheet, CellRangeAddress cellRangeAddress) {
        Rect rect = new Rect();
        rect.left = Math.round(getValueX(sheet, cellRangeAddress.getFirstColumn(), 0));
        rect.top = Math.round(getValueY(sheet, cellRangeAddress.getFirstRow(), 0));
        rect.right = Math.round(getValueX(sheet, cellRangeAddress.getLastColumn() + 1, 0));
        rect.bottom = Math.round(getValueY(sheet, cellRangeAddress.getLastRow() + 1, 0));
        return rect;
    }

    public Rectangle getCellAnchor(Sheet sheet, CellAnchor cellAnchor) {
        Rectangle rectangle = new Rectangle();
        if (cellAnchor == null) {
            return null;
        }
        rectangle.x = Math.round(getValueX(sheet, (int) cellAnchor.getStart().getColumn(), cellAnchor.getStart().getDX()));
        rectangle.y = Math.round(getValueY(sheet, cellAnchor.getStart().getRow(), cellAnchor.getStart().getDY()));
        if (cellAnchor.getType() == 1) {
            rectangle.width = Math.round(getValueX(sheet, (int) cellAnchor.getEnd().getColumn(), cellAnchor.getEnd().getDX()) - ((float) rectangle.x));
            rectangle.height = Math.round(getValueY(sheet, cellAnchor.getEnd().getRow(), cellAnchor.getEnd().getDY()) - ((float) rectangle.y));
        } else if (cellAnchor.getType() == 0) {
            rectangle.width = cellAnchor.getWidth();
            rectangle.height = cellAnchor.getHeight();
        }
        return rectangle;
    }

    public RectF getCellAnchor(SheetView sheetView, Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getRangeAddressIndex() >= 0) {
            return getCellRangeAddressAnchor(sheetView, sheetView.getCurrentSheet().getMergeRange(cell.getRangeAddressIndex()));
        }
        this.area.left = getValueX(sheetView, cell.getColNumber(), 0.0f);
        this.area.top = getValueY(sheetView, cell.getRowNumber(), 0.0f);
        this.area.right = getValueX(sheetView, cell.getColNumber() + 1, 0.0f);
        this.area.bottom = getValueY(sheetView, cell.getRowNumber() + 1, 0.0f);
        return this.area;
    }

    public RectF getCellAnchor(SheetView sheetView, int i, int i2) {
        Sheet currentSheet = sheetView.getCurrentSheet();
        if (!(currentSheet.getRow(i) == null || currentSheet.getRow(i).getCell(i2) == null)) {
            Cell cell = currentSheet.getRow(i).getCell(i2);
            if (cell.getRangeAddressIndex() >= 0) {
                return getCellRangeAddressAnchor(sheetView, currentSheet.getMergeRange(cell.getRangeAddressIndex()));
            }
        }
        this.area.left = getValueX(sheetView, i2, 0.0f);
        this.area.top = getValueY(sheetView, i, 0.0f);
        this.area.right = getValueX(sheetView, i2 + 1, 0.0f);
        this.area.bottom = getValueY(sheetView, i + 1, 0.0f);
        return this.area;
    }

    public RectF getCellAnchor(SheetView sheetView, int i, int i2, int i3) {
        Sheet currentSheet = sheetView.getCurrentSheet();
        if (!(currentSheet.getRow(i) == null || currentSheet.getRow(i).getCell(i2) == null || currentSheet.getRow(i).getCell(i3) == null)) {
            Cell cell = currentSheet.getRow(i).getCell(i3);
            if (cell.getRangeAddressIndex() >= 0) {
                i3 = currentSheet.getMergeRange(cell.getRangeAddressIndex()).getLastColumn();
            }
        }
        this.area.left = getValueX(sheetView, i2, 0.0f);
        this.area.top = getValueY(sheetView, i, 0.0f);
        this.area.right = getValueX(sheetView, i3 + 1, 0.0f);
        this.area.bottom = getValueY(sheetView, i + 1, 0.0f);
        return this.area;
    }

    public Rect getCellAnchor(Sheet sheet, int i, int i2) {
        if (!(sheet.getRow(i) == null || sheet.getRow(i).getCell(i2) == null)) {
            Cell cell = sheet.getRow(i).getCell(i2);
            if (cell.getRangeAddressIndex() >= 0) {
                return getCellRangeAddressAnchor(sheet, sheet.getMergeRange(cell.getRangeAddressIndex()));
            }
        }
        Rect rect = new Rect();
        rect.left = Math.round(getValueX(sheet, i2, 0));
        rect.top = Math.round(getValueY(sheet, i, 0));
        rect.right = Math.round(getValueX(sheet, i2 + 1, 0));
        rect.bottom = Math.round(getValueY(sheet, i + 1, 0));
        return rect;
    }

    public Rect getCellAnchor(Sheet sheet, int i, int i2, boolean z) {
        if (z || sheet.getRow(i) == null || sheet.getRow(i).getCell(i2) == null) {
            Rect rect = new Rect();
            rect.left = Math.round(getValueX(sheet, i2, 0));
            rect.top = Math.round(getValueY(sheet, i, 0));
            rect.right = Math.round(getValueX(sheet, i2 + 1, 0));
            rect.bottom = Math.round(getValueY(sheet, i + 1, 0));
            return rect;
        }
        Cell cell = sheet.getRow(i).getCell(i2);
        if (cell.getRangeAddressIndex() >= 0) {
            return getCellRangeAddressAnchor(sheet, sheet.getMergeRange(cell.getRangeAddressIndex()));
        }
        return null;
    }

    private float getValueX(Sheet sheet, int i, int i2) {
        float f = 0.0f;
        for (int i3 = 0; i3 < i; i3++) {
            if (!sheet.isColumnHidden(i3)) {
                f += sheet.getColumnPixelWidth(i3);
            }
        }
        return ((float) i2) + f;
    }

    private float getValueY(Sheet sheet, int i, int i2) {
        float f = 0.0f;
        for (int i3 = 0; i3 < i; i3++) {
            Row row = sheet.getRow(i3);
            if (row == null || !row.isZeroHeight()) {
                f += row == null ? (float) sheet.getDefaultRowHeight() : row.getRowPixelHeight();
            }
        }
        return f + ((float) i2);
    }

    public float getValueX(SheetView sheetView, int i, float f) {
        float rowHeaderWidth = (float) sheetView.getRowHeaderWidth();
        Sheet currentSheet = sheetView.getCurrentSheet();
        SheetScroller minRowAndColumnInformation = sheetView.getMinRowAndColumnInformation();
        int minColumnIndex = minRowAndColumnInformation.getMinColumnIndex() > 0 ? minRowAndColumnInformation.getMinColumnIndex() : 0;
        if (minColumnIndex < i && !minRowAndColumnInformation.isColumnAllVisible()) {
            minColumnIndex++;
            rowHeaderWidth = (float) (((double) rowHeaderWidth) + (minRowAndColumnInformation.getVisibleColumnWidth() * ((double) sheetView.getZoom())));
        }
        int i2 = currentSheet.getWorkbook().isBefore07Version() ? 256 : 16384;
        while (minColumnIndex < i && minColumnIndex <= i2) {
            if (!currentSheet.isColumnHidden(minColumnIndex)) {
                rowHeaderWidth += currentSheet.getColumnPixelWidth(minColumnIndex) * sheetView.getZoom();
            }
            minColumnIndex++;
        }
        return f + rowHeaderWidth;
    }

    public float getValueY(SheetView sheetView, int i, float f) {
        float zoom = sheetView.getZoom() * 30.0f;
        Sheet currentSheet = sheetView.getCurrentSheet();
        SheetScroller minRowAndColumnInformation = sheetView.getMinRowAndColumnInformation();
        int minRowIndex = minRowAndColumnInformation.getMinRowIndex() > 0 ? minRowAndColumnInformation.getMinRowIndex() : 0;
        if (minRowIndex < i && !minRowAndColumnInformation.isRowAllVisible()) {
            minRowIndex++;
            zoom = (float) (((double) zoom) + (minRowAndColumnInformation.getVisibleRowHeight() * ((double) sheetView.getZoom())));
        }
        int i2 = currentSheet.getWorkbook().isBefore07Version() ? 65536 : 1048576;
        while (minRowIndex < i && minRowIndex <= i2) {
            Row row = currentSheet.getRow(minRowIndex);
            if (row == null || !row.isZeroHeight()) {
                zoom += (row == null ? (float) sheetView.getCurrentSheet().getDefaultRowHeight() : row.getRowPixelHeight()) * sheetView.getZoom();
            }
            minRowIndex++;
        }
        return zoom + f;
    }

    public String getFormatContents(Workbook workbook, Cell cell) {
        String str;
        short s;
        short s2;
        if (!cell.hasValidValue()) {
            return null;
        }
        CellStyle cellStyle = cell.getCellStyle();
        short cellType = cell.getCellType();
        if (cellType != 0) {
            if (cellType != 1) {
                if (cellType == 4) {
                    return String.valueOf(cell.getBooleanValue()).toUpperCase();
                }
                if (cellType == 5) {
                    return ErrorEval.getText(cell.getErrorValue());
                }
            } else if (cell.getStringCellValueIndex() >= 0) {
                return workbook.getSharedString(cell.getStringCellValueIndex());
            }
            return "";
        }
        String formatCode = cellStyle.getFormatCode();
        if (formatCode == null) {
            s = 6;
            str = "General";
        } else {
            if (cell.getCellNumericType() > 0) {
                s2 = cell.getCellNumericType();
            } else {
                s2 = NumericFormatter.instance().getNumericCellType(formatCode);
                cell.setCellNumericType(s2);
            }
            short s3 = s2;
            str = formatCode;
            s = s3;
        }
        if (s != 10) {
            return NumericFormatter.instance().getFormatContents(str, cell.getNumberValue(), s);
        }
        try {
            String formatContents = NumericFormatter.instance().getFormatContents(str, cell.getDateCellValue(workbook.isUsing1904DateWindowing()));
            cell.setCellType(1);
            cell.setCellValue(Integer.valueOf(workbook.addSharedString(formatContents)));
            return formatContents;
        } catch (Exception unused) {
            return String.valueOf(cell.getNumberValue());
        }
    }
}
