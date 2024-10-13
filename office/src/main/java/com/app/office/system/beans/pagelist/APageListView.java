package com.app.office.system.beans.pagelist;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.AdapterView;
import java.util.Iterator;
import java.util.LinkedList;

public class APageListView extends AdapterView<Adapter> {
    private static final int GAP = 20;
    private SparseArray<APageListItem> childViewsCache = new SparseArray<>(3);
    /* access modifiers changed from: private */
    public int currentIndex;
    private APageListEventManage eventManage;
    private boolean isConfigurationChanged;
    private boolean isDoRequestLayout = true;
    /* access modifiers changed from: private */
    public boolean isInit;
    private boolean isInitZoom;
    /* access modifiers changed from: private */
    public boolean isResetLayout;
    private Adapter pageAdapter;
    /* access modifiers changed from: private */
    public IPageListViewListener pageListViewListener;
    private LinkedList<APageListItem> pageViewCache = new LinkedList<>();
    private float zoom = 1.0f;

    public View getSelectedView() {
        return null;
    }

    public void setSelection(int i) {
    }

    public APageListView(Context context) {
        super(context);
    }

    public APageListView(Context context, IPageListViewListener iPageListViewListener) {
        super(context);
        this.pageListViewListener = iPageListViewListener;
        this.eventManage = new APageListEventManage(this);
        this.pageAdapter = new APageListAdapter(this);
        setLongClickable(true);
        post(new Runnable() {
            public void run() {
                if (APageListView.this.pageListViewListener != null && APageListView.this.pageListViewListener.isInit()) {
                    APageListView.this.init();
                }
            }
        });
    }

    public void init() {
        this.isInit = true;
        requestLayout();
    }

    public void requestLayout() {
        if (this.isDoRequestLayout) {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt instanceof APageListItem) {
                APageListItem aPageListItem = (APageListItem) childAt;
                aPageListItem.measure(((int) (((float) aPageListItem.getPageWidth()) * this.zoom)) | 1073741824, 1073741824 | ((int) (((float) aPageListItem.getPageHeight()) * this.zoom)));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.isConfigurationChanged) {
            float fitZoom = getFitZoom();
            if (this.zoom < fitZoom) {
                setZoom(fitZoom, false);
                this.isInit = false;
                postDelayed(new Runnable() {
                    public void run() {
                        boolean unused = APageListView.this.isInit = true;
                        boolean unused2 = APageListView.this.isResetLayout = true;
                        APageListView.this.requestLayout();
                    }
                }, 1);
                this.pageListViewListener.changeZoom();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        APageListItem currentPageView = getCurrentPageView();
        if (currentPageView != null && currentPageView.getControl().getSysKit().getCalloutManager().getDrawingMode() != 0) {
            return false;
        }
        this.eventManage.processOnTouch(motionEvent);
        this.pageListViewListener.onEventMethod(this, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 0);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.isInit) {
            if (this.pageListViewListener.getPageListViewMovingPosition() == 0) {
                layout_Horizontal();
            } else {
                layout_Vertical();
            }
            invalidate();
            if (this.isConfigurationChanged) {
                this.isConfigurationChanged = false;
                APageListItem currentPageView = getCurrentPageView();
                if (currentPageView != null) {
                    postRepaint(currentPageView);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:66:0x01c0  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:80:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void layout_Horizontal() {
        /*
            r11 = this;
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r0 = r11.childViewsCache
            int r1 = r11.currentIndex
            java.lang.Object r0 = r0.get(r1)
            com.app.office.system.beans.pagelist.APageListItem r0 = (com.app.office.system.beans.pagelist.APageListItem) r0
            boolean r1 = r11.isResetLayout
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x00d9
            if (r0 == 0) goto L_0x0093
            int r1 = r0.getLeft()
            int r1 = java.lang.Math.abs(r1)
            int r4 = r0.getWidth()
            if (r1 >= r4) goto L_0x0093
            android.graphics.Point r1 = r11.getScreenSizeOffset(r0)
            int r4 = r0.getLeft()
            int r5 = r0.getMeasuredWidth()
            int r4 = r4 + r5
            int r5 = r1.x
            int r4 = r4 + r5
            int r4 = r4 + 10
            com.app.office.system.beans.pagelist.APageListEventManage r5 = r11.eventManage
            int r5 = r5.getScrollX()
            int r4 = r4 + r5
            int r5 = r11.getWidth()
            int r5 = r5 / 2
            if (r4 >= r5) goto L_0x0062
            int r4 = r11.currentIndex
            int r4 = r4 + r3
            android.widget.Adapter r5 = r11.pageAdapter
            int r5 = r5.getCount()
            if (r4 >= r5) goto L_0x0062
            com.app.office.system.beans.pagelist.APageListEventManage r4 = r11.eventManage
            boolean r4 = r4.isOnFling()
            if (r4 != 0) goto L_0x0062
            r11.postUnRepaint(r0)
            com.app.office.system.beans.pagelist.APageListEventManage r1 = r11.eventManage
            r11.post(r1)
            int r1 = r11.currentIndex
            int r1 = r1 + r3
            r11.currentIndex = r1
            goto L_0x0093
        L_0x0062:
            int r4 = r0.getLeft()
            int r1 = r1.x
            int r4 = r4 - r1
            int r4 = r4 + -10
            com.app.office.system.beans.pagelist.APageListEventManage r1 = r11.eventManage
            int r1 = r1.getScrollX()
            int r4 = r4 + r1
            int r1 = r11.getWidth()
            int r1 = r1 / 2
            if (r4 < r1) goto L_0x0093
            int r1 = r11.currentIndex
            if (r1 <= 0) goto L_0x0093
            com.app.office.system.beans.pagelist.APageListEventManage r1 = r11.eventManage
            boolean r1 = r1.isOnFling()
            if (r1 != 0) goto L_0x0093
            r11.postUnRepaint(r0)
            com.app.office.system.beans.pagelist.APageListEventManage r1 = r11.eventManage
            r11.post(r1)
            int r1 = r11.currentIndex
            int r1 = r1 - r3
            r11.currentIndex = r1
        L_0x0093:
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r1 = r11.childViewsCache
            int r1 = r1.size()
            int[] r4 = new int[r1]
            r5 = 0
        L_0x009c:
            if (r5 >= r1) goto L_0x00a9
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r6 = r11.childViewsCache
            int r6 = r6.keyAt(r5)
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x009c
        L_0x00a9:
            r5 = 0
        L_0x00aa:
            if (r5 >= r1) goto L_0x0142
            r6 = r4[r5]
            int r7 = r11.currentIndex
            int r8 = r7 + -1
            if (r6 < r8) goto L_0x00ba
            r6 = r4[r5]
            int r7 = r7 + 1
            if (r6 <= r7) goto L_0x00d6
        L_0x00ba:
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r6 = r11.childViewsCache
            r7 = r4[r5]
            java.lang.Object r6 = r6.get(r7)
            com.app.office.system.beans.pagelist.APageListItem r6 = (com.app.office.system.beans.pagelist.APageListItem) r6
            r6.releaseResources()
            java.util.LinkedList<com.app.office.system.beans.pagelist.APageListItem> r7 = r11.pageViewCache
            r7.add(r6)
            r11.removeViewInLayout(r6)
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r6 = r11.childViewsCache
            r7 = r4[r5]
            r6.remove(r7)
        L_0x00d6:
            int r5 = r5 + 1
            goto L_0x00aa
        L_0x00d9:
            r11.isResetLayout = r2
            com.app.office.system.beans.pagelist.APageListEventManage r1 = r11.eventManage
            r1.setScrollAxisValue(r2, r2)
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r1 = r11.childViewsCache
            int r1 = r1.size()
            int[] r4 = new int[r1]
            r5 = 0
        L_0x00e9:
            if (r5 >= r1) goto L_0x00f6
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r6 = r11.childViewsCache
            int r6 = r6.keyAt(r5)
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x00e9
        L_0x00f6:
            r5 = 0
            r6 = 0
        L_0x00f8:
            if (r5 >= r1) goto L_0x0130
            r7 = r4[r5]
            int r8 = r11.currentIndex
            int r9 = r8 + -1
            if (r7 < r9) goto L_0x0108
            r7 = r4[r5]
            int r8 = r8 + 1
            if (r7 <= r8) goto L_0x012d
        L_0x0108:
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r6 = r11.childViewsCache
            r7 = r4[r5]
            java.lang.Object r6 = r6.get(r7)
            com.app.office.system.beans.pagelist.APageListItem r6 = (com.app.office.system.beans.pagelist.APageListItem) r6
            r6.releaseResources()
            java.util.LinkedList<com.app.office.system.beans.pagelist.APageListItem> r7 = r11.pageViewCache
            r7.add(r6)
            r11.removeViewInLayout(r6)
            android.util.SparseArray<com.app.office.system.beans.pagelist.APageListItem> r6 = r11.childViewsCache
            r7 = r4[r5]
            r6.remove(r7)
            r6 = r4[r5]
            int r7 = r11.currentIndex
            if (r6 != r7) goto L_0x012c
            r6 = 1
            goto L_0x012d
        L_0x012c:
            r6 = 0
        L_0x012d:
            int r5 = r5 + 1
            goto L_0x00f8
        L_0x0130:
            float r1 = r11.zoom
            r4 = 1120403456(0x42c80000, float:100.0)
            float r1 = r1 * r4
            int r1 = (int) r1
            r4 = 100
            if (r1 != r4) goto L_0x013d
            if (r6 != 0) goto L_0x0142
        L_0x013d:
            com.app.office.system.beans.pagelist.APageListEventManage r1 = r11.eventManage
            r11.post(r1)
        L_0x0142:
            if (r0 != 0) goto L_0x0146
            r0 = 1
            goto L_0x0147
        L_0x0146:
            r0 = 0
        L_0x0147:
            int r1 = r11.currentIndex
            com.app.office.system.beans.pagelist.APageListItem r1 = r11.createPageView(r1)
            android.graphics.Point r4 = r11.getScreenSizeOffset(r1)
            if (r0 == 0) goto L_0x0158
            int r0 = r4.x
            int r5 = r4.y
            goto L_0x016e
        L_0x0158:
            int r0 = r1.getLeft()
            com.app.office.system.beans.pagelist.APageListEventManage r5 = r11.eventManage
            int r5 = r5.getScrollX()
            int r0 = r0 + r5
            int r5 = r1.getTop()
            com.app.office.system.beans.pagelist.APageListEventManage r6 = r11.eventManage
            int r6 = r6.getScrollY()
            int r5 = r5 + r6
        L_0x016e:
            com.app.office.system.beans.pagelist.APageListEventManage r6 = r11.eventManage
            r6.setScrollAxisValue(r2, r2)
            int r2 = r1.getMeasuredWidth()
            int r2 = r2 + r0
            int r6 = r1.getMeasuredHeight()
            int r6 = r6 + r5
            com.app.office.system.beans.pagelist.APageListEventManage r7 = r11.eventManage
            boolean r7 = r7.isTouchEventIn()
            if (r7 != 0) goto L_0x01a1
            com.app.office.system.beans.pagelist.APageListEventManage r7 = r11.eventManage
            boolean r7 = r7.isScrollerFinished()
            if (r7 == 0) goto L_0x01a1
            android.graphics.Rect r7 = r11.getScrollBounds(r0, r5, r2, r6)
            android.graphics.Point r7 = r11.getCorrection(r7)
            int r8 = r7.x
            int r2 = r2 + r8
            int r8 = r7.x
            int r0 = r0 + r8
            int r8 = r7.y
            int r5 = r5 + r8
            int r7 = r7.y
            goto L_0x01b8
        L_0x01a1:
            int r7 = r1.getMeasuredHeight()
            int r8 = r11.getHeight()
            if (r7 > r8) goto L_0x01b9
            android.graphics.Rect r7 = r11.getScrollBounds(r0, r5, r2, r6)
            android.graphics.Point r7 = r11.getCorrection(r7)
            int r8 = r7.y
            int r5 = r5 + r8
            int r7 = r7.y
        L_0x01b8:
            int r6 = r6 + r7
        L_0x01b9:
            r1.layout(r0, r5, r2, r6)
            int r1 = r11.currentIndex
            if (r1 <= 0) goto L_0x01ec
            int r1 = r1 - r3
            com.app.office.system.beans.pagelist.APageListItem r1 = r11.createPageView(r1)
            android.graphics.Point r7 = r11.getScreenSizeOffset(r1)
            int r7 = r7.x
            int r7 = r7 + 20
            int r8 = r4.x
            int r7 = r7 + r8
            int r8 = r1.getMeasuredWidth()
            int r8 = r0 - r8
            int r8 = r8 - r7
            int r9 = r6 + r5
            int r10 = r1.getMeasuredHeight()
            int r10 = r9 - r10
            int r10 = r10 / 2
            int r0 = r0 - r7
            int r7 = r1.getMeasuredHeight()
            int r9 = r9 + r7
            int r9 = r9 / 2
            r1.layout(r8, r10, r0, r9)
        L_0x01ec:
            int r0 = r11.currentIndex
            int r0 = r0 + r3
            android.widget.Adapter r1 = r11.pageAdapter
            int r1 = r1.getCount()
            if (r0 >= r1) goto L_0x0224
            int r0 = r11.currentIndex
            int r0 = r0 + r3
            com.app.office.system.beans.pagelist.APageListItem r0 = r11.createPageView(r0)
            android.graphics.Point r1 = r11.getScreenSizeOffset(r0)
            int r3 = r4.x
            int r3 = r3 + 20
            int r1 = r1.x
            int r3 = r3 + r1
            int r1 = r2 + r3
            int r6 = r6 + r5
            int r4 = r0.getMeasuredHeight()
            int r4 = r6 - r4
            int r4 = r4 / 2
            int r5 = r0.getMeasuredWidth()
            int r2 = r2 + r5
            int r2 = r2 + r3
            int r3 = r0.getMeasuredHeight()
            int r6 = r6 + r3
            int r6 = r6 / 2
            r0.layout(r1, r4, r2, r6)
        L_0x0224:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.system.beans.pagelist.APageListView.layout_Horizontal():void");
    }

    private void layout_Vertical() {
        int i;
        int i2;
        APageListItem aPageListItem = this.childViewsCache.get(this.currentIndex);
        if (!this.isResetLayout) {
            if (aPageListItem != null) {
                Point screenSizeOffset = getScreenSizeOffset(aPageListItem);
                if (aPageListItem.getTop() + aPageListItem.getMeasuredHeight() + screenSizeOffset.y + 10 + this.eventManage.getScrollY() < getHeight() / 2 && this.currentIndex + 1 < this.pageAdapter.getCount() && !this.eventManage.isOnFling()) {
                    postUnRepaint(aPageListItem);
                    post(this.eventManage);
                    int i3 = this.currentIndex + 1;
                    this.currentIndex = i3;
                    Log.e("current ++", String.valueOf(i3));
                } else if (((aPageListItem.getTop() - screenSizeOffset.y) - 10) + this.eventManage.getScrollY() >= getHeight() / 2 && this.currentIndex > 0 && !this.eventManage.isOnFling()) {
                    postUnRepaint(aPageListItem);
                    post(this.eventManage);
                    int i4 = this.currentIndex - 1;
                    this.currentIndex = i4;
                    Log.e("current --", String.valueOf(i4));
                }
            }
            int size = this.childViewsCache.size();
            int[] iArr = new int[size];
            for (int i5 = 0; i5 < size; i5++) {
                iArr[i5] = this.childViewsCache.keyAt(i5);
            }
            for (int i6 = 0; i6 < size; i6++) {
                int i7 = iArr[i6];
                int i8 = this.currentIndex;
                if (i7 < i8 - 1 || iArr[i6] > i8 + 1) {
                    APageListItem aPageListItem2 = this.childViewsCache.get(iArr[i6]);
                    aPageListItem2.releaseResources();
                    this.pageViewCache.add(aPageListItem2);
                    removeViewInLayout(aPageListItem2);
                    this.childViewsCache.remove(iArr[i6]);
                }
            }
        } else {
            this.isResetLayout = false;
            this.eventManage.setScrollAxisValue(0, 0);
            int size2 = this.childViewsCache.size();
            int[] iArr2 = new int[size2];
            for (int i9 = 0; i9 < size2; i9++) {
                iArr2[i9] = this.childViewsCache.keyAt(i9);
            }
            boolean z = false;
            for (int i10 = 0; i10 < size2; i10++) {
                int i11 = iArr2[i10];
                int i12 = this.currentIndex;
                if (i11 < i12 - 1 || iArr2[i10] > i12 + 1) {
                    APageListItem aPageListItem3 = this.childViewsCache.get(iArr2[i10]);
                    aPageListItem3.releaseResources();
                    this.pageViewCache.add(aPageListItem3);
                    removeViewInLayout(aPageListItem3);
                    this.childViewsCache.remove(iArr2[i10]);
                    z = iArr2[i10] == this.currentIndex;
                }
            }
            if (((int) (this.zoom * 100.0f)) != 100 || !z) {
                post(this.eventManage);
            }
        }
        boolean z2 = aPageListItem == null;
        APageListItem createPageView = createPageView(this.currentIndex);
        Point screenSizeOffset2 = getScreenSizeOffset(createPageView);
        if (z2) {
            i2 = screenSizeOffset2.x;
            i = screenSizeOffset2.y;
        } else {
            i2 = createPageView.getLeft() + this.eventManage.getScrollX();
            i = createPageView.getTop() + this.eventManage.getScrollY();
        }
        this.eventManage.setScrollAxisValue(0, 0);
        int measuredWidth = createPageView.getMeasuredWidth() + i2;
        int measuredHeight = createPageView.getMeasuredHeight() + i;
        if (!this.eventManage.isTouchEventIn() && this.eventManage.isScrollerFinished()) {
            Point correction = getCorrection(getScrollBounds(i2, i, measuredWidth, measuredHeight));
            measuredWidth += correction.x;
            i2 += correction.x;
            i += correction.y;
            measuredHeight += correction.y;
        } else if (createPageView.getMeasuredWidth() <= getWidth()) {
            Point correction2 = getCorrection(getScrollBounds(i2, i, measuredWidth, measuredHeight));
            measuredWidth += correction2.x;
            i2 += correction2.x;
        }
        createPageView.layout(i2, i, measuredWidth, measuredHeight);
        int i13 = this.currentIndex;
        if (i13 > 0) {
            APageListItem createPageView2 = createPageView(i13 - 1);
            int i14 = getScreenSizeOffset(createPageView2).y + 20 + screenSizeOffset2.y;
            createPageView2.layout(i2, (i - i14) - createPageView2.getMeasuredHeight(), measuredWidth, (measuredHeight - i14) - createPageView2.getMeasuredHeight());
        }
        if (this.currentIndex + 1 < this.pageAdapter.getCount()) {
            APageListItem createPageView3 = createPageView(this.currentIndex + 1);
            int i15 = screenSizeOffset2.y + 20 + getScreenSizeOffset(createPageView3).y;
            createPageView3.layout(i2, i + i15 + createPageView3.getMeasuredHeight(), measuredWidth, measuredHeight + i15 + createPageView3.getMeasuredHeight());
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.isConfigurationChanged = true;
    }

    public void showPDFPageForIndex(int i) {
        if (i >= 0 && i < this.pageAdapter.getCount()) {
            this.currentIndex = i;
            postDelayed(new Runnable(i) {
                int gotoIndex;
                final /* synthetic */ int val$index;

                {
                    this.val$index = r2;
                    this.gotoIndex = r2;
                }

                public void run() {
                    if (this.gotoIndex == APageListView.this.currentIndex) {
                        boolean unused = APageListView.this.isResetLayout = true;
                        APageListView.this.requestLayout();
                    }
                }
            }, 1);
            this.pageListViewListener.updateStutus((Object) null);
        }
    }

    public void nextPageView() {
        if (this.currentIndex + 1 < this.pageAdapter.getCount()) {
            APageListItem aPageListItem = this.childViewsCache.get(this.currentIndex + 1);
            if (aPageListItem != null) {
                this.currentIndex++;
                this.eventManage.slideViewOntoScreen(aPageListItem);
                return;
            }
            postDelayed(new Runnable() {
                public void run() {
                    boolean unused = APageListView.this.isResetLayout = true;
                    APageListView.this.requestLayout();
                }
            }, 1);
            this.pageListViewListener.updateStutus((Object) null);
        }
    }

    public void previousPageview() {
        APageListItem aPageListItem;
        int i = this.currentIndex;
        if (i != 0 && (aPageListItem = this.childViewsCache.get(i - 1)) != null) {
            this.currentIndex--;
            this.eventManage.slideViewOntoScreen(aPageListItem);
        }
    }

    public void exportImage(APageListItem aPageListItem, Bitmap bitmap) {
        if (aPageListItem.getPageIndex() == this.currentIndex && !this.eventManage.isTouchEventIn() && this.eventManage.isScrollerFinished()) {
            this.pageListViewListener.exportImage(aPageListItem, bitmap);
        }
    }

    public boolean isPointVisibleOnScreen(int i, int i2) {
        float f = this.zoom;
        int i3 = (int) (((float) i) * f);
        int i4 = (int) (((float) i2) * f);
        APageListItem currentPageView = getCurrentPageView();
        if (currentPageView == null) {
            return false;
        }
        int max = Math.max(currentPageView.getLeft(), 0) - currentPageView.getLeft();
        int max2 = Math.max(currentPageView.getTop(), 0) - currentPageView.getTop();
        if (max >= getWidth() + max || max2 >= getHeight() + max2 || i3 < max || i3 >= max + getWidth() || i4 < max2 || i4 >= max2 + getHeight()) {
            return false;
        }
        return true;
    }

    public void setItemPointVisibleOnScreen(int i, int i2) {
        APageListItem currentPageView;
        int i3;
        View view;
        View view2;
        if ((i >= 0 || i2 >= 0) && (currentPageView = getCurrentPageView()) != null && !isPointVisibleOnScreen(i, i2)) {
            float f = this.zoom;
            int i4 = (int) (((float) i) * f);
            int i5 = (int) (((float) i2) * f);
            int i6 = 0;
            if (i4 > 0) {
                if (getWidth() + i4 > currentPageView.getMeasuredWidth()) {
                    i4 = currentPageView.getMeasuredWidth() - getWidth();
                }
                i3 = -i4;
            } else {
                i3 = 0;
            }
            if (i5 > 0) {
                if (getHeight() + i5 > currentPageView.getMeasuredHeight()) {
                    i5 = currentPageView.getMeasuredHeight() - getHeight();
                }
                i6 = -i5;
            }
            Point screenSizeOffset = getScreenSizeOffset(currentPageView);
            int measuredWidth = currentPageView.getMeasuredWidth() + i3;
            int measuredHeight = currentPageView.getMeasuredHeight() + i6;
            if (currentPageView.getMeasuredHeight() <= getHeight()) {
                Point correction = getCorrection(getScrollBounds(i3, i6, measuredWidth, measuredHeight));
                i6 += correction.y;
                measuredHeight += correction.y;
            }
            currentPageView.layout(i3, i6, measuredWidth, measuredHeight);
            int i7 = this.currentIndex;
            if (i7 > 0 && (view2 = this.childViewsCache.get(i7 - 1)) != null) {
                int i8 = getScreenSizeOffset(view2).x + 20 + screenSizeOffset.x;
                int i9 = measuredHeight + i6;
                view2.layout((i3 - view2.getMeasuredWidth()) - i8, (i9 - view2.getMeasuredHeight()) / 2, i3 - i8, (i9 + view2.getMeasuredHeight()) / 2);
            }
            if (this.currentIndex + 1 < this.pageAdapter.getCount() && (view = this.childViewsCache.get(this.currentIndex + 1)) != null) {
                int i10 = screenSizeOffset.x + 20 + getScreenSizeOffset(view).x;
                int i11 = measuredHeight + i6;
                view.layout(measuredWidth + i10, (i11 - view.getMeasuredHeight()) / 2, measuredWidth + view.getMeasuredWidth() + i10, (i11 + view.getMeasuredHeight()) / 2);
            }
            postRepaint(currentPageView);
        }
    }

    public Object getModel() {
        return this.pageListViewListener.getModel();
    }

    public int getDisplayedPageIndex() {
        return this.currentIndex;
    }

    public void setZoom(float f, int i, int i2) {
        setZoom(f, i, i2, true);
    }

    public void setZoom(float f, boolean z) {
        setZoom(f, Integer.MIN_VALUE, Integer.MIN_VALUE, z);
    }

    public void setZoom(float f, int i, int i2, final boolean z) {
        int i3;
        if (((int) (f * 1.0E7f)) != ((int) (this.zoom * 1.0E7f))) {
            this.isInitZoom = true;
            if (i == Integer.MIN_VALUE && i2 == Integer.MIN_VALUE) {
                i = getWidth() / 2;
                i2 = getHeight() / 2;
            }
            float f2 = this.zoom;
            this.zoom = f;
            this.pageListViewListener.changeZoom();
            post(new Runnable() {
                public void run() {
                    APageListItem currentPageView;
                    if (z && (currentPageView = APageListView.this.getCurrentPageView()) != null) {
                        APageListView.this.postRepaint(currentPageView);
                    }
                }
            });
            if (z) {
                APageListItem currentPageView = getCurrentPageView();
                int i4 = 0;
                if (currentPageView != null) {
                    i4 = currentPageView.getLeft();
                    i3 = currentPageView.getTop();
                } else {
                    i3 = 0;
                }
                float f3 = this.zoom / f2;
                int scrollX = i - (i4 + this.eventManage.getScrollX());
                float f4 = (float) scrollX;
                float scrollY = (float) (i2 - (i3 + this.eventManage.getScrollY()));
                this.eventManage.setScrollAxisValue((int) (f4 - (f4 * f3)), (int) (scrollY - (f3 * scrollY)));
                requestLayout();
            }
        }
    }

    public void setFitSize(int i) {
        setZoom(getFitZoom(i), true);
        postInvalidate();
    }

    public int getFitSizeState() {
        APageListItem currentPageView = getCurrentPageView();
        if (currentPageView != null) {
            int abs = Math.abs(currentPageView.getWidth() - getWidth());
            int abs2 = Math.abs(currentPageView.getHeight() - getHeight());
            if (abs < 2 && abs2 < 2) {
                return 3;
            }
            if (abs >= 2 || abs2 < 2) {
                return (abs < 2 || abs2 >= 2) ? 0 : 1;
            }
            return 2;
        }
    }

    public float getZoom() {
        return this.zoom;
    }

    public float getFitZoom() {
        return getFitZoom(0);
    }

    public float getFitZoom(int i) {
        int i2 = this.currentIndex;
        if (i2 >= 0 && i2 < this.pageListViewListener.getPageCount()) {
            Rect pageSize = this.pageListViewListener.getPageSize(this.currentIndex);
            int width = getWidth();
            int height = getHeight();
            ViewParent parent = getParent();
            while (width == 0 && parent != null && parent != null && (parent instanceof View)) {
                View view = (View) parent;
                int width2 = view.getWidth();
                int height2 = view.getHeight();
                parent = parent.getParent();
                int i3 = width2;
                height = height2;
                width = i3;
            }
            if (!(width == 0 || height == 0)) {
                if (i == 0) {
                    if (!this.pageListViewListener.isIgnoreOriginalSize()) {
                        return Math.min(Math.min(((float) width) / ((float) pageSize.width()), ((float) height) / ((float) pageSize.height())), 1.0f);
                    }
                    return Math.min(Math.min(((float) width) / ((float) pageSize.width()), ((float) height) / ((float) pageSize.height())), 3.0f);
                } else if (i == 1) {
                    return Math.min(((float) width) / ((float) pageSize.width()), 3.0f);
                } else {
                    if (i == 2) {
                        return Math.min(((float) height) / ((float) pageSize.height()), 3.0f);
                    }
                }
            }
        }
        return 1.0f;
    }

    public int getCurrentPageNumber() {
        return this.currentIndex + 1;
    }

    public Adapter getAdapter() {
        return this.pageAdapter;
    }

    public void setAdapter(Adapter adapter) {
        this.pageAdapter = adapter;
    }

    /* access modifiers changed from: protected */
    public void postUnRepaint(final APageListItem aPageListItem) {
        if (aPageListItem != null) {
            post(new Runnable() {
                public void run() {
                    aPageListItem.removeRepaintImageView();
                }
            });
        }
    }

    public void postRepaint(final APageListItem aPageListItem) {
        if (aPageListItem != null) {
            post(new Runnable() {
                public void run() {
                    aPageListItem.addRepaintImageView((Bitmap) null);
                }
            });
        }
    }

    public APageListItem getCurrentPageView() {
        SparseArray<APageListItem> sparseArray = this.childViewsCache;
        if (sparseArray != null) {
            return sparseArray.get(this.currentIndex);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public IPageListViewListener getPageListViewListener() {
        return this.pageListViewListener;
    }

    private APageListItem createPageView(int i) {
        APageListItem aPageListItem = this.childViewsCache.get(i);
        if (aPageListItem == null) {
            aPageListItem = (APageListItem) this.pageAdapter.getView(i, this.pageViewCache.size() == 0 ? null : this.pageViewCache.removeFirst(), this);
            ViewGroup.LayoutParams layoutParams = aPageListItem.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(-2, -2);
            }
            addViewInLayout(aPageListItem, 0, layoutParams, true);
            this.childViewsCache.append(i, aPageListItem);
            aPageListItem.measure(((int) (((float) aPageListItem.getPageWidth()) * this.zoom)) | 1073741824, 1073741824 | ((int) (((float) aPageListItem.getPageHeight()) * this.zoom)));
        }
        return aPageListItem;
    }

    /* access modifiers changed from: protected */
    public Rect getScrollBounds(int i, int i2, int i3, int i4) {
        int width = getWidth() - i3;
        int i5 = -i;
        int height = getHeight() - i4;
        int i6 = -i2;
        if (width > i5) {
            width = (width + i5) / 2;
            i5 = width;
        }
        if (height > i6) {
            height = (height + i6) / 2;
            i6 = height;
        }
        return new Rect(width, height, i5, i6);
    }

    /* access modifiers changed from: protected */
    public Rect getScrollBounds(View view) {
        return getScrollBounds(view.getLeft() + this.eventManage.getScrollX(), view.getTop() + this.eventManage.getScrollY(), view.getLeft() + view.getMeasuredWidth() + this.eventManage.getScrollX(), view.getTop() + view.getMeasuredHeight() + this.eventManage.getScrollY());
    }

    /* access modifiers changed from: protected */
    public Point getCorrection(Rect rect) {
        return new Point(Math.min(Math.max(0, rect.left), rect.right), Math.min(Math.max(0, rect.top), rect.bottom));
    }

    /* access modifiers changed from: protected */
    public Point getScreenSizeOffset(View view) {
        return new Point(Math.max((getWidth() - view.getMeasuredWidth()) / 2, 0), Math.max((getHeight() - view.getMeasuredHeight()) / 2, 0));
    }

    public int getPageCount() {
        return this.pageListViewListener.getPageCount();
    }

    /* access modifiers changed from: protected */
    public APageListItem getPageListItem(int i, View view, ViewGroup viewGroup) {
        return this.pageListViewListener.getPageListItem(i, view, viewGroup);
    }

    public boolean isInit() {
        return this.isInit;
    }

    public void setDoRequstLayout(boolean z) {
        this.isDoRequestLayout = z;
    }

    public boolean isInitZoom() {
        return this.isInitZoom;
    }

    public void setInitZoom(boolean z) {
        this.isInitZoom = z;
    }

    public void dispose() {
        this.pageListViewListener = null;
        APageListEventManage aPageListEventManage = this.eventManage;
        if (aPageListEventManage != null) {
            aPageListEventManage.dispose();
            this.eventManage = null;
        }
        Adapter adapter = this.pageAdapter;
        if (adapter instanceof APageListAdapter) {
            ((APageListAdapter) adapter).dispose();
            this.pageAdapter = null;
        }
        SparseArray<APageListItem> sparseArray = this.childViewsCache;
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                this.childViewsCache.valueAt(i).dispose();
            }
            this.childViewsCache.clear();
            this.childViewsCache = null;
        }
        LinkedList<APageListItem> linkedList = this.pageViewCache;
        if (linkedList != null) {
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                ((APageListItem) it.next()).dispose();
            }
            this.pageViewCache.clear();
            this.pageViewCache = null;
        }
    }
}
