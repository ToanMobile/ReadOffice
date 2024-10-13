package com.app.office.wp.view;

import android.graphics.Paint;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.font.FontTypefaceManage;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.CharAttr;
import com.app.office.simpletext.view.IView;

public class LeafView extends AbstractView {
    private static StringBuffer title = new StringBuffer();
    protected CharAttr charAttr;
    protected int numPages = -1;
    protected Paint paint;

    public void free() {
    }

    public short getType() {
        return 7;
    }

    public LeafView() {
    }

    public LeafView(IElement iElement, IElement iElement2) {
        this.elem = iElement2;
        initProperty(iElement2, iElement);
    }

    public void initProperty(IElement iElement, IElement iElement2) {
        this.elem = iElement;
        Paint paint2 = this.paint;
        if (paint2 == null) {
            this.paint = new Paint();
        } else {
            paint2.reset();
        }
        this.paint.setAntiAlias(true);
        if (this.charAttr == null) {
            this.charAttr = new CharAttr();
        }
        AttrManage.instance().fillCharAttr(this.charAttr, iElement2.getAttribute(), iElement.getAttribute());
        if (this.charAttr.isBold && this.charAttr.isItalic) {
            this.paint.setTextSkewX(-0.2f);
            this.paint.setFakeBoldText(true);
        } else if (this.charAttr.isBold) {
            this.paint.setFakeBoldText(true);
        } else if (this.charAttr.isItalic) {
            this.paint.setTextSkewX(-0.25f);
        }
        this.paint.setTypeface(FontTypefaceManage.instance().getFontTypeface(this.charAttr.fontIndex));
        if (this.charAttr.subSuperScriptType > 0) {
            this.paint.setTextSize(((((float) this.charAttr.fontSize) * (((float) this.charAttr.fontScale) / 100.0f)) * 1.3333334f) / 2.0f);
        } else {
            this.paint.setTextSize(((float) this.charAttr.fontSize) * (((float) this.charAttr.fontScale) / 100.0f) * 1.3333334f);
        }
        this.paint.setColor(this.charAttr.fontColor);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0088, code lost:
        r11 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int doLayout(com.app.office.simpletext.view.DocAttr r4, com.app.office.simpletext.view.PageAttr r5, com.app.office.simpletext.view.ParaAttr r6, int r7, int r8, int r9, int r10, long r11, int r13) {
        /*
            r3 = this;
            r4 = 0
            long r5 = r3.getStartOffset(r4)
            com.app.office.simpletext.model.IElement r7 = r3.elem
            long r7 = r7.getStartOffset()
            com.app.office.simpletext.model.IElement r10 = r3.elem
            java.lang.String r4 = r10.getText(r4)
            int r10 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r10 <= 0) goto L_0x0024
            long r10 = r5 - r7
            int r11 = (int) r10
            com.app.office.simpletext.model.IElement r10 = r3.elem
            long r0 = r10.getEndOffset()
            long r0 = r0 - r7
            int r7 = (int) r0
            java.lang.String r4 = r4.substring(r11, r7)
        L_0x0024:
            int r7 = r4.length()
            float[] r7 = new float[r7]
            android.graphics.Paint r8 = r3.paint
            r8.getTextWidths(r4, r7)
            r8 = 0
            com.app.office.simpletext.view.ViewKit r10 = com.app.office.simpletext.view.ViewKit.instance()
            r11 = 2
            boolean r10 = r10.getBitValue(r13, r11)
            com.app.office.simpletext.view.ViewKit r12 = com.app.office.simpletext.view.ViewKit.instance()
            r0 = 0
            boolean r12 = r12.getBitValue(r13, r0)
            r13 = 0
        L_0x0043:
            int r1 = r4.length()
            if (r13 >= r1) goto L_0x0088
            char r1 = r4.charAt(r13)
            r2 = r7[r13]
            float r8 = r8 + r2
            r2 = 7
            if (r1 == r2) goto L_0x0082
            r2 = 10
            if (r1 == r2) goto L_0x0082
            r2 = 13
            if (r1 != r2) goto L_0x005c
            goto L_0x0082
        L_0x005c:
            if (r10 != 0) goto L_0x0066
            r2 = 12
            if (r1 != r2) goto L_0x0066
            int r13 = r13 + 1
            r11 = 3
            goto L_0x0089
        L_0x0066:
            r2 = 11
            if (r1 != r2) goto L_0x006b
            goto L_0x0085
        L_0x006b:
            float r1 = (float) r9
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x007f
            r4 = r7[r13]
            float r8 = r8 - r4
            if (r12 == 0) goto L_0x007d
            if (r13 != 0) goto L_0x007d
            r4 = r7[r13]
            float r8 = r8 + r4
            int r13 = r13 + 1
            goto L_0x0088
        L_0x007d:
            r11 = 1
            goto L_0x0089
        L_0x007f:
            int r13 = r13 + 1
            goto L_0x0043
        L_0x0082:
            r4 = r7[r13]
            float r8 = r8 - r4
        L_0x0085:
            int r13 = r13 + 1
            goto L_0x0089
        L_0x0088:
            r11 = 0
        L_0x0089:
            long r9 = (long) r13
            long r9 = r9 + r5
            r3.setEndOffset(r9)
            int r4 = (int) r8
            android.graphics.Paint r5 = r3.paint
            float r5 = r5.descent()
            android.graphics.Paint r6 = r3.paint
            float r6 = r6.ascent()
            float r5 = r5 - r6
            double r5 = (double) r5
            double r5 = java.lang.Math.ceil(r5)
            int r5 = (int) r5
            r3.setSize(r4, r5)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.view.LeafView.doLayout(com.app.office.simpletext.view.DocAttr, com.app.office.simpletext.view.PageAttr, com.app.office.simpletext.view.ParaAttr, int, int, int, int, long, int):int");
    }

    public float getTextWidth() {
        String substring = this.elem.getText((IDocument) null).substring((int) (this.start - this.elem.getStartOffset()), (int) (this.end - this.elem.getStartOffset()));
        float[] fArr = new float[substring.length()];
        this.paint.getTextWidths(substring, fArr);
        float f = 0.0f;
        for (int i = 0; i < substring.length(); i++) {
            f += fArr[i];
        }
        return f;
    }

    private String getFieldTextReplacedByPage(String str, int i) {
        if (str == null) {
            return str;
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = title;
        stringBuffer.delete(0, stringBuffer.length());
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (Character.isDigit(charArray[i2])) {
                title.append(charArray[i2]);
            }
        }
        return title.length() > 0 ? str.replace(title.toString(), String.valueOf(i)) : str;
    }

    public int getPageNumber() {
        try {
            IView parentView = getParentView().getParentView().getParentView();
            if (parentView instanceof CellView) {
                parentView = parentView.getParentView().getParentView().getParentView();
            }
            if (parentView instanceof PageView) {
                return ((PageView) parentView).getPageNumber();
            }
            return parentView instanceof TitleView ? -1 : 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x0205  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x021b  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x017a  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0187  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void draw(android.graphics.Canvas r25, int r26, int r27, float r28) {
        /*
            r24 = this;
            r1 = r24
            r0 = r28
            monitor-enter(r24)
            int r2 = r1.x     // Catch:{ all -> 0x0255 }
            float r2 = (float) r2     // Catch:{ all -> 0x0255 }
            float r2 = r2 * r0
            r3 = r26
            float r3 = (float) r3     // Catch:{ all -> 0x0255 }
            float r2 = r2 + r3
            int r4 = r1.y     // Catch:{ all -> 0x0255 }
            float r4 = (float) r4     // Catch:{ all -> 0x0255 }
            float r4 = r4 * r0
            r5 = r27
            float r6 = (float) r5     // Catch:{ all -> 0x0255 }
            float r10 = r4 + r6
            android.graphics.Paint r4 = r1.paint     // Catch:{ all -> 0x0255 }
            int r11 = r4.getColor()     // Catch:{ all -> 0x0255 }
            com.app.office.simpletext.view.CharAttr r4 = r1.charAttr     // Catch:{ all -> 0x0255 }
            int r4 = r4.highlightedColor     // Catch:{ all -> 0x0255 }
            r5 = -1
            if (r4 == r5) goto L_0x0053
            com.app.office.simpletext.view.IView r4 = r24.getParentView()     // Catch:{ all -> 0x0255 }
            if (r4 == 0) goto L_0x0053
            android.graphics.Paint r5 = r1.paint     // Catch:{ all -> 0x0255 }
            com.app.office.simpletext.view.CharAttr r7 = r1.charAttr     // Catch:{ all -> 0x0255 }
            int r7 = r7.highlightedColor     // Catch:{ all -> 0x0255 }
            r5.setColor(r7)     // Catch:{ all -> 0x0255 }
            int r5 = r24.getWidth()     // Catch:{ all -> 0x0255 }
            float r5 = (float) r5     // Catch:{ all -> 0x0255 }
            float r5 = r5 * r0
            float r7 = r2 + r5
            int r4 = r4.getHeight()     // Catch:{ all -> 0x0255 }
            float r4 = (float) r4     // Catch:{ all -> 0x0255 }
            float r4 = r4 * r0
            float r8 = r6 + r4
            android.graphics.Paint r9 = r1.paint     // Catch:{ all -> 0x0255 }
            r4 = r25
            r5 = r2
            r4.drawRect(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0255 }
            android.graphics.Paint r4 = r1.paint     // Catch:{ all -> 0x0255 }
            r4.setColor(r11)     // Catch:{ all -> 0x0255 }
        L_0x0053:
            android.graphics.Paint r4 = r1.paint     // Catch:{ all -> 0x0255 }
            float r11 = r4.getTextSize()     // Catch:{ all -> 0x0255 }
            float r4 = r11 * r0
            android.graphics.Paint r5 = r1.paint     // Catch:{ all -> 0x0255 }
            r5.setTextSize(r4)     // Catch:{ all -> 0x0255 }
            com.app.office.simpletext.view.CharAttr r4 = r1.charAttr     // Catch:{ all -> 0x0255 }
            short r4 = r4.subSuperScriptType     // Catch:{ all -> 0x0255 }
            r5 = 1
            if (r4 != r5) goto L_0x007c
            android.graphics.Paint r4 = r1.paint     // Catch:{ all -> 0x0255 }
            float r4 = r4.descent()     // Catch:{ all -> 0x0255 }
            android.graphics.Paint r6 = r1.paint     // Catch:{ all -> 0x0255 }
            float r6 = r6.ascent()     // Catch:{ all -> 0x0255 }
            float r4 = r4 - r6
            double r6 = (double) r4     // Catch:{ all -> 0x0255 }
            double r6 = java.lang.Math.ceil(r6)     // Catch:{ all -> 0x0255 }
            int r4 = (int) r6     // Catch:{ all -> 0x0255 }
            float r4 = (float) r4     // Catch:{ all -> 0x0255 }
            float r10 = r10 - r4
        L_0x007c:
            com.app.office.simpletext.model.IElement r4 = r1.elem     // Catch:{ all -> 0x0255 }
            r6 = 0
            java.lang.String r4 = r4.getText(r6)     // Catch:{ all -> 0x0255 }
            long r7 = r1.start     // Catch:{ all -> 0x0255 }
            com.app.office.simpletext.model.IElement r9 = r1.elem     // Catch:{ all -> 0x0255 }
            long r12 = r9.getStartOffset()     // Catch:{ all -> 0x0255 }
            long r7 = r7 - r12
            int r8 = (int) r7     // Catch:{ all -> 0x0255 }
            long r12 = r1.end     // Catch:{ all -> 0x0255 }
            com.app.office.simpletext.model.IElement r7 = r1.elem     // Catch:{ all -> 0x0255 }
            long r14 = r7.getStartOffset()     // Catch:{ all -> 0x0255 }
            long r12 = r12 - r14
            int r7 = (int) r12     // Catch:{ all -> 0x0255 }
            com.app.office.simpletext.view.CharAttr r9 = r1.charAttr     // Catch:{ all -> 0x0255 }
            byte r9 = r9.pageNumberType     // Catch:{ all -> 0x0255 }
            r12 = 2
            if (r9 != r5) goto L_0x00f3
            com.app.office.simpletext.view.IView r9 = r24.getParentView()     // Catch:{ Exception -> 0x0108 }
            com.app.office.simpletext.view.IView r9 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            com.app.office.simpletext.view.IView r9 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            if (r9 == 0) goto L_0x0108
        L_0x00ac:
            if (r9 == 0) goto L_0x00e1
            boolean r14 = r9 instanceof com.app.office.wp.view.TitleView     // Catch:{ Exception -> 0x0108 }
            if (r14 == 0) goto L_0x00bf
            com.app.office.simpletext.view.IView r14 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            if (r14 == 0) goto L_0x00bf
            com.app.office.simpletext.view.IView r6 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            com.app.office.wp.view.PageView r6 = (com.app.office.wp.view.PageView) r6     // Catch:{ Exception -> 0x0108 }
            goto L_0x00e1
        L_0x00bf:
            boolean r14 = r9 instanceof com.app.office.wp.view.PageView     // Catch:{ Exception -> 0x0108 }
            if (r14 == 0) goto L_0x00c7
            r6 = r9
            com.app.office.wp.view.PageView r6 = (com.app.office.wp.view.PageView) r6     // Catch:{ Exception -> 0x0108 }
            goto L_0x00e1
        L_0x00c7:
            boolean r14 = r9 instanceof com.app.office.wp.view.WPSTRoot     // Catch:{ Exception -> 0x0108 }
            if (r14 == 0) goto L_0x00dc
            com.app.office.simpletext.view.IView r9 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            com.app.office.simpletext.view.IView r9 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            com.app.office.simpletext.view.IView r9 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            com.app.office.simpletext.view.IView r9 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            goto L_0x00ac
        L_0x00dc:
            com.app.office.simpletext.view.IView r9 = r9.getParentView()     // Catch:{ Exception -> 0x0108 }
            goto L_0x00ac
        L_0x00e1:
            if (r6 == 0) goto L_0x0108
            int r6 = r6.getPageNumber()     // Catch:{ Exception -> 0x00f1 }
            java.lang.String r4 = r1.getFieldTextReplacedByPage(r4, r6)     // Catch:{ Exception -> 0x00f1 }
            int r6 = r4.length()     // Catch:{ Exception -> 0x0105 }
            r7 = r6
            goto L_0x0105
        L_0x00f1:
            r6 = 1
            goto L_0x0109
        L_0x00f3:
            com.app.office.simpletext.view.CharAttr r6 = r1.charAttr     // Catch:{ all -> 0x0255 }
            byte r6 = r6.pageNumberType     // Catch:{ all -> 0x0255 }
            if (r6 != r12) goto L_0x0108
            int r6 = r1.numPages     // Catch:{ all -> 0x0255 }
            if (r6 <= 0) goto L_0x0108
            java.lang.String r4 = r1.getFieldTextReplacedByPage(r4, r6)     // Catch:{ all -> 0x0255 }
            int r7 = r4.length()     // Catch:{ all -> 0x0255 }
        L_0x0105:
            r6 = 1
            r8 = 0
            goto L_0x0109
        L_0x0108:
            r6 = 0
        L_0x0109:
            int r9 = r4.length()     // Catch:{ all -> 0x0255 }
            float[] r9 = new float[r9]     // Catch:{ all -> 0x0255 }
            android.graphics.Paint r14 = r1.paint     // Catch:{ all -> 0x0255 }
            r14.getTextWidths(r4, r9)     // Catch:{ all -> 0x0255 }
            r15 = 10
            r14 = 13
            r5 = 7
            r21 = 0
            if (r6 != 0) goto L_0x017a
            double r12 = (double) r0     // Catch:{ all -> 0x0255 }
            r16 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r18 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r18 == 0) goto L_0x017a
            r12 = r8
            r13 = 0
        L_0x0126:
            if (r12 >= r7) goto L_0x012f
            r16 = r9[r12]     // Catch:{ all -> 0x0255 }
            float r13 = r13 + r16
            int r12 = r12 + 1
            goto L_0x0126
        L_0x012f:
            int r12 = r7 + -1
            char r6 = r4.charAt(r12)     // Catch:{ all -> 0x0255 }
            if (r6 == r5) goto L_0x013b
            if (r6 == r15) goto L_0x013b
            if (r6 != r14) goto L_0x013e
        L_0x013b:
            r6 = r9[r12]     // Catch:{ all -> 0x0255 }
            float r13 = r13 - r6
        L_0x013e:
            com.app.office.simpletext.view.IView r6 = r24.getNextView()     // Catch:{ all -> 0x0255 }
            if (r6 == 0) goto L_0x0168
            short r12 = r6.getType()     // Catch:{ all -> 0x0255 }
            if (r12 == r5) goto L_0x0158
            short r12 = r6.getType()     // Catch:{ all -> 0x0255 }
            if (r12 != r14) goto L_0x0168
            com.app.office.wp.view.ShapeView r6 = (com.app.office.wp.view.ShapeView) r6     // Catch:{ all -> 0x0255 }
            boolean r6 = r6.isInline()     // Catch:{ all -> 0x0255 }
            if (r6 == 0) goto L_0x0168
        L_0x0158:
            com.app.office.simpletext.view.IView r6 = r24.getNextView()     // Catch:{ all -> 0x0255 }
            int r6 = r6.getX()     // Catch:{ all -> 0x0255 }
            float r6 = (float) r6     // Catch:{ all -> 0x0255 }
            float r6 = r6 * r0
            float r6 = r6 + r3
            float r6 = r6 - r2
            float r13 = r13 - r6
            r3 = 0
            goto L_0x0171
        L_0x0168:
            r3 = 0
            int r6 = r1.getLayoutSpan(r3)     // Catch:{ all -> 0x0255 }
            float r6 = (float) r6     // Catch:{ all -> 0x0255 }
            float r6 = r6 * r0
            float r13 = r13 - r6
        L_0x0171:
            int r6 = (r13 > r21 ? 1 : (r13 == r21 ? 0 : -1))
            if (r6 == 0) goto L_0x017b
            int r6 = r7 - r8
            float r6 = (float) r6     // Catch:{ all -> 0x0255 }
            float r13 = r13 / r6
            goto L_0x017c
        L_0x017a:
            r3 = 0
        L_0x017b:
            r13 = 0
        L_0x017c:
            android.graphics.Paint r6 = r1.paint     // Catch:{ all -> 0x0255 }
            float r6 = r6.ascent()     // Catch:{ all -> 0x0255 }
            float r6 = r10 - r6
            r12 = r2
        L_0x0185:
            if (r8 >= r7) goto L_0x01e6
            char r3 = r4.charAt(r8)     // Catch:{ all -> 0x0255 }
            if (r3 == r15) goto L_0x01d5
            if (r3 == r14) goto L_0x01d5
            if (r3 == r5) goto L_0x01d5
            r5 = 11
            if (r3 == r5) goto L_0x01d5
            r5 = 12
            if (r3 == r5) goto L_0x01d5
            r14 = 9
            if (r3 == r14) goto L_0x01d5
            r14 = 32
            if (r3 == r14) goto L_0x01d5
            r14 = 2
            if (r3 == r14) goto L_0x01d5
            if (r3 != r5) goto L_0x01a7
            goto L_0x01d5
        L_0x01a7:
            int r3 = r8 + 1
            r14 = r3
            r5 = 0
        L_0x01ab:
            if (r14 >= r7) goto L_0x01b9
            r17 = r9[r14]     // Catch:{ all -> 0x0255 }
            int r17 = (r17 > r21 ? 1 : (r17 == r21 ? 0 : -1))
            if (r17 == 0) goto L_0x01b4
            goto L_0x01b9
        L_0x01b4:
            int r5 = r5 + 1
            int r14 = r14 + 1
            goto L_0x01ab
        L_0x01b9:
            int r17 = r3 + r5
            android.graphics.Paint r3 = r1.paint     // Catch:{ all -> 0x0255 }
            r22 = 13
            r14 = r25
            r23 = 10
            r15 = r4
            r16 = r8
            r18 = r12
            r19 = r6
            r20 = r3
            r14.drawText(r15, r16, r17, r18, r19, r20)     // Catch:{ all -> 0x0255 }
            r3 = r9[r8]     // Catch:{ all -> 0x0255 }
            float r3 = r3 - r13
            float r12 = r12 + r3
            int r8 = r8 + r5
            goto L_0x01dd
        L_0x01d5:
            r22 = 13
            r23 = 10
            r3 = r9[r8]     // Catch:{ all -> 0x0255 }
            float r3 = r3 - r13
            float r12 = r12 + r3
        L_0x01dd:
            r3 = 1
            int r8 = r8 + r3
            r3 = 0
            r5 = 7
            r14 = 13
            r15 = 10
            goto L_0x0185
        L_0x01e6:
            android.graphics.Paint r3 = r1.paint     // Catch:{ all -> 0x0255 }
            float r3 = r3.descent()     // Catch:{ all -> 0x0255 }
            android.graphics.Paint r4 = r1.paint     // Catch:{ all -> 0x0255 }
            float r4 = r4.ascent()     // Catch:{ all -> 0x0255 }
            float r3 = r3 - r4
            double r3 = (double) r3     // Catch:{ all -> 0x0255 }
            double r3 = java.lang.Math.ceil(r3)     // Catch:{ all -> 0x0255 }
            int r3 = (int) r3     // Catch:{ all -> 0x0255 }
            r4 = 2
            int r3 = r3 / r4
            float r3 = (float) r3     // Catch:{ all -> 0x0255 }
            float r3 = r3 + r10
            com.app.office.simpletext.view.CharAttr r4 = r1.charAttr     // Catch:{ all -> 0x0255 }
            boolean r4 = r4.isStrikeThrough     // Catch:{ all -> 0x0255 }
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r4 == 0) goto L_0x021b
            float r6 = r3 - r5
            int r4 = r24.getWidth()     // Catch:{ all -> 0x0255 }
            float r4 = (float) r4     // Catch:{ all -> 0x0255 }
            float r4 = r4 * r0
            float r7 = r2 + r4
            float r8 = r3 + r5
            android.graphics.Paint r9 = r1.paint     // Catch:{ all -> 0x0255 }
            r4 = r25
            r5 = r2
            r4.drawRect(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0255 }
            goto L_0x024e
        L_0x021b:
            com.app.office.simpletext.view.CharAttr r4 = r1.charAttr     // Catch:{ all -> 0x0255 }
            boolean r4 = r4.isDoubleStrikeThrough     // Catch:{ all -> 0x0255 }
            if (r4 == 0) goto L_0x024e
            r4 = 1077936128(0x40400000, float:3.0)
            float r6 = r3 - r4
            int r4 = r24.getWidth()     // Catch:{ all -> 0x0255 }
            float r4 = (float) r4     // Catch:{ all -> 0x0255 }
            float r4 = r4 * r0
            float r7 = r2 + r4
            float r8 = r3 - r5
            android.graphics.Paint r9 = r1.paint     // Catch:{ all -> 0x0255 }
            r4 = r25
            r5 = r2
            r4.drawRect(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0255 }
            int r4 = r24.getWidth()     // Catch:{ all -> 0x0255 }
            float r4 = (float) r4     // Catch:{ all -> 0x0255 }
            float r4 = r4 * r0
            float r7 = r2 + r4
            r0 = 1073741824(0x40000000, float:2.0)
            float r8 = r3 + r0
            android.graphics.Paint r9 = r1.paint     // Catch:{ all -> 0x0255 }
            r4 = r25
            r5 = r2
            r6 = r3
            r4.drawRect(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0255 }
        L_0x024e:
            android.graphics.Paint r0 = r1.paint     // Catch:{ all -> 0x0255 }
            r0.setTextSize(r11)     // Catch:{ all -> 0x0255 }
            monitor-exit(r24)
            return
        L_0x0255:
            r0 = move-exception
            monitor-exit(r24)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.view.LeafView.draw(android.graphics.Canvas, int, int, float):void");
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        rectangle.x = (int) this.paint.measureText(this.elem.getText((IDocument) null).substring((int) (this.start - this.elem.getStartOffset()), (int) (j - this.elem.getStartOffset())));
        rectangle.x += getX();
        rectangle.y += getY();
        rectangle.height = getLayoutSpan((byte) 1);
        return rectangle;
    }

    public long viewToModel(int i, int i2, boolean z) {
        int i3 = i - this.x;
        String substring = this.elem.getText((IDocument) null).substring((int) (this.start - this.elem.getStartOffset()), (int) (this.end - this.elem.getStartOffset()));
        float[] fArr = new float[substring.length()];
        this.paint.getTextWidths(substring, fArr);
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i4 >= substring.length()) {
                break;
            }
            i3 = (int) (((float) i3) - fArr[i4]);
            if (i3 > 0) {
                i5++;
                i4++;
            } else if (((float) i3) + fArr[i4] >= fArr[i4] / 2.0f) {
                i5++;
            }
        }
        return this.start + ((long) i5);
    }

    public int getBaseline() {
        if (!"\n".equals(this.elem.getText((IDocument) null))) {
            return (int) (-this.paint.ascent());
        }
        return 0;
    }

    public int getUnderlinePosition() {
        return (int) (((float) (getY() + getHeight())) - (((float) getHeight()) - this.paint.getTextSize()));
    }

    public CharAttr getCharAttr() {
        return this.charAttr;
    }

    public boolean hasUpdatedFieldText() {
        CharAttr charAttr2 = this.charAttr;
        return charAttr2 != null && charAttr2.pageNumberType == 2;
    }

    public void setNumPages(int i) {
        if (hasUpdatedFieldText()) {
            this.numPages = i;
        }
    }

    public void dispose() {
        super.dispose();
        this.paint = null;
        this.charAttr = null;
    }
}
