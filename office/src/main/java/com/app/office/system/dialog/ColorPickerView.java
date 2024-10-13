package com.app.office.system.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;

public class ColorPickerView extends View {
    private static final int CENTER_RADIUS = 32;
    private static int CENTER_X = 140;
    private static int CENTER_Y = 140;
    private static final float PI = 3.1415925f;
    private int alpha = 255;
    private Paint mCenterPaint;
    private final int[] mColors;
    private boolean mHighlightCenter;
    private Paint mPaint;
    private boolean mTrackingCenter;

    private int pinToByte(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 255) {
            return 255;
        }
        return i;
    }

    public ColorPickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int[] iArr = {SupportMenu.CATEGORY_MASK, -65281, -16776961, -16711681, -16711936, InputDeviceCompat.SOURCE_ANY, SupportMenu.CATEGORY_MASK};
        this.mColors = iArr;
        SweepGradient sweepGradient = new SweepGradient(0.0f, 0.0f, iArr, (float[]) null);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setShader(sweepGradient);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(50.0f);
        Paint paint2 = new Paint(1);
        this.mCenterPaint = paint2;
        paint2.setColor(ColorPickerDialog.mInitialColor);
        this.mCenterPaint.setStrokeWidth(5.0f);
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
    }

    public void setAlpha(int i) {
        this.alpha = i;
        this.mCenterPaint.setAlpha(i);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float strokeWidth = ((float) CENTER_X) - (this.mPaint.getStrokeWidth() * 0.5f);
        int i = CENTER_X;
        canvas.translate((float) i, (float) i);
        float f = -strokeWidth;
        canvas.drawOval(new RectF(f, f, strokeWidth, strokeWidth), this.mPaint);
        canvas.drawCircle(0.0f, 0.0f, 32.0f, this.mCenterPaint);
        if (this.mTrackingCenter) {
            int color = this.mCenterPaint.getColor();
            this.mCenterPaint.setStyle(Paint.Style.STROKE);
            if (!this.mHighlightCenter) {
                this.mCenterPaint.setAlpha(128);
            }
            canvas.drawCircle(0.0f, 0.0f, this.mCenterPaint.getStrokeWidth() + 32.0f, this.mCenterPaint);
            this.mCenterPaint.setStyle(Paint.Style.FILL);
            this.mCenterPaint.setColor(color);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2);
    }

    private int floatToByte(float f) {
        return Math.round(f);
    }

    private int ave(int i, int i2, float f) {
        return i + Math.round(f * ((float) (i2 - i)));
    }

    private int interpColor(int[] iArr, float f) {
        if (f <= 0.0f) {
            return iArr[0];
        }
        if (f >= 1.0f) {
            return iArr[iArr.length - 1];
        }
        float length = f * ((float) (iArr.length - 1));
        int i = (int) length;
        float f2 = length - ((float) i);
        int i2 = iArr[i];
        int i3 = iArr[i + 1];
        return Color.argb(this.alpha, ave(Color.red(i2), Color.red(i3), f2), ave(Color.green(i2), Color.green(i3), f2), ave(Color.blue(i2), Color.blue(i3), f2));
    }

    private int rotateColor(int i, float f) {
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix.setRGB2YUV();
        colorMatrix2.setRotate(0, (f * 180.0f) / 3.1415927f);
        colorMatrix.postConcat(colorMatrix2);
        colorMatrix2.setYUV2RGB();
        colorMatrix.postConcat(colorMatrix2);
        float[] array = colorMatrix.getArray();
        float f2 = (float) red;
        float f3 = (float) green;
        float f4 = (float) blue;
        return Color.argb(Color.alpha(i), pinToByte(floatToByte((array[0] * f2) + (array[1] * f3) + (array[2] * f4))), pinToByte(floatToByte((array[5] * f2) + (array[6] * f3) + (array[7] * f4))), pinToByte(floatToByte((array[10] * f2) + (array[11] * f3) + (array[12] * f4))));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002e, code lost:
        if (r10 != 2) goto L_0x0074;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            r9 = this;
            float r0 = r10.getX()
            int r1 = CENTER_X
            float r1 = (float) r1
            float r0 = r0 - r1
            float r1 = r10.getY()
            int r2 = CENTER_Y
            float r2 = (float) r2
            float r1 = r1 - r2
            float r2 = r0 * r0
            float r3 = r1 * r1
            float r2 = r2 + r3
            double r2 = (double) r2
            double r2 = java.lang.Math.sqrt(r2)
            r4 = 0
            r5 = 1
            r6 = 4629700416936869888(0x4040000000000000, double:32.0)
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 > 0) goto L_0x0024
            r2 = 1
            goto L_0x0025
        L_0x0024:
            r2 = 0
        L_0x0025:
            int r10 = r10.getAction()
            if (r10 == 0) goto L_0x003b
            if (r10 == r5) goto L_0x0031
            r3 = 2
            if (r10 == r3) goto L_0x0045
            goto L_0x0074
        L_0x0031:
            boolean r10 = r9.mTrackingCenter
            if (r10 == 0) goto L_0x0074
            r9.mTrackingCenter = r4
            r9.invalidate()
            goto L_0x0074
        L_0x003b:
            r9.mTrackingCenter = r2
            if (r2 == 0) goto L_0x0045
            r9.mHighlightCenter = r5
            r9.invalidate()
            goto L_0x0074
        L_0x0045:
            boolean r10 = r9.mTrackingCenter
            if (r10 == 0) goto L_0x0053
            boolean r10 = r9.mHighlightCenter
            if (r10 == r2) goto L_0x0074
            r9.mHighlightCenter = r2
            r9.invalidate()
            goto L_0x0074
        L_0x0053:
            double r1 = (double) r1
            double r3 = (double) r0
            double r0 = java.lang.Math.atan2(r1, r3)
            float r10 = (float) r0
            r0 = 1086918618(0x40c90fda, float:6.283185)
            float r10 = r10 / r0
            r0 = 0
            int r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x0066
            r0 = 1065353216(0x3f800000, float:1.0)
            float r10 = r10 + r0
        L_0x0066:
            android.graphics.Paint r0 = r9.mCenterPaint
            int[] r1 = r9.mColors
            int r10 = r9.interpColor(r1, r10)
            r0.setColor(r10)
            r9.invalidate()
        L_0x0074:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.system.dialog.ColorPickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public int getColor() {
        return this.mCenterPaint.getColor();
    }
}
