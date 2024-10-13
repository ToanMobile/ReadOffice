package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.thirdpart.achartengine.model.XYMultipleSeriesDataset;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeChart extends LineChart {
    public static final long DAY = 86400000;
    public static final String TYPE = "Time";
    private String mDateFormat;

    public String getChartType() {
        return TYPE;
    }

    TimeChart() {
    }

    public TimeChart(XYMultipleSeriesDataset xYMultipleSeriesDataset, XYMultipleSeriesRenderer xYMultipleSeriesRenderer) {
        super(xYMultipleSeriesDataset, xYMultipleSeriesRenderer);
    }

    public String getDateFormat() {
        return this.mDateFormat;
    }

    public void setDateFormat(String str) {
        this.mDateFormat = str;
    }

    /* access modifiers changed from: protected */
    public void drawXLabels(List<Double> list, Double[] dArr, Canvas canvas, Paint paint, int i, int i2, float f, double d, double d2) {
        int i3;
        float f2;
        boolean z;
        List<Double> list2 = list;
        Paint paint2 = paint;
        int size = list.size();
        if (size > 0) {
            boolean isShowLabels = this.mRenderer.isShowLabels();
            boolean isShowGridH = this.mRenderer.isShowGridH();
            DateFormat dateFormat = getDateFormat(list2.get(0).doubleValue(), list2.get(size - 1).doubleValue());
            int i4 = 0;
            while (i4 < size) {
                long round = Math.round(list2.get(i4).doubleValue());
                float f3 = (float) (((double) i) + ((((double) round) - d2) * d));
                if (isShowLabels) {
                    paint2.setColor(this.mRenderer.getLabelsColor());
                    float f4 = f3;
                    f2 = f3;
                    float labelsTextSize = f + (this.mRenderer.getLabelsTextSize() / 3.0f);
                    i3 = size;
                    z = isShowLabels;
                    long j = round;
                    Paint paint3 = paint;
                    canvas.drawLine(f3, f, f4, labelsTextSize, paint3);
                    drawText(canvas, dateFormat.format(new Date(j)), f2, f + ((this.mRenderer.getLabelsTextSize() * 4.0f) / 3.0f), paint3, this.mRenderer.getXLabelsAngle());
                } else {
                    f2 = f3;
                    i3 = size;
                    z = isShowLabels;
                }
                if (isShowGridH) {
                    paint2.setColor(this.mRenderer.getGridColor());
                    canvas.drawLine(f2, f, f2, (float) i2, paint);
                } else {
                    int i5 = i2;
                }
                i4++;
                size = i3;
                isShowLabels = z;
            }
        }
    }

    private DateFormat getDateFormat(double d, double d2) {
        if (this.mDateFormat != null) {
            try {
                return new SimpleDateFormat(this.mDateFormat);
            } catch (Exception unused) {
            }
        }
        DateFormat dateInstance = SimpleDateFormat.getDateInstance(2);
        double d3 = d2 - d;
        if (d3 <= 8.64E7d || d3 >= 4.32E8d) {
            return d3 < 8.64E7d ? SimpleDateFormat.getTimeInstance(2) : dateInstance;
        }
        return SimpleDateFormat.getDateTimeInstance(3, 3);
    }
}
