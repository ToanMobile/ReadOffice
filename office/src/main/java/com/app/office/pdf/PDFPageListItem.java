package com.app.office.pdf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.app.office.fc.pdf.PDFLib;
import com.app.office.simpletext.control.SafeAsyncTask;
import com.app.office.system.IControl;
import com.app.office.system.beans.pagelist.APageListItem;
import com.app.office.system.beans.pagelist.APageListView;

public class PDFPageListItem extends APageListItem {
    private SafeAsyncTask<Void, Void, Bitmap> darwOriginalPageTask;
    /* access modifiers changed from: private */
    public boolean isAutoTest;
    /* access modifiers changed from: private */
    public boolean isOriginalBitmapValid;
    /* access modifiers changed from: private */
    public final PDFLib lib;
    /* access modifiers changed from: private */
    public ProgressBar mBusyIndicator;
    /* access modifiers changed from: private */
    public Bitmap originalBitmap;
    /* access modifiers changed from: private */
    public ImageView originalImageView;
    /* access modifiers changed from: private */
    public Rect repaintArea;
    /* access modifiers changed from: private */
    public ImageView repaintImageView;
    private SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo> repaintSyncTask;
    /* access modifiers changed from: private */
    public View searchView;
    /* access modifiers changed from: private */
    public int viewHeight;
    /* access modifiers changed from: private */
    public int viewWidth;

    public void setLinkHighlighting(boolean z) {
    }

    public PDFPageListItem(APageListView aPageListView, IControl iControl, int i, int i2) {
        super(aPageListView, i, i2);
        this.listView = aPageListView;
        this.control = iControl;
        this.lib = (PDFLib) aPageListView.getModel();
        this.isAutoTest = iControl.isAutoTest();
        setBackgroundColor(-1);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        super.onLayout(z, i, i2, i3, i4);
        int i7 = i3 - i;
        int i8 = i4 - i2;
        ImageView imageView = this.originalImageView;
        if (imageView != null) {
            imageView.layout(0, 0, i7, i8);
        }
        View view = this.searchView;
        if (view != null) {
            view.layout(0, 0, i7, i8);
        }
        if (this.viewWidth == i7 && this.viewHeight == i8) {
            ImageView imageView2 = this.repaintImageView;
            if (imageView2 != null) {
                imageView2.layout(this.repaintArea.left, this.repaintArea.top, this.repaintArea.right, this.repaintArea.bottom);
            }
        } else {
            this.viewHeight = 0;
            this.viewWidth = 0;
            this.repaintArea = null;
            ImageView imageView3 = this.repaintImageView;
            if (imageView3 != null) {
                imageView3.setImageBitmap((Bitmap) null);
            }
        }
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

    public void setPageItemRawData(final int i, int i2, int i3) {
        super.setPageItemRawData(i, i2, i3);
        this.isOriginalBitmapValid = false;
        SafeAsyncTask<Void, Void, Bitmap> safeAsyncTask = this.darwOriginalPageTask;
        if (safeAsyncTask != null) {
            safeAsyncTask.cancel(true);
            this.darwOriginalPageTask = null;
        }
        if (this.originalImageView == null) {
            AnonymousClass1 r7 = new ImageView(this.listView.getContext()) {
                public boolean isOpaque() {
                    return true;
                }

                public void onDraw(Canvas canvas) {
                    try {
                        super.onDraw(canvas);
                    } catch (Exception unused) {
                    }
                }
            };
            this.originalImageView = r7;
            r7.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(this.originalImageView);
        }
        if (this.pageWidth > 0 && this.pageHeight > 0) {
            if (Build.VERSION.SDK_INT >= 1) {
                this.originalImageView.setImageBitmap((Bitmap) null);
            }
            float fitZoom = this.listView.getFitZoom();
            Bitmap bitmap = this.originalBitmap;
            if (!(bitmap != null && bitmap.getWidth() == ((int) (((float) this.pageWidth) * fitZoom)) && this.originalBitmap.getHeight() == ((int) (((float) this.pageHeight) * fitZoom)))) {
                int i4 = (int) (((float) this.pageWidth) * fitZoom);
                int i5 = (int) (((float) this.pageHeight) * fitZoom);
                try {
                    if (!this.listView.isInitZoom()) {
                        this.listView.setZoom(fitZoom, false);
                    }
                    if (this.originalBitmap != null) {
                        while (!this.lib.isDrawPageSyncFinished()) {
                            try {
                                Thread.sleep(100);
                            } catch (Exception unused) {
                            }
                        }
                        this.originalBitmap.recycle();
                    }
                    this.originalBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
                } catch (OutOfMemoryError unused2) {
                    System.gc();
                    try {
                        Thread.sleep(50);
                        this.originalBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
                    } catch (Exception unused3) {
                        return;
                    }
                }
            }
            AnonymousClass2 r72 = new SafeAsyncTask<Void, Void, Bitmap>() {
                private boolean isCancel = false;

                /* access modifiers changed from: protected */
                public Bitmap doInBackground(Void... voidArr) {
                    try {
                        if (PDFPageListItem.this.originalBitmap == null) {
                            return null;
                        }
                        Thread.sleep(PDFPageListItem.this.pageIndex == PDFPageListItem.this.listView.getCurrentPageNumber() + -1 ? 500 : 1000);
                        if (this.isCancel) {
                            return null;
                        }
                        PDFPageListItem.this.lib.drawPageSync(PDFPageListItem.this.originalBitmap, PDFPageListItem.this.pageIndex, (float) PDFPageListItem.this.originalBitmap.getWidth(), (float) PDFPageListItem.this.originalBitmap.getHeight(), 0, 0, PDFPageListItem.this.originalBitmap.getWidth(), PDFPageListItem.this.originalBitmap.getHeight(), 1);
                        return PDFPageListItem.this.originalBitmap;
                    } catch (Exception unused) {
                        return null;
                    }
                }

                /* access modifiers changed from: protected */
                public void onPreExecute() {
                    PDFPageListItem.this.originalImageView.setImageBitmap((Bitmap) null);
                    if (PDFPageListItem.this.mBusyIndicator == null) {
                        ProgressBar unused = PDFPageListItem.this.mBusyIndicator = new ProgressBar(PDFPageListItem.this.getContext());
                        PDFPageListItem.this.mBusyIndicator.setIndeterminate(true);
                        PDFPageListItem.this.mBusyIndicator.setBackgroundResource(17301612);
                        PDFPageListItem pDFPageListItem = PDFPageListItem.this;
                        pDFPageListItem.addView(pDFPageListItem.mBusyIndicator);
                        PDFPageListItem.this.mBusyIndicator.setVisibility(0);
                        return;
                    }
                    PDFPageListItem.this.mBusyIndicator.setVisibility(0);
                }

                /* access modifiers changed from: protected */
                public void onCancelled() {
                    this.isCancel = true;
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(Bitmap bitmap) {
                    try {
                        boolean unused = PDFPageListItem.this.mIsBlank = false;
                        boolean unused2 = PDFPageListItem.this.isOriginalBitmapValid = true;
                        if (!(PDFPageListItem.this.listView == null || PDFPageListItem.this.mBusyIndicator == null)) {
                            PDFPageListItem.this.mBusyIndicator.setVisibility(4);
                        }
                        PDFPageListItem.this.listView.setDoRequstLayout(false);
                        PDFPageListItem.this.originalImageView.setImageBitmap(PDFPageListItem.this.originalBitmap);
                        PDFPageListItem.this.listView.setDoRequstLayout(true);
                        PDFPageListItem.this.invalidate();
                        if (PDFPageListItem.this.listView != null) {
                            if ((((int) (PDFPageListItem.this.listView.getZoom() * 100.0f)) == 100 || (PDFPageListItem.this.isInit && i == 0)) && bitmap != null) {
                                if (!PDFPageListItem.this.isInit || i != 0) {
                                    PDFPageListItem.this.listView.exportImage(this, PDFPageListItem.this.originalBitmap);
                                } else {
                                    PDFPageListItem.this.listView.postRepaint(PDFPageListItem.this.listView.getCurrentPageView());
                                }
                            }
                            boolean unused3 = PDFPageListItem.this.isInit = false;
                            if (PDFPageListItem.this.isAutoTest) {
                                PDFPageListItem.this.control.actionEvent(22, true);
                            }
                        }
                    } catch (NullPointerException unused4) {
                    }
                }
            };
            this.darwOriginalPageTask = r72;
            r72.safeExecute(new Void[0]);
            if (this.searchView == null) {
                AnonymousClass3 r5 = new View(getContext()) {
                    /* access modifiers changed from: protected */
                    public void onDraw(Canvas canvas) {
                        super.onDraw(canvas);
                        PDFFind pDFFind = (PDFFind) PDFPageListItem.this.control.getFind();
                        if (pDFFind != null && !PDFPageListItem.this.mIsBlank) {
                            pDFFind.drawHighlight(canvas, 0, 0, this);
                        }
                    }
                };
                this.searchView = r5;
                addView(r5, new ViewGroup.LayoutParams(-1, -1));
            }
        }
    }

    public void releaseResources() {
        super.releaseResources();
        this.isOriginalBitmapValid = false;
        SafeAsyncTask<Void, Void, Bitmap> safeAsyncTask = this.darwOriginalPageTask;
        if (safeAsyncTask != null) {
            safeAsyncTask.cancel(true);
            this.darwOriginalPageTask = null;
        }
        SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo> safeAsyncTask2 = this.repaintSyncTask;
        if (safeAsyncTask2 != null) {
            safeAsyncTask2.cancel(true);
            this.repaintSyncTask = null;
        }
        ImageView imageView = this.originalImageView;
        if (imageView != null) {
            imageView.setImageBitmap((Bitmap) null);
        }
        ImageView imageView2 = this.repaintImageView;
        if (imageView2 != null) {
            imageView2.setImageBitmap((Bitmap) null);
        }
        this.control.getMainFrame().isShowProgressBar();
        ProgressBar progressBar = this.mBusyIndicator;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
    }

    public void blank(int i) {
        super.blank(i);
        this.isOriginalBitmapValid = false;
        SafeAsyncTask<Void, Void, Bitmap> safeAsyncTask = this.darwOriginalPageTask;
        if (safeAsyncTask != null) {
            safeAsyncTask.cancel(true);
            this.darwOriginalPageTask = null;
        }
        SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo> safeAsyncTask2 = this.repaintSyncTask;
        if (safeAsyncTask2 != null) {
            safeAsyncTask2.cancel(true);
            this.repaintSyncTask = null;
        }
        ImageView imageView = this.originalImageView;
        if (imageView != null) {
            imageView.setImageBitmap((Bitmap) null);
        }
        ImageView imageView2 = this.repaintImageView;
        if (imageView2 != null) {
            imageView2.setImageBitmap((Bitmap) null);
        }
        ProgressBar progressBar = this.mBusyIndicator;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
    }

    public int getHyperlinkCount(float f, float f2) {
        float width = ((float) getWidth()) / ((float) this.pageWidth);
        return this.lib.getHyperlinkCountSync(this.pageIndex, (f - ((float) getLeft())) / width, (f2 - ((float) getTop())) / width);
    }

    /* access modifiers changed from: protected */
    public void addRepaintImageView(Bitmap bitmap) {
        Rect rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
        if (rect.width() != this.pageWidth || rect.height() != this.pageHeight || (this.originalBitmap != null && ((int) this.listView.getZoom()) * 100 == 100 && (this.originalBitmap.getWidth() != this.pageWidth || this.originalBitmap.getHeight() != this.pageHeight))) {
            Rect rect2 = new Rect(0, 0, this.listView.getWidth(), this.listView.getHeight());
            if (rect2.intersect(rect)) {
                rect2.offset(-rect.left, -rect.top);
                if (!rect2.equals(this.repaintArea) || this.viewHeight != rect.width() || this.viewHeight != rect.height()) {
                    SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo> safeAsyncTask = this.repaintSyncTask;
                    if (safeAsyncTask != null) {
                        safeAsyncTask.cancel(true);
                        this.repaintSyncTask = null;
                    }
                    if (this.repaintImageView == null) {
                        AnonymousClass4 r1 = new ImageView(this.listView.getContext()) {
                            public boolean isOpaque() {
                                return true;
                            }

                            public void onDraw(Canvas canvas) {
                                try {
                                    super.onDraw(canvas);
                                } catch (Exception unused) {
                                }
                            }
                        };
                        this.repaintImageView = r1;
                        r1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        this.repaintImageView.setImageBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
                    }
                    this.repaintSyncTask = new SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo>() {
                        /* access modifiers changed from: protected */
                        public RepaintAreaInfo doInBackground(RepaintAreaInfo... repaintAreaInfoArr) {
                            try {
                                PDFPageListItem.this.lib.drawPageSync(repaintAreaInfoArr[0].bm, PDFPageListItem.this.pageIndex, (float) repaintAreaInfoArr[0].viewWidth, (float) repaintAreaInfoArr[0].viewHeight, repaintAreaInfoArr[0].repaintArea.left, repaintAreaInfoArr[0].repaintArea.top, repaintAreaInfoArr[0].repaintArea.width(), repaintAreaInfoArr[0].repaintArea.height(), 1);
                                return repaintAreaInfoArr[0];
                            } catch (Exception unused) {
                                return null;
                            }
                        }

                        /* access modifiers changed from: protected */
                        public void onPostExecute(RepaintAreaInfo repaintAreaInfo) {
                            try {
                                int unused = PDFPageListItem.this.viewWidth = repaintAreaInfo.viewWidth;
                                int unused2 = PDFPageListItem.this.viewHeight = repaintAreaInfo.viewHeight;
                                Rect unused3 = PDFPageListItem.this.repaintArea = repaintAreaInfo.repaintArea;
                                Drawable drawable = PDFPageListItem.this.repaintImageView.getDrawable();
                                if (drawable instanceof BitmapDrawable) {
                                    if (((BitmapDrawable) drawable).getBitmap() != null) {
                                        while (!PDFPageListItem.this.lib.isDrawPageSyncFinished()) {
                                            try {
                                                Thread.sleep(100);
                                            } catch (Exception unused4) {
                                            }
                                        }
                                        ((BitmapDrawable) drawable).getBitmap().recycle();
                                    }
                                    PDFPageListItem.this.listView.setDoRequstLayout(false);
                                    PDFPageListItem.this.repaintImageView.setImageBitmap((Bitmap) null);
                                    PDFPageListItem.this.repaintImageView.setImageBitmap(repaintAreaInfo.bm);
                                    PDFPageListItem.this.listView.setDoRequstLayout(true);
                                }
                                PDFPageListItem.this.repaintImageView.layout(PDFPageListItem.this.repaintArea.left, PDFPageListItem.this.repaintArea.top, PDFPageListItem.this.repaintArea.right, PDFPageListItem.this.repaintArea.bottom);
                                if (PDFPageListItem.this.repaintImageView.getParent() == null) {
                                    PDFPageListItem pDFPageListItem = PDFPageListItem.this;
                                    pDFPageListItem.addView(pDFPageListItem.repaintImageView);
                                    if (PDFPageListItem.this.searchView != null) {
                                        PDFPageListItem.this.searchView.bringToFront();
                                    }
                                }
                                PDFPageListItem.this.invalidate();
                                if (PDFPageListItem.this.listView != null) {
                                    PDFPageListItem.this.listView.exportImage(this, repaintAreaInfo.bm);
                                }
                            } catch (Exception unused5) {
                            }
                        }
                    };
                    try {
                        Bitmap createBitmap = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
                        this.repaintSyncTask.safeExecute(new RepaintAreaInfo(createBitmap, rect.width(), rect.height(), rect2));
                    } catch (OutOfMemoryError unused) {
                        ImageView imageView = this.repaintImageView;
                        if (imageView != null) {
                            Drawable drawable = imageView.getDrawable();
                            if (drawable instanceof BitmapDrawable) {
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                                if (bitmapDrawable.getBitmap() != null) {
                                    while (!this.lib.isDrawPageSyncFinished()) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (Exception unused2) {
                                        }
                                    }
                                    bitmapDrawable.getBitmap().recycle();
                                }
                            }
                        }
                        System.gc();
                        try {
                            Thread.sleep(50);
                            Bitmap createBitmap2 = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
                            this.repaintSyncTask.safeExecute(new RepaintAreaInfo(createBitmap2, rect.width(), rect.height(), rect2));
                        } catch (Exception | OutOfMemoryError unused3) {
                        }
                    }
                }
            }
        } else if (!this.mIsBlank && this.isOriginalBitmapValid) {
            this.listView.exportImage(this, this.originalBitmap);
        }
    }

    /* access modifiers changed from: protected */
    public void removeRepaintImageView() {
        SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo> safeAsyncTask = this.repaintSyncTask;
        if (safeAsyncTask != null) {
            safeAsyncTask.cancel(true);
            this.repaintSyncTask = null;
        }
        this.viewHeight = 0;
        this.viewWidth = 0;
        this.repaintArea = null;
        ImageView imageView = this.repaintImageView;
        if (imageView != null) {
            imageView.setImageBitmap((Bitmap) null);
        }
    }

    /* access modifiers changed from: protected */
    public void drawSerachView(Canvas canvas) {
        View view = this.searchView;
        if (view != null) {
            view.draw(canvas);
        }
    }

    public void dispose() {
        this.listView = null;
        SafeAsyncTask<Void, Void, Bitmap> safeAsyncTask = this.darwOriginalPageTask;
        if (safeAsyncTask != null) {
            safeAsyncTask.cancel(true);
            this.darwOriginalPageTask = null;
        }
        SafeAsyncTask<RepaintAreaInfo, Void, RepaintAreaInfo> safeAsyncTask2 = this.repaintSyncTask;
        if (safeAsyncTask2 != null) {
            safeAsyncTask2.cancel(true);
            this.repaintSyncTask = null;
        }
        ImageView imageView = this.originalImageView;
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    while (!this.lib.isDrawPageSyncFinished()) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception unused) {
                        }
                    }
                    bitmapDrawable.getBitmap().recycle();
                }
            }
            this.originalImageView.setImageBitmap((Bitmap) null);
        }
        ImageView imageView2 = this.repaintImageView;
        if (imageView2 != null) {
            Drawable drawable2 = imageView2.getDrawable();
            if (drawable2 instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable2 = (BitmapDrawable) drawable2;
                if (bitmapDrawable2.getBitmap() != null) {
                    while (!this.lib.isDrawPageSyncFinished()) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception unused2) {
                        }
                    }
                    bitmapDrawable2.getBitmap().recycle();
                }
            }
            this.repaintImageView.setImageBitmap((Bitmap) null);
        }
    }
}
