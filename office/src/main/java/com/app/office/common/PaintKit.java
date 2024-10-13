package com.app.office.common;

import android.graphics.Paint;
import android.graphics.Typeface;

public class PaintKit {
    private static PaintKit pk = new PaintKit();
    private Paint paint = null;

    private PaintKit() {
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setTextSize(16.0f);
        this.paint.setTypeface(Typeface.SERIF);
        this.paint.setFlags(1);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public static PaintKit instance() {
        return pk;
    }

    public Paint getPaint() {
        this.paint.reset();
        this.paint.setAntiAlias(true);
        return this.paint;
    }
}
