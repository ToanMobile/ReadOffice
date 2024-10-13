package com.app.office.wp.view;

import com.app.office.simpletext.control.IWord;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.AbstractView;
import com.app.office.simpletext.view.IView;
import com.app.office.system.IControl;

public class TitleView extends AbstractView {
    private IView pageRoot;

    public short getType() {
        return 12;
    }

    public TitleView(IElement iElement) {
        this.elem = iElement;
    }

    public IWord getContainer() {
        IView iView = this.pageRoot;
        if (iView != null) {
            return iView.getContainer();
        }
        return null;
    }

    public IControl getControl() {
        IView iView = this.pageRoot;
        if (iView != null) {
            return iView.getControl();
        }
        return null;
    }

    public IDocument getDocument() {
        IView iView = this.pageRoot;
        if (iView != null) {
            return iView.getDocument();
        }
        return null;
    }

    public void setPageRoot(IView iView) {
        this.pageRoot = iView;
    }

    public void dispose() {
        super.dispose();
        this.pageRoot = null;
    }
}
