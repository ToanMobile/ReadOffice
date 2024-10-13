package com.app.office.pg.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.app.office.common.picture.PictureKit;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGSlide;
import com.app.office.pg.view.SlideDrawKit;
import com.app.office.system.IControl;
import com.app.office.system.SysKit;
import com.app.office.system.beans.pagelist.APageListItem;
import com.app.office.system.beans.pagelist.APageListView;
import com.app.office.system.beans.pagelist.IPageListViewListener;

public class PGPrintMode extends FrameLayout implements IPageListViewListener {
    /* access modifiers changed from: private */
    public IControl control;
    /* access modifiers changed from: private */
    public PGEditor editor;
    /* access modifiers changed from: private */
    public APageListView listView;
    private Rect pageSize = new Rect();
    private Paint paint;
    /* access modifiers changed from: private */
    public PGModel pgModel;
    private int preShowPageIndex = -1;

    public boolean isInit() {
        return true;
    }

    public PGPrintMode(Context context) {
        super(context);
    }

    public PGPrintMode(Context context, IControl iControl, PGModel pGModel, PGEditor pGEditor) {
        super(context);
        this.control = iControl;
        this.pgModel = pGModel;
        this.editor = pGEditor;
        APageListView aPageListView = new APageListView(context, this);
        this.listView = aPageListView;
        addView(aPageListView, new FrameLayout.LayoutParams(-1, -1));
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        this.paint.setTypeface(Typeface.SANS_SERIF);
        this.paint.setTextSize(24.0f);
    }

    public void setVisible(boolean z) {
        if (z) {
            this.listView.setVisibility(0);
        } else {
            this.listView.setVisibility(8);
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

    public void setVisibility(int i) {
        super.setVisibility(i);
        exportImage(this.listView.getCurrentPageView(), (Bitmap) null);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawPageNubmer(canvas);
    }

    public void init() {
        if (((int) (getZoom() * 100.0f)) == 100) {
            setZoom(getFitZoom(), Integer.MIN_VALUE, Integer.MIN_VALUE);
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

    public APageListView getListView() {
        return this.listView;
    }

    public void nextPageView() {
        this.listView.nextPageView();
    }

    public void previousPageview() {
        this.listView.previousPageview();
    }

    public void showSlideForIndex(int i) {
        this.listView.showPDFPageForIndex(i);
    }

    public int getPageCount() {
        return Math.max(this.pgModel.getSlideCount(), 1);
    }

    public APageListItem getPageListItem(int i, View view, ViewGroup viewGroup) {
        Rect pageSize2 = getPageSize(i);
        return new PGPageListItem(this.listView, this.control, this.editor, pageSize2.width(), pageSize2.height());
    }

    public Rect getPageSize(int i) {
        Dimension pageSize2 = this.pgModel.getPageSize();
        if (pageSize2 == null) {
            this.pageSize.set(0, 0, getWidth(), getHeight());
        } else {
            this.pageSize.set(0, 0, pageSize2.width, pageSize2.height);
        }
        return this.pageSize;
    }

    public void exportImage(final APageListItem aPageListItem, Bitmap bitmap) {
        if (getControl() != null && (getParent() instanceof Presentation)) {
            PGFind pGFind = (PGFind) this.control.getFind();
            if (pGFind.isSetPointToVisible()) {
                pGFind.setSetPointToVisible(false);
                PGEditor pGEditor = this.editor;
                Rectangle modelToView = pGEditor.modelToView(pGEditor.getHighlight().getSelectStart(), new Rectangle(), false);
                if (!this.listView.isPointVisibleOnScreen(modelToView.x, modelToView.y)) {
                    this.listView.setItemPointVisibleOnScreen(modelToView.x, modelToView.y);
                    return;
                }
            }
            post(new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:7:0x0025, code lost:
                    r1 = java.lang.Math.min(r11.this$0.getWidth(), r5.getWidth());
                    r2 = java.lang.Math.min(r11.this$0.getHeight(), r5.getHeight());
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r11 = this;
                        com.app.office.pg.control.PGPrintMode r0 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.model.PGModel r0 = r0.pgModel     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r1 = r5     // Catch:{ Exception -> 0x0130 }
                        int r1 = r1.getPageIndex()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.model.PGSlide r6 = r0.getSlide(r1)     // Catch:{ Exception -> 0x0130 }
                        if (r6 == 0) goto L_0x0130
                        com.app.office.pg.control.PGPrintMode r0 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.IControl r0 = r0.getControl()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.common.IOfficeToPicture r0 = r0.getOfficeToPicture()     // Catch:{ Exception -> 0x0130 }
                        if (r0 == 0) goto L_0x0130
                        byte r1 = r0.getModeType()     // Catch:{ Exception -> 0x0130 }
                        r2 = 1
                        if (r1 != r2) goto L_0x0130
                        com.app.office.pg.control.PGPrintMode r1 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        int r1 = r1.getWidth()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r2 = r5     // Catch:{ Exception -> 0x0130 }
                        int r2 = r2.getWidth()     // Catch:{ Exception -> 0x0130 }
                        int r1 = java.lang.Math.min(r1, r2)     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r2 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        int r2 = r2.getHeight()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r3 = r5     // Catch:{ Exception -> 0x0130 }
                        int r3 = r3.getHeight()     // Catch:{ Exception -> 0x0130 }
                        int r2 = java.lang.Math.min(r2, r3)     // Catch:{ Exception -> 0x0130 }
                        android.graphics.Bitmap r8 = r0.getBitmap(r1, r2)     // Catch:{ Exception -> 0x0130 }
                        if (r8 != 0) goto L_0x004c
                        return
                    L_0x004c:
                        int r3 = r8.getWidth()     // Catch:{ Exception -> 0x0130 }
                        r4 = -1
                        r5 = 0
                        if (r3 != r1) goto L_0x00b6
                        int r3 = r8.getHeight()     // Catch:{ Exception -> 0x0130 }
                        if (r3 != r2) goto L_0x00b6
                        android.graphics.Canvas r1 = new android.graphics.Canvas     // Catch:{ Exception -> 0x0130 }
                        r1.<init>(r8)     // Catch:{ Exception -> 0x0130 }
                        r1.drawColor(r4)     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r2 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListView r2 = r2.listView     // Catch:{ Exception -> 0x0130 }
                        float r9 = r2.getZoom()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r2 = r5     // Catch:{ Exception -> 0x0130 }
                        int r2 = r2.getLeft()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r3 = r5     // Catch:{ Exception -> 0x0130 }
                        int r3 = r3.getTop()     // Catch:{ Exception -> 0x0130 }
                        int r4 = java.lang.Math.max(r2, r5)     // Catch:{ Exception -> 0x0130 }
                        int r4 = r4 - r2
                        int r2 = -r4
                        float r2 = (float) r2     // Catch:{ Exception -> 0x0130 }
                        int r4 = java.lang.Math.max(r3, r5)     // Catch:{ Exception -> 0x0130 }
                        int r4 = r4 - r3
                        int r3 = -r4
                        float r3 = (float) r3     // Catch:{ Exception -> 0x0130 }
                        r1.translate(r2, r3)     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.view.SlideDrawKit r2 = com.app.office.pg.view.SlideDrawKit.instance()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r3 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.model.PGModel r4 = r3.pgModel     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r3 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGEditor r5 = r3.editor     // Catch:{ Exception -> 0x0130 }
                        r3 = r1
                        r7 = r9
                        r2.drawSlide(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r2 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.IControl r2 = r2.control     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.SysKit r2 = r2.getSysKit()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.CalloutView.CalloutManager r2 = r2.getCalloutManager()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r3 = r5     // Catch:{ Exception -> 0x0130 }
                        int r3 = r3.getPageIndex()     // Catch:{ Exception -> 0x0130 }
                        r2.drawPath(r1, r3, r9)     // Catch:{ Exception -> 0x0130 }
                        goto L_0x012d
                    L_0x00b6:
                        int r3 = r8.getWidth()     // Catch:{ Exception -> 0x0130 }
                        float r3 = (float) r3     // Catch:{ Exception -> 0x0130 }
                        float r1 = (float) r1     // Catch:{ Exception -> 0x0130 }
                        float r3 = r3 / r1
                        int r1 = r8.getHeight()     // Catch:{ Exception -> 0x0130 }
                        float r1 = (float) r1     // Catch:{ Exception -> 0x0130 }
                        float r2 = (float) r2     // Catch:{ Exception -> 0x0130 }
                        float r1 = r1 / r2
                        float r1 = java.lang.Math.min(r3, r1)     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r2 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListView r2 = r2.listView     // Catch:{ Exception -> 0x0130 }
                        float r2 = r2.getZoom()     // Catch:{ Exception -> 0x0130 }
                        float r9 = r2 * r1
                        com.app.office.system.beans.pagelist.APageListItem r2 = r5     // Catch:{ Exception -> 0x0130 }
                        int r2 = r2.getLeft()     // Catch:{ Exception -> 0x0130 }
                        float r2 = (float) r2     // Catch:{ Exception -> 0x0130 }
                        float r2 = r2 * r1
                        int r2 = (int) r2     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r3 = r5     // Catch:{ Exception -> 0x0130 }
                        int r3 = r3.getTop()     // Catch:{ Exception -> 0x0130 }
                        float r3 = (float) r3     // Catch:{ Exception -> 0x0130 }
                        float r3 = r3 * r1
                        int r1 = (int) r3     // Catch:{ Exception -> 0x0130 }
                        android.graphics.Canvas r10 = new android.graphics.Canvas     // Catch:{ Exception -> 0x0130 }
                        r10.<init>(r8)     // Catch:{ Exception -> 0x0130 }
                        r10.drawColor(r4)     // Catch:{ Exception -> 0x0130 }
                        int r3 = java.lang.Math.max(r2, r5)     // Catch:{ Exception -> 0x0130 }
                        int r3 = r3 - r2
                        int r2 = -r3
                        float r2 = (float) r2     // Catch:{ Exception -> 0x0130 }
                        int r3 = java.lang.Math.max(r1, r5)     // Catch:{ Exception -> 0x0130 }
                        int r3 = r3 - r1
                        int r1 = -r3
                        float r1 = (float) r1     // Catch:{ Exception -> 0x0130 }
                        r10.translate(r2, r1)     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.view.SlideDrawKit r2 = com.app.office.pg.view.SlideDrawKit.instance()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r1 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.model.PGModel r4 = r1.pgModel     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r1 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGEditor r5 = r1.editor     // Catch:{ Exception -> 0x0130 }
                        r3 = r10
                        r7 = r9
                        r2.drawSlide(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0130 }
                        com.app.office.pg.control.PGPrintMode r1 = com.app.office.pg.control.PGPrintMode.this     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.IControl r1 = r1.control     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.SysKit r1 = r1.getSysKit()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.CalloutView.CalloutManager r1 = r1.getCalloutManager()     // Catch:{ Exception -> 0x0130 }
                        com.app.office.system.beans.pagelist.APageListItem r2 = r5     // Catch:{ Exception -> 0x0130 }
                        int r2 = r2.getPageIndex()     // Catch:{ Exception -> 0x0130 }
                        r1.drawPath(r10, r2, r9)     // Catch:{ Exception -> 0x0130 }
                    L_0x012d:
                        r0.callBack(r8)     // Catch:{ Exception -> 0x0130 }
                    L_0x0130:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.PGPrintMode.AnonymousClass1.run():void");
                }
            });
        }
    }

    public Bitmap getSnapshot(Bitmap bitmap) {
        PGPageListItem pGPageListItem;
        if (getControl() == null || !(getParent() instanceof Presentation) || (pGPageListItem = (PGPageListItem) getListView().getCurrentPageView()) == null) {
            return null;
        }
        PGSlide slide = this.pgModel.getSlide(pGPageListItem.getPageIndex());
        if (slide != null) {
            int min = Math.min(getWidth(), pGPageListItem.getWidth());
            int min2 = Math.min(getHeight(), pGPageListItem.getHeight());
            if (bitmap.getWidth() == min && bitmap.getHeight() == min2) {
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(-1);
                float zoom = this.listView.getZoom();
                int left = pGPageListItem.getLeft();
                int top = pGPageListItem.getTop();
                canvas.translate((float) (-(Math.max(left, 0) - left)), (float) (-(Math.max(top, 0) - top)));
                SlideDrawKit.instance().drawSlide(canvas, this.pgModel, this.editor, slide, zoom);
            } else {
                float min3 = Math.min(((float) bitmap.getWidth()) / ((float) min), ((float) bitmap.getHeight()) / ((float) min2));
                float zoom2 = this.listView.getZoom() * min3;
                int left2 = (int) (((float) pGPageListItem.getLeft()) * min3);
                int top2 = (int) (((float) pGPageListItem.getTop()) * min3);
                Canvas canvas2 = new Canvas(bitmap);
                canvas2.drawColor(-1);
                canvas2.translate((float) (-(Math.max(left2, 0) - left2)), (float) (-(Math.max(top2, 0) - top2)));
                SlideDrawKit.instance().drawSlide(canvas2, this.pgModel, this.editor, slide, zoom2);
            }
        }
        return bitmap;
    }

    public boolean isIgnoreOriginalSize() {
        return this.control.getMainFrame().isIgnoreOriginalSize();
    }

    public byte getPageListViewMovingPosition() {
        return this.control.getMainFrame().getPageListViewMovingPosition();
    }

    public Object getModel() {
        return this.pgModel;
    }

    public IControl getControl() {
        return this.control;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0048, code lost:
        r4 = (com.app.office.common.shape.TextBox) r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onEventMethod(android.view.View r9, android.view.MotionEvent r10, android.view.MotionEvent r11, float r12, float r13, byte r14) {
        /*
            r8 = this;
            r0 = 3
            if (r14 != r0) goto L_0x00a3
            if (r10 == 0) goto L_0x00a3
            int r0 = r10.getAction()
            r1 = 1
            if (r0 != r1) goto L_0x00a3
            com.app.office.system.beans.pagelist.APageListView r0 = r8.listView
            com.app.office.system.beans.pagelist.APageListItem r0 = r0.getCurrentPageView()
            if (r0 == 0) goto L_0x00a3
            com.app.office.system.beans.pagelist.APageListView r2 = r8.listView
            float r2 = r2.getZoom()
            float r3 = r10.getX()
            int r4 = r0.getLeft()
            float r4 = (float) r4
            float r3 = r3 - r4
            float r3 = r3 / r2
            int r3 = (int) r3
            float r4 = r10.getY()
            int r5 = r0.getTop()
            float r5 = (float) r5
            float r4 = r4 - r5
            float r4 = r4 / r2
            int r2 = (int) r4
            com.app.office.pg.model.PGModel r4 = r8.pgModel
            int r0 = r0.getPageIndex()
            com.app.office.pg.model.PGSlide r0 = r4.getSlide(r0)
            com.app.office.common.shape.IShape r0 = r0.getTextboxShape(r3, r2)
            if (r0 == 0) goto L_0x00a3
            short r4 = r0.getType()
            if (r4 != r1) goto L_0x00a3
            r4 = r0
            com.app.office.common.shape.TextBox r4 = (com.app.office.common.shape.TextBox) r4
            com.app.office.simpletext.view.STRoot r5 = r4.getRootView()
            if (r5 == 0) goto L_0x00a3
            com.app.office.java.awt.Rectangle r6 = r0.getBounds()
            int r6 = r6.x
            int r3 = r3 - r6
            com.app.office.java.awt.Rectangle r0 = r0.getBounds()
            int r0 = r0.y
            int r2 = r2 - r0
            r0 = 0
            long r2 = r5.viewToModel(r3, r2, r0)
            r5 = 0
            int r0 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r0 < 0) goto L_0x00a3
            com.app.office.simpletext.model.SectionElement r0 = r4.getElement()
            com.app.office.simpletext.model.IElement r0 = r0.getElement(r2)
            com.app.office.simpletext.model.ParagraphElement r0 = (com.app.office.simpletext.model.ParagraphElement) r0
            if (r0 == 0) goto L_0x00a3
            com.app.office.simpletext.model.IElement r0 = r0.getLeaf(r2)
            if (r0 == 0) goto L_0x00a3
            com.app.office.simpletext.model.AttrManage r2 = com.app.office.simpletext.model.AttrManage.instance()
            com.app.office.simpletext.model.IAttributeSet r0 = r0.getAttribute()
            int r0 = r2.getHperlinkID(r0)
            if (r0 < 0) goto L_0x00a3
            com.app.office.system.IControl r2 = r8.control
            com.app.office.system.SysKit r2 = r2.getSysKit()
            com.app.office.common.hyperlink.HyperlinkManage r2 = r2.getHyperlinkManage()
            com.app.office.common.hyperlink.Hyperlink r0 = r2.getHyperlink(r0)
            if (r0 == 0) goto L_0x00a3
            com.app.office.system.IControl r9 = r8.control
            r10 = 536870920(0x20000008, float:1.0842032E-19)
            r9.actionEvent(r10, r0)
            return r1
        L_0x00a3:
            com.app.office.system.IControl r0 = r8.control
            com.app.office.system.IMainFrame r1 = r0.getMainFrame()
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            r7 = r14
            boolean r9 = r1.onEventMethod(r2, r3, r4, r5, r6, r7)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.pg.control.PGPrintMode.onEventMethod(android.view.View, android.view.MotionEvent, android.view.MotionEvent, float, float, byte):boolean");
    }

    public void updateStutus(Object obj) {
        this.control.actionEvent(20, obj);
    }

    public void resetSearchResult(APageListItem aPageListItem) {
        if (getParent() instanceof Presentation) {
            Presentation presentation = (Presentation) getParent();
            if (presentation.getFind().getPageIndex() != aPageListItem.getPageIndex()) {
                presentation.getEditor().getHighlight().removeHighlight();
            }
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

    public void setDrawPictrue(boolean z) {
        PictureKit.instance().setDrawPictrue(z);
    }

    public PGSlide getCurrentPGSlide() {
        APageListItem currentPageView = this.listView.getCurrentPageView();
        if (currentPageView != null) {
            return this.pgModel.getSlide(currentPageView.getPageIndex());
        }
        return this.pgModel.getSlide(0);
    }

    private void drawPageNubmer(Canvas canvas) {
        if (this.control.getMainFrame().isDrawPageNumber()) {
            String valueOf = String.valueOf(this.listView.getCurrentPageNumber() + " / " + this.pgModel.getSlideCount());
            int measureText = (int) this.paint.measureText(valueOf);
            int descent = (int) (this.paint.descent() - this.paint.ascent());
            int width = (getWidth() - measureText) / 2;
            int height = (getHeight() - descent) + -20;
            Drawable pageNubmerDrawable = SysKit.getPageNubmerDrawable();
            pageNubmerDrawable.setBounds(width - 10, height - 10, measureText + width + 10, descent + height + 10);
            pageNubmerDrawable.draw(canvas);
            canvas.drawText(valueOf, (float) width, (float) ((int) (((float) height) - this.paint.ascent())), this.paint);
        }
        if (this.preShowPageIndex != this.listView.getCurrentPageNumber()) {
            changePage();
            this.preShowPageIndex = this.listView.getCurrentPageNumber();
        }
    }

    public void changePage() {
        this.control.getMainFrame().changePage();
    }

    public boolean isChangePage() {
        return this.control.getMainFrame().isChangePage();
    }

    public void dispose() {
        this.control = null;
        APageListView aPageListView = this.listView;
        if (aPageListView != null) {
            aPageListView.dispose();
        }
        this.pgModel = null;
        this.pageSize = null;
    }
}
