package com.app.office.system.beans.pagelist;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public interface IPageListViewListener {
    public static final byte Moving_Horizontal = 0;
    public static final byte Moving_Vertical = 1;
    public static final byte ON_CLICK = 10;
    public static final byte ON_DOUBLE_TAP = 8;
    public static final byte ON_DOUBLE_TAP_EVENT = 9;
    public static final byte ON_DOWN = 1;
    public static final byte ON_FLING = 6;
    public static final byte ON_LONG_PRESS = 5;
    public static final byte ON_SCROLL = 4;
    public static final byte ON_SHOW_PRESS = 2;
    public static final byte ON_SINGLE_TAP_CONFIRMED = 7;
    public static final byte ON_SINGLE_TAP_UP = 3;
    public static final byte ON_TOUCH = 0;

    void changeZoom();

    void exportImage(APageListItem aPageListItem, Bitmap bitmap);

    Object getModel();

    int getPageCount();

    APageListItem getPageListItem(int i, View view, ViewGroup viewGroup);

    byte getPageListViewMovingPosition();

    Rect getPageSize(int i);

    boolean isChangePage();

    boolean isIgnoreOriginalSize();

    boolean isInit();

    boolean isShowZoomingMsg();

    boolean isTouchZoom();

    boolean onEventMethod(View view, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, byte b);

    void resetSearchResult(APageListItem aPageListItem);

    void setDrawPictrue(boolean z);

    void updateStutus(Object obj);
}
