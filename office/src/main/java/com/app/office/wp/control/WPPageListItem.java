package com.app.office.wp.control;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.app.office.system.IControl;
import com.app.office.system.beans.pagelist.APageListItem;
import com.app.office.system.beans.pagelist.APageListView;
import com.app.office.wp.view.PageRoot;
import com.app.office.wp.view.PageView;

public class WPPageListItem extends APageListItem {
    private static final int BACKGROUND_COLOR = -1;
    private boolean isInit = true;
    private PageRoot pageRoot;

    /* access modifiers changed from: protected */
    public void removeRepaintImageView() {
    }

    public WPPageListItem(APageListView aPageListView, IControl iControl, int i, int i2) {
        super(aPageListView, i, i2);
        this.control = iControl;
        this.pageRoot = (PageRoot) aPageListView.getModel();
        setBackgroundColor(-1);
    }

    public void onDraw(Canvas canvas) {
        PageView pageView = this.pageRoot.getPageView(this.pageIndex);
        if (pageView != null) {
            float zoom = this.listView.getZoom();
            canvas.save();
            canvas.translate(((float) (-pageView.getX())) * zoom, ((float) (-pageView.getY())) * zoom);
            pageView.drawForPrintMode(canvas, 0, 0, zoom);
            canvas.restore();
        }
    }

    public void setPageItemRawData(int i, int i2, int i3) {
        super.setPageItemRawData(i, i2, i3);
        if (((int) (this.listView.getZoom() * 100.0f)) == 100 || (this.isInit && i == 0)) {
            this.listView.exportImage(this, (Bitmap) null);
        }
        this.isInit = false;
    }

    /* access modifiers changed from: protected */
    public void addRepaintImageView(Bitmap bitmap) {
        postInvalidate();
        this.listView.exportImage(this, (Bitmap) null);
    }

    public void dispose() {
        super.dispose();
        this.control = null;
        this.pageRoot = null;
    }
}
