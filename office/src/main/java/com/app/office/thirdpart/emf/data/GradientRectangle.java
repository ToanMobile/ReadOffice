package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class GradientRectangle extends Gradient {
    private int lowerRight;
    private int upperLeft;

    public GradientRectangle(int i, int i2) {
        this.upperLeft = i;
        this.lowerRight = i2;
    }

    public GradientRectangle(EMFInputStream eMFInputStream) throws IOException {
        this.upperLeft = eMFInputStream.readULONG();
        this.lowerRight = eMFInputStream.readULONG();
    }

    public String toString() {
        return "  GradientRectangle: " + this.upperLeft + ", " + this.lowerRight;
    }
}
