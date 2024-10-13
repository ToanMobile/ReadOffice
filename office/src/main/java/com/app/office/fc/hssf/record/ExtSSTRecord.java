package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.cont.ContinuableRecord;
import com.app.office.fc.hssf.record.cont.ContinuableRecordOutput;
import com.app.office.fc.util.LittleEndianOutput;
import java.util.ArrayList;

public final class ExtSSTRecord extends ContinuableRecord {
    public static final int DEFAULT_BUCKET_SIZE = 8;
    public static final int MAX_BUCKETS = 128;
    public static final short sid = 255;
    private InfoSubRecord[] _sstInfos;
    private short _stringsPerBucket;

    public short getSid() {
        return 255;
    }

    public static final class InfoSubRecord {
        public static final int ENCODED_SIZE = 8;
        private int field_1_stream_pos;
        private int field_2_bucket_sst_offset;
        private short field_3_zero;

        public InfoSubRecord(int i, int i2) {
            this.field_1_stream_pos = i;
            this.field_2_bucket_sst_offset = i2;
        }

        public InfoSubRecord(RecordInputStream recordInputStream) {
            this.field_1_stream_pos = recordInputStream.readInt();
            this.field_2_bucket_sst_offset = recordInputStream.readShort();
            this.field_3_zero = recordInputStream.readShort();
        }

        public int getStreamPos() {
            return this.field_1_stream_pos;
        }

        public int getBucketSSTOffset() {
            return this.field_2_bucket_sst_offset;
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeInt(this.field_1_stream_pos);
            littleEndianOutput.writeShort(this.field_2_bucket_sst_offset);
            littleEndianOutput.writeShort(this.field_3_zero);
        }
    }

    public ExtSSTRecord() {
        this._stringsPerBucket = 8;
        this._sstInfos = new InfoSubRecord[0];
    }

    public ExtSSTRecord(RecordInputStream recordInputStream) {
        this._stringsPerBucket = recordInputStream.readShort();
        ArrayList arrayList = new ArrayList(recordInputStream.remaining() / 8);
        while (recordInputStream.available() > 0) {
            arrayList.add(new InfoSubRecord(recordInputStream));
            if (recordInputStream.available() == 0 && recordInputStream.hasNextRecord() && recordInputStream.getNextSid() == 60) {
                recordInputStream.nextRecord();
            }
        }
        this._sstInfos = (InfoSubRecord[]) arrayList.toArray(new InfoSubRecord[arrayList.size()]);
    }

    public void setNumStringsPerBucket(short s) {
        this._stringsPerBucket = s;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[EXTSST]\n");
        stringBuffer.append("    .dsst           = ");
        stringBuffer.append(Integer.toHexString(this._stringsPerBucket));
        stringBuffer.append("\n");
        stringBuffer.append("    .numInfoRecords = ");
        stringBuffer.append(this._sstInfos.length);
        stringBuffer.append("\n");
        for (int i = 0; i < this._sstInfos.length; i++) {
            stringBuffer.append("    .inforecord     = ");
            stringBuffer.append(i);
            stringBuffer.append("\n");
            stringBuffer.append("    .streampos      = ");
            stringBuffer.append(Integer.toHexString(this._sstInfos[i].getStreamPos()));
            stringBuffer.append("\n");
            stringBuffer.append("    .sstoffset      = ");
            stringBuffer.append(Integer.toHexString(this._sstInfos[i].getBucketSSTOffset()));
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/EXTSST]\n");
        return stringBuffer.toString();
    }

    public void serialize(ContinuableRecordOutput continuableRecordOutput) {
        continuableRecordOutput.writeShort(this._stringsPerBucket);
        int i = 0;
        while (true) {
            InfoSubRecord[] infoSubRecordArr = this._sstInfos;
            if (i < infoSubRecordArr.length) {
                infoSubRecordArr[i].serialize(continuableRecordOutput);
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this._sstInfos.length * 8) + 2;
    }

    /* access modifiers changed from: protected */
    public InfoSubRecord[] getInfoSubRecords() {
        return this._sstInfos;
    }

    public static final int getNumberOfInfoRecsForStrings(int i) {
        int i2 = i / 8;
        if (i % 8 != 0) {
            i2++;
        }
        if (i2 > 128) {
            return 128;
        }
        return i2;
    }

    public static final int getRecordSizeForStrings(int i) {
        return (getNumberOfInfoRecsForStrings(i) * 8) + 6;
    }

    public void setBucketOffsets(int[] iArr, int[] iArr2) {
        this._sstInfos = new InfoSubRecord[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            this._sstInfos[i] = new InfoSubRecord(iArr[i], iArr2[i]);
        }
    }
}
