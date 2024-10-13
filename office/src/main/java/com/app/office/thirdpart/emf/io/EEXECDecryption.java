package com.app.office.thirdpart.emf.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EEXECDecryption extends InputStream implements EEXECConstants {
    private int c1;
    private int c2;
    private boolean first;
    private InputStream in;
    private int n;
    private int r;

    public EEXECDecryption(InputStream inputStream) {
        this(inputStream, 55665, 4);
    }

    public EEXECDecryption(InputStream inputStream, int i, int i2) {
        this.first = true;
        this.in = inputStream;
        this.r = i;
        this.n = i2;
        this.c1 = 52845;
        this.c2 = 22719;
    }

    private int decrypt(int i) {
        int i2 = this.r;
        int i3 = ((i2 >>> 8) ^ i) % 256;
        this.r = (((i + i2) * this.c1) + this.c2) % 65536;
        return i3;
    }

    public int read() throws IOException {
        if (this.first) {
            int i = this.n;
            byte[] bArr = new byte[i];
            boolean z = false;
            for (int i2 = 0; i2 < i; i2++) {
                int read = this.in.read();
                bArr[i2] = (byte) read;
                if (!Character.isDigit((char) read) && ((read < 97 || read > 102) && (read < 65 || read > 70))) {
                    z = true;
                }
            }
            if (z) {
                for (int i3 = 0; i3 < i; i3++) {
                    decrypt(bArr[i3] & 255);
                }
            } else {
                ASCIIHexInputStream aSCIIHexInputStream = new ASCIIHexInputStream(new ByteArrayInputStream(bArr), true);
                int i4 = 0;
                while (true) {
                    int read2 = aSCIIHexInputStream.read();
                    if (read2 < 0) {
                        break;
                    }
                    decrypt(read2);
                    i4++;
                }
                this.in = new ASCIIHexInputStream(this.in, true);
                while (i4 < this.n) {
                    decrypt(this.in.read());
                    i4++;
                }
            }
            this.first = false;
        }
        int read3 = this.in.read();
        if (read3 == -1) {
            return -1;
        }
        return decrypt(read3);
    }

    public void close() throws IOException {
        super.close();
        this.in.close();
    }
}
