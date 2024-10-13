package com.app.office.fc.hpsf;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

public class TypeWriter {
    public static int writeToStream(OutputStream outputStream, short s) throws IOException {
        byte[] bArr = new byte[2];
        LittleEndian.putShort(bArr, 0, s);
        outputStream.write(bArr, 0, 2);
        return 2;
    }

    public static int writeToStream(OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[4];
        LittleEndian.putInt(bArr, 0, i);
        outputStream.write(bArr, 0, 4);
        return 4;
    }

    public static int writeToStream(OutputStream outputStream, long j) throws IOException {
        byte[] bArr = new byte[8];
        LittleEndian.putLong(bArr, 0, j);
        outputStream.write(bArr, 0, 8);
        return 8;
    }

    public static void writeUShortToStream(OutputStream outputStream, int i) throws IOException {
        if ((-65536 & i) == 0) {
            writeToStream(outputStream, (short) i);
            return;
        }
        throw new IllegalPropertySetDataException("Value " + i + " cannot be represented by 2 bytes.");
    }

    public static int writeUIntToStream(OutputStream outputStream, long j) throws IOException {
        long j2 = j & -4294967296L;
        if (j2 == 0 || j2 == -4294967296L) {
            return writeToStream(outputStream, (int) j);
        }
        throw new IllegalPropertySetDataException("Value " + j + " cannot be represented by 4 bytes.");
    }

    public static int writeToStream(OutputStream outputStream, ClassID classID) throws IOException {
        byte[] bArr = new byte[16];
        classID.write(bArr, 0);
        outputStream.write(bArr, 0, 16);
        return 16;
    }

    public static void writeToStream(OutputStream outputStream, Property[] propertyArr, int i) throws IOException, UnsupportedVariantTypeException {
        if (propertyArr != null) {
            for (Property property : propertyArr) {
                writeUIntToStream(outputStream, property.getID());
                writeUIntToStream(outputStream, (long) property.getSize());
            }
            for (Property property2 : propertyArr) {
                long type = property2.getType();
                writeUIntToStream(outputStream, type);
                VariantSupport.write(outputStream, (long) ((int) type), property2.getValue(), i);
            }
        }
    }

    public static int writeToStream(OutputStream outputStream, double d) throws IOException {
        byte[] bArr = new byte[8];
        LittleEndian.putDouble(bArr, 0, d);
        outputStream.write(bArr, 0, 8);
        return 8;
    }
}
