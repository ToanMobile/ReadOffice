package com.app.office.fc.hssf.record.cont;

import com.app.office.fc.util.DelayableLittleEndianOutput;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class ContinuableRecordOutput implements LittleEndianOutput {
    private static final LittleEndianOutput NOPOutput = new DelayableLittleEndianOutput() {
        public LittleEndianOutput createDelayedOutput(int i) {
            return this;
        }

        public void write(byte[] bArr) {
        }

        public void write(byte[] bArr, int i, int i2) {
        }

        public void writeByte(int i) {
        }

        public void writeDouble(double d) {
        }

        public void writeInt(int i) {
        }

        public void writeLong(long j) {
        }

        public void writeShort(int i) {
        }
    };
    private final LittleEndianOutput _out;
    private int _totalPreviousRecordsSize = 0;
    private UnknownLengthRecordOutput _ulrOutput;

    public ContinuableRecordOutput(LittleEndianOutput littleEndianOutput, int i) {
        this._ulrOutput = new UnknownLengthRecordOutput(littleEndianOutput, i);
        this._out = littleEndianOutput;
    }

    public static ContinuableRecordOutput createForCountingOnly() {
        return new ContinuableRecordOutput(NOPOutput, -777);
    }

    public int getTotalSize() {
        return this._totalPreviousRecordsSize + this._ulrOutput.getTotalSize();
    }

    /* access modifiers changed from: package-private */
    public void terminate() {
        this._ulrOutput.terminate();
    }

    public int getAvailableSpace() {
        return this._ulrOutput.getAvailableSpace();
    }

    public void writeContinue() {
        this._ulrOutput.terminate();
        this._totalPreviousRecordsSize += this._ulrOutput.getTotalSize();
        this._ulrOutput = new UnknownLengthRecordOutput(this._out, 60);
    }

    public void writeContinueIfRequired(int i) {
        if (this._ulrOutput.getAvailableSpace() < i) {
            writeContinue();
        }
    }

    public void writeStringData(String str) {
        int i;
        int i2;
        boolean hasMultibyte = StringUtil.hasMultibyte(str);
        if (hasMultibyte) {
            i2 = 1;
            i = 3;
        } else {
            i = 2;
            i2 = 0;
        }
        writeContinueIfRequired(i);
        writeByte(i2);
        writeCharacterData(str, hasMultibyte);
    }

    public void writeString(String str, int i, int i2) {
        int i3;
        int i4;
        boolean hasMultibyte = StringUtil.hasMultibyte(str);
        if (hasMultibyte) {
            i4 = 1;
            i3 = 5;
        } else {
            i3 = 4;
            i4 = 0;
        }
        if (i > 0) {
            i4 |= 8;
            i3 += 2;
        }
        if (i2 > 0) {
            i4 |= 4;
            i3 += 4;
        }
        writeContinueIfRequired(i3);
        writeShort(str.length());
        writeByte(i4);
        if (i > 0) {
            writeShort(i);
        }
        if (i2 > 0) {
            writeInt(i2);
        }
        writeCharacterData(str, hasMultibyte);
    }

    private void writeCharacterData(String str, boolean z) {
        int length = str.length();
        int i = 0;
        if (z) {
            while (true) {
                int min = Math.min(length - i, this._ulrOutput.getAvailableSpace() / 2);
                while (min > 0) {
                    this._ulrOutput.writeShort(str.charAt(i));
                    min--;
                    i++;
                }
                if (i < length) {
                    writeContinue();
                    writeByte(1);
                } else {
                    return;
                }
            }
        } else {
            int i2 = 0;
            while (true) {
                int min2 = Math.min(length - i2, this._ulrOutput.getAvailableSpace() / 1);
                while (min2 > 0) {
                    this._ulrOutput.writeByte(str.charAt(i2));
                    min2--;
                    i2++;
                }
                if (i2 < length) {
                    writeContinue();
                    writeByte(0);
                } else {
                    return;
                }
            }
        }
    }

    public void write(byte[] bArr) {
        writeContinueIfRequired(bArr.length);
        this._ulrOutput.write(bArr);
    }

    public void write(byte[] bArr, int i, int i2) {
        int i3 = 0;
        while (true) {
            int min = Math.min(i2 - i3, this._ulrOutput.getAvailableSpace() / 1);
            while (min > 0) {
                this._ulrOutput.writeByte(bArr[i3 + i]);
                min--;
                i3++;
            }
            if (i3 < i2) {
                writeContinue();
            } else {
                return;
            }
        }
    }

    public void writeByte(int i) {
        writeContinueIfRequired(1);
        this._ulrOutput.writeByte(i);
    }

    public void writeDouble(double d) {
        writeContinueIfRequired(8);
        this._ulrOutput.writeDouble(d);
    }

    public void writeInt(int i) {
        writeContinueIfRequired(4);
        this._ulrOutput.writeInt(i);
    }

    public void writeLong(long j) {
        writeContinueIfRequired(8);
        this._ulrOutput.writeLong(j);
    }

    public void writeShort(int i) {
        writeContinueIfRequired(2);
        this._ulrOutput.writeShort(i);
    }
}
