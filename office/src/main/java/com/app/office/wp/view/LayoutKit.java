package com.app.office.wp.view;

import com.app.office.simpletext.font.FontKit;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.simpletext.view.IView;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.ViewKit;
import com.app.office.system.IControl;
import com.app.office.wp.control.Word;

public class LayoutKit {
    private static LayoutKit kit = new LayoutKit();

    public int buildLine(IDocument iDocument, ParagraphView paragraphView) {
        return 0;
    }

    private LayoutKit() {
    }

    public static LayoutKit instance() {
        return kit;
    }

    public void layoutAllPage(PageRoot pageRoot, float f) {
        if (pageRoot != null && pageRoot.getChildView() != null) {
            Word word = (Word) pageRoot.getContainer();
            IView childView = pageRoot.getChildView();
            int width = childView.getWidth();
            int width2 = word.getWidth();
            if (width2 == 0) {
                width2 = word.getWordWidth();
            }
            float f2 = (float) width2;
            float f3 = (float) width;
            int i = f2 > f3 * f ? (((int) (((f2 / f) - f3) - 10.0f)) / 2) + 5 : 5;
            int i2 = 5;
            while (childView != null) {
                childView.setLocation(i, i2);
                i2 += childView.getHeight() + 5;
                childView = childView.getNextView();
            }
            int i3 = width + 10;
            pageRoot.setSize(i3, i2);
            ((Word) pageRoot.getContainer()).setSize(i3, i2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x019b, code lost:
        r3 = r38;
        r3.deleteView(r15, true);
        r11 = r3;
        r9 = r24;
        r10 = r30;
        r7 = 1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0156  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int layoutPara(com.app.office.system.IControl r33, com.app.office.simpletext.model.IDocument r34, com.app.office.simpletext.view.DocAttr r35, com.app.office.simpletext.view.PageAttr r36, com.app.office.simpletext.view.ParaAttr r37, com.app.office.wp.view.ParagraphView r38, long r39, int r41, int r42, int r43, int r44, int r45) {
        /*
            r32 = this;
            r15 = r33
            r14 = r37
            r12 = r38
            r10 = r39
            r0 = r45
            int r1 = r14.leftIndent
            int r2 = r14.leftIndent
            int r2 = r43 - r2
            int r3 = r14.rightIndent
            int r2 = r2 - r3
            if (r2 >= 0) goto L_0x0018
            r16 = r43
            goto L_0x001a
        L_0x0018:
            r16 = r2
        L_0x001a:
            com.app.office.simpletext.view.ViewKit r2 = com.app.office.simpletext.view.ViewKit.instance()
            r13 = 3
            boolean r2 = r2.getBitValue(r0, r13)
            r9 = 0
            if (r2 == 0) goto L_0x0028
            r2 = 0
            goto L_0x002a
        L_0x0028:
            r2 = r43
        L_0x002a:
            com.app.office.simpletext.model.IElement r8 = r38.getElement()
            long r17 = r8.getEndOffset()
            com.app.office.simpletext.view.IView r3 = r38.getPreView()
            if (r3 != 0) goto L_0x0051
            int r3 = r14.beforeSpace
            int r3 = r44 - r3
            int r4 = r14.beforeSpace
            r12.setTopIndent(r4)
            int r4 = r14.afterSpace
            r12.setBottomIndent(r4)
            int r4 = r38.getY()
            int r5 = r14.beforeSpace
            int r4 = r4 + r5
            r12.setY(r4)
            goto L_0x0079
        L_0x0051:
            int r4 = r14.beforeSpace
            if (r4 <= 0) goto L_0x006e
            int r4 = r14.beforeSpace
            int r3 = r3.getBottomIndent()
            int r4 = r4 - r3
            int r3 = java.lang.Math.max(r9, r4)
            int r4 = r44 - r3
            r12.setTopIndent(r3)
            int r5 = r38.getY()
            int r5 = r5 + r3
            r12.setY(r5)
            goto L_0x0070
        L_0x006e:
            r4 = r44
        L_0x0070:
            int r3 = r14.afterSpace
            int r3 = r4 - r3
            int r4 = r14.afterSpace
            r12.setBottomIndent(r4)
        L_0x0079:
            com.app.office.simpletext.view.ViewKit r4 = com.app.office.simpletext.view.ViewKit.instance()
            boolean r4 = r4.getBitValue(r0, r9)
            r7 = 1
            if (r3 >= 0) goto L_0x0087
            if (r4 != 0) goto L_0x0087
            return r7
        L_0x0087:
            r6 = 6
            com.app.office.simpletext.view.IView r5 = com.app.office.wp.view.ViewFactory.createView(r15, r8, r8, r6)
            com.app.office.wp.view.LineView r5 = (com.app.office.wp.view.LineView) r5
            r5.setStartOffset(r10)
            r12.appendChlidView(r5)
            com.app.office.simpletext.view.ViewKit r6 = com.app.office.simpletext.view.ViewKit.instance()
            int r6 = r6.setBitValue(r0, r9, r7)
            com.app.office.simpletext.view.ViewKit r0 = com.app.office.simpletext.view.ViewKit.instance()
            boolean r19 = r0.getBitValue(r6, r7)
            r0 = -1
            r21 = r1
            r22 = r3
            r23 = r4
            r4 = r5
            r42 = 0
            r0 = 0
            r1 = 1
            r20 = -1
            r24 = 0
            r5 = r2
            r2 = r10
        L_0x00b6:
            if (r22 <= 0) goto L_0x01fb
            int r25 = (r2 > r17 ? 1 : (r2 == r17 ? 0 : -1))
            if (r25 >= 0) goto L_0x01fb
            if (r0 == r13) goto L_0x01fb
            r0 = 0
            if (r1 == 0) goto L_0x0101
            long r25 = r8.getStartOffset()
            int r27 = (r10 > r25 ? 1 : (r10 == r25 ? 0 : -1))
            if (r27 != 0) goto L_0x0101
            r0 = r32
            r13 = r1
            r1 = r33
            r28 = r2
            r2 = r34
            r3 = r35
            r12 = r4
            r4 = r36
            r30 = r5
            r5 = r37
            r26 = r6
            r6 = r38
            r7 = r21
            r41 = r8
            r8 = r42
            r27 = r12
            r12 = 0
            r9 = r16
            r10 = r22
            r11 = r26
            com.app.office.wp.view.BNView r0 = r0.createBNView(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            if (r0 == 0) goto L_0x00fd
            int r1 = r0.getWidth()
            r10 = r32
            r7 = r0
            r11 = r1
            goto L_0x0112
        L_0x00fd:
            r10 = r32
            r7 = r0
            goto L_0x0110
        L_0x0101:
            r13 = r1
            r28 = r2
            r27 = r4
            r30 = r5
            r26 = r6
            r41 = r8
            r12 = 0
            r7 = 0
            r10 = r32
        L_0x0110:
            r11 = r20
        L_0x0112:
            int r0 = r10.getLineIndent(r15, r11, r14, r13)
            if (r7 == 0) goto L_0x0156
            int r1 = r14.leftIndent
            int r1 = r1 + r0
            int r2 = r14.tabClearPosition
            if (r1 != r2) goto L_0x0156
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r2 = r41.getAttribute()
            r3 = 4104(0x1008, float:5.751E-42)
            boolean r1 = r1.hasAttribute(r2, r3)
            if (r1 == 0) goto L_0x013d
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r2 = r41.getAttribute()
            int r1 = r1.getParaSpecialIndent(r2)
            if (r1 < 0) goto L_0x014d
        L_0x013d:
            com.app.office.simpletext.model.AttrManage r1 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r2 = r41.getAttribute()
            r3 = 4097(0x1001, float:5.741E-42)
            boolean r1 = r1.hasAttribute(r2, r3)
            if (r1 == 0) goto L_0x0156
        L_0x014d:
            r7.setX(r12)
            r0 = r11
            r13 = r27
            r21 = 0
            goto L_0x0158
        L_0x0156:
            r13 = r27
        L_0x0158:
            r13.setLeftIndent(r0)
            int r1 = r21 + r0
            r9 = r42
            r13.setLocation(r1, r9)
            int r20 = r16 - r0
            r0 = r32
            r1 = r33
            r2 = r34
            r3 = r35
            r4 = r36
            r5 = r37
            r6 = r13
            r8 = r21
            r27 = r9
            r10 = r20
            r31 = r11
            r11 = r22
            r15 = r13
            r25 = 3
            r12 = r17
            r14 = r26
            int r0 = r0.layoutLine(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r14)
            r1 = 1
            int r2 = r15.getLayoutSpan(r1)
            if (r19 != 0) goto L_0x01a9
            if (r23 != 0) goto L_0x01a9
            int r3 = r22 - r2
            if (r3 < 0) goto L_0x019b
            com.app.office.simpletext.view.IView r3 = r15.getChildView()
            if (r3 == 0) goto L_0x019b
            if (r20 > 0) goto L_0x01a9
        L_0x019b:
            r3 = r38
            r5 = r15
            r3.deleteView(r5, r1)
            r11 = r3
            r9 = r24
            r10 = r30
            r7 = 1
            goto L_0x0202
        L_0x01a9:
            r3 = r38
            r5 = r15
            r9 = r24
            int r24 = r9 + r2
            int r4 = r27 + r2
            int r22 = r22 - r2
            r2 = 0
            long r6 = r5.getEndOffset(r2)
            r2 = 0
            int r8 = r5.getLayoutSpan(r2)
            r10 = r30
            int r8 = java.lang.Math.max(r10, r8)
            int r9 = (r6 > r17 ? 1 : (r6 == r17 ? 0 : -1))
            if (r9 >= 0) goto L_0x01dd
            if (r22 <= 0) goto L_0x01dd
            r12 = r41
            r11 = r3
            r13 = 6
            r3 = r33
            com.app.office.simpletext.view.IView r5 = com.app.office.wp.view.ViewFactory.createView(r3, r12, r12, r13)
            com.app.office.wp.view.LineView r5 = (com.app.office.wp.view.LineView) r5
            r5.setStartOffset(r6)
            r11.appendChlidView(r5)
            goto L_0x01e3
        L_0x01dd:
            r12 = r41
            r11 = r3
            r13 = 6
            r3 = r33
        L_0x01e3:
            r14 = r37
            r15 = r3
            r42 = r4
            r4 = r5
            r2 = r6
            r5 = r8
            r8 = r12
            r6 = r26
            r20 = r31
            r1 = 0
            r7 = 1
            r9 = 0
            r13 = 3
            r23 = 0
            r12 = r11
            r10 = r39
            goto L_0x00b6
        L_0x01fb:
            r28 = r2
            r10 = r5
            r11 = r12
            r9 = r24
            r7 = r0
        L_0x0202:
            r11.setSize(r10, r9)
            r0 = r28
            r11.setEndOffset(r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.view.LayoutKit.layoutPara(com.app.office.system.IControl, com.app.office.simpletext.model.IDocument, com.app.office.simpletext.view.DocAttr, com.app.office.simpletext.view.PageAttr, com.app.office.simpletext.view.ParaAttr, com.app.office.wp.view.ParagraphView, long, int, int, int, int, int):int");
    }

    public int layoutLine(IControl iControl, IDocument iDocument, DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, LineView lineView, BNView bNView, int i, int i2, int i3, int i4, long j, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        IElement leaf;
        int i10;
        IDocument iDocument2 = iDocument;
        LineView lineView2 = lineView;
        long startOffset = lineView2.getStartOffset((IDocument) null);
        IElement element = lineView.getElement();
        int i11 = 0;
        int i12 = i5;
        boolean bitValue = ViewKit.instance().getBitValue(i12, 0);
        int i13 = i3;
        long j2 = startOffset;
        int i14 = i12;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        while (true) {
            if (((i13 <= 0 || j2 >= j) && !bitValue) || (leaf = iDocument2.getLeaf(j2)) == null) {
                i7 = i14;
                i6 = i17;
                i8 = i15;
                i9 = i19;
                j2 = j2;
            } else {
                LeafView leafView = (LeafView) ViewFactory.createView(iControl, leaf, element, 7);
                lineView2.appendChlidView(leafView);
                leafView.setStartOffset(j2);
                leafView.setLocation(i18, i11);
                int i20 = i16;
                int i21 = i17;
                int i22 = i18;
                long j3 = j2;
                int i23 = i14;
                i15 = leafView.doLayout(docAttr, pageAttr, paraAttr, i18, 0, i13, i4, j, i23);
                if ((leafView.getType() == 8 || leafView.getType() == 13) && i15 == 1) {
                    lineView2.deleteView(leafView, true);
                    i7 = i23;
                    i9 = i19;
                    i16 = i20;
                    i6 = i21;
                    j2 = j3;
                    i8 = 0;
                    break;
                }
                j2 = leafView.getEndOffset((IDocument) null);
                lineView2.setEndOffset(j2);
                int layoutSpan = leafView.getLayoutSpan((byte) 0);
                i19 += layoutSpan;
                i18 = i22 + layoutSpan;
                int max = Math.max(i21, leafView.getLayoutSpan((byte) 1));
                if (leafView.getType() == 8 || leafView.getType() == 13) {
                    i16 = i20;
                    i10 = 1;
                } else {
                    i10 = 1;
                    i16 = Math.max(i20, leafView.getLayoutSpan((byte) 1));
                }
                i13 -= layoutSpan;
                if (i15 == i10 || i15 == 2 || i15 == 3) {
                    i7 = i23;
                    i6 = max;
                    i8 = i15;
                    i9 = i19;
                } else {
                    i14 = ViewKit.instance().setBitValue(i23, 0, false);
                    i17 = max;
                    bitValue = false;
                    i11 = 0;
                }
            }
        }
        lineView2.setSize(i9, i6);
        lineView2.setHeightExceptShape(i16);
        if (i8 == 1) {
            adjustLine(lineView2, ((long) FontKit.instance().findBreakOffset(element.getText(iDocument2).substring((int) (startOffset - element.getStartOffset())), (int) (j2 - startOffset))) + startOffset);
        }
        lineView.layoutAlignment(docAttr, pageAttr, paraAttr, bNView, i3, i7);
        return i8;
    }

    private BNView createBNView(IControl iControl, IDocument iDocument, DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, ParagraphView paragraphView, int i, int i2, int i3, int i4, int i5) {
        ParaAttr paraAttr2 = paraAttr;
        if ((paraAttr2.listID < 0 || paraAttr2.listLevel < 0) && paraAttr2.pgBulletID < 0) {
            return null;
        }
        IControl iControl2 = iControl;
        BNView bNView = (BNView) ViewFactory.createView(iControl, (IElement) null, (IElement) null, 13);
        bNView.doLayout(iDocument, docAttr, pageAttr, paraAttr, paragraphView, i, i2, i3, i4, i5);
        paragraphView.setBNView(bNView);
        return bNView;
    }

    private void adjustLine(LineView lineView, long j) {
        IView lastView = lineView.getLastView();
        int width = lineView.getWidth();
        while (lastView != null && lastView.getStartOffset((IDocument) null) >= j) {
            IView preView = lastView.getPreView();
            width -= lastView.getWidth();
            lineView.deleteView(lastView, true);
            lastView = preView;
        }
        if (lastView != null && lastView.getEndOffset((IDocument) null) > j) {
            lastView.setEndOffset(j);
            int width2 = width - lastView.getWidth();
            int textWidth = (int) ((LeafView) lastView).getTextWidth();
            lastView.setWidth(textWidth);
            width = width2 + textWidth;
        }
        lineView.setEndOffset(j);
        lineView.setWidth(width);
    }

    private int getLineIndent(IControl iControl, int i, ParaAttr paraAttr, boolean z) {
        if (z) {
            if (i <= 0) {
                i = 0;
            }
            return paraAttr.specialIndentValue > 0 ? paraAttr.specialIndentValue + i : i;
        } else if (z || paraAttr.specialIndentValue >= 0) {
            return 0;
        } else {
            if (i <= 0 || iControl.getApplicationType() != 2) {
                return -paraAttr.specialIndentValue;
            }
            return i;
        }
    }
}
