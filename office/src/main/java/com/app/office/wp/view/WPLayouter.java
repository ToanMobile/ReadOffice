package com.app.office.wp.view;

import com.app.office.constant.wp.AttrIDConstant;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.simpletext.view.IView;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.ViewKit;
import com.app.office.system.IControl;
import com.app.office.wp.model.WPDocument;
import java.util.ArrayList;
import java.util.List;

public class WPLayouter {
    private ParagraphView breakPara;
    private long currentLayoutOffset;
    private int currentPageNumber = 1;
    private IDocument doc;
    private DocAttr docAttr;
    private TitleView footer;
    private TitleView header;
    private TableLayoutKit hfTableLayout;
    private PageAttr pageAttr;
    private ParaAttr paraAttr;
    private PageRoot root;
    private IElement section;
    private List<LeafView> shapeViews = new ArrayList();
    private TableLayoutKit tableLayout;

    public WPLayouter(PageRoot pageRoot) {
        this.root = pageRoot;
        DocAttr docAttr2 = new DocAttr();
        this.docAttr = docAttr2;
        docAttr2.rootType = 0;
        this.pageAttr = new PageAttr();
        this.paraAttr = new ParaAttr();
        this.tableLayout = new TableLayoutKit();
        this.hfTableLayout = new TableLayoutKit();
    }

    public void doLayout() {
        this.tableLayout.clearBreakPages();
        IDocument document = this.root.getDocument();
        this.doc = document;
        this.section = document.getSection(0);
        AttrManage.instance().fillPageAttr(this.pageAttr, this.section.getAttribute());
        PageView pageView = (PageView) ViewFactory.createView(this.root.getControl(), this.section, (IElement) null, 4);
        this.root.appendChlidView(pageView);
        layoutPage(pageView);
        LayoutKit.instance().layoutAllPage(this.root, 1.0f);
    }

    public int layoutPage(PageView pageView) {
        PageView pageView2;
        short s;
        ParagraphView paragraphView;
        boolean z;
        IElement iElement;
        int i;
        ParagraphView paragraphView2;
        IElement iElement2;
        int i2;
        byte b;
        IElement iElement3;
        PageView pageView3 = pageView;
        int i3 = this.currentPageNumber;
        this.currentPageNumber = i3 + 1;
        pageView3.setPageNumber(i3);
        layoutHeaderAndFooter(pageView);
        pageView3.setSize(this.pageAttr.pageWidth, this.pageAttr.pageHeight);
        pageView3.setIndent(this.pageAttr.leftMargin, this.pageAttr.topMargin, this.pageAttr.rightMargin, this.pageAttr.bottomMargin);
        pageView3.setStartOffset(this.currentLayoutOffset);
        int i4 = this.pageAttr.leftMargin;
        int i5 = this.pageAttr.topMargin;
        int i6 = (this.pageAttr.pageWidth - this.pageAttr.leftMargin) - this.pageAttr.rightMargin;
        int i7 = (this.pageAttr.pageHeight - this.pageAttr.topMargin) - this.pageAttr.bottomMargin;
        int i8 = 1;
        int bitValue = ViewKit.instance().setBitValue(0, 0, true);
        long areaEnd = this.doc.getAreaEnd(0);
        ParagraphView paragraphView3 = this.breakPara;
        IElement element = paragraphView3 != null ? paragraphView3.getElement() : this.doc.getParagraph(this.currentLayoutOffset);
        ParagraphView paragraphView4 = this.breakPara;
        int i9 = 5;
        short s2 = 9;
        IElement iElement4 = null;
        if (paragraphView4 != null) {
            if (paragraphView4.getType() == 9) {
                pageView3.setHasBreakTable(true);
                ((TableView) this.breakPara).setBreakPages(true);
            }
        } else if (AttrManage.instance().hasAttribute(element.getAttribute(), AttrIDConstant.PARA_LEVEL_ID)) {
            element = ((WPDocument) this.doc).getParagraph0(this.currentLayoutOffset);
            paragraphView4 = (ParagraphView) ViewFactory.createView(this.root.getControl(), element, (IElement) null, 9);
        } else {
            paragraphView4 = (ParagraphView) ViewFactory.createView(this.root.getControl(), element, (IElement) null, 5);
        }
        pageView3.appendChlidView(paragraphView4);
        paragraphView4.setStartOffset(this.currentLayoutOffset);
        paragraphView4.setEndOffset(element.getEndOffset());
        int i10 = i5;
        int i11 = i7;
        int i12 = bitValue;
        int i13 = 0;
        boolean z2 = true;
        ParagraphView paragraphView5 = paragraphView4;
        IElement iElement5 = element;
        ParagraphView paragraphView6 = paragraphView5;
        while (true) {
            if (i11 <= 0 || this.currentLayoutOffset >= areaEnd || i13 == i8 || i13 == 3) {
                IElement iElement6 = iElement5;
                z = true;
                s = 9;
                paragraphView = paragraphView6;
                iElement = iElement6;
            } else {
                paragraphView6.setLocation(i4, i10);
                if (paragraphView6.getType() == s2) {
                    if (!(paragraphView6.getPreView() == null || paragraphView6.getPreView().getElement() == iElement5)) {
                        this.tableLayout.clearBreakPages();
                    }
                    paragraphView2 = paragraphView6;
                    iElement2 = iElement5;
                    i = i10;
                    TableView tableView = (TableView) paragraphView6;
                    i2 = i12;
                    b = 1;
                    i13 = this.tableLayout.layoutTable(this.root.getControl(), this.doc, this.root, this.docAttr, this.pageAttr, this.paraAttr, tableView, this.currentLayoutOffset, i4, i, i6, i11, i2, this.breakPara != null);
                } else {
                    paragraphView2 = paragraphView6;
                    iElement2 = iElement5;
                    i2 = i12;
                    i = i10;
                    b = 1;
                    this.tableLayout.clearBreakPages();
                    AttrManage.instance().fillParaAttr(this.root.getControl(), this.paraAttr, iElement2.getAttribute());
                    i13 = LayoutKit.instance().layoutPara(this.root.getControl(), this.doc, this.docAttr, this.pageAttr, this.paraAttr, paragraphView2, this.currentLayoutOffset, i4, i, i6, i11, i2);
                }
                paragraphView = paragraphView2;
                int layoutSpan = paragraphView.getLayoutSpan(b);
                if (z2 || paragraphView.getChildView() != null) {
                    pageView3 = pageView;
                    if (paragraphView.getType() != 9) {
                        this.root.getViewContainer().add(paragraphView);
                    }
                    collectShapeView(pageView3, paragraphView, false);
                    i10 = i + layoutSpan;
                    iElement4 = null;
                    long endOffset = paragraphView.getEndOffset((IDocument) null);
                    this.currentLayoutOffset = endOffset;
                    i11 -= layoutSpan;
                    if (i11 <= 0 || endOffset >= areaEnd || i13 == 1 || i13 == 3) {
                        i9 = 5;
                        iElement3 = iElement2;
                    } else {
                        iElement3 = this.doc.getParagraph(endOffset);
                        if (AttrManage.instance().hasAttribute(iElement3.getAttribute(), AttrIDConstant.PARA_LEVEL_ID)) {
                            if (iElement3 != paragraphView.getElement()) {
                                this.tableLayout.clearBreakPages();
                            }
                            iElement3 = ((WPDocument) this.doc).getParagraph0(this.currentLayoutOffset);
                            paragraphView = (ParagraphView) ViewFactory.createView(this.root.getControl(), iElement3, (IElement) null, 9);
                            i9 = 5;
                        } else {
                            i9 = 5;
                            paragraphView = (ParagraphView) ViewFactory.createView(this.root.getControl(), iElement3, (IElement) null, 5);
                        }
                        paragraphView.setStartOffset(this.currentLayoutOffset);
                        pageView3.appendChlidView(paragraphView);
                    }
                    int bitValue2 = ViewKit.instance().setBitValue(i2, 0, false);
                    this.breakPara = null;
                    paragraphView6 = paragraphView;
                    i12 = bitValue2;
                    s2 = 9;
                    i8 = 1;
                    z2 = false;
                    iElement5 = iElement3;
                } else {
                    if (this.breakPara == null) {
                        iElement = this.doc.getParagraph(this.currentLayoutOffset - 1);
                        pageView2 = pageView;
                    } else {
                        pageView2 = pageView;
                        iElement = iElement2;
                    }
                    z = true;
                    pageView2.deleteView(paragraphView, true);
                    s = 9;
                    iElement4 = null;
                    i9 = 5;
                }
            }
        }
        IElement iElement62 = iElement5;
        z = true;
        s = 9;
        paragraphView = paragraphView6;
        iElement = iElement62;
        if (paragraphView.getType() == s && this.tableLayout.isTableBreakPages()) {
            this.breakPara = (ParagraphView) ViewFactory.createView(this.root.getControl(), iElement, iElement4, s);
            pageView2.setHasBreakTable(z);
            ((TableView) paragraphView).setBreakPages(z);
        } else if (iElement != null && this.currentLayoutOffset < iElement.getEndOffset()) {
            this.breakPara = (ParagraphView) ViewFactory.createView(this.root.getControl(), iElement, iElement4, i9);
        }
        pageView2.setEndOffset(this.currentLayoutOffset);
        this.root.getViewContainer().sort();
        this.root.addPageView(pageView2);
        pageView2.setPageBackgroundColor(this.pageAttr.pageBRColor);
        pageView2.setPageBorder(this.pageAttr.pageBorder);
        return i13;
    }

    private void layoutHeaderAndFooter(PageView pageView) {
        if (this.header == null) {
            TitleView layoutHFParagraph = layoutHFParagraph(pageView, true);
            this.header = layoutHFParagraph;
            if (layoutHFParagraph != null) {
                int layoutSpan = layoutHFParagraph.getLayoutSpan((byte) 1);
                if (this.pageAttr.headerMargin + layoutSpan > this.pageAttr.topMargin) {
                    PageAttr pageAttr2 = this.pageAttr;
                    pageAttr2.topMargin = pageAttr2.headerMargin + layoutSpan;
                }
                this.header.setParentView(pageView);
            }
        } else {
            for (LeafView next : this.shapeViews) {
                if (WPViewKit.instance().getArea(next.getStartOffset((IDocument) null)) == 1152921504606846976L) {
                    pageView.addShapeView(next);
                }
            }
        }
        pageView.setHeader(this.header);
        if (this.footer == null) {
            TitleView layoutHFParagraph2 = layoutHFParagraph(pageView, false);
            this.footer = layoutHFParagraph2;
            if (layoutHFParagraph2 != null) {
                if (layoutHFParagraph2.getY() < this.pageAttr.pageHeight - this.pageAttr.bottomMargin) {
                    PageAttr pageAttr3 = this.pageAttr;
                    pageAttr3.bottomMargin = pageAttr3.pageHeight - this.footer.getY();
                }
                this.footer.setParentView(pageView);
            }
        } else {
            for (LeafView next2 : this.shapeViews) {
                if (WPViewKit.instance().getArea(next2.getStartOffset((IDocument) null)) == 2305843009213693952L) {
                    pageView.addShapeView(next2);
                }
            }
        }
        pageView.setFooter(this.footer);
    }

    private TitleView layoutHFParagraph(PageView pageView, boolean z) {
        ParagraphView paragraphView;
        float f;
        int i;
        ParagraphView paragraphView2;
        int i2;
        long j;
        int i3;
        short s;
        int i4;
        ParagraphView paragraphView3;
        long j2 = z ? 1152921504606846976L : 2305843009213693952L;
        IElement hFElement = this.doc.getHFElement(j2, (byte) 1);
        if (hFElement == null) {
            return null;
        }
        float f2 = this.pageAttr.pageLinePitch;
        this.pageAttr.pageLinePitch = -1.0f;
        TitleView titleView = (TitleView) ViewFactory.createView(this.root.getControl(), hFElement, (IElement) null, 12);
        titleView.setPageRoot(this.root);
        titleView.setLocation(this.pageAttr.leftMargin, this.pageAttr.headerMargin);
        long endOffset = hFElement.getEndOffset();
        int i5 = (this.pageAttr.pageWidth - this.pageAttr.leftMargin) - this.pageAttr.rightMargin;
        int i6 = (((this.pageAttr.pageHeight - this.pageAttr.topMargin) - this.pageAttr.bottomMargin) - 100) / 2;
        int bitValue = ViewKit.instance().setBitValue(0, 0, true);
        IElement paragraph = this.doc.getParagraph(j2);
        short s2 = 9;
        if (AttrManage.instance().hasAttribute(paragraph.getAttribute(), AttrIDConstant.PARA_LEVEL_ID)) {
            paragraph = ((WPDocument) this.doc).getParagraph0(j2);
            paragraphView = (ParagraphView) ViewFactory.createView(this.root.getControl(), paragraph, (IElement) null, 9);
        } else {
            paragraphView = (ParagraphView) ViewFactory.createView(this.root.getControl(), paragraph, (IElement) null, 5);
        }
        titleView.appendChlidView(paragraphView);
        paragraphView.setStartOffset(j2);
        paragraphView.setEndOffset(paragraph.getEndOffset());
        long j3 = j2;
        int i7 = bitValue;
        IElement iElement = paragraph;
        ParagraphView paragraphView4 = paragraphView;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        boolean z2 = true;
        while (true) {
            if (i6 > 0 && j3 < endOffset && i8 != 1) {
                paragraphView4.setLocation(0, i10);
                if (paragraphView4.getType() == s2) {
                    TableLayoutKit tableLayoutKit = this.hfTableLayout;
                    IControl control = this.root.getControl();
                    IDocument iDocument = this.doc;
                    PageRoot pageRoot = this.root;
                    f = f2;
                    DocAttr docAttr2 = this.docAttr;
                    j = endOffset;
                    PageAttr pageAttr2 = this.pageAttr;
                    ParaAttr paraAttr2 = this.paraAttr;
                    TableView tableView = (TableView) paragraphView4;
                    int i11 = i9;
                    boolean z3 = this.breakPara != null;
                    i2 = i11;
                    IDocument iDocument2 = iDocument;
                    i4 = i10;
                    PageRoot pageRoot2 = pageRoot;
                    paragraphView2 = paragraphView4;
                    s = AttrIDConstant.PARA_LEVEL_ID;
                    i3 = tableLayoutKit.layoutTable(control, iDocument2, pageRoot2, docAttr2, pageAttr2, paraAttr2, tableView, j3, 0, i4, i5, i6, i7, z3);
                } else {
                    f = f2;
                    j = endOffset;
                    i2 = i9;
                    i4 = i10;
                    paragraphView2 = paragraphView4;
                    s = AttrIDConstant.PARA_LEVEL_ID;
                    this.hfTableLayout.clearBreakPages();
                    AttrManage.instance().fillParaAttr(this.root.getControl(), this.paraAttr, iElement.getAttribute());
                    i3 = LayoutKit.instance().layoutPara(this.root.getControl(), this.doc, this.docAttr, this.pageAttr, this.paraAttr, paragraphView2, j3, 0, i4, i5, i6, i7);
                }
                i8 = i3;
                ParagraphView paragraphView5 = paragraphView2;
                int layoutSpan = paragraphView5.getLayoutSpan((byte) 1);
                if (!z2 && paragraphView5.getChildView() == null) {
                    titleView.deleteView(paragraphView5, true);
                    i = i2;
                    break;
                }
                int i12 = i4 + layoutSpan;
                int i13 = i2 + layoutSpan;
                int i14 = i12;
                long endOffset2 = paragraphView5.getEndOffset((IDocument) null);
                i6 -= layoutSpan;
                collectShapeView(pageView, paragraphView5, true);
                if (i6 > 0 && endOffset2 < j && i8 != 1) {
                    iElement = this.doc.getParagraph(endOffset2);
                    if (AttrManage.instance().hasAttribute(iElement.getAttribute(), s)) {
                        iElement = ((WPDocument) this.doc).getParagraph0(endOffset2);
                        paragraphView3 = (ParagraphView) ViewFactory.createView(this.root.getControl(), iElement, (IElement) null, 9);
                    } else {
                        paragraphView3 = (ParagraphView) ViewFactory.createView(this.root.getControl(), iElement, (IElement) null, 5);
                    }
                    paragraphView3.setStartOffset(endOffset2);
                    titleView.appendChlidView(paragraphView3);
                    paragraphView5 = paragraphView3;
                }
                i7 = ViewKit.instance().setBitValue(i7, 0, false);
                j3 = endOffset2;
                f2 = f;
                endOffset = j;
                z2 = false;
                i10 = i14;
                paragraphView4 = paragraphView5;
                i9 = i13;
                s2 = 9;
            } else {
                f = f2;
                i = i9;
            }
        }
        f = f2;
        i = i9;
        titleView.setSize(i5, i);
        if (!z) {
            titleView.setY((this.pageAttr.pageHeight - i) - this.pageAttr.footerMargin);
        }
        this.pageAttr.pageLinePitch = f;
        return titleView;
    }

    public void backLayout() {
        PageView pageView = (PageView) ViewFactory.createView(this.root.getControl(), this.section, (IElement) null, 4);
        this.root.appendChlidView(pageView);
        layoutPage(pageView);
    }

    public long getCurrentLayoutOffset() {
        return this.currentLayoutOffset;
    }

    public void setCurrentLayoutOffset(long j) {
        this.currentLayoutOffset = j;
    }

    public boolean isLayoutFinish() {
        return this.currentLayoutOffset >= this.doc.getAreaEnd(0) && this.breakPara == null;
    }

    private void collectShapeView(PageView pageView, ParagraphView paragraphView, boolean z) {
        if (paragraphView.getType() == 5) {
            collectShapeViewForPara(pageView, paragraphView, z);
        } else if (paragraphView.getType() == 9) {
            for (IView childView = paragraphView.getChildView(); childView != null; childView = childView.getNextView()) {
                for (IView childView2 = childView.getChildView(); childView2 != null; childView2 = childView2.getNextView()) {
                    for (IView childView3 = childView2.getChildView(); childView3 != null; childView3 = childView3.getNextView()) {
                        collectShapeViewForPara(pageView, paragraphView, z);
                    }
                }
            }
        }
    }

    private void collectShapeViewForPara(PageView pageView, ParagraphView paragraphView, boolean z) {
        for (IView childView = paragraphView.getChildView(); childView != null; childView = childView.getNextView()) {
            for (IView childView2 = childView.getChildView(); childView2 != null; childView2 = childView2.getNextView()) {
                if (childView2.getType() == 13) {
                    ShapeView shapeView = (ShapeView) childView2;
                    if (!shapeView.isInline()) {
                        pageView.addShapeView(shapeView);
                        if (z) {
                            this.shapeViews.add(shapeView);
                        }
                    }
                } else if (childView2.getType() == 8) {
                    ObjView objView = (ObjView) childView2;
                    if (!objView.isInline()) {
                        pageView.addShapeView(objView);
                        if (z) {
                            this.shapeViews.add(objView);
                        }
                    }
                }
            }
        }
    }

    public void dispose() {
        this.docAttr.dispose();
        this.docAttr = null;
        this.pageAttr.dispose();
        this.pageAttr = null;
        this.paraAttr.dispose();
        this.paraAttr = null;
        this.root = null;
        this.doc = null;
        this.breakPara = null;
        this.header = null;
        this.footer = null;
        this.tableLayout = null;
        this.hfTableLayout = null;
        this.shapeViews.clear();
    }
}
