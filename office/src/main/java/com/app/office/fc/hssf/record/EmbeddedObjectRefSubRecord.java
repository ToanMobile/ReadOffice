package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.AreaPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.hssf.formula.ptg.RefPtg;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianInputStream;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;
import java.io.ByteArrayInputStream;

public final class EmbeddedObjectRefSubRecord extends SubRecord {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final short sid = 9;
    private int field_1_unknown_int;
    private Ptg field_2_refPtg;
    private byte[] field_2_unknownFormulaData;
    private boolean field_3_unicode_flag;
    private String field_4_ole_classname;
    private Byte field_4_unknownByte;
    private Integer field_5_stream_id;
    private byte[] field_6_unknown;

    public Object clone() {
        return this;
    }

    public short getSid() {
        return 9;
    }

    EmbeddedObjectRefSubRecord() {
        this.field_2_unknownFormulaData = new byte[]{2, 108, 106, 22, 1};
        this.field_6_unknown = EMPTY_BYTE_ARRAY;
        this.field_4_ole_classname = null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: type inference failed for: r5v8 */
    /* JADX WARNING: type inference failed for: r5v11 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public EmbeddedObjectRefSubRecord(com.app.office.fc.util.LittleEndianInput r9, int r10) {
        /*
            r8 = this;
            r8.<init>()
            short r0 = r9.readShort()
            int r10 = r10 + -2
            int r0 = r10 - r0
            int r1 = r9.readUShort()
            int r10 = r10 + -2
            int r2 = r9.readInt()
            r8.field_1_unknown_int = r2
            int r10 = r10 + -4
            byte[] r2 = readRawData(r9, r1)
            int r10 = r10 - r1
            com.app.office.fc.hssf.formula.ptg.Ptg r3 = readRefPtg(r2)
            r8.field_2_refPtg = r3
            r4 = 0
            if (r3 != 0) goto L_0x002a
            r8.field_2_unknownFormulaData = r2
            goto L_0x002c
        L_0x002a:
            r8.field_2_unknownFormulaData = r4
        L_0x002c:
            int r2 = r0 + 3
            r3 = 4
            r5 = 0
            if (r10 < r2) goto L_0x006c
            byte r2 = r9.readByte()
            r6 = 3
            if (r2 != r6) goto L_0x0064
            int r2 = r9.readUShort()
            if (r2 <= 0) goto L_0x005e
            byte r6 = r9.readByte()
            r7 = 1
            r6 = r6 & r7
            if (r6 == 0) goto L_0x0048
            r5 = 1
        L_0x0048:
            r8.field_3_unicode_flag = r5
            if (r5 == 0) goto L_0x0055
            java.lang.String r5 = com.app.office.fc.util.StringUtil.readUnicodeLE(r9, r2)
            r8.field_4_ole_classname = r5
            int r2 = r2 * 2
            goto L_0x005b
        L_0x0055:
            java.lang.String r5 = com.app.office.fc.util.StringUtil.readCompressedUnicode(r9, r2)
            r8.field_4_ole_classname = r5
        L_0x005b:
            int r2 = r2 + r3
            r5 = r2
            goto L_0x006e
        L_0x005e:
            java.lang.String r2 = ""
            r8.field_4_ole_classname = r2
            r5 = 3
            goto L_0x006e
        L_0x0064:
            com.app.office.fc.hssf.record.RecordFormatException r9 = new com.app.office.fc.hssf.record.RecordFormatException
            java.lang.String r10 = "Expected byte 0x03 here"
            r9.<init>((java.lang.String) r10)
            throw r9
        L_0x006c:
            r8.field_4_ole_classname = r4
        L_0x006e:
            int r10 = r10 - r5
            int r5 = r5 + r1
            int r5 = r5 % 2
            if (r5 == 0) goto L_0x0089
            byte r1 = r9.readByte()
            int r10 = r10 + -1
            com.app.office.fc.hssf.formula.ptg.Ptg r2 = r8.field_2_refPtg
            if (r2 == 0) goto L_0x0089
            java.lang.String r2 = r8.field_4_ole_classname
            if (r2 != 0) goto L_0x0089
            byte r1 = (byte) r1
            java.lang.Byte r1 = java.lang.Byte.valueOf(r1)
            r8.field_4_unknownByte = r1
        L_0x0089:
            int r1 = r10 - r0
            if (r1 <= 0) goto L_0x00ac
            java.io.PrintStream r2 = java.lang.System.err
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Discarding "
            r5.append(r6)
            r5.append(r1)
            java.lang.String r6 = " unexpected padding bytes "
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r2.println(r5)
            readRawData(r9, r1)
            int r10 = r10 - r1
        L_0x00ac:
            if (r0 < r3) goto L_0x00bb
            int r0 = r9.readInt()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r8.field_5_stream_id = r0
            int r10 = r10 + -4
            goto L_0x00bd
        L_0x00bb:
            r8.field_5_stream_id = r4
        L_0x00bd:
            byte[] r9 = readRawData(r9, r10)
            r8.field_6_unknown = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.record.EmbeddedObjectRefSubRecord.<init>(com.app.office.fc.util.LittleEndianInput, int):void");
    }

    private static Ptg readRefPtg(byte[] bArr) {
        LittleEndianInputStream littleEndianInputStream = new LittleEndianInputStream(new ByteArrayInputStream(bArr));
        byte readByte = littleEndianInputStream.readByte();
        if (readByte == 36) {
            return new RefPtg((LittleEndianInput) littleEndianInputStream);
        }
        if (readByte == 37) {
            return new AreaPtg((LittleEndianInput) littleEndianInputStream);
        }
        if (readByte == 58) {
            return new Ref3DPtg(littleEndianInputStream);
        }
        if (readByte != 59) {
            return null;
        }
        return new Area3DPtg(littleEndianInputStream);
    }

    private static byte[] readRawData(LittleEndianInput littleEndianInput, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Negative size (" + i + ")");
        } else if (i == 0) {
            return EMPTY_BYTE_ARRAY;
        } else {
            byte[] bArr = new byte[i];
            littleEndianInput.readFully(bArr);
            return bArr;
        }
    }

    private int getStreamIDOffset(int i) {
        int i2 = i + 6;
        String str = this.field_4_ole_classname;
        if (str != null) {
            i2 += 3;
            int length = str.length();
            if (length > 0) {
                int i3 = i2 + 1;
                if (this.field_3_unicode_flag) {
                    length *= 2;
                }
                i2 = i3 + length;
            }
        }
        return i2 % 2 != 0 ? i2 + 1 : i2;
    }

    private int getDataSize(int i) {
        int i2 = i + 2;
        if (this.field_5_stream_id != null) {
            i2 += 4;
        }
        return i2 + this.field_6_unknown.length;
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        Ptg ptg = this.field_2_refPtg;
        return getDataSize(getStreamIDOffset(ptg == null ? this.field_2_unknownFormulaData.length : ptg.getSize()));
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        Ptg ptg = this.field_2_refPtg;
        int length = ptg == null ? this.field_2_unknownFormulaData.length : ptg.getSize();
        int streamIDOffset = getStreamIDOffset(length);
        int dataSize = getDataSize(streamIDOffset);
        littleEndianOutput.writeShort(9);
        littleEndianOutput.writeShort(dataSize);
        littleEndianOutput.writeShort(streamIDOffset);
        littleEndianOutput.writeShort(length);
        littleEndianOutput.writeInt(this.field_1_unknown_int);
        Ptg ptg2 = this.field_2_refPtg;
        if (ptg2 == null) {
            littleEndianOutput.write(this.field_2_unknownFormulaData);
        } else {
            ptg2.write(littleEndianOutput);
        }
        int i = length + 12;
        if (this.field_4_ole_classname != null) {
            littleEndianOutput.writeByte(3);
            int length2 = this.field_4_ole_classname.length();
            littleEndianOutput.writeShort(length2);
            i = i + 1 + 2;
            if (length2 > 0) {
                littleEndianOutput.writeByte(this.field_3_unicode_flag ? 1 : 0);
                int i2 = i + 1;
                if (this.field_3_unicode_flag) {
                    StringUtil.putUnicodeLE(this.field_4_ole_classname, littleEndianOutput);
                    length2 *= 2;
                } else {
                    StringUtil.putCompressedUnicode(this.field_4_ole_classname, littleEndianOutput);
                }
                i = i2 + length2;
            }
        }
        int i3 = streamIDOffset - (i - 6);
        if (i3 != 0) {
            if (i3 == 1) {
                Byte b = this.field_4_unknownByte;
                littleEndianOutput.writeByte(b == null ? 0 : b.intValue());
            } else {
                throw new IllegalStateException("Bad padding calculation (" + streamIDOffset + ", " + i + ")");
            }
        }
        Integer num = this.field_5_stream_id;
        if (num != null) {
            littleEndianOutput.writeInt(num.intValue());
        }
        littleEndianOutput.write(this.field_6_unknown);
    }

    public Integer getStreamId() {
        return this.field_5_stream_id;
    }

    public String getOLEClassName() {
        return this.field_4_ole_classname;
    }

    public byte[] getObjectData() {
        return this.field_6_unknown;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ftPictFmla]\n");
        stringBuffer.append("    .f2unknown     = ");
        stringBuffer.append(HexDump.intToHex(this.field_1_unknown_int));
        stringBuffer.append("\n");
        if (this.field_2_refPtg == null) {
            stringBuffer.append("    .f3unknown     = ");
            stringBuffer.append(HexDump.toHex(this.field_2_unknownFormulaData));
            stringBuffer.append("\n");
        } else {
            stringBuffer.append("    .formula       = ");
            stringBuffer.append(this.field_2_refPtg.toString());
            stringBuffer.append("\n");
        }
        if (this.field_4_ole_classname != null) {
            stringBuffer.append("    .unicodeFlag   = ");
            stringBuffer.append(this.field_3_unicode_flag);
            stringBuffer.append("\n");
            stringBuffer.append("    .oleClassname  = ");
            stringBuffer.append(this.field_4_ole_classname);
            stringBuffer.append("\n");
        }
        if (this.field_4_unknownByte != null) {
            stringBuffer.append("    .f4unknown   = ");
            stringBuffer.append(HexDump.byteToHex(this.field_4_unknownByte.intValue()));
            stringBuffer.append("\n");
        }
        if (this.field_5_stream_id != null) {
            stringBuffer.append("    .streamId      = ");
            stringBuffer.append(HexDump.intToHex(this.field_5_stream_id.intValue()));
            stringBuffer.append("\n");
        }
        if (this.field_6_unknown.length > 0) {
            stringBuffer.append("    .f7unknown     = ");
            stringBuffer.append(HexDump.toHex(this.field_6_unknown));
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/ftPictFmla]");
        return stringBuffer.toString();
    }
}
