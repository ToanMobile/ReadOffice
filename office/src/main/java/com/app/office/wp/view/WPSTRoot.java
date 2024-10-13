package com.app.office.wp.view;

import android.graphics.Canvas;
import com.app.office.constant.wp.AttrIDConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.simpletext.view.IRoot;
import com.app.office.simpletext.view.IView;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.ViewContainer;
import com.app.office.simpletext.view.ViewKit;
import com.app.office.system.IControl;
import com.app.office.wp.model.WPDocument;

public class WPSTRoot extends AbstractView implements IRoot {
    private static TableLayoutKit tableLayout = new TableLayoutKit();
    private IWord container;
    private IDocument doc;
    private DocAttr docAttr = new DocAttr();
    private int elementIndex;
    private boolean isWrapLine;
    private int maxParaWidth;
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

    public WPSTRoot(IWord iWord, IDocument iDocument, int i) {
        this.doc = iDocument;
        this.container = iWord;
        this.elementIndex = i;
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
        ParagraphView paragraphView;
        int i;
        WPSTRoot wPSTRoot;
        int i2;
        long j;
        int i3;
        ParagraphView paragraphView2;
        byte b;
        ParagraphView paragraphView3;
        WPSTRoot wPSTRoot2 = this;
        IElement textboxSectionElementForIndex = ((WPDocument) wPSTRoot2.doc).getTextboxSectionElementForIndex(wPSTRoot2.elementIndex);
        AttrManage.instance().fillPageAttr(wPSTRoot2.pageAttr, textboxSectionElementForIndex.getAttribute());
        IAttributeSet attribute = wPSTRoot2.doc.getSection(0).getAttribute();
        int pageWidth = (int) (((float) ((AttrManage.instance().getPageWidth(attribute) - AttrManage.instance().getPageMarginLeft(attribute)) - AttrManage.instance().getPageMarginRight(attribute))) * 0.06666667f);
        tableLayout.clearBreakPages();
        int i4 = wPSTRoot2.pageAttr.leftMargin;
        int i5 = wPSTRoot2.pageAttr.topMargin;
        wPSTRoot2.setTopIndent(wPSTRoot2.pageAttr.topMargin);
        wPSTRoot2.setLeftIndent(wPSTRoot2.pageAttr.leftMargin);
        int max = Math.max(5, ((wPSTRoot2.isWrapLine ? wPSTRoot2.pageAttr.pageWidth : pageWidth) - wPSTRoot2.pageAttr.leftMargin) - wPSTRoot2.pageAttr.rightMargin);
        int bitValue = ViewKit.instance().setBitValue(ViewKit.instance().setBitValue(0, 0, true), 3, !wPSTRoot2.isWrapLine || wPSTRoot2.pageAttr.horizontalAlign == 1);
        long endOffset = textboxSectionElementForIndex.getEndOffset();
        long startOffset = textboxSectionElementForIndex.getStartOffset();
        if (wPSTRoot2.doc.getParaCount(endOffset) != 0) {
            IElement paragraph = wPSTRoot2.doc.getParagraph(startOffset);
            short s = 9;
            if (AttrManage.instance().hasAttribute(paragraph.getAttribute(), AttrIDConstant.PARA_LEVEL_ID)) {
                paragraph = ((WPDocument) wPSTRoot2.doc).getParagraph0(startOffset);
                paragraphView = (ParagraphView) ViewFactory.createView(getControl(), paragraph, (IElement) null, 9);
            } else {
                paragraphView = (ParagraphView) ViewFactory.createView(getControl(), paragraph, (IElement) null, 5);
            }
            wPSTRoot2.appendChlidView(paragraphView);
            paragraphView.setStartOffset(startOffset);
            paragraphView.setEndOffset(paragraph.getEndOffset());
            IElement iElement = paragraph;
            ParagraphView paragraphView4 = paragraphView;
            long j2 = startOffset;
            int i6 = 0;
            int i7 = Integer.MAX_VALUE;
            int i8 = 0;
            int i9 = i5;
            while (true) {
                if (i7 <= 0 || j2 >= endOffset || i6 == 1) {
                    i = pageWidth;
                    wPSTRoot = wPSTRoot2;
                } else {
                    paragraphView4.setLocation(i4, i9);
                    if (paragraphView4.getType() == s) {
                        i3 = i9;
                        j = endOffset;
                        i2 = i4;
                        i = pageWidth;
                        i6 = tableLayout.layoutTable(getControl(), wPSTRoot2.doc, this, wPSTRoot2.docAttr, wPSTRoot2.pageAttr, wPSTRoot2.paraAttr, (TableView) paragraphView4, j2, i4, i3, max, i7, bitValue, false);
                        b = 1;
                        wPSTRoot = this;
                        paragraphView2 = paragraphView4;
                    } else {
                        ParagraphView paragraphView5 = paragraphView4;
                        i3 = i9;
                        j = endOffset;
                        i2 = i4;
                        i = pageWidth;
                        tableLayout.clearBreakPages();
                        wPSTRoot = this;
                        AttrManage.instance().fillParaAttr(getControl(), wPSTRoot.paraAttr, iElement.getAttribute());
                        i6 = LayoutKit.instance().layoutPara(getControl(), wPSTRoot.doc, wPSTRoot.docAttr, wPSTRoot.pageAttr, wPSTRoot.paraAttr, paragraphView5, j2, i2, i3, max, i7, bitValue);
                        paragraphView2 = paragraphView5;
                        b = 1;
                    }
                    int layoutSpan = paragraphView2.getLayoutSpan(b);
                    int i10 = i3 + layoutSpan;
                    long endOffset2 = paragraphView2.getEndOffset((IDocument) null);
                    i7 -= layoutSpan;
                    i8 += layoutSpan;
                    wPSTRoot.maxParaWidth = Math.max(wPSTRoot.maxParaWidth, paragraphView2.getLayoutSpan((byte) 0));
                    if (i7 > 0 && endOffset2 < j) {
                        IElement paragraph2 = wPSTRoot.doc.getParagraph(endOffset2);
                        if (paragraph2 == null) {
                            break;
                        }
                        if (AttrManage.instance().hasAttribute(paragraph2.getAttribute(), AttrIDConstant.PARA_LEVEL_ID)) {
                            paragraph2 = ((WPDocument) wPSTRoot.doc).getParagraph0(endOffset2);
                            paragraphView3 = (ParagraphView) ViewFactory.createView(getControl(), paragraph2, (IElement) null, 9);
                        } else {
                            paragraphView3 = (ParagraphView) ViewFactory.createView(getControl(), paragraph2, (IElement) null, 5);
                        }
                        paragraphView2.setStartOffset(endOffset2);
                        wPSTRoot.appendChlidView(paragraphView2);
                        iElement = paragraph2;
                    }
                    wPSTRoot2 = wPSTRoot;
                    j2 = endOffset2;
                    endOffset = j;
                    i4 = i2;
                    pageWidth = i;
                    s = 9;
                    ParagraphView paragraphView6 = paragraphView2;
                    i9 = i10;
                    paragraphView4 = paragraphView6;
                }
            }
            i = pageWidth;
            wPSTRoot = wPSTRoot2;
            int i11 = i8;
            if (!wPSTRoot.isWrapLine) {
                wPSTRoot.paraAlign(wPSTRoot.maxParaWidth);
            }
            wPSTRoot.layoutPageAlign(i11, wPSTRoot.maxParaWidth);
            if (!wPSTRoot.isWrapLine) {
                wPSTRoot.pageAttr.pageWidth = i;
            }
        }
    }

    private void paraAlign(int i) {
        for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
            this.paraAttr.horizontalAlignment = (byte) AttrManage.instance().getParaHorizontalAlign(childView.getElement().getAttribute());
            for (IView childView2 = childView.getChildView(); childView2 != null; childView2 = childView2.getNextView()) {
                if (childView2.getType() == 6) {
                    ((LineView) childView2).layoutAlignment(this.docAttr, this.pageAttr, this.paraAttr, ((ParagraphView) childView).getBNView(), i, 0, false);
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
        if (i3 >= 0) {
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
    }

    public String getText() {
        String str = "";
        for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
            str = str + ((ParagraphView) childView).getText();
        }
        return str;
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        canvas.save();
        float f2 = (float) i;
        float f3 = (float) i2;
        canvas.clipRect(f2, f3, (((float) this.pageAttr.pageWidth) * f) + f2, (((float) (this.pageAttr.pageHeight - this.pageAttr.bottomMargin)) * f) + f3);
        super.draw(canvas, i, i2, f);
        canvas.restore();
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

    public int getAdjustTextboxWidth() {
        return this.maxParaWidth + this.pageAttr.leftMargin + this.pageAttr.rightMargin;
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
