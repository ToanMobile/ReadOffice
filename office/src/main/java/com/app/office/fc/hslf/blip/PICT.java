package com.app.office.fc.hslf.blip;

import com.app.office.fc.hslf.blip.Metafile;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

public final class PICT extends Metafile {
    public int getSignature() {
        return 21552;
    }

    public int getType() {
        return 4;
    }

    public byte[] getData() {
        byte[] bArr;
        byte[] rawData = getRawData();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(new byte[512]);
            try {
                bArr = read(rawData, 16);
            } catch (IOException unused) {
                bArr = read(rawData, 32);
            }
            byteArrayOutputStream.write(bArr);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new HSLFException((Throwable) e);
        }
    }

    private byte[] read(byte[] bArr, int i) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        Metafile.Header header = new Metafile.Header();
        header.read(bArr, i);
        byteArrayInputStream.skip((long) (i + header.getSize()));
        InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[4096];
        while (true) {
            int read = inflaterInputStream.read(bArr2);
            if (read >= 0) {
                byteArrayOutputStream.write(bArr2, 0, read);
            } else {
                inflaterInputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public void setData(byte[] bArr) throws IOException {
        byte[] compress = compress(bArr, 512, bArr.length - 512);
        Metafile.Header header = new Metafile.Header();
        header.wmfsize = bArr.length - 512;
        header.bounds = new Rectangle(0, 0, 200, 200);
        header.size = new Dimension(header.bounds.width * 12700, header.bounds.height * 12700);
        header.zipsize = compress.length;
        byte[] checksum = getChecksum(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(checksum);
        byteArrayOutputStream.write(new byte[16]);
        header.write(byteArrayOutputStream);
        byteArrayOutputStream.write(compress);
        setRawData(byteArrayOutputStream.toByteArray());
    }
}
