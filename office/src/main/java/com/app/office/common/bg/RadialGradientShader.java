package com.app.office.common.bg;

import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import com.app.office.system.IControl;

public class RadialGradientShader extends Gradient {
    public static final int Center_BL = 2;
    public static final int Center_BR = 3;
    public static final int Center_Center = 4;
    public static final int Center_TL = 0;
    public static final int Center_TR = 1;
    private int positionType;

    public int getGradientType() {
        return 4;
    }

    public RadialGradientShader(int i, int[] iArr, float[] fArr) {
        super(iArr, fArr);
        this.positionType = i;
    }

    public Shader createShader(IControl iControl, int i, Rect rect) {
        int[] circleCoordinate = getCircleCoordinate();
        if (this.positionType == 4 && getFocus() == 0) {
            int length = this.colors.length;
            for (int i2 = 0; i2 < length / 2; i2++) {
                int i3 = this.colors[i2];
                int i4 = (length - 1) - i2;
                this.colors[i2] = this.colors[i4];
                this.colors[i4] = i3;
            }
        }
        this.shader = new RadialGradient((float) circleCoordinate[0], (float) circleCoordinate[1], (float) circleCoordinate[2], this.colors, this.positions, Shader.TileMode.REPEAT);
        return this.shader;
    }

    private int[] getCircleCoordinate() {
        int ceil = (int) Math.ceil(Math.sqrt(Math.pow(100.0d, 2.0d) * 2.0d));
        int i = this.positionType;
        if (i == 1) {
            return new int[]{100, 0, ceil};
        } else if (i == 2) {
            return new int[]{0, 100, ceil};
        } else if (i == 3) {
            return new int[]{100, 100, ceil};
        } else if (i != 4) {
            return new int[]{0, 0, ceil};
        } else {
            return new int[]{50, 50, ceil / 2};
        }
    }
}
