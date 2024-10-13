package com.app.office.wp.view;

import android.graphics.Canvas;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.IView;

public class CellView extends AbstractView {
    private int background = -1;
    private short column;
    private boolean isFirstMergedCell;
    private boolean isMergedCell;

    public short getType() {
        return 11;
    }

    public CellView(IElement iElement) {
        this.elem = iElement;
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        super.draw(canvas, i, i2, f);
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView view = getView(j, 6, z);
        if (view != null) {
            view.modelToView(j, rectangle, z);
        }
        rectangle.x += getX() + getLeftIndent();
        rectangle.y += getY() + getTopIndent();
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

    public boolean isFirstMergedCell() {
        return this.isFirstMergedCell;
    }

    public void setFirstMergedCell(boolean z) {
        this.isFirstMergedCell = z;
    }

    public boolean isMergedCell() {
        return this.isMergedCell;
    }

    public void setMergedCell(boolean z) {
        this.isMergedCell = z;
    }

    public boolean isValidLastCell() {
        if (getNextView() == null) {
            return true;
        }
        CellView cellView = (CellView) getNextView();
        if (isMergedCell()) {
            return cellView.isValidLastCell();
        }
        if (cellView.getStartOffset((IDocument) null) == 0 && cellView.getEndOffset((IDocument) null) == 0) {
            return cellView.isValidLastCell();
        }
        return false;
    }

    public short getColumn() {
        return this.column;
    }

    public void setColumn(short s) {
        this.column = s;
    }

    public void setBackground(int i) {
        this.background = i;
    }

    /* access modifiers changed from: protected */
    public int getBackground() {
        return this.background;
    }

    public void dispose() {
        super.dispose();
    }
}
