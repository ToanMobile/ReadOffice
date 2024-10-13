package com.app.office.fc.hslf.model;

import android.graphics.Path;
import android.graphics.PointF;
import com.app.office.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.EscherArrayProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.GeneralPath;
import com.app.office.java.awt.geom.PathIterator;
import com.app.office.java.awt.geom.Point2D;
import com.app.office.java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import kotlin.jvm.internal.ByteCompanionObject;

public final class Freeform extends AutoShape {
    public static final byte[] SEGMENTINFO_CLOSE = {1, 96};
    public static final byte[] SEGMENTINFO_CUBICTO = {0, -83};
    public static final byte[] SEGMENTINFO_CUBICTO1 = {0, -81};
    public static final byte[] SEGMENTINFO_CUBICTO2 = {0, -77};
    public static final byte[] SEGMENTINFO_END = {0, ByteCompanionObject.MIN_VALUE};
    public static final byte[] SEGMENTINFO_ESCAPE = {1, 0};
    public static final byte[] SEGMENTINFO_ESCAPE1 = {3, 0};
    public static final byte[] SEGMENTINFO_ESCAPE2 = {1, 32};
    public static final byte[] SEGMENTINFO_LINETO = {0, -84};
    public static final byte[] SEGMENTINFO_LINETO2 = {0, -80};
    public static final byte[] SEGMENTINFO_MOVETO = {0, 64};

    protected Freeform(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    public Path[] getFreeformPath(Rectangle rectangle, PointF pointF, byte b, PointF pointF2, byte b2) {
        return ShapeKit.getFreeformPath(getSpContainer(), rectangle, pointF, b, pointF2, b2);
    }

    public Freeform(Shape shape) {
        super((EscherContainerRecord) null, shape);
        this._escherContainer = createSpContainer(0, shape instanceof ShapeGroup);
    }

    public Freeform() {
        this((Shape) null);
    }

    public void setPath(GeneralPath generalPath) {
        boolean z;
        Rectangle2D bounds2D = generalPath.getBounds2D();
        PathIterator pathIterator = generalPath.getPathIterator(new AffineTransform());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        boolean z2 = false;
        while (!pathIterator.isDone()) {
            double[] dArr = new double[6];
            int currentSegment = pathIterator.currentSegment(dArr);
            if (currentSegment == 0) {
                z = z2;
                arrayList2.add(new Point2D.Double(dArr[0], dArr[1]));
                arrayList.add(ShapeKit.SEGMENTINFO_MOVETO);
            } else if (currentSegment == 1) {
                z = z2;
                arrayList2.add(new Point2D.Double(dArr[0], dArr[1]));
                arrayList.add(ShapeKit.SEGMENTINFO_LINETO);
                arrayList.add(ShapeKit.SEGMENTINFO_ESCAPE);
            } else if (currentSegment == 3) {
                z = z2;
                arrayList2.add(new Point2D.Double(dArr[i], dArr[1]));
                arrayList2.add(new Point2D.Double(dArr[2], dArr[3]));
                arrayList2.add(new Point2D.Double(dArr[4], dArr[5]));
                arrayList.add(ShapeKit.SEGMENTINFO_CUBICTO);
                arrayList.add(ShapeKit.SEGMENTINFO_ESCAPE2);
            } else if (currentSegment != 4) {
                z = z2;
            } else {
                arrayList2.add((Point2D.Double) arrayList2.get(i));
                arrayList.add(ShapeKit.SEGMENTINFO_LINETO);
                arrayList.add(ShapeKit.SEGMENTINFO_ESCAPE);
                arrayList.add(ShapeKit.SEGMENTINFO_LINETO);
                arrayList.add(ShapeKit.SEGMENTINFO_CLOSE);
                z2 = true;
                pathIterator.next();
                i = 0;
            }
            z2 = z;
            pathIterator.next();
            i = 0;
        }
        if (!z2) {
            arrayList.add(ShapeKit.SEGMENTINFO_LINETO);
        }
        arrayList.add(new byte[]{0, ByteCompanionObject.MIN_VALUE});
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__SHAPEPATH, 4));
        EscherArrayProperty escherArrayProperty = new EscherArrayProperty(16709, false, (byte[]) null);
        escherArrayProperty.setNumberOfElementsInArray(arrayList2.size());
        escherArrayProperty.setNumberOfElementsInMemory(arrayList2.size());
        escherArrayProperty.setSizeOfElements(65520);
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            Point2D.Double doubleR = (Point2D.Double) arrayList2.get(i2);
            byte[] bArr = new byte[4];
            LittleEndian.putShort(bArr, 0, (short) ((int) (((doubleR.getX() - bounds2D.getX()) * 576.0d) / 72.0d)));
            LittleEndian.putShort(bArr, 2, (short) ((int) (((doubleR.getY() - bounds2D.getY()) * 576.0d) / 72.0d)));
            escherArrayProperty.setElement(i2, bArr);
        }
        escherOptRecord.addEscherProperty(escherArrayProperty);
        EscherArrayProperty escherArrayProperty2 = new EscherArrayProperty(16710, false, (byte[]) null);
        escherArrayProperty2.setNumberOfElementsInArray(arrayList.size());
        escherArrayProperty2.setNumberOfElementsInMemory(arrayList.size());
        escherArrayProperty2.setSizeOfElements(2);
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            escherArrayProperty2.setElement(i3, (byte[]) arrayList.get(i3));
        }
        escherOptRecord.addEscherProperty(escherArrayProperty2);
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__RIGHT, (int) ((bounds2D.getWidth() * 576.0d) / 72.0d)));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.GEOMETRY__BOTTOM, (int) ((bounds2D.getHeight() * 576.0d) / 72.0d)));
        escherOptRecord.sortProperties();
        setAnchor(bounds2D);
    }

    /* JADX WARNING: type inference failed for: r1v5, types: [com.app.office.fc.ddf.EscherProperty] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.java.awt.geom.GeneralPath getPath() {
        /*
            r19 = this;
            r0 = r19
            com.app.office.fc.ddf.EscherContainerRecord r1 = r0._escherContainer
            r2 = -4085(0xfffffffffffff00b, float:NaN)
            com.app.office.fc.ddf.EscherRecord r1 = com.app.office.fc.ShapeKit.getEscherChild(r1, r2)
            com.app.office.fc.ddf.EscherOptRecord r1 = (com.app.office.fc.ddf.EscherOptRecord) r1
            com.app.office.fc.ddf.EscherSimpleProperty r2 = new com.app.office.fc.ddf.EscherSimpleProperty
            r3 = 324(0x144, float:4.54E-43)
            r4 = 4
            r2.<init>(r3, r4)
            r1.addEscherProperty(r2)
            r2 = 16709(0x4145, float:2.3414E-41)
            com.app.office.fc.ddf.EscherProperty r2 = com.app.office.fc.ShapeKit.getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r1, (int) r2)
            com.app.office.fc.ddf.EscherArrayProperty r2 = (com.app.office.fc.ddf.EscherArrayProperty) r2
            if (r2 != 0) goto L_0x0029
            r2 = 325(0x145, float:4.55E-43)
            com.app.office.fc.ddf.EscherProperty r2 = com.app.office.fc.ShapeKit.getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r1, (int) r2)
            com.app.office.fc.ddf.EscherArrayProperty r2 = (com.app.office.fc.ddf.EscherArrayProperty) r2
        L_0x0029:
            r3 = 16710(0x4146, float:2.3416E-41)
            com.app.office.fc.ddf.EscherProperty r3 = com.app.office.fc.ShapeKit.getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r1, (int) r3)
            com.app.office.fc.ddf.EscherArrayProperty r3 = (com.app.office.fc.ddf.EscherArrayProperty) r3
            if (r3 != 0) goto L_0x003c
            r3 = 326(0x146, float:4.57E-43)
            com.app.office.fc.ddf.EscherProperty r1 = com.app.office.fc.ShapeKit.getEscherProperty((com.app.office.fc.ddf.AbstractEscherOptRecord) r1, (int) r3)
            r3 = r1
            com.app.office.fc.ddf.EscherArrayProperty r3 = (com.app.office.fc.ddf.EscherArrayProperty) r3
        L_0x003c:
            r1 = 0
            if (r2 != 0) goto L_0x0040
            return r1
        L_0x0040:
            if (r3 != 0) goto L_0x0043
            return r1
        L_0x0043:
            com.app.office.java.awt.geom.GeneralPath r1 = new com.app.office.java.awt.geom.GeneralPath
            r1.<init>()
            int r11 = r2.getNumberOfElementsInArray()
            int r12 = r3.getNumberOfElementsInArray()
            r13 = 0
            r4 = 0
            r5 = 0
        L_0x0053:
            if (r4 >= r12) goto L_0x012f
            if (r5 >= r11) goto L_0x012f
            byte[] r6 = r3.getElement(r4)
            byte[] r7 = com.app.office.fc.ShapeKit.SEGMENTINFO_MOVETO
            boolean r7 = java.util.Arrays.equals(r6, r7)
            r8 = 2
            r9 = 1141899264(0x44100000, float:576.0)
            r10 = 1116733440(0x42900000, float:72.0)
            if (r7 == 0) goto L_0x0084
            int r6 = r5 + 1
            byte[] r5 = r2.getElement(r5)
            short r7 = com.app.office.fc.util.LittleEndian.getShort(r5, r13)
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r8)
            float r7 = (float) r7
            float r7 = r7 * r10
            float r7 = r7 / r9
            float r5 = (float) r5
            float r5 = r5 * r10
            float r5 = r5 / r9
            r1.moveTo((float) r7, (float) r5)
        L_0x0081:
            r5 = r6
            goto L_0x012b
        L_0x0084:
            byte[] r7 = com.app.office.fc.ShapeKit.SEGMENTINFO_CUBICTO
            boolean r7 = java.util.Arrays.equals(r6, r7)
            if (r7 != 0) goto L_0x00d3
            byte[] r7 = com.app.office.fc.ShapeKit.SEGMENTINFO_CUBICTO2
            boolean r7 = java.util.Arrays.equals(r6, r7)
            if (r7 == 0) goto L_0x0095
            goto L_0x00d3
        L_0x0095:
            byte[] r7 = com.app.office.fc.ShapeKit.SEGMENTINFO_LINETO
            boolean r6 = java.util.Arrays.equals(r6, r7)
            if (r6 == 0) goto L_0x012b
            int r4 = r4 + 1
            byte[] r6 = r3.getElement(r4)
            byte[] r7 = com.app.office.fc.ShapeKit.SEGMENTINFO_ESCAPE
            boolean r7 = java.util.Arrays.equals(r6, r7)
            if (r7 == 0) goto L_0x00c7
            int r6 = r5 + 1
            if (r6 >= r11) goto L_0x012b
            byte[] r5 = r2.getElement(r5)
            short r7 = com.app.office.fc.util.LittleEndian.getShort(r5, r13)
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r8)
            float r7 = (float) r7
            float r7 = r7 * r10
            float r7 = r7 / r9
            float r5 = (float) r5
            float r5 = r5 * r10
            float r5 = r5 / r9
            r1.lineTo((float) r7, (float) r5)
            goto L_0x0081
        L_0x00c7:
            byte[] r7 = com.app.office.fc.ShapeKit.SEGMENTINFO_CLOSE
            boolean r6 = java.util.Arrays.equals(r6, r7)
            if (r6 == 0) goto L_0x012b
            r1.closePath()
            goto L_0x012b
        L_0x00d3:
            int r14 = r4 + 1
            int r4 = r5 + 1
            byte[] r5 = r2.getElement(r5)
            short r6 = com.app.office.fc.util.LittleEndian.getShort(r5, r13)
            short r5 = com.app.office.fc.util.LittleEndian.getShort(r5, r8)
            int r7 = r4 + 1
            byte[] r4 = r2.getElement(r4)
            short r15 = com.app.office.fc.util.LittleEndian.getShort(r4, r13)
            short r4 = com.app.office.fc.util.LittleEndian.getShort(r4, r8)
            int r16 = r7 + 1
            byte[] r7 = r2.getElement(r7)
            short r9 = com.app.office.fc.util.LittleEndian.getShort(r7, r13)
            short r7 = com.app.office.fc.util.LittleEndian.getShort(r7, r8)
            float r6 = (float) r6
            float r6 = r6 * r10
            r8 = 1141899264(0x44100000, float:576.0)
            float r6 = r6 / r8
            float r5 = (float) r5
            float r5 = r5 * r10
            float r17 = r5 / r8
            float r5 = (float) r15
            float r5 = r5 * r10
            float r15 = r5 / r8
            float r4 = (float) r4
            float r4 = r4 * r10
            float r18 = r4 / r8
            float r4 = (float) r9
            float r4 = r4 * r10
            float r9 = r4 / r8
            float r4 = (float) r7
            float r4 = r4 * r10
            float r10 = r4 / r8
            r4 = r1
            r5 = r6
            r6 = r17
            r7 = r15
            r8 = r18
            r4.curveTo((float) r5, (float) r6, (float) r7, (float) r8, (float) r9, (float) r10)
            r4 = r14
            r5 = r16
        L_0x012b:
            int r4 = r4 + 1
            goto L_0x0053
        L_0x012f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.model.Freeform.getPath():com.app.office.java.awt.geom.GeneralPath");
    }

    public Shape getOutline() {
        GeneralPath path = getPath();
        Rectangle2D anchor2D = getAnchor2D();
        Rectangle2D bounds2D = path.getBounds2D();
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(anchor2D.getX(), anchor2D.getY());
        affineTransform.scale(anchor2D.getWidth() / bounds2D.getWidth(), anchor2D.getHeight() / bounds2D.getHeight());
        return affineTransform.createTransformedShape(path);
    }

    public ArrowPathAndTail getStartArrowPathAndTail(Rectangle rectangle) {
        return ShapeKit.getStartArrowPathAndTail(this._escherContainer, rectangle);
    }

    public ArrowPathAndTail getEndArrowPathAndTail(Rectangle rectangle) {
        return ShapeKit.getEndArrowPathAndTail(this._escherContainer, rectangle);
    }

    public void dispose() {
        super.dispose();
    }
}
