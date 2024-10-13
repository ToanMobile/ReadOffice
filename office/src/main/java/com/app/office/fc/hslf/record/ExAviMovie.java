package com.app.office.fc.hslf.record;

public final class ExAviMovie extends ExMCIMovie {
    protected ExAviMovie(byte[] bArr, int i, int i2) {
        super(bArr, i, i2);
    }

    public ExAviMovie() {
    }

    public long getRecordType() {
        return (long) RecordTypes.ExAviMovie.typeID;
    }
}
