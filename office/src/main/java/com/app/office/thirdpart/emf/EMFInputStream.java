package com.app.office.thirdpart.emf;

import android.graphics.Point;
import com.app.office.java.awt.Color;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.thirdpart.emf.io.ActionHeader;
import com.app.office.thirdpart.emf.io.ActionSet;
import com.app.office.thirdpart.emf.io.TagHeader;
import com.app.office.thirdpart.emf.io.TaggedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EMFInputStream extends TaggedInputStream implements EMFConstants {
    public static int DEFAULT_VERSION = 1;
    private EMFHeader header;

    /* access modifiers changed from: protected */
    public ActionHeader readActionHeader() throws IOException {
        return null;
    }

    public EMFInputStream(InputStream inputStream) {
        this(inputStream, DEFAULT_VERSION);
    }

    public EMFInputStream(InputStream inputStream, int i) {
        this(inputStream, new EMFTagSet(i));
    }

    public EMFInputStream(InputStream inputStream, EMFTagSet eMFTagSet) {
        super(inputStream, eMFTagSet, (ActionSet) null, true);
    }

    public int readDWORD() throws IOException {
        return (int) readUnsignedInt();
    }

    public int[] readDWORD(int i) throws IOException {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readDWORD();
        }
        return iArr;
    }

    public int readWORD() throws IOException {
        return readUnsignedShort();
    }

    public int readLONG() throws IOException {
        return readInt();
    }

    public int[] readLONG(int i) throws IOException {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readLONG();
        }
        return iArr;
    }

    public float readFLOAT() throws IOException {
        return readFloat();
    }

    public int readUINT() throws IOException {
        return (int) readUnsignedInt();
    }

    public int readULONG() throws IOException {
        return (int) readUnsignedInt();
    }

    public Color readCOLORREF() throws IOException {
        Color color = new Color(readUnsignedByte(), readUnsignedByte(), readUnsignedByte());
        readByte();
        return color;
    }

    public Color readCOLOR16() throws IOException {
        return new Color(readShort() >> 8, readShort() >> 8, readShort() >> 8, readShort() >> 8);
    }

    public AffineTransform readXFORM() throws IOException {
        return new AffineTransform(readFLOAT(), readFLOAT(), readFLOAT(), readFLOAT(), readFLOAT(), readFLOAT());
    }

    public Rectangle readRECTL() throws IOException {
        int readLONG = readLONG();
        int readLONG2 = readLONG();
        return new Rectangle(readLONG, readLONG2, readLONG() - readLONG, readLONG() - readLONG2);
    }

    public Point readPOINTL() throws IOException {
        return new Point(readLONG(), readLONG());
    }

    public Point[] readPOINTL(int i) throws IOException {
        Point[] pointArr = new Point[i];
        for (int i2 = 0; i2 < i; i2++) {
            pointArr[i2] = readPOINTL();
        }
        return pointArr;
    }

    public Point readPOINTS() throws IOException {
        return new Point(readShort(), readShort());
    }

    public Point[] readPOINTS(int i) throws IOException {
        Point[] pointArr = new Point[i];
        for (int i2 = 0; i2 < i; i2++) {
            pointArr[i2] = readPOINTS();
        }
        return pointArr;
    }

    public Dimension readSIZEL() throws IOException {
        return new Dimension(readLONG(), readLONG());
    }

    public int readBYTE() throws IOException {
        return readByte();
    }

    public byte[] readBYTE(int i) throws IOException {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) readBYTE();
        }
        return bArr;
    }

    public boolean readBOOLEAN() throws IOException {
        return readBYTE() != 0;
    }

    public String readWCHAR(int i) throws IOException {
        int i2 = i * 2;
        byte[] readByte = readByte(i2);
        int i3 = 0;
        while (true) {
            if (i3 < i2) {
                if (readByte[i3] == 0 && readByte[i3 + 1] == 0) {
                    i2 = i3;
                    break;
                }
                i3 += 2;
            } else {
                break;
            }
        }
        return new String(readByte, 0, i2, "UTF-16LE");
    }

    /* access modifiers changed from: protected */
    public TagHeader readTagHeader() throws IOException {
        int read = read();
        if (read == -1) {
            return null;
        }
        return new TagHeader(read | (readUnsignedByte() << 8) | (readUnsignedByte() << 16) | (readUnsignedByte() << 24), ((long) readDWORD()) - 8);
    }

    public EMFHeader readHeader() throws IOException {
        if (this.header == null) {
            this.header = new EMFHeader(this);
        }
        return this.header;
    }

    public int getVersion() {
        return DEFAULT_VERSION;
    }
}
