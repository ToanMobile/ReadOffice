package com.app.office.pg.control;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGSlide;
import com.app.office.pg.view.SlideDrawKit;
import com.app.office.system.IControl;
import com.app.office.system.beans.pagelist.APageListItem;
import com.app.office.system.beans.pagelist.APageListView;

public class PGPageListItem extends APageListItem {
    private static final int BACKGROUND_COLOR = -1;
    public static final int BUSY_SIZE = 60;
    private PGEditor editor;
    /* access modifiers changed from: private */
    public ProgressBar mBusyIndicator;
    /* access modifiers changed from: private */
    public PGModel pgModel;

    /* access modifiers changed from: protected */
    public void removeRepaintImageView() {
    }

    public PGPageListItem(APageListView aPageListView, IControl iControl, PGEditor pGEditor, int i, int i2) {
        super(aPageListView, i, i2);
        this.control = iControl;
        this.pgModel = (PGModel) aPageListView.getModel();
        this.editor = pGEditor;
        setBackgroundColor(-1);
    }

    public void onDraw(Canvas canvas) {
        PGSlide slide = this.pgModel.getSlide(this.pageIndex);
        if (slide != null) {
            Canvas canvas2 = canvas;
            SlideDrawKit.instance().drawSlide(canvas2, this.pgModel, this.editor, slide, this.listView.getZoom());
        }
    }

    public void setPageItemRawData(int i, int i2, int i3) {
        super.setPageItemRawData(i, i2, i3);
        if (this.pageIndex >= this.pgModel.getRealSlideCount()) {
            new AsyncTask<Void, Void, Void>() {
                /* access modifiers changed from: protected */
                public Void doInBackground(Void... voidArr) {
                    while (PGPageListItem.this.pgModel != null && PGPageListItem.this.pageIndex >= PGPageListItem.this.pgModel.getRealSlideCount()) {
                        try {
                            Thread.sleep(200);
                        } catch (Exception unused) {
                            return null;
                        }
                    }
                    return null;
                }

                /* access modifiers changed from: protected */
                public void onPreExecute() {
                    if (PGPageListItem.this.mBusyIndicator == null) {
                        ProgressBar unused = PGPageListItem.this.mBusyIndicator = new ProgressBar(PGPageListItem.this.getContext());
                        PGPageListItem.this.mBusyIndicator.setIndeterminate(true);
                        PGPageListItem.this.mBusyIndicator.setBackgroundResource(17301612);
                        PGPageListItem pGPageListItem = PGPageListItem.this;
                        pGPageListItem.addView(pGPageListItem.mBusyIndicator);
                        PGPageListItem.this.mBusyIndicator.setVisibility(0);
                        return;
                    }
                    PGPageListItem.this.mBusyIndicator.setVisibility(0);
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(Void voidR) {
                    if (PGPageListItem.this.mBusyIndicator != null) {
                        PGPageListItem.this.mBusyIndicator.setVisibility(4);
                    }
                    PGPageListItem.this.postInvalidate();
                    if (PGPageListItem.this.listView != null) {
                        if (PGPageListItem.this.pageIndex == PGPageListItem.this.listView.getCurrentPageNumber() - 1) {
                            PGPageListItem.this.listView.exportImage(PGPageListItem.this.listView.getCurrentPageView(), (Bitmap) null);
                        }
                        boolean unused = PGPageListItem.this.isInit = false;
                    }
                }
            }.execute(new Void[1]);
            return;
        }
        if (((int) (this.listView.getZoom() * 100.0f)) == 100 || (this.isInit && i == 0)) {
            this.listView.exportImage(this, (Bitmap) null);
        }
        this.isInit = false;
        ProgressBar progressBar = this.mBusyIndicator;
        if (progressBar != null) {
            progressBar.setVisibility(4);
        }
    }

    public void releaseResources() {
        super.releaseResources();
        SlideDrawKit instance = SlideDrawKit.instance();
        PGModel pGModel = this.pgModel;
        instance.disposeOldSlideView(pGModel, pGModel.getSlide(this.pageIndex));
    }

    public void blank(int i) {
        super.blank(i);
    }

    /* access modifiers changed from: protected */
    public void addRepaintImageView(Bitmap bitmap) {
        postInvalidate();
        this.listView.exportImage(this, bitmap);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        super.onLayout(z, i, i2, i3, i4);
        int i7 = i3 - i;
        int i8 = i4 - i2;
        if (this.mBusyIndicator != null) {
            if (i7 > this.listView.getWidth()) {
                i5 = ((this.listView.getWidth() - 60) / 2) - i;
            } else {
                i5 = (i7 - 60) / 2;
            }
            if (i8 > this.listView.getHeight()) {
                i6 = ((this.listView.getHeight() - 60) / 2) - i2;
            } else {
                i6 = (i8 - 60) / 2;
            }
            this.mBusyIndicator.layout(i5, i6, i5 + 60, i6 + 60);
        }
    }

    public void dispose() {
        super.dispose();
        this.control = null;
        this.pgModel = null;
    }
}
