package com.app.office.system.beans.CalloutView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.View;
import com.app.office.system.IControl;
import java.util.List;

public class CalloutView extends View {
    private static final float TOUCH_TOLERANCE = 4.0f;
    private CalloutManager calloutMgr;
    private IControl control;
    private int index = 0;
    private int left = 0;
    /* access modifiers changed from: private */
    public IExportListener mListener;
    private PathInfo mPathInfo = null;
    private List<PathInfo> mPathList = null;
    private float mX;
    private float mY;
    private final int offset = 5;
    private Runnable runnable = null;
    private int top = 0;
    private float zoom = 1.0f;

    public CalloutView(Context context, IControl iControl, IExportListener iExportListener) {
        super(context);
        this.control = iControl;
        this.mListener = iExportListener;
        this.calloutMgr = iControl.getSysKit().getCalloutManager();
    }

    public void setZoom(float f) {
        this.zoom = f;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect clipBounds = canvas.getClipBounds();
        List<PathInfo> path = this.calloutMgr.getPath(this.index, false);
        this.mPathList = path;
        if (path != null) {
            for (int i = 0; i < this.mPathList.size(); i++) {
                PathInfo pathInfo = this.mPathList.get(i);
                MyPaint myPaint = new MyPaint();
                myPaint.setStrokeWidth((float) pathInfo.width);
                myPaint.setColor(pathInfo.color);
                canvas.save();
                canvas.clipRect(this.left, this.top, clipBounds.right, clipBounds.bottom);
                float f = this.zoom;
                canvas.scale(f, f);
                canvas.drawPath(pathInfo.path, myPaint);
                canvas.restore();
            }
        }
    }

    public void setClip(int i, int i2) {
        this.left = i;
        this.top = i2;
    }

    private void touch_start(float f, float f2) {
        float f3 = this.zoom;
        float f4 = f / f3;
        float f5 = f2 / f3;
        this.mX = f4;
        this.mY = f5;
        if (this.calloutMgr.getDrawingMode() == 1) {
            PathInfo pathInfo = new PathInfo();
            this.mPathInfo = pathInfo;
            pathInfo.path = new Path();
            this.mPathInfo.path.moveTo(f4, f5);
            this.mPathInfo.color = this.calloutMgr.getColor();
            this.mPathInfo.width = this.calloutMgr.getWidth();
            List<PathInfo> path = this.calloutMgr.getPath(this.index, true);
            this.mPathList = path;
            path.add(this.mPathInfo);
        }
    }

    private void touch_move(float f, float f2) {
        if (this.calloutMgr.getDrawingMode() == 1) {
            float f3 = this.zoom;
            float f4 = f / f3;
            float f5 = f2 / f3;
            float abs = Math.abs(f4 - this.mX);
            float abs2 = Math.abs(f5 - this.mY);
            if (abs >= TOUCH_TOLERANCE || abs2 >= TOUCH_TOLERANCE) {
                Path path = this.mPathInfo.path;
                float f6 = this.mX;
                float f7 = this.mY;
                path.quadTo(f6, f7, (f4 + f6) / 2.0f, (f5 + f7) / 2.0f);
                this.mX = f4;
                this.mY = f5;
            }
        }
    }

    private void touch_up() {
        if (this.calloutMgr.getDrawingMode() == 1) {
            this.mPathInfo.path.lineTo(this.mX, this.mY);
            this.mPathInfo.x = this.mX + 1.0f;
            this.mPathInfo.y = this.mY + 1.0f;
        } else if (this.calloutMgr.getDrawingMode() == 2 && this.mPathList != null) {
            for (int i = 0; i < this.mPathList.size(); i++) {
                PathInfo pathInfo = this.mPathList.get(i);
                Path path = new Path(pathInfo.path);
                path.lineTo(pathInfo.x, pathInfo.y);
                RectF rectF = new RectF();
                path.computeBounds(rectF, false);
                Region region = new Region();
                region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
                float f = this.mX;
                float f2 = this.mY;
                if (region.op(new Region(((int) f) - 5, ((int) f2) - 5, ((int) f) + 5, ((int) f2) + 5), Region.Op.INTERSECT)) {
                    this.mPathList.remove(i);
                }
            }
        }
    }

    private void exportImage() {
        Runnable runnable2 = this.runnable;
        if (runnable2 != null) {
            removeCallbacks(runnable2);
        }
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                CalloutView.this.mListener.exportImage();
            }
        };
        this.runnable = r0;
        postDelayed(r0, 1000);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.calloutMgr.getDrawingMode() == 0) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            touch_start(x, y);
            invalidate();
        } else if (action == 1) {
            touch_up();
            invalidate();
            exportImage();
        } else if (action == 2) {
            touch_move(x, y);
            invalidate();
        }
        return true;
    }
}
