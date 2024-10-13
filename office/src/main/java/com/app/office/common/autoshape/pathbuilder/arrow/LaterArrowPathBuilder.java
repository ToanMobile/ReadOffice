package com.app.office.common.autoshape.pathbuilder.arrow;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.common.shape.AutoShape;
import java.util.ArrayList;
import java.util.List;

public class LaterArrowPathBuilder {
    private static final float TODEGREE = 1.6666666f;
    private static Path path = new Path();
    private static RectF s_rect = new RectF();

    public static Object getArrowPath(AutoShape autoShape, Rect rect) {
        path.reset();
        int shapeType = autoShape.getShapeType();
        if (shapeType == 13) {
            return getRightArrowPath(autoShape, rect);
        }
        if (shapeType == 15) {
            return getHomePlatePath(autoShape, rect);
        }
        if (shapeType == 55) {
            return getChevronPath(autoShape, rect);
        }
        if (shapeType == 83) {
            return getQuadArrowCalloutPath(autoShape, rect);
        }
        if (shapeType == 99) {
            return getCircularArrowPath(autoShape, rect);
        }
        if (shapeType == 182) {
            return getLeftRightUpArrowPath(autoShape, rect);
        }
        if (shapeType == 93) {
            return getStripedRightArrowPath(autoShape, rect);
        }
        if (shapeType == 94) {
            return getNotchedRightArrowPath(autoShape, rect);
        }
        switch (shapeType) {
            case 66:
                return getLeftArrowPath(autoShape, rect);
            case 67:
                return getDownArrowPath(autoShape, rect);
            case 68:
                return getUpArrowPath(autoShape, rect);
            case 69:
                return getLeftRightArrowPath(autoShape, rect);
            case 70:
                return getUpDownArrowPath(autoShape, rect);
            default:
                switch (shapeType) {
                    case 76:
                        return getQuadArrowPath(autoShape, rect);
                    case 77:
                        return getLeftArrowCalloutPath(autoShape, rect);
                    case 78:
                        return getRightArrowCalloutPath(autoShape, rect);
                    case 79:
                        return getUpArrowCalloutPath(autoShape, rect);
                    case 80:
                        return getDownArrowCalloutPath(autoShape, rect);
                    case 81:
                        return getLeftRightArrowCalloutPath(autoShape, rect);
                    default:
                        switch (shapeType) {
                            case 89:
                                return getLeftUpArrowPath(autoShape, rect);
                            case 90:
                                return getBentUpArrowPath(autoShape, rect);
                            case 91:
                                return getBentArrowPath(autoShape, rect);
                            default:
                                switch (shapeType) {
                                    case 101:
                                        return getUturnArrowPath(autoShape, rect);
                                    case 102:
                                        return getCurvedRightArrowPath(autoShape, rect);
                                    case 103:
                                        return getCurvedLeftArrowPath(autoShape, rect);
                                    case 104:
                                        return getCurvedUpArrowPath(autoShape, rect);
                                    case 105:
                                        return getCurvedDownArrowPath(autoShape, rect);
                                    default:
                                        return new Path();
                                }
                        }
                }
        }
    }

    private static Path getRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        int height = rect.height() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) height) * 0.5f);
            i2 = round;
            i = Math.round(((float) min) * 0.5f);
        } else {
            i2 = Math.round(((float) height) * adjustData[0].floatValue());
            i = Math.round(((float) min) * adjustData[1].floatValue());
        }
        path.moveTo((float) rect.left, (float) (rect.centerY() - i2));
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() - i2));
        path.lineTo((float) (rect.right - i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() + i2));
        path.lineTo((float) rect.left, (float) (rect.centerY() + i2));
        path.close();
        return path;
    }

    private static Path getLeftArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        int height = rect.height() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) height) * 0.5f);
            i2 = round;
            i = Math.round(((float) min) * 0.5f);
        } else {
            i2 = Math.round(((float) height) * adjustData[0].floatValue());
            i = Math.round(((float) min) * adjustData[1].floatValue());
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i), (float) rect.top);
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() - i2));
        path.lineTo((float) rect.right, (float) (rect.centerY() - i2));
        path.lineTo((float) rect.right, (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getUpArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        int width = rect.width() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) width) * 0.5f);
            i2 = round;
            i = Math.round(((float) min) * 0.5f);
        } else {
            i2 = Math.round(((float) width) * adjustData[0].floatValue());
            i = Math.round(((float) min) * adjustData[1].floatValue());
        }
        path.moveTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i2), (float) rect.bottom);
        path.lineTo((float) (rect.centerX() - i2), (float) rect.bottom);
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.top + i));
        path.lineTo((float) rect.left, (float) (rect.top + i));
        path.close();
        return path;
    }

    private static Path getDownArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        int width = rect.width() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) width) * 0.5f);
            i2 = round;
            i = Math.round(((float) min) * 0.5f);
        } else {
            i2 = Math.round(((float) width) * adjustData[0].floatValue());
            i = Math.round(((float) min) * adjustData[1].floatValue());
        }
        path.moveTo((float) (rect.centerX() - i2), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i2), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.bottom - i));
        path.lineTo((float) rect.right, (float) (rect.bottom - i));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.bottom - i));
        path.close();
        return path;
    }

    private static Path getLeftRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        int height = rect.height() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) height) * 0.5f);
            i2 = round;
            i = Math.round(((float) min) * 0.5f);
        } else {
            i2 = Math.round(((float) height) * adjustData[0].floatValue());
            i = Math.round(((float) min) * adjustData[1].floatValue());
            if (i * 2 > rect.width()) {
                i = min * 2;
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i), (float) rect.top);
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() - i2));
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() - i2));
        path.lineTo((float) (rect.right - i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getUpDownArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        int width = rect.width() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) width) * 0.5f);
            i2 = round;
            i = Math.round(((float) min) * 0.5f);
        } else {
            i2 = Math.round(((float) width) * adjustData[0].floatValue());
            i = Math.round(((float) min) * adjustData[1].floatValue());
            if (i * 2 > rect.height()) {
                i = min * 2;
            }
        }
        path.moveTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.bottom - i));
        path.lineTo((float) rect.right, (float) (rect.bottom - i));
        path.lineTo(rect.exactCenterX(), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.top + i));
        path.lineTo((float) rect.left, (float) (rect.top + i));
        path.close();
        return path;
    }

    private static Path getQuadArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height()) / 2;
        int min2 = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            int round = Math.round(((float) min) * 0.225f);
            float f = ((float) min2) * 0.225f;
            i2 = Math.round(f);
            i3 = round;
            i = Math.round(f);
        } else {
            i3 = Math.round(((float) min) * adjustData[0].floatValue());
            float f2 = (float) min2;
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i = Math.round(f2 * adjustData[2].floatValue());
            if (i3 > i2) {
                i3 = i2;
            }
            int i4 = min2 / 2;
            if (i2 + i > i4) {
                i = i4 - i2;
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() - i2));
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() - i3));
        path.lineTo((float) (rect.centerX() - i3), (float) (rect.centerY() - i3));
        path.lineTo((float) (rect.centerX() - i3), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.top + i));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i3), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i3), (float) (rect.centerY() - i3));
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() - i3));
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() - i2));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() + i3));
        path.lineTo((float) (rect.centerX() + i3), (float) (rect.centerY() + i3));
        path.lineTo((float) (rect.centerX() + i3), (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.bottom - i));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() - i3), (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() - i3), (float) (rect.centerY() + i3));
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() + i3));
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() + i2));
        path.close();
        return path;
    }

    private static Path getLeftRightUpArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            float f = ((float) min) * 0.225f;
            i3 = Math.round(f / 2.0f);
            i2 = Math.round(f);
            i = Math.round(f);
        } else {
            float f2 = (float) min;
            i3 = Math.round((adjustData[0].floatValue() * f2) / 2.0f);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i = Math.round(f2 * adjustData[2].floatValue());
            if (i3 > i2) {
                i3 = i2;
            }
            if (i2 + i > rect.width()) {
                i = (min / 2) - i2;
            }
            int i4 = i2 * 2;
            if (i4 + i > rect.height()) {
                i = rect.height() - i4;
            }
        }
        path.moveTo((float) (rect.left + i), (float) ((rect.bottom - i2) + i3));
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i2));
        int i5 = i2 * 2;
        path.lineTo((float) (rect.left + i), (float) (rect.bottom - i5));
        path.lineTo((float) (rect.left + i), (float) ((rect.bottom - i2) - i3));
        path.lineTo((float) (rect.centerX() - i3), (float) ((rect.bottom - i2) - i3));
        path.lineTo((float) (rect.centerX() - i3), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.top + i));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i3), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i3), (float) ((rect.bottom - i2) - i3));
        path.lineTo((float) (rect.right - i), (float) ((rect.bottom - i2) - i3));
        path.lineTo((float) (rect.right - i), (float) (rect.bottom - i5));
        path.lineTo((float) rect.right, (float) (rect.bottom - i2));
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) (rect.right - i), (float) ((rect.bottom - i2) + i3));
        path.close();
        return path;
    }

    private static Path getBentArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 4) {
            float f = (float) min;
            float f2 = 0.25f * f;
            i4 = Math.round(f2);
            i3 = Math.round(f2);
            i2 = Math.round(f2);
            i = Math.round(f * 0.4375f);
        } else {
            float f3 = (float) min;
            i4 = Math.round(adjustData[0].floatValue() * f3);
            i3 = Math.round(adjustData[1].floatValue() * f3);
            i2 = Math.round(adjustData[2].floatValue() * f3);
            i = Math.round(f3 * adjustData[3].floatValue());
            int i5 = i3 * 2;
            if (i4 > i5) {
                i4 = i5;
            }
            if (i2 + i > rect.width()) {
                i = rect.width() - i2;
            }
            if (i > rect.height()) {
                i = rect.height();
            }
        }
        path.moveTo((float) rect.left, (float) rect.bottom);
        int i6 = i4 / 2;
        path.lineTo((float) rect.left, (float) (((rect.top + i3) - i6) + i));
        int i7 = i * 2;
        s_rect.set((float) rect.left, (float) ((rect.top + i3) - i6), (float) (rect.left + i7), (float) (((rect.top + i3) - i6) + i7));
        path.arcTo(s_rect, 180.0f, 90.0f);
        path.lineTo((float) (rect.right - i2), (float) ((rect.top + i3) - i6));
        path.lineTo((float) (rect.right - i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i3));
        path.lineTo((float) (rect.right - i2), (float) (rect.top + (i3 * 2)));
        path.lineTo((float) (rect.right - i2), (float) (rect.top + i3 + i6));
        if (i >= i4) {
            path.lineTo((float) (rect.left + i), (float) (rect.top + i3 + i6));
            int i8 = (i - i4) * 2;
            s_rect.set((float) (rect.left + i4), (float) (rect.top + i3 + i6), (float) (rect.left + i8), (float) (rect.top + i3 + i6 + i8));
            path.arcTo(s_rect, 270.0f, -90.0f);
            path.lineTo((float) (rect.left + i4), (float) (((rect.top + i3) - i6) + i));
        } else {
            path.lineTo((float) (rect.left + i4), (float) (rect.top + i3 + i6));
        }
        path.lineTo((float) (rect.left + i4), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getUturnArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 5) {
            float f = (float) min;
            float f2 = 0.25f * f;
            i4 = Math.round(f2);
            i3 = Math.round(f2);
            i2 = Math.round(f2);
            i5 = Math.round(f * 0.4375f);
            i = Math.round(((float) rect.height()) * 0.75f);
        } else {
            float f3 = (float) min;
            i4 = Math.round(adjustData[0].floatValue() * f3);
            i3 = Math.round(adjustData[1].floatValue() * f3);
            i2 = Math.round(adjustData[2].floatValue() * f3);
            i5 = Math.round(f3 * adjustData[3].floatValue());
            i = Math.round(((float) rect.height()) * adjustData[4].floatValue());
            int i6 = i3 * 2;
            if (i4 > i6) {
                i4 = i6;
            }
            int i7 = i5 + i2;
            if (i7 >= i) {
                i = i7;
            }
            if (i > rect.height()) {
                i = rect.height();
                i2 = i - i5;
            }
            if (i - i2 < i4) {
                i2 = i - i4;
            }
        }
        path.moveTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.top + i5));
        int i8 = i5 * 2;
        s_rect.set((float) rect.left, (float) rect.top, (float) (rect.left + i8), (float) (rect.top + i8));
        path.arcTo(s_rect, 180.0f, 90.0f);
        int i9 = i4 / 2;
        path.lineTo((float) (((rect.right - i3) + i9) - i5), (float) rect.top);
        s_rect.set((float) (((rect.right - i3) + i9) - i8), (float) rect.top, (float) ((rect.right - i3) + i9), (float) (rect.top + i8));
        path.arcTo(s_rect, 270.0f, 90.0f);
        path.lineTo((float) ((rect.right - i3) + i9), (float) ((rect.top + i) - i2));
        path.lineTo((float) rect.right, (float) ((rect.top + i) - i2));
        path.lineTo((float) (rect.right - i3), (float) (rect.top + i));
        path.lineTo((float) (rect.right - (i3 * 2)), (float) ((rect.top + i) - i2));
        path.lineTo((float) ((rect.right - i3) - i9), (float) ((rect.top + i) - i2));
        if (i5 >= i4) {
            path.lineTo((float) ((rect.right - i3) - i9), (float) (rect.top + i5));
            int i10 = (i5 - i4) * 2;
            s_rect.set((float) (((rect.right - i3) - i9) - i10), (float) (rect.top + i4), (float) ((rect.right - i3) - i9), (float) (rect.top + i4 + i10));
            path.arcTo(s_rect, 0.0f, -90.0f);
            path.lineTo((float) (((rect.right - i3) + i9) - i5), (float) (rect.top + i4));
            path.lineTo((float) (rect.left + i5), (float) (rect.top + i4));
            s_rect.set((float) (rect.left + i4), (float) (rect.top + i4), (float) (rect.left + i4 + i10), (float) (rect.top + i4 + i10));
            path.arcTo(s_rect, 270.0f, -90.0f);
            path.lineTo((float) (rect.left + i4), (float) (rect.top + i5));
        } else {
            path.lineTo((float) ((rect.right - i3) - i9), (float) (rect.top + i4));
            path.lineTo((float) (rect.left + i4), (float) (rect.top + i4));
        }
        path.lineTo((float) (rect.left + i4), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getLeftUpArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            float f = ((float) min) * 0.25f;
            i3 = Math.round(f);
            i2 = Math.round(f);
            i = Math.round(f);
        } else {
            float f2 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i = Math.round(f2 * adjustData[2].floatValue());
            int i4 = i2 * 2;
            if (i3 > i4) {
                i3 = i4;
            }
            if (i4 + i > min) {
                i = min - i4;
            }
        }
        int i5 = i3 / 2;
        path.moveTo((float) (rect.left + i), (float) ((rect.bottom - i2) + i5));
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i2));
        int i6 = i2 * 2;
        path.lineTo((float) (rect.left + i), (float) (rect.bottom - i6));
        path.lineTo((float) (rect.left + i), (float) ((rect.bottom - i2) - i5));
        path.lineTo((float) ((rect.right - i2) - i5), (float) ((rect.bottom - i2) - i5));
        path.lineTo((float) ((rect.right - i2) - i5), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i6), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i));
        path.lineTo((float) ((rect.right - i2) + i5), (float) (rect.top + i));
        path.lineTo((float) ((rect.right - i2) + i5), (float) ((rect.bottom - i2) + i5));
        path.close();
        return path;
    }

    private static Path getBentUpArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            float f = ((float) min) * 0.25f;
            i3 = Math.round(f);
            i2 = Math.round(f);
            i = Math.round(f);
        } else {
            float f2 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i = Math.round(f2 * adjustData[2].floatValue());
        }
        path.moveTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i3));
        int i4 = i3 / 2;
        path.lineTo((float) ((rect.right - i2) - i4), (float) (rect.bottom - i3));
        path.lineTo((float) ((rect.right - i2) - i4), (float) (rect.top + i));
        path.lineTo((float) (rect.right - (i2 * 2)), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i));
        path.lineTo((float) ((rect.right - i2) + i4), (float) (rect.top + i));
        path.lineTo((float) ((rect.right - i2) + i4), (float) rect.bottom);
        path.close();
        return path;
    }

    private static List<Path> getCurvedRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Rect rect2 = rect;
        ArrayList arrayList = new ArrayList(2);
        Float[] adjustData = autoShape.getAdjustData();
        Path path2 = new Path();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            float f = (float) min;
            float f2 = 0.25f * f;
            i2 = Math.round(f2);
            i = Math.round(f * 0.5f);
            i3 = Math.round(f2);
        } else {
            float f3 = (float) min;
            i2 = Math.round(adjustData[0].floatValue() * f3);
            i = Math.round(adjustData[1].floatValue() * f3);
            i3 = Math.round(f3 * adjustData[2].floatValue());
        }
        int width = rect.width() * 2;
        int i4 = i / 2;
        int i5 = i2 / 2;
        int i6 = ((rect2.bottom - i4) - i5) - rect2.top;
        path2.moveTo((float) rect2.right, (float) rect2.top);
        s_rect.set((float) rect2.left, (float) rect2.top, (float) (rect2.left + width), (float) (rect2.top + i6));
        path2.arcTo(s_rect, 270.0f, -90.0f);
        int i7 = i6 / 2;
        path2.lineTo((float) rect2.left, (float) (rect2.top + i7 + i2));
        s_rect.set((float) rect2.left, (float) (rect2.top + i2), (float) (rect2.left + width), (float) (rect2.top + i6 + i2));
        path2.arcTo(s_rect, 180.0f, 90.0f);
        path2.close();
        arrayList.add(path2);
        Path path3 = new Path();
        path3.moveTo((float) rect2.left, (float) (rect2.top + i7));
        int i8 = i2;
        int i9 = i4;
        double d = (double) (width / 2);
        ArrayList arrayList2 = arrayList;
        int i10 = i5;
        double d2 = (double) i3;
        double sqrt = Math.sqrt((Math.pow((double) i7, 2.0d) * (Math.pow(d, 2.0d) - Math.pow(d2, 2.0d))) / Math.pow(d, 2.0d));
        int atan = (int) ((Math.atan(sqrt / d2) * 180.0d) / 3.141592653589793d);
        s_rect.set((float) rect2.left, (float) rect2.top, (float) (rect2.left + width), (float) (rect2.top + i6));
        path3.arcTo(s_rect, 180.0f, (float) (-atan));
        int i11 = (int) sqrt;
        path3.setLastPoint((float) (rect2.right - i3), (float) (rect2.top + i7 + i11));
        path3.lineTo((float) (rect2.right - i3), (float) ((((rect2.top + i7) + i11) + i10) - i9));
        path3.lineTo((float) rect2.right, (float) (rect2.bottom - i9));
        path3.lineTo((float) (rect2.right - i3), (float) (rect2.top + i7 + i11 + i10 + i9));
        path3.lineTo((float) (rect2.right - i3), (float) (rect2.top + i7 + i11 + i8));
        s_rect.set((float) rect2.left, (float) (rect2.top + i8), (float) (rect2.left + width), (float) (rect2.top + i6 + i8));
        path3.arcTo(s_rect, (float) (180 - atan), (float) atan);
        path3.close();
        ArrayList arrayList3 = arrayList2;
        arrayList3.add(path3);
        return arrayList3;
    }

    private static List<Path> getCurvedLeftArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Rect rect2 = rect;
        ArrayList arrayList = new ArrayList(2);
        Float[] adjustData = autoShape.getAdjustData();
        Path path2 = new Path();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            float f = (float) min;
            float f2 = 0.25f * f;
            i2 = Math.round(f2);
            i = Math.round(f * 0.5f);
            i3 = Math.round(f2);
        } else {
            float f3 = (float) min;
            i2 = Math.round(adjustData[0].floatValue() * f3);
            i = Math.round(adjustData[1].floatValue() * f3);
            i3 = Math.round(f3 * adjustData[2].floatValue());
        }
        int width = rect.width() * 2;
        int i4 = i / 2;
        int i5 = i2 / 2;
        int i6 = ((rect2.bottom - i4) - i5) - rect2.top;
        int i7 = i6 / 2;
        path2.moveTo((float) rect2.right, (float) (rect2.top + i7));
        double d = (double) (width / 2);
        int i8 = i2;
        int i9 = i4;
        double d2 = (double) i3;
        double sqrt = Math.sqrt((Math.pow((double) i7, 2.0d) * (Math.pow(d, 2.0d) - Math.pow(d2, 2.0d))) / Math.pow(d, 2.0d));
        int atan = (int) ((Math.atan(sqrt / d2) * 180.0d) / 3.141592653589793d);
        s_rect.set((float) (rect2.right - width), (float) rect2.top, (float) rect2.right, (float) (rect2.top + i6));
        float f4 = (float) atan;
        path2.arcTo(s_rect, 0.0f, f4);
        int i10 = (int) sqrt;
        path2.setLastPoint((float) (rect2.left + i3), (float) (rect2.top + i7 + i10));
        path2.lineTo((float) (rect2.left + i3), (float) ((((rect2.top + i7) + i10) + i5) - i9));
        path2.lineTo((float) rect2.left, (float) (rect2.bottom - i9));
        path2.lineTo((float) (rect2.left + i3), (float) (rect2.top + i7 + i10 + i5 + i9));
        path2.lineTo((float) (rect2.left + i3), (float) (rect2.top + i7 + i10 + i8));
        s_rect.set((float) (rect2.right - width), (float) (rect2.top + i8), (float) rect2.right, (float) (rect2.top + i6 + i8));
        path2.arcTo(s_rect, f4, (float) (-atan));
        path2.close();
        arrayList.add(path2);
        Path path3 = new Path();
        path3.moveTo((float) rect2.left, (float) rect2.top);
        s_rect.set((float) (rect2.right - width), (float) rect2.top, (float) rect2.right, (float) (rect2.top + i6));
        path3.arcTo(s_rect, 270.0f, 90.0f);
        path3.lineTo((float) rect2.right, (float) (rect2.top + i7 + i8));
        s_rect.set((float) (rect2.right - width), (float) (rect2.top + i8), (float) rect2.right, (float) (rect2.top + i6 + i8));
        path3.arcTo(s_rect, 0.0f, -90.0f);
        path3.close();
        arrayList.add(path3);
        return arrayList;
    }

    private static List<Path> getCurvedUpArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Rect rect2 = rect;
        ArrayList arrayList = new ArrayList(2);
        Float[] adjustData = autoShape.getAdjustData();
        Path path2 = new Path();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            float f = (float) min;
            float f2 = 0.25f * f;
            i2 = Math.round(f2);
            i = Math.round(f * 0.5f);
            i3 = Math.round(f2);
        } else {
            float f3 = (float) min;
            i2 = Math.round(adjustData[0].floatValue() * f3);
            i = Math.round(adjustData[1].floatValue() * f3);
            i3 = Math.round(f3 * adjustData[2].floatValue());
        }
        int i4 = i / 2;
        int i5 = i2 / 2;
        int width = ((rect.width() - i4) - i5) / 2;
        int height = rect.height();
        path2.moveTo((float) (rect2.left + width), (float) rect2.bottom);
        double d = (double) height;
        int i6 = i2;
        double d2 = (double) i3;
        double sqrt = Math.sqrt((Math.pow((double) width, 2.0d) * (Math.pow(d, 2.0d) - Math.pow(d2, 2.0d))) / Math.pow(d, 2.0d));
        int atan = (int) ((Math.atan(sqrt / d2) * 180.0d) / 3.141592653589793d);
        int i7 = width * 2;
        s_rect.set((float) rect2.left, (float) (rect2.top - height), (float) (rect2.left + i7), (float) (rect2.top + height));
        path2.arcTo(s_rect, 90.0f, (float) (-atan));
        float f4 = (float) sqrt;
        path2.setLastPoint(((float) (rect2.left + width)) + f4, (float) (rect2.top + i3));
        float f5 = (float) i5;
        int i8 = i4;
        float f6 = (float) i8;
        path2.lineTo(((((float) (rect2.left + width)) + f4) + f5) - f6, (float) (rect2.top + i3));
        path2.lineTo((float) (rect2.right - i8), (float) rect2.top);
        path2.lineTo(((float) (rect2.left + width)) + f4 + f5 + f6, (float) (rect2.top + i3));
        int i9 = i6;
        path2.lineTo(((float) (rect2.left + width)) + f4 + ((float) i9), (float) (rect2.top + i3));
        s_rect.set((float) (rect2.left + i9), (float) (rect2.top - height), (float) (rect2.left + i7 + i9), (float) (rect2.top + height));
        path2.arcTo(s_rect, (float) (90 - atan), (float) atan);
        path2.close();
        arrayList.add(path2);
        Path path3 = new Path();
        path3.moveTo((float) rect2.left, (float) rect2.top);
        s_rect.set((float) rect2.left, (float) (rect2.top - height), (float) (rect2.left + i7), (float) (rect2.top + height));
        path3.arcTo(s_rect, 180.0f, -90.0f);
        path3.lineTo((float) (rect2.left + width + i9), (float) rect2.bottom);
        s_rect.set((float) (rect2.left + i9), (float) (rect2.top - height), (float) (rect2.left + i7 + i9), (float) (rect2.top + height));
        path3.arcTo(s_rect, 90.0f, 90.0f);
        path3.close();
        arrayList.add(path3);
        return arrayList;
    }

    private static List<Path> getCurvedDownArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        Rect rect2 = rect;
        ArrayList arrayList = new ArrayList(2);
        Float[] adjustData = autoShape.getAdjustData();
        Path path2 = new Path();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 3) {
            float f = (float) min;
            float f2 = 0.25f * f;
            i2 = Math.round(f2);
            i = Math.round(f * 0.5f);
            i3 = Math.round(f2);
        } else {
            float f3 = (float) min;
            i2 = Math.round(adjustData[0].floatValue() * f3);
            i = Math.round(adjustData[1].floatValue() * f3);
            i3 = Math.round(f3 * adjustData[2].floatValue());
        }
        int i4 = i / 2;
        int i5 = i2 / 2;
        int width = ((rect.width() - i4) - i5) / 2;
        int height = rect.height();
        path2.moveTo((float) rect2.left, (float) rect2.bottom);
        int i6 = width * 2;
        int i7 = height * 2;
        s_rect.set((float) rect2.left, (float) rect2.top, (float) (rect2.left + i6), (float) (rect2.top + i7));
        path2.arcTo(s_rect, 180.0f, 90.0f);
        path2.lineTo((float) (rect2.left + width + i2), (float) rect2.top);
        s_rect.set((float) (rect2.left + i2), (float) rect2.top, (float) (rect2.left + i6 + i2), (float) (rect2.top + i7));
        path2.arcTo(s_rect, 270.0f, -90.0f);
        path2.close();
        arrayList.add(path2);
        Path path3 = new Path();
        path3.moveTo((float) (rect2.left + width), (float) rect2.top);
        ArrayList arrayList2 = arrayList;
        double d = (double) height;
        int i8 = i2;
        int i9 = i4;
        double d2 = (double) i3;
        double sqrt = Math.sqrt((Math.pow((double) width, 2.0d) * (Math.pow(d, 2.0d) - Math.pow(d2, 2.0d))) / Math.pow(d, 2.0d));
        int atan = (int) ((Math.atan(sqrt / d2) * 180.0d) / 3.141592653589793d);
        s_rect.set((float) rect2.left, (float) rect2.top, (float) (rect2.left + i6), (float) (rect2.top + i7));
        path3.arcTo(s_rect, 270.0f, (float) atan);
        float f4 = (float) sqrt;
        path3.setLastPoint(((float) (rect2.left + width)) + f4, (float) (rect2.bottom - i3));
        float f5 = (float) i5;
        int i10 = i9;
        float f6 = (float) i10;
        path3.lineTo(((((float) (rect2.left + width)) + f4) + f5) - f6, (float) (rect2.bottom - i3));
        path3.lineTo((float) (rect2.right - i10), (float) rect2.bottom);
        path3.lineTo(((float) (rect2.left + width)) + f4 + f5 + f6, (float) (rect2.bottom - i3));
        float f7 = ((float) (rect2.left + width)) + f4;
        int i11 = i8;
        path3.lineTo(f7 + ((float) i11), (float) (rect2.bottom - i3));
        s_rect.set((float) (rect2.left + i11), (float) rect2.top, (float) (rect2.left + i6 + i11), (float) (rect2.top + i7));
        path3.arcTo(s_rect, (float) (atan + TIFFConstants.TIFFTAG_IMAGEDESCRIPTION), (float) (-atan));
        path3.close();
        ArrayList arrayList3 = arrayList2;
        arrayList3.add(path3);
        return arrayList3;
    }

    private static Path getStripedRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Rect rect2 = rect;
        Float[] adjustData = autoShape.getAdjustData();
        int height = rect.height() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) height) * 0.5f);
            i = round;
            i2 = Math.round(((float) min) * 0.5f);
        } else {
            i = Math.round(((float) height) * adjustData[0].floatValue());
            i2 = Math.round(((float) min) * adjustData[1].floatValue());
        }
        int i3 = min / 32;
        path.addRect((float) rect2.left, (float) (rect.centerY() - i), (float) (rect2.left + i3), (float) (rect.centerY() + i), Path.Direction.CW);
        path.addRect((float) (rect2.left + (i3 * 2)), (float) (rect.centerY() - i), (float) (rect2.left + (i3 * 4)), (float) (rect.centerY() + i), Path.Direction.CW);
        int i4 = i3 * 5;
        path.moveTo((float) (rect2.left + i4), (float) (rect.centerY() - i));
        path.lineTo((float) (rect2.right - i2), (float) (rect.centerY() - i));
        path.lineTo((float) (rect2.right - i2), (float) rect2.top);
        path.lineTo((float) rect2.right, (float) rect.centerY());
        path.lineTo((float) (rect2.right - i2), (float) rect2.bottom);
        path.lineTo((float) (rect2.right - i2), (float) (rect.centerY() + i));
        path.lineTo((float) (rect2.left + i4), (float) (rect.centerY() + i));
        path.close();
        return path;
    }

    private static Path getNotchedRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        int height = rect.height() / 2;
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 2) {
            int round = Math.round(((float) height) * 0.5f);
            i2 = round;
            i = Math.round(((float) min) * 0.5f);
        } else {
            i2 = Math.round(((float) height) * adjustData[0].floatValue());
            i = Math.round(((float) min) * adjustData[1].floatValue());
        }
        int height2 = ((i2 * 2) * i) / rect.height();
        path.moveTo((float) rect.left, (float) (rect.centerY() - i2));
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() - i2));
        path.lineTo((float) (rect.right - i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() + i2));
        path.lineTo((float) rect.left, (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.left + height2), (float) rect.centerY());
        path.close();
        return path;
    }

    private static Path getHomePlatePath(AutoShape autoShape, Rect rect) {
        int i;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            i = Math.round(((float) min) * 0.5f);
        } else {
            i = Math.round(((float) min) * adjustData[0].floatValue());
        }
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) (rect.right - i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getChevronPath(AutoShape autoShape, Rect rect) {
        int i;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 1) {
            i = Math.round(((float) min) * 0.5f);
        } else {
            i = Math.round(((float) min) * adjustData[0].floatValue());
        }
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) (rect.right - i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) (rect.left + i), (float) rect.centerY());
        path.close();
        return path;
    }

    private static Path getRightArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 4) {
            float f = ((float) min) * 0.25f;
            i3 = Math.round(f);
            i2 = Math.round(f);
            i4 = Math.round(f);
            i = Math.round(((float) rect.width()) * 0.65f);
        } else {
            for (int i5 = 0; i5 < 4; i5++) {
                if (adjustData[i5].floatValue() > 1.0f && i5 != 2) {
                    adjustData[i5] = Float.valueOf(1.0f);
                }
            }
            float f2 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i4 = Math.round(f2 * adjustData[2].floatValue());
            i = Math.round(((float) rect.width()) * adjustData[3].floatValue());
            int i6 = i2 * 2;
            if (i3 > i6) {
                i3 = i6;
            }
            if (i4 > rect.width()) {
                i4 = rect.width();
            }
            if (i + i4 > rect.width()) {
                i = rect.width() - i4;
            }
        }
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) (rect.left + i), (float) rect.top);
        int i7 = i3 / 2;
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() - i7));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() - i7));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() - i2));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.left + i), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getLeftArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 4) {
            float f = ((float) min) * 0.25f;
            i3 = Math.round(f);
            i2 = Math.round(f);
            i4 = Math.round(f);
            i = Math.round(((float) rect.width()) * 0.65f);
        } else {
            for (int i5 = 0; i5 < 4; i5++) {
                if (adjustData[i5].floatValue() > 1.0f && i5 != 2) {
                    adjustData[i5] = Float.valueOf(1.0f);
                }
            }
            float f2 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i4 = Math.round(f2 * adjustData[2].floatValue());
            i = Math.round(((float) rect.width()) * adjustData[3].floatValue());
            int i6 = i2 * 2;
            if (i3 > i6) {
                i3 = i6;
            }
            if (i4 > rect.width()) {
                i4 = rect.width();
            }
            if (i + i4 > rect.width()) {
                i = rect.width() - i4;
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() - i2));
        int i7 = i3 / 2;
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() - i7));
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() - i7));
        path.lineTo((float) (rect.right - i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) (rect.right - i), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() + i2));
        path.close();
        return path;
    }

    private static Path getUpArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 4) {
            float f = ((float) min) * 0.25f;
            i3 = Math.round(f);
            i2 = Math.round(f);
            i4 = Math.round(f);
            i = Math.round(((float) rect.height()) * 0.65f);
        } else {
            for (int i5 = 0; i5 < 4; i5++) {
                if (adjustData[i5].floatValue() > 1.0f && i5 != 2) {
                    adjustData[i5] = Float.valueOf(1.0f);
                }
            }
            float f2 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i4 = Math.round(f2 * adjustData[2].floatValue());
            i = Math.round(((float) rect.height()) * adjustData[3].floatValue());
            int i6 = i2 * 2;
            if (i3 > i6) {
                i3 = i6;
            }
            if (i4 > rect.height()) {
                i4 = rect.width();
            }
            if (i + i4 > rect.height()) {
                i = rect.height() - i4;
            }
        }
        path.moveTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.top + i4));
        int i7 = i3 / 2;
        path.lineTo((float) (rect.centerX() + i7), (float) (rect.top + i4));
        path.lineTo((float) (rect.centerX() + i7), (float) (rect.bottom - i));
        path.lineTo((float) rect.right, (float) (rect.bottom - i));
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() - i7), (float) (rect.bottom - i));
        path.lineTo((float) (rect.centerX() - i7), (float) (rect.top + i4));
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.top + i4));
        path.close();
        return path;
    }

    private static Path getDownArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 4) {
            float f = ((float) min) * 0.25f;
            i3 = Math.round(f);
            i2 = Math.round(f);
            i4 = Math.round(f);
            i = Math.round(((float) rect.height()) * 0.65f);
        } else {
            for (int i5 = 0; i5 < 4; i5++) {
                if (adjustData[i5].floatValue() > 1.0f && i5 != 2) {
                    adjustData[i5] = Float.valueOf(1.0f);
                }
            }
            float f2 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i4 = Math.round(f2 * adjustData[2].floatValue());
            i = Math.round(((float) rect.height()) * adjustData[3].floatValue());
            int i6 = i2 * 2;
            if (i3 > i6) {
                i3 = i6;
            }
            if (i4 > rect.height()) {
                i4 = rect.width();
            }
            if (i + i4 > rect.height()) {
                i = rect.height() - i4;
            }
        }
        path.moveTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.bottom - i4));
        int i7 = i3 / 2;
        path.lineTo((float) (rect.centerX() - i7), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.centerX() - i7), (float) (rect.top + i));
        path.lineTo((float) rect.left, (float) (rect.top + i));
        path.lineTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i7), (float) (rect.top + i));
        path.lineTo((float) (rect.centerX() + i7), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.bottom - i4));
        path.close();
        return path;
    }

    private static Path getLeftRightArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 4) {
            float f = ((float) min) * 0.25f;
            i3 = Math.round(f);
            i2 = Math.round(f);
            i4 = Math.round(f);
            i = Math.round(((float) rect.width()) * 0.5f);
        } else {
            for (int i5 = 0; i5 < 4; i5++) {
                if (adjustData[i5].floatValue() > 1.0f && i5 != 2) {
                    adjustData[i5] = Float.valueOf(1.0f);
                }
            }
            float f2 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i2 = Math.round(adjustData[1].floatValue() * f2);
            i4 = Math.round(f2 * adjustData[2].floatValue());
            i = Math.round(((float) rect.width()) * adjustData[3].floatValue());
            if (i4 * 2 >= rect.width()) {
                i4 = rect.width() / 2;
            }
            int i6 = i4 * 2;
            if (i6 + i >= rect.width()) {
                i = rect.width() - i6;
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() - i2));
        int i7 = i3 / 2;
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() - i7));
        int i8 = i / 2;
        path.lineTo((float) (rect.centerX() - i8), (float) (rect.centerY() - i7));
        path.lineTo((float) (rect.centerX() - i8), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i8), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i8), (float) (rect.centerY() - i7));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() - i7));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() - i2));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.centerX() + i8), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.centerX() + i8), (float) rect.bottom);
        path.lineTo((float) (rect.centerX() - i8), (float) rect.bottom);
        path.lineTo((float) (rect.centerX() - i8), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() + i7));
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() + i2));
        path.close();
        return path;
    }

    private static Path getQuadArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        int min = Math.min(rect.width(), rect.height());
        if (adjustData == null || adjustData.length != 4) {
            float f = (float) min;
            float f2 = 0.18515f * f;
            i3 = Math.round(f2);
            i2 = Math.round(f2);
            i4 = Math.round(f2);
            i = Math.round(f * 0.48f);
        } else {
            i4 = 0;
            for (int i5 = 0; i5 < 4; i5++) {
                if (adjustData[i5].floatValue() > 1.0f && i5 != 2) {
                    adjustData[i5] = Float.valueOf(1.0f);
                }
            }
            float f3 = (float) min;
            i3 = Math.round(adjustData[0].floatValue() * f3);
            i2 = Math.round(adjustData[1].floatValue() * f3);
            int round = Math.round(adjustData[2].floatValue() * f3);
            i = Math.round(f3 * adjustData[3].floatValue());
            int i6 = i2 * 2;
            if (i3 > i6) {
                i3 = i6;
            }
            if (i > i6) {
                i = i6;
            }
            if (i6 >= min) {
                i2 = min / 2;
            } else {
                i4 = round;
            }
            if (i4 * 2 >= min) {
                i4 = min / 2;
            }
            int i7 = min / 2;
            if (i2 + i4 > i7) {
                i4 = i7 - i2;
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() - i2));
        int i8 = i3 / 2;
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() - i8));
        int i9 = i / 2;
        path.lineTo((float) (rect.centerX() - i9), (float) (rect.centerY() - i8));
        path.lineTo((float) (rect.centerX() - i9), (float) (rect.centerY() - i9));
        path.lineTo((float) (rect.centerX() - i8), (float) (rect.centerY() - i9));
        path.lineTo((float) (rect.centerX() - i8), (float) (rect.top + i4));
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.top + i4));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.top + i4));
        path.lineTo((float) (rect.centerX() + i8), (float) (rect.top + i4));
        path.lineTo((float) (rect.centerX() + i8), (float) (rect.centerY() - i9));
        path.lineTo((float) (rect.centerX() + i9), (float) (rect.centerY() - i9));
        path.lineTo((float) (rect.centerX() + i9), (float) (rect.centerY() - i8));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() - i8));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() - i2));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() + i2));
        path.lineTo((float) (rect.right - i4), (float) (rect.centerY() + i8));
        path.lineTo((float) (rect.centerX() + i9), (float) (rect.centerY() + i8));
        path.lineTo((float) (rect.centerX() + i9), (float) (rect.centerY() + i9));
        path.lineTo((float) (rect.centerX() + i8), (float) (rect.centerY() + i9));
        path.lineTo((float) (rect.centerX() + i8), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.centerX() + i2), (float) (rect.bottom - i4));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) (rect.centerX() - i2), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.centerX() - i8), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.centerX() - i8), (float) (rect.centerY() + i9));
        path.lineTo((float) (rect.centerX() - i9), (float) (rect.centerY() + i9));
        path.lineTo((float) (rect.centerX() - i9), (float) (rect.centerY() + i8));
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() + i8));
        path.lineTo((float) (rect.left + i4), (float) (rect.centerY() + i2));
        path.close();
        return path;
    }

    private static Path getCircularArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length != 5) {
            float f = ((float) 100) * 0.125f;
            i4 = Math.round(f);
            i3 = 20;
            i = TIFFConstants.TIFFTAG_SMINSAMPLEVALUE;
            i2 = 180;
            i5 = Math.round(f);
        } else {
            float f2 = (float) 100;
            i4 = Math.round(adjustData[0].floatValue() * f2);
            i3 = Math.round(adjustData[1].floatValue() * TODEGREE);
            i = Math.round(adjustData[2].floatValue() * TODEGREE);
            i2 = Math.round(adjustData[3].floatValue() * TODEGREE);
            i5 = Math.round(f2 * adjustData[4].floatValue());
        }
        int i6 = 50 - i5;
        double d = (double) i6;
        double d2 = (((double) i) * 3.141592653589793d) / 180.0d;
        double cos = Math.cos(d2) * d;
        int i7 = i6;
        double d3 = (((double) (i3 + i)) * 3.141592653589793d) / 180.0d;
        double tan = Math.tan(d3);
        double sin = (Math.sin(d2) * d) - (tan * cos);
        double d4 = d;
        double sqrt = Math.sqrt(Math.pow((double) i5, 2.0d) / (Math.pow(tan, 2.0d) + 1.0d));
        int i8 = i4 / 2;
        double d5 = d3;
        double sqrt2 = Math.sqrt(Math.pow((double) i8, 2.0d) / (Math.pow(tan, 2.0d) + 1.0d));
        if (i > 90 && i < 270) {
            sqrt = -sqrt;
            sqrt2 = -sqrt2;
        }
        double d6 = cos + sqrt2;
        double d7 = sqrt;
        double angle = getAngle(d6, (tan * d6) + sin);
        double d8 = cos - sqrt2;
        double angle2 = getAngle(d8, (tan * d8) + sin);
        float f3 = (float) ((i5 - i8) - 50);
        float f4 = (float) (i7 + i8);
        s_rect.set(f3, f3, f4, f4);
        double d9 = angle2;
        double d10 = (double) i2;
        path.arcTo(s_rect, (float) i2, ((float) ((angle - d10) + 360.0d)) % 360.0f);
        double d11 = cos + d7;
        path.lineTo((float) d11, (float) ((d11 * tan) + sin));
        path.lineTo((float) (Math.cos(d5) * d4), (float) (Math.sin(d5) * d4));
        double d12 = cos - d7;
        path.lineTo((float) d12, (float) ((tan * d12) + sin));
        float f5 = (float) ((i5 + i8) - 50);
        float f6 = (float) (i7 - i8);
        s_rect.set(f5, f5, f6, f6);
        double d13 = d9;
        path.arcTo(s_rect, (float) d13, ((float) ((d10 - d13) - 360.0d)) % 360.0f);
        path.close();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) rect.width()) / 100.0f, ((float) rect.height()) / 100.0f);
        path.transform(matrix);
        path.offset((float) rect.centerX(), (float) rect.centerY());
        return path;
    }

    private static double getAngle(double d, double d2) {
        double acos = (Math.acos(d / Math.sqrt((d * d) + (d2 * d2))) * 180.0d) / 3.141592653589793d;
        return d2 < 0.0d ? 360.0d - acos : acos;
    }
}
