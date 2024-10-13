package com.app.office.thirdpart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.borders.Line;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;
import com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer;
import com.app.office.thirdpart.achartengine.renderers.XYMultipleSeriesRenderer;
import java.util.List;

public abstract class AbstractChart {
    public static final short CHART_AREA = 0;
    public static final short CHART_BAR = 1;
    public static final short CHART_BUBBLE = 8;
    public static final short CHART_DOUGHNUT = 7;
    public static final short CHART_LINE = 2;
    public static final short CHART_PIE = 3;
    public static final short CHART_RADAR = 9;
    public static final short CHART_SCATTER = 4;
    public static final short CHART_STOCK = 5;
    public static final short CHART_SURFACE = 6;
    public static final short CHART_UNKOWN = 10;
    public static final byte LegendPosition_Bottom = 3;
    public static final byte LegendPosition_Left = 0;
    public static final byte LegendPosition_Right = 2;
    public static final byte LegendPosition_Top = 1;
    private int categoryAxisTextColor = -16777216;
    private Rectangle legendArea = null;
    protected byte legendPos = 2;

    public abstract void draw(Canvas canvas, IControl iControl, int i, int i2, int i3, int i4, Paint paint);

    public abstract void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleSeriesRenderer, float f, float f2, int i, Paint paint);

    public abstract int getLegendShapeWidth(int i);

    public abstract float getZoomRate();

    public abstract void setZoomRate(float f);

    public int getCategoryAxisTextColor() {
        return this.categoryAxisTextColor;
    }

    public void setCategoryAxisTextColor(int i) {
        this.categoryAxisTextColor = i;
    }

    public byte getLegendPosition() {
        return this.legendPos;
    }

    public void setLegendPosition(byte b) {
        this.legendPos = b;
    }

    /* access modifiers changed from: protected */
    public void drawBackground(DefaultRenderer defaultRenderer, Canvas canvas, int i, int i2, int i3, int i4, Paint paint, boolean z, int i5) {
        if (defaultRenderer.isApplyBackgroundColor() || z) {
            if (z) {
                paint.setColor(i5);
            } else {
                paint.setColor(defaultRenderer.getBackgroundColor());
            }
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect((float) i, (float) i2, (float) (i + i3), (float) (i2 + i4), paint);
        }
    }

    /* access modifiers changed from: protected */
    public void drawBackgroundAndFrame(DefaultRenderer defaultRenderer, Canvas canvas, IControl iControl, Rect rect, Paint paint) {
        Rect rect2 = rect;
        Paint paint2 = paint;
        int alpha = paint.getAlpha();
        Path path = new Path();
        path.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CCW);
        BackgroundAndFill backgroundAndFill = defaultRenderer.getBackgroundAndFill();
        if (backgroundAndFill != null) {
            paint2.setStyle(Paint.Style.FILL);
            BackgroundDrawer.drawPathBackground(canvas, iControl, 1, backgroundAndFill, rect, (IAnimation) null, 1.0f, path, paint);
            paint2.setAlpha(alpha);
        }
        Line chartFrame = defaultRenderer.getChartFrame();
        if (chartFrame != null) {
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeWidth(2.0f);
            if (chartFrame.isDash()) {
                paint2.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 10.0f));
            }
            BackgroundDrawer.drawPathBackground(canvas, (IControl) null, 1, chartFrame.getBackgroundAndFill(), rect, (IAnimation) null, 1.0f, path, paint);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setAlpha(alpha);
        }
        paint.reset();
        paint2.setAntiAlias(true);
    }

    public Rectangle getMaxTitleAreaSize(int i, int i2) {
        return new Rectangle((int) (((float) i) * 0.8f), i2 / 2);
    }

    public Rectangle getTitleTextAreaSize(DefaultRenderer defaultRenderer, int i, int i2, Paint paint) {
        if (!defaultRenderer.isShowChartTitle()) {
            return null;
        }
        return getTextSize(defaultRenderer.getChartTitle(), defaultRenderer.getChartTitleTextSize() * defaultRenderer.getZoomRate(), ((float) i) * 0.8f, ((float) i2) * 0.5f, paint);
    }

    public Rectangle getTextSize(String str, float f, float f2, float f3, Paint paint) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        float f4 = 0.0f;
        paint.setTextSize(f);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float ceil = (float) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent));
        float measureText = paint.measureText(str);
        if (measureText < f2) {
            return new Rectangle((int) Math.ceil((double) measureText), (int) Math.ceil((double) ceil));
        }
        float[] fArr = new float[0];
        while (true) {
            if (str.length() <= 0) {
                break;
            }
            float f5 = f4 + ceil;
            if (f5 > f3) {
                break;
            }
            int i = 1;
            int breakText = paint.breakText(str, true, f2, fArr);
            if (breakText != 0) {
                i = breakText;
            }
            str.substring(0, i);
            str = str.substring(i, str.length());
            if (str.length() > 0 && f4 + (2.0f * ceil) > f3) {
                f4 = f5;
                break;
            }
            f4 = f5;
        }
        return new Rectangle((int) Math.ceil((double) f2), (int) Math.ceil((double) f4));
    }

    /* access modifiers changed from: protected */
    public void drawTitle(Canvas canvas, String str, float f, float f2, float f3, float f4, float f5, Paint paint, float f6) {
        float[] fArr;
        int i;
        Canvas canvas2 = canvas;
        String str2 = str;
        Paint paint2 = paint;
        float f7 = f6;
        float f8 = f2 * f;
        float f9 = f3 * f;
        float f10 = f4 * f;
        float f11 = f5 * f;
        float f12 = 0.0f;
        int i2 = (f7 > 0.0f ? 1 : (f7 == 0.0f ? 0 : -1));
        if (i2 != 0) {
            canvas2.rotate(f7, f8, f9);
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float ceil = (float) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent));
        if (paint2.measureText(str2) >= f10) {
            float[] fArr2 = new float[0];
            float f13 = f9;
            while (true) {
                if (str2.length() <= 0) {
                    break;
                }
                float f14 = f12 + ceil;
                if (f14 > f11) {
                    break;
                }
                int i3 = 1;
                int breakText = paint2.breakText(str2, true, f10, fArr2);
                float f15 = f10;
                if (breakText == 0) {
                    fArr = fArr2;
                    i = 0;
                } else {
                    i3 = breakText;
                    i = 0;
                    fArr = fArr2;
                }
                String substring = str2.substring(i, i3);
                str2 = str2.substring(i3, str2.length());
                if (str2.length() > 0 && f12 + (2.0f * ceil) > f11) {
                    canvas2.drawText(substring.substring(0, substring.length() - 1) + "...", f8, f13 + fontMetrics.descent, paint2);
                    break;
                }
                canvas2.drawText(substring, f8, fontMetrics.descent + f13, paint2);
                f13 += ceil;
                f10 = f15;
                f12 = f14;
                fArr2 = fArr;
            }
        } else {
            canvas2.drawText(str2, f8, f9, paint2);
        }
        if (i2 != 0) {
            canvas2.rotate(-f7, f8, f9);
        }
    }

    public int getMaxLegendWidth(float f) {
        byte b = this.legendPos;
        if (b == 0 || b == 2) {
            return Math.round(f * 0.35f);
        }
        return Math.round(f * 0.9f);
    }

    public int getMaxLegendHeight(float f) {
        byte b = this.legendPos;
        if (b == 0 || b == 2) {
            return Math.round(f * 0.9f);
        }
        return Math.round(f * 0.35f);
    }

    public Rectangle getLegendAutoSize(DefaultRenderer defaultRenderer, String[] strArr, int i, int i2, Paint paint) {
        int i3;
        if (!defaultRenderer.isShowLegend()) {
            return null;
        }
        paint.setTextSize(defaultRenderer.getLegendTextSize() * defaultRenderer.getZoomRate());
        int min = Math.min(strArr.length, defaultRenderer.getSeriesRendererCount());
        float f = -1.0f;
        float f2 = -1.0f;
        for (int i4 = 0; i4 < min; i4++) {
            String replace = strArr[i4].replace("\n", " ");
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            f2 = Math.max((float) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)), f2);
            f = Math.max(paint.measureText(replace), f);
        }
        float legendShapeWidth = ((float) getLegendShapeWidth(0)) * defaultRenderer.getZoomRate() * 2.0f;
        int maxLegendHeight = getMaxLegendHeight((float) i2);
        int maxLegendWidth = getMaxLegendWidth((float) i);
        float f3 = (float) maxLegendWidth;
        float f4 = f3 - legendShapeWidth;
        int ceil = (int) Math.ceil((double) (legendShapeWidth + f));
        int ceil2 = (int) Math.ceil((double) f2);
        if (f > f4) {
            this.legendArea = new Rectangle(maxLegendWidth, Math.min(ceil2 * ((int) Math.ceil((double) (f / f4))) * min, maxLegendHeight));
        } else {
            byte b = this.legendPos;
            if (b != 0) {
                int i5 = 2;
                if (b != 1) {
                    if (b != 2) {
                        if (b != 3) {
                            return null;
                        }
                    }
                }
                if (min > ((int) (f3 / ((float) ceil)))) {
                    float f5 = (float) min;
                    double ceil3 = Math.ceil((double) (f5 / ((float) 2)));
                    while (true) {
                        i3 = (int) ceil3;
                        if (i3 * ceil <= maxLegendWidth) {
                            break;
                        }
                        i5++;
                        ceil3 = Math.ceil((double) (f5 / ((float) i5)));
                    }
                    int i6 = min - ((min / i3) * i3);
                    while (true) {
                        int i7 = i3 - 1;
                        if (i6 >= i7 || ((int) Math.ceil((double) (f5 / ((float) i7)))) != i5) {
                            this.legendArea = new Rectangle(ceil * i3, Math.min(ceil2 * i5, maxLegendHeight));
                        } else {
                            i6 = i5 - 1;
                            i3 = i7;
                        }
                    }
                    this.legendArea = new Rectangle(ceil * i3, Math.min(ceil2 * i5, maxLegendHeight));
                } else {
                    this.legendArea = new Rectangle(ceil * min, ceil2);
                }
            }
            this.legendArea = new Rectangle(ceil, Math.min(ceil2 * min, maxLegendHeight));
        }
        return this.legendArea;
    }

    public Rectangle getSingleAutoLegendSize(DefaultRenderer defaultRenderer, String[] strArr, Paint paint, int i) {
        paint.setTextSize(defaultRenderer.getLegendTextSize() * defaultRenderer.getZoomRate());
        int min = Math.min(strArr.length, defaultRenderer.getSeriesRendererCount());
        float f = -1.0f;
        float f2 = -1.0f;
        for (int i2 = 0; i2 < min; i2++) {
            String replace = strArr[i2].replace("\n", " ");
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            f2 = Math.max((float) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)), f2);
            f = Math.max(paint.measureText(replace), f);
        }
        float legendShapeWidth = ((float) i) - ((((float) getLegendShapeWidth(0)) * defaultRenderer.getZoomRate()) * 2.0f);
        if (f <= legendShapeWidth) {
            return new Rectangle((int) Math.ceil((double) (f + (((float) getLegendShapeWidth(0)) * defaultRenderer.getZoomRate() * 2.0f))), (int) Math.ceil((double) f2));
        }
        return new Rectangle(i, ((int) Math.ceil((double) f2)) * ((int) Math.ceil((double) (f / legendShapeWidth))));
    }

    private float getLegendTextOffset(DefaultRenderer defaultRenderer) {
        return ((float) (getLegendShapeWidth(0) * 2)) * defaultRenderer.getZoomRate();
    }

    /* access modifiers changed from: protected */
    public int drawLegend(Canvas canvas, DefaultRenderer defaultRenderer, String[] strArr, int i, int i2, int i3, int i4, Paint paint, boolean z) {
        float f;
        float f2;
        int i5;
        int i6;
        Paint.FontMetrics fontMetrics;
        int i7;
        Canvas canvas2 = canvas;
        DefaultRenderer defaultRenderer2 = defaultRenderer;
        String[] strArr2 = strArr;
        int i8 = i;
        int i9 = i3;
        Paint paint2 = paint;
        if (defaultRenderer.isShowLegend()) {
            Rectangle singleAutoLegendSize = getSingleAutoLegendSize(defaultRenderer2, strArr2, paint2, i9);
            float f3 = (float) i8;
            float f4 = (float) (i8 + i9);
            paint2.setTextAlign(Paint.Align.LEFT);
            paint2.setTextSize(defaultRenderer.getLegendTextSize() * defaultRenderer.getZoomRate());
            Paint.FontMetrics fontMetrics2 = paint.getFontMetrics();
            int min = Math.min(strArr2.length, defaultRenderer.getSeriesRendererCount());
            float f5 = (float) i2;
            float f6 = f3;
            int i10 = 0;
            while (i10 < min) {
                float legendShapeWidth = ((float) getLegendShapeWidth(i10)) * defaultRenderer.getZoomRate();
                String replace = strArr2[i10].replace("\n", " ");
                float measureText = paint2.measureText(replace);
                float legendTextOffset = getLegendTextOffset(defaultRenderer2);
                float f7 = legendTextOffset + measureText;
                byte b = this.legendPos;
                if (b != 0) {
                    if (b != 1) {
                        if (b != 2) {
                            if (b != 3) {
                                i5 = min;
                                fontMetrics = fontMetrics2;
                                f = f3;
                                f2 = f4;
                                i6 = i10;
                                i10 = i6 + 1;
                                f4 = f2;
                                f3 = f;
                                fontMetrics2 = fontMetrics;
                                min = i5;
                            }
                        }
                    }
                    if (f7 > ((float) singleAutoLegendSize.width)) {
                        i5 = min;
                        Paint.FontMetrics fontMetrics3 = fontMetrics2;
                        f = f3;
                        f2 = f4;
                        String str = replace;
                        i6 = i10;
                        float f8 = f5 + ((float) singleAutoLegendSize.height);
                        if (strArr2.length == defaultRenderer.getSeriesRendererCount()) {
                            paint2.setColor(defaultRenderer2.getSeriesRendererAt(i6).getColor());
                        } else {
                            paint2.setColor(-3355444);
                        }
                        Paint.FontMetrics fontMetrics4 = fontMetrics3;
                        boolean z2 = true;
                        drawLegendShape(canvas, defaultRenderer2.getSeriesRendererAt(i6), f, f8, i6, paint);
                        paint2.setColor(this.categoryAxisTextColor);
                        int i11 = i3;
                        fontMetrics = fontMetrics4;
                        float f9 = ((float) i11) - legendTextOffset;
                        int i12 = 0;
                        float[] fArr = new float[0];
                        String str2 = str;
                        float f10 = f8;
                        while (str2.length() > 0) {
                            int breakText = paint2.breakText(str2, z2, f9, fArr);
                            if (breakText == 0) {
                                breakText = 1;
                            }
                            String substring = str2.substring(i12, breakText);
                            str2 = str2.substring(breakText, str2.length());
                            canvas2.drawText(substring, f + (legendShapeWidth * 2.0f), fontMetrics.descent + f10, paint2);
                            f10 = (float) (((double) f10) + Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent)));
                            f9 = f9;
                            fArr = fArr;
                            i12 = 0;
                            z2 = true;
                        }
                        f6 = f;
                        f5 = f10;
                        i9 = i11;
                    } else if (f6 + ((float) singleAutoLegendSize.width) > f4) {
                        f5 += (float) singleAutoLegendSize.height;
                        float zoomRate = f3 * defaultRenderer.getZoomRate();
                        if (strArr2.length == defaultRenderer.getSeriesRendererCount()) {
                            paint2.setColor(defaultRenderer2.getSeriesRendererAt(i10).getColor());
                        } else {
                            paint2.setColor(-3355444);
                        }
                        f2 = f4;
                        int i13 = i10;
                        i5 = min;
                        Paint.FontMetrics fontMetrics5 = fontMetrics2;
                        drawLegendShape(canvas, defaultRenderer2.getSeriesRendererAt(i10), zoomRate, f5, i13, paint);
                        paint2.setColor(this.categoryAxisTextColor);
                        canvas2.drawText(replace, zoomRate + (legendShapeWidth * 2.0f), f5 + fontMetrics5.descent, paint2);
                        f6 = zoomRate + ((float) singleAutoLegendSize.width);
                        fontMetrics = fontMetrics5;
                        i9 = i3;
                        float f11 = f3;
                        i6 = i13;
                        f = f11;
                    } else {
                        int i14 = i10;
                        i5 = min;
                        Paint.FontMetrics fontMetrics6 = fontMetrics2;
                        f2 = f4;
                        String str3 = replace;
                        if (strArr2.length == defaultRenderer.getSeriesRendererCount()) {
                            i7 = i14;
                            paint2.setColor(defaultRenderer2.getSeriesRendererAt(i7).getColor());
                        } else {
                            i7 = i14;
                            paint2.setColor(-3355444);
                        }
                        f = f3;
                        i6 = i7;
                        drawLegendShape(canvas, defaultRenderer2.getSeriesRendererAt(i7), f6, f5, i7, paint);
                        paint2.setColor(this.categoryAxisTextColor);
                        canvas2.drawText(str3, f6 + (legendShapeWidth * 2.0f), f5 + fontMetrics6.descent, paint2);
                        f6 += (float) singleAutoLegendSize.width;
                        fontMetrics = fontMetrics6;
                        i9 = i3;
                    }
                    i10 = i6 + 1;
                    f4 = f2;
                    f3 = f;
                    fontMetrics2 = fontMetrics;
                    min = i5;
                }
                i5 = min;
                Paint.FontMetrics fontMetrics7 = fontMetrics2;
                int i15 = i9;
                f = f3;
                f2 = f4;
                String str4 = replace;
                i6 = i10;
                if (strArr2.length == defaultRenderer.getSeriesRendererCount()) {
                    paint2.setColor(defaultRenderer2.getSeriesRendererAt(i6).getColor());
                } else {
                    paint2.setColor(-3355444);
                }
                Paint.FontMetrics fontMetrics8 = fontMetrics7;
                i9 = i15;
                drawLegendShape(canvas, defaultRenderer2.getSeriesRendererAt(i6), f6, f5, i6, paint);
                paint2.setColor(this.categoryAxisTextColor);
                float f12 = (float) i9;
                if (f7 > f12) {
                    float f13 = f12 - legendTextOffset;
                    int i16 = 0;
                    float[] fArr2 = new float[0];
                    float f14 = f5;
                    while (str4.length() > 0) {
                        int breakText2 = paint2.breakText(str4, true, f13, fArr2);
                        if (breakText2 == 0) {
                            breakText2 = 1;
                        }
                        String substring2 = str4.substring(i16, breakText2);
                        str4 = str4.substring(breakText2, str4.length());
                        Paint.FontMetrics fontMetrics9 = fontMetrics8;
                        canvas2.drawText(substring2, f6 + (legendShapeWidth * 2.0f), fontMetrics9.descent + f14, paint2);
                        f14 = (float) (((double) f14) + Math.ceil((double) (fontMetrics9.descent - fontMetrics9.ascent)));
                        f13 = f13;
                        i16 = 0;
                    }
                    fontMetrics = fontMetrics8;
                    f5 = f14;
                } else {
                    fontMetrics = fontMetrics8;
                    canvas2.drawText(str4, f6 + (legendShapeWidth * 2.0f), f5 + fontMetrics.descent, paint2);
                    f5 += (float) singleAutoLegendSize.height;
                }
                i10 = i6 + 1;
                f4 = f2;
                f3 = f;
                fontMetrics2 = fontMetrics;
                min = i5;
            }
        }
        return Math.round(defaultRenderer.getLegendTextSize() * defaultRenderer.getZoomRate());
    }

    /* access modifiers changed from: protected */
    public boolean getExceed(float f, DefaultRenderer defaultRenderer, int i, int i2) {
        boolean z = true;
        boolean z2 = f > ((float) i);
        if (!isVertical(defaultRenderer)) {
            return z2;
        }
        if (f <= ((float) i2)) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public boolean isVertical(DefaultRenderer defaultRenderer) {
        return (defaultRenderer instanceof XYMultipleSeriesRenderer) && ((XYMultipleSeriesRenderer) defaultRenderer).getOrientation() == XYMultipleSeriesRenderer.Orientation.VERTICAL;
    }

    /* access modifiers changed from: protected */
    public void drawPath(Canvas canvas, float[] fArr, Paint paint, boolean z) {
        Path path = new Path();
        path.moveTo(fArr[0], fArr[1]);
        for (int i = 2; i < fArr.length; i += 2) {
            path.lineTo(fArr[i], fArr[i + 1]);
        }
        if (z) {
            path.lineTo(fArr[0], fArr[1]);
        }
        canvas.drawPath(path, paint);
    }

    private String getFitText(String str, float f, Paint paint) {
        int length = str.length();
        String str2 = str;
        int i = 0;
        while (paint.measureText(str2) > f && i < length) {
            i++;
            str2 = str.substring(0, length - i) + "...";
        }
        if (i == length) {
            return "...";
        }
        return str2;
    }

    /* access modifiers changed from: protected */
    public void drawLabel(Canvas canvas, String str, DefaultRenderer defaultRenderer, List<RectF> list, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4, Paint paint) {
        List<RectF> list2 = list;
        Paint paint2 = paint;
        if (defaultRenderer.isShowLabels()) {
            paint2.setColor(defaultRenderer.getLabelsColor());
            double radians = Math.toRadians((double) (90.0f - (f3 + (f4 / 2.0f))));
            double sin = Math.sin(radians);
            double cos = Math.cos(radians);
            float f5 = (float) i;
            double d = (double) f;
            int round = Math.round(((float) (d * sin)) + f5);
            float f6 = (float) i2;
            int round2 = Math.round(((float) (d * cos)) + f6);
            double d2 = (double) f2;
            int round3 = Math.round(f5 + ((float) (sin * d2)));
            int round4 = Math.round(f6 + ((float) (d2 * cos)));
            float labelsTextSize = defaultRenderer.getLabelsTextSize();
            float f7 = labelsTextSize / 2.0f;
            float max = Math.max(f7, 10.0f);
            paint2.setTextAlign(Paint.Align.LEFT);
            if (round > round3) {
                max = -max;
                paint2.setTextAlign(Paint.Align.RIGHT);
            }
            float f8 = (float) round3;
            float f9 = max + f8;
            float f10 = (float) round4;
            float f11 = ((float) i4) - f9;
            if (round > round3) {
                f11 = f9 - ((float) i3);
            }
            String fitText = getFitText(str, f11, paint2);
            float measureText = paint2.measureText(fitText);
            boolean z = false;
            while (!z) {
                int size = list.size();
                int i5 = 0;
                boolean z2 = false;
                while (i5 < size && !z2) {
                    RectF rectF = list2.get(i5);
                    int i6 = size;
                    if (rectF.intersects(f9, f10, f9 + measureText, f10 + labelsTextSize)) {
                        f10 = Math.max(f10, rectF.bottom);
                        z2 = true;
                    }
                    i5++;
                    size = i6;
                }
                z = !z2;
            }
            float f12 = (float) ((int) (f10 - f7));
            Canvas canvas2 = canvas;
            float f13 = f12;
            Paint paint3 = paint;
            canvas2.drawLine((float) round, (float) round2, f8, f13, paint3);
            canvas2.drawLine(f8, f12, f9, f13, paint3);
            canvas.drawText(fitText, f9, f10, paint2);
            list2.add(new RectF(f9, f10, measureText + f9, labelsTextSize + f10));
        }
    }
}
