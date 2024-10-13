package com.app.office.fc.hslf.blip;

import com.app.office.fc.hslf.blip.Metafile;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.InflaterInputStream;

public final class WMF extends Metafile {
    public int getSignature() {
        return 8544;
    }

    public int getType() {
        return 3;
    }

    public byte[] getData() {
        try {
            byte[] rawData = getRawData();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(rawData);
            byteArrayInputStream.skip(8);
            Metafile.Header header = new Metafile.Header();
            header.read(rawData, 16);
            byteArrayInputStream.skip((long) (header.getSize() + 16));
            InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inflaterInputStream.read(bArr);
                if (read >= 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    inflaterInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            throw new HSLFException((Throwable) e);
        }
    }

    public void setData(byte[] bArr) throws IOException {
        AldusHeader aldusHeader = new AldusHeader();
        aldusHeader.read(bArr, 0);
        int size = aldusHeader.getSize() + 0;
        byte[] compress = compress(bArr, size, bArr.length - size);
        Metafile.Header header = new Metafile.Header();
        header.wmfsize = bArr.length - aldusHeader.getSize();
        header.bounds = new Rectangle((short) aldusHeader.left, (short) aldusHeader.top, ((short) aldusHeader.right) - ((short) aldusHeader.left), ((short) aldusHeader.bottom) - ((short) aldusHeader.top));
        int i = 1219200 / aldusHeader.inch;
        header.size = new Dimension(header.bounds.width * i, header.bounds.height * i);
        header.zipsize = compress.length;
        byte[] checksum = getChecksum(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(checksum);
        header.write(byteArrayOutputStream);
        byteArrayOutputStream.write(compress);
        setRawData(byteArrayOutputStream.toByteArray());
    }

    public class AldusHeader {
        public static final int APMHEADER_KEY = -1698247209;
        public int bottom;
        public int checksum;
        public int handle;
        public int inch = 72;
        public int left;
        public int reserved;
        public int right;
        public int top;

        public int getSize() {
            return 22;
        }

        public AldusHeader() {
        }

        public void read(byte[] bArr, int i) {
            int i2 = LittleEndian.getInt(bArr, i);
            int i3 = i + 4;
            if (i2 == -1698247209) {
                this.handle = LittleEndian.getUShort(bArr, i3);
                int i4 = i3 + 2;
                this.left = LittleEndian.getUShort(bArr, i4);
                int i5 = i4 + 2;
                this.top = LittleEndian.getUShort(bArr, i5);
                int i6 = i5 + 2;
                this.right = LittleEndian.getUShort(bArr, i6);
                int i7 = i6 + 2;
                this.bottom = LittleEndian.getUShort(bArr, i7);
                int i8 = i7 + 2;
                this.inch = LittleEndian.getUShort(bArr, i8);
                int i9 = i8 + 2;
                this.reserved = LittleEndian.getInt(bArr, i9);
                this.checksum = LittleEndian.getShort(bArr, i9 + 4);
                getChecksum();
                return;
            }
            throw new HSLFException("Not a valid WMF file");
        }

        public int getChecksum() {
            return ((((this.left ^ -43247) ^ this.top) ^ this.right) ^ this.bottom) ^ this.inch;
        }

        public void write(OutputStream outputStream) throws IOException {
            byte[] bArr = new byte[22];
            LittleEndian.putInt(bArr, 0, APMHEADER_KEY);
            LittleEndian.putUShort(bArr, 4, 0);
            LittleEndian.putUShort(bArr, 6, this.left);
            LittleEndian.putUShort(bArr, 8, this.top);
            LittleEndian.putUShort(bArr, 10, this.right);
            LittleEndian.putUShort(bArr, 12, this.bottom);
            LittleEndian.putUShort(bArr, 14, this.inch);
            LittleEndian.putInt(bArr, 16, 0);
            int checksum2 = getChecksum();
            this.checksum = checksum2;
            LittleEndian.putUShort(bArr, 20, checksum2);
            outputStream.write(bArr);
        }
    }
}
