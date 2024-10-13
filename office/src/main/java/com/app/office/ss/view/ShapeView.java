package com.app.office.ss.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.PaintKit;
import com.app.office.common.autoshape.AutoShapeKit;
import com.app.office.common.picture.Picture;
import com.app.office.common.picture.PictureKit;
import com.app.office.common.shape.AChart;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.SmartArt;
import com.app.office.common.shape.TextBox;
import com.app.office.java.awt.Rectangle;
import com.app.office.simpletext.model.AttrManage;
import com.app.office.simpletext.model.IAttributeSet;
import com.app.office.simpletext.model.STDocument;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.simpletext.view.STRoot;
import com.app.office.system.IControl;

public class ShapeView {
    private Rect shapeRect = new Rect();
    private SheetView sheetView;
    private Rect temRect = new Rect();

    public ShapeView(SheetView sheetView2) {
        this.sheetView = sheetView2;
    }

    public void panzoomViewRect(Rectangle rectangle, IShape iShape) {
        float zoom = this.sheetView.getZoom();
        if (iShape == null || !(iShape instanceof SmartArt)) {
            int rowHeaderWidth = this.sheetView.getRowHeaderWidth();
            int columnHeaderHeight = this.sheetView.getColumnHeaderHeight();
            float scrollX = this.sheetView.getScrollX();
            float scrollY = this.sheetView.getScrollY();
            this.shapeRect.left = Math.round((((float) rectangle.x) - scrollX) * zoom) + rowHeaderWidth;
            this.shapeRect.right = rowHeaderWidth + Math.round((((float) (rectangle.x + rectangle.width)) - scrollX) * zoom);
            this.shapeRect.top = Math.round((((float) rectangle.y) - scrollY) * zoom) + columnHeaderHeight;
            this.shapeRect.bottom = columnHeaderHeight + Math.round((((float) (rectangle.y + rectangle.height)) - scrollY) * zoom);
        } else {
            this.shapeRect.left = Math.round(((float) rectangle.x) * zoom);
            this.shapeRect.right = Math.round(((float) (rectangle.x + rectangle.width)) * zoom);
            this.shapeRect.top = Math.round(((float) rectangle.y) * zoom);
            this.shapeRect.bottom = Math.round(((float) (rectangle.y + rectangle.height)) * zoom);
        }
        this.temRect.set(this.shapeRect.left, this.shapeRect.top, this.shapeRect.right, this.shapeRect.bottom);
    }

    public void draw(Canvas canvas) {
        Rect clipBounds = canvas.getClipBounds();
        clipBounds.left = this.sheetView.getRowHeaderWidth();
        clipBounds.top = this.sheetView.getColumnHeaderHeight();
        int shapeCount = this.sheetView.getCurrentSheet().getShapeCount();
        IControl control = this.sheetView.getSpreadsheet().getControl();
        for (int i = 0; i < shapeCount && !this.sheetView.getSpreadsheet().isAbortDrawing(); i++) {
            drawShape(canvas, clipBounds, control, (IShape) null, this.sheetView.getCurrentSheet().getShape(i));
        }
    }

    private void drawShape(Canvas canvas, Rect rect, IControl iControl, IShape iShape, IShape iShape2) {
        Canvas canvas2 = canvas;
        IShape iShape3 = iShape;
        IShape iShape4 = iShape2;
        canvas.save();
        Rectangle bounds = iShape2.getBounds();
        int i = 0;
        if (bounds == null && iShape2.getType() == 5) {
            DisplayMetrics displayMetrics = this.sheetView.getSpreadsheet().getControl().getMainFrame().getActivity().getResources().getDisplayMetrics();
            Rectangle rectangle = new Rectangle(0, 0, Math.round((float) Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels)), Math.round((float) Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels)));
            iShape4.setBounds(rectangle);
            bounds = rectangle;
        }
        panzoomViewRect(bounds, iShape3);
        if (this.temRect.intersect(rect) || iShape3 != null) {
            if (iShape4 instanceof GroupShape) {
                if (iShape2.getFlipVertical()) {
                    canvas2.translate((float) this.shapeRect.left, (float) this.shapeRect.bottom);
                    canvas2.scale(1.0f, -1.0f);
                    canvas2.translate((float) (-this.shapeRect.left), (float) (-this.shapeRect.top));
                }
                if (iShape2.getFlipHorizontal()) {
                    canvas2.translate((float) this.shapeRect.right, (float) this.shapeRect.top);
                    canvas2.scale(-1.0f, 1.0f);
                    canvas2.translate((float) (-this.shapeRect.left), (float) (-this.shapeRect.top));
                }
                IShape[] shapes = ((GroupShape) iShape4).getShapes();
                while (i < shapes.length) {
                    IShape iShape5 = shapes[i];
                    if (!iShape2.isHidden()) {
                        drawShape(canvas, rect, iControl, iShape2, iShape5);
                    }
                    i++;
                }
            } else {
                short type = iShape2.getType();
                if (type == 0) {
                    PictureShape pictureShape = (PictureShape) iShape4;
                    processRotation(canvas2, pictureShape, this.shapeRect);
                    BackgroundDrawer.drawLineAndFill(canvas, iControl, this.sheetView.getSheetIndex(), pictureShape, this.shapeRect, this.sheetView.getZoom());
                    Picture picture = iControl.getSysKit().getPictureManage().getPicture(pictureShape.getPictureIndex());
                    PictureKit.instance().drawPicture(canvas, this.sheetView.getSpreadsheet().getControl(), this.sheetView.getSheetIndex(), picture, (float) this.shapeRect.left, (float) this.shapeRect.top, this.sheetView.getZoom(), (float) this.shapeRect.width(), (float) this.shapeRect.height(), pictureShape.getPictureEffectInfor());
                } else if (type == 1) {
                    drawTextbox(canvas2, this.shapeRect, (TextBox) iShape4);
                } else if (type == 2 || type == 4) {
                    AutoShapeKit.instance().drawAutoShape(canvas, iControl, this.sheetView.getSheetIndex(), (AutoShape) iShape4, this.shapeRect, this.sheetView.getZoom());
                } else if (type == 5) {
                    AChart aChart = (AChart) iShape4;
                    if (aChart.getAChart() != null) {
                        processRotation(canvas2, iShape4, this.shapeRect);
                        aChart.getAChart().setZoomRate(this.sheetView.getZoom());
                        aChart.getAChart().draw(canvas, iControl, this.shapeRect.left, this.shapeRect.top, this.shapeRect.width(), this.shapeRect.height(), PaintKit.instance().getPaint());
                    }
                } else if (type == 8) {
                    SmartArt smartArt = (SmartArt) iShape4;
                    BackgroundDrawer.drawLineAndFill(canvas, iControl, this.sheetView.getSheetIndex(), smartArt, this.shapeRect, this.sheetView.getZoom());
                    canvas2.translate((float) this.shapeRect.left, (float) this.shapeRect.top);
                    IShape[] shapes2 = smartArt.getShapes();
                    int length = shapes2.length;
                    while (i < length) {
                        drawShape(canvas, rect, iControl, smartArt, shapes2[i]);
                        i++;
                    }
                }
            }
            canvas.restore();
        }
    }

    private void drawTextbox(Canvas canvas, Rect rect, TextBox textBox) {
        SectionElement element = textBox.getElement();
        if (element.getEndOffset() - element.getStartOffset() != 0 && !textBox.isEditor()) {
            processRotation(canvas, textBox, rect);
            STRoot rootView = textBox.getRootView();
            if (rootView == null) {
                STDocument sTDocument = new STDocument();
                sTDocument.appendSection(element);
                IAttributeSet attribute = element.getAttribute();
                AttrManage.instance().setPageWidth(attribute, (int) Math.round(textBox.getBounds().getWidth() * 15.0d));
                AttrManage.instance().setPageHeight(attribute, (int) Math.round(textBox.getBounds().getHeight() * 15.0d));
                STRoot sTRoot = new STRoot(this.sheetView.getSpreadsheet().getEditor(), sTDocument);
                sTRoot.setWrapLine(textBox.isWrapLine());
                sTRoot.doLayout();
                textBox.setRootView(sTRoot);
                rootView = sTRoot;
            }
            if (rootView != null) {
                rootView.draw(canvas, rect.left, rect.top, this.sheetView.getZoom());
            }
        }
    }

    private void processRotation(Canvas canvas, IShape iShape, Rect rect) {
        float rotation = iShape.getRotation();
        if (iShape.getFlipVertical()) {
            rotation += 180.0f;
        }
        if (rotation != 0.0f) {
            canvas.rotate(rotation, (float) rect.centerX(), (float) rect.centerY());
        }
    }

    public void dispose() {
        this.sheetView = null;
        this.shapeRect = null;
        this.temRect = null;
    }
}
