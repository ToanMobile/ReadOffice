package com.app.office.fc.hslf.model;

import com.app.office.fc.ShapeKit;
import com.app.office.fc.ddf.AbstractEscherOptRecord;
import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherChildAnchorRecord;
import com.app.office.fc.ddf.EscherClientAnchorRecord;
import com.app.office.fc.ddf.EscherClientDataRecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherRecordFactory;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.fc.hslf.record.InteractiveInfo;
import com.app.office.fc.hslf.record.InteractiveInfoAtom;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.Color;
import java.io.ByteArrayOutputStream;

public abstract class SimpleShape extends Shape {
    protected EscherClientDataRecord _clientData;
    protected Record[] _clientRecords;

    protected SimpleShape(EscherContainerRecord escherContainerRecord, Shape shape) {
        super(escherContainerRecord, shape);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: com.app.office.java.awt.geom.Rectangle2D} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: com.app.office.java.awt.geom.Rectangle2D$Double} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: com.app.office.java.awt.geom.Rectangle2D$Double} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: com.app.office.java.awt.geom.Rectangle2D$Double} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.app.office.java.awt.geom.Rectangle2D getLogicalAnchor2D() {
        /*
            r21 = this;
            r0 = r21
            com.app.office.java.awt.geom.Rectangle2D r1 = r21.getAnchor2D()
            com.app.office.fc.hslf.model.Shape r2 = r0._parent
            if (r2 == 0) goto L_0x0060
            com.app.office.fc.hslf.model.Shape r2 = r0._parent
            com.app.office.fc.hslf.model.ShapeGroup r2 = (com.app.office.fc.hslf.model.ShapeGroup) r2
            com.app.office.fc.hslf.model.Shape r3 = r0._parent
            com.app.office.java.awt.geom.Rectangle2D r2 = r2.getClientAnchor2D(r3)
            com.app.office.fc.hslf.model.Shape r3 = r0._parent
            com.app.office.fc.hslf.model.ShapeGroup r3 = (com.app.office.fc.hslf.model.ShapeGroup) r3
            com.app.office.java.awt.geom.Rectangle2D r3 = r3.getCoordinates()
            double r4 = r3.getWidth()
            double r6 = r2.getWidth()
            double r4 = r4 / r6
            double r6 = r3.getHeight()
            double r8 = r2.getHeight()
            double r6 = r6 / r8
            double r8 = r2.getX()
            double r10 = r1.getX()
            double r12 = r3.getX()
            double r10 = r10 - r12
            double r10 = r10 / r4
            double r13 = r8 + r10
            double r8 = r2.getY()
            double r10 = r1.getY()
            double r2 = r3.getY()
            double r10 = r10 - r2
            double r10 = r10 / r6
            double r15 = r8 + r10
            double r2 = r1.getWidth()
            double r17 = r2 / r4
            double r1 = r1.getHeight()
            double r19 = r1 / r6
            com.app.office.java.awt.geom.Rectangle2D$Double r1 = new com.app.office.java.awt.geom.Rectangle2D$Double
            r12 = r1
            r12.<init>(r13, r15, r17, r19)
        L_0x0060:
            int r2 = r21.getRotation()
            if (r2 == 0) goto L_0x00e4
            double r3 = r1.getX()
            double r5 = r1.getWidth()
            r7 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r5 = r5 / r7
            double r3 = r3 + r5
            double r5 = r1.getY()
            double r9 = r1.getHeight()
            double r9 = r9 / r7
            double r5 = r5 + r9
            com.app.office.java.awt.geom.AffineTransform r7 = new com.app.office.java.awt.geom.AffineTransform
            r7.<init>()
            r7.translate(r3, r5)
            double r8 = (double) r2
            double r8 = java.lang.Math.toRadians(r8)
            r7.rotate(r8)
            double r8 = -r3
            double r10 = -r5
            r7.translate(r8, r10)
            com.app.office.java.awt.Shape r2 = r7.createTransformedShape(r1)
            com.app.office.java.awt.geom.Rectangle2D r2 = r2.getBounds2D()
            double r12 = r1.getWidth()
            double r14 = r1.getHeight()
            int r7 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r7 >= 0) goto L_0x00b1
            double r12 = r2.getWidth()
            double r14 = r2.getHeight()
            int r7 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r7 > 0) goto L_0x00c9
        L_0x00b1:
            double r12 = r1.getWidth()
            double r14 = r1.getHeight()
            int r7 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r7 <= 0) goto L_0x00e4
            double r12 = r2.getWidth()
            double r14 = r2.getHeight()
            int r2 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r2 >= 0) goto L_0x00e4
        L_0x00c9:
            com.app.office.java.awt.geom.AffineTransform r2 = new com.app.office.java.awt.geom.AffineTransform
            r2.<init>()
            r2.translate(r3, r5)
            r3 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            r2.rotate(r3)
            r2.translate(r8, r10)
            com.app.office.java.awt.Shape r1 = r2.createTransformedShape(r1)
            com.app.office.java.awt.geom.Rectangle2D r1 = r1.getBounds2D()
        L_0x00e4:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hslf.model.SimpleShape.getLogicalAnchor2D():com.app.office.java.awt.geom.Rectangle2D");
    }

    /* access modifiers changed from: protected */
    public Record getClientDataRecord(int i) {
        Record[] clientRecords = getClientRecords();
        if (clientRecords == null) {
            return null;
        }
        for (int i2 = 0; i2 < clientRecords.length; i2++) {
            if (clientRecords[i2].getRecordType() == ((long) i)) {
                return clientRecords[i2];
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Record[] getClientRecords() {
        if (this._clientData == null) {
            EscherRecord escherChild = ShapeKit.getEscherChild(getSpContainer(), -4079);
            if (escherChild != null && !(escherChild instanceof EscherClientDataRecord)) {
                byte[] serialize = escherChild.serialize();
                EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
                escherClientDataRecord.fillFields(serialize, 0, new DefaultEscherRecordFactory());
                escherChild = escherClientDataRecord;
            }
            this._clientData = (EscherClientDataRecord) escherChild;
        }
        EscherClientDataRecord escherClientDataRecord2 = this._clientData;
        if (escherClientDataRecord2 != null && this._clientRecords == null) {
            byte[] remainingData = escherClientDataRecord2.getRemainingData();
            this._clientRecords = Record.findChildRecords(remainingData, 0, remainingData.length);
        }
        return this._clientRecords;
    }

    /* access modifiers changed from: protected */
    public EscherContainerRecord createSpContainer(boolean z) {
        EscherRecord escherRecord;
        this._escherContainer = new EscherContainerRecord();
        this._escherContainer.setRecordId(EscherContainerRecord.SP_CONTAINER);
        this._escherContainer.setOptions(15);
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        escherSpRecord.setFlags(z ? 2562 : 2560);
        this._escherContainer.addChildRecord(escherSpRecord);
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        this._escherContainer.addChildRecord(escherOptRecord);
        if (z) {
            escherRecord = new EscherChildAnchorRecord();
        } else {
            escherRecord = new EscherClientAnchorRecord();
            byte[] bArr = new byte[16];
            LittleEndian.putUShort(bArr, 0, 0);
            LittleEndian.putUShort(bArr, 2, 0);
            LittleEndian.putInt(bArr, 4, 8);
            escherRecord.fillFields(bArr, 0, (EscherRecordFactory) null);
        }
        this._escherContainer.addChildRecord(escherRecord);
        return this._escherContainer;
    }

    public void setLineWidth(double d) {
        setEscherProperty((EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), EscherProperties.LINESTYLE__LINEWIDTH, (int) (d * 12700.0d));
    }

    public void setLineColor(Color color) {
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        if (color == null) {
            setEscherProperty(escherOptRecord, EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524288);
            return;
        }
        setEscherProperty(escherOptRecord, EscherProperties.LINESTYLE__COLOR, new Color(color.getBlue(), color.getGreen(), color.getRed(), 0).getRGB());
        setEscherProperty(escherOptRecord, EscherProperties.LINESTYLE__NOLINEDRAWDASH, color == null ? 1572880 : 1572888);
    }

    public int getLineDashing() {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 462);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public void setLineDashing(int i) {
        setEscherProperty((EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), EscherProperties.LINESTYLE__LINEDASHING, i);
    }

    public void setLineStyle(int i) {
        EscherOptRecord escherOptRecord = (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085);
        if (i == 0) {
            i = -1;
        }
        setEscherProperty(escherOptRecord, EscherProperties.LINESTYLE__LINESTYLE, i);
    }

    public int getLineStyle() {
        EscherSimpleProperty escherSimpleProperty = (EscherSimpleProperty) ShapeKit.getEscherProperty((AbstractEscherOptRecord) (EscherOptRecord) ShapeKit.getEscherChild(this._escherContainer, -4085), 461);
        if (escherSimpleProperty == null) {
            return 0;
        }
        return escherSimpleProperty.getPropertyValue();
    }

    public void setFillColor(Color color) {
        getFill().setForegroundColor(color);
    }

    public void setRotation(int i) {
        setEscherProperty(4, i << 16);
    }

    /* access modifiers changed from: protected */
    public void updateClientData() {
        if (this._clientData != null && this._clientRecords != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            while (i < this._clientRecords.length) {
                try {
                    i++;
                } catch (Exception e) {
                    throw new HSLFException((Throwable) e);
                }
            }
            this._clientData.setRemainingData(byteArrayOutputStream.toByteArray());
        }
    }

    public void setHyperlink(Hyperlink hyperlink) {
        if (hyperlink.getId() != -1) {
            EscherClientDataRecord escherClientDataRecord = new EscherClientDataRecord();
            escherClientDataRecord.setOptions(15);
            getSpContainer().addChildRecord(escherClientDataRecord);
            InteractiveInfoAtom interactiveInfoAtom = new InteractiveInfo().getInteractiveInfoAtom();
            int type = hyperlink.getType();
            if (type == 0) {
                interactiveInfoAtom.setAction((byte) 3);
                interactiveInfoAtom.setJump((byte) 1);
                interactiveInfoAtom.setHyperlinkType((byte) 0);
            } else if (type == 1) {
                interactiveInfoAtom.setAction((byte) 3);
                interactiveInfoAtom.setJump((byte) 2);
                interactiveInfoAtom.setHyperlinkType((byte) 1);
            } else if (type == 2) {
                interactiveInfoAtom.setAction((byte) 3);
                interactiveInfoAtom.setJump((byte) 3);
                interactiveInfoAtom.setHyperlinkType((byte) 2);
            } else if (type == 3) {
                interactiveInfoAtom.setAction((byte) 3);
                interactiveInfoAtom.setJump((byte) 4);
                interactiveInfoAtom.setHyperlinkType((byte) 3);
            } else if (type == 8) {
                interactiveInfoAtom.setAction((byte) 4);
                interactiveInfoAtom.setJump((byte) 0);
                interactiveInfoAtom.setHyperlinkType((byte) 8);
            }
            interactiveInfoAtom.setHyperlinkID(hyperlink.getId());
            return;
        }
        throw new HSLFException("You must call SlideShow.addHyperlink(Hyperlink link) first");
    }

    public void dispose() {
        super.dispose();
        Record[] recordArr = this._clientRecords;
        if (recordArr != null) {
            for (Record dispose : recordArr) {
                dispose.dispose();
            }
            this._clientRecords = null;
        }
        EscherClientDataRecord escherClientDataRecord = this._clientData;
        if (escherClientDataRecord != null) {
            escherClientDataRecord.dispose();
            this._clientData = null;
        }
    }
}
