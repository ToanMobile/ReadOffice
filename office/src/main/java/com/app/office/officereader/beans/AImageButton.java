package com.app.office.officereader.beans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.app.office.common.PaintKit;
import com.app.office.system.IControl;

public class AImageButton extends View implements GestureDetector.OnGestureListener, View.OnClickListener {
    protected int actionID;
    protected Bitmap bitmap;
    protected Bitmap bitmapDisable;
    protected IControl control;
    protected int focusBgResID = -1;
    protected GestureDetector gesture;
    protected boolean longPressed;
    protected int normalBgResID = -1;
    protected int pushBgResID = -1;
    protected String toolstip;

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public AImageButton(Context context, IControl iControl, String str, int i, int i2, int i3) {
        super(context);
        this.control = iControl;
        this.toolstip = str;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = context.getResources().getDisplayMetrics().densityDpi;
        options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), i, options);
        if (i2 != -1) {
            this.bitmapDisable = BitmapFactory.decodeResource(getContext().getResources(), i2, options);
        }
        this.actionID = i3;
        this.gesture = new GestureDetector(context, this);
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
        setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        int i2 = z ? this.focusBgResID : this.normalBgResID;
        if (i2 != -1) {
            setBackgroundResource(i2);
        }
    }

    public void onClick(View view) {
        if (!this.longPressed && (view instanceof AImageButton)) {
            this.control.actionEvent(((AImageButton) view).getActionID(), (Object) null);
        }
        this.longPressed = false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        if (r0 != 3) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            android.view.GestureDetector r0 = r5.gesture
            r0.onTouchEvent(r6)
            int r0 = r6.getAction()
            boolean r1 = r5.isEnabled()
            r2 = 18
            r3 = 0
            r4 = 1
            if (r1 != 0) goto L_0x001f
            int r6 = r6.getAction()
            if (r6 != r4) goto L_0x001e
            com.app.office.system.IControl r6 = r5.control
            r6.actionEvent(r2, r3)
        L_0x001e:
            return r4
        L_0x001f:
            r1 = -1
            if (r0 == 0) goto L_0x003c
            if (r0 == r4) goto L_0x002b
            r4 = 2
            if (r0 == r4) goto L_0x003c
            r4 = 3
            if (r0 == r4) goto L_0x002b
            goto L_0x0043
        L_0x002b:
            int r0 = r5.normalBgResID
            if (r0 != r1) goto L_0x0033
            r5.setBackgroundDrawable(r3)
            goto L_0x0036
        L_0x0033:
            r5.setBackgroundResource(r0)
        L_0x0036:
            com.app.office.system.IControl r0 = r5.control
            r0.actionEvent(r2, r3)
            goto L_0x0043
        L_0x003c:
            int r0 = r5.pushBgResID
            if (r0 == r1) goto L_0x0043
            r5.setBackgroundResource(r0)
        L_0x0043:
            boolean r6 = super.onTouchEvent(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.officereader.beans.AImageButton.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void onDraw(Canvas canvas) {
        if (this.bitmap != null) {
            if (isEnabled()) {
                canvas.drawBitmap(this.bitmap, (float) ((getWidth() - this.bitmap.getWidth()) / 2), (float) ((getHeight() - this.bitmap.getHeight()) / 2), PaintKit.instance().getPaint());
                return;
            }
            Bitmap bitmap2 = this.bitmapDisable;
            if (bitmap2 != null) {
                canvas.drawBitmap(bitmap2, (float) ((getWidth() - this.bitmapDisable.getWidth()) / 2), (float) ((getHeight() - this.bitmapDisable.getHeight()) / 2), PaintKit.instance().getPaint());
            }
        }
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.longPressed = true;
        String str = this.toolstip;
        if (str != null && str.length() > 0) {
            this.control.actionEvent(17, this.toolstip);
        }
    }

    public int getActionID() {
        return this.actionID;
    }

    public String getToolstip() {
        return this.toolstip;
    }

    public void setFocusBgResID(int i) {
        this.focusBgResID = i;
    }

    public void setPushBgResID(int i) {
        this.pushBgResID = i;
    }

    public void setNormalBgResID(int i) {
        setBackgroundResource(i);
        this.normalBgResID = i;
    }

    public int getIconWidth() {
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 == null) {
            return 0;
        }
        return bitmap2.getWidth();
    }

    public int getIconHeight() {
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 == null) {
            return 0;
        }
        return bitmap2.getHeight();
    }

    public void dispose() {
        this.toolstip = null;
        this.control = null;
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.bitmap = null;
        }
        Bitmap bitmap3 = this.bitmapDisable;
        if (bitmap3 != null) {
            bitmap3.recycle();
            this.bitmapDisable = null;
        }
        this.gesture = null;
    }
}
