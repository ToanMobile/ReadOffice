package com.app.office.system.beans.pagelist;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import com.app.office.system.IControl;
import com.app.office.system.beans.CalloutView.CalloutView;
import com.app.office.system.beans.CalloutView.IExportListener;

public class APageListItem extends ViewGroup implements IExportListener {
    protected CalloutView callouts;
    /* access modifiers changed from: protected */
    public IControl control;
    /* access modifiers changed from: protected */
    public boolean isInit = true;
    /* access modifiers changed from: protected */
    public APageListView listView;
    /* access modifiers changed from: protected */
    public boolean mIsBlank;
    protected int pageHeight;
    /* access modifiers changed from: protected */
    public int pageIndex;
    protected int pageWidth;

    /* access modifiers changed from: protected */
    public void addRepaintImageView(Bitmap bitmap) {
    }

    public boolean isOpaque() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void removeRepaintImageView() {
    }

    public void setLinkHighlighting(boolean z) {
    }

    public APageListItem(APageListView aPageListView, int i, int i2) {
        super(aPageListView.getContext());
        this.listView = aPageListView;
        this.pageWidth = i;
        this.pageHeight = i2;
        setBackgroundColor(-1);
    }

    public IControl getControl() {
        return this.control;
    }

    public void initCalloutView() {
        if (this.callouts == null) {
            CalloutView calloutView = new CalloutView(this.listView.getContext(), this.control, this);
            this.callouts = calloutView;
            calloutView.setIndex(this.pageIndex);
            addView(this.callouts, 0);
        }
    }

    public void exportImage() {
        APageListView aPageListView = this.listView;
        aPageListView.postRepaint(aPageListView.getCurrentPageView());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(View.MeasureSpec.getMode(i) == 0 ? this.pageWidth : View.MeasureSpec.getSize(i), View.MeasureSpec.getMode(i2) == 0 ? this.pageHeight : View.MeasureSpec.getSize(i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        CalloutView calloutView = this.callouts;
        if (calloutView != null) {
            calloutView.setZoom(this.listView.getZoom());
            this.callouts.layout(0, 0, i3 - i, i4 - i2);
            this.callouts.bringToFront();
        }
    }

    public void setPageItemRawData(int i, int i2, int i3) {
        this.mIsBlank = false;
        this.pageIndex = i;
        this.pageWidth = i2;
        this.pageHeight = i3;
        CalloutView calloutView = this.callouts;
        if (calloutView != null) {
            calloutView.setIndex(i);
        } else if (!this.control.getSysKit().getCalloutManager().isPathEmpty(i)) {
            initCalloutView();
        }
    }

    public void releaseResources() {
        this.mIsBlank = true;
        this.pageIndex = 0;
        if (this.pageWidth == 0 || this.pageHeight == 0) {
            this.pageWidth = this.listView.getWidth();
            this.pageHeight = this.listView.getHeight();
        }
    }

    public void blank(int i) {
        this.mIsBlank = true;
        this.pageIndex = i;
        if (this.pageWidth == 0 || this.pageHeight == 0) {
            this.pageWidth = this.listView.getWidth();
            this.pageHeight = this.listView.getHeight();
        }
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public int getPageWidth() {
        return this.pageWidth;
    }

    public int getPageHeight() {
        return this.pageHeight;
    }

    public void dispose() {
        this.listView = null;
    }
}
