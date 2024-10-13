package com.app.office.thirdpart.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategorySeries implements Serializable {
    private List<String> mCategories = new ArrayList();
    private String mTitle;
    private List<Double> mValues = new ArrayList();

    public CategorySeries(String str) {
        this.mTitle = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public synchronized void add(double d) {
        add((this.mCategories.size() + 1) + "", d);
    }

    public synchronized void add(String str, double d) {
        this.mCategories.add(str);
        this.mValues.add(Double.valueOf(d));
    }

    public synchronized void set(int i, String str, double d) {
        this.mCategories.set(i, str);
        this.mValues.set(i, Double.valueOf(d));
    }

    public synchronized void remove(int i) {
        this.mCategories.remove(i);
        this.mValues.remove(i);
    }

    public synchronized void clear() {
        this.mCategories.clear();
        this.mValues.clear();
    }

    public synchronized double getValue(int i) {
        return this.mValues.get(i).doubleValue();
    }

    public synchronized String getCategory(int i) {
        return this.mCategories.get(i);
    }

    public synchronized int getItemCount() {
        return this.mCategories.size();
    }

    public XYSeries toXYSeries() {
        XYSeries xYSeries = new XYSeries(this.mTitle);
        int i = 0;
        for (Double doubleValue : this.mValues) {
            i++;
            xYSeries.add((double) i, doubleValue.doubleValue());
        }
        return xYSeries;
    }
}
