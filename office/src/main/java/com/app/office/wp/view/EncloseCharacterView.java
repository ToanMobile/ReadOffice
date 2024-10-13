package com.app.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.app.office.simpletext.model.IElement;

public class EncloseCharacterView extends LeafView {
    protected Paint enclosePaint;
    protected Path path;

    public void free() {
    }

    public short getType() {
        return 14;
    }

    public EncloseCharacterView() {
    }

    public EncloseCharacterView(IElement iElement, IElement iElement2) {
        super(iElement, iElement2);
    }

    public void initProperty(IElement iElement, IElement iElement2) {
        super.initProperty(iElement, iElement2);
        Paint paint = new Paint();
        this.enclosePaint = paint;
        paint.setColor(this.charAttr.fontColor);
        this.enclosePaint.setStyle(Paint.Style.STROKE);
        this.enclosePaint.setAntiAlias(true);
        this.path = new Path();
    }

    public void draw(Canvas canvas, int i, int i2, float f) {
        super.draw(canvas, i, i2, f);
        drawEnclose(canvas, i, i2, f);
    }

    private void drawEnclose(Canvas canvas, int i, int i2, float f) {
        int i3 = ((int) (((float) this.x) * f)) + i;
        int i4 = ((int) (((float) this.y) * f)) + i2;
        int width = (int) (((float) getWidth()) * f);
        int height = (int) (((float) getHeight()) * f);
        if (this.charAttr.encloseType == 0) {
            canvas.drawArc(new RectF((float) i3, (float) i4, (float) (i3 + width), (float) (i4 + height)), 0.0f, 360.0f, false, this.enclosePaint);
        } else if (this.charAttr.encloseType == 1) {
            canvas.drawRect((float) i3, (float) i4, (float) (i3 + width), (float) (i4 + height), this.enclosePaint);
        } else if (this.charAttr.encloseType == 2) {
            this.path.reset();
            this.path.moveTo((float) ((width / 2) + i3), (float) i4);
            float f2 = (float) (i4 + height);
            this.path.lineTo((float) i3, f2);
            this.path.lineTo((float) (i3 + width), f2);
            this.path.close();
            canvas.drawPath(this.path, this.enclosePaint);
        } else if (this.charAttr.encloseType == 3) {
            this.path.reset();
            float f3 = (float) ((width / 2) + i3);
            this.path.moveTo(f3, (float) i4);
            float f4 = (float) ((height / 2) + i4);
            this.path.lineTo((float) i3, f4);
            this.path.lineTo(f3, (float) (i4 + height));
            this.path.lineTo((float) (i3 + width), f4);
            this.path.close();
            canvas.drawPath(this.path, this.enclosePaint);
        }
    }

    public void dispose() {
        super.dispose();
        this.paint = null;
    }
}
