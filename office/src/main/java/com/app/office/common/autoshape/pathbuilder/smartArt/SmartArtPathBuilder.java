package com.app.office.common.autoshape.pathbuilder.smartArt;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.common.shape.AutoShape;
import com.app.office.common.shape.ShapeTypes;

public class SmartArtPathBuilder {
    private static final float TODEGREE = 1.6666666f;
    private static Path path = new Path();
    private static RectF s_rect = new RectF();
    private static Matrix sm = new Matrix();

    public static Path getStarPath(AutoShape autoShape, Rect rect) {
        path.reset();
        switch (autoShape.getShapeType()) {
            case ShapeTypes.Funnel /*240*/:
                return getFunnelPath(autoShape, rect);
            case ShapeTypes.Gear6 /*241*/:
                return getGear6Path(autoShape, rect);
            case ShapeTypes.Gear9 /*242*/:
                return getGear9Path(autoShape, rect);
            case ShapeTypes.LeftCircularArrow /*243*/:
                return getLeftCircularArrowPath(autoShape, rect);
            case ShapeTypes.PieWedge /*245*/:
                return getPieWedgePath(autoShape, rect);
            case ShapeTypes.SwooshArrow /*246*/:
                return getSwooshArrowPath(autoShape, rect);
            default:
                return null;
        }
    }

    private static Path getFunnelPath(AutoShape autoShape, Rect rect) {
        path.addOval(new RectF(28.0f, 22.0f, 688.0f, 238.0f), Path.Direction.CCW);
        path.moveTo(0.0f, 130.0f);
        path.arcTo(new RectF(0.0f, 0.0f, 716.0f, 260.0f), 180.0f, 180.0f);
        path.arcTo(new RectF(258.0f, 444.0f, 458.0f, 536.0f), 30.0f, 150.0f);
        path.close();
        sm.reset();
        sm.postScale(((float) rect.width()) / 716.0f, ((float) rect.height()) / 536.0f);
        path.transform(sm);
        path.offset((float) rect.left, (float) rect.top);
        return path;
    }

    private static Path getGear6Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        path.moveTo(5131482.0f, 1736961.0f);
        path.lineTo(6143269.0f, 1432030.0f);
        path.lineTo(6515568.0f, 2076873.0f);
        path.lineTo(5745593.0f, 2800638.0f);
        path.cubicTo(5857203.0f, 3212114.0f, 5857203.0f, 3645892.0f, 5745592.0f, 4057368.0f);
        path.lineTo(6515568.0f, 4781127.0f);
        path.lineTo(6143269.0f, 5425970.0f);
        path.lineTo(5131482.0f, 5121039.0f);
        path.cubicTo(4830937.0f, 5423437.0f, 4455271.0f, 5640328.0f, 4043114.0f, 5749407.0f);
        path.lineTo(3801303.0f, 6778110.0f);
        path.lineTo(3056697.0f, 6778110.0f);
        path.lineTo(2814884.0f, 5749410.0f);
        path.cubicTo(2402727.0f, 5640330.0f, 2027062.0f, 5423438.0f, 1726518.0f, 5121040.0f);
        path.lineTo(714731.0f, 5425970.0f);
        path.lineTo(342432.0f, 4781127.0f);
        path.lineTo(1112407.0f, 4057362.0f);
        path.cubicTo(1000796.0f, 3645886.0f, 1000796.0f, 3212108.0f, 1112407.0f, 2800632.0f);
        path.lineTo(342432.0f, 2076873.0f);
        path.lineTo(714731.0f, 1432030.0f);
        path.lineTo(1726518.0f, 1736961.0f);
        path.cubicTo(2027063.0f, 1434563.0f, 2402729.0f, 1217673.0f, 2814886.0f, 1108594.0f);
        path.lineTo(3056697.0f, 79890.0f);
        path.lineTo(3801303.0f, 79890.0f);
        path.lineTo(4043116.0f, 1108590.0f);
        path.cubicTo(4455273.0f, 1217671.0f, 4830938.0f, 1434562.0f, 5131482.0f, 1736961.0f);
        path.close();
        sm.reset();
        sm.postScale(((float) rect.width()) / 6858000.0f, ((float) rect.height()) / 6858000.0f);
        path.transform(sm);
        path.offset((float) rect2.left, (float) rect2.top);
        return path;
    }

    private static Path getGear9Path(AutoShape autoShape, Rect rect) {
        Rect rect2 = rect;
        path.moveTo(4056564.0f, 911200.0f);
        path.lineTo(4501105.0f, 538168.0f);
        path.lineTo(4856239.0f, 836163.0f);
        path.lineTo(4566066.0f, 1338725.0f);
        path.cubicTo(4772395.0f, 1570831.0f, 4929267.0f, 1842544.0f, 5027111.0f, 2137283.0f);
        path.lineTo(5607429.0f, 2137269.0f);
        path.lineTo(5687931.0f, 2593823.0f);
        path.lineTo(5142605.0f, 2792288.0f);
        path.cubicTo(5151467.0f, 3102716.0f, 5096985.0f, 3411694.0f, 4982485.0f, 3700369.0f);
        path.lineTo(5427044.0f, 4073378.0f);
        path.lineTo(5195245.0f, 4474864.0f);
        path.lineTo(4649930.0f, 4276370.0f);
        path.cubicTo(4457179.0f, 4519870.0f, 4216835.0f, 4721542.0f, 3943563.0f, 4869081.0f);
        path.lineTo(4044350.0f, 5440580.0f);
        path.lineTo(3608711.0f, 5599139.0f);
        path.lineTo(3318566.0f, 5096561.0f);
        path.cubicTo(3014392.0f, 5159194.0f, 2700646.0f, 5159194.0f, 2396472.0f, 5096561.0f);
        path.lineTo(2106329.0f, 5599139.0f);
        path.lineTo(1670690.0f, 5440580.0f);
        path.lineTo(1771476.0f, 4869081.0f);
        path.cubicTo(1498205.0f, 4721541.0f, 1257861.0f, 4519869.0f, 1065110.0f, 4276369.0f);
        path.lineTo(519795.0f, 4474864.0f);
        path.lineTo(287996.0f, 4073378.0f);
        path.lineTo(732555.0f, 3700369.0f);
        path.cubicTo(618055.0f, 3411694.0f, 563574.0f, 3102715.0f, 572436.0f, 2792288.0f);
        path.lineTo(27109.0f, 2593823.0f);
        path.lineTo(107611.0f, 2137269.0f);
        path.lineTo(687928.0f, 2137283.0f);
        path.cubicTo(785773.0f, 1842544.0f, 942647.0f, 1570832.0f, 1148976.0f, 1338726.0f);
        path.lineTo(858801.0f, 836163.0f);
        path.lineTo(1213935.0f, 538168.0f);
        path.lineTo(1658476.0f, 911200.0f);
        path.cubicTo(1922884.0f, 748311.0f, 2217710.0f, 641003.0f, 2524962.0f, 595826.0f);
        path.lineTo(2625719.0f, 24319.0f);
        path.lineTo(3089321.0f, 24319.0f);
        path.lineTo(3190077.0f, 595823.0f);
        path.cubicTo(3497329.0f, 641001.0f, 3792154.0f, 748309.0f, 4056562.0f, 911199.0f);
        path.cubicTo(4056563.0f, 911199.0f, 4056563.0f, 911200.0f, 4056564.0f, 911200.0f);
        path.close();
        sm.reset();
        sm.postScale(((float) rect.width()) / 5715040.0f, ((float) rect.height()) / 5715040.0f);
        path.transform(sm);
        path.offset((float) rect2.left, (float) rect2.top);
        return path;
    }

    private static Path getLeftCircularArrowPath(AutoShape autoShape, Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        autoShape.setFlipVertical(true);
        Float[] adjustData = autoShape.getAdjustData();
        if (adjustData == null || adjustData.length != 5) {
            float f = ((float) 100) * 0.125f;
            i3 = Math.round(f);
            i = TIFFConstants.TIFFTAG_SMINSAMPLEVALUE;
            i2 = -180;
            i4 = Math.round(f);
            i5 = 20;
        } else {
            float f2 = (float) 100;
            i3 = Math.round(adjustData[0].floatValue() * f2);
            i5 = Math.round(adjustData[1].floatValue() * TODEGREE);
            i = Math.round(adjustData[2].floatValue() * TODEGREE);
            i2 = -Math.round(adjustData[3].floatValue() * TODEGREE);
            i4 = Math.round(f2 * adjustData[4].floatValue());
        }
        int i6 = 50 - i4;
        double d = (double) i6;
        double d2 = (((double) i) * 3.141592653589793d) / 180.0d;
        double cos = Math.cos(d2) * d;
        int i7 = i6;
        double d3 = (((double) (i5 + i)) * 3.141592653589793d) / 180.0d;
        double tan = Math.tan(d3);
        double sin = (Math.sin(d2) * d) - (tan * cos);
        double d4 = d;
        double sqrt = Math.sqrt(Math.pow((double) i4, 2.0d) / (Math.pow(tan, 2.0d) + 1.0d));
        int i8 = i3 / 2;
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
        float f3 = (float) ((i4 - i8) - 50);
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
        float f5 = (float) ((i4 + i8) - 50);
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

    private static Path getPieWedgePath(AutoShape autoShape, Rect rect) {
        path.moveTo((float) rect.right, (float) rect.bottom);
        path.lineTo((float) rect.left, (float) rect.bottom);
        path.arcTo(new RectF((float) rect.left, (float) rect.top, (float) (rect.left + (rect.width() * 2)), (float) (rect.top + (rect.height() * 2))), 180.0f, 90.0f);
        path.close();
        return path;
    }

    private static Path getSwooshArrowPath(AutoShape autoShape, Rect rect) {
        path.moveTo(0.0f, 3600000.0f);
        path.cubicTo(400000.0f, 2000000.0f, 1300000.0f, 950000.0f, 2700000.0f, 450000.0f);
        path.lineTo(2649297.0f, 0.0f);
        path.lineTo(3600000.0f, 720000.0f);
        path.lineTo(2852109.0f, 1800000.0f);
        path.lineTo(2801406.0f, 1350000.0f);
        path.cubicTo(1533802.0f, 1550000.0f, 600000.0f, 2300000.0f, 0.0f, 3600000.0f);
        path.close();
        sm.reset();
        sm.postScale(((float) rect.width()) / 3600000.0f, ((float) rect.height()) / 3600000.0f);
        path.transform(sm);
        path.offset((float) rect.left, (float) rect.top);
        return path;
    }
}
