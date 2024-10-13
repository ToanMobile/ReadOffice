package com.app.office.officereader.beans;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.app.office.R;

public class TitleBar extends LinearLayout {
    private int height;
    private ProgressBar mBusyIndicator;
    private Paint paint;
    private String title;
    private float yPostion;

    public TitleBar(Context context) {
        super(context);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.sys_button_focus_bg_vertical, options);
        this.height = options.outHeight;
        setBackgroundResource(R.drawable.sys_button_focus_bg_vertical);
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        this.paint.setColor(-1);
        this.paint.setTextSize(24.0f);
        Paint.FontMetrics fontMetrics = this.paint.getFontMetrics();
        this.yPostion = (((((float) this.height) - fontMetrics.descent) + fontMetrics.ascent) / 2.0f) - fontMetrics.ascent;
        ProgressBar progressBar = new ProgressBar(getContext());
        this.mBusyIndicator = progressBar;
        progressBar.setIndeterminate(true);
        addView(this.mBusyIndicator);
        this.mBusyIndicator.setVisibility(8);
    }

    public int getTitleHeight() {
        return this.height;
    }

    public void setTitle(String str) {
        this.title = str;
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        String str = this.title;
        if (str != null) {
            canvas.drawText(str, 5.0f, this.yPostion, this.paint);
        }
    }

    public void showProgressBar(boolean z) {
        this.mBusyIndicator.setVisibility(z ? 0 : 8);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mBusyIndicator != null) {
            int min = (Math.min(getWidth(), getHeight()) / 2) | Integer.MIN_VALUE;
            this.mBusyIndicator.measure(min, min);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = i3 - i;
        int i6 = i4 - i2;
        ProgressBar progressBar = this.mBusyIndicator;
        if (progressBar != null) {
            int measuredWidth = progressBar.getMeasuredWidth();
            int measuredHeight = this.mBusyIndicator.getMeasuredHeight();
            this.mBusyIndicator.layout((i5 - measuredWidth) - 5, (i6 - measuredHeight) / 2, i5 - 5, (i6 + measuredHeight) / 2);
        }
    }

    public void dispose() {
        this.paint = null;
        this.title = null;
        ProgressBar progressBar = this.mBusyIndicator;
        if (progressBar != null) {
            removeView(progressBar);
            this.mBusyIndicator = null;
        }
    }
}
