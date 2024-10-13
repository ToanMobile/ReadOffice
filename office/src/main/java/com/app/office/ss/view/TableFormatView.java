package com.app.office.ss.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.app.office.common.PaintKit;
import com.app.office.ss.model.CellRangeAddress;
import com.app.office.ss.model.baseModel.Workbook;
import com.app.office.ss.model.style.CellStyle;
import com.app.office.ss.model.table.SSTable;
import com.app.office.ss.model.table.TableFormatManager;
import com.app.office.ss.util.ModelUtil;

public class TableFormatView {
    private SheetView sheetView;

    public TableFormatView(SheetView sheetView2) {
        this.sheetView = sheetView2;
    }

    public void draw(Canvas canvas) {
        Paint paint = PaintKit.instance().getPaint();
        int color = paint.getColor();
        canvas.save();
        TableFormatManager tableFormatManager = this.sheetView.getCurrentSheet().getWorkbook().getTableFormatManager();
        SSTable[] tables = this.sheetView.getCurrentSheet().getTables();
        if (!(tables == null || tableFormatManager == null)) {
            for (SSTable sSTable : tables) {
                if (sSTable.isHeaderRowShown() && (sSTable.getHeaderRowDxfId() >= 0 || sSTable.getHeaderRowBorderDxfId() >= 0)) {
                    drawHeaderRowFormat(canvas, tableFormatManager, sSTable, paint);
                }
                if (sSTable.isTotalRowShown() && (sSTable.getTotalsRowDxfId() >= 0 || sSTable.getTotalsRowBorderDxfId() >= 0)) {
                    drawTotalRowFormat(canvas, tableFormatManager, sSTable, paint);
                }
                if (sSTable.getTableBorderDxfId() >= 0) {
                    drawTableBorders(canvas, tableFormatManager, sSTable, paint);
                }
            }
        }
        paint.setColor(color);
        canvas.restore();
    }

    private void drawHeaderRowFormat(Canvas canvas, TableFormatManager tableFormatManager, SSTable sSTable, Paint paint) {
        Workbook workbook = this.sheetView.getCurrentSheet().getWorkbook();
        CellRangeAddress tableReference = sSTable.getTableReference();
        CellStyle format = tableFormatManager.getFormat(sSTable.getHeaderRowDxfId());
        CellStyle format2 = tableFormatManager.getFormat(sSTable.getHeaderRowBorderDxfId());
        RectF cellAnchor = ModelUtil.instance().getCellAnchor(this.sheetView, tableReference.getFirstRow(), tableReference.getFirstColumn(), tableReference.getLastColumn());
        if (format != null) {
            drawFormatBorders(canvas, paint, workbook, format, cellAnchor);
        }
        if (format2 != null) {
            drawFormatBorders(canvas, paint, workbook, format2, cellAnchor);
        }
    }

    private void drawTotalRowFormat(Canvas canvas, TableFormatManager tableFormatManager, SSTable sSTable, Paint paint) {
        Workbook workbook = this.sheetView.getCurrentSheet().getWorkbook();
        CellRangeAddress tableReference = sSTable.getTableReference();
        CellStyle format = tableFormatManager.getFormat(sSTable.getTotalsRowDxfId());
        CellStyle format2 = tableFormatManager.getFormat(sSTable.getTotalsRowBorderDxfId());
        RectF cellAnchor = ModelUtil.instance().getCellAnchor(this.sheetView, tableReference.getLastRow(), tableReference.getFirstColumn(), tableReference.getLastColumn());
        if (format != null) {
            drawFormatBorders(canvas, paint, workbook, format, cellAnchor);
        }
        if (format2 != null) {
            drawFormatBorders(canvas, paint, workbook, format2, cellAnchor);
        }
    }

    private void drawTableBorders(Canvas canvas, TableFormatManager tableFormatManager, SSTable sSTable, Paint paint) {
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        drawFormatBorders(canvas2, paint2, this.sheetView.getCurrentSheet().getWorkbook(), tableFormatManager.getFormat(sSTable.getTableBorderDxfId()), ModelUtil.instance().getCellRangeAddressAnchor(this.sheetView, sSTable.getTableReference()));
    }

    private void drawFormatBorders(Canvas canvas, Paint paint, Workbook workbook, CellStyle cellStyle, RectF rectF) {
        if (rectF.left > ((float) this.sheetView.getRowHeaderWidth()) && cellStyle.getBorderLeft() != 0) {
            paint.setColor(workbook.getColor(cellStyle.getBorderLeftColorIdx()));
            canvas.drawRect(rectF.left, rectF.top, rectF.left + 1.0f, rectF.bottom, paint);
        }
        if (rectF.top > ((float) this.sheetView.getColumnHeaderHeight()) && cellStyle.getBorderTop() != 0) {
            paint.setColor(workbook.getColor(cellStyle.getBorderTopColorIdx()));
            canvas.drawRect(rectF.left, rectF.top, rectF.right, rectF.top + 1.0f, paint);
        }
        if (rectF.right > ((float) this.sheetView.getRowHeaderWidth()) && cellStyle.getBorderRight() != 0) {
            paint.setColor(workbook.getColor(cellStyle.getBorderRightColorIdx()));
            canvas.drawRect(rectF.right, rectF.top, rectF.right + 1.0f, rectF.bottom, paint);
        }
        if (rectF.bottom > ((float) this.sheetView.getColumnHeaderHeight()) && cellStyle.getBorderBottom() != 0) {
            paint.setColor(workbook.getColor(cellStyle.getBorderBottomColorIdx()));
            canvas.drawRect(rectF.left, rectF.bottom, rectF.right, rectF.bottom + 1.0f, paint);
        }
    }
}
