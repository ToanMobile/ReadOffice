package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.hssf.record.UnknownRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.LittleEndianOutput;

public final class ObjectLinkRecord extends StandardRecord {
    public static final short ANCHOR_ID_CHART_TITLE = 1;
    public static final short ANCHOR_ID_SERIES_OR_POINT = 4;
    public static final short ANCHOR_ID_X_AXIS = 3;
    public static final short ANCHOR_ID_Y_AXIS = 2;
    public static final short ANCHOR_ID_Z_AXIS = 7;
    public static final short sid = 4135;
    private short field_1_anchorId;
    private short field_2_link1;
    private short field_3_link2;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 6;
    }

    public short getSid() {
        return sid;
    }

    public ObjectLinkRecord() {
    }

    public ObjectLinkRecord(RecordInputStream recordInputStream) {
        this.field_1_anchorId = recordInputStream.readShort();
        this.field_2_link1 = recordInputStream.readShort();
        this.field_3_link2 = recordInputStream.readShort();
    }

    public ObjectLinkRecord(UnknownRecord unknownRecord) {
        if (unknownRecord.getSid() == 4135 && unknownRecord.getData().length == getDataSize()) {
            byte[] data = unknownRecord.getData();
            this.field_1_anchorId = LittleEndian.getShort(data, 0);
            this.field_2_link1 = LittleEndian.getShort(data, 2);
            this.field_3_link2 = LittleEndian.getShort(data, 4);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[OBJECTLINK]\n");
        stringBuffer.append("    .anchorId             = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getAnchorId()));
        stringBuffer.append(" (");
        stringBuffer.append(getAnchorId());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .link1                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLink1()));
        stringBuffer.append(" (");
        stringBuffer.append(getLink1());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .link2                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLink2()));
        stringBuffer.append(" (");
        stringBuffer.append(getLink2());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/OBJECTLINK]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_anchorId);
        littleEndianOutput.writeShort(this.field_2_link1);
        littleEndianOutput.writeShort(this.field_3_link2);
    }

    public Object clone() {
        ObjectLinkRecord objectLinkRecord = new ObjectLinkRecord();
        objectLinkRecord.field_1_anchorId = this.field_1_anchorId;
        objectLinkRecord.field_2_link1 = this.field_2_link1;
        objectLinkRecord.field_3_link2 = this.field_3_link2;
        return objectLinkRecord;
    }

    public short getAnchorId() {
        return this.field_1_anchorId;
    }

    public void setAnchorId(short s) {
        this.field_1_anchorId = s;
    }

    public short getLink1() {
        return this.field_2_link1;
    }

    public void setLink1(short s) {
        this.field_2_link1 = s;
    }

    public short getLink2() {
        return this.field_3_link2;
    }

    public void setLink2(short s) {
        this.field_3_link2 = s;
    }
}
