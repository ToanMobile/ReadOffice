package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.util.RKUtil;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class MulRKRecord extends StandardRecord {
    public static final short sid = 189;
    private int field_1_row;
    private short field_2_first_col;
    private RkRec[] field_3_rks;
    private short field_4_last_col;

    public short getSid() {
        return 189;
    }

    public int getRow() {
        return this.field_1_row;
    }

    public short getFirstColumn() {
        return this.field_2_first_col;
    }

    public short getLastColumn() {
        return this.field_4_last_col;
    }

    public int getNumColumns() {
        return (this.field_4_last_col - this.field_2_first_col) + 1;
    }

    public short getXFAt(int i) {
        return this.field_3_rks[i].xf;
    }

    public double getRKNumberAt(int i) {
        return RKUtil.decodeNumber(this.field_3_rks[i].rk);
    }

    public MulRKRecord(RecordInputStream recordInputStream) {
        this.field_1_row = recordInputStream.readUShort();
        this.field_2_first_col = recordInputStream.readShort();
        this.field_3_rks = RkRec.parseRKs(recordInputStream);
        this.field_4_last_col = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[MULRK]\n");
        stringBuffer.append("\t.row\t = ");
        stringBuffer.append(HexDump.shortToHex(getRow()));
        stringBuffer.append("\n");
        stringBuffer.append("\t.firstcol= ");
        stringBuffer.append(HexDump.shortToHex(getFirstColumn()));
        stringBuffer.append("\n");
        stringBuffer.append("\t.lastcol = ");
        stringBuffer.append(HexDump.shortToHex(getLastColumn()));
        stringBuffer.append("\n");
        for (int i = 0; i < getNumColumns(); i++) {
            stringBuffer.append("\txf[");
            stringBuffer.append(i);
            stringBuffer.append("] = ");
            stringBuffer.append(HexDump.shortToHex(getXFAt(i)));
            stringBuffer.append("\n");
            stringBuffer.append("\trk[");
            stringBuffer.append(i);
            stringBuffer.append("] = ");
            stringBuffer.append(getRKNumberAt(i));
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/MULRK]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        throw new RecordFormatException("Sorry, you can't serialize MulRK in this release");
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        throw new RecordFormatException("Sorry, you can't serialize MulRK in this release");
    }

    private static final class RkRec {
        public static final int ENCODED_SIZE = 6;
        public final int rk;
        public final short xf;

        private RkRec(RecordInputStream recordInputStream) {
            this.xf = recordInputStream.readShort();
            this.rk = recordInputStream.readInt();
        }

        public static RkRec[] parseRKs(RecordInputStream recordInputStream) {
            int remaining = (recordInputStream.remaining() - 2) / 6;
            RkRec[] rkRecArr = new RkRec[remaining];
            for (int i = 0; i < remaining; i++) {
                rkRecArr[i] = new RkRec(recordInputStream);
            }
            return rkRecArr;
        }
    }
}
