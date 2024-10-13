package com.app.office.wp.view;

import com.app.office.constant.wp.WPModelConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.view.IView;
import com.app.office.simpletext.view.ViewKit;
import com.app.office.wp.control.Word;

public class WPViewKit extends ViewKit {
    private static WPViewKit kit = new WPViewKit();

    public long getArea(long j) {
        return j & WPModelConstant.AREA_MASK;
    }

    public static WPViewKit instance() {
        return kit;
    }

    public PageView getPageView(IView iView, int i, int i2) {
        if (iView == null) {
            return null;
        }
        IView childView = iView.getChildView();
        while (childView != null && (i2 <= childView.getY() || i2 >= childView.getY() + childView.getHeight() + 5)) {
            childView = childView.getNextView();
        }
        if (childView == null) {
            childView = iView.getChildView();
        }
        if (childView == null) {
            return null;
        }
        return (PageView) childView;
    }

    public IView getView(Word word, long j, int i, boolean z) {
        return word.getRoot(word.getCurrentRootType()).getView(j, i, z);
    }

    public IView getView(Word word, int i, int i2, int i3, boolean z) {
        return word.getRoot(word.getCurrentRootType()).getView(i, i2, i3, z);
    }

    public Rectangle getAbsoluteCoordinate(IView iView, int i, Rectangle rectangle) {
        rectangle.setBounds(0, 0, 0, 0);
        while (iView != null && iView.getType() != i) {
            rectangle.x += iView.getX();
            rectangle.y += iView.getY();
            iView = iView.getParentView();
        }
        return rectangle;
    }
}
