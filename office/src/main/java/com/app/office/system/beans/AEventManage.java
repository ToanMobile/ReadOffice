package com.app.office.system.beans;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import android.widget.Toast;
import com.app.office.constant.EventConstant;
import com.app.office.system.IControl;

public abstract class AEventManage implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener {
    protected IControl control;
    protected float distance = 0.0f;
    protected GestureDetector gesture;
    protected boolean isFling;
    protected boolean isScroll;
    protected int mActivePointerId = -1;
    protected int mMaximumVelocity;
    protected int mMinimumVelocity;
    protected Scroller mScroller;
    protected VelocityTracker mVelocityTracker;
    protected int midXDoublePoint;
    protected int midYDoublePoint;
    protected boolean singleTabup = false;
    protected Toast toast = null;
    protected boolean zoomChange;

    public void fling(int i, int i2) {
    }

    public AEventManage(Context context, IControl iControl) {
        this.control = iControl;
        this.gesture = new GestureDetector(context, this, (Handler) null, true);
        this.mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.toast = Toast.makeText(context, "", 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a7 A[Catch:{ Exception -> 0x0109 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00b5 A[Catch:{ Exception -> 0x0109 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0101 A[Catch:{ Exception -> 0x0109 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouch(android.view.View r10, android.view.MotionEvent r11) {
        /*
            r9 = this;
            r0 = 0
            android.view.GestureDetector r1 = r9.gesture     // Catch:{ Exception -> 0x0116 }
            if (r1 != 0) goto L_0x0006
            return r0
        L_0x0006:
            com.app.office.system.IControl r1 = r9.control     // Catch:{ Exception -> 0x0116 }
            com.app.office.system.IMainFrame r2 = r1.getMainFrame()     // Catch:{ Exception -> 0x0116 }
            r5 = 0
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            r7 = -1082130432(0xffffffffbf800000, float:-1.0)
            r8 = 0
            r3 = r10
            r4 = r11
            r2.onEventMethod(r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x0116 }
            int r10 = r11.getPointerCount()     // Catch:{ Exception -> 0x0116 }
            r1 = 2
            if (r10 != r1) goto L_0x0023
            boolean r10 = r9.zoom(r11)     // Catch:{ Exception -> 0x0116 }
            return r10
        L_0x0023:
            android.view.GestureDetector r10 = r9.gesture     // Catch:{ Exception -> 0x0116 }
            r10.onTouchEvent(r11)     // Catch:{ Exception -> 0x0116 }
            android.view.VelocityTracker r10 = r9.mVelocityTracker     // Catch:{ Exception -> 0x0116 }
            if (r10 != 0) goto L_0x0032
            android.view.VelocityTracker r10 = android.view.VelocityTracker.obtain()     // Catch:{ Exception -> 0x0116 }
            r9.mVelocityTracker = r10     // Catch:{ Exception -> 0x0116 }
        L_0x0032:
            android.view.VelocityTracker r10 = r9.mVelocityTracker     // Catch:{ Exception -> 0x0116 }
            r10.addMovement(r11)     // Catch:{ Exception -> 0x0116 }
            int r10 = r11.getAction()     // Catch:{ Exception -> 0x0116 }
            if (r10 == 0) goto L_0x010c
            r11 = 1
            r2 = -1
            r3 = 0
            if (r10 == r11) goto L_0x0054
            r11 = 3
            if (r10 == r11) goto L_0x0047
            goto L_0x0124
        L_0x0047:
            r9.mActivePointerId = r2     // Catch:{ Exception -> 0x0116 }
            android.view.VelocityTracker r10 = r9.mVelocityTracker     // Catch:{ Exception -> 0x0116 }
            if (r10 == 0) goto L_0x0124
            r10.recycle()     // Catch:{ Exception -> 0x0116 }
            r9.mVelocityTracker = r3     // Catch:{ Exception -> 0x0116 }
            goto L_0x0124
        L_0x0054:
            boolean r10 = r9.singleTabup     // Catch:{ Exception -> 0x0116 }
            if (r10 != 0) goto L_0x0104
            android.view.VelocityTracker r10 = r9.mVelocityTracker     // Catch:{ Exception -> 0x0116 }
            r4 = 1000(0x3e8, float:1.401E-42)
            int r5 = r9.mMaximumVelocity     // Catch:{ Exception -> 0x0116 }
            float r5 = (float) r5     // Catch:{ Exception -> 0x0116 }
            r10.computeCurrentVelocity(r4, r5)     // Catch:{ Exception -> 0x0116 }
            int r4 = r9.mActivePointerId     // Catch:{ Exception -> 0x0116 }
            float r4 = r10.getYVelocity(r4)     // Catch:{ Exception -> 0x0116 }
            int r4 = (int) r4     // Catch:{ Exception -> 0x0116 }
            int r5 = r9.mActivePointerId     // Catch:{ Exception -> 0x0116 }
            float r10 = r10.getXVelocity(r5)     // Catch:{ Exception -> 0x0116 }
            int r10 = (int) r10     // Catch:{ Exception -> 0x0116 }
            int r5 = java.lang.Math.abs(r4)     // Catch:{ Exception -> 0x0116 }
            int r6 = r9.mMinimumVelocity     // Catch:{ Exception -> 0x0116 }
            if (r5 > r6) goto L_0x0083
            int r5 = java.lang.Math.abs(r10)     // Catch:{ Exception -> 0x0116 }
            int r6 = r9.mMinimumVelocity     // Catch:{ Exception -> 0x0116 }
            if (r5 <= r6) goto L_0x0081
            goto L_0x0083
        L_0x0081:
            r11 = 0
            goto L_0x009d
        L_0x0083:
            boolean r5 = r9.isScroll     // Catch:{ Exception -> 0x0116 }
            if (r5 != 0) goto L_0x0094
            com.app.office.system.IControl r5 = r9.control     // Catch:{ Exception -> 0x0116 }
            byte r5 = r5.getApplicationType()     // Catch:{ Exception -> 0x0116 }
            if (r5 != r1) goto L_0x0091
            r5 = 1
            goto L_0x0092
        L_0x0091:
            r5 = 0
        L_0x0092:
            r9.isScroll = r5     // Catch:{ Exception -> 0x0116 }
        L_0x0094:
            boolean r5 = r9.zoomChange     // Catch:{ Exception -> 0x0116 }
            if (r5 != 0) goto L_0x009d
            int r10 = -r10
            int r4 = -r4
            r9.fling(r10, r4)     // Catch:{ Exception -> 0x0116 }
        L_0x009d:
            r9.midXDoublePoint = r2     // Catch:{ Exception -> 0x0109 }
            r9.midYDoublePoint = r2     // Catch:{ Exception -> 0x0109 }
            r9.mActivePointerId = r2     // Catch:{ Exception -> 0x0109 }
            android.view.VelocityTracker r10 = r9.mVelocityTracker     // Catch:{ Exception -> 0x0109 }
            if (r10 == 0) goto L_0x00ac
            r10.recycle()     // Catch:{ Exception -> 0x0109 }
            r9.mVelocityTracker = r3     // Catch:{ Exception -> 0x0109 }
        L_0x00ac:
            android.widget.Toast r10 = r9.toast     // Catch:{ Exception -> 0x0109 }
            r10.cancel()     // Catch:{ Exception -> 0x0109 }
            boolean r10 = r9.isScroll     // Catch:{ Exception -> 0x0109 }
            if (r10 == 0) goto L_0x00f9
            r9.isScroll = r0     // Catch:{ Exception -> 0x0109 }
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            byte r10 = r10.getApplicationType()     // Catch:{ Exception -> 0x0109 }
            r2 = 536870922(0x2000000a, float:1.0842035E-19)
            if (r10 != 0) goto L_0x00d7
            boolean r10 = r9.zoomChange     // Catch:{ Exception -> 0x0109 }
            if (r10 == 0) goto L_0x00d7
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            com.app.office.system.IMainFrame r10 = r10.getMainFrame()     // Catch:{ Exception -> 0x0109 }
            boolean r10 = r10.isZoomAfterLayoutForWord()     // Catch:{ Exception -> 0x0109 }
            if (r10 != 0) goto L_0x00d7
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            r10.actionEvent(r2, r3)     // Catch:{ Exception -> 0x0109 }
        L_0x00d7:
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            byte r10 = r10.getApplicationType()     // Catch:{ Exception -> 0x0109 }
            if (r10 != r1) goto L_0x00ed
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            boolean r10 = r10.isSlideShow()     // Catch:{ Exception -> 0x0109 }
            if (r10 != 0) goto L_0x00f2
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            r10.actionEvent(r2, r3)     // Catch:{ Exception -> 0x0109 }
            goto L_0x00f2
        L_0x00ed:
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            r10.actionEvent(r2, r3)     // Catch:{ Exception -> 0x0109 }
        L_0x00f2:
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            r1 = 20
            r10.actionEvent(r1, r3)     // Catch:{ Exception -> 0x0109 }
        L_0x00f9:
            com.app.office.system.IControl r10 = r9.control     // Catch:{ Exception -> 0x0109 }
            byte r10 = r10.getApplicationType()     // Catch:{ Exception -> 0x0109 }
            if (r10 == 0) goto L_0x0105
            r9.zoomChange = r0     // Catch:{ Exception -> 0x0109 }
            goto L_0x0105
        L_0x0104:
            r11 = 0
        L_0x0105:
            r9.singleTabup = r0     // Catch:{ Exception -> 0x0109 }
            r0 = r11
            goto L_0x0124
        L_0x0109:
            r10 = move-exception
            r0 = r11
            goto L_0x0117
        L_0x010c:
            r9.stopFling()     // Catch:{ Exception -> 0x0116 }
            int r10 = r11.getPointerId(r0)     // Catch:{ Exception -> 0x0116 }
            r9.mActivePointerId = r10     // Catch:{ Exception -> 0x0116 }
            goto L_0x0124
        L_0x0116:
            r10 = move-exception
        L_0x0117:
            com.app.office.system.IControl r11 = r9.control
            com.app.office.system.SysKit r11 = r11.getSysKit()
            com.app.office.system.ErrorUtil r11 = r11.getErrorKit()
            r11.writerLog(r10)
        L_0x0124:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.system.beans.AEventManage.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0118  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean zoom(android.view.MotionEvent r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            com.app.office.system.IControl r2 = r0.control
            com.app.office.system.IMainFrame r2 = r2.getMainFrame()
            boolean r2 = r2.isTouchZoom()
            r3 = 1
            if (r2 != 0) goto L_0x0012
            return r3
        L_0x0012:
            com.app.office.system.IControl r2 = r0.control
            r4 = 536870917(0x20000005, float:1.0842028E-19)
            r5 = 0
            java.lang.Object r2 = r2.getActionValue(r4, r5)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            com.app.office.system.IControl r6 = r0.control
            r7 = 536870918(0x20000006, float:1.084203E-19)
            java.lang.Object r5 = r6.getActionValue(r7, r5)
            java.lang.Float r5 = (java.lang.Float) r5
            float r5 = r5.floatValue()
            r6 = 1176256512(0x461c4000, float:10000.0)
            float r7 = r2 * r6
            int r7 = (int) r7
            float r8 = r5 * r6
            int r8 = (int) r8
            r9 = 0
            if (r7 != r8) goto L_0x003f
            r7 = 1
            goto L_0x0040
        L_0x003f:
            r7 = 0
        L_0x0040:
            int r8 = r18.getActionMasked()
            r10 = 4611686018427387904(0x4000000000000000, double:2.0)
            r12 = 2
            if (r8 == r12) goto L_0x008c
            r5 = 5
            if (r8 == r5) goto L_0x004e
            goto L_0x0115
        L_0x004e:
            float r5 = r1.getX(r9)
            float r7 = r1.getY(r9)
            float r8 = r1.getX(r3)
            float r1 = r1.getY(r3)
            float r13 = java.lang.Math.min(r5, r8)
            float r5 = r5 - r8
            float r8 = java.lang.Math.abs(r5)
            r14 = 1073741824(0x40000000, float:2.0)
            float r8 = r8 / r14
            float r13 = r13 + r8
            int r8 = (int) r13
            r0.midXDoublePoint = r8
            float r8 = java.lang.Math.min(r7, r1)
            float r7 = r7 - r1
            float r1 = java.lang.Math.abs(r7)
            float r1 = r1 / r14
            float r8 = r8 + r1
            int r1 = (int) r8
            r0.midYDoublePoint = r1
            float r5 = r5 * r5
            float r7 = r7 * r7
            float r5 = r5 + r7
            double r7 = (double) r5
            double r7 = java.lang.Math.sqrt(r7)
            double r7 = r7 / r10
            float r1 = (float) r7
            r0.distance = r1
            goto L_0x0115
        L_0x008c:
            float r8 = r1.getX(r9)
            float r13 = r1.getY(r9)
            float r14 = r1.getX(r3)
            float r1 = r1.getY(r3)
            float r8 = r8 - r14
            float r8 = r8 * r8
            float r13 = r13 - r1
            float r13 = r13 * r13
            float r8 = r8 + r13
            double r13 = (double) r8
            double r13 = java.lang.Math.sqrt(r13)
            double r13 = r13 / r10
            float r1 = (float) r13
            float r8 = r0.distance
            float r8 = r8 - r1
            float r8 = java.lang.Math.abs(r8)
            r10 = 1090519040(0x41000000, float:8.0)
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 <= 0) goto L_0x0115
            float r8 = r0.distance
            int r8 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x00bf
            r8 = 1
            goto L_0x00c0
        L_0x00bf:
            r8 = 0
        L_0x00c0:
            float r10 = r2 - r5
            float r10 = java.lang.Math.abs(r10)
            double r10 = (double) r10
            r13 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            int r15 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            if (r15 >= 0) goto L_0x00d5
            if (r8 != 0) goto L_0x00d5
            if (r7 == 0) goto L_0x00d5
            goto L_0x00e9
        L_0x00d5:
            r10 = 1077936128(0x40400000, float:3.0)
            float r11 = r2 - r10
            float r11 = java.lang.Math.abs(r11)
            double r13 = (double) r11
            r15 = 4562254508917369340(0x3f50624dd2f1a9fc, double:0.001)
            int r11 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r11 >= 0) goto L_0x00eb
            if (r8 == 0) goto L_0x00eb
        L_0x00e9:
            r5 = 0
            goto L_0x010d
        L_0x00eb:
            r11 = 1036831949(0x3dcccccd, float:0.1)
            if (r8 == 0) goto L_0x00f2
            float r2 = r2 + r11
            goto L_0x00f3
        L_0x00f2:
            float r2 = r2 - r11
        L_0x00f3:
            int r11 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r11 <= 0) goto L_0x00fa
            r5 = 1077936128(0x40400000, float:3.0)
            goto L_0x0100
        L_0x00fa:
            int r10 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r10 >= 0) goto L_0x00ff
            goto L_0x0100
        L_0x00ff:
            r5 = r2
        L_0x0100:
            if (r8 == 0) goto L_0x010b
            if (r7 == 0) goto L_0x010b
            r2 = 1092616192(0x41200000, float:10.0)
            float r5 = r5 * r2
            int r5 = (int) r5
            float r5 = (float) r5
            float r5 = r5 / r2
        L_0x010b:
            r2 = r5
            r5 = 1
        L_0x010d:
            if (r5 == 0) goto L_0x0110
            goto L_0x0112
        L_0x0110:
            float r1 = r0.distance
        L_0x0112:
            r0.distance = r1
            goto L_0x0116
        L_0x0115:
            r5 = 0
        L_0x0116:
            if (r5 == 0) goto L_0x017a
            r0.isScroll = r3
            r0.zoomChange = r3
            com.app.office.system.IControl r1 = r0.control
            r5 = 3
            int[] r5 = new int[r5]
            float r6 = r6 * r2
            int r6 = (int) r6
            r5[r9] = r6
            int r6 = r0.midXDoublePoint
            r5[r3] = r6
            int r6 = r0.midYDoublePoint
            r5[r12] = r6
            r1.actionEvent(r4, r5)
            com.app.office.system.IControl r1 = r0.control
            android.view.View r1 = r1.getView()
            r1.postInvalidate()
            com.app.office.system.IControl r1 = r0.control
            com.app.office.system.IMainFrame r1 = r1.getMainFrame()
            boolean r1 = r1.isShowZoomingMsg()
            if (r1 == 0) goto L_0x017a
            com.app.office.system.IControl r1 = r0.control
            byte r1 = r1.getApplicationType()
            if (r1 != r12) goto L_0x0157
            com.app.office.system.IControl r1 = r0.control
            boolean r1 = r1.isSlideShow()
            if (r1 == 0) goto L_0x0157
            return r3
        L_0x0157:
            android.widget.Toast r1 = r0.toast
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = 1120403456(0x42c80000, float:100.0)
            float r2 = r2 * r5
            int r2 = java.lang.Math.round(r2)
            r4.append(r2)
            java.lang.String r2 = "%"
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r1.setText(r2)
            android.widget.Toast r1 = r0.toast
            r1.show()
        L_0x017a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.system.beans.AEventManage.zoom(android.view.MotionEvent):boolean");
    }

    public boolean onDown(MotionEvent motionEvent) {
        return this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 1);
    }

    public void onShowPress(MotionEvent motionEvent) {
        this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 2);
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (!this.isScroll) {
            this.singleTabup = true;
        }
        return this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 3);
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.isScroll = true;
        return this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, motionEvent2, f, f2, (byte) 4);
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 5);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, motionEvent2, f, f2, (byte) 6);
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 7);
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        return this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 8);
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return this.control.getMainFrame().onEventMethod(this.control.getView(), motionEvent, (MotionEvent) null, -1.0f, -1.0f, (byte) 9);
    }

    public void onClick(View view) {
        this.control.getMainFrame().onEventMethod(this.control.getView(), (MotionEvent) null, (MotionEvent) null, -1.0f, -1.0f, (byte) 10);
    }

    public void computeScroll() {
        if (this.isFling && this.mScroller.isFinished()) {
            this.isFling = false;
            this.control.actionEvent(EventConstant.APP_GENERATED_PICTURE_ID, (Object) null);
            this.control.actionEvent(20, (Object) null);
        }
    }

    public void stopFling() {
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.isFling = true;
            this.mScroller.abortAnimation();
        }
    }

    public int getMiddleXOfDoublePoint() {
        return this.midXDoublePoint;
    }

    public int getMiddleYOfDoublePoint() {
        return this.midYDoublePoint;
    }

    public void dispose() {
        this.control = null;
        this.gesture = null;
        this.mVelocityTracker = null;
        this.toast = null;
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        this.mScroller = null;
    }
}
