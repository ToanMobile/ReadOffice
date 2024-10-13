package com.app.office.pg.control;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import com.app.office.common.IOfficeToPicture;
import com.app.office.common.ISlideShow;
import com.app.office.common.picture.PictureKit;
import com.app.office.constant.EventConstant;
import com.app.office.java.awt.Dimension;
import com.app.office.pg.animate.ShapeAnimation;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGNotes;
import com.app.office.pg.model.PGSlide;
import com.app.office.pg.view.SlideDrawKit;
import com.app.office.pg.view.SlideShowView;
import com.app.office.simpletext.model.IDocument;
import com.app.office.system.IControl;
import com.app.office.system.IFind;
import com.app.office.system.SysKit;
import com.app.office.system.beans.CalloutView.CalloutView;
import com.app.office.system.beans.CalloutView.IExportListener;
import java.util.List;

public class Presentation extends FrameLayout implements IFind, IExportListener {
    private CalloutView callouts;
    /* access modifiers changed from: private */
    public IControl control;
    private int currentIndex = -1;
    private PGSlide currentSlide;
    private PGEditor editor;
    private PGEventManage eventManage;
    private float fitZoom = 1.0f;
    private boolean init;
    private boolean isConfigurationChanged;
    private int mHeight;
    private int mWidth;
    private PGFind pgFind;
    private PGModel pgModel;
    private PGPrintMode pgPrintMode;
    private int preShowSlideIndex = -1;
    private int slideIndex_SlideShow;
    private Rect slideSize = null;
    private SlideShowView slideView;
    private boolean slideshow;
    private float zoom = 1.0f;

    public int getPageIndex() {
        return -1;
    }

    public void initSlidebar() {
    }

    public void resetSearchResult() {
    }

    public Presentation(Activity activity, PGModel pGModel, IControl iControl) {
        super(activity);
        this.control = iControl;
        this.pgModel = pGModel;
        setLongClickable(true);
        this.pgFind = new PGFind(this);
        this.editor = new PGEditor(this);
        PGPrintMode pGPrintMode = new PGPrintMode(activity, iControl, pGModel, this.editor);
        this.pgPrintMode = pGPrintMode;
        addView(pGPrintMode);
    }

    public void initCalloutView() {
        if (!this.slideshow) {
            this.pgPrintMode.getListView().getCurrentPageView().initCalloutView();
        } else if (this.callouts == null) {
            CalloutView calloutView = new CalloutView(getContext(), this.control, this);
            this.callouts = calloutView;
            calloutView.setIndex(this.slideIndex_SlideShow);
            addView(this.callouts);
        }
    }

    public void exportImage() {
        if (this.slideshow) {
            createPicture();
            return;
        }
        PGPrintMode pGPrintMode = this.pgPrintMode;
        pGPrintMode.exportImage(pGPrintMode.getListView().getCurrentPageView(), (Bitmap) null);
    }

    public void init() {
        this.init = true;
        initSlidebar();
        this.pgPrintMode.init();
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        PGPrintMode pGPrintMode = this.pgPrintMode;
        if (pGPrintMode != null) {
            pGPrintMode.setBackgroundColor(i);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        PGPrintMode pGPrintMode = this.pgPrintMode;
        if (pGPrintMode != null) {
            pGPrintMode.setBackgroundResource(i);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        PGPrintMode pGPrintMode = this.pgPrintMode;
        if (pGPrintMode != null) {
            pGPrintMode.setBackgroundDrawable(drawable);
        }
    }

    public void setViewVisible(boolean z) {
        this.pgPrintMode.setVisible(z);
    }

    public boolean showLoadingSlide() {
        if (this.currentIndex >= getRealSlideCount()) {
            return false;
        }
        post(new Runnable() {
            public void run() {
                Presentation.this.setViewVisible(true);
            }
        });
        this.pgPrintMode.showSlideForIndex(this.currentIndex);
        return true;
    }

    public void showSlide(int i, boolean z) {
        if (!z) {
            this.control.getMainFrame().setFindBackForwardState(false);
        }
        if (i < this.pgModel.getSlideCount()) {
            if (!this.slideshow) {
                this.currentIndex = i;
                if (i < getRealSlideCount()) {
                    this.pgPrintMode.showSlideForIndex(i);
                } else {
                    setViewVisible(false);
                }
            } else {
                int i2 = this.currentIndex;
                this.currentIndex = i;
                PGSlide slide = this.pgModel.getSlide(i);
                this.currentSlide = slide;
                if (this.slideView == null) {
                    this.slideView = new SlideShowView(this, slide);
                }
                SlideShowView slideShowView = this.slideView;
                if (slideShowView != null) {
                    slideShowView.changeSlide(this.currentSlide);
                }
                if (i2 != this.currentIndex) {
                    this.control.actionEvent(20, (Object) null);
                    SlideDrawKit instance = SlideDrawKit.instance();
                    PGModel pGModel = this.pgModel;
                    instance.disposeOldSlideView(pGModel, pGModel.getSlide(i2));
                }
                postInvalidate();
                post(new Runnable() {
                    public void run() {
                        if (Presentation.this.control != null) {
                            Presentation.this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:10|11|12|13|14) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0029 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r5) {
        /*
            r4 = this;
            boolean r0 = r4.init
            if (r0 == 0) goto L_0x005e
            boolean r0 = r4.slideshow
            if (r0 != 0) goto L_0x0009
            goto L_0x005e
        L_0x0009:
            com.app.office.pg.view.SlideShowView r0 = r4.slideView     // Catch:{ NullPointerException -> 0x0050 }
            float r1 = r4.fitZoom     // Catch:{ NullPointerException -> 0x0050 }
            com.app.office.system.beans.CalloutView.CalloutView r2 = r4.callouts     // Catch:{ NullPointerException -> 0x0050 }
            r0.drawSlide(r5, r1, r2)     // Catch:{ NullPointerException -> 0x0050 }
            com.app.office.system.IControl r5 = r4.control     // Catch:{ NullPointerException -> 0x0050 }
            boolean r5 = r5.isAutoTest()     // Catch:{ NullPointerException -> 0x0050 }
            if (r5 == 0) goto L_0x003c
            int r5 = r4.currentIndex     // Catch:{ NullPointerException -> 0x0050 }
            int r0 = r4.getRealSlideCount()     // Catch:{ NullPointerException -> 0x0050 }
            r1 = 1
            int r0 = r0 - r1
            if (r5 >= r0) goto L_0x0031
            r2 = 500(0x1f4, double:2.47E-321)
            java.lang.Thread.sleep(r2)     // Catch:{ Exception -> 0x0029 }
        L_0x0029:
            int r5 = r4.currentIndex     // Catch:{ NullPointerException -> 0x0050 }
            int r5 = r5 + r1
            r0 = 0
            r4.showSlide(r5, r0)     // Catch:{ NullPointerException -> 0x0050 }
            goto L_0x003c
        L_0x0031:
            com.app.office.system.IControl r5 = r4.control     // Catch:{ NullPointerException -> 0x0050 }
            r0 = 22
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ NullPointerException -> 0x0050 }
            r5.actionEvent(r0, r1)     // Catch:{ NullPointerException -> 0x0050 }
        L_0x003c:
            int r5 = r4.preShowSlideIndex     // Catch:{ NullPointerException -> 0x0050 }
            int r0 = r4.currentIndex     // Catch:{ NullPointerException -> 0x0050 }
            if (r5 == r0) goto L_0x005e
            com.app.office.system.IControl r5 = r4.control     // Catch:{ NullPointerException -> 0x0050 }
            com.app.office.system.IMainFrame r5 = r5.getMainFrame()     // Catch:{ NullPointerException -> 0x0050 }
            r5.changePage()     // Catch:{ NullPointerException -> 0x0050 }
            int r5 = r4.currentIndex     // Catch:{ NullPointerException -> 0x0050 }
            r4.preShowSlideIndex = r5     // Catch:{ NullPointerException -> 0x0050 }
            goto L_0x005e
        L_0x0050:
            r5 = move-exception
            com.app.office.system.IControl r0 = r4.control
            com.app.office.system.SysKit r0 = r0.getSysKit()
            com.app.office.system.ErrorUtil r0 = r0.getErrorKit()
            r0.writerLog(r5)
        L_0x005e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.Presentation.onDraw(android.graphics.Canvas):void");
    }

    public void createPicture() {
        IOfficeToPicture officeToPicture = this.control.getOfficeToPicture();
        if (officeToPicture != null && officeToPicture.getModeType() == 1) {
            try {
                toPicture(officeToPicture);
            } catch (Exception unused) {
            }
        }
    }

    private void toPicture(IOfficeToPicture iOfficeToPicture) {
        if (!this.init || !this.slideshow) {
            ((PGPageListItem) this.pgPrintMode.getListView().getCurrentPageView()).addRepaintImageView((Bitmap) null);
        } else if (this.slideView.animationStoped()) {
            boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
            PictureKit.instance().setDrawPictrue(true);
            float f = this.slideshow ? this.fitZoom : this.zoom;
            Dimension pageSize = getPageSize();
            int min = Math.min((int) (((float) pageSize.width) * f), getWidth());
            int min2 = Math.min((int) (((float) pageSize.height) * f), getHeight());
            Bitmap bitmap = iOfficeToPicture.getBitmap(min, min2);
            if (bitmap != null) {
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(-16777216);
                this.slideView.drawSlideForToPicture(canvas, f, min, min2);
                this.control.getSysKit().getCalloutManager().drawPath(canvas, getCurrentIndex(), f);
                iOfficeToPicture.callBack(bitmap);
                PictureKit.instance().setDrawPictrue(isDrawPictrue);
            }
        }
    }

    public Bitmap getSnapshot(Bitmap bitmap) {
        boolean z;
        if (bitmap == null) {
            return null;
        }
        if (!this.init || !(z = this.slideshow)) {
            return this.pgPrintMode.getSnapshot(bitmap);
        }
        float f = z ? this.fitZoom : this.zoom;
        Dimension pageSize = getPageSize();
        float min = f * Math.min(((float) bitmap.getWidth()) / ((float) Math.min((int) (((float) pageSize.width) * f), getWidth())), ((float) bitmap.getHeight()) / ((float) Math.min((int) (((float) pageSize.height) * f), getHeight())));
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(-16777216);
        this.slideView.drawSlideForToPicture(canvas, min, bitmap.getWidth(), bitmap.getHeight());
        return bitmap;
    }

    public Bitmap slideToImage(int i) {
        if (i <= 0 || i > getRealSlideCount()) {
            return null;
        }
        SlideDrawKit instance = SlideDrawKit.instance();
        PGModel pGModel = this.pgModel;
        return instance.slideToImage(pGModel, this.editor, pGModel.getSlide(i - 1));
    }

    public Bitmap slideAreaToImage(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = i;
        if (i8 <= 0 || i8 > getRealSlideCount() || !SysKit.isValidateRect((int) getPageSize().getWidth(), (int) getPageSize().getHeight(), i2, i3, i4, i5)) {
            return null;
        }
        SlideDrawKit instance = SlideDrawKit.instance();
        PGModel pGModel = this.pgModel;
        return instance.slideAreaToImage(pGModel, this.editor, pGModel.getSlide(i8 - 1), i2, i3, i4, i5, i6, i7);
    }

    public Bitmap getThumbnail(int i, float f) {
        if (i <= 0 || i > getRealSlideCount()) {
            return null;
        }
        SlideDrawKit instance = SlideDrawKit.instance();
        PGModel pGModel = this.pgModel;
        return instance.getThumbnail(pGModel, this.editor, pGModel.getSlide(i - 1), f);
    }

    public String getSldieNote(int i) {
        if (i <= 0 || i > getSlideCount()) {
            return null;
        }
        PGNotes notes = this.pgModel.getSlide(i - 1).getNotes();
        if (notes == null) {
            return "";
        }
        return notes.getNotes();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.isConfigurationChanged = true;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        processPageSize(i, i2);
    }

    private void processPageSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        boolean z = this.isConfigurationChanged;
        if (z || this.slideshow) {
            if (z) {
                this.isConfigurationChanged = false;
            }
            this.fitZoom = getFitZoom();
            if (this.slideshow) {
                post(new Runnable() {
                    public void run() {
                        Presentation.this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                    }
                });
            }
        }
    }

    public float getFitZoom() {
        if (!this.slideshow) {
            return this.pgPrintMode.getFitZoom();
        }
        Dimension pageSize = getPageSize();
        return Math.min(((float) this.mWidth) / ((float) pageSize.width), ((float) this.mHeight) / ((float) pageSize.height));
    }

    public int getCurrentIndex() {
        return this.slideshow ? this.slideIndex_SlideShow : this.pgPrintMode.getCurrentPageNumber() - 1;
    }

    public int getSlideCount() {
        return this.pgModel.getSlideCount();
    }

    public int getRealSlideCount() {
        return this.pgModel.getRealSlideCount();
    }

    public PGSlide getSlide(int i) {
        return this.pgModel.getSlide(i);
    }

    public IControl getControl() {
        return this.control;
    }

    public int getmWidth() {
        return this.mWidth;
    }

    public void setmWidth(int i) {
        this.mWidth = i;
    }

    public int getmHeight() {
        return this.mHeight;
    }

    public void setmHeight(int i) {
        this.mHeight = i;
    }

    public void setSize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public float getZoom() {
        return this.slideshow ? this.fitZoom : this.pgPrintMode.getZoom();
    }

    public void setZoom(float f, int i, int i2) {
        if (!this.slideshow) {
            this.pgPrintMode.setZoom(f, i, i2);
        }
    }

    public void setFitSize(int i) {
        if (!this.slideshow) {
            this.pgPrintMode.setFitSize(i);
        }
    }

    public int getFitSizeState() {
        if (this.slideshow) {
            return 0;
        }
        return this.pgPrintMode.getFitSizeState();
    }

    public Dimension getPageSize() {
        return this.pgModel.getPageSize();
    }

    public IDocument getRenderersDoc() {
        return this.pgModel.getRenderersDoc();
    }

    public PGSlide getCurrentSlide() {
        if (this.slideshow) {
            return this.pgModel.getSlide(this.slideIndex_SlideShow);
        }
        return this.pgPrintMode.getCurrentPGSlide();
    }

    public boolean find(String str) {
        if (!this.slideshow) {
            return this.pgFind.find(str);
        }
        return false;
    }

    public boolean findBackward() {
        if (!this.slideshow) {
            return this.pgFind.findBackward();
        }
        return false;
    }

    public boolean findForward() {
        if (!this.slideshow) {
            return this.pgFind.findForward();
        }
        return false;
    }

    public String getSelectedText() {
        return this.editor.getHighlight().getSelectText();
    }

    public PGSlide getSlideMaster(int i) {
        return this.pgModel.getSlideMaster(i);
    }

    public PGEditor getEditor() {
        return this.editor;
    }

    public void setAnimationDuration(int i) {
        if (this.slideView == null) {
            this.slideView = new SlideShowView(this, this.currentSlide);
        }
        SlideShowView slideShowView = this.slideView;
        if (slideShowView != null) {
            slideShowView.setAnimationDuration(i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b0, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void beginSlideShow(int r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 <= 0) goto L_0x00af
            com.app.office.pg.model.PGModel r0 = r4.pgModel     // Catch:{ all -> 0x00b1 }
            int r0 = r0.getSlideCount()     // Catch:{ all -> 0x00b1 }
            if (r5 <= r0) goto L_0x000d
            goto L_0x00af
        L_0x000d:
            com.app.office.pg.control.PGEventManage r0 = r4.eventManage     // Catch:{ all -> 0x00b1 }
            if (r0 != 0) goto L_0x001a
            com.app.office.pg.control.PGEventManage r0 = new com.app.office.pg.control.PGEventManage     // Catch:{ all -> 0x00b1 }
            com.app.office.system.IControl r1 = r4.control     // Catch:{ all -> 0x00b1 }
            r0.<init>(r4, r1)     // Catch:{ all -> 0x00b1 }
            r4.eventManage = r0     // Catch:{ all -> 0x00b1 }
        L_0x001a:
            int r0 = r4.getCurrentIndex()     // Catch:{ all -> 0x00b1 }
            r1 = 1
            int r0 = r0 + r1
            r2 = 0
            if (r0 == r5) goto L_0x0025
            r0 = 1
            goto L_0x0026
        L_0x0025:
            r0 = 0
        L_0x0026:
            com.app.office.pg.control.PGEventManage r3 = r4.eventManage     // Catch:{ all -> 0x00b1 }
            r4.setOnTouchListener(r3)     // Catch:{ all -> 0x00b1 }
            com.app.office.system.IControl r3 = r4.control     // Catch:{ all -> 0x00b1 }
            com.app.office.system.SysKit r3 = r3.getSysKit()     // Catch:{ all -> 0x00b1 }
            com.app.office.system.beans.CalloutView.CalloutManager r3 = r3.getCalloutManager()     // Catch:{ all -> 0x00b1 }
            r3.setDrawingMode(r2)     // Catch:{ all -> 0x00b1 }
            com.app.office.pg.control.PGPrintMode r2 = r4.pgPrintMode     // Catch:{ all -> 0x00b1 }
            r3 = 8
            r2.setVisibility(r3)     // Catch:{ all -> 0x00b1 }
            r4.slideshow = r1     // Catch:{ all -> 0x00b1 }
            int r2 = r4.getWidth()     // Catch:{ all -> 0x00b1 }
            int r3 = r4.getHeight()     // Catch:{ all -> 0x00b1 }
            r4.processPageSize(r2, r3)     // Catch:{ all -> 0x00b1 }
            int r5 = r5 - r1
            r4.slideIndex_SlideShow = r5     // Catch:{ all -> 0x00b1 }
            com.app.office.pg.model.PGModel r2 = r4.pgModel     // Catch:{ all -> 0x00b1 }
            com.app.office.pg.model.PGSlide r5 = r2.getSlide(r5)     // Catch:{ all -> 0x00b1 }
            r4.currentSlide = r5     // Catch:{ all -> 0x00b1 }
            com.app.office.pg.view.SlideShowView r2 = r4.slideView     // Catch:{ all -> 0x00b1 }
            if (r2 != 0) goto L_0x0062
            com.app.office.pg.view.SlideShowView r2 = new com.app.office.pg.view.SlideShowView     // Catch:{ all -> 0x00b1 }
            r2.<init>(r4, r5)     // Catch:{ all -> 0x00b1 }
            r4.slideView = r2     // Catch:{ all -> 0x00b1 }
        L_0x0062:
            com.app.office.pg.view.SlideShowView r5 = r4.slideView     // Catch:{ all -> 0x00b1 }
            com.app.office.pg.model.PGSlide r2 = r4.currentSlide     // Catch:{ all -> 0x00b1 }
            r5.initSlideShow(r2, r1)     // Catch:{ all -> 0x00b1 }
            r5 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r4.setBackgroundColor(r5)     // Catch:{ all -> 0x00b1 }
            com.app.office.system.beans.CalloutView.CalloutView r5 = r4.callouts     // Catch:{ all -> 0x00b1 }
            if (r5 != 0) goto L_0x0086
            com.app.office.system.IControl r5 = r4.control     // Catch:{ all -> 0x00b1 }
            com.app.office.system.SysKit r5 = r5.getSysKit()     // Catch:{ all -> 0x00b1 }
            com.app.office.system.beans.CalloutView.CalloutManager r5 = r5.getCalloutManager()     // Catch:{ all -> 0x00b1 }
            boolean r5 = r5.isPathEmpty()     // Catch:{ all -> 0x00b1 }
            if (r5 != 0) goto L_0x008b
            r4.initCalloutView()     // Catch:{ all -> 0x00b1 }
            goto L_0x008b
        L_0x0086:
            int r1 = r4.slideIndex_SlideShow     // Catch:{ all -> 0x00b1 }
            r5.setIndex(r1)     // Catch:{ all -> 0x00b1 }
        L_0x008b:
            r4.postInvalidate()     // Catch:{ all -> 0x00b1 }
            if (r0 == 0) goto L_0x00a5
            com.app.office.system.IControl r5 = r4.getControl()     // Catch:{ all -> 0x00b1 }
            com.app.office.system.IMainFrame r5 = r5.getMainFrame()     // Catch:{ all -> 0x00b1 }
            if (r5 == 0) goto L_0x00a5
            com.app.office.system.IControl r5 = r4.getControl()     // Catch:{ all -> 0x00b1 }
            com.app.office.system.IMainFrame r5 = r5.getMainFrame()     // Catch:{ all -> 0x00b1 }
            r5.changePage()     // Catch:{ all -> 0x00b1 }
        L_0x00a5:
            com.app.office.pg.control.Presentation$4 r5 = new com.app.office.pg.control.Presentation$4     // Catch:{ all -> 0x00b1 }
            r5.<init>()     // Catch:{ all -> 0x00b1 }
            r4.post(r5)     // Catch:{ all -> 0x00b1 }
            monitor-exit(r4)     // Catch:{ all -> 0x00b1 }
            return
        L_0x00af:
            monitor-exit(r4)     // Catch:{ all -> 0x00b1 }
            return
        L_0x00b1:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00b1 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.Presentation.beginSlideShow(int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasNextSlide_Slideshow() {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.slideshow     // Catch:{ all -> 0x0017 }
            r1 = 0
            if (r0 == 0) goto L_0x0015
            int r0 = r4.slideIndex_SlideShow     // Catch:{ all -> 0x0017 }
            com.app.office.pg.model.PGModel r2 = r4.pgModel     // Catch:{ all -> 0x0017 }
            int r2 = r2.getSlideCount()     // Catch:{ all -> 0x0017 }
            r3 = 1
            int r2 = r2 - r3
            if (r0 >= r2) goto L_0x0013
            r1 = 1
        L_0x0013:
            monitor-exit(r4)     // Catch:{ all -> 0x0017 }
            return r1
        L_0x0015:
            monitor-exit(r4)     // Catch:{ all -> 0x0017 }
            return r1
        L_0x0017:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0017 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.Presentation.hasNextSlide_Slideshow():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000d, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasPreviousSlide_Slideshow() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.slideshow     // Catch:{ all -> 0x0010 }
            r1 = 0
            if (r0 == 0) goto L_0x000e
            int r0 = r3.slideIndex_SlideShow     // Catch:{ all -> 0x0010 }
            r2 = 1
            if (r0 < r2) goto L_0x000c
            r1 = 1
        L_0x000c:
            monitor-exit(r3)     // Catch:{ all -> 0x0010 }
            return r1
        L_0x000e:
            monitor-exit(r3)     // Catch:{ all -> 0x0010 }
            return r1
        L_0x0010:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0010 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.Presentation.hasPreviousSlide_Slideshow():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasNextAction_Slideshow() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.slideshow     // Catch:{ all -> 0x001e }
            if (r0 == 0) goto L_0x001b
            com.app.office.pg.view.SlideShowView r0 = r3.slideView     // Catch:{ all -> 0x001e }
            boolean r0 = r0.gotoNextSlide()     // Catch:{ all -> 0x001e }
            r1 = 1
            if (r0 == 0) goto L_0x0019
            int r0 = r3.slideIndex_SlideShow     // Catch:{ all -> 0x001e }
            com.app.office.pg.model.PGModel r2 = r3.pgModel     // Catch:{ all -> 0x001e }
            int r2 = r2.getSlideCount()     // Catch:{ all -> 0x001e }
            int r2 = r2 - r1
            if (r0 >= r2) goto L_0x001b
        L_0x0019:
            monitor-exit(r3)     // Catch:{ all -> 0x001e }
            return r1
        L_0x001b:
            r0 = 0
            monitor-exit(r3)     // Catch:{ all -> 0x001e }
            return r0
        L_0x001e:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x001e }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.Presentation.hasNextAction_Slideshow():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasPreviousAction_Slideshow() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.slideshow     // Catch:{ all -> 0x0017 }
            if (r0 == 0) goto L_0x0014
            int r0 = r2.slideIndex_SlideShow     // Catch:{ all -> 0x0017 }
            r1 = 1
            if (r0 >= r1) goto L_0x0012
            com.app.office.pg.view.SlideShowView r0 = r2.slideView     // Catch:{ all -> 0x0017 }
            boolean r0 = r0.gotopreviousSlide()     // Catch:{ all -> 0x0017 }
            if (r0 != 0) goto L_0x0014
        L_0x0012:
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            return r1
        L_0x0014:
            r0 = 0
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            return r0
        L_0x0017:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.Presentation.hasPreviousAction_Slideshow():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0132, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void slideShow(byte r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.slideshow     // Catch:{ all -> 0x0133 }
            if (r0 == 0) goto L_0x0131
            com.app.office.pg.view.SlideShowView r0 = r3.slideView     // Catch:{ all -> 0x0133 }
            boolean r0 = r0.animationStoped()     // Catch:{ all -> 0x0133 }
            if (r0 == 0) goto L_0x0131
            com.app.office.system.IControl r0 = r3.control     // Catch:{ all -> 0x0133 }
            com.app.office.system.SysKit r0 = r0.getSysKit()     // Catch:{ all -> 0x0133 }
            com.app.office.system.beans.CalloutView.CalloutManager r0 = r0.getCalloutManager()     // Catch:{ all -> 0x0133 }
            int r0 = r0.getDrawingMode()     // Catch:{ all -> 0x0133 }
            if (r0 == 0) goto L_0x001f
            goto L_0x0131
        L_0x001f:
            r0 = 4
            r1 = 1
            if (r4 != r0) goto L_0x0052
            boolean r0 = r3.hasPreviousSlide_Slideshow()     // Catch:{ all -> 0x0133 }
            if (r0 == 0) goto L_0x0052
            int r4 = r3.slideIndex_SlideShow     // Catch:{ all -> 0x0133 }
            int r4 = r4 - r1
            r3.slideIndex_SlideShow = r4     // Catch:{ all -> 0x0133 }
            if (r4 < 0) goto L_0x011b
            com.app.office.pg.view.SlideShowView r0 = r3.slideView     // Catch:{ all -> 0x0133 }
            com.app.office.pg.model.PGModel r2 = r3.pgModel     // Catch:{ all -> 0x0133 }
            com.app.office.pg.model.PGSlide r4 = r2.getSlide(r4)     // Catch:{ all -> 0x0133 }
            r0.initSlideShow(r4, r1)     // Catch:{ all -> 0x0133 }
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x011b
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            r4.changePage()     // Catch:{ all -> 0x0133 }
            goto L_0x011b
        L_0x0052:
            com.app.office.pg.view.SlideShowView r0 = r3.slideView     // Catch:{ all -> 0x0133 }
            boolean r0 = r0.isExitSlideShow()     // Catch:{ all -> 0x0133 }
            if (r0 == 0) goto L_0x0069
            com.app.office.system.IControl r4 = r3.control     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            r0 = 0
            r4.fullScreen(r0)     // Catch:{ all -> 0x0133 }
            r3.endSlideShow()     // Catch:{ all -> 0x0133 }
            monitor-exit(r3)     // Catch:{ all -> 0x0133 }
            return
        L_0x0069:
            r0 = 2
            if (r4 == r0) goto L_0x00db
            r0 = 3
            if (r4 == r0) goto L_0x00a1
            r0 = 5
            if (r4 == r0) goto L_0x0074
            goto L_0x011b
        L_0x0074:
            boolean r4 = r3.hasNextSlide_Slideshow()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x011b
            com.app.office.pg.view.SlideShowView r4 = r3.slideView     // Catch:{ all -> 0x0133 }
            com.app.office.pg.model.PGModel r0 = r3.pgModel     // Catch:{ all -> 0x0133 }
            int r2 = r3.slideIndex_SlideShow     // Catch:{ all -> 0x0133 }
            int r2 = r2 + r1
            r3.slideIndex_SlideShow = r2     // Catch:{ all -> 0x0133 }
            com.app.office.pg.model.PGSlide r0 = r0.getSlide(r2)     // Catch:{ all -> 0x0133 }
            r4.initSlideShow(r0, r1)     // Catch:{ all -> 0x0133 }
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x011b
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            r4.changePage()     // Catch:{ all -> 0x0133 }
            goto L_0x011b
        L_0x00a1:
            boolean r4 = r3.hasNextAction_Slideshow()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x011b
            com.app.office.pg.view.SlideShowView r4 = r3.slideView     // Catch:{ all -> 0x0133 }
            boolean r4 = r4.gotoNextSlide()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x00d5
            com.app.office.pg.view.SlideShowView r4 = r3.slideView     // Catch:{ all -> 0x0133 }
            com.app.office.pg.model.PGModel r0 = r3.pgModel     // Catch:{ all -> 0x0133 }
            int r2 = r3.slideIndex_SlideShow     // Catch:{ all -> 0x0133 }
            int r2 = r2 + r1
            r3.slideIndex_SlideShow = r2     // Catch:{ all -> 0x0133 }
            com.app.office.pg.model.PGSlide r0 = r0.getSlide(r2)     // Catch:{ all -> 0x0133 }
            r4.initSlideShow(r0, r1)     // Catch:{ all -> 0x0133 }
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x011b
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            r4.changePage()     // Catch:{ all -> 0x0133 }
            goto L_0x011b
        L_0x00d5:
            com.app.office.pg.view.SlideShowView r4 = r3.slideView     // Catch:{ all -> 0x0133 }
            r4.nextActionSlideShow()     // Catch:{ all -> 0x0133 }
            goto L_0x011b
        L_0x00db:
            boolean r4 = r3.hasPreviousAction_Slideshow()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x011b
            com.app.office.pg.view.SlideShowView r4 = r3.slideView     // Catch:{ all -> 0x0133 }
            boolean r4 = r4.gotopreviousSlide()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x0116
            com.app.office.pg.model.PGModel r4 = r3.pgModel     // Catch:{ all -> 0x0133 }
            int r0 = r3.slideIndex_SlideShow     // Catch:{ all -> 0x0133 }
            int r0 = r0 - r1
            r3.slideIndex_SlideShow = r0     // Catch:{ all -> 0x0133 }
            com.app.office.pg.model.PGSlide r4 = r4.getSlide(r0)     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x0100
            com.app.office.pg.view.SlideShowView r0 = r3.slideView     // Catch:{ all -> 0x0133 }
            r0.initSlideShow(r4, r1)     // Catch:{ all -> 0x0133 }
            com.app.office.pg.view.SlideShowView r4 = r3.slideView     // Catch:{ all -> 0x0133 }
            r4.gotoLastAction()     // Catch:{ all -> 0x0133 }
        L_0x0100:
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x011b
            com.app.office.system.IControl r4 = r3.getControl()     // Catch:{ all -> 0x0133 }
            com.app.office.system.IMainFrame r4 = r4.getMainFrame()     // Catch:{ all -> 0x0133 }
            r4.changePage()     // Catch:{ all -> 0x0133 }
            goto L_0x011b
        L_0x0116:
            com.app.office.pg.view.SlideShowView r4 = r3.slideView     // Catch:{ all -> 0x0133 }
            r4.previousActionSlideShow()     // Catch:{ all -> 0x0133 }
        L_0x011b:
            com.app.office.system.beans.CalloutView.CalloutView r4 = r3.callouts     // Catch:{ all -> 0x0133 }
            if (r4 == 0) goto L_0x0124
            int r0 = r3.slideIndex_SlideShow     // Catch:{ all -> 0x0133 }
            r4.setIndex(r0)     // Catch:{ all -> 0x0133 }
        L_0x0124:
            r3.postInvalidate()     // Catch:{ all -> 0x0133 }
            com.app.office.pg.control.Presentation$5 r4 = new com.app.office.pg.control.Presentation$5     // Catch:{ all -> 0x0133 }
            r4.<init>()     // Catch:{ all -> 0x0133 }
            r3.post(r4)     // Catch:{ all -> 0x0133 }
            monitor-exit(r3)     // Catch:{ all -> 0x0133 }
            return
        L_0x0131:
            monitor-exit(r3)     // Catch:{ all -> 0x0133 }
            return
        L_0x0133:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0133 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.Presentation.slideShow(byte):void");
    }

    public void endSlideShow() {
        synchronized (this) {
            if (this.slideshow) {
                this.control.getSysKit().getCalloutManager().setDrawingMode(0);
                setOnTouchListener((View.OnTouchListener) null);
                this.pgPrintMode.setVisibility(0);
                Object viewBackground = this.control.getMainFrame().getViewBackground();
                if (viewBackground != null) {
                    if (viewBackground instanceof Integer) {
                        setBackgroundColor(((Integer) viewBackground).intValue());
                    } else if (viewBackground instanceof Drawable) {
                        setBackgroundDrawable((Drawable) viewBackground);
                    }
                }
                this.currentIndex = this.slideIndex_SlideShow;
                this.slideshow = false;
                this.slideView.endSlideShow();
                showSlide(this.currentIndex, false);
                CalloutView calloutView = this.callouts;
                if (calloutView != null) {
                    calloutView.setVisibility(4);
                }
                post(new Runnable() {
                    public void run() {
                        ISlideShow slideShow = Presentation.this.control.getSlideShow();
                        if (slideShow != null) {
                            slideShow.exit();
                        }
                        Presentation.this.initSlidebar();
                    }
                });
            }
        }
    }

    public boolean isSlideShow() {
        return this.slideshow;
    }

    public PGFind getFind() {
        return this.pgFind;
    }

    public PGPrintMode getPrintMode() {
        return this.pgPrintMode;
    }

    public Rect getSlideDrawingRect() {
        if (!this.slideshow) {
            return null;
        }
        Rect rect = this.slideSize;
        if (rect == null) {
            this.slideSize = new Rect(this.slideView.getDrawingRect());
        } else {
            rect.set(this.slideView.getDrawingRect());
        }
        int width = this.slideSize.width();
        Rect rect2 = this.slideSize;
        int i = this.mWidth;
        rect2.set((i - width) / 2, 0, (i + width) / 2, this.mHeight);
        return this.slideSize;
    }

    public PGModel getPGModel() {
        return this.pgModel;
    }

    public int getSlideAnimationSteps(int i) {
        synchronized (this) {
            List<ShapeAnimation> slideShowAnimation = this.pgModel.getSlide(i - 1).getSlideShowAnimation();
            if (slideShowAnimation == null) {
                return 1;
            }
            int size = slideShowAnimation.size() + 1;
            return size;
        }
    }

    public Bitmap getSlideshowToImage(int i, int i2) {
        Bitmap slideshowToImage;
        synchronized (this) {
            if (this.slideView == null) {
                this.slideView = new SlideShowView(this, this.pgModel.getSlide(i - 1));
            }
            slideshowToImage = this.slideView.getSlideshowToImage(this.pgModel.getSlide(i - 1), i2);
        }
        return slideshowToImage;
    }

    public void dispose() {
        this.control = null;
        this.currentSlide = null;
        SlideShowView slideShowView = this.slideView;
        if (slideShowView != null) {
            slideShowView.dispose();
            this.slideView = null;
        }
        PGEventManage pGEventManage = this.eventManage;
        if (pGEventManage != null) {
            pGEventManage.dispose();
            this.eventManage = null;
        }
        this.pgModel.dispose();
        this.pgModel = null;
        PGFind pGFind = this.pgFind;
        if (pGFind != null) {
            pGFind.dispose();
            this.pgFind = null;
        }
    }
}
