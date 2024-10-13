package com.app.office.thirdpart.emf.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASCII85InputStream extends DecodingInputStream implements ASCII85 {
    private int[] b = new int[4];
    private int bIndex;
    private int bLength;
    private int[] c = new int[5];
    private boolean endReached;
    private InputStream in;
    private int lineNo;
    private int prev;

    public ASCII85InputStream(InputStream inputStream) {
        this.in = inputStream;
        this.bIndex = 0;
        this.bLength = 0;
        this.endReached = false;
        this.prev = -1;
        this.lineNo = 1;
    }

    public int read() throws IOException {
        if (this.bIndex >= this.bLength) {
            if (this.endReached) {
                return -1;
            }
            int readTuple = readTuple();
            this.bLength = readTuple;
            if (readTuple < 0) {
                return -1;
            }
            this.bIndex = 0;
        }
        int[] iArr = this.b;
        int i = this.bIndex;
        int i2 = iArr[i];
        this.bIndex = i + 1;
        return i2;
    }

    public int getLineNo() {
        return this.lineNo;
    }

    private int readTuple() throws IOException, EncodingException {
        int[] iArr;
        int i = 0;
        int i2 = -1;
        while (!this.endReached && i < 5) {
            this.prev = i2;
            i2 = this.in.read();
            if (i2 == -1) {
                throw new EncodingException("ASCII85InputStream: missing '~>' at end of stream");
            } else if (!(i2 == 0 || i2 == 9)) {
                if (i2 != 10) {
                    if (i2 == 12) {
                        continue;
                    } else if (i2 == 13) {
                        this.lineNo++;
                    } else if (i2 == 32) {
                        continue;
                    } else if (i2 != 122) {
                        if (i2 != 126) {
                            this.c[i] = i2;
                            i++;
                        } else {
                            i2 = this.in.read();
                            while (i2 >= 0 && i2 != 62 && Character.isWhitespace(i2)) {
                                i2 = this.in.read();
                            }
                            if (i2 == 62) {
                                this.endReached = true;
                            } else {
                                throw new EncodingException("ASCII85InputStream: Invalid EOD, expected '>', found " + i2);
                            }
                        }
                    } else if (i == 0) {
                        int[] iArr2 = this.b;
                        iArr2[3] = 0;
                        iArr2[2] = 0;
                        iArr2[1] = 0;
                        iArr2[0] = 0;
                        return 4;
                    } else {
                        throw new EncodingException("ASCII85InputStream: 'z' encoding can only appear at the start of a group, cIndex: " + i);
                    }
                } else if (this.prev != 13) {
                    this.lineNo++;
                }
            }
        }
        if (i > 0) {
            int i3 = 0;
            while (true) {
                iArr = this.c;
                if (i3 >= iArr.length) {
                    break;
                }
                if (i3 >= i) {
                    iArr[i3] = 117;
                } else {
                    iArr[i3] = iArr[i3] - 33;
                }
                i3++;
            }
            long j = 4294967295L & ((((long) iArr[0]) * ASCII85.a85p4) + (((long) iArr[1]) * ASCII85.a85p3) + (((long) iArr[2]) * ASCII85.a85p2) + (((long) iArr[3]) * 85) + ((long) iArr[4]));
            int[] iArr3 = this.b;
            iArr3[0] = (int) ((j >> 24) & 255);
            iArr3[1] = (int) ((j >> 16) & 255);
            iArr3[2] = (int) ((j >> 8) & 255);
            iArr3[3] = (int) (j & 255);
        }
        return i - 1;
    }

    public static void main(String[] strArr) throws Exception {
        if (strArr.length < 1) {
            System.err.println("Usage: ASCII85InputStream filename");
            System.exit(1);
        }
        ASCII85InputStream aSCII85InputStream = new ASCII85InputStream(new FileInputStream(strArr[0]));
        for (int read = aSCII85InputStream.read(); read != -1; read = aSCII85InputStream.read()) {
            System.out.write(read);
        }
        aSCII85InputStream.close();
        System.out.flush();
    }
}
