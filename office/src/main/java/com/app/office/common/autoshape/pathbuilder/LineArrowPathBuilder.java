package com.app.office.common.autoshape.pathbuilder;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.app.office.common.shape.Arrow;
import com.app.office.fc.dom4j.Element;

public class LineArrowPathBuilder {
    private static final int LARGE = 13;
    private static final int MEDIUM = 9;
    private static final int SMALL = 5;
    static PointF p = new PointF();

    public static int getArrowLength(Arrow arrow, int i) {
        if (i < 3) {
            return 9;
        }
        return i * 3;
    }

    private static int getArrowWidth(Arrow arrow, int i) {
        if (i < 3) {
            return 9;
        }
        return i * 3;
    }

    public static ArrowPathAndTail getDirectLineArrowPath(float f, float f2, float f3, float f4, Arrow arrow, int i) {
        return getDirectLineArrowPath(f, f2, f3, f4, arrow, i, 1.0f);
    }

    public static ArrowPathAndTail getDirectLineArrowPath(float f, float f2, float f3, float f4, Arrow arrow, int i, float f5) {
        ArrowPathAndTail arrowPathAndTail = new ArrowPathAndTail();
        int arrowWidth = getArrowWidth(arrow, i);
        float arrowLength = ((float) getArrowLength(arrow, i)) * f5;
        float f6 = f3 - f;
        float f7 = f4 - f2;
        float sqrt = (float) (((double) arrowLength) / Math.sqrt(Math.pow((double) f6, 2.0d) + Math.pow((double) f7, 2.0d)));
        float f8 = f4 - (f7 * sqrt);
        float f9 = f3 - (f6 * sqrt);
        arrowPathAndTail.setArrowPath(buildArrowPath(f9, f8, f3, f4, ((float) arrowWidth) * f5, arrowLength, arrow.getType()));
        arrowPathAndTail.setArrowTailCenter(f9, f8);
        return arrowPathAndTail;
    }

    public static ArrowPathAndTail getQuadBezArrowPath(float f, float f2, float f3, float f4, float f5, float f6, Arrow arrow, int i) {
        return getQuadBezArrowPath(f, f2, f3, f4, f5, f6, arrow, i, 1.0f);
    }

    public static ArrowPathAndTail getQuadBezArrowPath(float f, float f2, float f3, float f4, float f5, float f6, Arrow arrow, int i, float f7) {
        boolean z;
        float f8;
        ArrowPathAndTail arrowPathAndTail = new ArrowPathAndTail();
        float arrowWidth = ((float) getArrowWidth(arrow, i)) * f7;
        float arrowLength = ((float) getArrowLength(arrow, i)) * f7;
        PointF quadBezComputePoint = quadBezComputePoint(f, f2, f3, f4, f5, f6, 0.9f);
        int round = (int) Math.round(Math.sqrt(Math.pow((double) (quadBezComputePoint.x - f5), 2.0d) + Math.pow((double) (quadBezComputePoint.y - f6), 2.0d)));
        float f9 = 0.01f;
        Boolean bool = null;
        PointF pointF = quadBezComputePoint;
        float f10 = 0.9f;
        while (true) {
            float f11 = ((float) round) - arrowLength;
            if (Math.abs(f11) <= 1.0f || f10 >= 1.0f || f10 <= 0.0f) {
                arrowPathAndTail.setArrowPath(buildArrowPath(pointF.x, pointF.y, f5, f6, arrowWidth, arrowLength, arrow.getType()));
                arrowPathAndTail.setArrowTailCenter(pointF.x, pointF.y);
            } else {
                if (f11 > 1.0f) {
                    f8 = f10 + f9;
                    if (bool != null && !bool.booleanValue()) {
                        f9 = (float) (((double) f9) * 0.1d);
                        f8 -= f9;
                    }
                    z = true;
                } else {
                    float f12 = f10 - f9;
                    if (bool != null && bool.booleanValue()) {
                        f9 = (float) (((double) f9) * 0.1d);
                        f12 += f9;
                    }
                    z = false;
                }
                float f13 = f8;
                Boolean bool2 = z;
                PointF quadBezComputePoint2 = quadBezComputePoint(f, f2, f3, f4, f5, f6, f13);
                round = (int) Math.round(Math.sqrt((double) (((quadBezComputePoint2.x - f5) * (quadBezComputePoint2.x - f5)) + ((quadBezComputePoint2.y - f6) * (quadBezComputePoint2.y - f6)))));
                f9 = f9;
                bool = bool2;
                float f14 = f13;
                pointF = quadBezComputePoint2;
                f10 = f14;
            }
        }
        arrowPathAndTail.setArrowPath(buildArrowPath(pointF.x, pointF.y, f5, f6, arrowWidth, arrowLength, arrow.getType()));
        arrowPathAndTail.setArrowTailCenter(pointF.x, pointF.y);
        return arrowPathAndTail;
    }

    public static ArrowPathAndTail getCubicBezArrowPath(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Arrow arrow, int i) {
        return getCubicBezArrowPath(f, f2, f3, f4, f5, f6, f7, f8, arrow, i, 1.0f);
    }

    public static ArrowPathAndTail getCubicBezArrowPath(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Arrow arrow, int i, float f9) {
        float f10;
        boolean z;
        ArrowPathAndTail arrowPathAndTail = new ArrowPathAndTail();
        int arrowWidth = getArrowWidth(arrow, i);
        int arrowLength = getArrowLength(arrow, i);
        float f11 = 0.9f;
        PointF cubicBezComputePoint = cubicBezComputePoint(f, f2, f3, f4, f5, f6, f7, f8, 0.9f);
        int round = (int) Math.round(Math.sqrt(Math.pow((double) (cubicBezComputePoint.x - f7), 2.0d) + Math.pow((double) (cubicBezComputePoint.y - f8), 2.0d)));
        float f12 = 0.01f;
        Boolean bool = null;
        PointF pointF = cubicBezComputePoint;
        while (true) {
            int i2 = round - arrowLength;
            if (Math.abs(i2) <= 1 || f11 >= 1.0f || f11 <= 0.0f) {
                arrowPathAndTail.setArrowPath(buildArrowPath(pointF.x, pointF.y, f7, f8, (float) arrowWidth, (float) arrowLength, arrow.getType()));
                arrowPathAndTail.setArrowTailCenter(pointF.x, pointF.y);
            } else {
                if (i2 > 1) {
                    f10 = f11 + f12;
                    if (bool != null && !bool.booleanValue()) {
                        f12 = (float) (((double) f12) * 0.1d);
                        f10 -= f12;
                    }
                    z = true;
                } else {
                    float f13 = f11 - f12;
                    if (bool != null && bool.booleanValue()) {
                        f12 = (float) (((double) f12) * 0.1d);
                        f13 += f12;
                    }
                    z = false;
                }
                float f14 = f10;
                pointF = cubicBezComputePoint(f, f2, f3, f4, f5, f6, f7, f8, f14);
                round = (int) Math.round(Math.sqrt((double) (((pointF.x - f7) * (pointF.x - f7)) + ((pointF.y - f8) * (pointF.y - f8)))));
                f12 = f12;
                bool = z;
                f11 = f14;
            }
        }
        arrowPathAndTail.setArrowPath(buildArrowPath(pointF.x, pointF.y, f7, f8, (float) arrowWidth, (float) arrowLength, arrow.getType()));
        arrowPathAndTail.setArrowTailCenter(pointF.x, pointF.y);
        return arrowPathAndTail;
    }

    private static PointF quadBezComputePoint(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = 1.0f - f7;
        float f9 = f8 * f8;
        p.x = f * f9;
        p.y = f9 * f2;
        float f10 = 2.0f * f7 * f8;
        p.x += f3 * f10;
        p.y += f10 * f4;
        float f11 = f7 * f7;
        p.x += f5 * f11;
        p.y += f11 * f6;
        return p;
    }

    private static PointF cubicBezComputePoint(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        PointF pointF = new PointF();
        float f10 = 1.0f - f9;
        float f11 = f10 * f10 * f10;
        pointF.x = f * f11;
        pointF.y = f11 * f2;
        float f12 = 3.0f * f9;
        float f13 = f12 * f10 * f10;
        pointF.x += f3 * f13;
        pointF.y += f13 * f4;
        float f14 = f12 * f9 * f10;
        pointF.x += f5 * f14;
        pointF.y += f14 * f6;
        float f15 = f9 * f9 * f9;
        pointF.x += f7 * f15;
        pointF.y += f15 * f8;
        return pointF;
    }

    private static Path buildArrowPath(float f, float f2, float f3, float f4, float f5) {
        Path path = new Path();
        path.moveTo(f3, f4);
        int i = (int) (f5 * 15.0f);
        float f6 = f3 - f;
        float f7 = f4 - f2;
        float f8 = -f7;
        float sqrt = (float) Math.sqrt((double) ((f6 * f6) + (f7 * f7)));
        float f9 = ((float) i) / (sqrt * 2.0f);
        float f10 = -((float) (((double) i) / (((Math.tan((double) 1.0f) / 2.0d) * 2.0d) * ((double) sqrt))));
        float f11 = (float) ((int) (f3 + (f10 * f6)));
        float f12 = (float) ((int) (f4 + (f10 * f7)));
        path.lineTo((f9 * f8) + f11, ((f9 * f6) / 2.0f) + f12);
        float f13 = -f9;
        path.lineTo(f11 + (f8 * f13), f12 + ((f13 * f6) / 2.0f));
        path.close();
        return path;
    }

    private static Path buildArrowPath(float f, float f2, float f3, float f4, float f5, float f6, byte b) {
        if (b == 1) {
            return buildTriangleArrowPath(f, f2, f3, f4, f5);
        }
        if (b == 2) {
            return buildStealthArrowPath(f, f2, f3, f4, f5, f6);
        }
        if (b == 3) {
            return buildDiamondArrowPath(f, f2, f3, f4, f5, f6);
        }
        if (b != 4) {
            return b != 5 ? new Path() : buildArrowArrowPath(f, f2, f3, f4, f5);
        }
        return buildOvalArrowPath(f3, f4, f5, f6);
    }

    private static Path buildTriangleArrowPath(float f, float f2, float f3, float f4, float f5) {
        Path path = new Path();
        path.moveTo(f3, f4);
        if (f4 == f2) {
            float f6 = f5 / 2.0f;
            path.lineTo(f, f2 - f6);
            path.lineTo(f, f2 + f6);
        } else if (f3 == f) {
            float f7 = f5 / 2.0f;
            path.lineTo(f - f7, f2);
            path.lineTo(f + f7, f2);
        } else {
            double atan = Math.atan((double) (-1.0f / ((f4 - f2) / (f3 - f))));
            double d = (double) (f5 / 2.0f);
            float cos = (float) (Math.cos(atan) * d);
            float sin = (float) (d * Math.sin(atan));
            path.lineTo(f + cos, f2 + sin);
            path.lineTo(f - cos, f2 - sin);
        }
        path.close();
        return path;
    }

    private static Path buildArrowArrowPath(float f, float f2, float f3, float f4, float f5) {
        Path path = new Path();
        if (f4 == f2) {
            float f6 = f5 / 2.0f;
            path.moveTo(f, f2 - f6);
            path.lineTo(f3, f4);
            path.lineTo(f, f2 + f6);
        } else if (f3 == f) {
            float f7 = f5 / 2.0f;
            path.moveTo(f - f7, f2);
            path.lineTo(f3, f4);
            path.lineTo(f + f7, f2);
        } else {
            double atan = Math.atan((double) (-1.0f / ((f4 - f2) / (f3 - f))));
            double d = (double) (f5 / 2.0f);
            float cos = (float) (Math.cos(atan) * d);
            float sin = (float) (d * Math.sin(atan));
            path.moveTo(f + cos, f2 + sin);
            path.lineTo(f3, f4);
            path.lineTo(f - cos, f2 - sin);
        }
        return path;
    }

    private static Path buildDiamondArrowPath(float f, float f2, float f3, float f4, float f5, float f6) {
        Path path = new Path();
        if (f4 == f2 || f3 == f) {
            float f7 = f6 / 2.0f;
            path.moveTo(f3 - f7, f4);
            float f8 = f5 / 2.0f;
            path.lineTo(f3, f4 - f8);
            path.lineTo(f7 + f3, f4);
            path.lineTo(f3, f4 + f8);
        } else {
            float f9 = f4 - f2;
            float f10 = f3 - f;
            double atan = Math.atan((double) (-1.0f / (f9 / f10)));
            float cos = (float) (((double) (f6 / 2.0f)) * Math.cos(atan));
            float sin = (float) (((double) (f5 / 2.0f)) * Math.sin(atan));
            path.moveTo(f, f2);
            path.lineTo(f3 + cos, f4 + sin);
            path.lineTo(f10 + f3, f9 + f4);
            path.lineTo(f3 - cos, f4 - sin);
        }
        path.close();
        return path;
    }

    private static Path buildStealthArrowPath(float f, float f2, float f3, float f4, float f5, float f6) {
        Path path = new Path();
        path.moveTo(f3, f4);
        if (f4 == f2) {
            float f7 = f5 / 2.0f;
            path.lineTo(f, f2 - f7);
            path.lineTo(((f3 - f) / 4.0f) + f, f4);
            path.lineTo(f, f2 + f7);
        } else if (f3 == f) {
            float f8 = f5 / 2.0f;
            path.lineTo(f - f8, f2);
            path.lineTo(f, ((f4 - f2) / 4.0f) + f2);
            path.lineTo(f + f8, f2);
        } else {
            float f9 = f4 - f2;
            float f10 = f3 - f;
            double atan = Math.atan((double) (-1.0f / (f9 / f10)));
            float cos = (float) (((double) (f6 / 2.0f)) * Math.cos(atan));
            float sin = (float) (((double) (f5 / 2.0f)) * Math.sin(atan));
            path.lineTo(f + cos, f2 + sin);
            path.lineTo((f10 / 4.0f) + f, (f9 / 4.0f) + f2);
            path.lineTo(f - cos, f2 - sin);
        }
        path.close();
        return path;
    }

    private static Path buildOvalArrowPath(float f, float f2, float f3, float f4) {
        Path path = new Path();
        float f5 = f4 / 2.0f;
        float f6 = f3 / 2.0f;
        path.addOval(new RectF(f - f5, f2 - f6, f + f5, f2 + f6), Path.Direction.CCW);
        return path;
    }

    public static PointF getReferencedPosition(Element element, PointF pointF, byte b) {
        float f;
        float f2;
        float f3;
        float parseInt = (((float) Integer.parseInt(element.attributeValue("x"))) * 96.0f) / 914400.0f;
        float parseInt2 = (((float) Integer.parseInt(element.attributeValue("y"))) * 96.0f) / 914400.0f;
        if (b != 1) {
            if (b == 2) {
                f3 = 0.7f;
                parseInt = (parseInt * 0.3f) + (pointF.x * 0.7f);
                f2 = parseInt2 * 0.3f;
                f = pointF.y;
            }
            return new PointF(parseInt, parseInt2);
        }
        f3 = 0.8f;
        parseInt = (parseInt * 0.2f) + (pointF.x * 0.8f);
        f2 = parseInt2 * 0.2f;
        f = pointF.y;
        parseInt2 = f2 + (f * f3);
        return new PointF(parseInt, parseInt2);
    }

    public static PointF getReferencedPosition(float f, float f2, float f3, float f4, byte b) {
        float f5;
        float f6;
        float f7;
        if (b != 1) {
            if (b == 2) {
                f5 = 0.3f;
                f6 = f * 0.3f;
                f7 = 0.7f;
            }
            return new PointF(f, f2);
        }
        f5 = 0.2f;
        f6 = f * 0.2f;
        f7 = 0.8f;
        f = f6 + (f3 * f7);
        f2 = (f2 * f5) + (f4 * f7);
        return new PointF(f, f2);
    }
}
