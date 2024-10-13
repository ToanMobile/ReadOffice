package com.app.office.common.autoshape.pathbuilder.starAndBanner.star;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.shape.AutoShape;

public class EarlyStarPathBuilder {
    public static RectF s_rect = new RectF();
    private static Matrix sm = new Matrix();

    public static Path getStarPath(AutoShape autoShape, Rect rect) {
        int shapeType = autoShape.getShapeType();
        if (shapeType != 12) {
            if (shapeType == 92) {
                return getStar24Path(autoShape, rect);
            }
            if (shapeType == 187) {
                return getStar4Path(autoShape, rect);
            }
            if (shapeType != 235) {
                switch (shapeType) {
                    case 58:
                        return getStar8Path(autoShape, rect);
                    case 59:
                        return getStar16Path(autoShape, rect);
                    case 60:
                        return getStar32Path(autoShape, rect);
                    default:
                        return null;
                }
            }
        }
        return getStar5Path(autoShape, rect);
    }

    private static Path getStar4Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            f2 = min * 0.125f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = (0.5f - adjustData[0].floatValue()) * min;
            f = (0.5f - adjustData[0].floatValue()) * min;
        }
        int i = (int) (min / 2.0f);
        Path starPath = StarPathBuilder.getStarPath(i, i, (int) f2, (int) f, 4);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar5Path(AutoShape autoShape, Rect rect) {
        autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        float f = 1.05146f * min;
        float f2 = 1.10557f * min;
        Path starPath = StarPathBuilder.getStarPath((int) (f / 2.0f), (int) (f2 / 2.0f), (int) (f * 0.2f), (int) (0.2f * f2), 5);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), ((float) rect.centerY()) + ((((f2 * ((float) rect.height())) / min) - ((float) rect.height())) / 2.0f));
        return starPath;
    }

    private static Path getStar8Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            f2 = min * 0.375f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = (0.5f - adjustData[0].floatValue()) * min;
            f = (0.5f - adjustData[0].floatValue()) * min;
        }
        int i = (int) (min / 2.0f);
        Path starPath = StarPathBuilder.getStarPath(i, i, (int) f2, (int) f, 8);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar16Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            f2 = min * 0.375f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = (0.5f - adjustData[0].floatValue()) * min;
            f = (0.5f - adjustData[0].floatValue()) * min;
        }
        int i = ((int) min) / 2;
        Path starPath = StarPathBuilder.getStarPath(i, i, (int) f2, (int) f, 16);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar24Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            f2 = min * 0.375f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = (0.5f - adjustData[0].floatValue()) * min;
            f = (0.5f - adjustData[0].floatValue()) * min;
        }
        int i = ((int) min) / 2;
        Path starPath = StarPathBuilder.getStarPath(i, i, (int) f2, (int) f, 24);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar32Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            f2 = min * 0.375f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = (0.5f - adjustData[0].floatValue()) * min;
            f = (0.5f - adjustData[0].floatValue()) * min;
        }
        int i = ((int) min) / 2;
        Path starPath = StarPathBuilder.getStarPath(i, i, (int) f2, (int) f, 32);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }
}
