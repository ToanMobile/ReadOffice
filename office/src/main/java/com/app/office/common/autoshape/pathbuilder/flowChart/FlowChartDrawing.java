package com.app.office.common.autoshape.pathbuilder.flowChart;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.autoshape.AutoShapeKit;
import com.app.office.common.shape.AutoShape;
import com.app.office.system.IControl;

public class FlowChartDrawing {
    private static Rect flowRect = new Rect();
    private static final FlowChartDrawing kit = new FlowChartDrawing();
    private static Path path = new Path();
    private static RectF rectF = new RectF();

    public static FlowChartDrawing instance() {
        return kit;
    }

    public void drawFlowChart(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        int shapeType = autoShape.getShapeType();
        if (shapeType == 176) {
            drawFlowChartAlternateProcess(canvas, iControl, i, autoShape, rect, f);
        } else if (shapeType != 177) {
            switch (shapeType) {
                case 109:
                    drawFlowChartProcess(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 110:
                    drawFlowChartDecision(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 111:
                    drawFlowChartInputOutput(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 112:
                    drawFlowChartPredefinedProcess(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 113:
                    drawFlowChartInternalStorage(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 114:
                    drawFlowChartDocument(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 115:
                    drawFlowChartMultidocument(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 116:
                    drawFlowChartTerminator(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 117:
                    drawFlowChartPreparation(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 118:
                    drawFlowChartManualInput(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 119:
                    drawFlowChartManualOperation(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 120:
                    drawFlowChartConnector(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 121:
                    drawFlowChartPunchedCard(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 122:
                    drawFlowChartPunchedTape(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 123:
                    drawFlowChartSummingJunction(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 124:
                    drawFlowChartOr(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 125:
                    drawFlowChartCollate(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 126:
                    drawFlowChartSort(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 127:
                    drawFlowChartExtract(canvas, iControl, i, autoShape, rect, f);
                    return;
                case 128:
                    drawFlowChartMerge(canvas, iControl, i, autoShape, rect, f);
                    return;
                default:
                    switch (shapeType) {
                        case 130:
                            drawFlowChartOnlineStorage(canvas, iControl, i, autoShape, rect, f);
                            return;
                        case 131:
                            drawFlowChartMagneticTape(canvas, iControl, i, autoShape, rect, f);
                            return;
                        case 132:
                            drawFlowChartMagneticDisk(canvas, iControl, i, autoShape, rect, f);
                            return;
                        case 133:
                            drawFlowChartMagneticDrum(canvas, iControl, i, autoShape, rect, f);
                            return;
                        case 134:
                            drawFlowChartDisplay(canvas, iControl, i, autoShape, rect, f);
                            return;
                        case 135:
                            drawFlowChartDelay(canvas, iControl, i, autoShape, rect, f);
                            return;
                        default:
                            return;
                    }
            }
        } else {
            drawFlowChartOffpageConnector(canvas, iControl, i, autoShape, rect, f);
        }
    }

    private void drawFlowChartProcess(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        Rect rect2 = rect;
        path.reset();
        path.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect2, f);
    }

    private void drawFlowChartAlternateProcess(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        path.reset();
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addRoundRect(rectF, new float[]{min, min, min, min, min, min, min, min}, Path.Direction.CW);
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartDecision(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo(rect.exactCenterX(), (float) rect.top);
        path.lineTo((float) rect.right, rect.exactCenterY());
        path.lineTo(rect.exactCenterX(), (float) rect.bottom);
        path.lineTo((float) rect.left, rect.exactCenterY());
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartInputOutput(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float width = ((float) rect.width()) * 0.2f;
        path.reset();
        path.moveTo(((float) rect.left) + width, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo(((float) rect.right) - width, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartPredefinedProcess(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        Rect rect2 = rect;
        float width = ((float) rect.width()) * 0.125f;
        path.reset();
        path.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        path.moveTo(((float) rect2.left) + width, (float) rect2.top);
        path.lineTo(((float) rect2.left) + width, (float) rect2.bottom);
        path.moveTo(((float) rect2.right) - width, (float) rect2.top);
        path.lineTo(((float) rect2.right) - width, (float) rect2.bottom);
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect2, f);
    }

    private void drawFlowChartInternalStorage(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        Rect rect2 = rect;
        float width = ((float) rect.width()) * 0.125f;
        float height = ((float) rect.height()) * 0.125f;
        path.reset();
        path.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        path.moveTo(((float) rect2.left) + width, (float) rect2.top);
        path.lineTo(((float) rect2.left) + width, (float) rect2.bottom);
        path.moveTo((float) rect2.left, ((float) rect2.top) + height);
        path.lineTo((float) rect2.right, ((float) rect2.top) + height);
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect2, f);
    }

    private void drawFlowChartDocument(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float height = ((float) rect.height()) * 0.2f;
        path.reset();
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        float height2 = ((float) rect.height()) * 0.07f * 2.0f;
        rectF.set(rect.exactCenterX(), ((float) rect.bottom) - height, ((float) rect.right) + (((float) rect.width()) / 2.0f), (((float) rect.bottom) + height) - height2);
        path.arcTo(rectF, 270.0f, -90.0f);
        rectF.set((float) rect.left, ((float) rect.bottom) - height2, rect.exactCenterX(), (float) rect.bottom);
        path.arcTo(rectF, 0.0f, 180.0f);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartMultidocument(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        Rect rect2 = rect;
        int width = (int) (((double) rect.width()) * 0.137d);
        int height = (int) (((double) rect.height()) * 0.167d);
        flowRect.set(rect2.left + width, rect2.top, rect2.right, rect2.bottom - height);
        drawFlowChartDocument(canvas, iControl, i, autoShape, flowRect, f);
        int i2 = width / 2;
        int i3 = height / 2;
        flowRect.set(rect2.left + i2, rect2.top + i3, rect2.right - i2, rect2.bottom - i3);
        drawFlowChartDocument(canvas, iControl, i, autoShape, flowRect, f);
        flowRect.set(rect2.left, rect2.top + height, rect2.right - width, rect2.bottom);
        drawFlowChartDocument(canvas, iControl, i, autoShape, flowRect, f);
    }

    private void drawFlowChartTerminator(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float width = ((float) rect.width()) * 0.16f;
        float height = ((float) rect.height()) * 0.5f;
        path.reset();
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addRoundRect(rectF, new float[]{width, height, width, height, width, height, width, height}, Path.Direction.CW);
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartPreparation(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float width = ((float) rect.width()) * 0.2f;
        path.reset();
        path.moveTo(((float) rect.left) + width, (float) rect.top);
        path.lineTo(((float) rect.right) - width, (float) rect.top);
        path.lineTo((float) rect.right, rect.exactCenterY());
        path.lineTo(((float) rect.right) - width, (float) rect.bottom);
        path.lineTo(((float) rect.left) + width, (float) rect.bottom);
        path.lineTo((float) rect.left, rect.exactCenterY());
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartManualInput(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo((float) rect.left, ((float) rect.top) + (((float) rect.height()) * 0.2f));
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartManualOperation(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float width = ((float) rect.width()) * 0.2f;
        path.reset();
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo(((float) rect.right) - width, (float) rect.bottom);
        path.lineTo(((float) rect.left) + width, (float) rect.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartConnector(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartOffpageConnector(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float height = ((float) rect.height()) * 0.2f;
        path.reset();
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.bottom) - height);
        path.lineTo(rect.exactCenterX(), (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.bottom) - height);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartPunchedCard(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo(((float) rect.left) + (((float) rect.width()) * 0.2f), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.top) + (((float) rect.height()) * 0.2f));
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartPunchedTape(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        float height = ((float) rect.height()) * 0.1f * 2.0f;
        rectF.set((float) rect.left, (float) rect.top, rect.exactCenterX(), ((float) rect.top) + height);
        path.arcTo(rectF, 180.0f, -180.0f);
        rectF.set(rect.exactCenterX(), (float) rect.top, (float) rect.right, ((float) rect.top) + height);
        path.arcTo(rectF, 180.0f, 180.0f);
        rectF.set(rect.exactCenterX(), ((float) rect.bottom) - height, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, 0.0f, -180.0f);
        rectF.set((float) rect.left, ((float) rect.bottom) - height, rect.exactCenterX(), (float) rect.bottom);
        path.arcTo(rectF, 0.0f, 180.0f);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartSummingJunction(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        Rect rect2 = rect;
        float sqrt = (((float) Math.sqrt(2.0d)) * ((float) rect.width())) / 4.0f;
        float sqrt2 = (((float) Math.sqrt(2.0d)) * ((float) rect.height())) / 4.0f;
        float exactCenterX = rect.exactCenterX();
        float exactCenterY = rect.exactCenterY();
        path.reset();
        rectF.set((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom);
        path.addOval(rectF, Path.Direction.CW);
        float f2 = exactCenterX - sqrt;
        float f3 = exactCenterY - sqrt2;
        path.moveTo(f2, f3);
        float f4 = exactCenterX + sqrt;
        float f5 = exactCenterY + sqrt2;
        path.lineTo(f4, f5);
        path.moveTo(f4, f3);
        path.lineTo(f2, f5);
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect2, f);
    }

    private void drawFlowChartOr(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        path.moveTo(rect.exactCenterX(), (float) rect.top);
        path.lineTo(rect.exactCenterX(), (float) rect.bottom);
        path.moveTo((float) rect.left, rect.exactCenterY());
        path.lineTo((float) rect.right, rect.exactCenterY());
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartCollate(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo(rect.exactCenterX(), rect.exactCenterY());
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.lineTo(rect.exactCenterX(), rect.exactCenterY());
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartSort(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo(rect.exactCenterX(), (float) rect.top);
        path.lineTo((float) rect.right, rect.exactCenterY());
        path.lineTo(rect.exactCenterX(), (float) rect.bottom);
        path.lineTo((float) rect.left, rect.exactCenterY());
        path.close();
        path.moveTo((float) rect.left, rect.exactCenterY());
        path.lineTo((float) rect.right, rect.exactCenterY());
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartExtract(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo(rect.exactCenterX(), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartMerge(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo(rect.exactCenterX(), (float) rect.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartOnlineStorage(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float width = ((float) rect.width()) * 0.16f;
        path.reset();
        rectF.set(((float) rect.right) - width, (float) rect.top, ((float) rect.right) + width, (float) rect.bottom);
        path.arcTo(rectF, 90.0f, 180.0f);
        rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + (width * 2.0f), (float) rect.bottom);
        path.arcTo(rectF, 270.0f, -180.0f);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartDelay(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        path.reset();
        path.moveTo((float) rect.left, (float) rect.top);
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, 270.0f, 180.0f);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartMagneticTape(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        AutoShape autoShape2 = autoShape;
        Rect rect2 = rect;
        float width = ((float) rect.width()) * 0.15f;
        float height = ((float) rect.height()) * 0.15f;
        path.reset();
        rectF.set((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom);
        path.addOval(rectF, Path.Direction.CW);
        int i2 = i;
        AutoShape autoShape3 = autoShape;
        Rect rect3 = rect;
        float f2 = f;
        AutoShapeKit.instance().drawShape(canvas, iControl, i2, autoShape3, path, rect3, f2);
        boolean hasLine = autoShape.hasLine();
        autoShape2.setLine(false);
        path.reset();
        path.moveTo(rect.exactCenterX(), ((float) rect2.bottom) - height);
        path.lineTo((float) rect2.right, ((float) rect2.bottom) - height);
        path.lineTo((float) rect2.right, (float) rect2.bottom);
        path.moveTo(rect.exactCenterX(), (float) rect2.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i2, autoShape3, path, rect3, f2);
        autoShape2.setLine(hasLine);
        path.reset();
        path.moveTo(((float) rect2.right) - width, ((float) rect2.bottom) - height);
        path.lineTo((float) rect2.right, ((float) rect2.bottom) - height);
        path.lineTo((float) rect2.right, (float) rect2.bottom);
        path.lineTo(rect.exactCenterX(), (float) rect2.bottom);
        AutoShapeKit.instance().drawShape(canvas, iControl, i2, autoShape3, path, rect3, f2);
    }

    private void drawFlowChartMagneticDisk(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float height = ((float) rect.height()) * 0.32f;
        path.reset();
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, ((float) rect.top) + height);
        path.addOval(rectF, Path.Direction.CW);
        rectF.set((float) rect.left, ((float) rect.bottom) - height, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, 0.0f, 180.0f);
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, ((float) rect.top) + height);
        path.arcTo(rectF, 180.0f, -180.0f);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartMagneticDrum(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float width = ((float) rect.width()) * 0.34f;
        path.reset();
        rectF.set(((float) rect.right) - width, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        path.moveTo(((float) rect.right) - (width / 2.0f), (float) rect.bottom);
        rectF.set(((float) rect.right) - width, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, 90.0f, 180.0f);
        rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + width, (float) rect.bottom);
        path.arcTo(rectF, 270.0f, -180.0f);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }

    private void drawFlowChartDisplay(Canvas canvas, IControl iControl, int i, AutoShape autoShape, Rect rect, float f) {
        float width = ((float) rect.width()) * 0.16f;
        path.reset();
        path.moveTo((float) rect.left, rect.exactCenterY());
        path.lineTo(((float) rect.left) + width, (float) rect.top);
        rectF.set(((float) rect.right) - (2.0f * width), (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, 270.0f, 180.0f);
        path.lineTo(((float) rect.left) + width, (float) rect.bottom);
        path.close();
        AutoShapeKit.instance().drawShape(canvas, iControl, i, autoShape, path, rect, f);
    }
}
