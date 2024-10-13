package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.crypto.Biff8DecryptingStream;
import com.app.office.fc.hssf.record.crypto.Biff8EncryptionKey;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class RecordInputStream implements LittleEndianInput {
    private static final int DATA_LEN_NEEDS_TO_BE_READ = -1;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int INVALID_SID_VALUE = -1;
    public static final short MAX_RECORD_DATA_SIZE = 8224;
    private final BiffHeaderInput _bhi;
    private int _currentDataLength;
    private int _currentDataOffset;
    private int _currentSid;
    private final LittleEndianInput _dataInput;
    private int _nextSid;

    public static final class LeftoverDataException extends RuntimeException {
        public LeftoverDataException(int i, int i2) {
            super("Initialisation of record 0x" + Integer.toHexString(i).toUpperCase() + " left " + i2 + " bytes remaining still to be read.");
        }
    }

    private static final class SimpleHeaderInput implements BiffHeaderInput {
        private final LittleEndianInput _lei;

        public SimpleHeaderInput(InputStream inputStream) {
            this._lei = RecordInputStream.getLEI(inputStream);
        }

        public int available() {
            return this._lei.available();
        }

        public int readDataSize() {
            return this._lei.readUShort();
        }

        public int readRecordSID() {
            return this._lei.readUShort();
        }
    }

    public RecordInputStream(InputStream inputStream) throws RecordFormatException {
        this(inputStream, (Biff8EncryptionKey) null, 0);
    }

    public RecordInputStream(InputStream inputStream, Biff8EncryptionKey biff8EncryptionKey, int i) throws RecordFormatException {
        if (biff8EncryptionKey == null) {
            this._dataInput = getLEI(inputStream);
            this._bhi = new SimpleHeaderInput(inputStream);
        } else {
            Biff8DecryptingStream biff8DecryptingStream = new Biff8DecryptingStream(inputStream, i, biff8EncryptionKey);
            this._bhi = biff8DecryptingStream;
            this._dataInput = biff8DecryptingStream;
        }
        this._nextSid = readNextSid();
    }

    static LittleEndianInput getLEI(InputStream inputStream) {
        if (inputStream instanceof LittleEndianInput) {
            return (LittleEndianInput) inputStream;
        }
        return new LittleEndianInputStream(inputStream);
    }

    public int available() {
        return remaining();
    }

    public int read(byte[] bArr, int i, int i2) {
        int min = Math.min(i2, remaining());
        if (min == 0) {
            return 0;
        }
        readFully(bArr, i, min);
        return min;
    }

    public short getSid() {
        return (short) this._currentSid;
    }

    public boolean hasNextRecord() throws LeftoverDataException {
        int i = this._currentDataLength;
        if (i == -1 || i == this._currentDataOffset) {
            if (i != -1) {
                this._nextSid = readNextSid();
            }
            return this._nextSid != -1;
        }
        throw new LeftoverDataException(this._currentSid, remaining());
    }

    private int readNextSid() {
        if (this._bhi.available() < 4) {
            return -1;
        }
        int readRecordSID = this._bhi.readRecordSID();
        if (readRecordSID != -1) {
            this._currentDataLength = -1;
            return readRecordSID;
        }
        throw new RecordFormatException("Found invalid sid (" + readRecordSID + ")");
    }

    public void nextRecord() throws RecordFormatException {
        int i = this._nextSid;
        if (i == -1) {
            throw new IllegalStateException("EOF - next record not available");
        } else if (this._currentDataLength == -1) {
            this._currentSid = i;
            this._currentDataOffset = 0;
            int readDataSize = this._bhi.readDataSize();
            this._currentDataLength = readDataSize;
            if (readDataSize > 8224) {
                throw new RecordFormatException("The content of an excel record cannot exceed 8224 bytes");
            }
        } else {
            throw new IllegalStateException("Cannot call nextRecord() without checking hasNextRecord() first");
        }
    }

    private void checkRecordPosition(int i) {
        int remaining = remaining();
        if (remaining < i) {
            if (remaining != 0 || !isContinueNext()) {
                throw new RecordFormatException("Not enough data (" + remaining + ") to read requested (" + i + ") bytes");
            }
            nextRecord();
        }
    }

    public byte readByte() {
        checkRecordPosition(1);
        this._currentDataOffset++;
        return this._dataInput.readByte();
    }

    public short readShort() {
        checkRecordPosition(2);
        this._currentDataOffset += 2;
        return this._dataInput.readShort();
    }

    public int readInt() {
        checkRecordPosition(4);
        this._currentDataOffset += 4;
        return this._dataInput.readInt();
    }

    public long readLong() {
        checkRecordPosition(8);
        this._currentDataOffset += 8;
        return this._dataInput.readLong();
    }

    public int readUByte() {
        return readByte() & 255;
    }

    public int readUShort() {
        checkRecordPosition(2);
        this._currentDataOffset += 2;
        return this._dataInput.readUShort();
    }

    public double readDouble() {
        double longBitsToDouble = Double.longBitsToDouble(readLong());
        Double.isNaN(longBitsToDouble);
        return longBitsToDouble;
    }

    public void readFully(byte[] bArr) {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) {
        checkRecordPosition(i2);
        this._dataInput.readFully(bArr, i, i2);
        this._currentDataOffset += i2;
    }

    public String readString() {
        return readStringCommon(readUShort(), readByte() == 0);
    }

    public String readUnicodeLEString(int i) {
        return readStringCommon(i, false);
    }

    public String readCompressedUnicode(int i) {
        return readStringCommon(i, true);
    }

    private String readStringCommon(int i, boolean z) {
        int i2;
        int i3;
        if (i < 0 || i > 1048576) {
            throw new IllegalArgumentException("Bad requested string length (" + i + ")");
        }
        char[] cArr = new char[i];
        int i4 = 0;
        while (true) {
            int remaining = remaining();
            if (!z) {
                remaining /= 2;
            }
            if (i - i4 <= remaining) {
                while (i4 < i) {
                    if (z) {
                        i2 = readUByte();
                    } else {
                        i2 = readShort();
                    }
                    cArr[i4] = (char) i2;
                    i4++;
                }
                return new String(cArr);
            }
            while (remaining > 0) {
                if (z) {
                    i3 = readUByte();
                } else {
                    i3 = readShort();
                }
                cArr[i4] = (char) i3;
                i4++;
                remaining--;
            }
            if (!isContinueNext()) {
                throw new RecordFormatException("Expected to find a ContinueRecord in order to read remaining " + (i - i4) + " of " + i + " chars");
            } else if (remaining() == 0) {
                nextRecord();
                z = readByte() == 0;
            } else {
                throw new RecordFormatException("Odd number of bytes(" + remaining() + ") left behind");
            }
        }
    }

    public byte[] readRemainder() {
        int remaining = remaining();
        if (remaining == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[remaining];
        readFully(bArr);
        return bArr;
    }

    public byte[] readAllContinuedRemainder() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16448);
        while (true) {
            byte[] readRemainder = readRemainder();
            byteArrayOutputStream.write(readRemainder, 0, readRemainder.length);
            if (!isContinueNext()) {
                return byteArrayOutputStream.toByteArray();
            }
            nextRecord();
        }
    }

    public int remaining() {
        int i = this._currentDataLength;
        if (i == -1) {
            return 0;
        }
        return i - this._currentDataOffset;
    }

    private boolean isContinueNext() {
        int i = this._currentDataLength;
        if (i != -1 && this._currentDataOffset != i) {
            throw new IllegalStateException("Should never be called before end of current record");
        } else if (hasNextRecord() && this._nextSid == 60) {
            return true;
        } else {
            return false;
        }
    }

    public int getNextSid() {
        return this._nextSid;
    }
}
