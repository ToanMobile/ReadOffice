package com.app.office.simpletext.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.animate.IAnimation;
import com.app.office.pg.animate.ShapeAnimation;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.system.IControl;
import com.app.office.wp.view.LayoutKit;
import com.app.office.wp.view.LineView;
import com.app.office.wp.view.ParagraphView;
import com.app.office.wp.view.ViewFactory;
import java.util.ArrayList;

public class STRoot extends AbstractView implements IRoot {
    private IWord container;
    private IDocument doc;
    private DocAttr docAttr = new DocAttr();
    private boolean isWrapLine;
    private PageAttr pageAttr = new PageAttr();
    private ParaAttr paraAttr = new ParaAttr();

    public void backLayout() {
    }

    public boolean canBackLayout() {
        return false;
    }

    public short getType() {
        return 3;
    }

    public ViewContainer getViewContainer() {
        return null;
    }

    public STRoot(IWord iWord, IDocument iDocument) {
        this.doc = iDocument;
        this.container = iWord;
    }

    public IWord getContainer() {
        return this.container;
    }

    public IDocument getDocument() {
        return this.doc;
    }

    public IControl getControl() {
        return this.container.getControl();
    }

    public void doLayout() {
        STRoot sTRoot;
        byte b;
        int i;
        int i2;
        long j;
        int i3;
        STRoot sTRoot2 = this;
        AttrManage.instance().fillPageAttr(sTRoot2.pageAttr, sTRoot2.doc.getSection(0).getAttribute());
        int i4 = sTRoot2.pageAttr.leftMargin;
        int i5 = sTRoot2.pageAttr.topMargin;
        sTRoot2.setTopIndent(sTRoot2.pageAttr.topMargin);
        sTRoot2.setLeftIndent(sTRoot2.pageAttr.leftMargin);
        int max = Math.max(5, ((sTRoot2.isWrapLine ? sTRoot2.pageAttr.pageWidth : Integer.MAX_VALUE) - sTRoot2.pageAttr.leftMargin) - sTRoot2.pageAttr.rightMargin);
        int i6 = 0;
        ViewKit.instance().setBitValue(0, 0, true);
        int bitValue = ViewKit.instance().setBitValue(0, 3, !sTRoot2.isWrapLine || sTRoot2.pageAttr.horizontalAlign == 1);
        long areaEnd = sTRoot2.doc.getAreaEnd(0);
        IElement paragraphForIndex = sTRoot2.doc.getParagraphForIndex(0, 0);
        ParagraphView paragraphView = (ParagraphView) ViewFactory.createView(sTRoot2.container.getControl(), paragraphForIndex, (IElement) null, 5);
        sTRoot2.appendChlidView(paragraphView);
        paragraphView.setStartOffset(0);
        paragraphView.setEndOffset(paragraphForIndex.getEndOffset());
        int paraCount = sTRoot2.doc.getParaCount(areaEnd);
        long j2 = 0;
        int i7 = i5;
        IElement iElement = paragraphForIndex;
        boolean z = true;
        int i8 = 0;
        int i9 = 1;
        int i10 = Integer.MAX_VALUE;
        int i11 = 0;
        while (true) {
            if (i10 <= 0 || j2 >= areaEnd) {
                sTRoot = sTRoot2;
                i = i8;
                b = 1;
            } else {
                paragraphView.setLocation(i4, i7);
                AttrManage.instance().fillParaAttr(sTRoot2.container.getControl(), sTRoot2.paraAttr, iElement.getAttribute());
                if (sTRoot2.container.getEditType() == 2) {
                    if (z) {
                        sTRoot2.paraAttr.beforeSpace = i6;
                    }
                    if (paraCount == i9) {
                        sTRoot2.paraAttr.afterSpace = i6;
                    }
                }
                LayoutKit instance = LayoutKit.instance();
                IControl control = sTRoot2.container.getControl();
                IDocument iDocument = sTRoot2.doc;
                DocAttr docAttr2 = sTRoot2.docAttr;
                PageAttr pageAttr2 = sTRoot2.pageAttr;
                ParaAttr paraAttr2 = sTRoot2.paraAttr;
                int i12 = i8;
                IControl iControl = control;
                int i13 = i9;
                IDocument iDocument2 = iDocument;
                ParagraphView paragraphView2 = paragraphView;
                int i14 = i7;
                int i15 = paraCount;
                long j3 = areaEnd;
                int i16 = i13;
                b = 1;
                instance.layoutPara(iControl, iDocument2, docAttr2, pageAttr2, paraAttr2, paragraphView2, j2, i4, i14, max, Integer.MAX_VALUE, bitValue);
                int layoutSpan = paragraphView2.getLayoutSpan((byte) 1);
                i7 = i14 + layoutSpan;
                long endOffset = paragraphView2.getEndOffset((IDocument) null);
                i10 -= layoutSpan;
                i11 += layoutSpan;
                z = false;
                i = Math.max(i12, paragraphView2.getLayoutSpan((byte) 0));
                if (i10 <= 0 || endOffset >= j3) {
                    j = 0;
                    sTRoot = this;
                    i3 = i16;
                } else {
                    sTRoot = this;
                    i3 = i16 + 1;
                    j = 0;
                    IElement paragraphForIndex2 = sTRoot.doc.getParagraphForIndex(i16, 0);
                    if (paragraphForIndex2 == null) {
                        break;
                    }
                    ParagraphView paragraphView3 = (ParagraphView) ViewFactory.createView(sTRoot.container.getControl(), paragraphForIndex2, (IElement) null, 5);
                    paragraphView3.setStartOffset(endOffset);
                    sTRoot.appendChlidView(paragraphView3);
                    iElement = paragraphForIndex2;
                    paragraphView2 = paragraphView3;
                }
                j2 = endOffset;
                i9 = i3;
                i6 = 0;
                paragraphView = paragraphView2;
                long j4 = j;
                areaEnd = j3;
                i8 = i;
                sTRoot2 = sTRoot;
                paraCount = i15;
            }
        }
        int i17 = i;
        int i18 = i11;
        if (sTRoot.pageAttr.horizontalAlign == 0) {
            if (sTRoot.pageAttr.horizontalAlign == b) {
                i2 = i17;
            } else {
                i2 = (sTRoot.pageAttr.pageWidth - sTRoot.pageAttr.leftMargin) - sTRoot.pageAttr.rightMargin;
            }
            sTRoot.paraAlign(i2);
        }
        sTRoot.layoutPageAlign(i18, i17);
    }

    private void paraAlign(int i) {
        if (!this.isWrapLine) {
            for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
                this.paraAttr.horizontalAlignment = (byte) AttrManage.instance().getParaHorizontalAlign(childView.getElement().getAttribute());
                for (IView childView2 = childView.getChildView(); childView2 != null; childView2 = childView2.getNextView()) {
                    if (childView2.getType() == 6) {
                        ((LineView) childView2).layoutAlignment(this.docAttr, this.pageAttr, this.paraAttr, ((ParagraphView) childView).getBNView(), i, 0, false);
                    }
                }
            }
        }
    }

    private void layoutPageAlign(int i, int i2) {
        int i3;
        int i4 = (this.pageAttr.pageHeight - this.pageAttr.topMargin) - this.pageAttr.bottomMargin;
        byte b = this.pageAttr.verticalAlign;
        if (b != 1) {
            i3 = b != 2 ? 0 : i4 - i;
        } else {
            i3 = (i4 - i) / 2;
        }
        setY(i3);
        setTopIndent(i3);
        if (this.pageAttr.horizontalAlign == 1) {
            int i5 = (((this.pageAttr.pageWidth - this.pageAttr.leftMargin) - this.pageAttr.rightMargin) - i2) / 2;
            for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
                this.paraAttr.horizontalAlignment = (byte) AttrManage.instance().getParaHorizontalAlign(childView.getElement().getAttribute());
                IView childView2 = childView.getChildView();
                while (childView2 != null && childView2.getType() == 6) {
                    ((LineView) childView2).layoutAlignment(this.docAttr, this.pageAttr, this.paraAttr, ((ParagraphView) childView).getBNView(), i2, 0, false);
                    childView2.setX(childView2.getX() + i5);
                    childView2 = childView2.getNextView();
                }
            }
        }
    }

    public String getText() {
        String str = "";
        for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
            str = str + ((ParagraphView) childView).getText();
        }
        return str;
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        STRoot sTRoot = this;
        Canvas canvas2 = canvas;
        float f2 = f;
        new ArrayList(10);
        if (sTRoot.container != null) {
            int i3 = ((int) (((float) sTRoot.x) * f2)) + i;
            int i4 = ((int) (((float) sTRoot.y) * f2)) + i2;
            IView childView = getChildView();
            Rect clipBounds = canvas.getClipBounds();
            int i5 = 0;
            while (childView != null) {
                if (childView.intersection(clipBounds, i3, i4, f2)) {
                    IAnimation paragraphAnimation = sTRoot.container.getParagraphAnimation(i5);
                    if (paragraphAnimation != null) {
                        ShapeAnimation shapeAnimation = paragraphAnimation.getShapeAnimation();
                        int paragraphBegin = shapeAnimation.getParagraphBegin();
                        int paragraphEnd = shapeAnimation.getParagraphEnd();
                        if ((paragraphBegin == -2 && paragraphEnd == -2) || ((paragraphBegin == -1 && paragraphEnd == -1) || (paragraphBegin >= 0 && paragraphEnd >= 0 && i5 >= paragraphBegin && i5 <= paragraphEnd))) {
                            Rect viewRect = childView.getViewRect(i3, i4, f2);
                            int fps = (int) (((float) (paragraphAnimation.getFPS() * paragraphAnimation.getDuration())) / 1000.0f);
                            float progress = paragraphAnimation.getCurrentAnimationInfor().getProgress();
                            if (shapeAnimation.getAnimationType() == 0) {
                                int pow = (int) (((double) (i4 - (viewRect.bottom + 5))) + ((((double) ((viewRect.bottom + 5) * 2)) / Math.pow((double) fps, 2.0d)) * 0.5d * Math.pow((double) (((float) fps) * progress), 2.0d)));
                                if (childView.intersection(clipBounds, i3, pow, f2) && childView.intersection(clipBounds, i3, pow, f2)) {
                                    canvas2 = canvas;
                                    childView.draw(canvas2, i3, pow, f2);
                                }
                            } else if (shapeAnimation.getAnimationType() == 1) {
                                canvas.save();
                                canvas2.rotate((float) paragraphAnimation.getCurrentAnimationInfor().getAngle(), (float) viewRect.centerX(), (float) viewRect.centerY());
                                childView.draw(canvas2, i3, i4, f2);
                                canvas.restore();
                            } else if (shapeAnimation.getAnimationType() == 2) {
                                int pow2 = (int) (((double) (((clipBounds.bottom - viewRect.top) + 5) + i4)) - (((((double) (((clipBounds.bottom - viewRect.top) + 5) * 2)) / Math.pow((double) fps, 2.0d)) * 0.5d) * Math.pow((double) (((float) fps) * (1.0f - progress)), 2.0d)));
                                if (childView.intersection(clipBounds, i3, pow2, f2)) {
                                    canvas2 = canvas;
                                    childView.draw(canvas2, i3, pow2, f2);
                                }
                            } else {
                                childView.draw(canvas2, i3, i4, f2);
                            }
                            canvas2 = canvas;
                        }
                    } else {
                        childView.draw(canvas2, i3, i4, f2);
                    }
                }
                i5++;
                childView = childView.getNextView();
                sTRoot = this;
            }
            return;
        }
        super.draw(canvas, i, i2, f);
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView view = getView(j, 5, z);
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
        while (childView != null && (y < childView.getY() || y >= childView.getY() + childView.getLayoutSpan((byte) 1))) {
            childView = childView.getNextView();
        }
        if (childView != null) {
            return childView.viewToModel(x, y, z);
        }
        return -1;
    }

    public void setWrapLine(boolean z) {
        this.isWrapLine = z;
    }

    public void dispose() {
        super.dispose();
        this.doc = null;
        this.container = null;
        this.pageAttr = null;
        this.paraAttr = null;
        this.docAttr = null;
    }
}
