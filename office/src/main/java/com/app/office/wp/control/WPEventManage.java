package com.app.office.wp.control;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.app.office.common.hyperlink.Hyperlink;
import com.app.office.common.picture.PictureKit;
import com.app.office.constant.EventConstant;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IElement;
import com.app.office.system.IControl;
import com.app.office.system.beans.AEventManage;

public class WPEventManage extends AEventManage {
    private static final String TAG = "WPEventManage";
    private int oldX;
    private int oldY;
    protected Word word;

    public WPEventManage(Word word2, IControl iControl) {
        super(word2.getContext(), iControl);
        this.word = word2;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        try {
            super.onTouch(view, motionEvent);
            int action = motionEvent.getAction();
            if (action == 0) {
                PictureKit.instance().setDrawPictrue(true);
                processDown(view, motionEvent);
            } else if (action == 1) {
                if (this.zoomChange) {
                    this.zoomChange = false;
                    if (this.word.getCurrentRootType() == 0) {
                        this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
                    }
                    if (this.control.getMainFrame().isZoomAfterLayoutForWord()) {
                        this.control.actionEvent(EventConstant.WP_LAYOUT_NORMAL_VIEW, (Object) null);
                    }
                }
                this.word.getControl().actionEvent(20, (Object) null);
            }
        } catch (Exception e) {
            Log.d(TAG, "onTouch: " + e.getMessage());
            this.control.getSysKit().getErrorKit().writerLog(e);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void processDown(View view, MotionEvent motionEvent) {
        long viewToModel = this.word.viewToModel(convertCoorForX(motionEvent.getX()), convertCoorForY(motionEvent.getY()), false);
        if (this.word.getHighlight().isSelectText()) {
            this.word.getHighlight().removeHighlight();
            this.word.getStatus().setPressOffset(viewToModel);
            this.word.postInvalidate();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00c9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onScroll(android.view.MotionEvent r8, android.view.MotionEvent r9, float r10, float r11) {
        /*
            r7 = this;
            com.app.office.wp.control.Word r0 = r7.word
            com.app.office.wp.control.StatusManage r0 = r0.getStatus()
            boolean r0 = r0.isSelectTextStatus()
            r1 = 1
            if (r0 == 0) goto L_0x000e
            return r1
        L_0x000e:
            super.onScroll(r8, r9, r10, r11)
            float r8 = java.lang.Math.abs(r10)
            float r9 = java.lang.Math.abs(r11)
            r0 = 0
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 <= 0) goto L_0x0020
            r8 = 1
            goto L_0x0021
        L_0x0020:
            r8 = 0
        L_0x0021:
            com.app.office.wp.control.Word r9 = r7.word
            com.app.office.java.awt.Rectangle r9 = r9.getVisibleRect()
            int r2 = r9.x
            int r3 = r9.y
            com.app.office.wp.control.Word r4 = r7.word
            float r4 = r4.getZoom()
            com.app.office.wp.control.Word r5 = r7.word
            int r5 = r5.getCurrentRootType()
            if (r5 != r1) goto L_0x0061
            com.app.office.system.IControl r5 = r7.control
            com.app.office.system.IMainFrame r5 = r5.getMainFrame()
            boolean r5 = r5.isZoomAfterLayoutForWord()
            if (r5 == 0) goto L_0x0061
            com.app.office.wp.control.Word r5 = r7.word
            int r5 = r5.getWidth()
            com.app.office.wp.control.Word r6 = r7.word
            int r6 = r6.getWordWidth()
            if (r5 != r6) goto L_0x005a
            com.app.office.wp.control.Word r5 = r7.word
            int r5 = r5.getWidth()
            goto L_0x006b
        L_0x005a:
            com.app.office.wp.control.Word r5 = r7.word
            int r5 = r5.getWordWidth()
            goto L_0x0067
        L_0x0061:
            com.app.office.wp.control.Word r5 = r7.word
            int r5 = r5.getWordWidth()
        L_0x0067:
            float r5 = (float) r5
            float r5 = r5 * r4
            int r5 = (int) r5
        L_0x006b:
            com.app.office.wp.control.Word r6 = r7.word
            int r6 = r6.getWordHeight()
            float r6 = (float) r6
            float r6 = r6 * r4
            int r4 = (int) r6
            r6 = 0
            if (r8 == 0) goto L_0x009f
            int r8 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r8 <= 0) goto L_0x0090
            int r8 = r9.width
            int r8 = r8 + r2
            if (r8 >= r5) goto L_0x0090
            float r8 = (float) r2
            float r8 = r8 + r10
            int r8 = (int) r8
            int r10 = r9.width
            int r10 = r10 + r8
            if (r10 <= r5) goto L_0x008e
            int r8 = r9.width
            int r5 = r5 - r8
            r2 = r5
            goto L_0x00b6
        L_0x008e:
            r2 = r8
            goto L_0x00b6
        L_0x0090:
            int r8 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x00c7
            if (r2 <= 0) goto L_0x00c7
            float r8 = (float) r2
            float r8 = r8 + r10
            int r8 = (int) r8
            if (r8 >= 0) goto L_0x009c
            goto L_0x009d
        L_0x009c:
            r0 = r8
        L_0x009d:
            r2 = r0
            goto L_0x00b6
        L_0x009f:
            int r8 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            if (r8 <= 0) goto L_0x00b8
            int r8 = r9.height
            int r8 = r8 + r3
            if (r8 >= r4) goto L_0x00b8
            float r8 = (float) r3
            float r8 = r8 + r11
            int r8 = (int) r8
            int r10 = r9.height
            int r10 = r10 + r8
            if (r10 <= r4) goto L_0x00b5
            int r8 = r9.height
            int r4 = r4 - r8
            r3 = r4
            goto L_0x00b6
        L_0x00b5:
            r3 = r8
        L_0x00b6:
            r0 = 1
            goto L_0x00c7
        L_0x00b8:
            int r8 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x00c7
            if (r3 <= 0) goto L_0x00c7
            float r8 = (float) r3
            float r8 = r8 + r11
            int r8 = (int) r8
            if (r8 >= 0) goto L_0x00c4
            goto L_0x00c5
        L_0x00c4:
            r0 = r8
        L_0x00c5:
            r3 = r0
            goto L_0x00b6
        L_0x00c7:
            if (r0 == 0) goto L_0x00d0
            r7.isScroll = r1
            com.app.office.wp.control.Word r8 = r7.word
            r8.scrollTo(r2, r3)
        L_0x00d0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.wp.control.WPEventManage.onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float):boolean");
    }

    public void fling(int i, int i2) {
        int i3;
        super.fling(i, i2);
        Rectangle visibleRect = this.word.getVisibleRect();
        float zoom = this.word.getZoom();
        this.oldY = 0;
        this.oldX = 0;
        if (this.word.getCurrentRootType() != 1 || !this.control.getMainFrame().isZoomAfterLayoutForWord()) {
            i3 = (int) (((float) this.word.getWordWidth()) * zoom);
        } else if (this.word.getWidth() == this.word.getWordWidth()) {
            i3 = this.word.getWidth();
        } else {
            i3 = ((int) (((float) this.word.getWordWidth()) * zoom)) + 5;
        }
        if (Math.abs(i2) > Math.abs(i)) {
            this.oldY = visibleRect.y;
            this.mScroller.fling(visibleRect.x, visibleRect.y, 0, i2, 0, visibleRect.x, 0, ((int) (((float) this.word.getWordHeight()) * zoom)) - visibleRect.height);
        } else {
            this.oldX = visibleRect.x;
            this.mScroller.fling(visibleRect.x, visibleRect.y, i, 0, 0, i3 - visibleRect.width, visibleRect.y, 0);
        }
        this.word.postInvalidate();
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        super.onDoubleTapEvent(motionEvent);
        return true;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        IElement leaf;
        int hperlinkID;
        Hyperlink hyperlink;
        super.onSingleTapUp(motionEvent);
        if (motionEvent.getAction() == 1) {
            long viewToModel = this.word.viewToModel(convertCoorForX(motionEvent.getX()), convertCoorForY(motionEvent.getY()), false);
            if (viewToModel >= 0 && (leaf = this.word.getDocument().getLeaf(viewToModel)) != null && (hperlinkID = AttrManage.instance().getHperlinkID(leaf.getAttribute())) >= 0 && (hyperlink = this.control.getSysKit().getHyperlinkManage().getHyperlink(hperlinkID)) != null) {
                this.control.actionEvent(EventConstant.APP_HYPERLINK, hyperlink);
            }
        }
        return true;
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            this.isFling = true;
            PictureKit.instance().setDrawPictrue(false);
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if ((this.oldX == currX && this.oldY == currY) || (currX == this.word.getScrollX() && currY == this.word.getScrollY())) {
                PictureKit.instance().setDrawPictrue(true);
                this.mScroller.abortAnimation();
                this.word.postInvalidate();
                return;
            }
            this.oldX = currX;
            this.oldY = currY;
            this.word.scrollTo(currX, currY);
        } else if (!PictureKit.instance().isDrawPictrue()) {
            PictureKit.instance().setDrawPictrue(true);
            this.word.postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public int convertCoorForX(float f) {
        return (int) ((f + ((float) this.word.getScrollX())) / this.word.getZoom());
    }

    /* access modifiers changed from: protected */
    public int convertCoorForY(float f) {
        return (int) ((f + ((float) this.word.getScrollY())) / this.word.getZoom());
    }

    public void dispose() {
        super.dispose();
        this.word = null;
    }
}
