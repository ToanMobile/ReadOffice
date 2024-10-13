package com.app.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.PaintKit;
import com.app.office.common.autoshape.AutoShapeKit;
import com.app.office.common.picture.Picture;
import com.app.office.common.picture.PictureKit;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.WPAutoShape;
import com.app.office.common.shape.WPChartShape;
import com.app.office.common.shape.WPGroupShape;
import com.app.office.common.shape.WPPictureShape;
import com.app.office.common.shape.WatermarkShape;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.IElement;
import com.app.office.simpletext.view.DocAttr;
import com.app.office.simpletext.view.PageAttr;
import com.app.office.simpletext.view.ParaAttr;
import com.app.office.simpletext.view.ViewKit;
import com.app.office.system.IControl;
import com.app.office.thirdpart.achartengine.chart.AbstractChart;
import java.util.Hashtable;
import java.util.Map;

public class ShapeView extends LeafView {
    private static final int GAP = 100;
    private boolean isInline;
    private PageAttr pageAttr;
    private Rect rect = new Rect();
    private Map<Integer, WPSTRoot> roots;
    private WPAutoShape wpShape;

    public void free() {
    }

    public short getType() {
        return 13;
    }

    public ShapeView() {
    }

    public ShapeView(IElement iElement, IElement iElement2, AutoShape autoShape) {
        super(iElement, iElement2);
        this.wpShape = (WPAutoShape) autoShape;
        this.roots = new Hashtable();
    }

    public int doLayout(DocAttr docAttr, PageAttr pageAttr2, ParaAttr paraAttr, int i, int i2, int i3, int i4, long j, int i5) {
        int i6;
        PageAttr pageAttr3 = pageAttr2;
        this.pageAttr = pageAttr3;
        this.isInline = docAttr.rootType == 1 || !(this.wpShape.getWrap() == 3 || this.wpShape.getWrap() == 6);
        if (this.wpShape.isWatermarkShape()) {
            this.isInline = false;
        } else if (WPViewKit.instance().getArea(this.start + 1) == 1152921504606846976L || WPViewKit.instance().getArea(this.start + 1) == 2305843009213693952L) {
            this.isInline = true;
        }
        Rectangle bounds = this.wpShape.getBounds();
        if (this.isInline) {
            i6 = bounds.width;
            setSize(i6, bounds.height);
        } else {
            if (this.wpShape.isWatermarkShape()) {
                WatermarkShape watermarkShape = (WatermarkShape) this.wpShape;
                this.paint = new Paint();
                this.paint.setAntiAlias(true);
                String watermartString = watermarkShape.getWatermartString();
                if (watermartString != null && watermartString.length() > 0) {
                    int length = watermartString.length();
                    int i7 = (pageAttr3.pageWidth - pageAttr3.leftMargin) - pageAttr3.rightMargin;
                    if (watermarkShape.isAutoFontSize()) {
                        int i8 = i7 / length;
                        this.paint.setTextSize((float) i8);
                        this.paint.getTextBounds(watermartString, 0, length, this.rect);
                        if (this.rect.width() < i7) {
                            int i9 = i8;
                            while (this.rect.width() < i7) {
                                int i10 = i8 + 1;
                                this.paint.setTextSize((float) i10);
                                this.paint.getTextBounds(watermartString, 0, length, this.rect);
                                int i11 = i10;
                                i9 = i8;
                                i8 = i11;
                            }
                            i8 = i9;
                        } else if (this.rect.width() > i7) {
                            int i12 = i8;
                            while (this.rect.width() > i7) {
                                int i13 = i12 - 1;
                                this.paint.setTextSize((float) i13);
                                this.paint.getTextBounds(watermartString, 0, length, this.rect);
                                int i14 = i12;
                                i12 = i13;
                                i8 = i14;
                            }
                        }
                        watermarkShape.setFontSize(i8);
                        this.paint.setTextSize((float) i8);
                    } else {
                        this.paint.setTextSize((float) watermarkShape.getFontSize());
                    }
                    this.paint.setColor(watermarkShape.getFontColor());
                    this.paint.setAlpha(Math.round(watermarkShape.getOpacity() * 255.0f));
                    this.paint.getTextBounds(watermartString, 0, length, this.rect);
                    setX((pageAttr3.pageWidth - this.rect.width()) / 2);
                    setY((pageAttr3.pageHeight - this.rect.height()) / 2);
                }
            } else {
                PositionLayoutKit.instance().processShapePosition(this, this.wpShape, pageAttr3);
            }
            i6 = 0;
        }
        setEndOffset(this.start + 1);
        if (!ViewKit.instance().getBitValue(i5, 0) && i6 > i3) {
            return 1;
        }
        WPAutoShape wPAutoShape = this.wpShape;
        layoutTextbox(wPAutoShape, wPAutoShape.getGroupShape());
        return 0;
    }

    private void layoutTextbox(WPAutoShape wPAutoShape, WPGroupShape wPGroupShape) {
        if (wPGroupShape != null) {
            IShape[] shapes = wPGroupShape.getShapes();
            if (shapes != null) {
                for (IShape iShape : shapes) {
                    if (iShape.getType() == 7) {
                        layoutTextbox((WPAutoShape) null, (WPGroupShape) iShape);
                    } else if (iShape instanceof WPAutoShape) {
                        WPAutoShape wPAutoShape2 = (WPAutoShape) iShape;
                        layoutTextbox(wPAutoShape2, wPAutoShape2.getGroupShape());
                    }
                }
            }
        } else if (wPAutoShape.getElementIndex() >= 0) {
            WPSTRoot wPSTRoot = new WPSTRoot(getContainer(), getDocument(), wPAutoShape.getElementIndex());
            wPSTRoot.setWrapLine(wPAutoShape.isTextWrapLine());
            wPSTRoot.doLayout();
            wPSTRoot.setParentView(this);
            this.roots.put(Integer.valueOf(wPAutoShape.getElementIndex()), wPSTRoot);
            if (!wPAutoShape.isTextWrapLine()) {
                wPAutoShape.getBounds().width = wPSTRoot.getAdjustTextboxWidth();
            }
        }
    }

    public float getTextWidth() {
        if (this.isInline) {
            return (float) this.wpShape.getBounds().width;
        }
        return 0.0f;
    }

    public synchronized void draw(Canvas canvas, int i, int i2, float f) {
        WPSTRoot wPSTRoot;
        if (this.isInline) {
            int i3 = ((int) (((float) this.x) * f)) + i;
            int i4 = ((int) (((float) this.y) * f)) + i2;
            Rectangle bounds = this.wpShape.getBounds();
            this.rect.set(i3, i4, (int) (((float) i3) + (((float) bounds.width) * f)), (int) (((float) i4) + (((float) bounds.height) * f)));
            if (this.wpShape.getGroupShape() != null) {
                drawGroupShape(canvas, this.wpShape.getGroupShape(), this.rect, f);
            } else if (this.wpShape.getType() == 2) {
                AutoShapeKit.instance().drawAutoShape(canvas, getControl(), getPageNumber(), this.wpShape, this.rect, f);
            } else if (this.wpShape.getType() == 5) {
                AbstractChart aChart = ((WPChartShape) this.wpShape).getAChart();
                aChart.setZoomRate(f);
                aChart.draw(canvas, getControl(), this.rect.left, this.rect.top, this.rect.width(), this.rect.height(), PaintKit.instance().getPaint());
            }
            if (this.roots.size() > 0 && this.wpShape.getElementIndex() >= 0 && (wPSTRoot = this.roots.get(Integer.valueOf(this.wpShape.getElementIndex()))) != null) {
                canvas.save();
                canvas.rotate(this.wpShape.getRotation(), this.rect.exactCenterX(), this.rect.exactCenterY());
                wPSTRoot.draw(canvas, i3, i4, f);
                canvas.restore();
            }
        }
    }

    public synchronized void drawForWrap(Canvas canvas, int i, int i2, float f) {
        WPSTRoot wPSTRoot;
        try {
            int i3 = ((int) (((float) this.x) * f)) + i;
            int i4 = ((int) (((float) this.y) * f)) + i2;
            Rectangle bounds = this.wpShape.getBounds();
            if (this.wpShape.isWatermarkShape()) {
                String watermartString = ((WatermarkShape) this.wpShape).getWatermartString();
                if (watermartString != null && watermartString.length() > 0) {
                    canvas.save();
                    float textSize = this.paint.getTextSize();
                    this.paint.setTextSize(((float) ((WatermarkShape) this.wpShape).getFontSize()) * f);
                    float rotation = this.wpShape.getRotation();
                    canvas.translate(((float) i) + ((((float) this.pageAttr.leftMargin) + (((float) ((this.pageAttr.pageWidth - this.pageAttr.leftMargin) - this.pageAttr.rightMargin)) / 2.0f)) * f), ((float) i2) + ((((float) this.pageAttr.topMargin) + (((float) ((this.pageAttr.pageHeight - this.pageAttr.topMargin) - this.pageAttr.bottomMargin)) / 2.0f)) * f));
                    canvas.rotate(rotation, 0.0f, 0.0f);
                    canvas.drawText(watermartString, (((float) (-this.rect.width())) * f) / 2.0f, 0.0f, this.paint);
                    this.paint.setTextSize(textSize);
                    canvas.restore();
                    return;
                }
            } else {
                this.rect.set(i3, i4, (int) (((float) i3) + (((float) bounds.width) * f)), (int) (((float) i4) + (((float) bounds.height) * f)));
                if (this.wpShape.getGroupShape() != null) {
                    AutoShapeKit.instance().drawAutoShape(canvas, getControl(), getPageNumber(), this.wpShape, this.rect, f);
                    drawGroupShape(canvas, this.wpShape.getGroupShape(), this.rect, f);
                } else if (this.wpShape.getType() == 2) {
                    AutoShapeKit.instance().drawAutoShape(canvas, getControl(), getPageNumber(), this.wpShape, this.rect, f);
                } else if (this.wpShape.getType() == 5) {
                    AbstractChart aChart = ((WPChartShape) this.wpShape).getAChart();
                    aChart.setZoomRate(f);
                    aChart.draw(canvas, getControl(), this.rect.left, this.rect.top, this.rect.width(), this.rect.height(), PaintKit.instance().getPaint());
                }
            }
            if (this.roots.size() > 0 && this.wpShape.getElementIndex() >= 0 && (wPSTRoot = this.roots.get(Integer.valueOf(this.wpShape.getElementIndex()))) != null) {
                canvas.save();
                canvas.rotate(this.wpShape.getRotation(), this.rect.exactCenterX(), this.rect.exactCenterY());
                wPSTRoot.draw(canvas, i3, i4, f);
                canvas.restore();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void drawGroupShape(Canvas canvas, GroupShape groupShape, Rect rect2, float f) {
        IShape[] shapes;
        int i;
        int i2;
        Rect rect3;
        Rect rect4;
        WPSTRoot wPSTRoot;
        Canvas canvas2 = canvas;
        Rect rect5 = rect2;
        float f2 = f;
        if (groupShape != null && (shapes = groupShape.getShapes()) != null) {
            Rect rect6 = new Rect();
            int length = shapes.length;
            int i3 = 0;
            while (i3 < length) {
                IShape iShape = shapes[i3];
                if (iShape.getType() == 7) {
                    drawGroupShape(canvas2, (GroupShape) iShape, rect5, f2);
                } else {
                    if (iShape.getType() == 0) {
                        rect6.setEmpty();
                        Rectangle bounds = iShape.getBounds();
                        rect6.left = rect5.left + ((int) (((float) bounds.x) * f2));
                        rect6.top = rect5.top + ((int) (((float) bounds.y) * f2));
                        rect6.right = (int) (((float) rect6.left) + (((float) bounds.width) * f2));
                        rect6.bottom = (int) (((float) rect6.top) + (((float) bounds.height) * f2));
                        if (iShape instanceof WPPictureShape) {
                            iShape = ((WPPictureShape) iShape).getPictureShape();
                        }
                        if (iShape != null) {
                            PictureShape pictureShape = (PictureShape) iShape;
                            BackgroundDrawer.drawLineAndFill(canvas, getControl(), getPageNumber(), pictureShape, rect2, f);
                            PictureKit instance = PictureKit.instance();
                            IControl control = getControl();
                            int pageNumber = getPageNumber();
                            Picture picture = pictureShape.getPicture(getControl());
                            float f3 = (float) rect6.left;
                            float f4 = (float) rect6.top;
                            float f5 = ((float) iShape.getBounds().width) * f2;
                            Canvas canvas3 = canvas;
                            i = i3;
                            float f6 = f5;
                            i2 = length;
                            rect4 = rect6;
                            instance.drawPicture(canvas3, control, pageNumber, picture, f3, f4, f, f6, ((float) iShape.getBounds().height) * f2, pictureShape.getPictureEffectInfor());
                        }
                    } else {
                        i = i3;
                        i2 = length;
                        rect4 = rect6;
                        if (iShape.getType() == 2) {
                            rect4.setEmpty();
                            Rectangle bounds2 = iShape.getBounds();
                            rect3 = rect4;
                            rect3.left = rect5.left + ((int) (((float) bounds2.x) * f2));
                            rect3.top = rect5.top + ((int) (((float) bounds2.y) * f2));
                            rect3.right = (int) (((float) rect3.left) + (((float) bounds2.width) * f2));
                            rect3.bottom = (int) (((float) rect3.top) + (((float) bounds2.height) * f2));
                            AutoShapeKit.instance().drawAutoShape(canvas, getControl(), getPageNumber(), (AutoShape) iShape, rect3, f);
                            WPAutoShape wPAutoShape = (WPAutoShape) iShape;
                            if (wPAutoShape.getElementIndex() >= 0 && (wPSTRoot = this.roots.get(Integer.valueOf(wPAutoShape.getElementIndex()))) != null) {
                                wPSTRoot.draw(canvas2, rect3.left, rect3.top, f2);
                            }
                            rect6 = rect3;
                            length = i2;
                            i3 = i + 1;
                        }
                    }
                    rect3 = rect4;
                    rect6 = rect3;
                    length = i2;
                    i3 = i + 1;
                }
                i = i3;
                i2 = length;
                rect3 = rect6;
                rect6 = rect3;
                length = i2;
                i3 = i + 1;
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

    public int getBaseline() {
        if (this.isInline) {
            return (int) this.wpShape.getBounds().getHeight();
        }
        return 0;
    }

    public boolean isBehindDoc() {
        if (this.wpShape.getGroupShape() != null) {
            if (this.wpShape.getGroupShape().getWrapType() == 6) {
                return true;
            }
            return false;
        } else if (this.wpShape.getWrap() == 6) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInline() {
        return this.isInline;
    }

    public void dispose() {
        super.dispose();
        Map<Integer, WPSTRoot> map = this.roots;
        if (map != null) {
            for (Integer num : map.keySet()) {
                WPSTRoot wPSTRoot = this.roots.get(num);
                if (wPSTRoot != null) {
                    wPSTRoot.dispose();
                }
            }
            this.roots.clear();
            this.roots = null;
        }
        this.wpShape = null;
    }
}
