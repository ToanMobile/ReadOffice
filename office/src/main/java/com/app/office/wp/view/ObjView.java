package com.app.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.picture.PictureKit;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.WPAutoShape;
import com.app.office.common.shape.WPPictureShape;
import com.app.office.common.shape.WatermarkShape;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.ViewKit;
import com.app.office.system.IControl;

public class ObjView extends LeafView {
    private boolean isInline;
    private PageAttr pageAttr;
    private WPAutoShape picShape;
    private Rect rect = new Rect();

    public void free() {
    }

    public short getType() {
        return 8;
    }

    public ObjView() {
    }

    public ObjView(IElement iElement, IElement iElement2, WPAutoShape wPAutoShape) {
        super(iElement, iElement2);
        this.picShape = wPAutoShape;
    }

    public void initProperty(IElement iElement, IElement iElement2) {
        this.elem = iElement;
        this.paint = new Paint();
        this.paint.setFlags(1);
        this.paint.setTextSize(20.0f);
    }

    public int doLayout(DocAttr docAttr, PageAttr pageAttr2, ParaAttr paraAttr, int i, int i2, int i3, int i4, long j, int i5) {
        int i6;
        this.pageAttr = pageAttr2;
        this.isInline = docAttr.rootType == 1 || !(this.picShape.getWrap() == 3 || this.picShape.getWrap() == 6);
        if (this.picShape.isWatermarkShape()) {
            this.isInline = false;
        } else if (WPViewKit.instance().getArea(this.start + 1) == 1152921504606846976L || WPViewKit.instance().getArea(this.start + 1) == 2305843009213693952L) {
            this.isInline = true;
        }
        Rectangle bounds = this.picShape.getBounds();
        if (this.isInline) {
            i6 = bounds.width;
            setSize(i6, bounds.height);
        } else {
            if (!this.picShape.isWatermarkShape()) {
                PositionLayoutKit.instance().processShapePosition(this, this.picShape, pageAttr2);
            }
            i6 = 0;
        }
        setEndOffset(this.start + 1);
        if (!ViewKit.instance().getBitValue(i5, 0) && i6 > i3) {
            return 1;
        }
        return 0;
    }

    public float getTextWidth() {
        if (this.picShape.isWatermarkShape()) {
            return (float) this.picShape.getBounds().width;
        }
        if (this.isInline) {
            return (float) ((int) ((WPPictureShape) this.picShape).getPictureShape().getBounds().getWidth());
        }
        return 0.0f;
    }

    public synchronized void draw(Canvas canvas, int i, int i2, float f) {
        synchronized (this) {
            if (this.isInline) {
                IControl control = getControl();
                float f2 = (float) i;
                int round = Math.round((((float) this.x) * f) + f2);
                float f3 = (float) i2;
                int round2 = Math.round((((float) this.y) * f) + f3);
                this.rect.set(round, round2, Math.round((((float) this.x) * f) + f2 + (((float) getWidth()) * f)), Math.round((((float) this.y) * f) + f3 + (((float) getHeight()) * f)));
                if (!this.picShape.isWatermarkShape()) {
                    BackgroundDrawer.drawLineAndFill(canvas, control, getPageNumber(), ((WPPictureShape) this.picShape).getPictureShape(), this.rect, f);
                    Canvas canvas2 = canvas;
                    IControl iControl = control;
                    PictureKit.instance().drawPicture(canvas2, iControl, getPageNumber(), ((WPPictureShape) this.picShape).getPictureShape().getPicture(getControl()), (float) round, (float) round2, f, ((float) getWidth()) * f, ((float) getHeight()) * f, ((WPPictureShape) this.picShape).getPictureShape().getPictureEffectInfor());
                }
            }
        }
    }

    public synchronized void drawForWrap(Canvas canvas, int i, int i2, float f) {
        float f2 = f;
        synchronized (this) {
            int i3 = this.x;
            int i4 = this.y;
            Rectangle bounds = this.picShape.getBounds();
            IControl control = getControl();
            float f3 = (float) i;
            int round = Math.round((((float) this.x) * f2) + f3);
            float f4 = (float) i2;
            int round2 = Math.round((((float) this.y) * f2) + f4);
            double d = (double) f2;
            this.rect.set(round, round2, (int) Math.round(((double) ((((float) this.x) * f2) + f3)) + (bounds.getWidth() * d)), (int) Math.round(((double) ((((float) this.y) * f2) + f4)) + (bounds.getHeight() * d)));
            if (this.picShape.isWatermarkShape()) {
                int i5 = (this.pageAttr.pageWidth - this.pageAttr.leftMargin) - this.pageAttr.rightMargin;
                float f5 = f4 + ((((float) this.pageAttr.topMargin) + (((float) ((this.pageAttr.pageHeight - this.pageAttr.topMargin) - this.pageAttr.bottomMargin)) / 2.0f)) * f2);
                int round3 = Math.round((f3 + ((((float) this.pageAttr.leftMargin) + (((float) i5) / 2.0f)) * f2)) - ((((float) bounds.width) * f2) / 2.0f));
                int round4 = Math.round(f5 - ((((float) bounds.height) * f2) / 2.0f));
                PictureKit.instance().drawPicture(canvas, control, getPageNumber(), PictureShape.getPicture(control, ((WatermarkShape) this.picShape).getPictureIndex()), (float) round3, (float) round4, f, (float) Math.round(bounds.getWidth() * d), (float) Math.round(bounds.getHeight() * d), ((WatermarkShape) this.picShape).getEffectInfor());
            } else {
                BackgroundDrawer.drawLineAndFill(canvas, control, getPageNumber(), ((WPPictureShape) this.picShape).getPictureShape(), this.rect, f);
                PictureKit.instance().drawPicture(canvas, control, getPageNumber(), ((WPPictureShape) this.picShape).getPictureShape().getPicture(getControl()), (float) round, (float) round2, f, (float) Math.round(bounds.getWidth() * d), (float) Math.round(bounds.getHeight() * d), ((WPPictureShape) this.picShape).getPictureShape().getPictureEffectInfor());
            }
        }
    }

    public Rectangle modelToView(long j, Rectangle rectangle, boolean z) {
        rectangle.x += getX();
        rectangle.y += getY();
        return rectangle;
    }

    public long viewToModel(int i, int i2, boolean z) {
        return this.start;
    }

    public boolean isBehindDoc() {
        return this.picShape.getWrap() == 6;
    }

    public int getBaseline() {
        if (this.picShape.isWatermarkShape() || !this.isInline) {
            return 0;
        }
        return (int) ((WPPictureShape) this.picShape).getPictureShape().getBounds().getHeight();
    }

    public boolean isInline() {
        return this.isInline;
    }

    public void dispose() {
        super.dispose();
        this.picShape = null;
    }
}
