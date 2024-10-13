package com.app.office.fc.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public final class IOUtils {
    private static final POILogger logger = POILogFactory.getLogger(IOUtils.class);

    private IOUtils() {
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        int i = 0;
        while (i != -1) {
            i = inputStream.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toByteArray(ByteBuffer byteBuffer, int i) {
        if (byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
            return byteBuffer.array();
        }
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return bArr;
    }

    public static int readFully(InputStream inputStream, byte[] bArr) throws IOException {
        return readFully(inputStream, bArr, 0, bArr.length);
    }

    public static int readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        do {
            int read = inputStream.read(bArr, i + i3, i2 - i3);
            if (read >= 0) {
                i3 += read;
            } else if (i3 == 0) {
                return -1;
            } else {
                return i3;
            }
        } while (i3 != i2);
        return i3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0007 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x000b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int readFully(java.nio.channels.ReadableByteChannel r3, java.nio.ByteBuffer r4) throws java.io.IOException {
        /*
            r0 = 0
        L_0x0001:
            int r1 = r3.read(r4)
            if (r1 >= 0) goto L_0x000b
            if (r0 != 0) goto L_0x000a
            r0 = -1
        L_0x000a:
            return r0
        L_0x000b:
            int r0 = r0 + r1
            int r1 = r4.capacity()
            if (r0 == r1) goto L_0x001c
            int r1 = r4.position()
            int r2 = r4.capacity()
            if (r1 != r2) goto L_0x0001
        L_0x001c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.util.IOUtils.readFully(java.nio.channels.ReadableByteChannel, java.nio.ByteBuffer):int");
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return;
            }
            if (read > 0) {
                outputStream.write(bArr, 0, read);
            }
        }
    }

    public static long calculateChecksum(byte[] bArr) {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr, 0, bArr.length);
        return crc32.getValue();
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            POILogger pOILogger = logger;
            int i = POILogger.ERROR;
            pOILogger.log(i, (Object) "Unable to close resource: " + e, (Throwable) e);
        }
    }
}
