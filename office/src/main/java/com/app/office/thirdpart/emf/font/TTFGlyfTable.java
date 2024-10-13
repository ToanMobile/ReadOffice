package com.app.office.thirdpart.emf.font;

import com.app.office.java.awt.Rectangle;
import com.app.office.java.awt.Shape;
import com.app.office.java.awt.geom.AffineTransform;
import com.app.office.java.awt.geom.GeneralPath;
import java.io.IOException;

public class TTFGlyfTable extends TTFVersionTable {
    private static final boolean READ_GLYPHS = false;
    public Glyph[] glyphs;
    private long[] offsets;

    public String getTag() {
        return "glyf";
    }

    public abstract class Glyph {
        public int xMax;
        public int xMin;
        public int yMax;
        public int yMin;

        public abstract GeneralPath getShape();

        public abstract String getType();

        public Glyph() {
        }

        public void read() throws IOException {
            this.xMin = TTFGlyfTable.this.ttf.readFWord();
            this.yMin = TTFGlyfTable.this.ttf.readFWord();
            this.xMax = TTFGlyfTable.this.ttf.readFWord();
            this.yMax = TTFGlyfTable.this.ttf.readFWord();
        }

        public Rectangle getBBox() {
            int i = this.xMin;
            int i2 = this.yMin;
            return new Rectangle(i, i2, this.xMax - i, this.yMax - i2);
        }

        public String toString() {
            return "[" + getType() + "] (" + this.xMin + "," + this.yMin + "):(" + this.xMax + "," + this.yMax + ")";
        }

        public String toDetailedString() {
            return toString();
        }
    }

    public class SimpleGlyph extends Glyph {
        private static final int ON_CURVE = 0;
        private static final int REPEAT_FLAG = 3;
        private static final int X_POSITIVE = 4;
        private static final int X_SAME = 4;
        private static final int X_SHORT = 1;
        private static final int Y_POSITIVE = 5;
        private static final int Y_SAME = 5;
        private static final int Y_SHORT = 2;
        public int[] endPtsOfContours;
        public int[] flags;
        public int[] instructions;
        public int numberOfContours;
        public boolean[] onCurve;
        public GeneralPath shape;
        public int[] xCoordinates;
        public int[] yCoordinates;

        public String getType() {
            return "Simple Glyph";
        }

        public SimpleGlyph(int i) {
            super();
            this.numberOfContours = i;
            this.endPtsOfContours = new int[i];
        }

        public void read() throws IOException {
            super.read();
            int i = 0;
            while (true) {
                int[] iArr = this.endPtsOfContours;
                if (i >= iArr.length) {
                    break;
                }
                iArr[i] = TTFGlyfTable.this.ttf.readUShort();
                i++;
            }
            this.instructions = new int[TTFGlyfTable.this.ttf.readUShort()];
            int i2 = 0;
            while (true) {
                int[] iArr2 = this.instructions;
                if (i2 >= iArr2.length) {
                    break;
                }
                iArr2[i2] = TTFGlyfTable.this.ttf.readByte();
                i2++;
            }
            int[] iArr3 = this.endPtsOfContours;
            int i3 = iArr3[iArr3.length - 1] + 1;
            this.flags = new int[i3];
            this.xCoordinates = new int[i3];
            this.yCoordinates = new int[i3];
            this.onCurve = new boolean[i3];
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < i3; i6++) {
                if (i4 > 0) {
                    this.flags[i6] = i5;
                    i4--;
                } else {
                    this.flags[i6] = TTFGlyfTable.this.ttf.readRawByte();
                    if (TTFInput.flagBit(this.flags[i6], 3)) {
                        i4 = TTFGlyfTable.this.ttf.readByte();
                        i5 = this.flags[i6];
                    }
                }
                TTFInput.checkZeroBit(this.flags[i6], 6, "flags");
                TTFInput.checkZeroBit(this.flags[i6], 7, "flags");
                this.onCurve[i6] = TTFInput.flagBit(this.flags[i6], 0);
            }
            int i7 = 0;
            for (int i8 = 0; i8 < i3; i8++) {
                if (TTFInput.flagBit(this.flags[i8], 1)) {
                    if (TTFInput.flagBit(this.flags[i8], 4)) {
                        int[] iArr4 = this.xCoordinates;
                        i7 += TTFGlyfTable.this.ttf.readByte();
                        iArr4[i8] = i7;
                    } else {
                        int[] iArr5 = this.xCoordinates;
                        i7 -= TTFGlyfTable.this.ttf.readByte();
                        iArr5[i8] = i7;
                    }
                } else if (TTFInput.flagBit(this.flags[i8], 4)) {
                    this.xCoordinates[i8] = i7;
                } else {
                    int[] iArr6 = this.xCoordinates;
                    i7 += TTFGlyfTable.this.ttf.readShort();
                    iArr6[i8] = i7;
                }
            }
            int i9 = 0;
            for (int i10 = 0; i10 < i3; i10++) {
                if (TTFInput.flagBit(this.flags[i10], 2)) {
                    if (TTFInput.flagBit(this.flags[i10], 5)) {
                        int[] iArr7 = this.yCoordinates;
                        i9 += TTFGlyfTable.this.ttf.readByte();
                        iArr7[i10] = i9;
                    } else {
                        int[] iArr8 = this.yCoordinates;
                        i9 -= TTFGlyfTable.this.ttf.readByte();
                        iArr8[i10] = i9;
                    }
                } else if (TTFInput.flagBit(this.flags[i10], 5)) {
                    this.yCoordinates[i10] = i9;
                } else {
                    int[] iArr9 = this.yCoordinates;
                    i9 += TTFGlyfTable.this.ttf.readShort();
                    iArr9[i10] = i9;
                }
            }
        }

        public String toString() {
            String str = super.toString() + ", " + this.numberOfContours + " contours, endPts={";
            int i = 0;
            while (i < this.numberOfContours) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(i == 0 ? "" : ",");
                sb.append(this.endPtsOfContours[i]);
                str = sb.toString();
                i++;
            }
            return str + "}, " + this.instructions.length + " instructions";
        }

        public String toDetailedString() {
            String str = toString() + "\n  instructions = {";
            for (int i = 0; i < this.instructions.length; i++) {
                str = str + Integer.toHexString(this.instructions[i]) + " ";
            }
            return str + "}";
        }

        public GeneralPath getShape() {
            GeneralPath generalPath = this.shape;
            if (generalPath != null) {
                return generalPath;
            }
            this.shape = new GeneralPath(1);
            int i = 0;
            int i2 = 0;
            while (i < this.endPtsOfContours.length) {
                int i3 = i2 + 1;
                this.shape.moveTo((float) this.xCoordinates[i2], (float) this.yCoordinates[i2]);
                boolean z = true;
                while (i3 <= this.endPtsOfContours[i]) {
                    if (this.onCurve[i3]) {
                        if (z) {
                            this.shape.lineTo((float) this.xCoordinates[i3], (float) this.yCoordinates[i3]);
                        } else {
                            GeneralPath generalPath2 = this.shape;
                            int[] iArr = this.xCoordinates;
                            int i4 = i3 - 1;
                            int[] iArr2 = this.yCoordinates;
                            generalPath2.quadTo((float) iArr[i4], (float) iArr2[i4], (float) iArr[i3], (float) iArr2[i3]);
                        }
                        z = true;
                    } else {
                        if (!z) {
                            int[] iArr3 = this.xCoordinates;
                            int i5 = i3 - 1;
                            int i6 = iArr3[i5];
                            int[] iArr4 = this.yCoordinates;
                            int i7 = iArr4[i5];
                            this.shape.quadTo((float) i6, (float) i7, (float) ((int) (((double) (iArr3[i3] + i6)) / 2.0d)), (float) ((int) (((double) (iArr4[i3] + i7)) / 2.0d)));
                        }
                        z = false;
                    }
                    i3++;
                }
                int i8 = i3 - 1;
                if (!this.onCurve[i8]) {
                    GeneralPath generalPath3 = this.shape;
                    int[] iArr5 = this.xCoordinates;
                    int[] iArr6 = this.yCoordinates;
                    generalPath3.quadTo((float) iArr5[i8], (float) iArr6[i8], (float) iArr5[i2], (float) iArr6[i2]);
                } else {
                    int[] iArr7 = this.xCoordinates;
                    if (iArr7[i8] == iArr7[i2]) {
                        int[] iArr8 = this.yCoordinates;
                        if (iArr8[i8] == iArr8[i2]) {
                        }
                    }
                    this.shape.closePath();
                }
                i++;
                i2 = i3;
            }
            return this.shape;
        }
    }

    public class CompositeGlyph extends Glyph {
        private static final int ARGS_WORDS = 0;
        private static final int ARGS_XY = 1;
        private static final int MORE_COMPONENTS = 5;
        private static final int SCALE = 3;
        private static final int TWO_BY_TWO = 7;
        private static final int XY_SCALE = 6;
        private int noComponents;
        private GeneralPath shape;

        public String getType() {
            return "Composite Glyph";
        }

        public CompositeGlyph() {
            super();
        }

        public GeneralPath getShape() {
            return this.shape;
        }

        public void read() throws IOException {
            int i;
            int i2;
            super.read();
            this.shape = new GeneralPath();
            this.noComponents = 0;
            boolean z = true;
            while (z) {
                this.noComponents++;
                TTFGlyfTable.this.ttf.readUShortFlags();
                z = TTFGlyfTable.this.ttf.flagBit(5);
                int readUShort = TTFGlyfTable.this.ttf.readUShort();
                if (TTFGlyfTable.this.ttf.flagBit(0)) {
                    i2 = TTFGlyfTable.this.ttf.readShort();
                    i = TTFGlyfTable.this.ttf.readShort();
                } else {
                    i2 = TTFGlyfTable.this.ttf.readChar();
                    i = TTFGlyfTable.this.ttf.readChar();
                }
                AffineTransform affineTransform = new AffineTransform();
                if (TTFGlyfTable.this.ttf.flagBit(1)) {
                    affineTransform.translate((double) i2, (double) i);
                } else {
                    System.err.println("TTFGlyfTable: ARGS_ARE_POINTS not implemented.");
                }
                if (TTFGlyfTable.this.ttf.flagBit(3)) {
                    double readF2Dot14 = TTFGlyfTable.this.ttf.readF2Dot14();
                    affineTransform.scale(readF2Dot14, readF2Dot14);
                } else if (TTFGlyfTable.this.ttf.flagBit(6)) {
                    affineTransform.scale(TTFGlyfTable.this.ttf.readF2Dot14(), TTFGlyfTable.this.ttf.readF2Dot14());
                } else if (TTFGlyfTable.this.ttf.flagBit(7)) {
                    System.err.println("TTFGlyfTable: WE_HAVE_A_TWO_BY_TWO not implemented.");
                }
                GeneralPath generalPath = (GeneralPath) TTFGlyfTable.this.getGlyph(readUShort).getShape().clone();
                generalPath.transform(affineTransform);
                this.shape.append((Shape) generalPath, false);
            }
        }

        public String toString() {
            return super.toString() + ", " + this.noComponents + " components";
        }
    }

    public void readTable() throws IOException {
        this.glyphs = new Glyph[((TTFMaxPTable) getTable("maxp")).numGlyphs];
        this.offsets = ((TTFLocaTable) getTable("loca")).offset;
    }

    public Glyph getGlyph(int i) throws IOException {
        Glyph[] glyphArr = this.glyphs;
        if (glyphArr[i] != null) {
            return glyphArr[i];
        }
        this.ttf.pushPos();
        this.ttf.seek(this.offsets[i]);
        short readShort = this.ttf.readShort();
        if (readShort >= 0) {
            this.glyphs[i] = new SimpleGlyph(readShort);
        } else {
            this.glyphs[i] = new CompositeGlyph();
        }
        this.glyphs[i].read();
        this.ttf.popPos();
        return this.glyphs[i];
    }

    public String toString() {
        String tTFVersionTable = super.toString();
        for (int i = 0; i < this.glyphs.length; i++) {
            tTFVersionTable = tTFVersionTable + "\n  #" + i + ": " + this.glyphs[i];
        }
        return tTFVersionTable;
    }
}
