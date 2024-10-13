package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndianOutput;

public final class AttrPtg extends ControlPtg {
    private static final int SIZE = 4;
    public static final AttrPtg SUM = new AttrPtg(16, 0, (int[]) null, -1);
    private static final BitField baxcel = BitFieldFactory.getInstance(32);
    private static final BitField optiChoose = BitFieldFactory.getInstance(4);
    private static final BitField optiIf = BitFieldFactory.getInstance(2);
    private static final BitField optiSkip = BitFieldFactory.getInstance(8);
    private static final BitField optiSum = BitFieldFactory.getInstance(16);
    private static final BitField semiVolatile = BitFieldFactory.getInstance(1);
    public static final byte sid = 25;
    private static final BitField space = BitFieldFactory.getInstance(64);
    private final int _chooseFuncOffset;
    private final short _data;
    private final int[] _jumpTable;
    private final byte _options;

    public int getNumberOfOperands() {
        return 1;
    }

    public int getType() {
        return -1;
    }

    public static final class SpaceType {
        public static final int CR_BEFORE = 1;
        public static final int CR_BEFORE_CLOSE_PAREN = 5;
        public static final int CR_BEFORE_OPEN_PAREN = 3;
        public static final int SPACE_AFTER_EQUALITY = 6;
        public static final int SPACE_BEFORE = 0;
        public static final int SPACE_BEFORE_CLOSE_PAREN = 4;
        public static final int SPACE_BEFORE_OPEN_PAREN = 2;

        private SpaceType() {
        }
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [int, short] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AttrPtg(com.app.office.fc.util.LittleEndianInput r5) {
        /*
            r4 = this;
            r4.<init>()
            byte r0 = r5.readByte()
            r4._options = r0
            short r0 = r5.readShort()
            r4._data = r0
            boolean r1 = r4.isOptimizedChoose()
            if (r1 == 0) goto L_0x002c
            int[] r1 = new int[r0]
            r2 = 0
        L_0x0018:
            if (r2 >= r0) goto L_0x0023
            int r3 = r5.readUShort()
            r1[r2] = r3
            int r2 = r2 + 1
            goto L_0x0018
        L_0x0023:
            r4._jumpTable = r1
            int r5 = r5.readUShort()
            r4._chooseFuncOffset = r5
            goto L_0x0032
        L_0x002c:
            r5 = 0
            r4._jumpTable = r5
            r5 = -1
            r4._chooseFuncOffset = r5
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.hssf.formula.ptg.AttrPtg.<init>(com.app.office.fc.util.LittleEndianInput):void");
    }

    private AttrPtg(int i, int i2, int[] iArr, int i3) {
        this._options = (byte) i;
        this._data = (short) i2;
        this._jumpTable = iArr;
        this._chooseFuncOffset = i3;
    }

    public static AttrPtg createSpace(int i, int i2) {
        return new AttrPtg(space.set(0), (i & 255) | ((i2 << 8) & 65535), (int[]) null, -1);
    }

    public static AttrPtg createIf(int i) {
        return new AttrPtg(optiIf.set(0), i, (int[]) null, -1);
    }

    public static AttrPtg createSkip(int i) {
        return new AttrPtg(optiSkip.set(0), i, (int[]) null, -1);
    }

    public static AttrPtg getSumSingle() {
        return new AttrPtg(optiSum.set(0), 0, (int[]) null, -1);
    }

    public boolean isSemiVolatile() {
        return semiVolatile.isSet(this._options);
    }

    public boolean isOptimizedIf() {
        return optiIf.isSet(this._options);
    }

    public boolean isOptimizedChoose() {
        return optiChoose.isSet(this._options);
    }

    public boolean isSum() {
        return optiSum.isSet(this._options);
    }

    public boolean isSkip() {
        return optiSkip.isSet(this._options);
    }

    private boolean isBaxcel() {
        return baxcel.isSet(this._options);
    }

    public boolean isSpace() {
        return space.isSet(this._options);
    }

    public short getData() {
        return this._data;
    }

    public int[] getJumpTable() {
        return (int[]) this._jumpTable.clone();
    }

    public int getChooseFuncOffset() {
        if (this._jumpTable != null) {
            return this._chooseFuncOffset;
        }
        throw new IllegalStateException("Not tAttrChoose");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        if (isSemiVolatile()) {
            stringBuffer.append("volatile ");
        }
        if (isSpace()) {
            stringBuffer.append("space count=");
            stringBuffer.append((this._data >> 8) & 255);
            stringBuffer.append(" type=");
            stringBuffer.append(this._data & 255);
            stringBuffer.append(" ");
        }
        if (isOptimizedIf()) {
            stringBuffer.append("if dist=");
            stringBuffer.append(this._data);
        } else if (isOptimizedChoose()) {
            stringBuffer.append("choose nCases=");
            stringBuffer.append(this._data);
        } else if (isSkip()) {
            stringBuffer.append("skip dist=");
            stringBuffer.append(this._data);
        } else if (isSum()) {
            stringBuffer.append("sum ");
        } else if (isBaxcel()) {
            stringBuffer.append("assign ");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 25);
        littleEndianOutput.writeByte(this._options);
        littleEndianOutput.writeShort(this._data);
        int[] iArr = this._jumpTable;
        if (iArr != null) {
            for (int writeShort : iArr) {
                littleEndianOutput.writeShort(writeShort);
            }
            littleEndianOutput.writeShort(this._chooseFuncOffset);
        }
    }

    public int getSize() {
        int[] iArr = this._jumpTable;
        if (iArr != null) {
            return ((iArr.length + 1) * 2) + 4;
        }
        return 4;
    }

    public String toFormulaString(String[] strArr) {
        if (space.isSet(this._options)) {
            return strArr[0];
        }
        if (optiIf.isSet(this._options)) {
            return toFormulaString() + "(" + strArr[0] + ")";
        } else if (optiSkip.isSet(this._options)) {
            return toFormulaString() + strArr[0];
        } else {
            return toFormulaString() + "(" + strArr[0] + ")";
        }
    }

    public String toFormulaString() {
        if (semiVolatile.isSet(this._options)) {
            return "ATTR(semiVolatile)";
        }
        if (optiIf.isSet(this._options)) {
            return "IF";
        }
        if (optiChoose.isSet(this._options)) {
            return "CHOOSE";
        }
        if (optiSkip.isSet(this._options)) {
            return "";
        }
        if (optiSum.isSet(this._options)) {
            return "SUM";
        }
        if (baxcel.isSet(this._options)) {
            return "ATTR(baxcel)";
        }
        if (space.isSet(this._options)) {
            return "";
        }
        return "UNKNOWN ATTRIBUTE";
    }
}
