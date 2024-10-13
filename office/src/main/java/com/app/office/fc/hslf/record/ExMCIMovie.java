package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogger;

public class ExMCIMovie extends RecordContainer {
    private byte[] _header;
    private ExVideoContainer exVideo;

    protected ExMCIMovie(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    public ExMCIMovie() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        ExVideoContainer exVideoContainer = new ExVideoContainer();
        this.exVideo = exVideoContainer;
        this._children = new Record[]{exVideoContainer};
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof ExVideoContainer) {
            this.exVideo = (ExVideoContainer) this._children[0];
            return;
        }
        POILogger pOILogger = this.logger;
        int i = POILogger.ERROR;
        pOILogger.log(i, (Object) "First child record wasn't a ExVideoContainer, was of type " + this._children[0].getRecordType());
    }

    public long getRecordType() {
        return (long) RecordTypes.ExMCIMovie.typeID;
    }

    public ExVideoContainer getExVideo() {
        return this.exVideo;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        ExVideoContainer exVideoContainer = this.exVideo;
        if (exVideoContainer != null) {
            exVideoContainer.dispose();
            this.exVideo = null;
        }
    }
}
