package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.model.CategorySeries;
import com.app.office.thirdpart.achartengine.model.MultipleCategorySeries;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import java.util.ArrayList;

public class DoughnutChart extends RoundChart {
    private MultipleCategorySeries mDataset;
    private int mStep;

    public int getLegendShapeWidth(int i) {
        return 10;
    }

    public DoughnutChart(MultipleCategorySeries multipleCategorySeries, DefaultRenderer defaultRenderer) {
        super((CategorySeries) null, defaultRenderer);
        this.mDataset = multipleCategorySeries;
    }

    public void draw(Canvas canvas, IControl iControl, int i, int i2, int i3, int i4, Paint paint) {
        Paint paint2;
        DoughnutChart doughnutChart = this;
        Paint paint3 = paint;
        paint3.setAntiAlias(doughnutChart.mRenderer.isAntialiasing());
        paint3.setStyle(Paint.Style.FILL);
        paint3.setTextSize(doughnutChart.mRenderer.getLabelsTextSize());
        int legendHeight = doughnutChart.mRenderer.getLegendHeight();
        if (doughnutChart.mRenderer.isShowLegend() && legendHeight == 0) {
            legendHeight = i4 / 5;
        }
        int i5 = i + i3;
        int categoriesCount = doughnutChart.mDataset.getCategoriesCount();
        String[] strArr = new String[categoriesCount];
        for (int i6 = 0; i6 < categoriesCount; i6++) {
            strArr[i6] = doughnutChart.mDataset.getCategory(i6);
        }
        if (doughnutChart.mRenderer.isFitLegend()) {
            legendHeight = drawLegend(canvas, doughnutChart.mRenderer, strArr, i, i2, i3, i4, paint, true);
        }
        int i7 = (i2 + i4) - legendHeight;
        drawBackground(doughnutChart.mRenderer, canvas, i, i2, i3, i4, paint, false, 0);
        doughnutChart.mStep = 7;
        double d = 0.2d / ((double) categoriesCount);
        double min = (double) Math.min(Math.abs(i5 - i), Math.abs(i7 - i2));
        int scale = (int) (((double) doughnutChart.mRenderer.getScale()) * 0.35d * min);
        int i8 = (i + i5) / 2;
        int i9 = (i7 + i2) / 2;
        float f = (float) scale;
        float f2 = f * 1.1f;
        ArrayList arrayList = new ArrayList();
        int i10 = scale;
        float f3 = 0.9f * f;
        int i11 = 0;
        while (i11 < categoriesCount) {
            int itemCount = doughnutChart.mDataset.getItemCount(i11);
            String[] strArr2 = new String[itemCount];
            double d2 = 0.0d;
            for (int i12 = 0; i12 < itemCount; i12++) {
                d2 += doughnutChart.mDataset.getValues(i11)[i12];
                strArr2[i12] = doughnutChart.mDataset.getTitles(i11)[i12];
            }
            RectF rectF = new RectF((float) (i8 - i10), (float) (i9 - i10), (float) (i8 + i10), (float) (i9 + i10));
            int i13 = 0;
            float f4 = 0.0f;
            while (i13 < itemCount) {
                paint3.setColor(doughnutChart.mRenderer.getSeriesRendererAt(i13).getColor());
                float f5 = (float) ((((double) ((float) doughnutChart.mDataset.getValues(i11)[i13])) / d2) * 360.0d);
                float f6 = f5;
                int i14 = i13;
                canvas.drawArc(rectF, f4, f5, true, paint);
                drawLabel(canvas, doughnutChart.mDataset.getTitles(i11)[i14], doughnutChart.mRenderer, arrayList, i8, i9, f3, f2, f4, f6, i, i5, paint);
                f4 += f6;
                i13 = i14 + 1;
                i10 = i10;
                f3 = f3;
                rectF = rectF;
                itemCount = itemCount;
                i11 = i11;
                min = min;
                strArr = strArr;
                categoriesCount = categoriesCount;
                doughnutChart = this;
                paint3 = paint;
            }
            int i15 = i11;
            double d3 = min;
            String[] strArr3 = strArr;
            int i16 = categoriesCount;
            double d4 = d3 * d;
            int i17 = (int) (((double) i10) - d4);
            f3 = (float) (((double) f3) - (d4 - 2.0d));
            if (this.mRenderer.getBackgroundColor() != 0) {
                paint2 = paint;
                paint2.setColor(this.mRenderer.getBackgroundColor());
            } else {
                paint2 = paint;
                paint2.setColor(-1);
            }
            paint2.setStyle(Paint.Style.FILL);
            canvas.drawArc(new RectF((float) (i8 - i17), (float) (i9 - i17), (float) (i8 + i17), (float) (i9 + i17)), 0.0f, 360.0f, true, paint);
            i11 = i15 + 1;
            paint3 = paint2;
            doughnutChart = this;
            min = d3;
            strArr = strArr3;
            categoriesCount = i16;
            i10 = i17 - 1;
        }
        Paint paint4 = paint3;
        arrayList.clear();
        drawLegend(canvas, doughnutChart.mRenderer, strArr, i, i2, i3, i4, paint, false);
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint) {
        int i2 = this.mStep - 1;
        this.mStep = i2;
        canvas.drawCircle((f + 10.0f) - ((float) i2), f2, (float) i2, paint);
    }
}
