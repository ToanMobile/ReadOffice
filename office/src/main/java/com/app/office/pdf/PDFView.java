package com.app.office.pdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.app.office.common.IOfficeToPicture;
import com.app.office.fc.pdf.PDFLib;
import com.app.office.system.IControl;
import com.app.office.system.IFind;
import com.app.office.system.SysKit;
import com.app.office.system.beans.pagelist.APageListItem;
import com.app.office.system.beans.pagelist.APageListView;
import com.app.office.system.beans.pagelist.IPageListViewListener;

public class PDFView extends FrameLayout implements IPageListViewListener {
    /* access modifiers changed from: private */
    public IControl control;
    private AsyncTask<Void, Object, Bitmap> exportTask;
    private PDFFind find;
    private APageListView listView;
    private Rect[] pagesSize;
    /* access modifiers changed from: private */
    public Paint paint;
    /* access modifiers changed from: private */
    public PDFLib pdfLib;
    private int preShowPageIndex = -1;

    public void setDrawPictrue(boolean z) {
    }

    public PDFView(Context context) {
        super(context);
    }

    public PDFView(Context context, PDFLib pDFLib, IControl iControl) {
        super(context);
        this.control = iControl;
        this.pdfLib = pDFLib;
        APageListView aPageListView = new APageListView(context, this);
        this.listView = aPageListView;
        addView(aPageListView, new FrameLayout.LayoutParams(-1, -1));
        this.find = new PDFFind(this);
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        this.paint.setTypeface(Typeface.SANS_SERIF);
        this.paint.setTextSize(24.0f);
        if (!pDFLib.hasPasswordSync()) {
            this.pagesSize = pDFLib.getAllPagesSize();
        }
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        APageListView aPageListView = this.listView;
        if (aPageListView != null) {
            aPageListView.setBackgroundColor(i);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        APageListView aPageListView = this.listView;
        if (aPageListView != null) {
            aPageListView.setBackgroundResource(i);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        APageListView aPageListView = this.listView;
        if (aPageListView != null) {
            aPageListView.setBackgroundDrawable(drawable);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawPageNubmer(canvas);
    }

    public void init() {
        if (this.pdfLib.hasPasswordSync()) {
            new PasswordDialog(this.control, this.pdfLib).show();
        }
    }

    public void setZoom(float f, int i, int i2) {
        this.listView.setZoom(f, i, i2);
    }

    public void setFitSize(int i) {
        this.listView.setFitSize(i);
    }

    public int getFitSizeState() {
        return this.listView.getFitSizeState();
    }

    public float getZoom() {
        return this.listView.getZoom();
    }

    public float getFitZoom() {
        return this.listView.getFitZoom();
    }

    public int getCurrentPageNumber() {
        return this.listView.getCurrentPageNumber();
    }

    public IFind getFind() {
        return this.find;
    }

    public PDFLib getPDFLib() {
        return this.pdfLib;
    }

    public APageListView getListView() {
        return this.listView;
    }

    public void nextPageView() {
        this.listView.nextPageView();
    }

    public void previousPageview() {
        this.listView.previousPageview();
    }

    public void showPDFPageForIndex(int i) {
        this.listView.showPDFPageForIndex(i);
    }

    public Bitmap pageToImage(int i) {
        if (i <= 0 || i > getPageCount()) {
            return null;
        }
        int i2 = i - 1;
        Rect pageSize = getPageSize(i2);
        Bitmap createBitmap = Bitmap.createBitmap(pageSize.width(), pageSize.height(), Bitmap.Config.ARGB_8888);
        this.pdfLib.drawPageSync(createBitmap, i2, (float) pageSize.width(), (float) pageSize.height(), 0, 0, pageSize.width(), pageSize.height(), 1);
        return createBitmap;
    }

    public Bitmap getThumbnail(int i, float f) {
        Bitmap bitmap = null;
        if (i <= 0 || i > getPageCount()) {
            return null;
        }
        int i2 = i - 1;
        Rect pageSize = getPageSize(i2);
        int width = (int) (((float) pageSize.width()) * f);
        int height = (int) (((float) pageSize.height()) * f);
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.pdfLib.drawPageSync(bitmap, i2, (float) width, (float) height, 0, 0, width, height, 1);
            return bitmap;
        } catch (OutOfMemoryError e) {
            this.control.getSysKit().getErrorKit().writerLog(e);
            return bitmap;
        }
    }

    public Bitmap pageAreaToImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = i;
        if (i8 > 0 && i8 <= getPageCount()) {
            int i9 = i8 - 1;
            Rect pageSize = getPageSize(i9);
            if (!SysKit.isValidateRect(pageSize.width(), pageSize.height(), i2, i3, i4, i5)) {
                return null;
            }
            float f = (float) i4;
            float f2 = (float) i5;
            float min = Math.min(((float) i6) / f, ((float) i7) / f2);
            int i10 = (int) (f * min);
            int i11 = (int) (f2 * min);
            try {
                Bitmap createBitmap = Bitmap.createBitmap(i10, i11, Bitmap.Config.ARGB_8888);
                if (createBitmap == null) {
                    return null;
                }
                this.pdfLib.drawPageSync(createBitmap, i9, (float) ((int) (((float) pageSize.width()) * min)), (float) ((int) (((float) pageSize.height()) * min)), (int) (((float) i2) * min), (int) (((float) i3) * min), i10, i11, 1);
                return createBitmap;
            } catch (OutOfMemoryError unused) {
            }
        }
        return null;
    }

    public void passwordVerified() {
        if (this.listView != null) {
            this.pagesSize = this.pdfLib.getAllPagesSize();
            this.control.getMainFrame().openFileFinish();
            this.listView.init();
        }
    }

    public int getPageCount() {
        return this.pdfLib.getPageCountSync();
    }

    public APageListItem getPageListItem(int i, View view, ViewGroup viewGroup) {
        Rect pageSize = getPageSize(i);
        return new PDFPageListItem(this.listView, this.control, pageSize.width(), pageSize.height());
    }

    public Rect getPageSize(int i) {
        if (i < 0) {
            return null;
        }
        Rect[] rectArr = this.pagesSize;
        if (i >= rectArr.length) {
            return null;
        }
        return rectArr[i];
    }

    public void exportImage(final APageListItem aPageListItem, final Bitmap bitmap) {
        if (getControl() != null && bitmap != null) {
            if (this.find.isSetPointToVisible()) {
                this.find.setSetPointToVisible(false);
                RectF[] searchResult = this.find.getSearchResult();
                if (searchResult != null && searchResult.length > 0 && !this.listView.isPointVisibleOnScreen((int) searchResult[0].left, (int) searchResult[0].top)) {
                    this.listView.setItemPointVisibleOnScreen((int) searchResult[0].left, (int) searchResult[0].top);
                    return;
                }
            }
            AsyncTask<Void, Object, Bitmap> asyncTask = this.exportTask;
            if (asyncTask != null) {
                asyncTask.cancel(true);
                this.exportTask = null;
            }
            this.exportTask = new AsyncTask<Void, Object, Bitmap>() {
                private boolean isCancal;

                /* access modifiers changed from: protected */
                public void onPreExecute() {
                }

                /* access modifiers changed from: protected */
                /* JADX WARNING: Can't wrap try/catch for region: R(7:24|(1:26)(1:27)|28|29|30|31|32) */
                /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
                    r3 = java.lang.Math.min(r0.this$0.getWidth(), r7.getWidth());
                    r4 = java.lang.Math.min(r0.this$0.getHeight(), r7.getHeight());
                 */
                /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x0152 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public android.graphics.Bitmap doInBackground(java.lang.Void... r18) {
                    /*
                        r17 = this;
                        r0 = r17
                        com.app.office.pdf.PDFView r1 = com.app.office.pdf.PDFView.this
                        com.app.office.system.IControl r1 = r1.control
                        r2 = 0
                        if (r1 == 0) goto L_0x018c
                        com.app.office.pdf.PDFView r1 = com.app.office.pdf.PDFView.this
                        com.app.office.fc.pdf.PDFLib r1 = r1.pdfLib
                        if (r1 != 0) goto L_0x0015
                        goto L_0x018c
                    L_0x0015:
                        com.app.office.pdf.PDFView r1 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.IControl r1 = r1.control     // Catch:{ Exception -> 0x018c }
                        com.app.office.common.IOfficeToPicture r1 = r1.getOfficeToPicture()     // Catch:{ Exception -> 0x018c }
                        if (r1 == 0) goto L_0x018c
                        byte r3 = r1.getModeType()     // Catch:{ Exception -> 0x018c }
                        r4 = 1
                        if (r3 != r4) goto L_0x018c
                        com.app.office.pdf.PDFView r3 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        int r3 = r3.getWidth()     // Catch:{ Exception -> 0x018c }
                        android.graphics.Bitmap r4 = r7     // Catch:{ Exception -> 0x018c }
                        int r4 = r4.getWidth()     // Catch:{ Exception -> 0x018c }
                        int r3 = java.lang.Math.min(r3, r4)     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r4 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        int r4 = r4.getHeight()     // Catch:{ Exception -> 0x018c }
                        android.graphics.Bitmap r5 = r7     // Catch:{ Exception -> 0x018c }
                        int r5 = r5.getHeight()     // Catch:{ Exception -> 0x018c }
                        int r4 = java.lang.Math.min(r4, r5)     // Catch:{ Exception -> 0x018c }
                        android.graphics.Bitmap r1 = r1.getBitmap(r3, r4)     // Catch:{ Exception -> 0x018c }
                        if (r1 != 0) goto L_0x004f
                        return r2
                    L_0x004f:
                        android.graphics.Canvas r5 = new android.graphics.Canvas     // Catch:{ Exception -> 0x018c }
                        r5.<init>(r1)     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r6 = r6     // Catch:{ Exception -> 0x018c }
                        int r6 = r6.getLeft()     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r7 = r6     // Catch:{ Exception -> 0x018c }
                        int r7 = r7.getTop()     // Catch:{ Exception -> 0x018c }
                        int r8 = r1.getWidth()     // Catch:{ Exception -> 0x018c }
                        r9 = 0
                        if (r8 != r3) goto L_0x00d2
                        int r8 = r1.getHeight()     // Catch:{ Exception -> 0x018c }
                        if (r8 != r4) goto L_0x00d2
                        android.graphics.Bitmap r8 = r7     // Catch:{ Exception -> 0x018c }
                        int r8 = r8.getWidth()     // Catch:{ Exception -> 0x018c }
                        if (r8 != r3) goto L_0x0081
                        android.graphics.Bitmap r3 = r7     // Catch:{ Exception -> 0x018c }
                        int r3 = r3.getHeight()     // Catch:{ Exception -> 0x018c }
                        if (r3 == r4) goto L_0x007e
                        goto L_0x0081
                    L_0x007e:
                        r3 = 0
                        r4 = 0
                        goto L_0x0095
                    L_0x0081:
                        com.app.office.system.beans.pagelist.APageListItem r3 = r6     // Catch:{ Exception -> 0x018c }
                        int r3 = r3.getLeft()     // Catch:{ Exception -> 0x018c }
                        int r3 = java.lang.Math.min(r9, r3)     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r4 = r6     // Catch:{ Exception -> 0x018c }
                        int r4 = r4.getTop()     // Catch:{ Exception -> 0x018c }
                        int r4 = java.lang.Math.min(r9, r4)     // Catch:{ Exception -> 0x018c }
                    L_0x0095:
                        android.graphics.Bitmap r8 = r7     // Catch:{ Exception -> 0x018c }
                        float r3 = (float) r3     // Catch:{ Exception -> 0x018c }
                        float r4 = (float) r4     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r10 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        android.graphics.Paint r10 = r10.paint     // Catch:{ Exception -> 0x018c }
                        r5.drawBitmap(r8, r3, r4, r10)     // Catch:{ Exception -> 0x018c }
                        int r3 = java.lang.Math.max(r6, r9)     // Catch:{ Exception -> 0x018c }
                        int r3 = r3 - r6
                        int r3 = -r3
                        float r3 = (float) r3     // Catch:{ Exception -> 0x018c }
                        int r4 = java.lang.Math.max(r7, r9)     // Catch:{ Exception -> 0x018c }
                        int r4 = r4 - r7
                        int r4 = -r4
                        float r4 = (float) r4     // Catch:{ Exception -> 0x018c }
                        r5.translate(r3, r4)     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r3 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.IControl r3 = r3.control     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.SysKit r3 = r3.getSysKit()     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.CalloutView.CalloutManager r3 = r3.getCalloutManager()     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r4 = r6     // Catch:{ Exception -> 0x018c }
                        int r4 = r4.getPageIndex()     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r6 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        float r6 = r6.getZoom()     // Catch:{ Exception -> 0x018c }
                        r3.drawPath(r5, r4, r6)     // Catch:{ Exception -> 0x018c }
                        goto L_0x018b
                    L_0x00d2:
                        android.graphics.Matrix r8 = new android.graphics.Matrix     // Catch:{ Exception -> 0x018c }
                        r8.<init>()     // Catch:{ Exception -> 0x018c }
                        int r10 = r1.getWidth()     // Catch:{ Exception -> 0x018c }
                        float r10 = (float) r10     // Catch:{ Exception -> 0x018c }
                        float r3 = (float) r3     // Catch:{ Exception -> 0x018c }
                        float r10 = r10 / r3
                        int r3 = r1.getHeight()     // Catch:{ Exception -> 0x018c }
                        float r3 = (float) r3     // Catch:{ Exception -> 0x018c }
                        float r4 = (float) r4     // Catch:{ Exception -> 0x018c }
                        float r3 = r3 / r4
                        r8.postScale(r10, r3)     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r4 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        float r4 = r4.getZoom()     // Catch:{ Exception -> 0x018c }
                        r11 = 1232348160(0x49742400, float:1000000.0)
                        float r4 = r4 * r11
                        int r4 = (int) r4     // Catch:{ Exception -> 0x018c }
                        r11 = 1000000(0xf4240, float:1.401298E-39)
                        if (r4 != r11) goto L_0x012f
                        com.app.office.system.beans.pagelist.APageListItem r4 = r6     // Catch:{ Exception -> 0x018c }
                        int r4 = r4.getLeft()     // Catch:{ Exception -> 0x018c }
                        int r4 = java.lang.Math.min(r4, r9)     // Catch:{ Exception -> 0x018c }
                        float r4 = (float) r4     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r11 = r6     // Catch:{ Exception -> 0x018c }
                        int r11 = r11.getTop()     // Catch:{ Exception -> 0x018c }
                        int r11 = java.lang.Math.min(r11, r9)     // Catch:{ Exception -> 0x018c }
                        float r11 = (float) r11     // Catch:{ Exception -> 0x018c }
                        r8.postTranslate(r4, r11)     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r4 = r6     // Catch:{ Exception -> 0x018c }
                        int r4 = r4.getLeft()     // Catch:{ Exception -> 0x018c }
                        float r4 = (float) r4     // Catch:{ Exception -> 0x018c }
                        float r4 = r4 * r10
                        int r4 = (int) r4     // Catch:{ Exception -> 0x018c }
                        int r4 = java.lang.Math.min(r9, r4)     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r10 = r6     // Catch:{ Exception -> 0x018c }
                        int r10 = r10.getTop()     // Catch:{ Exception -> 0x018c }
                        float r10 = (float) r10     // Catch:{ Exception -> 0x018c }
                        float r10 = r10 * r3
                        int r3 = (int) r10     // Catch:{ Exception -> 0x018c }
                        int r3 = java.lang.Math.min(r9, r3)     // Catch:{ Exception -> 0x018c }
                        goto L_0x0131
                    L_0x012f:
                        r3 = 0
                        r4 = 0
                    L_0x0131:
                        android.graphics.Bitmap r10 = r7     // Catch:{ OutOfMemoryError -> 0x0152 }
                        r11 = 0
                        r12 = 0
                        int r13 = r10.getWidth()     // Catch:{ OutOfMemoryError -> 0x0152 }
                        android.graphics.Bitmap r14 = r7     // Catch:{ OutOfMemoryError -> 0x0152 }
                        int r14 = r14.getHeight()     // Catch:{ OutOfMemoryError -> 0x0152 }
                        r16 = 1
                        r15 = r8
                        android.graphics.Bitmap r10 = android.graphics.Bitmap.createBitmap(r10, r11, r12, r13, r14, r15, r16)     // Catch:{ OutOfMemoryError -> 0x0152 }
                        float r4 = (float) r4     // Catch:{ OutOfMemoryError -> 0x0152 }
                        float r3 = (float) r3     // Catch:{ OutOfMemoryError -> 0x0152 }
                        com.app.office.pdf.PDFView r11 = com.app.office.pdf.PDFView.this     // Catch:{ OutOfMemoryError -> 0x0152 }
                        android.graphics.Paint r11 = r11.paint     // Catch:{ OutOfMemoryError -> 0x0152 }
                        r5.drawBitmap(r10, r4, r3, r11)     // Catch:{ OutOfMemoryError -> 0x0152 }
                        goto L_0x015d
                    L_0x0152:
                        android.graphics.Bitmap r3 = r7     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r4 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        android.graphics.Paint r4 = r4.paint     // Catch:{ Exception -> 0x018c }
                        r5.drawBitmap(r3, r8, r4)     // Catch:{ Exception -> 0x018c }
                    L_0x015d:
                        int r3 = java.lang.Math.max(r6, r9)     // Catch:{ Exception -> 0x018c }
                        int r3 = r3 - r6
                        int r3 = -r3
                        float r3 = (float) r3     // Catch:{ Exception -> 0x018c }
                        int r4 = java.lang.Math.max(r7, r9)     // Catch:{ Exception -> 0x018c }
                        int r4 = r4 - r7
                        int r4 = -r4
                        float r4 = (float) r4     // Catch:{ Exception -> 0x018c }
                        r5.translate(r3, r4)     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r3 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.IControl r3 = r3.control     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.SysKit r3 = r3.getSysKit()     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.CalloutView.CalloutManager r3 = r3.getCalloutManager()     // Catch:{ Exception -> 0x018c }
                        com.app.office.system.beans.pagelist.APageListItem r4 = r6     // Catch:{ Exception -> 0x018c }
                        int r4 = r4.getPageIndex()     // Catch:{ Exception -> 0x018c }
                        com.app.office.pdf.PDFView r6 = com.app.office.pdf.PDFView.this     // Catch:{ Exception -> 0x018c }
                        float r6 = r6.getZoom()     // Catch:{ Exception -> 0x018c }
                        r3.drawPath(r5, r4, r6)     // Catch:{ Exception -> 0x018c }
                    L_0x018b:
                        return r1
                    L_0x018c:
                        return r2
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.app.office.pdf.PDFView.AnonymousClass1.doInBackground(java.lang.Void[]):android.graphics.Bitmap");
                }

                /* access modifiers changed from: protected */
                public void onCancelled() {
                    this.isCancal = true;
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        try {
                            if (PDFView.this.control == null) {
                                return;
                            }
                            if (!this.isCancal) {
                                IOfficeToPicture officeToPicture = PDFView.this.control.getOfficeToPicture();
                                if (officeToPicture != null && officeToPicture.getModeType() == 1) {
                                    officeToPicture.callBack(bitmap);
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            };
        }
    }

    public Bitmap getSanpshot(Bitmap bitmap) {
        APageListItem currentPageView;
        Bitmap bitmap2 = bitmap;
        if (bitmap2 == null || (currentPageView = this.listView.getCurrentPageView()) == null) {
            return null;
        }
        int min = Math.min(getWidth(), currentPageView.getWidth());
        int min2 = Math.min(getHeight(), currentPageView.getHeight());
        float width = ((float) bitmap.getWidth()) / ((float) min);
        float height = ((float) bitmap.getHeight()) / ((float) min2);
        int left = (int) (((float) currentPageView.getLeft()) * width);
        int top = (int) (((float) currentPageView.getTop()) * height);
        int max = Math.max(left, 0) - left;
        int max2 = Math.max(top, 0) - top;
        float pageWidth = ((float) currentPageView.getPageWidth()) * width * getZoom();
        Bitmap bitmap3 = bitmap;
        float f = pageWidth;
        this.pdfLib.drawPageSync(bitmap3, currentPageView.getPageIndex(), f, ((float) currentPageView.getPageHeight()) * height * getZoom(), max, max2, bitmap.getWidth(), bitmap.getHeight(), 1);
        if (max == 0 && pageWidth < ((float) bitmap.getWidth()) && min == currentPageView.getWidth()) {
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(-16777216);
            new Canvas(bitmap2).drawRect(((float) bitmap.getWidth()) - (((float) bitmap.getWidth()) - pageWidth), 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight(), this.paint);
        }
        return bitmap2;
    }

    public boolean isInit() {
        return !this.pdfLib.hasPasswordSync();
    }

    public boolean isIgnoreOriginalSize() {
        return this.control.getMainFrame().isIgnoreOriginalSize();
    }

    public byte getPageListViewMovingPosition() {
        return this.control.getMainFrame().getPageListViewMovingPosition();
    }

    public Object getModel() {
        return this.pdfLib;
    }

    public IControl getControl() {
        return this.control;
    }

    public boolean onEventMethod(View view, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, byte b) {
        return this.control.getMainFrame().onEventMethod(view, motionEvent, motionEvent2, f, f2, b);
    }

    public void updateStutus(Object obj) {
        this.control.actionEvent(20, obj);
    }

    public void resetSearchResult(APageListItem aPageListItem) {
        if (this.find != null && aPageListItem.getPageIndex() != this.find.getPageIndex()) {
            this.find.resetSearchResult();
        }
    }

    public boolean isTouchZoom() {
        return this.control.getMainFrame().isTouchZoom();
    }

    public boolean isShowZoomingMsg() {
        return this.control.getMainFrame().isShowZoomingMsg();
    }

    public void changeZoom() {
        this.control.getMainFrame().changeZoom();
    }

    public boolean isChangePage() {
        return this.control.getMainFrame().isChangePage();
    }

    private void drawPageNubmer(Canvas canvas) {
        if (this.control.getMainFrame().isDrawPageNumber()) {
            String valueOf = String.valueOf(this.listView.getCurrentPageNumber() + " / " + this.pdfLib.getPageCountSync());
            int measureText = (int) this.paint.measureText(valueOf);
            int descent = (int) (this.paint.descent() - this.paint.ascent());
            int width = (getWidth() - measureText) / 2;
            int height = (getHeight() - descent) + -20;
            Drawable pageNubmerDrawable = SysKit.getPageNubmerDrawable();
            pageNubmerDrawable.setBounds(width - 10, height - 10, measureText + width + 10, descent + height + 10);
            pageNubmerDrawable.draw(canvas);
            canvas.drawText(valueOf, (float) width, (float) ((int) (((float) height) - this.paint.ascent())), this.paint);
        }
        if (this.listView.isInit() && this.preShowPageIndex != this.listView.getCurrentPageNumber()) {
            this.control.getMainFrame().changePage();
            this.preShowPageIndex = this.listView.getCurrentPageNumber();
        }
    }

    public void dispose() {
        PDFFind pDFFind = this.find;
        if (pDFFind != null) {
            pDFFind.dispose();
        }
        PDFFind pDFFind2 = this.find;
        if (pDFFind2 != null) {
            pDFFind2.dispose();
            this.find = null;
        }
        PDFLib pDFLib = this.pdfLib;
        if (pDFLib != null) {
            pDFLib.setStopFlagSync(1);
            this.pdfLib = null;
        }
        APageListView aPageListView = this.listView;
        if (aPageListView != null) {
            aPageListView.dispose();
        }
        this.control = null;
    }
}
