package com.app.office.pg.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.app.office.common.BackgroundDrawer;
import com.app.office.common.PaintKit;
import com.app.office.common.autoshape.AutoShapeKit;
import com.app.office.common.borders.Line;
import com.app.office.common.picture.PictureKit;
import com.app.office.common.shape.AChart;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.GroupShape;
import com.app.office.common.shape.IShape;
import com.app.office.common.shape.PictureShape;
import com.app.office.common.shape.SmartArt;
import com.app.office.common.shape.TableCell;
import com.app.office.common.shape.TableShape;
import com.app.office.common.shape.TextBox;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Rectanglef;
import com.app.office.pg.animate.IAnimation;
import com.app.office.pg.animate.ShapeAnimation;
import com.app.office.pg.control.PGEditor;
import com.app.office.pg.control.Presentation;
import com.app.office.pg.model.PGModel;
import com.app.office.pg.model.PGSlide;
import com.app.office.simpletext.model.IDocument;
import com.app.office.simpletext.model.LeafElement;
import com.app.office.simpletext.model.ParagraphElement;
import com.app.office.simpletext.model.SectionElement;
import com.app.office.simpletext.view.STRoot;
import java.util.Map;

public class SlideDrawKit {
    private static SlideDrawKit kit;
    private Rect brRect = new Rect();

    public static SlideDrawKit instance() {
        if (kit == null) {
            kit = new SlideDrawKit();
        }
        return kit;
    }

    public void drawSlide(Canvas canvas, PGModel pGModel, PGEditor pGEditor, PGSlide pGSlide, float f) {
        drawSlide(canvas, pGModel, pGEditor, pGSlide, f, (Map<Integer, Map<Integer, IAnimation>>) null);
    }

    public void drawSlide(Canvas canvas, PGModel pGModel, PGEditor pGEditor, PGSlide pGSlide, float f, Map<Integer, Map<Integer, IAnimation>> map) {
        synchronized (this) {
            Dimension pageSize = pGModel.getPageSize();
            this.brRect.set(0, 0, (int) (((float) pageSize.width) * f), (int) (((float) pageSize.height) * f));
            if (!BackgroundDrawer.drawBackground(canvas, pGEditor.getControl(), pGSlide.getSlideNo(), pGSlide.getBackgroundAndFill(), this.brRect, (IAnimation) null, f)) {
                Canvas canvas2 = canvas;
                canvas.drawColor(Color.white.getRGB());
            } else {
                Canvas canvas3 = canvas;
            }
            int[] masterIndexs = pGSlide.getMasterIndexs();
            for (int slideMaster : masterIndexs) {
                PGModel pGModel2 = pGModel;
                drawShapes(canvas, pGModel, pGEditor, pGModel.getSlideMaster(slideMaster), pGSlide.getSlideNo(), f, map);
            }
            PGModel pGModel3 = pGModel;
            drawShapes(canvas, pGModel, pGEditor, pGSlide, pGSlide.getSlideNo(), f, map);
        }
    }

    private void drawShapes(Canvas canvas, PGModel pGModel, PGEditor pGEditor, PGSlide pGSlide, int i, float f, Map<Integer, Map<Integer, IAnimation>> map) {
        PGSlide pGSlide2 = pGSlide;
        if (pGSlide2 != null) {
            int shapeCount = pGSlide.getShapeCount();
            for (int i2 = 0; i2 < shapeCount; i2++) {
                IShape shape = pGSlide2.getShape(i2);
                if (!shape.isHidden()) {
                    int placeHolderID = shape.getPlaceHolderID();
                    boolean z = true;
                    if (!(pGSlide.getSlideType() == 2 || placeHolderID == 0 || placeHolderID == 19 || placeHolderID == 20 || placeHolderID == 21 || placeHolderID == 22 || placeHolderID == 23 || placeHolderID == 24)) {
                        z = false;
                    }
                    if (z) {
                        drawShape(canvas, pGModel, pGEditor, i, shape, f, map);
                    }
                }
            }
        }
    }

    private Rect getShapeRect(IShape iShape, float f) {
        Rectangle bounds = iShape.getBounds();
        int round = Math.round(((float) bounds.x) * f);
        int round2 = Math.round(((float) bounds.y) * f);
        return new Rect(round, round2, Math.round(((float) bounds.width) * f) + round, Math.round(((float) bounds.height) * f) + round2);
    }

    private void drawShape(Canvas canvas, PGModel pGModel, PGEditor pGEditor, int i, IShape iShape, float f, Map<Integer, Map<Integer, IAnimation>> map) {
        Canvas canvas2 = canvas;
        IShape iShape2 = iShape;
        float f2 = f;
        canvas.save();
        if (iShape2 instanceof GroupShape) {
            Rect shapeRect = getShapeRect(iShape2, f2);
            if (iShape.getFlipVertical()) {
                canvas.translate((float) shapeRect.left, (float) shapeRect.bottom);
                canvas.scale(1.0f, -1.0f);
                canvas.translate((float) (-shapeRect.left), (float) (-shapeRect.top));
            }
            if (iShape.getFlipHorizontal()) {
                canvas.translate((float) shapeRect.right, (float) shapeRect.top);
                canvas.scale(-1.0f, 1.0f);
                canvas.translate((float) (-shapeRect.left), (float) (-shapeRect.top));
            }
            if (iShape.getRotation() != 0.0f) {
                canvas.rotate(iShape.getRotation(), shapeRect.exactCenterX(), shapeRect.exactCenterY());
            }
            IShape[] shapes = ((GroupShape) iShape2).getShapes();
            for (IShape iShape3 : shapes) {
                if (!iShape.isHidden()) {
                    drawShape(canvas, pGModel, pGEditor, i, iShape3, f, map);
                }
            }
        } else if (iShape.getType() == 8) {
            Rect shapeRect2 = getShapeRect(iShape2, f2);
            SmartArt smartArt = (SmartArt) iShape2;
            BackgroundDrawer.drawLineAndFill(canvas, pGEditor.getControl(), i, smartArt, shapeRect2, f);
            canvas.translate((float) shapeRect2.left, (float) shapeRect2.top);
            for (IShape drawShape : smartArt.getShapes()) {
                drawShape(canvas, pGModel, pGEditor, i, drawShape, f, map);
            }
        } else if (iShape.getType() == 1) {
            drawTextShape(canvas, pGModel, pGEditor, i, (TextBox) iShape2, f, map);
        } else if (iShape.getType() == 4 || iShape.getType() == 2) {
            PGEditor pGEditor2 = pGEditor;
            AutoShapeKit.instance().drawAutoShape(canvas, pGEditor.getControl(), i, (AutoShape) iShape2, f);
        } else if (iShape.getType() == 0) {
            drawPicture(canvas, pGEditor, i, (PictureShape) iShape2, f);
        } else if (iShape.getType() == 5) {
            drawChart(canvas, pGEditor, (AChart) iShape2, f2);
        } else {
            PGEditor pGEditor3 = pGEditor;
            if (iShape.getType() == 6) {
                drawTable(canvas, pGModel, pGEditor, i, (TableShape) iShape2, f, map);
            }
        }
        canvas.restore();
    }

    private void drawTextShape(Canvas canvas, PGModel pGModel, PGEditor pGEditor, int i, TextBox textBox, float f, Map<Integer, Map<Integer, IAnimation>> map) {
        Canvas canvas2 = canvas;
        PGEditor pGEditor2 = pGEditor;
        TextBox textBox2 = textBox;
        float f2 = f;
        Map<Integer, Map<Integer, IAnimation>> map2 = map;
        Rectangle bounds = textBox.getBounds();
        SectionElement element = textBox.getElement();
        if (element != null && element.getEndOffset() - element.getStartOffset() != 0) {
            canvas.save();
            STRoot rootView = textBox.getRootView();
            Presentation pGView = pGEditor.getPGView();
            if (pGView != null && rootView == null && (textBox.getMCType() == 1 || textBox.getPlaceHolderID() == 8)) {
                pGModel.getRenderersDoc().appendSection(element);
                String text = element.getText((IDocument) null);
                if (text != null && text.contains("*")) {
                    String replace = text.replace("*", String.valueOf(i + pGView.getPGModel().getSlideNumberOffset()));
                    SectionElement sectionElement = new SectionElement();
                    sectionElement.setStartOffset(0);
                    sectionElement.setEndOffset((long) replace.length());
                    sectionElement.setAttribute(textBox.getElement().getAttribute().clone());
                    ParagraphElement paragraphElement = (ParagraphElement) textBox.getElement().getParaCollection().getElementForIndex(0);
                    ParagraphElement paragraphElement2 = new ParagraphElement();
                    paragraphElement2.setStartOffset(0);
                    paragraphElement2.setEndOffset((long) replace.length());
                    paragraphElement2.setAttribute(paragraphElement.getAttribute().clone());
                    sectionElement.appendParagraph(paragraphElement2, 0);
                    LeafElement leafElement = new LeafElement(replace);
                    leafElement.setStartOffset(0);
                    leafElement.setEndOffset((long) replace.length());
                    leafElement.setAttribute(((LeafElement) paragraphElement.getElementForIndex(0)).getAttribute().clone());
                    paragraphElement2.appendLeaf(leafElement);
                    textBox2.setElement(sectionElement);
                    element = sectionElement;
                }
            }
            if (rootView == null) {
                IDocument renderersDoc = pGModel.getRenderersDoc();
                renderersDoc.appendSection(element);
                STRoot sTRoot = new STRoot(pGEditor2, renderersDoc);
                sTRoot.setWrapLine(textBox.isWrapLine());
                sTRoot.doLayout();
                textBox2.setRootView(sTRoot);
                rootView = sTRoot;
            }
            if (map2 != null) {
                if (textBox.getGroupShapeID() >= 0) {
                    pGEditor2.setShapeAnimation(map2.get(Integer.valueOf(textBox.getGroupShapeID())));
                } else {
                    pGEditor2.setShapeAnimation(map2.get(Integer.valueOf(textBox.getShapeID())));
                }
                rootView.draw(canvas2, (int) (((float) bounds.x) * f2), (int) (((float) bounds.y) * f2), f2);
            } else {
                pGEditor.getHighlight().setPaintHighlight(textBox2 == pGEditor.getEditorTextBox());
                rootView.draw(canvas2, (int) (((float) bounds.x) * f2), (int) (((float) bounds.y) * f2), f2);
                pGEditor.getHighlight().setPaintHighlight(false);
            }
            canvas.restore();
        }
    }

    private void drawPicture(Canvas canvas, PGEditor pGEditor, int i, PictureShape pictureShape, float f) {
        PictureShape pictureShape2 = pictureShape;
        float f2 = f;
        canvas.save();
        processRotation(canvas, pictureShape2, f2);
        Rectangle bounds = pictureShape.getBounds();
        Canvas canvas2 = canvas;
        BackgroundDrawer.drawLineAndFill(canvas2, pGEditor.getControl(), i, pictureShape, getShapeRect(pictureShape2, f2), f);
        PictureKit.instance().drawPicture(canvas, pGEditor.getControl(), i, pictureShape2.getPicture(pGEditor.getControl()), ((float) bounds.x) * f2, ((float) bounds.y) * f2, f2, ((float) bounds.width) * f2, ((float) bounds.height) * f2, pictureShape.getPictureEffectInfor(), pictureShape.getAnimation());
        canvas.restore();
    }

    private void drawChart(Canvas canvas, PGEditor pGEditor, AChart aChart, float f) {
        IAnimation animation = aChart.getAnimation();
        if (animation == null || animation.getCurrentAnimationInfor().getAlpha() != 0) {
            canvas.save();
            Rectangle bounds = aChart.getBounds();
            Paint paint = PaintKit.instance().getPaint();
            if (animation != null) {
                ShapeAnimation shapeAnimation = animation.getShapeAnimation();
                int paragraphBegin = shapeAnimation.getParagraphBegin();
                int paragraphEnd = shapeAnimation.getParagraphEnd();
                if ((paragraphBegin == -2 && paragraphEnd == -2) || (paragraphBegin == -1 && paragraphEnd == -1)) {
                    int alpha = animation.getCurrentAnimationInfor().getAlpha();
                    paint.setAlpha(alpha);
                    float f2 = (((float) alpha) / 255.0f) * 0.5f;
                    double centerX = bounds.getCenterX();
                    double centerY = bounds.getCenterY();
                    Rectangle rectangle = new Rectangle(bounds);
                    rectangle.x = Math.round((float) (centerX - ((double) (((float) rectangle.width) * f2))));
                    rectangle.y = Math.round((float) (centerY - ((double) (((float) rectangle.height) * f2))));
                    float f3 = f2 * 2.0f;
                    rectangle.width = (int) (((float) rectangle.width) * f3);
                    rectangle.height = (int) (((float) rectangle.height) * f3);
                    float f4 = f2 * f * 2.0f;
                    processRotation(canvas, aChart, f4);
                    aChart.getAChart().setZoomRate(f4);
                    Canvas canvas2 = canvas;
                    aChart.getAChart().draw(canvas2, pGEditor.getControl(), (int) (((float) rectangle.x) * f), (int) (((float) rectangle.y) * f), (int) (((float) rectangle.width) * f), (int) (((float) rectangle.height) * f), paint);
                    return;
                }
            }
            processRotation(canvas, aChart, f);
            aChart.getAChart().setZoomRate(f);
            aChart.getAChart().draw(canvas, pGEditor.getControl(), (int) (((float) bounds.x) * f), (int) (((float) bounds.y) * f), (int) (((float) bounds.width) * f), (int) (((float) bounds.height) * f), paint);
            canvas.restore();
        }
    }

    private void drawTable(Canvas canvas, PGModel pGModel, PGEditor pGEditor, int i, TableShape tableShape, float f, Map<Integer, Map<Integer, IAnimation>> map) {
        int i2;
        Rectangle bounds;
        Canvas canvas2 = canvas;
        TableShape tableShape2 = tableShape;
        float f2 = f;
        canvas.save();
        processRotation(canvas2, tableShape2, f2);
        if (tableShape.getAnimation() != null) {
            int alpha = tableShape.getAnimation().getCurrentAnimationInfor().getAlpha();
            if (!(alpha == 255 || (bounds = tableShape.getBounds()) == null)) {
                float f3 = ((float) (bounds.height + bounds.y + 1)) * f2;
                canvas.saveLayerAlpha(((float) bounds.x) * f2, ((float) bounds.y) * f2, ((float) (bounds.x + bounds.width + 1)) * f2, f3, alpha);
            }
            i2 = alpha;
        } else {
            i2 = 255;
        }
        int cellCount = tableShape.getCellCount();
        for (int i3 = 0; i3 < cellCount; i3++) {
            TableCell cell = tableShape2.getCell(i3);
            if (cell != null) {
                Rectanglef bounds2 = cell.getBounds();
                this.brRect.set(Math.round(bounds2.getX() * f2), Math.round(bounds2.getY() * f2), Math.round((bounds2.getX() + bounds2.getWidth()) * f2), Math.round((bounds2.getY() + bounds2.getHeight()) * f2));
                BackgroundDrawer.drawBackground(canvas, pGEditor.getControl(), i, cell.getBackgroundAndFill(), this.brRect, (IAnimation) null, f);
                drawCellBorder(canvas2, cell, bounds2, f2);
                if (cell.getText() != null) {
                    drawTextShape(canvas, pGModel, pGEditor, i, cell.getText(), f, map);
                }
            }
        }
        if (i2 != 255) {
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawCellBorder(Canvas canvas, TableCell tableCell, Rectanglef rectanglef, float f) {
        drawCellBorder(canvas, tableCell, rectanglef, f, (IAnimation) null);
    }

    private void drawCellBorder(Canvas canvas, TableCell tableCell, Rectanglef rectanglef, float f, IAnimation iAnimation) {
        Paint paint = PaintKit.instance().getPaint();
        int color = paint.getColor();
        canvas.save();
        float max = Math.max(1.0f, f);
        Line leftLine = tableCell.getLeftLine();
        if (leftLine != null) {
            paint.setColor(leftLine.getBackgroundAndFill().getForegroundColor());
            paint.setStrokeWidth(((float) leftLine.getLineWidth()) * f);
            if (iAnimation != null) {
                paint.setAlpha(iAnimation.getCurrentAnimationInfor().getAlpha());
            }
            canvas.drawRect(rectanglef.getX() * f, rectanglef.getY() * f, (rectanglef.getX() * f) + max, (rectanglef.getY() + rectanglef.getHeight()) * f, paint);
        }
        Line topLine = tableCell.getTopLine();
        if (topLine != null) {
            paint.setColor(topLine.getBackgroundAndFill().getForegroundColor());
            paint.setStrokeWidth(((float) topLine.getLineWidth()) * f);
            if (iAnimation != null) {
                paint.setAlpha(iAnimation.getCurrentAnimationInfor().getAlpha());
            }
            canvas.drawRect(rectanglef.getX() * f, rectanglef.getY() * f, (rectanglef.getX() + rectanglef.getWidth()) * f, (rectanglef.getY() * f) + max, paint);
        }
        Line rightLine = tableCell.getRightLine();
        if (rightLine != null) {
            paint.setColor(rightLine.getBackgroundAndFill().getForegroundColor());
            paint.setStrokeWidth(((float) rightLine.getLineWidth()) * f);
            if (iAnimation != null) {
                paint.setAlpha(iAnimation.getCurrentAnimationInfor().getAlpha());
            }
            canvas.drawRect((rectanglef.getX() + rectanglef.getWidth()) * f, rectanglef.getY() * f, ((rectanglef.getX() + rectanglef.getWidth()) * f) + max, (rectanglef.getY() + rectanglef.getHeight()) * f, paint);
        }
        Line bottomLine = tableCell.getBottomLine();
        if (bottomLine != null) {
            paint.setColor(bottomLine.getBackgroundAndFill().getForegroundColor());
            paint.setStrokeWidth(((float) bottomLine.getLineWidth()) * f);
            if (iAnimation != null) {
                paint.setAlpha(iAnimation.getCurrentAnimationInfor().getAlpha());
            }
            canvas.drawRect(rectanglef.getX() * f, (rectanglef.getY() + rectanglef.getHeight()) * f, (rectanglef.getX() + rectanglef.getWidth()) * f, ((rectanglef.getY() + rectanglef.getHeight()) * f) + max, paint);
        }
        paint.setColor(color);
        canvas.restore();
    }

    private void processRotation(Canvas canvas, IShape iShape, float f) {
        Rectangle bounds = iShape.getBounds();
        float rotation = iShape.getRotation();
        if (iShape.getFlipVertical()) {
            rotation += 180.0f;
        }
        IAnimation animation = iShape.getAnimation();
        if (animation != null && animation.getShapeAnimation().getAnimationType() == 1) {
            rotation += (float) animation.getCurrentAnimationInfor().getAngle();
        }
        if (rotation != 0.0f) {
            canvas.rotate(rotation, (((float) bounds.x) + (((float) bounds.width) / 2.0f)) * f, (((float) bounds.y) + (((float) bounds.height) / 2.0f)) * f);
        }
    }

    public Bitmap slideToImage(PGModel pGModel, PGEditor pGEditor, PGSlide pGSlide) {
        return slideToImage(pGModel, pGEditor, pGSlide, (Map<Integer, Map<Integer, IAnimation>>) null);
    }

    public Bitmap slideToImage(PGModel pGModel, PGEditor pGEditor, PGSlide pGSlide, Map<Integer, Map<Integer, IAnimation>> map) {
        synchronized (this) {
            if (pGSlide == null) {
                return null;
            }
            boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
            PictureKit.instance().setDrawPictrue(true);
            Dimension pageSize = pGModel.getPageSize();
            Bitmap createBitmap = Bitmap.createBitmap(pageSize.width, pageSize.height, Bitmap.Config.ARGB_8888);
            this.brRect.set(0, 0, pageSize.width, pageSize.height);
            Canvas canvas = new Canvas(createBitmap);
            if (!BackgroundDrawer.drawBackground(canvas, pGEditor.getControl(), pGSlide.getSlideNo(), pGSlide.getBackgroundAndFill(), this.brRect, (IAnimation) null, 1.0f)) {
                canvas.drawColor(Color.white.getRGB());
            }
            int[] masterIndexs = pGSlide.getMasterIndexs();
            int i = 0;
            while (i < masterIndexs.length) {
                Canvas canvas2 = canvas;
                PGModel pGModel2 = pGModel;
                PGEditor pGEditor2 = pGEditor;
                drawShapes(canvas2, pGModel2, pGEditor2, pGModel.getSlideMaster(masterIndexs[i]), pGSlide.getSlideNo(), 1.0f, (Map<Integer, Map<Integer, IAnimation>>) null);
                i++;
                canvas = canvas;
            }
            PGModel pGModel3 = pGModel;
            drawShapes(canvas, pGModel, pGEditor, pGSlide, pGSlide.getSlideNo(), 1.0f, map);
            PictureKit.instance().setDrawPictrue(isDrawPictrue);
            return createBitmap;
        }
    }

    public Bitmap slideAreaToImage(PGModel pGModel, PGEditor pGEditor, PGSlide pGSlide, int i, int i2, int i3, int i4, int i5, int i6) {
        synchronized (this) {
            if (pGSlide == null) {
                return null;
            }
            boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
            PictureKit.instance().setDrawPictrue(true);
            float f = (float) i3;
            float f2 = (float) i4;
            float min = Math.min(((float) i5) / f, ((float) i6) / f2);
            try {
                Bitmap createBitmap = Bitmap.createBitmap((int) (f * min), (int) (f2 * min), Bitmap.Config.ARGB_8888);
                if (createBitmap == null) {
                    return null;
                }
                Dimension pageSize = pGModel.getPageSize();
                Canvas canvas = new Canvas(createBitmap);
                double d = (double) min;
                this.brRect.set(0, 0, (int) (pageSize.getWidth() * d), (int) (pageSize.getHeight() * d));
                canvas.translate(((float) (-i)) * min, ((float) (-i2)) * min);
                canvas.drawColor(Color.white.getRGB());
                BackgroundDrawer.drawBackground(canvas, pGEditor.getControl(), pGSlide.getSlideNo(), pGSlide.getBackgroundAndFill(), this.brRect, (IAnimation) null, 1.0f);
                int[] masterIndexs = pGSlide.getMasterIndexs();
                for (int slideMaster : masterIndexs) {
                    drawShapes(canvas, pGModel, pGEditor, pGModel.getSlideMaster(slideMaster), pGSlide.getSlideNo(), min, (Map<Integer, Map<Integer, IAnimation>>) null);
                }
                PGModel pGModel2 = pGModel;
                drawShapes(canvas, pGModel, pGEditor, pGSlide, pGSlide.getSlideNo(), min, (Map<Integer, Map<Integer, IAnimation>>) null);
                PictureKit.instance().setDrawPictrue(isDrawPictrue);
                return createBitmap;
            } catch (OutOfMemoryError unused) {
                return null;
            }
        }
    }

    public Bitmap getThumbnail(PGModel pGModel, PGEditor pGEditor, PGSlide pGSlide, float f) {
        synchronized (this) {
            if (pGSlide == null) {
                return null;
            }
            boolean isDrawPictrue = PictureKit.instance().isDrawPictrue();
            PictureKit.instance().setDrawPictrue(true);
            Dimension pageSize = pGModel.getPageSize();
            int i = (int) (((float) pageSize.width) * f);
            int i2 = (int) (((float) pageSize.height) * f);
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            this.brRect.set(0, 0, i, i2);
            canvas.drawColor(Color.white.getRGB());
            BackgroundDrawer.drawBackground(canvas, pGEditor.getControl(), pGSlide.getSlideNo(), pGSlide.getBackgroundAndFill(), this.brRect, (IAnimation) null, 1.0f);
            int[] masterIndexs = pGSlide.getMasterIndexs();
            int i3 = 0;
            while (i3 < masterIndexs.length) {
                Canvas canvas2 = canvas;
                PGModel pGModel2 = pGModel;
                PGEditor pGEditor2 = pGEditor;
                drawShapes(canvas2, pGModel2, pGEditor2, pGModel.getSlideMaster(masterIndexs[i3]), pGSlide.getSlideNo(), f, (Map<Integer, Map<Integer, IAnimation>>) null);
                i3++;
                canvas = canvas;
            }
            PGModel pGModel3 = pGModel;
            drawShapes(canvas, pGModel, pGEditor, pGSlide, pGSlide.getSlideNo(), f, (Map<Integer, Map<Integer, IAnimation>>) null);
            PictureKit.instance().setDrawPictrue(isDrawPictrue);
            return createBitmap;
        }
    }

    public void disposeOldSlideView(PGModel pGModel, PGSlide pGSlide) {
        TextBox text;
        STRoot rootView;
        if (pGSlide != null) {
            int shapeCount = pGSlide.getShapeCount();
            for (int i = 0; i < shapeCount; i++) {
                IShape shape = pGSlide.getShape(i);
                if (shape.getType() == 1) {
                    TextBox textBox = (TextBox) shape;
                    STRoot rootView2 = textBox.getRootView();
                    if (rootView2 != null) {
                        rootView2.dispose();
                        textBox.setRootView((STRoot) null);
                    }
                } else if (shape.getType() == 6) {
                    TableShape tableShape = (TableShape) shape;
                    int cellCount = tableShape.getCellCount();
                    for (int i2 = 0; i2 < cellCount; i2++) {
                        TableCell cell = tableShape.getCell(i2);
                        if (!(cell == null || (text = cell.getText()) == null || (rootView = text.getRootView()) == null)) {
                            rootView.dispose();
                            text.setRootView((STRoot) null);
                        }
                    }
                }
            }
        }
    }
}
