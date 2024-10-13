package com.app.office.ss.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.common.PaintKit;
import com.app.office.constant.SSConstant;
import com.app.office.ss.model.baseModel.Row;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.other.SheetScroller;
import com.app.office.ss.util.HeaderUtil;

public class RowHeader {
    private static final int EXTEDES_WIDTH = 10;
    private Rect rect;
    private int rowHeaderWidth = 50;
    private SheetView sheetview;
    private float y;

    public RowHeader(SheetView sheetView) {
        this.sheetview = sheetView;
    }

    public int getRowBottomBound(Canvas canvas, float f) {
        canvas.save();
        Rect clipBounds = canvas.getClipBounds();
        Paint paint = PaintKit.instance().getPaint();
        paint.setTextSize(16.0f * f);
        this.y = 30.0f * f;
        layoutRowLine(canvas, 0, f, paint);
        canvas.restore();
        return Math.min((int) this.y, clipBounds.bottom);
    }

    private void layoutRowLine(Canvas canvas, int i, float f, Paint paint) {
        Rect clipBounds = canvas.getClipBounds();
        Sheet currentSheet = this.sheetview.getCurrentSheet();
        SheetScroller minRowAndColumnInformation = this.sheetview.getMinRowAndColumnInformation();
        if (minRowAndColumnInformation.getMinRowIndex() > i) {
            i = minRowAndColumnInformation.getMinRowIndex();
        }
        if (!minRowAndColumnInformation.isRowAllVisible()) {
            i++;
            this.y = (float) (((double) this.y) + (minRowAndColumnInformation.getVisibleRowHeight() * ((double) f)));
        }
        int i2 = currentSheet.getWorkbook().isBefore07Version() ? 65536 : 1048576;
        while (this.y <= ((float) clipBounds.bottom) && i < i2) {
            Row row = currentSheet.getRow(i);
            if (row == null || !row.isZeroHeight()) {
                this.y += (row == null ? (float) this.sheetview.getCurrentSheet().getDefaultRowHeight() : row.getRowPixelHeight()) * f;
            }
            i++;
        }
    }

    public void draw(Canvas canvas, int i, float f) {
        canvas.save();
        Paint paint = PaintKit.instance().getPaint();
        int color = paint.getColor();
        float textSize = paint.getTextSize();
        paint.setTextSize(16.0f * f);
        this.y = 30.0f * f;
        Rect clipBounds = canvas.getClipBounds();
        this.rect = clipBounds;
        clipBounds.set(0, 0, this.rowHeaderWidth, clipBounds.bottom);
        paint.setColor(SSConstant.HEADER_FILL_COLOR);
        canvas.drawRect(this.rect, paint);
        drawRowLine(canvas, i, 0, f, paint);
        paint.setColor(color);
        paint.setTextSize(textSize);
        canvas.restore();
    }

    private void drawFirstVisibleHeader(Canvas canvas, float f, float f2, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        canvas.getClipBounds();
        SheetScroller minRowAndColumnInformation = this.sheetview.getMinRowAndColumnInformation();
        float rowHeight = minRowAndColumnInformation.getRowHeight() * f2;
        float visibleRowHeight = (float) (minRowAndColumnInformation.getVisibleRowHeight() * ((double) f2));
        if (HeaderUtil.instance().isActiveRow(this.sheetview.getCurrentSheet(), minRowAndColumnInformation.getMinRowIndex())) {
            paint.setColor(SSConstant.ACTIVE_COLOR);
        } else {
            paint.setColor(SSConstant.HEADER_FILL_COLOR);
        }
        Rect rect2 = this.rect;
        float f3 = this.y;
        rect2.set(0, (int) f3, this.rowHeaderWidth, (int) (f3 + visibleRowHeight));
        canvas.drawRect(this.rect, paint);
        paint.setColor(SSConstant.GRIDLINE_COLOR);
        float f4 = this.y;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        canvas2.drawRect(0.0f, f4, f, f4 + 1.0f, paint2);
        paint.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
        float f5 = this.y;
        canvas2.drawRect(0.0f, f5, (float) this.rowHeaderWidth, f5 + 1.0f, paint2);
        canvas.save();
        canvas.clipRect(this.rect);
        paint.setColor(-16777216);
        String valueOf = String.valueOf(minRowAndColumnInformation.getMinRowIndex() + 1);
        canvas.drawText(valueOf, (float) ((this.rowHeaderWidth - ((int) paint.measureText(valueOf))) / 2), ((this.y + ((float) ((int) (((double) rowHeight) - Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)))))) - fontMetrics.ascent) - (rowHeight - visibleRowHeight), paint);
        canvas.restore();
    }

    private void drawRowLine(Canvas canvas, int i, int i2, float f, Paint paint) {
        Canvas canvas2 = canvas;
        int i3 = i;
        float f2 = f;
        Paint paint2 = paint;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Rect clipBounds = canvas.getClipBounds();
        Sheet currentSheet = this.sheetview.getCurrentSheet();
        SheetScroller minRowAndColumnInformation = this.sheetview.getMinRowAndColumnInformation();
        int i4 = i2;
        int minRowIndex = minRowAndColumnInformation.getMinRowIndex() > i4 ? minRowAndColumnInformation.getMinRowIndex() : i4;
        if (!minRowAndColumnInformation.isRowAllVisible()) {
            drawFirstVisibleHeader(canvas2, (float) i3, f2, paint2);
            minRowIndex++;
            this.y = (float) (((double) this.y) + (minRowAndColumnInformation.getVisibleRowHeight() * ((double) f2)));
        }
        int i5 = currentSheet.getWorkbook().isBefore07Version() ? 65536 : 1048576;
        int i6 = minRowIndex;
        while (this.y <= ((float) clipBounds.bottom) && i6 < i5) {
            Row row = currentSheet.getRow(i6);
            if (row == null || !row.isZeroHeight()) {
                float defaultRowHeight = (row == null ? (float) this.sheetview.getCurrentSheet().getDefaultRowHeight() : row.getRowPixelHeight()) * f2;
                if (HeaderUtil.instance().isActiveRow(this.sheetview.getCurrentSheet(), i6)) {
                    paint2.setColor(SSConstant.ACTIVE_COLOR);
                } else {
                    paint2.setColor(SSConstant.HEADER_FILL_COLOR);
                }
                Rect rect2 = this.rect;
                float f3 = this.y;
                rect2.set(0, (int) f3, this.rowHeaderWidth, (int) (f3 + defaultRowHeight));
                canvas2.drawRect(this.rect, paint2);
                paint2.setColor(SSConstant.GRIDLINE_COLOR);
                float f4 = this.y;
                float f5 = defaultRowHeight;
                Paint paint3 = paint;
                canvas.drawRect(0.0f, f4, (float) i3, f4 + 1.0f, paint3);
                paint2.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
                float f6 = this.y;
                canvas.drawRect(0.0f, f6, (float) this.rowHeaderWidth, f6 + 1.0f, paint3);
                canvas.save();
                canvas2.clipRect(this.rect);
                paint2.setColor(-16777216);
                i6++;
                String valueOf = String.valueOf(i6);
                canvas2.drawText(valueOf, (float) ((this.rowHeaderWidth - ((int) paint2.measureText(valueOf))) / 2), (this.y + ((float) ((int) (((double) f5) - Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)))))) - fontMetrics.ascent, paint2);
                canvas.restore();
                this.y += f5;
                i5 = i5;
                f2 = f;
                currentSheet = currentSheet;
            } else {
                paint2.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
                float f7 = this.y;
                canvas.drawRect(0.0f, f7 - 1.0f, (float) this.rowHeaderWidth, f7 + 1.0f, paint);
                i6++;
            }
        }
        paint2.setColor(SSConstant.GRIDLINE_COLOR);
        float f8 = this.y;
        Paint paint4 = paint;
        canvas.drawRect(0.0f, f8, (float) i3, f8 + 1.0f, paint4);
        paint2.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
        float f9 = this.y;
        canvas.drawRect(0.0f, f9, (float) this.rowHeaderWidth, f9 + 1.0f, paint4);
        if (this.y < ((float) clipBounds.bottom)) {
            paint2.setColor(SSConstant.HEADER_FILL_COLOR);
            this.rect.set(0, (int) (this.y + 1.0f), clipBounds.right, clipBounds.bottom);
            canvas2.drawRect(this.rect, paint2);
        }
        paint2.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
        int i7 = this.rowHeaderWidth;
        canvas.drawRect((float) i7, 0.0f, (float) (i7 + 1), this.y, paint);
    }

    public void calculateRowHeaderWidth(float f) {
        Paint paint = PaintKit.instance().getPaint();
        paint.setTextSize(16.0f);
        int round = Math.round(paint.measureText(String.valueOf(this.sheetview.getCurrentMinRow()))) + 10;
        this.rowHeaderWidth = round;
        this.rowHeaderWidth = Math.round(((float) Math.max(round, 50)) * f);
    }

    public int getRowHeaderWidth() {
        return this.rowHeaderWidth;
    }

    public void setRowHeaderWidth(int i) {
        this.rowHeaderWidth = i;
    }

    public void dispose() {
        this.sheetview = null;
        this.rect = null;
    }
}
