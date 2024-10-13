package com.app.office.ss.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.PaintKit;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Cell;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.model.table.SSTableCellStyle;

public class CellBorderView {
    private SheetView sheetView;

    public CellBorderView(SheetView sheetView2) {
        this.sheetView = sheetView2;
    }

    public void draw(Canvas canvas, Cell cell, RectF rectF, SSTableCellStyle sSTableCellStyle) {
        Paint paint = PaintKit.instance().getPaint();
        int color = paint.getColor();
        Workbook workbook = this.sheetView.getSpreadsheet().getWorkbook();
        canvas.save();
        if (rectF.left > ((float) this.sheetView.getRowHeaderWidth())) {
            int LeftBorder = LeftBorder(cell);
            if (LeftBorder > -1) {
                paint.setColor(workbook.getColor(LeftBorder));
                canvas.drawRect(rectF.left, rectF.top, rectF.left + 1.0f, rectF.bottom, paint);
            } else if (!(sSTableCellStyle == null || sSTableCellStyle.getBorderColor() == null)) {
                paint.setColor(sSTableCellStyle.getBorderColor().intValue());
                canvas.drawRect(rectF.left, rectF.top, rectF.left + 1.0f, rectF.bottom, paint);
            }
        }
        if (rectF.top > ((float) this.sheetView.getColumnHeaderHeight())) {
            int TopBorder = TopBorder(cell);
            if (TopBorder > -1) {
                paint.setColor(workbook.getColor(TopBorder));
                canvas.drawRect(rectF.left, rectF.top, rectF.right, rectF.top + 1.0f, paint);
            } else if (!(sSTableCellStyle == null || sSTableCellStyle.getBorderColor() == null)) {
                paint.setColor(sSTableCellStyle.getBorderColor().intValue());
                canvas.drawRect(rectF.left, rectF.top, rectF.right, rectF.top + 1.0f, paint);
            }
        }
        if (rectF.right > ((float) this.sheetView.getRowHeaderWidth())) {
            int RightBorder = RightBorder(cell);
            if (RightBorder > -1) {
                paint.setColor(workbook.getColor(RightBorder));
                canvas.drawRect(rectF.right, rectF.top, rectF.right + 1.0f, rectF.bottom, paint);
            } else if (!(sSTableCellStyle == null || sSTableCellStyle.getBorderColor() == null)) {
                paint.setColor(sSTableCellStyle.getBorderColor().intValue());
                canvas.drawRect(rectF.right, rectF.top, rectF.right + 1.0f, rectF.bottom, paint);
            }
        }
        if (rectF.bottom > ((float) this.sheetView.getColumnHeaderHeight())) {
            int BottomBorder = BottomBorder(cell);
            if (BottomBorder > -1) {
                paint.setColor(workbook.getColor(BottomBorder));
                canvas.drawRect(rectF.left, rectF.bottom, rectF.right, rectF.bottom + 1.0f, paint);
            } else if (!(sSTableCellStyle == null || sSTableCellStyle.getBorderColor() == null)) {
                paint.setColor(sSTableCellStyle.getBorderColor().intValue());
                canvas.drawRect(rectF.left, rectF.bottom, rectF.right, rectF.bottom + 1.0f, paint);
            }
        }
        paint.setColor(color);
        canvas.restore();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ad, code lost:
        if (r8.getColNumber() != r1.getRow(r8.getRowNumber()).getExpandedRangeAddress(r8.getExpandedRangeAddressIndex()).getRangedAddress().getFirstColumn()) goto L_0x00b1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int LeftBorder(com.app.office.ss.model.baseModel.Cell r8) {
        /*
            r7 = this;
            com.app.office.ss.model.style.CellStyle r0 = r8.getCellStyle()
            com.app.office.ss.view.SheetView r1 = r7.sheetView
            com.app.office.ss.model.baseModel.Sheet r1 = r1.getCurrentSheet()
            int r2 = r8.getRangeAddressIndex()
            if (r2 < 0) goto L_0x002f
            int r2 = r8.getRangeAddressIndex()
            com.app.office.ss.model.CellRangeAddress r2 = r1.getMergeRange(r2)
            int r3 = r2.getFirstRow()
            com.app.office.ss.model.baseModel.Row r3 = r1.getRow(r3)
            int r2 = r2.getFirstColumn()
            com.app.office.ss.model.baseModel.Cell r2 = r3.getCell(r2)
            if (r2 == 0) goto L_0x002f
            com.app.office.ss.model.style.CellStyle r0 = r2.getCellStyle()
            r8 = r2
        L_0x002f:
            r2 = -1
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L_0x003f
            short r5 = r0.getBorderLeft()
            if (r5 <= 0) goto L_0x003f
            short r0 = r0.getBorderLeftColorIdx()
            goto L_0x0089
        L_0x003f:
            int r0 = r8.getRowNumber()
            com.app.office.ss.model.baseModel.Row r0 = r1.getRowByColumnsStyle(r0)
            int r5 = r8.getColNumber()
            int r5 = r5 - r4
            com.app.office.ss.model.baseModel.Cell r0 = r0.getCell(r5)
            if (r0 == 0) goto L_0x0087
            com.app.office.ss.model.style.CellStyle r5 = r0.getCellStyle()
            int r6 = r0.getRangeAddressIndex()
            if (r6 < 0) goto L_0x007a
            int r0 = r0.getRangeAddressIndex()
            com.app.office.ss.model.CellRangeAddress r0 = r1.getMergeRange(r0)
            int r6 = r0.getLastRow()
            com.app.office.ss.model.baseModel.Row r6 = r1.getRow(r6)
            int r0 = r0.getLastColumn()
            com.app.office.ss.model.baseModel.Cell r0 = r6.getCell(r0)
            if (r0 == 0) goto L_0x007a
            com.app.office.ss.model.style.CellStyle r5 = r0.getCellStyle()
        L_0x007a:
            if (r5 == 0) goto L_0x0087
            short r0 = r5.getBorderRight()
            if (r0 <= 0) goto L_0x0087
            short r0 = r5.getBorderRightColorIdx()
            goto L_0x0089
        L_0x0087:
            r0 = -1
            r4 = 0
        L_0x0089:
            if (r4 == 0) goto L_0x00b0
            int r5 = r8.getExpandedRangeAddressIndex()
            if (r5 < 0) goto L_0x00b0
            int r5 = r8.getRowNumber()
            com.app.office.ss.model.baseModel.Row r1 = r1.getRow(r5)
            int r5 = r8.getExpandedRangeAddressIndex()
            com.app.office.ss.other.ExpandedCellRangeAddress r1 = r1.getExpandedRangeAddress(r5)
            com.app.office.ss.model.CellRangeAddress r1 = r1.getRangedAddress()
            int r8 = r8.getColNumber()
            int r1 = r1.getFirstColumn()
            if (r8 == r1) goto L_0x00b0
            goto L_0x00b1
        L_0x00b0:
            r3 = r4
        L_0x00b1:
            if (r3 == 0) goto L_0x00b4
            return r0
        L_0x00b4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellBorderView.LeftBorder(com.app.office.ss.model.baseModel.Cell):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ad, code lost:
        if (r8.getColNumber() != r1.getRow(r8.getRowNumber()).getExpandedRangeAddress(r8.getExpandedRangeAddressIndex()).getRangedAddress().getLastColumn()) goto L_0x00b1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int RightBorder(com.app.office.ss.model.baseModel.Cell r8) {
        /*
            r7 = this;
            com.app.office.ss.model.style.CellStyle r0 = r8.getCellStyle()
            com.app.office.ss.view.SheetView r1 = r7.sheetView
            com.app.office.ss.model.baseModel.Sheet r1 = r1.getCurrentSheet()
            int r2 = r8.getRangeAddressIndex()
            if (r2 < 0) goto L_0x002f
            int r2 = r8.getRangeAddressIndex()
            com.app.office.ss.model.CellRangeAddress r2 = r1.getMergeRange(r2)
            int r3 = r2.getLastRow()
            com.app.office.ss.model.baseModel.Row r3 = r1.getRow(r3)
            int r2 = r2.getLastColumn()
            com.app.office.ss.model.baseModel.Cell r2 = r3.getCell(r2)
            if (r2 == 0) goto L_0x002f
            com.app.office.ss.model.style.CellStyle r0 = r2.getCellStyle()
            r8 = r2
        L_0x002f:
            r2 = -1
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L_0x003f
            short r5 = r0.getBorderRight()
            if (r5 <= 0) goto L_0x003f
            short r0 = r0.getBorderRightColorIdx()
            goto L_0x0089
        L_0x003f:
            int r0 = r8.getRowNumber()
            com.app.office.ss.model.baseModel.Row r0 = r1.getRowByColumnsStyle(r0)
            int r5 = r8.getColNumber()
            int r5 = r5 + r4
            com.app.office.ss.model.baseModel.Cell r0 = r0.getCell(r5)
            if (r0 == 0) goto L_0x0087
            com.app.office.ss.model.style.CellStyle r5 = r0.getCellStyle()
            int r6 = r0.getRangeAddressIndex()
            if (r6 < 0) goto L_0x007a
            int r0 = r0.getRangeAddressIndex()
            com.app.office.ss.model.CellRangeAddress r0 = r1.getMergeRange(r0)
            int r6 = r0.getFirstRow()
            com.app.office.ss.model.baseModel.Row r6 = r1.getRow(r6)
            int r0 = r0.getFirstColumn()
            com.app.office.ss.model.baseModel.Cell r0 = r6.getCell(r0)
            if (r0 == 0) goto L_0x007a
            com.app.office.ss.model.style.CellStyle r5 = r0.getCellStyle()
        L_0x007a:
            if (r5 == 0) goto L_0x0087
            short r0 = r5.getBorderLeft()
            if (r0 <= 0) goto L_0x0087
            short r0 = r5.getBorderLeftColorIdx()
            goto L_0x0089
        L_0x0087:
            r0 = -1
            r4 = 0
        L_0x0089:
            if (r4 == 0) goto L_0x00b0
            int r5 = r8.getExpandedRangeAddressIndex()
            if (r5 < 0) goto L_0x00b0
            int r5 = r8.getRowNumber()
            com.app.office.ss.model.baseModel.Row r1 = r1.getRow(r5)
            int r5 = r8.getExpandedRangeAddressIndex()
            com.app.office.ss.other.ExpandedCellRangeAddress r1 = r1.getExpandedRangeAddress(r5)
            com.app.office.ss.model.CellRangeAddress r1 = r1.getRangedAddress()
            int r8 = r8.getColNumber()
            int r1 = r1.getLastColumn()
            if (r8 == r1) goto L_0x00b0
            goto L_0x00b1
        L_0x00b0:
            r3 = r4
        L_0x00b1:
            if (r3 == 0) goto L_0x00b4
            return r0
        L_0x00b4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.ss.view.CellBorderView.RightBorder(com.app.office.ss.model.baseModel.Cell):int");
    }

    private int TopBorder(Cell cell) {
        Cell cell2;
        CellStyle cellStyle = cell.getCellStyle();
        Sheet currentSheet = this.sheetView.getCurrentSheet();
        if (cell.getRangeAddressIndex() >= 0) {
            CellRangeAddress mergeRange = currentSheet.getMergeRange(cell.getRangeAddressIndex());
            Cell cell3 = currentSheet.getRow(mergeRange.getFirstRow()).getCell(mergeRange.getFirstColumn());
            if (cell3 != null) {
                cellStyle = cell3.getCellStyle();
                cell = cell3;
            }
        }
        if (cellStyle != null && cellStyle.getBorderTop() > 0) {
            return cellStyle.getBorderTopColorIdx();
        }
        Row rowByColumnsStyle = currentSheet.getRowByColumnsStyle(cell.getRowNumber() - 1);
        if (rowByColumnsStyle == null || (cell2 = rowByColumnsStyle.getCell(cell.getColNumber())) == null) {
            return -1;
        }
        CellStyle cellStyle2 = cell2.getCellStyle();
        if (cell2.getRangeAddressIndex() >= 0) {
            CellRangeAddress mergeRange2 = currentSheet.getMergeRange(cell2.getRangeAddressIndex());
            Cell cell4 = currentSheet.getRow(mergeRange2.getLastRow()).getCell(mergeRange2.getLastColumn());
            if (cell4 != null) {
                cellStyle2 = cell4.getCellStyle();
            }
        }
        if (cellStyle2 == null || cellStyle2.getBorderBottom() <= 0) {
            return -1;
        }
        return cellStyle2.getBorderBottomColorIdx();
    }

    private int BottomBorder(Cell cell) {
        Cell cell2;
        CellStyle cellStyle = cell.getCellStyle();
        Sheet currentSheet = this.sheetView.getCurrentSheet();
        if (cell.getRangeAddressIndex() >= 0) {
            CellRangeAddress mergeRange = currentSheet.getMergeRange(cell.getRangeAddressIndex());
            Cell cell3 = currentSheet.getRow(mergeRange.getLastRow()).getCell(mergeRange.getLastColumn());
            if (cell3 != null) {
                cellStyle = cell3.getCellStyle();
                cell = cell3;
            }
        }
        if (cellStyle != null && cellStyle.getBorderBottom() > 0) {
            return cellStyle.getBorderBottomColorIdx();
        }
        Row rowByColumnsStyle = currentSheet.getRowByColumnsStyle(cell.getRowNumber() + 1);
        if (rowByColumnsStyle == null || (cell2 = rowByColumnsStyle.getCell(cell.getColNumber())) == null) {
            return -1;
        }
        CellStyle cellStyle2 = cell2.getCellStyle();
        if (cell2.getRangeAddressIndex() >= 0) {
            CellRangeAddress mergeRange2 = currentSheet.getMergeRange(cell2.getRangeAddressIndex());
            Cell cell4 = currentSheet.getRow(mergeRange2.getFirstRow()).getCell(mergeRange2.getFirstColumn());
            if (cell4 != null) {
                cellStyle2 = cell4.getCellStyle();
            }
        }
        if (cellStyle2 == null || cellStyle2.getBorderTop() <= 0) {
            return -1;
        }
        return cellStyle2.getBorderTopColorIdx();
    }

    public void drawActiveCellBorder(Canvas canvas, RectF rectF, short s) {
        Rect clipBounds = canvas.getClipBounds();
        clipBounds.left = this.sheetView.getRowHeaderWidth();
        clipBounds.top = this.sheetView.getColumnHeaderHeight();
        canvas.save();
        canvas.clipRect(clipBounds);
        Paint paint = PaintKit.instance().getPaint();
        int color = paint.getColor();
        paint.setColor(-16777216);
        if (s == 0 && rectF.left != rectF.right && rectF.top != rectF.bottom) {
            Canvas canvas2 = canvas;
            Paint paint2 = paint;
            canvas2.drawRect(rectF.left - 2.0f, rectF.top - 2.0f, rectF.left + 1.0f, rectF.bottom + 2.0f, paint2);
            canvas2.drawRect(rectF.left - 2.0f, rectF.top - 2.0f, rectF.right + 2.0f, rectF.top + 1.0f, paint2);
            canvas2.drawRect(rectF.right - 1.0f, rectF.top - 2.0f, rectF.right + 2.0f, rectF.bottom + 2.0f, paint2);
            canvas2.drawRect(rectF.left - 2.0f, rectF.bottom - 1.0f, rectF.right + 2.0f, rectF.bottom + 2.0f, paint2);
        } else if (s == 1 && rectF.top != rectF.bottom) {
            Canvas canvas3 = canvas;
            Paint paint3 = paint;
            canvas3.drawRect((float) (clipBounds.left - 2), rectF.top - 2.0f, (float) (clipBounds.right + 10), rectF.top + 1.0f, paint3);
            canvas3.drawRect((float) (clipBounds.left - 2), rectF.bottom - 1.0f, (float) (clipBounds.right + 10), rectF.bottom + 2.0f, paint3);
        } else if (s == 2 && rectF.left != rectF.right) {
            Canvas canvas4 = canvas;
            Paint paint4 = paint;
            canvas4.drawRect(rectF.left - 2.0f, (float) (clipBounds.top - 2), rectF.left + 1.0f, (float) (clipBounds.bottom + 2), paint4);
            canvas4.drawRect(rectF.right - 1.0f, (float) (clipBounds.top - 2), rectF.right + 2.0f, (float) (clipBounds.bottom + 2), paint4);
        }
        paint.setColor(color);
        canvas.restore();
    }

    public void dispose() {
        this.sheetView = null;
    }
}
