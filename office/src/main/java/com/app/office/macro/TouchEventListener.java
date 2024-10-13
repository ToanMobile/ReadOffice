package com.app.office.macro;

import android.view.MotionEvent;
import android.view.View;

public interface TouchEventListener {
    public static final byte EVENT_CLICK = 10;
    public static final byte EVENT_DOUBLE_TAP = 8;
    public static final byte EVENT_DOUBLE_TAP_EVENT = 9;
    public static final byte EVENT_DOWN = 1;
    public static final byte EVENT_FLING = 6;
    public static final byte EVENT_LONG_PRESS = 5;
    public static final byte EVENT_SCROLL = 4;
    public static final byte EVENT_SHOW_PRESS = 2;
    public static final byte EVENT_SINGLE_TAP_CONFIRMED = 7;
    public static final byte EVENT_SINGLE_TAP_UP = 3;
    public static final byte EVENT_TOUCH = 0;

    boolean onEventMethod(View view, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, byte b);
}
