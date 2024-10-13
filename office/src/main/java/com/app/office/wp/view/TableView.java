package com.app.office.wp.view;

import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.IView;

public class TableView extends ParagraphView {
    private boolean isBreakPages;

    public short getType() {
        return 9;
    }

    public TableView(IElement iElement) {
        super(iElement);
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [com.app.office.simpletext.view.IView] */
    /* JADX WARNING: type inference failed for: r0v4, types: [com.app.office.simpletext.view.IView] */
    /* JADX WARNING: type inference failed for: r0v6, types: [com.app.office.simpletext.view.IView] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r26, int r27, int r28, float r29) {
        /*
            r25 = this;
            r0 = r25
            r7 = r26
            r8 = r29
            int r1 = r0.x
            float r1 = (float) r1
            float r1 = r1 * r8
            r2 = r27
            float r2 = (float) r2
            float r9 = r1 + r2
            int r1 = r0.y
            float r1 = (float) r1
            float r1 = r1 * r8
            r2 = r28
            float r2 = (float) r2
            float r10 = r1 + r2
            com.app.office.simpletext.view.IView r1 = r25.getChildView()
            com.app.office.wp.view.RowView r1 = (com.app.office.wp.view.RowView) r1
            android.graphics.Rect r11 = r26.getClipBounds()
            android.graphics.Paint r12 = new android.graphics.Paint
            r12.<init>()
            android.graphics.Paint$Style r2 = android.graphics.Paint.Style.STROKE
            r12.setStyle(r2)
            r13 = r1
        L_0x002f:
            if (r13 == 0) goto L_0x0180
            int r1 = (int) r9
            int r2 = (int) r10
            boolean r1 = r13.intersection(r11, r1, r2, r8)
            if (r1 == 0) goto L_0x0169
            int r1 = r13.getX()
            float r1 = (float) r1
            float r1 = r1 * r8
            float r14 = r9 + r1
            int r1 = r13.getY()
            float r1 = (float) r1
            float r1 = r1 * r8
            float r15 = r10 + r1
            int r1 = r13.getHeight()
            float r1 = (float) r1
            float r6 = r1 * r8
            com.app.office.simpletext.view.IView r1 = r13.getChildView()
            com.app.office.wp.view.CellView r1 = (com.app.office.wp.view.CellView) r1
            r16 = 1
            r2 = 0
            r5 = r1
            r1 = 0
            r3 = 1
        L_0x005e:
            if (r5 == 0) goto L_0x0169
            int r4 = (int) r14
            int r0 = (int) r15
            boolean r17 = r5.intersection(r11, r4, r0, r8)
            if (r17 == 0) goto L_0x0149
            boolean r17 = r5.isMergedCell()
            if (r17 == 0) goto L_0x007f
            boolean r17 = r5.isFirstMergedCell()
            if (r17 != 0) goto L_0x007f
            com.app.office.simpletext.view.IView r0 = r5.getNextView()
            r5 = r0
            com.app.office.wp.view.CellView r5 = (com.app.office.wp.view.CellView) r5
            r3 = 1
            r0 = r25
            goto L_0x005e
        L_0x007f:
            r17 = r4
            int r4 = r5.getY()
            float r4 = (float) r4
            float r4 = r4 * r8
            float r4 = r4 + r15
            r27 = r10
            r10 = 0
            if (r3 == 0) goto L_0x009a
            int r1 = r5.getX()
            float r1 = (float) r1
            float r1 = r1 * r8
            float r1 = r1 + r14
            r3 = r1
            r18 = 0
            goto L_0x009e
        L_0x009a:
            float r1 = r1 + r2
            r18 = r3
            r3 = r1
        L_0x009e:
            int r1 = r5.getLayoutSpan(r10)
            float r1 = (float) r1
            float r10 = r1 * r8
            int r1 = r5.getHeight()
            float r1 = (float) r1
            float r1 = r1 * r8
            float r19 = java.lang.Math.max(r1, r6)
            float r1 = r3 + r10
            boolean r2 = r5.isValidLastCell()
            if (r2 == 0) goto L_0x00d4
            int r2 = r25.getWidth()
            float r2 = (float) r2
            float r2 = r2 * r8
            float r2 = r2 + r9
            float r2 = r1 - r2
            float r2 = java.lang.Math.abs(r2)
            r20 = 1092616192(0x41200000, float:10.0)
            int r2 = (r2 > r20 ? 1 : (r2 == r20 ? 0 : -1))
            if (r2 > 0) goto L_0x00d4
            int r1 = r25.getWidth()
            float r1 = (float) r1
            float r1 = r1 * r8
            float r1 = r1 + r9
        L_0x00d4:
            r2 = r1
            int r1 = r5.getBackground()
            r28 = r2
            r2 = -1
            if (r1 == r2) goto L_0x0111
            int r2 = r12.getColor()
            int r1 = r5.getBackground()
            r12.setColor(r1)
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.FILL
            r12.setStyle(r1)
            float r20 = r4 + r19
            r1 = r26
            r21 = r9
            r9 = r2
            r2 = r3
            r22 = r3
            r3 = r4
            r23 = r10
            r10 = r17
            r17 = r11
            r11 = r4
            r4 = r28
            r24 = r14
            r14 = r5
            r5 = r20
            r20 = r6
            r6 = r12
            r1.drawRect(r2, r3, r4, r5, r6)
            r12.setColor(r9)
            goto L_0x0121
        L_0x0111:
            r22 = r3
            r20 = r6
            r21 = r9
            r23 = r10
            r24 = r14
            r10 = r17
            r14 = r5
            r17 = r11
            r11 = r4
        L_0x0121:
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            r12.setStyle(r1)
            float r9 = r11 + r19
            r1 = r26
            r2 = r22
            r3 = r11
            r4 = r28
            r5 = r9
            r6 = r12
            r1.drawRect(r2, r3, r4, r5, r6)
            r26.save()
            r2 = r28
            r1 = r22
            r7.clipRect(r1, r11, r2, r9)
            r14.draw(r7, r10, r0, r8)
            r26.restore()
            r3 = r18
            r2 = r23
            goto L_0x0154
        L_0x0149:
            r20 = r6
            r21 = r9
            r27 = r10
            r17 = r11
            r24 = r14
            r14 = r5
        L_0x0154:
            com.app.office.simpletext.view.IView r0 = r14.getNextView()
            r5 = r0
            com.app.office.wp.view.CellView r5 = (com.app.office.wp.view.CellView) r5
            r0 = r25
            r10 = r27
            r11 = r17
            r6 = r20
            r9 = r21
            r14 = r24
            goto L_0x005e
        L_0x0169:
            r21 = r9
            r27 = r10
            r17 = r11
            com.app.office.simpletext.view.IView r0 = r13.getNextView()
            r13 = r0
            com.app.office.wp.view.RowView r13 = (com.app.office.wp.view.RowView) r13
            r0 = r25
            r10 = r27
            r11 = r17
            r9 = r21
            goto L_0x002f
        L_0x0180:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.view.TableView.draw(android.graphics.Canvas, int, int, float):void");
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView view = getView(j, 10, z);
        if (view != null) {
            view.modelToView(j, rectangle, z);
        }
        rectangle.x += getX();
        rectangle.y += getY();
        return rectangle;
    }

    public long viewToModel(int i, int i2, boolean z) {
        int x = i - getX();
        int y = i2 - getY();
        IView childView = getChildView();
        if (childView != null && y > childView.getY()) {
            while (childView != null && (y < childView.getY() || y >= childView.getY() + childView.getLayoutSpan((byte) 1))) {
                childView = childView.getNextView();
            }
        }
        if (childView == null) {
            childView = getChildView();
        }
        if (childView != null) {
            return childView.viewToModel(x, y, z);
        }
        return -1;
    }

    public void dispose() {
        super.dispose();
    }

    public boolean isBreakPages() {
        return this.isBreakPages;
    }

    public void setBreakPages(boolean z) {
        this.isBreakPages = z;
    }
}
