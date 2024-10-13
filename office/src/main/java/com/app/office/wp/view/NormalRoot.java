package com.app.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.app.office.constant.EventConstant;
import com.app.office.constant.wp.AttrIDConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.AttrManage;
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
import com.app.office.wp.control.Word;
import com.app.office.wp.model.WPDocument;
import java.lang.Thread;

public class NormalRoot extends AbstractView implements IRoot {
    private static final int LAYOUT_PARA = 20;
    private boolean canBackLayout;
    private long currentLayoutOffset;
    private IDocument doc;
    private DocAttr docAttr;
    private LayoutThread layoutThread;
    private int maxParaWidth;
    private PageAttr pageAttr;
    private ParaAttr paraAttr;
    private ParagraphView prePara;
    private boolean relayout = true;
    private TableLayoutKit tableLayout;
    private ViewContainer viewContainer;
    /* access modifiers changed from: private */
    public Word word;

    public short getType() {
        return 1;
    }

    public NormalRoot(Word word2) {
        this.word = word2;
        this.doc = word2.getDocument();
        this.layoutThread = new LayoutThread(this);
        this.canBackLayout = true;
        DocAttr docAttr2 = new DocAttr();
        this.docAttr = docAttr2;
        docAttr2.rootType = 1;
        this.pageAttr = new PageAttr();
        this.paraAttr = new ParaAttr();
        this.viewContainer = new ViewContainer();
        this.tableLayout = new TableLayoutKit();
    }

    public synchronized int layoutAll() {
        super.dispose();
        this.tableLayout.clearBreakPages();
        this.word.getControl().getSysKit().getListManage().resetForNormalView();
        this.viewContainer.clear();
        this.maxParaWidth = 0;
        this.prePara = null;
        this.currentLayoutOffset = 0;
        layoutPara();
        if (this.currentLayoutOffset < this.doc.getAreaEnd(0)) {
            this.canBackLayout = true;
            if (this.layoutThread.getState() == Thread.State.NEW) {
                this.layoutThread.start();
            }
            this.word.getControl().actionEvent(26, true);
        }
        layoutRoot();
        if (this.word.isExportImageAfterZoom() && (((float) getHeight()) * this.word.getZoom() >= ((float) (this.word.getScrollY() + this.word.getHeight())) || this.currentLayoutOffset >= this.doc.getAreaEnd(0))) {
            this.word.setExportImageAfterZoom(false);
            this.word.getControl().actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
        }
        return 0;
    }

    public int doLayout(int i, int i2, int i3, int i4, int i5, int i6) {
        IDocument document = getDocument();
        this.viewContainer.clear();
        layoutPara();
        if (this.currentLayoutOffset < document.getAreaEnd(0)) {
            if (this.layoutThread.getState() == Thread.State.NEW) {
                this.layoutThread.start();
            }
            this.word.getControl().actionEvent(26, true);
        }
        layoutRoot();
        return 0;
    }

    private int layoutPara() {
        int i;
        ParagraphView paragraphView;
        IDocument iDocument;
        int i2;
        int i3;
        byte b;
        NormalRoot normalRoot;
        ParagraphView paragraphView2;
        NormalRoot normalRoot2 = this;
        normalRoot2.relayout = true;
        ParagraphView paragraphView3 = normalRoot2.prePara;
        int i4 = 5;
        int y = paragraphView3 == null ? 5 : paragraphView3.getY() + normalRoot2.prePara.getHeight();
        if (normalRoot2.word.getControl().getMainFrame().isZoomAfterLayoutForWord()) {
            i = (int) (((float) normalRoot2.word.getResources().getDisplayMetrics().widthPixels) / normalRoot2.word.getZoom());
        } else {
            i = normalRoot2.word.getResources().getDisplayMetrics().widthPixels;
        }
        int i5 = i - 10;
        int bitValue = ViewKit.instance().setBitValue(0, 0, true);
        long areaEnd = normalRoot2.doc.getAreaEnd(0);
        IDocument document = normalRoot2.word.getDocument();
        int i6 = y;
        int i7 = 0;
        int i8 = Integer.MAX_VALUE;
        while (i7 < 20) {
            long j = normalRoot2.currentLayoutOffset;
            if (j >= areaEnd || !normalRoot2.relayout) {
                break;
            }
            IElement paragraph = document.getParagraph(j);
            if (AttrManage.instance().hasAttribute(paragraph.getAttribute(), AttrIDConstant.PARA_LEVEL_ID)) {
                paragraph = ((WPDocument) document).getParagraph0(normalRoot2.currentLayoutOffset);
                paragraphView = (ParagraphView) ViewFactory.createView(normalRoot2.word.getControl(), paragraph, (IElement) null, 9);
                ParagraphView paragraphView4 = normalRoot2.prePara;
                if (!(paragraphView4 == null || paragraph == paragraphView4.getElement())) {
                    normalRoot2.tableLayout.clearBreakPages();
                }
            } else {
                paragraphView = (ParagraphView) ViewFactory.createView(normalRoot2.word.getControl(), paragraph, (IElement) null, i4);
            }
            ParagraphView paragraphView5 = paragraphView;
            paragraphView5.setParentView(normalRoot2);
            paragraphView5.setStartOffset(paragraph.getStartOffset());
            paragraphView5.setEndOffset(paragraph.getEndOffset());
            ParagraphView paragraphView6 = normalRoot2.prePara;
            if (paragraphView6 == null) {
                normalRoot2.setChildView(paragraphView5);
            } else {
                paragraphView6.setNextView(paragraphView5);
                paragraphView5.setPreView(normalRoot2.prePara);
            }
            paragraphView5.setLocation(i4, i6);
            if (paragraphView5.getType() == 9) {
                i3 = i7;
                i2 = i6;
                iDocument = document;
                normalRoot2.tableLayout.layoutTable(normalRoot2.word.getControl(), document, this, normalRoot2.docAttr, normalRoot2.pageAttr, normalRoot2.paraAttr, (TableView) paragraphView5, normalRoot2.currentLayoutOffset, 5, i2, i5, i8, bitValue, false);
                b = 1;
                normalRoot = this;
                paragraphView2 = paragraphView5;
            } else {
                ParagraphView paragraphView7 = paragraphView5;
                i3 = i7;
                i2 = i6;
                iDocument = document;
                normalRoot = normalRoot2;
                normalRoot.tableLayout.clearBreakPages();
                AttrManage.instance().fillParaAttr(normalRoot.word.getControl(), normalRoot.paraAttr, paragraph.getAttribute());
                normalRoot.filteParaAttr(normalRoot.paraAttr);
                LayoutKit.instance().layoutPara(normalRoot.word.getControl(), iDocument, normalRoot.docAttr, normalRoot.pageAttr, normalRoot.paraAttr, paragraphView7, normalRoot.currentLayoutOffset, 5, i2, i5, i8, bitValue);
                paragraphView2 = paragraphView7;
                b = 1;
            }
            int layoutSpan = paragraphView2.getLayoutSpan(b);
            normalRoot.maxParaWidth = Math.max(paragraphView2.getLayoutSpan((byte) 0) + 5, normalRoot.maxParaWidth);
            i6 = i2 + layoutSpan;
            i8 -= layoutSpan;
            normalRoot.currentLayoutOffset = paragraphView2.getEndOffset((IDocument) null);
            i7 = i3 + 1;
            normalRoot.prePara = paragraphView2;
            normalRoot.viewContainer.add(paragraphView2);
            normalRoot2 = normalRoot;
            document = iDocument;
            i4 = 5;
        }
        NormalRoot normalRoot3 = normalRoot2;
        return 0;
    }

    private void filteParaAttr(ParaAttr paraAttr2) {
        int i = 0;
        paraAttr2.rightIndent = paraAttr2.rightIndent < 0 ? 0 : paraAttr2.rightIndent;
        if (paraAttr2.leftIndent >= 0) {
            i = paraAttr2.leftIndent;
        }
        paraAttr2.leftIndent = i;
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        canvas.drawColor(-1);
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        Rect clipBounds = canvas.getClipBounds();
        boolean z = false;
        for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
            if (childView.intersection(clipBounds, i3, i4, f)) {
                childView.draw(canvas, i3, i4, f);
                z = true;
            } else if (z) {
                return;
            }
        }
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView paragraph = this.viewContainer.getParagraph(j, z);
        if (paragraph != null) {
            paragraph.modelToView(j, rectangle, z);
            IView parentView = paragraph.getParentView();
            while (parentView != null && parentView.getType() != 1) {
                rectangle.x += parentView.getX();
                rectangle.y += parentView.getY();
                parentView = parentView.getParentView();
            }
        }
        rectangle.x += getX();
        rectangle.y += getY();
        return rectangle;
    }

    public long viewToModel(int i, int i2, boolean z) {
        int x = i - getX();
        int y = i2 - getY();
        IView childView = getChildView();
        if (childView == null) {
            return -1;
        }
        if (y > childView.getY()) {
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

    public IDocument getDocument() {
        return this.word.getDocument();
    }

    public IWord getContainer() {
        return this.word;
    }

    public IControl getControl() {
        return this.word.getControl();
    }

    public boolean canBackLayout() {
        return this.canBackLayout && this.currentLayoutOffset < this.doc.getAreaEnd(0);
    }

    public synchronized void backLayout() {
        layoutPara();
        layoutRoot();
        if (this.currentLayoutOffset >= this.doc.getAreaEnd(0)) {
            this.word.getControl().actionEvent(22, true);
            this.word.getControl().actionEvent(26, false);
            Rectangle visibleRect = this.word.getVisibleRect();
            final int i = visibleRect.x;
            final int i2 = visibleRect.y;
            int width = (int) (((float) getWidth()) * this.word.getZoom());
            int height = (int) (((float) getHeight()) * this.word.getZoom());
            if (visibleRect.x + visibleRect.width > width) {
                i = width - visibleRect.width;
            }
            if (visibleRect.y + visibleRect.height > height) {
                i2 = height - visibleRect.height;
            }
            if (!(i == visibleRect.x && i2 == visibleRect.y)) {
                this.word.post(new Runnable() {
                    public void run() {
                        NormalRoot.this.word.scrollTo(Math.max(0, i), Math.max(0, i2));
                    }
                });
            }
        }
        this.word.postInvalidate();
        if (this.word.isExportImageAfterZoom() && (((float) getHeight()) * this.word.getZoom() >= ((float) (this.word.getScrollY() + this.word.getHeight())) || this.currentLayoutOffset >= this.doc.getAreaEnd(0))) {
            this.word.setExportImageAfterZoom(false);
            this.word.getControl().actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
        }
    }

    public void layoutRoot() {
        if (this.prePara != null) {
            setSize(Math.max(this.word.getWidth(), this.maxParaWidth), this.prePara.getY() + this.prePara.getHeight());
        }
    }

    public void stopBackLayout() {
        this.canBackLayout = false;
        this.relayout = false;
    }

    public ViewContainer getViewContainer() {
        return this.viewContainer;
    }

    public synchronized void dispose() {
        super.dispose();
        this.canBackLayout = false;
        this.layoutThread.dispose();
        this.layoutThread = null;
        this.word = null;
        this.docAttr.dispose();
        this.docAttr = null;
        this.pageAttr.dispose();
        this.pageAttr = null;
        this.paraAttr.dispose();
        this.paraAttr = null;
        this.prePara = null;
        this.doc = null;
        this.tableLayout = null;
    }
}
