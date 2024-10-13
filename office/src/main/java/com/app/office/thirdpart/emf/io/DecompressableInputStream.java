package com.app.office.thirdpart.emf.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class DecompressableInputStream extends DecodingInputStream {
    private byte[] b = null;
    private boolean decompress;
    private int i = 0;
    private InflaterInputStream iis;
    private InputStream in;
    private int len = 0;

    public DecompressableInputStream(InputStream inputStream) {
        this.in = inputStream;
        this.decompress = false;
        try {
            int available = inputStream.available();
            this.len = available;
            byte[] bArr = new byte[available];
            this.b = bArr;
            this.in.read(bArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int read() throws IOException {
        int i2 = this.i;
        if (i2 >= this.len) {
            return -1;
        }
        byte[] bArr = this.b;
        this.i = i2 + 1;
        return bArr[i2] & 255;
    }

    public long skip(long j) throws IOException {
        this.i = (int) (((long) this.i) + j);
        return j;
    }

    public void startDecompressing() throws IOException {
        this.decompress = true;
        this.iis = new InflaterInputStream(this.in);
    }
}
