package com.app.office.fc.hssf.record.common;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.util.LittleEndianOutput;

public final class FeatSmartTag implements SharedFeature {
    private byte[] data;

    public FeatSmartTag() {
        this.data = new byte[0];
    }

    public FeatSmartTag(RecordInputStream recordInputStream) {
        this.data = recordInputStream.readRemainder();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" [FEATURE SMART TAGS]\n");
        stringBuffer.append(" [/FEATURE SMART TAGS]\n");
        return stringBuffer.toString();
    }

    public int getDataSize() {
        return this.data.length;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.write(this.data);
    }
}
