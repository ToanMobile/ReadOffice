package com.app.office.common.autoshape.pathbuilder.rect;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.ShapeTypes;

public class RectPathBuilder {
    private static Path path = new Path();
    private static RectF rectF = new RectF();

    public static Path getRectPath(AutoShape autoShape, Rect rect) {
        path.reset();
        int shapeType = autoShape.getShapeType();
        if (shapeType != 1) {
            if (shapeType == 2) {
                return getRoundRectanglePath(autoShape, rect);
            }
            if (!(shapeType == 136 || shapeType == 202)) {
                switch (shapeType) {
                    case ShapeTypes.Round1Rect /*210*/:
                        return getRound1Path(autoShape, rect);
                    case ShapeTypes.Round2SameRect /*211*/:
                        return getRound2Path(autoShape, rect);
                    case ShapeTypes.Round2DiagRect /*212*/:
                        return getRound2DiagRectPath(autoShape, rect);
                    case ShapeTypes.Snip1Rect /*213*/:
                        return getSnip1RectPath(autoShape, rect);
                    case ShapeTypes.Snip2SameRect /*214*/:
                        return getSnip2SameRectPath(autoShape, rect);
                    case ShapeTypes.Snip2DiagRect /*215*/:
                        return getSnip2DiagPath(autoShape, rect);
                    case ShapeTypes.SnipRoundRect /*216*/:
                        return getSnipRoundPath(autoShape, rect);
                    default:
                        return null;
                }
            }
        }
        return getRectanglePath(autoShape, rect);
    }

    private static Path getRectanglePath(AutoShape autoShape, Rect rect) {
        path.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        return path;
    }

    private static Path getRoundRectanglePath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
        }
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addRoundRect(rectF, new float[]{min, min, min, min, min, min, min, min}, Path.Direction.CW);
        return path;
    }

    private static Path getRound1Path(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
        }
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addRoundRect(rectF, new float[]{0.0f, 0.0f, min, min, 0.0f, 0.0f, 0.0f, 0.0f}, Path.Direction.CW);
        return path;
    }

    private static Path getRound2Path(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        float f = 0.0f;
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                f = ((float) Math.min(rect.width(), rect.height())) * adjustData[1].floatValue();
            }
        }
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addRoundRect(rectF, new float[]{min, min, min, min, f, f, f, f}, Path.Direction.CW);
        return path;
    }

    private static Path getRound2DiagRectPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        float f = 0.0f;
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                f = ((float) Math.min(rect.width(), rect.height())) * adjustData[1].floatValue();
            }
        }
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addRoundRect(rectF, new float[]{min, min, f, f, min, min, f, f}, Path.Direction.CW);
        return path;
    }

    private static Path getSnip1RectPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
        }
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo(((float) rect.right) - min, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + min);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getSnip2SameRectPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        float f = 0.0f;
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                f = ((float) Math.min(rect.width(), rect.height())) * adjustData[1].floatValue();
            }
        }
        path.moveTo(((float) rect.left) + min, (float) rect.top);
        path.lineTo(((float) rect.right) - min, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + min);
        path.lineTo((float) rect.right, ((float) rect.bottom) - f);
        path.lineTo(((float) rect.right) - f, (float) rect.bottom);
        path.lineTo(((float) rect.left) + f, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.bottom) - f);
        path.lineTo((float) rect.left, ((float) rect.top) + min);
        path.close();
        return path;
    }

    private static Path getSnip2DiagPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        float f = 0.0f;
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                f = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[1].floatValue();
            }
        }
        path.reset();
        path.moveTo(((float) rect.left) + f, (float) rect.top);
        path.lineTo(((float) rect.right) - min, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + min);
        path.lineTo((float) rect.right, ((float) rect.bottom) - f);
        path.lineTo(((float) rect.right) - f, (float) rect.bottom);
        path.lineTo(((float) rect.left) + min, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.bottom) - min);
        path.lineTo((float) rect.left, ((float) rect.top) + f);
        path.close();
        return path;
    }

    private static Path getdrawSnipRoundRectPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        float min2 = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                min2 = ((float) Math.min(rect.width(), rect.height())) * adjustData[1].floatValue();
            }
        }
        path.moveTo(((float) rect.left) + min, (float) rect.top);
        path.lineTo(((float) rect.right) - min2, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + min2);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.top) + min);
        float f = min * 2.0f;
        rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + f, ((float) rect.top) + f);
        path.arcTo(rectF, 180.0f, 90.0f);
        path.close();
        return path;
    }

    private static Path getSnipRoundPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        float min2 = ((float) Math.min(rect.width(), rect.height())) * 0.18f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                min2 = ((float) Math.min(rect.width(), rect.height())) * adjustData[1].floatValue();
            }
        }
        path.reset();
        path.moveTo(((float) rect.left) + min, (float) rect.top);
        path.lineTo(((float) rect.right) - min2, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + min2);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.top) + min);
        float f = min * 2.0f;
        rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + f, ((float) rect.top) + f);
        path.arcTo(rectF, 180.0f, 90.0f);
        path.close();
        return path;
    }
}
