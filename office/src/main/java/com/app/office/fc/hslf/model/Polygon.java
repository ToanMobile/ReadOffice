package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherArrayProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.geom.Point2D;
import kotlin.jvm.internal.ByteCompanionObject;

public final class Polygon extends AutoShape {
    protected Polygon(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public Polygon(Shape shape) {
        super((EscherContainerRecord) null, shape);
        this._escherContainer = createSpContainer(0, shape instanceof ShapeGroup);
    }

    public Polygon() {
        this((Shape) null);
    }

    public void setPoints(float[] fArr, float[] fArr2) {
        float findBiggest = findBiggest(fArr);
        float findBiggest2 = findBiggest(fArr2);
        float findSmallest = findSmallest(fArr);
        float findSmallest2 = findSmallest(fArr2);
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__RIGHT, (int) (((findBiggest - findSmallest) * 72.0f) / 576.0f)));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__BOTTOM, (int) (((findBiggest2 - findSmallest2) * 72.0f) / 576.0f)));
        for (int i = 0; i < fArr.length; i++) {
            fArr[i] = fArr[i] + (-findSmallest);
            fArr2[i] = fArr2[i] + (-findSmallest2);
        }
        int length = fArr.length;
        EscherArrayProperty escherArrayProperty = new EscherArrayProperty(EscherProperties.GEOMETRY__VERTICES, false, new byte[0]);
        int i2 = length + 1;
        escherArrayProperty.setNumberOfElementsInArray(i2);
        escherArrayProperty.setNumberOfElementsInMemory(i2);
        escherArrayProperty.setSizeOfElements(65520);
        for (int i3 = 0; i3 < length; i3++) {
            byte[] bArr = new byte[4];
            LittleEndian.putShort(bArr, 0, (short) ((int) ((fArr[i3] * 72.0f) / 576.0f)));
            LittleEndian.putShort(bArr, 2, (short) ((int) ((fArr2[i3] * 72.0f) / 576.0f)));
            escherArrayProperty.setElement(i3, bArr);
        }
        byte[] bArr2 = new byte[4];
        LittleEndian.putShort(bArr2, 0, (short) ((int) ((fArr[0] * 72.0f) / 576.0f)));
        LittleEndian.putShort(bArr2, 2, (short) ((int) ((fArr2[0] * 72.0f) / 576.0f)));
        escherArrayProperty.setElement(length, bArr2);
        escherOptRecord.addEscherProperty(escherArrayProperty);
        EscherArrayProperty escherArrayProperty2 = new EscherArrayProperty(EscherProperties.GEOMETRY__SEGMENTINFO, false, (byte[]) null);
        escherArrayProperty2.setSizeOfElements(2);
        int i4 = (length * 2) + 4;
        escherArrayProperty2.setNumberOfElementsInArray(i4);
        escherArrayProperty2.setNumberOfElementsInMemory(i4);
        escherArrayProperty2.setElement(0, new byte[]{0, 64});
        escherArrayProperty2.setElement(1, new byte[]{0, -84});
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = i5 * 2;
            escherArrayProperty2.setElement(i6 + 2, new byte[]{1, 0});
            escherArrayProperty2.setElement(i6 + 3, new byte[]{0, -84});
        }
        escherArrayProperty2.setElement(escherArrayProperty2.getNumberOfElementsInArray() - 2, new byte[]{1, 96});
        escherArrayProperty2.setElement(escherArrayProperty2.getNumberOfElementsInArray() - 1, new byte[]{0, ByteCompanionObject.MIN_VALUE});
        escherOptRecord.addEscherProperty(escherArrayProperty2);
        escherOptRecord.sortProperties();
    }

    public void setPoints(Point2D[] point2DArr) {
        float[] fArr = new float[point2DArr.length];
        float[] fArr2 = new float[point2DArr.length];
        for (int i = 0; i < point2DArr.length; i++) {
            fArr[i] = (float) point2DArr[i].getX();
            fArr2[i] = (float) point2DArr[i].getY();
        }
        setPoints(fArr, fArr2);
    }

    private float findBiggest(float[] fArr) {
        float f = Float.MIN_VALUE;
        for (int i = 0; i < fArr.length; i++) {
            if (fArr[i] > f) {
                f = fArr[i];
            }
        }
        return f;
    }

    private float findSmallest(float[] fArr) {
        float f = Float.MAX_VALUE;
        for (int i = 0; i < fArr.length; i++) {
            if (fArr[i] < f) {
                f = fArr[i];
            }
        }
        return f;
    }
}
