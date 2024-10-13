package com.app.office.thirdpart.emf;

import com.itextpdf.text.pdf.codec.TIFFConstants;
import com.app.office.common.shape.ShapeTypes;
import com.app.office.java.awt.Dimension;
import com.app.office.java.awt.Rectangle;
import java.io.IOException;

public class EMFHeader implements EMFConstants {
    private static final Dimension screenMM = new Dimension(TIFFConstants.TIFFTAG_COLORMAP, ShapeTypes.Funnel);
    private Rectangle bounds;
    private int bytes;
    private String description;
    private Dimension device;
    private Rectangle frame;
    private int handles;
    private Dimension micrometers;
    private Dimension millimeters;
    private boolean openGL;
    private int palEntries;
    private int records;
    private String signature;
    private int versionMajor;
    private int versionMinor;

    public EMFHeader(Rectangle rectangle, int i, int i2, int i3, int i4, int i5, String str, String str2, Dimension dimension) {
        Rectangle rectangle2 = rectangle;
        Dimension dimension2 = dimension;
        this.bounds = rectangle2;
        Dimension dimension3 = screenMM;
        double d = ((double) dimension3.width) / ((double) dimension2.width);
        double d2 = ((double) dimension3.height) / ((double) dimension2.height);
        this.frame = new Rectangle((int) (((double) (rectangle2.x * 100)) * d), (int) (((double) (rectangle2.y * 100)) * d2), (int) (((double) (rectangle2.width * 100)) * d), (int) (((double) (rectangle2.height * 100)) * d2));
        this.signature = " EMF";
        this.versionMajor = i;
        this.versionMinor = i2;
        this.bytes = i3;
        this.records = i4;
        this.handles = i5;
        this.description = str.trim() + "\u0000" + str2.trim() + "\u0000\u0000";
        this.palEntries = 0;
        this.device = dimension2;
        this.millimeters = dimension3;
        this.openGL = false;
        this.micrometers = new Dimension(dimension3.width * 1000, dimension3.height * 1000);
    }

    EMFHeader(EMFInputStream eMFInputStream) throws IOException {
        eMFInputStream.readUnsignedInt();
        int readDWORD = eMFInputStream.readDWORD();
        this.bounds = eMFInputStream.readRECTL();
        this.frame = eMFInputStream.readRECTL();
        this.signature = new String(eMFInputStream.readBYTE(4));
        int readDWORD2 = eMFInputStream.readDWORD();
        this.versionMajor = readDWORD2 >> 16;
        this.versionMinor = readDWORD2 & 65535;
        this.bytes = eMFInputStream.readDWORD();
        this.records = eMFInputStream.readDWORD();
        this.handles = eMFInputStream.readWORD();
        eMFInputStream.readWORD();
        int readDWORD3 = eMFInputStream.readDWORD();
        int readDWORD4 = eMFInputStream.readDWORD();
        this.palEntries = eMFInputStream.readDWORD();
        this.device = eMFInputStream.readSIZEL();
        this.millimeters = eMFInputStream.readSIZEL();
        int i = 100;
        if (readDWORD4 > 88) {
            eMFInputStream.readDWORD();
            eMFInputStream.readDWORD();
            this.openGL = eMFInputStream.readDWORD() != 0;
            if (readDWORD4 > 100) {
                this.micrometers = eMFInputStream.readSIZEL();
                i = 108;
            }
        } else {
            i = 88;
        }
        if (i < readDWORD4) {
            eMFInputStream.skipBytes(readDWORD4 - i);
        } else {
            readDWORD4 = i;
        }
        this.description = eMFInputStream.readWCHAR(readDWORD3);
        int i2 = readDWORD4 + (readDWORD3 * 2);
        if (i2 < readDWORD) {
            eMFInputStream.skipBytes(readDWORD - i2);
        }
    }

    public int size() {
        return (this.description.length() * 2) + 108;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("EMF Header\n");
        stringBuffer.append("  bounds: ");
        stringBuffer.append(this.bounds);
        stringBuffer.append("\n");
        stringBuffer.append("  frame: ");
        stringBuffer.append(this.frame);
        stringBuffer.append("\n");
        stringBuffer.append("  signature: ");
        stringBuffer.append(this.signature);
        stringBuffer.append("\n");
        stringBuffer.append("  versionMajor: ");
        stringBuffer.append(this.versionMajor);
        stringBuffer.append("\n");
        stringBuffer.append("  versionMinor: ");
        stringBuffer.append(this.versionMinor);
        stringBuffer.append("\n");
        stringBuffer.append("  #bytes: ");
        stringBuffer.append(this.bytes);
        stringBuffer.append("\n");
        stringBuffer.append("  #records: ");
        stringBuffer.append(this.records);
        stringBuffer.append("\n");
        stringBuffer.append("  #handles: ");
        stringBuffer.append(this.handles);
        stringBuffer.append("\n");
        stringBuffer.append("  description: ");
        stringBuffer.append(this.description);
        stringBuffer.append("\n");
        stringBuffer.append("  #palEntries: ");
        stringBuffer.append(this.palEntries);
        stringBuffer.append("\n");
        stringBuffer.append("  device: ");
        stringBuffer.append(this.device);
        stringBuffer.append("\n");
        stringBuffer.append("  millimeters: ");
        stringBuffer.append(this.millimeters);
        stringBuffer.append("\n");
        stringBuffer.append("  openGL: ");
        stringBuffer.append(this.openGL);
        stringBuffer.append("\n");
        stringBuffer.append("  micrometers: ");
        stringBuffer.append(this.micrometers);
        return stringBuffer.toString();
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public Rectangle getFrame() {
        return this.frame;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getDescription() {
        return this.description;
    }

    public Dimension getDevice() {
        return this.device;
    }

    public Dimension getMillimeters() {
        return this.millimeters;
    }

    public Dimension getMicrometers() {
        return this.micrometers;
    }

    public boolean isOpenGL() {
        return this.openGL;
    }
}
