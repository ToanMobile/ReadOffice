package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class TableStylesRecord extends StandardRecord {
    public static final short sid = 2190;
    private int cts;
    private int grbitFrt;
    private String rgchDefListStyle;
    private String rgchDefPivotStyle;
    private int rt;
    private byte[] unused = new byte[8];

    public short getSid() {
        return sid;
    }

    public TableStylesRecord(RecordInputStream recordInputStream) {
        this.rt = recordInputStream.readUShort();
        this.grbitFrt = recordInputStream.readUShort();
        recordInputStream.readFully(this.unused);
        this.cts = recordInputStream.readInt();
        int readUShort = recordInputStream.readUShort();
        int readUShort2 = recordInputStream.readUShort();
        this.rgchDefListStyle = recordInputStream.readUnicodeLEString(readUShort);
        this.rgchDefPivotStyle = recordInputStream.readUnicodeLEString(readUShort2);
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.write(this.unused);
        littleEndianOutput.writeInt(this.cts);
        littleEndianOutput.writeShort(this.rgchDefListStyle.length());
        littleEndianOutput.writeShort(this.rgchDefPivotStyle.length());
        StringUtil.putUnicodeLE(this.rgchDefListStyle, littleEndianOutput);
        StringUtil.putUnicodeLE(this.rgchDefPivotStyle, littleEndianOutput);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this.rgchDefListStyle.length() * 2) + 20 + (this.rgchDefPivotStyle.length() * 2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TABLESTYLES]\n");
        stringBuffer.append("    .rt      =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append(10);
        stringBuffer.append("    .grbitFrt=");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append(10);
        stringBuffer.append("    .unused  =");
        stringBuffer.append(HexDump.toHex(this.unused));
        stringBuffer.append(10);
        stringBuffer.append("    .cts=");
        stringBuffer.append(HexDump.intToHex(this.cts));
        stringBuffer.append(10);
        stringBuffer.append("    .rgchDefListStyle=");
        stringBuffer.append(this.rgchDefListStyle);
        stringBuffer.append(10);
        stringBuffer.append("    .rgchDefPivotStyle=");
        stringBuffer.append(this.rgchDefPivotStyle);
        stringBuffer.append(10);
        stringBuffer.append("[/TABLESTYLES]\n");
        return stringBuffer.toString();
    }
}
