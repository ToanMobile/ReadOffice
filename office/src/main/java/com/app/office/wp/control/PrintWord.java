package com.app.office.wp.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.common.picture.PictureKit;
import com.app.office.constant.EventConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.IElement;
import com.app.office.system.IControl;
import com.app.office.system.SysKit;
import com.app.office.system.beans.pagelist.APageListItem;
import com.app.office.system.beans.pagelist.APageListView;
import com.app.office.system.beans.pagelist.IPageListViewListener;
import com.app.office.wp.view.PageRoot;
import com.app.office.wp.view.PageView;

public class PrintWord extends FrameLayout implements IPageListViewListener {
    /* access modifiers changed from: private */
    public IControl control;
    /* access modifiers changed from: private */
    public APageListView listView;
    /* access modifiers changed from: private */
    public PageRoot pageRoot;
    private Rect pageSize = new Rect();
    private Paint paint;
    private int prePageCount = -1;
    private int preShowPageIndex = -1;

    public void init() {
    }

    public boolean isInit() {
        return true;
    }

    public PrintWord(Context context) {
        super(context);
    }

    public PrintWord(Context context, IControl iControl, PageRoot pageRoot2) {
        super(context);
        this.control = iControl;
        this.pageRoot = pageRoot2;
        APageListView aPageListView = new APageListView(context, this);
        this.listView = aPageListView;
        addView(aPageListView, new FrameLayout.LayoutParams(-1, -1));
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        this.paint.setTypeface(Typeface.SANS_SERIF);
        this.paint.setTextSize(24.0f);
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

    public void showPDFPageForIndex(int i) {
        this.listView.showPDFPageForIndex(i);
    }

    public long viewToModel(int i, int i2, boolean z) {
        int currentPageNumber = this.listView.getCurrentPageNumber() - 1;
        if (currentPageNumber < 0 || currentPageNumber >= getPageCount()) {
            return 0;
        }
        return this.pageRoot.viewToModel(i, i2, z);
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        int currentPageNumber = this.listView.getCurrentPageNumber() - 1;
        return (currentPageNumber < 0 || currentPageNumber >= getPageCount()) ? rectangle : this.pageRoot.modelToView(j, rectangle, z);
    }

    public int getPageCount() {
        return Math.max(this.pageRoot.getChildCount(), 1);
    }

    public APageListItem getPageListItem(int i, View view, ViewGroup viewGroup) {
        Rect pageSize2 = getPageSize(i);
        return new WPPageListItem(this.listView, this.control, pageSize2.width(), pageSize2.height());
    }

    public Rect getPageSize(int i) {
        PageView pageView = this.pageRoot.getPageView(i);
        if (pageView != null) {
            this.pageSize.set(0, 0, pageView.getWidth(), pageView.getHeight());
        } else {
            IAttributeSet attribute = this.pageRoot.getDocument().getSection(0).getAttribute();
            this.pageSize.set(0, 0, (int) (((float) AttrManage.instance().getPageWidth(attribute)) * 0.06666667f), (int) (((float) AttrManage.instance().getPageHeight(attribute)) * 0.06666667f));
        }
        return this.pageSize;
    }

    public void exportImage(final APageListItem aPageListItem, Bitmap bitmap) {
        if (getControl() != null && (getParent() instanceof Word)) {
            WPFind wPFind = (WPFind) this.control.getFind();
            if (wPFind.isSetPointToVisible()) {
                wPFind.setSetPointToVisible(false);
                PageView pageView = this.pageRoot.getPageView(aPageListItem.getPageIndex());
                if (pageView != null) {
                    Rectangle modelToView = modelToView(((Word) getParent()).getHighlight().getSelectStart(), new Rectangle(), false);
                    modelToView.x -= pageView.getX();
                    modelToView.y -= pageView.getY();
                    if (!this.listView.isPointVisibleOnScreen(modelToView.x, modelToView.y)) {
                        this.listView.setItemPointVisibleOnScreen(modelToView.x, modelToView.y);
                        return;
                    }
                } else {
                    return;
                }
            }
            post(new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
                    r1 = java.lang.Math.min(r11.this$0.getWidth(), r5.getWidth());
                    r3 = java.lang.Math.min(r11.this$0.getHeight(), r5.getHeight());
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r11 = this;
                        com.app.office.wp.control.PrintWord r0 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.IControl r0 = r0.getControl()     // Catch:{ Exception -> 0x019b }
                        com.app.office.common.IOfficeToPicture r0 = r0.getOfficeToPicture()     // Catch:{ Exception -> 0x019b }
                        if (r0 == 0) goto L_0x019b
                        byte r1 = r0.getModeType()     // Catch:{ Exception -> 0x019b }
                        r2 = 1
                        if (r1 != r2) goto L_0x019b
                        com.app.office.wp.control.PrintWord r1 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        int r1 = r1.getWidth()     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r3 = r5     // Catch:{ Exception -> 0x019b }
                        int r3 = r3.getWidth()     // Catch:{ Exception -> 0x019b }
                        int r1 = java.lang.Math.min(r1, r3)     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.PrintWord r3 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        int r3 = r3.getHeight()     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r4 = r5     // Catch:{ Exception -> 0x019b }
                        int r4 = r4.getHeight()     // Catch:{ Exception -> 0x019b }
                        int r3 = java.lang.Math.min(r3, r4)     // Catch:{ Exception -> 0x019b }
                        android.graphics.Bitmap r4 = r0.getBitmap(r1, r3)     // Catch:{ Exception -> 0x019b }
                        if (r4 != 0) goto L_0x003a
                        return
                    L_0x003a:
                        com.app.office.wp.control.PrintWord r5 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        android.view.ViewParent r5 = r5.getParent()     // Catch:{ Exception -> 0x019b }
                        boolean r5 = r5 instanceof com.app.office.wp.control.Word     // Catch:{ Exception -> 0x019b }
                        r6 = 0
                        if (r5 == 0) goto L_0x0054
                        com.app.office.wp.control.PrintWord r5 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        android.view.ViewParent r5 = r5.getParent()     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.Word r5 = (com.app.office.wp.control.Word) r5     // Catch:{ Exception -> 0x019b }
                        com.app.office.simpletext.control.IHighlight r5 = r5.getHighlight()     // Catch:{ Exception -> 0x019b }
                        r5.setPaintHighlight(r6)     // Catch:{ Exception -> 0x019b }
                    L_0x0054:
                        int r5 = r4.getWidth()     // Catch:{ Exception -> 0x019b }
                        r7 = -1
                        if (r5 != r1) goto L_0x00e3
                        int r5 = r4.getHeight()     // Catch:{ Exception -> 0x019b }
                        if (r5 != r3) goto L_0x00e3
                        android.graphics.Canvas r1 = new android.graphics.Canvas     // Catch:{ Exception -> 0x019b }
                        r1.<init>(r4)     // Catch:{ Exception -> 0x019b }
                        r1.drawColor(r7)     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.PrintWord r3 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListView r3 = r3.listView     // Catch:{ Exception -> 0x019b }
                        float r3 = r3.getZoom()     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.PrintWord r5 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.view.PageRoot r5 = r5.pageRoot     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r7 = r5     // Catch:{ Exception -> 0x019b }
                        int r7 = r7.getPageIndex()     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.view.PageView r5 = r5.getPageView(r7)     // Catch:{ Exception -> 0x019b }
                        if (r5 == 0) goto L_0x017f
                        r1.save()     // Catch:{ Exception -> 0x019b }
                        int r7 = r5.getX()     // Catch:{ Exception -> 0x019b }
                        int r7 = -r7
                        float r7 = (float) r7     // Catch:{ Exception -> 0x019b }
                        float r7 = r7 * r3
                        int r8 = r5.getY()     // Catch:{ Exception -> 0x019b }
                        int r8 = -r8
                        float r8 = (float) r8     // Catch:{ Exception -> 0x019b }
                        float r8 = r8 * r3
                        r1.translate(r7, r8)     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r7 = r5     // Catch:{ Exception -> 0x019b }
                        int r7 = r7.getLeft()     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r8 = r5     // Catch:{ Exception -> 0x019b }
                        int r8 = r8.getTop()     // Catch:{ Exception -> 0x019b }
                        int r9 = java.lang.Math.max(r7, r6)     // Catch:{ Exception -> 0x019b }
                        int r9 = r9 - r7
                        int r9 = -r9
                        int r10 = java.lang.Math.max(r8, r6)     // Catch:{ Exception -> 0x019b }
                        int r10 = r10 - r8
                        int r10 = -r10
                        r5.drawForPrintMode(r1, r9, r10, r3)     // Catch:{ Exception -> 0x019b }
                        r1.restore()     // Catch:{ Exception -> 0x019b }
                        int r5 = java.lang.Math.max(r7, r6)     // Catch:{ Exception -> 0x019b }
                        int r5 = r5 - r7
                        int r5 = -r5
                        float r5 = (float) r5     // Catch:{ Exception -> 0x019b }
                        int r6 = java.lang.Math.max(r8, r6)     // Catch:{ Exception -> 0x019b }
                        int r6 = r6 - r8
                        int r6 = -r6
                        float r6 = (float) r6     // Catch:{ Exception -> 0x019b }
                        r1.translate(r5, r6)     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.PrintWord r5 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.IControl r5 = r5.control     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.SysKit r5 = r5.getSysKit()     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.CalloutView.CalloutManager r5 = r5.getCalloutManager()     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r6 = r5     // Catch:{ Exception -> 0x019b }
                        int r6 = r6.getPageIndex()     // Catch:{ Exception -> 0x019b }
                        r5.drawPath(r1, r6, r3)     // Catch:{ Exception -> 0x019b }
                        goto L_0x017f
                    L_0x00e3:
                        com.app.office.wp.control.PrintWord r5 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.view.PageRoot r5 = r5.pageRoot     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r8 = r5     // Catch:{ Exception -> 0x019b }
                        int r8 = r8.getPageIndex()     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.view.PageView r5 = r5.getPageView(r8)     // Catch:{ Exception -> 0x019b }
                        if (r5 == 0) goto L_0x017f
                        int r8 = r4.getWidth()     // Catch:{ Exception -> 0x019b }
                        float r8 = (float) r8     // Catch:{ Exception -> 0x019b }
                        float r1 = (float) r1     // Catch:{ Exception -> 0x019b }
                        float r8 = r8 / r1
                        int r1 = r4.getHeight()     // Catch:{ Exception -> 0x019b }
                        float r1 = (float) r1     // Catch:{ Exception -> 0x019b }
                        float r3 = (float) r3     // Catch:{ Exception -> 0x019b }
                        float r1 = r1 / r3
                        float r1 = java.lang.Math.min(r8, r1)     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.PrintWord r3 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListView r3 = r3.listView     // Catch:{ Exception -> 0x019b }
                        float r3 = r3.getZoom()     // Catch:{ Exception -> 0x019b }
                        float r3 = r3 * r1
                        com.app.office.system.beans.pagelist.APageListItem r8 = r5     // Catch:{ Exception -> 0x019b }
                        int r8 = r8.getLeft()     // Catch:{ Exception -> 0x019b }
                        float r8 = (float) r8     // Catch:{ Exception -> 0x019b }
                        float r8 = r8 * r1
                        int r8 = (int) r8     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r9 = r5     // Catch:{ Exception -> 0x019b }
                        int r9 = r9.getTop()     // Catch:{ Exception -> 0x019b }
                        float r9 = (float) r9     // Catch:{ Exception -> 0x019b }
                        float r9 = r9 * r1
                        int r1 = (int) r9     // Catch:{ Exception -> 0x019b }
                        android.graphics.Canvas r9 = new android.graphics.Canvas     // Catch:{ Exception -> 0x019b }
                        r9.<init>(r4)     // Catch:{ Exception -> 0x019b }
                        r9.save()     // Catch:{ Exception -> 0x019b }
                        r9.drawColor(r7)     // Catch:{ Exception -> 0x019b }
                        int r7 = r5.getX()     // Catch:{ Exception -> 0x019b }
                        int r7 = -r7
                        float r7 = (float) r7     // Catch:{ Exception -> 0x019b }
                        float r7 = r7 * r3
                        int r10 = r5.getY()     // Catch:{ Exception -> 0x019b }
                        int r10 = -r10
                        float r10 = (float) r10     // Catch:{ Exception -> 0x019b }
                        float r10 = r10 * r3
                        r9.translate(r7, r10)     // Catch:{ Exception -> 0x019b }
                        int r7 = java.lang.Math.max(r8, r6)     // Catch:{ Exception -> 0x019b }
                        int r7 = r7 - r8
                        int r7 = -r7
                        int r10 = java.lang.Math.max(r1, r6)     // Catch:{ Exception -> 0x019b }
                        int r10 = r10 - r1
                        int r10 = -r10
                        r5.drawForPrintMode(r9, r7, r10, r3)     // Catch:{ Exception -> 0x019b }
                        r9.restore()     // Catch:{ Exception -> 0x019b }
                        int r5 = java.lang.Math.max(r8, r6)     // Catch:{ Exception -> 0x019b }
                        int r5 = r5 - r8
                        int r5 = -r5
                        float r5 = (float) r5     // Catch:{ Exception -> 0x019b }
                        int r6 = java.lang.Math.max(r1, r6)     // Catch:{ Exception -> 0x019b }
                        int r6 = r6 - r1
                        int r1 = -r6
                        float r1 = (float) r1     // Catch:{ Exception -> 0x019b }
                        r9.translate(r5, r1)     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.PrintWord r1 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.IControl r1 = r1.control     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.SysKit r1 = r1.getSysKit()     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.CalloutView.CalloutManager r1 = r1.getCalloutManager()     // Catch:{ Exception -> 0x019b }
                        com.app.office.system.beans.pagelist.APageListItem r5 = r5     // Catch:{ Exception -> 0x019b }
                        int r5 = r5.getPageIndex()     // Catch:{ Exception -> 0x019b }
                        r1.drawPath(r9, r5, r3)     // Catch:{ Exception -> 0x019b }
                    L_0x017f:
                        com.app.office.wp.control.PrintWord r1 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        android.view.ViewParent r1 = r1.getParent()     // Catch:{ Exception -> 0x019b }
                        boolean r1 = r1 instanceof com.app.office.wp.control.Word     // Catch:{ Exception -> 0x019b }
                        if (r1 == 0) goto L_0x0198
                        com.app.office.wp.control.PrintWord r1 = com.app.office.wp.control.PrintWord.this     // Catch:{ Exception -> 0x019b }
                        android.view.ViewParent r1 = r1.getParent()     // Catch:{ Exception -> 0x019b }
                        com.app.office.wp.control.Word r1 = (com.app.office.wp.control.Word) r1     // Catch:{ Exception -> 0x019b }
                        com.app.office.simpletext.control.IHighlight r1 = r1.getHighlight()     // Catch:{ Exception -> 0x019b }
                        r1.setPaintHighlight(r2)     // Catch:{ Exception -> 0x019b }
                    L_0x0198:
                        r0.callBack(r4)     // Catch:{ Exception -> 0x019b }
                    L_0x019b:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.control.PrintWord.AnonymousClass1.run():void");
                }
            });
        }
    }

    public Bitmap getSnapshot(Bitmap bitmap) {
        APageListItem currentPageView = getListView().getCurrentPageView();
        if (currentPageView == null) {
            return null;
        }
        int min = Math.min(getWidth(), currentPageView.getWidth());
        int min2 = Math.min(getHeight(), currentPageView.getHeight());
        if (getParent() instanceof Word) {
            ((Word) getParent()).getHighlight().setPaintHighlight(false);
        }
        if (bitmap.getWidth() == min && bitmap.getHeight() == min2) {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(-1);
            float zoom = this.listView.getZoom();
            PageView pageView = this.pageRoot.getPageView(currentPageView.getPageIndex());
            if (pageView != null) {
                canvas.translate(((float) (-pageView.getX())) * zoom, ((float) (-pageView.getY())) * zoom);
                int left = currentPageView.getLeft();
                int top = currentPageView.getTop();
                pageView.drawForPrintMode(canvas, -(Math.max(left, 0) - left), -(Math.max(top, 0) - top), zoom);
            }
        } else {
            PageView pageView2 = this.pageRoot.getPageView(currentPageView.getPageIndex());
            if (pageView2 != null) {
                float min3 = Math.min(((float) bitmap.getWidth()) / ((float) min), ((float) bitmap.getHeight()) / ((float) min2));
                float zoom2 = this.listView.getZoom() * min3;
                int left2 = (int) (((float) currentPageView.getLeft()) * min3);
                int top2 = (int) (((float) currentPageView.getTop()) * min3);
                Canvas canvas2 = new Canvas(bitmap);
                canvas2.drawColor(-1);
                canvas2.translate(((float) (-pageView2.getX())) * zoom2, ((float) (-pageView2.getY())) * zoom2);
                pageView2.drawForPrintMode(canvas2, -(Math.max(left2, 0) - left2), -(Math.max(top2, 0) - top2), zoom2);
            }
        }
        if (getParent() instanceof Word) {
            ((Word) getParent()).getHighlight().setPaintHighlight(true);
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
        return this.pageRoot;
    }

    public IControl getControl() {
        return this.control;
    }

    public boolean onEventMethod(View view, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, byte b) {
        APageListItem currentPageView;
        PageView pageView;
        IElement leaf;
        int hperlinkID;
        Hyperlink hyperlink;
        if (!(b != 3 || motionEvent == null || motionEvent.getAction() != 1 || (currentPageView = this.listView.getCurrentPageView()) == null || (pageView = this.pageRoot.getPageView(currentPageView.getPageIndex())) == null)) {
            float zoom = this.listView.getZoom();
            long viewToModel = pageView.viewToModel(((int) ((motionEvent.getX() - ((float) currentPageView.getLeft())) / zoom)) + pageView.getX(), ((int) ((motionEvent.getY() - ((float) currentPageView.getTop())) / zoom)) + pageView.getY(), false);
            if (viewToModel >= 0 && (leaf = pageView.getDocument().getLeaf(viewToModel)) != null && (hperlinkID = AttrManage.instance().getHperlinkID(leaf.getAttribute())) >= 0 && (hyperlink = this.control.getSysKit().getHyperlinkManage().getHyperlink(hperlinkID)) != null) {
                this.control.actionEvent(EventConstant.APP_HYPERLINK, hyperlink);
            }
        }
        return this.control.getMainFrame().onEventMethod(view, motionEvent, motionEvent2, f, f2, b);
    }

    public void updateStutus(Object obj) {
        this.control.actionEvent(20, obj);
    }

    public void resetSearchResult(APageListItem aPageListItem) {
        if (getParent() instanceof Word) {
            Word word = (Word) getParent();
            if (word.getFind().getPageIndex() != aPageListItem.getPageIndex()) {
                word.getHighlight().removeHighlight();
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

    public PageView getCurrentPageView() {
        APageListItem currentPageView = this.listView.getCurrentPageView();
        if (currentPageView != null) {
            return this.pageRoot.getPageView(currentPageView.getPageIndex());
        }
        return null;
    }

    private void drawPageNubmer(Canvas canvas) {
        if (this.control.getMainFrame().isDrawPageNumber()) {
            String valueOf = String.valueOf(this.listView.getCurrentPageNumber() + " / " + this.pageRoot.getChildCount());
            int measureText = (int) this.paint.measureText(valueOf);
            int descent = (int) (this.paint.descent() - this.paint.ascent());
            int width = (getWidth() - measureText) / 2;
            int height = (getHeight() - descent) + -20;
            Drawable pageNubmerDrawable = SysKit.getPageNubmerDrawable();
            pageNubmerDrawable.setBounds(width - 10, height - 10, measureText + width + 10, descent + height + 10);
            pageNubmerDrawable.draw(canvas);
            canvas.drawText(valueOf, (float) width, (float) ((int) (((float) height) - this.paint.ascent())), this.paint);
        }
        if (this.preShowPageIndex != this.listView.getCurrentPageNumber() || this.prePageCount != getPageCount()) {
            changePage();
            this.preShowPageIndex = this.listView.getCurrentPageNumber();
            this.prePageCount = getPageCount();
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
        this.pageRoot = null;
        this.pageSize = null;
    }
}
