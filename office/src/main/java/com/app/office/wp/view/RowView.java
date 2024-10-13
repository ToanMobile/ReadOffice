package com.app.office.wp.view;

import android.graphics.Rect;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.IView;

public class RowView extends AbstractView {
    private boolean isExactlyHeight;

    public short getType() {
        return 10;
    }

    public boolean intersection(Rect rect, int i, int i2, float f) {
        return true;
    }

    public RowView(IElement iElement) {
        this.elem = iElement;
    }

    public boolean isExactlyHeight() {
        return this.isExactlyHeight;
    }

    public void setExactlyHeight(boolean z) {
        this.isExactlyHeight = z;
    }

    public CellView getCellView(short s) {
        CellView cellView = (CellView) getChildView();
        int i = 0;
        while (cellView != null && i != s) {
            i++;
            cellView = (CellView) cellView.getNextView();
        }
        return cellView;
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        IView view = getView(j, 11, z);
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
            while (childView != null && (y < childView.getY() || y >= childView.getY() + childView.getLayoutSpan((byte) 1) || x < childView.getX() || x > childView.getX() + childView.getLayoutSpan((byte) 0))) {
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
}
