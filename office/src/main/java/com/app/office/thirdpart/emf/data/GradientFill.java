package com.app.office.thirdpart.emf.data;

import com.app.office.java.awt.Rectangle;
import com.app.office.thirdpart.emf.EMFConstants;
import com.app.office.thirdpart.emf.EMFInputStream;
import com.app.office.thirdpart.emf.EMFTag;
import java.io.IOException;

public class GradientFill extends EMFTag implements EMFConstants {
    private Rectangle bounds;
    private Gradient[] gradients;
    private int mode;
    private TriVertex[] vertices;

    public GradientFill() {
        super(118, 1);
    }

    public GradientFill(Rectangle rectangle, int i, TriVertex[] triVertexArr, Gradient[] gradientArr) {
        this();
        this.bounds = rectangle;
        this.mode = i;
        this.vertices = triVertexArr;
        this.gradients = gradientArr;
    }

    public EMFTag read(int i, EMFInputStream eMFInputStream, int i2) throws IOException {
        Rectangle readRECTL = eMFInputStream.readRECTL();
        int readDWORD = eMFInputStream.readDWORD();
        TriVertex[] triVertexArr = new TriVertex[readDWORD];
        int readDWORD2 = eMFInputStream.readDWORD();
        Gradient[] gradientArr = new Gradient[readDWORD2];
        int readULONG = eMFInputStream.readULONG();
        for (int i3 = 0; i3 < readDWORD; i3++) {
            triVertexArr[i3] = new TriVertex(eMFInputStream);
        }
        for (int i4 = 0; i4 < readDWORD2; i4++) {
            if (readULONG == 2) {
                gradientArr[i4] = new GradientTriangle(eMFInputStream);
            } else {
                gradientArr[i4] = new GradientRectangle(eMFInputStream);
            }
        }
        return new GradientFill(readRECTL, readULONG, triVertexArr, gradientArr);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.toString());
        stringBuffer.append("\n");
        stringBuffer.append("  bounds: ");
        stringBuffer.append(this.bounds);
        stringBuffer.append("\n");
        stringBuffer.append("  mode: ");
        stringBuffer.append(this.mode);
        stringBuffer.append("\n");
        for (int i = 0; i < this.vertices.length; i++) {
            stringBuffer.append("  vertex[");
            stringBuffer.append(i);
            stringBuffer.append("]: ");
            stringBuffer.append(this.vertices[i]);
            stringBuffer.append("\n");
        }
        for (int i2 = 0; i2 < this.gradients.length; i2++) {
            stringBuffer.append("  gradient[");
            stringBuffer.append(i2);
            stringBuffer.append("]: ");
            stringBuffer.append(this.gradients[i2]);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}
