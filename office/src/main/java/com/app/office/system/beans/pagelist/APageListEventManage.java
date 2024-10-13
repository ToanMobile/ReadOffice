package com.app.office.system.beans.pagelist;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;
import android.widget.Toast;
import com.app.office.thirdpart.emf.EMFConstants;
import java.util.NoSuchElementException;

public class APageListEventManage implements ScaleGestureDetector.OnScaleGestureListener, GestureDetector.OnGestureListener, Runnable, View.OnTouchListener, GestureDetector.OnDoubleTapListener, View.OnClickListener {
    private static final float MAX_ZOOM = 3.0f;
    private static final int MOVING_DIAGONALLY = 0;
    private static final int MOVING_DOWN = 4;
    private static final int MOVING_LEFT = 1;
    private static final int MOVING_RIGHT = 2;
    private static final int MOVING_UP = 3;
    private int eventPointerCount;
    protected GestureDetector gesture;
    private boolean isDoubleTap;
    private boolean isOnFling;
    private boolean isOnScroll;
    private boolean isProcessOnScroll = true;
    private boolean isScaling;
    private boolean isTouchEventIn;
    private APageListView listView;
    private ScaleGestureDetector mScaleGestureDetector;
    protected Scroller mScroller;
    private int mScrollerLastX;
    private int mScrollerLastY;
    private int mXScroll;
    private int mYScroll;
    protected Toast toast = null;

    public void dispose() {
    }

    /* access modifiers changed from: protected */
    public boolean zoom(MotionEvent motionEvent) {
        return false;
    }

    public APageListEventManage(APageListView aPageListView) {
        this.listView = aPageListView;
        this.gesture = new GestureDetector(aPageListView.getContext(), this);
        this.mScroller = new Scroller(aPageListView.getContext());
        this.mScaleGestureDetector = new ScaleGestureDetector(aPageListView.getContext(), this);
        this.toast = Toast.makeText(aPageListView.getContext(), "", 0);
    }

    /* access modifiers changed from: protected */
    public boolean processOnTouch(MotionEvent motionEvent) {
        GestureDetector gestureDetector;
        this.eventPointerCount = motionEvent.getPointerCount();
        if (motionEvent.getActionMasked() == 0) {
            this.isOnFling = false;
            this.isTouchEventIn = true;
        }
        ScaleGestureDetector scaleGestureDetector = this.mScaleGestureDetector;
        if (scaleGestureDetector != null) {
            scaleGestureDetector.onTouchEvent(motionEvent);
        }
        if (!this.isScaling && (gestureDetector = this.gesture) != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        if (motionEvent.getActionMasked() == 1) {
            this.isProcessOnScroll = true;
            this.isTouchEventIn = false;
            APageListItem currentPageView = this.listView.getCurrentPageView();
            if (currentPageView != null) {
                if (this.mScroller.isFinished() && !this.isDoubleTap) {
                    slideViewOntoScreen(currentPageView);
                }
                if (this.mScroller.isFinished() && this.isOnScroll) {
                    this.listView.getPageListViewListener().setDrawPictrue(true);
                    this.listView.postRepaint(currentPageView);
                }
            }
            this.isDoubleTap = false;
            this.isOnScroll = false;
            this.toast.cancel();
        }
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.listView.getPageListViewListener().onEventMethod(view, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 0);
        return false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        this.mScroller.forceFinished(true);
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 1);
        return true;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        APageListItem currentPageView;
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, motionEvent2, f, f2, (byte) 6);
        if (this.isProcessOnScroll && !this.isDoubleTap && (currentPageView = this.listView.getCurrentPageView()) != null) {
            Rect scrollBounds = this.listView.getScrollBounds(currentPageView);
            if (this.listView.getPageListViewListener().getPageListViewMovingPosition() == 0) {
                if (currentPageView.getWidth() <= this.listView.getWidth() || this.listView.getPageListViewListener().isChangePage()) {
                    int directionOfTravel = directionOfTravel(f, f2);
                    if (directionOfTravel != 1) {
                        if (directionOfTravel == 2 && scrollBounds.right <= 0) {
                            this.isOnFling = true;
                            this.listView.previousPageview();
                            return true;
                        }
                    } else if (scrollBounds.left >= 0) {
                        this.isOnFling = true;
                        this.listView.nextPageView();
                        return true;
                    }
                }
            } else if (currentPageView.getHeight() <= this.listView.getHeight() || this.listView.getPageListViewListener().isChangePage()) {
                int directionOfTravel2 = directionOfTravel(f, f2);
                if (directionOfTravel2 != 3) {
                    if (directionOfTravel2 == 4 && scrollBounds.bottom <= 0) {
                        this.isOnFling = true;
                        this.listView.previousPageview();
                        return true;
                    }
                } else if (scrollBounds.top >= 0) {
                    this.isOnFling = true;
                    this.listView.nextPageView();
                    return true;
                }
            }
            this.mScrollerLastY = 0;
            this.mScrollerLastX = 0;
            Rect rect = new Rect(scrollBounds);
            rect.inset(-100, -100);
            if (withinBoundsInDirectionOfTravel(scrollBounds, f, f2) && rect.contains(0, 0)) {
                this.mScroller.fling(0, 0, (int) f, (int) f2, scrollBounds.left, scrollBounds.right, scrollBounds.top, scrollBounds.bottom);
                this.listView.post(this);
            }
        }
        return true;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        APageListItem currentPageView;
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, motionEvent2, f, f2, (byte) 4);
        if (this.isProcessOnScroll && !this.isDoubleTap) {
            this.listView.getPageListViewListener().setDrawPictrue(false);
            this.isOnScroll = true;
            this.mXScroll = (int) (((float) this.mXScroll) - f);
            this.mYScroll = (int) (((float) this.mYScroll) - f2);
            if (!this.listView.getPageListViewListener().isChangePage() && (currentPageView = this.listView.getCurrentPageView()) != null && currentPageView.getWidth() > this.listView.getWidth()) {
                if (f > 0.0f) {
                    if ((this.listView.getWidth() - this.mXScroll) - currentPageView.getLeft() > currentPageView.getWidth() && currentPageView.getPageIndex() < this.listView.getPageCount() - 1) {
                        this.mXScroll = -((currentPageView.getWidth() - this.listView.getWidth()) + currentPageView.getLeft());
                    }
                } else if (f < 0.0f && this.mXScroll + currentPageView.getLeft() > 0 && currentPageView.getPageIndex() != 0) {
                    this.mXScroll = 0;
                }
            }
            this.listView.requestLayout();
        }
        return true;
    }

    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (this.eventPointerCount > 1 && this.listView.getPageListViewListener().isTouchZoom()) {
            this.isTouchEventIn = true;
            float zoom = this.listView.getZoom();
            float min = Math.min(Math.max(this.listView.getZoom() * scaleGestureDetector.getScaleFactor(), this.listView.getFitZoom()), 3.0f);
            if (((int) (min * 1.0E7f)) != ((int) (1.0E7f * zoom))) {
                this.isOnScroll = true;
                float f = min / zoom;
                this.listView.setZoom(min, false);
                APageListItem currentPageView = this.listView.getCurrentPageView();
                if (currentPageView != null) {
                    int focusX = ((int) scaleGestureDetector.getFocusX()) - (currentPageView.getLeft() + this.mXScroll);
                    int top = currentPageView.getTop();
                    int i = this.mYScroll;
                    int focusY = ((int) scaleGestureDetector.getFocusY()) - (top + i);
                    float f2 = (float) focusX;
                    this.mXScroll = (int) (((float) this.mXScroll) + (f2 - (f2 * f)));
                    float f3 = (float) focusY;
                    this.mYScroll = (int) (((float) i) + (f3 - (f * f3)));
                    this.listView.requestLayout();
                }
            }
            if (this.listView.getPageListViewListener().isShowZoomingMsg()) {
                Toast toast2 = this.toast;
                toast2.setText(Math.round(min * 100.0f) + "%");
                this.toast.show();
            }
        }
        return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        if (this.eventPointerCount > 1 && this.listView.getPageListViewListener().isTouchZoom()) {
            this.isScaling = true;
            this.mYScroll = 0;
            this.mXScroll = 0;
            this.isProcessOnScroll = false;
        }
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        if (this.eventPointerCount > 1 && this.listView.getPageListViewListener().isTouchZoom()) {
            this.isScaling = false;
        }
    }

    public void onShowPress(MotionEvent motionEvent) {
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 2);
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 3);
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 5);
    }

    public void onClick(View view) {
        this.listView.getPageListViewListener().onEventMethod(this.listView, (MotionEvent) null, (MotionEvent) null, -1.0f, -1.0f, (byte) 10);
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 7);
        return false;
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        this.isProcessOnScroll = true;
        this.isTouchEventIn = false;
        this.isDoubleTap = true;
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 8);
        return false;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        this.isTouchEventIn = false;
        this.isDoubleTap = true;
        this.listView.getPageListViewListener().onEventMethod(this.listView, motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 9);
        return false;
    }

    public void run() {
        if (!this.mScroller.isFinished()) {
            this.listView.getPageListViewListener().setDrawPictrue(false);
            this.mScroller.computeScrollOffset();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            this.mXScroll += currX - this.mScrollerLastX;
            this.mYScroll += currY - this.mScrollerLastY;
            this.mScrollerLastX = currX;
            this.mScrollerLastY = currY;
            this.listView.requestLayout();
            this.listView.post(this);
        } else if (!this.isTouchEventIn) {
            APageListView aPageListView = this.listView;
            aPageListView.postRepaint(aPageListView.getCurrentPageView());
            this.listView.getPageListViewListener().updateStutus((Object) null);
            this.listView.getPageListViewListener().setDrawPictrue(true);
        }
    }

    /* access modifiers changed from: protected */
    public void slideViewOntoScreen(APageListItem aPageListItem) {
        APageListView aPageListView = this.listView;
        Point correction = aPageListView.getCorrection(aPageListView.getScrollBounds(aPageListItem));
        if (!(correction.x == 0 && correction.y == 0)) {
            this.mScrollerLastY = 0;
            this.mScrollerLastX = 0;
            this.mScroller.startScroll(0, 0, correction.x, correction.y, EMFConstants.FW_NORMAL);
            this.listView.post(this);
        }
        this.listView.getPageListViewListener().resetSearchResult(aPageListItem);
    }

    /* access modifiers changed from: protected */
    public int getScrollX() {
        return this.mXScroll;
    }

    /* access modifiers changed from: protected */
    public int getScrollY() {
        return this.mYScroll;
    }

    /* access modifiers changed from: protected */
    public void setScrollAxisValue(int i, int i2) {
        this.mXScroll = i;
        this.mYScroll = i2;
    }

    /* access modifiers changed from: protected */
    public boolean isTouchEventIn() {
        return this.isTouchEventIn;
    }

    /* access modifiers changed from: protected */
    public boolean isScrollerFinished() {
        return this.mScroller.isFinished();
    }

    /* access modifiers changed from: protected */
    public boolean isOnFling() {
        return this.isOnFling;
    }

    /* access modifiers changed from: protected */
    public int directionOfTravel(float f, float f2) {
        if (Math.abs(f) > Math.abs(f2) * 2.0f) {
            return f > 0.0f ? 2 : 1;
        }
        if (Math.abs(f2) > Math.abs(f) * 2.0f) {
            return f2 > 0.0f ? 4 : 3;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean withinBoundsInDirectionOfTravel(Rect rect, float f, float f2) {
        int directionOfTravel = directionOfTravel(f, f2);
        if (directionOfTravel == 0) {
            return rect.contains(0, 0);
        }
        if (directionOfTravel != 1) {
            if (directionOfTravel != 2) {
                if (directionOfTravel != 3) {
                    if (directionOfTravel == 4) {
                        return rect.bottom >= 0;
                    }
                    throw new NoSuchElementException();
                } else if (rect.top <= 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (rect.right >= 0) {
                return true;
            } else {
                return false;
            }
        } else if (rect.left <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
