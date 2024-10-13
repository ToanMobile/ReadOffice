package com.app.office.common.bg;

import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import com.app.office.system.IControl;

public class LinearGradientShader extends Gradient {
    private float angle;

    public int getGradientType() {
        return 7;
    }

    public LinearGradientShader(float f, int[] iArr, float[] fArr) {
        super(iArr, fArr);
        this.angle = f;
    }

    public int getAngle() {
        return (int) this.angle;
    }

    public Shader createShader(IControl iControl, int i, Rect rect) {
        try {
            int[] linearGradientCoordinate = getLinearGradientCoordinate();
            this.shader = new LinearGradient((float) linearGradientCoordinate[0], (float) linearGradientCoordinate[1], (float) linearGradientCoordinate[2], (float) linearGradientCoordinate[3], this.colors, this.positions, Shader.TileMode.MIRROR);
            return this.shader;
        } catch (Exception unused) {
            return null;
        }
    }

    private int[] getLinearGradientCoordinate() {
        switch (Math.round(((this.angle + 22.0f) % 360.0f) / 45.0f)) {
            case 0:
                return new int[]{0, 0, 100, 0};
            case 1:
                return new int[]{0, 0, 100, 100};
            case 2:
                return new int[]{0, 0, 0, 100};
            case 3:
                return new int[]{100, 0, 0, 100};
            case 4:
                return new int[]{100, 0, 0, 0};
            case 5:
                return new int[]{100, 100, 0, 0};
            case 6:
                return new int[]{0, 100, 0, 0};
            default:
                return new int[]{0, 100, 100, 0};
        }
    }
}
