package com.app.office.common.autoshape.pathbuilder.wedgecallout;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.autoshape.ExtendPath;
import com.app.office.common.bg.BackgroundAndFill;
import com.app.office.common.shape.AutoShape;
import java.util.ArrayList;
import java.util.List;

public class WedgeCalloutDrawing {
    private static final WedgeCalloutDrawing kit = new WedgeCalloutDrawing();
    private static Path path = new Path();
    private static List<ExtendPath> paths = new ArrayList();
    private static RectF rectF = new RectF();

    public static WedgeCalloutDrawing instance() {
        return kit;
    }

    public Object getWedgeCalloutPath(AutoShape autoShape, Rect rect) {
        paths.clear();
        path.reset();
        int shapeType = autoShape.getShapeType();
        if (shapeType == 106) {
            return getCloudCalloutPath(autoShape, rect);
        }
        switch (shapeType) {
            case 41:
                if (autoShape.isAutoShape07()) {
                    return getCallout2(autoShape, rect);
                }
                return get03Callout2(autoShape, rect);
            case 42:
                if (autoShape.isAutoShape07()) {
                    return getCallout3(autoShape, rect);
                }
                return get03Callout3(autoShape, rect);
            case 43:
                return get03Callout4(autoShape, rect);
            case 44:
                if (autoShape.isAutoShape07()) {
                    return getAccentCallout2Path(autoShape, rect);
                }
                return get03AccentCallout2Path(autoShape, rect);
            case 45:
                if (autoShape.isAutoShape07()) {
                    return getAccentCallout3Path(autoShape, rect);
                }
                return get03AccentCallout3(autoShape, rect);
            case 46:
                return get03AccentCallout4(autoShape, rect);
            case 47:
                if (autoShape.isAutoShape07()) {
                    return getBorderCallout2Path(autoShape, rect);
                }
                return get03BorderCallout2Path(autoShape, rect);
            case 48:
                if (autoShape.isAutoShape07()) {
                    return getBorderCallout3Path(autoShape, rect);
                }
                return get03BorderCallout3Path(autoShape, rect);
            case 49:
                return get03BorderCallout4Path(autoShape, rect);
            case 50:
                if (autoShape.isAutoShape07()) {
                    return getAccentBorderCallout2(autoShape, rect);
                }
                return get03AccentBorderCallout2(autoShape, rect);
            case 51:
                if (autoShape.isAutoShape07()) {
                    return getAccentBorderCallout3(autoShape, rect);
                }
                return get03AccentBorderCallout3(autoShape, rect);
            case 52:
                return get03AccentBorderCallout4(autoShape, rect);
            default:
                switch (shapeType) {
                    case 61:
                        return getWedgeRectCalloutPath(autoShape, rect);
                    case 62:
                        return getWedgeRoundRectCalloutPath(autoShape, rect);
                    case 63:
                        return getWedgeEllipseCalloutPath(autoShape, rect);
                    default:
                        switch (shapeType) {
                            case 178:
                                if (autoShape.isAutoShape07()) {
                                    return getCallout1(autoShape, rect);
                                }
                                return get03AccentCallout1Path(autoShape, rect);
                            case 179:
                                if (autoShape.isAutoShape07()) {
                                    return getAccentCallout1Path(autoShape, rect);
                                }
                                return get03AccentCallout1Path(autoShape, rect);
                            case 180:
                                if (autoShape.isAutoShape07()) {
                                    return getBorderCallout1Path(autoShape, rect);
                                }
                                return get03BorderCallout2Path(autoShape, rect);
                            case 181:
                                if (autoShape.isAutoShape07()) {
                                    return getAccentBorderCallout1(autoShape, rect);
                                }
                                return get03BorderCallout2Path(autoShape, rect);
                            default:
                                return null;
                        }
                }
        }
    }

    private static Path getWedgeRectCalloutPath(AutoShape autoShape, Rect rect) {
        float f = ((float) (-rect.width())) * 0.2f;
        float height = ((float) rect.height()) * 0.6f;
        float width = ((float) rect.width()) / 12.0f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!autoShape.isAutoShape07()) {
            f = 0.433f * ((float) (-rect.width()));
            height = 0.7f * ((float) rect.height());
            if (adjustData != null && adjustData.length >= 2) {
                if (adjustData[0] != null) {
                    f = (((float) rect.width()) * adjustData[0].floatValue()) - ((float) (rect.width() / 2));
                }
                if (adjustData[1] != null) {
                    height = (((float) rect.height()) * adjustData[1].floatValue()) - ((float) (rect.height() / 2));
                }
            }
        } else if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                f = adjustData[0].floatValue() * ((float) rect.width());
            }
            if (adjustData[1] != null) {
                height = adjustData[1].floatValue() * ((float) rect.height());
            }
        }
        if (Math.abs(height / f) < ((float) rect.height()) / ((float) rect.width())) {
            float height2 = ((float) rect.height()) / 12.0f;
            if (f >= 0.0f) {
                path.moveTo((float) rect.left, (float) rect.top);
                path.lineTo((float) rect.right, (float) rect.top);
                if (height >= 0.0f) {
                    path.lineTo((float) rect.right, rect.exactCenterY() + height2);
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.right, ((float) rect.bottom) - (height2 * 2.0f));
                } else {
                    path.lineTo((float) rect.right, ((float) rect.top) + (2.0f * height2));
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.right, rect.exactCenterY() - height2);
                }
                path.lineTo((float) rect.right, (float) rect.bottom);
                path.lineTo((float) rect.left, (float) rect.bottom);
            } else {
                path.moveTo((float) rect.left, (float) rect.top);
                path.lineTo((float) rect.right, (float) rect.top);
                path.lineTo((float) rect.right, (float) rect.bottom);
                path.lineTo((float) rect.left, (float) rect.bottom);
                if (height >= 0.0f) {
                    path.lineTo((float) rect.left, ((float) rect.bottom) - (2.0f * height2));
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.left, rect.exactCenterY() + height2);
                } else {
                    path.lineTo((float) rect.left, rect.exactCenterY() - height2);
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.left, ((float) rect.top) + (height2 * 2.0f));
                }
            }
        } else if (height >= 0.0f) {
            path.moveTo((float) rect.left, (float) rect.top);
            path.lineTo((float) rect.right, (float) rect.top);
            path.lineTo((float) rect.right, (float) rect.bottom);
            if (f >= 0.0f) {
                path.lineTo(((float) rect.right) - (2.0f * width), (float) rect.bottom);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(rect.exactCenterX() + width, (float) rect.bottom);
            } else {
                path.lineTo(rect.exactCenterX() - width, (float) rect.bottom);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(((float) rect.left) + (width * 2.0f), (float) rect.bottom);
            }
            path.lineTo((float) rect.left, (float) rect.bottom);
        } else {
            path.moveTo((float) rect.left, (float) rect.top);
            if (f >= 0.0f) {
                path.lineTo(rect.exactCenterX() + width, (float) rect.top);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(((float) rect.right) - (width * 2.0f), (float) rect.top);
            } else {
                path.lineTo(((float) rect.left) + (2.0f * width), (float) rect.top);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(rect.exactCenterX() - width, (float) rect.top);
            }
            path.lineTo((float) rect.right, (float) rect.top);
            path.lineTo((float) rect.right, (float) rect.bottom);
            path.lineTo((float) rect.left, (float) rect.bottom);
        }
        path.close();
        return path;
    }

    private static Path getWedgeRoundRectCalloutPath(AutoShape autoShape, Rect rect) {
        float f = ((float) (-rect.width())) * 0.2f;
        float height = ((float) rect.height()) * 0.6f;
        float width = ((float) rect.width()) / 12.0f;
        float min = ((float) Math.min(rect.width(), rect.height())) * 0.16667f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!autoShape.isAutoShape07()) {
            f = 0.433f * ((float) (-rect.width()));
            height = 0.7f * ((float) rect.height());
            if (adjustData != null && adjustData.length >= 2) {
                if (adjustData[0] != null) {
                    f = (((float) rect.width()) * adjustData[0].floatValue()) - ((float) (rect.width() / 2));
                }
                if (adjustData[1] != null) {
                    height = (((float) rect.height()) * adjustData[1].floatValue()) - ((float) (rect.height() / 2));
                }
            }
        } else if (adjustData != null && adjustData.length >= 3) {
            if (adjustData[0] != null) {
                f = adjustData[0].floatValue() * ((float) rect.width());
            }
            if (adjustData[1] != null) {
                height = adjustData[1].floatValue() * ((float) rect.height());
            }
            if (adjustData[2] != null) {
                min = adjustData[2].floatValue() * ((float) Math.min(rect.width(), rect.height()));
            }
        }
        if (Math.abs(height / f) < ((float) rect.height()) / ((float) rect.width())) {
            float height2 = ((float) rect.height()) / 12.0f;
            if (f >= 0.0f) {
                float f2 = min * 2.0f;
                rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + f2, ((float) rect.top) + f2);
                path.arcTo(rectF, 180.0f, 90.0f);
                rectF.set(((float) rect.right) - f2, (float) rect.top, (float) rect.right, ((float) rect.top) + f2);
                path.arcTo(rectF, 270.0f, 90.0f);
                if (height >= 0.0f) {
                    path.lineTo((float) rect.right, rect.exactCenterY() + height2);
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.right, ((float) rect.bottom) - (height2 * 2.0f));
                } else {
                    path.lineTo((float) rect.right, ((float) rect.top) + (2.0f * height2));
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.right, rect.exactCenterY() - height2);
                }
                rectF.set(((float) rect.right) - f2, ((float) rect.bottom) - f2, (float) rect.right, (float) rect.bottom);
                path.arcTo(rectF, 0.0f, 90.0f);
                rectF.set((float) rect.left, ((float) rect.bottom) - f2, ((float) rect.left) + f2, (float) rect.bottom);
                path.arcTo(rectF, 90.0f, 90.0f);
            } else {
                float f3 = min * 2.0f;
                rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + f3, ((float) rect.top) + f3);
                path.arcTo(rectF, 180.0f, 90.0f);
                rectF.set(((float) rect.right) - f3, (float) rect.top, (float) rect.right, ((float) rect.top) + f3);
                path.arcTo(rectF, 270.0f, 90.0f);
                rectF.set(((float) rect.right) - f3, ((float) rect.bottom) - f3, (float) rect.right, (float) rect.bottom);
                path.arcTo(rectF, 0.0f, 90.0f);
                rectF.set((float) rect.left, ((float) rect.bottom) - f3, ((float) rect.left) + f3, (float) rect.bottom);
                path.arcTo(rectF, 90.0f, 90.0f);
                if (height >= 0.0f) {
                    path.lineTo((float) rect.left, ((float) rect.bottom) - (2.0f * height2));
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.left, rect.exactCenterY() + height2);
                } else {
                    path.lineTo((float) rect.left, rect.exactCenterY() - height2);
                    path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                    path.lineTo((float) rect.left, ((float) rect.top) + (height2 * 2.0f));
                }
            }
        } else if (height >= 0.0f) {
            float f4 = min * 2.0f;
            rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + f4, ((float) rect.top) + f4);
            path.arcTo(rectF, 180.0f, 90.0f);
            rectF.set(((float) rect.right) - f4, (float) rect.top, (float) rect.right, ((float) rect.top) + f4);
            path.arcTo(rectF, 270.0f, 90.0f);
            rectF.set(((float) rect.right) - f4, ((float) rect.bottom) - f4, (float) rect.right, (float) rect.bottom);
            path.arcTo(rectF, 0.0f, 90.0f);
            if (f >= 0.0f) {
                path.lineTo(((float) rect.right) - (2.0f * width), (float) rect.bottom);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(rect.exactCenterX() + width, (float) rect.bottom);
            } else {
                path.lineTo(rect.exactCenterX() - width, (float) rect.bottom);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(((float) rect.left) + (width * 2.0f), (float) rect.bottom);
            }
            rectF.set((float) rect.left, ((float) rect.bottom) - f4, ((float) rect.left) + f4, (float) rect.bottom);
            path.arcTo(rectF, 90.0f, 90.0f);
        } else {
            float f5 = min * 2.0f;
            rectF.set((float) rect.left, (float) rect.top, ((float) rect.left) + f5, ((float) rect.top) + f5);
            path.arcTo(rectF, 180.0f, 90.0f);
            if (f >= 0.0f) {
                path.lineTo(rect.exactCenterX() + width, (float) rect.top);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(((float) rect.right) - (width * 2.0f), (float) rect.top);
            } else {
                path.lineTo(((float) rect.left) + (2.0f * width), (float) rect.top);
                path.lineTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
                path.lineTo(rect.exactCenterX() - width, (float) rect.top);
            }
            rectF.set(((float) rect.right) - f5, (float) rect.top, (float) rect.right, ((float) rect.top) + f5);
            path.arcTo(rectF, 270.0f, 90.0f);
            rectF.set(((float) rect.right) - f5, ((float) rect.bottom) - f5, (float) rect.right, (float) rect.bottom);
            path.arcTo(rectF, 0.0f, 90.0f);
            rectF.set((float) rect.left, ((float) rect.bottom) - f5, ((float) rect.left) + f5, (float) rect.bottom);
            path.arcTo(rectF, 90.0f, 90.0f);
        }
        path.close();
        return path;
    }

    private static Path getWedgeEllipseCalloutPath(AutoShape autoShape, Rect rect) {
        float f = ((float) (-rect.width())) * 0.2f;
        float height = ((float) rect.height()) * 0.6f;
        Float[] adjustData = autoShape.getAdjustData();
        if (!autoShape.isAutoShape07()) {
            f = 0.433f * ((float) (-rect.width()));
            height = 0.7f * ((float) rect.height());
            if (adjustData != null && adjustData.length >= 2) {
                if (adjustData[0] != null) {
                    f = (((float) rect.width()) * adjustData[0].floatValue()) - ((float) (rect.width() / 2));
                }
                if (adjustData[1] != null) {
                    height = (((float) rect.height()) * adjustData[1].floatValue()) - ((float) (rect.height() / 2));
                }
            }
        } else if (adjustData != null && adjustData.length >= 2) {
            if (adjustData[0] != null) {
                f = adjustData[0].floatValue() * ((float) rect.width());
            }
            if (adjustData[1] != null) {
                height = adjustData[1].floatValue() * ((float) rect.height());
            }
        }
        float degrees = ((float) Math.toDegrees(Math.atan2((double) rect.width(), (double) rect.height()))) / 2.0f;
        float degrees2 = (float) Math.toDegrees(Math.atan2((double) Math.abs(height), (double) Math.abs(f)));
        path.moveTo(rect.exactCenterX() + f, rect.exactCenterY() + height);
        rectF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        if (height >= 0.0f) {
            path.arcTo(rectF, f >= 0.0f ? degrees2 + (degrees / 2.0f) : (180.0f - degrees2) + (degrees / 2.0f), 360.0f - degrees);
        } else {
            path.arcTo(rectF, f >= 0.0f ? (360.0f - degrees2) - (degrees / 2.0f) : (degrees2 + 180.0f) - (degrees / 2.0f), degrees - 0.012451172f);
        }
        path.close();
        return path;
    }

    private static Path getCloudCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        Rect rect2 = rect;
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
        Matrix matrix = new Matrix();
        matrix.postScale(((float) rect.width()) / 468.0f, ((float) rect.height()) / 468.0f);
        path.transform(matrix);
        path.offset((float) rect2.left, (float) rect2.top);
        Float[] adjustData = autoShape.getAdjustData();
        int i2 = 0;
        if (autoShape.isAutoShape07()) {
            if (adjustData == null || adjustData.length < 2) {
                i = Math.round(((float) rect.width()) * -0.2f);
                i2 = Math.round(((float) rect.height()) * 0.6f);
            } else {
                i = adjustData[0] != null ? Math.round(((float) rect.width()) * adjustData[0].floatValue()) : 0;
                if (adjustData[1] != null) {
                    i2 = Math.round(((float) rect.height()) * adjustData[1].floatValue());
                }
            }
        } else if (adjustData == null || adjustData.length < 2) {
            i = Math.round(((float) rect.width()) * -0.433f);
            i2 = Math.round(((float) rect.height()) * 0.7f);
        } else {
            i = adjustData[0] != null ? Math.round((((float) rect.width()) * adjustData[0].floatValue()) - ((float) (rect.width() / 2))) : 0;
            if (adjustData[1] != null) {
                i2 = Math.round((((float) rect.height()) * adjustData[1].floatValue()) - ((float) (rect.height() / 2)));
            }
        }
        double angle = getAngle((double) i, (double) i2);
        int width = rect.width() / 2;
        int height = rect.height() / 2;
        double d = (3.141592653589793d * angle) / 180.0d;
        float sqrt = (float) (((double) (width * height)) / Math.sqrt(Math.pow((double) height, 2.0d) + Math.pow(((double) width) * Math.tan(d), 2.0d)));
        if (angle > 90.0d && angle < 270.0d) {
            sqrt = -sqrt;
        }
        float centerX = (float) (rect.centerX() + i);
        float centerY = (float) (rect.centerY() + i2);
        float centerX2 = ((float) rect.centerX()) + sqrt;
        float centerY2 = ((float) rect.centerY()) + ((float) (((double) sqrt) * Math.tan(d)));
        float min = ((float) Math.min(rect.width(), rect.height())) / 468.0f;
        path.addCircle(centerX, centerY, 16.0f * min, Path.Direction.CW);
        float f = centerX - centerX2;
        float f2 = centerY - centerY2;
        path.addCircle((f * 0.7f) + centerX2, (0.7f * f2) + centerY2, 24.0f * min, Path.Direction.CW);
        path.addCircle(centerX2 + (f * 0.3f), centerY2 + (f2 * 0.3f), min * 40.0f, Path.Direction.CW);
        return path;
    }

    private static double getAngle(double d, double d2) {
        double acos = (Math.acos(d / Math.sqrt((d * d) + (d2 * d2))) * 180.0d) / 3.141592653589793d;
        return d2 < 0.0d ? 360.0d - acos : acos;
    }

    private static Path getBorderCallout1Path(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 1.125f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.38333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 4) {
            if (adjustData[0] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        path.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        path.moveTo(width, height);
        path.lineTo(width2, height2);
        return path;
    }

    private static List<ExtendPath> getBorderCallout2Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.125f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.46667f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 6) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getBorderCallout3Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.0f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 1.12963f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 8) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
            if (adjustData[6] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[6].floatValue());
            }
            if (adjustData[7] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        float f = height4;
        Path path3 = path2;
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path3);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path4 = new Path();
        path4.moveTo(width, height);
        path4.lineTo(width2, height2);
        path4.lineTo(width3, height3);
        path4.lineTo(width4, f);
        extendPath2.setPath(path4);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getAccentCallout1Path(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 1.125f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.38333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 4) {
            if (adjustData[0] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, (float) rect.top);
        path3.lineTo(width, (float) rect.bottom);
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getAccentCallout2Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.125f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.46667f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 6) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setPath(path2);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, (float) rect2.top);
        path3.lineTo(width, (float) rect2.bottom);
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getAccentCallout3Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.0f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 1.12963f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 8) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
            if (adjustData[6] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[6].floatValue());
            }
            if (adjustData[7] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        Path path3 = path2;
        float f = height4;
        Path path4 = path2;
        path3.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setPath(path4);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path5 = new Path();
        path5.moveTo(width, (float) rect2.top);
        path5.lineTo(width, (float) rect2.bottom);
        path5.moveTo(width, height);
        path5.lineTo(width2, height2);
        path5.lineTo(width3, height3);
        path5.lineTo(width4, f);
        extendPath2.setPath(path5);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getCallout1(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 1.125f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.38333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 4) {
            if (adjustData[0] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        extendPath.setPath(path2);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getCallout2(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.125f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.46667f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 6) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setPath(path2);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getCallout3(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.0f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 1.12963f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 8) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
            if (adjustData[6] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[6].floatValue());
            }
            if (adjustData[7] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        float f = height4;
        Path path3 = path2;
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setPath(path3);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path4 = new Path();
        path4.moveTo(width, height);
        path4.lineTo(width2, height2);
        path4.lineTo(width3, height3);
        path4.lineTo(width4, f);
        extendPath2.setPath(path4);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static Path getAccentBorderCallout1(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 1.125f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.38333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 4) {
            if (adjustData[0] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        path.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        path.moveTo(width, (float) rect.top);
        path.lineTo(width, (float) rect.bottom);
        path.moveTo(width, height);
        path.lineTo(width2, height2);
        return path;
    }

    private static List<ExtendPath> getAccentBorderCallout2(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.125f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.46667f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 6) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, (float) rect2.top);
        path3.lineTo(width, (float) rect2.bottom);
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> getAccentBorderCallout3(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 1.0f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 1.12963f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 8) {
            if (adjustData[0] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[3].floatValue());
            }
            if (adjustData[4] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[4].floatValue());
            }
            if (adjustData[5] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[5].floatValue());
            }
            if (adjustData[6] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[6].floatValue());
            }
            if (adjustData[7] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        Path path3 = path2;
        float f = height4;
        Path path4 = path2;
        path3.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path4);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path5 = new Path();
        path5.moveTo(width, (float) rect2.top);
        path5.lineTo(width, (float) rect2.bottom);
        path5.moveTo(width, height);
        path5.lineTo(width2, height2);
        path5.lineTo(width3, height3);
        path5.lineTo(width4, f);
        extendPath2.setPath(path5);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03BorderCallout2Path(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03BorderCallout3Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03BorderCallout4Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
            if (adjustData.length >= 7 && adjustData[6] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[6].floatValue());
            }
            if (adjustData.length >= 8 && adjustData[7] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        float f = width4;
        Path path3 = path2;
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path3);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path4 = new Path();
        path4.moveTo(width, height);
        path4.lineTo(width2, height2);
        path4.lineTo(width3, height3);
        path4.lineTo(f, height4);
        extendPath2.setPath(path4);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03AccentCallout1Path(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.38333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 4) {
            if (adjustData[0] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData[1] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData[2] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData[3] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        extendPath2.setLine(autoShape.getLine());
        extendPath2.setPath(path3);
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03AccentCallout2Path(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width2, (float) rect.top);
        path3.lineTo(width2, (float) rect.bottom);
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03AccentCallout3(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width3, (float) rect2.top);
        path3.lineTo(width3, (float) rect2.bottom);
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03AccentCallout4(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
            if (adjustData.length >= 7 && adjustData[6] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[6].floatValue());
            }
            if (adjustData.length >= 8 && adjustData[7] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        Path path3 = path2;
        float f = height4;
        Path path4 = path2;
        path3.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path4);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path5 = new Path();
        path5.moveTo(width4, (float) rect2.top);
        path5.lineTo(width4, (float) rect2.bottom);
        path5.moveTo(width, height);
        path5.lineTo(width2, height2);
        path5.lineTo(width3, height3);
        path5.lineTo(width4, f);
        extendPath2.setPath(path5);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03Callout2(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03Callout3(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03Callout4(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
            if (adjustData.length >= 7 && adjustData[6] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[6].floatValue());
            }
            if (adjustData.length >= 8 && adjustData[7] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        float f = width4;
        Path path3 = path2;
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path3);
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path4 = new Path();
        path4.moveTo(width, height);
        path4.lineTo(width2, height2);
        path4.lineTo(width3, height3);
        path4.lineTo(f, height4);
        extendPath2.setPath(path4);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03AccentBorderCallout2(AutoShape autoShape, Rect rect) {
        float height = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width2, (float) rect.top);
        path3.lineTo(width2, (float) rect.bottom);
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03AccentBorderCallout3(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        path2.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path2);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path3 = new Path();
        path3.moveTo(width3, (float) rect2.top);
        path3.lineTo(width3, (float) rect2.bottom);
        path3.moveTo(width, height);
        path3.lineTo(width2, height2);
        path3.lineTo(width3, height3);
        extendPath2.setPath(path3);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }

    private static List<ExtendPath> get03AccentBorderCallout4(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        float height = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width = ((float) rect2.left) + (((float) rect.width()) * -0.08333f);
        float height2 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width2 = ((float) rect2.left) + (((float) rect.width()) * -0.16667f);
        float height3 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width3 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        float height4 = ((float) rect2.top) + (((float) rect.height()) * 0.1875f);
        float width4 = ((float) rect2.left) + (((float) rect.width()) * 1.08333f);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                width = ((float) rect2.left) + (((float) rect.width()) * adjustData[0].floatValue());
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                height = ((float) rect2.top) + (((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                width2 = ((float) rect2.left) + (((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length >= 4 && adjustData[3] != null) {
                height2 = ((float) rect2.top) + (((float) rect.height()) * adjustData[3].floatValue());
            }
            if (adjustData.length >= 5 && adjustData[4] != null) {
                width3 = ((float) rect2.left) + (((float) rect.width()) * adjustData[4].floatValue());
            }
            if (adjustData.length >= 6 && adjustData[5] != null) {
                height3 = ((float) rect2.top) + (((float) rect.height()) * adjustData[5].floatValue());
            }
            if (adjustData.length >= 7 && adjustData[6] != null) {
                width4 = ((float) rect2.left) + (((float) rect.width()) * adjustData[6].floatValue());
            }
            if (adjustData.length >= 8 && adjustData[7] != null) {
                height4 = ((float) rect2.top) + (((float) rect.height()) * adjustData[7].floatValue());
            }
        }
        BackgroundAndFill backgroundAndFill = autoShape.getBackgroundAndFill();
        ExtendPath extendPath = new ExtendPath();
        Path path2 = new Path();
        Path path3 = path2;
        float f = height4;
        Path path4 = path2;
        path3.addRect((float) rect2.left, (float) rect2.top, (float) rect2.right, (float) rect2.bottom, Path.Direction.CW);
        extendPath.setBackgroundAndFill(backgroundAndFill);
        extendPath.setPath(path4);
        extendPath.setLine(autoShape.getLine());
        paths.add(extendPath);
        ExtendPath extendPath2 = new ExtendPath();
        Path path5 = new Path();
        path5.moveTo(width4, (float) rect2.top);
        path5.lineTo(width4, (float) rect2.bottom);
        path5.moveTo(width, height);
        path5.lineTo(width2, height2);
        path5.lineTo(width3, height3);
        path5.lineTo(width4, f);
        extendPath2.setPath(path5);
        extendPath2.setLine(autoShape.getLine());
        paths.add(extendPath2);
        return paths;
    }
}
