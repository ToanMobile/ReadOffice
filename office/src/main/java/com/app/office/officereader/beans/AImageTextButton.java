package com.app.office.officereader.beans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.app.office.system.IControl;

public class AImageTextButton extends AImageButton {
    public static final int GAP = 5;
    public static final int TEXT_BOTTOM = 1;
    public static final int TEXT_LEFT = 2;
    public static final int TEXT_RIGHT = 3;
    public static final int TEXT_TOP = 0;
    private int bottomIndent;
    private int leftIndent;
    private Paint paint;
    private int rightIndent;
    private String text;
    private int textGravity = -1;
    private int textHeight;
    private int textWidth;
    private int topIndent;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AImageTextButton(Context context, IControl iControl, String str, String str2, int i, int i2, int i3, int i4, int i5) {
        super(context, iControl, str2, i, i2, i3);
        String str3 = str;
        int i6 = i4;
        setEnabled(true);
        this.text = str3;
        Paint paint2 = new Paint();
        this.paint = paint2;
        if (i6 >= 0 && i6 <= 3) {
            this.textGravity = i6;
            paint2.setFlags(1);
            this.paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, 0));
            this.paint.setTextSize((float) i5);
            if (str3 != null && str.length() > 0) {
                this.textWidth = (int) this.paint.measureText(str);
                this.textHeight = (int) Math.ceil((double) (this.paint.descent() - this.paint.ascent()));
            }
        }
    }

    public void onDraw(Canvas canvas) {
        Rect clipBounds = canvas.getClipBounds();
        int i = clipBounds.right - clipBounds.left;
        int i2 = clipBounds.bottom - clipBounds.top;
        int width = this.bitmap.getWidth();
        int height = this.bitmap.getHeight();
        int i3 = this.textGravity;
        if (i3 == 0) {
            int i4 = i2 - height;
            canvas.drawText(this.text, (float) (i - (this.textWidth / 2)), ((float) (((i4 - 10) - this.textHeight) / 2)) - this.paint.ascent(), this.paint);
            canvas.drawBitmap(this.bitmap, (float) ((i - width) / 2), (float) (i4 - 5), this.paint);
        } else if (i3 == 1) {
            int i5 = (((i2 - height) - 30) - this.textHeight) / 2;
            this.topIndent = i5;
            canvas.drawBitmap(this.bitmap, (float) ((i - width) / 2), (float) i5, this.paint);
            canvas.drawText(this.text, (float) ((i - this.textWidth) / 2), ((float) ((height + this.topIndent) + 30)) - this.paint.ascent(), this.paint);
        } else if (i3 == 2) {
            canvas.drawText(this.text, (float) ((((i - this.textWidth) - width) - 10) / 2), ((float) ((i2 - this.textHeight) / 2)) - this.paint.ascent(), this.paint);
            canvas.drawBitmap(this.bitmap, (float) ((i - width) - 5), (float) ((i2 - height) / 2), this.paint);
        } else if (i3 == 3) {
            int i6 = i / 10;
            this.leftIndent = i6;
            canvas.drawBitmap(this.bitmap, (float) i6, (float) ((i2 - height) / 2), this.paint);
            canvas.drawText(this.text, (float) (width + this.leftIndent + 30), ((float) ((i2 - this.textHeight) / 2)) - this.paint.ascent(), this.paint);
        }
    }

    public int getTopIndent() {
        return this.topIndent;
    }

    public void setTopIndent(int i) {
        this.topIndent = i;
    }

    public int getBottomIndent() {
        return this.bottomIndent;
    }

    public void setBottomIndent(int i) {
        this.bottomIndent = i;
    }

    public int getLeftIndent() {
        return this.leftIndent;
    }

    public void setLeftIndent(int i) {
        this.leftIndent = i;
    }

    public int getRightIndent() {
        return this.rightIndent;
    }

    public void setRightIndent(int i) {
        this.rightIndent = i;
    }

    public void dispose() {
        super.dispose();
        this.text = null;
    }
}
