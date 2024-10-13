package com.app.office.wp.view;

import android.graphics.Canvas;
import com.app.office.constant.EventConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.IRoot;
import com.app.office.simpletext.view.IView;
import com.app.office.simpletext.view.ViewContainer;
import com.app.office.system.IControl;
import com.app.office.wp.control.Word;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageRoot extends AbstractView implements IRoot {
    private boolean canBackLayout = true;
    private LayoutThread layoutThread = new LayoutThread(this);
    private List<PageView> pages = new ArrayList();
    private int paraCount;
    private ViewContainer viewContainer = new ViewContainer();
    private Word word;
    private WPLayouter wpLayouter = new WPLayouter(this);

    public short getType() {
        return 0;
    }

    public PageRoot(Word word2) {
        this.word = word2;
    }

    public int doLayout(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            setParaCount(getDocument().getParaCount(0));
            this.wpLayouter.doLayout();
            if (this.wpLayouter.isLayoutFinish() || this.word.getControl().getMainFrame().isThumbnail()) {
                this.word.getControl().actionEvent(EventConstant.WP_LAYOUT_COMPLETED, true);
                this.word.getControl().actionEvent(22, true);
                return 0;
            }
            this.layoutThread.start();
            this.word.getControl().actionEvent(26, true);
            return 0;
        } catch (Exception e) {
            this.word.getControl().getSysKit().getErrorKit().writerLog(e);
            return 0;
        }
    }

    public synchronized void draw(Canvas canvas, int i, int i2, float f) {
        super.draw(canvas, i, i2, f);
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView paragraph = this.viewContainer.getParagraph(j, z);
        if (paragraph != null) {
            paragraph.modelToView(j, rectangle, z);
            IView parentView = paragraph.getParentView();
            while (parentView != null && parentView.getType() != 0) {
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
        if (childView != null && y > childView.getY()) {
            while (childView != null && (y < childView.getY() || y > childView.getY() + childView.getHeight() + 2)) {
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
        return this.canBackLayout && !this.wpLayouter.isLayoutFinish();
    }

    public synchronized void backLayout() {
        this.wpLayouter.backLayout();
        this.word.postInvalidate();
        if (this.wpLayouter.isLayoutFinish()) {
            this.word.getControl().actionEvent(22, true);
            this.word.getControl().actionEvent(EventConstant.WP_LAYOUT_COMPLETED, true);
        }
        this.word.getControl().actionEvent(20, (Object) null);
        LayoutKit.instance().layoutAllPage(this, this.word.getZoom());
        this.word.layoutPrintMode();
    }

    public int getParaCount() {
        return this.paraCount;
    }

    public void setParaCount(int i) {
        this.paraCount = i;
    }

    public int getPageCount() {
        return getChildCount();
    }

    public int getChildCount() {
        List<PageView> list = this.pages;
        if (list != null) {
            return list.size();
        }
        return 1;
    }

    public ViewContainer getViewContainer() {
        return this.viewContainer;
    }

    public void addPageView(PageView pageView) {
        this.pages.add(pageView);
    }

    public PageView getPageView(int i) {
        if (i < 0 || i >= this.pages.size()) {
            return null;
        }
        return this.pages.get(i);
    }

    public boolean checkUpdateHeaderFooterFieldText() {
        Iterator<PageView> it = this.pages.iterator();
        while (true) {
            boolean z = false;
            while (true) {
                if (!it.hasNext()) {
                    return z;
                }
                PageView next = it.next();
                if (z || next.checkUpdateHeaderFooterFieldText(this.pages.size())) {
                    z = true;
                }
            }
        }
    }

    public synchronized void dispose() {
        super.dispose();
        this.canBackLayout = false;
        LayoutThread layoutThread2 = this.layoutThread;
        if (layoutThread2 != null) {
            layoutThread2.dispose();
            this.layoutThread = null;
        }
        WPLayouter wPLayouter = this.wpLayouter;
        if (wPLayouter != null) {
            wPLayouter.dispose();
            this.wpLayouter = null;
        }
        ViewContainer viewContainer2 = this.viewContainer;
        if (viewContainer2 != null) {
            viewContainer2.dispose();
            this.viewContainer = null;
        }
        List<PageView> list = this.pages;
        if (list != null) {
            list.clear();
            this.pages = null;
        }
        this.word = null;
    }
}
