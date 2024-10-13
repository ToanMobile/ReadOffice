package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.LittleEndianOutput;

public class ChartTitleFormatRecord extends StandardRecord {
    public static final short sid = 4176;
    private CTFormat[] _formats;

    public short getSid() {
        return sid;
    }

    private static final class CTFormat {
        public static final int ENCODED_SIZE = 4;
        private int _fontIndex;
        private int _offset;

        protected CTFormat(short s, short s2) {
            this._offset = s;
            this._fontIndex = s2;
        }

        public CTFormat(RecordInputStream recordInputStream) {
            this._offset = recordInputStream.readShort();
            this._fontIndex = recordInputStream.readShort();
        }

        public int getOffset() {
            return this._offset;
        }

        public void setOffset(int i) {
            this._offset = i;
        }

        public int getFontIndex() {
            return this._fontIndex;
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this._offset);
            littleEndianOutput.writeShort(this._fontIndex);
        }
    }

    public ChartTitleFormatRecord(RecordInputStream recordInputStream) {
        int readUShort = recordInputStream.readUShort();
        this._formats = new CTFormat[readUShort];
        for (int i = 0; i < readUShort; i++) {
            this._formats[i] = new CTFormat(recordInputStream);
        }
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._formats.length);
        int i = 0;
        while (true) {
            CTFormat[] cTFormatArr = this._formats;
            if (i < cTFormatArr.length) {
                cTFormatArr[i].serialize(littleEndianOutput);
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this._formats.length * 4) + 2;
    }

    public int getFormatCount() {
        return this._formats.length;
    }

    public void modifyFormatRun(short s, short s2) {
        int i = 0;
        int i2 = 0;
        while (true) {
            CTFormat[] cTFormatArr = this._formats;
            if (i < cTFormatArr.length) {
                CTFormat cTFormat = cTFormatArr[i];
                if (i2 != 0) {
                    cTFormat.setOffset(cTFormat.getOffset() + i2);
                } else if (s == cTFormat.getOffset()) {
                    CTFormat[] cTFormatArr2 = this._formats;
                    if (i < cTFormatArr2.length - 1) {
                        i2 = s2 - (cTFormatArr2[i + 1].getOffset() - cTFormat.getOffset());
                    }
                }
                i++;
            } else {
                return;
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CHARTTITLEFORMAT]\n");
        stringBuffer.append("    .format_runs       = ");
        stringBuffer.append(this._formats.length);
        stringBuffer.append("\n");
        int i = 0;
        while (true) {
            CTFormat[] cTFormatArr = this._formats;
            if (i < cTFormatArr.length) {
                CTFormat cTFormat = cTFormatArr[i];
                stringBuffer.append("       .char_offset= ");
                stringBuffer.append(cTFormat.getOffset());
                stringBuffer.append(",.fontidx= ");
                stringBuffer.append(cTFormat.getFontIndex());
                stringBuffer.append("\n");
                i++;
            } else {
                stringBuffer.append("[/CHARTTITLEFORMAT]\n");
                return stringBuffer.toString();
            }
        }
    }
}
