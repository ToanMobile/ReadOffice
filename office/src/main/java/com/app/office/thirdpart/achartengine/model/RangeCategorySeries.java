package com.app.office.thirdpart.achartengine.model;

import java.util.ArrayList;
import java.util.List;

public class RangeCategorySeries extends CategorySeries {
    private List<Double> mMaxValues = new ArrayList();

    public RangeCategorySeries(String str) {
        super(str);
    }

    public synchronized void add(double d, double d2) {
        super.add(d);
        this.mMaxValues.add(Double.valueOf(d2));
    }

    public synchronized void add(String str, double d, double d2) {
        super.add(str, d);
        this.mMaxValues.add(Double.valueOf(d2));
    }

    public synchronized void remove(int i) {
        super.remove(i);
        this.mMaxValues.remove(i);
    }

    public synchronized void clear() {
        super.clear();
        this.mMaxValues.clear();
    }

    public double getMinimumValue(int i) {
        return getValue(i);
    }

    public double getMaximumValue(int i) {
        return this.mMaxValues.get(i).doubleValue();
    }

    public XYSeries toXYSeries() {
        XYSeries xYSeries = new XYSeries(getTitle());
        int itemCount = getItemCount();
        int i = 0;
        while (i < itemCount) {
            int i2 = i + 1;
            double d = (double) i2;
            xYSeries.add(d, getMinimumValue(i));
            xYSeries.add(d, getMaximumValue(i));
            i = i2;
        }
        return xYSeries;
    }
}
