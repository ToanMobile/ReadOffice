package com.app.office.thirdpart.achartengine.renderers;

import android.graphics.Paint;
import java.io.Serializable;

public class BasicStroke implements Serializable {
    public static final BasicStroke DASHED = new BasicStroke(Paint.Cap.ROUND, Paint.Join.BEVEL, 10.0f, new float[]{10.0f, 10.0f}, 1.0f);
    public static final BasicStroke DOTTED = new BasicStroke(Paint.Cap.ROUND, Paint.Join.BEVEL, 5.0f, new float[]{2.0f, 10.0f}, 1.0f);
    public static final BasicStroke SOLID = new BasicStroke(Paint.Cap.BUTT, Paint.Join.MITER, 4.0f, (float[]) null, 0.0f);
    private Paint.Cap mCap;
    private float[] mIntervals;
    private Paint.Join mJoin;
    private float mMiter;
    private float mPhase;

    public BasicStroke(Paint.Cap cap, Paint.Join join, float f, float[] fArr, float f2) {
        this.mCap = cap;
        this.mJoin = join;
        this.mMiter = f;
        this.mIntervals = fArr;
    }

    public Paint.Cap getCap() {
        return this.mCap;
    }

    public Paint.Join getJoin() {
        return this.mJoin;
    }

    public float getMiter() {
        return this.mMiter;
    }

    public float[] getIntervals() {
        return this.mIntervals;
    }

    public float getPhase() {
        return this.mPhase;
    }
}
