package com.app.office.ss.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.common.PaintKit;
import com.app.office.constant.SSConstant;
import com.app.office.ss.model.baseModel.Sheet;
import com.app.office.ss.other.SheetScroller;
import com.app.office.ss.util.HeaderUtil;

public class ColumnHeader {
    private int columnHeaderHeight = 30;
    private Rect rect;
    private SheetView sheetview;
    private float x;

    public ColumnHeader(SheetView sheetView) {
        this.sheetview = sheetView;
        this.rect = new Rect();
    }

    public int getColumnRightBound(Canvas canvas, float f) {
        canvas.save();
        Rect clipBounds = canvas.getClipBounds();
        Paint paint = PaintKit.instance().getPaint();
        paint.setTextSize(16.0f * f);
        this.x = (float) this.sheetview.getRowHeaderWidth();
        layoutColumnLine(canvas, 0, f, paint);
        canvas.restore();
        return Math.min((int) this.x, clipBounds.right);
    }

    private void layoutColumnLine(Canvas canvas, int i, float f, Paint paint) {
        Rect clipBounds = canvas.getClipBounds();
        Sheet currentSheet = this.sheetview.getCurrentSheet();
        SheetScroller minRowAndColumnInformation = this.sheetview.getMinRowAndColumnInformation();
        if (minRowAndColumnInformation.getMinColumnIndex() > i) {
            i = minRowAndColumnInformation.getMinColumnIndex();
        }
        if (!minRowAndColumnInformation.isColumnAllVisible()) {
            i++;
            this.x = (float) (((double) this.x) + (minRowAndColumnInformation.getVisibleColumnWidth() * ((double) f)));
        }
        int i2 = currentSheet.getWorkbook().isBefore07Version() ? 256 : 16384;
        while (this.x <= ((float) clipBounds.right) && i < i2) {
            if (!currentSheet.isColumnHidden(i)) {
                this.x += currentSheet.getColumnPixelWidth(i) * f;
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
        this.x = (float) this.sheetview.getRowHeaderWidth();
        canvas.getClipBounds();
        Paint paint2 = paint;
        drawColumnLine(canvas, i, 0, f, paint2);
        paint.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
        int i2 = this.columnHeaderHeight;
        canvas.drawRect(0.0f, (float) i2, this.x, (float) (i2 + 1), paint2);
        paint.setColor(color);
        paint.setTextSize(textSize);
        canvas.restore();
    }

    private void drawFirstVisibleColumn(Canvas canvas, float f, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        SheetScroller minRowAndColumnInformation = this.sheetview.getMinRowAndColumnInformation();
        float columnWidth = minRowAndColumnInformation.getColumnWidth() * f;
        float visibleColumnWidth = (float) (minRowAndColumnInformation.getVisibleColumnWidth() * ((double) f));
        if (HeaderUtil.instance().isActiveColumn(this.sheetview.getCurrentSheet(), minRowAndColumnInformation.getMinColumnIndex())) {
            paint.setColor(SSConstant.ACTIVE_COLOR);
        } else {
            paint.setColor(SSConstant.HEADER_FILL_COLOR);
        }
        Rect rect2 = this.rect;
        float f2 = this.x;
        rect2.set((int) f2, 0, (int) (f2 + visibleColumnWidth), this.columnHeaderHeight);
        canvas.drawRect(this.rect, paint);
        paint.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
        float f3 = this.x;
        canvas.drawRect(f3, 0.0f, f3 + 1.0f, (float) this.columnHeaderHeight, paint);
        canvas.save();
        canvas.clipRect(this.rect);
        paint.setColor(-16777216);
        String columnHeaderTextByIndex = HeaderUtil.instance().getColumnHeaderTextByIndex(minRowAndColumnInformation.getMinColumnIndex());
        canvas.drawText(columnHeaderTextByIndex, (this.x + ((columnWidth - paint.measureText(columnHeaderTextByIndex)) / 2.0f)) - (columnWidth - visibleColumnWidth), ((float) (((int) (((double) this.columnHeaderHeight) - Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)))) / 2)) - fontMetrics.ascent, paint);
        canvas.restore();
    }

    private void drawColumnLine(Canvas canvas, int i, int i2, float f, Paint paint) {
        SheetScroller sheetScroller;
        int i3;
        Canvas canvas2 = canvas;
        int i4 = i;
        float f2 = f;
        Paint paint2 = paint;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Rect clipBounds = canvas.getClipBounds();
        Sheet currentSheet = this.sheetview.getCurrentSheet();
        SheetScroller minRowAndColumnInformation = this.sheetview.getMinRowAndColumnInformation();
        int i5 = i2;
        int minColumnIndex = minRowAndColumnInformation.getMinColumnIndex() > i5 ? minRowAndColumnInformation.getMinColumnIndex() : i5;
        if (!minRowAndColumnInformation.isColumnAllVisible()) {
            drawFirstVisibleColumn(canvas2, f2, paint2);
            minColumnIndex++;
            sheetScroller = minRowAndColumnInformation;
            this.x = (float) (((double) this.x) + (minRowAndColumnInformation.getVisibleColumnWidth() * ((double) f2)));
        } else {
            sheetScroller = minRowAndColumnInformation;
        }
        int i6 = currentSheet.getWorkbook().isBefore07Version() ? 256 : 16384;
        int i7 = minColumnIndex;
        while (this.x <= ((float) clipBounds.right) && i7 < i6) {
            if (currentSheet.isColumnHidden(i7)) {
                paint2.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
                float f3 = this.x;
                canvas.drawRect(f3 - 1.0f, 0.0f, f3 + 1.0f, (float) this.columnHeaderHeight, paint);
                i7++;
            } else {
                float columnPixelWidth = currentSheet.getColumnPixelWidth(i7) * f2;
                if (HeaderUtil.instance().isActiveColumn(this.sheetview.getCurrentSheet(), i7)) {
                    paint2.setColor(SSConstant.ACTIVE_COLOR);
                } else {
                    paint2.setColor(SSConstant.HEADER_FILL_COLOR);
                }
                Rect rect2 = this.rect;
                float f4 = this.x;
                rect2.set((int) f4, 0, (int) (f4 + columnPixelWidth), this.columnHeaderHeight);
                canvas2.drawRect(this.rect, paint2);
                if (i7 != sheetScroller.getMinColumnIndex()) {
                    paint2.setColor(SSConstant.GRIDLINE_COLOR);
                    float f5 = this.x;
                    i3 = SSConstant.HEADER_GRIDLINE_COLOR;
                    canvas.drawRect(f5, 0.0f, f5 + 1.0f, (float) i4, paint);
                } else {
                    i3 = SSConstant.HEADER_GRIDLINE_COLOR;
                }
                paint2.setColor(i3);
                float f6 = this.x;
                canvas.drawRect(f6, 0.0f, f6 + 1.0f, (float) this.columnHeaderHeight, paint);
                canvas.save();
                canvas2.clipRect(this.rect);
                paint2.setColor(-16777216);
                String columnHeaderTextByIndex = HeaderUtil.instance().getColumnHeaderTextByIndex(i7);
                canvas2.drawText(columnHeaderTextByIndex, this.x + ((columnPixelWidth - ((float) ((int) paint2.measureText(columnHeaderTextByIndex)))) / 2.0f), ((float) (((int) (((double) this.columnHeaderHeight) - Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)))) / 2)) - fontMetrics.ascent, paint2);
                canvas.restore();
                this.x += columnPixelWidth;
                i7++;
                f2 = f;
            }
        }
        paint2.setColor(SSConstant.GRIDLINE_COLOR);
        float f7 = this.x;
        Paint paint3 = paint;
        canvas.drawRect(f7, 0.0f, f7 + 1.0f, (float) i4, paint3);
        paint2.setColor(SSConstant.HEADER_GRIDLINE_COLOR);
        float f8 = this.x;
        canvas.drawRect(f8, 0.0f, f8 + 1.0f, (float) this.columnHeaderHeight, paint3);
        if (this.x < ((float) clipBounds.right)) {
            paint2.setColor(SSConstant.HEADER_FILL_COLOR);
            this.rect.set(((int) this.x) + 1, 0, clipBounds.right, clipBounds.bottom);
            canvas2.drawRect(this.rect, paint2);
        }
    }

    public int getColumnHeaderHeight() {
        return this.columnHeaderHeight;
    }

    public void setColumnHeaderHeight(int i) {
        this.columnHeaderHeight = i;
    }

    public void calculateColumnHeaderHeight(float f) {
        this.columnHeaderHeight = Math.round(f * 30.0f);
    }

    public void dispose() {
        this.sheetview = null;
        this.rect = null;
    }
}
