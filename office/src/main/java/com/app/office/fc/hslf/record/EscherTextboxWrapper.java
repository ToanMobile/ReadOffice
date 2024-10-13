package com.app.office.fc.hslf.record;

import com.app.office.fc.ddf.EscherTextboxRecord;

public final class EscherTextboxWrapper extends RecordContainer {
    private EscherTextboxRecord _escherRecord;
    private long _type;
    private int shapeId;

    public EscherTextboxRecord getEscherRecord() {
        return this._escherRecord;
    }

    public EscherTextboxWrapper(EscherTextboxRecord escherTextboxRecord) {
        this._escherRecord = escherTextboxRecord;
        this._type = (long) escherTextboxRecord.getRecordId();
        byte[] data = this._escherRecord.getData();
        this._children = Record.findChildRecords(data, 0, data.length);
    }

    public EscherTextboxWrapper() {
        EscherTextboxRecord escherTextboxRecord = new EscherTextboxRecord();
        this._escherRecord = escherTextboxRecord;
        escherTextboxRecord.setRecordId(EscherTextboxRecord.RECORD_ID);
        this._escherRecord.setOptions(15);
        this._children = new Record[0];
    }

    public long getRecordType() {
        return this._type;
    }

    public int getShapeId() {
        return this.shapeId;
    }

    public void setShapeId(int i) {
        this.shapeId = i;
    }

    public void dispose() {
        super.dispose();
        EscherTextboxRecord escherTextboxRecord = this._escherRecord;
        if (escherTextboxRecord != null) {
            escherTextboxRecord.dispose();
            this._escherRecord = null;
        }
    }
}
