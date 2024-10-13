package com.app.office.wp.view;

import android.graphics.Canvas;
import com.app.office.java.awt.Rectangle;
import com.app.office.objectpool.IMemObj;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.IView;

public class ParagraphView extends AbstractView implements IMemObj {
    private BNView bnView;

    public void free() {
    }

    public IMemObj getCopy() {
        return null;
    }

    public short getType() {
        return 5;
    }

    public ParagraphView(IElement iElement) {
        this.elem = iElement;
    }

    public String getText() {
        return this.elem.getText((IDocument) null);
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        if (getChildView() == null) {
            buildLine();
        }
        IView view = getView(j, 6, z);
        if (view != null) {
            view.modelToView(j, rectangle, z);
        }
        rectangle.x += getX();
        rectangle.y += getY();
        return rectangle;
    }

    public long viewToModel(int i, int i2, boolean z) {
        if (getChildView() == null) {
            buildLine();
        }
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

    public void draw(Canvas canvas, int i, int i2, float f) {
        if (getChildView() == null) {
            buildLine();
        }
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        BNView bNView = this.bnView;
        if (bNView != null) {
            bNView.draw(canvas, i3, i4, f);
        }
        super.draw(canvas, i, i2, f);
    }

    public void setBNView(BNView bNView) {
        this.bnView = bNView;
    }

    public BNView getBNView() {
        return this.bnView;
    }

    private void buildLine() {
        IDocument document = getDocument();
        if (document != null) {
            LayoutKit.instance().buildLine(document, this);
        }
    }

    public void dispose() {
        super.dispose();
        BNView bNView = this.bnView;
        if (bNView != null) {
            bNView.dispose();
            this.bnView = null;
        }
    }
}
