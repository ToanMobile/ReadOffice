package com.app.office.fc.util;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.InputStream;

public interface FixedField {
    void readFromBytes(byte[] bArr) throws ArrayIndexOutOfBoundsException;

    void readFromStream(InputStream inputStream) throws IOException, LittleEndian.BufferUnderrunException;

    String toString();

    void writeToBytes(byte[] bArr) throws ArrayIndexOutOfBoundsException;
}
