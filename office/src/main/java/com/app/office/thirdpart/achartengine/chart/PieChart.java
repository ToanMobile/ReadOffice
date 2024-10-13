package com.app.office.thirdpart.achartengine.chart;

import com.app.office.thirdpart.achartengine.model.CategorySeries;
import com.app.office.thirdpart.achartengine.renderers.DefaultRenderer;

public class PieChart extends RoundChart {
    public PieChart(CategorySeries categorySeries, DefaultRenderer defaultRenderer) {
        super(categorySeries, defaultRenderer);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00d7, code lost:
        if (r10.legendPos == 2) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00f5, code lost:
        if (r10.legendPos == 3) goto L_0x00f9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r42, com.app.office.system.IControl r43, int r44, int r45, int r46, int r47, android.graphics.Paint r48) {
        /*
            r41 = this;
            r10 = r41
            r11 = r44
            r12 = r45
            r13 = r46
            r14 = r47
            r15 = r48
            r42.save()
            int r9 = r11 + r13
            int r8 = r12 + r14
            r7 = r42
            r7.clipRect(r11, r12, r9, r8)
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            boolean r0 = r0.isAntialiasing()
            r15.setAntiAlias(r0)
            android.graphics.Paint$Style r0 = android.graphics.Paint.Style.FILL
            r15.setStyle(r0)
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            float r0 = r0.getLabelsTextSize()
            r15.setTextSize(r0)
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r1 = r10.mRenderer
            android.graphics.Rect r4 = new android.graphics.Rect
            r4.<init>(r11, r12, r9, r8)
            r0 = r41
            r2 = r42
            r3 = r43
            r5 = r48
            r0.drawBackgroundAndFrame(r1, r2, r3, r4, r5)
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            int r0 = r0.getLegendHeight()
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r1 = r10.mRenderer
            boolean r1 = r1.isShowLegend()
            if (r1 == 0) goto L_0x0053
            if (r0 != 0) goto L_0x0053
            int r0 = r14 / 5
        L_0x0053:
            r16 = r0
            com.app.office.thirdpart.achartengine.model.CategorySeries r0 = r10.mDataset
            int r6 = r0.getItemCount()
            r0 = 0
            java.lang.String[] r5 = new java.lang.String[r6]
            r4 = 0
            r17 = r0
            r0 = 0
        L_0x0063:
            if (r0 >= r6) goto L_0x0078
            com.app.office.thirdpart.achartengine.model.CategorySeries r1 = r10.mDataset
            double r1 = r1.getValue(r0)
            double r17 = r17 + r1
            com.app.office.thirdpart.achartengine.model.CategorySeries r1 = r10.mDataset
            java.lang.String r1 = r1.getCategory(r0)
            r5[r0] = r1
            int r0 = r0 + 1
            goto L_0x0063
        L_0x0078:
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            com.app.office.java.awt.Rectangle r3 = r10.getTitleTextAreaSize(r0, r13, r14, r15)
            if (r3 == 0) goto L_0x0087
            int r0 = r3.height
            int r0 = r14 - r0
            r19 = r0
            goto L_0x0089
        L_0x0087:
            r19 = r14
        L_0x0089:
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r1 = r10.mRenderer
            r0 = r41
            r2 = r5
            r20 = r8
            r8 = r3
            r3 = r46
            r7 = 0
            r4 = r19
            r19 = r5
            r5 = r48
            com.app.office.java.awt.Rectangle r5 = r0.getLegendAutoSize(r1, r2, r3, r4, r5)
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            double[] r21 = r0.getMargins()
            r4 = 1
            r0 = r21[r4]
            double r2 = (double) r13
            double r0 = r0 * r2
            int r0 = (int) r0
            int r1 = r11 + r0
            r22 = r21[r7]
            r43 = r5
            double r4 = (double) r14
            r25 = r8
            double r7 = r22 * r4
            int r0 = (int) r7
            int r0 = r0 + r12
            r8 = r25
            if (r25 == 0) goto L_0x00bf
            int r7 = r8.height
            int r0 = r0 + r7
        L_0x00bf:
            r7 = r0
            r0 = 3
            r22 = r21[r0]
            r25 = r1
            double r0 = r22 * r2
            int r0 = (int) r0
            int r0 = r9 - r0
            if (r43 == 0) goto L_0x00e3
            byte r1 = r10.legendPos
            if (r1 == 0) goto L_0x00da
            byte r1 = r10.legendPos
            r28 = r2
            r2 = 2
            r3 = r43
            if (r1 != r2) goto L_0x00e8
            goto L_0x00df
        L_0x00da:
            r28 = r2
            r2 = 2
            r3 = r43
        L_0x00df:
            int r1 = r3.width
            int r0 = r0 - r1
            goto L_0x00e8
        L_0x00e3:
            r28 = r2
            r2 = 2
            r3 = r43
        L_0x00e8:
            r1 = r0
            r22 = r21[r2]
            if (r3 == 0) goto L_0x00fc
            byte r0 = r10.legendPos
            r2 = 1
            if (r0 == r2) goto L_0x00f8
            byte r0 = r10.legendPos
            r2 = 3
            if (r0 != r2) goto L_0x00fd
            goto L_0x00f9
        L_0x00f8:
            r2 = 3
        L_0x00f9:
            int r0 = r3.height
            goto L_0x00fd
        L_0x00fc:
            r2 = 3
        L_0x00fd:
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            float r0 = r0.getLegendTextSize()
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r2 = r10.mRenderer
            float r2 = r2.getZoomRate()
            float r0 = r0 * r2
            r15.setTextSize(r0)
            android.graphics.Paint$Align r0 = android.graphics.Paint.Align.CENTER
            r15.setTextAlign(r0)
            r2 = 1
            r15.setFakeBoldText(r2)
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            boolean r0 = r0.isShowChartTitle()
            if (r0 == 0) goto L_0x0197
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            float r0 = r0.getChartTitleTextSize()
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r2 = r10.mRenderer
            float r2 = r2.getZoomRate()
            float r0 = r0 * r2
            r15.setTextSize(r0)
            com.app.office.java.awt.Rectangle r0 = r10.getMaxTitleAreaSize(r13, r14)
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r2 = r10.mRenderer
            java.lang.String r2 = r2.getChartTitle()
            r23 = 1065353216(0x3f800000, float:1.0)
            int r30 = r13 / 2
            r43 = r1
            int r1 = r11 + r30
            float r1 = (float) r1
            r30 = r1
            float r1 = (float) r12
            r31 = r3
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r3 = r10.mRenderer
            float r3 = r3.getChartTitleTextSize()
            r32 = r4
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r4 = r10.mRenderer
            float r4 = r4.getZoomRate()
            float r3 = r3 * r4
            r4 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 * r4
            float r5 = r1 + r3
            int r1 = r0.width
            float r4 = (float) r1
            int r0 = r0.height
            float r3 = (float) r0
            r34 = 0
            r1 = 3
            r0 = r41
            r36 = r43
            r35 = r25
            r22 = r30
            r1 = r42
            r24 = r28
            r27 = 1
            r29 = r3
            r28 = r31
            r3 = r23
            r23 = r4
            r30 = r32
            r4 = r22
            r37 = r28
            r38 = r6
            r6 = r23
            r39 = r7
            r7 = r29
            r40 = r8
            r8 = r48
            r22 = r9
            r9 = r34
            r0.drawTitle(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x01a9
        L_0x0197:
            r36 = r1
            r37 = r3
            r30 = r4
            r38 = r6
            r39 = r7
            r40 = r8
            r22 = r9
            r35 = r25
            r24 = r28
        L_0x01a9:
            r0 = 0
            r15.setFakeBoldText(r0)
            int r8 = r20 - r16
            r2 = r35
            r3 = r36
            int r4 = r3 - r2
            int r4 = java.lang.Math.abs(r4)
            r5 = r39
            int r6 = r8 - r5
            int r6 = java.lang.Math.abs(r6)
            int r4 = java.lang.Math.min(r4, r6)
            double r6 = (double) r4
            r26 = 4599976659396224614(0x3fd6666666666666, double:0.35)
            double r6 = r6 * r26
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r4 = r10.mRenderer
            float r4 = r4.getScale()
            double r0 = (double) r4
            double r6 = r6 * r0
            int r0 = (int) r6
            double r1 = (double) r2
            r6 = 1
            r26 = r21[r6]
            double r26 = r26 * r24
            double r1 = r1 + r26
            double r3 = (double) r3
            double r1 = r1 + r3
            r7 = 3
            r3 = r21[r7]
            double r3 = r3 * r24
            double r1 = r1 - r3
            int r1 = (int) r1
            r9 = 2
            int r1 = r1 / r9
            double r2 = (double) r8
            r23 = r21[r9]
            double r23 = r23 * r30
            double r2 = r2 - r23
            double r4 = (double) r5
            double r2 = r2 + r4
            r4 = 0
            r23 = r21[r4]
            double r23 = r23 * r30
            double r2 = r2 + r23
            int r2 = (int) r2
            int r2 = r2 / r9
            android.graphics.RectF r8 = new android.graphics.RectF
            int r3 = r1 - r0
            float r3 = (float) r3
            int r5 = r2 - r0
            float r5 = (float) r5
            int r1 = r1 + r0
            float r1 = (float) r1
            int r2 = r2 + r0
            float r0 = (float) r2
            r8.<init>(r3, r5, r1, r0)
            java.util.ArrayList r16 = new java.util.ArrayList
            r16.<init>()
            r4 = r38
            r5 = 0
            r21 = 0
        L_0x0215:
            if (r5 >= r4) goto L_0x0253
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            com.app.office.thirdpart.achartengine.renderers.SimpleSeriesRenderer r0 = r0.getSeriesRendererAt(r5)
            int r0 = r0.getColor()
            r15.setColor(r0)
            com.app.office.thirdpart.achartengine.model.CategorySeries r0 = r10.mDataset
            double r0 = r0.getValue(r5)
            float r0 = (float) r0
            double r0 = (double) r0
            double r0 = r0 / r17
            r2 = 4645040803167600640(0x4076800000000000, double:360.0)
            double r0 = r0 * r2
            float r3 = (float) r0
            r0 = 1119092736(0x42b40000, float:90.0)
            float r2 = r21 - r0
            r23 = 1
            r0 = r42
            r1 = r8
            r24 = r3
            r25 = r4
            r4 = r23
            r23 = r5
            r5 = r48
            r0.drawArc(r1, r2, r3, r4, r5)
            float r21 = r21 + r24
            int r5 = r23 + 1
            r4 = r25
            goto L_0x0215
        L_0x0253:
            r16.clear()
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r0 = r10.mRenderer
            boolean r0 = r0.isShowLegend()
            if (r0 == 0) goto L_0x02b1
            r0 = r37
            int r8 = r0.width
            int r0 = r0.height
            int r16 = java.lang.Math.min(r14, r0)
            byte r0 = r41.getLegendPosition()
            if (r0 == 0) goto L_0x0280
            if (r0 == r6) goto L_0x0277
            if (r0 == r9) goto L_0x0280
            if (r0 == r7) goto L_0x0277
            r4 = r11
            r5 = r12
            goto L_0x02a0
        L_0x0277:
            int r0 = r13 - r8
            int r0 = r0 / r9
            int r0 = r0 + r11
            int r1 = r20 - r16
        L_0x027d:
            r4 = r0
            r5 = r1
            goto L_0x02a0
        L_0x0280:
            int r0 = r22 - r8
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r1 = r10.mRenderer
            float r1 = r1.getLegendTextSize()
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r2 = r10.mRenderer
            float r2 = r2.getZoomRate()
            float r1 = r1 * r2
            int r1 = (int) r1
            int r0 = r0 - r1
            r1 = r40
            if (r1 == 0) goto L_0x029b
            int r1 = r1.height
            int r1 = r1 + r14
            int r1 = r1 / r9
            goto L_0x029e
        L_0x029b:
            int r1 = r14 - r16
            int r1 = r1 / r9
        L_0x029e:
            int r1 = r1 + r12
            goto L_0x027d
        L_0x02a0:
            com.app.office.thirdpart.achartengine.renderers.DefaultRenderer r2 = r10.mRenderer
            r9 = 0
            r0 = r41
            r1 = r42
            r3 = r19
            r6 = r8
            r7 = r16
            r8 = r48
            r0.drawLegend(r1, r2, r3, r4, r5, r6, r7, r8, r9)
        L_0x02b1:
            r42.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.thirdpart.achartengine.chart.PieChart.draw(android.graphics.Canvas, com.app.office.system.IControl, int, int, int, int, android.graphics.Paint):void");
    }
}
