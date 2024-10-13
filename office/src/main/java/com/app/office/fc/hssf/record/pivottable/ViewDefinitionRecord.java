package com.app.office.fc.hssf.record.pivottable;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class ViewDefinitionRecord extends StandardRecord {
    public static final short sid = 176;
    private int cCol;
    private int cDim;
    private int cDimCol;
    private int cDimData;
    private int cDimPg;
    private int cDimRw;
    private int cRw;
    private int colFirst;
    private int colFirstData;
    private int colLast;
    private String dataField;
    private int grbit;
    private int iCache;
    private int ipos4Data;
    private int itblAutoFmt;
    private String name;
    private int reserved;
    private int rwFirst;
    private int rwFirstData;
    private int rwFirstHead;
    private int rwLast;
    private int sxaxis4Data;

    public short getSid() {
        return sid;
    }

    public ViewDefinitionRecord(RecordInputStream recordInputStream) {
        this.rwFirst = recordInputStream.readUShort();
        this.rwLast = recordInputStream.readUShort();
        this.colFirst = recordInputStream.readUShort();
        this.colLast = recordInputStream.readUShort();
        this.rwFirstHead = recordInputStream.readUShort();
        this.rwFirstData = recordInputStream.readUShort();
        this.colFirstData = recordInputStream.readUShort();
        this.iCache = recordInputStream.readUShort();
        this.reserved = recordInputStream.readUShort();
        this.sxaxis4Data = recordInputStream.readUShort();
        this.ipos4Data = recordInputStream.readUShort();
        this.cDim = recordInputStream.readUShort();
        this.cDimRw = recordInputStream.readUShort();
        this.cDimCol = recordInputStream.readUShort();
        this.cDimPg = recordInputStream.readUShort();
        this.cDimData = recordInputStream.readUShort();
        this.cRw = recordInputStream.readUShort();
        this.cCol = recordInputStream.readUShort();
        this.grbit = recordInputStream.readUShort();
        this.itblAutoFmt = recordInputStream.readUShort();
        int readUShort = recordInputStream.readUShort();
        int readUShort2 = recordInputStream.readUShort();
        this.name = StringUtil.readUnicodeString(recordInputStream, readUShort);
        this.dataField = StringUtil.readUnicodeString(recordInputStream, readUShort2);
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.rwFirst);
        littleEndianOutput.writeShort(this.rwLast);
        littleEndianOutput.writeShort(this.colFirst);
        littleEndianOutput.writeShort(this.colLast);
        littleEndianOutput.writeShort(this.rwFirstHead);
        littleEndianOutput.writeShort(this.rwFirstData);
        littleEndianOutput.writeShort(this.colFirstData);
        littleEndianOutput.writeShort(this.iCache);
        littleEndianOutput.writeShort(this.reserved);
        littleEndianOutput.writeShort(this.sxaxis4Data);
        littleEndianOutput.writeShort(this.ipos4Data);
        littleEndianOutput.writeShort(this.cDim);
        littleEndianOutput.writeShort(this.cDimRw);
        littleEndianOutput.writeShort(this.cDimCol);
        littleEndianOutput.writeShort(this.cDimPg);
        littleEndianOutput.writeShort(this.cDimData);
        littleEndianOutput.writeShort(this.cRw);
        littleEndianOutput.writeShort(this.cCol);
        littleEndianOutput.writeShort(this.grbit);
        littleEndianOutput.writeShort(this.itblAutoFmt);
        littleEndianOutput.writeShort(this.name.length());
        littleEndianOutput.writeShort(this.dataField.length());
        StringUtil.writeUnicodeStringFlagAndData(littleEndianOutput, this.name);
        StringUtil.writeUnicodeStringFlagAndData(littleEndianOutput, this.dataField);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return StringUtil.getEncodedSize(this.name) + 40 + StringUtil.getEncodedSize(this.dataField);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXVIEW]\n");
        stringBuffer.append("    .rwFirst      =");
        stringBuffer.append(HexDump.shortToHex(this.rwFirst));
        stringBuffer.append(10);
        stringBuffer.append("    .rwLast       =");
        stringBuffer.append(HexDump.shortToHex(this.rwLast));
        stringBuffer.append(10);
        stringBuffer.append("    .colFirst     =");
        stringBuffer.append(HexDump.shortToHex(this.colFirst));
        stringBuffer.append(10);
        stringBuffer.append("    .colLast      =");
        stringBuffer.append(HexDump.shortToHex(this.colLast));
        stringBuffer.append(10);
        stringBuffer.append("    .rwFirstHead  =");
        stringBuffer.append(HexDump.shortToHex(this.rwFirstHead));
        stringBuffer.append(10);
        stringBuffer.append("    .rwFirstData  =");
        stringBuffer.append(HexDump.shortToHex(this.rwFirstData));
        stringBuffer.append(10);
        stringBuffer.append("    .colFirstData =");
        stringBuffer.append(HexDump.shortToHex(this.colFirstData));
        stringBuffer.append(10);
        stringBuffer.append("    .iCache       =");
        stringBuffer.append(HexDump.shortToHex(this.iCache));
        stringBuffer.append(10);
        stringBuffer.append("    .reserved     =");
        stringBuffer.append(HexDump.shortToHex(this.reserved));
        stringBuffer.append(10);
        stringBuffer.append("    .sxaxis4Data  =");
        stringBuffer.append(HexDump.shortToHex(this.sxaxis4Data));
        stringBuffer.append(10);
        stringBuffer.append("    .ipos4Data    =");
        stringBuffer.append(HexDump.shortToHex(this.ipos4Data));
        stringBuffer.append(10);
        stringBuffer.append("    .cDim         =");
        stringBuffer.append(HexDump.shortToHex(this.cDim));
        stringBuffer.append(10);
        stringBuffer.append("    .cDimRw       =");
        stringBuffer.append(HexDump.shortToHex(this.cDimRw));
        stringBuffer.append(10);
        stringBuffer.append("    .cDimCol      =");
        stringBuffer.append(HexDump.shortToHex(this.cDimCol));
        stringBuffer.append(10);
        stringBuffer.append("    .cDimPg       =");
        stringBuffer.append(HexDump.shortToHex(this.cDimPg));
        stringBuffer.append(10);
        stringBuffer.append("    .cDimData     =");
        stringBuffer.append(HexDump.shortToHex(this.cDimData));
        stringBuffer.append(10);
        stringBuffer.append("    .cRw          =");
        stringBuffer.append(HexDump.shortToHex(this.cRw));
        stringBuffer.append(10);
        stringBuffer.append("    .cCol         =");
        stringBuffer.append(HexDump.shortToHex(this.cCol));
        stringBuffer.append(10);
        stringBuffer.append("    .grbit        =");
        stringBuffer.append(HexDump.shortToHex(this.grbit));
        stringBuffer.append(10);
        stringBuffer.append("    .itblAutoFmt  =");
        stringBuffer.append(HexDump.shortToHex(this.itblAutoFmt));
        stringBuffer.append(10);
        stringBuffer.append("    .name         =");
        stringBuffer.append(this.name);
        stringBuffer.append(10);
        stringBuffer.append("    .dataField    =");
        stringBuffer.append(this.dataField);
        stringBuffer.append(10);
        stringBuffer.append("[/SXVIEW]\n");
        return stringBuffer.toString();
    }
}
