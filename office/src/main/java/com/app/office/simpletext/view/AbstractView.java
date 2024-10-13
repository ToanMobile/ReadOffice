package com.app.office.simpletext.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.system.IControl;
import java.util.List;

public abstract class AbstractView implements IView {
    protected int bottomIndent;
    protected IView child;
    protected IElement elem;
    protected long end;
    protected int height;
    protected int leftIndent;
    protected IView nextView;
    protected IView parent;
    protected IView preView;
    protected int rightIndent;
    protected long start;
    protected int topIndent;
    protected int width;
    protected int x;
    protected int y;

    public int doLayout(int i, int i2, int i3, int i4, long j, int i5) {
        return 0;
    }

    public void free() {
    }

    public long getNextForCoordinate(long j, int i, int i2, int i3) {
        return 0;
    }

    public long getNextForOffset(long j, int i, int i2, int i3) {
        return 0;
    }

    public short getType() {
        return -1;
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        return null;
    }

    public long viewToModel(int i, int i2, boolean z) {
        return 0;
    }

    public IElement getElement() {
        return this.elem;
    }

    public void setElement(IElement iElement) {
        this.elem = iElement;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int i) {
        this.x = i;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int i) {
        this.y = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public long getStartOffset(IDocument iDocument) {
        return this.start;
    }

    public void setStartOffset(long j) {
        this.start = j;
    }

    public long getElemStart(IDocument iDocument) {
        return this.elem.getStartOffset();
    }

    public long getEndOffset(IDocument iDocument) {
        return this.end;
    }

    public long getElemEnd(IDocument iDocument) {
        return this.elem.getEndOffset();
    }

    public IView getParentView() {
        return this.parent;
    }

    public void setParentView(IView iView) {
        this.parent = iView;
    }

    public IView getChildView() {
        return this.child;
    }

    public void setChildView(IView iView) {
        this.child = iView;
    }

    public IView getPreView() {
        return this.preView;
    }

    public void setPreView(IView iView) {
        this.preView = iView;
    }

    public IView getNextView() {
        return this.nextView;
    }

    public void setNextView(IView iView) {
        this.nextView = iView;
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public void setLocation(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public void setBound(int i, int i2, int i3, int i4) {
        this.x = i;
        this.y = i2;
        this.width = i3;
        this.height = i2;
    }

    public IWord getContainer() {
        IView parentView = getParentView();
        if (parentView != null) {
            return parentView.getContainer();
        }
        return null;
    }

    public IControl getControl() {
        IView parentView = getParentView();
        if (parentView != null) {
            return parentView.getControl();
        }
        return null;
    }

    public IDocument getDocument() {
        IView parentView = getParentView();
        if (parentView != null) {
            return parentView.getDocument();
        }
        return null;
    }

    public IView getLastView() {
        IView childView = getChildView();
        if (childView == null) {
            return null;
        }
        while (childView.getNextView() != null) {
            childView = childView.getNextView();
        }
        return childView;
    }

    public int getChildCount() {
        int i = 0;
        for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
            i++;
        }
        return i;
    }

    public void appendChlidView(IView iView) {
        iView.setParentView(this);
        if (this.child == null) {
            this.child = iView;
            return;
        }
        IView lastView = getLastView();
        iView.setPreView(lastView);
        lastView.setNextView(iView);
    }

    public void insertView(IView iView, IView iView2) {
        iView2.setParentView(this);
        if (iView == null) {
            IView iView3 = this.child;
            if (iView3 == null) {
                this.child = iView2;
                return;
            }
            iView2.setNextView(iView3);
            this.child.setPreView(iView2);
            this.child = iView2;
        }
    }

    public void deleteView(IView iView, boolean z) {
        iView.setParentView((IView) null);
        if (iView == this.child) {
            this.child = null;
        } else {
            IView preView2 = iView.getPreView();
            IView nextView2 = iView.getNextView();
            preView2.setNextView(nextView2);
            if (nextView2 != null) {
                nextView2.setPreView(preView2);
            }
        }
        if (z) {
            iView.dispose();
        }
    }

    public void setEndOffset(long j) {
        this.end = j;
    }

    public Rect getViewRect(int i, int i2, float f) {
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        return new Rect(i3, i4, ((int) (((float) getLayoutSpan((byte) 0)) * f)) + i3, ((int) (((float) getLayoutSpan((byte) 1)) * f)) + i4);
    }

    public boolean intersection(Rect rect, int i, int i2, float f) {
        int layoutSpan = (int) (((float) getLayoutSpan((byte) 0)) * f);
        int layoutSpan2 = (int) (((float) getLayoutSpan((byte) 1)) * f);
        int i3 = rect.right - rect.left;
        int i4 = rect.bottom - rect.top;
        if (i3 <= 0 || i4 <= 0 || layoutSpan <= 0 || layoutSpan2 <= 0) {
            return false;
        }
        int i5 = ((int) (((float) this.x) * f)) + i;
        int i6 = ((int) (((float) this.y) * f)) + i2;
        int i7 = rect.left;
        int i8 = rect.top;
        int i9 = i3 + i7;
        int i10 = i4 + i8;
        int i11 = layoutSpan + i5;
        int i12 = layoutSpan2 + i6;
        if (i9 >= i7 && i9 <= i5) {
            return false;
        }
        if (i10 >= i8 && i10 <= i6) {
            return false;
        }
        if (i11 >= i5 && i11 <= i7) {
            return false;
        }
        if (i12 < i6 || i12 > i8) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0010, code lost:
        r0 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean contains(long r6, boolean r8) {
        /*
            r5 = this;
            com.app.office.simpletext.model.IDocument r0 = r5.getDocument()
            long r1 = r5.getStartOffset(r0)
            long r3 = r5.getEndOffset(r0)
            int r0 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r0 < 0) goto L_0x001a
            int r0 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r0 < 0) goto L_0x0018
            if (r0 != 0) goto L_0x001a
            if (r8 == 0) goto L_0x001a
        L_0x0018:
            r6 = 1
            goto L_0x001b
        L_0x001a:
            r6 = 0
        L_0x001b:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.simpletext.view.AbstractView.contains(long, boolean):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0009, code lost:
        r2 = r1.y;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean contains(int r2, int r3, boolean r4) {
        /*
            r1 = this;
            int r4 = r1.x
            if (r2 < r4) goto L_0x0016
            int r0 = r1.width
            int r4 = r4 + r0
            if (r2 >= r4) goto L_0x0016
            int r2 = r1.y
            if (r3 < r2) goto L_0x0016
            int r4 = r1.getHeight()
            int r2 = r2 + r4
            if (r3 >= r2) goto L_0x0016
            r2 = 1
            goto L_0x0017
        L_0x0016:
            r2 = 0
        L_0x0017:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.simpletext.view.AbstractView.contains(int, int, boolean):boolean");
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        Rect clipBounds = canvas.getClipBounds();
        for (IView childView = getChildView(); childView != null; childView = childView.getNextView()) {
            if (childView.intersection(clipBounds, i3, i4, f)) {
                childView.draw(canvas, i3, i4, f);
            }
        }
    }

    public void getLineHeight(List<Integer> list) {
        IView childView = getChildView();
        if (childView != null) {
            while (childView != null) {
                list.add(Integer.valueOf(childView.getHeight()));
                childView = childView.getNextView();
            }
        }
    }

    public int getTopIndent() {
        return this.topIndent;
    }

    public void setTopIndent(int i) {
        this.topIndent = i;
    }

    public int getBottomIndent() {
        return this.bottomIndent;
    }

    public void setBottomIndent(int i) {
        this.bottomIndent = i;
    }

    public int getLeftIndent() {
        return this.leftIndent;
    }

    public void setLeftIndent(int i) {
        this.leftIndent = i;
    }

    public int getRightIndent() {
        return this.rightIndent;
    }

    public void setRightIndent(int i) {
        this.rightIndent = i;
    }

    public int getLayoutSpan(byte b) {
        int height2;
        int i;
        if (b == 0) {
            height2 = this.rightIndent + this.width;
            i = this.leftIndent;
        } else {
            height2 = this.topIndent + getHeight();
            i = this.bottomIndent;
        }
        return height2 + i;
    }

    public void setIndent(int i, int i2, int i3, int i4) {
        this.leftIndent = i;
        this.topIndent = i2;
        this.rightIndent = i3;
        this.bottomIndent = i4;
    }

    public IView getView(long j, int i, boolean z) {
        IView iView = this.child;
        while (iView != null && !iView.contains(j, z)) {
            iView = iView.getNextView();
        }
        return (iView == null || iView.getType() == i) ? iView : iView.getView(j, i, z);
    }

    public IView getView(int i, int i2, int i3, boolean z) {
        IView iView = this.child;
        while (iView != null && !iView.contains(i, i2, z)) {
            iView = iView.getNextView();
        }
        return (iView == null || iView.getType() == i3) ? iView : iView.getView(i - this.x, i2 - this.y, i3, z);
    }

    public void dispose() {
        this.parent = null;
        this.elem = null;
        IView iView = this.child;
        while (iView != null) {
            IView nextView2 = iView.getNextView();
            iView.dispose();
            iView = nextView2;
        }
        this.preView = null;
        this.nextView = null;
        this.child = null;
    }
}
