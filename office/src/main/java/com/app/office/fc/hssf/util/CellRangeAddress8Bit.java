package com.app.office.fc.hssf.util;

import com.app.office.fc.ss.util.CellRangeAddressBase;
import com.app.office.fc.util.LittleEndianByteArrayOutputStream;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class CellRangeAddress8Bit extends CellRangeAddressBase {
    public static final int ENCODED_SIZE = 6;

    public static int getEncodedSize(int i) {
        return i * 6;
    }

    public CellRangeAddress8Bit(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public CellRangeAddress8Bit(LittleEndianInput littleEndianInput) {
        super(readUShortAndCheck(littleEndianInput), littleEndianInput.readUShort(), littleEndianInput.readUByte(), littleEndianInput.readUByte());
    }

    private static int readUShortAndCheck(LittleEndianInput littleEndianInput) {
        if (littleEndianInput.available() >= 6) {
            return littleEndianInput.readUShort();
        }
        throw new RuntimeException("Ran out of data reading CellRangeAddress");
    }

    public int serialize(int i, byte[] bArr) {
        serialize(new LittleEndianByteArrayOutputStream(bArr, i, 6));
        return 6;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getFirstRow());
        littleEndianOutput.writeShort(getLastRow());
        littleEndianOutput.writeByte(getFirstColumn());
        littleEndianOutput.writeByte(getLastColumn());
    }

    public CellRangeAddress8Bit copy() {
        return new CellRangeAddress8Bit(getFirstRow(), getLastRow(), getFirstColumn(), getLastColumn());
    }
}
