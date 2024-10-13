package com.app.office.common.autoshape.pathbuilder.baseshape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.ss.util.ColorUtil;
import java.util.ArrayList;
import java.util.List;

public class BaseShapePathBuilder {
    private static final float TODEGREE_03 = 0.3295496f;
    private static final float TODEGREE_07 = 1.6666666f;
    private static Matrix m = new Matrix();
    private static Path path = new Path();
    private static List<ExtendPath> paths = new ArrayList();
    private static RectF rectF = new RectF();

    public static Object getBaseShapePath(AutoShape autoShape, Rect rect) {
        path.reset();
        paths.clear();
        int shapeType = autoShape.getShapeType();
        if (shapeType == 16) {
            return getCubePath(autoShape, rect);
        }
        if (shapeType == 19) {
            return getArcPath(autoShape, rect);
        }
        if (shapeType == 65) {
            return getFoldedCornerPath(autoShape, rect);
        }
        if (shapeType == 234) {
            return getCloudPath(autoShape, rect);
        }
        if (shapeType == 56) {
            return getPentagonPath(autoShape, rect);
        }
        if (shapeType == 57) {
            return getNoSmokingPath(autoShape, rect);
        }
        if (shapeType == 73) {
            return getLightningBoltPath(autoShape, rect);
        }
        if (shapeType == 74) {
            return getHeartPath(autoShape, rect);
        }
        if (shapeType == 95) {
            return getBlockArcPath(autoShape, rect);
        }
        if (shapeType == 96) {
            return getSmileyFacePath(autoShape, rect);
        }
        switch (shapeType) {
            case 3:
                return getEllipsePath(autoShape, rect);
            case 4:
                return getDiamondPath(autoShape, rect);
            case 5:
                return getTrianglePath(autoShape, rect);
            case 6:
                return getRtTrianglePath(autoShape, rect);
            case 7:
                return getParallelogramPath(autoShape, rect);
            case 8:
                return getTrapezoidPath(autoShape, rect);
            case 9:
                return getHexagonPath(autoShape, rect);
            case 10:
                return getOctagonPath(autoShape, rect);
            case 11:
                return getPlusPath(autoShape, rect);
            default:
                switch (shapeType) {
                    case 21:
                        return getPlaquePath(autoShape, rect);
                    case 22:
                        return getCanPath(autoShape, rect);
                    case 23:
                        return getDonutPath(autoShape, rect);
                    default:
                        switch (shapeType) {
                            case 84:
                                return getBevelPath(autoShape, rect);
                            case 85:
                                return getLeftBracketPath(autoShape, rect);
                            case 86:
                                return getRightBracketPath(autoShape, rect);
                            case 87:
                                return getLeftBracePath(autoShape, rect);
                            case 88:
                                return getRightBracePath(autoShape, rect);
                            default:
                                switch (shapeType) {
                                    case 183:
                                        return getSunPath(autoShape, rect);
                                    case 184:
                                        return getMoonPath(autoShape, rect);
                                    case 185:
                                        return getBracketPairPath(autoShape, rect);
                                    case 186:
                                        return getBracePairPath(autoShape, rect);
                                    default:
                                        switch (shapeType) {
                                            case ShapeTypes.Heptagon /*217*/:
                                                return getHeptagonPath(autoShape, rect);
                                            case ShapeTypes.Decagon /*218*/:
                                                return getDecagonPath(autoShape, rect);
                                            case ShapeTypes.Dodecagon /*219*/:
                                                return getDodecagonPath(autoShape, rect);
                                            case ShapeTypes.Pie /*220*/:
                                                return getPiePath(autoShape, rect);
                                            case ShapeTypes.Chord /*221*/:
                                                return getChordPath(autoShape, rect);
                                            case ShapeTypes.Teardrop /*222*/:
                                                return getTeardropPath(autoShape, rect);
                                            case ShapeTypes.Frame /*223*/:
                                                return getFramePath(autoShape, rect);
                                            case 224:
                                                return getHalfFramePath(autoShape, rect);
                                            case ShapeTypes.Corner /*225*/:
                                                return getCornerPath(autoShape, rect);
                                            case 226:
                                                return getDiagStripePath(autoShape, rect);
                                            default:
                                                return null;
                                        }
                                }
                        }
                }
        }
    }

    private static Path getEllipsePath(AutoShape autoShape, Rect rect) {
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        return path;
    }

    private static Path getTrianglePath(AutoShape autoShape, Rect rect) {
        float width = ((float) rect.width()) * 0.5f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            width = ((float) rect.width()) * adjustData[0].floatValue();
        }
        path.moveTo(((float) rect.left) + width, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getRtTrianglePath(AutoShape autoShape, Rect rect) {
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getParallelogramPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        Float[] adjustData = autoShape.getAdjustData();
        if (autoShape.isAutoShape07()) {
            f = ((float) Math.min(rect.width(), rect.height())) * 0.2f;
            if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
                f2 = (float) Math.min(rect.width(), rect.height());
                f3 = adjustData[0].floatValue();
            }
            path.reset();
            path.moveTo(((float) rect.left) + f, (float) rect.top);
            path.lineTo((float) rect.right, (float) rect.top);
            path.lineTo(((float) rect.right) - f, (float) rect.bottom);
            path.lineTo((float) rect.left, (float) rect.bottom);
            path.close();
            return path;
        }
        f = ((float) rect.width()) * 0.25f;
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            f2 = (float) rect.width();
            f3 = adjustData[0].floatValue();
        }
        path.reset();
        path.moveTo(((float) rect.left) + f, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo(((float) rect.right) - f, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
        f = f2 * f3;
        path.reset();
        path.moveTo(((float) rect.left) + f, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo(((float) rect.right) - f, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getTrapezoidPath(AutoShape autoShape, Rect rect) {
        Float[] adjustData = autoShape.getAdjustData();
        if (autoShape.isAutoShape07()) {
            float min = ((float) Math.min(rect.width(), rect.height())) * 0.2f;
            if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            path.moveTo(((float) rect.left) + min, (float) rect.top);
            path.lineTo(((float) rect.right) - min, (float) rect.top);
            path.lineTo((float) rect.right, (float) rect.bottom);
            path.lineTo((float) rect.left, (float) rect.bottom);
            path.close();
        } else {
            float width = ((float) rect.width()) * 0.25f;
            if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
                width = ((float) rect.width()) * adjustData[0].floatValue();
            }
            path.moveTo((float) rect.left, (float) rect.top);
            path.lineTo((float) rect.right, (float) rect.top);
            path.lineTo(((float) rect.right) - width, (float) rect.bottom);
            path.lineTo(((float) rect.left) + width, (float) rect.bottom);
            path.close();
        }
        return path;
    }

    private static Path getDiamondPath(AutoShape autoShape, Rect rect) {
        path.moveTo(rect.exactCenterX(), (float) rect.top);
        path.lineTo((float) rect.right, rect.exactCenterY());
        path.lineTo(rect.exactCenterX(), (float) rect.bottom);
        path.lineTo((float) rect.left, rect.exactCenterY());
        path.close();
        return path;
    }

    private static Path getPentagonPath(AutoShape autoShape, Rect rect) {
        float width = ((float) rect.width()) / 2.0f;
        float tan = ((float) Math.tan(Math.toRadians(36.0d))) * width;
        path.moveTo(((float) rect.left) + width, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + tan);
        path.lineTo(((float) rect.right) - ((((float) rect.height()) - tan) * ((float) Math.tan(Math.toRadians(18.0d)))), (float) rect.bottom);
        path.lineTo(((float) rect.left) + ((((float) rect.height()) - tan) * ((float) Math.tan(Math.toRadians(18.0d)))), (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.top) + tan);
        path.close();
        return path;
    }

    private static Path getHexagonPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        Float[] adjustData = autoShape.getAdjustData();
        if (autoShape.isAutoShape07()) {
            f = ((float) Math.min(rect.width(), rect.height())) * 0.25f;
            if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
                f2 = (float) Math.min(rect.width(), rect.height());
                f3 = adjustData[0].floatValue();
            }
            path.moveTo(((float) rect.left) + f, (float) rect.top);
            path.lineTo(((float) rect.right) - f, (float) rect.top);
            path.lineTo((float) rect.right, rect.exactCenterY());
            path.lineTo(((float) rect.right) - f, (float) rect.bottom);
            path.lineTo(((float) rect.left) + f, (float) rect.bottom);
            path.lineTo((float) rect.left, rect.exactCenterY());
            path.close();
            return path;
        }
        f = ((float) rect.width()) * 0.25f;
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            f2 = (float) rect.width();
            f3 = adjustData[0].floatValue();
        }
        path.moveTo(((float) rect.left) + f, (float) rect.top);
        path.lineTo(((float) rect.right) - f, (float) rect.top);
        path.lineTo((float) rect.right, rect.exactCenterY());
        path.lineTo(((float) rect.right) - f, (float) rect.bottom);
        path.lineTo(((float) rect.left) + f, (float) rect.bottom);
        path.lineTo((float) rect.left, rect.exactCenterY());
        path.close();
        return path;
        f = f2 * f3;
        path.moveTo(((float) rect.left) + f, (float) rect.top);
        path.lineTo(((float) rect.right) - f, (float) rect.top);
        path.lineTo((float) rect.right, rect.exactCenterY());
        path.lineTo(((float) rect.right) - f, (float) rect.bottom);
        path.lineTo(((float) rect.left) + f, (float) rect.bottom);
        path.lineTo((float) rect.left, rect.exactCenterY());
        path.close();
        return path;
    }

    private static Path getHeptagonPath(AutoShape autoShape, Rect rect) {
        float width = ((float) rect.width()) * 0.1f;
        float width2 = ((float) rect.width()) * 0.275f;
        float height = ((float) rect.height()) * 0.2f;
        float height2 = ((float) rect.height()) * 0.35f;
        path.reset();
        path.moveTo(rect.exactCenterX(), (float) rect.top);
        path.lineTo(((float) rect.right) - width, ((float) rect.top) + height);
        path.lineTo((float) rect.right, ((float) rect.bottom) - height2);
        path.lineTo(((float) rect.right) - width2, (float) rect.bottom);
        path.lineTo(((float) rect.left) + width2, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.bottom) - height2);
        path.lineTo(((float) rect.left) + width, ((float) rect.top) + height);
        path.close();
        return path;
    }

    private static Path getOctagonPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.25f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
        }
        path.moveTo(((float) rect.left) + min, (float) rect.top);
        path.lineTo(((float) rect.right) - min, (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + min);
        path.lineTo((float) rect.right, ((float) rect.bottom) - min);
        path.lineTo(((float) rect.right) - min, (float) rect.bottom);
        path.lineTo(((float) rect.left) + min, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.bottom) - min);
        path.lineTo((float) rect.left, ((float) rect.top) + min);
        path.close();
        return path;
    }

    private static Path getDecagonPath(AutoShape autoShape, Rect rect) {
        float width = ((float) rect.width()) * 0.1f;
        float width2 = ((float) rect.width()) * 0.35f;
        float height = ((float) rect.height()) * 0.2f;
        path.moveTo(((float) rect.left) + width2, (float) rect.top);
        path.lineTo(((float) rect.right) - width2, (float) rect.top);
        path.lineTo(((float) rect.right) - width, ((float) rect.top) + height);
        path.lineTo((float) rect.right, rect.exactCenterY());
        path.lineTo(((float) rect.right) - width, ((float) rect.bottom) - height);
        path.lineTo(((float) rect.right) - width2, (float) rect.bottom);
        path.lineTo(((float) rect.left) + width2, (float) rect.bottom);
        path.lineTo(((float) rect.left) + width, ((float) rect.bottom) - height);
        path.lineTo((float) rect.left, rect.exactCenterY());
        path.lineTo(((float) rect.left) + width, ((float) rect.top) + height);
        path.close();
        return path;
    }

    private static Path getDodecagonPath(AutoShape autoShape, Rect rect) {
        float width = ((float) rect.width()) * 0.133f;
        float width2 = ((float) rect.width()) * 0.35f;
        float height = ((float) rect.height()) * 0.133f;
        float height2 = ((float) rect.height()) * 0.35f;
        path.moveTo(((float) rect.left) + width2, (float) rect.top);
        path.lineTo(((float) rect.right) - width2, (float) rect.top);
        path.lineTo(((float) rect.right) - width, ((float) rect.top) + height);
        path.lineTo((float) rect.right, ((float) rect.top) + height2);
        path.lineTo((float) rect.right, ((float) rect.bottom) - height2);
        path.lineTo(((float) rect.right) - width, ((float) rect.bottom) - height);
        path.lineTo(((float) rect.right) - width2, (float) rect.bottom);
        path.lineTo(((float) rect.left) + width2, (float) rect.bottom);
        path.lineTo(((float) rect.left) + width, ((float) rect.bottom) - height);
        path.lineTo((float) rect.left, ((float) rect.bottom) - height2);
        path.lineTo((float) rect.left, ((float) rect.top) + height2);
        path.lineTo(((float) rect.left) + width, ((float) rect.top) + height);
        path.close();
        return path;
    }

    private static Path getPiePath(AutoShape autoShape, Rect rect) {
        Float[] adjustData = autoShape.getAdjustData();
        float f = 0.0f;
        float f2 = 270.0f;
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                f = adjustData[0].floatValue() * TODEGREE_07;
            }
            if (adjustData[1] != null) {
                f2 = adjustData[1].floatValue() * TODEGREE_07;
            }
        }
        path.moveTo((float) rect.centerX(), (float) rect.centerY());
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, f, ((f2 - f) + 360.0f) % 360.0f);
        path.close();
        return path;
    }

    private static Path getChordPath(AutoShape autoShape, Rect rect) {
        Float[] adjustData = autoShape.getAdjustData();
        float f = 45.0f;
        float f2 = 270.0f;
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                f = (adjustData[0].floatValue() * 10.0f) / 6.0f;
            }
            if (adjustData[1] != null) {
                f2 = (adjustData[1].floatValue() * 10.0f) / 6.0f;
            }
        }
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, f, f2 - f);
        path.close();
        return path;
    }

    private static Path getTeardropPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1 || adjustData[0] == null) {
            f2 = ((float) rect.width()) / 2.0f;
            f = ((float) rect.height()) / 2.0f;
        } else {
            f2 = (((float) rect.width()) / 2.0f) * adjustData[0].floatValue();
            f = (((float) rect.height()) / 2.0f) * adjustData[0].floatValue();
        }
        path.moveTo((float) rect.right, (float) rect.centerY());
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.arcTo(rectF, 0.0f, 270.0f);
        path.quadTo(((float) rect.centerX()) + (f2 / 2.0f), (float) rect.top, ((float) rect.centerX()) + f2, ((float) rect.centerY()) - f);
        path.quadTo((float) rect.right, ((float) rect.centerY()) - (f / 2.0f), (float) rect.right, (float) rect.centerY());
        path.close();
        return path;
    }

    private static Path getFramePath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.height(), rect.width())) * 0.1f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
        }
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addRect(rectF, Path.Direction.CW);
        rectF.set(((float) rect.left) + min, ((float) rect.top) + min, ((float) rect.right) - min, ((float) rect.bottom) - min);
        path.addRect(rectF, Path.Direction.CCW);
        return path;
    }

    private static Path getHalfFramePath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.height(), rect.width())) * 0.33333f;
        float min2 = ((float) Math.min(rect.height(), rect.width())) * 0.33333f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                min2 = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                min = ((float) Math.min(rect.height(), rect.width())) * adjustData[1].floatValue();
            }
        }
        float width = (((float) rect.width()) * min2) / ((float) rect.height());
        float height = (((float) rect.height()) * min) / ((float) rect.width());
        path.reset();
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo(((float) rect.right) - width, ((float) rect.top) + min2);
        path.lineTo(((float) rect.left) + min, ((float) rect.top) + min2);
        path.lineTo(((float) rect.left) + min, ((float) rect.bottom) - height);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getCornerPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.height(), rect.width())) * 0.5f;
        float min2 = ((float) Math.min(rect.height(), rect.width())) * 0.5f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                min2 = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
            }
            if (adjustData[1] != null) {
                min = ((float) Math.min(rect.height(), rect.width())) * adjustData[1].floatValue();
            }
        }
        float height = ((float) rect.height()) - min2;
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo(((float) rect.left) + min, (float) rect.top);
        path.lineTo(((float) rect.left) + min, ((float) rect.top) + height);
        path.lineTo((float) rect.right, ((float) rect.top) + height);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getDiagStripePath(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.height()) * 0.5f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            height = ((float) rect.height()) * adjustData[0].floatValue();
        }
        float width = (((float) rect.width()) * height) / ((float) rect.height());
        path.moveTo((float) rect.left, ((float) rect.top) + height);
        path.lineTo(((float) rect.left) + width, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getPlusPath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.height(), rect.width())) * 0.25f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
        }
        path.moveTo((float) rect.left, ((float) rect.top) + min);
        path.lineTo(((float) rect.left) + min, ((float) rect.top) + min);
        path.lineTo(((float) rect.left) + min, (float) rect.top);
        path.lineTo(((float) rect.right) - min, (float) rect.top);
        path.lineTo(((float) rect.right) - min, ((float) rect.top) + min);
        path.lineTo((float) rect.right, ((float) rect.top) + min);
        path.lineTo((float) rect.right, ((float) rect.bottom) - min);
        path.lineTo(((float) rect.right) - min, ((float) rect.bottom) - min);
        path.lineTo(((float) rect.right) - min, (float) rect.bottom);
        path.lineTo(((float) rect.left) + min, (float) rect.bottom);
        path.lineTo(((float) rect.left) + min, ((float) rect.bottom) - min);
        path.lineTo((float) rect.left, ((float) rect.bottom) - min);
        path.close();
        return path;
    }

    private static Path getPlaquePath(AutoShape autoShape, Rect rect) {
        float min = ((float) Math.min(rect.height(), rect.width())) * 0.16f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
        }
        rectF.set(((float) rect.right) - min, ((float) rect.top) - min, ((float) rect.right) + min, ((float) rect.top) + min);
        path.arcTo(rectF, 180.0f, -90.0f);
        rectF.set(((float) rect.right) - min, ((float) rect.bottom) - min, ((float) rect.right) + min, ((float) rect.bottom) + min);
        path.arcTo(rectF, 270.0f, -90.0f);
        rectF.set(((float) rect.left) - min, ((float) rect.bottom) - min, ((float) rect.left) + min, ((float) rect.bottom) + min);
        path.arcTo(rectF, 0.0f, -90.0f);
        rectF.set(((float) rect.left) - min, ((float) rect.top) - min, ((float) rect.left) + min, ((float) rect.top) + min);
        path.arcTo(rectF, 90.0f, -90.0f);
        path.close();
        return path;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getCanPath(com.app.office.common.shape.AutoShape r10, android.graphics.Rect r11) {
        /*
            java.lang.Float[] r0 = r10.getAdjustData()
            boolean r1 = r10.isAutoShape07()
            r2 = 0
            if (r1 == 0) goto L_0x003b
            int r1 = r11.height()
            int r3 = r11.width()
            int r1 = java.lang.Math.min(r1, r3)
            float r1 = (float) r1
            r3 = 1043542835(0x3e333333, float:0.175)
            float r1 = r1 * r3
            if (r0 == 0) goto L_0x005a
            int r3 = r0.length
            r4 = 1
            if (r3 < r4) goto L_0x005a
            r3 = r0[r2]
            if (r3 == 0) goto L_0x005a
            int r1 = r11.height()
            int r3 = r11.width()
            int r1 = java.lang.Math.min(r1, r3)
            float r1 = (float) r1
            r0 = r0[r2]
            float r0 = r0.floatValue()
            goto L_0x0058
        L_0x003b:
            int r1 = r11.height()
            float r1 = (float) r1
            r3 = 1048576000(0x3e800000, float:0.25)
            float r1 = r1 * r3
            if (r0 == 0) goto L_0x005a
            int r3 = r0.length
            if (r3 <= 0) goto L_0x005a
            r3 = r0[r2]
            if (r3 == 0) goto L_0x005a
            int r1 = r11.height()
            float r1 = (float) r1
            r0 = r0[r2]
            float r0 = r0.floatValue()
        L_0x0058:
            float r1 = r1 * r0
        L_0x005a:
            com.app.office.common.bg.BackgroundAndFill r0 = r10.getBackgroundAndFill()
            if (r0 == 0) goto L_0x007d
            com.app.office.common.bg.BackgroundAndFill r3 = new com.app.office.common.bg.BackgroundAndFill
            r3.<init>()
            r3.setFillType(r2)
            com.app.office.ss.util.ColorUtil r2 = com.app.office.ss.util.ColorUtil.instance()
            int r4 = r0.getForegroundColor()
            r5 = 4600877379321698714(0x3fd999999999999a, double:0.4)
            int r2 = r2.getColorWithTint(r4, r5)
            r3.setForegroundColor(r2)
            goto L_0x007e
        L_0x007d:
            r3 = r0
        L_0x007e:
            com.app.office.common.autoshape.ExtendPath r2 = new com.app.office.common.autoshape.ExtendPath
            r2.<init>()
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            android.graphics.RectF r5 = rectF
            int r6 = r11.left
            float r6 = (float) r6
            int r7 = r11.top
            float r7 = (float) r7
            int r8 = r11.right
            float r8 = (float) r8
            int r9 = r11.top
            float r9 = (float) r9
            float r9 = r9 + r1
            r5.set(r6, r7, r8, r9)
            android.graphics.RectF r5 = rectF
            android.graphics.Path$Direction r6 = android.graphics.Path.Direction.CW
            r4.addOval(r5, r6)
            r2.setBackgroundAndFill(r3)
            r2.setPath(r4)
            com.app.office.common.borders.Line r3 = r10.getLine()
            r2.setLine((com.app.office.common.borders.Line) r3)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r3 = paths
            r3.add(r2)
            com.app.office.common.autoshape.ExtendPath r2 = new com.app.office.common.autoshape.ExtendPath
            r2.<init>()
            android.graphics.Path r3 = new android.graphics.Path
            r3.<init>()
            android.graphics.RectF r4 = rectF
            r5 = -1020002304(0xffffffffc3340000, float:-180.0)
            r6 = 1127481344(0x43340000, float:180.0)
            r3.arcTo(r4, r6, r5)
            android.graphics.RectF r4 = rectF
            int r5 = r11.left
            float r5 = (float) r5
            int r7 = r11.bottom
            float r7 = (float) r7
            float r7 = r7 - r1
            int r1 = r11.right
            float r1 = (float) r1
            int r11 = r11.bottom
            float r11 = (float) r11
            r4.set(r5, r7, r1, r11)
            android.graphics.RectF r11 = rectF
            r1 = 0
            r3.arcTo(r11, r1, r6)
            r3.close()
            com.app.office.common.borders.Line r10 = r10.getLine()
            r2.setLine((com.app.office.common.borders.Line) r10)
            r2.setBackgroundAndFill(r0)
            r2.setPath(r3)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r10 = paths
            r10.add(r2)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r10 = paths
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.baseshape.BaseShapePathBuilder.getCanPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    private static List<ExtendPath> getCubePath(AutoShape autoShape, Rect rect) {
        BackgroundAndFill backgroundAndFill;
        float min = ((float) Math.min(rect.height(), rect.width())) * 0.25f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
        }
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, ((float) rect.top) + min, ((float) rect.right) - min, (float) rect.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill2);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        if (backgroundAndFill2 != null) {
            backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), 0.2d));
        } else {
            backgroundAndFill = backgroundAndFill2;
        }
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(((float) rect.left) + min, (float) rect.top);
        path3.lineTo((float) rect.right, (float) rect.top);
        path3.lineTo(((float) rect.right) - min, ((float) rect.top) + min);
        path3.lineTo((float) rect.left, ((float) rect.top) + min);
        path3.close();
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        if (backgroundAndFill2 != null) {
            backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.2d));
        }
        ExtendPath extendPath3 = new ExtendPath();
        Path path4 = new Path();
        path4.moveTo(((float) rect.right) - min, ((float) rect.top) + min);
        path4.lineTo((float) rect.right, (float) rect.top);
        path4.lineTo((float) rect.right, ((float) rect.bottom) - min);
        path4.lineTo(((float) rect.right) - min, (float) rect.bottom);
        path4.close();
        extendPath3.setLine(autoShape.getLine());
        extendPath3.setBackgroundAndFill(backgroundAndFill);
        extendPath3.setPath(path4);
        paths.add(extendPath3);
        return paths;
    }

    private static List<ExtendPath> getBevelPath(AutoShape autoShape, Rect rect) {
        BackgroundAndFill backgroundAndFill;
        float min = ((float) Math.min(rect.height(), rect.width())) * 0.125f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
        }
        BackgroundAndFill backgroundAndFill2 = autoShape.getBackgroundAndFill();
        if (backgroundAndFill2 != null) {
            backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), 0.2d));
        } else {
            backgroundAndFill = backgroundAndFill2;
        }
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.moveTo((float) rect.left, (float) rect.top);
        path2.lineTo((float) rect.right, (float) rect.top);
        path2.lineTo(((float) rect.right) - min, ((float) rect.top) + min);
        path2.lineTo(((float) rect.left) + min, ((float) rect.top) + min);
        path2.close();
        extendPath.setLine(autoShape.getLine());
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        paths.add(extendPath);
        if (backgroundAndFill2 != null) {
            backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.4d));
        }
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(((float) rect.right) - min, ((float) rect.top) + min);
        path3.lineTo((float) rect.right, (float) rect.top);
        path3.lineTo((float) rect.right, (float) rect.bottom);
        path3.lineTo(((float) rect.right) - min, ((float) rect.bottom) - min);
        path3.close();
        extendPath2.setLine(autoShape.getLine());
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        extendPath2.setPath(path3);
        paths.add(extendPath2);
        if (backgroundAndFill2 != null) {
            backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), -0.2d));
        }
        ExtendPath extendPath3 = new ExtendPath();
        Path path4 = new Path();
        path4.moveTo(((float) rect.left) + min, ((float) rect.bottom) - min);
        path4.lineTo(((float) rect.right) - min, ((float) rect.bottom) - min);
        path4.lineTo((float) rect.right, (float) rect.bottom);
        path4.lineTo((float) rect.left, (float) rect.bottom);
        path4.close();
        extendPath3.setLine(autoShape.getLine());
        extendPath3.setBackgroundAndFill(backgroundAndFill);
        extendPath3.setPath(path4);
        paths.add(extendPath3);
        if (backgroundAndFill2 != null) {
            backgroundAndFill = new BackgroundAndFill();
            backgroundAndFill.setFillType((byte) 0);
            backgroundAndFill.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill2.getForegroundColor(), 0.4d));
        }
        ExtendPath extendPath4 = new ExtendPath();
        Path path5 = new Path();
        path5.moveTo((float) rect.left, (float) rect.top);
        path5.lineTo(((float) rect.left) + min, ((float) rect.top) + min);
        path5.lineTo(((float) rect.left) + min, ((float) rect.bottom) - min);
        path5.lineTo((float) rect.left, (float) rect.bottom);
        path5.close();
        extendPath4.setLine(autoShape.getLine());
        extendPath4.setBackgroundAndFill(backgroundAndFill);
        extendPath4.setPath(path5);
        paths.add(extendPath4);
        ExtendPath extendPath5 = new ExtendPath();
        Path path6 = new Path();
        path6.addRect(((float) rect.left) + min, ((float) rect.top) + min, ((float) rect.right) - min, ((float) rect.bottom) - min, Path.Direction.CW);
        extendPath5.setLine(autoShape.getLine());
        extendPath5.setBackgroundAndFill(backgroundAndFill2);
        extendPath5.setPath(path6);
        paths.add(extendPath5);
        return paths;
    }

    private static Path getDonutPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        Float[] adjustData = autoShape.getAdjustData();
        if (autoShape.isAutoShape07()) {
            f = ((float) Math.min(rect.height(), rect.width())) * 0.25f;
            if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
                f = ((float) Math.min(rect.height(), rect.width())) * adjustData[0].floatValue();
            }
            f2 = f;
        } else {
            f = ((float) rect.width()) * 0.25f;
            float height = 0.25f * ((float) rect.height());
            if (adjustData == null || adjustData.length < 1 || adjustData[0] == null) {
                f2 = height;
            } else {
                f = ((float) rect.width()) * adjustData[0].floatValue();
                f2 = adjustData[0].floatValue() * ((float) rect.height());
            }
        }
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path.addOval(rectF, Path.Direction.CW);
        rectF.set(((float) rect.left) + f, ((float) rect.top) + f2, ((float) rect.right) - f, ((float) rect.bottom) - f2);
        path.addOval(rectF, Path.Direction.CCW);
        return path;
    }

    private static Path getNoSmokingPath(AutoShape autoShape, Rect rect) {
        float min = (float) Math.min(rect.width(), rect.height());
        Float[] adjustData = autoShape.getAdjustData();
        float floatValue = (adjustData == null || adjustData.length < 1 || adjustData[0] == null) ? 0.2f : adjustData[0].floatValue();
        float f = min * floatValue;
        rectF.set(0.0f, 0.0f, min, min);
        path.addOval(rectF, Path.Direction.CCW);
        double acos = floatValue <= 0.25f ? Math.acos((double) ((floatValue * 0.5f) / (0.5f - floatValue))) : (((double) (0.5f - floatValue)) * 1.0471975511965976d) / 0.25d;
        float f2 = min - f;
        rectF.set(f, f, f2, f2);
        float f3 = (float) (((2.0d * acos) / 3.141592653589793d) * 180.0d);
        path.arcTo(rectF, (float) (((5.497787143782138d - acos) / 3.141592653589793d) * 180.0d), f3, true);
        path.close();
        path.arcTo(rectF, (float) (((2.356194490192345d - acos) / 3.141592653589793d) * 180.0d), f3, true);
        path.close();
        m.reset();
        m.postScale(((float) rect.width()) / min, ((float) rect.height()) / min);
        path.transform(m);
        path.offset((float) rect.left, (float) rect.top);
        return path;
    }

    private static Path getBlockArcPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3 = 0.25f;
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.25f;
        Float[] adjustData = autoShape.getAdjustData();
        float f4 = 0.0f;
        float f5 = 180.0f;
        if (!autoShape.isAutoShape07()) {
            if (adjustData == null || adjustData.length < 1) {
                min = ((float) rect.width()) * 0.25f;
                f = 180.0f;
            } else {
                f = adjustData[0] != null ? adjustData[0].floatValue() * TODEGREE_03 : 0.0f;
                if (adjustData.length < 2 || adjustData[1] == null) {
                    f2 = (float) rect.width();
                } else {
                    f2 = (float) rect.width();
                    f3 = adjustData[1].floatValue();
                }
                min = f2 * f3;
            }
            if (f >= 0.0f) {
                f4 = (90.0f - f) + 90.0f;
            } else {
                f += 360.0f;
                f4 = 360.0f - (f - 180.0f);
            }
            f5 = f;
        } else if (adjustData != null && adjustData.length >= 3) {
            if (adjustData[0] != null) {
                f5 = (adjustData[0].floatValue() * 10.0f) / 6.0f;
            }
            if (adjustData[1] != null) {
                f4 = (adjustData[1].floatValue() * 10.0f) / 6.0f;
            }
            if (adjustData[2] != null) {
                min = adjustData[2].floatValue() * ((float) Math.min(rect.width(), rect.height()));
            }
        }
        if (f4 >= f5) {
            rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
            path.arcTo(rectF, f5, f4 - f5);
            rectF.set(((float) rect.left) + min, ((float) rect.top) + min, ((float) rect.right) - min, ((float) rect.bottom) - min);
            path.arcTo(rectF, f4, f5 - f4);
        } else {
            rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
            path.arcTo(rectF, f5, (f4 + 360.0f) - f5);
            rectF.set(((float) rect.left) + min, ((float) rect.top) + min, ((float) rect.right) - min, ((float) rect.bottom) - min);
            path.arcTo(rectF, f4, (f5 - f4) - 360.0f);
        }
        path.close();
        return path;
    }

    private static List<ExtendPath> getFoldedCornerPath(AutoShape autoShape, Rect rect) {
        float f;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        if (autoShape.isAutoShape07()) {
            float min = ((float) Math.min(rect.width(), rect.height())) * 0.25f;
            if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
                min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
            }
            BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
            ExtendPath extendPath = new ExtendPath();
            Path path2 = new Path();
            path2.moveTo((float) rect2.left, (float) rect2.top);
            path2.lineTo((float) rect2.right, (float) rect2.top);
            path2.lineTo((float) rect2.right, ((float) rect2.bottom) - min);
            path2.lineTo(((float) rect2.right) - min, (float) rect2.bottom);
            path2.lineTo((float) rect2.left, (float) rect2.bottom);
            path2.close();
            extendPath.setLine(autoShape.getLine());
            extendPath.setPath(path2);
            extendPath.setBackgroundAndFill(backgroundAndFill);
            paths.add(extendPath);
            if (backgroundAndFill != null) {
                BackgroundAndFill backgroundAndFill2 = new BackgroundAndFill();
                backgroundAndFill2.setFillType((byte) 0);
                backgroundAndFill2.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill.getForegroundColor(), -0.2d));
                backgroundAndFill = backgroundAndFill2;
            }
            ExtendPath extendPath2 = new ExtendPath();
            Path path3 = new Path();
            path3.moveTo(((float) rect2.right) - (((((float) Math.sin(Math.toRadians(75.0d))) * min) * ((float) Math.sqrt(6.0d))) / 3.0f), ((float) rect2.bottom) - (((((float) Math.sin(Math.toRadians(75.0d))) * min) * ((float) Math.sqrt(6.0d))) / 3.0f));
            path3.lineTo((float) rect2.right, ((float) rect2.bottom) - min);
            path3.lineTo(((float) rect2.right) - min, (float) rect2.bottom);
            path3.close();
            extendPath2.setLine(autoShape.getLine());
            extendPath2.setPath(path3);
            extendPath2.setBackgroundAndFill(backgroundAndFill);
            paths.add(extendPath2);
        } else {
            float min2 = ((float) Math.min(rect.width(), rect.height())) * 0.125f;
            if (adjustData != null && adjustData.length >= 1) {
                min2 = ((float) Math.min(rect.width(), rect.height())) * (1.0f - adjustData[0].floatValue());
            }
            if (rect.height() > rect.width()) {
                min2 = (float) (((double) min2) * 1.4286d);
                f = 0.7f;
            } else {
                f = 1.4286f;
            }
            ExtendPath extendPath3 = new ExtendPath();
            Path path4 = new Path();
            path4.moveTo((float) rect2.left, (float) rect2.top);
            path4.lineTo((float) rect2.right, (float) rect2.top);
            path4.lineTo((float) rect2.right, ((float) rect2.bottom) - min2);
            float f2 = f * min2;
            path4.lineTo(((float) rect2.right) - f2, (float) rect2.bottom);
            path4.lineTo((float) rect2.left, (float) rect2.bottom);
            path4.close();
            BackgroundAndFill backgroundAndFill3 = autoShape.getBackgroundAndFill();
            extendPath3.setLine(autoShape.getLine());
            extendPath3.setPath(path4);
            extendPath3.setBackgroundAndFill(backgroundAndFill3);
            paths.add(extendPath3);
            if (backgroundAndFill3 != null) {
                BackgroundAndFill backgroundAndFill4 = new BackgroundAndFill();
                backgroundAndFill4.setFillType((byte) 0);
                backgroundAndFill4.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill3.getForegroundColor(), -0.2d));
                backgroundAndFill3 = backgroundAndFill4;
            }
            ExtendPath extendPath4 = new ExtendPath();
            Path path5 = new Path();
            path5.moveTo(((float) rect2.right) - (((((float) Math.sin(Math.toRadians(75.0d))) * f2) * ((float) Math.sqrt(6.0d))) / 3.0f), ((float) rect2.bottom) - (((((float) Math.sin(Math.toRadians(75.0d))) * min2) * ((float) Math.sqrt(6.0d))) / 3.0f));
            path5.lineTo((float) rect2.right, ((float) rect2.bottom) - min2);
            path5.lineTo(((float) rect2.right) - f2, (float) rect2.bottom);
            path5.close();
            extendPath4.setLine(autoShape.getLine());
            extendPath4.setPath(path5);
            extendPath4.setBackgroundAndFill(backgroundAndFill3);
            paths.add(extendPath4);
        }
        return paths;
    }

    private static List<ExtendPath> getSmileyFacePath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float height = ((float) rect.height()) * 0.04653f * 2.0f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (autoShape.isAutoShape07()) {
                if (adjustData[0] != null) {
                    f2 = (float) rect.height();
                    f = adjustData[0].floatValue();
                }
            } else if (adjustData[0] != null) {
                f2 = (float) rect.height();
                f = adjustData[0].floatValue() - 0.77f;
            }
            height = f2 * f * 2.0f;
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        path2.addOval(rectF, Path.Direction.CW);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        extendPath.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath);
        float width = ((float) rect.left) + (((float) rect.width()) / 4.0f);
        float width2 = ((float) rect.right) - (((float) rect.width()) / 4.0f);
        float height2 = (((float) rect.bottom) - (((float) rect.height()) / 4.0f)) - Math.abs(height);
        float height3 = (((float) rect.bottom) - (((float) rect.height()) / 4.0f)) + Math.abs(height);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        rectF.set(width, height2, width2, height3);
        if (height >= 0.0f) {
            path3.arcTo(rectF, 15.0f, 150.0f);
        } else {
            path3.arcTo(rectF, 195.0f, 150.0f);
        }
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        extendPath2.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath2);
        if (backgroundAndFill != null) {
            BackgroundAndFill backgroundAndFill2 = new BackgroundAndFill();
            backgroundAndFill2.setFillType((byte) 0);
            backgroundAndFill2.setForegroundColor(ColorUtil.instance().getColorWithTint(backgroundAndFill.getForegroundColor(), -0.2d));
            backgroundAndFill = backgroundAndFill2;
        }
        float exactCenterX = rect.exactCenterX() - (((float) rect.width()) / 5.0f);
        float exactCenterX2 = rect.exactCenterX() - (((float) rect.width()) / 10.0f);
        float exactCenterY = rect.exactCenterY() - (((float) rect.height()) / 5.0f);
        float exactCenterY2 = rect.exactCenterY() - (((float) rect.height()) / 10.0f);
        ExtendPath extendPath3 = new ExtendPath();
        Path path4 = new Path();
        rectF.set(exactCenterX, exactCenterY, exactCenterX2, exactCenterY2);
        path4.addOval(rectF, Path.Direction.CW);
        extendPath3.setPath(path4);
        extendPath3.setLine(autoShape.getLine());
        extendPath3.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath3);
        float exactCenterX3 = rect.exactCenterX() + (((float) rect.width()) / 10.0f);
        float exactCenterX4 = rect.exactCenterX() + (((float) rect.width()) / 5.0f);
        ExtendPath extendPath4 = new ExtendPath();
        Path path5 = new Path();
        rectF.set(exactCenterX3, exactCenterY, exactCenterX4, exactCenterY2);
        path5.addOval(rectF, Path.Direction.CW);
        extendPath4.setPath(path5);
        extendPath4.setLine(autoShape.getLine());
        extendPath4.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath4);
        return paths;
    }

    private static Path getSunPath(AutoShape autoShape, Rect rect) {
        float f;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        float f2 = 0.0f;
        if (adjustData == null || adjustData.length < 1) {
            float height = 0.25f * ((float) rect.height());
            f2 = ((float) rect.width()) * 0.25f;
            f = height;
        } else if (adjustData[0] != null) {
            f2 = ((float) rect.width()) * adjustData[0].floatValue();
            f = adjustData[0].floatValue() * ((float) rect.height());
        } else {
            f = 0.0f;
        }
        rectF.set(((float) rect2.left) + f2, ((float) rect2.top) + f, ((float) rect2.right) - f2, ((float) rect2.bottom) - f);
        path.addOval(rectF, Path.Direction.CW);
        path.moveTo((float) rect.centerX(), (float) rect2.top);
        float f3 = f * 0.75f;
        path.lineTo((float) (rect.centerX() + (rect.width() / 14)), ((float) rect2.top) + f3);
        path.lineTo((float) (rect.centerX() - (rect.width() / 14)), ((float) rect2.top) + f3);
        path.close();
        path.moveTo((float) rect.centerX(), (float) rect2.bottom);
        path.lineTo((float) (rect.centerX() - (rect.width() / 14)), ((float) rect2.bottom) - f3);
        path.lineTo((float) (rect.centerX() + (rect.width() / 14)), ((float) rect2.bottom) - f3);
        path.close();
        path.moveTo((float) rect2.left, (float) rect.centerY());
        float f4 = f2 * 0.75f;
        path.lineTo(((float) rect2.left) + f4, (float) (rect.centerY() - (rect.height() / 14)));
        path.lineTo(((float) rect2.left) + f4, (float) (rect.centerY() + (rect.height() / 14)));
        path.close();
        path.moveTo((float) rect2.right, (float) rect.centerY());
        path.lineTo(((float) rect2.right) - f4, (float) (rect.centerY() + (rect.height() / 14)));
        path.lineTo(((float) rect2.right) - f4, (float) (rect.centerY() - (rect.height() / 14)));
        path.close();
        float sqrt = ((float) (Math.sqrt(0.5d) * ((double) rect.width()))) / 2.0f;
        float sqrt2 = ((float) (Math.sqrt(0.5d) * ((double) rect.height()))) / 2.0f;
        float sqrt3 = ((float) (Math.sqrt(0.5d) * ((double) (((float) rect.width()) - (f4 * 2.0f))))) / 2.0f;
        float sqrt4 = ((float) (Math.sqrt(0.5d) * ((double) (((float) rect.height()) - (f3 * 2.0f))))) / 2.0f;
        float width = (float) ((rect.width() + rect.height()) / 28);
        float width2 = (float) (((double) (((float) rect.width()) * width)) / Math.sqrt(Math.pow((double) rect.width(), 2.0d) + Math.pow((double) rect.height(), 2.0d)));
        float height2 = (float) (((double) (width * ((float) rect.height()))) / Math.sqrt(Math.pow((double) rect.width(), 2.0d) + Math.pow((double) rect.height(), 2.0d)));
        float f5 = sqrt3 + width2;
        float f6 = sqrt4 - height2;
        float f7 = sqrt3 - width2;
        float f8 = sqrt4 + height2;
        path.moveTo(((float) rect.centerX()) + sqrt, ((float) rect.centerY()) + sqrt2);
        path.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) + f6);
        path.lineTo(((float) rect.centerX()) + f7, ((float) rect.centerY()) + f8);
        path.close();
        path.moveTo(((float) rect.centerX()) - sqrt, ((float) rect.centerY()) - sqrt2);
        path.lineTo(((float) rect.centerX()) - f5, ((float) rect.centerY()) - f6);
        path.lineTo(((float) rect.centerX()) - f7, ((float) rect.centerY()) - f8);
        path.close();
        path.moveTo(((float) rect.centerX()) + sqrt, ((float) rect.centerY()) - sqrt2);
        path.lineTo(((float) rect.centerX()) + f5, ((float) rect.centerY()) - f6);
        path.lineTo(((float) rect.centerX()) + f7, ((float) rect.centerY()) - f8);
        path.close();
        path.moveTo(((float) rect.centerX()) - sqrt, ((float) rect.centerY()) + sqrt2);
        path.lineTo(((float) rect.centerX()) - f5, ((float) rect.centerY()) + f6);
        path.lineTo(((float) rect.centerX()) - f7, ((float) rect.centerY()) + f8);
        path.close();
        return path;
    }

    private static Path getHeartPath(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        path.moveTo(0.0f, 30.0f);
        path.cubicTo(0.0f, -10.0f, 40.0f, 0.0f, 50.0f, 20.0f);
        path.cubicTo(60.0f, 0.0f, 100.0f, -10.0f, 100.0f, 30.0f);
        path.cubicTo(100.0f, 60.0f, 60.0f, 100.0f, 50.0f, 100.0f);
        path.cubicTo(40.0f, 100.0f, 0.0f, 60.0f, 0.0f, 30.0f);
        path.close();
        m.reset();
        m.postScale(((float) rect.width()) / 100.0f, ((float) rect.height()) / 100.0f);
        path.transform(m);
        path.offset((float) rect2.left, (float) rect2.top);
        return path;
    }

    private static Path getLightningBoltPath(AutoShape autoShape, Rect rect) {
        float width = (float) rect.width();
        float height = (float) rect.height();
        path.moveTo(((float) rect.left) + (width * 0.4f), (float) rect.top);
        path.lineTo(((float) rect.left) + (0.6f * width), ((float) rect.top) + (0.2857f * height));
        path.lineTo(((float) rect.left) + (0.5167f * width), ((float) rect.top) + (0.3f * height));
        path.lineTo(((float) rect.right) - (0.23f * width), ((float) rect.bottom) - (0.44f * height));
        path.lineTo(((float) rect.right) - (0.3448f * width), ((float) rect.bottom) - (0.4f * height));
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo(((float) rect.left) + (0.4615f * width), ((float) rect.bottom) - (0.3167f * height));
        path.lineTo(((float) rect.left) + (0.5455f * width), ((float) rect.bottom) - (height * 0.35f));
        path.lineTo(((float) rect.left) + (0.25f * width), ((float) rect.top) + (0.4545f * height));
        path.lineTo(((float) rect.left) + (width * 0.35f), ((float) rect.top) + (0.3921f * height));
        path.lineTo((float) rect.left, ((float) rect.top) + (height * 0.19f));
        path.close();
        return path;
    }

    private static Path getMoonPath(AutoShape autoShape, Rect rect) {
        float width = ((float) rect.width()) * 0.5f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            width = ((float) rect.width()) * (1.0f - adjustData[0].floatValue());
        }
        rectF.set((float) rect.left, (float) rect.top, (float) ((rect.right * 2) - rect.left), (float) rect.bottom);
        path.arcTo(rectF, 90.0f, 180.0f);
        rectF.set(((float) rect.right) - width, (float) rect.top, ((float) rect.right) + width, (float) rect.bottom);
        path.arcTo(rectF, 270.0f, -180.0f);
        return path;
    }

    private static Path getCloudPath(AutoShape autoShape, Rect rect) {
        path.reset();
        rectF.set(0.0f, 160.0f, 90.0f, 285.0f);
        path.arcTo(rectF, 120.0f, 148.0f);
        rectF.set(41.0f, 44.0f, 188.0f, 250.0f);
        path.arcTo(rectF, 172.5f, 127.5f);
        rectF.set(140.0f, 14.0f, 264.0f, 220.0f);
        path.arcTo(rectF, 218.0f, 90.0f);
        rectF.set(230.0f, 0.0f, 340.0f, 210.0f);
        path.arcTo(rectF, 219.0f, 92.0f);
        rectF.set(296.0f, 0.0f, 428.0f, 246.0f);
        path.arcTo(rectF, 232.0f, 101.0f);
        rectF.set(342.0f, 60.0f, 454.0f, 214.0f);
        path.arcTo(rectF, 293.0f, 89.0f);
        rectF.set(324.0f, 130.0f, 468.0f, 327.0f);
        path.arcTo(rectF, 319.0f, 119.0f);
        rectF.set(280.0f, 240.0f, 405.0f, 412.0f);
        path.arcTo(rectF, 1.0f, 122.0f);
        rectF.set(168.0f, 274.0f, 312.0f, 468.0f);
        path.arcTo(rectF, 16.0f, 130.0f);
        rectF.set(57.0f, 249.0f, 213.0f, 441.0f);
        path.arcTo(rectF, 56.0f, 74.0f);
        rectF.set(11.0f, 259.0f, 99.0f, 386.0f);
        path.arcTo(rectF, 84.0f, 140.0f);
        path.close();
        m.reset();
        m.postScale(((float) rect.width()) / 468.0f, ((float) rect.height()) / 468.0f);
        path.transform(m);
        path.offset((float) rect.left, (float) rect.top);
        return path;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getArcPath(com.app.office.common.shape.AutoShape r11, android.graphics.Rect r12) {
        /*
            java.lang.Float[] r0 = r11.getAdjustData()
            boolean r1 = r11.isAutoShape07()
            r2 = 2
            r3 = 1132920832(0x43870000, float:270.0)
            r4 = 0
            r5 = 1
            r6 = 1135869952(0x43b40000, float:360.0)
            r7 = 0
            if (r1 == 0) goto L_0x002b
            if (r0 == 0) goto L_0x005e
            int r1 = r0.length
            if (r1 < r2) goto L_0x005e
            r1 = r0[r4]
            float r1 = r1.floatValue()
            r2 = 1070945621(0x3fd55555, float:1.6666666)
            float r3 = r1 * r2
            r0 = r0[r5]
            float r0 = r0.floatValue()
            float r7 = r0 * r2
            goto L_0x005e
        L_0x002b:
            if (r0 == 0) goto L_0x0050
            int r1 = r0.length
            if (r1 < r5) goto L_0x0050
            r1 = r0[r4]
            r3 = 1077936128(0x40400000, float:3.0)
            if (r1 == 0) goto L_0x003e
            r1 = r0[r4]
            float r1 = r1.floatValue()
            float r1 = r1 / r3
            goto L_0x003f
        L_0x003e:
            r1 = 0
        L_0x003f:
            int r4 = r0.length
            if (r4 < r2) goto L_0x004f
            r2 = r0[r5]
            if (r2 == 0) goto L_0x004f
            r0 = r0[r5]
            float r0 = r0.floatValue()
            float r0 = r0 / r3
            r3 = r1
            goto L_0x0051
        L_0x004f:
            r3 = r1
        L_0x0050:
            r0 = 0
        L_0x0051:
            int r1 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x0056
            float r3 = r3 + r6
        L_0x0056:
            int r1 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x005d
            float r7 = r0 + r6
            goto L_0x005e
        L_0x005d:
            r7 = r0
        L_0x005e:
            com.app.office.common.bg.BackgroundAndFill r0 = r11.getBackgroundAndFill()
            com.app.office.common.autoshape.ExtendPath r1 = new com.app.office.common.autoshape.ExtendPath
            r1.<init>()
            android.graphics.Path r1 = new android.graphics.Path
            r1.<init>()
            if (r0 == 0) goto L_0x00ab
            com.app.office.common.autoshape.ExtendPath r1 = new com.app.office.common.autoshape.ExtendPath
            r1.<init>()
            android.graphics.Path r2 = new android.graphics.Path
            r2.<init>()
            float r4 = r12.exactCenterX()
            float r5 = r12.exactCenterY()
            r2.moveTo(r4, r5)
            android.graphics.RectF r4 = rectF
            int r5 = r12.left
            float r5 = (float) r5
            int r8 = r12.top
            float r8 = (float) r8
            int r9 = r12.right
            float r9 = (float) r9
            int r10 = r12.bottom
            float r10 = (float) r10
            r4.set(r5, r8, r9, r10)
            android.graphics.RectF r4 = rectF
            float r5 = r7 - r3
            float r5 = r5 + r6
            float r5 = r5 % r6
            r2.arcTo(r4, r3, r5)
            r2.close()
            r1.setPath(r2)
            r1.setBackgroundAndFill(r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = paths
            r0.add(r1)
        L_0x00ab:
            boolean r0 = r11.hasLine()
            if (r0 == 0) goto L_0x00e3
            com.app.office.common.autoshape.ExtendPath r0 = new com.app.office.common.autoshape.ExtendPath
            r0.<init>()
            android.graphics.Path r1 = new android.graphics.Path
            r1.<init>()
            android.graphics.RectF r2 = rectF
            int r4 = r12.left
            float r4 = (float) r4
            int r5 = r12.top
            float r5 = (float) r5
            int r8 = r12.right
            float r8 = (float) r8
            int r12 = r12.bottom
            float r12 = (float) r12
            r2.set(r4, r5, r8, r12)
            android.graphics.RectF r12 = rectF
            float r7 = r7 - r3
            float r7 = r7 + r6
            float r7 = r7 % r6
            r1.arcTo(r12, r3, r7)
            r0.setPath(r1)
            com.app.office.common.borders.Line r11 = r11.getLine()
            r0.setLine((com.app.office.common.borders.Line) r11)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r11 = paths
            r11.add(r0)
        L_0x00e3:
            java.util.List<com.app.office.common.autoshape.ExtendPath> r11 = paths
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.baseshape.BaseShapePathBuilder.getArcPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    private static List<ExtendPath> getBracketPairPath(AutoShape autoShape, Rect rect) {
        float f;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1 || adjustData[0] == null) {
            f = 0.18f * ((float) Math.min(rect.width(), rect.height()));
        } else {
            f = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        if (backgroundAndFill != null) {
            ExtendPath extendPath = new ExtendPath();
            Path path2 = new Path();
            rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
            path2.addRoundRect(rectF, new float[]{f, f, f, f, f, f, f, f}, Path.Direction.CW);
            extendPath.setPath(path2);
            extendPath.setBackgroundAndFill(backgroundAndFill);
            paths.add(extendPath);
        }
        if (autoShape.hasLine()) {
            ExtendPath extendPath2 = new ExtendPath();
            Path path3 = new Path();
            float f2 = 2.0f * f;
            rectF.set(((float) rect.right) - f2, (float) rect.top, (float) rect.right, ((float) rect.top) + f2);
            path3.arcTo(rectF, 270.0f, 90.0f);
            rectF.set(((float) rect.right) - f2, ((float) rect.bottom) - f2, (float) rect.right, (float) rect.bottom);
            path3.arcTo(rectF, 0.0f, 90.0f);
            path3.moveTo(((float) rect.left) + f, (float) rect.bottom);
            rectF.set((float) rect.left, ((float) rect.bottom) - f2, ((float) rect.left) + f2, (float) rect.bottom);
            path3.arcTo(rectF, 90.0f, 90.0f);
            rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + f2, ((float) rect.top) + f2);
            path3.arcTo(rectF, 180.0f, 90.0f);
            extendPath2.setPath(path3);
            extendPath2.setLine(autoShape.getLine());
            paths.add(extendPath2);
        }
        return paths;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00bd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getLeftBracketPath(com.app.office.common.shape.AutoShape r13, android.graphics.Rect r14) {
        /*
            java.lang.Float[] r0 = r13.getAdjustData()
            boolean r1 = r13.isAutoShape07()
            r2 = 1
            r3 = 1034147594(0x3da3d70a, float:0.08)
            r4 = 0
            if (r1 == 0) goto L_0x0037
            if (r0 == 0) goto L_0x002a
            int r1 = r0.length
            if (r1 < r2) goto L_0x002a
            int r1 = r14.width()
            int r2 = r14.height()
            int r1 = java.lang.Math.min(r1, r2)
            float r1 = (float) r1
            r0 = r0[r4]
            float r0 = r0.floatValue()
        L_0x0027:
            float r1 = r1 * r0
            goto L_0x0059
        L_0x002a:
            int r0 = r14.width()
            int r1 = r14.height()
            int r0 = java.lang.Math.min(r0, r1)
            goto L_0x0056
        L_0x0037:
            if (r0 == 0) goto L_0x0052
            int r1 = r0.length
            if (r1 < r2) goto L_0x0052
            r1 = r0[r4]
            if (r1 == 0) goto L_0x0052
            r1 = r0[r4]
            if (r1 == 0) goto L_0x0050
            int r1 = r14.height()
            float r1 = (float) r1
            r0 = r0[r4]
            float r0 = r0.floatValue()
            goto L_0x0027
        L_0x0050:
            r1 = 0
            goto L_0x0059
        L_0x0052:
            int r0 = r14.height()
        L_0x0056:
            float r0 = (float) r0
            float r1 = r0 * r3
        L_0x0059:
            com.app.office.common.bg.BackgroundAndFill r0 = r13.getBackgroundAndFill()
            r2 = 1127481344(0x43340000, float:180.0)
            r3 = 1073741824(0x40000000, float:2.0)
            r4 = 1119092736(0x42b40000, float:90.0)
            if (r0 == 0) goto L_0x00b7
            com.app.office.common.autoshape.ExtendPath r5 = new com.app.office.common.autoshape.ExtendPath
            r5.<init>()
            android.graphics.Path r6 = new android.graphics.Path
            r6.<init>()
            android.graphics.RectF r7 = rectF
            int r8 = r14.left
            float r8 = (float) r8
            int r9 = r14.bottom
            float r9 = (float) r9
            float r10 = r1 * r3
            float r9 = r9 - r10
            int r11 = r14.right
            int r11 = r11 * 2
            int r12 = r14.left
            int r11 = r11 - r12
            float r11 = (float) r11
            int r12 = r14.bottom
            float r12 = (float) r12
            r7.set(r8, r9, r11, r12)
            android.graphics.RectF r7 = rectF
            r6.arcTo(r7, r4, r4)
            android.graphics.RectF r7 = rectF
            int r8 = r14.left
            float r8 = (float) r8
            int r9 = r14.top
            float r9 = (float) r9
            int r11 = r14.right
            int r11 = r11 * 2
            int r12 = r14.left
            int r11 = r11 - r12
            float r11 = (float) r11
            int r12 = r14.top
            float r12 = (float) r12
            float r12 = r12 + r10
            r7.set(r8, r9, r11, r12)
            android.graphics.RectF r7 = rectF
            r6.arcTo(r7, r2, r4)
            r6.close()
            r5.setPath(r6)
            r5.setBackgroundAndFill(r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = paths
            r0.add(r5)
        L_0x00b7:
            boolean r0 = r13.hasLine()
            if (r0 == 0) goto L_0x0110
            com.app.office.common.autoshape.ExtendPath r0 = new com.app.office.common.autoshape.ExtendPath
            r0.<init>()
            android.graphics.Path r5 = new android.graphics.Path
            r5.<init>()
            android.graphics.RectF r6 = rectF
            int r7 = r14.left
            float r7 = (float) r7
            int r8 = r14.bottom
            float r8 = (float) r8
            float r1 = r1 * r3
            float r8 = r8 - r1
            int r3 = r14.right
            int r3 = r3 * 2
            int r9 = r14.left
            int r3 = r3 - r9
            float r3 = (float) r3
            int r9 = r14.bottom
            float r9 = (float) r9
            r6.set(r7, r8, r3, r9)
            android.graphics.RectF r3 = rectF
            r5.arcTo(r3, r4, r4)
            android.graphics.RectF r3 = rectF
            int r6 = r14.left
            float r6 = (float) r6
            int r7 = r14.top
            float r7 = (float) r7
            int r8 = r14.right
            int r8 = r8 * 2
            int r9 = r14.left
            int r8 = r8 - r9
            float r8 = (float) r8
            int r14 = r14.top
            float r14 = (float) r14
            float r14 = r14 + r1
            r3.set(r6, r7, r8, r14)
            android.graphics.RectF r14 = rectF
            r5.arcTo(r14, r2, r4)
            r0.setPath(r5)
            com.app.office.common.borders.Line r13 = r13.getLine()
            r0.setLine((com.app.office.common.borders.Line) r13)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r13 = paths
            r13.add(r0)
        L_0x0110:
            java.util.List<com.app.office.common.autoshape.ExtendPath> r13 = paths
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.baseshape.BaseShapePathBuilder.getLeftBracketPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00bc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getRightBracketPath(com.app.office.common.shape.AutoShape r14, android.graphics.Rect r15) {
        /*
            java.lang.Float[] r0 = r14.getAdjustData()
            boolean r1 = r14.isAutoShape07()
            r2 = 1
            r3 = 1034147594(0x3da3d70a, float:0.08)
            r4 = 0
            if (r1 == 0) goto L_0x0039
            if (r0 == 0) goto L_0x002c
            int r1 = r0.length
            if (r1 < r2) goto L_0x002c
            r1 = r0[r4]
            if (r1 == 0) goto L_0x002c
            int r1 = r15.width()
            int r2 = r15.height()
            int r1 = java.lang.Math.min(r1, r2)
            float r1 = (float) r1
            r0 = r0[r4]
            float r0 = r0.floatValue()
            goto L_0x004d
        L_0x002c:
            int r0 = r15.width()
            int r1 = r15.height()
            int r0 = java.lang.Math.min(r0, r1)
            goto L_0x0054
        L_0x0039:
            if (r0 == 0) goto L_0x0050
            int r1 = r0.length
            if (r1 < r2) goto L_0x0050
            r1 = r0[r4]
            if (r1 == 0) goto L_0x0050
            int r1 = r15.height()
            float r1 = (float) r1
            r0 = r0[r4]
            float r0 = r0.floatValue()
        L_0x004d:
            float r1 = r1 * r0
            goto L_0x0057
        L_0x0050:
            int r0 = r15.height()
        L_0x0054:
            float r0 = (float) r0
            float r1 = r0 * r3
        L_0x0057:
            com.app.office.common.bg.BackgroundAndFill r0 = r14.getBackgroundAndFill()
            r2 = 0
            r3 = 1132920832(0x43870000, float:270.0)
            r4 = 1073741824(0x40000000, float:2.0)
            r5 = 1119092736(0x42b40000, float:90.0)
            if (r0 == 0) goto L_0x00b6
            com.app.office.common.autoshape.ExtendPath r6 = new com.app.office.common.autoshape.ExtendPath
            r6.<init>()
            android.graphics.Path r7 = new android.graphics.Path
            r7.<init>()
            android.graphics.RectF r8 = rectF
            int r9 = r15.left
            int r9 = r9 * 2
            int r10 = r15.right
            int r9 = r9 - r10
            float r9 = (float) r9
            int r10 = r15.top
            float r10 = (float) r10
            int r11 = r15.right
            float r11 = (float) r11
            int r12 = r15.top
            float r12 = (float) r12
            float r13 = r1 * r4
            float r12 = r12 + r13
            r8.set(r9, r10, r11, r12)
            android.graphics.RectF r8 = rectF
            r7.arcTo(r8, r3, r5)
            android.graphics.RectF r8 = rectF
            int r9 = r15.left
            int r9 = r9 * 2
            int r10 = r15.right
            int r9 = r9 - r10
            float r9 = (float) r9
            int r10 = r15.bottom
            float r10 = (float) r10
            float r10 = r10 - r13
            int r11 = r15.right
            float r11 = (float) r11
            int r12 = r15.bottom
            float r12 = (float) r12
            r8.set(r9, r10, r11, r12)
            android.graphics.RectF r8 = rectF
            r7.arcTo(r8, r2, r5)
            r7.close()
            r6.setPath(r7)
            r6.setBackgroundAndFill(r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = paths
            r0.add(r6)
        L_0x00b6:
            boolean r0 = r14.hasLine()
            if (r0 == 0) goto L_0x010f
            com.app.office.common.autoshape.ExtendPath r0 = new com.app.office.common.autoshape.ExtendPath
            r0.<init>()
            android.graphics.Path r6 = new android.graphics.Path
            r6.<init>()
            android.graphics.RectF r7 = rectF
            int r8 = r15.left
            int r8 = r8 * 2
            int r9 = r15.right
            int r8 = r8 - r9
            float r8 = (float) r8
            int r9 = r15.top
            float r9 = (float) r9
            int r10 = r15.right
            float r10 = (float) r10
            int r11 = r15.top
            float r11 = (float) r11
            float r1 = r1 * r4
            float r11 = r11 + r1
            r7.set(r8, r9, r10, r11)
            android.graphics.RectF r4 = rectF
            r6.arcTo(r4, r3, r5)
            android.graphics.RectF r3 = rectF
            int r4 = r15.left
            int r4 = r4 * 2
            int r7 = r15.right
            int r4 = r4 - r7
            float r4 = (float) r4
            int r7 = r15.bottom
            float r7 = (float) r7
            float r7 = r7 - r1
            int r1 = r15.right
            float r1 = (float) r1
            int r15 = r15.bottom
            float r15 = (float) r15
            r3.set(r4, r7, r1, r15)
            android.graphics.RectF r15 = rectF
            r6.arcTo(r15, r2, r5)
            r0.setPath(r6)
            com.app.office.common.borders.Line r14 = r14.getLine()
            r0.setLine((com.app.office.common.borders.Line) r14)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r14 = paths
            r14.add(r0)
        L_0x010f:
            java.util.List<com.app.office.common.autoshape.ExtendPath> r14 = paths
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.baseshape.BaseShapePathBuilder.getRightBracketPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    private static List<ExtendPath> getBracePairPath(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.08f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!(adjustData == null || adjustData.length < 1 || adjustData[0] == null)) {
            min = ((float) Math.min(rect.width(), rect.height())) * adjustData[0].floatValue();
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        if (backgroundAndFill != null) {
            ExtendPath extendPath = new ExtendPath();
            Path path2 = new Path();
            float f = min * 3.0f;
            float f2 = min * 2.0f;
            rectF.set(((float) rect2.right) - f, (float) rect2.top, ((float) rect2.right) - min, ((float) rect2.top) + f2);
            path2.arcTo(rectF, 270.0f, 90.0f);
            rectF.set(((float) rect2.right) - min, rect.exactCenterY() - f2, ((float) rect2.right) + min, rect.exactCenterY());
            path2.arcTo(rectF, 180.0f, -90.0f);
            rectF.set(((float) rect2.right) - min, rect.exactCenterY(), ((float) rect2.right) + min, rect.exactCenterY() + f2);
            path2.arcTo(rectF, 270.0f, -90.0f);
            rectF.set(((float) rect2.right) - f, ((float) rect2.bottom) - f2, ((float) rect2.right) - min, (float) rect2.bottom);
            path2.arcTo(rectF, 0.0f, 90.0f);
            rectF.set(((float) rect2.left) + min, ((float) rect2.bottom) - f2, ((float) rect2.left) + f, (float) rect2.bottom);
            path2.arcTo(rectF, 90.0f, 90.0f);
            rectF.set(((float) rect2.left) - min, rect.exactCenterY(), ((float) rect2.left) + min, rect.exactCenterY() + f2);
            path2.arcTo(rectF, 0.0f, -90.0f);
            rectF.set(((float) rect2.left) - min, rect.exactCenterY() - f2, ((float) rect2.left) + min, rect.exactCenterY());
            path2.arcTo(rectF, 90.0f, -90.0f);
            rectF.set(((float) rect2.left) + min, (float) rect2.top, ((float) rect2.left) + f, ((float) rect2.top) + f2);
            path2.arcTo(rectF, 180.0f, 90.0f);
            path2.close();
            extendPath.setPath(path2);
            extendPath.setBackgroundAndFill(backgroundAndFill);
            paths.add(extendPath);
        }
        if (autoShape.hasLine()) {
            ExtendPath extendPath2 = new ExtendPath();
            Path path3 = new Path();
            float f3 = 2.0f * min;
            path3.moveTo(((float) rect2.right) - f3, (float) rect2.top);
            float f4 = 3.0f * min;
            rectF.set(((float) rect2.right) - f4, (float) rect2.top, ((float) rect2.right) - min, ((float) rect2.top) + f3);
            path3.arcTo(rectF, 270.0f, 90.0f);
            rectF.set(((float) rect2.right) - min, rect.exactCenterY() - f3, ((float) rect2.right) + min, rect.exactCenterY());
            path3.arcTo(rectF, 180.0f, -90.0f);
            rectF.set(((float) rect2.right) - min, rect.exactCenterY(), ((float) rect2.right) + min, rect.exactCenterY() + f3);
            path3.arcTo(rectF, 270.0f, -90.0f);
            rectF.set(((float) rect2.right) - f4, ((float) rect2.bottom) - f3, ((float) rect2.right) - min, (float) rect2.bottom);
            path3.arcTo(rectF, 0.0f, 90.0f);
            path3.moveTo(((float) rect2.left) + f3, (float) rect2.bottom);
            rectF.set(((float) rect2.left) + min, ((float) rect2.bottom) - f3, ((float) rect2.left) + f4, (float) rect2.bottom);
            path3.arcTo(rectF, 90.0f, 90.0f);
            rectF.set(((float) rect2.left) - min, rect.exactCenterY(), ((float) rect2.left) + min, rect.exactCenterY() + f3);
            path3.arcTo(rectF, 0.0f, -90.0f);
            rectF.set(((float) rect2.left) - min, rect.exactCenterY() - f3, ((float) rect2.left) + min, rect.exactCenterY());
            path3.arcTo(rectF, 90.0f, -90.0f);
            rectF.set(((float) rect2.left) + min, (float) rect2.top, ((float) rect2.left) + f4, ((float) rect2.top) + f3);
            path3.arcTo(rectF, 180.0f, 90.0f);
            extendPath2.setPath(path3);
            extendPath2.setLine(autoShape.getLine());
            paths.add(extendPath2);
        }
        return paths;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x014b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getLeftBracePath(com.app.office.common.shape.AutoShape r16, android.graphics.Rect r17) {
        /*
            r0 = r17
            int r1 = r17.height()
            float r1 = (float) r1
            r2 = 1056964608(0x3f000000, float:0.5)
            float r1 = r1 * r2
            java.lang.Float[] r2 = r16.getAdjustData()
            boolean r3 = r16.isAutoShape07()
            r4 = 2
            r5 = 1034594539(0x3daaa8eb, float:0.08333)
            r6 = 1
            r7 = 0
            if (r3 == 0) goto L_0x0058
            int r3 = r17.width()
            int r8 = r17.height()
            int r3 = java.lang.Math.min(r3, r8)
            float r3 = (float) r3
            float r3 = r3 * r5
            if (r2 == 0) goto L_0x0086
            int r5 = r2.length
            if (r5 < r4) goto L_0x0086
            r4 = r2[r7]
            if (r4 == 0) goto L_0x0048
            int r3 = r17.width()
            int r4 = r17.height()
            int r3 = java.lang.Math.min(r3, r4)
            float r3 = (float) r3
            r4 = r2[r7]
            float r4 = r4.floatValue()
            float r3 = r3 * r4
        L_0x0048:
            r4 = r2[r6]
            if (r4 == 0) goto L_0x0086
            int r1 = r17.height()
            float r1 = (float) r1
            r2 = r2[r6]
            float r2 = r2.floatValue()
            goto L_0x0084
        L_0x0058:
            int r3 = r17.height()
            float r3 = (float) r3
            float r3 = r3 * r5
            if (r2 == 0) goto L_0x0086
            int r5 = r2.length
            if (r5 < r4) goto L_0x0086
            r4 = r2[r7]
            if (r4 == 0) goto L_0x0075
            int r3 = r17.height()
            float r3 = (float) r3
            r4 = r2[r7]
            float r4 = r4.floatValue()
            float r3 = r3 * r4
        L_0x0075:
            r4 = r2[r6]
            if (r4 == 0) goto L_0x0086
            int r1 = r17.height()
            float r1 = (float) r1
            r2 = r2[r6]
            float r2 = r2.floatValue()
        L_0x0084:
            float r1 = r1 * r2
        L_0x0086:
            int r2 = r0.top
            float r2 = (float) r2
            float r2 = r2 + r1
            r4 = 1073741824(0x40000000, float:2.0)
            float r5 = r3 * r4
            float r2 = r2 + r5
            int r5 = r0.bottom
            float r5 = (float) r5
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x009e
            int r2 = r17.height()
            float r2 = (float) r2
            float r2 = r2 - r1
            float r3 = r2 / r4
        L_0x009e:
            com.app.office.common.bg.BackgroundAndFill r2 = r16.getBackgroundAndFill()
            r6 = 0
            r7 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r8 = 1119092736(0x42b40000, float:90.0)
            if (r2 == 0) goto L_0x0145
            com.app.office.common.autoshape.ExtendPath r9 = new com.app.office.common.autoshape.ExtendPath
            r9.<init>()
            android.graphics.Path r10 = new android.graphics.Path
            r10.<init>()
            android.graphics.RectF r11 = rectF
            float r12 = r17.exactCenterX()
            int r13 = r0.bottom
            float r13 = (float) r13
            float r14 = r3 * r4
            float r13 = r13 - r14
            int r15 = r0.right
            float r15 = (float) r15
            int r5 = r17.width()
            float r5 = (float) r5
            float r5 = r5 / r4
            float r15 = r15 + r5
            int r5 = r0.bottom
            float r5 = (float) r5
            r11.set(r12, r13, r15, r5)
            android.graphics.RectF r5 = rectF
            r10.arcTo(r5, r8, r8)
            android.graphics.RectF r5 = rectF
            int r11 = r0.left
            float r11 = (float) r11
            int r12 = r17.width()
            float r12 = (float) r12
            float r12 = r12 / r4
            float r11 = r11 - r12
            int r12 = r0.top
            float r12 = (float) r12
            float r12 = r12 + r1
            float r13 = r17.exactCenterX()
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r1
            float r15 = r15 + r14
            r5.set(r11, r12, r13, r15)
            android.graphics.RectF r5 = rectF
            r10.arcTo(r5, r6, r7)
            android.graphics.RectF r5 = rectF
            int r11 = r0.left
            float r11 = (float) r11
            int r12 = r17.width()
            float r12 = (float) r12
            float r12 = r12 / r4
            float r11 = r11 - r12
            int r12 = r0.top
            float r12 = (float) r12
            float r12 = r12 + r1
            float r12 = r12 - r14
            float r13 = r17.exactCenterX()
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r1
            r5.set(r11, r12, r13, r15)
            android.graphics.RectF r5 = rectF
            r10.arcTo(r5, r8, r7)
            android.graphics.RectF r5 = rectF
            float r11 = r17.exactCenterX()
            int r12 = r0.top
            float r12 = (float) r12
            int r13 = r0.right
            float r13 = (float) r13
            int r15 = r17.width()
            float r15 = (float) r15
            float r15 = r15 / r4
            float r13 = r13 + r15
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r14
            r5.set(r11, r12, r13, r15)
            android.graphics.RectF r5 = rectF
            r11 = 1127481344(0x43340000, float:180.0)
            r10.arcTo(r5, r11, r8)
            r10.close()
            r9.setPath(r10)
            r9.setBackgroundAndFill(r2)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r2 = paths
            r2.add(r9)
        L_0x0145:
            boolean r2 = r16.hasLine()
            if (r2 == 0) goto L_0x01e8
            com.app.office.common.autoshape.ExtendPath r2 = new com.app.office.common.autoshape.ExtendPath
            r2.<init>()
            android.graphics.Path r5 = new android.graphics.Path
            r5.<init>()
            android.graphics.RectF r9 = rectF
            float r10 = r17.exactCenterX()
            int r11 = r0.bottom
            float r11 = (float) r11
            float r3 = r3 * r4
            float r11 = r11 - r3
            int r12 = r0.right
            float r12 = (float) r12
            int r13 = r17.width()
            float r13 = (float) r13
            float r13 = r13 / r4
            float r12 = r12 + r13
            int r13 = r0.bottom
            float r13 = (float) r13
            r9.set(r10, r11, r12, r13)
            android.graphics.RectF r9 = rectF
            r5.arcTo(r9, r8, r8)
            android.graphics.RectF r9 = rectF
            int r10 = r0.left
            float r10 = (float) r10
            int r11 = r17.width()
            float r11 = (float) r11
            float r11 = r11 / r4
            float r10 = r10 - r11
            int r11 = r0.top
            float r11 = (float) r11
            float r11 = r11 + r1
            float r12 = r17.exactCenterX()
            int r13 = r0.top
            float r13 = (float) r13
            float r13 = r13 + r1
            float r13 = r13 + r3
            r9.set(r10, r11, r12, r13)
            android.graphics.RectF r9 = rectF
            r5.arcTo(r9, r6, r7)
            android.graphics.RectF r6 = rectF
            int r9 = r0.left
            float r9 = (float) r9
            int r10 = r17.width()
            float r10 = (float) r10
            float r10 = r10 / r4
            float r9 = r9 - r10
            int r10 = r0.top
            float r10 = (float) r10
            float r10 = r10 + r1
            float r10 = r10 - r3
            float r11 = r17.exactCenterX()
            int r12 = r0.top
            float r12 = (float) r12
            float r12 = r12 + r1
            r6.set(r9, r10, r11, r12)
            android.graphics.RectF r1 = rectF
            r5.arcTo(r1, r8, r7)
            android.graphics.RectF r1 = rectF
            float r6 = r17.exactCenterX()
            int r7 = r0.top
            float r7 = (float) r7
            int r9 = r0.right
            float r9 = (float) r9
            int r10 = r17.width()
            float r10 = (float) r10
            float r10 = r10 / r4
            float r9 = r9 + r10
            int r0 = r0.top
            float r0 = (float) r0
            float r0 = r0 + r3
            r1.set(r6, r7, r9, r0)
            android.graphics.RectF r0 = rectF
            r1 = 1127481344(0x43340000, float:180.0)
            r5.arcTo(r0, r1, r8)
            r2.setPath(r5)
            com.app.office.common.borders.Line r0 = r16.getLine()
            r2.setLine((com.app.office.common.borders.Line) r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = paths
            r0.add(r2)
        L_0x01e8:
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = paths
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.baseshape.BaseShapePathBuilder.getLeftBracePath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x015d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.app.office.common.autoshape.ExtendPath> getRightBracePath(com.app.office.common.shape.AutoShape r17, android.graphics.Rect r18) {
        /*
            r0 = r18
            int r1 = r18.height()
            float r1 = (float) r1
            r2 = 1056964608(0x3f000000, float:0.5)
            float r1 = r1 * r2
            java.lang.Float[] r2 = r17.getAdjustData()
            boolean r3 = r17.isAutoShape07()
            r4 = 2
            r5 = 1034594539(0x3daaa8eb, float:0.08333)
            r6 = 1
            r7 = 0
            if (r3 == 0) goto L_0x0058
            int r3 = r18.width()
            int r8 = r18.height()
            int r3 = java.lang.Math.min(r3, r8)
            float r3 = (float) r3
            float r3 = r3 * r5
            if (r2 == 0) goto L_0x0086
            int r5 = r2.length
            if (r5 < r4) goto L_0x0086
            r4 = r2[r7]
            if (r4 == 0) goto L_0x0048
            int r3 = r18.width()
            int r4 = r18.height()
            int r3 = java.lang.Math.min(r3, r4)
            float r3 = (float) r3
            r4 = r2[r7]
            float r4 = r4.floatValue()
            float r3 = r3 * r4
        L_0x0048:
            r4 = r2[r6]
            if (r4 == 0) goto L_0x0086
            int r1 = r18.height()
            float r1 = (float) r1
            r2 = r2[r6]
            float r2 = r2.floatValue()
            goto L_0x0084
        L_0x0058:
            int r3 = r18.height()
            float r3 = (float) r3
            float r3 = r3 * r5
            if (r2 == 0) goto L_0x0086
            int r5 = r2.length
            if (r5 < r4) goto L_0x0086
            r4 = r2[r7]
            if (r4 == 0) goto L_0x0075
            int r3 = r18.height()
            float r3 = (float) r3
            r4 = r2[r7]
            float r4 = r4.floatValue()
            float r3 = r3 * r4
        L_0x0075:
            r4 = r2[r6]
            if (r4 == 0) goto L_0x0086
            int r1 = r18.height()
            float r1 = (float) r1
            r2 = r2[r6]
            float r2 = r2.floatValue()
        L_0x0084:
            float r1 = r1 * r2
        L_0x0086:
            int r2 = r0.top
            float r2 = (float) r2
            float r2 = r2 + r1
            r4 = 1073741824(0x40000000, float:2.0)
            float r5 = r3 * r4
            float r2 = r2 + r5
            int r5 = r0.bottom
            float r5 = (float) r5
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x009e
            int r2 = r18.height()
            float r2 = (float) r2
            float r2 = r2 - r1
            float r3 = r2 / r4
        L_0x009e:
            com.app.office.common.bg.BackgroundAndFill r2 = r17.getBackgroundAndFill()
            r6 = 1127481344(0x43340000, float:180.0)
            r7 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r8 = 1119092736(0x42b40000, float:90.0)
            r9 = 1132920832(0x43870000, float:270.0)
            if (r2 == 0) goto L_0x0157
            com.app.office.common.autoshape.ExtendPath r10 = new com.app.office.common.autoshape.ExtendPath
            r10.<init>()
            android.graphics.Path r11 = new android.graphics.Path
            r11.<init>()
            android.graphics.RectF r12 = rectF
            int r13 = r0.left
            float r13 = (float) r13
            int r14 = r18.width()
            float r14 = (float) r14
            float r14 = r14 / r4
            float r13 = r13 - r14
            int r14 = r0.top
            float r14 = (float) r14
            int r15 = r0.right
            int r5 = r0.left
            int r15 = r15 + r5
            float r5 = (float) r15
            float r5 = r5 / r4
            int r15 = r0.top
            float r15 = (float) r15
            float r16 = r3 * r4
            float r15 = r15 + r16
            r12.set(r13, r14, r5, r15)
            android.graphics.RectF r5 = rectF
            r11.arcTo(r5, r9, r8)
            android.graphics.RectF r5 = rectF
            int r12 = r0.right
            int r13 = r0.left
            int r12 = r12 + r13
            float r12 = (float) r12
            float r12 = r12 / r4
            int r13 = r0.top
            float r13 = (float) r13
            float r13 = r13 + r1
            float r13 = r13 - r16
            int r14 = r0.right
            float r14 = (float) r14
            int r15 = r18.width()
            float r15 = (float) r15
            float r15 = r15 / r4
            float r14 = r14 + r15
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r1
            r5.set(r12, r13, r14, r15)
            android.graphics.RectF r5 = rectF
            r11.arcTo(r5, r6, r7)
            android.graphics.RectF r5 = rectF
            int r12 = r0.right
            int r13 = r0.left
            int r12 = r12 + r13
            float r12 = (float) r12
            float r12 = r12 / r4
            int r13 = r0.top
            float r13 = (float) r13
            float r13 = r13 + r1
            int r14 = r0.right
            float r14 = (float) r14
            int r15 = r18.width()
            float r15 = (float) r15
            float r15 = r15 / r4
            float r14 = r14 + r15
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r1
            float r15 = r15 + r16
            r5.set(r12, r13, r14, r15)
            android.graphics.RectF r5 = rectF
            r11.arcTo(r5, r9, r7)
            android.graphics.RectF r5 = rectF
            int r12 = r0.left
            float r12 = (float) r12
            int r13 = r18.width()
            float r13 = (float) r13
            float r13 = r13 / r4
            float r12 = r12 - r13
            int r13 = r0.bottom
            float r13 = (float) r13
            float r13 = r13 - r16
            int r14 = r0.right
            int r15 = r0.left
            int r14 = r14 + r15
            float r14 = (float) r14
            float r14 = r14 / r4
            int r15 = r0.bottom
            float r15 = (float) r15
            r5.set(r12, r13, r14, r15)
            android.graphics.RectF r5 = rectF
            r12 = 0
            r11.arcTo(r5, r12, r8)
            r11.close()
            r10.setPath(r11)
            r10.setBackgroundAndFill(r2)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r2 = paths
            r2.add(r10)
        L_0x0157:
            boolean r2 = r17.hasLine()
            if (r2 == 0) goto L_0x0205
            com.app.office.common.autoshape.ExtendPath r2 = new com.app.office.common.autoshape.ExtendPath
            r2.<init>()
            android.graphics.Path r5 = new android.graphics.Path
            r5.<init>()
            android.graphics.RectF r10 = rectF
            int r11 = r0.left
            float r11 = (float) r11
            int r12 = r18.width()
            float r12 = (float) r12
            float r12 = r12 / r4
            float r11 = r11 - r12
            int r12 = r0.top
            float r12 = (float) r12
            int r13 = r0.right
            int r14 = r0.left
            int r13 = r13 + r14
            float r13 = (float) r13
            float r13 = r13 / r4
            int r14 = r0.top
            float r14 = (float) r14
            float r3 = r3 * r4
            float r14 = r14 + r3
            r10.set(r11, r12, r13, r14)
            android.graphics.RectF r10 = rectF
            r5.arcTo(r10, r9, r8)
            android.graphics.RectF r10 = rectF
            int r11 = r0.right
            int r12 = r0.left
            int r11 = r11 + r12
            float r11 = (float) r11
            float r11 = r11 / r4
            int r12 = r0.top
            float r12 = (float) r12
            float r12 = r12 + r1
            float r12 = r12 - r3
            int r13 = r0.right
            float r13 = (float) r13
            int r14 = r18.width()
            float r14 = (float) r14
            float r14 = r14 / r4
            float r13 = r13 + r14
            int r14 = r0.top
            float r14 = (float) r14
            float r14 = r14 + r1
            r10.set(r11, r12, r13, r14)
            android.graphics.RectF r10 = rectF
            r5.arcTo(r10, r6, r7)
            android.graphics.RectF r6 = rectF
            int r10 = r0.right
            int r11 = r0.left
            int r10 = r10 + r11
            float r10 = (float) r10
            float r10 = r10 / r4
            int r11 = r0.top
            float r11 = (float) r11
            float r11 = r11 + r1
            int r12 = r0.right
            float r12 = (float) r12
            int r13 = r18.width()
            float r13 = (float) r13
            float r13 = r13 / r4
            float r12 = r12 + r13
            int r13 = r0.top
            float r13 = (float) r13
            float r13 = r13 + r1
            float r13 = r13 + r3
            r6.set(r10, r11, r12, r13)
            android.graphics.RectF r1 = rectF
            r5.arcTo(r1, r9, r7)
            android.graphics.RectF r1 = rectF
            int r6 = r0.left
            float r6 = (float) r6
            int r7 = r18.width()
            float r7 = (float) r7
            float r7 = r7 / r4
            float r6 = r6 - r7
            int r7 = r0.bottom
            float r7 = (float) r7
            float r7 = r7 - r3
            int r3 = r0.right
            int r9 = r0.left
            int r3 = r3 + r9
            float r3 = (float) r3
            float r3 = r3 / r4
            int r0 = r0.bottom
            float r0 = (float) r0
            r1.set(r6, r7, r3, r0)
            android.graphics.RectF r0 = rectF
            r1 = 0
            r5.arcTo(r0, r1, r8)
            r2.setPath(r5)
            com.app.office.common.borders.Line r0 = r17.getLine()
            r2.setLine((com.app.office.common.borders.Line) r0)
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = paths
            r0.add(r2)
        L_0x0205:
            java.util.List<com.app.office.common.autoshape.ExtendPath> r0 = paths
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.baseshape.BaseShapePathBuilder.getRightBracePath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }
}
