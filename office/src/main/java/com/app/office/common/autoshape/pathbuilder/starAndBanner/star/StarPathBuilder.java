package com.app.office.common.autoshape.pathbuilder.starAndBanner.star;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.ShapeTypes;

public class StarPathBuilder {
    private static Path path = new Path();
    private static Matrix sm = new Matrix();

    public static Path getStarPath(AutoShape autoShape, Rect rect) {
        path.reset();
        int shapeType = autoShape.getShapeType();
        if (!(shapeType == 12 || shapeType == 92 || shapeType == 187)) {
            if (shapeType == 71) {
                return getIrregularSeal1Path(autoShape, rect);
            }
            if (shapeType == 72) {
                return getIrregularSeal2Path(autoShape, rect);
            }
            switch (shapeType) {
                case 58:
                case 59:
                case 60:
                    break;
                default:
                    switch (shapeType) {
                        case ShapeTypes.Star5 /*235*/:
                        case ShapeTypes.Star6 /*236*/:
                        case 237:
                        case 238:
                        case 239:
                            break;
                        default:
                            return path;
                    }
            }
        }
        if (autoShape.isAutoShape07()) {
            return LaterStarPathBuilder.getStarPath(autoShape, rect);
        }
        return EarlyStarPathBuilder.getStarPath(autoShape, rect);
    }

    private static Path getIrregularSeal1Path(AutoShape autoShape, Rect rect) {
        path.moveTo(66.0f, 206.0f);
        path.lineTo(0.0f, 150.0f);
        path.lineTo(83.0f, 134.0f);
        path.lineTo(8.0f, 41.0f);
        path.lineTo(128.0f, 112.0f);
        path.lineTo(147.0f, 42.0f);
        path.lineTo(190.0f, 103.0f);
        path.lineTo(255.0f, 0.0f);
        path.lineTo(250.0f, 93.0f);
        path.lineTo(323.0f, 78.0f);
        path.lineTo(294.0f, 128.0f);
        path.lineTo(370.0f, 142.0f);
        path.lineTo(310.0f, 185.0f);
        path.lineTo(380.0f, 233.0f);
        path.lineTo(296.0f, 228.0f);
        path.lineTo(319.0f, 318.0f);
        path.lineTo(247.0f, 255.0f);
        path.lineTo(233.0f, 346.0f);
        path.lineTo(185.0f, 263.0f);
        path.lineTo(149.0f, 380.0f);
        path.lineTo(135.0f, 275.0f);
        path.lineTo(84.0f, 309.0f);
        path.lineTo(99.0f, 245.0f);
        path.lineTo(0.0f, 256.0f);
        path.close();
        sm.reset();
        sm.postScale(((float) rect.width()) / 380.0f, ((float) rect.height()) / 380.0f);
        path.transform(sm);
        path.offset((float) rect.left, (float) rect.top);
        return path;
    }

    private static Path getIrregularSeal2Path(AutoShape autoShape, Rect rect) {
        path.moveTo(70.0f, 203.0f);
        path.lineTo(20.0f, 143.0f);
        path.lineTo(95.0f, 137.0f);
        path.lineTo(79.0f, 64.0f);
        path.lineTo(151.0f, 113.0f);
        path.lineTo(170.0f, 32.0f);
        path.lineTo(202.0f, 76.0f);
        path.lineTo(260.0f, 0.0f);
        path.lineTo(255.0f, 101.0f);
        path.lineTo(316.0f, 55.0f);
        path.lineTo(287.0f, 114.0f);
        path.lineTo(380.0f, 115.0f);
        path.lineTo(298.0f, 164.0f);
        path.lineTo(321.0f, 198.0f);
        path.lineTo(287.0f, 215.0f);
        path.lineTo(331.0f, 273.0f);
        path.lineTo(257.0f, 251.0f);
        path.lineTo(262.0f, 304.0f);
        path.lineTo(215.0f, 280.0f);
        path.lineTo(204.0f, 330.0f);
        path.lineTo(174.0f, 304.0f);
        path.lineTo(153.0f, 345.0f);
        path.lineTo(132.0f, 317.0f);
        path.lineTo(86.0f, 380.0f);
        path.lineTo(85.0f, 319.0f);
        path.lineTo(23.0f, 313.0f);
        path.lineTo(58.0f, 269.0f);
        path.lineTo(0.0f, 225.0f);
        path.close();
        sm.reset();
        sm.postScale(((float) rect.width()) / 380.0f, ((float) rect.height()) / 380.0f);
        path.transform(sm);
        path.offset((float) rect.left, (float) rect.top);
        return path;
    }

    public static Path getStarPath(int i, int i2, int i3, int i4, int i5) {
        int i6;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        int i7 = i;
        int i8 = i2;
        int i9 = i3;
        int i10 = i4;
        int i11 = i5;
        float f9 = 360.0f;
        float f10 = 360.0f / ((float) (i11 * 2));
        path.moveTo(0.0f, (float) (-i8));
        float f11 = 90.0f;
        double d = 3.141592653589793d;
        float f12 = 270.0f;
        double d2 = 2.0d;
        if (i9 <= 0 || i10 <= 0) {
            int i12 = 1;
            float f13 = 270.0f;
            while (true) {
                int i13 = i12 + 1;
                if (i12 >= i11) {
                    break;
                }
                path.lineTo(0.0f, 0.0f);
                float f14 = (((f13 + f10) % 360.0f) + f10) % 360.0f;
                int i14 = (f14 > 90.0f ? 1 : (f14 == 90.0f ? 0 : -1));
                if (i14 == 0) {
                    i6 = i13;
                    f = f14;
                    f3 = (float) i8;
                    f2 = 0.0f;
                } else {
                    i6 = i13;
                    int i15 = i14;
                    double d3 = (((double) f14) * 3.141592653589793d) / 180.0d;
                    f = f14;
                    f2 = (float) (((double) (i7 * i8)) / Math.sqrt(Math.pow((double) i8, 2.0d) + Math.pow(((double) i7) * Math.tan(d3), 2.0d)));
                    if (i15 > 0 && f < 270.0f) {
                        f2 = -f2;
                    }
                    f3 = (float) (((double) f2) * Math.tan(d3));
                }
                path.lineTo(f2, f3);
                i12 = i6;
                f13 = f;
            }
            path.lineTo(0.0f, 0.0f);
        } else {
            int i16 = 1;
            float f15 = 270.0f;
            while (true) {
                int i17 = i16 + 1;
                if (i16 >= i11) {
                    break;
                }
                float f16 = (f15 + f10) % f9;
                int i18 = (f16 > f11 ? 1 : (f16 == f11 ? 0 : -1));
                if (i18 == 0) {
                    f4 = (float) i10;
                    f5 = 0.0f;
                } else {
                    double pow = Math.pow((double) i10, d2);
                    double d4 = (double) i9;
                    double d5 = (((double) f16) * d) / 180.0d;
                    f5 = (float) (((double) (i9 * i10)) / Math.sqrt(pow + Math.pow(d4 * Math.tan(d5), 2.0d)));
                    if (i18 > 0 && f16 < f12) {
                        f5 = -f5;
                    }
                    f4 = (float) (((double) f5) * Math.tan(d5));
                }
                path.lineTo(f5, f4);
                float f17 = (f16 + f10) % 360.0f;
                int i19 = (f17 > 90.0f ? 1 : (f17 == 90.0f ? 0 : -1));
                if (i19 == 0) {
                    f7 = f17;
                    f6 = 0.0f;
                    f8 = (float) i8;
                } else {
                    double d6 = (((double) f17) * 3.141592653589793d) / 180.0d;
                    f7 = f17;
                    float sqrt = (float) (((double) (i7 * i8)) / Math.sqrt(Math.pow((double) i8, 2.0d) + Math.pow(((double) i7) * Math.tan(d6), 2.0d)));
                    if (i19 > 0 && f7 < 270.0f) {
                        sqrt = -sqrt;
                    }
                    f6 = sqrt;
                    f8 = (float) (((double) f6) * Math.tan(d6));
                }
                path.lineTo(f6, f8);
                i9 = i3;
                i10 = i4;
                f15 = f7;
                i16 = i17;
                f9 = 360.0f;
                f11 = 90.0f;
                d2 = 2.0d;
                d = 3.141592653589793d;
                f12 = 270.0f;
            }
            int i20 = i3;
            int i21 = i4;
            double d7 = (((double) (270.0f - f10)) * 3.141592653589793d) / 180.0d;
            float f18 = -((float) (((double) (i20 * i21)) / Math.sqrt(Math.pow((double) i21, 2.0d) + Math.pow(((double) i20) * Math.tan(d7), 2.0d))));
            path.lineTo(f18, (float) (((double) f18) * Math.tan(d7)));
        }
        path.close();
        return path;
    }
}
