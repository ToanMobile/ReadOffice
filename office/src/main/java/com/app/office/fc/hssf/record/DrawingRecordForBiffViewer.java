package com.app.office.fc.hssf.record;

import java.io.ByteArrayInputStream;

public final class DrawingRecordForBiffViewer extends AbstractEscherHolderRecord {
    public static final short sid = 236;

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "MSODRAWING";
    }

    public short getSid() {
        return 236;
    }

    public DrawingRecordForBiffViewer() {
    }

    public DrawingRecordForBiffViewer(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }

    public DrawingRecordForBiffViewer(DrawingRecord drawingRecord) {
        super(convertToInputStream(drawingRecord));
        convertRawBytesToEscherRecords();
    }

    private static RecordInputStream convertToInputStream(DrawingRecord drawingRecord) {
        RecordInputStream recordInputStream = new RecordInputStream(new ByteArrayInputStream(drawingRecord.serialize()));
        recordInputStream.nextRecord();
        return recordInputStream;
    }
}
