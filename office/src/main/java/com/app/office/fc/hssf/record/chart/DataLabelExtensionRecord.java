package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class DataLabelExtensionRecord extends StandardRecord {
    private static final BitField showBubbleSizes = BitFieldFactory.getInstance(16);
    private static final BitField showCategoryName = BitFieldFactory.getInstance(2);
    private static final BitField showPercent = BitFieldFactory.getInstance(8);
    private static final BitField showSeriesName = BitFieldFactory.getInstance(1);
    private static final BitField showValue = BitFieldFactory.getInstance(4);
    public static final short sid = 2155;
    private short cchSep;
    private short grbit;
    private int grbitFrt;
    private String rgchSep;
    private int rt;
    private byte[] unused = new byte[8];

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 12;
    }

    public short getSid() {
        return sid;
    }

    public DataLabelExtensionRecord(RecordInputStream recordInputStream) {
        this.rt = recordInputStream.readShort();
        this.grbitFrt = recordInputStream.readShort();
        recordInputStream.readFully(this.unused);
        this.grbit = recordInputStream.readShort();
        this.cchSep = recordInputStream.readShort();
        byte[] bArr = new byte[recordInputStream.available()];
        recordInputStream.readFully(bArr);
        this.rgchSep = StringUtil.getFromUnicodeLE(bArr);
    }

    public boolean isShowSeriesName() {
        return showSeriesName.isSet(this.grbit);
    }

    public boolean isShowCategoryName() {
        return showCategoryName.isSet(this.grbit);
    }

    public boolean isShowValue() {
        return showValue.isSet(this.grbit);
    }

    public boolean isShowPercent() {
        return showPercent.isSet(this.grbit);
    }

    public boolean isShowBubbleSizes() {
        return showBubbleSizes.isSet(this.grbit);
    }

    public String getSeparator() {
        return this.rgchSep;
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rt);
        littleEndianOutput.writeShort(this.grbitFrt);
        littleEndianOutput.write(this.unused);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[DATALABEXT]\n");
        stringBuffer.append("    .rt      =");
        stringBuffer.append(HexDump.shortToHex(this.rt));
        stringBuffer.append(10);
        stringBuffer.append("    .grbitFrt=");
        stringBuffer.append(HexDump.shortToHex(this.grbitFrt));
        stringBuffer.append(10);
        stringBuffer.append("    .unused  =");
        stringBuffer.append(HexDump.toHex(this.unused));
        stringBuffer.append(10);
        stringBuffer.append("[/DATALABEXT]\n");
        return stringBuffer.toString();
    }
}
