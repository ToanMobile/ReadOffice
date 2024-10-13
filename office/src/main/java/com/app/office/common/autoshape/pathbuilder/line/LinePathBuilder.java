package com.app.office.common.autoshape.pathbuilder.line;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.common.autoshape.pathbuilder.LineArrowPathBuilder;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.LineShape;
import java.util.ArrayList;
import java.util.List;

public class LinePathBuilder {
    private static List<ExtendPath> paths = new ArrayList();

    public static List<ExtendPath> getLinePath(LineShape lineShape, Rect rect, float f) {
        paths.clear();
        int shapeType = lineShape.getShapeType();
        if (shapeType != 20) {
            switch (shapeType) {
                case 32:
                    break;
                case 33:
                    return getBentConnectorPath2(lineShape, rect, f);
                case 34:
                    return getBentConnectorPath3(lineShape, rect, f);
                default:
                    switch (shapeType) {
                        case 37:
                            return getCurvedConnector2Path(lineShape, rect, f);
                        case 38:
                            return getCurvedConnector3Path(lineShape, rect, f);
                        case 39:
                            return getCurvedConnector4Path(lineShape, rect, f);
                        case 40:
                            return getCurvedConnector4Path(lineShape, rect, f);
                        default:
                            return null;
                    }
            }
        }
        return getStraightConnectorPath(lineShape, rect, f);
    }

    private static List<ExtendPath> getStraightConnectorPath(LineShape lineShape, Rect rect, float f) {
        ExtendPath extendPath;
        ExtendPath extendPath2;
        Rect rect2;
        Rect rect3 = rect;
        ExtendPath extendPath3 = new ExtendPath();
        Path path = new Path();
        int i = rect3.left;
        int i2 = rect3.top;
        int i3 = rect3.right;
        int i4 = rect3.bottom;
        double sqrt = Math.sqrt((double) ((rect.width() * rect.width()) + (rect.height() * rect.height())));
        if (!lineShape.getStartArrowhead() || !(lineShape.getStartArrow().getType() == 1 || lineShape.getStartArrow().getType() == 2)) {
            extendPath = extendPath3;
        } else {
            int arrowLength = LineArrowPathBuilder.getArrowLength(lineShape.getStartArrow(), lineShape.getLine().getLineWidth());
            int i5 = i3 - i;
            if (Math.abs(i5) >= 1) {
                extendPath = extendPath3;
                i = (int) (((double) i) + ((((double) (((float) arrowLength) * f)) / sqrt) * ((double) i5) * 0.75d));
            } else {
                extendPath = extendPath3;
            }
            int i6 = i4 - i2;
            if (Math.abs(i6) >= 1) {
                i2 = (int) (((double) i2) + ((((double) (((float) arrowLength) * f)) / sqrt) * ((double) i6) * 0.75d));
            }
        }
        if (!lineShape.getEndArrowhead() || !(lineShape.getEndArrow().getType() == 1 || lineShape.getEndArrow().getType() == 2)) {
            extendPath2 = extendPath;
        } else {
            int arrowLength2 = LineArrowPathBuilder.getArrowLength(lineShape.getEndArrow(), lineShape.getLine().getLineWidth());
            if (Math.abs(i3 - i) >= 1) {
                extendPath2 = extendPath;
                i3 = (int) (((double) i3) + ((((double) (((float) arrowLength2) * f)) / sqrt) * ((double) (i - i3)) * 0.75d));
            } else {
                extendPath2 = extendPath;
            }
            if (Math.abs(i4 - i2) >= 1) {
                i4 = (int) (((double) i4) + ((((double) (((float) arrowLength2) * f)) / sqrt) * ((double) (i2 - i4)) * 0.75d));
            }
        }
        path.moveTo((float) i, (float) i2);
        path.lineTo((float) i3, (float) i4);
        BackgroundAndFill backgroundAndFill = lineShape.getBackgroundAndFill();
        if (backgroundAndFill == null) {
            backgroundAndFill = lineShape.getLine().getBackgroundAndFill();
        }
        BackgroundAndFill backgroundAndFill2 = backgroundAndFill;
        ExtendPath extendPath4 = extendPath2;
        extendPath4.setBackgroundAndFill(backgroundAndFill2);
        extendPath4.setLine(lineShape.getLine());
        extendPath4.setPath(path);
        paths.add(extendPath4);
        if (lineShape.getEndArrowhead()) {
            ExtendPath extendPath5 = new ExtendPath();
            extendPath5.setArrowFlag(true);
            rect2 = rect;
            extendPath5.setPath(LineArrowPathBuilder.getDirectLineArrowPath((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, lineShape.getEndArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getEndArrow().getType() != 5) {
                extendPath5.setBackgroundAndFill(backgroundAndFill2);
            } else {
                extendPath5.setLine(lineShape.getLine());
            }
            paths.add(extendPath5);
        } else {
            rect2 = rect;
        }
        if (lineShape.getStartArrowhead()) {
            ExtendPath extendPath6 = new ExtendPath();
            extendPath6.setArrowFlag(true);
            extendPath6.setPath(LineArrowPathBuilder.getDirectLineArrowPath((float) rect2.right, (float) rect2.bottom, (float) rect2.left, (float) rect2.top, lineShape.getStartArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getStartArrow().getType() != 5) {
                extendPath6.setBackgroundAndFill(backgroundAndFill2);
            } else {
                extendPath6.setLine(lineShape.getLine());
            }
            paths.add(extendPath6);
        }
        return paths;
    }

    private static List<ExtendPath> getBentConnectorPath2(LineShape lineShape, Rect rect, float f) {
        Rect rect2 = rect;
        rect.width();
        Float[] adjustData = lineShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            rect.width();
            adjustData[0].floatValue();
        }
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        int i = rect2.left;
        int i2 = rect2.top;
        int i3 = rect2.right;
        int i4 = rect2.bottom;
        if (lineShape.getStartArrowhead() && (lineShape.getStartArrow().getType() == 1 || lineShape.getStartArrow().getType() == 2)) {
            double d = (double) i;
            int i5 = i3 - i;
            i = (int) (d + Math.ceil((double) (((((float) LineArrowPathBuilder.getArrowLength(lineShape.getStartArrow(), lineShape.getLine().getLineWidth())) * f) / ((float) Math.abs(i5))) * ((float) i5) * 0.75f)));
        }
        if (lineShape.getEndArrowhead() && (lineShape.getEndArrow().getType() == 1 || lineShape.getEndArrow().getType() == 2)) {
            i4 = (int) (((double) i4) + Math.ceil((double) (((((float) LineArrowPathBuilder.getArrowLength(lineShape.getEndArrow(), lineShape.getLine().getLineWidth())) * f) / ((float) Math.abs(i4 - i2))) * ((float) (i2 - i4)) * 0.75f)));
        }
        float f2 = (float) i;
        path.moveTo(f2, (float) i2);
        path.lineTo((float) rect2.right, (float) rect2.top);
        float f3 = (float) i4;
        path.lineTo((float) i3, f3);
        BackgroundAndFill backgroundAndFill = lineShape.getBackgroundAndFill();
        if (backgroundAndFill == null) {
            backgroundAndFill = lineShape.getLine().getBackgroundAndFill();
        }
        BackgroundAndFill backgroundAndFill2 = backgroundAndFill;
        extendPath.setPath(path);
        extendPath.setLine(lineShape.getLine());
        paths.add(extendPath);
        if (lineShape.getEndArrowhead()) {
            ExtendPath extendPath2 = new ExtendPath();
            extendPath2.setArrowFlag(true);
            extendPath2.setPath(LineArrowPathBuilder.getDirectLineArrowPath((float) rect2.right, f3, (float) rect2.right, (float) rect2.bottom, lineShape.getEndArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getEndArrow().getType() != 5) {
                extendPath2.setBackgroundAndFill(backgroundAndFill2);
            } else {
                extendPath2.setLine(lineShape.getLine());
            }
            paths.add(extendPath2);
        }
        if (lineShape.getStartArrowhead()) {
            ExtendPath extendPath3 = new ExtendPath();
            extendPath3.setArrowFlag(true);
            float f4 = f2;
            extendPath3.setPath(LineArrowPathBuilder.getDirectLineArrowPath(f4, (float) rect2.top, (float) rect2.left, (float) rect2.top, lineShape.getStartArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getStartArrow().getType() != 5) {
                extendPath3.setBackgroundAndFill(backgroundAndFill2);
            } else {
                extendPath3.setLine(lineShape.getLine());
            }
            paths.add(extendPath3);
        }
        return paths;
    }

    private static List<ExtendPath> getBentConnectorPath3(LineShape lineShape, Rect rect, float f) {
        Rect rect2 = rect;
        float width = ((float) rect.width()) * 0.5f;
        Float[] adjustData = lineShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            width = ((float) rect.width()) * adjustData[0].floatValue();
        }
        float f2 = width;
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        int i = rect2.left;
        int i2 = rect2.top;
        int i3 = rect2.right;
        int i4 = rect2.bottom;
        if (lineShape.getStartArrowhead() && (lineShape.getStartArrow().getType() == 1 || lineShape.getStartArrow().getType() == 2)) {
            double d = (double) i;
            int i5 = i3 - i;
            i = (int) (d + Math.ceil((double) (((((float) LineArrowPathBuilder.getArrowLength(lineShape.getStartArrow(), lineShape.getLine().getLineWidth())) * f) / ((float) Math.abs(i5))) * ((float) i5) * 0.75f)));
        }
        if (lineShape.getEndArrowhead() && (lineShape.getEndArrow().getType() == 1 || lineShape.getEndArrow().getType() == 2)) {
            i3 = (int) (((double) i3) + Math.ceil((double) (((((float) LineArrowPathBuilder.getArrowLength(lineShape.getEndArrow(), lineShape.getLine().getLineWidth())) * f) / ((float) Math.abs(i3 - i))) * ((float) (i - i3)) * 0.75f)));
        }
        path.moveTo((float) i, (float) i2);
        path.lineTo(((float) rect2.left) + f2, (float) rect2.top);
        path.lineTo(((float) rect2.left) + f2, (float) rect2.bottom);
        path.lineTo((float) i3, (float) i4);
        BackgroundAndFill backgroundAndFill = lineShape.getBackgroundAndFill();
        if (backgroundAndFill == null) {
            backgroundAndFill = lineShape.getLine().getBackgroundAndFill();
        }
        BackgroundAndFill backgroundAndFill2 = backgroundAndFill;
        extendPath.setPath(path);
        extendPath.setLine(lineShape.getLine());
        paths.add(extendPath);
        if (lineShape.getEndArrowhead()) {
            ExtendPath extendPath2 = new ExtendPath();
            extendPath2.setArrowFlag(true);
            extendPath2.setPath(LineArrowPathBuilder.getDirectLineArrowPath(((float) rect2.left) + f2, (float) rect2.bottom, (float) rect2.right, (float) rect2.bottom, lineShape.getEndArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getEndArrow().getType() != 5) {
                extendPath2.setBackgroundAndFill(backgroundAndFill2);
            } else {
                extendPath2.setLine(lineShape.getLine());
            }
            paths.add(extendPath2);
        }
        if (lineShape.getStartArrowhead()) {
            ExtendPath extendPath3 = new ExtendPath();
            extendPath3.setArrowFlag(true);
            extendPath3.setPath(LineArrowPathBuilder.getDirectLineArrowPath(((float) rect2.left) + f2, (float) rect2.top, (float) rect2.left, (float) rect2.top, lineShape.getStartArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getStartArrow().getType() != 5) {
                extendPath3.setBackgroundAndFill(backgroundAndFill2);
            } else {
                extendPath3.setLine(lineShape.getLine());
            }
            paths.add(extendPath3);
        }
        return paths;
    }

    private static List<ExtendPath> getCurvedConnector2Path(LineShape lineShape, Rect rect, float f) {
        Rect rect2 = rect;
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        path.reset();
        path.moveTo((float) rect2.left, (float) rect2.top);
        path.quadTo((float) rect2.right, (float) rect2.top, (float) rect2.right, (float) rect2.bottom);
        BackgroundAndFill backgroundAndFill = lineShape.getBackgroundAndFill();
        if (backgroundAndFill == null) {
            backgroundAndFill = lineShape.getLine().getBackgroundAndFill();
        }
        extendPath.setPath(path);
        extendPath.setLine(lineShape.getLine());
        paths.add(extendPath);
        if (lineShape.getEndArrowhead()) {
            ExtendPath extendPath2 = new ExtendPath();
            extendPath2.setArrowFlag(true);
            extendPath2.setPath(LineArrowPathBuilder.getQuadBezArrowPath((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, lineShape.getEndArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getEndArrow().getType() != 5) {
                extendPath2.setBackgroundAndFill(backgroundAndFill);
            } else {
                extendPath2.setLine(lineShape.getLine());
            }
            paths.add(extendPath2);
        }
        if (lineShape.getStartArrowhead()) {
            ExtendPath extendPath3 = new ExtendPath();
            extendPath3.setArrowFlag(true);
            extendPath3.setPath(LineArrowPathBuilder.getQuadBezArrowPath((float) rect2.right, (float) rect2.bottom, (float) rect2.right, (float) rect2.top, (float) rect2.left, (float) rect2.top, lineShape.getStartArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getStartArrow().getType() != 5) {
                extendPath3.setBackgroundAndFill(backgroundAndFill);
            } else {
                extendPath3.setLine(lineShape.getLine());
            }
            paths.add(extendPath3);
        }
        return paths;
    }

    private static List<ExtendPath> getCurvedConnector3Path(LineShape lineShape, Rect rect, float f) {
        PointF pointF;
        Rect rect2 = rect;
        float width = ((float) rect.width()) * 0.5f;
        Float[] adjustData = lineShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            width = ((float) rect.width()) * adjustData[0].floatValue();
        }
        BackgroundAndFill backgroundAndFill = lineShape.getBackgroundAndFill();
        if (backgroundAndFill == null) {
            backgroundAndFill = lineShape.getLine().getBackgroundAndFill();
        }
        PointF pointF2 = null;
        if (lineShape.getEndArrowhead()) {
            ExtendPath extendPath = new ExtendPath();
            extendPath.setArrowFlag(true);
            ArrowPathAndTail quadBezArrowPath = LineArrowPathBuilder.getQuadBezArrowPath(((float) rect2.left) + width, rect.exactCenterY(), ((float) rect2.left) + width, (float) rect2.bottom, (float) rect2.right, (float) rect2.bottom, lineShape.getEndArrow(), lineShape.getLine().getLineWidth(), f);
            byte type = lineShape.getEndArrow().getType();
            if (type == 1 || type == 2) {
                pointF = quadBezArrowPath.getArrowTailCenter();
            } else {
                pointF = null;
            }
            extendPath.setPath(quadBezArrowPath.getArrowPath());
            if (type != 5) {
                extendPath.setBackgroundAndFill(backgroundAndFill);
            } else {
                extendPath.setLine(lineShape.getLine());
            }
            paths.add(extendPath);
        } else {
            pointF = null;
        }
        if (lineShape.getStartArrowhead()) {
            ExtendPath extendPath2 = new ExtendPath();
            extendPath2.setArrowFlag(true);
            ArrowPathAndTail quadBezArrowPath2 = LineArrowPathBuilder.getQuadBezArrowPath(((float) rect2.left) + width, rect.exactCenterY(), ((float) rect2.left) + width, (float) rect2.top, (float) rect2.left, (float) rect2.top, lineShape.getStartArrow(), lineShape.getLine().getLineWidth(), f);
            byte type2 = lineShape.getStartArrow().getType();
            if (type2 == 1 || type2 == 2) {
                pointF2 = quadBezArrowPath2.getArrowTailCenter();
            }
            extendPath2.setPath(quadBezArrowPath2.getArrowPath());
            if (type2 != 5) {
                extendPath2.setBackgroundAndFill(backgroundAndFill);
            } else {
                extendPath2.setLine(lineShape.getLine());
            }
            paths.add(extendPath2);
        }
        ExtendPath extendPath3 = new ExtendPath();
        Path path = new Path();
        path.reset();
        if (pointF2 != null) {
            PointF referencedPosition = LineArrowPathBuilder.getReferencedPosition((float) rect2.left, (float) rect2.top, pointF2.x, pointF2.y, lineShape.getStartArrow().getType());
            path.moveTo(referencedPosition.x, referencedPosition.y);
        } else {
            path.moveTo((float) rect2.left, (float) rect2.top);
        }
        path.quadTo(((float) rect2.left) + width, (float) rect2.top, ((float) rect2.left) + width, rect.exactCenterY());
        path.moveTo(((float) rect2.left) + width, rect.exactCenterY());
        if (pointF != null) {
            PointF referencedPosition2 = LineArrowPathBuilder.getReferencedPosition((float) rect2.right, (float) rect2.bottom, pointF.x, pointF.y, lineShape.getEndArrow().getType());
            path.quadTo(((float) rect2.left) + width, (float) rect2.bottom, referencedPosition2.x, referencedPosition2.y);
        } else {
            path.quadTo(((float) rect2.left) + width, (float) rect2.bottom, (float) rect2.right, (float) rect2.bottom);
        }
        extendPath3.setPath(path);
        extendPath3.setLine(lineShape.getLine());
        paths.add(extendPath3);
        return paths;
    }

    private static List<ExtendPath> getCurvedConnector4Path(LineShape lineShape, Rect rect, float f) {
        Rect rect2 = rect;
        float width = ((float) rect.width()) * 0.5f;
        float height = ((float) rect.height()) * 0.5f;
        Float[] adjustData = lineShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect.width()) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                height = ((float) rect.height()) * adjustData[1].floatValue();
            }
        }
        float f2 = ((float) rect2.left) + width;
        float f3 = ((float) rect2.top) + (height / 2.0f);
        float f4 = (((float) rect2.right) + f2) / 2.0f;
        float f5 = ((float) rect2.top) + height;
        ExtendPath extendPath = new ExtendPath();
        Path path = new Path();
        path.reset();
        path.moveTo((float) rect2.left, (float) rect2.top);
        path.quadTo(f2, (float) rect2.top, f2, f3);
        path.moveTo(f2, f3);
        path.quadTo(f2, f5, f4, f5);
        path.moveTo(f4, f5);
        path.quadTo((float) rect2.right, f5, (float) rect2.right, (float) rect2.bottom);
        BackgroundAndFill backgroundAndFill = lineShape.getBackgroundAndFill();
        if (backgroundAndFill == null) {
            backgroundAndFill = lineShape.getLine().getBackgroundAndFill();
        }
        extendPath.setPath(path);
        extendPath.setLine(lineShape.getLine());
        paths.add(extendPath);
        if (lineShape.getEndArrowhead()) {
            ExtendPath extendPath2 = new ExtendPath();
            extendPath2.setArrowFlag(true);
            extendPath2.setPath(LineArrowPathBuilder.getQuadBezArrowPath(f4, f5, (float) rect2.right, f5, (float) rect2.right, (float) rect2.bottom, lineShape.getEndArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getEndArrow().getType() != 5) {
                extendPath2.setBackgroundAndFill(backgroundAndFill);
            } else {
                extendPath2.setLine(lineShape.getLine());
            }
            paths.add(extendPath2);
        }
        if (lineShape.getStartArrowhead()) {
            ExtendPath extendPath3 = new ExtendPath();
            extendPath3.setArrowFlag(true);
            extendPath3.setPath(LineArrowPathBuilder.getQuadBezArrowPath(f2, f3, f2, (float) rect2.top, (float) rect2.left, (float) rect2.top, lineShape.getStartArrow(), lineShape.getLine().getLineWidth(), f).getArrowPath());
            if (lineShape.getStartArrow().getType() != 5) {
                extendPath3.setBackgroundAndFill(backgroundAndFill);
            } else {
                extendPath3.setLine(lineShape.getLine());
            }
            paths.add(extendPath3);
        }
        return paths;
    }
}
