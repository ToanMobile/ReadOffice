package com.app.office.common.autoshape;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.app.office.common.shape.ShapeTypes;

public class AutoShapeTypes {
    private static final AutoShapeTypes kit = new AutoShapeTypes();

    public static AutoShapeTypes instance() {
        return kit;
    }

    public int getAutoShapeType(String str) {
        if (str == null) {
            return 0;
        }
        if (str.equals("line")) {
            return 20;
        }
        if (str.equals("straightConnector1")) {
            return 32;
        }
        if (str.equals("bentConnector2")) {
            return 33;
        }
        if (str.equals("bentConnector3")) {
            return 34;
        }
        if (str.equals("curvedConnector2")) {
            return 37;
        }
        if (str.equals("curvedConnector3")) {
            return 38;
        }
        if (str.equals("curvedConnector4")) {
            return 39;
        }
        if (str.equals("curvedConnector5")) {
            return 40;
        }
        if (str.equals("rect") || str.equals("Rect")) {
            return 1;
        }
        if (str.equals("roundRect")) {
            return 2;
        }
        if (str.equals("round1Rect")) {
            return ShapeTypes.Round1Rect;
        }
        if (str.equals("round2SameRect")) {
            return ShapeTypes.Round2SameRect;
        }
        if (str.equals("round2DiagRect")) {
            return ShapeTypes.Round2DiagRect;
        }
        if (str.equals("snip1Rect")) {
            return ShapeTypes.Snip1Rect;
        }
        if (str.equals("snip2SameRect")) {
            return ShapeTypes.Snip2SameRect;
        }
        if (str.equals("snip2DiagRect")) {
            return ShapeTypes.Snip2DiagRect;
        }
        if (str.equals("snipRoundRect")) {
            return ShapeTypes.SnipRoundRect;
        }
        if (str.equals("ellipse")) {
            return 3;
        }
        if (str.equals("triangle")) {
            return 5;
        }
        if (str.equals("rtTriangle")) {
            return 6;
        }
        if (str.equals("parallelogram")) {
            return 7;
        }
        if (str.equals("trapezoid")) {
            return 8;
        }
        if (str.equals("diamond")) {
            return 4;
        }
        if (str.equals("pentagon")) {
            return 56;
        }
        if (str.equals("hexagon")) {
            return 9;
        }
        if (str.equals("heptagon")) {
            return ShapeTypes.Heptagon;
        }
        if (str.equals("octagon")) {
            return 10;
        }
        if (str.equals("decagon")) {
            return ShapeTypes.Decagon;
        }
        if (str.equals("dodecagon")) {
            return ShapeTypes.Dodecagon;
        }
        if (str.equals("pie")) {
            return ShapeTypes.Pie;
        }
        if (str.equals("chord")) {
            return ShapeTypes.Chord;
        }
        if (str.equals("teardrop")) {
            return ShapeTypes.Teardrop;
        }
        if (str.equals(TypedValues.AttributesType.S_FRAME)) {
            return ShapeTypes.Frame;
        }
        if (str.equals("halfFrame")) {
            return 224;
        }
        if (str.equals("corner")) {
            return ShapeTypes.Corner;
        }
        if (str.equals("diagStripe")) {
            return 226;
        }
        if (str.equals("plus")) {
            return 11;
        }
        if (str.equals("plaque")) {
            return 21;
        }
        if (str.equals("can")) {
            return 22;
        }
        if (str.equals("cube")) {
            return 16;
        }
        if (str.equals("bevel")) {
            return 84;
        }
        if (str.equals("donut")) {
            return 23;
        }
        if (str.equals("noSmoking")) {
            return 57;
        }
        if (str.equals("blockArc")) {
            return 95;
        }
        if (str.equals("foldedCorner")) {
            return 65;
        }
        if (str.equals("smileyFace")) {
            return 96;
        }
        if (str.equals("heart")) {
            return 74;
        }
        if (str.equals("lightningBolt")) {
            return 73;
        }
        if (str.equals("sun")) {
            return 183;
        }
        if (str.equals("moon")) {
            return 184;
        }
        if (str.equals("cloud")) {
            return ShapeTypes.Cloud;
        }
        if (str.equals("arc")) {
            return 19;
        }
        if (str.equals("bracketPair")) {
            return 185;
        }
        if (str.equals("bracePair")) {
            return 186;
        }
        if (str.equals("leftBracket")) {
            return 85;
        }
        if (str.equals("rightBracket")) {
            return 86;
        }
        if (str.equals("leftBrace")) {
            return 87;
        }
        if (str.equals("rightBrace")) {
            return 88;
        }
        if (str.equals("mathPlus")) {
            return ShapeTypes.MathPlus;
        }
        if (str.equals("mathMinus")) {
            return ShapeTypes.MathMinus;
        }
        if (str.equals("mathMultiply")) {
            return ShapeTypes.MathMultiply;
        }
        if (str.equals("mathDivide")) {
            return ShapeTypes.MathDivide;
        }
        if (str.equals("mathEqual")) {
            return ShapeTypes.MathEqual;
        }
        if (str.equals("mathNotEqual")) {
            return ShapeTypes.MathNotEqual;
        }
        if (str.equals("rightArrow")) {
            return 13;
        }
        if (str.equals("leftArrow")) {
            return 66;
        }
        if (str.equals("upArrow")) {
            return 68;
        }
        if (str.equals("downArrow")) {
            return 67;
        }
        if (str.equals("leftRightArrow")) {
            return 69;
        }
        if (str.equals("upDownArrow") || str.equals("upDownArrow")) {
            return 70;
        }
        if (str.equals("quadArrow")) {
            return 76;
        }
        if (str.equals("leftRightUpArrow")) {
            return 182;
        }
        if (str.equals("bentArrow")) {
            return 91;
        }
        if (str.equals("uturnArrow")) {
            return 101;
        }
        if (str.equals("leftUpArrow")) {
            return 89;
        }
        if (str.equals("bentUpArrow")) {
            return 90;
        }
        if (str.equals("curvedRightArrow")) {
            return 102;
        }
        if (str.equals("curvedLeftArrow")) {
            return 103;
        }
        if (str.equals("curvedUpArrow")) {
            return 104;
        }
        if (str.equals("curvedDownArrow")) {
            return 105;
        }
        if (str.equals("stripedRightArrow")) {
            return 93;
        }
        if (str.equals("notchedRightArrow")) {
            return 94;
        }
        if (str.equals("homePlate")) {
            return 15;
        }
        if (str.equals("chevron")) {
            return 55;
        }
        if (str.equals("rightArrowCallout")) {
            return 78;
        }
        if (str.equals("leftArrowCallout")) {
            return 77;
        }
        if (str.equals("downArrowCallout")) {
            return 80;
        }
        if (str.equals("upArrowCallout")) {
            return 79;
        }
        if (str.equals("leftRightArrowCallout")) {
            return 81;
        }
        if (str.equals("quadArrowCallout")) {
            return 83;
        }
        if (str.equals("circularArrow")) {
            return 99;
        }
        if (str.equals("flowChartProcess")) {
            return 109;
        }
        if (str.equals("flowChartAlternateProcess")) {
            return 176;
        }
        if (str.equals("flowChartDecision")) {
            return 110;
        }
        if (str.equals("flowChartInputOutput")) {
            return 111;
        }
        if (str.equals("flowChartPredefinedProcess")) {
            return 112;
        }
        if (str.equals("flowChartInternalStorage")) {
            return 113;
        }
        if (str.equals("flowChartDocument")) {
            return 114;
        }
        if (str.equals("flowChartMultidocument")) {
            return 115;
        }
        if (str.equals("flowChartTerminator")) {
            return 116;
        }
        if (str.equals("flowChartPreparation")) {
            return 117;
        }
        if (str.equals("flowChartManualInput")) {
            return 118;
        }
        if (str.equals("flowChartManualOperation")) {
            return 119;
        }
        if (str.equals("flowChartConnector")) {
            return 120;
        }
        if (str.equals("flowChartOffpageConnector")) {
            return 177;
        }
        if (str.equals("flowChartPunchedCard")) {
            return 121;
        }
        if (str.equals("flowChartPunchedTape")) {
            return 122;
        }
        if (str.equals("flowChartSummingJunction")) {
            return 123;
        }
        if (str.equals("flowChartOr")) {
            return 124;
        }
        if (str.equals("flowChartCollate")) {
            return 125;
        }
        if (str.equals("flowChartSort")) {
            return 126;
        }
        if (str.equals("flowChartExtract")) {
            return 127;
        }
        if (str.equals("flowChartMerge")) {
            return 128;
        }
        if (str.equals("flowChartOnlineStorage")) {
            return 130;
        }
        if (str.equals("flowChartDelay")) {
            return 135;
        }
        if (str.equals("flowChartMagneticTape")) {
            return 131;
        }
        if (str.equals("flowChartMagneticDisk")) {
            return 132;
        }
        if (str.equals("flowChartMagneticDrum")) {
            return 133;
        }
        if (str.equals("flowChartDisplay")) {
            return 134;
        }
        if (str.equals("wedgeRectCallout")) {
            return 61;
        }
        if (str.equals("wedgeRoundRectCallout")) {
            return 62;
        }
        if (str.equals("wedgeEllipseCallout")) {
            return 63;
        }
        if (str.equals("cloudCallout")) {
            return 106;
        }
        if (str.equals("borderCallout1")) {
            return 180;
        }
        if (str.equals("borderCallout2")) {
            return 47;
        }
        if (str.equals("borderCallout3")) {
            return 48;
        }
        if (str.equals("accentCallout1")) {
            return 179;
        }
        if (str.equals("accentCallout2")) {
            return 44;
        }
        if (str.equals("accentCallout3")) {
            return 45;
        }
        if (str.equals("callout1")) {
            return 178;
        }
        if (str.equals("callout2")) {
            return 41;
        }
        if (str.equals("callout3")) {
            return 42;
        }
        if (str.equals("accentBorderCallout1")) {
            return 181;
        }
        if (str.equals("accentBorderCallout2")) {
            return 50;
        }
        if (str.equals("accentBorderCallout3")) {
            return 51;
        }
        if (str.equals("actionButtonBackPrevious")) {
            return ShapeTypes.ActionButtonBackPrevious;
        }
        if (str.equals("actionButtonForwardNext")) {
            return ShapeTypes.ActionButtonForwardNext;
        }
        if (str.equals("actionButtonBeginning")) {
            return ShapeTypes.ActionButtonBeginning;
        }
        if (str.equals("actionButtonEnd")) {
            return ShapeTypes.ActionButtonEnd;
        }
        if (str.equals("actionButtonHome")) {
            return ShapeTypes.ActionButtonHome;
        }
        if (str.equals("actionButtonInformation")) {
            return ShapeTypes.ActionButtonInformation;
        }
        if (str.equals("actionButtonReturn")) {
            return ShapeTypes.ActionButtonReturn;
        }
        if (str.equals("actionButtonMovie")) {
            return 200;
        }
        if (str.equals("actionButtonDocument")) {
            return ShapeTypes.ActionButtonDocument;
        }
        if (str.equals("actionButtonSound")) {
            return ShapeTypes.ActionButtonSound;
        }
        if (str.equals("actionButtonHelp")) {
            return ShapeTypes.ActionButtonHelp;
        }
        if (str.equals("actionButtonBlank")) {
            return ShapeTypes.ActionButtonBlank;
        }
        if (str.equals("irregularSeal1")) {
            return 71;
        }
        if (str.equals("irregularSeal2")) {
            return 72;
        }
        if (str.equals("star4")) {
            return 187;
        }
        if (str.equals("star5")) {
            return ShapeTypes.Star5;
        }
        if (str.equals("star6")) {
            return ShapeTypes.Star6;
        }
        if (str.equals("star7")) {
            return 237;
        }
        if (str.equals("star8")) {
            return 58;
        }
        if (str.equals("star10")) {
            return 238;
        }
        if (str.equals("star12")) {
            return 239;
        }
        if (str.equals("star16")) {
            return 59;
        }
        if (str.equals("star24")) {
            return 92;
        }
        if (str.equals("star32")) {
            return 60;
        }
        if (str.equals("ribbon2")) {
            return 54;
        }
        if (str.equals("ribbon")) {
            return 53;
        }
        if (str.equals("ellipseRibbon2")) {
            return 108;
        }
        if (str.equals("ellipseRibbon")) {
            return 107;
        }
        if (str.equals("verticalScroll")) {
            return 97;
        }
        if (str.equals("horizontalScroll")) {
            return 98;
        }
        if (str.equals("wave")) {
            return 64;
        }
        if (str.equals("doubleWave")) {
            return ShapeTypes.DoubleWave;
        }
        if (str.equals("funnel")) {
            return ShapeTypes.Funnel;
        }
        if (str.equals("gear6")) {
            return ShapeTypes.Gear6;
        }
        if (str.equals("gear9")) {
            return ShapeTypes.Gear9;
        }
        if (str.equals("leftCircularArrow")) {
            return ShapeTypes.LeftCircularArrow;
        }
        if (str.equals("leftRightRibbon")) {
            return ShapeTypes.LeftRightRibbon;
        }
        if (str.equals("pieWedge")) {
            return ShapeTypes.PieWedge;
        }
        if (str.equals("swooshArrow")) {
            return ShapeTypes.SwooshArrow;
        }
        return 0;
    }
}
