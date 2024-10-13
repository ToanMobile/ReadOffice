package com.app.office.thirdpart.achartengine.renderers;

import android.graphics.Color;
import com.app.office.thirdpart.achartengine.chart.PointStyle;

public class XYSeriesRenderer extends SimpleSeriesRenderer {
    private boolean mFillBelowLine = false;
    private int mFillColor = Color.argb(125, 0, 0, 200);
    private boolean mFillPoints = true;
    private float mLineWidth = 3.0f;
    private PointStyle mPointStyle = PointStyle.POINT;

    public boolean isFillBelowLine() {
        return this.mFillBelowLine;
    }

    public void setFillBelowLine(boolean z) {
        this.mFillBelowLine = z;
    }

    public boolean isFillPoints() {
        return this.mFillPoints;
    }

    public void setFillPoints(boolean z) {
        this.mFillPoints = z;
    }

    public int getFillBelowLineColor() {
        return this.mFillColor;
    }

    public void setFillBelowLineColor(int i) {
        this.mFillColor = i;
    }

    public PointStyle getPointStyle() {
        return this.mPointStyle;
    }

    public void setPointStyle(PointStyle pointStyle) {
        this.mPointStyle = pointStyle;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public void setLineWidth(float f) {
        this.mLineWidth = f;
    }
}
