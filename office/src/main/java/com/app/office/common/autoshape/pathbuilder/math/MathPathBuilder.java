package com.app.office.common.autoshape.pathbuilder.math;

import android.graphics.Path;
import android.graphics.Rect;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.ShapeTypes;

public class MathPathBuilder {
    private static Path path = new Path();

    public static Path getMathPath(AutoShape autoShape, Rect rect) {
        path.reset();
        switch (autoShape.getShapeType()) {
            case ShapeTypes.MathPlus /*227*/:
                return getMathPlusPath(autoShape, rect);
            case ShapeTypes.MathMinus /*228*/:
                return getMathMinusPath(autoShape, rect);
            case ShapeTypes.MathMultiply /*229*/:
                return getMathMultiplyPath(autoShape, rect);
            case ShapeTypes.MathDivide /*230*/:
                return getMathDividePath(autoShape, rect);
            case ShapeTypes.MathEqual /*231*/:
                return getMathEqualPath(autoShape, rect);
            case ShapeTypes.MathNotEqual /*232*/:
                return getMathNotEqualPath(autoShape, rect);
            default:
                return null;
        }
    }

    private static Path getMathPlusPath(AutoShape autoShape, Rect rect) {
        float min = (((float) Math.min(rect.width(), rect.height())) * 0.24f) / 2.0f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = (((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue()) / 2.0f;
        }
        float width = ((float) rect.left) + (((float) rect.width()) / 8.0f);
        float width2 = ((float) rect.right) - (((float) rect.width()) / 8.0f);
        float height = ((float) rect.top) + (((float) rect.height()) / 8.0f);
        float height2 = ((float) rect.bottom) - (((float) rect.height()) / 8.0f);
        path.moveTo(width, rect.exactCenterY() - min);
        path.lineTo(rect.exactCenterX() - min, rect.exactCenterY() - min);
        path.lineTo(rect.exactCenterX() - min, height);
        path.lineTo(rect.exactCenterX() + min, height);
        path.lineTo(rect.exactCenterX() + min, rect.exactCenterY() - min);
        path.lineTo(width2, rect.exactCenterY() - min);
        path.lineTo(width2, rect.exactCenterY() + min);
        path.lineTo(rect.exactCenterX() + min, rect.exactCenterY() + min);
        path.lineTo(rect.exactCenterX() + min, height2);
        path.lineTo(rect.exactCenterX() - min, height2);
        path.lineTo(rect.exactCenterX() - min, rect.exactCenterY() + min);
        path.lineTo(width, rect.exactCenterY() + min);
        path.close();
        return path;
    }

    private static Path getMathMinusPath(AutoShape autoShape, Rect rect) {
        float height = (((float) rect.height()) * 0.24f) / 2.0f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            height = (((float) rect.height()) * adjustData[0].floatValue()) / 2.0f;
        }
        float width = ((float) rect.left) + (((float) rect.width()) / 8.0f);
        float width2 = ((float) rect.right) - (((float) rect.width()) / 8.0f);
        path.addRect(width, rect.exactCenterY() - height, width2, rect.exactCenterY() + height, Path.Direction.CW);
        return path;
    }

    private static Path getMathMultiplyPath(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.height()) * 0.24f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            height = ((float) rect.height()) * adjustData[0].floatValue();
        }
        float height2 = ((float) rect.height()) / ((float) rect.width());
        float f = height2 * height2;
        float sqrt = (height * ((float) Math.sqrt((double) (f + 1.0f)))) / 2.0f;
        float sqrt2 = (((float) Math.sqrt((double) ((rect.width() * rect.width()) + (rect.height() * rect.height())))) * ((float) Math.sqrt((double) ((1.0f / f) + 1.0f)))) / 4.0f;
        float f2 = ((float) (rect.right + rect.left)) / 2.0f;
        float exactCenterY = rect.exactCenterY();
        float f3 = (1.0f / height2) + height2;
        float f4 = (sqrt2 - sqrt) / f3;
        float f5 = (height2 * f4) + sqrt;
        float f6 = (sqrt2 + sqrt) / f3;
        float f7 = (height2 * f6) - sqrt;
        path.moveTo(f2, exactCenterY - sqrt);
        float f8 = f2 + f4;
        float f9 = exactCenterY - f5;
        path.lineTo(f8, f9);
        float f10 = f2 + f6;
        float f11 = exactCenterY - f7;
        path.lineTo(f10, f11);
        float f12 = sqrt / height2;
        path.lineTo(f2 + f12, exactCenterY);
        float f13 = f7 + exactCenterY;
        path.lineTo(f10, f13);
        float f14 = f5 + exactCenterY;
        path.lineTo(f8, f14);
        path.lineTo(f2, sqrt + exactCenterY);
        float f15 = f2 - f4;
        path.lineTo(f15, f14);
        float f16 = f2 - f6;
        path.lineTo(f16, f13);
        path.lineTo(f2 - f12, exactCenterY);
        path.lineTo(f16, f11);
        path.lineTo(f15, f9);
        path.close();
        return path;
    }

    private static Path getMathDividePath(AutoShape autoShape, Rect rect) {
        float height = (((float) rect.height()) * 0.2352f) / 2.0f;
        float height2 = ((float) rect.height()) * 0.0588f;
        float height3 = ((float) rect.height()) * 0.1176f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 3) {
            if (adjustData[0] != null) {
                height = (((float) rect.height()) * adjustData[0].floatValue()) / 2.0f;
            }
            if (adjustData[1] != null) {
                height2 = ((float) rect.height()) * adjustData[1].floatValue();
            }
            if (adjustData[2] != null) {
                height3 = ((float) rect.height()) * adjustData[2].floatValue();
            }
        }
        path.addRect(((float) rect.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() - height, ((float) rect.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() + height, Path.Direction.CW);
        path.moveTo(rect.exactCenterX() + height3, ((rect.exactCenterY() - height) - height2) - height3);
        path.addCircle(rect.exactCenterX(), ((rect.exactCenterY() - height) - height2) - height3, height3, Path.Direction.CW);
        path.moveTo(rect.exactCenterX(), rect.exactCenterY() + height + height2 + height3);
        path.addCircle(rect.exactCenterX(), rect.exactCenterY() + height + height2 + height3, height3, Path.Direction.CW);
        return path;
    }

    private static Path getMathEqualPath(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect.height()) * 0.2352f;
        float height2 = (((float) rect.height()) * 0.1176f) / 2.0f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                height = ((float) rect.height()) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                height2 = (((float) rect.height()) * adjustData[1].floatValue()) / 2.0f;
            }
        }
        path.reset();
        path.addRect((((float) rect.width()) / 8.0f) + ((float) rect2.left), (rect.exactCenterY() - height2) - height, ((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() - height2, Path.Direction.CW);
        path.moveTo(((float) rect2.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2);
        path.addRect(((float) rect2.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2, ((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2 + height, Path.Direction.CW);
        return path;
    }

    private static Path getMathNotEqualPath(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect.height()) * 0.2352f;
        float height2 = (((float) rect.height()) * 0.1176f) / 2.0f;
        Float[] adjustData = autoShape.getAdjustData();
        float f = 110.0f;
        if (adjustData != null && adjustData.length >= 3) {
            if (adjustData[0] != null) {
                height = ((float) rect.height()) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                f = (adjustData[1].floatValue() * 10.0f) / 6.0f;
            }
            if (adjustData[2] != null) {
                height2 = (((float) rect.height()) * adjustData[2].floatValue()) / 2.0f;
            }
        }
        float f2 = -((float) Math.tan(Math.toRadians((double) f)));
        float f3 = f2 * f2;
        float sqrt = (((float) Math.sqrt((double) (f3 + 1.0f))) * height) / 2.0f;
        float height3 = (((float) rect.height()) / 2.0f) - ((sqrt - (((float) rect.height()) / 2.0f)) / f3);
        float exactCenterX = rect.exactCenterX();
        float exactCenterY = rect.exactCenterY();
        float height4 = ((((float) rect.height()) / 2.0f) - sqrt) / f2;
        float height5 = ((float) rect.height()) / 2.0f;
        float f4 = (height3 + sqrt) / ((1.0f / f2) + f2);
        float f5 = (f2 * f4) - sqrt;
        float f6 = height2 + height;
        float f7 = (f6 - sqrt) / f2;
        float f8 = (f6 + sqrt) / f2;
        path.reset();
        float f9 = (height2 - sqrt) / f2;
        float f10 = (sqrt + height2) / f2;
        path.moveTo(((float) rect2.left) + (((float) rect.width()) / 8.0f), (rect.exactCenterY() - height2) - height);
        if (f2 >= 0.0f) {
            float f11 = exactCenterY - f6;
            path.lineTo(exactCenterX + f7, f11);
            path.lineTo(exactCenterX + height4, exactCenterY - height5);
            path.lineTo(exactCenterX + f4, exactCenterY - f5);
            path.lineTo(exactCenterX + f8, f11);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), (rect.exactCenterY() - height2) - height);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() - height2);
            float f12 = exactCenterY - height2;
            path.lineTo(exactCenterX + f10, f12);
            float f13 = exactCenterY + height2;
            path.lineTo(exactCenterX - f9, f13);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2 + height);
            float f14 = f6 + exactCenterY;
            path.lineTo(exactCenterX - f7, f14);
            path.lineTo(exactCenterX - height4, height5 + exactCenterY);
            path.lineTo(exactCenterX - f4, exactCenterY + f5);
            path.lineTo(exactCenterX - f8, f14);
            path.lineTo(((float) rect2.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2 + height);
            path.lineTo(((float) rect2.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2);
            path.lineTo(exactCenterX - f10, f13);
            path.lineTo(exactCenterX + f9, f12);
        } else {
            float f15 = exactCenterY - f6;
            path.lineTo(exactCenterX + f8, f15);
            path.lineTo(exactCenterX + f4, exactCenterY - f5);
            path.lineTo(exactCenterX + height4, exactCenterY - height5);
            path.lineTo(exactCenterX + f7, f15);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), (rect.exactCenterY() - height2) - height);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() - height2);
            float f16 = exactCenterY - height2;
            path.lineTo(exactCenterX + f9, f16);
            float f17 = exactCenterY + height2;
            path.lineTo(exactCenterX - f10, f17);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2);
            path.lineTo(((float) rect2.right) - (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2 + height);
            float f18 = f6 + exactCenterY;
            path.lineTo(exactCenterX - f8, f18);
            path.lineTo(exactCenterX - f4, f5 + exactCenterY);
            path.lineTo(exactCenterX - height4, exactCenterY + height5);
            path.lineTo(exactCenterX - f7, f18);
            path.lineTo(((float) rect2.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2 + height);
            path.lineTo(((float) rect2.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() + height2);
            path.lineTo(exactCenterX - f9, f17);
            path.lineTo(exactCenterX + f10, f16);
        }
        path.lineTo(((float) rect2.left) + (((float) rect.width()) / 8.0f), rect.exactCenterY() - height2);
        path.close();
        return path;
    }
}
