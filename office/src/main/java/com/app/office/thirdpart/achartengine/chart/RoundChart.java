package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.model.CategorySeries;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import java.util.ArrayList;

public abstract class RoundChart extends AbstractChart {
    protected static final int SHAPE_WIDTH = 10;
    protected CategorySeries mDataset;
    protected DefaultRenderer mRenderer;

    public RoundChart(CategorySeries categorySeries, DefaultRenderer defaultRenderer) {
        this.mDataset = categorySeries;
        this.mRenderer = defaultRenderer;
    }

    public void setZoomRate(float f) {
        this.mRenderer.setZoomRate(f);
    }

    public float getZoomRate() {
        return this.mRenderer.getZoomRate();
    }

    public void draw(Canvas canvas, IControl iControl, int i, int i2, int i3, int i4, Paint paint) {
        String[] strArr;
        int i5;
        int i6 = i;
        int i7 = i2;
        Paint paint2 = paint;
        int i8 = i6 + i3;
        int i9 = i7 + i4;
        Rect rect = new Rect(i6, i7, i8, i9);
        canvas.save();
        canvas.clipRect(rect);
        paint2.setAntiAlias(this.mRenderer.isAntialiasing());
        paint2.setStyle(Paint.Style.FILL);
        paint2.setTextSize(this.mRenderer.getLabelsTextSize());
        int legendHeight = this.mRenderer.getLegendHeight();
        if (this.mRenderer.isShowLegend() && legendHeight == 0) {
            legendHeight = i4 / 5;
        }
        int itemCount = this.mDataset.getItemCount();
        String[] strArr2 = new String[itemCount];
        double d = 0.0d;
        for (int i10 = 0; i10 < itemCount; i10++) {
            d += this.mDataset.getValue(i10);
            strArr2[i10] = this.mDataset.getCategory(i10);
        }
        if (this.mRenderer.isFitLegend()) {
            strArr = strArr2;
            i5 = itemCount;
            legendHeight = drawLegend(canvas, this.mRenderer, strArr2, i, i2, i3, i4, paint, true);
        } else {
            strArr = strArr2;
            i5 = itemCount;
        }
        int i11 = i9 - legendHeight;
        drawBackground(this.mRenderer, canvas, i, i2, i3, i4, paint, false, 0);
        int min = (int) (((double) Math.min(Math.abs(i8 - i6), Math.abs(i11 - i7))) * 0.35d * ((double) this.mRenderer.getScale()));
        int i12 = (i6 + i8) / 2;
        int i13 = (i11 + i7) / 2;
        float f = (float) min;
        float f2 = f * 0.9f;
        float f3 = f * 1.1f;
        RectF rectF = new RectF((float) (i12 - min), (float) (i13 - min), (float) (i12 + min), (float) (i13 + min));
        ArrayList arrayList = new ArrayList();
        int i14 = 0;
        float f4 = 0.0f;
        while (i14 < i5) {
            paint.setColor(this.mRenderer.getSeriesRendererAt(i14).getColor());
            float value = (float) ((((double) ((float) this.mDataset.getValue(i14))) / d) * 360.0d);
            canvas.drawArc(rectF, f4, value, true, paint);
            float f5 = value;
            int i15 = i8;
            drawLabel(canvas, this.mDataset.getCategory(i14), this.mRenderer, arrayList, i12, i13, f2, f3, f4, f5, i, i15, paint);
            f4 += f5;
            i14++;
            int i16 = i2;
            i5 = i5;
            rectF = rectF;
            i8 = i15;
        }
        arrayList.clear();
        drawLegend(canvas, this.mRenderer, strArr, i, i2, i3, i4, paint, false);
        canvas.restore();
    }

    public int getLegendShapeWidth(int i) {
        return (int) getRenderer().getLegendTextSize();
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint) {
        float legendShapeWidth = ((float) getLegendShapeWidth(0)) * this.mRenderer.getZoomRate();
        float f3 = legendShapeWidth / 2.0f;
        float f4 = f + f3;
        Canvas canvas2 = canvas;
        float f5 = f2 - f3;
        float f6 = legendShapeWidth + f4;
        float f7 = f2 + f3;
        Paint paint2 = paint;
        canvas2.drawRect(f4, f5, f6, f7, paint2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(-16777216);
        canvas2.drawRect((float) Math.round(f4), f5, f6, f7, paint2);
        paint.setStyle(Paint.Style.FILL);
    }

    public DefaultRenderer getRenderer() {
        return this.mRenderer;
    }
}
