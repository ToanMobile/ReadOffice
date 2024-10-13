package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.common.UnicodeString;
import com.app.office.fc.hssf.record.cont.ContinuableRecord;
import com.app.office.fc.hssf.record.cont.ContinuableRecordOutput;
import com.app.office.fc.util.IntMapper;
import java.util.Iterator;

public final class SSTRecord extends ContinuableRecord {
    private static final UnicodeString EMPTY_STRING = new UnicodeString("");
    static final int MAX_DATA_SPACE = 8216;
    static final int SST_RECORD_OVERHEAD = 12;
    static final int STD_RECORD_OVERHEAD = 4;
    public static final short sid = 252;
    int[] bucketAbsoluteOffsets;
    int[] bucketRelativeOffsets;
    private SSTDeserializer deserializer;
    private int field_1_num_strings;
    private int field_2_num_unique_strings;
    private IntMapper<UnicodeString> field_3_strings;

    public short getSid() {
        return 252;
    }

    public SSTRecord() {
        this.field_1_num_strings = 0;
        this.field_2_num_unique_strings = 0;
        IntMapper<UnicodeString> intMapper = new IntMapper<>();
        this.field_3_strings = intMapper;
        this.deserializer = new SSTDeserializer(intMapper);
    }

    public int addString(UnicodeString unicodeString) {
        this.field_1_num_strings++;
        if (unicodeString == null) {
            unicodeString = EMPTY_STRING;
        }
        int index = this.field_3_strings.getIndex(unicodeString);
        if (index != -1) {
            return index;
        }
        int size = this.field_3_strings.size();
        this.field_2_num_unique_strings++;
        SSTDeserializer.addToStringTable(this.field_3_strings, unicodeString);
        return size;
    }

    public int getNumStrings() {
        return this.field_1_num_strings;
    }

    public int getNumUniqueStrings() {
        return this.field_2_num_unique_strings;
    }

    public UnicodeString getString(int i) {
        return this.field_3_strings.get(i);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SST]\n");
        stringBuffer.append("    .numstrings     = ");
        stringBuffer.append(Integer.toHexString(getNumStrings()));
        stringBuffer.append("\n");
        stringBuffer.append("    .uniquestrings  = ");
        stringBuffer.append(Integer.toHexString(getNumUniqueStrings()));
        stringBuffer.append("\n");
        for (int i = 0; i < this.field_3_strings.size(); i++) {
            stringBuffer.append("    .string_" + i + "      = ");
            stringBuffer.append(this.field_3_strings.get(i).getDebugInfo());
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/SST]\n");
        return stringBuffer.toString();
    }

    public SSTRecord(RecordInputStream recordInputStream) {
        this.field_1_num_strings = recordInputStream.readInt();
        this.field_2_num_unique_strings = recordInputStream.readInt();
        IntMapper<UnicodeString> intMapper = new IntMapper<>();
        this.field_3_strings = intMapper;
        SSTDeserializer sSTDeserializer = new SSTDeserializer(intMapper);
        this.deserializer = sSTDeserializer;
        sSTDeserializer.manufactureStrings(this.field_2_num_unique_strings, recordInputStream);
    }

    /* access modifiers changed from: package-private */
    public Iterator<UnicodeString> getStrings() {
        return this.field_3_strings.iterator();
    }

    /* access modifiers changed from: package-private */
    public int countStrings() {
        return this.field_3_strings.size();
    }

    /* access modifiers changed from: protected */
    public void serialize(ContinuableRecordOutput continuableRecordOutput) {
        SSTSerializer sSTSerializer = new SSTSerializer(this.field_3_strings, getNumStrings(), getNumUniqueStrings());
        sSTSerializer.serialize(continuableRecordOutput);
        this.bucketAbsoluteOffsets = sSTSerializer.getBucketAbsoluteOffsets();
        this.bucketRelativeOffsets = sSTSerializer.getBucketRelativeOffsets();
    }

    /* access modifiers changed from: package-private */
    public SSTDeserializer getDeserializer() {
        return this.deserializer;
    }

    public ExtSSTRecord createExtSSTRecord(int i) {
        int[] iArr = this.bucketAbsoluteOffsets;
        if (iArr == null || iArr == null) {
            throw new IllegalStateException("SST record has not yet been serialized.");
        }
        ExtSSTRecord extSSTRecord = new ExtSSTRecord();
        extSSTRecord.setNumStringsPerBucket(8);
        int[] iArr2 = (int[]) this.bucketAbsoluteOffsets.clone();
        int[] iArr3 = (int[]) this.bucketRelativeOffsets.clone();
        for (int i2 = 0; i2 < iArr2.length; i2++) {
            iArr2[i2] = iArr2[i2] + i;
        }
        extSSTRecord.setBucketOffsets(iArr2, iArr3);
        return extSSTRecord;
    }

    public int calcExtSSTRecordSize() {
        return ExtSSTRecord.getRecordSizeForStrings(this.field_3_strings.size());
    }
}
