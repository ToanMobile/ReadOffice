package com.app.office.fc.hslf.blip;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.fc.hslf.usermodel.PictureData;
import com.app.office.fc.util.LittleEndian;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public abstract class Metafile extends PictureData {

    public static class Header {
        public Rectangle bounds;
        public int compression;
        public int filter = TIFFConstants.TIFFTAG_SUBFILETYPE;
        public Dimension size;
        public int wmfsize;
        public int zipsize;

        public int getSize() {
            return 34;
        }

        public void read(byte[] bArr, int i) {
            this.wmfsize = LittleEndian.getInt(bArr, i);
            int i2 = i + 4;
            int i3 = LittleEndian.getInt(bArr, i2);
            int i4 = i2 + 4;
            int i5 = LittleEndian.getInt(bArr, i4);
            int i6 = i4 + 4;
            int i7 = LittleEndian.getInt(bArr, i6);
            int i8 = i6 + 4;
            int i9 = LittleEndian.getInt(bArr, i8);
            int i10 = i8 + 4;
            this.bounds = new Rectangle(i3, i5, i7 - i3, i9 - i5);
            int i11 = LittleEndian.getInt(bArr, i10);
            int i12 = i10 + 4;
            int i13 = LittleEndian.getInt(bArr, i12);
            int i14 = i12 + 4;
            this.size = new Dimension(i11, i13);
            this.zipsize = LittleEndian.getInt(bArr, i14);
            int i15 = i14 + 4;
            this.compression = LittleEndian.getUnsignedByte(bArr, i15);
            this.filter = LittleEndian.getUnsignedByte(bArr, i15 + 1);
        }

        public void write(OutputStream outputStream) throws IOException {
            byte[] bArr = new byte[34];
            LittleEndian.putInt(bArr, 0, this.wmfsize);
            LittleEndian.putInt(bArr, 4, this.bounds.x);
            LittleEndian.putInt(bArr, 8, this.bounds.y);
            LittleEndian.putInt(bArr, 12, this.bounds.x + this.bounds.width);
            LittleEndian.putInt(bArr, 16, this.bounds.y + this.bounds.height);
            LittleEndian.putInt(bArr, 20, this.size.width);
            LittleEndian.putInt(bArr, 24, this.size.height);
            LittleEndian.putInt(bArr, 28, this.zipsize);
            bArr[32] = 0;
            bArr[33] = (byte) this.filter;
            outputStream.write(bArr);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] compress(byte[] bArr, int i, int i2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
        deflaterOutputStream.write(bArr, i, i2);
        deflaterOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public void writeByte_WMFAndEMF(FileOutputStream fileOutputStream) {
        try {
            byte[] rawData = getRawData();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(rawData);
            byteArrayInputStream.skip(8);
            Header header = new Header();
            header.read(rawData, 16);
            byteArrayInputStream.skip((long) (header.getSize() + 16));
            InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inflaterInputStream.read(bArr);
                if (read >= 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    inflaterInputStream.close();
                    return;
                }
            }
        } catch (IOException e) {
            throw new HSLFException((Throwable) e);
        }
    }
}
