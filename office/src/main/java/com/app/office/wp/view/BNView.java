package com.app.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.app.office.common.bulletnumber.ListLevel;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.CharAttr;

public class BNView extends AbstractView {
    private CharAttr charAttr = new CharAttr();
    private Object content;
    private ListLevel currLevel;
    private Paint paint;

    public void free() {
    }

    public short getType() {
        return 13;
    }

    public BNView() {
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setFlags(1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006c, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int doLayout(com.app.office.simpletext.model.IDocument r3, com.app.office.simpletext.view.DocAttr r4, com.app.office.simpletext.view.PageAttr r5, com.app.office.simpletext.view.ParaAttr r6, com.app.office.wp.view.ParagraphView r7, int r8, int r9, int r10, int r11, int r12) {
        /*
            r2 = this;
            monitor-enter(r2)
            int r5 = r6.listAlignIndent     // Catch:{ all -> 0x01d0 }
            int r5 = r5 + r8
            r2.setLocation(r5, r9)     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.model.IElement r5 = r7.getElement()     // Catch:{ all -> 0x01d0 }
            r8 = 0
            java.lang.String r9 = ""
            int r10 = r6.listID     // Catch:{ all -> 0x01d0 }
            r11 = 0
            r12 = 1
            if (r10 < 0) goto L_0x00fe
            com.app.office.system.IControl r8 = r7.getControl()     // Catch:{ all -> 0x01d0 }
            com.app.office.system.SysKit r8 = r8.getSysKit()     // Catch:{ all -> 0x01d0 }
            com.app.office.common.bulletnumber.ListManage r8 = r8.getListManage()     // Catch:{ all -> 0x01d0 }
            int r9 = r6.listID     // Catch:{ all -> 0x01d0 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x01d0 }
            com.app.office.common.bulletnumber.ListData r8 = r8.getListData(r9)     // Catch:{ all -> 0x01d0 }
            if (r8 != 0) goto L_0x002e
            monitor-exit(r2)
            return r11
        L_0x002e:
            short r9 = r8.getLinkStyleID()     // Catch:{ all -> 0x01d0 }
            if (r9 < 0) goto L_0x006d
            com.app.office.simpletext.model.StyleManage r9 = com.app.office.simpletext.model.StyleManage.instance()     // Catch:{ all -> 0x01d0 }
            short r10 = r8.getLinkStyleID()     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.model.Style r9 = r9.getStyle(r10)     // Catch:{ all -> 0x01d0 }
            if (r9 == 0) goto L_0x006d
            com.app.office.simpletext.model.AttrManage r8 = com.app.office.simpletext.model.AttrManage.instance()     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.model.IAttributeSet r9 = r9.getAttrbuteSet()     // Catch:{ all -> 0x01d0 }
            int r8 = r8.getParaListID(r9)     // Catch:{ all -> 0x01d0 }
            com.app.office.system.IControl r7 = r7.getControl()     // Catch:{ all -> 0x01d0 }
            com.app.office.system.SysKit r7 = r7.getSysKit()     // Catch:{ all -> 0x01d0 }
            com.app.office.common.bulletnumber.ListManage r7 = r7.getListManage()     // Catch:{ all -> 0x01d0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x01d0 }
            com.app.office.common.bulletnumber.ListData r8 = r7.getListData(r8)     // Catch:{ all -> 0x01d0 }
            if (r8 == 0) goto L_0x006b
            com.app.office.common.bulletnumber.ListLevel[] r7 = r8.getLevels()     // Catch:{ all -> 0x01d0 }
            int r7 = r7.length     // Catch:{ all -> 0x01d0 }
            if (r7 != 0) goto L_0x006d
        L_0x006b:
            monitor-exit(r2)
            return r11
        L_0x006d:
            long r9 = r5.getEndOffset()     // Catch:{ all -> 0x01d0 }
            r0 = 1
            long r9 = r9 - r0
            com.app.office.simpletext.model.IElement r3 = r3.getLeaf(r9)     // Catch:{ all -> 0x01d0 }
            byte r7 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            com.app.office.common.bulletnumber.ListLevel r7 = r8.getLevel(r7)     // Catch:{ all -> 0x01d0 }
            com.app.office.common.bulletnumber.ListKit r9 = com.app.office.common.bulletnumber.ListKit.instance()     // Catch:{ all -> 0x01d0 }
            byte r10 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            java.lang.String r9 = r9.getBulletText(r8, r7, r4, r10)     // Catch:{ all -> 0x01d0 }
            byte r10 = r4.rootType     // Catch:{ all -> 0x01d0 }
            if (r10 != r12) goto L_0x0091
            byte r10 = r8.getNormalPreParaLevel()     // Catch:{ all -> 0x01d0 }
            goto L_0x0095
        L_0x0091:
            byte r10 = r8.getPreParaLevel()     // Catch:{ all -> 0x01d0 }
        L_0x0095:
            byte r0 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            if (r0 >= r10) goto L_0x00b6
            byte r10 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            int r10 = r10 + r12
        L_0x009c:
            r0 = 9
            if (r10 >= r0) goto L_0x00db
            byte r0 = r4.rootType     // Catch:{ all -> 0x01d0 }
            if (r0 != r12) goto L_0x00ac
            com.app.office.common.bulletnumber.ListLevel r0 = r8.getLevel(r10)     // Catch:{ all -> 0x01d0 }
            r0.setNormalParaCount(r11)     // Catch:{ all -> 0x01d0 }
            goto L_0x00b3
        L_0x00ac:
            com.app.office.common.bulletnumber.ListLevel r0 = r8.getLevel(r10)     // Catch:{ all -> 0x01d0 }
            r0.setParaCount(r11)     // Catch:{ all -> 0x01d0 }
        L_0x00b3:
            int r10 = r10 + 1
            goto L_0x009c
        L_0x00b6:
            byte r0 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            if (r0 <= r10) goto L_0x00db
            int r10 = r10 + r12
        L_0x00bb:
            byte r0 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            if (r10 >= r0) goto L_0x00db
            com.app.office.common.bulletnumber.ListLevel r0 = r8.getLevel(r10)     // Catch:{ all -> 0x01d0 }
            byte r1 = r4.rootType     // Catch:{ all -> 0x01d0 }
            if (r1 != r12) goto L_0x00d0
            int r1 = r0.getNormalParaCount()     // Catch:{ all -> 0x01d0 }
            int r1 = r1 + r12
            r0.setNormalParaCount(r1)     // Catch:{ all -> 0x01d0 }
            goto L_0x00d8
        L_0x00d0:
            int r1 = r0.getParaCount()     // Catch:{ all -> 0x01d0 }
            int r1 = r1 + r12
            r0.setParaCount(r1)     // Catch:{ all -> 0x01d0 }
        L_0x00d8:
            int r10 = r10 + 1
            goto L_0x00bb
        L_0x00db:
            byte r4 = r4.rootType     // Catch:{ all -> 0x01d0 }
            if (r4 != r12) goto L_0x00ed
            int r4 = r7.getNormalParaCount()     // Catch:{ all -> 0x01d0 }
            int r4 = r4 + r12
            r7.setNormalParaCount(r4)     // Catch:{ all -> 0x01d0 }
            byte r4 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            r8.setNormalPreParaLevel(r4)     // Catch:{ all -> 0x01d0 }
            goto L_0x00fa
        L_0x00ed:
            int r4 = r7.getParaCount()     // Catch:{ all -> 0x01d0 }
            int r4 = r4 + r12
            r7.setParaCount(r4)     // Catch:{ all -> 0x01d0 }
            byte r4 = r6.listLevel     // Catch:{ all -> 0x01d0 }
            r8.setPreParaLevel(r4)     // Catch:{ all -> 0x01d0 }
        L_0x00fa:
            r2.currLevel = r7     // Catch:{ all -> 0x01d0 }
            r8 = r3
            goto L_0x0120
        L_0x00fe:
            int r4 = r6.pgBulletID     // Catch:{ all -> 0x01d0 }
            if (r4 < 0) goto L_0x0120
            long r8 = r5.getStartOffset()     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.model.IElement r8 = r3.getLeaf(r8)     // Catch:{ all -> 0x01d0 }
            com.app.office.system.IControl r3 = r7.getControl()     // Catch:{ all -> 0x01d0 }
            com.app.office.system.SysKit r3 = r3.getSysKit()     // Catch:{ all -> 0x01d0 }
            com.app.office.pg.model.PGBulletText r3 = r3.getPGBulletText()     // Catch:{ all -> 0x01d0 }
            int r4 = r6.pgBulletID     // Catch:{ all -> 0x01d0 }
            java.lang.String r9 = r3.getBulletText(r4)     // Catch:{ all -> 0x01d0 }
            if (r9 != 0) goto L_0x0120
            java.lang.String r9 = ""
        L_0x0120:
            com.app.office.simpletext.model.AttrManage r3 = com.app.office.simpletext.model.AttrManage.instance()     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.view.CharAttr r4 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.model.IAttributeSet r5 = r5.getAttribute()     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.model.IAttributeSet r6 = r8.getAttribute()     // Catch:{ all -> 0x01d0 }
            r3.fillCharAttr(r4, r5, r6)     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.view.CharAttr r3 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            boolean r3 = r3.isBold     // Catch:{ all -> 0x01d0 }
            if (r3 == 0) goto L_0x014b
            com.app.office.simpletext.view.CharAttr r3 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            boolean r3 = r3.isItalic     // Catch:{ all -> 0x01d0 }
            if (r3 == 0) goto L_0x014b
            android.graphics.Paint r3 = r2.paint     // Catch:{ all -> 0x01d0 }
            r4 = -1102263091(0xffffffffbe4ccccd, float:-0.2)
            r3.setTextSkewX(r4)     // Catch:{ all -> 0x01d0 }
            android.graphics.Paint r3 = r2.paint     // Catch:{ all -> 0x01d0 }
            r3.setFakeBoldText(r12)     // Catch:{ all -> 0x01d0 }
            goto L_0x0164
        L_0x014b:
            com.app.office.simpletext.view.CharAttr r3 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            boolean r3 = r3.isBold     // Catch:{ all -> 0x01d0 }
            if (r3 == 0) goto L_0x0157
            android.graphics.Paint r3 = r2.paint     // Catch:{ all -> 0x01d0 }
            r3.setFakeBoldText(r12)     // Catch:{ all -> 0x01d0 }
            goto L_0x0164
        L_0x0157:
            com.app.office.simpletext.view.CharAttr r3 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            boolean r3 = r3.isItalic     // Catch:{ all -> 0x01d0 }
            if (r3 == 0) goto L_0x0164
            android.graphics.Paint r3 = r2.paint     // Catch:{ all -> 0x01d0 }
            r4 = -1098907648(0xffffffffbe800000, float:-0.25)
            r3.setTextSkewX(r4)     // Catch:{ all -> 0x01d0 }
        L_0x0164:
            android.graphics.Paint r3 = r2.paint     // Catch:{ all -> 0x01d0 }
            android.graphics.Typeface r4 = android.graphics.Typeface.SERIF     // Catch:{ all -> 0x01d0 }
            android.graphics.Typeface r4 = android.graphics.Typeface.create(r4, r11)     // Catch:{ all -> 0x01d0 }
            r3.setTypeface(r4)     // Catch:{ all -> 0x01d0 }
            android.graphics.Paint r3 = r2.paint     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.view.CharAttr r4 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            int r4 = r4.fontSize     // Catch:{ all -> 0x01d0 }
            float r4 = (float) r4     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.view.CharAttr r5 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            int r5 = r5.fontScale     // Catch:{ all -> 0x01d0 }
            float r5 = (float) r5     // Catch:{ all -> 0x01d0 }
            r6 = 1120403456(0x42c80000, float:100.0)
            float r5 = r5 / r6
            float r4 = r4 * r5
            r5 = 1068149419(0x3faaaaab, float:1.3333334)
            float r4 = r4 * r5
            r3.setTextSize(r4)     // Catch:{ all -> 0x01d0 }
            android.graphics.Paint r3 = r2.paint     // Catch:{ all -> 0x01d0 }
            com.app.office.simpletext.view.CharAttr r4 = r2.charAttr     // Catch:{ all -> 0x01d0 }
            int r4 = r4.fontColor     // Catch:{ all -> 0x01d0 }
            r3.setColor(r4)     // Catch:{ all -> 0x01d0 }
            int r3 = r9.length()     // Catch:{ all -> 0x01d0 }
            float[] r4 = new float[r3]     // Catch:{ all -> 0x01d0 }
            android.graphics.Paint r5 = r2.paint     // Catch:{ all -> 0x01d0 }
            r5.getTextWidths(r9, r4)     // Catch:{ all -> 0x01d0 }
            r5 = 0
            r6 = 0
        L_0x019e:
            if (r6 >= r3) goto L_0x01a6
            r7 = r4[r6]     // Catch:{ all -> 0x01d0 }
            float r5 = r5 + r7
            int r6 = r6 + 1
            goto L_0x019e
        L_0x01a6:
            int r3 = r2.getX()     // Catch:{ all -> 0x01d0 }
            float r3 = (float) r3     // Catch:{ all -> 0x01d0 }
            float r3 = r3 + r5
            r4 = 1105199104(0x41e00000, float:28.0)
            float r3 = r3 % r4
            int r3 = (int) r3     // Catch:{ all -> 0x01d0 }
            if (r3 <= 0) goto L_0x01b5
            float r3 = (float) r3     // Catch:{ all -> 0x01d0 }
            float r4 = r4 - r3
            float r5 = r5 + r4
        L_0x01b5:
            int r3 = (int) r5     // Catch:{ all -> 0x01d0 }
            android.graphics.Paint r4 = r2.paint     // Catch:{ all -> 0x01d0 }
            float r4 = r4.descent()     // Catch:{ all -> 0x01d0 }
            android.graphics.Paint r5 = r2.paint     // Catch:{ all -> 0x01d0 }
            float r5 = r5.ascent()     // Catch:{ all -> 0x01d0 }
            float r4 = r4 - r5
            double r4 = (double) r4     // Catch:{ all -> 0x01d0 }
            double r4 = java.lang.Math.ceil(r4)     // Catch:{ all -> 0x01d0 }
            int r4 = (int) r4     // Catch:{ all -> 0x01d0 }
            r2.setSize(r3, r4)     // Catch:{ all -> 0x01d0 }
            r2.content = r9     // Catch:{ all -> 0x01d0 }
            monitor-exit(r2)
            return r11
        L_0x01d0:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.view.BNView.doLayout(com.app.office.simpletext.model.IDocument, com.app.office.simpletext.view.DocAttr, com.app.office.simpletext.view.PageAttr, com.app.office.simpletext.view.ParaAttr, com.app.office.wp.view.ParagraphView, int, int, int, int, int):int");
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        Object obj = this.content;
        if (obj != null && (obj instanceof String)) {
            String str = (String) obj;
            float textSize = this.paint.getTextSize();
            this.paint.setTextSize((this.charAttr.subSuperScriptType > 0 ? textSize / 2.0f : textSize) * f);
            canvas.drawText(str, 0, str.length(), (float) i3, ((float) i4) - this.paint.ascent(), this.paint);
            this.paint.setTextSize(textSize);
        }
    }

    public int getBaseline() {
        return (int) (-this.paint.ascent());
    }

    public synchronized void dispose() {
        this.content = null;
        this.paint = null;
        this.charAttr = null;
        ListLevel listLevel = this.currLevel;
        if (listLevel != null) {
            listLevel.setParaCount(listLevel.getParaCount() - 1);
        }
    }
}
