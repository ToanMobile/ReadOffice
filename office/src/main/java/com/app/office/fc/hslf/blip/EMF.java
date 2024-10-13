package com.app.office.fc.hslf.blip;

import com.app.office.fc.hslf.blip.Metafile;
import com.app.office.fc.hslf.exceptions.HSLFException;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

public final class EMF extends Metafile {
    public int getSignature() {
        return 15680;
    }

    public int getType() {
        return 2;
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
        byte[] compress = compress(bArr, 0, bArr.length);
        Metafile.Header header = new Metafile.Header();
        header.wmfsize = bArr.length;
        header.bounds = new Rectangle(0, 0, 200, 200);
        header.size = new Dimension(header.bounds.width * 12700, header.bounds.height * 12700);
        header.zipsize = compress.length;
        byte[] checksum = getChecksum(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(checksum);
        header.write(byteArrayOutputStream);
        byteArrayOutputStream.write(compress);
        setRawData(byteArrayOutputStream.toByteArray());
    }
}
