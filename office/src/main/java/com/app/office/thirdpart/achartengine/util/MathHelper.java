package com.app.office.thirdpart.achartengine.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MathHelper {
    private static final NumberFormat FORMAT = NumberFormat.getNumberInstance();
    public static final double NULL_VALUE = Double.MAX_VALUE;

    private MathHelper() {
    }

    public static double[] minmax(List<Double> list) {
        if (list.size() == 0) {
            return new double[2];
        }
        double doubleValue = list.get(0).doubleValue();
        int size = list.size();
        double d = doubleValue;
        for (int i = 1; i < size; i++) {
            double doubleValue2 = list.get(i).doubleValue();
            doubleValue = Math.min(doubleValue, doubleValue2);
            d = Math.max(d, doubleValue2);
        }
        return new double[]{doubleValue, d};
    }

    public static List<Double> getLabels(double d, double d2, int i) {
        FORMAT.setMaximumFractionDigits(5);
        ArrayList arrayList = new ArrayList();
        double[] computeLabels = computeLabels(d, d2, i);
        int i2 = ((int) ((computeLabels[1] - computeLabels[0]) / computeLabels[2])) + 1;
        for (int i3 = 0; i3 < i2; i3++) {
            double d3 = computeLabels[0] + (((double) i3) * computeLabels[2]);
            try {
                NumberFormat numberFormat = FORMAT;
                d3 = numberFormat.parse(numberFormat.format(d3)).doubleValue();
            } catch (ParseException unused) {
            }
            arrayList.add(Double.valueOf(d3));
        }
        return arrayList;
    }

    private static double[] computeLabels(double d, double d2, int i) {
        boolean z;
        double d3;
        double d4;
        int i2 = i;
        if (Math.abs(d - d2) < 1.0000000116860974E-7d) {
            double roundUp = roundUp(d / ((double) i2));
            return new double[]{roundUp, Math.ceil(d2 / roundUp) * roundUp, roundUp};
        }
        if (d > d2) {
            d3 = d;
            d4 = d2;
            z = true;
        } else {
            z = false;
            d4 = d;
            d3 = d2;
        }
        double roundUp2 = roundUp(Math.abs(d4 - d3) / ((double) i2));
        double floor = Math.floor(d4 / roundUp2) * roundUp2;
        double ceil = Math.ceil(d3 / roundUp2) * roundUp2;
        if (z) {
            return new double[]{ceil, floor, roundUp2 * -1.0d};
        }
        return new double[]{floor, ceil, roundUp2};
    }

    private static double roundUp(double d) {
        int floor = (int) Math.floor(Math.log10(d));
        double pow = d * Math.pow(10.0d, (double) (-floor));
        if (pow > 5.0d) {
            pow = 10.0d;
        } else if (pow > 2.0d) {
            pow = 5.0d;
        } else if (pow > 1.0d) {
            pow = 2.0d;
        }
        return pow * Math.pow(10.0d, (double) floor);
    }

    public static float[] getFloats(List<Float> list) {
        int size = list.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = list.get(i).floatValue();
        }
        return fArr;
    }
}
