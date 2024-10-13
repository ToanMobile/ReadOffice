package com.app.office.fc.hssf.record;

import com.app.office.common.shape.ShapeTypes;
import com.app.office.fc.util.LittleEndianOutput;
import java.util.ArrayList;
import java.util.List;

public final class PaletteRecord extends StandardRecord {
    public static final short FIRST_COLOR_INDEX = 8;
    public static final byte STANDARD_PALETTE_SIZE = 56;
    public static final short sid = 146;
    private final List<PColor> _colors;

    public short getSid() {
        return sid;
    }

    public PaletteRecord() {
        PColor[] createDefaultPalette = createDefaultPalette();
        this._colors = new ArrayList(createDefaultPalette.length);
        for (PColor add : createDefaultPalette) {
            this._colors.add(add);
        }
    }

    public PaletteRecord(RecordInputStream recordInputStream) {
        short readShort = recordInputStream.readShort();
        this._colors = new ArrayList(readShort);
        for (int i = 0; i < readShort; i++) {
            this._colors.add(new PColor(recordInputStream));
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PALETTE]\n");
        stringBuffer.append("  numcolors     = ");
        stringBuffer.append(this._colors.size());
        stringBuffer.append(10);
        for (int i = 0; i < this._colors.size(); i++) {
            stringBuffer.append("* colornum      = ");
            stringBuffer.append(i);
            stringBuffer.append(10);
            stringBuffer.append(this._colors.get(i).toString());
            stringBuffer.append("/*colornum      = ");
            stringBuffer.append(i);
            stringBuffer.append(10);
        }
        stringBuffer.append("[/PALETTE]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._colors.size());
        for (int i = 0; i < this._colors.size(); i++) {
            this._colors.get(i).serialize(littleEndianOutput);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this._colors.size() * 4) + 2;
    }

    public byte[] getColor(int i) {
        int i2 = i - 8;
        if (i2 < 0 || i2 >= this._colors.size()) {
            return null;
        }
        return this._colors.get(i2).getTriplet();
    }

    public void setColor(short s, byte b, byte b2, byte b3) {
        int i = s - 8;
        if (i >= 0 && i < 56) {
            while (this._colors.size() <= i) {
                this._colors.add(new PColor(0, 0, 0));
            }
            this._colors.set(i, new PColor(b, b2, b3));
        }
    }

    private static PColor[] createDefaultPalette() {
        return new PColor[]{pc(0, 0, 0), pc(255, 255, 255), pc(255, 0, 0), pc(0, 255, 0), pc(0, 0, 255), pc(255, 255, 0), pc(255, 0, 255), pc(0, 255, 255), pc(128, 0, 0), pc(0, 128, 0), pc(0, 0, 128), pc(128, 128, 0), pc(128, 0, 128), pc(0, 128, 128), pc(ShapeTypes.ActionButtonInformation, ShapeTypes.ActionButtonInformation, ShapeTypes.ActionButtonInformation), pc(128, 128, 128), pc(153, 153, 255), pc(153, 51, 102), pc(255, 255, 204), pc(204, 255, 255), pc(102, 0, 102), pc(255, 128, 128), pc(0, 102, 204), pc(204, 204, 255), pc(0, 0, 128), pc(255, 0, 255), pc(255, 255, 0), pc(0, 255, 255), pc(128, 0, 128), pc(128, 0, 0), pc(0, 128, 128), pc(0, 0, 255), pc(0, 204, 255), pc(204, 255, 255), pc(204, 255, 204), pc(255, 255, 153), pc(153, 204, 255), pc(255, 153, 204), pc(204, 153, 255), pc(255, 204, 153), pc(51, 102, 255), pc(51, 204, 204), pc(153, 204, 0), pc(255, 204, 0), pc(255, 153, 0), pc(255, 102, 0), pc(102, 102, 153), pc(150, 150, 150), pc(0, 51, 102), pc(51, 153, 102), pc(0, 51, 0), pc(51, 51, 0), pc(153, 51, 0), pc(153, 51, 102), pc(51, 51, 153), pc(51, 51, 51)};
    }

    private static PColor pc(int i, int i2, int i3) {
        return new PColor(i, i2, i3);
    }

    private static final class PColor {
        public static final short ENCODED_SIZE = 4;
        private int _blue;
        private int _green;
        private int _red;

        public PColor(int i, int i2, int i3) {
            this._red = i;
            this._green = i2;
            this._blue = i3;
        }

        public byte[] getTriplet() {
            return new byte[]{(byte) this._red, (byte) this._green, (byte) this._blue};
        }

        public PColor(RecordInputStream recordInputStream) {
            this._red = recordInputStream.readByte();
            this._green = recordInputStream.readByte();
            this._blue = recordInputStream.readByte();
            recordInputStream.readByte();
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeByte(this._red);
            littleEndianOutput.writeByte(this._green);
            littleEndianOutput.writeByte(this._blue);
            littleEndianOutput.writeByte(0);
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("  red   = ");
            stringBuffer.append(this._red & 255);
            stringBuffer.append(10);
            stringBuffer.append("  green = ");
            stringBuffer.append(this._green & 255);
            stringBuffer.append(10);
            stringBuffer.append("  blue  = ");
            stringBuffer.append(this._blue & 255);
            stringBuffer.append(10);
            return stringBuffer.toString();
        }
    }
}
