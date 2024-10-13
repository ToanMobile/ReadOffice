package com.app.office.java.awt;

import androidx.core.view.ViewCompat;
import com.app.office.common.shape.ShapeTypes;
import java.io.Serializable;

public class Color implements Serializable {
    public static final Color BLACK;
    public static final Color BLUE;
    public static final Color CYAN;
    public static final Color DARK_GRAY;
    private static final double FACTOR = 0.7d;
    public static final Color GRAY;
    public static final Color GREEN;
    public static final Color LIGHT_GRAY;
    public static final Color MAGENTA;
    public static final Color ORANGE;
    public static final Color PINK;
    public static final Color RED;
    public static final Color WHITE;
    public static final Color YELLOW;
    public static final Color black;
    public static final Color blue;
    public static final Color cyan;
    public static final Color darkGray;
    public static final Color gray;
    public static final Color green;
    public static final Color lightGray;
    public static final Color magenta;
    public static final Color orange;
    public static final Color pink;
    public static final Color red;
    private static final long serialVersionUID = 118526816881161077L;
    public static final Color white;
    public static final Color yellow;
    private float falpha;
    private float[] frgbvalue;
    private float[] fvalue;
    private transient long pData;
    int value;

    private static native void initIDs();

    static {
        Color color = new Color(255, 255, 255);
        white = color;
        WHITE = color;
        Color color2 = new Color((int) ShapeTypes.ActionButtonInformation, (int) ShapeTypes.ActionButtonInformation, (int) ShapeTypes.ActionButtonInformation);
        lightGray = color2;
        LIGHT_GRAY = color2;
        Color color3 = new Color(128, 128, 128);
        gray = color3;
        GRAY = color3;
        Color color4 = new Color(64, 64, 64);
        darkGray = color4;
        DARK_GRAY = color4;
        Color color5 = new Color(0, 0, 0);
        black = color5;
        BLACK = color5;
        Color color6 = new Color(255, 0, 0);
        red = color6;
        RED = color6;
        Color color7 = new Color(255, 175, 175);
        pink = color7;
        PINK = color7;
        Color color8 = new Color(255, 200, 0);
        orange = color8;
        ORANGE = color8;
        Color color9 = new Color(255, 255, 0);
        yellow = color9;
        YELLOW = color9;
        Color color10 = new Color(0, 255, 0);
        green = color10;
        GREEN = color10;
        Color color11 = new Color(255, 0, 255);
        magenta = color11;
        MAGENTA = color11;
        Color color12 = new Color(0, 255, 255);
        cyan = color12;
        CYAN = color12;
        Color color13 = new Color(0, 0, 255);
        blue = color13;
        BLUE = color13;
    }

    private static void testColorValueRange(int i, int i2, int i3, int i4) {
        boolean z;
        String str;
        if (i4 < 0 || i4 > 255) {
            str = " Alpha";
            z = true;
        } else {
            z = false;
            str = "";
        }
        if (i < 0 || i > 255) {
            str = str + " Red";
            z = true;
        }
        if (i2 < 0 || i2 > 255) {
            str = str + " Green";
            z = true;
        }
        if (i3 < 0 || i3 > 255) {
            str = str + " Blue";
            z = true;
        }
        if (z) {
            throw new IllegalArgumentException("Color parameter outside of expected range:" + str);
        }
    }

    private static void testColorValueRange(float f, float f2, float f3, float f4) {
        String str;
        boolean z;
        double d = (double) f4;
        if (d < 0.0d || d > 1.0d) {
            str = " Alpha";
            z = true;
        } else {
            z = false;
            str = "";
        }
        double d2 = (double) f;
        if (d2 < 0.0d || d2 > 1.0d) {
            str = str + " Red";
            z = true;
        }
        double d3 = (double) f2;
        if (d3 < 0.0d || d3 > 1.0d) {
            str = str + " Green";
            z = true;
        }
        double d4 = (double) f3;
        if (d4 < 0.0d || d4 > 1.0d) {
            str = str + " Blue";
            z = true;
        }
        if (z) {
            throw new IllegalArgumentException("Color parameter outside of expected range:" + str);
        }
    }

    public Color(int i, int i2, int i3) {
        this(i, i2, i3, 255);
    }

    public Color(int i, int i2, int i3, int i4) {
        this.frgbvalue = null;
        this.fvalue = null;
        this.falpha = 0.0f;
        this.value = ((i4 & 255) << 24) | ((i & 255) << 16) | ((i2 & 255) << 8) | ((i3 & 255) << 0);
        testColorValueRange(i, i2, i3, i4);
    }

    public Color(int i) {
        this.frgbvalue = null;
        this.fvalue = null;
        this.falpha = 0.0f;
        this.value = i | -16777216;
    }

    public Color(int i, boolean z) {
        this.frgbvalue = null;
        this.fvalue = null;
        this.falpha = 0.0f;
        if (z) {
            this.value = i;
        } else {
            this.value = i | -16777216;
        }
    }

    public Color(int i, int i2) {
        this.frgbvalue = null;
        this.fvalue = null;
        this.falpha = 0.0f;
        this.value = (i & ViewCompat.MEASURED_SIZE_MASK) | ((i2 & 255) << 24);
    }

    public Color(float f, float f2, float f3) {
        this((int) (((double) (f * 255.0f)) + 0.5d), (int) (((double) (f2 * 255.0f)) + 0.5d), (int) (((double) (255.0f * f3)) + 0.5d));
        testColorValueRange(f, f2, f3, 1.0f);
        float[] fArr = new float[3];
        this.frgbvalue = fArr;
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        this.falpha = 1.0f;
        this.fvalue = fArr;
    }

    public Color(float f, float f2, float f3, float f4) {
        this((int) (((double) (f * 255.0f)) + 0.5d), (int) (((double) (f2 * 255.0f)) + 0.5d), (int) (((double) (f3 * 255.0f)) + 0.5d), (int) (((double) (255.0f * f4)) + 0.5d));
        float[] fArr = new float[3];
        this.frgbvalue = fArr;
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        this.falpha = f4;
        this.fvalue = fArr;
    }

    public int getRed() {
        return (getRGB() >> 16) & 255;
    }

    public int getGreen() {
        return (getRGB() >> 8) & 255;
    }

    public int getBlue() {
        return (getRGB() >> 0) & 255;
    }

    public int getAlpha() {
        return (getRGB() >> 24) & 255;
    }

    public int getRGB() {
        return this.value;
    }

    public Color brighter() {
        int red2 = getRed();
        int green2 = getGreen();
        int blue2 = getBlue();
        if (red2 == 0 && green2 == 0 && blue2 == 0) {
            return new Color(3, 3, 3);
        }
        if (red2 > 0 && red2 < 3) {
            red2 = 3;
        }
        if (green2 > 0 && green2 < 3) {
            green2 = 3;
        }
        if (blue2 > 0 && blue2 < 3) {
            blue2 = 3;
        }
        return new Color(Math.min((int) (((double) red2) / FACTOR), 255), Math.min((int) (((double) green2) / FACTOR), 255), Math.min((int) (((double) blue2) / FACTOR), 255));
    }

    public Color darker() {
        return new Color(Math.max((int) (((double) getRed()) * FACTOR), 0), Math.max((int) (((double) getGreen()) * FACTOR), 0), Math.max((int) (((double) getBlue()) * FACTOR), 0));
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Color) && ((Color) obj).getRGB() == getRGB();
    }

    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }

    public static Color decode(String str) throws NumberFormatException {
        int intValue = Integer.decode(str).intValue();
        return new Color((intValue >> 16) & 255, (intValue >> 8) & 255, intValue & 255);
    }

    public static Color getColor(String str) {
        return getColor(str, (Color) null);
    }

    public static Color getColor(String str, Color color) {
        Integer integer = Integer.getInteger(str);
        if (integer == null) {
            return color;
        }
        int intValue = integer.intValue();
        return new Color((intValue >> 16) & 255, (intValue >> 8) & 255, intValue & 255);
    }

    public static Color getColor(String str, int i) {
        Integer integer = Integer.getInteger(str);
        if (integer != null) {
            i = integer.intValue();
        }
        return new Color((i >> 16) & 255, (i >> 8) & 255, (i >> 0) & 255);
    }

    public static int HSBtoRGB(float f, float f2, float f3) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (f2 == 0.0f) {
            i3 = (int) ((f3 * 255.0f) + 0.5f);
            i2 = i3;
            i = i2;
        } else {
            float floor = (f - ((float) Math.floor((double) f))) * 6.0f;
            float floor2 = floor - ((float) Math.floor((double) floor));
            float f4 = (1.0f - f2) * f3;
            float f5 = (1.0f - (f2 * floor2)) * f3;
            float f6 = (1.0f - (f2 * (1.0f - floor2))) * f3;
            int i6 = (int) floor;
            if (i6 == 0) {
                i4 = (int) ((f3 * 255.0f) + 0.5f);
                i5 = (int) ((f6 * 255.0f) + 0.5f);
            } else if (i6 == 1) {
                i4 = (int) ((f5 * 255.0f) + 0.5f);
                i5 = (int) ((f3 * 255.0f) + 0.5f);
            } else if (i6 != 2) {
                if (i6 == 3) {
                    i3 = (int) ((f4 * 255.0f) + 0.5f);
                    i2 = (int) ((f5 * 255.0f) + 0.5f);
                } else if (i6 == 4) {
                    i3 = (int) ((f6 * 255.0f) + 0.5f);
                    i2 = (int) ((f4 * 255.0f) + 0.5f);
                } else if (i6 != 5) {
                    i3 = 0;
                    i2 = 0;
                    i = 0;
                } else {
                    i3 = (int) ((f3 * 255.0f) + 0.5f);
                    i2 = (int) ((f4 * 255.0f) + 0.5f);
                    i = (int) ((f5 * 255.0f) + 0.5f);
                }
                i = (int) ((f3 * 255.0f) + 0.5f);
            } else {
                i3 = (int) ((f4 * 255.0f) + 0.5f);
                i2 = (int) ((f3 * 255.0f) + 0.5f);
                i = (int) ((f6 * 255.0f) + 0.5f);
            }
            i = (int) ((f4 * 255.0f) + 0.5f);
        }
        return (i3 << 16) | -16777216 | (i2 << 8) | (i << 0);
    }

    public static float[] RGBtoHSB(int i, int i2, int i3, float[] fArr) {
        if (fArr == null) {
            fArr = new float[3];
        }
        int i4 = i > i2 ? i : i2;
        if (i3 > i4) {
            i4 = i3;
        }
        int i5 = i < i2 ? i : i2;
        if (i3 < i5) {
            i5 = i3;
        }
        float f = (float) i4;
        float f2 = f / 255.0f;
        float f3 = 0.0f;
        float f4 = i4 != 0 ? ((float) (i4 - i5)) / f : 0.0f;
        if (f4 != 0.0f) {
            float f5 = (float) (i4 - i5);
            float f6 = ((float) (i4 - i)) / f5;
            float f7 = ((float) (i4 - i2)) / f5;
            float f8 = ((float) (i4 - i3)) / f5;
            float f9 = (i == i4 ? f8 - f7 : i2 == i4 ? (f6 + 2.0f) - f8 : (f7 + 4.0f) - f6) / 6.0f;
            f3 = f9 < 0.0f ? f9 + 1.0f : f9;
        }
        fArr[0] = f3;
        fArr[1] = f4;
        fArr[2] = f2;
        return fArr;
    }

    public static Color getHSBColor(float f, float f2, float f3) {
        return new Color(HSBtoRGB(f, f2, f3));
    }

    public float[] getRGBComponents(float[] fArr) {
        if (fArr == null) {
            fArr = new float[4];
        }
        float[] fArr2 = this.frgbvalue;
        if (fArr2 == null) {
            fArr[0] = ((float) getRed()) / 255.0f;
            fArr[1] = ((float) getGreen()) / 255.0f;
            fArr[2] = ((float) getBlue()) / 255.0f;
            fArr[3] = ((float) getAlpha()) / 255.0f;
        } else {
            fArr[0] = fArr2[0];
            fArr[1] = fArr2[1];
            fArr[2] = fArr2[2];
            fArr[3] = this.falpha;
        }
        return fArr;
    }

    public float[] getRGBColorComponents(float[] fArr) {
        if (fArr == null) {
            fArr = new float[3];
        }
        float[] fArr2 = this.frgbvalue;
        if (fArr2 == null) {
            fArr[0] = ((float) getRed()) / 255.0f;
            fArr[1] = ((float) getGreen()) / 255.0f;
            fArr[2] = ((float) getBlue()) / 255.0f;
        } else {
            fArr[0] = fArr2[0];
            fArr[1] = fArr2[1];
            fArr[2] = fArr2[2];
        }
        return fArr;
    }

    public float[] getComponents(float[] fArr) {
        float[] fArr2 = this.fvalue;
        if (fArr2 == null) {
            return getRGBComponents(fArr);
        }
        int length = fArr2.length;
        if (fArr == null) {
            fArr = new float[(length + 1)];
        }
        for (int i = 0; i < length; i++) {
            fArr[i] = this.fvalue[i];
        }
        fArr[length] = this.falpha;
        return fArr;
    }

    public float[] getColorComponents(float[] fArr) {
        float[] fArr2 = this.fvalue;
        if (fArr2 == null) {
            return getRGBColorComponents(fArr);
        }
        int length = fArr2.length;
        if (fArr == null) {
            fArr = new float[length];
        }
        for (int i = 0; i < length; i++) {
            fArr[i] = this.fvalue[i];
        }
        return fArr;
    }
}
