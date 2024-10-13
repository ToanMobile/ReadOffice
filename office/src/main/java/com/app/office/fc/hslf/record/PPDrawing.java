package com.app.office.fc.hslf.record;

import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherBoolProperty;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherDgRecord;
import com.app.office.fc.ddf.EscherOptRecord;
import com.app.office.fc.ddf.EscherProperties;
import com.app.office.fc.ddf.EscherRGBProperty;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.EscherSimpleProperty;
import com.app.office.fc.ddf.EscherSpRecord;
import com.app.office.fc.ddf.EscherSpgrRecord;
import com.app.office.fc.ddf.EscherTextboxRecord;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public final class PPDrawing extends RecordAtom {
    private byte[] _header;
    private long _type;
    private EscherRecord[] childRecords;
    private EscherDgRecord dg;
    private EscherTextboxWrapper[] textboxWrappers;

    public Record[] getChildRecords() {
        return null;
    }

    public EscherRecord[] getEscherRecords() {
        return this.childRecords;
    }

    public EscherTextboxWrapper[] getTextboxWrappers() {
        return this.textboxWrappers;
    }

    protected PPDrawing(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        int i3 = 0;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._type = (long) LittleEndian.getUShort(this._header, 2);
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr, i, bArr3, 0, i2);
        DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
        Vector vector = new Vector();
        findEscherChildren(defaultEscherRecordFactory, bArr3, 8, i2 - 8, vector);
        this.childRecords = new EscherRecord[vector.size()];
        int i4 = 0;
        while (true) {
            EscherRecord[] escherRecordArr = this.childRecords;
            if (i4 >= escherRecordArr.length) {
                break;
            }
            escherRecordArr[i4] = (EscherRecord) vector.get(i4);
            i4++;
        }
        Vector vector2 = new Vector();
        findEscherTextboxRecord(this.childRecords, vector2);
        this.textboxWrappers = new EscherTextboxWrapper[vector2.size()];
        while (true) {
            EscherTextboxWrapper[] escherTextboxWrapperArr = this.textboxWrappers;
            if (i3 < escherTextboxWrapperArr.length) {
                escherTextboxWrapperArr[i3] = (EscherTextboxWrapper) vector2.get(i3);
                i3++;
            } else {
                return;
            }
        }
    }

    public PPDrawing() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 15);
        LittleEndian.putUShort(this._header, 2, RecordTypes.PPDrawing.typeID);
        LittleEndian.putInt(this._header, 4, 0);
        this.textboxWrappers = new EscherTextboxWrapper[0];
        create();
    }

    private void findEscherChildren(DefaultEscherRecordFactory defaultEscherRecordFactory, byte[] bArr, int i, int i2, Vector vector) {
        int i3 = LittleEndian.getInt(bArr, i + 4) + 8;
        EscherRecord createRecord = defaultEscherRecordFactory.createRecord(bArr, i);
        createRecord.fillFields(bArr, i, defaultEscherRecordFactory);
        vector.add(createRecord);
        int recordSize = createRecord.getRecordSize();
        if (recordSize == i3) {
            i3 = recordSize;
        }
        int i4 = i + i3;
        int i5 = i2 - i3;
        if (i5 >= 8) {
            findEscherChildren(defaultEscherRecordFactory, bArr, i4, i5, vector);
        }
    }

    private void findEscherTextboxRecord(EscherRecord[] escherRecordArr, Vector vector) {
        for (int i = 0; i < escherRecordArr.length; i++) {
            if (escherRecordArr[i] instanceof EscherTextboxRecord) {
                EscherTextboxWrapper escherTextboxWrapper = new EscherTextboxWrapper(escherRecordArr[i]);
                vector.add(escherTextboxWrapper);
                if ("BinaryTagData".equals(escherRecordArr[i].getRecordName())) {
                    escherTextboxWrapper.setShapeId(escherRecordArr[i].getRecordId());
                } else {
                    int i2 = i;
                    while (true) {
                        if (i2 < 0) {
                            break;
                        } else if (escherRecordArr[i2] instanceof EscherSpRecord) {
                            escherTextboxWrapper.setShapeId(escherRecordArr[i2].getShapeId());
                            break;
                        } else {
                            i2--;
                        }
                    }
                }
            } else if (escherRecordArr[i].isContainerRecord()) {
                List<EscherRecord> childRecords2 = escherRecordArr[i].getChildRecords();
                EscherRecord[] escherRecordArr2 = new EscherRecord[childRecords2.size()];
                childRecords2.toArray(escherRecordArr2);
                findEscherTextboxRecord(escherRecordArr2, vector);
            }
        }
    }

    public long getRecordType() {
        return this._type;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        int i = 0;
        for (int i2 = 0; i2 < this.textboxWrappers.length; i2++) {
        }
        int i3 = 0;
        int i4 = 0;
        while (true) {
            EscherRecord[] escherRecordArr = this.childRecords;
            if (i3 >= escherRecordArr.length) {
                break;
            }
            i4 += escherRecordArr[i3].getRecordSize();
            i3++;
        }
        LittleEndian.putInt(this._header, 4, i4);
        outputStream.write(this._header);
        byte[] bArr = new byte[i4];
        int i5 = 0;
        while (true) {
            EscherRecord[] escherRecordArr2 = this.childRecords;
            if (i < escherRecordArr2.length) {
                i5 += escherRecordArr2[i].serialize(i5, bArr);
                i++;
            } else {
                outputStream.write(bArr);
                return;
            }
        }
    }

    private void create() {
        EscherContainerRecord escherContainerRecord = new EscherContainerRecord();
        escherContainerRecord.setRecordId(EscherContainerRecord.DG_CONTAINER);
        escherContainerRecord.setOptions(15);
        EscherDgRecord escherDgRecord = new EscherDgRecord();
        escherDgRecord.setOptions(16);
        escherDgRecord.setNumShapes(1);
        escherContainerRecord.addChildRecord(escherDgRecord);
        EscherContainerRecord escherContainerRecord2 = new EscherContainerRecord();
        escherContainerRecord2.setOptions(15);
        escherContainerRecord2.setRecordId(EscherContainerRecord.SPGR_CONTAINER);
        EscherContainerRecord escherContainerRecord3 = new EscherContainerRecord();
        escherContainerRecord3.setOptions(15);
        escherContainerRecord3.setRecordId(EscherContainerRecord.SP_CONTAINER);
        EscherSpgrRecord escherSpgrRecord = new EscherSpgrRecord();
        escherSpgrRecord.setOptions(1);
        escherContainerRecord3.addChildRecord(escherSpgrRecord);
        EscherSpRecord escherSpRecord = new EscherSpRecord();
        escherSpRecord.setOptions(2);
        escherSpRecord.setFlags(5);
        escherContainerRecord3.addChildRecord(escherSpRecord);
        escherContainerRecord2.addChildRecord(escherContainerRecord3);
        escherContainerRecord.addChildRecord(escherContainerRecord2);
        EscherContainerRecord escherContainerRecord4 = new EscherContainerRecord();
        escherContainerRecord4.setOptions(15);
        escherContainerRecord4.setRecordId(EscherContainerRecord.SP_CONTAINER);
        EscherSpRecord escherSpRecord2 = new EscherSpRecord();
        escherSpRecord2.setOptions(18);
        escherSpRecord2.setFlags(3072);
        escherContainerRecord4.addChildRecord(escherSpRecord2);
        EscherOptRecord escherOptRecord = new EscherOptRecord();
        escherOptRecord.setRecordId(EscherOptRecord.RECORD_ID);
        escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.FILL__FILLCOLOR, 134217728));
        escherOptRecord.addEscherProperty(new EscherRGBProperty(EscherProperties.FILL__FILLBACKCOLOR, 134217733));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.FILL__RECTRIGHT, 10064750));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.FILL__RECTBOTTOM, 7778750));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.FILL__NOFILLHITTEST, 1179666));
        escherOptRecord.addEscherProperty(new EscherBoolProperty(EscherProperties.LINESTYLE__NOLINEDRAWDASH, 524288));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.SHAPE__BLACKANDWHITESETTINGS, 9));
        escherOptRecord.addEscherProperty(new EscherSimpleProperty(EscherProperties.SHAPE__BACKGROUNDSHAPE, 65537));
        escherContainerRecord4.addChildRecord(escherOptRecord);
        escherContainerRecord.addChildRecord(escherContainerRecord4);
        this.childRecords = new EscherRecord[]{escherContainerRecord};
    }

    public void addTextboxWrapper(EscherTextboxWrapper escherTextboxWrapper) {
        EscherTextboxWrapper[] escherTextboxWrapperArr = this.textboxWrappers;
        EscherTextboxWrapper[] escherTextboxWrapperArr2 = new EscherTextboxWrapper[(escherTextboxWrapperArr.length + 1)];
        System.arraycopy(escherTextboxWrapperArr, 0, escherTextboxWrapperArr2, 0, escherTextboxWrapperArr.length);
        escherTextboxWrapperArr2[this.textboxWrappers.length] = escherTextboxWrapper;
        this.textboxWrappers = escherTextboxWrapperArr2;
    }

    public EscherDgRecord getEscherDgRecord() {
        if (this.dg == null) {
            Iterator<EscherRecord> childIterator = ((EscherContainerRecord) this.childRecords[0]).getChildIterator();
            while (true) {
                if (!childIterator.hasNext()) {
                    break;
                }
                EscherRecord next = childIterator.next();
                if (next instanceof EscherDgRecord) {
                    this.dg = (EscherDgRecord) next;
                    break;
                }
            }
        }
        return this.dg;
    }

    public void dispose() {
        this._header = null;
        EscherRecord[] escherRecordArr = this.childRecords;
        if (escherRecordArr != null) {
            for (EscherRecord dispose : escherRecordArr) {
                dispose.dispose();
            }
            this.childRecords = null;
        }
        EscherTextboxWrapper[] escherTextboxWrapperArr = this.textboxWrappers;
        if (escherTextboxWrapperArr != null) {
            for (EscherTextboxWrapper dispose2 : escherTextboxWrapperArr) {
                dispose2.dispose();
            }
            this.textboxWrappers = null;
        }
        EscherDgRecord escherDgRecord = this.dg;
        if (escherDgRecord != null) {
            escherDgRecord.dispose();
            this.dg = null;
        }
    }
}
