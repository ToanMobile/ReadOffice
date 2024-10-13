package com.app.office.common.autoshape.pathbuilder.arrow;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.app.office.common.shape.AutoShape;

public class EarlyArrowPathBuilder extends ArrowPathBuilder {
    private static final float TODEGREE = 0.3295496f;
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
                    case 82:
                        return getUpDownArrowCalloutPath(autoShape, rect);
                    case 83:
                        return getQuadArrowCalloutPath(autoShape, rect);
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
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.75f);
            i = Math.round(((float) rect.height()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.75f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo((float) rect.left, (float) (rect.bottom - i));
        path.close();
        return path;
    }

    private static Path getLeftArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.25f);
            i = Math.round(((float) rect.height()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.25f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) rect.right, (float) (rect.top + i));
        path.lineTo((float) rect.right, (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getUpArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.height()) * 0.25f);
            i = Math.round(((float) rect.width()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.height()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.height()) * 0.25f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.width()) * 0.25f);
            } else {
                i = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
        }
        path.moveTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i2));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i2));
        path.lineTo((float) (rect.right - i), (float) rect.bottom);
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.lineTo((float) (rect.left + i), (float) (rect.top + i2));
        path.lineTo((float) rect.left, (float) (rect.top + i2));
        path.close();
        return path;
    }

    private static Path getDownArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.height()) * 0.75f);
            i = Math.round(((float) rect.width()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.height()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.height()) * 0.75f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.width()) * 0.25f);
            } else {
                i = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
        }
        path.moveTo((float) (rect.left + i), (float) rect.top);
        path.lineTo((float) (rect.right - i), (float) rect.top);
        path.lineTo((float) (rect.right - i), (float) (rect.top + i2));
        path.lineTo((float) rect.right, (float) (rect.top + i2));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.top + i2));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i2));
        path.close();
        return path;
    }

    private static Path getLeftRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.2f);
            i = Math.round(((float) rect.height()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.2f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i2), (float) rect.bottom);
        path.lineTo((float) (rect.right - i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getUpDownArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.25f);
            i = Math.round(((float) rect.height()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.25f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
        }
        path.moveTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) (rect.bottom - i));
        path.lineTo((float) rect.right, (float) (rect.bottom - i));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) rect.left, (float) (rect.top + i));
        path.close();
        return path;
    }

    private static Path getQuadArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            int round = Math.round(((float) rect.height()) * 0.3f);
            int round2 = Math.round(((float) rect.height()) * 0.4f);
            int round3 = Math.round(((float) rect.width()) * 0.2f);
            int round4 = Math.round(((float) rect.width()) * 0.3f);
            int round5 = Math.round(((float) rect.width()) * 0.4f);
            int i7 = round4;
            i4 = round;
            i = Math.round(((float) rect.height()) * 0.2f);
            i6 = round3;
            i2 = round5;
            i5 = round2;
            i3 = i7;
        } else {
            if (adjustData[0] != null) {
                i4 = Math.round(((float) rect.height()) * adjustData[0].floatValue());
                i3 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                int round6 = Math.round(((float) rect.height()) * 0.3f);
                i3 = Math.round(((float) rect.width()) * 0.3f);
                i4 = round6;
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                int round7 = Math.round(((float) rect.height()) * 0.4f);
                i2 = Math.round(((float) rect.width()) * 0.4f);
                i5 = round7;
            } else {
                i5 = Math.round(((float) rect.height()) * adjustData[1].floatValue());
                i2 = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                int round8 = Math.round(((float) rect.width()) * 0.2f);
                i6 = round8;
                i = Math.round(((float) rect.height()) * 0.2f);
            } else {
                i6 = Math.round(((float) rect.width()) * adjustData[2].floatValue());
                i = Math.round(((float) rect.height()) * adjustData[2].floatValue());
            }
        }
        path.moveTo((float) (rect.left + i2), (float) (rect.bottom - i5));
        path.lineTo((float) (rect.left + i6), (float) (rect.bottom - i5));
        path.lineTo((float) (rect.left + i6), (float) (rect.bottom - i4));
        path.lineTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i6), (float) (rect.top + i4));
        path.lineTo((float) (rect.left + i6), (float) (rect.top + i5));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i5));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i3), (float) (rect.top + i));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.right - i3), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i2), (float) (rect.top + i5));
        path.lineTo((float) (rect.right - i6), (float) (rect.top + i5));
        path.lineTo((float) (rect.right - i6), (float) (rect.top + i4));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i6), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.right - i6), (float) (rect.bottom - i5));
        path.lineTo((float) (rect.right - i2), (float) (rect.bottom - i5));
        path.lineTo((float) (rect.right - i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.right - i3), (float) (rect.bottom - i));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) (rect.left + i3), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.close();
        return path;
    }

    private static Path getLeftRightUpArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            int round = Math.round(((((float) rect.height()) * 0.2f) * 10.0f) / 7.0f);
            int round2 = Math.round(((((float) rect.height()) * 0.1f) * 10.0f) / 7.0f);
            i4 = Math.round(((float) rect.width()) * 0.3f * 0.7f);
            i2 = Math.round(((float) rect.width()) * 0.3f);
            int round3 = Math.round(((float) rect.width()) * 0.4f);
            i3 = round;
            i = Math.round(((float) rect.height()) * 0.2f);
            int i7 = round2;
            i5 = round3;
            i6 = i7;
        } else {
            if (adjustData[0] != null) {
                i3 = Math.round(((((float) rect.height()) * (0.5f - adjustData[0].floatValue())) * 10.0f) / 7.0f);
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                int round4 = Math.round(((((float) rect.height()) * 0.2f) * 10.0f) / 7.0f);
                i2 = Math.round(((float) rect.width()) * 0.3f);
                i3 = round4;
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                int round5 = Math.round(((((float) rect.height()) * 0.1f) * 10.0f) / 7.0f);
                i5 = Math.round(((float) rect.width()) * 0.4f);
                i6 = round5;
            } else {
                i6 = Math.round(((((float) rect.height()) * (0.5f - adjustData[1].floatValue())) * 10.0f) / 7.0f);
                i5 = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i4 = Math.round(((float) rect.width()) * 0.2f * 0.7f);
                i = Math.round(((float) rect.height()) * 0.2f);
            } else {
                i4 = Math.round(((float) rect.width()) * adjustData[2].floatValue() * 0.7f);
                i = Math.round(((float) rect.height()) * adjustData[2].floatValue());
            }
        }
        path.moveTo((float) (rect.left + i4), (float) ((rect.bottom - i3) + i6));
        path.lineTo((float) (rect.left + i4), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.bottom - i3));
        int i8 = i3 * 2;
        path.lineTo((float) (rect.left + i4), (float) (rect.bottom - i8));
        path.lineTo((float) (rect.left + i4), (float) ((rect.bottom - i3) - i6));
        path.lineTo((float) (rect.left + i5), (float) ((rect.bottom - i3) - i6));
        path.lineTo((float) (rect.left + i5), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.right - i2), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i5), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i5), (float) ((rect.bottom - i3) - i6));
        path.lineTo((float) (rect.right - i4), (float) ((rect.bottom - i3) - i6));
        path.lineTo((float) (rect.right - i4), (float) (rect.bottom - i8));
        path.lineTo((float) rect.right, (float) (rect.bottom - i3));
        path.lineTo((float) (rect.right - i4), (float) rect.bottom);
        path.lineTo((float) (rect.right - i4), (float) ((rect.bottom - i3) + i6));
        path.close();
        return path;
    }

    private static Path getBentArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.7f);
            i = Math.round(((float) rect.height()) * 0.125f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.7f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.height()) * 0.125f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
        }
        float height = ((float) rect.height()) * 0.57f;
        path.moveTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.top) + height);
        s_rect.set((float) rect.left, (float) (rect.top + i), ((float) rect.left) + (((float) rect.width()) * 1.04f), ((float) rect.top) + height + (height - ((float) i)));
        path.arcTo(s_rect, 180.0f, 90.0f);
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        float f = height / 2.0f;
        path.lineTo((float) rect.right, ((float) rect.top) + f);
        path.lineTo((float) (rect.left + i2), ((float) rect.top) + height);
        float f2 = height - ((float) (i * 2));
        float f3 = f2 / 2.0f;
        path.lineTo((float) (rect.left + i2), ((float) rect.top) + f + f3);
        float height2 = f2 / ((float) rect.height());
        s_rect.set(((float) rect.left) + (((float) rect.width()) * height2), ((float) rect.top) + f + f3, ((float) rect.left) + (((float) rect.width()) * (1.14f - height2)), ((((float) rect.top) + height) + height) - (f + f3));
        path.arcTo(s_rect, 270.0f, -90.0f);
        path.lineTo(((float) rect.left) + (((float) rect.width()) * height2), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getUturnArrowPath(AutoShape autoShape, Rect rect) {
        int width = rect.width();
        int height = rect.height();
        path.moveTo((float) rect.left, (float) rect.bottom);
        float f = (float) height;
        float f2 = 0.38f * f;
        path.lineTo((float) rect.left, ((float) rect.top) + f2);
        float f3 = (float) width;
        s_rect.set((float) rect.left, (float) rect.top, ((float) rect.right) - (0.14f * f3), ((float) rect.top) + (0.76f * f));
        path.arcTo(s_rect, 180.0f, 180.0f);
        path.lineTo((float) rect.right, ((float) rect.top) + f2);
        float f4 = f3 * 0.28f;
        path.lineTo(((float) rect.right) - f4, ((float) rect.top) + (0.66f * f));
        path.lineTo(((float) rect.right) - (0.56f * f3), ((float) rect.top) + f2);
        float f5 = f3 * 0.42000002f;
        path.lineTo(((float) rect.right) - f5, ((float) rect.top) + f2);
        s_rect.set(((float) rect.left) + f4, ((float) rect.top) + (0.28f * f), ((float) rect.right) - f5, ((float) rect.top) + (f * 0.48f));
        path.arcTo(s_rect, 0.0f, -180.0f);
        path.lineTo(((float) rect.left) + f4, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getLeftUpArrowPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            f = ((float) rect.height()) * 0.43f;
            float height = 0.86f * ((float) rect.height());
            float width = 0.28f * ((float) rect.width());
            f4 = (float) Math.round(((float) rect.width()) * 0.43f);
            f2 = width;
            f6 = (float) Math.round(((float) rect.height()) * 0.28f);
            f3 = height;
            f5 = (float) Math.round(((float) rect.width()) * 0.86f);
        } else {
            if (adjustData[0] != null) {
                f4 = ((float) rect.width()) * adjustData[0].floatValue();
                f = ((float) rect.height()) * adjustData[0].floatValue();
            } else {
                f = ((float) rect.height()) * 0.43f;
                f4 = (float) Math.round(((float) rect.width()) * 0.43f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                float round = (float) Math.round(((float) rect.width()) * 0.86f);
                f3 = 0.86f * ((float) rect.height());
                f5 = round;
            } else {
                f5 = ((float) rect.width()) * adjustData[1].floatValue();
                f3 = ((float) rect.height()) * adjustData[1].floatValue();
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                f2 = ((float) rect.width()) * 0.28f;
                f6 = (float) Math.round(((float) rect.height()) * 0.28f);
            } else {
                f6 = ((float) rect.height()) * adjustData[2].floatValue();
                f2 = ((float) rect.width()) * adjustData[2].floatValue();
            }
        }
        float height2 = ((float) rect.height()) - f;
        float height3 = height2 - ((((float) rect.height()) - f3) * 2.0f);
        path.moveTo(((float) rect.left) + f2, ((float) rect.top) + f3);
        path.lineTo(((float) rect.left) + f2, (float) rect.bottom);
        path.lineTo((float) rect.left, ((float) rect.bottom) - (height2 / 2.0f));
        path.lineTo(((float) rect.left) + f2, ((float) rect.top) + f);
        path.lineTo(((float) rect.left) + f2, (((float) rect.top) + f3) - height3);
        float width2 = ((float) rect.width()) - f4;
        path.lineTo((((float) rect.left) + f5) - (width2 - ((((float) rect.width()) - f5) * 2.0f)), (((float) rect.top) + f3) - height3);
        path.lineTo((((float) rect.left) + f5) - (width2 - ((((float) rect.width()) - f5) * 2.0f)), ((float) rect.top) + f6);
        path.lineTo(((float) rect.left) + f4, ((float) rect.top) + f6);
        path.lineTo(((float) rect.right) - (width2 / 2.0f), (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + f6);
        path.lineTo(((float) rect.left) + f5, ((float) rect.top) + f6);
        path.lineTo(((float) rect.left) + f5, ((float) rect.top) + f3);
        path.close();
        return path;
    }

    private static Path getBentUpArrowPath(AutoShape autoShape, Rect rect) {
        float f;
        float f2;
        float f3;
        int i;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            f = (float) Math.round(((float) rect.width()) * 0.43f);
            f2 = (float) Math.round(((float) rect.width()) * 0.86f);
            i = Math.round(((float) rect.height()) * 0.28f);
        } else {
            if (adjustData[0] != null) {
                f = ((float) rect.width()) * adjustData[0].floatValue();
            } else {
                f = (float) Math.round(((float) rect.width()) * 0.43f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                f2 = (float) Math.round(((float) rect.width()) * 0.86f);
            } else {
                f2 = ((float) rect.width()) * adjustData[1].floatValue();
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i = Math.round(((float) rect.height()) * 0.28f);
            } else {
                f3 = ((float) rect.height()) * adjustData[2].floatValue();
                float width = ((float) rect.width()) - f;
                float width2 = width - ((((float) rect.width()) - f2) * 2.0f);
                path.moveTo((((float) rect.left) + f2) - width2, ((float) rect.top) + f3);
                path.lineTo(((float) rect.left) + f, ((float) rect.top) + f3);
                path.lineTo(((float) rect.right) - (width / 2.0f), (float) rect.top);
                path.lineTo((float) rect.right, ((float) rect.top) + f3);
                path.lineTo(((float) rect.left) + f2, ((float) rect.top) + f3);
                path.lineTo(((float) rect.left) + f2, (float) rect.bottom);
                path.lineTo((float) rect.left, (float) rect.bottom);
                float height = (((float) rect.height()) * width2) / ((float) rect.width());
                path.lineTo((float) rect.left, ((float) rect.bottom) - height);
                path.lineTo((((float) rect.left) + f2) - (width - ((((float) rect.width()) - f2) * 2.0f)), ((float) rect.bottom) - height);
                path.close();
                return path;
            }
        }
        f3 = (float) i;
        float width3 = ((float) rect.width()) - f;
        float width22 = width3 - ((((float) rect.width()) - f2) * 2.0f);
        path.moveTo((((float) rect.left) + f2) - width22, ((float) rect.top) + f3);
        path.lineTo(((float) rect.left) + f, ((float) rect.top) + f3);
        path.lineTo(((float) rect.right) - (width3 / 2.0f), (float) rect.top);
        path.lineTo((float) rect.right, ((float) rect.top) + f3);
        path.lineTo(((float) rect.left) + f2, ((float) rect.top) + f3);
        path.lineTo(((float) rect.left) + f2, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        float height2 = (((float) rect.height()) * width22) / ((float) rect.width());
        path.lineTo((float) rect.left, ((float) rect.bottom) - height2);
        path.lineTo((((float) rect.left) + f2) - (width3 - ((((float) rect.width()) - f2) * 2.0f)), ((float) rect.bottom) - height2);
        path.close();
        return path;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<android.graphics.Path> getCurvedRightArrowPath(com.app.office.common.shape.AutoShape r22, android.graphics.Rect r23) {
        /*
            r0 = r23
            java.util.ArrayList r1 = new java.util.ArrayList
            r2 = 2
            r1.<init>(r2)
            java.lang.Float[] r3 = r22.getAdjustData()
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            r5 = 1059760867(0x3f2aaae3, float:0.66667)
            r6 = 1063675494(0x3f666666, float:0.9)
            r7 = 1058642330(0x3f19999a, float:0.6)
            if (r3 == 0) goto L_0x0082
            int r8 = r3.length
            r9 = 1
            if (r8 < r9) goto L_0x0082
            r8 = 0
            r10 = r3[r8]
            if (r10 == 0) goto L_0x0033
            int r7 = r23.height()
            float r7 = (float) r7
            r8 = r3[r8]
            float r8 = r8.floatValue()
            float r7 = r7 * r8
            goto L_0x003f
        L_0x0033:
            int r8 = r23.height()
            float r8 = (float) r8
            float r8 = r8 * r7
            int r7 = java.lang.Math.round(r8)
            float r7 = (float) r7
        L_0x003f:
            int r8 = r3.length
            if (r8 < r2) goto L_0x0054
            r8 = r3[r9]
            if (r8 == 0) goto L_0x0054
            int r6 = r23.height()
            float r6 = (float) r6
            r8 = r3[r9]
            float r8 = r8.floatValue()
            float r6 = r6 * r8
            goto L_0x0060
        L_0x0054:
            int r8 = r23.height()
            float r8 = (float) r8
            float r8 = r8 * r6
            int r6 = java.lang.Math.round(r8)
            float r6 = (float) r6
        L_0x0060:
            int r8 = r3.length
            r9 = 3
            if (r8 < r9) goto L_0x0076
            r8 = r3[r2]
            if (r8 == 0) goto L_0x0076
            int r5 = r23.width()
            float r5 = (float) r5
            r3 = r3[r2]
            float r3 = r3.floatValue()
            float r5 = r5 * r3
            goto L_0x00a6
        L_0x0076:
            int r3 = r23.width()
            float r3 = (float) r3
            float r3 = r3 * r5
            int r3 = java.lang.Math.round(r3)
            goto L_0x00a5
        L_0x0082:
            int r3 = r23.height()
            float r3 = (float) r3
            float r3 = r3 * r7
            int r3 = java.lang.Math.round(r3)
            float r7 = (float) r3
            int r3 = r23.height()
            float r3 = (float) r3
            float r3 = r3 * r6
            int r3 = java.lang.Math.round(r3)
            float r6 = (float) r3
            int r3 = r23.width()
            float r3 = (float) r3
            float r3 = r3 * r5
            int r3 = java.lang.Math.round(r3)
        L_0x00a5:
            float r5 = (float) r3
        L_0x00a6:
            int r3 = r23.height()
            float r3 = (float) r3
            r8 = 1053609165(0x3ecccccd, float:0.4)
            float r3 = r3 * r8
            int r8 = r23.height()
            float r8 = (float) r8
            float r8 = r8 - r7
            float r6 = r6 - r7
            float r6 = r3 - r6
            r7 = 1073741824(0x40000000, float:2.0)
            float r6 = r6 * r7
            float r3 = r3 - r6
            r6 = 0
            int r9 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r9 >= 0) goto L_0x00c4
            r3 = 0
        L_0x00c4:
            int r6 = r23.width()
            float r6 = (float) r6
            float r6 = r6 - r5
            int r5 = r23.width()
            int r5 = r5 * 2
            float r2 = (float) r5
            int r5 = r0.bottom
            float r5 = (float) r5
            float r8 = r8 / r7
            float r5 = r5 - r8
            float r9 = r3 / r7
            float r5 = r5 - r9
            int r10 = r0.top
            float r10 = (float) r10
            float r5 = r5 - r10
            int r10 = r0.right
            float r10 = (float) r10
            int r11 = r0.top
            float r11 = (float) r11
            r4.moveTo(r10, r11)
            android.graphics.RectF r10 = s_rect
            int r11 = r0.left
            float r11 = (float) r11
            int r12 = r0.top
            float r12 = (float) r12
            int r13 = r0.left
            float r13 = (float) r13
            float r13 = r13 + r2
            int r14 = r0.top
            float r14 = (float) r14
            float r14 = r14 + r5
            r10.set(r11, r12, r13, r14)
            android.graphics.RectF r10 = s_rect
            r11 = 1132920832(0x43870000, float:270.0)
            r12 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r4.arcTo(r10, r11, r12)
            int r10 = r0.left
            float r10 = (float) r10
            int r11 = r0.top
            float r11 = (float) r11
            float r12 = r5 / r7
            float r11 = r11 + r12
            float r11 = r11 + r3
            r4.lineTo(r10, r11)
            android.graphics.RectF r10 = s_rect
            int r11 = r0.left
            float r11 = (float) r11
            int r13 = r0.top
            float r13 = (float) r13
            float r13 = r13 + r3
            int r14 = r0.left
            float r14 = (float) r14
            float r14 = r14 + r2
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r5
            float r15 = r15 + r3
            r10.set(r11, r13, r14, r15)
            android.graphics.RectF r10 = s_rect
            r11 = 1119092736(0x42b40000, float:90.0)
            r13 = 1127481344(0x43340000, float:180.0)
            r4.arcTo(r10, r13, r11)
            r4.close()
            r1.add(r4)
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            int r10 = r0.left
            float r10 = (float) r10
            int r11 = r0.top
            float r11 = (float) r11
            float r11 = r11 + r12
            r4.moveTo(r10, r11)
            double r10 = (double) r12
            r14 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r10 = java.lang.Math.pow(r10, r14)
            float r7 = r2 / r7
            r22 = r8
            double r7 = (double) r7
            double r16 = java.lang.Math.pow(r7, r14)
            r18 = r3
            r19 = r4
            double r3 = (double) r6
            double r20 = java.lang.Math.pow(r3, r14)
            double r16 = r16 - r20
            double r10 = r10 * r16
            double r7 = java.lang.Math.pow(r7, r14)
            double r10 = r10 / r7
            double r7 = java.lang.Math.sqrt(r10)
            double r3 = r7 / r3
            double r3 = java.lang.Math.atan(r3)
            r10 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r3 = r3 * r10
            r10 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r3 = r3 / r10
            int r3 = (int) r3
            android.graphics.RectF r4 = s_rect
            int r10 = r0.left
            float r10 = (float) r10
            int r11 = r0.top
            float r11 = (float) r11
            int r14 = r0.left
            float r14 = (float) r14
            float r14 = r14 + r2
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r5
            r4.set(r10, r11, r14, r15)
            android.graphics.RectF r4 = s_rect
            int r10 = -r3
            float r10 = (float) r10
            r11 = r19
            r11.arcTo(r4, r13, r10)
            int r4 = r0.right
            float r4 = (float) r4
            float r4 = r4 - r6
            int r10 = r0.top
            float r10 = (float) r10
            float r10 = r10 + r12
            int r7 = (int) r7
            float r7 = (float) r7
            float r10 = r10 + r7
            r11.setLastPoint(r4, r10)
            int r4 = r0.right
            float r4 = (float) r4
            float r4 = r4 - r6
            int r8 = r0.top
            float r8 = (float) r8
            float r8 = r8 + r12
            float r8 = r8 + r7
            float r8 = r8 + r9
            float r8 = r8 - r22
            r11.lineTo(r4, r8)
            int r4 = r0.right
            float r4 = (float) r4
            int r8 = r0.bottom
            float r8 = (float) r8
            float r8 = r8 - r22
            r11.lineTo(r4, r8)
            int r4 = r0.right
            float r4 = (float) r4
            float r4 = r4 - r6
            int r8 = r0.top
            float r8 = (float) r8
            float r8 = r8 + r12
            float r8 = r8 + r7
            float r8 = r8 + r9
            float r8 = r8 + r22
            r11.lineTo(r4, r8)
            int r4 = r0.right
            float r4 = (float) r4
            float r4 = r4 - r6
            int r6 = r0.top
            float r6 = (float) r6
            float r6 = r6 + r12
            float r6 = r6 + r7
            float r6 = r6 + r18
            r11.lineTo(r4, r6)
            android.graphics.RectF r4 = s_rect
            int r6 = r0.left
            float r6 = (float) r6
            int r7 = r0.top
            float r7 = (float) r7
            float r7 = r7 + r18
            int r8 = r0.left
            float r8 = (float) r8
            float r8 = r8 + r2
            int r0 = r0.top
            float r0 = (float) r0
            float r0 = r0 + r5
            float r0 = r0 + r18
            r4.set(r6, r7, r8, r0)
            android.graphics.RectF r0 = s_rect
            int r2 = 180 - r3
            float r2 = (float) r2
            float r3 = (float) r3
            r11.arcTo(r0, r2, r3)
            r11.close()
            r1.add(r11)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.arrow.EarlyArrowPathBuilder.getCurvedRightArrowPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<android.graphics.Path> getCurvedLeftArrowPath(com.app.office.common.shape.AutoShape r22, android.graphics.Rect r23) {
        /*
            r0 = r23
            java.util.ArrayList r1 = new java.util.ArrayList
            r2 = 2
            r1.<init>(r2)
            java.lang.Float[] r3 = r22.getAdjustData()
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            r5 = 1059760867(0x3f2aaae3, float:0.66667)
            r6 = 1063675494(0x3f666666, float:0.9)
            r7 = 1058642330(0x3f19999a, float:0.6)
            if (r3 == 0) goto L_0x0082
            int r8 = r3.length
            r9 = 1
            if (r8 < r9) goto L_0x0082
            r8 = 0
            r10 = r3[r8]
            if (r10 == 0) goto L_0x0033
            int r7 = r23.height()
            float r7 = (float) r7
            r8 = r3[r8]
            float r8 = r8.floatValue()
            float r7 = r7 * r8
            goto L_0x003f
        L_0x0033:
            int r8 = r23.height()
            float r8 = (float) r8
            float r8 = r8 * r7
            int r7 = java.lang.Math.round(r8)
            float r7 = (float) r7
        L_0x003f:
            int r8 = r3.length
            if (r8 < r2) goto L_0x0054
            r8 = r3[r9]
            if (r8 == 0) goto L_0x0054
            int r6 = r23.height()
            float r6 = (float) r6
            r8 = r3[r9]
            float r8 = r8.floatValue()
            float r6 = r6 * r8
            goto L_0x0060
        L_0x0054:
            int r8 = r23.height()
            float r8 = (float) r8
            float r8 = r8 * r6
            int r6 = java.lang.Math.round(r8)
            float r6 = (float) r6
        L_0x0060:
            int r8 = r3.length
            r9 = 3
            if (r8 < r9) goto L_0x0076
            r8 = r3[r2]
            if (r8 == 0) goto L_0x0076
            int r5 = r23.width()
            float r5 = (float) r5
            r3 = r3[r2]
            float r3 = r3.floatValue()
            float r5 = r5 * r3
            goto L_0x00a6
        L_0x0076:
            int r3 = r23.width()
            float r3 = (float) r3
            float r3 = r3 * r5
            int r3 = java.lang.Math.round(r3)
            goto L_0x00a5
        L_0x0082:
            int r3 = r23.height()
            float r3 = (float) r3
            float r3 = r3 * r7
            int r3 = java.lang.Math.round(r3)
            float r7 = (float) r3
            int r3 = r23.height()
            float r3 = (float) r3
            float r3 = r3 * r6
            int r3 = java.lang.Math.round(r3)
            float r6 = (float) r3
            int r3 = r23.width()
            float r3 = (float) r3
            float r3 = r3 * r5
            int r3 = java.lang.Math.round(r3)
        L_0x00a5:
            float r5 = (float) r3
        L_0x00a6:
            int r3 = r23.height()
            float r3 = (float) r3
            r8 = 1053609165(0x3ecccccd, float:0.4)
            float r3 = r3 * r8
            int r8 = r23.height()
            float r8 = (float) r8
            float r8 = r8 - r7
            float r6 = r6 - r7
            float r6 = r3 - r6
            r7 = 1073741824(0x40000000, float:2.0)
            float r6 = r6 * r7
            float r3 = r3 - r6
            r6 = 0
            int r9 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r9 >= 0) goto L_0x00c4
            r3 = 0
        L_0x00c4:
            int r9 = r23.width()
            int r9 = r9 * 2
            float r2 = (float) r9
            int r9 = r0.bottom
            float r9 = (float) r9
            float r8 = r8 / r7
            float r9 = r9 - r8
            float r10 = r3 / r7
            float r9 = r9 - r10
            int r11 = r0.top
            float r11 = (float) r11
            float r9 = r9 - r11
            int r11 = r0.right
            float r11 = (float) r11
            int r12 = r0.top
            float r12 = (float) r12
            float r13 = r9 / r7
            float r12 = r12 + r13
            r4.moveTo(r11, r12)
            double r11 = (double) r13
            r14 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r11 = java.lang.Math.pow(r11, r14)
            float r7 = r2 / r7
            double r6 = (double) r7
            double r16 = java.lang.Math.pow(r6, r14)
            r19 = r3
            r18 = r4
            double r3 = (double) r5
            double r20 = java.lang.Math.pow(r3, r14)
            double r16 = r16 - r20
            double r11 = r11 * r16
            double r6 = java.lang.Math.pow(r6, r14)
            double r11 = r11 / r6
            double r6 = java.lang.Math.sqrt(r11)
            double r3 = r6 / r3
            double r3 = java.lang.Math.atan(r3)
            r11 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r3 = r3 * r11
            r11 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r3 = r3 / r11
            int r3 = (int) r3
            android.graphics.RectF r4 = s_rect
            int r11 = r0.right
            float r11 = (float) r11
            float r11 = r11 - r2
            int r12 = r0.top
            float r12 = (float) r12
            int r14 = r0.right
            float r14 = (float) r14
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r9
            r4.set(r11, r12, r14, r15)
            android.graphics.RectF r4 = s_rect
            float r11 = (float) r3
            r12 = r18
            r14 = 0
            r12.arcTo(r4, r14, r11)
            int r4 = r0.left
            float r4 = (float) r4
            float r4 = r4 + r5
            int r14 = r0.top
            float r14 = (float) r14
            float r14 = r14 + r13
            int r6 = (int) r6
            float r6 = (float) r6
            float r14 = r14 + r6
            r12.setLastPoint(r4, r14)
            int r4 = r0.left
            float r4 = (float) r4
            float r4 = r4 + r5
            int r7 = r0.top
            float r7 = (float) r7
            float r7 = r7 + r13
            float r7 = r7 + r6
            float r7 = r7 + r10
            float r7 = r7 - r8
            r12.lineTo(r4, r7)
            int r4 = r0.left
            float r4 = (float) r4
            int r7 = r0.bottom
            float r7 = (float) r7
            float r7 = r7 - r8
            r12.lineTo(r4, r7)
            int r4 = r0.left
            float r4 = (float) r4
            float r4 = r4 + r5
            int r7 = r0.top
            float r7 = (float) r7
            float r7 = r7 + r13
            float r7 = r7 + r6
            float r7 = r7 + r10
            float r7 = r7 + r8
            r12.lineTo(r4, r7)
            int r4 = r0.left
            float r4 = (float) r4
            float r4 = r4 + r5
            int r5 = r0.top
            float r5 = (float) r5
            float r5 = r5 + r13
            float r5 = r5 + r6
            float r5 = r5 + r19
            r12.lineTo(r4, r5)
            android.graphics.RectF r4 = s_rect
            int r5 = r0.right
            float r5 = (float) r5
            float r5 = r5 - r2
            int r6 = r0.top
            float r6 = (float) r6
            float r6 = r6 + r19
            int r7 = r0.right
            float r7 = (float) r7
            int r8 = r0.top
            float r8 = (float) r8
            float r8 = r8 + r9
            float r8 = r8 + r19
            r4.set(r5, r6, r7, r8)
            android.graphics.RectF r4 = s_rect
            int r3 = -r3
            float r3 = (float) r3
            r12.arcTo(r4, r11, r3)
            r12.close()
            r1.add(r12)
            android.graphics.Path r3 = new android.graphics.Path
            r3.<init>()
            int r4 = r0.left
            float r4 = (float) r4
            int r5 = r0.top
            float r5 = (float) r5
            r3.moveTo(r4, r5)
            android.graphics.RectF r4 = s_rect
            int r5 = r0.right
            float r5 = (float) r5
            float r5 = r5 - r2
            int r6 = r0.top
            float r6 = (float) r6
            int r7 = r0.right
            float r7 = (float) r7
            int r8 = r0.top
            float r8 = (float) r8
            float r8 = r8 + r9
            r4.set(r5, r6, r7, r8)
            android.graphics.RectF r4 = s_rect
            r5 = 1132920832(0x43870000, float:270.0)
            r6 = 1119092736(0x42b40000, float:90.0)
            r3.arcTo(r4, r5, r6)
            int r4 = r0.right
            float r4 = (float) r4
            int r5 = r0.top
            float r5 = (float) r5
            float r5 = r5 + r13
            float r5 = r5 + r19
            r3.lineTo(r4, r5)
            android.graphics.RectF r4 = s_rect
            int r5 = r0.right
            float r5 = (float) r5
            float r5 = r5 - r2
            int r2 = r0.top
            float r2 = (float) r2
            float r2 = r2 + r19
            int r6 = r0.right
            float r6 = (float) r6
            int r0 = r0.top
            float r0 = (float) r0
            float r0 = r0 + r9
            float r0 = r0 + r19
            r4.set(r5, r2, r6, r0)
            android.graphics.RectF r0 = s_rect
            r2 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r4 = 0
            r3.arcTo(r0, r4, r2)
            r3.close()
            r1.add(r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.arrow.EarlyArrowPathBuilder.getCurvedLeftArrowPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<android.graphics.Path> getCurvedUpArrowPath(com.app.office.common.shape.AutoShape r21, android.graphics.Rect r22) {
        /*
            r0 = r22
            java.util.ArrayList r1 = new java.util.ArrayList
            r2 = 2
            r1.<init>(r2)
            java.lang.Float[] r3 = r21.getAdjustData()
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            r5 = 1063675494(0x3f666666, float:0.9)
            r6 = 1058642330(0x3f19999a, float:0.6)
            if (r3 == 0) goto L_0x0082
            int r7 = r3.length
            r8 = 1
            if (r7 < r8) goto L_0x0082
            r7 = 0
            r9 = r3[r7]
            if (r9 == 0) goto L_0x0030
            int r6 = r22.width()
            float r6 = (float) r6
            r7 = r3[r7]
            float r7 = r7.floatValue()
            float r6 = r6 * r7
            goto L_0x003c
        L_0x0030:
            int r7 = r22.width()
            float r7 = (float) r7
            float r7 = r7 * r6
            int r6 = java.lang.Math.round(r7)
            float r6 = (float) r6
        L_0x003c:
            int r7 = r3.length
            if (r7 < r2) goto L_0x0051
            r7 = r3[r8]
            if (r7 == 0) goto L_0x0051
            int r5 = r22.width()
            float r5 = (float) r5
            r7 = r3[r8]
            float r7 = r7.floatValue()
            float r5 = r5 * r7
            goto L_0x005d
        L_0x0051:
            int r7 = r22.width()
            float r7 = (float) r7
            float r7 = r7 * r5
            int r5 = java.lang.Math.round(r7)
            float r5 = (float) r5
        L_0x005d:
            int r7 = r3.length
            r8 = 3
            if (r7 < r8) goto L_0x0073
            r7 = r3[r2]
            if (r7 == 0) goto L_0x0073
            int r7 = r22.height()
            float r7 = (float) r7
            r2 = r3[r2]
            float r2 = r2.floatValue()
            float r7 = r7 * r2
            goto L_0x00a9
        L_0x0073:
            int r2 = r22.height()
            float r2 = (float) r2
            r3 = 1059760867(0x3f2aaae3, float:0.66667)
            float r2 = r2 * r3
            int r2 = java.lang.Math.round(r2)
            goto L_0x00a8
        L_0x0082:
            int r2 = r22.width()
            float r2 = (float) r2
            float r2 = r2 * r6
            int r2 = java.lang.Math.round(r2)
            float r6 = (float) r2
            int r2 = r22.width()
            float r2 = (float) r2
            float r2 = r2 * r5
            int r2 = java.lang.Math.round(r2)
            float r5 = (float) r2
            int r2 = r22.height()
            float r2 = (float) r2
            r3 = 1051372091(0x3eaaaa3b, float:0.33333)
            float r2 = r2 * r3
            int r2 = java.lang.Math.round(r2)
        L_0x00a8:
            float r7 = (float) r2
        L_0x00a9:
            int r2 = r22.width()
            float r2 = (float) r2
            r3 = 1053609165(0x3ecccccd, float:0.4)
            float r2 = r2 * r3
            int r3 = r22.width()
            float r3 = (float) r3
            float r3 = r3 - r6
            float r5 = r5 - r6
            float r5 = r2 - r5
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 * r6
            float r2 = r2 - r5
            r5 = 0
            int r8 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r8 >= 0) goto L_0x00c7
            r2 = 0
        L_0x00c7:
            int r5 = r22.width()
            float r5 = (float) r5
            float r3 = r3 / r6
            float r5 = r5 - r3
            float r8 = r2 / r6
            float r5 = r5 - r8
            float r5 = r5 / r6
            int r9 = r22.height()
            float r9 = (float) r9
            int r10 = r0.left
            float r10 = (float) r10
            float r10 = r10 + r5
            int r11 = r0.bottom
            float r11 = (float) r11
            r4.moveTo(r10, r11)
            double r10 = (double) r5
            r12 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r10 = java.lang.Math.pow(r10, r12)
            double r14 = (double) r9
            double r16 = java.lang.Math.pow(r14, r12)
            r18 = r1
            r21 = r2
            double r1 = (double) r7
            double r19 = java.lang.Math.pow(r1, r12)
            double r16 = r16 - r19
            double r10 = r10 * r16
            double r12 = java.lang.Math.pow(r14, r12)
            double r10 = r10 / r12
            double r10 = java.lang.Math.sqrt(r10)
            double r1 = r10 / r1
            double r1 = java.lang.Math.atan(r1)
            r12 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r1 = r1 * r12
            r12 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r1 = r1 / r12
            int r1 = (int) r1
            android.graphics.RectF r2 = s_rect
            int r12 = r0.left
            float r12 = (float) r12
            int r13 = r0.top
            float r13 = (float) r13
            float r13 = r13 - r9
            int r14 = r0.left
            float r14 = (float) r14
            float r6 = r6 * r5
            float r14 = r14 + r6
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r9
            r2.set(r12, r13, r14, r15)
            android.graphics.RectF r2 = s_rect
            int r12 = -r1
            float r12 = (float) r12
            r13 = 1119092736(0x42b40000, float:90.0)
            r4.arcTo(r2, r13, r12)
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r5
            float r10 = (float) r10
            float r2 = r2 + r10
            int r11 = r0.top
            float r11 = (float) r11
            float r11 = r11 + r7
            r4.setLastPoint(r2, r11)
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r5
            float r2 = r2 + r10
            float r2 = r2 + r8
            float r2 = r2 - r3
            int r11 = r0.top
            float r11 = (float) r11
            float r11 = r11 + r7
            r4.lineTo(r2, r11)
            int r2 = r0.right
            float r2 = (float) r2
            float r2 = r2 - r3
            int r11 = r0.top
            float r11 = (float) r11
            r4.lineTo(r2, r11)
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r5
            float r2 = r2 + r10
            float r2 = r2 + r8
            float r2 = r2 + r3
            int r3 = r0.top
            float r3 = (float) r3
            float r3 = r3 + r7
            r4.lineTo(r2, r3)
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r5
            float r2 = r2 + r10
            float r2 = r2 + r21
            int r3 = r0.top
            float r3 = (float) r3
            float r3 = r3 + r7
            r4.lineTo(r2, r3)
            android.graphics.RectF r2 = s_rect
            int r3 = r0.left
            float r3 = (float) r3
            float r3 = r3 + r21
            int r7 = r0.top
            float r7 = (float) r7
            float r7 = r7 - r9
            int r8 = r0.left
            float r8 = (float) r8
            float r8 = r8 + r6
            float r8 = r8 + r21
            int r10 = r0.top
            float r10 = (float) r10
            float r10 = r10 + r9
            r2.set(r3, r7, r8, r10)
            android.graphics.RectF r2 = s_rect
            int r3 = 90 - r1
            float r3 = (float) r3
            float r1 = (float) r1
            r4.arcTo(r2, r3, r1)
            r4.close()
            r1 = r18
            r1.add(r4)
            android.graphics.Path r2 = new android.graphics.Path
            r2.<init>()
            int r3 = r0.left
            float r3 = (float) r3
            int r4 = r0.top
            float r4 = (float) r4
            r2.moveTo(r3, r4)
            android.graphics.RectF r3 = s_rect
            int r4 = r0.left
            float r4 = (float) r4
            int r7 = r0.top
            float r7 = (float) r7
            float r7 = r7 - r9
            int r8 = r0.left
            float r8 = (float) r8
            float r8 = r8 + r6
            int r10 = r0.top
            float r10 = (float) r10
            float r10 = r10 + r9
            r3.set(r4, r7, r8, r10)
            android.graphics.RectF r3 = s_rect
            r4 = 1127481344(0x43340000, float:180.0)
            r7 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r2.arcTo(r3, r4, r7)
            int r3 = r0.left
            float r3 = (float) r3
            float r3 = r3 + r5
            float r3 = r3 + r21
            int r4 = r0.bottom
            float r4 = (float) r4
            r2.lineTo(r3, r4)
            android.graphics.RectF r3 = s_rect
            int r4 = r0.left
            float r4 = (float) r4
            float r4 = r4 + r21
            int r5 = r0.top
            float r5 = (float) r5
            float r5 = r5 - r9
            int r7 = r0.left
            float r7 = (float) r7
            float r7 = r7 + r6
            float r7 = r7 + r21
            int r0 = r0.top
            float r0 = (float) r0
            float r0 = r0 + r9
            r3.set(r4, r5, r7, r0)
            android.graphics.RectF r0 = s_rect
            r2.arcTo(r0, r13, r13)
            r2.close()
            r1.add(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.arrow.EarlyArrowPathBuilder.getCurvedUpArrowPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<android.graphics.Path> getCurvedDownArrowPath(com.app.office.common.shape.AutoShape r22, android.graphics.Rect r23) {
        /*
            r0 = r23
            java.util.ArrayList r1 = new java.util.ArrayList
            r2 = 2
            r1.<init>(r2)
            java.lang.Float[] r3 = r22.getAdjustData()
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            r5 = 1063675494(0x3f666666, float:0.9)
            r6 = 1058642330(0x3f19999a, float:0.6)
            if (r3 == 0) goto L_0x0082
            int r7 = r3.length
            r8 = 1
            if (r7 < r8) goto L_0x0082
            r7 = 0
            r9 = r3[r7]
            if (r9 == 0) goto L_0x0030
            int r6 = r23.width()
            float r6 = (float) r6
            r7 = r3[r7]
            float r7 = r7.floatValue()
            float r6 = r6 * r7
            goto L_0x003c
        L_0x0030:
            int r7 = r23.width()
            float r7 = (float) r7
            float r7 = r7 * r6
            int r6 = java.lang.Math.round(r7)
            float r6 = (float) r6
        L_0x003c:
            int r7 = r3.length
            if (r7 < r2) goto L_0x0051
            r7 = r3[r8]
            if (r7 == 0) goto L_0x0051
            int r5 = r23.width()
            float r5 = (float) r5
            r7 = r3[r8]
            float r7 = r7.floatValue()
            float r5 = r5 * r7
            goto L_0x005d
        L_0x0051:
            int r7 = r23.width()
            float r7 = (float) r7
            float r7 = r7 * r5
            int r5 = java.lang.Math.round(r7)
            float r5 = (float) r5
        L_0x005d:
            int r7 = r3.length
            r8 = 3
            if (r7 < r8) goto L_0x0073
            r7 = r3[r2]
            if (r7 == 0) goto L_0x0073
            int r7 = r23.height()
            float r7 = (float) r7
            r2 = r3[r2]
            float r2 = r2.floatValue()
            float r7 = r7 * r2
            goto L_0x00a9
        L_0x0073:
            int r2 = r23.height()
            float r2 = (float) r2
            r3 = 1059760867(0x3f2aaae3, float:0.66667)
            float r2 = r2 * r3
            int r2 = java.lang.Math.round(r2)
            goto L_0x00a8
        L_0x0082:
            int r2 = r23.width()
            float r2 = (float) r2
            float r2 = r2 * r6
            int r2 = java.lang.Math.round(r2)
            float r6 = (float) r2
            int r2 = r23.width()
            float r2 = (float) r2
            float r2 = r2 * r5
            int r2 = java.lang.Math.round(r2)
            float r5 = (float) r2
            int r2 = r23.height()
            float r2 = (float) r2
            r3 = 1059760816(0x3f2aaab0, float:0.666667)
            float r2 = r2 * r3
            int r2 = java.lang.Math.round(r2)
        L_0x00a8:
            float r7 = (float) r2
        L_0x00a9:
            int r2 = r23.width()
            float r2 = (float) r2
            r3 = 1053609165(0x3ecccccd, float:0.4)
            float r2 = r2 * r3
            int r3 = r23.width()
            float r3 = (float) r3
            float r3 = r3 - r6
            float r5 = r5 - r6
            float r5 = r2 - r5
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 * r6
            float r2 = r2 - r5
            r5 = 0
            int r8 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r8 >= 0) goto L_0x00c7
            r2 = 0
        L_0x00c7:
            int r5 = r23.height()
            float r5 = (float) r5
            float r5 = r5 - r7
            int r7 = r23.width()
            float r7 = (float) r7
            float r3 = r3 / r6
            float r7 = r7 - r3
            float r8 = r2 / r6
            float r7 = r7 - r8
            float r7 = r7 / r6
            int r9 = r23.height()
            float r9 = (float) r9
            int r10 = r0.left
            float r10 = (float) r10
            int r11 = r0.bottom
            float r11 = (float) r11
            r4.moveTo(r10, r11)
            android.graphics.RectF r10 = s_rect
            int r11 = r0.left
            float r11 = (float) r11
            int r12 = r0.top
            float r12 = (float) r12
            int r13 = r0.left
            float r13 = (float) r13
            float r14 = r7 * r6
            float r13 = r13 + r14
            int r15 = r0.top
            float r15 = (float) r15
            float r6 = r6 * r9
            float r15 = r15 + r6
            r10.set(r11, r12, r13, r15)
            android.graphics.RectF r10 = s_rect
            r11 = 1127481344(0x43340000, float:180.0)
            r12 = 1119092736(0x42b40000, float:90.0)
            r4.arcTo(r10, r11, r12)
            int r10 = r0.left
            float r10 = (float) r10
            float r10 = r10 + r7
            float r10 = r10 + r2
            int r11 = r0.top
            float r11 = (float) r11
            r4.lineTo(r10, r11)
            android.graphics.RectF r10 = s_rect
            int r11 = r0.left
            float r11 = (float) r11
            float r11 = r11 + r2
            int r12 = r0.top
            float r12 = (float) r12
            int r13 = r0.left
            float r13 = (float) r13
            float r13 = r13 + r14
            float r13 = r13 + r2
            int r15 = r0.top
            float r15 = (float) r15
            float r15 = r15 + r6
            r10.set(r11, r12, r13, r15)
            android.graphics.RectF r10 = s_rect
            r11 = -1028390912(0xffffffffc2b40000, float:-90.0)
            r12 = 1132920832(0x43870000, float:270.0)
            r4.arcTo(r10, r12, r11)
            r4.close()
            r1.add(r4)
            android.graphics.Path r4 = new android.graphics.Path
            r4.<init>()
            int r10 = r0.left
            float r10 = (float) r10
            float r10 = r10 + r7
            int r11 = r0.top
            float r11 = (float) r11
            r4.moveTo(r10, r11)
            double r10 = (double) r7
            r12 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r10 = java.lang.Math.pow(r10, r12)
            r15 = r1
            r16 = r2
            double r1 = (double) r9
            double r17 = java.lang.Math.pow(r1, r12)
            r19 = r8
            double r8 = (double) r5
            double r20 = java.lang.Math.pow(r8, r12)
            double r17 = r17 - r20
            double r10 = r10 * r17
            double r1 = java.lang.Math.pow(r1, r12)
            double r10 = r10 / r1
            double r1 = java.lang.Math.sqrt(r10)
            double r8 = r1 / r8
            double r8 = java.lang.Math.atan(r8)
            r10 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r8 = r8 * r10
            r10 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r8 = r8 / r10
            int r8 = (int) r8
            android.graphics.RectF r9 = s_rect
            int r10 = r0.left
            float r10 = (float) r10
            int r11 = r0.top
            float r11 = (float) r11
            int r12 = r0.left
            float r12 = (float) r12
            float r12 = r12 + r14
            int r13 = r0.top
            float r13 = (float) r13
            float r13 = r13 + r6
            r9.set(r10, r11, r12, r13)
            android.graphics.RectF r9 = s_rect
            float r10 = (float) r8
            r11 = 1132920832(0x43870000, float:270.0)
            r4.arcTo(r9, r11, r10)
            int r9 = r0.left
            float r9 = (float) r9
            float r9 = r9 + r7
            float r1 = (float) r1
            float r9 = r9 + r1
            int r2 = r0.bottom
            float r2 = (float) r2
            float r2 = r2 - r5
            r4.setLastPoint(r9, r2)
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r7
            float r2 = r2 + r1
            float r2 = r2 + r19
            float r2 = r2 - r3
            int r9 = r0.bottom
            float r9 = (float) r9
            float r9 = r9 - r5
            r4.lineTo(r2, r9)
            int r2 = r0.right
            float r2 = (float) r2
            float r2 = r2 - r3
            int r9 = r0.bottom
            float r9 = (float) r9
            r4.lineTo(r2, r9)
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r7
            float r2 = r2 + r1
            float r2 = r2 + r19
            float r2 = r2 + r3
            int r3 = r0.bottom
            float r3 = (float) r3
            float r3 = r3 - r5
            r4.lineTo(r2, r3)
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r7
            float r2 = r2 + r1
            float r2 = r2 + r16
            int r1 = r0.bottom
            float r1 = (float) r1
            float r1 = r1 - r5
            r4.lineTo(r2, r1)
            android.graphics.RectF r1 = s_rect
            int r2 = r0.left
            float r2 = (float) r2
            float r2 = r2 + r16
            int r3 = r0.top
            float r3 = (float) r3
            int r5 = r0.left
            float r5 = (float) r5
            float r5 = r5 + r14
            float r5 = r5 + r16
            int r0 = r0.top
            float r0 = (float) r0
            float r0 = r0 + r6
            r1.set(r2, r3, r5, r0)
            android.graphics.RectF r0 = s_rect
            int r1 = r8 + 270
            float r1 = (float) r1
            int r2 = -r8
            float r2 = (float) r2
            r4.arcTo(r0, r1, r2)
            r4.close()
            r0 = r15
            r0.add(r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.common.autoshape.pathbuilder.arrow.EarlyArrowPathBuilder.getCurvedDownArrowPath(com.app.office.common.shape.AutoShape, android.graphics.Rect):java.util.List");
    }

    private static Path getStripedRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.75f);
            i = Math.round(((float) rect.height()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.75f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
        }
        float width = ((float) rect.width()) * 0.03f;
        path.addRect((float) rect.left, (float) (rect.top + i), ((float) rect.left) + width, (float) (rect.bottom - i), Path.Direction.CW);
        path.addRect(((float) rect.left) + (2.0f * width), (float) (rect.top + i), ((float) rect.left) + (4.0f * width), (float) (rect.bottom - i), Path.Direction.CW);
        float f = width * 5.0f;
        path.moveTo(((float) rect.left) + f, (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo(((float) rect.left) + f, (float) (rect.bottom - i));
        path.close();
        return path;
    }

    private static Path getNotchedRightArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.75f);
            i = Math.round(((float) rect.height()) * 0.25f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.75f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) (rect.bottom - i));
        path.lineTo(((float) rect.left) + (((float) ((rect.height() - (i * 2)) * (rect.width() - i2))) / ((float) rect.height())), (float) rect.centerY());
        path.lineTo((float) rect.left, (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.close();
        return path;
    }

    private static Path getHomePlatePath(AutoShape autoShape, Rect rect) {
        int i;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length != 1 || adjustData[0] == null) {
            i = Math.round(((float) rect.width()) * 0.75f);
        } else {
            i = Math.round(((float) rect.width()) * adjustData[0].floatValue());
        }
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) (rect.left + i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getChevronPath(AutoShape autoShape, Rect rect) {
        int i;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length != 1 || adjustData[0] == null) {
            i = Math.round(((float) rect.width()) * 0.75f);
        } else {
            i = Math.round(((float) rect.width()) * adjustData[0].floatValue());
        }
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) (rect.left + i), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.left + i), (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) ((rect.left + rect.width()) - i), (float) rect.centerY());
        path.close();
        return path;
    }

    private static Path getRightArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.67f);
            i3 = Math.round(((float) rect.height()) * 0.25f);
            i4 = Math.round(((float) rect.width()) * 0.83f);
            i = Math.round(((float) rect.height()) * 0.375f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.67f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i3 = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i3 = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i4 = Math.round(((float) rect.width()) * 0.83f);
            } else {
                i4 = Math.round(((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length < 4 || adjustData[3] == null) {
                i = Math.round(((float) rect.height()) * 0.375f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) rect.top);
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i4), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i4), (float) (rect.top + i3));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.left + i4), (float) (rect.bottom - i3));
        path.lineTo((float) (rect.left + i4), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
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
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.33f);
            i3 = Math.round(((float) rect.height()) * 0.25f);
            i4 = Math.round(((float) rect.width()) * 0.17f);
            i = Math.round(((float) rect.height()) * 0.375f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.33f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i3 = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i3 = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i4 = Math.round(((float) rect.width()) * 0.17f);
            } else {
                i4 = Math.round(((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length < 4 || adjustData[3] == null) {
                i = Math.round(((float) rect.height()) * 0.375f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i4), (float) (rect.top + i3));
        path.lineTo((float) (rect.left + i4), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
        path.lineTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i4), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i4), (float) (rect.bottom - i3));
        path.close();
        return path;
    }

    private static Path getUpArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.height()) * 0.33f);
            i3 = Math.round(((float) rect.width()) * 0.25f);
            i4 = Math.round(((float) rect.height()) * 0.17f);
            i = Math.round(((float) rect.width()) * 0.375f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.height()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.height()) * 0.33f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i3 = Math.round(((float) rect.width()) * 0.25f);
            } else {
                i3 = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i4 = Math.round(((float) rect.height()) * 0.17f);
            } else {
                i4 = Math.round(((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData.length < 4 || adjustData[3] == null) {
                i = Math.round(((float) rect.width()) * 0.375f);
            } else {
                i = Math.round(((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) (rect.top + i2));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i2));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i4));
        path.lineTo((float) (rect.left + i3), (float) (rect.top + i4));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.right - i3), (float) (rect.top + i4));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i4));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i2));
        path.lineTo((float) rect.right, (float) (rect.top + i2));
        path.lineTo((float) rect.right, (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getDownArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.height()) * 0.67f);
            i3 = Math.round(((float) rect.width()) * 0.25f);
            i4 = Math.round(((float) rect.height()) * 0.83f);
            i = Math.round(((float) rect.width()) * 0.375f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.height()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.height()) * 0.67f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i3 = Math.round(((float) rect.width()) * 0.25f);
            } else {
                i3 = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i4 = Math.round(((float) rect.height()) * 0.83f);
            } else {
                i4 = Math.round(((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData.length < 4 || adjustData[3] == null) {
                i = Math.round(((float) rect.width()) * 0.375f);
            } else {
                i = Math.round(((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) (rect.top + i2));
        path.lineTo((float) rect.left, (float) rect.top);
        path.lineTo((float) rect.right, (float) rect.top);
        path.lineTo((float) rect.right, (float) (rect.top + i2));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i2));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i4));
        path.lineTo((float) (rect.right - i3), (float) (rect.top + i4));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) (rect.left + i3), (float) (rect.top + i4));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i4));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i2));
        path.close();
        return path;
    }

    private static Path getLeftRightArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.width()) * 0.35f);
            i3 = Math.round(((float) rect.height()) * 0.25f);
            i4 = Math.round(((float) rect.width()) * 0.13f);
            i = Math.round(((float) rect.height()) * 0.375f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.width()) * 0.35f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i3 = Math.round(((float) rect.height()) * 0.25f);
            } else {
                i3 = Math.round(((float) rect.height()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i4 = Math.round(((float) rect.width()) * 0.13f);
            } else {
                i4 = Math.round(((float) rect.width()) * adjustData[2].floatValue());
            }
            if (adjustData.length < 4 || adjustData[3] == null) {
                i = Math.round(((float) rect.height()) * 0.375f);
            } else {
                i = Math.round(((float) rect.height()) * adjustData[3].floatValue());
            }
        }
        path.moveTo((float) (rect.left + i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i4), (float) (rect.bottom - i));
        path.lineTo((float) (rect.left + i4), (float) (rect.bottom - i3));
        path.lineTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i4), (float) (rect.top + i3));
        path.lineTo((float) (rect.left + i4), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) (rect.top + i));
        path.lineTo((float) (rect.left + i2), (float) rect.top);
        path.lineTo((float) (rect.right - i2), (float) rect.top);
        path.lineTo((float) (rect.right - i2), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i4), (float) (rect.top + i));
        path.lineTo((float) (rect.right - i4), (float) (rect.top + i3));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i4), (float) (rect.bottom - i3));
        path.lineTo((float) (rect.right - i4), (float) (rect.bottom - i));
        path.lineTo((float) (rect.right - i2), (float) (rect.bottom - i));
        path.lineTo((float) (rect.right - i2), (float) rect.bottom);
        path.lineTo((float) (rect.left + i2), (float) rect.bottom);
        path.close();
        return path;
    }

    private static Path getUpDownArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            i2 = Math.round(((float) rect.height()) * 0.25f);
            i3 = Math.round(((float) rect.width()) * 0.25f);
            i4 = Math.round(((float) rect.height()) * 0.125f);
            i = Math.round(((float) rect.width()) * 0.375f);
        } else {
            if (adjustData[0] != null) {
                i2 = Math.round(((float) rect.height()) * adjustData[0].floatValue());
            } else {
                i2 = Math.round(((float) rect.height()) * 0.25f);
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                i3 = Math.round(((float) rect.width()) * 0.25f);
            } else {
                i3 = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                i4 = Math.round(((float) rect.height()) * 0.125f);
            } else {
                i4 = Math.round(((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData.length < 4 || adjustData[3] == null) {
                i = Math.round(((float) rect.width()) * 0.375f);
            } else {
                i = Math.round(((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        path.moveTo((float) rect.left, (float) (rect.top + i2));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i2));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i4));
        path.lineTo((float) (rect.left + i3), (float) (rect.top + i4));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.right - i3), (float) (rect.top + i4));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i4));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i2));
        path.lineTo((float) rect.right, (float) (rect.top + i2));
        path.lineTo((float) rect.right, (float) (rect.bottom - i2));
        path.lineTo((float) (rect.right - i), (float) (rect.bottom - i2));
        path.lineTo((float) (rect.right - i), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.right - i3), (float) (rect.bottom - i4));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) (rect.left + i3), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.left + i), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.left + i), (float) (rect.bottom - i2));
        path.lineTo((float) rect.left, (float) (rect.bottom - i2));
        path.close();
        return path;
    }

    private static Path getQuadArrowCalloutPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length < 1) {
            int round = Math.round(((float) rect.width()) * 0.25f);
            int round2 = Math.round(((float) rect.height()) * 0.375f);
            int round3 = Math.round(((float) rect.width()) * 0.125f);
            int round4 = Math.round(((float) rect.height()) * 0.45f);
            int round5 = Math.round(((float) rect.height()) * 0.25f);
            int round6 = Math.round(((float) rect.width()) * 0.375f);
            int round7 = Math.round(((float) rect.height()) * 0.125f);
            int i9 = round5;
            i5 = round;
            i = Math.round(((float) rect.width()) * 0.45f);
            i8 = round4;
            i2 = round7;
            i7 = round3;
            i3 = round6;
            i6 = round2;
            i4 = i9;
        } else {
            if (adjustData[0] != null) {
                i5 = Math.round(((float) rect.width()) * adjustData[0].floatValue());
                i4 = Math.round(((float) rect.height()) * adjustData[0].floatValue());
            } else {
                int round8 = Math.round(((float) rect.width()) * 0.25f);
                i4 = Math.round(((float) rect.height()) * 0.25f);
                i5 = round8;
            }
            if (adjustData.length < 2 || adjustData[1] == null) {
                int round9 = Math.round(((float) rect.height()) * 0.375f);
                i3 = Math.round(((float) rect.width()) * 0.375f);
                i6 = round9;
            } else {
                i6 = Math.round(((float) rect.height()) * adjustData[1].floatValue());
                i3 = Math.round(((float) rect.width()) * adjustData[1].floatValue());
            }
            if (adjustData.length < 3 || adjustData[2] == null) {
                int round10 = Math.round(((float) rect.width()) * 0.125f);
                i2 = Math.round(((float) rect.height()) * 0.125f);
                i7 = round10;
            } else {
                i7 = Math.round(((float) rect.width()) * adjustData[2].floatValue());
                i2 = Math.round(((float) rect.height()) * adjustData[2].floatValue());
            }
            if (adjustData.length < 4 || adjustData[3] == null) {
                int round11 = Math.round(((float) rect.height()) * 0.45f);
                i8 = round11;
                i = Math.round(((float) rect.width()) * 0.45f);
            } else {
                i8 = Math.round(((float) rect.height()) * adjustData[3].floatValue());
                i = Math.round(((float) rect.width()) * adjustData[3].floatValue());
            }
        }
        path.moveTo((float) (rect.left + i5), (float) (rect.bottom - i8));
        path.lineTo((float) (rect.left + i7), (float) (rect.bottom - i8));
        path.lineTo((float) (rect.left + i7), (float) (rect.bottom - i6));
        path.lineTo((float) rect.left, (float) rect.centerY());
        path.lineTo((float) (rect.left + i7), (float) (rect.top + i6));
        path.lineTo((float) (rect.left + i7), (float) (rect.top + i8));
        path.lineTo((float) (rect.left + i5), (float) (rect.top + i8));
        path.lineTo((float) (rect.left + i5), (float) (rect.top + i4));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i4));
        path.lineTo((float) (rect.left + i), (float) (rect.top + i2));
        path.lineTo((float) (rect.left + i3), (float) (rect.top + i2));
        path.lineTo((float) rect.centerX(), (float) rect.top);
        path.lineTo((float) (rect.right - i3), (float) (rect.top + i2));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i2));
        path.lineTo((float) (rect.right - i), (float) (rect.top + i4));
        path.lineTo((float) (rect.right - i5), (float) (rect.top + i4));
        path.lineTo((float) (rect.right - i5), (float) (rect.top + i8));
        path.lineTo((float) (rect.right - i7), (float) (rect.top + i8));
        path.lineTo((float) (rect.right - i7), (float) (rect.top + i6));
        path.lineTo((float) rect.right, (float) rect.centerY());
        path.lineTo((float) (rect.right - i7), (float) (rect.bottom - i6));
        path.lineTo((float) (rect.right - i7), (float) (rect.bottom - i8));
        path.lineTo((float) (rect.right - i5), (float) (rect.bottom - i8));
        path.lineTo((float) (rect.right - i5), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.right - i), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.right - i), (float) (rect.bottom - i2));
        path.lineTo((float) (rect.right - i3), (float) (rect.bottom - i2));
        path.lineTo((float) rect.centerX(), (float) rect.bottom);
        path.lineTo((float) (rect.left + i3), (float) (rect.bottom - i2));
        path.lineTo((float) (rect.left + i), (float) (rect.bottom - i2));
        path.lineTo((float) (rect.left + i), (float) (rect.bottom - i4));
        path.lineTo((float) (rect.left + i5), (float) (rect.bottom - i4));
        path.close();
        return path;
    }

    private static Path getCircularArrowPath(AutoShape autoShape, Rect rect) {
        float f;
        Float[] adjustData = autoShape.getAdjustData();
        float f2 = 0.25f;
        float f3 = 180.0f;
        float f4 = 0.0f;
        if (adjustData != null && adjustData.length >= 1) {
            if (adjustData[0] != null) {
                f3 = adjustData[0].floatValue() * TODEGREE;
                if (f3 < 0.0f) {
                    f3 += 360.0f;
                }
            }
            if (adjustData.length >= 2 && adjustData[1] != null) {
                float floatValue = adjustData[1].floatValue() * TODEGREE;
                if (floatValue < 0.0f) {
                    floatValue += 360.0f;
                }
                f4 = floatValue;
            }
            if (adjustData.length >= 3 && adjustData[2] != null) {
                f = adjustData[2].floatValue();
                f2 = (float) 100;
                float f5 = f * f2;
                float f6 = (float) 50;
                double d = (double) f6;
                double d2 = (((double) f3) * 3.141592653589793d) / 180.0d;
                path.moveTo((float) (d * Math.cos(d2)), (float) (d * Math.sin(d2)));
                float f7 = -f6;
                s_rect.set(f7, f7, f6, f6);
                float f8 = (f4 - f3) + 360.0f;
                path.arcTo(s_rect, f3, f8 % 360.0f);
                float f9 = ((float) 100) * 0.125f;
                double d3 = (double) (f6 + f9);
                double d4 = (((double) f4) * 3.141592653589793d) / 180.0d;
                path.lineTo((float) (Math.cos(d4) * d3), (float) (d3 * Math.sin(d4)));
                double d5 = (double) ((f6 + f5) * 0.5f);
                double d6 = (((double) (30.0f + f4)) * 3.141592653589793d) / 180.0d;
                path.lineTo((float) (Math.cos(d6) * d5), (float) (d5 * Math.sin(d6)));
                double d7 = (double) (f5 - f9);
                path.lineTo((float) (Math.cos(d4) * d7), (float) (d7 * Math.sin(d4)));
                float f10 = -f5;
                s_rect.set(f10, f10, f5, f5);
                path.arcTo(s_rect, f4, (-f8) % 360.0f);
                path.close();
                Matrix matrix = new Matrix();
                matrix.postScale(((float) rect.width()) / 100.0f, ((float) rect.height()) / 100.0f);
                path.transform(matrix);
                path.offset((float) rect.centerX(), (float) rect.centerY());
                return path;
            }
        }
        f = (float) 100;
        float f52 = f * f2;
        float f62 = (float) 50;
        double d8 = (double) f62;
        double d22 = (((double) f3) * 3.141592653589793d) / 180.0d;
        path.moveTo((float) (d8 * Math.cos(d22)), (float) (d8 * Math.sin(d22)));
        float f72 = -f62;
        s_rect.set(f72, f72, f62, f62);
        float f82 = (f4 - f3) + 360.0f;
        path.arcTo(s_rect, f3, f82 % 360.0f);
        float f92 = ((float) 100) * 0.125f;
        double d32 = (double) (f62 + f92);
        double d42 = (((double) f4) * 3.141592653589793d) / 180.0d;
        path.lineTo((float) (Math.cos(d42) * d32), (float) (d32 * Math.sin(d42)));
        double d52 = (double) ((f62 + f52) * 0.5f);
        double d62 = (((double) (30.0f + f4)) * 3.141592653589793d) / 180.0d;
        path.lineTo((float) (Math.cos(d62) * d52), (float) (d52 * Math.sin(d62)));
        double d72 = (double) (f52 - f92);
        path.lineTo((float) (Math.cos(d42) * d72), (float) (d72 * Math.sin(d42)));
        float f102 = -f52;
        s_rect.set(f102, f102, f52, f52);
        path.arcTo(s_rect, f4, (-f82) % 360.0f);
        path.close();
        Matrix matrix2 = new Matrix();
        matrix2.postScale(((float) rect.width()) / 100.0f, ((float) rect.height()) / 100.0f);
        path.transform(matrix2);
        path.offset((float) rect.centerX(), (float) rect.centerY());
        return path;
    }
}
