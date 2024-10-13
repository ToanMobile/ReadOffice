package com.app.office.thirdpart.achartengine.renderers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialRenderer extends DefaultRenderer {
    private double mAngleMax = 30.0d;
    private double mAngleMin = 330.0d;
    private String mChartTitle = "";
    private float mChartTitleTextSize = 15.0f;
    private double mMajorTickSpacing = Double.MAX_VALUE;
    private double mMaxValue = -1.7976931348623157E308d;
    private double mMinValue = Double.MAX_VALUE;
    private double mMinorTickSpacing = Double.MAX_VALUE;
    private List<Type> visualTypes = new ArrayList();

    public enum Type {
        NEEDLE,
        ARROW
    }

    public String getChartTitle() {
        return this.mChartTitle;
    }

    public void setChartTitle(String str) {
        this.mChartTitle = str;
    }

    public float getChartTitleTextSize() {
        return this.mChartTitleTextSize;
    }

    public void setChartTitleTextSize(float f) {
        this.mChartTitleTextSize = f;
    }

    public double getAngleMin() {
        return this.mAngleMin;
    }

    public void setAngleMin(double d) {
        this.mAngleMin = d;
    }

    public double getAngleMax() {
        return this.mAngleMax;
    }

    public void setAngleMax(double d) {
        this.mAngleMax = d;
    }

    public double getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(double d) {
        this.mMinValue = d;
    }

    public boolean isMinValueSet() {
        return this.mMinValue != Double.MAX_VALUE;
    }

    public double getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(double d) {
        this.mMaxValue = d;
    }

    public boolean isMaxValueSet() {
        return this.mMaxValue != -1.7976931348623157E308d;
    }

    public double getMinorTicksSpacing() {
        return this.mMinorTickSpacing;
    }

    public void setMinorTicksSpacing(double d) {
        this.mMinorTickSpacing = d;
    }

    public double getMajorTicksSpacing() {
        return this.mMajorTickSpacing;
    }

    public void setMajorTicksSpacing(double d) {
        this.mMajorTickSpacing = d;
    }

    public Type getVisualTypeForIndex(int i) {
        if (i < this.visualTypes.size()) {
            return this.visualTypes.get(i);
        }
        return Type.NEEDLE;
    }

    public void setVisualTypes(Type[] typeArr) {
        this.visualTypes.clear();
        this.visualTypes.addAll(Arrays.asList(typeArr));
    }
}
