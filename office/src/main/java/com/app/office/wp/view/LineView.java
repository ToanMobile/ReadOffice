package com.app.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.CharAttr;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.simpletext.view.IView;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.ViewKit;

public class LineView extends AbstractView {
    private int heightExceptShape;

    public void free() {
    }

    public short getType() {
        return 6;
    }

    public LineView() {
    }

    public LineView(IElement iElement) {
        this.elem = iElement;
    }

    public void layoutAlignment(DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, BNView bNView, int i, int i2) {
        layoutAlignment(docAttr, pageAttr, paraAttr, bNView, i, i2, true);
    }

    public void setHeightExceptShape(int i) {
        this.heightExceptShape = i;
    }

    public int getHeightExceptShape() {
        return this.heightExceptShape;
    }

    public void layoutAlignment(DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, BNView bNView, int i, int i2, boolean z) {
        if (!ViewKit.instance().getBitValue(i2, 3)) {
            layoutHorizontal(docAttr, pageAttr, paraAttr, bNView, i, i2);
        }
        if (z) {
            layoutVertical(docAttr, pageAttr, paraAttr, bNView, i, i2);
        }
    }

    public void layoutHorizontal(DocAttr docAttr, PageAttr pageAttr, ParaAttr paraAttr, BNView bNView, int i, int i2) {
        byte b = paraAttr.horizontalAlignment;
        if (b == 1) {
            this.x += (i - this.width) / 2;
        } else if (b == 2) {
            this.x += i - this.width;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0082, code lost:
        if (r3 != 5) goto L_0x00ea;
     */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void layoutVertical(com.app.office.simpletext.view.DocAttr r3, com.app.office.simpletext.view.PageAttr r4, com.app.office.simpletext.view.ParaAttr r5, com.app.office.wp.view.BNView r6, int r7, int r8) {
        /*
            r2 = this;
            com.app.office.simpletext.view.IView r3 = r2.getChildView()
            com.app.office.wp.view.LeafView r3 = (com.app.office.wp.view.LeafView) r3
            if (r3 != 0) goto L_0x0009
            return
        L_0x0009:
            r7 = 0
            if (r6 != 0) goto L_0x000e
            r8 = 0
            goto L_0x0012
        L_0x000e:
            int r8 = r6.getBaseline()
        L_0x0012:
            if (r3 == 0) goto L_0x0023
            int r0 = r3.getBaseline()
            int r8 = java.lang.Math.max(r8, r0)
            com.app.office.simpletext.view.IView r3 = r3.getNextView()
            com.app.office.wp.view.LeafView r3 = (com.app.office.wp.view.LeafView) r3
            goto L_0x0012
        L_0x0023:
            com.app.office.simpletext.view.IView r3 = r2.getChildView()
            com.app.office.wp.view.LeafView r3 = (com.app.office.wp.view.LeafView) r3
        L_0x0029:
            if (r3 == 0) goto L_0x0073
            short r0 = r3.getType()
            r1 = 13
            if (r0 != r1) goto L_0x0043
            r0 = r3
            com.app.office.wp.view.ShapeView r0 = (com.app.office.wp.view.ShapeView) r0
            boolean r0 = r0.isInline()
            if (r0 != 0) goto L_0x005b
            com.app.office.simpletext.view.IView r3 = r3.getNextView()
            com.app.office.wp.view.LeafView r3 = (com.app.office.wp.view.LeafView) r3
            goto L_0x0029
        L_0x0043:
            short r0 = r3.getType()
            r1 = 8
            if (r0 != r1) goto L_0x005b
            r0 = r3
            com.app.office.wp.view.ObjView r0 = (com.app.office.wp.view.ObjView) r0
            boolean r0 = r0.isInline()
            if (r0 != 0) goto L_0x005b
            com.app.office.simpletext.view.IView r3 = r3.getNextView()
            com.app.office.wp.view.LeafView r3 = (com.app.office.wp.view.LeafView) r3
            goto L_0x0029
        L_0x005b:
            int r0 = r3.getBaseline()
            int r0 = r8 - r0
            r3.setTopIndent(r0)
            int r1 = r3.getY()
            int r1 = r1 + r0
            r3.setY(r1)
            com.app.office.simpletext.view.IView r3 = r3.getNextView()
            com.app.office.wp.view.LeafView r3 = (com.app.office.wp.view.LeafView) r3
            goto L_0x0029
        L_0x0073:
            byte r3 = r5.lineSpaceType
            r8 = 0
            r0 = 1
            if (r3 == 0) goto L_0x00b8
            if (r3 == r0) goto L_0x00b8
            r1 = 2
            if (r3 == r1) goto L_0x00b8
            r1 = 3
            if (r3 == r1) goto L_0x0085
            r1 = 5
            if (r3 == r1) goto L_0x00b8
            goto L_0x00ea
        L_0x0085:
            float r3 = r5.lineSpaceValue
            int r7 = r2.heightExceptShape
            float r7 = (float) r7
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 <= 0) goto L_0x00a0
            float r3 = r4.pageLinePitch
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 <= 0) goto L_0x009d
            float r3 = r5.lineSpaceValue
            float r4 = r4.pageLinePitch
            float r8 = java.lang.Math.max(r3, r4)
            goto L_0x00e9
        L_0x009d:
            float r8 = r5.lineSpaceValue
            goto L_0x00e9
        L_0x00a0:
            float r3 = r4.pageLinePitch
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 <= 0) goto L_0x00b4
            int r3 = r2.heightExceptShape
            float r3 = (float) r3
            float r5 = r4.pageLinePitch
            float r3 = r3 / r5
            double r7 = (double) r3
            double r7 = java.lang.Math.ceil(r7)
            float r3 = r4.pageLinePitch
            goto L_0x00d8
        L_0x00b4:
            int r3 = r2.heightExceptShape
            float r8 = (float) r3
            goto L_0x00e9
        L_0x00b8:
            float r3 = r4.pageLinePitch
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 <= 0) goto L_0x00e2
            int r3 = r2.heightExceptShape
            float r3 = (float) r3
            float r7 = r4.pageLinePitch
            float r8 = r5.lineSpaceValue
            float r7 = r7 * r8
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 <= 0) goto L_0x00dd
            int r3 = r2.heightExceptShape
            float r3 = (float) r3
            float r5 = r4.pageLinePitch
            float r3 = r3 / r5
            double r7 = (double) r3
            double r7 = java.lang.Math.ceil(r7)
            float r3 = r4.pageLinePitch
        L_0x00d8:
            double r3 = (double) r3
            double r7 = r7 * r3
            float r8 = (float) r7
            goto L_0x00e9
        L_0x00dd:
            float r3 = r4.pageLinePitch
            float r4 = r5.lineSpaceValue
            goto L_0x00e7
        L_0x00e2:
            float r3 = r5.lineSpaceValue
            int r4 = r2.heightExceptShape
            float r4 = (float) r4
        L_0x00e7:
            float r8 = r3 * r4
        L_0x00e9:
            r7 = 1
        L_0x00ea:
            if (r7 == 0) goto L_0x010d
            int r3 = r2.heightExceptShape
            float r3 = (float) r3
            float r8 = r8 - r3
            r3 = 1073741824(0x40000000, float:2.0)
            float r8 = r8 / r3
            int r3 = (int) r8
            r2.setTopIndent(r3)
            r2.setBottomIndent(r3)
            int r4 = r2.getY()
            int r4 = r4 + r3
            r2.setY(r4)
            if (r6 == 0) goto L_0x010d
            r6.setTopIndent(r3)
            r6.setBottomIndent(r3)
            r6.setY(r3)
        L_0x010d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.view.LineView.layoutVertical(com.app.office.simpletext.view.DocAttr, com.app.office.simpletext.view.PageAttr, com.app.office.simpletext.view.ParaAttr, com.app.office.wp.view.BNView, int, int):void");
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView view = getView(j, 7, z);
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
        IView view = getView(x, y, 7, z);
        if (view == null) {
            if (x > getWidth()) {
                view = getLastView();
            } else {
                view = getChildView();
            }
        }
        if (view != null) {
            return view.viewToModel(x, y, z);
        }
        return -1;
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        Canvas canvas2 = canvas;
        float f2 = f;
        canvas.save();
        IWord container = getContainer();
        int i3 = ((int) (((float) this.x) * f2)) + i;
        int i4 = ((int) (((float) this.y) * f2)) + i2;
        Rect clipBounds = canvas.getClipBounds();
        if (getTopIndent() < 0 && container != null && container.getEditType() == 0) {
            float f3 = (float) i3;
            float f4 = (float) i4;
            canvas.clipRect(f3, f4 - (((float) getTopIndent()) * f2), (((float) getLayoutSpan((byte) 0)) * f2) + f3, (f4 - (((float) getTopIndent()) * f2)) + (((float) getLayoutSpan((byte) 1)) * f2));
        }
        for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
            if (childView.intersection(clipBounds, i3, i4, f2)) {
                childView.draw(canvas, i3, i4, f2);
            }
        }
        canvas.restore();
        drawUnderline(canvas, i, i2, f);
        if (container != null && container.getHighlight() != null) {
            container.getHighlight().draw(canvas, this, i3, i4, getStartOffset((IDocument) null), getEndOffset((IDocument) null), f);
        }
    }

    private void drawUnderline(Canvas canvas, int i, int i2, float f) {
        Paint paint = new Paint();
        int i3 = ((int) (((float) this.x) * f)) + i;
        int topIndent = (int) ((((float) this.y) * f) + ((float) i2) + (((float) getTopIndent()) * f));
        LeafView leafView = (LeafView) getChildView();
        int i4 = Integer.MAX_VALUE;
        int i5 = 0;
        int i6 = 0;
        while (leafView != null) {
            CharAttr charAttr = leafView.getCharAttr();
            if (charAttr == null) {
                leafView = (LeafView) leafView.getNextView();
            } else {
                if (charAttr.underlineType > 0) {
                    if (i4 != Integer.MAX_VALUE && i4 != charAttr.underlineColor) {
                        paint.setColor(i4);
                        int i7 = i5 + topIndent;
                        int i8 = i3 + i6;
                        float f2 = (float) (i7 + 2);
                        canvas.drawRect((float) i3, (float) (i7 + 1), (float) i8, f2, paint);
                        i4 = charAttr.underlineColor;
                        i3 = i8;
                        i5 = 0;
                        i6 = 0;
                    } else if (i4 == Integer.MAX_VALUE) {
                        i4 = charAttr.underlineColor;
                    }
                    i6 += (int) (((float) leafView.getWidth()) * f);
                    i5 = Math.max(i5, (int) (((float) leafView.getUnderlinePosition()) * f));
                } else {
                    if (i4 != Integer.MAX_VALUE) {
                        paint.setColor(i4);
                        int i9 = i5 + topIndent;
                        int i10 = i3 + i6;
                        float f3 = (float) (i9 + 2);
                        canvas.drawRect((float) i3, (float) (i9 + 1), (float) i10, f3, paint);
                        i3 = i10;
                        i5 = 0;
                        i6 = 0;
                    }
                    i3 += (int) (((float) leafView.getWidth()) * f);
                    i4 = Integer.MAX_VALUE;
                }
                leafView = (LeafView) leafView.getNextView();
            }
        }
        if (i4 != Integer.MAX_VALUE) {
            paint.setColor(i4);
            int i11 = topIndent + i5;
            canvas.drawRect((float) i3, (float) (i11 + 1), (float) (i3 + i6), (float) (i11 + 2), paint);
        }
    }

    public void dispose() {
        super.dispose();
    }
}
