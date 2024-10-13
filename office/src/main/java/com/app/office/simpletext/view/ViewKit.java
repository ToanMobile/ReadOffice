package com.app.office.simpletext.view;

public class ViewKit {
    private static final ViewKit kit = new ViewKit();

    public boolean getBitValue(int i, int i2) {
        return ((i >>> i2) & 1) == 1;
    }

    public int setBitValue(int i, int i2, boolean z) {
        int i3 = (((z ? i : ~i) >>> i2) | 1) << i2;
        return z ? i | i3 : i & (~i3);
    }

    public static ViewKit instance() {
        return kit;
    }

    public IView getParentView(IView iView, short s) {
        IView parentView = iView.getParentView();
        while (parentView != null && parentView.getType() != s) {
            parentView = parentView.getParentView();
        }
        return parentView;
    }
}
