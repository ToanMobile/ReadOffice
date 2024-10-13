package com.app.office.thirdpart.emf.data;

import com.app.office.thirdpart.emf.EMFInputStream;
import java.io.IOException;

public class GradientTriangle extends Gradient {
    private int vertex1;
    private int vertex2;
    private int vertex3;

    public GradientTriangle(int i, int i2, int i3) {
        this.vertex1 = i;
        this.vertex2 = i2;
        this.vertex3 = i3;
    }

    public GradientTriangle(EMFInputStream eMFInputStream) throws IOException {
        this.vertex1 = eMFInputStream.readULONG();
        this.vertex2 = eMFInputStream.readULONG();
        this.vertex3 = eMFInputStream.readULONG();
    }

    public String toString() {
        return "  GradientTriangle: " + this.vertex1 + ", " + this.vertex2 + ", " + this.vertex3;
    }
}
