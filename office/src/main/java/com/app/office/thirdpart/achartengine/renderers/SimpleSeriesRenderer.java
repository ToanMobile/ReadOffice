package com.app.office.thirdpart.achartengine.renderers;

import java.io.Serializable;

public class SimpleSeriesRenderer implements Serializable {
    private float mChartValuesTextSize = 10.0f;
    private int mColor = -16776961;
    private boolean mDisplayChartValues;
    private boolean mGradientEnabled = false;
    private int mGradientStartColor;
    private double mGradientStartValue;
    private int mGradientStopColor;
    private double mGradientStopValue;
    private BasicStroke mStroke;

    public int getColor() {
        return this.mColor;
    }

    public void setColor(int i) {
        this.mColor = i;
    }

    public boolean isDisplayChartValues() {
        return this.mDisplayChartValues;
    }

    public void setDisplayChartValues(boolean z) {
        this.mDisplayChartValues = z;
    }

    public float getChartValuesTextSize() {
        return this.mChartValuesTextSize;
    }

    public void setChartValuesTextSize(float f) {
        this.mChartValuesTextSize = f;
    }

    public BasicStroke getStroke() {
        return this.mStroke;
    }

    public void setStroke(BasicStroke basicStroke) {
        this.mStroke = basicStroke;
    }

    public boolean isGradientEnabled() {
        return this.mGradientEnabled;
    }

    public void setGradientEnabled(boolean z) {
        this.mGradientEnabled = z;
    }

    public double getGradientStartValue() {
        return this.mGradientStartValue;
    }

    public int getGradientStartColor() {
        return this.mGradientStartColor;
    }

    public void setGradientStart(double d, int i) {
        this.mGradientStartValue = d;
        this.mGradientStartColor = i;
    }

    public double getGradientStopValue() {
        return this.mGradientStopValue;
    }

    public int getGradientStopColor() {
        return this.mGradientStopColor;
    }

    public void setGradientStop(double d, int i) {
        this.mGradientStopValue = d;
        this.mGradientStopColor = i;
    }
}
