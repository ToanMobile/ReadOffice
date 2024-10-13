package com.app.office.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.java.awt.Dimension;

public class Scrollbar {
    private final int SCROLLBAR_COLOR = -1891351484;
    private final int SCROLLBAR_COLOR_ALPHA = 125;
    private final int SCROLLBAR_OFFBORDER = 2;
    private final int SCROLLBAR_SIZE = 5;
    private Dimension pageSize = new Dimension();
    private RectF rect = new RectF();

    public void setPageSize(int i, int i2) {
        this.pageSize.setSize(i, i2);
    }

    public void draw(Canvas canvas, int i, int i2, Paint paint) {
        Rect clipBounds = canvas.getClipBounds();
        int color = paint.getColor();
        int alpha = paint.getAlpha();
        paint.setColor(-1891351484);
        paint.setAlpha(125);
        if (this.pageSize.width > clipBounds.right) {
            drawHorizontalScrollBar(canvas, i, paint);
        }
        if (this.pageSize.height > clipBounds.bottom) {
            drawVerticalScrollBar(canvas, i2, paint);
        }
        paint.setColor(color);
        paint.setAlpha(alpha);
    }

    private void drawHorizontalScrollBar(Canvas canvas, int i, Paint paint) {
        Rect clipBounds = canvas.getClipBounds();
        float f = (float) ((clipBounds.right * clipBounds.right) / this.pageSize.width);
        float f2 = (((float) ((this.pageSize.width / 2) - i)) / (((float) this.pageSize.width) / ((float) clipBounds.right))) - (f / 2.0f);
        this.rect.set(f2, (float) ((clipBounds.bottom - 5) - 2), f + f2, (float) (clipBounds.bottom - 2));
        canvas.drawRoundRect(this.rect, 2.0f, 2.0f, paint);
    }

    private void drawVerticalScrollBar(Canvas canvas, int i, Paint paint) {
        Rect clipBounds = canvas.getClipBounds();
        float f = (float) ((clipBounds.bottom * clipBounds.bottom) / this.pageSize.height);
        float f2 = (((float) ((this.pageSize.height / 2) - i)) / (((float) this.pageSize.height) / ((float) clipBounds.bottom))) - (f / 2.0f);
        this.rect.set((float) ((clipBounds.right - 5) - 2), f2, (float) (clipBounds.right - 2), f + f2);
        canvas.drawRoundRect(this.rect, 2.0f, 2.0f, paint);
    }
}
