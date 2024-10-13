package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.sprm.ParagraphSprmUncompressor;
import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.hwpf.sprm.SprmOperation;
import com.app.office.fc.hwpf.usermodel.ParagraphProperties;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public final class PAPX extends BytePropertyNode<PAPX> {
    private ParagraphHeight _phe;

    public PAPX(int i, int i2, CharIndexTranslator charIndexTranslator, byte[] bArr, ParagraphHeight paragraphHeight, byte[] bArr2) {
        super(i, i2, charIndexTranslator, new SprmBuffer(bArr, 2));
        this._phe = paragraphHeight;
        SprmBuffer findHuge = findHuge(new SprmBuffer(bArr, 2), bArr2);
        if (findHuge != null) {
            this._buf = findHuge;
        }
    }

    public PAPX(int i, int i2, byte[] bArr, ParagraphHeight paragraphHeight, byte[] bArr2) {
        super(i, i2, new SprmBuffer(bArr, 2));
        this._phe = paragraphHeight;
        SprmBuffer findHuge = findHuge(new SprmBuffer(bArr, 2), bArr2);
        if (findHuge != null) {
            this._buf = findHuge;
        }
    }

    @Deprecated
    public PAPX(int i, int i2, CharIndexTranslator charIndexTranslator, SprmBuffer sprmBuffer, byte[] bArr) {
        super(i, i2, charIndexTranslator, sprmBuffer);
        this._phe = new ParagraphHeight();
        SprmBuffer findHuge = findHuge(sprmBuffer, bArr);
        if (findHuge != null) {
            this._buf = findHuge;
        }
    }

    public PAPX(int i, int i2, SprmBuffer sprmBuffer) {
        super(i, i2, sprmBuffer);
        this._phe = new ParagraphHeight();
    }

    private SprmBuffer findHuge(SprmBuffer sprmBuffer, byte[] bArr) {
        byte[] byteArray = sprmBuffer.toByteArray();
        if (byteArray.length != 8 || bArr == null) {
            return null;
        }
        SprmOperation sprmOperation = new SprmOperation(byteArray, 2);
        if ((sprmOperation.getOperation() != 69 && sprmOperation.getOperation() != 70) || sprmOperation.getSizeCode() != 3) {
            return null;
        }
        int operand = sprmOperation.getOperand();
        if (operand + 1 >= bArr.length) {
            return null;
        }
        short s = LittleEndian.getShort(bArr, operand);
        if (operand + s >= bArr.length) {
            return null;
        }
        byte[] bArr2 = new byte[(s + 2)];
        bArr2[0] = byteArray[0];
        bArr2[1] = byteArray[1];
        System.arraycopy(bArr, operand + 2, bArr2, 2, s);
        return new SprmBuffer(bArr2, 2);
    }

    public ParagraphHeight getParagraphHeight() {
        return this._phe;
    }

    public byte[] getGrpprl() {
        return ((SprmBuffer) this._buf).toByteArray();
    }

    public short getIstd() {
        byte[] grpprl = getGrpprl();
        if (grpprl.length == 0) {
            return 0;
        }
        if (grpprl.length == 1) {
            return (short) LittleEndian.getUnsignedByte(grpprl, 0);
        }
        return LittleEndian.getShort(grpprl);
    }

    public SprmBuffer getSprmBuf() {
        return (SprmBuffer) this._buf;
    }

    public ParagraphProperties getParagraphProperties(StyleSheet styleSheet) {
        if (styleSheet == null) {
            return new ParagraphProperties();
        }
        return ParagraphSprmUncompressor.uncompressPAP(styleSheet.getParagraphStyle(getIstd()), getGrpprl(), 2);
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return this._phe.equals(((PAPX) obj)._phe);
        }
        return false;
    }

    public String toString() {
        return "PAPX from " + getStart() + " to " + getEnd() + " (in bytes " + getStartBytes() + " to " + getEndBytes() + ")";
    }
}
