package com.app.office.common.autoshape.pathbuilder.starAndBanner.star;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.ShapeTypes;

public class LaterStarPathBuilder {
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
            switch (shapeType) {
                case 58:
                    return getStar8Path(autoShape, rect);
                case 59:
                    return getStar16Path(autoShape, rect);
                case 60:
                    return getStar32Path(autoShape, rect);
                default:
                    switch (shapeType) {
                        case ShapeTypes.Star5 /*235*/:
                            break;
                        case ShapeTypes.Star6 /*236*/:
                            return getStar6Path(autoShape, rect);
                        case 237:
                            return getStar7Path(autoShape, rect);
                        case 238:
                            return getStar10Path(autoShape, rect);
                        case 239:
                            return getStar12Path(autoShape, rect);
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
            f2 = adjustData[0].floatValue() * min;
            f = adjustData[0].floatValue() * min;
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
        float f;
        float f2;
        float f3;
        float f4;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            f4 = min * 1.05146f;
            f3 = min * 1.10557f;
            f = 0.2f;
            f2 = f4 * 0.2f;
        } else {
            f4 = adjustData[1].floatValue() * min;
            f3 = adjustData[2].floatValue() * min;
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * f4;
            f = adjustData[0].floatValue();
        }
        Path starPath = StarPathBuilder.getStarPath((int) (f4 / 2.0f), (int) (f3 / 2.0f), (int) f2, (int) (f * f3), 5);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), ((float) rect.centerY()) + ((((f3 * ((float) rect.height())) / min) - ((float) rect.height())) / 2.0f));
        return starPath;
    }

    private static Path getStar6Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            f3 = min * 1.1547f;
            f = 0.28f;
            f2 = f3 * 0.28f;
        } else {
            f3 = adjustData[1].floatValue() * min;
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * f3;
            f = adjustData[0].floatValue();
        }
        Path starPath = StarPathBuilder.getStarPath((int) (f3 / 2.0f), (int) (min / 2.0f), (int) f2, (int) (f * min), 6);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar7Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        float f4;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            f4 = min * 1.02572f;
            f3 = min * 1.0521f;
            f = 0.32f;
            f2 = f4 * 0.32f;
        } else {
            f4 = adjustData[1].floatValue() * min;
            f3 = adjustData[2].floatValue() * min;
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * f4;
            f = adjustData[0].floatValue();
        }
        Path starPath = StarPathBuilder.getStarPath((int) (f4 / 2.0f), (int) (f3 / 2.0f), (int) f2, (int) (f * f3), 7);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar8Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            f2 = min * 0.36f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * min;
            f = adjustData[0].floatValue() * min;
        }
        int i = (int) (min / 2.0f);
        Path starPath = StarPathBuilder.getStarPath(i, i, (int) f2, (int) f, 8);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar10Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            f3 = min * 1.05146f;
            f = 0.42f;
            f2 = f3 * 0.42f;
        } else {
            f3 = adjustData[1].floatValue() * min;
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * f3;
            f = adjustData[0].floatValue();
        }
        Path starPath = StarPathBuilder.getStarPath(((int) f3) / 2, ((int) min) / 2, (int) f2, (int) (f * min), 10);
        sm.reset();
        sm.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        starPath.transform(sm);
        starPath.offset((float) rect.centerX(), (float) rect.centerY());
        return starPath;
    }

    private static Path getStar12Path(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        float min = (float) Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            f2 = min * 0.368f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * min;
            f = adjustData[0].floatValue() * min;
        }
        int i = ((int) min) / 2;
        Path starPath = StarPathBuilder.getStarPath(i, i, (int) f2, (int) f, 12);
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
            f2 = min * 0.368f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * min;
            f = adjustData[0].floatValue() * min;
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
            f2 = min * 0.368f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * min;
            f = adjustData[0].floatValue() * min;
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
            f2 = min * 0.368f;
            f = f2;
        } else {
            if (adjustData[0].floatValue() > 0.5f) {
                adjustData[0] = Float.valueOf(0.5f);
            }
            f2 = adjustData[0].floatValue() * min;
            f = adjustData[0].floatValue() * min;
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
