package com.app.office.fc.hssf.record.pivottable;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class DataItemRecord extends StandardRecord {
    public static final short sid = 197;
    private int df;
    private int ifmt;
    private int iiftab;
    private int isxvd;
    private int isxvdData;
    private int isxvi;
    private String name;

    public short getSid() {
        return 197;
    }

    public DataItemRecord(RecordInputStream recordInputStream) {
        this.isxvdData = recordInputStream.readUShort();
        this.iiftab = recordInputStream.readUShort();
        this.df = recordInputStream.readUShort();
        this.isxvd = recordInputStream.readUShort();
        this.isxvi = recordInputStream.readUShort();
        this.ifmt = recordInputStream.readUShort();
        this.name = recordInputStream.readString();
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.isxvdData);
        littleEndianOutput.writeShort(this.iiftab);
        littleEndianOutput.writeShort(this.df);
        littleEndianOutput.writeShort(this.isxvd);
        littleEndianOutput.writeShort(this.isxvi);
        littleEndianOutput.writeShort(this.ifmt);
        StringUtil.writeUnicodeString(littleEndianOutput, this.name);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return StringUtil.getEncodedSize(this.name) + 12;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXDI]\n");
        stringBuffer.append("  .isxvdData = ");
        stringBuffer.append(HexDump.shortToHex(this.isxvdData));
        stringBuffer.append("\n");
        stringBuffer.append("  .iiftab = ");
        stringBuffer.append(HexDump.shortToHex(this.iiftab));
        stringBuffer.append("\n");
        stringBuffer.append("  .df = ");
        stringBuffer.append(HexDump.shortToHex(this.df));
        stringBuffer.append("\n");
        stringBuffer.append("  .isxvd = ");
        stringBuffer.append(HexDump.shortToHex(this.isxvd));
        stringBuffer.append("\n");
        stringBuffer.append("  .isxvi = ");
        stringBuffer.append(HexDump.shortToHex(this.isxvi));
        stringBuffer.append("\n");
        stringBuffer.append("  .ifmt = ");
        stringBuffer.append(HexDump.shortToHex(this.ifmt));
        stringBuffer.append("\n");
        stringBuffer.append("[/SXDI]\n");
        return stringBuffer.toString();
    }
}
