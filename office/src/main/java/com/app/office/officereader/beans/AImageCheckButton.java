package com.app.office.officereader.beans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.app.office.common.PaintKit;
import com.app.office.system.IControl;

public class AImageCheckButton extends AImageButton {
    public static final short CHECK = 1;
    public static final short DISABLE = 0;
    public static final short UNCHECK = 2;
    private short state;
    protected Bitmap uncheckBitmap;
    protected String uncheckTips;

    public AImageCheckButton(Context context, IControl iControl, String str, String str2, int i, int i2, int i3, int i4) {
        super(context, iControl, str, i, i3, i4);
        this.uncheckTips = str2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = context.getResources().getDisplayMetrics().densityDpi;
        options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.uncheckBitmap = BitmapFactory.decodeResource(context.getResources(), i2, options);
    }

    public void onDraw(Canvas canvas) {
        Paint paint = PaintKit.instance().getPaint();
        short s = this.state;
        if (s == 0) {
            canvas.drawBitmap(this.bitmapDisable, (float) ((getWidth() - this.bitmapDisable.getWidth()) / 2), (float) ((getHeight() - this.bitmapDisable.getHeight()) / 2), paint);
        } else if (s == 1) {
            canvas.drawBitmap(this.bitmap, (float) ((getWidth() - this.bitmap.getWidth()) / 2), (float) ((getHeight() - this.bitmap.getHeight()) / 2), paint);
        } else if (s == 2) {
            canvas.drawBitmap(this.uncheckBitmap, (float) ((getWidth() - this.uncheckBitmap.getWidth()) / 2), (float) ((getHeight() - this.uncheckBitmap.getHeight()) / 2), paint);
        }
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.longPressed = true;
        short s = this.state;
        if (s == 1) {
            this.control.actionEvent(17, this.toolstip);
        } else if (s == 2) {
            this.control.actionEvent(17, this.uncheckTips);
        }
    }

    public void onClick(View view) {
        if (this.longPressed) {
            this.longPressed = false;
            return;
        }
        short s = this.state;
        boolean z = true;
        if (s == 1) {
            setState(2);
        } else if (s == 2) {
            setState(1);
        }
        IControl iControl = this.control;
        int actionID = ((AImageButton) view).getActionID();
        if (this.state != 1) {
            z = false;
        }
        iControl.actionEvent(actionID, Boolean.valueOf(z));
        postInvalidate();
        this.longPressed = false;
    }

    public void setState(short s) {
        this.state = s;
        setEnabled(s != 0);
    }

    public short getState() {
        return this.state;
    }

    public void dispose() {
        super.dispose();
        Bitmap bitmap = this.uncheckBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.uncheckBitmap = null;
        }
    }
}
